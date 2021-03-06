package com.tubitv.tubitv.Screens

import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout

/**
 * Created by vburian on 5/14/18.
 */
class CastingScreen : BaseScreen() {
    private val castingBar = findObjectById(appPackage + ":id/container_current",false)

    init {
        castingBar.waitForExists(globalTimeout)
    }

    public fun clickOnCastingSmallBar() {
        castingBar.click()
    }

    class CastingScreen() : BaseScreen() {


    }

}
