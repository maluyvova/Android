package com.tubitv.tubitv

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import org.junit.Before

/**
 * Created by vburian on 4/3/18.
 */
open class SimpleLaunchApp:BaseTest() {

    fun castings(){
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val custButton=uiDevice.findObject(UiSelector().description("Cast button. Disconnected"))
        val castButton=uiDevice.findObject(UiSelector().description("Cast button. Connected"))
        if(uiDevice.findObject(UiSelector().resourceId(appPackage+":id/cast_featurehighlight_help_text_header_view")).waitForExists(globalTimeout)) {
            if (custButton.waitForExists(globalTimeout)) {
                custButton.click()
                uiDevice.pressBack()
            } else if (castButton.waitForExists(globalTimeout)) {
                castButton.click()
                uiDevice.pressBack()
            }
        }
        else if (custButton.waitForExists(globalTimeout)){
            custButton.click()
            uiDevice.pressBack()
        }

    }


    @Before
    fun luanchApp(){
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        killApp()
        clearAppData()
        launchApp(appPackage)
    }
}