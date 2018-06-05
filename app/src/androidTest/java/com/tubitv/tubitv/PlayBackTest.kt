package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.HomeScreen
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/5/18.
 */
class PlayBackTest:LaunchAppWithFacebook() {

@Test
    fun timer(){
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
    launchApp(appPackage)
    val homePage2 = HomeScreen.HomeScreenWithContinueWatching()
    homePage2.removeFromHistory()
    val removeFromHistoryScreen = HomeScreen.RemoveFormHistoryScreen()
    removeFromHistoryScreen.clickOnRemoveFromHistory()
    Assert.assertNotEquals(beforeScrolling,afterScrolling)
}



}