# ConcentricProgressBar                      [![](https://jitpack.io/v/rajBopche/ConcentricProgressBar.svg)](https://jitpack.io/#rajBopche/ConcentricProgressBar)      [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)



A **Circular Progress Bar** with concentric circles.


![](blueprogress.gif)
![](redprogress.gif)


## Installation


### Gradle

1. Include **Maven** in **Project Level build.gradle** file

```gradle
repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
```

2. Add the library dependency in **App Level build.gradle** file

`implementation "com.github.rajBopche:ConcentricProgressBar:$latest_version"`



## Usage

```xml

<com.example.concentricprogressbar.ConcentricProgressBar
        android:id="@+id/progress_bar_concentric"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:inner_progress_color="@color/color_dark_orange"
        app:outer_progress_color="@color/color_bright_red"
        />

```


## Requirement

* The **min sdk** for library is currently set to **19**.




## Limitation

* The number of progress circles is restricited to two circles for now, but can be progressively modified in future releases.


