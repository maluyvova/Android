package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/23/18.
 */
class LaunchScreen:BaseScreen(){

private val textAnimation=uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/view_login_adapter_text_view"))
private val createNewAcountButton=uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/activity_main_login_sign_up_tv"))
    val signInButton=uiDevice.findObject(UiSelector().text("Sign In"))

    init {
        Assert.assertTrue("Expected text animation is not displayed in Launch Screen",textAnimation.waitForExists(globalTimeout))
            Assert.assertTrue("Expected create new account button is not displayed in Launch Screen",createNewAcountButton.waitForExists(globalTimeout))
        Assert.assertTrue("Expected Sign In buttom is not displayed",signInButton.waitForExists(globalTimeout))
    }
    fun clickOnSignIn():SignInScreen{
        signInButton.click()
        return SignInScreen()
    }

}
