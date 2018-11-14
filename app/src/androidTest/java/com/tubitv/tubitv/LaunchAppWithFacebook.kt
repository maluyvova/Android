package com.tubitv.tubitv

import com.tubitv.tubitv.Helpers.InsuranceBeforeTests
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
        putInPortraitMode()
        killApp()
        clearAppData()
        launchApp(appPackage, false)
        textFromFacebookButton = SignIn()
        UpdateScreen().clickOnMaybeLatter()
        //casting()
        HomeScreen(false).dismissCasting()
        uiDevice.setOrientationNatural()
        InsuranceBeforeTests().deleteHistory()
        InsuranceBeforeTests().deleteFromQueue()
    }

}


class ID {

    fun getId() {
        val interfacesList = Collections.list(NetworkInterface.getNetworkInterfaces());

        for (interfaces: NetworkInterface in interfacesList) {
            // This will give you the interface MAC ADDRESS
            val sm = interfaces.getHardwareAddress();
        }


        // val deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}