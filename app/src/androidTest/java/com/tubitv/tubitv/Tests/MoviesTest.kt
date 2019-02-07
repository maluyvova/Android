package com.tubitv.tubitv.Tests

import android.support.test.uiautomator.*
import com.tubitv.tubitv.Enomus.DirectionOfScrolling
import com.tubitv.tubitv.Enomus.TypeOfContent
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.*
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.continueWatching
import junit.framework.Assert.*
import org.junit.Test
import org.junit.Assert
import java.util.*

/**
 * Created by vburian on 2/20/18.
 */

class MoviesTest : LaunchAppWithFacebook() {
    val tvCategory = "TV Comedies"
    private val category = "Horror"
    private val nameOfMovie = LinkedList<String>()
    private var selectFirstTime = true
    private val longTermTitle = "Oldboy"

    @Test
    fun selectTitleFromMostPopular() {
        val homePage = HomeScreen(true) //at this moment it's checking if test in coorect screen
        val titleInHomeScreen = homePage.title
        val gotitPage = homePage.clickOnTitle(0)
        val datailPage = gotitPage.clickOnGotIt()
        val titleInDatailScreen = datailPage.titleDatailScreen
        assertEquals("Orginal name $titleInHomeScreen should be same like $titleInDatailScreen", titleInHomeScreen.toLowerCase(), titleInDatailScreen.toLowerCase())
    }

    @Test
    fun scrollFeaturedTitles() {
        val homePage = HomeScreen(true) //at this moment it's checking if test in coorect screen
        val text = homePage.textOfTitleInFeaturedCategor
        homePage.scrollFeaturetTitles(2)
        val titleInHomeScreen1 = homePage.textOfTitleInFeaturedCategor
        Assert.assertNotEquals(text, titleInHomeScreen1)
    }

    @Test
    fun selectTitleFromFeatured() {
        val homePage = HomeScreen(true)
        val text = homePage.textOfTitleInFeaturedCategor
        val gotit = homePage.clickOnTitleInFeaturedCategory()
        val moviedatail = gotit.clickOnGotIt()
        val titleInDatailScreen = moviedatail.titleDatailScreen
        assertEquals("Orginal name $text should be same like $titleInDatailScreen", text.toLowerCase(), titleInDatailScreen.toLowerCase())

    }

    @Test
    fun longPressAndAddToQueue() {
        val homePage = HomeScreen(true)
        val firstCategoryName = homePage.textCategory()
        val TextInHomeScreen = homePage.title
        val titleInHomeScreen = homePage.longPress()
        if (!titleInHomeScreen.clickAddToQueueAfterLongClickWithoutReturn()) {
            try {
                homePage.longPressWithoutReturn()
                titleInHomeScreen.clickAddToQueueAfterLongClick()
            } catch (e: UiObjectNotFoundException) {
                homePage.longPressWithoutReturn()
                titleInHomeScreen.clickAddToQueueAfterLongClick()
            }
        }
        val sideCategoryScreen = homePage.clickOnBrowseButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Queue")
        val gotItScreen = subCategoryScreen.clickOnTitleForQueue(0)
        val movieDatailsScreen = gotItScreen.clickOnGotIt()
        val textFromQueue = movieDatailsScreen.titleDatailScreen

        if (TextInHomeScreen.toLowerCase().equals(textFromQueue.toLowerCase())) {
            movieDatailsScreen.clickOnAddToQueue()
            if (movieDatailsScreen.checkIfStillOnThisPage()) {
                uiDevice.pressBack() //check this conditions
            }
            val textOfCategory = homePage.textCategory()
            assertEquals("Orginal name $TextInHomeScreen should be same like $textFromQueue", textOfCategory.toLowerCase(), firstCategoryName.toLowerCase())
        } else
            assertEquals("Title that added to queue doesn't match with title that realy in queue", 5, 4)
    }

