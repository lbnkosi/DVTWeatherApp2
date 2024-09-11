package com.lbnkosi.weatherapp.features.screens.splash

import androidx.lifecycle.viewModelScope
import com.lbnkosi.weatherapp.domain.viewstate.IViewEvent
import com.lbnkosi.weatherapp.features.base.BaseViewModel
import com.lbnkosi.weatherapp.domain.viewstate.splash.SplashViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashViewState, SplashViewEvent>() {

    init {
        viewModelScope.launch {
            delay(1000)
            setEvent(SplashViewEvent.DirectToDashBoard)
        }
    }

    override fun createInitialState() = SplashViewState()
    override fun onTriggerEvent(event: SplashViewEvent) {}
}

sealed class SplashViewEvent : IViewEvent {
    object DirectToDashBoard : SplashViewEvent()
}