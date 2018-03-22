package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.globalTimeout
import com.tubitv.tubitv.moviesListTimeout
import junit.framework.Assert
import java.lang.reflect.AccessibleObject
import java.util.*

/**
 * Created by vburian on 2/19/18.
 */
open class HomeScreen:BaseScreen(){
    protected val firstListOfAllObjects = UiCollection(UiSelector().resourceId("com.tubitv:id/view_category_recycler"))
    public val scrollHomePage=UiScrollable(UiSelector().resourceId("com.tubitv:id/view_category_recycler"))
    private val categoryList = UiSelector().resourceId("com.tubitv:id/view_content_recycler_ll")
    private val textOfMovie = UiSelector().resourceId("com.tubitv:id/view_home_content_title_tv")
    private val titleOfMovie =    UiSelector().resourceId("com.tubitv:id/view_home_content_iv")
    public val textOFCategory =UiSelector().resourceId("com.tubitv:id/view_content_recycler_category_title")
    private val headerLine=UiSelector().resourceId("com.tubitv:id/view_content_recycler_header")
    private val featuredTitles=UiSelector().resourceId("com.tubitv:id/view_home_content_iv")
    private val featuredTitlesText=UiSelector().resourceId("com.tubitv:id/view_home_content_tv_title")
    private val sideCategoryMenu=uiDevice.findObject(UiSelector().className("android.widget.ImageButton"))



    init{

        Assert.assertTrue("Expected first List of All Objects is not displayed",firstListOfAllObjects.waitForExists(moviesListTimeout))
        Assert.assertTrue("Expected titles is not displayed",getTitleFromGrid().waitForExists(moviesListTimeout))
    }
    protected fun getGrid(number:Int) =
            firstListOfAllObjects.getChildByInstance(categoryList,number) // it's object of all category moivies in homepage

    private fun getFirstTitleTextInFeatured()=
            getGrid(0).getChild(featuredTitlesText)

    public val textOfTitleInFeaturedCategor get() = getFirstTitleTextInFeatured().text

    public fun clickOnTitleInFeaturedCateg():GotIt{
        getFirstTitleTextInFeatured().click()
        return GotIt()
    }

    fun ScrollToSpecificCategory(s:String ){
        scrollHomePage.scrollTextIntoView("$s")

    }

    private fun getTextOFMovie() =
            getGrid(1).getChild(textOfMovie) //got a first element from the list of movies

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

    public fun waitForExistsFirstCategoryText(text:String){
      uiDevice.findObject(getTextOfCategory().selector.text(text)).waitForExists(globalTimeout)
    }

   //private fun som(){


      // uiDevice.findObject(textOFCategory.text("fd")).waitForExists("fdf") CHECK THIS OUT
  // }

    fun clickOnSidecategorButton():SideCategoryMenuScreen{
        sideCategoryMenu.click()
        return SideCategoryMenuScreen()
    }


    public val textCategory=getTextOfCategory().text

    public val title get() = getTextOFMovie().text //get text title form the home page

    public fun clickOnTitleNoGotIt():MovieDatailScreen{
        getTextOFMovie().click()
        return MovieDatailScreen()
    }


    public fun clickOnTitle():GotIt{
        getTextOFMovie().click()
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
       private val titleInContinueWatching = uiDevice.findObject(UiSelector().resourceId("com.tubitv:id/view_home_content_continue_iv"))

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


    class QueueScreen(){
        var homescreen=HomeScreen()
        private val queueList=UiSelector().resourceId("com.tubitv:id/view_content_recycler_ll")
        private val titlesInQueue=UiSelector().resourceId("com.tubitv:id/view_home_content_iv")
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





    }



