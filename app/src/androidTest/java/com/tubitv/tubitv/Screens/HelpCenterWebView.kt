package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.NativeDeviceFileScreen
import com.tubitv.tubitv.Helpers.TestExceptionWithError
import com.tubitv.tubitv.appPackage
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert.assertThat

/**
 * Created by vburian on 12/10/18.
 */
class HelpCenterWebView : BaseScreen() {
    private val webViewClass = "android.webkit.WebView"
    private val tableWithIcons = "__next"
    private val expectedText = "Why is some Tubi content available on web/mobile but not on any other devices?"
    private val expectedText2 = "While we aim to provide a consistent Tubi experience across all supported devices, we do sometimes test out new content on a limited-device basis before offering it everywhere."
    private val expectedText3 = "Movies and shows that are available on web/mobile only will not be visible when you open Tubi on other devices, even if you've added them to your queue previously. However, when you open Tubi on your phone or web browser, the titles will still be there, ready to watch."
    private val expectedText4 = "Protip: You can help get limited-device content expanded to other devices: watch them! The more that people watch the movies and TV shows on web/mobile, the more likely we are to expand their availability to all platforms!"
    private val sendMessageIccon = appPackage + ":id/support_fab"
    private val cameraButton = appPackage + ":id/belvedere_dialog_row_text"

    init {
        assertTrue("Help Center web view didn't show up", findObjectByClass(webViewClass, true).exists())
    }

    fun clickOnIcon(i: Int) {
        try {
            findElementByIdAnd1LevelDeeper(tableWithIcons, i, true).click()
        } catch (e: UiObjectNotFoundException) {
            throw TestExceptionWithError("Can't click on icon on webview Help Center", e)
        }
    }

    fun clickOnSendMessage(): SendMessageView {
        try {
            findElementById(sendMessageIccon, true).click()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't click on send message Icon", e)
        }
        return SendMessageView()
    }


    fun clcikOnFirstLinkOnGeneral() {
        try {
            findElementByText("Why is some Tubi content available on web/mobile but not on any other devices?", true).click()
            try {
                findElementByText(expectedText, true)
                findElementByText(expectedText2, false)
                findElementByText(expectedText3, false)
                findElementByText(expectedText4, false)
            } catch (e: UiObjectNotFoundException) {
                TestExceptionWithError("Can't find elements by text", e)
            }
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't click on first link for General", e)
        }
    }
}

class SendMessageView() : BaseScreen() {
    private val inputField = appPackage + ":id/message_composer_input_text"
    private val attachButton = appPackage + ":id/attachments_indicator_icon"
    private val sendButton = appPackage + ":id/message_composer_send_btn"
    private val backButton = "Navigate up"
    private val cameraButton = appPackage + ":id/belvedere_dialog_row_text"
    private val attachedPhoto = appPackage + ":id/attachment_image"
    private val deleteAttachment = appPackage + ":id/attachment_delete"
    private val sentMessage = appPackage + ":id/request_user_message_text"
    private val statusOfMessage = appPackage + ":id/request_user_message_status"
    private val statusOfAttachment = appPackage + ":id/request_user_attachment_generic_status"
    private val whenMessageWasSent = appPackage + ":id/request_date_message_text"
    private val addAttachment = appPackage + ":id/floating_action_menu_fab"
    private var sendButtonIsSelected = false
    private val addPhotoFromPhotoAsFile = appPackage + ":id/belvedere_fam_item_documents"
    private val addPhoto = appPackage + ":id/belvedere_fam_item_google_photos"
    private val sentAttachment = appPackage + ":id/request_user_attachment_generic_type"


    init {
        assertTrue("Input field is not present on 'Contact us' screen", findElementById(inputField, true).exists())
        assertTrue("Attach button is not present on SendMessageScreen", findElementById(attachButton, true).exists())
        assertTrue("Send button is not present on SendMessageScreen", findElementById(sendButton, true).exists())
        assertTrue("Back button is not present on SendMessageScreen", uiDevice.findObject(UiSelector().description(backButton)).exists())
    }

    fun provideMessageToDescription(text: String): SendMessageView {
        try {
            findElementById(inputField, false).setText(text)
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find Description field", e)
        }
        return this
    }

    fun clearMessageFromDescription(): SendMessageView {
        try {
            findElementById(inputField, false).clearTextField()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find Description field", e)
        }
        return SendMessageView()
    }

