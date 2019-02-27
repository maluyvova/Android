package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.ScreensForComparing
import com.tubitv.tubitv.Helpers.ScreenComparing
import com.tubitv.tubitv.Helpers.ScreenRecording
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.PlayBackScreen
import com.tubitv.tubitv.Screens.SubCategoryScreen
import junit.framework.Assert.assertTrue
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

/**
 * Created by vburian on 12/21/18.
 */
@RunWith(Parameterized::class)
public class BlackScreenTest(val paramOne: Int, val paramTwo: String) : LaunchAppWithFacebook() {

    var screenRecordingStarted = false
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Array<Array<Any>> {
            return Array(2) { arrayOf(2, "") }
        }
    }

    private val category = "Comedy"
    val tvCategory2 = "Reality TV"

    @Test
    fun playbackTestForMovieOneTime() {
        var time = ""
        val homePage = HomeScreen(true)
        val countOfMovies = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(category)
                .countOfMovies()
        val movieDetailPage = SubCategoryScreen(category).clickOnTitleForQueue(Random().nextInt(countOfMovies))
                .clickOnGotIt()
        val nameOfMovie = movieDetailPage.titleName
        val player = movieDetailPage.clickOnPlay()
        time = player.waitUntilAdsfinishes()
                .textOfLeftTimer()
        //ScreenRecording().statrtRecording()
        val screenComparing = ScreenComparing(nameOfMovie, ScreensForComparing.SECOND_SCREENSHOOT)
        while (!player.checkIfAutoplayExists() && !player.checkIfTitleFinished(time)) {
            time = player.waitUntilAdsfinishes()
                    .textOfLeftTimer()
            assertTrue("Video is not playing For Movie: $nameOfMovie, because 1 and 2 screenshots are same on minute:$time", screenComparing.getDifferencePercent(time) > 0.5)
            val nameOfFolderForRecords = screenComparing.getFolderForRecords()
            if (!screenRecordingStarted) {
                ScreenRecording().statrtRecording(nameOfFolderForRecords)
                movieDetailPage.clickOnPlay()
                screenRecordingStarted = true
            }
        }
        screenComparing.deleteFolderForTitle()
    }

    @Test
    fun playbackTestForMovieWithAutoplay2Times() {
        var autoplayPopped = 0
        var time = ""
        val homePage = HomeScreen(true)
        val countOfMovies = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(category)
                .countOfMovies()
        val movieDetailPage = SubCategoryScreen(category).clickOnTitleForQueue(Random().nextInt(countOfMovies))
                .clickOnGotIt()
        val player = movieDetailPage.clickOnPlay()
        var nameOfMovie = player.getNameOfTitleFromPlayback()
        time = player.waitUntilAdsfinishes()
                .textOfLeftTimer()
        var screenComparing = ScreenComparing(nameOfMovie, ScreensForComparing.SECOND_SCREENSHOOT)
        while (autoplayPopped < 2) {

            if (time.get(0).toString().toIntOrNull() != null) {
                if (time.get(0).toInt() > 3) {
                    if (player.checkIfAutoplayExists()) {
                        autoplayPopped++
                        PlayBackScreen.AutoPlay().playTitleFromAutoplay()
                        var nameOfMovie = player.getNameOfTitleFromPlayback()
                        screenComparing = ScreenComparing(nameOfMovie, ScreensForComparing.SECOND_SCREENSHOOT)
                    }
                }
            } else {
                if (player.checkIfAutoplayExists()) {
                    autoplayPopped++
                    PlayBackScreen.AutoPlay().playTitleFromAutoplay()
                    var nameOfMovie = player.getNameOfTitleFromPlayback()
                    screenComparing = ScreenComparing(nameOfMovie, ScreensForComparing.SECOND_SCREENSHOOT)
                }
            }
            time = player.waitUntilAdsfinishes()
                    .textOfLeftTimer()

            assertTrue(" this test with autoplay Video is not playing For Movie: $nameOfMovie, because 1 and 2 screenshots are same on minute:$time", screenComparing.getDifferencePercent(time) > 0.5)
            val nameOfFolderForRecords = screenComparing.getFolderForRecords()
            if (!screenRecordingStarted) {
                ScreenRecording().statrtRecording(nameOfFolderForRecords)
                movieDetailPage.clickOnPlay()
                screenRecordingStarted = true
            }
        }
        screenComparing.deleteFolderForTitle()
    }

    @Test
    fun playbackTestForSerialOneEpisode() {
        var time = ""
        val homePage = HomeScreen(true)
        val countOfMovies = homePage.clickOnBrowseButton()
                .scrollToSpecificCategory(tvCategory2)
                .countOfMovies()
        val serialDetailsScreen = SubCategoryScreen(category).clickOnTitleForQueue(Random().nextInt(countOfMovies))
                .clickOnGotIt()
        val nameOfSerial = serialDetailsScreen.titleName
        val player = serialDetailsScreen.clickOnPlay()
        time = player.waitUntilAdsfinishes()
                .textOfLeftTimer()
        //ScreenRecording().statrtRecording()
        val screenComparing = ScreenComparing(nameOfSerial, ScreensForComparing.SECOND_SCREENSHOOT)
        while (!player.checkIfAutoplayExists() && !player.checkIfTitleFinished(time)) {
            time = player.waitUntilAdsfinishes()
                    .textOfLeftTimer()
            assertTrue("Video is not playing For serial: $nameOfSerial, because 1 and 2 screenshots are same on minute:$time", screenComparing.getDifferencePercent(time) > 0.5)
            val nameOfFolderForRecords = screenComparing.getFolderForRecords()
            if (!screenRecordingStarted) {
                ScreenRecording().statrtRecording(nameOfFolderForRecords)
                serialDetailsScreen.clickOnPlay()
                screenRecordingStarted = true
            }
        }
        screenComparing.deleteFolderForTitle()
    }

    @After
    fun stopRecording() {
        ScreenRecording().stopRecording()
    }

}
