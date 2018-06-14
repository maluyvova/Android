package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 3/14/18.
 */
class SideCategoryMenuScreen:BaseScreen(){
   private val boxWithListOfCategories=UiCollection(UiSelector().resourceId(appPackage +":id/design_navigation_view"))
   private val category =UiSelector().resourceId(appPackage+":id/design_menu_item_text")
   private val userAcountPicture=UiDeviceID(appPackage+":id/nav_menu_header_user_avatar")
   private val userAcountName=UiDeviceID(appPackage+":id/nav_menu_header_user_name")
   private val userEmail=UiDeviceID(appPackage+":id/nav_menu_header_user_email_address")
    init {
        Assert.assertTrue("Expected Category name in TOP is not displayed",boxWithListOfCategories.waitForExists(globalTimeout))
        Assert.assertTrue("Expected picture of user account is not displayed",userAcountPicture.waitForExists(globalTimeout))
        Assert.assertTrue("Expected name of user account is not displayed",userAcountName.waitForExists(globalTimeout))
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

    fun getUserEmail():String{
       return userEmail.text
    }
    fun getUserName():String{
        return userAcountName.text
    }






















}