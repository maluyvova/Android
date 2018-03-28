package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.EmailSigUpScreen
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.LaunchScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 3/23/18.
 */
class LogInTest:BaseTest(){

    fun SignOut():LaunchScreen{
        val homepage=HomeScreen()
        val smallWindowWithSetings=homepage.clickOnThreeDotsSetings()
        val settingsScreen=smallWindowWithSetings.clickOnSettings()
        val launchScreen=settingsScreen.clickOnSignOut()
        return LaunchScreen()
    }







  @Test
    fun logIn(){
     val launchScreen=SignOut()
      val signInScreen=launchScreen.clickOnSignIn()
      signInScreen.sendTextToEmailField("tubitv@tubitv.tubitv")
      signInScreen.sendTextToPasswordField("tubitv")
      signInScreen.clickOnSignInButton()
  }

    @Test
    fun createNewAcountWithEmail(){
        val launchScreen=SignOut()
       val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1)   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(1) //1 is Female
        emailSignUpScreen.pasteEmail()
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegister()
    }
    @Test
    fun incorectYear(){
        val launchScreen=SignOut()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectDefultYear()   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(1) //1 is Female
        emailSignUpScreen.pasteEmail()
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        val birthdayErrorMeassage=EmailSigUpScreen.worningObjects().waitForBirthdayObj()
        Assert.assertEquals("Warning message shoild be $birthdayErrorMeassage because test didn't provide password",birthdayErrorMeassage.toLowerCase(),"You must be 13 or over".toLowerCase())
    }
    @Test
    fun genderNotSelected(){
        val launchScreen=SignOut()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1)   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.pasteEmail()
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        val genderErrorMessage=EmailSigUpScreen.worningObjects().waitForGenderOj()
        Assert.assertEquals("Warning message shoild be $genderErrorMessage because test didn't provide password",genderErrorMessage.toLowerCase(),"Please pick a gender".toLowerCase())
    }
    @Test
    fun emailNotProvided(){
        val launchScreen=SignOut()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1)   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(1) //1 is Female
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        val emailErrorMessage=EmailSigUpScreen.worningObjects().waitForEmailWorning()
        Assert.assertEquals ("Warning message should be $emailErrorMessage because test didn't provide password",emailErrorMessage.toLowerCase(),"Enter a valid email address".toLowerCase())
    }
    @Test
    fun passwordNotProvided(){
        val launchScreen=SignOut()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1)   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(1) //1 is Female
        emailSignUpScreen.pasteEmail()
        emailSignUpScreen.clickOnRegisterExseption()
        val errorMessage=EmailSigUpScreen.worningObjects().waitForPasswordWorning()
        Assert.assertEquals("Warning message shoild be $errorMessage because test didn't provide password",errorMessage.toLowerCase(),"Please provide a password".toLowerCase())
    }








}