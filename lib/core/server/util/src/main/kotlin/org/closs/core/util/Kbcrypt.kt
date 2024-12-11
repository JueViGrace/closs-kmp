package org.closs.core.util

import com.toxicbakery.bcrypt.Bcrypt

object Kbcrypt {
    private const val SALT_ROUNDS = 12

    fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return Bcrypt.verify(password, hashedPassword.toByteArray())
    }

    fun hashPassword(password: String): String {
        return Bcrypt.hash(input = password, saltRounds = SALT_ROUNDS).decodeToString()
    }
}
