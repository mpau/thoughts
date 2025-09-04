package com.mpauli.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.placeholder
import com.eygraber.compose.placeholder.material3.shimmer
import com.mpauli.core.ui.R

@Composable
fun AppNetworkImageWithShimmer(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    )

    val isLoading = painter.state is AsyncImagePainter.State.Loading

    Box(
        modifier = modifier
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer()
            )
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = stringResource(R.string.perspective_image_description),
            contentScale = ContentScale.Crop
        )
    }
}
