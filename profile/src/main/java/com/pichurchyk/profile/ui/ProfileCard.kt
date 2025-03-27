package com.pichurchyk.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.TextStyles
import com.pichurchyk.profile.R

@Composable
fun ProfileHeader(
    modifier: Modifier,
    email: String? = stringResource(R.string.hidden_email),
    name: String? = null,
    avatarUrl: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_user),
                contentDescription = stringResource(R.string.profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                    .padding(2.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            )

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 6.dp, y = 6.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(4.dp),
                painter = painterResource(R.drawable.ic_upload),
                contentDescription = stringResource(R.string.upload_profile_image),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        name?.let {
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = name,
                style = TextStyles.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        email?.let {
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 6.dp),
                text = email,
                style = TextStyles.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        ProfileHeader(
            modifier = Modifier,
            email = "asdasda",
            name = "VLAD"
        )
    }
}