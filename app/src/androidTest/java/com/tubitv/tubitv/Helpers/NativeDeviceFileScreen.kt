package com.tubitv.tubitv.Helpers

import com.tubitv.tubitv.Screens.BaseScreen
import com.tubitv.tubitv.deviceName

/**
 * Created by vburian on 4/9/19.
 */
class NativeDeviceFileScreen : BaseScreen() {

    private val s10 = "android:id/title"

    fun addFile() {
        when (deviceName) {
//            "Pixel2" -> findElementById(pixel2PhotoButton, true).click()
//            "GalaxyS8" -> findElementById(galaxyS8Button, true).click()
//            "LG K20" -> findElementByText(okButton, true).click()
//            "TabA" -> findElementById(galaxyS8Button, true).click()
//            "G6" -> findElementByText(okButton, true).click()
//            "LGNexus" -> findElementById(pixel2PhotoButton, true).click()
//            "SumsungTablet" -> findElementById(galaxyS8Button, true).click()
//            "AsusTablet" -> findElementById(assusTablet,true).click()
            "GalaxyS10" -> {
                findElementById(s10, true).click()
               findObjectById("com.tubitv:id/dismiss_area",true).click()
            }
            else -> throw TestException("Device id is not added to this framework , please find method getDeviceNameBasedOnId() and add your device id (read exception for that method)")
        }

    }

}