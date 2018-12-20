package com.tubitv.tubitv

import android.support.test.uiautomator.UiSelector
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
    val deepLinkForActionCategory = "http://tubitv.com/category/action?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkForSerial = "https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkForComedy = "https://tubitv.com/category/comedy?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkForMovie = "http://tubitv.com/movies/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val anotherDeepLinkForMovie = "https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkForMovieIgor = "https://link.tubi.tv/t1XXhN28RR"
    val deeplinkForSerialDeadLikeMe = "https://link.tubi.tv/kCXwWs68RR"
    val deeplinkForMovieWar = "https://link.tubi.tv/XuWf4pbvpS"
    val deeplinkForTheHollow = "http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkForOldBoyUTM = "https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkForWildAtHeartUTMSerial = "http://tubitv.com/tv-shows/203?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkForMcLeodsDaughtersUTMSerial = "http://tubitv.com/series/337?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deepLinkDogTheBountyHunterUTMSErial = "https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkToCategoryUTMAnime = " tubitv://media-browse?categoryId=anime\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkToCategoryUTMAnime2 = "https://tubitv.com/category/anime?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
    val deeplinkToPlayBackUTM="tubitv://media-playback?contentId=294111\\&contentType=video\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "
    val anotherDeeplinkWithUTM ="http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"
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
        // ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + deepLinkForActionCategory + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen().categoryText, "Action")
    }

    @Test
    fun deepLinkForSerial() {
        val expectedTitleOfSerial = "Dog the Bounty Hunter"
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + deepLinkForSerial + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected serial $expectedTitleOfSerial didn't show up probably expired  ", MovieDatailScreen().titleDatailScreen, "$expectedTitleOfSerial")
    }

    @Test
    fun deepLinkForComedy() {
        val expectedCategory = "Comedy"
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + deepLinkForComedy + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected category: $expectedCategory didn't show up after deep link", MoviesByCategoryScreen().categoryText, "$expectedCategory")
    }

    @Test
    fun deepLinkForMovie() {
        val expectedMovie = "Oldboy"
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + deepLinkForMovie + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link ", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkForVideo() {
        val expectedMovie = "Oldboy"
        getInstrum()
        ifNotRegistred()
        uiDevice.executeShellCommand(comandForDeepLink + anotherDeepLinkForMovie + appPackage)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link ", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkForMovieIgor() {
        val expectedMovie = "Igor"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForMovieIgor)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkForSerialDeadLikeMe() {
        val expectedMovie = "Dead Like Me"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForSerialDeadLikeMe)
        ifFirstTime()
        Assert.assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deepLinkForMoveWar() {
        val expectedMovie = "127 Hours"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForMovieWar)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMtheHollow() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForTheHollow)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMOldboy() {
        val expectedMovie = "Oldboy"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForOldBoyUTM)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMWildAtHeartSerial() {
        val expectedMovie = "Wild at Heart"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForWildAtHeartUTMSerial)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekMiddleOfPlayback()
                .seekToTheEnd()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    @Test
    fun deeplinkWithUTMMcLeodsDaughtersSerial() {
        val expectedMovie = "McLeod's Daughters"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkForMcLeodsDaughtersUTMSerial)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekMiddleOfPlayback()
                .seekToTheBegining()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }

    @Test
    fun deeplinkWithUTMDogTheBountyHunterSerial() {
        val expectedMovie = "Dog the Bounty Hunter"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deepLinkDogTheBountyHunterUTMSErial)
        ifFirstTime()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        SerialsScreen().clickOnPlayButton()
                .seekMiddleOfPlayback()
                .seekToTheEnd()
                .seekToTheBegining()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
                .scrollToSpecificCategory(continueWatching)
                .removeAllTitles()
    }


    @Test
    fun deepLinkForAnimeUTM() {
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkToCategoryUTMAnime + appPackage)
        ifFirstTime()
        val category = MoviesByCategoryScreen().categoryText
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen().categoryText, "Anime")
    }

    @Test
    fun deepLinkForAnime2UTM() {
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + deeplinkToCategoryUTMAnime2 + appPackage)
        ifFirstTime()
        val category = MoviesByCategoryScreen().categoryText
        Assert.assertEquals("Expected category Action didn't show up after deep link", MoviesByCategoryScreen().categoryText, "Anime")
    }

    @Test
    fun deepLinkUML() {
        val expectedMovie = "The Hollow"
        getInstrum()
        killApp()
        uiDevice.executeShellCommand(comandForDeepLink + anotherDeeplinkWithUTM )
        ifFirstTime()
        casting()
        assertEquals("Expected movie:$expectedMovie didn't show up after deep link -> requirements :$requirements", MovieDatailScreen().titleDatailScreen, "$expectedMovie")
        MovieDatailScreen().clickOnPlay()
                .seekMiddleOfPlayback()
                .seekToAutoPlay(TypeOfContent.MOVIES)
                .playTitleFromAutoplay()
                .seekMiddleOfPlayback()
        BaseScreen().navigateBackToHomeScreen()
                .clickOnSidecategorButton()
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