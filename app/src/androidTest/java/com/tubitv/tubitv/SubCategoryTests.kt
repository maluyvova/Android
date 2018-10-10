package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.GotIt
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.MovieDatailScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 4/2/18.
 */
class SubCategoryTests : LaunchAppWithFacebook() {


    @Test
    fun addToQueueSubCategory() {

        val category = "Queue"
        val homeScreen = HomeScreen()
        val sideCategoryScreen = homeScreen.clickOnSidecategorButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Special Interest")
        val randomNumber = Random().nextInt(subCategoryScreen.countOfMovies())
        subCategoryScreen.clickOnTitle(randomNumber)
        GotIt().clickOnGotIt()
        MovieDatailScreen().simpleClickOnAddToQueue()
        val first = MovieDatailScreen().titleDatailScreen
        killApp()
        launchApp(appPackage, false)
        val titlInQueie = homeScreen.getText(category)             //getTextOfTitleWithIndex("Queue")
        homeScreen.clickOnElementByText(category)
        val movieDatailScreen = MoviesByCategoryScreen().clickOnTitleNoGotIt()
        movieDatailScreen.simpleClickOnAddToQueue() //remove from queue in this case
        killApp()
        launchApp(appPackage, false)
        Assert.assertEquals(first, titlInQueie)
    }
}