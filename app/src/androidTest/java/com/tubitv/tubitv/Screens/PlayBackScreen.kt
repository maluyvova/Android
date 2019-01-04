package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.*
import com.tubitv.tubitv.*
import com.tubitv.tubitv.Enomus.TypeOfContent
import com.tubitv.tubitv.Helpers.TestException
import junit.framework.Assert

/**
 * Created by vburian on 2/26/18.
 */
class PlayBackScreen : BaseScreen() {
    private var playButton = appPackage + ":id/view_tubi_controller_play_toggle_ib"
    private var subtittless = appPackage + ":id/view_tubi_controller_subtitles_ib"
    private var titleText = appPackage + ":id/view_tubi_controller_title"
    private var scrollControlSeek = UiScrollable(UiSelector().resourceId(appPackage + ":id/view_tubi_controller_seek_bar"))
    private var rightTimer = appPackage + ":id/view_tubi_controller_remaining_time"
    private var leftTimer = appPackage + ":id/view_tubi_controller_elapsed_time"
    private var fifteenMinForwardButton = appPackage + ":id/view_tubi_controller_forward_ib"
    private var fifteenMinBackButton = appPackage + ":id/view_tubi_controller_rewind_ib"
    private val autoplay = appPackage + ":id/play_next_container"
    private val tagleForAutoplay = appPackage + ":id/button_toggle"
    private val backButton = appPackage + ":id/back_button"
    private val ratingModal = "android:id/content"
    private val ratingText = "Loving your Tubi TV app?"
    private val noThanksButtonForRating = "android:id/button2"
    private val togleButton = appPackage + ":id/button_toggle"

    fun textOfRightTimer(): String {
        var time = ""
        try {
            time = findElementById(rightTimer, false).text
        } catch (e: UiObjectNotFoundException) {
            wakeUpScreen()
            time = findElementById(rightTimer, false).text
        }
        return time
    }

    fun textOfLeftTimer(): String {
        var time = ""
        try {
            time = findElementById(leftTimer, false).text
        } catch (e: UiObjectNotFoundException) {
            wakeUpScreen()
            time = findElementById(leftTimer, false).text
        }
        return time
    }

    fun wakeUpScreen() {
        uiDevice.swipe(385, 317, 1500, 483, 2)
    }

    fun scrollSeekBar() {
        scrollControlSeek.setAsHorizontalList().scrollToEnd(1)
    }

    fun checkIfAutoplayExists(): Boolean {
        return findElementById(autoplay, false).waitForExists(waitForAutoplay) && findElementById(tagleForAutoplay,false).waitForExists(globalTimeout)
    }

    fun checkIfTitleFinished(time: String): Boolean {
        return (time.equals("00:00") && time.equals("-00:00"))
    }

    fun seekToAutoPlay(typeOfContent: TypeOfContent): AutoPlay {
        var substring = 0;
        var timeTilliSeek = 0
        if (typeOfContent.equals(TypeOfContent.SERIALS)) {
            substring = 5
            timeTilliSeek = 15
        } else {
            timeTilliSeek = 45
            substring = 3
        }
        var i = 0
        var k = 0
        var time = textOfRightTimer()
        scrollControlSeek.dragTo(findObjectById(rightTimer, false), 1)
        var times = textOfRightTimer();
        if (!findElementById(rightTimer, false).waitForExists(globalTimeout)) {
            scrollControlSeek.setAsHorizontalList().scrollBackward(1)
        } else if (times.equals("00:00") || times.equals("-00:00")) {
            if (findElementById(fifteenMinBackButton, false).exists()) {
                for (j in 0..4) {
                    try {
                        findObjectById(fifteenMinBackButton, false).click()
                        if (findElementById(autoplay, false).exists()) {
                            return AutoPlay()
                        }
                    } catch (e: UiObjectNotFoundException) {
                        if (findElementById(autoplay, false).waitForExists(globalTimeout)) {
                            return AutoPlay()
                        }
                    }
                }
            } else if (!findElementById(rightTimer, false).exists()) {
                wakeUpScreen()
                scrollControlSeek.setAsHorizontalList().scrollBackward(1)
            } else scrollControlSeek.setAsHorizontalList().scrollBackward(1)
        }
        while (!(time.substring(1, 2).toInt() == 0) || !(time.substring(2, 3).toInt() == 0) || k > 50) {
            if (i > 5) {
                scrollControlSeek.setAsHorizontalList().scrollBackward(1)
                i = 0
            }
            if (!findElementById(fifteenMinBackButton, false).exists()) {
                if (!findElementById(fifteenMinBackButton, false).waitForExists(globalTimeout)) {
                    wakeUpScreen()
                }
            }
            try {
                findObjectById(fifteenMinForwardButton, false).click()
            } catch (e: UiObjectNotFoundException) {
                wakeUpScreen()
                i++
            }
            if (!findElementById(rightTimer, false).exists()) {
                wakeUpScreen()
            }
            time = textOfRightTimer()
            k++
        }
        if (typeOfContent.equals(TypeOfContent.SERIALS)) {
            findObjectById(fifteenMinForwardButton, false).click()
            findObjectById(fifteenMinForwardButton, false).click()
        }
        return AutoPlay()
    }

    fun seekMiddleOfPlayback(): PlayBackScreen {
        textOfRightTimer()
        scrollControlSeek.dragTo(findObjectById(playButton, false), 0)
        return this
    }

