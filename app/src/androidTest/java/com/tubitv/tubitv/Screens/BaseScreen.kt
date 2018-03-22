package com.tubitv.tubitv.Screens

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice

/**
 * Created by vburian on 2/20/18.
 */
open class BaseScreen{

    protected val uiDevice:UiDevice=UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())



}