    @Test
    fun clickOnCategory() {
        val homePage = HomeScreen(true)
        val textOfCategory = homePage.textCategory()
        val caregory =homePage.getTextOfCategory(0)
        val categoryText=caregory.text
        caregory.click()
        val moviesByCategoryScreen = MoviesByCategoryScreen(categoryText)
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
        var mark: Boolean = true
        val homePage = HomeScreen(true)
        val firstCategoryName = homePage.textCategory()
        val textOfCategory = homePage.getTextOfCategory(0).text
        val textOfTitleInHomePage = homePage.getText(textOfCategory)
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val textOfTitleInMovieDatailScreen = movieDatailScreen.titleDatailScreen
        movieDatailScreen.clickOnAddToQueue()
        val sideCategoryScreen = homePage.clickOnBrowseButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Queue")
        val movieDatailScreen2 = subCategoryScreen.clickOnTitleForQueueNoGotIt(0)
        movieDatailScreen2.simpleClickOnRemoveFromQueue()
        if (movieDatailScreen2.checkIfStillOnThisPage()) {
            uiDevice.pressBack() //check this conditions
        }
        val homePage3 = HomeScreen(true)
        val sideCategory = homePage3.clickOnBrowseButton()
        try {
            sideCategory.scrollToSpecificCategoryWithoutReturn("Queue")   //Check this
        } catch (e: TestException) {
            assertEquals("Queue category still on home page after,click add and then remove from queue", "most popular", "most popular")
            mark = false
        }
        if (mark) {
            assertEquals("Title still in Queue but test should remove it", 1, 2)
        }
    }

