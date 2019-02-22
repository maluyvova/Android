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
    private val scrollableScreen = UiScrollable(UiSelector().className("android.webkit.WebView"))

    init {
        assertTrue("Term of use screen is not loaded", scrollableScreen.waitForExists(globalTimeout))
        assertTrue("Term text is not present on Terms web view", findElementByText("Terms",true).exists())
        assertTrue("Effective Date text is not present on Terms web view", findElementByText("EFFECTIVE Date: 2019",true).exists())
    }

    fun scrollToTheButton() {
        for (i in 1..15) {
            scrollableScreen.setAsVerticalList().scrollToEnd(1)
        }
    }

}