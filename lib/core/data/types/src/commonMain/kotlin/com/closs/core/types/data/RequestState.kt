package com.closs.core.types.data

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import com.closs.core.types.data.RequestState.Error
import com.closs.core.types.data.RequestState.Idle
import com.closs.core.types.data.RequestState.Loading
import com.closs.core.types.data.RequestState.Success

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val error: DataError) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    /**
     * Returns data from a [Success].
     * @throws ClassCastException If the current state is not [Success]
     *  */
    fun getSuccessData() = (this as Success).data
    fun getSuccessDataOrNull(): T? {
        return try {
            (this as Success).data
        } catch (e: Exception) {
            null
        }
    }
    fun getSuccessDataSafe(): T? = if (this.isSuccess()) (this as Success).data else getSuccessDataOrNull()

    /**
     * Returns an error message from an [Error]
     * @throws ClassCastException If the current state is not [Error]
     *  */
    fun getErrorMessage() = (this as Error).error
    fun getErrorMessageOrEmpty(): DataError {
        return try {
            (this as Error).error
        } catch (e: Exception) {
            DataError.UnknownDataError(e.message)
        }
    }
}

@Composable
fun<T> RequestState<T>.DisplayResult(
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (DataError) -> Unit,
    transitionSpec: AnimatedContentTransitionScope<*>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 300)) togetherWith
            fadeOut(tween(durationMillis = 300))
    }
) {
    AnimatedContent(
        targetState = this,
        transitionSpec = transitionSpec,
        label = "Animated State"
    ) { state ->
        when (state) {
            is Idle -> {
                onIdle?.invoke()
            }

            is Loading -> {
                onLoading()
            }

            is Success -> {
                onSuccess(state.getSuccessData())
            }

            is Error -> {
                onError(state.getErrorMessage())
            }
        }
    }
}
