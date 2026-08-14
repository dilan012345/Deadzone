


> [!CAUTION]
> pausing development on this app as
> android kills the current gesture (and 
> therefore ending the pen stroke) when 
> your palm is placed on the screen, I 
> cannot think of a solution to maintain 
> pointer tracking after ACTION_CANCEL 
> occurs

adb shell settings put system surface_palm_touch 0 
adb shell settings put system surface_palm_swipe 0 






## Jot

Jot is inspired heavily by Google's Material 3 expressive UI concepts

It utilises a fork of DrawBox with support for custom `BackgroundPattern`, replacing the default background with a custom one through a simple `Painter` resource.

Original: https://github.com/akshay2211/DrawBox
Fork: https://github.com/dilan012345/DrawBox

### Screenshots


Made with Jetpack Compose
