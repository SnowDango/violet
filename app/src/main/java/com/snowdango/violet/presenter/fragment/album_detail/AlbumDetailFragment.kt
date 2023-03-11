package com.snowdango.violet.presenter.fragment.album_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.snowdango.violet.view.style.AppTheme

class AlbumDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val albumId = arguments?.getLong("albumId") ?: 0

        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    AlbumDetailScreen(albumId)
                }
            }
        }
    }
}