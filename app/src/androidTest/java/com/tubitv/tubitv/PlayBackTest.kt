package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.*
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 6/5/18.
 */
class PlayBackTest : LaunchAppWithFacebook() {

    @Test
    fun timerForMovie() {
        val homePage = HomeScreen()
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
        val homePage = HomeScreen()
        homePage.ScrollToSpecificCategory(category)
        val serials = HomeScreen.Serials(category)
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


    fun changeQuality() {
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        playBackScreen.clickOnQuality()

    }


}