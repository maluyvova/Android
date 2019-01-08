package com.tubitv.tubitv

/**
 * Created by vburian on 2/19/18.
 */
import android.Manifest
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.GrantPermissionRule
import android.support.test.uiautomator.*
import android.util.Log
import com.tubitv.tubitv.Helpers.TestException
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import java.lang.Thread.sleep
import java.net.NetworkInterface
import java.util.*



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
open class BaseTest {

    val deviceName:String =getDeviceNameBasedOnId(getDeviceId())

    protected lateinit var uiDevice: UiDevice

    fun killApp() = executeShellCommand("am force-stop " + appPackage)
    fun executeShellCommand(command: String): String {
        val stdOut = uiDevice.executeShellCommand(command)
        Log.i("Test", command + ": " + stdOut)
        return stdOut
    }

    fun putInPortraitMode() {
        executeShellCommand("content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0")
        executeShellCommand("content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0")
        uiDevice.setOrientationNatural()

    }

    fun givePermission(app: String) {
        executeShellCommand("pm revoke " + app + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm revoke " + app + " android.permission.READ_EXTERNAL_STORAGE")
        executeShellCommand("pm revoke " + testPackage + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm revoke " + testPackage + " android.permission.READ_EXTERNAL_STORAGE")
        executeShellCommand("pm revoke " + testsPackage + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm revoke " + testsPackage + " android.permission.READ_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + app + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + app + " android.permission.READ_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + testPackage + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + testPackage + " android.permission.READ_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + testsPackage + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + testsPackage + " android.permission.READ_EXTERNAL_STORAGE")
    }


    fun clearAppData() = executeShellCommand("pm clear " + appPackage)
    fun getInstrum(): UiDevice {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        return uiDevice
    }

    protected fun launchApp(appPackage: String, luanchAppFromSameScreen: Boolean) {
        givePermission(appPackage)
        GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
        uiDevice.pressHome()
        val launcherPackage = uiDevice.launcherPackageName
        assertThat(launcherPackage, CoreMatchers.notNullValue())
        uiDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                globalTimeout)
        Log.i("Test", "launching the app")
        val context = InstrumentationRegistry.getContext()
        val intent = context.packageManager
                .getLaunchIntentForPackage(appPackage)
        assertThat("Application is not installed or disabled (Package not found or cannot be launched)", intent, CoreMatchers.notNullValue())
        // Clear out any previous instances
        if (luanchAppFromSameScreen == false) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        } else {
            context.startActivity(intent)
        }

        uiDevice.wait(Until.hasObject(By.pkg(appPackage).depth(0)),
                globalTimeout)
    }

    protected fun checkConfigForDevce(): UiObject {
        uiDevice.pressHome()
        uiDevice.pressRecentApps()
        val app = uiDevice.findObject(UiSelector().resourceId("com.android.systemui:id/title"))
        val appForAnotherDevices = uiDevice.findObject(UiSelector().resourceId("com.android.systemui:id/activity_description"))
        if (app.waitForExists(globalTimeout)) {
            return app
        } else if (appForAnotherDevices.waitForExists(globalTimeout))
            return appForAnotherDevices
        else throw TestException("Some another config for device (Check which device and id for overview")
    }


    protected fun minimizeAndOpenAppFromSameScreen() {
        try {
            uiDevice.pressRecentApps()
            sleep(2000)
            uiDevice.pressRecentApps()
            while (!uiDevice.currentPackageName.equals(appPackage)) {
                uiDevice.pressRecentApps()
            }
        } catch (e: UiObjectNotFoundException) {
            TestException("cannot get app from overview")
        }
    }

   protected fun getDeviceId(): ByteArray {
        var id: ByteArray = byteArrayOf()
        var interfaceList = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (interfaces: NetworkInterface in interfaceList) {
            val byteArray = interfaces.hardwareAddress
            if (byteArray != null) {
                if (byteArray.size > 4) {
                    id = byteArray
                    break
                }
            }
        }
        return id
    }

    protected fun getDeviceNameBasedOnId(id: ByteArray): String {
        var device = ""
        when {
            id.contentEquals(byteArrayOf(126, 0, -1, -125, 35, 82)) -> device = "Pixel2"
            id.contentEquals(byteArrayOf(-10, -124, 8, -36, 98, 105)) -> device = "GalaxyS8"
            id.contentEquals(byteArrayOf(-102, -59, -117, -104, -54, -50)) -> device = "GalaxyS8"
            id.contentEquals(byteArrayOf(-50, 100, -52, 64, -78, -47)) -> device = "Note4"
            id.contentEquals(byteArrayOf(-38, 103, -50, -68, -5, -31)) -> device = "AsusTablet"
            id.contentEquals(byteArrayOf(-7, 14, 99, 9, -121)) -> device = "SumsungTablet"
            id.contentEquals(byteArrayOf(124, -7, 14, 99, 9, -121)) -> device = "SumsungTablet"
            id.contentEquals(byteArrayOf(-106, 8, 119, -125, 64, -43)) -> device = "G6"
            id.contentEquals(byteArrayOf(-110, -66, -2, 76, -109, -90)) -> device = "SunsungGalaxyTablet"
            id.contentEquals(byteArrayOf(2, 0, 0, 0, 0, 0)) -> device = "Vlad's emulator"
            id.contentEquals(byteArrayOf(-62, -121, -87, -49, -128, 71)) -> device = "AsusTablet"
            id.contentEquals(byteArrayOf(18, 124, -18, -108, -72, -50)) -> device = "AsusTablet"
            id.isEmpty() -> throw TestException("Something wrong with getDeviceId() method, can't get id")

            else -> throw TestException("Your device/emulator is not register in this framework yet, please add this id:${id.joinToString()} in this statment, Also you need to add your deviceid everywhere where we use it e.g -> NativeCamera()")
        }
        return device
    }


    protected fun signIn(): String {

        var textFromButton = ""
        val continueFacebook = uiDevice.findObject(UiSelector().resourceId("u_0_9"))
        val continueFacebookClass = uiDevice.findObject(UiSelector().className("android.widget.Button"))
        val titleBox = UiCollection(UiSelector().resourceId(appPackage + ":id/empty_holder"))
        val castPopUp = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/cast_featurehighlight_help_text_header_view"))
        try {
            uiDevice.findObject(UiSelector().packageName(appPackage).text("Sign In")).click()
        } catch (e: UiObjectNotFoundException) {
            return ""
        }
        uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/custom_fb_login_button")).click()
        if (continueFacebook.waitForExists(globalTimeout)) {
            textFromButton = continueFacebook.text
            continueFacebook.click()
        }
        if (continueFacebookClass.waitForExists(globalTimeout)) {
            textFromButton = continueFacebook.text
            continueFacebookClass.click()
        }
        return textFromButton

        assertThat(uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/nav_app_bar_main_logo"))
                .waitForExists(facebookLogin), CoreMatchers.`is`(true))

    }


    protected fun casting() {
        val castMessage = appPackage + ":id/cast_featurehighlight_help_text_header_view"

        if (uiDevice.findObject(UiSelector().resourceId(castMessage)).waitForExists(globalTimeout)) {
            uiDevice.click(712, 2018)
        }

    }


}