    fun checkIfSendButtonDisabled(): Boolean {
        var enabled = true
        try {
            enabled = findElementById(sendButton, false).isEnabled
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find send button", e)
        }
        return enabled
    }

    fun clickOnDeletePhoto(): SendMessageView {
        try {
            findElementById(deleteAttachment, true).click()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't delete attachment", e)
        }
        return this
    }

    fun checkIfAttachmentDeleted(): Boolean {
        var attachmentStillExists = true
        try {
            attachmentStillExists = findElementById(attachedPhoto, true).exists()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Something wrong with Attachment", e)
        }
        return attachmentStillExists
    }

    fun clickOnSendButton(): SendMessageView {
        sendButton
        var x = 0
        try {
            while (!findElementById(sendButton, false).isEnabled) {
                x++
                if (x > 90) {
                    break
                }
            }
            findElementById(sendButton, false).click()
            sendButtonIsSelected = true
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find Send button", e)
        }
        return SendMessageView()
    }

    fun checkIfMessageWasSent(withAttachment: Boolean) {
        try {
            assertThat("Body of sent message is not present on screen", findElementById(sentMessage, true).exists(), equalTo(true))
        } catch (e: UiObjectNotFoundException) {
            throw   TestExceptionWithError("Can't find message sent ", e)
        }
        try {
            assertThat("Time stamp of message is not correct ", findElementById(whenMessageWasSent, true).text, equalTo("TODAY"))
        } catch (e: UiObjectNotFoundException) {
            throw   TestExceptionWithError("Can't find time stemp of sent message ", e)
        }
        if (withAttachment) {
            try {
                assertThat("Attachment is not sent ", findElementById(sentAttachment, true).exists(), equalTo(true))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find sen't attachment", e)
            }

            try {
                assertThat("Status of attachment is not correct ", findElementById(statusOfAttachment, true).text, equalTo("Delivered"))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find status of sent attachment ", e)
            }
        } else {
            try {
                assertThat("Status of message is not correct ", findElementById(statusOfMessage, true).text, equalTo("Delivered"))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find status of sent message ", e)
            }
        }
    }

    fun clickOnAttachButton(): NativeDeviceFileScreen {
        try {
            findElementById(attachButton, false).click()
            findObjectById(addAttachment, false).click()
            findObjectById(addPhotoFromPhotoAsFile, false).click()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find Attach Button button", e)
        }
        return NativeDeviceFileScreen()
    }

    fun clickOnBackButton(): Any {
        try {
            uiDevice.findObject(UiSelector().description(backButton)).click()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find Back button", e)
        }
        if (sendButtonIsSelected) {
            return HelpCenterWebView()
        } else return MessageNotSentDialog()
    }

    fun clickOnCamera() {
        try {
            findElementById(cameraButton, true).click()
        } catch (e: UiObjectNotFoundException) {
            TestExceptionWithError("Can't find Back button", e)
        }
    }

    class MessageNotSentDialog() : BaseScreen() {
        private val messageNotSent = appPackage + ":id/alertTitle"
        private val message = "android:id/message"
        private val cancelButton = "android:id/button2"
        private val deleteButton = "android:id/button1"

        init {
            try {
                assertThat("Text is not correct on 'Contact us' when click back", findElementById(messageNotSent, true).text, equalTo("Message not sent"))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find 'Message not sent' after cliked 'Back' button on 'Contact us' screen", e)
            }

            try {
                assertThat("Message is not correct on 'Contact us' when click back", findElementById(message, true).text, equalTo("Going back will delete your message. Are you sure you want to delete it?"))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find 'Message not sent' after cliked 'Back' button on 'Contact us' screen", e)
            }
            try {
                assertThat("Cancel button is not correct on 'Contact us' when click back", findElementById(cancelButton, true).text, equalTo("CANCEL"))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find 'Cancel' button after clicked 'Back' button on 'Contact us' screen", e)
            }
            try {
                assertThat("'Delete' button is not correct on 'Contact us' when click back", findElementById(deleteButton, true).text, equalTo("DELETE"))
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find 'Delete' button after clicked 'Back' button on 'Contact us' screen", e)
            }
        }


        fun clickOnDelete(): HelpCenterWebView {
            try {
                findElementById(deleteButton, true).click()
            } catch (e: UiObjectNotFoundException) {
                throw   TestExceptionWithError("Can't find 'Delete' button after clicked 'Back' button on 'Contact us' screen", e)
            }
            return HelpCenterWebView()
        }
    }

}