package com.tubitv.tubitv.Screens

import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.tubitv.tubitv.Helpers.TestException
import com.tubitv.tubitv.Helpers.TextExceptionWithError
import com.tubitv.tubitv.appPackage
import com.tubitv.tubitv.globalTimeout
import junit.framework.Assert
import junit.framework.Assert.assertTrue

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
            TextExceptionWithError("Can't click on icon on webview Help Center", e)
        }
    }

    fun clickOnSendMessage(): SendMessageView {
        try {
            findElementById(sendMessageIccon, true).click()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't click on send message Icon", e)
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
                TextExceptionWithError("Can't find elements by text", e)
            }
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't click on first link for General", e)
        }
    }
}

class SendMessageView() : BaseScreen() {
    private val descriptionField = appPackage + ":id/contact_fragment_description"
    private val attachButton = appPackage + ":id/fragment_contact_zendesk_attachment"
    private val sendButton = appPackage + ":id/fragment_contact_zendesk_menu_done"
    private val backButton = "Navigate up"
    private val cameraButton = appPackage + ":id/belvedere_dialog_row_text"
    private val attachedPhoto = appPackage + ":id/attachment_image"
    private val deleteAttachment = appPackage + ":id/attachment_delete"


    init {
        assertTrue("Description field is not present on SendMessageScreen", findElementById(descriptionField, true).exists())
        assertTrue("Attach button is not present on SendMessageScreen", findElementById(attachButton, true).exists())
        assertTrue("Send button is not present on SendMessageScreen", findElementById(sendButton, true).exists())
        assertTrue("Back button is not present on SendMessageScreen", uiDevice.findObject(UiSelector().description(backButton)).exists())
    }

    fun provideMessageToDescription(text: String): SendMessageView {
        try {
            findElementById(descriptionField, false).setText(text)
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find Description field", e)
        }
        return this
    }

    fun clearMessageFromDescription(): SendMessageView {
        try {
            findElementById(descriptionField, false).clearTextField()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find Description field", e)
        }
        return SendMessageView()
    }

    fun checkIfSendButtonDisabled(): Boolean {
        var enabled = true
        try {
            enabled = findElementById(sendButton, false).isEnabled
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find send button", e)
        }
        return enabled
    }

    fun clickOnDeletePhoto():SendMessageView {
        try {
            findElementById(deleteAttachment, true).click()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't delete attachment", e)
        }
        return this
    }

    fun checkIfAttachmentDeleted(): Boolean {
        var attachmentStillExists = true
        try {
            attachmentStillExists = findElementById(attachedPhoto, true).exists()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Something wrong with Attachment", e)
        }
        return attachmentStillExists
    }

    fun clickOnSendButton(withAttachment: Boolean): HelpCenterWebView {
        var x = 0
        try {
            while (!findElementById(sendButton, false).isEnabled) {
                x++
                if (x > 90) {
                    break
                }
            }
            if (withAttachment) {
                findElementById(attachedPhoto, true)
                findElementById(deleteAttachment, true)

            }

            findElementById(sendButton, false).click()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find Send button", e)
        }
        return HelpCenterWebView()
    }

    fun clickOnAttachButton(): SendMessageView {
        try {
            findElementById(attachButton, false).click()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find Attach Button button", e)
        }
        return this
    }

    fun clickOnBackButton(): HelpCenterWebView {
        try {
            uiDevice.findObject(UiSelector().description(backButton)).click()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find Back button", e)
        }
        return HelpCenterWebView()
    }

    fun clickOnCamera() {
        try {
            findElementById(cameraButton, true).click()
        } catch (e: UiObjectNotFoundException) {
            TextExceptionWithError("Can't find Back button", e)
        }
    }


}