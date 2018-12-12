package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/23/18.
 */
class SettingsScreen : BaseScreen() {
    protected val signOutButton = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/sign_out"))
    protected val captionsSwitcher = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/captions_switch"))
    protected val portraitSwithcer = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/portrait_switch"))
    protected val areYouSureOk = uiDevice.findObject(UiSelector().resourceId("android:id/button1"))

    init {
        Assert.assertTrue("Expected Sign out Button is not displayed in settings", signOutButton.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected caption Switcher is not displayed in settings", captionsSwitcher.waitForExists(moviesListTimeout))
       // Assert.assertTrue("Expected portrait Switcher is not displayed in settings", captionsSwitcher.waitForExists(moviesListTimeout))

    }

    public fun clickOnSignOut(): LaunchScreen {
        signOutButton.click()
        return LaunchScreen()
    }

    public fun clickOnLockInLandscapeMode() {
        portraitSwithcer.click()
    }

    public fun simpleClickOnLockInLandscapeMode() {
        portraitSwithcer.click()
    }

    public fun clickOnCaption() {
        captionsSwitcher.click()
    }

}