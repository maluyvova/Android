package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/23/18.
 */
public class SignInScreen:BaseScreen(){
    private val emailField=uiDevice.findObject(UiSelector().resourceId(appPackage +":id/email"))
    private val passwordField=uiDevice.findObject(UiSelector().resourceId(appPackage+":id/password"))
    private val signInButton=uiDevice.findObject(UiSelector().resourceId(appPackage+":id/sign_in_button"))
    private val forgotPassword=uiDevice.findObject(UiSelector().resourceId(appPackage+":id/forgot_password"))


    init {
        Assert.assertTrue("Expected Email field is not displayed in Sign in Page",emailField.waitForExists(globalTimeout))
        Assert.assertTrue("Expected Password field is not displayed in Sign In Page",passwordField.waitForExists(globalTimeout))
        Assert.assertTrue("Expected Sign In button is not displayed in Sign In Page",signInButton.waitForExists(globalTimeout))
        Assert.assertTrue("Expected forgot password  is not displayed in Sign In Page ",forgotPassword.waitForExists(globalTimeout))
    }

   fun sendTextToEmailField( text:String){
       emailField.setText(text)
   }

    fun sendTextToPasswordField(text:String){
        passwordField.setText(text)
    }
    fun clickOnSignInButton():HomeScreen{
        signInButton.click()
        return HomeScreen()
    }


}