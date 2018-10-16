package com.tubitv.tubitv

import android.support.test.uiautomator.*
import com.tubitv.tubitv.Helpers.TestException
import org.junit.Test
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.MovieDatailScreen
import com.tubitv.tubitv.Screens.MoviesByCategoryScreen
import org.junit.Assert
import junit.framework.Assert.assertEquals

/**
 * Created by vburian on 2/20/18.
 */

class MoviesTest : LaunchAppWithFacebook() {


    @Test
    fun selectTitleFromMostPopular() {
        val homePage = HomeScreen() //at this moment it's checking if test in coorect screen
        val titleInHomeScreen = homePage.title
        val gotitPage = homePage.clickOnTitle(0)
        val datailPage = gotitPage.clickOnGotIt()
        val titleInDatailScreen = datailPage.titleDatailScreen
        assertEquals("Orginal name $titleInHomeScreen should be same like $titleInDatailScreen", titleInHomeScreen.toLowerCase(), titleInDatailScreen.toLowerCase())
    }

    @Test
    fun scrollFeaturedTitles() {
        val homePage = HomeScreen() //at this moment it's checking if test in coorect screen
        val text = homePage.textOfTitleInFeaturedCategor
        homePage.scrollFeaturetTitles(2)
        val titleInHomeScreen1 = homePage.textOfTitleInFeaturedCategor
        Assert.assertNotEquals(text, titleInHomeScreen1)
    }

    @Test
    fun selectTitleFromFeatured() {
        val homePage = HomeScreen()
        val text = homePage.textOfTitleInFeaturedCategor
        val gotit = homePage.clickOnTitleInFeaturedCategory()
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
        titleInHomeScreen.clickAddToQueueAfterLongClick()
        val sideCategoryScreen = homePage.clickOnSidecategorButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Queue")
        val gotItScreen = subCategoryScreen.clickOnTitleForQueue(0)
        val movieDatailsScreen = gotItScreen.clickOnGotIt()
        val textFromQueue = movieDatailsScreen.titleDatailScreen

        if (TextInHomeScreen.toLowerCase().equals(textFromQueue.toLowerCase())) {
            movieDatailsScreen.clickOnAddToQueue()
            val textOfCategory = homePage.textCategory
            assertEquals("Orginal name $TextInHomeScreen should be same like $textFromQueue", textOfCategory.toLowerCase(), mostpoular.toLowerCase())
        } else
            assertEquals("Title that added to queue doesn't match with title that realy in queue", 5, 4)
    }

    @Test
    fun clickOnCategory() {
        val homePage = HomeScreen()
        val textOfCategory = homePage.textCategory
        homePage.getTextOfCategory(0).click()
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
        val textOfCategory = homePage.getTextOfCategory(0).text
        val textOfTitleInHomePage = homePage.getText(textOfCategory)
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val textOfTitleInMovieDatailScreen = movieDatailScreen.titleDatailScreen
        movieDatailScreen.clickOnAddToQueue()
        val sideCategoryScreen = homePage.clickOnSidecategorButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Queue")
        val movieDatailScreen2 = subCategoryScreen.clickOnTitleForQueueNoGotIt(0)
        movieDatailScreen2.simpleClickOnRemoveFromQueue()
        if (movieDatailScreen2.checkIfStillOnThisPage()) {
            uiDevice.pressBack() //check this conditions
        }
        val homePage3 = HomeScreen()
        homePage3.getTextOfCategory(1).text
        val categoryInHomePage1 = homePage3.textCategory//should be most popular
        //  assertEquals("Title text in Home Page not matches with title in Movie Details Page ", textOfTitleInHomePage.toLowerCase(), textOfTitleInMovieDatailScreen.toLowerCase())
        assertEquals("Queue is still on Hme Page", categoryInHomePage1.toLowerCase(), "most popular")
    }

