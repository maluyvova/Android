package com.tubitv.tubitv.Screens

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.*
import com.tubitv.tubitv.*
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Helpers.TestExceptionWithError

/**
 * Created by vburian on 2/20/18.
 */
open class BaseScreen {

    protected val facebookSignIn = appPackage + ":id/prompt_image_background"
    private val homeScreen = appPackage + ":id/fragment_home_list_category_recycler"
    public val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    protected val tabs = "android:id/tabs"

    protected fun findObjectById(id: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findObjectByIdAndIndex(id: String, index: Int, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id).index(index))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findObjectByIdChildByCount(id: String, index: Int, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id).childSelector(UiSelector().index(index)))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findElementParentIdChildIndex(id: String, waitForObject: Boolean, vararg level: Int): UiObject {
        var i = 0
        var uiSelector = (UiSelector().resourceId(id))
        while (i <= level.size - 1) {
            uiSelector = uiSelector.childSelector(UiSelector().index(level[i]))
            i++
        }
        val uiObject = uiDevice.findObject(uiSelector)
        if (waitForObject) {
            uiObject.waitForExists(globalTimeout)
        }
        return uiObject
    }

    protected fun getChildCount(parentId: String, childId: String): Int {
        return UiCollection(UiSelector().resourceId(parentId)).getChildCount(UiSelector().resourceId(childId))
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

    protected fun findObjectByClass(classs: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().className(classs))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findObjectByClassShortTime(classs: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().className(classs))
        if (waitFoObject) {
            objectt.waitForExists(shortWaitTime)
        }
        return objectt
    }

    protected fun findByContentDesc(description: String, waitFoObject: Boolean): UiObject {
        val obbject = uiDevice.findObject(UiSelector().description(description))
        if (waitFoObject) {
            obbject.waitForExists(globalTimeout)
        }
        return obbject
    }

    protected fun findElementById(id: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findElementByIdParentChildByPackageName(id: String, packageName: String, waitFoObject: Boolean, ids: String): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id).childSelector(UiSelector().packageName(packageName).childSelector(UiSelector().resourceId(ids))))
        if (waitFoObject) {
            objectt.waitForExists(globalTimeout)
        }
        return objectt
    }

    protected fun findElementByIdParentChildByClassName(id: String, className: String, waitFoObject: Boolean, index: Int): UiObject {
        val objectt = UiCollection(UiSelector().resourceId(id)).getChild(UiSelector().className(className).index(index))
        //val objectt = uiDevice.findObject(UiSelector().resourceId(id).childSelector(UiSelector().className(className)).index(index))
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

    protected fun findElementByIdMediumtTimeOut(id: String, waitFoObject: Boolean): UiObject {
        val objectt = uiDevice.findObject(UiSelector().resourceId(id))
        if (waitFoObject) {
            objectt.waitForExists(mediumWaitTime)
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





    protected fun waitForObjectShortTime(id: String): Boolean {
        var bool = false
        try {
            bool = uiDevice.findObject(UiSelector().resourceId(id)).waitForExists(shortWaitTime)
        } catch (e: UiObjectNotFoundException) {
            throw TestExceptionWithError("Can't find object for waiting", e)
        }
        return bool
    }

    fun navigateBackToHomeScreen(): HomeScreen {
        var i = 0
        while (!waitForObjectShortTime(homeScreen) && i < 7) {
            uiDevice.pressBack()
            i++
            if(i>30){
                throw TestException("Something wrong with navigating back to home screen")
            }
        }
        return HomeScreen(true)
    }


}


