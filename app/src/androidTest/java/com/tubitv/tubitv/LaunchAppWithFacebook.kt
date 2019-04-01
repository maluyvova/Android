package com.tubitv.tubitv

import com.tubitv.tubitv.Helpers.InsuranceBeforeTests
import com.tubitv.tubitv.Helpers.ScreenRecording
import org.junit.Before
import com.tubitv.tubitv.Screens.UpdateScreen
import java.net.NetworkInterface
import java.util.*
import com.tubitv.tubitv.Screens.HomeScreen


/**
 * Created by vburian on 4/3/18.
 */
open class LaunchAppWithFacebook : BaseTest() {
    var textFromFacebookButton = ""
    private val updateAppButton = appPackage + ":id/update_button"
    @Before
    fun setUps() {
        getInstrum()
        setDeviceId()
        putInPortraitMode()
        killApp()
        clearAppData()
        launchApp(appPackage, false)
        //textFromFacebookButton = signIn()
        UpdateScreen().clickOnMaybeLatter()
        //casting()
        HomeScreen(false).dismissCasting()
        uiDevice.setOrientationNatural()
        InsuranceBeforeTests().deleteHistory()
        InsuranceBeforeTests().deleteFromQueue()
    }

}