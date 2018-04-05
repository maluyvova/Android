package com.tubitv.tubitv
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.*
import android.util.Log
import com.tubitv.tubitv.Networking.ServerManager
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.MovieDatailScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import org.junit.Assert
import junit.framework.Assert.assertEquals
import org.junit.After

/**
 * Created by vburian on 2/20/18.
 */

class MoviesTest:LaunchAppWithFacebook() {


    @After
    fun deleteQuie() {
        ServerManager().deleteQueue()
    }


    @Test
    fun selectTitleFromMostPopular() {

        val homePage = HomeScreen() //at this moment it's checking if test in coorect screen
        val titleInHomeScreen = homePage.title
        val gotitPage = homePage.clickOnTitle()
        val datailPage = gotitPage.clickOnGotIt()
        val titleInDatailScreen = datailPage.titleDatailScreen
        assertEquals("Orginal name $titleInHomeScreen should be same like $titleInDatailScreen", titleInHomeScreen.toLowerCase(), titleInDatailScreen.toLowerCase())
    }

    @Test
    fun selectTitleFromFeatured() {
        val homePage = HomeScreen()
        val text = homePage.textOfTitleInFeaturedCategor
        val gotit = homePage.clickOnTitleInFeaturedCateg()
        val moviedatail = gotit.clickOnGotIt()
        val titleInDatailScreen = moviedatail.titleDatailScreen
        assertEquals("Orginal name $text should be same like $titleInDatailScreen", text.toLowerCase(), titleInDatailScreen.toLowerCase())

    }

    @Test
    fun longPressAndAddToQueue() {
        val mostpoular = "Most Popular"
        val homePage = HomeScreen()
        val TextInHomeScreen = homePage.title
        val titleInHomeScreen = homePage.longPress()
        val screenWithQueue = titleInHomeScreen.clickAddToQueueAfterLongClick()
        homePage.waitForExistsFirstCategoryText("Queue")
        val TextFromQueue = screenWithQueue.textFromFirstTitleInQueue
        if (TextInHomeScreen.toLowerCase().equals(TextFromQueue.toLowerCase())) {
            val homeScreen = HomeScreen()
            val titlesInHomeScreen = homeScreen.longPressToRemoveFromQueue()
            titlesInHomeScreen.clickAddToQueueAfterLongClick()
            homePage.waitForExistsFirstCategoryText(mostpoular)
            val textOfCategory = homePage.textCategory

            assertEquals("Orginal name $TextInHomeScreen should be same like $TextFromQueue", textOfCategory.toLowerCase(), mostpoular.toLowerCase())
        } else print("Title in queue not equal to title what test adds to queue")
    }

    @Test
    fun clickOnCategory() {
        val homePage = HomeScreen()
        val textOfCategory = homePage.textCategory
        homePage.getTextOfCategory().click()
        val moviesByCategoryScreen = MoviesByCategoryScreen()
        val movieText = moviesByCategoryScreen.titleText
        val textOfCategoryInCategoryScreen = moviesByCategoryScreen.categoryText
        val gotItScreen = moviesByCategoryScreen.clickOnTitle()
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val textOfTitleInDatailScreen = movieDatailScreen.titleDatailScreen
        assertEquals("Text of Categories in home page is not matched with text in MovieCategory ", textOfCategory.toLowerCase(), textOfCategoryInCategoryScreen.toLowerCase())
        assertEquals("Title from Category Screen is not matches with title in Movies", movieText.toLowerCase(), textOfTitleInDatailScreen.toLowerCase())

    }

    @Test
    fun addToQueue() {
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle()
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val textOfTitleInMovieDatailScreen = movieDatailScreen.titleDatailScreen
        val homePage2 = movieDatailScreen.clickOnAddToQueue()
        homePage2.waitForExistsFirstCategoryText("Queue")
        val categoryInHomePage = homePage2.textCategory// should be Queue
        val textOfTitleInHomePage = homePage2.title
        val movieDatailScreen2 = homePage2.clickOnTitleNoGotIt()
        movieDatailScreen2.clickOnRemoveFromQueue()
        val homePage3 = HomeScreen()
        val quie=homePage3.getTextOfCategory().text
        if (quie=="Queue"){
            homePage3.getTextOfCategory().waitForExists(globalTimeout)
        }
        homePage3.waitForExistsFirstCategoryText("Most Popular")
        val categoryInHomePage1 = homePage3.textCategory//should be most popular
        assertEquals("Category Queue is not on home page ", categoryInHomePage.toLowerCase(), "queue")
        assertEquals("Title text in Home Page not matches with title in Movie Datal Page ", textOfTitleInHomePage.toLowerCase(), textOfTitleInMovieDatailScreen.toLowerCase())
        assertEquals("Queue is still on Hme Page", categoryInHomePage1.toLowerCase(), "most popular")
    }

    @Test
    fun addAndDelteFromQueue() {
        val mostpoular = "Most Popular"
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle()
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.simpleClickOnAddToQueue()
        movieDatailScreen.waitUntillSelected()
        val homePage2 = movieDatailScreen.simpleClickOnRemoveFromQueue()
        homePage2.waitForExistsFirstCategoryText(mostpoular)
        val textOfCategory = homePage2.textCategory
        assertEquals("Queue category still on home page after,click add and then remove from queue", textOfCategory.toLowerCase(), "most popular")
    }

    @Test
    fun startPlaybackAndCheckingIfTitleInHistory() {
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle()
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        Thread.sleep(45000)
        uiDevice.click(20, 20)
        uiDevice.pressBack()
        uiDevice.pressBack()
        killApp()
        launchApp()
        val homePage2 = HomeScreen.HomeScreenWithContinueWatching()
        homePage2.removeFromHistory()
        val removeFromHistoryScreen = HomeScreen.RemoveFormHistoryScreen()
        removeFromHistoryScreen.clickOnRemoveFromHistory()
        val firstCategor = homePage.textCategory
        Assert.assertEquals("First Category should be 'Most popular' because test should delete title form 'Continue watching' with long press", firstCategor.toLowerCase(), "most popular")
    }


    @Test
    fun clickOnThreeDots() {
        val homePage = HomeScreen()
        val textOfCategorInHomePage = homePage.textCategory
        homePage.clickOnThreeDots()
        val moviesbycategoryscreen = MoviesByCategoryScreen()
        val textOfCategoriInCaategotScreen = moviesbycategoryscreen.categoryText
        Assert.assertEquals("This test clicking on three dots,and test fail because  category text mismatch in HomePage and CategoryScreen", textOfCategorInHomePage.toLowerCase(), textOfCategoriInCaategotScreen.toLowerCase())
    }

    @Test
    fun scrollToSideAndVerifyIfTitleMissmatches() {
        val homescreen = HomeScreen()
        homescreen.ScrollToSpecificCategory("Action")
        val textOfTitle = homescreen.getTextOfTitleWithIndex()
        homescreen.horisontalScrollTitles(4)
        val textOfTitle2 = homescreen.getTextOfTitleWithIndex()
        Assert.assertNotEquals("This test scrolling titles to side and comparing first title text with text in new view  ", textOfTitle, textOfTitle2)
    }
}