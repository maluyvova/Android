package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 2/26/18.
 */
class PlayBackScreen:BaseScreen()
{
   private var playBackWindow=uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/controller_panel"))
    private var playButton = uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/view_tubi_controller_play_toggle_ib"))
    private var seek=UiSelector().resourceId("com.tubitv:id/view_tubi_controller_rewind_ib")
    private var subtittles =UiSelector().resourceId("com.tubitv:id/view_tubi_controller_subtitles_ib")
   private var quality=UiSelector().resourceId("com.tubitv:id/view_tubi_controller_quality_ib")
   private var titleText=UiSelector().resourceId("com.tubitv:id/view_tubi_controller_title")
    private var controllerSeek=UiSelector().resourceId("com.tubitv:id/view_tubi_controller_seek_bar")
    private var rightTimer=uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/view_tubi_controller_remaining_time"))
   private var leftTimer=uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/view_tubi_controller_elapsed_time"))

    public fun textOfRightTimer(){
     leftTimer.text
    }

    public fun clickPlay(){
        playButton.click()
    }


}


