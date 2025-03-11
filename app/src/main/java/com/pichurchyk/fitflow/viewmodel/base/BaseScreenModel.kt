package com.pichurchyk.fitflow.viewmodel.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val defaultError = "Some error occurred"
}