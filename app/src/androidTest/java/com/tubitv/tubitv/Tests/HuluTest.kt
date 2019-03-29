package com.tubitv.tubitv.Tests

import com.tubitv.tubitv.Enomus.Categories
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.Screens.BaseScreen
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.Screens.HuluPlaybackScreen
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/6/18.
 */
class HuluTest : LaunchAppWithFacebook() {
    val textWhatTestIsLookingFor = "Beverly Hills 90210"


    @Test
    fun huluTitleWithYearsLimitationsErrorMessageValidation() {
        var warningMessage = ""
        var warningMeassageAfterClose = ""
        val homeScreen = HomeScreen(true)
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = searchSreen.provideTextToSearch("Evil Bong 777")
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName.toLowerCase()
        if (textOfTitle.equals("Evil Bong 777".toLowerCase())) {
            titleDatailScreen.clickOnPlay()
            val huluPlaybackScreen = HuluPlaybackScreen()
            if (huluPlaybackScreen.waitForAgeLimitation()) {
                huluPlaybackScreen.clickOnMonth()
                huluPlaybackScreen.selectMonth()
                huluPlaybackScreen.clickOnDay()
                huluPlaybackScreen.selectDay()
                huluPlaybackScreen.clickOnYear()
                huluPlaybackScreen.selectYear()
                huluPlaybackScreen.clickOnSubmit()
                warningMessage = huluPlaybackScreen.getextFromWorningMessage()
                minimizeAndOpenAppFromSameScreen()
                warningMeassageAfterClose = huluPlaybackScreen.getextFromWorningMessage()
            } else throw
            TestException("Fields for age limitation didn't appear ")
        } else throw TestException("Hulu tile ($textWhatTestIsLookingFor) with age limitation expired ")

        Assert.assertEquals("Text for warning message mismatch for " +
                "age limitation for hulu titles", warningMessage, "We're sorry, but the video you have requested is restricted to users 18 years and older. Your parents will thank us. Don't worry, though. We have plenty of other stuff to watch.")
        Assert.assertEquals("Text for warning message after minimize app is not the same", warningMeassageAfterClose, warningMessage)
    }


    @Test
    fun huluTitleWithYearsLimitations() {
        val huluPlaybackScreen = HuluPlaybackScreen()
        val homeScreen = HomeScreen(true)
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = searchSreen.provideTextToSearch("Evil Bong 777")
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName.toLowerCase()
        if (textOfTitle.equals("Evil Bong 777".toLowerCase())) {
            titleDatailScreen.clickOnPlay()
            if (huluPlaybackScreen.waitForAgeLimitation()) {
                huluPlaybackScreen.clickOnMonth()
                huluPlaybackScreen.selectMonth()
                huluPlaybackScreen.clickOnDay()
                huluPlaybackScreen.selectDay()
                huluPlaybackScreen.clickOnYear()
                huluPlaybackScreen.scrollYaers()
                huluPlaybackScreen.selectYear()
                huluPlaybackScreen.clickOnSubmit()
                huluPlaybackScreen.waitUntilAddsGone()
                uiDevice.click(97, 1030)
            }
        }
        Assert.assertTrue(huluPlaybackScreen.verifyIfPlaybackOpenened())
    }

    @Test
    fun huluTitleAddedToHistory() {
        val homeScreen = HomeScreen(true)
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = searchSreen.provideTextToSearch(textWhatTestIsLookingFor)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val titleFromSerch = titleDatailScreen.titleName
        val playBackScreen = titleDatailScreen.clickOnPlay()
        Thread.sleep(100000)
        BaseScreen().navigateBackToHomeScreen()
        val subCategory = homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
        val titleFromContinueWatching = subCategory.clickOnTitle(titleFromSerch)
                .titleName
        uiDevice.pressBack()
        subCategory.longClickOnTitle(titleFromContinueWatching)
                .clickRemoveFromHistory()

        assertEquals("Hulu title is not added to continueWatching after watching ", titleFromSerch, titleFromContinueWatching)
    }

    @Test
    fun huluTitleAddedToHistoryPickerDidntPopUp() {
        val huluPlaybackScreen = HuluPlaybackScreen()
        val homeScreen = HomeScreen(true)
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = searchSreen.provideTextToSearch("Evil Bong 777")
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val textOfTitle = titleDatailScreen.titleName.toLowerCase()
        if (textOfTitle.equals("Evil Bong 777".toLowerCase())) {
            titleDatailScreen.clickOnPlay()
            if (huluPlaybackScreen.waitForAgeLimitation()) {
                huluPlaybackScreen.clickOnMonth()
                huluPlaybackScreen.selectMonth()
                huluPlaybackScreen.clickOnDay()
                huluPlaybackScreen.selectDay()
                huluPlaybackScreen.clickOnYear()
                huluPlaybackScreen.scrollYaers()
                huluPlaybackScreen.selectYear()
                huluPlaybackScreen.clickOnSubmit()
                huluPlaybackScreen.waitUntilAddsGone()
                uiDevice.click(97, 1030)
            }
        }
        Thread.sleep(100000)
        BaseScreen().navigateBackToHomeScreen()
        val subCategory = homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.CONTINUE_WATCHING.value)
        val movieDetailsScreen = subCategory.clickOnTitle(textOfTitle)
        val titleFromContinueWatching = movieDetailsScreen.titleName
        movieDetailsScreen.clickOnPlay()
        val agePicker=huluPlaybackScreen.waitForAgeLimitation()
        uiDevice.pressBack()
        uiDevice.pressBack()
        subCategory.longClickOnTitle(titleFromContinueWatching)
                .clickRemoveFromHistory()
        assertEquals("Age picker pop-ups for Hulu title even when title already is added to 'Continue watching' ", agePicker, false)
        assertEquals("Hulu title is not added to continueWatching after watching ", textOfTitle, titleFromContinueWatching)
    }

    @Test
    fun huluTitleAddedToQueue() {
        val homeScreen = HomeScreen(true)
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        val searchSreen = homeScreen.clickOnSearch()
        val gotIt = searchSreen.provideTextToSearch(textWhatTestIsLookingFor)
                .clickOnFirstTitleFirstTime()
        val titleDatailScreen = gotIt.clickOnGotIt()
        val titleFromSerch = titleDatailScreen.titleName
        val playBackScreen = titleDatailScreen.clickOnAddToQueue()
        BaseScreen().navigateBackToHomeScreen()
        val subCategory = homeScreen.clickOnBrowseButton()
                .scrollToSpecificCategory(Categories.QUEUE.value)
        val titleFromContinueWatching = subCategory.clickOnTitle(titleFromSerch)
                .titleName
        uiDevice.pressBack()
        subCategory.longClickOnTitle(titleFromContinueWatching)
                .clickAddToQueueAfterLongClickWithoutReturn()
        assertEquals("Hulu title is not added to Queeu ", titleFromSerch, titleFromContinueWatching)
    }


}

