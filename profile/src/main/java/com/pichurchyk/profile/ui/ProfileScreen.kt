package com.pichurchyk.profile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.CommonButton
import com.pichurchyk.fitflow.common.ui.Header
import com.pichurchyk.fitflow.common.ui.Loader
import com.pichurchyk.fitflow.common.ui.SnackbarInfo
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.profile.R
import com.pichurchyk.profile.ui.goals.NutritionGoals
import com.pichurchyk.profile.ui.stats.ProfileStats
import com.pichurchyk.profile.ui.viewmodel.ProfileIntent
import com.pichurchyk.profile.ui.viewmodel.ProfileViewModel
import com.pichurchyk.profile.ui.viewmodel.ProfileViewState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import com.pichurchyk.fitflow.common.R as commonR

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

    var isProfileChanged by remember {
        mutableStateOf(false)
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
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary),
                title = stringResource(R.string.profile),
                textColor = MaterialTheme.colorScheme.onPrimary,
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
                        top = paddingValues.calculateTopPadding()
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
                        val user = state.userData
                        val userParams = state.userParams
                        val nutritionGoals = state.nutritionGoals

                        ProfileHeader(
                            modifier = Modifier,
                            email = user.email,
                            name = user.name,
                            avatarUrl = user.avatarUrl
                        )

                        userParams?.let {
                            ProfileStats(
                                modifier = Modifier.padding(top = 10.dp, start = 6.dp, end = 6.dp),
                                userParams = userParams
                            )
                        }

                        nutritionGoals?.let {
                            NutritionGoals(
                                modifier = Modifier.padding(top = 24.dp, start = 6.dp, end = 6.dp),
                                goals = it,
                                onGoalChanged = {
                                    viewModel.handleIntent(ProfileIntent.OnNutritionGoalChanged(it))
                                }
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            if (isProfileChanged) {
                CommonButton(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    text = stringResource(commonR.string.save)
                ) {

                }
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        ProfileScreen { }
    }
}