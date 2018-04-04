package com.tubitv.tubitv

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import com.tubitv.tubitv.Screens.EmailSigUpScreen
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.LaunchScreen
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 3/23/18.
 */
class LogInTest:SimpleLaunchApp(){

    fun SignOut():LaunchScreen{
        val homepage=HomeScreen()
        val smallWindowWithSetings=homepage.clickOnThreeDotsSetings()
        val settingsScreen=smallWindowWithSetings.clickOnSettings()
        val launchScreen=settingsScreen.clickOnSignOut()
        return LaunchScreen()
    }

    @Test
    fun createNewAcountWithEmail(){
        val launchScreen=LaunchScreen()
       val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1,"1994","1993","1992","1991")   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(Random().nextInt(3)) //1 is Female
        emailSignUpScreen.pasteEmail("vladb","@gmail.com")
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegister()
    }
    @Test
    fun incorectYear(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectDefultYear()   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(Random().nextInt(3)) //1 is Female
        emailSignUpScreen.pasteEmail("vladb","@gmail.com")
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        val birthdayErrorMeassage=EmailSigUpScreen.worningObjects().waitForBirthdayObj()
        Assert.assertEquals("Warning message shoild be $birthdayErrorMeassage because test didn't provide password",birthdayErrorMeassage.toLowerCase(),"You must be 13 or over".toLowerCase())
    }
    @Test
    fun genderNotSelected(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1,"1994","1993","1992","1991")   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.pasteEmail("vladb","@gmail.com")
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        val genderErrorMessage=EmailSigUpScreen.worningObjects().waitForGenderOj()
        Assert.assertEquals("Warning message shoild be $genderErrorMessage because test didn't provide password",genderErrorMessage.toLowerCase(),"Please pick a gender".toLowerCase())
    }
    @Test
    fun emailNotProvided(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1,"1994","1993","1992","1991")   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(Random().nextInt(3)) //1 is Female
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        val emailErrorMessage=EmailSigUpScreen.worningObjects().waitForEmailWorning()
        Assert.assertEquals ("Warning message should be $emailErrorMessage because test didn't provide password",emailErrorMessage.toLowerCase(),"Enter a valid email address".toLowerCase())
    }
    @Test
    fun passwordNotProvided(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1,"1994","1993","1992","1991")   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(Random().nextInt(3)) //1 is Female
        emailSignUpScreen.pasteEmail("vladb","@gmail.com")
        emailSignUpScreen.clickOnRegisterExseption()
        val errorMessage=EmailSigUpScreen.worningObjects().waitForPasswordWorning()
        Assert.assertEquals("Warning message shoild be $errorMessage because test didn't provide password",errorMessage.toLowerCase(),"Please provide a password".toLowerCase())
    }
    @Test
    fun orlradyExistsEmail(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1,"1994","1993","1992","1991")   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(Random().nextInt(3)) //1 is Female
        emailSignUpScreen.pasteEmailExists("vburian@tubi.tv")
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        emailSignUpScreen.clickAndCheckOnOKButtonWhenEmailAlreadyExists()
    }
    @Test
    fun mustBe13YearOld(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        emailSignUpScreen.sendTextToNameField("Vova")
        emailSignUpScreen.selectYear(2,1,"2009","2010","2011","2012")   //field its year, and steps it's how many times swapdowm 1 time 1 year
        emailSignUpScreen.selectGender(Random().nextInt(3)) //1 is Female
        emailSignUpScreen.pasteEmail("vladb","@gmail.com")
        emailSignUpScreen.pastePassword("tubitv")
        emailSignUpScreen.clickOnRegisterExseption()
        val warnigntext=EmailSigUpScreen.worningObjects().waitForYouMustBe13YearsOld()
        Assert.assertEquals ("Warning message should be $warnigntext because test didn't correct year old",warnigntext.toLowerCase(),"You must be 13 or over".toLowerCase())
    }
    @Test
    fun naviageteFromRegisterToSignIn(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail= launchScreen.clickOnCreateAccount()
        val emailSignUpScreen=signInWithFacebookOrEmail.clickOnSighUpWithEmail()
        val signInScreen=emailSignUpScreen.clickOnSignIn()
    }
    @Test
    fun signUpWithFacebook(){
        val launchScreen=LaunchScreen()
        val signInWithFacebookOrEmail=launchScreen.clickOnCreateAccount()
        signInWithFacebookOrEmail.clickOnSignUpWithFacebook()
    }










}