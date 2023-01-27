package com.snowdango.violet.presenter.fragment.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.snowdango.violet.BuildConfig
import com.snowdango.violet.presenter.dialog.TwitterAuthDialog
import com.snowdango.violet.repository.datastore.LastSongDataStore
import com.snowdango.violet.view.style.AppTheme
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModel()
    private val dataStore: LastSongDataStore by inject()
    private var twitterAuthDialog: TwitterAuthDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        twitterAuthDialog = TwitterAuthDialog(viewLifecycleOwner.lifecycleScope, requireContext())
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    HistoryScreen(dataStore) {
                        twitterAuthDialog?.getRequestToken {
                            val builder = ConfigurationBuilder()
                                .setDebugEnabled(true)
                                .setOAuthAccessToken(BuildConfig.TWITTER_ACCESS_TOKEN)
                                .setOAuthAccessTokenSecret(BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
                            val twitter = TwitterFactory(builder.build()).instance
                            twitter.updateStatus("test")
                        }
                    }
                }
            }
        }
    }
}