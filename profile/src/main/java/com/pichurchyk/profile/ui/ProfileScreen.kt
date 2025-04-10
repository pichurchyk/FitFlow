package com.pichurchyk.profile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ext.clearFocusOnClick
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

    val focusManager = LocalFocusManager.current

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
    val insets = WindowInsets.systemBars.asPaddingValues()

    Scaffold(
        modifier = Modifier.padding(top = insets.calculateTopPadding()).clearFocusOnClick(),
        topBar = {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
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

                        val nutritionGoalsState = state.nutritionGoals

                        ProfileHeader(
                            modifier = Modifier,
                            email = user.email,
                            name = user.name,
                            avatarUrl = user.avatarUrl
                        )


                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize(),
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            item(span = { GridItemSpan(3) }) {
                                userParams?.let {
                                    ProfileStats(
                                        modifier = Modifier.padding(
                                            top = 10.dp,
                                            start = 6.dp,
                                            end = 6.dp
                                        ),
                                        userParams = userParams
                                    )
                                }
                            }

                            item(span = { GridItemSpan(3) }) {
                                NutritionGoals(
                                    modifier = Modifier.padding(
                                        top = 24.dp,
                                        start = 6.dp,
                                        end = 6.dp
                                    ),
                                    state = nutritionGoalsState,
                                    onGoalChanged = {
                                        viewModel.handleIntent(ProfileIntent.ChangeNutritionGoal(it))
                                    },
                                    onSaveClick = {
                                        focusManager.clearFocus(true)
                                        viewModel.handleIntent(ProfileIntent.SaveChangedNutritionGoals)
                                    },
                                    onDiscardClick = {
                                        focusManager.clearFocus(true)
                                        viewModel.handleIntent(ProfileIntent.DiscardChangedNutritionGoals)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {}
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        ProfileScreen { }
    }
}