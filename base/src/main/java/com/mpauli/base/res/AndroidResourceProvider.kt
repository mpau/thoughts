package com.mpauli.base.res

import android.content.Context

internal class AndroidResourceProvider(private val context: Context) : ResourceProvider {

    override fun getString(stringId: Int): String = context.getString(stringId)
}
