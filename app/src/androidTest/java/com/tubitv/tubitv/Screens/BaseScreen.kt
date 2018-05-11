package com.tubitv.tubitv.Screens

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject
import android.support.test.uiautomator.UiSelector

/**
 * Created by vburian on 2/20/18.
 */
open class BaseScreen{

    protected val uiDevice:UiDevice=UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    public fun  UiDeviceID(id:String):UiObject{
      return uiDevice.findObject(UiSelector().resourceId(id))
   }


}