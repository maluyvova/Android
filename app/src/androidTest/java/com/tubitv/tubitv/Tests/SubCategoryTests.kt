package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.Categories
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
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        val sideCategoryScreen = homeScreen.clickOnBrowseButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory(Categories.CON_TV.value)
        val randomNumber = Random().nextInt(subCategoryScreen.countOfMovies())
        subCategoryScreen.clickOnTitle(randomNumber)
        GotIt().clickOnGotIt()
        MovieDatailScreen().simpleClickOnAddToQueue()
        val first = MovieDatailScreen().titleName
        killApp()
        launchApp(appPackage, false)
        val sideCategoryScreen2 = homeScreen.clickOnBrowseButton()
        val subCategoryScreen2 = sideCategoryScreen2.scrollToSpecificCategory(Categories.QUEUE.value)
        val movieDatailScreen = subCategoryScreen2.clickOnTitleForQueueNoGotIt(0)
        val titleInQueue = movieDatailScreen.titleName
        movieDatailScreen.simpleClickOnAddToQueue()
        killApp()
        launchApp(appPackage, false)
        val sideCategoryScreen3 = homeScreen.clickOnBrowseButton()
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



