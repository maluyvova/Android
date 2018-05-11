package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.GotIt
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.MovieDatailScreen
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 4/2/18.
 */
class SubCategoryTests:LaunchAppWithFacebook() {


  @Test
    fun addToQueuSubCategory() {

        val homeScreen=HomeScreen()
     val subCategoryScreen= homeScreen.scrollToTheEndAndClickOnSubCategory()
      subCategoryScreen.swipeScreen(1)
      val randomNumber= Random().nextInt(subCategoryScreen.countOfMovies())
      subCategoryScreen.clickOnTitle(randomNumber)
      GotIt().clickOnGotIt()
      MovieDatailScreen().simpleClickOnAddToQueue()
     val first= MovieDatailScreen().titleDatailScreen
      killApp()
      launchApp()
      val titlInQueie=homeScreen.getText("Queue")             //getTextOfTitleWithIndex("Queue")
      val movieDatailScreen=homeScreen.clickOnTitleNoGotIt()
      movieDatailScreen.clickOnRemoveFromQueue()
      Assert.assertEquals(first,titlInQueie)
    }
}