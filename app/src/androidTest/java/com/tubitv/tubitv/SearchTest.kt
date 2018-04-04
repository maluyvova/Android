package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 4/4/18.
 */
class SearchTest:LaunchAppWithFacebook() {

  @Test
    fun verifyIfTitleAfterSearchContainText(){
      val textWhatTestIsLookingFor="zombie"
      val homeScreen=HomeScreen()
      val searchSreen=homeScreen.clickAndSendTextToSearch(textWhatTestIsLookingFor)
      val gotIt=searchSreen.clickOnTitleByInstatnce(0)
      val titleDatailScreen=gotIt.clickOnGotIt()
      val textOfTitle=titleDatailScreen.titleDatailScreen.toLowerCase()
      Assert.assertTrue("Title name not contain text which test provided in search field",textOfTitle.contains(textWhatTestIsLookingFor))
  }





}