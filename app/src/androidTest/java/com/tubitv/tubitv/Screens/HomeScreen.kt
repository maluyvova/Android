package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.LaunchAppWithFacebook
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert
import java.lang.reflect.AccessibleObject
import java.util.*

/**
 * Created by vburian on 2/19/18.
 */
open class HomeScreen:BaseScreen(){

    protected val firstListOfAllObjects = UiCollection(UiSelector().resourceId(appPackage + ":id/view_category_recycler"))
    public val scrollHomePage=UiScrollable(UiSelector().resourceId(appPackage+":id/view_category_recycler"))
    private val categoryList = UiSelector().resourceId(appPackage+":id/view_content_recycler_ll")
    private val textOfMovie = UiSelector().resourceId(appPackage+":id/view_home_content_title_tv")
    private val titleOfMovie =    UiSelector().resourceId(appPackage+":id/view_home_content_iv")
    public val textOFCategory =UiSelector().resourceId(appPackage+":id/view_content_recycler_category_title")
    private val headerLine=UiSelector().resourceId(appPackage+":id/view_content_recycler_header")
    private val featuredTitles=UiSelector().resourceId(appPackage+":id/view_home_content_iv")
    private val featuredTitlesText=UiSelector().resourceId(appPackage+":id/view_home_content_tv_title")
    private val sideCategoryMenu=uiDevice.findObject(UiSelector().className("android.widget.ImageButton"))
    private val treeDotsSetingsButton=uiDevice.findObject(UiSelector().description("More options"))
    private val containerOfTitlesSmaller=UiSelector().resourceId(appPackage+":id/view_content_recycler")
    private val textOfTitleInContnueWatching=UiDeviceID(appPackage+":id/view_home_content_continue_title_tv")
    private val playButtonForContinueWatching=UiDeviceID(appPackage+":id/view_home_content_continue_play_btn")
    private val searchButton=uiDevice.findObject(UiSelector().description("Search"))
    private val searchField=UiDeviceID(appPackage+":id/nav_app_bar_main_search")

