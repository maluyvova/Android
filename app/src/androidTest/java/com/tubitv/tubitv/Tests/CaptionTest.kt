package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.*
import org.junit.Assert

/**
 * Created by vburian on 5/31/18.
 */
class CaptionTest : LaunchAppWithFacebook() {


    // @Test
//    fun turnOnGlobalCaption() {
//        val homepage = HomeScreen(true)
//        val smalSettingScreen = homepage.clickOnThreeDotsSetings()
//        val settingsScreen = smalSettingScreen.clickOnSettings()
//        settingsScreen.clickOnCaption()
//        uiDevice.pressBack()
//        homepage.clickOnThreeDots()
//        val moviByCategoryScreen = MoviesByCategoryScreen("")
//        val countOftitles = moviByCategoryScreen.getCountOfTitles()
//        val randomTitle = moviByCategoryScreen.gotkRandomTite(6)
//        randomTitle.click()
//        val movieDatailScreen = GotIt().clickOnGotIt()
//        for (i in 0..countOftitles) {
//            if (movieDatailScreen.checkIfCCIconpresent()) {
//                break
//            } else {
//                uiDevice.pressBack()
//                moviByCategoryScreen.gotkRandomTite(6).click()
//            }
//        }
//        val plaback = movieDatailScreen.clickOnPlay()
//        val isSelectedSubtitles = plaback.checkIfSubtitlesIsSelected()
//        // Assert.assertTrue(isSelectedSubtitles)
//        Assert.assertEquals(isSelectedSubtitles, "vova")
//    }
//
}