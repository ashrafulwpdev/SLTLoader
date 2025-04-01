# 🌐 SLTLoader: Android Lottie Loader Utility
SLTLoader is a reusable, customizable loading overlay for Android applications built using Airbnb's Lottie animation library. It provides a sleek and modern way to block UI interaction during background tasks, featuring configurable animations, overlay colors, and sizes. Whether you're fetching data, processing tasks, or waiting for a response, SLTLoader enhances the user experience with smooth, visually appealing feedback.


[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![GitHub issues](https://img.shields.io/github/issues/ashrafulwpdev/SLTLoader)](https://github.com/ashrafulwpdev/SLTLoader/issues)
[![GitHub stars](https://img.shields.io/github/stars/ashrafulwpdev/SLTLoader)](https://github.com/ashrafulwpdev/SLTLoader/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/ashrafulwpdev/SLTLoader)](https://github.com/ashrafulwpdev/SLTLoader/network/members)

✅ **Company:** [Softourtech](https://github.com/Softourtech)  
✅ **Developer:** [Ashraful Islam](https://github.com/ashrafulwpdev)  
✅ **License:** [MIT License](LICENSE)  
✅ **Free for personal & commercial use** - Just credit [Softourtech](https://github.com/Softourtech)



## 🚀 Features
- 🔄 **Reusable Global Loader**: Use it anywhere in your app to block UI during background tasks.
- 🎨 **Optional Rounded Box**: Add a rounded background to the Lottie animation for a polished look.
- 🌈 **Dynamic Color Customization**: Change the Lottie animation’s color dynamically using modern Lottie color filters.
- 🚫 **Blocks User Interaction**: Prevents user interaction while the loader is visible.
- ✨ **Lottie JSON Support**: Supports Lottie animations from `res/raw/` with a fallback mechanism.
- 🛠 **Error Handling**: Includes validation for animation resources and a fallback animation if the specified resource is missing.
- 🎥 **Smooth Animations**: Fade-in and fade-out transitions for the loader overlay.


  ## 📸 Demonstration Screenshots

Here are some screenshots demonstrating the usage of SLTLoader:

<div style="display: flex; justify-content: center; gap: 10px; flex-wrap: wrap; padding: 10px;">
    <img src="https://raw.githubusercontent.com/ashrafulwpdev/SLTLoader/ashrafulwpdev-imp/Screenshot_20250401_112353.png" width="18%" style="border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);" />
    <img src="https://raw.githubusercontent.com/ashrafulwpdev/SLTLoader/ashrafulwpdev-imp/Screenshot_20250401_112533.png" width="18%" style="border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);" />
    <img src="https://raw.githubusercontent.com/ashrafulwpdev/SLTLoader/ashrafulwpdev-imp/Screenshot_20250401_112600.png" width="18%" style="border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);" />
    <img src="https://raw.githubusercontent.com/ashrafulwpdev/SLTLoader/ashrafulwpdev-imp/Screenshot_20250401_112625.png" width="18%" style="border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);" />
    <img src="https://raw.githubusercontent.com/ashrafulwpdev/SLTLoader/ashrafulwpdev-imp/Screenshot_20250401_112640.png" width="18%" style="border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);" />
</div>




## Installation

Add the JitPack repository to your project-level `build.gradle` file:

```groovy
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Then, add the dependency in your project-level `build.gradle` file:

```groovy
dependencies {
    implementation 'com.github.ashrafulwpdev:SLTLoader:1.0.1'
}
```


## Usage

### 1. Showing the Default Loader
---

```java
// Initialize the SLTLoader
SLTLoader loader = new SLTLoader(context, rootView);

// Show the default loader with a specific animation resource ID
loader.showDefaultLoader(R.raw.default_loader_animation);
```

### 2. Showing a Custom Loader with Configuration
---

```java
// Initialize the SLTLoader
SLTLoader loader = new SLTLoader(context, rootView);

// Configure the loader settings
SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.custom_loader_animation)
        .setWidthDp(50)
        .setHeightDp(50)
        .setUseRoundedBox(true)
        .setOverlayColor(Color.parseColor("#80000000"))
        .setChangeJsonColor(true)
        .setJsonColor(Color.parseColor("#FF0000"))
        .setAnimationSpeed(1.5f);

// Show the custom loader with the specified configuration
loader.showCustomLoader(config);
```

### 3. Showing a Custom Loader with Individual Parameters
---

```java
// Initialize the SLTLoader
SLTLoader loader = new SLTLoader(context, rootView);

// Show the custom loader with individual parameters
loader.showCustomLoader(
        R.raw.custom_loader_animation,
        50,  // width in dp
        50,  // height in dp
        true,  // use rounded box
        Color.parseColor("#80000000"),  // overlay color
        true,  // change JSON color
        Color.parseColor("#FF0000"),  // JSON color
        1.5f  // animation speed
);
```

### 4. Setting a Loader Callback
---

```java
// Initialize the SLTLoader
SLTLoader loader = new SLTLoader(context, rootView);

// Set a callback to listen for loader state changes
loader.setLoaderCallback(new SLTLoader.LoaderCallback() {
    @Override
    public void onLoaderShown() {
        // Loader has been shown
        Log.d("LoaderCallback", "Loader is visible");
    }

    @Override
    public void onLoaderHidden() {
        // Loader has been hidden
        Log.d("LoaderCallback", "Loader is hidden");
    }
});
```

### 5. Hiding the Loader
---

```java
// Initialize the SLTLoader
SLTLoader loader = new SLTLoader(context, rootView);

// Show the default loader
loader.showDefaultLoader(R.raw.default_loader_animation);

// Hide the loader
loader.hideLoader();
```

### 6. Cleaning Up the Loader on Destroy
---

```java
// Initialize the SLTLoader
SLTLoader loader = new SLTLoader(context, rootView);

// Show the default loader
loader.showDefaultLoader(R.raw.default_loader_animation);

// Clean up the loader when the activity or fragment is destroyed
@Override
protected void onDestroy() {
    super.onDestroy();
    loader.onDestroy();
}
```

### 7. Basic Usage
---

1. Add a root layout to your activity's layout file:

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/root_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <!-- Your other UI components -->

</FrameLayout>
```

2. Initialize `SLTLoader` in your activity:

```java
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.softourtech.slt.SLTLoader;

public class MainActivity extends AppCompatActivity {

    private SLTLoader sltLoader;
    private FrameLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize root layout for overlay loaders
        rootLayout = findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(this, rootLayout);

        // Show default loader
        sltLoader.showDefaultLoader(R.raw.default_loader);

        // Hide loader after 3 seconds
        new Handler().postDelayed(() -> sltLoader.hideLoader(), 3000);
    }
}
```

### 8. Hiding the Loader
---

#### Hiding Loader
To hide the loader:

```java
sltLoader.hideLoader();
```

#### Hiding with a Timer

```java
// Hide the loader after 3 seconds
new Handler().postDelayed(() -> sltLoader.hideLoader(), 3000);
```

**What Happens**: The loader hides after a specified time delay.

**When to Use**: For tasks with a known duration.

### 9. Hiding with a Button
---

```java
Button hideButton = findViewById(R.id.hide_button);
hideButton.setOnClickListener(v -> sltLoader.hideLoader());
```

**What Happens**: The loader hides when the button is clicked.

**When to Use**: When user interaction is required to hide the loader.

### 10. Hiding after a Background Task
---

```java
Executors.newSingleThreadExecutor().execute(() -> {
    try {
        Thread.sleep(2000);  // Simulate background work
    } catch (InterruptedException e) {
        // Ignore error
    }
    runOnUiThread(() -> sltLoader.hideLoader());
});
```

**What Happens**: The loader hides when the background task is complete.

**When to Use**: For tasks that run in the background.


### 11. Example: Basic Loader with a Timer
---

Show a simple loader and hide it after a few seconds.

```java
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);
        sltLoader.showDefaultLoader(R.raw.default_loader);
        new Handler().postDelayed(() -> sltLoader.hideLoader(), 3000);
    }
}
```

**What Happens**: A loader appears for 3 seconds and then disappears.

**When to Use**: For quick tasks.

### 12. Example: Loader with Custom Color
---

Show a loader with a different color and hide it after a delay.

```java
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.custom_loader)
            .setWidthDp(50)
            .setHeightDp(50)
            .setChangeJsonColor(true)
            .setJsonColor(Color.RED)
            .setOverlayColor(Color.parseColor("#80000000"));

        sltLoader.showCustomLoader(config);
        new Handler().postDelayed(() -> sltLoader.hideLoader(), 4000);
    }
}
```

**What Happens**: A 50x50 red loader with a dark gray background shows for 4 seconds.

**When to Use**: To match your app’s colors or make the loader stand out.


### 13. Example: Simple Loader Without Background
---

Show a loader with no background or extra box, then hide it.

```java
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.custom_spinner)
            .setWidthDp(80)
            .setHeightDp(80)
            .setUseRoundedBox(false)
            .setOverlayColor(Color.TRANSPARENT);

        sltLoader.showCustomLoader(config);
        new Handler().postDelayed(() -> sltLoader.hideLoader(), 3000);
    }
}
```

**What Happens**: A big 80x80 loader spins for 3 seconds with no background or box.

**When to Use**: For a clean look without covering the screen.

### 14. Example: Loader with Buttons
---

Show and hide the loader by tapping buttons.

```java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        Button showButton = view.findViewById(R.id.show_button);
        Button hideButton = view.findViewById(R.id.hide_button);

        showButton.setOnClickListener(v -> {
            SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.loading_global)
                .setWidthDp(40)
                .setHeightDp(40);
            sltLoader.showCustomLoader(config);
        });

        hideButton.setOnClickListener(v -> sltLoader.hideLoader());
        
        return view;
    }
}
```

Layout (`fragment_sample.xml`):

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center">

    <Button
        android:id="@+id/show_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Show Loader" />

    <Button
        android:id="@+id/hide_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hide Loader" />
</LinearLayout>
```

**What Happens**: Tap "Show Loader" to see it, and "Hide Loader" to make it go away.

**When to Use**: When users control when the loader appears, like starting a task.


### 15. Example: Loader for a Background Task
---

Show the loader while something runs in the background.

```java
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;
import java.util.concurrent.Executors;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.loading_dots)
            .setWidthDp(60)
            .setHeightDp(60);

        sltLoader.showCustomLoader(config);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Thread.sleep(2000);  // Wait 2 seconds
            } catch (InterruptedException e) {
                // Ignore error
            }
            getActivity().runOnUiThread(() -> sltLoader.hideLoader());  // Hide when done
        });
    }
}
```

**What Happens**: A 60x60 loader shows for 2 seconds while a fake task runs.

**When to Use**: For things like downloading data from the internet.


### 16. Example: Loader with Speed Change
---

Show a loader that speeds up after a bit.

```java
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.loading_global)
            .setAnimationSpeed(1.0f);  // Start at normal speed

        sltLoader.showCustomLoader(config);
        new Handler().postDelayed(() -> sltLoader.loaderAnimation.setSpeed(2.0f), 2000);  // Make it spin faster
        new Handler().postDelayed(() -> sltLoader.hideLoader(), 4000);
    }
}
```

**What Happens**: The loader spins normally for 2 seconds, then speeds up, and hides after 4 seconds.

**When to Use**: To show progress changing, like a task getting faster.


### 17. Example: Loader with Messages
---

Show the loader and print messages when it starts and stops.

```java
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {
    private static final String TAG = "SampleFragment";

    private SLTLoader sltLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        sltLoader.setLoaderCallback(new SLTLoader.LoaderCallback() {
            @Override
            public void onLoaderShown() {
                Log.d(TAG, "Loader is showing now!");
            }

            @Override
            public void onLoaderHidden() {
                Log.d(TAG, "Loader is gone!");
            }
        });

        sltLoader.showDefaultLoader(R.raw.loading_global);
        new Handler().postDelayed(() -> sltLoader.hideLoader(), 3000);
    }
}
```

**What Happens**: The loader spins for 3 seconds, and messages show up in the log when it starts and stops.

**When to Use**: To check if the loader is working or to do something when it changes.


### 18. Example: Loader in a Specific Area
---

Put the loader in a certain part of the screen.

```java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        FrameLayout rootLayout = view.findViewById(R.id.custom_area);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        SLTLoader.LoaderConfig config = new SLTLoader.LoaderConfig(R.raw.loading_global)
            .setWidthDp(40)
            .setHeightDp(40);

        sltLoader.showCustomLoader(config);
        new Handler().postDelayed(() -> sltLoader.hideLoader(), 3000);
        return view;
    }
}
```

Layout (`fragment_sample.xml`):

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <FrameLayout
        android:id="@+id/custom_area"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:background="#EEEEEE" />
</FrameLayout>
```

