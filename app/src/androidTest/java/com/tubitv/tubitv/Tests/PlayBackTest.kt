package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.Categories
import com.tubitv.tubitv.Enomus.DirectionOfScrolling
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.*
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.shortWaitTime
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
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val beforeScrolling = playBackScreen.textOfRightTimer()
        playBackScreen.scrollSeekBar()
        val afterScrolling = playBackScreen.textOfRightTimer()
        killApp()
        launchApp(appPackage, false)
        val sideCategoryMenu = homePage.clickOnBrowseButton()
        val subCategoryScreen = sideCategoryMenu.scrollToSpecificCategory("Continue Watching")
        val smallWindowAddToQueueOrHistory = subCategoryScreen.longClickOnTitle(0)
        smallWindowAddToQueueOrHistory.clickRemoveFromHistory()
        Assert.assertNotEquals(beforeScrolling, afterScrolling)
    }

    @Test
    fun timerForSerial() {
        val category = Categories.REALITY_TV.value
        val homePage = HomeScreen(true)
        homePage.scrollToSpecificCategory(category, DirectionOfScrolling.DOWN)
        val serials = Serials(category)
        val moviesByCategoryScreen = serials.clickOnSerialCategory()
        val serialScreen = SerialsScreen()
        serialScreen.selectRundomSerialTitle(category)
        if (GotIt().gotitButton.waitForExists(shortWaitTime))
            GotIt().clickOnGotIt()
        var playBackScreen = MovieDatailScreen().clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val beforeScrolling = playBackScreen.textOfRightTimer()
        playBackScreen.scrollSeekBar()
        val afterScrolling = playBackScreen.textOfRightTimer()
        killApp()
        launchApp(appPackage, false)
        val sideCategoryMenu = homePage.clickOnBrowseButton()
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
        playBackScreen.seekToMiddleOfPlayback()
        Thread.sleep(2000)
        playBackScreen.seekToTheBegining()
        Thread.sleep(2000)
        playBackScreen.seekToTheEnd()
        Thread.sleep(2000)
        playBackScreen.seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .longClickOnTitle(textOfTitle)
                .clickRemoveFromHistory()
    }

    @Test
    fun scrollForwardBackForSerial() {
        val homeScreen = HomeScreen(true)
        val movieDatailScreen = HomeScreen(true)
                .clickOnBrowseButton()
                .scrollToSpecificCategory(tvCategory)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
        val textOfTitle = movieDatailScreen.titleName
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        playBackScreen.seekToMiddleOfPlayback()
        Thread.sleep(2000)
        playBackScreen.seekToTheBegining()
        Thread.sleep(2000)
        playBackScreen.seekToTheEnd()
        Thread.sleep(2000)
        playBackScreen.seekToMiddleOfPlayback()
        Thread.sleep(6000)
        playBackScreen.seekToTheEnd()
        Thread.sleep(6000)
        playBackScreen.seekToTheBegining()
        Thread.sleep(6000)
        playBackScreen.seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
        homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
                .longClickOnTitle(textOfTitle)
                .clickRemoveFromHistory()
    }

}