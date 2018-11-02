package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert
import junit.framework.Assert.assertTrue

/**
 * Created by vburian on 8/10/18.
 */
class TermOfUseScreen : BaseScreen() {
    private val scrollableScreen = UiScrollable(UiSelector().className("android.widget.ScrollView"))

    init {
        assertTrue("Term of use screen is not loaded", scrollableScreen.waitForExists(globalTimeout))
    }

    fun scrollToTheButtom() {
        for (i in 1..9) {
            scrollableScreen.setAsVerticalList().scrollToEnd(1)
        }
    }

}