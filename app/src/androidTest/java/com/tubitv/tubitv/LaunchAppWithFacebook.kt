package com.tubitv.tubitv


import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import com.tubitv.tubitv.Helpers.InsuranceBeforeTests
import org.junit.Before
import java.security.AccessController.getContext
import android.content.Context.TELEPHONY_SERVICE
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.*
import android.net.wifi.WifiManager
import com.tubitv.tubitv.Screens.UpdateScreen
import java.net.NetworkInterface
import java.util.*
import android.app.ActivityManager.RunningTaskInfo
import android.app.ActivityManager
import android.util.Log


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
        casting()
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