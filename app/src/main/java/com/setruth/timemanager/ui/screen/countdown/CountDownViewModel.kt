package com.setruth.timemanager.ui.screen.countdown

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CountDownViewModel @Inject constructor() : ViewModel() {

    private val _seconds = MutableStateFlow(0)
    val seconds = _seconds.asStateFlow()

    private val _minutes = MutableStateFlow(0)
    val minutes = _minutes.asStateFlow()

    fun changeSeconds(seconds: Int) {
        _seconds.value = seconds
    }

    fun changeMinutes(minutes: Int) {
        _minutes.value = minutes
    }
}
