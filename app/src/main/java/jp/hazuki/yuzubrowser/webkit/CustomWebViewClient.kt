/*
 * Copyright (C) 2017 Hazuki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.hazuki.yuzubrowser.webkit

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Message
import android.view.KeyEvent
import android.webkit.*
import com.crashlytics.android.Crashlytics

open class CustomWebViewClient : WebViewClient() {
    open fun doUpdateVisitedHistory(web: CustomWebView, url: String, isReload: Boolean) {}

    override fun doUpdateVisitedHistory(view: WebView, url: String, isReload: Boolean) {
        if (view is CustomWebView)
            doUpdateVisitedHistory(view as CustomWebView, url, isReload)
    }

    open fun onFormResubmission(web: CustomWebView, dontResend: Message, resend: Message) {
        dontResend.sendToTarget()
    }

    override fun onFormResubmission(view: WebView, dontResend: Message, resend: Message) {
        if (view is CustomWebView)
            onFormResubmission(view as CustomWebView, dontResend, resend)
        else
            dontResend.sendToTarget()
    }

    open fun onLoadResource(web: CustomWebView, url: String) {}

    override fun onLoadResource(view: WebView, url: String) {
        if (view is CustomWebView)
            onLoadResource(view as CustomWebView, url)
    }

    open fun onPageFinished(web: CustomWebView, url: String) {}

    override fun onPageFinished(view: WebView, url: String) {
        if (view is CustomWebView)
            onPageFinished(view as CustomWebView, url)
    }

    open fun onPageStarted(web: CustomWebView, url: String, favicon: Bitmap?) {}

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        if (view is CustomWebView)
            onPageStarted(view as CustomWebView, url, favicon)
    }

    open fun onReceivedHttpAuthRequest(web: CustomWebView, handler: HttpAuthHandler, host: String, realm: String) {
        handler.cancel()
    }

    override fun onReceivedHttpAuthRequest(view: WebView, handler: HttpAuthHandler, host: String, realm: String) {
        if (view is CustomWebView)
            onReceivedHttpAuthRequest(view as CustomWebView, handler, host, realm)
        else
            handler.cancel()
    }

    open fun onReceivedLoginRequest(view: CustomWebView, realm: String, account: String, args: String) {}

    override fun onReceivedLoginRequest(view: WebView, realm: String, account: String, args: String) {
        if (view is CustomWebView)
            onReceivedLoginRequest(view as CustomWebView, realm, account, args)
    }

    open fun onReceivedSslError(web: CustomWebView, handler: SslErrorHandler, error: SslError) {
        handler.cancel()
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        if (view is CustomWebView)
            onReceivedSslError(view as CustomWebView, handler, error)
        else
            handler.cancel()
    }

    open fun onScaleChanged(view: CustomWebView, oldScale: Float, newScale: Float) {}

    override fun onScaleChanged(view: WebView, oldScale: Float, newScale: Float) {
        if (view is CustomWebView)
            onScaleChanged(view as CustomWebView, oldScale, newScale)
    }

    open fun onUnhandledKeyEvent(view: CustomWebView, event: KeyEvent) {}

    override fun onUnhandledKeyEvent(view: WebView, event: KeyEvent) {
        if (view is CustomWebView)
            onUnhandledKeyEvent(view as CustomWebView, event)
    }

    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
        return if (view is CustomWebView)
            shouldInterceptRequest(view as CustomWebView, request)
        else
            null
    }

    open fun shouldInterceptRequest(web: CustomWebView, request: WebResourceRequest): WebResourceResponse? = null

    open fun shouldOverrideKeyEvent(web: CustomWebView, event: KeyEvent): Boolean = false

    override fun shouldOverrideKeyEvent(view: WebView, event: KeyEvent): Boolean {
        return if (view is CustomWebView)
            shouldOverrideKeyEvent(view as CustomWebView, event)
        else
            false
    }

    open fun shouldOverrideUrlLoading(web: CustomWebView, url: String, uri: Uri): Boolean = false

    @Suppress("OverridingDeprecatedMember")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        try {
            return if (view is CustomWebView)
                shouldOverrideUrlLoading(view as CustomWebView, url, Uri.parse(url))
            else
                false
        } catch (t: Throwable) {
            Crashlytics.logException(t)
            throw t
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        try {
            return if (view is CustomWebView)
                shouldOverrideUrlLoading(view as CustomWebView, request.url.toString(), request.url)
            else
                false
        } catch (t: Throwable) {
            Crashlytics.logException(t)
            throw t
        }
    }
}
