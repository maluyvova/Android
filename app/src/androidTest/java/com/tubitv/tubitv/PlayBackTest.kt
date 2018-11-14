package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.*
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/5/18.
 */
class PlayBackTest : LaunchAppWithFacebook() {

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



}