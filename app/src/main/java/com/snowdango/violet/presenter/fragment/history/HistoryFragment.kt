package com.snowdango.violet.presenter.fragment.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.snowdango.violet.repository.datastore.LastSongDataStore
import com.snowdango.violet.view.style.AppTheme
import com.snowdango.violet.viewmodel.history.HistoryViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModel()
    private val dataStore: LastSongDataStore by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    HistoryScreen(dataStore)
                }
            }
        }
    }


}