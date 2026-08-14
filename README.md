
Following commands required utilise palm rejection:

> [!CAUTION]
> AVOID USE IF YOUR DEVICE HAS AN EMR OR ACTIVE STYLUS DIGITISER HARDWARE (i.e S-PEN)

`adb shell settings put system surface_palm_touch 0`<br>
`adb shell settings put system surface_palm_swipe 0`<br>
`adb shell device_config put input_native_boot palm_rejection_enabled false`

Plan to use shizuku to simpllify process




## Jot


It utilises a fork of DrawBox with support for custom `BackgroundPattern`, replacing the default background with a custom one through a simple `Painter` resource.
(Not in use for current purposes - will either migrate away from drawbox or significantly redevelop drawbox) 

Original: https://github.com/akshay2211/DrawBox
Fork: https://github.com/dilan012345/DrawBox

### Screenshots


Made with Jetpack Compose
