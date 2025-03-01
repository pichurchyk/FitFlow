package com.pichurchyk.fitflow.viewmodel.dashboard

import com.pichurchyk.nutrition.model.DailyInfo
import com.pichurchyk.nutrition.model.WaterIntake

sealed class DashboardViewState() {

    data object Init: DashboardViewState()

    data class Error(val message: String): DashboardViewState()

    sealed class ShowData(): DashboardViewState() {

        data object Loading: ShowData()

        data class Loaded(val data: DailyInfo, val waterIntakes: List<WaterIntake>): ShowData()
    }
}