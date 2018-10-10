package com.tubitv.tubitv

import com.tubitv.tubitv.Screens.*
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 7/4/18.
 */
class AutoPlayTest:LaunchAppWithFacebook(){//SimpleLaunchApp() {
//Change this for Facebook

    val tvCategory="Reality TV"

    @Test
    fun selectNextTitleForAutoplayMovies(){
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle=playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
        var nameOfTitleFromAutoplay= autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle=playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("Text between title on autoplay and text from playback is different",nameOfTitleFromAutoplay,nextTitle)
        Assert.assertNotEquals("Same title is playing after autoplay",selectedTitle,nextTitle)
    }

    @Test
    fun selectNextTitleThreeTimesMovies(){
        selectNextTitleForAutoplayMovies()
        val playBackScreen=PlayBackScreen()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle=playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
        var nameOfTitleFromAutoplay= autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle=playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("Text between title on autoplay and text from playback is different",nameOfTitleFromAutoplay,nextTitle)
        Assert.assertNotEquals("Same title is playing after autoplay",selectedTitle,nextTitle)
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitles=playBackScreen.getNameOfTitleFromPlayback()
        playBackScreen.seekToAutoPlay("Movie")
        var nameOfTitleFromAutoplays= autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitles=playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("Text between title on autoplay and text from playback is different",nameOfTitleFromAutoplay,nextTitle)
        Assert.assertNotEquals("Same title is playing after autoplay",selectedTitle,nextTitle) }

        @Test
    fun selectNextNextTitleInAutoplayMovies(){
      //  val signInScreen= LaunchScreen().clickOnSignIn()
      //  signInScreen.sendTextToEmailField("vvburian@tubi.tv")
      //  signInScreen.sendTextToPasswordField("tubitv")
      //  signInScreen.clickOnSignInButton()
        val homePage = HomeScreen()
      //  castings()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
        var nameOfTitleFromAutoplay= autoplayScreen.textFromFirstTitleAutoplay
        var textOfNextTitleInAutoplay= autoplayScreen.textFromNextTitleAutoplay
        autoplayScreen.slideToNextTitle(true)
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle=playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertNotEquals("Next tile in Autoplay same like a first one",nameOfTitleFromAutoplay,textOfNextTitleInAutoplay)
        Assert.assertEquals("Incorrect title is playing after autoplay",nextTitle,textOfNextTitleInAutoplay) }

      @Test
     fun hideAutoplayMovies(){
      //    val signInScreen= LaunchScreen().clickOnSignIn()
       //   signInScreen.sendTextToEmailField("vvburian@tubi.tv")
      //    signInScreen.sendTextToPasswordField("tubitv")
       //   signInScreen.clickOnSignInButton()
          val homePage = HomeScreen()
         // castings()
          val gotItScreen = homePage.clickOnTitle(0)
          val movieDatailScreen = gotItScreen.clickOnGotIt()
          movieDatailScreen.dontSelectHuluTitle()
          var playBackScreen = movieDatailScreen.clickOnPlay()
          playBackScreen.waitUntilAdsfinishes()
          val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
          autoplayScreen.hideAutoplay()
          Assert.assertTrue("I clck on tagle in Autoplay but title in left corner didn't appear",autoplayScreen.titleOnLefSideWhenAutoplayIsHodens.waitForExists(globalTimeout)) }

    @Test
      fun hideAutoplayAndSelectTitleFromLowerLeftCornerMovies(){
      //    val signInScreen= LaunchScreen().clickOnSignIn()
      //    signInScreen.sendTextToEmailField("vvburian@tubi.tv")
      //    signInScreen.sendTextToPasswordField("tubitv")
      //    signInScreen.clickOnSignInButton()
          val homePage = HomeScreen()
         // castings()
          val gotItScreen = homePage.clickOnTitle(0)
          val movieDatailScreen = gotItScreen.clickOnGotIt()
          movieDatailScreen.dontSelectHuluTitle()
          var playBackScreen = movieDatailScreen.clickOnPlay()
          playBackScreen.waitUntilAdsfinishes()
          val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
          var nameOfTitleFromAutoplay= autoplayScreen.textFromFirstTitleAutoplay
          autoplayScreen.hideAutoplay()
          autoplayScreen.selectTitleNextTitleFromHiddenAutoplay()
          playBackScreen.waitUntilAdsfinishes()
          var nameOfTitle=playBackScreen.getNameOfTitleFromPlayback()
          Assert.assertEquals("I hide autoplay, then select title from hidden autoplay",nameOfTitle,nameOfTitleFromAutoplay)
      }

