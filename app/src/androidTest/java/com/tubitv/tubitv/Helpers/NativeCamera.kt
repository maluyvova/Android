package com.tubitv.tubitv.Helpers

import com.tubitv.tubitv.Screens.BaseScreen
import com.tubitv.tubitv.Screens.SendMessageView
import java.lang.Thread.sleep
import kotlin.concurrent.thread

/**
 * Created by vburian on 12/12/18.
 */
class NativeCamera : BaseScreen() {

    private val pixel2PhotoButton = "com.google.android.GoogleCamera:id/shutter_button"
    private val galaxyS8Button = "com.sec.android.app.camera:id/okay"
    private val okButton = "OK"


    fun makeAPhoto(deviceName: String): SendMessageView {
        uiDevice.executeShellCommand("input keyevent KEYCODE_CAMERA")
        uiDevice.executeShellCommand("input keyevent KEYCODE_CAMERA")
        sleep(2500)
        when (deviceName) {
            "Pixel2" -> findElementById(pixel2PhotoButton, true).click()
            "GalaxyS8" -> findElementById(galaxyS8Button, true).click()
            "LG K20" -> findElementByText(okButton, true).click()
            "TabA" -> findElementById(galaxyS8Button, true).click()
            "G6" -> findElementByText(okButton, true).click()
            "LGNexus" -> findElementById(pixel2PhotoButton, true).click()
            "SumsungTablet" -> findElementById(galaxyS8Button, true).click()
            else -> throw TestException("Device id is not added to this framework , please find method getDeviceNameBasedOnId() and add your device id (read exception for that method)")
        }
        if (findElementById(pixel2PhotoButton, false).exists()) {
            findElementById(pixel2PhotoButton, false).click()
        }
        return SendMessageView()
    }
}