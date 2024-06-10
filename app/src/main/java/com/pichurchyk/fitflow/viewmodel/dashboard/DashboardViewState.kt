package com.pichurchyk.fitflow.viewmodel.dashboard

import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO

sealed class DashboardViewState() {

    data object Init: DashboardViewState()

    data class Error(val message: String): DashboardViewState()

    sealed class ShowData(): DashboardViewState() {

        data object Loading: ShowData()

        data object Empty: ShowData()

        data class Loaded(val data: DailyInfoDTO): ShowData()

    }

}