    init{

        Assert.assertTrue("Expected first List of All Objects is not displayed",firstListOfAllObjects.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected titles is not displayed",getTitleFromGrid().waitForExists(moviesListTimeout))
    }
    protected fun getGrid(number:Int) =
            firstListOfAllObjects.getChildByInstance(categoryList,number) // it's object of all category moivies in homepage

    private fun getFirstTitleTextInFeatured()=
            getGrid(0).getChild(featuredTitlesText)

    public fun clickAndSendTextToSearch(text:String):SearchScreen{
      searchButton.click()
        searchField.setText(text)
        return SearchScreen()
    }
    public val textOfTitleInFeaturedCategor get() = getFirstTitleTextInFeatured().text

    public fun clickOnTitleInFeaturedCateg():GotIt{
        getFirstTitleTextInFeatured().click()
        return GotIt()
    }
    public val titleInContinueWatching get()=textOfTitleInContnueWatching.text

    fun ScrollToSpecificCategory(s:String ){
        scrollHomePage.scrollTextIntoView("$s")
    }

    fun scrollToTheEndAndClickOnSubCategory():SubCategoryScreen{
        var number:Int=0
        while(number!=18){
            scrollHomePage.scrollToEnd(1)
            number++
        }
        for (i in 1..6){
           val box= getGrid(i).getChild(textOFCategory)
            if( box.text=="Special Interest")
                box.click()
            break
        }
     return SubCategoryScreen()
    }

    fun horisontalScrollTitles(swipes:Int){
        val scroll=UiScrollable(containerOfTitlesSmaller.index(1))
        scroll.setAsHorizontalList().scrollToEnd(swipes)
    }
    fun getTextOfTitleWithIndex():String{
      val text=  containerOfTitlesSmaller.index(1).resourceId(appPackage+":id/view_home_content_title_tv").index(1)
      return uiDevice.findObject(text).text
    }

    public fun getTextOFMovie(numberOfView:Int) =
            getGrid(numberOfView).getChild(textOfMovie) //got a first element from the list of movies

    private fun getHeaderLine()=
            getGrid(1).getChild(headerLine)

    public fun clickOnThreeDots(){
        getHeaderLine().getChild(UiSelector().className("android.widget.ImageView")).click()
    }

    private fun getTitleFromGrid()=
            getGrid(1).getChild(titleOfMovie)

    public   fun getTextOfCategory()=
            getGrid(1).getChild(textOFCategory)

public fun text():Objects{
    val text=getTextOfCategory()
    return text()
}



    public fun clickOnThreeDotsSetings():SettingSmallWindowInRightCorner{
        treeDotsSetingsButton.click()
        return SettingSmallWindowInRightCorner()
    }



    public fun waitForExistsFirstCategoryText(text:String){
      uiDevice.findObject(getTextOfCategory().selector.text(text)).waitForExists(globalTimeout)
    }
    public fun clickOnCategoryWithText(text:String){
      uiDevice.findObject(getTextOfCategory().selector.text(text)).click()
    }

   //private fun som(){


      // uiDevice.findObject(textOFCategory.text("fd")).waitForExists("fdf") CHECK THIS OUT
  // }

    fun clickOnSidecategorButton():SideCategoryMenuScreen{
        sideCategoryMenu.click()
        return SideCategoryMenuScreen()
    }


    public val textCategory=getTextOfCategory().text

    public val title get() = getTextOFMovie(1).text //get text title form the home page

    public fun clickOnTitleNoGotIt():MovieDatailScreen{
        getTextOFMovie(1).click()
        return MovieDatailScreen()
    }


    public fun clickOnTitle():GotIt{
        getTextOFMovie(1).click()
        return GotIt()
    }
    public fun clickBack(){
        val pressBack = uiDevice.pressBack()
    }

    public fun longPress():AddToQueue{
        getTitleFromGrid().dragTo(getTitleFromGrid(),10)

return AddToQueue()
    }

    public fun longPressToRemoveFromQueue():AddToQueue{
        getTitleFromGrid().dragTo(getTitleFromGrid(),10)
        return AddToQueue()
    }

   open class HomeScreenWithContinueWatching():BaseScreen(){
       private val titleInContinueWatching = uiDevice.findObject(UiSelector().resourceId(appPackage+":id/view_home_content_continue_iv"))

        init{
            Assert.assertTrue("Title is not added to 'History' after watcing 30 sec and click Back",titleInContinueWatching.waitForExists(moviesListTimeout))
        }
        public fun removeFromHistory(){
            titleInContinueWatching.dragTo(titleInContinueWatching,10)
            uiDevice.findObject(UiSelector().resourceId("android:id/select_dialog_listview")).waitForExists(globalTimeout)
        }
    }



    class RemoveFormHistoryScreen():BaseScreen(){
        private val boxWithRemoveFromHistory = UiCollection(UiSelector().resourceId("android:id/select_dialog_listview"))
        private val removeFromHistory=UiSelector().resourceId("android:id/text1")
        init{
            Assert.assertTrue("Title is not added to 'History' after watcing 30 sec and click Back",boxWithRemoveFromHistory.waitForExists(moviesListTimeout))
        }
        protected fun getBottonRemoveFromHistory()=
            boxWithRemoveFromHistory.getChildByInstance(removeFromHistory,0)


        public fun clickOnRemoveFromHistory(){
            getBottonRemoveFromHistory().click()


        }

    }


    class QueueScreen():BaseScreen(){
        var homescreen=HomeScreen()
        private val queueList=UiSelector().resourceId(appPackage+":id/view_content_recycler_ll")
        private val titlesInQueue=UiSelector().resourceId(appPackage+":id/view_home_content_iv")
       init{Assert.assertTrue("Expected queue is not displayed",getQueuFromGrid().waitForExists(globalTimeout))}
        protected fun getGrid(number:Int) =
                homescreen.firstListOfAllObjects.getChildByInstance(queueList,number) // it's object of all category moivies in

        private fun getQueuFromGrid()=
                getGrid(1).getChild(homescreen.textOfMovie)

        private fun getFirstTitleInQueu()=
            getGrid(1).getChild(homescreen.textOfMovie)

        public val textFromFirstTitleInQueue get()=getQueuFromGrid().text



    }


     class AddToQueue():BaseScreen(){
         private val addToQueueLongClick=uiDevice.findObject(UiSelector().resourceId("android:id/text1"))
     init{
         Assert.assertTrue("Expected small window with add to queue is not displayed",addToQueueLongClick.waitForExists(moviesListTimeout))

     }

         public fun clickAddToQueueAfterLongClickWithoutReturn(){
             addToQueueLongClick.click()
         }


         public fun clickAddToQueueAfterLongClick():QueueScreen{
             addToQueueLongClick.click()
             return QueueScreen()
         }
     }

    class Serials():BaseScreen(){
        private val titleWithSerial=uiDevice.findObject(UiSelector().text("Most Popular TV Shows"))

        public  fun clickOnSerialCategory():MoviesByCategoryScreen{
            titleWithSerial.click()
            return MoviesByCategoryScreen()
        }
    }

    class SettingSmallWindowInRightCorner():BaseScreen(){
       private val boxWithSettingsItems=UiCollection(UiSelector().className("android.widget.ListView"))
        private val boxWithSettingsForInit=uiDevice.findObject(UiSelector().className("android.widget.FrameLayout"))
         private val SettingsAboutHelpCenter=UiSelector().resourceId(appPackage+":id/title")
    init {
        Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", boxWithSettingsForInit.waitForExists (moviesListTimeout))

    }

        fun clickOnSettings():SettingsScreen{
            boxWithSettingsItems.getChildByInstance(SettingsAboutHelpCenter,0).click()
            return SettingsScreen()
        }
    }
    class History():BaseScreen(){
        private val removeFromHistory=uiDevice.findObject(UiSelector().text("Remove from history"))
        private val addToQueue=uiDevice.findObject(UiSelector().text("Add to queue"))
        init {
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", removeFromHistory.waitForExists (moviesListTimeout))
            Assert.assertTrue("Expected small pop-up with Seting,About,Help Center not showed up", addToQueue.waitForExists (moviesListTimeout))

        }

        public fun clickOnRemoveFromHisory(){
            removeFromHistory.click()
        }
    }



    }



