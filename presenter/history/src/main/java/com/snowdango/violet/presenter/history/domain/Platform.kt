package com.snowdango.violet.presenter.history.domain

import android.net.Uri
import com.snowdango.violet.presenter.common.domain.PlatformType

data class Platform(
    val type: PlatformType,
    val mobileScheme: Uri?,
)
