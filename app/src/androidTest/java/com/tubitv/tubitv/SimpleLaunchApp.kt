package com.tubitv.tubitv

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import org.junit.Before

/**
 * Created by vburian on 4/3/18.
 */
open class SimpleLaunchApp:BaseTest() {
    @Before
    fun luanchApp(){
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        killApp()
        clearAppData()
        launchApp(appPackage)
    }
}