package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiObject
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.RandomEmail
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/26/18.
 */
class EmailSigUpScreen:BaseScreen(){
   private val firstNameField=UiDeviceID("com.tubitv:id/name")
    private val emailField=UiDeviceID("com.tubitv:id/email")
   private val passwordField=UiDeviceID("com.tubitv:id/password")
    private val registerButton=UiDeviceID("com.tubitv:id/sign_up_button")
    private val termOfUseButton=UiDeviceID("com.tubitv:id/term_of_use")
    private val privacyPolice=UiDeviceID("com.tubitv:id/privacy_policy")
    private val signInButton=uiDevice.findObject(UiSelector().packageName("com.tubitv").text("Sign In"))
    private val birthdayButton=UiDeviceID("com.tubitv:id/birthday")
    private val genderButton=UiDeviceID("com.tubitv:id/gender")


    init {
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", firstNameField.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", emailField.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", passwordField.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", registerButton.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", termOfUseButton.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", privacyPolice.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", signInButton.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", birthdayButton.waitForExists (moviesListTimeout))
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", genderButton.waitForExists (moviesListTimeout))
    }


    fun sendTextToNameField(name:String){
        firstNameField.setText(name)
    }
    fun selectYear(field:Int,steps:Int){
        birthdayButton.click()
       val box= UiCollection(UiSelector().resourceId("android:id/pickers"))
        val data=UiSelector().className("android.widget.NumberPicker")
        val year=box.getChildByInstance(data, field)
        fun textFromYear():UiObject {
         return   year.getChild(UiSelector().resourceId("android:id/numberpicker_input"))
        }
        while (textFromYear().text!="1994")
        {
            year.swipeDown(31)
        }
        UiDeviceID("android:id/button1").click()
    }
    fun selectDefultYear(){
        birthdayButton.click()
        UiDeviceID("android:id/button1").click()
    }

    fun selectGender(nums:Int){
        genderButton.click()
        val doneButton=uiDevice.findObject(UiSelector().packageName("com.tubitv").text("DONE"))
        if (nums==1){
            uiDevice.findObject(UiSelector().packageName("com.tubitv").text("Female")).click()
            doneButton.click()
        }
       else if(nums==2){
            uiDevice.findObject(UiSelector().packageName("com.tubitv").text("Male")).click()
            doneButton.click()
        }
        else{ uiDevice.findObject(UiSelector().packageName("com.tubitv").text("Other")).click()
        doneButton.click()}

    }

    fun pasteEmail(){
        emailField.setText(RandomEmail.randomemail())
    }
    fun pastePassword(text:String){
        passwordField.setText(text)
    }
    fun clickOnRegister():HomeScreen{
        registerButton.click()
        return HomeScreen()
    }
    fun clickOnRegisterExseption(){
        registerButton.click()
    }


    class worningObjects():BaseScreen(){
        private val birhtdayobj=UiDeviceID("com.tubitv:id/birthday_error_warning")
        private val genderwarningobj=UiDeviceID("com.tubitv:id/gender_error_warning")
        private val emailWarningObj=UiDeviceID("com.tubitv:id/email_error_warning")
        private val passwordWarnngObj=UiDeviceID("com.tubitv:id/password_error_warning")
        fun waitForBirthdayObj():String{
            birhtdayobj.waitForExists(globalTimeout)
            return birhtdayobj.text
        }
        fun waitForGenderOj():String{
           genderwarningobj.waitForExists(globalTimeout)
            return genderwarningobj.text
        }
        fun waitForEmailWorning():String{
            emailWarningObj.waitForExists(globalTimeout)
            return emailWarningObj.text
        }
        fun waitForPasswordWorning():String{
           val some= passwordWarnngObj.waitForExists(globalTimeout)
            return passwordWarnngObj.text
        }



    }





}