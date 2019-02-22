package com.tubitv.tubitv.Screens

import android.provider.Contacts
import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.TestExceptionWithError
import com.tubitv.tubitv.appPackage
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

/**
 * Created by vburian on 6/13/18.
 */
class PrivatePolicyScreen : BaseScreen() {

    init {
        try {
            assertThat("Can't find Privacy header on 'Privacy' screen", findElementByText("Privacy", true).exists(), equalTo(true))
        } catch (e: UiObjectNotFoundException) {
            throw   TestExceptionWithError("Can't find Privacy header on 'Privacy' screen", e)
        }

        try {
            assertThat("Can't find 'Last updated' element on 'Privacy' screen", findElementByText("Last Updated: June 5th, 2018", true).exists(), equalTo(true))
        } catch (e: UiObjectNotFoundException) {
            throw   TestExceptionWithError("Can't find 'Last updated' element on 'Privacy' screen", e)
        }

    }

}