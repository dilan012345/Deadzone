




## Deadzone


It utilises a fork of DrawBox with support for custom `BackgroundPattern`, replacing the default background with a custom one through a simple `Painter` resource.
(Not in use for current purposes - will either migrate away from drawbox or significantly redevelop drawbox) 

Original: https://github.com/akshay2211/DrawBox
Fork: https://github.com/dilan012345/DrawBox





> [!CAUTION]
> Avoid commands if your device has EMR or active stylus digitiser hardware (e.g. S Pen).

Following commands required utilise palm rejection: 
<br>`adb shell settings put system surface_palm_touch 0`<br>
`adb shell settings put system surface_palm_swipe 0`<br>
`adb shell device_config put input_native_boot palm_rejection_enabled false` <br>
`adb shell device_config put input com.android.input.flags.report_palms_to_gestures_library false `


ocasssionally resets so will have to think of alternative or a script

Plan to use shizuku to simplify process

### Screenshots


Made with Jetpack Compose
