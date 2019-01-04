package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 4/4/18.
 */
class SearchTest: LaunchAppWithFacebook() {

  @Test
    fun verifyIfTitleAfterSearchContainText(){
      val textWhatTestIsLookingFor="zombie"
      val homeScreen=HomeScreen(true)
      val searchSreen=homeScreen.clickAndSendTextToSearch(textWhatTestIsLookingFor)
      val gotIt=searchSreen.clickOnTitleByInstatnce(0)
      val titleDatailScreen=gotIt.clickOnGotIt()
      val textOfTitle = titleDatailScreen.titleDatailScreen.toLowerCase()
      Assert.assertTrue("Title name not contain text which test provided in search field",textOfTitle.contains(textWhatTestIsLookingFor))
  }


    @Test
    fun slideTitleOnTheSide(){
        val textWhatTestIsLookingFor="zombie"
        val homeScreen=HomeScreen(true)
        val searchSreen=homeScreen.clickAndSendTextToSearch(textWhatTestIsLookingFor)
        val gotIt=searchSreen.clickOnTitleByInstatnce(0)
        val titleDatailScreen=gotIt.clickOnGotIt()
        val titleText=titleDatailScreen.titleDatailScreen
        titleDatailScreen.rightscrollScreenOnSide(3)
        val titleTextAfterSlides =titleDatailScreen.titleDatailScreen
        titleDatailScreen.leftscrllScreenOnSide(3)
        val titleorig=titleDatailScreen.titleDatailScreen
        Assert.assertEquals("Scroll on the side doesn't wrok",titleText,titleorig)
        Assert.assertNotEquals("Scroll on the side doesn't work",titleText,titleTextAfterSlides)
    }





}