    @Test
    fun addAndDelteFromQueue() {

        var mark: Boolean = true
        val homePage = HomeScreen(true)
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.clickOnAddToQueue()
        movieDatailScreen.waitUntillSelected()
        val homePage2 = movieDatailScreen.simpleClickOnRemoveFromQueue()
        val sideCategory = homePage2.clickOnBrowseButton()
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

    @Test
    fun startPlaybackAndCheckingIfTitleInHistory() {
        val homePage = HomeScreen(true)
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
        val sideCategoryScreen = HomeScreen(true).clickOnBrowseButton()
        val subCategoryScreen = sideCategoryScreen.scrollToSpecificCategory("Continue Watching")
        val titleInHistory = subCategoryScreen.textOFTitle
        val smallWindowAddToQueueOrHistory = subCategoryScreen.longClickOnTitle(0)
        smallWindowAddToQueueOrHistory.clickRemoveFromHistory()
        Assert.assertEquals("First Category should be 'Most popular' because test should delete title form 'Continue watching' with long press", textOfTitleInHomePage, titleInHistory)
    }


    @Test
    fun clickOnThreeDots() {
        val homePage = HomeScreen(true)
        val textOfCategorInHomePage = homePage.textCategory()
        homePage.clickOnThreeDots()
        val moviesbycategoryscreen = MoviesByCategoryScreen(textOfCategorInHomePage)
        val textOfCategoriInCaategotScreen = moviesbycategoryscreen.categoryText
        Assert.assertEquals("This test clicking on three dots,and test fail because  category text mismatch in HomePage and CategoryScreen", textOfCategorInHomePage.toLowerCase(), textOfCategoriInCaategotScreen.toLowerCase())
    }

    @Test
    fun scrollToSideAndVerifyIfTitleMissmatches() {
        val category = "Action"
        val homescreen = HomeScreen(true)
        homescreen.scrollToSpecificCategory(category, DirectionOfScrolling.DOWN)
        val textOfTitle = homescreen.getTextOfTitleWithIndex(category)
        uiDevice.pressBack()
        homescreen.horisontalScrollTitles(2, category)
        val textOfTitle2 = homescreen.getTextOfTitleWithIndex(category)
        Assert.assertNotEquals("This test scrolling titles to side and comparing first title text with text in new view  ", textOfTitle, textOfTitle2)
    }

    @Test
    fun selectTitleFromYouMighAlsoLike() {
        val homePage = HomeScreen(true)
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        val datailScreen = MovieDatailScreen().selectTitleFromMightAlsoLike()
        datailScreen.scrollableScreen.setAsVerticalList().scrollToEnd(1)
        Assert.assertFalse(datailScreen.youMightaAlsoLike.exists())
    }

    @Test
    fun counterForFeaturedTitles() {
        val homePage = HomeScreen(true)
        val beforeScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        homePage.scrollFeaturetTitles(beforeScrollingNumber.substring(4, 5).toInt() - 1)
        val afterScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        Assert.assertEquals("This test is scrolling Featured titles and then checking if the counter has been changed", beforeScrollingNumber.substring(4, 5), afterScrollingNumber.substring(0, 1))
    }

    @Test
    fun makeACircleForFeaturedTitles() {
        val homePage = HomeScreen(true)
        val beforeScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        val textOfTitleBeforeScrolling = homePage.textOfTitleInFeaturedCategor
        homePage.scrollFeaturetTitles(beforeScrollingNumber.substring(4, 5).toInt())
        val afterScrollingNumber = homePage.getTextFromFeaturedTitlesCounter()
        val textOfTitleAfterScrolling = homePage.textOfTitleInFeaturedCategor
        Assert.assertEquals("This test is scrolling Featured titles till got circle and then checking if the number the that was in the beginning", beforeScrollingNumber.substring(0, 1), afterScrollingNumber.substring(0, 1))
        Assert.assertEquals("This test is scrolling Featured titles till got circle and then checking if the text of the title is the same that was in the beginning", textOfTitleBeforeScrolling, textOfTitleAfterScrolling)

    }

    @Test
    fun selectPlaybackNavigateBackToHome5TimesSerials() {
        var firsTime = true
        fun selectMovie() {
            val homePage = HomeScreen(true)
            val sideCategoryScreen = homePage.clickOnBrowseButton()
            val subcategory = sideCategoryScreen.scrollToSpecificCategory(category)
            subcategory.clickOnTitle(0)
            if (firsTime) {
                GotIt().clickOnGotIt()
            }
            val playBack = MovieDatailScreen().clickOnPlay()
            playBack.waitUntilAdsfinishes()
            BaseScreen().navigateBackToHomeScreen()
            firsTime = false
        }
        selectMovie()
        selectMovie()
        selectMovie()
        selectMovie()
        selectMovie()
        val homeScreen = HomeScreen(true)
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .longClickOnTitle(0)
                .clickRemoveFromHistory()

    }

    fun seekToMiddle(numberOfTitle: Int) {

        val homePage = HomeScreen(true)
        homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(category)
                .clickOnTitle(numberOfTitle)
        if (selectFirstTime) {
            GotIt().clickOnGotIt()
        }
        selectFirstTime = false
        val movieDatailScreen = MovieDatailScreen()
        movieDatailScreen.dontSelectHuluTitle()
        nameOfMovie.add(movieDatailScreen.titleDatailScreen)
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.seekToMiddleOfPlayback()
    }


    @Test
    fun startPlaybackAndCheckingIfTitleAddedToHistoryForGuest() {
        seekToMiddle(0)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .longClickOnTitle(nameOfMovie.get(0))
                .clickRemoveFromHistory()
    }

    @Test
    fun checkIfTitleStillInContinueWatchingAfterKillingAppForGuest() {
        seekToMiddle(0)
        killApp()
        launchApp(appPackage, false)
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .longClickOnTitle(nameOfMovie.get(0))
                .clickRemoveFromHistory()
    }

    @Test
    fun add5MoviesTitlesToHistoryForGuest() {
        seekToMiddle(1)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(3)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(5)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(7)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(9)
        BaseScreen().navigateBackToHomeScreen()
        val subCategory = HomeScreen(true)
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
        subCategory.longClickOnTitle(nameOfMovie.get(0))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(1))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(2))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(3))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(4))
                .clickRemoveFromHistory()

    }

    @Test
    fun startPlaybackAndCheckingIfTitleAddedToHistoryForRegisterUser() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        seekToMiddle(0)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .longClickOnTitle(nameOfMovie.get(0))
                .clickRemoveFromHistory()
    }

    @Test
    fun checkIfTitleStillInContinueWatchingAfterKillingAppForRegisterUser() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        seekToMiddle(0)
        killApp()
        launchApp(appPackage, false)
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .longClickOnTitle(nameOfMovie.get(0))
                .clickRemoveFromHistory()
    }

    @Test
    fun add5MoviesTitlesToHistoryForRegisterUser() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        seekToMiddle(1)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(3)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(5)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(7)
        BaseScreen().navigateBackToHomeScreen()
        seekToMiddle(9)
        BaseScreen().navigateBackToHomeScreen()
        val subCategory = HomeScreen(true)
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
        subCategory.longClickOnTitle(nameOfMovie.get(0))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(1))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(2))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(3))
                .clickRemoveFromHistory()
        subCategory.longClickOnTitle(nameOfMovie.get(4))
                .clickRemoveFromHistory()
    }

    @Test
    fun playTillFirstQueuePointScrollToAutoplayVerifyIfTitleStartsFromBeginningForGuest() {
        val homeScreen = HomeScreen(true)
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = searchSreen.provideTextToSearch("Oldboy")
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val playBackScreen = titleDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        Thread.sleep(550000)
        val autoPlay = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        autoPlay.playTitleFromAutoplay()
        playBackScreen.waitUntilAdsfinishes()
        val timer = playBackScreen.textOfLeftTimer()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()


        assertTrue("This test was playing title till first queue point then scroll to autoplay and then select title from autoplay, Title from autoplay starts not from beginning", timer.substring(0, 2).equals("00"))
    }

    @Test
    fun playTillFirstQueuePointScrollToAutoplayVerifyIfTitleStartsFromBeginningForRegisterUser() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = searchSreen.provideTextToSearch(longTermTitle)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val playBackScreen = titleDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        Thread.sleep(550000)
        val autoPlay = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        autoPlay.playTitleFromAutoplay()
        playBackScreen.waitUntilAdsfinishes()
        val timer = playBackScreen.textOfLeftTimer()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()


        assertTrue("This test was playing title till first queue point then scroll to autoplay and then select title from autoplay, Title from autoplay starts not from beginning", timer.substring(0, 2).equals("00"))
    }

    @Test
    fun checkIfTitleRemovedFromContinueWatchingAfterAutoplayForGuest() {
        var titleStillInContinueWatching = true
        val homeScreen = HomeScreen(true)
        val textOfCategory = homeScreen.getTextOfCategory(0).text
        val textOfTitle = homeScreen.getText(textOfCategory)
        val gotItScreen = homeScreen.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        playBackScreen.seekToMiddleOfPlayback()
        uiDevice.pressBack()
        movieDatailScreen.clickOnPlay()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
        val subcategoryScreen = BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
        try {
            subcategoryScreen.longClickOnTitle(textOfTitle)
        } catch (e: TestException) {
            BaseScreen().navigateBackToHomeScreen()
                    .clickOnBrowseButton()
                    .scrollToSpecificCategory(continueWatching)
                    .removeAllTitles()
            titleStillInContinueWatching = false
        }
        assertFalse("Title: $textOfTitle still stays in 'ContinueWatching' after autoplay pop-ups for this title, which means title should be removed from 'Continue Watching' after autoplay", titleStillInContinueWatching)
    }

    @Test
    fun checkIfTitleRemovedFromContinueWatchingAfterAutoplayForRegisterUser() {
        var titleStillInContinueWatching = true
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        val textOfCategory = homeScreen.getTextOfCategory(0).text
        val textOfTitle = homeScreen.getText(textOfCategory)
        Thread.sleep(2000)
        val gotItScreen = homeScreen.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        playBackScreen.seekToMiddleOfPlayback()
        uiDevice.pressBack()
        movieDatailScreen.clickOnPlay()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
        val subcategoryScreen = BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
        try {
            subcategoryScreen.longClickOnTitle(textOfTitle)
                    .clickRemoveFromHistory()
        } catch (e: TestException) {
            BaseScreen().navigateBackToHomeScreen()
                    .clickOnBrowseButton()
                    .scrollToSpecificCategory(continueWatching)
                    .removeAllTitles()
            titleStillInContinueWatching = false
        }
        assertFalse("Title: $textOfTitle still stays in 'ContinueWatching' after autoplay pop-ups for this title, which means title should be removed from 'Continue Watching' after autoplay", titleStillInContinueWatching)
    }

    @Test
    fun playTitleNavigateBack5TimesForGuest() {

        val homeScreen = HomeScreen(true)
        val gotItScreen = homeScreen.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        for (i in 1..5) {
            uiDevice.pressBack()
            movieDatailScreen.clickOnPlay()
                    .waitUntilAdsfinishes()
        }
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun playTitleNavigateBack5TimesForRegisterUser() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        Thread.sleep(2000)
        playTitleNavigateBack5TimesForGuest()
    }

    @Test
    fun clickOnPauseAndBackButton() {
        val homePage = HomeScreen(true)
        val sideCategoryScreen = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(category)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
                .clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
                .clickPlay()
                .clickOnNativeBackForMovie()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun clickOnBackButtonWhileMovieIsPlaying() {
        val homePage = HomeScreen(true)
        val sideCategoryScreen = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(category)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
                .clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
                .clickOnNativeBackForMovie()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun clickOnBackButtonWhileMovieIsPlayingAndThenSelectMovieAgain() {
        val homePage = HomeScreen(true)
        val sideCategoryScreen = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(category)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
                .clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
                .clickOnNativeBackForMovie()
                .clickOnPlay()
                .seekToTheEnd()
                .clickOnNativeBackForMovie()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun selectSerialThenMovieGuest() {
        val homePage = HomeScreen(true)
        val movieDetailPage = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(category)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
        val nameOfMovie = movieDetailPage.titleDatailScreen
        movieDetailPage.clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
        Thread.sleep(10000)
        BaseScreen().navigateBackToHomeScreen()

        val serialDetailPage = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(tvCategory)
                .clickOnTitle(0)
        val nameOfSerial = MovieDatailScreen().titleDatailScreen

        MovieDatailScreen().clickOnPlay()
                .waitUntilAdsfinishes()
                .seekToMiddleOfPlayback()
        Thread.sleep(10000)
        BaseScreen().navigateBackToHomeScreen().clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching).
                verifyIfTitlesAddedToContinueWatching(nameOfMovie,nameOfSerial)


    }


}