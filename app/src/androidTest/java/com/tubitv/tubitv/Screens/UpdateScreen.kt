package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiObjectNotFoundException
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.appPackage
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue

/**
 * Created by vburian on 10/25/18.
 */
class UpdateScreen : BaseScreen() {

    private val mainText = appPackage + ":id/prompt_title"
    private val subText = appPackage + ":id/prompt_text"
    private val updateAppButton = appPackage + ":id/update_button"
    private val maybeLater = appPackage + ":id/cancel_button"
    private var isScreenExists = false

    init {
        isScreenExists = findElementByIdShortTimeOut(updateAppButton, true).exists()
        if (isScreenExists) {
            assertTrue("UpdateApp button doesn't exists on update screen", findElementById(updateAppButton, true).exists())
            assertTrue("MaybeLatter button doesn't exists on update screen", findElementById(maybeLater, false).exists())
            assertTrue("UpdateApp button doesn't exists on update screen", findElementById(updateAppButton, false).exists())
            assertEquals("Main text 'New Tubi App available!' doesn't correspond requirements", findElementById(mainText, false).text, "New Tubi App \n"+"available!")
            assertEquals("SubText text doesn't correspond requirements", findElementById(subText, false).text, "We made some enhancements to our experience that require you to update the Tubi app.")
        }
    }

    fun clickOnMaybeLatter(): HomeScreen {
        if (!isScreenExists) {
            return HomeScreen(true)
        }
        try {
            findObjectById(maybeLater, false).click()
            return HomeScreen(true)
        } catch (e: UiObjectNotFoundException) {
            throw TestException("'Maybe later' button is not found in update screen")
        }
    }

    fun clickOnUpdateNow() {
        try {
            findObjectById(updateAppButton, false).click()
        } catch (e: UiObjectNotFoundException) {
            throw TestException("'Update App' button is not found in update screen")
        }
    }

}