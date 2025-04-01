package com.softourtech.softlottieloaderexample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.softourtech.slt.SLTLoader;

public class MainActivity extends AppCompatActivity {

    private SLTLoader sltLoaderOverlay; // For full-screen overlay loaders
    private SLTLoader sltLoaderInsideButton; // For inside-button loader
    private FrameLayout rootLayout;
    private FrameLayout insideButtonContainer;
    private Button insideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize root layout for overlay loaders
        rootLayout = findViewById(R.id.root_layout);
        sltLoaderOverlay = new SLTLoader(this, rootLayout);

        // Initialize inside button container and button
        insideButtonContainer = findViewById(R.id.inside_button_container);
        insideButton = findViewById(R.id.button_inside);

        // Buttons for different demos
        Button defaultLoaderButton = findViewById(R.id.button_default_loader);
        Button customConfigLoaderButton = findViewById(R.id.button_custom_config_loader);
        Button customParamsLoaderButton = findViewById(R.id.button_custom_params_loader);
        Button insideButtonLoaderButton = findViewById(R.id.button_inside_loader);
        Button hideLoaderButton = findViewById(R.id.button_hide_loader);

        // Set callback for overlay loader
        sltLoaderOverlay.setLoaderCallback(new SLTLoader.LoaderCallback() {
            @Override
            public void onLoaderShown() {
                System.out.println("Overlay Loader Shown!");
            }

            @Override
            public void onLoaderHidden() {
                System.out.println("Overlay Loader Hidden!");
            }
        });

        // 1. Default Loader (Minimal Configuration)
        defaultLoaderButton.setOnClickListener(v -> {
            sltLoaderOverlay.showDefaultLoader(R.raw.default_loader);
            simulateLoading(sltLoaderOverlay, 3000);
        });

        // 2. Custom Loader with LoaderConfig (Fluent API)
        customConfigLoaderButton.setOnClickListener(v -> {
            SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.default_loader)
                    .setWidthDp(40)
                    .setHeightDp(40)
                    .setUseRoundedBox(true)
                    .setOverlayColor(Color.parseColor("#80000000"))
                    .setChangeJsonColor(false);
            sltLoaderOverlay.showCustomLoader(config);

        });

        // 3. Custom Loader with Individual Parameters (Explicit Control)
        customParamsLoaderButton.setOnClickListener(v -> {
            int widthPx = sltLoaderOverlay.dpToPx(80);
            int heightPx = sltLoaderOverlay.dpToPx(80);
            sltLoaderOverlay.showCustomLoader(
                    R.raw.default_loader,
                    widthPx,
                    heightPx,
                    false, // No rounded box
                    Color.parseColor("#9000BCD4"), // Cyan overlay
                    true,
                    Color.MAGENTA,
                    0.8f // Slower speed
            );
            simulateLoading(sltLoaderOverlay, 5000);
        });

        // 4. Inside Button Loader (Triggered by Separate Button)
        insideButtonLoaderButton.setOnClickListener(v -> {
            sltLoaderInsideButton = new SLTLoader(this, insideButtonContainer);
            SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.default_loader)
                    .setWidthDp(30)  // Small size to fit inside button
                    .setHeightDp(30)
                    .setOverlayColor(Color.TRANSPARENT) // No overlay
                    .setChangeJsonColor(true)
                    .setJsonColor(Color.RED)
                    .setAnimationSpeed(1.0f);
            sltLoaderInsideButton.showCustomLoader(config);
            insideButton.setText(""); // Hide text while loading
            simulateLoading(sltLoaderInsideButton, 3000, () -> insideButton.setText("Inside Loader"));
        });

        // 5. Hide Loader Manually
        hideLoaderButton.setOnClickListener(v -> {
            if (sltLoaderOverlay.isLoaderVisible()) {
                sltLoaderOverlay.hideLoader();
            }
            if (sltLoaderInsideButton != null && sltLoaderInsideButton.isLoaderVisible()) {
                sltLoaderInsideButton.hideLoader();
                insideButton.setText("Inside Loader");
            }
        });

        // 6. Inside Button Click (Triggers Loading Inside Itself)
        insideButton.setOnClickListener(v -> {
            if (sltLoaderInsideButton == null || !sltLoaderInsideButton.isLoaderVisible()) {
                sltLoaderInsideButton = new SLTLoader(this, insideButtonContainer);
                sltLoaderInsideButton.showDefaultLoader(R.raw.default_loader);
                insideButton.setText("");
                simulateLoading(sltLoaderInsideButton, 2000, () -> insideButton.setText("Inside Loader"));
            }
        });
    }

    // Simulate loading with optional callback
    private void simulateLoading(SLTLoader loader, long durationMs) {
        simulateLoading(loader, durationMs, null);
    }

    private void simulateLoading(SLTLoader loader, long durationMs, Runnable onComplete) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loader.hideLoader();
            if (onComplete != null) onComplete.run();
        }, durationMs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sltLoaderOverlay.onDestroy();
        if (sltLoaderInsideButton != null) {
            sltLoaderInsideButton.onDestroy();
        }
    }
}