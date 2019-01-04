package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.GotIt
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.MovieDatailScreen
import com.tubitv.tubitv.appPackage
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 4/2/18.
 */
class SubCategoryTests : LaunchAppWithFacebook() {


    @Test
    fun addToQueueSubCategory() {
        var mark = true
        val category = "Queue"
        val homeScreen = HomeScreen(true)
        val sideCategoryScreen = homeScreen.clickOnSidecategorButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Special Interest")
        val randomNumber = Random().nextInt(subCategoryScreen.countOfMovies())
        subCategoryScreen.clickOnTitle(randomNumber)
        GotIt().clickOnGotIt()
        MovieDatailScreen().simpleClickOnAddToQueue()
        val first = MovieDatailScreen().titleDatailScreen
        killApp()
        launchApp(appPackage, false)
        val sideCategoryScreen2 = homeScreen.clickOnSidecategorButton()
        val subCategoryScreen2 = sideCategoryScreen2.scrollToSpecificCategory(category)
        val movieDatailScreen = subCategoryScreen2.clickOnTitleForQueueNoGotIt(0)
        val titleInQueue = movieDatailScreen.titleDatailScreen
        movieDatailScreen.simpleClickOnAddToQueue()
        killApp()
        launchApp(appPackage, false)
        val sideCategoryScreen3 = homeScreen.clickOnSidecategorButton()
        try {
            sideCategoryScreen3.scrollToSpecificCategoryWithoutReturn("Queue")
        } catch (e: TestException) {
            assertEquals("Queue category still on home page after,click add and then remove from queue", "most popular", "most popular")
            mark = false
        }
        if (mark) {
            assertEquals("Title still in Queue but test should remove it", 1, 2)
        }
        assertEquals("Incorrect title in Queue ", first, titleInQueue)

    }
}



