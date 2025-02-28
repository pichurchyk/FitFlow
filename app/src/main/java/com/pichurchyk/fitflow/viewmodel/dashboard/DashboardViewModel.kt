package com.pichurchyk.fitflow.viewmodel.dashboard

import androidx.lifecycle.viewModelScope
import com.pichurchyk.fitflow.common.ext.date.getNextDay
import com.pichurchyk.fitflow.common.ext.date.getPreviousDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.fitflow.viewmodel.base.BaseViewModel
import com.pichurchyk.nutrition.usecase.FetchRemoteAndLocalUseCase
import com.pichurchyk.nutrition.usecase.GetDailyInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

class DashboardViewModel(
    private val getDailyInfoUseCase: GetDailyInfoUseCase,
    private val fetchRemoteAndLocalUseCase: FetchRemoteAndLocalUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<DashboardViewState>(DashboardViewState.Init)
    val state = _state.asStateFlow()

    private val _selectedDate = MutableStateFlow(Date().toStartOfDay())
    val selectedDate = _selectedDate.asStateFlow()

    init {
        viewModelScope.launch {
            selectedDate
                .drop(1)
                .collect {
                    loadData()

                    fetchRemoteAndLocalUseCase.invoke(it)
                }
        }
    }

    fun handleIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadData -> {
                loadData()
            }

            is DashboardIntent.ChangeDate -> {
                changeDate(intent.date).also { loadData() }
            }

            is DashboardIntent.SelectNextDate -> {
                selectNextDate()
            }

            is DashboardIntent.SelectPreviousDate -> {
                selectPreviousDate()
            }
        }
    }

    private fun selectNextDate() {
        _selectedDate.value = _selectedDate.value.getNextDay()
    }

    private fun selectPreviousDate() {
        _selectedDate.value = _selectedDate.value.getPreviousDay()
    }

    private fun changeDate(date: Date) {
        _selectedDate.value = date.toStartOfDay()
    }

    private fun loadData() {
        viewModelScope.launch {
            getDailyInfoUseCase.invoke(selectedDate.first())
                .catch {
                    it.printStackTrace()
                }
                .collect { summary ->
                    _state.value = DashboardViewState.ShowData.Loaded(summary)
                }
        }
    }
}