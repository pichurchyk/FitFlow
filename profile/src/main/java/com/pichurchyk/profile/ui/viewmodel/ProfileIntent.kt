package com.pichurchyk.profile.ui.viewmodel

sealed class ProfileIntent {
    data object LoadInfo: ProfileIntent()
}