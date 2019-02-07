package com.tubitv.tubitv.Tests

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.*
import com.tubitv.tubitv.Enomus.TypeOfContent
import com.tubitv.tubitv.Screens.*
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test

/**
 * Created by vburian on 6/12/18.
 */
class DeepLinksTest : BaseTest() {

    val requirements = "https://tubitv.atlassian.net/wiki/spaces/EC/pages/770113559/Testing+deep+link+on+mobile"
    val comandForDeepLink = "am start -W -a android.intent.action.VIEW -d "
    val ActionCategory = "http://tubitv.com/category/action?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val ForSerial = "https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val ForComedy = "https://tubitv.com/category/comedy?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val ForDrama = "tubitv://media-browse?categoryId=1291\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val ForMovieOldBoy = "http://tubitv.com/movies/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val LinkForMovieOldBoy = "https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val MovieIgor = "https://link.tubi.tv/t1XXhN28RR"
    val SerialDeadLikeMe = "https://link.tubi.tv/kCXwWs68RR"
    val Movie127Hours = "https://link.tubi.tv/XuWf4pbvpS"
    val TheHollow = "http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val OldBoyUTM = "https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val WildAtHeartUTMSerial = "http://tubitv.com/tv-shows/203?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val McLeodsDaughtersUTMSerial = "https://link.tubi.tv/vInFMDv8hT"
    val McLeodsDaughters = "http://tubitv.com/series/337?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val DogTheBountyHunterUTMSErial = "https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkToCategoryUTMAnime = " tubitv://media-browse?categoryId=anime\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkToCategoryUTMAnime2 = "https://tubitv.com/category/anime?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkToPlayBackUTM = "tubitv://media-playback?contentId=294111\\&contentType=video\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "
    val anotherDeeplinkWithUTM = "http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkForMovieWomanThouArtLoosed = "http://tubitv.com/movies/456403?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    //val deepLinkForPlayback="tubitv://media-playback?contentId=294111\\&contentType=movie\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "


    fun ifNotRegistred() {
        launchApp(appPackage, false)
        if (uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_login_adapter_text_view")).waitForExists(globalTimeout)) {
            SignInTest().signInWithCorrectEmailAndPassword()
        }
        killApp()

    }

    fun ifFirstTime() {
        var app = "Tubi TV Staging"
        if (appPackage.equals("com.tubitv.fire") || appPackage.equals("com.tubitv")) {
            app = "Tubi TV"
        }
        if (uiDevice.findObject(UiSelector().resourceId("android:id/title")).waitForExists(globalTimeout)) {
            uiDevice.findObject(UiSelector().textContains(app)).click()
            if (uiDevice.findObject(UiSelector().resourceId("android:id/button_once")).exists())
                uiDevice.findObject(UiSelector().resourceId("android:id/button_once")).click()
            else uiDevice.click(200, 200)
        } else if (uiDevice.findObject(UiSelector().textContains(app)).exists()) {
            uiDevice.findObject(UiSelector().textContains(app)).click()
            if (uiDevice.findObject(UiSelector().resourceId("android:id/button1")).waitForExists(globalTimeout)) {
                uiDevice.findObject(UiSelector().resourceId("android:id/button1")).click()
            } else if ((uiDevice.findObject(UiSelector().text("OK")).exists())) {
                uiDevice.findObject(UiSelector().text("OK")).click()
            }
        }
    }

    @Test
    fun deepLinkForAction() {
        getInstrum()
        clearAppData()
        // ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + ActionCategory + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen("Action").categoryText, "Action")
    }

    @Test
    fun deepLinkForSerial() {
        val expectedTitleOfSerial = "Dog the Bounty Hunter"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + ForSerial + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected serial $expectedTitleOfSerial didn't show up probably expired  ", MovieDatailScreen().titleDatailScreen, "$expectedTitleOfSerial")
    }

