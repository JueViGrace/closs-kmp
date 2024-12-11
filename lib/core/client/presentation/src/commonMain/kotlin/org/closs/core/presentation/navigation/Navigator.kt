package org.closs.core.presentation.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

interface Navigator {
    val startDestination: org.closs.core.presentation.navigation.Destination
    val navigationActions: Flow<NavigationAction>
    val stack: StateFlow<NavigationStack>
    val stateHandle: SavedStateHandle

    suspend fun navigate(
        destination: org.closs.core.presentation.navigation.Destination,
        navOptions: NavOptions? = null
    )

    suspend fun navigateUp()
}

class DefaultNavigator(
    override val startDestination: org.closs.core.presentation.navigation.Destination,
    override val stateHandle: SavedStateHandle
) : Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions: Flow<NavigationAction> = _navigationActions.receiveAsFlow()

    private val _stack = MutableStateFlow(NavigationStack(mutableListOf(startDestination), startDestination))
    override val stack: StateFlow<NavigationStack> = _stack.asStateFlow()

    override suspend fun navigate(
        destination: org.closs.core.presentation.navigation.Destination,
        navOptions: NavOptions?
    ) {
        _stack.update { stack ->
            if (navOptions?.isPopUpToInclusive() == true) {
                stack.destinations.remove(navOptions.popUpToRouteObject as org.closs.core.presentation.navigation.Destination)
            }

            if (stack.destinations.contains(destination)) {
                return@update stack
            }

            stack.destinations.add(destination)
            stack.copy(
                currentDestination = stack.destinations.lastOrNull()
            )
        }
        _navigationActions.send(NavigationAction.Navigate(destination, navOptions))
    }

    override suspend fun navigateUp() {
        _stack.update { stack ->
            if (stack.destinations.isEmpty()) {
                return@update stack
            }
            stack.destinations.removeLast()
            stack.copy(
                currentDestination = stack.destinations.lastOrNull()
            )
        }
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}

data class NavigationStack(
    val destinations: MutableList<org.closs.core.presentation.navigation.Destination> = mutableListOf(),
    val currentDestination: org.closs.core.presentation.navigation.Destination? = null,
)
