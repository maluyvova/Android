package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.facebookLogin
import com.tubitv.tubitv.globalTimeout

/**
 * Created by vburian on 6/6/18.
 */
class HuluPlaybackScreen:BaseScreen() {

    private val forAgeLimitationworningMessage=uiDevice.findObject(UiSelector().textContains("sorry"))
    private val allElements=UiCollection(UiSelector().resourceId("mobile-player"))
    private val forAgeLimitationMonth=uiDevice.findObject(UiSelector().text("Month"))
    private val forAgeLimitationDay=uiDevice.findObject(UiSelector().text("Day"))
    private val forAgeLimitationYear=uiDevice.findObject(UiSelector().text("Year"))
    private val forAgeLimitationMonthPicker=uiDevice.findObject(UiSelector().text("February"))
    private val forAgeLimitationDayPicker=uiDevice.findObject(UiSelector().text("4"))
    private val forAgeLimitationYearPicker=uiDevice.findObject(UiSelector().textContains("19"))
    private val forAgeLimitationSubmitButton=allElements.getChild(UiSelector().index(2)).
            getChild(UiSelector().index(0))
            .getChild(UiSelector().index(2))
            .getChild(UiSelector().index(5))
    private val forAgeLimitationYearScrollable=UiCollection(UiSelector().resourceId("android:id/custom")).getChild(UiSelector().index(0))
    private val scroll= UiScrollable(UiSelector().resourceId("android:id/custom"))
    private val adscounter=uiDevice.findObject(UiSelector().textContains("of"))









    fun  waitForAgeLimitation():Boolean{
    if( forAgeLimitationDay.waitForExists(globalTimeout)&&
        forAgeLimitationMonth.waitForExists(globalTimeout)&&
        forAgeLimitationYear.waitForExists(globalTimeout) ){
        return true
    }
        else return false }

    fun clickOnMonth(){
        forAgeLimitationMonth.click()
    }
    fun clickOnDay(){
        forAgeLimitationDay.waitForExists(globalTimeout)
        forAgeLimitationDay.click()
    }
    fun clickOnYear(){
        forAgeLimitationYear.waitForExists(globalTimeout)
        forAgeLimitationYear.click()
    }
    fun selectMonth(){
       if( forAgeLimitationMonthPicker.waitForExists(globalTimeout))
        forAgeLimitationMonthPicker.click()
        else uiDevice.click(540,440)
    }
    fun selectDay(){
       if(forAgeLimitationDayPicker.waitForExists(globalTimeout))
        forAgeLimitationDayPicker.click()
        else uiDevice.click(855,440)

    }
    fun selectYear(){
     if (forAgeLimitationYearPicker.waitForExists(globalTimeout))
       forAgeLimitationYearPicker.click()
       else {
        uiDevice.click(1076,480)

    }}
    fun clickOnSubmit(){
     forAgeLimitationSubmitButton.click()
    }
    fun getextFromWorningMessage():String{
        forAgeLimitationworningMessage.waitForExists(globalTimeout)
       return forAgeLimitationworningMessage.text
    }
    fun scrollYaers(){
        var i =0
        if (scroll.waitForExists(globalTimeout)){
        while(i<4){
            if(scroll.waitForExists(globalTimeout))
        scroll.setAsVerticalList().scrollToEnd(i)
            i++ } }
    else
            while(i<4){
            //uiDevice.swipe(1076,669,1076,250,i)
             uiDevice.drag(1076,669,1076,250,i)
                i++}
    }

    fun waitUntilAddsGone(){
        if (adscounter.waitForExists(facebookLogin)){
            adscounter.waitUntilGone(347000L)
        }
        else throw TestException("No ads")
    }
    fun verifyIfPlaybackOpenened():Boolean{
       return uiDevice.findObject(UiSelector().textContains("Evil Bong 777 ")).waitForExists(globalTimeout)
    }



}