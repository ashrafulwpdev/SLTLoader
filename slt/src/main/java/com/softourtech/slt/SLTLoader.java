package com.softourtech.slt;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;

import java.io.IOException;

/**
 * SLTLoader provides a reusable Lottie-based loading overlay for Android apps.
 * It blocks UI interaction during background tasks with customizable animations and colors.
 */
public class SLTLoader {

    private static final String TAG = "SLTLoader";
    private static final int DEFAULT_ANIMATION_DURATION = 250;
    public static final int DEFAULT_LOADER_SIZE_DP = 40;
    private static final int LOADER_PADDING_DP = 16;
    private static final int MAX_LOADER_DURATION_MS = 15000; // 15 sec timeout
    public static final int DEFAULT_OVERLAY_COLOR = Color.parseColor("#80000000");

    private final Context context;
    private FrameLayout loaderOverlay;
    private LottieAnimationView loaderAnimation;
    private int currentAnimationRes = -1;
    private boolean isLoaderVisible = false;
    private Runnable hideRunnable;
    private LoaderCallback loaderCallback;
    private ViewGroup rootView; // Root view to attach the loader

    // Callback interface for loading state changes
    public interface LoaderCallback {
        void onLoaderShown();
        void onLoaderHidden();
    }

    /**
     * Configuration class for the loader settings.
     */
    public static class LoaderConfig {
        int animationResId;
        int widthDp = DEFAULT_LOADER_SIZE_DP;
        int heightDp = DEFAULT_LOADER_SIZE_DP;
        boolean useRoundedBox = false;
        int overlayColor = DEFAULT_OVERLAY_COLOR;
        boolean changeJsonColor = true;
        int jsonColor = Color.parseColor("#4CAF50");
        float animationSpeed = 1.0f;

        public LoaderConfig(int animationResId) {
            this.animationResId = animationResId;
        }

        public LoaderConfig setWidthDp(int widthDp) {
            this.widthDp = widthDp;
            return this;
        }

        public LoaderConfig setHeightDp(int heightDp) {
            this.heightDp = heightDp;
            return this;
        }

        public LoaderConfig setUseRoundedBox(boolean useRoundedBox) {
            this.useRoundedBox = useRoundedBox;
            return this;
        }

        public LoaderConfig setOverlayColor(int overlayColor) {
            this.overlayColor = overlayColor;
            return this;
        }

        public LoaderConfig setChangeJsonColor(boolean changeJsonColor) {
            this.changeJsonColor = changeJsonColor;
            return this;
        }

        public LoaderConfig setJsonColor(int jsonColor) {
            this.jsonColor = jsonColor;
            return this;
        }

        public LoaderConfig setAnimationSpeed(float animationSpeed) {
            this.animationSpeed = animationSpeed;
            return this;
        }
    }

    public SLTLoader(Context context, ViewGroup rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    public int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    private boolean isValidResource(int resId) {
        try {
            context.getResources().openRawResource(resId).close();
            return true;
        } catch (Resources.NotFoundException | IOException e) {
            Log.e(TAG, "Resource validation failed for ID: " + resId, e);
        }
        return false;
    }

    public boolean isDarkMode() {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    public void setLoaderCallback(LoaderCallback callback) {
        this.loaderCallback = callback;
    }

    public boolean isLoaderVisible() {
        return isLoaderVisible;
    }

    public void showDefaultLoader(int defaultAnimationResId) {
        LoaderConfig config = new LoaderConfig(defaultAnimationResId)
                .setWidthDp(DEFAULT_LOADER_SIZE_DP)
                .setHeightDp(DEFAULT_LOADER_SIZE_DP)
                .setUseRoundedBox(false)
                .setOverlayColor(Color.TRANSPARENT)
                .setChangeJsonColor(false);
        showCustomLoader(config);
    }

    public void showCustomLoader(LoaderConfig config) {
        int widthPx = dpToPx(config.widthDp);
        int heightPx = dpToPx(config.heightDp);

        if (loaderOverlay == null) {
            initializeLoaderOverlay(config.animationResId, widthPx, heightPx, config.useRoundedBox,
                    config.overlayColor, config.changeJsonColor, config.jsonColor, config.animationSpeed);
        } else {
            updateLoaderOverlay(config.animationResId, config.overlayColor, config.changeJsonColor,
                    config.jsonColor, config.animationSpeed);
        }

        loaderOverlay.setAlpha(0f);
        loaderOverlay.animate().alpha(1f).setDuration(DEFAULT_ANIMATION_DURATION).start();
        loaderAnimation.playAnimation();
        isLoaderVisible = true;

        if (loaderCallback != null) {
            loaderCallback.onLoaderShown();
        }
        startLoaderTimeout();
    }

    public void showCustomLoader(int animationResId, int width, int height, boolean useRoundedBox,
                                 int overlayColor, boolean changeJsonColor, int jsonColor, float animationSpeed) {
        if (loaderOverlay == null) {
            initializeLoaderOverlay(animationResId, width, height, useRoundedBox, overlayColor,
                    changeJsonColor, jsonColor, animationSpeed);
        } else {
            updateLoaderOverlay(animationResId, overlayColor, changeJsonColor, jsonColor, animationSpeed);
        }

        loaderOverlay.setAlpha(0f);
        loaderOverlay.animate().alpha(1f).setDuration(DEFAULT_ANIMATION_DURATION).start();
        loaderAnimation.playAnimation();
        isLoaderVisible = true;

        if (loaderCallback != null) {
            loaderCallback.onLoaderShown();
        }
        startLoaderTimeout();
    }

    private void startLoaderTimeout() {
        if (hideRunnable != null) {
            loaderOverlay.removeCallbacks(hideRunnable);
        }
        hideRunnable = this::hideLoader;
        loaderOverlay.postDelayed(hideRunnable, MAX_LOADER_DURATION_MS);
    }

    private void initializeLoaderOverlay(int animationResId, int width, int height, boolean useRoundedBox,
                                         int overlayColor, boolean changeJsonColor, int jsonColor, float animationSpeed) {
        try {
            loaderOverlay = new FrameLayout(context);
            loaderOverlay.setClickable(true);
            loaderOverlay.setFocusable(true);
            loaderOverlay.setOnTouchListener((v, event) -> true);

            FrameLayout animationContainer = createAnimationContainer(useRoundedBox, width, height);
            loaderAnimation = createLottieAnimationView(animationResId, changeJsonColor, jsonColor, animationSpeed);

            animationContainer.addView(loaderAnimation, createLayoutParams(width, height));
            loaderOverlay.addView(animationContainer, createContainerLayoutParams(width, height, useRoundedBox));
            addOverlayToRootView(loaderOverlay, overlayColor);
        } catch (Exception e) {
            Log.e(TAG, "Error initializing loader: " + e.getMessage(), e);
            cleanupLoader();
        }
    }

    private FrameLayout createAnimationContainer(boolean useRoundedBox, int width, int height) {
        FrameLayout animationContainer = new FrameLayout(context);
        if (useRoundedBox) {
            // Use the custom drawable instead of the default one
            animationContainer.setBackgroundResource(R.drawable.loader_background);
            int padding = dpToPx(LOADER_PADDING_DP);
            animationContainer.setPadding(padding, padding, padding, padding);
        }
        return animationContainer;
    }

    private LottieAnimationView createLottieAnimationView(int animationResId, boolean changeJsonColor,
                                                          int jsonColor, float animationSpeed) {
        LottieAnimationView animationView = new LottieAnimationView(context);
        animationView.setContentDescription("Loading animation");
        if (isValidResource(animationResId)) {
            animationView.setAnimation(animationResId);
            currentAnimationRes = animationResId;
        } else {
            Log.e(TAG, "Loader file missing. No fallback animation available.");
            // Note: You need to add a default_loader.json to res/raw/ if you want a fallback
        }
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        animationView.setSpeed(animationSpeed);
        if (changeJsonColor) {
            animationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR, new LottieValueCallback<>(jsonColor));
        }
        return animationView;
    }

    private FrameLayout.LayoutParams createLayoutParams(int width, int height) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        return params;
    }

