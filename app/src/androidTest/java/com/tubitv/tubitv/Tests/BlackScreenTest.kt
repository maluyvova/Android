package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.ScreensForComparing
import com.tubitv.tubitv.Helpers.ScreenComparing
import com.tubitv.tubitv.Helpers.ScreenRecording
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.PlayBackScreen
import com.tubitv.tubitv.Screens.SubCategoryScreen
import junit.framework.Assert.assertTrue

import org.junit.Test
import java.util.*

/**
 * Created by vburian on 12/21/18.
 */
class BlackScreenTest : LaunchAppWithFacebook() {

    private val category = "Comedy"
    val tvCategory2 = "Reality TV"

    @Test
    fun playbackTestForMovieOneTime() {
        var time = ""
        val homePage = HomeScreen(true)
        val countOfMovies = homePage.clickOnSidecategorButton()
                .scrollToSpecificCategory(category)
                .countOfMovies()
        val movieDetailPage = SubCategoryScreen().clickOnTitleForQueue(Random().nextInt(countOfMovies))
                .clickOnGotIt()
        val nameOfMovie = movieDetailPage.titleDatailScreen
        val player = movieDetailPage.clickOnPlay()
        time = player.waitUntilAdsfinishes()
                .textOfRightTimer()
        ScreenRecording().statrtRecording()
        val screenComparing = ScreenComparing(nameOfMovie, ScreensForComparing.SECOND_SCREENSHOOT)
        while (!player.checkIfAutoplayExists() && !player.checkIfTitleFinished(time)) {
            time = player.waitUntilAdsfinishes()
                    .textOfRightTimer()
            assertTrue("Video is not playing For Movie: $nameOfMovie, because 1 and 2 screenshots are same on minute:$time", screenComparing.getDifferencePercent(time) > 0.5)
        }
        screenComparing.deleteFolderForTitle()
    }

    @Test
    fun playbackTestForMovieWithAutoplay2Times() {
        var autoplayPopped = 0
        var time = ""
        val homePage = HomeScreen(true)
        val countOfMovies = homePage.clickOnSidecategorButton()
                .scrollToSpecificCategory(category)
                .countOfMovies()
        val movieDetailPage = SubCategoryScreen().clickOnTitleForQueue(Random().nextInt(countOfMovies))
                .clickOnGotIt()
        val nameOfMovie = movieDetailPage.titleDatailScreen
        val player = movieDetailPage.clickOnPlay()
        time = player.waitUntilAdsfinishes()
                .textOfRightTimer()
        ScreenRecording().statrtRecording()
        val screenComparing = ScreenComparing(nameOfMovie, ScreensForComparing.SECOND_SCREENSHOOT)
        while (autoplayPopped < 2) {
            if (player.checkIfAutoplayExists()) {
                autoplayPopped++
                PlayBackScreen.AutoPlay().playTitleFromAutoplay()
            }
            time = player.waitUntilAdsfinishes()
                    .textOfRightTimer()
            assertTrue(" this test with autoplay Video is not playing For Movie: $nameOfMovie, because 1 and 2 screenshots are same on minute:$time", screenComparing.getDifferencePercent(time) > 0.5)
        }
        screenComparing.deleteFolderForTitle()
    }

    @Test
    fun playbackTestForSerialOneEpisode() {
        var time = ""
        val homePage = HomeScreen(true)
        val countOfMovies = homePage.clickOnSidecategorButton()
                .scrollToSpecificCategory(tvCategory2)
                .countOfMovies()
        val serialDetailsScreen = SubCategoryScreen().clickOnTitleForQueue(Random().nextInt(countOfMovies))
                .clickOnGotIt()
        val nameOfSerial = serialDetailsScreen.titleDatailScreen
        val player = serialDetailsScreen.clickOnPlay()
        time = player.waitUntilAdsfinishes()
                .textOfRightTimer()
        ScreenRecording().statrtRecording()
        val screenComparing = ScreenComparing(nameOfSerial, ScreensForComparing.SECOND_SCREENSHOOT)
        while (!player.checkIfAutoplayExists() && !player.checkIfTitleFinished(time)) {
            time = player.waitUntilAdsfinishes()
                    .textOfRightTimer()
            assertTrue("Video is not playing For serial: $nameOfSerial, because 1 and 2 screenshots are same on minute:$time", screenComparing.getDifferencePercent(time) > 0.5)
        }
        screenComparing.deleteFolderForTitle()
    }

//    @After
//    fun deleteScreens(){
//        ScreenComparing("","",ScreensForComparing.BLACK_SCREEN).deleteScreenShoots()
//    }

}
