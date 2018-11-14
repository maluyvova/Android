package com.tubitv.tubitv.Screens

import android.provider.Contacts
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.*
import android.support.v7.app.AppCompatActivity
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.shortWaitTime

/**
 * Created by vburian on 2/20/18.
 */
open class BaseScreen {

    val facebookSignIn = appPackage + ":id/prompt_image_background"

    protected val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    protected fun findObjectById(id: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun getChildCount(parentId:String,childId:String):Int{
     return   UiCollection(UiSelector().resourceId(parentId)).getChildCount(UiSelector().resourceId(childId))
    }

    protected fun findElementByDescription(description: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().description(description))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findElementByText(text: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().text(text))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun scrollableScreenById(id: String): UiScrollable {
        return UiScrollable(UiSelector().resourceId(id))

    }

    protected fun findObjectByClass(classs: String,waitFoObject: Boolean):UiObject {
    val objectt=uiDevice.findObject(UiSelector().className(classs))
        if (waitFoObject){
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findElementById(id: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findElementByIdShortTimeOut(id: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id))
        if (waitFoObject) {
            objectt.waitForExists(shortWaitTime)
        }
        return objectt
    }


    protected fun findElementByIdAnd1LevelDeeper(id: String, level: Int, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id).
                childSelector(UiSelector().index(level)))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findElmentByidAnd2LevelsDeeper(id: String, level: Int, levell: Int, waitFoObject: Boolean): UiObject {
        val objects = uiDevice.findObject(UiSelector().resourceId(id)
                .childSelector(UiSelector().index(level))
                .childSelector(UiSelector().index(levell)))
        if (waitFoObject) {
            objects.waitForExists(globalTimeout)
        }
        return objects
    }

    protected fun findElmentByidAnd3LevelsDeeper(id: String, level: Int, levell: Int, levelll: Int, waitFoObject: Boolean): UiObject {
        val objects = uiDevice.findObject(UiSelector().resourceId(id)
                .childSelector(UiSelector().index(level))
                .childSelector(UiSelector().index(levell))
                .childSelector(UiSelector().index(levelll)))
        if (waitFoObject) {
            objects.waitForExists(globalTimeout)
        }
        return objects
    }

    protected fun findElmentByClassAnd3LevelsDeeper(classs: String, level: Int, levell: Int, levelll: Int, waitFoObject: Boolean): UiObject {
        val objects = uiDevice.findObject(UiSelector().className(classs)
                .childSelector(UiSelector().index(level))
                .childSelector(UiSelector().index(levell))
                .childSelector(UiSelector().index(levelll)))
        if (waitFoObject) {
            objects.waitForExists(globalTimeout)
        }
        return objects
    }

    protected fun findElmentByidAnd5LevelsDeeper(id: String, level: Int, levell: Int, levelll: Int, levellll: Int, levelllll: Int, waitFoObject: Boolean): UiObject {
        val objects = uiDevice.findObject(UiSelector().resourceId(id)
                .childSelector(UiSelector().index(level))
                .childSelector(UiSelector().index(levell))
                .childSelector(UiSelector().index(levelll))
                .childSelector(UiSelector().index(levellll))
                .childSelector(UiSelector().index(levelllll)))
        if (waitFoObject) {
            objects.waitForExists(globalTimeout)
        }
        return objects
    }

    protected fun findElmentByidAnd7LevelsDeeper(id: String, level: Int, levell: Int, levelll: Int, levellll: Int, levelllll: Int, levellllll: Int, levelllllll: Int, waitFoObject: Boolean): UiObject {
        val objects = uiDevice.findObject(UiSelector().resourceId(id)
                .childSelector(UiSelector().index(level))
                .childSelector(UiSelector().index(levell))
                .childSelector(UiSelector().index(levelll))
                .childSelector(UiSelector().index(levellll))
                .childSelector(UiSelector().index(levelllll))
                .childSelector(UiSelector().index(levellllll))
                .childSelector(UiSelector().index(levelllllll)))
        if (waitFoObject) {
            objects.waitForExists(globalTimeout)
        }
        return objects
    }


}


