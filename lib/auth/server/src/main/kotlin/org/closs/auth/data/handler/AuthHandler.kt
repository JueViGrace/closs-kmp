package org.closs.auth.data.handler

import kotlinx.coroutines.withContext
import org.closs.auth.data.storage.AuthStore
import org.closs.core.shared.types.auth.AuthDto
import org.closs.core.shared.types.auth.ForgotPasswordDto
import org.closs.core.shared.types.auth.RefreshTokenDto
import org.closs.core.shared.types.auth.SignInDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import kotlin.coroutines.CoroutineContext

// todo: check for conflicts
interface AuthHandler {
    suspend fun signIn(dto: SignInDto): APIResponse<AuthDto?>
    suspend fun forgotPassword(dto: ForgotPasswordDto): APIResponse<AuthDto?>
    suspend fun refresh(dto: RefreshTokenDto): APIResponse<AuthDto?>
}

class DefaultAuthHandler(
    private val coroutineContext: CoroutineContext,
    private val store: AuthStore
) : AuthHandler {
    override suspend fun signIn(dto: SignInDto): APIResponse<AuthDto?> {
        return withContext(coroutineContext) {
            val result = store.signIn(dto)
                ?: return@withContext ServerResponse.badRequest(
                    message = "Invalid credentials"
                )

            return@withContext ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    // todo: do this right
    override suspend fun forgotPassword(dto: ForgotPasswordDto): APIResponse<AuthDto?> {
        return withContext(coroutineContext) {
            val result = store.forgotPassword(dto)
                ?: return@withContext ServerResponse.badRequest(
                    message = "Unable to process change of password, try again."
                )

            return@withContext ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun refresh(dto: RefreshTokenDto): APIResponse<AuthDto?> {
        return withContext(coroutineContext) {
            val result = store.refresh(dto)
                ?: return@withContext ServerResponse.badRequest(
                    message = "Invalid refresh token"
                )

            return@withContext ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
