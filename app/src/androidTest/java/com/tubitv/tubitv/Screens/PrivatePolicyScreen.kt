package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage

/**
 * Created by vburian on 6/13/18.
 */
class PrivatePolicyScreen : BaseScreen() {
    private var textOFPrivatePolicy = UiCollection(UiSelector().resourceId(appPackage + ":id/activity_abstract_drawer_container"))
            .getChild(UiSelector().index(0))
            .getChild(UiSelector().index(0))
            .getChild(UiSelector().index(0))


    public val textOfPriVatePolicy get() = textOFPrivatePolicy.text


}