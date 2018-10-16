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
    private val firstNameField = appPackage + ":id/name"
    private val emailField = appPackage + ":id/email"
    private val passwordField = appPackage + ":id/password"
    private val registerButton = appPackage + ":id/sign_up_button"
    private val termOfUseButton = appPackage + ":id/term_of_use"
    private val privacyPolice = appPackage + ":id/privacy_policy"
    private val birthdayButton = appPackage + ":id/birthday"
    private val genderButton = appPackage + ":id/gender"
    private val okButtonWhenEmailAlreadyexists = "android:id/button1"
    private val signInButtonInRegisterScreen = "Sign In"
    private val unvisablePasswordToggle = appPackage + ":id/text_input_password_toggle"
    private val androidButton = "android:id/button1"


    init {
        Assert.assertTrue("Expected first name field not showed up", findElementById(firstNameField, false).waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected email field not showed up", findElementById(emailField, false).waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected password field not showed up", findElementById(passwordField, false).waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected register button not showed up", findElementById(registerButton, false).waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected termOfUse button not showed up", findElementById(termOfUseButton, false).waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected privacy police not showed up", findElementById(privacyPolice, false).waitForExists(moviesListTimeout))
        //Assert.assertTrue("Expected signIn button not showed up", signInButton.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected birthday button not showed up", findElementById(birthdayButton, false).waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected gender button not showed up", findElementById(genderButton, false).waitForExists(moviesListTimeout))
    }


    fun sendTextToNameField(name: String) {
        findObjectById(firstNameField, false).setText(name)
    }

    fun selectYear(field: Int, steps: Int, years: String, years1: String, years2: String, years3: String) {
        findObjectById(birthdayButton, false).click()
        val box = UiCollection(UiSelector().resourceId("android:id/pickers"))
        val data = UiSelector().className("android.widget.NumberPicker")
        val year = box.getChildByInstance(data, field)
        fun textFromYear(): UiObject {
            return year.getChild(UiSelector().resourceId("android:id/numberpicker_input"))
        }
        while (textFromYear().text != years && textFromYear().text != years1 && textFromYear().text != years2 && textFromYear().text != years3) {
            year.swipeDown(31)
        }
        findObjectById(androidButton, false).click()
    }

    fun selectDefultYear() {
        findObjectById(birthdayButton, false).click()
        findObjectById(androidButton, false).click()
    }

    fun clickOnSignIn(): SignInScreen {
        findElementByText(signInButtonInRegisterScreen, true).click()
        return SignInScreen()
    }

    fun selectGender(nums: Int) {
        findObjectById(genderButton, false).click()
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
        findObjectById(emailField, false).setText(name + RandomEmail.randomemail() + RandomEmail.randomemail() + gmail)
    }

    fun pasteEmailExists(email: String) {
        findObjectById(emailField, false).setText(email)
    }


    fun pastePassword(text: String) {
        findObjectById(passwordField, false).setText(text)
    }

    fun clickOnRegister(): HomeScreen {
        findObjectById(registerButton, false).click()
        return HomeScreen()
    }

    fun clickOnRegisterExseption() {
        findObjectById(registerButton, false).click()
    }

    fun clickAndCheckOnOKButtonWhenEmailAlreadyExists(): EmailSigUpScreen {
        findObjectById(okButtonWhenEmailAlreadyexists, true).click()
        return EmailSigUpScreen()
    }

    fun clickOnTermOfUse(): TermOfUseScreen {
        findObjectById(termOfUseButton, false).click()
        return TermOfUseScreen()
    }

    fun getTextFromFields(): MutableList<String> {
        return mutableListOf<String>(findObjectById(firstNameField, false).text, findObjectById(emailField, false).text, findObjectById(passwordField, false).text)
    }

    fun makeUnvisablePassword() {
        if (findElementById(unvisablePasswordToggle, false).exists())
            findObjectById(unvisablePasswordToggle, false).click()
    }


    class worningObjects() : BaseScreen() {
        private val birhtdayobj = appPackage + ":id/birthday_error_warning"
        private val genderWarningObj = appPackage + ":id/gender_error_warning"
        private val emailWarningObj = appPackage + ":id/email_error_warning"
        private val passwordWarnngObj = appPackage + ":id/password_error_warning"
        private val youMustBe13YearsOldError = appPackage + ":id/birthday_error_warning"

        fun waitForBirthdayObj(): String {
            return findObjectById(birhtdayobj, true).text
        }

        fun waitForGenderObj(): String {
            return findObjectById(genderWarningObj, true).text

        }

        fun waitForEmailWorning(): String {
            return findObjectById(emailWarningObj, true).text

        }

        fun waitForPasswordWorning(): String {
            return findElementById(passwordWarnngObj, true).text

        }

        fun waitForYouMustBe13YearsOld(): String {
            return findObjectById(youMustBe13YearsOldError, true).text
        }


    }


}