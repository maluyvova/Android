package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiObject
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.RandomEmail
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/26/18.
 */
class EmailSigUpScreen : BaseScreen() {
    private val firstNameField = findObjectById(appPackage + ":id/name")
    private val emailField = findObjectById(appPackage + ":id/email")
    private val passwordField = findObjectById(appPackage + ":id/password")
    private val registerButton = findObjectById(appPackage + ":id/sign_up_button")
    private val termOfUseButton = findObjectById(appPackage + ":id/term_of_use")
    private val privacyPolice = findObjectById(appPackage + ":id/privacy_policy")
    private val signInButton = uiDevice.findObject(UiSelector().packageName(appPackage).text("Sign In"))
    private val birthdayButton = findObjectById(appPackage + ":id/birthday")
    private val genderButton = findObjectById(appPackage + ":id/gender")
    private val okButtonWhenEmailAlreadyexists = findObjectById("android:id/button1")
    private val signInButtonInRegisterScreen = uiDevice.findObject(UiSelector().packageName(appPackage).text("Sign In"))
    private val unvisablePasswordToggle = findObjectById(appPackage+":id/text_input_password_toggle")


    init {
        Assert.assertTrue("Expected first name field not showed up", firstNameField.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected email field not showed up", emailField.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected password field not showed up", passwordField.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected register button not showed up", registerButton.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected termOfUse button not showed up", termOfUseButton.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected privacy police not showed up", privacyPolice.waitForExists(moviesListTimeout))
        //Assert.assertTrue("Expected signIn button not showed up", signInButton.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected birthday button not showed up", birthdayButton.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected gender button not showed up", genderButton.waitForExists(moviesListTimeout))
    }


    fun sendTextToNameField(name: String) {
        firstNameField.setText(name)
    }

    fun selectYear(field: Int, steps: Int, years: String, years1: String, years2: String, years3: String) {
        birthdayButton.click()
        val box = UiCollection(UiSelector().resourceId("android:id/pickers"))
        val data = UiSelector().className("android.widget.NumberPicker")
        val year = box.getChildByInstance(data, field)
        fun textFromYear(): UiObject {
            return year.getChild(UiSelector().resourceId("android:id/numberpicker_input"))
        }
        while (textFromYear().text != years && textFromYear().text != years1 && textFromYear().text != years2 && textFromYear().text != years3) {
            year.swipeDown(31)
        }
        findObjectById("android:id/button1").click()
    }

    fun selectDefultYear() {
        birthdayButton.click()
        findObjectById("android:id/button1").click()
    }

    fun clickOnSignIn(): SignInScreen {
        signInButtonInRegisterScreen.waitForExists(globalTimeout)
        signInButtonInRegisterScreen.click()
        return SignInScreen()
    }

    fun selectGender(nums: Int) {
        genderButton.click()
        val doneButton = uiDevice.findObject(UiSelector().packageName(appPackage).text("DONE"))
        if (nums == 1) {
            uiDevice.findObject(UiSelector().packageName(appPackage).text("Female")).click()
            doneButton.click()
        } else if (nums == 2) {
            uiDevice.findObject(UiSelector().packageName(appPackage).text("Male")).click()
            doneButton.click()
        } else {
            uiDevice.findObject(UiSelector().packageName(appPackage).text("Other")).click()
            doneButton.click()
        }

    }

    fun pasteEmail(name: String, gmail: String) {
        emailField.setText(name + RandomEmail.randomemail() + RandomEmail.randomemail() + gmail)
    }

    fun pasteEmailExists(email: String) {
        emailField.setText(email)
    }


    fun pastePassword(text: String) {
        passwordField.setText(text)
    }

    fun clickOnRegister(): HomeScreen {
        registerButton.click()
        return HomeScreen()
    }

    fun clickOnRegisterExseption() {
        registerButton.click()
    }

    fun clickAndCheckOnOKButtonWhenEmailAlreadyExists(): EmailSigUpScreen {
        okButtonWhenEmailAlreadyexists.waitForExists(globalTimeout)
        okButtonWhenEmailAlreadyexists.click()
        return EmailSigUpScreen()
    }

    fun clickOnTermOfUse(): TermOfUseScreen {
        termOfUseButton.click()
        return TermOfUseScreen()
    }

    fun getTextFromFields():MutableList<String>{
       return mutableListOf<String>(firstNameField.text,emailField.text,passwordField.text)
    }

    fun makeUnvisablePassword(){
        if(unvisablePasswordToggle.exists())
        unvisablePasswordToggle.click()
    }


    class worningObjects() : BaseScreen() {
        private val birhtdayobj = findObjectById(appPackage + ":id/birthday_error_warning")
        private val genderwarningobj = findObjectById(appPackage + ":id/gender_error_warning")
        private val emailWarningObj = findObjectById(appPackage + ":id/email_error_warning")
        private val passwordWarnngObj = findObjectById(appPackage + ":id/password_error_warning")
        private val youMustBe13YearsOldError = findObjectById(appPackage + ":id/birthday_error_warning")
        fun waitForBirthdayObj(): String {
            birhtdayobj.waitForExists(globalTimeout)
            return birhtdayobj.text
        }

        fun waitForGenderOj(): String {
            genderwarningobj.waitForExists(globalTimeout)
            return genderwarningobj.text
        }

        fun waitForEmailWorning(): String {
            emailWarningObj.waitForExists(globalTimeout)
            return emailWarningObj.text
        }

        fun waitForPasswordWorning(): String {
            val some = passwordWarnngObj.waitForExists(globalTimeout)
            return passwordWarnngObj.text
        }

        fun waitForYouMustBe13YearsOld(): String {
            youMustBe13YearsOldError.waitForExists(globalTimeout)
            return youMustBe13YearsOldError.text
        }


    }


}