package com.tubitv.tubitv

/**
 * Created by vburian on 2/19/18.
 */
import android.content.Intent
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.*
import android.util.Log
import android.support.test.uiautomator.BySelector
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Screens.BaseScreen
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
open class BaseTest {

    protected lateinit var uiDevice: UiDevice

    fun killApp() = executeShellCommand("am force-stop " + appPackage)
    fun executeShellCommand(command: String) {
        val stdOut = uiDevice.executeShellCommand(command)
        Log.i("Test", command + ": " + stdOut)
    }

    fun clearAppData() = executeShellCommand("pm clear " + appPackage)
    fun getInstrum(): UiDevice {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        return uiDevice
    }

    protected fun launchApp(appPackage: String, luanchAppFromSameScreen: Boolean) {
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


    protected fun minimizeAndOpenAppFromSameScreen(app: UiObject) {
        if (app.text.contains("Tubi")) {
            app.click()
        } else throw TestException("app is not in overview menu")
    }


    protected fun SignIn(): String {
        var textFromButton = ""
        val continueFacebook = uiDevice.findObject(UiSelector().resourceId("u_0_9"))
        val titleBox = UiCollection(UiSelector().resourceId(appPackage + ":id/empty_holder"))
        val castPopUp = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/cast_featurehighlight_help_text_header_view"))
        uiDevice.findObject(UiSelector().packageName(appPackage).text("Sign In")).click()
        uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/custom_fb_login_button")).click()
        if (continueFacebook.waitForExists(globalTimeout)) {
            textFromButton = continueFacebook.text
            continueFacebook.click()
            if (continueFacebook.waitForExists(globalTimeout)) {
                textFromButton = continueFacebook.text
                continueFacebook.click()
            }

        }
        return textFromButton

        // uiDevice.findObject(UiSelector().resourceId("u_0_9")).click()
        // uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/email")).setText("tubitv@tubitv.tubitv")
        // uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/password")).setText("tubitv")
        //uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/sign_in_button")).click()

        assertThat(uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/nav_app_bar_main_logo"))
                .waitForExists(facebookLogin), CoreMatchers.`is`(true))

    }


    protected fun casting() {
        val custButton = uiDevice.findObject(UiSelector().description("Cast button. Disconnected"))
        val castButton = uiDevice.findObject(UiSelector().description("Cast button. Connected"))
        if (uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/cast_featurehighlight_help_text_header_view")).waitForExists(globalTimeout)) {
            if (custButton.waitForExists(globalTimeout)) {
                custButton.click()
                uiDevice.pressBack()
            } else if (castButton.waitForExists(globalTimeout)) {
                castButton.click()
                uiDevice.pressBack()
            }
        } else if (custButton.waitForExists(globalTimeout)) {
            custButton.click()
            uiDevice.pressBack()
        }

    }


}