    fun seekToTheEnd(): PlayBackScreen {
        try {
            textOfRightTimer()
            scrollControlSeek.dragTo(findObjectById(rightTimer, false), 0)
        } catch (e: UiObjectNotFoundException) {
            TestException("Can't find play back elements")
        }
        return this
    }

    fun clickOnNativeBackForMovie(): MovieDatailScreen {
        textOfRightTimer()
        findObjectById(backButton, true).click()
        if (findElementByIdMediumtTimeOut(ratingModal, true).exists()) {
            if (findElementByText(ratingText, false).exists()) {
                findObjectById(noThanksButtonForRating, false).click()
            }
        }
        return MovieDatailScreen()
    }

    fun clickOnNativeBackForSerial(): SerialsScreen {
        textOfRightTimer()
        findObjectById(backButton, true).click()
        if (findElementByIdShortTimeOut(ratingModal, true).exists()) {
            if (findElementByText(ratingText, false).exists()) {
                findObjectByClass(noThanksButtonForRating, false).click()
            }
        }
        return SerialsScreen()
    }

    fun seekToTheBegining(): PlayBackScreen {
        try {
            textOfRightTimer()
            scrollControlSeek.dragTo(findObjectById(leftTimer, false), 0)
        } catch (e: UiObjectNotFoundException) {
            TestException("Can't find play back elements")
        }
        return this
    }

    fun getNameOfTitleFromPlayback(): String {
        var textTtitle = ""
        if (findElementById(titleText, false).waitForExists(globalTimeout)) {
            textTtitle = findElementById(titleText, false).text
        } else {
            wakeUpScreen()
            textTtitle = findElementById(titleText, false).text
        }
        return textTtitle
    }

    fun waitUntilAdsfinishes(): PlayBackScreen {
        wakeUpScreen()
        while (!findElementById(rightTimer, false).exists()) {
            Thread.sleep(1000)
            wakeUpScreen()
        }
        return this
    }

    public fun waitForPlayBack(): String {
        if (findElementById(playButton, false).waitForExists(globalTimeout))
            return findElementById(titleText, false).text
        else {
            wakeUpScreen()
            return findElementById(titleText, false).text
        }
    }

    public fun clickPlay(): PlayBackScreen {
        findObjectById(playButton, false).click()
        return this
    }

    public fun checkIfSubtitlesIsSelected(): String {
        return findElementById(subtittless, false).text
    }

    class AutoPlay() : BaseScreen() {
        private var titleText = appPackage + ":id/view_tubi_controller_title"
        private val autoplayScrollable = UiScrollable(UiSelector().resourceId(appPackage + ":id/play_next_container"))
        private val autoplay = appPackage + ":id/play_next_container"
        private val autoplayUiSelector = UiSelector().resourceId(appPackage + ":id/play_next_container")
        private val nameOfNextTitle = appPackage + ":id/title"
        private val nameOfNextTitleUiSelector = UiSelector().resourceId(appPackage + ":id/title")
        private val playButtonForTitle = appPackage + ":id/continue_play_btn"
        private val togleButton = appPackage + ":id/button_toggle"
        private val timer = appPackage + ":id/timer_text"
        private val autoplayBox = UiCollection(UiSelector().resourceId(appPackage + ":id/autoplay_drawer"))
        private val containerOfNextTitle = autoplayBox.getChildByInstance(autoplayUiSelector, 1)
        private val titleOnLefSideWhenAutoplayIsHoden = appPackage + ":id/next_item"

        //Name,Duration,caption have same id but they inside containerOfNextTitle
        init {
            if (findElementById(autoplay, false).waitForExists(waitForAutoplay)) {
                Assert.assertTrue("Autoplay didn't pop-up, possible something wrong with seekToAutoplay ", 2 > 1)
            } else if (findElementById(togleButton, false).waitForExists(globalTimeout)) {
                if (!findElementById(autoplay, false).exists()) {
                    findElementById(togleButton, false).click()
                }
                Assert.assertTrue("Autoplay didn't pop-up, possible something wrong with seekToAutoplay ", 2 > 1)
            } else Assert.assertEquals("Autoplay didn't pop-up, possible something wrong with seekToAutoplay ", 2, 5)
        }

        public val titleOnLefSideWhenAutoplayIsHodens get() = this.findObjectById(titleOnLefSideWhenAutoplayIsHoden, false)
        public val textFromFirstTitleAutoplay get() = findObjectById(nameOfNextTitle, false).text
        public val textFromNextTitleAutoplay get() = containerOfNextTitle.getChild(nameOfNextTitleUiSelector).text
        public val textFromAutoplayTimer get() = findObjectById(timer, false).text
        fun playTitleFromAutoplay(): PlayBackScreen {
            if (findElementById(playButtonForTitle, false).waitForExists(globalTimeout)) {
                findObjectById(playButtonForTitle, false).click()
            } else throw TestException("Play button is not present on first title for autoplay")
            return PlayBackScreen()
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
            findObjectById(togleButton, true).click()
        }

        fun selectTitleNextTitleFromHiddenAutoplay() {
            findObjectById(titleOnLefSideWhenAutoplayIsHoden, true).click()
        }

        fun wait20secForNextTitle(): Boolean {
            return findElementById(timer, false).waitUntilGone(28000)
        }

        fun verifyThatTheTimerIsNotExists(): Boolean {
            var exist = false
            try {
                findObjectById(timer, false).click()
                exist = true
            } catch (e: UiObjectNotFoundException) {
                exist = false
            }
            return exist
        }
    }


}


