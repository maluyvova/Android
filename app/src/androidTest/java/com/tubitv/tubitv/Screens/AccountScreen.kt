package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.TestExceptionWithError
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue

/**
 * Created by vburian on 3/23/18.
 */
class AccountScreen : BaseScreen() {
    protected val signOutButton = appPackage + ":id/sign_in_or_out_button"
    protected val captionsSwitcher = appPackage + ":id/captions_switch"
    private val captionText = appPackage + ":id/caption_text_view"
    protected val landscapeSwithcer = appPackage + ":id/lock_landscape_switch"
    private val landscapeText = appPackage + ":id/lock_landscape_text_view"
    protected val areYouSureOk = "android:id/button1"
    private val signedAsObj = appPackage + ":id/signed_in_as_text_view"
    private val aboutButton = appPackage + ":id/about_setting_layout"
    private val aboutText = appPackage + ":id/about_text_view"
    private val helpCenterText = appPackage + ":id/help_text_view"
    private val helpCenter = appPackage + ":id/help_setting_layout"


    init {
        assertTrue("Expected Sign out Button is not displayed in settings", findElementById(signedAsObj, true).exists())
        assertEquals("Text on caption switcher doesn't correspond requirements", findObjectById(captionText, true).text, "Captions")
        assertEquals("Text on 'Lock in Landscape Mode' switcher doesn't correspond requirements", findObjectById(landscapeText, true).text, "Lock in Landscape Mode")
        assertEquals("Text on 'About' switcher doesn't correspond requirements", findObjectById(aboutText, true).text, "About")
        assertEquals("Text on 'Help Ceter' switcher doesn't correspond requirements", findObjectById(helpCenterText, true).text, "Help Center")
        assertEquals("Text on 'Sign in/Register' button doesn't correspond requirements", findObjectById(signOutButton, true).text, "Sign in/Register")
    }

    public fun clickOnSignOut(): LaunchScreen {

        try {
            findObjectById(signOutButton,false).click()}
        catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find signIn/Register button on Account Screen", e)
        }
        return LaunchScreen()
    }

    public fun clickOnLockInLandscapeMode() {
        try {
            findObjectById(landscapeSwithcer, false).click()
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Lock in 'Landscape Mode' switcher", e)
        }

    }

    public fun clickOnCaption() {
        try {
            findObjectById(captionsSwitcher,false).click()
        }
        catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find 'Caption' switcher on Account Screen", e)
        }
    }

    public fun clickOnSearch(): SearchScreen {
        try {
            findElementParentIdChildIndex(tabs, true, 2, 0).click()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find search button", e)
        }
        return SearchScreen()
    }

}