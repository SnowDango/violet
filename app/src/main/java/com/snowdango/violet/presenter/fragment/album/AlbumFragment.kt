package com.snowdango.violet.presenter.fragment.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.snowdango.violet.R
import com.snowdango.violet.view.style.AppTheme

class AlbumFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    AlbumScreen(
                        onAlbumDetailNavigate = {
                            val bundle = Bundle()
                            bundle.putLong("albumId", it)
                            findNavController().navigate(
                                R.id.albumDetailFragment,
                                bundle
                            )
                        }
                    )
                }
            }
        }
    }
}