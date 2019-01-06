package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.ScreensForComparing
import com.tubitv.tubitv.Helpers.ScreenComparing
import com.tubitv.tubitv.Helpers.ScreenRecording
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.HomeScreen
import junit.framework.Assert.assertTrue

import org.junit.Test

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
        val movieDetailPage = homePage.clickOnSidecategorButton()
                .scrollToSpecificCategory(category)
                .clickOnTitleForQueue(0)
                .clickOnGotIt()
        val nameOfMovie = movieDetailPage.titleDatailScreen
        val player = movieDetailPage.clickOnPlay()
        time = player.waitUntilAdsfinishes()
                .textOfRightTimer()
        ScreenRecording().statrtRecording()
        val screenComparing=ScreenComparing(nameOfMovie,ScreensForComparing.SECOND_SCREENSHOOT)
        while (!player.checkIfAutoplayExists() && !player.checkIfTitleFinished(time)) {
            time = player.waitUntilAdsfinishes()
                    .textOfRightTimer()
            assertTrue("Video is not playing, because 1 and 2 screenshots are same",screenComparing.getDifferencePercent(time)>0.5)
            firstTime = false
        }
        screenComparing.deleteFolderForTitle()
    }
//    @After
//    fun deleteScreens(){
//        ScreenComparing("","",ScreensForComparing.BLACK_SCREEN).deleteScreenShoots()
//    }

}