    @Test
    fun deepLinkForComedy() {
        val expectedCategory = "Comedy"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + ForComedy + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category: $expectedCategory didn't show up after deep link", MoviesByCategoryScreen("Comedy").categoryText, "$expectedCategory")
    }

    @Test
    fun deepLinkForDrama() {
        val expectedCategory = "Drama"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + ForDrama + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category: $expectedCategory didn't show up after deep link", MoviesByCategoryScreen("Drama").categoryText, "$expectedCategory")
    }

    @Test
    fun deepLinkForMovieOldBoy() {
        val expectedMovie = "Oldboy"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + ForMovieOldBoy + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link ", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkForVideoOldBoy() {
        val expectedMovie = "Oldboy"
        getInstrum()
        clearAppData()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + LinkForMovieOldBoy + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link ", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkForMovieIgor() {
        val expectedMovie = "Igor"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + MovieIgor)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    //@Test
    fun deepLinkForMove127Hours() {
        val expectedMovie = "127 Hours"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + Movie127Hours)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMtheHollow() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + TheHollow)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMOldboy() {
        val expectedMovie = "Oldboy"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + OldBoyUTM)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMWildAtHeartSerial() {
        val expectedMovie = "Wild at Heart"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + WildAtHeartUTMSerial)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekToMiddleOfPlayback()
                .seekToTheEnd()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    @Test
    fun deeplinkWithUTMMcLeodsDaughtersSerial() {
        val expectedMovie = "McLeod's Daughters"
        getInstrum()
        clearAppData()
        launchApp(appPackage, false)
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + McLeodsDaughtersUTMSerial)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekToMiddleOfPlayback()
                .seekToTheBegining()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithMcLeodsDaughtersSerial() {
        val expectedMovie = "McLeod's Daughters"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + McLeodsDaughters)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekToMiddleOfPlayback()
                .seekToTheBegining()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMDogTheBountyHunterSerial() {
        val expectedMovie = "Dog the Bounty Hunter"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + DogTheBountyHunterUTMSErial)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekToMiddleOfPlayback()
                .seekToTheEnd()
                .seekToTheBegining()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    @Test
    fun deepLinkForAnimeUTM() {
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkToCategoryUTMAnime + appPackage)
        ifFirstTime()
        val category = MoviesByCategoryScreen("Anime").categoryText
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen("Anime").categoryText, "Anime")
    }

    @Test
    fun deepLinkForAnime2UTM() {
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkToCategoryUTMAnime2 + appPackage)
        ifFirstTime()
        val category = MoviesByCategoryScreen("Anime").categoryText
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen("Anime").categoryText, "Anime")
    }

    @Test
    fun deepLinkUMLTheHollow() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + anotherDeeplinkWithUTM)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkUMLTheHollowRegisterUser() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        clearAppData()
        launchApp(appPackage, false)
        val homeScreen = HomeScreen(true)
        homeScreen.dismissCasting()
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + anotherDeeplinkWithUTM)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkUMWomanThouArtLoosed() {
        val expectedMovie = "Woman Thou Art Loosed"
        getInstrum()
        killApp()
        clearAppData()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForMovieWomanThouArtLoosed)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkUMWomanThouArtLoosedForRegisterUser() {
        val expectedMovie = "Woman Thou Art Loosed"
        getInstrum()
        killApp()
        clearAppData()
        launchApp(appPackage, false)
        val homeScreen = HomeScreen(true)
        homeScreen.dismissCasting()
        homeScreen.longPress()
                .clickAddToQueueAfterLongClickWithoutReturn()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForMovieWomanThouArtLoosed)
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekToMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekToMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnBrowseButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    /* @Test
     fun playbackDeepLink(){
         getInstrum()
         ifNotRegistred()
       uiDevice.executeShellCommand(comandForDeepLink+deepLinkForPlayback+ appPackage)
         ifFirstTime()
        val title= PlayBackScreen().waitForPlayBack()
         Assert.assertEquals(title,"Oldboy")
     }*/


}