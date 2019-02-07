package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.DirectionOfScrolling
import com.tubitv.tubitv.Enomus.TypeOfContent
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.*
import com.tubitv.tubitv.continueWatching
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert.*
import org.junit.Assert
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.regex.Pattern



/**
 * Created by vburian on 7/4/18.
 */
class AutoPlayTest : LaunchAppWithFacebook() {//SimpleLaunchApp() {
//Change this for Facebook

    val tvCategory = "Reality TV"
    val categoryForSideMenu = "Horror"
    var fistTime = true
    var p = Pattern.compile("-?\\d+")

    @Test
    fun selectNextTitleForAutoplayMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle = playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        var nameOfTitleFromAutoplay = autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle = playBackScreen.getNameOfTitleFromPlayback()
        if(fistTime){
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()}
        assertEquals("Text between title on autoplay and text from playback is different", nameOfTitleFromAutoplay, nextTitle)
        assertNotEquals("Same title is playing after autoplay", selectedTitle, nextTitle)
    }

    @Test
    fun selectNextTitleThreeTimesMovies() {
        fistTime=false
        selectNextTitleForAutoplayMovies()
        val playBackScreen = PlayBackScreen()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle = playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        var nameOfTitleFromAutoplay = autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle = playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("Text between title on autoplay and text from playback is different", nameOfTitleFromAutoplay, nextTitle)
        Assert.assertNotEquals("Same title is playing after autoplay", selectedTitle, nextTitle)
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitles = playBackScreen.getNameOfTitleFromPlayback()
        playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        var nameOfTitleFromAutoplays = autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitles = playBackScreen.getNameOfTitleFromPlayback()
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
        assertEquals("Text between title on autoplay and text from playback is different", nameOfTitleFromAutoplay, nextTitle)
        assertNotEquals("Same title is playing after autoplay", selectedTitle, nextTitle)
    }

    @Test
    fun selectNextNextTitleInAutoplayMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        var nameOfTitleFromAutoplay = autoplayScreen.textFromFirstTitleAutoplay
        var textOfNextTitleInAutoplay = autoplayScreen.textFromNextTitleAutoplay
        autoplayScreen.slideToNextTitle(true)
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle = playBackScreen.getNameOfTitleFromPlayback()
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
        assertNotEquals("Next tile in Autoplay same like a first one", nameOfTitleFromAutoplay, textOfNextTitleInAutoplay)
        assertEquals("Incorrect title is playing after autoplay", nextTitle, textOfNextTitleInAutoplay)
    }

    @Test
    fun hideAutoplayMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        autoplayScreen.hideAutoplay()
        val titleFromLeftCorner=autoplayScreen.titleOnLefSideWhenAutoplayIsHodens.waitForExists(globalTimeout)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
        assertTrue("I click on tagle in Autoplay but title in left corner didn't appear",titleFromLeftCorner)
    }

    @Test
    fun hideAutoplayAndSelectTitleFromLowerLeftCornerMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        var nameOfTitleFromAutoplay = autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.hideAutoplay()
        autoplayScreen.selectTitleNextTitleFromHiddenAutoplay()
        playBackScreen.waitUntilAdsfinishes()
        var nameOfTitle = playBackScreen.getNameOfTitleFromPlayback()
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
        assertEquals("I hide autoplay, then select title from hidden autoplay", nameOfTitle, nameOfTitleFromAutoplay)
    }

    @Test
    fun wait20SecForNextTitleMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        val titleShouldPlay = autoplayScreen.textFromFirstTitleAutoplay
        if (autoplayScreen.wait20secForNextTitle()) {
            playBackScreen.waitUntilAdsfinishes()
            Assert.assertEquals("After 20 sec waiting for next title from autoplay, for some reason titles don't match", titleShouldPlay, playBackScreen.getNameOfTitleFromPlayback())
        } else Assert.assertEquals("Next title didn't play after 20 sec waiting for next title", 2, 5)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun scrollToNextTitleAndVerifyIfTimerDisaperdMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        autoplayScreen.slideToNextTitle(true)
        autoplayScreen.slideToPrevoisTitle()
        assertFalse("I scroll to the side and 20 sec timer still exists ", autoplayScreen.verifyThatTheTimerIsNotExists())
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun scrollToLastTitleInAutoplayMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        autoplayScreen.slideToNextTitle(false)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun hideAutoplayAndScrollToBeginingMovies() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = movieDatailScreen.clickOnPlay()
        playBackScreen.waitUntilAdsfinishes()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        autoplayScreen.hideAutoplay()
        val firstTime = playBackScreen.textOfRightTimer()
        playBackScreen.scrollSeekBar()
        val secondTime = playBackScreen.textOfRightTimer()
        Assert.assertNotEquals("I hide autoplay and seek to another moment with scroll bar", firstTime, secondTime)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    @Test
    fun hideAutoplayAndCheckIfTimerIsStoppedWhenAutoplayIsHiddenForMovie() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle = playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        val timer = autoplayScreen.textFromAutoplayTimer.replace("[^-?0-9]+".toRegex(), "").toInt()
        //val timer = autoplayScreen.textFromAutoplayTimer.substring(12, 14).toInt()
        autoplayScreen.hideAutoplay()
        Thread.sleep(15000)
        autoplayScreen.hideAutoplay()
        val timerAfterWiat = autoplayScreen.textFromAutoplayTimer.replace("[^-?0-9]+".toRegex(), "").toInt()
        Assert.assertTrue("", timerAfterWiat - timer <= 4)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    @Test
    fun seekToAutoplayAndThenClickOnOverviewAndThenNavigateBackToApp() {
        val homePage = HomeScreen(true)
        val sideCategory = homePage.clickOnBrowseButton()
        val subCaregoryScreen = sideCategory.scrollToSpecificCategory(categoryForSideMenu)
        val gotItScreen = subCaregoryScreen.clickOnTitleForQueue(1)
        val movieDatailScreen = gotItScreen.clickOnGotIt()
        movieDatailScreen.dontSelectHuluTitle()
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle = playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.MOVIES)
        val timer = autoplayScreen.textFromAutoplayTimer.replace("[^-?0-9]+".toRegex(), "").toInt()
        minimizeAndOpenAppFromSameScreen()
        PlayBackScreen.AutoPlay()
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    @Test
    fun selectNextEpisodeForSerial() {
        val homePage = HomeScreen(true)
        val category = homePage.scrollToSpecificCategory(tvCategory, DirectionOfScrolling.DOWN)
        val serials = Serials(category)
        val moviesByCategoryScreen = serials.clickOnSerialCategory()
        val serialScreen = SerialsScreen()
        serialScreen.selectRundomSerialTitle(category)
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle = playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.SERIALS)
        var nameOfTitleFromAutoplay = autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.playTitleFromAutoplay()
        val nextTitle = playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("Text between title on autoplay and text from playback is different for serials", nameOfTitleFromAutoplay, nextTitle)
        Assert.assertNotEquals("Same title is playing after autoplay for serials", selectedTitle, nextTitle)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun hideAutoplayAndSelectTitleFromLowerLeftCornerForSerials() {
        val homePage = HomeScreen(true)
        val category = homePage.scrollToSpecificCategory(tvCategory, DirectionOfScrolling.DOWN)
        val serials = Serials(category)
        val moviesByCategoryScreen = serials.clickOnSerialCategory()
        val serialScreen = SerialsScreen()
        serialScreen.selectRundomSerialTitle(category)
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle = playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.SERIALS)
        var nameOfTitleFromAutoplay = autoplayScreen.textFromFirstTitleAutoplay
        autoplayScreen.hideAutoplay()
        autoplayScreen.selectTitleNextTitleFromHiddenAutoplay()
        playBackScreen.waitUntilAdsfinishes()
        var nameOfTitle = playBackScreen.getNameOfTitleFromPlayback()
        Assert.assertEquals("I hide autoplay, then select title from hidden autoplay for Serials", nameOfTitle, nameOfTitleFromAutoplay)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun hideAutoplayAndCheckIfTimerIsStoppedWhenAutoplayIsHiddenForSerial() {
        val homePage = HomeScreen(true)
        val category = homePage.scrollToSpecificCategory(tvCategory, DirectionOfScrolling.DOWN)
        val serials = Serials(category)
        val moviesByCategoryScreen = serials.clickOnSerialCategory()
        val serialScreen = SerialsScreen()
        serialScreen.selectRundomSerialTitle(tvCategory)
        var playBackScreen = SerialsScreen().clickOnPlayButton()
        playBackScreen.waitUntilAdsfinishes()
        val selectedTitle = playBackScreen.getNameOfTitleFromPlayback()
        val autoplayScreen = playBackScreen.seekToAutoPlay(TypeOfContent.SERIALS)
        val timer = autoplayScreen.textFromAutoplayTimer.replace("[^-?0-9]+".toRegex(), "").toInt()
        autoplayScreen.hideAutoplay()
        Thread.sleep(15000)
        autoplayScreen.hideAutoplay()
        val timerAfterWiat = autoplayScreen.textFromAutoplayTimer.replace("[^-?0-9]+".toRegex(), "").toInt()
        Assert.assertTrue("", timerAfterWiat - timer <= 4)
        BaseScreen().navigateBackToHomeScreen()
        HomeScreen(true).clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


}