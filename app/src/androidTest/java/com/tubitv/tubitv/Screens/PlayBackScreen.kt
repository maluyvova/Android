package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 2/26/18.
 */
class PlayBackScreen:BaseScreen()
{
   private var playBackWindow=uiDevice.findObject(UiSelector().resourceId(appPackage +":id/controller_panel"))
    private var playButton = uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_tubi_controller_play_toggle_ib"))
    private var seek=UiSelector().resourceId(appPackage+":id/view_tubi_controller_rewind_ib")
    private val s= uiDevice.findObject(By.res(appPackage+":id/view_tubi_controller_subtitles_ib"))
    private val sub=uiDevice.wait(Until.findObject(By.text("Fire With Fire")), globalTimeout)
    private val nameOfTile=uiDevice.findObject(By.text("Fire With Fire"))
    private var subtittless=UiDeviceID(appPackage+":id/view_tubi_controller_subtitles_ib")
   // private var subtittles =UiSelector().resourceId(appPackage+":id/view_tubi_controller_subtitles_ib")
   private var quality=UiSelector().resourceId(appPackage+":id/view_tubi_controller_quality_ib")
   private var titleText=UiSelector().resourceId(appPackage+":id/view_tubi_controller_title")
    private var scrollControlSeek=UiScrollable(UiSelector().resourceId(appPackage+":id/view_tubi_controller_seek_bar"))
    private var controllerSeek=UiSelector().resourceId(appPackage+":id/view_tubi_controller_seek_bar")
    private var rightTimer=uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_tubi_controller_remaining_time"))
   private var leftTimer=uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_tubi_controller_elapsed_time"))

    public fun textOfRightTimer():String{

    return rightTimer.text
    }


    public fun scrollSeekBar(){
        scrollControlSeek.setAsHorizontalList().scrollToEnd(1)
    }

    fun waitUntilAdsfinishes(){
        uiDevice.swipe(385,317,1500,483,2)
        while(!rightTimer.exists()){
            Thread.sleep(1000)
            uiDevice.swipe(385,317,1500,483,2)
        }
    }



    public fun clickPlay(){
        playButton.click()
    }

    public fun checkIfSubtitlesIsSelected():String{
      return subtittless.text
    }


}


