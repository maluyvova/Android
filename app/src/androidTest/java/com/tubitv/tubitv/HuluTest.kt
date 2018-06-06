package com.tubitv.tubitv

import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.HuluPlaybackScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/6/18.
 */
class HuluTest:LaunchAppWithFacebook() {
    val textWhatTestIsLookingFor="Evil Bong 777"


@Test
    fun huluTitleWithYearsLimitationsErrorMessageValidation(){
    var warningMessage=""
    var warningMeassageAfterClose=""
        val homeScreen= HomeScreen()
        val searchSreen=homeScreen.clickAndSendTextToSearch(textWhatTestIsLookingFor)
        val gotIt=searchSreen.clickOnTitleByInstatnce(0)
        val titleDatailScreen=gotIt.clickOnGotIt()
        val textOfTitle=titleDatailScreen.titleDatailScreen.toLowerCase()
        if (textOfTitle.equals(textWhatTestIsLookingFor.toLowerCase())){
        titleDatailScreen.clickOnPlay()
            val huluPlaybackScreen=HuluPlaybackScreen()
            if (huluPlaybackScreen.waitForAgeLimitation()){
                huluPlaybackScreen.clickOnMonth()
                huluPlaybackScreen.selectMonth()
                huluPlaybackScreen.clickOnDay()
                huluPlaybackScreen.selectDay()
                huluPlaybackScreen.clickOnYear()
                huluPlaybackScreen.selectYear()
                huluPlaybackScreen.clickOnSubmit()
                 warningMessage=huluPlaybackScreen.getextFromWorningMessage()
               minimizeAndOpenAppFromSameScreen()
                warningMeassageAfterClose=huluPlaybackScreen.getextFromWorningMessage()
            }
            else throw
                    TestException("Fields for age limitation didn't appear ")
        }
    else throw TestException("Hulu tile ($textWhatTestIsLookingFor) with age limitation expired ")

    Assert.assertEquals("Text for warning message mismatch for " +
            "age limitation for hulu titles",warningMessage,"We're sorry, but the video you have requested is restricted to users 18 years and older. Your parents will thank us. Don't worry, though. We have plenty of other stuff to watch.")
    Assert.assertEquals("Text for warning message after minimize app is not the same",warningMeassageAfterClose,warningMessage)
}



@Test
fun huluTitleWithYearsLimitations(){
    val huluPlaybackScreen=HuluPlaybackScreen()
    val homeScreen= HomeScreen()
    val searchSreen=homeScreen.clickAndSendTextToSearch(textWhatTestIsLookingFor)
    val gotIt=searchSreen.clickOnTitleByInstatnce(0)
    val titleDatailScreen=gotIt.clickOnGotIt()
    val textOfTitle=titleDatailScreen.titleDatailScreen.toLowerCase()
    if (textOfTitle.equals(textWhatTestIsLookingFor.toLowerCase())){
        titleDatailScreen.clickOnPlay()
        if (huluPlaybackScreen.waitForAgeLimitation()){
            huluPlaybackScreen.clickOnMonth()
            huluPlaybackScreen.selectMonth()
            huluPlaybackScreen.clickOnDay()
            huluPlaybackScreen.selectDay()
            huluPlaybackScreen.clickOnYear()
            huluPlaybackScreen.scrollYaers()
            huluPlaybackScreen.selectYear()
            huluPlaybackScreen.clickOnSubmit()
            huluPlaybackScreen.waitUntilAddsGone()
            uiDevice.click(97,1030)
        } }
Assert.assertTrue(huluPlaybackScreen.verifyIfPlaybackOpenened())
}

}
