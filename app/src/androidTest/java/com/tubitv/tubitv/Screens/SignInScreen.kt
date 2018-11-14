package com.tubitv.tubitv.Screens

import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/23/18.
 */
public class SignInScreen : BaseScreen() {

    private val emailField = appPackage + ":id/email"
    private val passwordField = appPackage + ":id/password"
    private val signInButton = appPackage + ":id/sign_in_button"
    private val forgotPassword = appPackage + ":id/forgot_password"
    private val pleaseTryAgainOkButton = "android:id/button1"
    private val pleaseTryAgainText = "android:id/message"


    init {
        Assert.assertTrue("Expected Email field is not displayed in Sign in Page", findElementById(emailField,false).waitForExists(globalTimeout))
        Assert.assertTrue("Expected Password field is not displayed in Sign In Page", findElementById(passwordField,false).waitForExists(globalTimeout))
        Assert.assertTrue("Expected Sign In button is not displayed in Sign In Page", findElementById(signInButton,false).waitForExists(globalTimeout))
        Assert.assertTrue("Expected forgot password  is not displayed in Sign In Page ", findElementById(forgotPassword,false).waitForExists(globalTimeout))
    }

    fun sendTextToEmailField(text: String) {
        findObjectById(emailField,false).setText(text)
    }

    fun sendTextToPasswordField(text: String) {
        findObjectById(passwordField,false).setText(text)
    }

    fun clickOnSignInButton(checkForObjects: Boolean): HomeScreen {
        findObjectById(signInButton,false).click()
        return HomeScreen(checkForObjects)
    }

    fun simpleClickOnSignInButton() {
        findObjectById(signInButton,false).click()
    }

    fun emptyFieldsClickOnSignInButton(): SignInScreen {
        findObjectById(signInButton,false).click()
        return SignInScreen()
    }

    fun textFromTryAgain(): String {
        findObjectById(pleaseTryAgainText,true)
        for (j in 1..10) {
            if (findElementById(pleaseTryAgainText,false).text.contains("Cannot") || findElementById(pleaseTryAgainText,false).text.contains("Try")) {
                break
            }
            Thread.sleep(1000)
        }
        return findElementById(pleaseTryAgainText,false).text

    }

    fun clickOnOkPleaseTryAgain(): SignInScreen {
        findObjectById(pleaseTryAgainOkButton,true).click()
        return SignInScreen()
    }

    inner class SignInAfterAddToQueue{

        //Todo signIn but not first lunch





    }


}