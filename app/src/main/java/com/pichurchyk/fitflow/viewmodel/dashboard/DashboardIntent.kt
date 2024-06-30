package com.pichurchyk.fitflow.viewmodel.dashboard

import java.util.Date

sealed class DashboardIntent {
    data object LoadData: DashboardIntent()
    data object SelectPreviousDate: DashboardIntent()
    data object SelectNextDate: DashboardIntent()
    data class ChangeDate(val date: Date): DashboardIntent()
}