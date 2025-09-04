package com.mpauli.base.res

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes stringId: Int): String
}
