package com.tubitv.tubitv

/**
 * Created by vburian on 2/19/18.
 */
import android.Manifest
import android.content.Intent
import android.os.Build
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

    val deviceName: String = getDeviceNameBasedOnId(getDeviceId())

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
        executeShellCommand("pm grant " + app + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + app + " android.permission.READ_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + testPackage + " android.permission.WRITE_EXTERNAL_STORAGE")
        executeShellCommand("pm grant " + testPackage + " android.permission.READ_EXTERNAL_STORAGE")

    }


    fun clearAppData() = executeShellCommand("pm clear " + appPackage)
    fun getInstrum(): UiDevice {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        return uiDevice
    }

    fun launchApp(appPackage: String, luanchAppFromSameScreen: Boolean) {
        givePermission(appPackage)
        GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
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
            var i = 0
            uiDevice.pressRecentApps()
            sleep(2000)
            uiDevice.pressRecentApps()
            sleep(2000)
            while (!uiDevice.currentPackageName.equals(appPackage)) {
                if (i > 20) {
                    throw TestException("Something wrong with minimize app method")
                }
                uiDevice.pressRecentApps()
                i++
            }
            if (!uiDevice.currentPackageName.equals(appPackage)) {
                uiDevice.pressRecentApps()
            }
        } catch (e: UiObjectNotFoundException) {
            throw   TestException("cannot get app from overview")
        }
    }

    protected fun getNetworkDeviceId(): ByteArray {
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

    protected fun getDeviceId(): String {
        val info = Build.FINGERPRINT
        val id = info.split("/").get(4)
        return id
    }

    protected fun getDeviceNameBasedOnId(id: String): String {

        var device = ""
        when {
            id.contentEquals("5164942:user") -> device = "Pixel2"
            id.contentEquals("G975U1UEU1ASAU:user") -> device = "GalaxyS10"
            id.contentEquals("G950USQS2BRB1:user") -> device = "GalaxyS8"
//            id.contentEquals(byteArrayOf(-2, -6, 100, -2, 67, 75)) -> device = "GalaxyS8"
            id.contentEquals("N910VVRU2BPA1:user") -> device = "Note4"
//            id.contentEquals(byteArrayOf(-38, 103, -50, -68, -5, -31)) -> device = "AsusTablet"
            id.contentEquals("T700XXS1CRI2:user") -> device = "SumsungTablet"
            id.contentEquals("T530NUUES1BPL1:user") -> device = "SumsungTablet"
            id.contentEquals("180041045dcf7:user") -> device = "G6"
//            id.contentEquals(byteArrayOf(34, -61, 57, -66, -100, -124)) -> device = "SunsungGalaxyTablet"
//            id.contentEquals(byteArrayOf(2, 0, 0, 0, 0, 0)) -> device = "Vlad's emulator"
//            id.contentEquals(byteArrayOf(-54, -75, -4, 38, 50, 40)) -> device = "AsusTablet"
            id.contentEquals("2074855:user") -> device = "AsusTablet"
//            id.contentEquals(byteArrayOf(-74, -9, -95, -20, -72, -68)) -> device = "LG K20"
            id.contentEquals("T380DXU2BRK2:user") -> device = "TabA"
            id.contentEquals("4657601:user") -> device = "LGNexus"
            id.isEmpty() -> throw TestException("Something wrong with getNetworkDeviceId() method, can't get id")

            else -> throw TestException("Your device/emulator is not register in this framework yet, please add this id:${id} in this statment, Also you need to add your deviceid everywhere where we use it e.g -> NativeCamera()")
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



