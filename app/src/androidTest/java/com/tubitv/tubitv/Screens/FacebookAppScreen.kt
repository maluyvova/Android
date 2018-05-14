package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.globalTimeout

/**
 * Created by vburian on 5/14/18.
 */
class FacebookAppScreen :BaseScreen() {
    private val sharedLink= uiDevice.findObject(UiSelector().textContains("shared a link"))



fun getText():String{
    sharedLink.waitForExists(globalTimeout)
    return sharedLink.text
}

}