    @Test
    fun addAndDelteFromQueue() {
        //val nock = Mockt()
        var mark: Boolean = true
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.simpleClickOnAddToQueue()
        movieDatailScreen.waitUntillSelected()
        val homePage2 = movieDatailScreen.simpleClickOnRemoveFromQueue()
        val sideCategory = homePage2.clickOnSidecategorButton()
        try {
            sideCategory.scrollToSpecificCategoryWithoutReturn("Queue")
        } catch (e: TestException) {
            assertEquals("Queue category still on home page after,click add and then remove from queue", "most popular", "most popular")
            mark = false
        }
        if (mark) {
            assertEquals("Title still in Queue but test should remove it", 1, 2)
        }
    }
    //assertEquals("Queue category still on home page after,click add and then remove from queue", textOfCategory.toLowerCase(), "most popular") }

    @Test
    fun startPlaybackAndCheckingIfTitleInHistory() {
        val homePage = HomeScreen()
        val textOfCategory = homePage.getTextOfCategory(0).text
        val textOfTitleInHomePage = homePage.getText(textOfCategory)
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        Thread.sleep(45000)
        uiDevice.pressBack()
        uiDevice.pressBack()
        killApp()
        launchApp(appPackage, false)
        val sideCategoryScreen = HomeScreen().clickOnSidecategorButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Continue Watching")
        val titleInHistory = subCategoryScreen.textOFTitle
        val smallWindowAddToQueueOrHistory = subCategoryScreen.longClickOnTitle(0)
        smallWindowAddToQueueOrHistory.clickRemoveFromHistory()
        Assert.assertEquals("First Category should be 'Most popular' because test should delete title form 'Continue watching' with long press", textOfTitleInHomePage, titleInHistory)
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
        val category = "Action"
        val homescreen = HomeScreen()
        homescreen.ScrollToSpecificCategory(category)
        val textOfTitle = homescreen.getTextOfTitleWithIndex(category)
        homescreen.horisontalScrollTitles(2, category)
        val textOfTitle2 = homescreen.getTextOfTitleWithIndex(category)
        Assert.assertNotEquals("This test scrolling titles to side and comparing first title text with text in new view  ", textOfTitle, textOfTitle2)
    }

    @Test
    fun selectTitleFromYouMighAlsoLike() {
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val datailScreen = MovieDatailScreen().selectTitleFromMightAlsoLike()
        datailScreen.scrollableScreen.setAsVerticalList().scrollToEnd(1)
        Assert.assertFalse(datailScreen.youMightaAlsoLike.exists())

    }

    @Test
    fun counterForFeaturedTitles() {
        val homePage = HomeScreen()
        val beforeScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        homePage.scrollFeaturetTitles(beforeScrollingNumber.substring(4, 5).toInt() - 1)
        val afterScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        Assert.assertEquals("This test is scrolling Featured titles and then checking if the counter has been changed", beforeScrollingNumber.substring(4, 5), afterScrollingNumber.substring(0, 1))
    }

    @Test
    fun makeACircleForFeaturedTitles() {
        val homePage = HomeScreen()
        val beforeScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        val textOfTitleBeforeScrolling = homePage.textOfTitleInFeaturedCategor
        homePage.scrollFeaturetTitles(beforeScrollingNumber.substring(4, 5).toInt())
        val afterScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        val textOfTitleAfterScrolling = homePage.textOfTitleInFeaturedCategor
        Assert.assertEquals("This test is scrolling Featured titles till got circle and then checking if the number the that was in the beginning", beforeScrollingNumber.substring(0, 1), afterScrollingNumber.substring(0, 1))
        Assert.assertEquals("This test is scrolling Featured titles till got circle and then checking if the text of the title is the same that was in the beginning", textOfTitleBeforeScrolling, textOfTitleAfterScrolling)

    }

    // @Test
    fun startPlaybackAndCheckingIfTitleInHistoryd() {
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = movieDatailScreen.clickOnPlay()

        Thread.sleep(4000)
        uiDevice.pressEnter()
        uiDevice.click(50, 40)
        uiDevice.click(50, 40)
        uiDevice.click(300, 400)
        uiDevice.click(50, 40)
        uiDevice.click(50, 40)
        uiDevice.click(50, 40)
        //Thread.sleep(4000)
        uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_subtitles_ib")).click()
        // uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_tubi_controller_loading")).click()
        //UiObject2()
        // Assert.assertEquals("First Category should be 'Most popular' because test should delete title form 'Continue watching' with long press", firstCategor.toLowerCase(), "most popular")
    }

}