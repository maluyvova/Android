package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/30/18.
 */
class SerialsScreen():BaseScreen(){
    private val scrollableScreen=UiScrollable(UiSelector().resourceId(appPackage+":id/scrollView_main"))
    private val playButton=UiDeviceID(appPackage+":id/imageView_play")
    private val scrollableNextEpisodes=UiScrollable(UiSelector().resourceId(appPackage+":id/episode_list_recyclerview"))
    private val titleOfEpsideInTheButton=UiDeviceID(appPackage+":id/title")
    private val playButtonForNextEpisode=UiDeviceID(appPackage+":id/play_button")
    private val episodeNumber=UiDeviceID(appPackage+":id/vaudTextView_episode_title")
    init {
        Assert.assertTrue("Expected 'Play' button is not displayed ",playButton.waitForExists(globalTimeout))
        Assert.assertTrue("Expected 'Play' button is not displayed ",episodeNumber.waitForExists(globalTimeout))
    }


    fun scrollScreen(number:Int){
       scrollableScreen.setAsVerticalList().scrollToEnd(number)
    }

    fun getTextOfEpisode():String{
        return  titleOfEpsideInTheButton.text
    }

    fun getNumberOfEpisode():String{
        return episodeNumber.text
    }

    fun scrollEpisdoesList(number:Int){
        scrollableNextEpisodes.setAsHorizontalList().scrollToEnd(number)
    }

    fun clickOnPlayInTheButtonNextEpisode(){
        playButtonForNextEpisode.click()
    }





}
