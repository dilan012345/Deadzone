<h1>Vectoid: Infinite canvas app </h1>
<h3>An android Procreate alternative </h3>
Palm rejection currently unavailable publicly
<br>
> [!IMPORTANT]
> Vectoid will require `Shizuku` to be installed
> [DOWNLOAD SHIZUKU](
https://www.google.com/url?q=https://play.google.com/store/apps/details/Shizuku%3Fid%3Dmoe.shizuku.privileged.api%26hl%3Den_GB&sa=U&ved=2ahUKEwicocH4kaOWAxWsQkEAHQ4yLsMQFnoECCQQAQ&usg=AOvVaw2j0NOjPhFKOARkRpJd5bJr)
<br>

This project got severely more complicated than I initially anticipated, currently a form of palm rejection is working, 
<br>

It utilises a fork of DrawBox with support for custom `BackgroundPattern`, replacing the default background with a custom one through a simple `Painter` resource.
(Not in use for current purposes - will either migrate away from drawbox or significantly redevelop drawbox) 
<br>
Original: https://github.com/akshay2211/DrawBox
Fork: https://github.com/dilan012345/DrawBox
<br>




> [!CAUTION]
> Avoid commands if your device has EMR or active stylus digitiser hardware (e.g. S Pen).
<br>
Following commands required utilise palm rejection: 
<br>`adb shell settings put system surface_palm_touch 0`<br>
`adb shell settings put system surface_palm_swipe 0`<br>
`adb shell device_config put input_native_boot palm_rejection_enabled false` <br>
`adb shell device_config put input com.android.input.flags.report_palms_to_gestures_library false `


ocasssionally resets so will have to think of alternative or a script
<br>
Plan to use shizuku to simplify process
<br>
<h2> Screenshots </h2>
<img width="1920" height="1200" alt="Screenshot of drawing" src="https://github.com/user-attachments/assets/e44e297e-c3fe-4f10-8343-8745d2094b01" />

<br>
Made with Jetpack Compose
