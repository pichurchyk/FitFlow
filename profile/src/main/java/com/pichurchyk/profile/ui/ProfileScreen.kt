package com.pichurchyk.profile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pichurchyk.fitflow.common.ui.Header
import com.pichurchyk.fitflow.common.ui.Loader
import com.pichurchyk.fitflow.common.ui.SnackbarInfo
import com.pichurchyk.profile.R
import com.pichurchyk.profile.ui.viewmodel.ProfileIntent
import com.pichurchyk.profile.ui.viewmodel.ProfileViewModel
import com.pichurchyk.profile.ui.viewmodel.ProfileViewState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    closeScreen: () -> Unit,
) {

    val viewState by viewModel.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ProfileIntent.LoadInfo)
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            coroutineScope.launch {

                snackbarHostState.showSnackbar(
                    SnackbarInfo(
                        isError = true,
                        message = message
                    )
                )
            }
        }
    }

    BackHandler {
        closeScreen()
    }

    Scaffold(
        topBar = {
            Header(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.profile),
                onBackPressed = {
                    closeScreen()
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = paddingValues.calculateBottomPadding(),
                        top = paddingValues.calculateTopPadding() + 12.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                when (val state = viewState) {
                    is ProfileViewState.Loading -> {
                        Loader()
                    }

                    is ProfileViewState.Error -> {
                        errorMessage = state.message
                    }

                    is ProfileViewState.Loaded -> {
                        val user = state.data

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(user.avatarUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.ic_user),
                            contentDescription = stringResource(R.string.profile_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(100.dp).clip(CircleShape),
                        )

                        Header(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.profile),
                            onBackPressed = {
                                closeScreen()
                            }
                        )
                    }
                }
            }
        },
        bottomBar = {

        }
    )
}