package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.ScreensForComparing
import com.tubitv.tubitv.Helpers.ScreenComparing
import com.tubitv.tubitv.Helpers.ScreenRecording
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.SubCategoryScreen
import junit.framework.Assert.assertTrue

import org.junit.Test
import java.util.*

/**
 * Created by vburian on 12/21/18.
 */
class BlackScreenTest : LaunchAppWithFacebook() {

    private val category = "Comedy"

    @Test
    fun some() {
        var firstTime = true
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
            assertTrue("Video is not playing, because 1 and 2 screenshots are same on minute:$time", screenComparing.getDifferencePercent(time) > 0.5)
            firstTime = false
        }
        screenComparing.deleteFolderForTitle()
    }

//    @After
//    fun deleteScreens(){
//        ScreenComparing("","",ScreensForComparing.BLACK_SCREEN).deleteScreenShoots()
//    }

}
