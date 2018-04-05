package com.tubitv.tubitv.Helpers

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Screens.BaseScreen
import com.tubitv.tubitv.Screens.HomeScreen
import com.tubitv.tubitv.appPackage

/**
 * Created by vburian on 4/5/18.
 */
class InsuranceBeforeTests:BaseScreen() {
    private val titleInContinueWatching = uiDevice.findObject(UiSelector().resourceId(appPackage +":id/view_home_content_continue_iv"))
    private val removeFromHistory=uiDevice.findObject(UiSelector().text("Remove from history"))


    fun deleteHistory() {
        if (titleInContinueWatching.exists()) {
            titleInContinueWatching.dragTo(titleInContinueWatching, 20)
            removeFromHistory.click()
        }
    }
    fun deleteFromQueue(){
       val homePage=HomeScreen()
       if(homePage.getTextOfCategory().text=="Queue"){
          val remove= homePage.longPressToRemoveFromQueue()
           remove.clickAddToQueueAfterLongClickWithoutReturn()
       }
    }

   }


