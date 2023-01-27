package com.snowdango.violet.presenter.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.snowdango.violet.BuildConfig
import com.snowdango.violet.domain.token.TwitterToken
import com.snowdango.violet.model.data.TwitterTokenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class TwitterAuthDialog(private val scope: CoroutineScope, private val context: Context) :
    KoinComponent {

    private val twitterTokenModel: TwitterTokenModel by inject()

    lateinit var twitter: Twitter
    lateinit var twitterDialog: Dialog

    fun getRequestToken(callback: (TwitterToken) -> Unit) {
        scope.launch(Dispatchers.Default) {

            val token = twitterTokenModel.getTwitterToken()
            if (token.accessToken.isNotBlank() && token.accessTokenSecret.isNotBlank()) {
                withContext(Dispatchers.Main) {
                    callback(token)
                }
            } else {

                // twitterインスタンスにConsumer keyとConsumer Secretを設定
                val builder = ConfigurationBuilder()
                    .setDebugEnabled(true)
                    .setOAuthConsumerKey(BuildConfig.TWITTER_CONSUMER_KEY)
                    .setOAuthConsumerSecret(BuildConfig.TWITTER_CONSUMER_SECRET)

                val config = builder.build()
                val factory = TwitterFactory(config)

                // twitter インスタンスを生成
                twitter = factory.instance

                try {
                    // リクエストトークンを取得
                    val requestToken = twitter.oAuthRequestToken
                    withContext(Dispatchers.Main) {
                        setupTwitterWebviewDialog(requestToken.authorizationURL, callback)
                    }
                } catch (e: IllegalStateException) {
                    Timber.e(e)
                }

            }
        }
    }

    // Dialogの設定
    private fun setupTwitterWebviewDialog(url: String, callback: (TwitterToken) -> Unit) {
        twitterDialog = Dialog(context)
        val webView = WebView(context)

        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.webViewClient = TwitterWebViewClient(callback)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        twitterDialog.setContentView(webView)
        twitterDialog.show()
    }

    // WebViewの設定
    inner class TwitterWebViewClient(private val callback: (TwitterToken) -> Unit) :
        WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Timber.d("page Url: $url")
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request?.url.toString().startsWith("callback://")) {
                // 認証が完了したらダイアログを閉じる
                if (request?.url.toString().contains("callback://")) {
                    handleUrl(request?.url.toString())
                    twitterDialog.dismiss()
                }
                return true
            }
            return false
        }

        // OAuthのトークン取得
        private fun handleUrl(url: String) {
            val uri = Uri.parse(url)
            val oauthVerifier = uri.getQueryParameter("oauth_verifier") ?: ""
            scope.launch(Dispatchers.Default) {
                val accToken = withContext(Dispatchers.IO) {
                    twitter.getOAuthAccessToken(oauthVerifier)
                }
                val token = TwitterToken(accToken.token, accToken.tokenSecret)
                twitterTokenModel.saveTwitterToken(token)
                getUserProfile()
                withContext(Dispatchers.Main) {
                    callback(token)
                }
            }
        }

        // ユーザ情報取得
        private suspend fun getUserProfile() {
            val usr = withContext(Dispatchers.IO) { twitter.verifyCredentials() }

            Log.i("twitter", usr.name)
            Log.i("twitter", usr.screenName)
        }
    }

}
