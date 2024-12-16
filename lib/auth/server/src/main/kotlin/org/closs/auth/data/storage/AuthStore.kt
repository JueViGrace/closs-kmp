package org.closs.auth.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.Closs_token
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.auth.AuthDto
import org.closs.core.shared.types.auth.ForgotPasswordDto
import org.closs.core.shared.types.auth.RefreshTokenDto
import org.closs.core.shared.types.auth.SignInDto
import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.aliases.DbUser
import org.closs.core.util.Jwt
import org.closs.core.util.Kbcrypt

interface AuthStore {
    suspend fun getValidUser(dto: SignInDto): AuthDto?
    suspend fun refresh(dto: RefreshTokenDto): AuthDto?
    suspend fun forgotPassword(dto: ForgotPasswordDto): AuthDto?
}

class DefaultAuthStore(
    private val jwt: Jwt,
    private val scope: CoroutineScope,
    private val dbHelper: DbHelper
) : AuthStore {
    private suspend fun findUserByUsername(username: String): DbUser? {
        return dbHelper.withDatabase { db ->
            db.transactionWithResult {
                executeOne(
                    query = db.clossUserQueries.findExisitngByUsername(username)
                )
            }
        }
    }

    override suspend fun getValidUser(dto: SignInDto): AuthDto? {
        val dbUser: DbUser? = findUserByUsername(dto.username)

        if (dbUser == null || !Kbcrypt.verifyPassword(dto.password, dbUser.password)) {
            return null
        }

        return saveToken(
            userDto = UserDto(
                id = dbUser.id,
                username = dbUser.username,
                code = dbUser.codigo,
                lastSync = dbUser.ult_sinc,
                version = dbUser.version,
                createdAt = dbUser.created_at,
                updatedAt = dbUser.updated_at,
            )
        )
    }

    // todo: delete old token?
    override suspend fun refresh(dto: RefreshTokenDto): AuthDto? {
        val token = jwt.verifyToken(dto.refreshToken) ?: return null

        val dbUser = dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossUserQueries.findExistingUser(token.userId)
            )
        }
        if (dbUser == null) {
            return null
        }

        return saveToken(
            userDto = UserDto(
                id = dbUser.id,
                username = dbUser.username,
                code = dbUser.codigo,
                lastSync = dbUser.ult_sinc,
                version = dbUser.version,
                createdAt = dbUser.created_at,
                updatedAt = dbUser.updated_at,
            )
        )
    }

    override suspend fun forgotPassword(dto: ForgotPasswordDto): AuthDto? {
        return null
    }

    private suspend fun saveToken(
        userDto: UserDto,
    ): AuthDto? {
        val accessToken = jwt.createAccessToken(
            claims = mapOf(
                "user_id" to userDto.id,
            )
        )
        val refreshToken = jwt.createRefreshToken(
            claims = mapOf(
                "user_id" to userDto.id,
            )
        )

        scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.clossTokenQueries.insert(
                        closs_token = Closs_token(
                            token = refreshToken,
                            user_id = userDto.id
                        )
                    ).executeAsOneOrNull()
                }
            }
        }.await() ?: return null

        return AuthDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = userDto
        )
    }
}
