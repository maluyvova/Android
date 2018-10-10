package com.tubitv.tubitv.Helpers

import android.support.test.uiautomator.UiScrollable
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
    public val scrollHomePage = UiScrollable(UiSelector().resourceId(appPackage + ":id/view_category_recycler"))

    fun deleteHistory() {
        while (titleInContinueWatching.exists()) {
            titleInContinueWatching.dragTo(titleInContinueWatching, 20)
            removeFromHistory.click()
        }
    }
    fun deleteFromQueue(){
       val homePage=HomeScreen()
   //     scrollHomePage.setMaxSearchSwipes(3)
 //      val QuoueOnScreen= scrollHomePage.scrollDescriptionIntoView("Queue")
       if(homePage.getTextOfCategory(0).text.equals("Queue")){
     //     val remove= homePage.longPressToRemoveFromQueue()
      //     remove.clickAddToQueueAfterLongClickWithoutReturn()
       }
    }

   }