**What Happens**: A small loader spins in a gray box at the top of the screen for 3 seconds.

**When to Use**: To show loading in just one part of the screen, like a form.


### 19. Example: Another Way to Show Loader
---

Use the old method with lots of settings.

```java
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.softourtech.slt.SLTLoader;

public class SampleFragment extends Fragment {

    private SLTLoader sltLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout rootLayout = view.findViewById(R.id.root_layout);
        sltLoader = new SLTLoader(getContext(), rootLayout);

        sltLoader.showCustomLoader(
            R.raw.loading_dots,
            sltLoader.dpToPx(50),
            sltLoader.dpToPx(50),
            true,
            Color.parseColor("#AA00FF00"),
            true,
            Color.YELLOW,
            1.5f
        );

        new Handler().postDelayed(() -> sltLoader.hideLoader(), 3000);
    }
}
```

**What Happens**: A yellow loader with a green background spins fast for 3 seconds.




---
### Recommendations
1. **Keep Loaders Minimal**: Only use loaders when absolutely necessary to indicate ongoing processes.
2. **Match Loader Design with App Theme**: Ensure the loader’s design aligns with your app’s color scheme and overall design aesthetic.
3. **Use Appropriate Loader Sizes**: Adjust the size of the loader based on the context (e.g., larger loaders for full-screen operations, smaller ones for inline operations).



## 🤝 Contributing
Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

```bash
# Example commands for contributing
git clone https://github.com/ashrafulwpdev/SLTLoader.git
cd SLTLoader
git checkout -b feature/AmazingFeature
# Make your changes
git add .
git commit -m 'Add some AmazingFeature'
git push origin feature/AmazingFeature
# Open a Pull Request via GitHub
```

---

## 🐞 Issues
If you encounter any issues, please feel free to [open an issue](https://github.com/ashrafulwpdev/SLTLoader/issues).

---

## 👨‍💻 Developed By
**Company:** Softourtech  
**Developer:** [Ashraful Islam](https://github.com/ashrafulwpdev)  
**Website:** [Softourtech](https://softourtech.com)

Building reusable and efficient solutions for Android developers!

---

## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
