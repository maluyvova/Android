package com.tubitv.tubitv

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import com.tubitv.tubitv.Helpers.InsuranceBeforeTests
import org.junit.Before

/**
 * Created by vburian on 4/3/18.
 */
open class LaunchAppWithFacebook:BaseTest() {

     @Before
    fun setUps() {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        killApp()
        clearAppData()
        launchApp(appPackage)
        SignIn()
        casting()
         InsuranceBeforeTests().deleteHistory()
         InsuranceBeforeTests().deleteFromQueue()

    }
}