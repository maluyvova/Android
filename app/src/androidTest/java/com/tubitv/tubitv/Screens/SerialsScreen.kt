package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert
import java.util.*

/**
 * Created by vburian on 3/30/18.
 */
class SerialsScreen() : BaseScreen() {
    private val season2 = UiCollection(UiSelector().text("Season 2"))
    private val seasonPicker = UiDeviceID(appPackage + ":id/seasonSpinner")
    private val textOfSeason = UiDeviceID(appPackage + ":id/textview")
    private val scrollableScreen = UiScrollable(UiSelector().resourceId(appPackage + ":id/scrollView_main"))
    private val playButton = UiDeviceID(appPackage + ":id/imageView_play")
    private val scrollableNextEpisodes = UiScrollable(UiSelector().resourceId(appPackage + ":id/episode_list_recyclerview"))
    private val titleOfEpsideInTheButton = UiDeviceID(appPackage + ":id/title")
    private val playButtonForNextEpisode = UiDeviceID(appPackage + ":id/play_button")
    private val episodeNumber = UiDeviceID(appPackage + ":id/vaudTextView_episode_title")//:id/vaudTextView_episdoe_title
    private val presentedByHulu = UiDeviceID(appPackage + ":id/vaudTextView_present_hulu")

    init {
        // Assert.assertTrue("Expected 'Play' button is not displayed ",playButton.waitForExists(globalTimeout))
        // Assert.assertTrue("Episode number is not displayed ",episodeNumber.waitForExists(globalTimeout))
    }

    fun scrollScreen(number: Int) {
        scrollableScreen.setAsVerticalList().scrollToEnd(number)
    }

    public val presentByHulu get() = this.presentedByHulu
    public val seasonPickers get() = this.seasonPicker
    public val sseason2 get() = this.season2
    public val scrlbleScreen get() = this.scrollableScreen
    public val textofSeason get() = this.textOfSeason
    fun getTextOfEpisode(): String {
        return titleOfEpsideInTheButton.text
    }

    fun getNumberOfEpisode(): String {
        return episodeNumber.text
    }

    fun scrollEpisdoesList(number: Int) {
        scrollableNextEpisodes.setAsHorizontalList().scrollToEnd(number)
    }

    fun clickOnPlayInTheButtonNextEpisode() {
        playButtonForNextEpisode.click()
    }

    fun clickOnPlayButton(): PlayBackScreen {
        playButton.waitForExists(globalTimeout)
        playButton.click()
        return PlayBackScreen()
    }

    fun selectRundomSerialTitle() {
        val numbersOfTitles = MoviesByCategoryScreen().getCountOfTitles()
        val randomNumber = Random().nextInt(numbersOfTitles)
        val title = MoviesByCategoryScreen().gotkRandomTite(randomNumber) //randomNumber
        title.click()
        if (GotIt().gotitButton.waitForExists(globalTimeout))
            GotIt().clickOnGotIt()
        if (SerialsScreen().presentByHulu.exists()) {
            uiDevice.pressBack()
            selectRundomSerialTitle()
        }
        val serialScreen = SerialsScreen()
    }


}