    private FrameLayout.LayoutParams createContainerLayoutParams(int width, int height, boolean useRoundedBox) {
        int backgroundWidth = useRoundedBox ? width + dpToPx(32) : ViewGroup.LayoutParams.WRAP_CONTENT;
        int backgroundHeight = useRoundedBox ? height + dpToPx(32) : ViewGroup.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(backgroundWidth, backgroundHeight);
        params.gravity = Gravity.CENTER;
        return params;
    }

    private void addOverlayToRootView(FrameLayout overlay, int overlayColor) {
        if (rootView != null) {
            rootView.addView(overlay);
            overlay.setBackgroundColor(overlayColor);
            Log.d(TAG, "Overlay added with color: " + String.format("#%08X", overlayColor));
        } else {
            Log.e(TAG, "Root view not found, cannot show loader");
        }
    }

    private void updateLoaderOverlay(int animationResId, int overlayColor, boolean changeJsonColor, int jsonColor,
                                     float animationSpeed) {
        loaderOverlay.setBackgroundColor(overlayColor);
        loaderOverlay.setVisibility(View.VISIBLE);

        if (animationResId != currentAnimationRes && isValidResource(animationResId)) {
            loaderAnimation.setAnimation(animationResId);
            currentAnimationRes = animationResId;
        }

        loaderAnimation.setSpeed(animationSpeed);
        if (changeJsonColor) {
            loaderAnimation.addValueCallback(new KeyPath("**"), LottieProperty.COLOR, new LottieValueCallback<>(jsonColor));
        }

        if (!loaderAnimation.isAnimating()) {
            loaderAnimation.playAnimation();
        }
    }

    public void hideLoader() {
        if (loaderOverlay != null && isLoaderVisible) {
            Log.d(TAG, "Hiding loader");
            loaderOverlay.animate()
                    .alpha(0f)
                    .setDuration(DEFAULT_ANIMATION_DURATION)
                    .withEndAction(this::cleanupLoader)
                    .start();
            if (hideRunnable != null) {
                loaderOverlay.removeCallbacks(hideRunnable);
                hideRunnable = null;
            }
            if (loaderCallback != null) {
                loaderCallback.onLoaderHidden();
            }
        }
    }

    private void cleanupLoader() {
        if (loaderOverlay != null) {
            loaderOverlay.setVisibility(View.GONE);
            if (rootView != null) {
                rootView.removeView(loaderOverlay);
            }
            loaderOverlay.removeAllViews();
            loaderOverlay = null;
        }

        if (loaderAnimation != null) {
            loaderAnimation.cancelAnimation();  // Stop the Lottie animation
            loaderAnimation.removeAllUpdateListeners();
            loaderAnimation.removeAllAnimatorListeners();
            loaderAnimation = null;
        }

        currentAnimationRes = -1;
        isLoaderVisible = false;
    }

    public void onDestroy() {
        if (loaderOverlay != null && hideRunnable != null) {
            loaderOverlay.removeCallbacks(hideRunnable);
        }
        cleanupLoader();
    }
}