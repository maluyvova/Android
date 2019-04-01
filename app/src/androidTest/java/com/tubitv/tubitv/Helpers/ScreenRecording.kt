package com.tubitv.tubitv.Helpers

import android.os.Environment
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.BaseTest
import com.tubitv.tubitv.Screens.BaseScreen
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.deviceName
import com.tubitv.tubitv.screenCamPackage
import java.io.File
import java.text.FieldPosition

/**
 * Created by vburian on 12/27/18.
 */
class ScreenRecording : BaseScreen() {
    private val container = "com.orpheusdroid.screenrecorder:id/viewpager"
    private val recordButton = "com.orpheusdroid.screenrecorder:id/fab"
    private val okButton = "android:id/button1"
    private val reminderButton = "com.android.systemui:id/remember"


    fun statrtRecording(nameOfMovie: String) {
        val baseTest = BaseTest()
        baseTest.getInstrum()
        baseTest.launchApp(screenCamPackage, false)
        if (findElementById(okButton, true).exists()) {
            findObjectById(okButton, true).click()
        }
        findElementParentIdChildIndex(container, true, 0, 0, 1, 0).click()
        clickOnRadioButton(0)
        findElementParentIdChildIndex(container, true, 0, 0, 2, 0).click()
        clickOnRadioButton(0)
        findElementParentIdChildIndex(container, true, 0, 0, 3, 0).click()
        clickOnRadioButton(2)
        findElementParentIdChildIndex(container, true, 0, 0, 8, 0).click()
        if (!findElementByText("Pictures", true).exists()) {
            scrollableScreenById("com.orpheusdroid.screenrecorder:id/rv").scrollForward()
        }
        findElementByText("Pictures", true).click()
        while (!findElementByText(nameOfMovie, true).exists()) {
            scrollableScreenById("com.orpheusdroid.screenrecorder:id/rv").scrollForward()
        }
        findElementByText(nameOfMovie, true).click()
        findElementByText("app_screenRecords", true).click()
        findObjectById(okButton, true).click()
        findObjectById(recordButton, true).click()
        if (waitForObjectShortTime(reminderButton)) {
            findObjectById(reminderButton, false).click()
            findObjectById(okButton, false).click()
        }
        baseTest.launchApp(appPackage, true)

    }

    fun stopRecording() {
        val baseTest = BaseTest()
        baseTest.getInstrum()
        uiDevice.pressBack()
        uiDevice.openQuickSettings()
        if (findElementByText("Stop", true).exists()) {
            findElementByText("Stop", true).click()
        } else {
            when {
                deviceName.equals("Pixel2") -> findElementById("com.android.systemui:id/qs_carrier_text", false).dragTo(findObjectById("com.android.systemui:id/status_bar_contents", false), 10)
                deviceName.equals("G6") -> {
                    findElementById("com.android.systemui:id/page_decor", false).dragTo(findObjectById("com.android.systemui:id/statusbar_gradient_view", false), 10)
                    findElementByIdParentChildByPackageName("android:id/status_bar_latest_event_content", "com.orpheusdroid.screenrecorder", true, "android:id/expand_button").click()
                }
                deviceName.equals("GalaxyS8")->{
                    findObjectById("com.android.systemui:id/handler_image_view",true).dragTo(findObjectById("com.android.systemui:id/status_bar_contents",false),10)
                    findElementByIdParentChildByPackageName("android:id/status_bar_latest_event_content", "com.orpheusdroid.screenrecorder", true, "android:id/expand_button").click()


                }
            }
            try {
                findElementByText("Stop", true).click()
            } catch (e: UiObjectNotFoundException) {
                findElementByText("STOP", true).click()
            }
        }
    }


    fun clickOnRadioButton(position: Int) {
        findObjectByIdAndIndex("android:id/text1", position, true).click()
    }
}