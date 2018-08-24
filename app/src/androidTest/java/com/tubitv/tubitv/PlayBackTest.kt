package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.*
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by vburian on 6/5/18.
 */
class PlayBackTest:LaunchAppWithFacebook() {

@Test
    fun timerForMovie(){
    val homePage = HomeScreen()
    val gotItScreen = homePage.clickOnTitle(0)
    val movieDatailScreen = gotItScreen.clickOnGotIt()
    movieDatailScreen.dontSelectHuluTitle()
    var playBackScreen = movieDatailScreen.clickOnPlay()
    playBackScreen.waitUntilAdsfinishes()
    val beforeScrolling=playBackScreen.textOfRightTimer()
    playBackScreen.scrollSeekBar()
    val afterScrolling=playBackScreen.textOfRightTimer()
    killApp()
    launchApp(appPackage,false)
    val homePage2 = HomeScreen.HomeScreenWithContinueWatching()
    homePage2.removeFromHistory()
    val removeFromHistoryScreen = HomeScreen.RemoveFormHistoryScreen()
    removeFromHistoryScreen.clickOnRemoveFromHistory()
    Assert.assertNotEquals(beforeScrolling,afterScrolling)
}
    @Test
    fun timerForSerial(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory("Most Popular TV Shows")
        val serials =HomeScreen.Serials()
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        val serialScreen= SerialsScreen()
        serialScreen.selectRundomSerialTitle()
        if(GotIt().gotitButton.waitForExists(globalTimeout))
        GotIt().clickOnGotIt()
        var playBackScreen = MovieDatailScreen().clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val beforeScrolling=playBackScreen.textOfRightTimer()
        playBackScreen.scrollSeekBar()
        val afterScrolling=playBackScreen.textOfRightTimer()
        killApp()
        launchApp(appPackage,false)
        val homePage2 = HomeScreen.HomeScreenWithContinueWatching()
        homePage2.removeFromHistory()
        val removeFromHistoryScreen = HomeScreen.RemoveFormHistoryScreen()
        removeFromHistoryScreen.clickOnRemoveFromHistory()
        Assert.assertNotEquals(beforeScrolling,afterScrolling) }

    @Test
    fun changeQuality(){
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        playBackScreen.clickOnQuality()

    }





}