    @Test
    fun wait20SecForNextTitleMovies(){
        val homePage = HomeScreen()
       // castings()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
        val titleShouldPlay=autoplayScreen.textFromFirstTitleAutoplay
        if (autoplayScreen.wait20secForNextTitle()){
            playBackScreen.waitUntilAdsfinishes()
           Assert.assertEquals("After 20 sec waiting for next title from autoplay, for some reason titles don't match",titleShouldPlay,playBackScreen.getNameOfTitleFromPlayback())
        }
        else Assert.assertEquals("Next title didn't play after 20 sec waiting for next title",2,5)
    }

    @Test
    fun scrollToNextTitleAndVerifyIfTimerDisaperdMovies(){
        val homePage = HomeScreen()
      //  castings()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
        autoplayScreen.slideToNextTitle(true)
        autoplayScreen.slideToPrevoisTitle()
        Assert.assertFalse("I slide on side and 20 sec timer still exists ",autoplayScreen.verifyThatTheTimerIsNotExists())
    }
    @Test
    fun scrollToLastTitleInAutoplayMovies(){
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
        autoplayScreen.slideToNextTitle(false)
    }
    @Test
    fun hideAutoplayAndScrollToBeginingMovies(){
        val homePage = HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen=playBackScreen.seekToAutoPlay("Movie")
        autoplayScreen.hideAutoplay()
        val firstTime=playBackScreen.textOfRightTimer()
        playBackScreen.scrollSeekBar()
        val secondTime=playBackScreen.textOfRightTimer()
        Assert.assertNotEquals("I hide autoplay and seek to another moment with scroll bar",firstTime,secondTime)}


    @Test
    fun hideAutoplayAndCheckIfTimerIsStoppedWhenAutoplayIsHiddenForMovie(){
        val homePage=HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle=playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen= playBackScreen.seekToAutoPlay("Movie")
        val timer = autoplayScreen.textFromAutoplayTimer.substring(12,14).toInt()
        autoplayScreen.hideAutoplay()
        Thread.sleep(15000)
        autoplayScreen.hideAutoplay()
        val timerAfterWiat=autoplayScreen.textFromAutoplayTimer.substring(12,14).toInt()
        Assert.assertTrue("",timerAfterWiat-timer<=4) }


    @Test
    fun seekToAutoplayAndThenClickOnOverviewAndThenNavigateBackToApp(){
        val homePage=HomeScreen()
        val gotItScreen = homePage.clickOnTitle(0)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle=playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen= playBackScreen.seekToAutoPlay("Movie")
        val timer = autoplayScreen.textFromAutoplayTimer.substring(12,14).toInt()
        minimizeAndOpenAppFromSameScreen(checkConfigForDevce())
        PlayBackScreen.AutoPlay() }















    @Test
    fun selectNextEpisodeForSerial(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory(tvCategory)
        val serials =HomeScreen.Serials(tvCategory)
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        val serialScreen=SerialsScreen()
        serialScreen.selectRundomSerialTitle()
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle=playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen= playBackScreen.seekToAutoPlay("Serial")
        var nameOfTitleFromAutoplay= autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle=playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("Text between title on autoplay and text from playback is different for serials",nameOfTitleFromAutoplay,nextTitle)
        Assert.assertNotEquals("Same title is playing after autoplay for serials",selectedTitle,nextTitle)
    }

    @Test
    fun hideAutoplayAndSelectTitleFromLowerLeftCornerForSerials(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory(tvCategory)
        val serials =HomeScreen.Serials(tvCategory)
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        val serialScreen=SerialsScreen()
        serialScreen.selectRundomSerialTitle()
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle=playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen= playBackScreen.seekToAutoPlay("Serial")
        var nameOfTitleFromAutoplay= autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.hideAutoplay()
        autoplayScreen.selectTitleNextTitleFromHiddenAutoplay()
        playBackScreen.waitUntilAdsfinishes()
        var nameOfTitle=playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("I hide autoplay, then select title from hidden autoplay for Serials",nameOfTitle,nameOfTitleFromAutoplay) }

    @Test
    fun hideAutoplayAndCheckIfTimerIsStoppedWhenAutoplayIsHiddenForSerial(){
        val homePage=HomeScreen()
        homePage.ScrollToSpecificCategory(tvCategory)
        val serials =HomeScreen.Serials(tvCategory)
        val moviesByCategoryScreen=serials.clickOnSerialCategory()
        val serialScreen=SerialsScreen()
        serialScreen.selectRundomSerialTitle()
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle=playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen= playBackScreen.seekToAutoPlay("Serial")
        val timer = autoplayScreen.textFromAutoplayTimer.substring(12,14).toInt()
        autoplayScreen.hideAutoplay()
        Thread.sleep(15000)
        autoplayScreen.hideAutoplay()
        val timerAfterWiat=autoplayScreen.textFromAutoplayTimer.substring(12,14).toInt()
        Assert.assertTrue("",timerAfterWiat-timer<=4)
    }



}