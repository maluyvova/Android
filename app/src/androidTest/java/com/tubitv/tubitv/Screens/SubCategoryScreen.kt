package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiCollection
import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert

/**
 * Created by vburian on 4/2/18.
 */
class SubCategoryScreen:BaseScreen() {
    private val screen=UiScrollable(UiSelector().resourceId(appPackage+":id/view_grid_category_recycler"))
    private val boxWithTitles=UiCollection(UiSelector().resourceId(appPackage+":id/fragment_category_recycler"))
    private val title=UiSelector().className("android.widget.LinearLayout")
init {
    Assert.assertTrue("Expected screen with subtitles is not displayed ",screen.waitForExists(globalTimeout))
}

fun swipeScreen(number:Int){
    screen.setAsHorizontalList().scrollToEnd(number)
}
    fun countOfMovies():Int{
        return boxWithTitles.getChildCount(title)
    }
fun clickOnTitle(number:Int){

    boxWithTitles.getChildByInstance(title,number).click()

}



}
