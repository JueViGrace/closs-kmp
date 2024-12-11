package org.closs.accloss.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.closs.accloss.data.AppRepository
import org.closs.accloss.presentation.state.AppState
import org.closs.core.presentation.messages.Messages
import org.closs.core.presentation.navigation.Destination
import org.closs.core.presentation.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.state.RequestState

class AppViewModel(
    navigator: Navigator,
    messages: Messages,
    appRepository: AppRepository,
) : ViewModel() {
    val messages = messages.messages

    private var _state = MutableStateFlow(AppState())
    val state = combine(
        _state,
        appRepository.validateSession(),
    ) { state, session ->
        when (session) {
            is RequestState.Error -> {
                delay(1000)
                navigator.navigate(
                    destination = Destination.SignIn,
                    navOptions = NavOptions.Builder().apply {
                        setPopUpTo(route = Destination.Splash, inclusive = true)
                        setLaunchSingleTop(true)
                    }.build()
                )
                state.copy(
                    session = null,
                    snackMessage = session.error.message,
                    description = session.error.description ?: ""
                )
            }
            is RequestState.Success -> {
                delay(1000)
                navigator.navigate(
                    destination = Destination.Home,
                    navOptions = NavOptions.Builder().apply {
                        setPopUpTo(route = Destination.Splash, inclusive = true)
                        setLaunchSingleTop(true)
                    }.build()
                )
                state.copy(
                    session = session.data,
                    snackMessage = Res.string.welcome_back,
                    description = session.data.user.username
                )
            }
            else -> {
                state.copy(
                    session = null,
                    snackMessage = null,
                    description = ""
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        AppState()
    )
}
