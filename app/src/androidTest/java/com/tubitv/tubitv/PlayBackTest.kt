package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.*
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/5/18.
 */
class PlayBackTest : LaunchAppWithFacebook() {
    val tvCategory = "TV Comedies"

    @Test
    fun timerForMovie() {
        val homePage = HomeScreen(true)
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val beforeScrolling = playBackScreen.textOfRightTimer()
        playBackScreen.scrollSeekBar()
        val afterScrolling = playBackScreen.textOfRightTimer()
        killApp()
        launchApp(appPackage, false)
        val sideCategoryMenu = homePage.clickOnSidecategorButton()
        val subCategoryScreen = sideCategoryMenu.scrollToSpecificCategory("Continue Watching")
        val smallWindowAddToQueueOrHistory = subCategoryScreen.longClickOnTitle(0)
        smallWindowAddToQueueOrHistory.clickRemoveFromHistory()
        Assert.assertNotEquals(beforeScrolling, afterScrolling)
    }

    @Test
    fun timerForSerial() {
        val category = "Reality TV"
        val homePage = HomeScreen(true)
        homePage.ScrollToSpecificCategory(category)
        val serials = Serials(category)
        val moviesByCategoryScreen = serials.clickOnSerialCategory()
        val serialScreen = SerialsScreen()
        serialScreen.selectRundomSerialTitle()
        if (GotIt().gotitButton.waitForExists(shortWaitTime))
            GotIt().clickOnGotIt()
        var playBackScreen = MovieDatailScreen().clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val beforeScrolling = playBackScreen.textOfRightTimer()
        playBackScreen.scrollSeekBar()
        val afterScrolling = playBackScreen.textOfRightTimer()
        killApp()
        launchApp(appPackage, false)
        val sideCategoryMenu = homePage.clickOnSidecategorButton()
        val subCategoryScreen = sideCategoryMenu.scrollToSpecificCategory("Continue Watching")
        val smallWindowAddToQueueOrHistory = subCategoryScreen.longClickOnTitle(0)
        smallWindowAddToQueueOrHistory.clickRemoveFromHistory()
        Assert.assertNotEquals(beforeScrolling, afterScrolling)
    }

    @Test
    fun scrollForwardBackForMovies() {
        val homeScreen = HomeScreen(true)
        val textOfCategory = homeScreen.getTextOfCategory(0).text
        val textOfTitle = homeScreen.getText(textOfCategory)
        val gotItScreen = homeScreen.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        playBackScreen.seekMiddleOfPlayback()
        Thread.sleep(2000)
        playBackScreen.seekToTheBegining()
        Thread.sleep(2000)
        playBackScreen.seekToTheEnd()
        Thread.sleep(2000)
        playBackScreen.seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .longClickOnTitle(textOfTitle)
                .clickRemoveFromHistory()
    }

    @Test
    fun scrollForwardBackForSerial() {
        val homeScreen = HomeScreen(true)
        val movieDatailScreen = HomeScreen(true)
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(tvCategory)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
        val textOfTitle = movieDatailScreen.titleDatailScreen
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        playBackScreen.seekMiddleOfPlayback()
        Thread.sleep(2000)
        playBackScreen.seekToTheBegining()
        Thread.sleep(2000)
        playBackScreen.seekToTheEnd()
        Thread.sleep(2000)
        playBackScreen.seekMiddleOfPlayback()
        Thread.sleep(6000)
        playBackScreen.seekToTheEnd()
        Thread.sleep(6000)
        playBackScreen.seekToTheBegining()
        Thread.sleep(6000)
        playBackScreen.seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .longClickOnTitle(textOfTitle)
                .clickRemoveFromHistory()
    }

}