package com.pichurchyk.fitflow.viewmodel.dashboard

import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.database.model.ext.getCarbs
import com.pichurchyk.nutrition.database.model.ext.getFat
import com.pichurchyk.nutrition.database.model.ext.getProtein
import com.pichurchyk.nutrition.database.model.ext.getWater

sealed class DashboardViewState() {

    data object Init: DashboardViewState()

    data class Error(val message: String): DashboardViewState()

    sealed class ShowData(): DashboardViewState() {

        data object Loading: ShowData()

        data class Loaded(val data: DailyInfoDTO): ShowData() {
            fun getSummaryFat(): Int = data.getFat().sumOf { it.value }
            fun getSummaryProtein(): Int = data.getProtein().sumOf { it.value }
            fun getSummaryCarbs(): Int = data.getCarbs().sumOf { it.value }
            fun getSummaryWater(): Int = data.getWater().sumOf { it.value }
        }

    }

}