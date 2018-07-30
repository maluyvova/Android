package com.tubitv.tubitv

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import com.tubitv.tubitv.Helpers.InsuranceBeforeTests
import org.junit.Before

/**
 * Created by vburian on 4/3/18.
 */
open class LaunchAppWithFacebook:BaseTest() {
      var textFromFacebookButton=""
     @Before
    fun setUps() {
       getInstrum()
        killApp()
        clearAppData()
        launchApp(appPackage,false)
       textFromFacebookButton= SignIn()
         casting()
         uiDevice.setOrientationNatural()
         InsuranceBeforeTests().deleteHistory()
         InsuranceBeforeTests().deleteFromQueue()
 }
}