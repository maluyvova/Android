package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiObjectNotFoundException
import com.tubitv.tubitv.Helpers.TestExceptionWithError
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert.assertEquals

/**
 * Created by vburian on 2/20/19.
 */
class AboutScreen : BaseScreen() {


    private val mainText = appPackage + ":id/tip_text_view"
    private val privacyPolicyButton = appPackage + ":id/privacy_policy_text_view"
    private val termsOfUseButton = appPackage + ":id/terms_of_use_text_view"

    init {
        try {
            assertEquals("Can't find Privacy Policy on AboutScreen screen", findObjectById(privacyPolicyButton, true).waitForExists(globalTimeout), true)
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Privacy Policy on AboutScreen screen", e)
        }
        try {
            assertEquals("Can't find 'Terms of Use' button on AboutScreen screen", findObjectById(termsOfUseButton, true).waitForExists(globalTimeout), true)
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find 'Terms of Use' button on AboutScreen screen", e)
        }
        try {
            assertEquals("Main text does not correspond requirements ", findObjectById(mainText, true).text, "Escape the claws of subscription fees! Tubi TV, the free Internet TV network, is working on your behalf to unlock Hollywood so entertainment is free, without the burden of credit cards. Check out the largest collection of premium and unique movies and TV shows. For free, forever, since advertisers pay so you never have to.\n" +
                    "\n" +
                    " Updated weekly; find out more at tubitv.com.\n" +
                    "\n")
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Main text on AboutScreen screen", e)
        }
    }


    fun clickOnPrivacyPolicy(): PrivatePolicyScreen {
        try {
            findObjectById(privacyPolicyButton, false).click()
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Privacy Policy on AboutScreen screen", e)
        }
        return PrivatePolicyScreen()
    }

    fun clickOnTermsOfUse():TermOfUseScreen {
        try {
            findObjectById(termsOfUseButton, false).click()
        } catch (e: UiObjectNotFoundException) {
            throw    TestExceptionWithError("Can't find Tems of Use on AboutScreen screen", e)
        }
        return TermOfUseScreen()
    }
}