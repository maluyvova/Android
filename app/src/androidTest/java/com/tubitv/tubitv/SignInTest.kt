package com.tubitv.tubitv

import com.tubitv.tubitv.Helpers.RandomEmail
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.LaunchScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 3/28/18.
 */
class SignInTest() : SimpleLaunchApp() {

    @Test
    fun signInWithCorrectEmailAndPassword() {
        val signInScreen = LaunchScreen().clickOnSignIn()
        signInScreen.sendTextToEmailField("tubitv@tubitv.tubitv")
        signInScreen.sendTextToPasswordField("tubitv")
        signInScreen.clickOnSignInButton()
    }

    @Test
    fun signInWithIncorrectEmail() {
        val signInScreen = LaunchScreen().clickOnSignIn()
        signInScreen.sendTextToEmailField("vladb" + RandomEmail.randomemail() + RandomEmail.randomemail() + "@gmail.com")
        signInScreen.sendTextToPasswordField("tubitv")
        signInScreen.simpleClickOnSignInButton()
        val textFromAgainPopUp = signInScreen.textFromTryAgain()
        signInScreen.clickOnOkPleaseTryAgain()
        Assert.assertEquals("This test provided not exists email in Sign In page,and text is not equal to", textFromAgainPopUp.toLowerCase(), "Please try again.".toLowerCase())
    }

    @Test
    fun signInWithIncorrectPassword() {
        val signInScreen = LaunchScreen().clickOnSignIn()
        signInScreen.sendTextToEmailField("tubitv@tubitv.tubitv")
        signInScreen.sendTextToPasswordField("a" + RandomEmail.randomemail())
        signInScreen.simpleClickOnSignInButton()
        val textFromAgainPopUp = signInScreen.textFromTryAgain()
        signInScreen.clickOnOkPleaseTryAgain()
        Assert.assertEquals("This test provided not correct password in Sign In page,and text is not equal to", textFromAgainPopUp.toLowerCase(), "Cannot login the user by email, Incorrect password".toLowerCase())
    }

    @Test
    fun emptyFieldInSignInPage() {
        val signInScreen = LaunchScreen().clickOnSignIn()
        signInScreen.emptyFieldsClickOnSignInButton()

    }
}