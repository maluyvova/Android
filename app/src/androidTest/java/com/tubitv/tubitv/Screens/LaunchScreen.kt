package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.shortWaitTime
import junit.framework.Assert

/**
 * Created by vburian on 3/23/18.
 */
class LaunchScreen : BaseScreen() {

    private val textAnimation = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_login_adapter_text_view"))
    private val createNewAcountButton = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/activity_main_login_sign_up_tv"))
    private val signInButton = uiDevice.findObject(UiSelector().text("Sign In"))


    init {
        if (!textAnimation.waitForExists(shortWaitTime)) {

            val homePage = HomeScreen()
            homePage.dismissCasting()
            Thread.sleep(4000)
            //fix it ^^^^
            val titleInHomeScreen=homePage.longPress()
            val signInScreen=titleInHomeScreen.clickAddToQueueAfterLongClickForSignIn()
            signInScreen.clickOnSignIn()
            LaunchScreen()
        }
        Assert.assertTrue("Expected text animation is not displayed in Launch Screen", textAnimation.waitForExists(globalTimeout))
        Assert.assertTrue("Expected create new account button is not displayed in Launch Screen", createNewAcountButton.waitForExists(globalTimeout))
        Assert.assertTrue("Expected Sign In buttom is not displayed", signInButton.waitForExists(globalTimeout))
    }

    fun clickOnSignIn(): SignInScreen {
        signInButton.click()
        return SignInScreen()
    }


    fun clickOnCreateAccount(): SignInWithEmailOrFacebookScreen {
        createNewAcountButton.click()
        return SignInWithEmailOrFacebookScreen()
    }


    class SignInWithEmailOrFacebookScreen() : BaseScreen() {
        val continueFacebook = uiDevice.findObject(UiSelector().resourceId("u_0_9"))
        private val signInWithFacebook = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/custom_fb_login_button"))
        private val signInWithEmail = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/sign_up_with_email"))

        init {
            Assert.assertTrue("Expected Sign In With Email button is not displayed ", signInWithEmail.waitForExists(globalTimeout))
            Assert.assertTrue("Expected Sign In With Facebook button is not displayed", signInWithFacebook.waitForExists(globalTimeout))
        }

        public fun clickOnSighUpWithEmail(): EmailSigUpScreen {
            signInWithEmail.click()
            return EmailSigUpScreen()
        }

        fun clickOnSignUpWithFacebook(): HomeScreen {
            signInWithFacebook.waitForExists(globalTimeout)
            signInWithFacebook.click()
            if (continueFacebook.waitForExists(globalTimeout)) {
                continueFacebook.click()
            }
            return HomeScreen()
        }


    }

}
