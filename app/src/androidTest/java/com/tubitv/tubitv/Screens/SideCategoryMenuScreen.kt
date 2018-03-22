package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/14/18.
 */
class SideCategoryMenuScreen:BaseScreen(){
    val boxWithListOfCategories=UiCollection(UiSelector().resourceId("com.tubitv:id/design_navigation_view"))
    val category =UiSelector().resourceId("com.tubitv:id/design_menu_item_text")
    init {
        Assert.assertTrue("Expected Category name in TOP is not displayed",boxWithListOfCategories.waitForExists(globalTimeout))

    }

    fun numberOftitles():Int{
     return boxWithListOfCategories.getChildCount(category)

    }


    fun selectRandomcategory(index:Int){
      boxWithListOfCategories.getChildByInstance(category,index).click()

    }
    fun textOfRandomCategory(index:Int):String{
        val text=boxWithListOfCategories.getChildByInstance(category,index).text
      return text
    }





















}