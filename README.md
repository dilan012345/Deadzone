<h1>Vectoid: Infinite canvas app </h1>
<h3>An android Procreate alternative </h3>
Palm rejection currently unavailable publicly

> [!IMPORTANT]
> Vectoid will require `Shizuku` to be installed
> [DOWNLOAD SHIZUKU](
https://www.google.com/url?q=https://play.google.com/store/apps/details/Shizuku%3Fid%3Dmoe.shizuku.privileged.api%26hl%3Den_GB&sa=U&ved=2ahUKEwicocH4kaOWAxWsQkEAHQ4yLsMQFnoECCQQAQ&usg=AOvVaw2j0NOjPhFKOARkRpJd5bJr)


This project got severely more complicated than I initially anticipated, currently a form of palm rejection is working, 

Left to do:
UI 
drawbox palm rejection implementation 
path (currently uses dots, yet a normal canvas path cannot reliably end the stroke when the palm is on)




VectorLabs is an infinite whiteboard app with a deadzone to rest your palm

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
<img width="1920" height="1200" alt="Image" src="https://github.com/user-attachments/assets/e44e297e-c3fe-4f10-8343-8745d2094b01" />

Made with Jetpack Compose
