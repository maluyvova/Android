package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.*
import com.tubitv.tubitv.Helpers.TestException
import junit.framework.Assert

/**
 * Created by vburian on 2/26/18.
 */
class PlayBackScreen : BaseScreen() {
    private var playBackWindow = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/controller_panel"))
    private var playButton = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_play_toggle_ib"))
    private var seek = UiSelector().resourceId(appPackage + ":id/view_tubi_controller_rewind_ib")
    private val s = uiDevice.findObject(By.res(appPackage + ":id/view_tubi_controller_subtitles_ib"))
    //  private val sub=uiDevice.wait(Until.findObject(By.text("Fire With Fire")), globalTimeout)
    //  private val nameOfTile=uiDevice.findObject(By.text("Fire With Fire"))
    private var subtittless = UiDeviceID(appPackage + ":id/view_tubi_controller_subtitles_ib")
    // private var subtittles =UiSelector().resourceId(appPackage+":id/view_tubi_controller_subtitles_ib")
    private var quality = UiDeviceID(appPackage + ":id/view_tubi_controller_quality_ib")
    private var titleText = UiDeviceID(appPackage + ":id/view_tubi_controller_title")
    private var scrollControlSeek = UiScrollable(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_seek_bar"))
    private var controllerSeek = UiSelector().resourceId(appPackage + ":id/view_tubi_controller_seek_bar")
    private var rightTimer = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_remaining_time"))
    private var leftTimer = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_elapsed_time"))
    private var fifteenMinForwardButton = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_forward_ib"))
    private var fifteenMinBackButton = uiDevice.findObject(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_rewind_ib"))
    private val autoplay = UiDeviceID(appPackage + ":id/play_next_container")

    public fun textOfRightTimer(): String {
        var time = ""
        try {
            time = rightTimer.text
        } catch (e: UiObjectNotFoundException) {
            wakeUpScreen()
            time = rightTimer.text
        }
        return time
    }

    public fun clickOnQuality(){
        if(quality.waitForExists(globalTimeout)){
            quality.click() }
        else {
            wakeUpScreen()
            quality.click() }
    }


    public fun wakeUpScreen() {
        uiDevice.swipe(385, 317, 1500, 483, 2)
    }

    public fun scrollSeekBar() {
        scrollControlSeek.setAsHorizontalList().scrollToEnd(1)
    }

    public fun seekToAutoPlay(movieOrSerial: String): AutoPlay {
        var substring = 0;
        var timeTilliSeek = 0
        if (movieOrSerial.equals("Serial")) {
            substring = 5
            timeTilliSeek = 15
        } else {
            timeTilliSeek = 45
            substring = 3
        }
        var i = 0
        var k = 0
        var time = textOfRightTimer()
        scrollControlSeek.dragTo(rightTimer, 1)
        var times = textOfRightTimer();
        if (!rightTimer.waitForExists(globalTimeout)) {
            scrollControlSeek.setAsHorizontalList().scrollBackward(1)
        } else if (times.equals("00:00") || times.equals("-00:00")) {
            if (fifteenMinBackButton.exists()) {
                for (j in 0..4) {
                    try {
                        fifteenMinBackButton.click()
                        if (autoplay.exists()) {
                            return AutoPlay()
                        }
                    } catch (e: UiObjectNotFoundException) {
                        if (autoplay.waitForExists(globalTimeout)) {
                            return AutoPlay()
                        }
                    }
                }
            } else if (!rightTimer.exists()) {
                wakeUpScreen()
                scrollControlSeek.setAsHorizontalList().scrollBackward(1)
            } else scrollControlSeek.setAsHorizontalList().scrollBackward(1)
        }
        while (!(time.substring(1, 2).toInt() == 0) || !(time.substring(2, 3).toInt() == 0) || k > 50) {
            if (i > 5) {
                scrollControlSeek.setAsHorizontalList().scrollBackward(1)
                i = 0
            }
            if (!fifteenMinBackButton.exists()) {
                if (!fifteenMinBackButton.waitForExists(globalTimeout)) {
                    wakeUpScreen()
                }
            }
            try {
                fifteenMinForwardButton.click()
            } catch (e: UiObjectNotFoundException) {
                wakeUpScreen()
                i++
            }
            if (!rightTimer.exists()) {
                wakeUpScreen()
            }
            time = textOfRightTimer()
            k++
        }
        if (movieOrSerial.equals("Serial")) {
            fifteenMinForwardButton.click()
            fifteenMinForwardButton.click()
        }
        return AutoPlay()
    }

    fun getNameOfTitleFromPlayback(): String {
        var textTtitle = ""
        if (titleText.waitForExists(globalTimeout)) {
            textTtitle = titleText.text
        } else {
            wakeUpScreen()
            textTtitle = titleText.text
        }
        return textTtitle
    }

    fun waitUntilAdsfinishes() {
        wakeUpScreen()
        while (!rightTimer.exists()) {
            Thread.sleep(1000)
            wakeUpScreen()
        }
    }

    public fun waitForPlayBack(): String {
        if (playButton.waitForExists(globalTimeout))
            return titleText.text
        else {
            wakeUpScreen()
            return titleText.text
        }
    }

    public fun clickPlay() {
        playButton.click()
    }

    public fun checkIfSubtitlesIsSelected(): String {
        return subtittless.text
    }
    class AutoPlay() : BaseScreen() {
        private val autoplayScrollable = UiScrollable(UiSelector().resourceId(appPackage + ":id/play_next_container"))
        private val autoplay = UiDeviceID(appPackage + ":id/play_next_container")
        private val autoplayUiSelector = UiSelector().resourceId(appPackage + ":id/play_next_container")
        private val nameOfNextTitle = UiDeviceID(appPackage + ":id/title")
        private val nameOfNextTitleUiSelector = UiSelector().resourceId(appPackage + ":id/title")
        private val yearAndDurationOfNextTitle = UiDeviceID(appPackage + ":id/vaudTextView_duration")
        private val captionIconOfNextTtile = UiDeviceID(appPackage + ":id/imageView_caption")
        private val playButtonForTitle = UiDeviceID(appPackage + ":id/continue_play_btn")
        private val togleButton = UiDeviceID(appPackage + ":id/button_toggle")
        private val timer = UiDeviceID(appPackage + ":id/timer_text")
        private val nextNextAvailableTitle = UiDeviceID(appPackage + ":id/poster")
        private val autoplayBox = UiCollection(UiSelector().resourceId(appPackage + ":id/autoplay_drawer"))
        private val containerOfNextTitle = autoplayBox.getChildByInstance(autoplayUiSelector, 1)
        private val titleOnLefSideWhenAutoplayIsHoden = UiDeviceID(appPackage + ":id/next_item")

        //Name,Duration,caption have same id but they inside containerOfNextTitle
        init {
            if (autoplay.waitForExists(waitForAutoplay)) {
                Assert.assertTrue("Autoplay didn't pop-up, possible something wrong with seekToAutoplay ", 2 > 1)
            } else if (togleButton.waitForExists(globalTimeout)) {
                if (!autoplay.exists()) {
                    togleButton.click()
                }
                Assert.assertTrue("Autoplay didn't pop-up, possible something wrong with seekToAutoplay ", 2 > 1)
            } else Assert.assertEquals("Autoplay didn't pop-up, possible something wrong with seekToAutoplay ", 2, 5)
        }

        public val titleOnLefSideWhenAutoplayIsHodens get() = this.titleOnLefSideWhenAutoplayIsHoden
        public val textFromFirstTitleAutoplay get() = nameOfNextTitle.text
        public val textFromNextTitleAutoplay get() = containerOfNextTitle.getChild(nameOfNextTitleUiSelector).text
        public val textFromAutoplayTimer get() = timer.text
        fun playTitleFromAutoplay() {
            if (playButtonForTitle.waitForExists(globalTimeout)) {
                playButtonForTitle.click()
            } else throw TestException("Play button is not present on first title for autoplay")
        }

        fun slideToNextTitle(jstOneTitle: Boolean) {
            var flag = true
            if (!jstOneTitle) {
                while (flag) {
                    try {
                        !textFromNextTitleAutoplay.equals(null)
                        autoplayScrollable.setAsHorizontalList().scrollForward()
                    } catch (e: UiObjectNotFoundException) {
                        flag = false
                        return
                    }
                }
            }
            autoplayScrollable.setAsHorizontalList().scrollForward()
        }

        fun slideToPrevoisTitle() {
            autoplayScrollable.setAsHorizontalList().scrollBackward()
        }

        fun hideAutoplay() {
            togleButton.waitForExists(globalTimeout)
            togleButton.click()
        }

        fun selectTitleNextTitleFromHiddenAutoplay() {
            titleOnLefSideWhenAutoplayIsHoden.waitForExists(globalTimeout)
            titleOnLefSideWhenAutoplayIsHoden.click()
        }

        fun wait20secForNextTitle(): Boolean {
            return timer.waitUntilGone(28000)
        }

        fun verifyThatTheTimerIsNotExists(): Boolean {
            var exist = false
            try {
                timer.click()
                exist = true
            } catch (e: UiObjectNotFoundException) {
                exist = false
            }
            return exist
        }
    }


}


