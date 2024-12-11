package org.closs.auth.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.api.KtorClient
import org.closs.core.database.helper.DbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.resources.resources.generated.resources.welcome
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.shared.types.auth.AuthDto
import org.closs.core.shared.types.auth.ForgotPasswordDto
import org.closs.core.shared.types.auth.SignInDto
import org.closs.core.types.Repository
import org.closs.core.types.auth.Session
import org.closs.core.types.auth.dtoToDomain
import org.closs.core.types.auth.sessionToDb
import org.closs.core.types.response.display
import org.closs.core.types.state.DataCodes
import org.closs.core.types.state.RequestState
import org.closs.core.types.user.domainToDb
import kotlin.coroutines.CoroutineContext

interface AuthRepository : Repository {
    suspend fun signIn(signInDto: SignInDto): RequestState<DataCodes>
    suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes>
}

class DefaultAuthRepository(
    private val ktorClient: KtorClient,
    private val dbHelper: DbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AuthRepository {
    override suspend fun signIn(signInDto: SignInDto): RequestState<DataCodes> {
        val call = ktorClient.call<AuthDto> {
            post(
                urlString = "/api/auth/signIn",
                body = signInDto
            )
        }

        return call.display(
            onFailure = { code ->
                RequestState.Error(error = code)
            },
            onSuccess = { res ->
                if (res.data == null) {
                    return@display RequestState.Error(
                        error = DataCodes.NullError(
                            msg = Res.string.unknown_error,
                            desc = res.message
                        )
                    )
                }

                saveSession(res.data!!.dtoToDomain())

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        msg = Res.string.welcome,
                        desc = res.message
                    )
                )
            }
        )
    }

    override suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes> {
        val call = ktorClient.call<AuthDto> {
            post(
                urlString = "/api/auth/forgotPassword",
                body = forgotPasswordDto
            )
        }

        return call.display(
            onFailure = { code ->
                RequestState.Error(
                    error = code
                )
            },
            onSuccess = { res ->
                if (res.data == null) {
                    return@display RequestState.Error(
                        error = DataCodes.NullError(
                            msg = Res.string.unknown_error,
                            desc = res.message
                        )
                    )
                }
                saveSession(res.data!!.dtoToDomain())

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        msg = Res.string.welcome_back,
                        desc = res.message
                    )
                )
            }
        )
    }

    private suspend fun saveSession(session: Session) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    db.clossSessionQueries.insert(
                        closs_session = session.sessionToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback()

                    db.clossUserQueries.insert(
                        closs_user = session.user.domainToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback()
                }
            }
        }.await()
    }
}