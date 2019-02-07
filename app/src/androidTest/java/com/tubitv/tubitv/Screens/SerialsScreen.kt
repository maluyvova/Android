package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import java.util.*

/**
 * Created by vburian on 3/30/18.
 */
class SerialsScreen() : BaseScreen() {
    private val season2 = UiCollection(UiSelector().text("Season 2"))
    private val seasonPicker = appPackage + ":id/seasonSpinner"
    private val textOfSeason = appPackage + ":id/textview"
    private val scrollableScreen = UiScrollable(UiSelector().resourceId(appPackage + ":id/scrollView_main"))
    private val playButton = appPackage + ":id/imageView_play"
    private val scrollableNextEpisodes = UiScrollable(UiSelector().resourceId(appPackage + ":id/episode_list_recyclerview"))
    private val titleOfEpsideInTheButton = appPackage + ":id/title"
    private val playButtonForNextEpisode = appPackage + ":id/play_button"
    private val episodeNumber = appPackage + ":id/vaudTextView_episode_title"//:id/vaudTextView_episdoe_title
    private val presentedByHulu = appPackage + ":id/vaudTextView_present_hulu"

    init {
        // Assert.assertTrue("Expected 'Play' button is not displayed ",playButton.waitForExists(globalTimeout))
        // Assert.assertTrue("Episode number is not displayed ",episodeNumber.waitForExists(globalTimeout))
    }

    fun scrollScreen(number: Int) {
        scrollableScreen.setAsVerticalList().scrollToEnd(number)
    }

    public val presentByHulu get() = this.findObjectById(presentedByHulu, false)
    public val seasonPickers get() = this.findObjectById(seasonPicker, false)
    public val sseason2 get() = this.season2
    public val scrlbleScreen get() = this.scrollableScreen
    public val textofSeason get() = this.findObjectById(textOfSeason, false)
    fun getTextOfEpisode(): String {
        return findElementById(titleOfEpsideInTheButton, false).text
    }

    fun getNumberOfEpisode(): String {
        return findElementById(episodeNumber, false).text
    }

    fun scrollEpisdoesList(number: Int) {
        for (n in 0..number) {
            scrollableNextEpisodes.setAsHorizontalList().scrollToEnd(number)
        }
    }

    fun clickOnPlayInTheButtonNextEpisode() {
        findObjectById(playButtonForNextEpisode, false).click()
    }

    fun clickOnPlayButton(): PlayBackScreen {
        findObjectById(playButton, true).click()
        if(findObjectById(playButton, true).exists()){
            findObjectById(playButton, false).click()
        }
        return PlayBackScreen()
    }

    fun selectRundomSerialTitle(category:String) {
        val numbersOfTitles = MoviesByCategoryScreen(category).getCountOfTitles()
        val randomNumber = Random().nextInt(numbersOfTitles)
        val title = MoviesByCategoryScreen(category).gotkRandomTite(randomNumber) //randomNumber
        title.click()
        if (GotIt().gotitButton.waitForExists(globalTimeout))
            GotIt().clickOnGotIt()
        if (SerialsScreen().presentByHulu.exists()) {
            uiDevice.pressBack()
            selectRundomSerialTitle(category)
        }
        val serialScreen = SerialsScreen()
    }


}
