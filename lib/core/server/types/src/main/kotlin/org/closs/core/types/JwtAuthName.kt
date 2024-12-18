package org.closs.core.types

enum class JwtAuthName(val value: String) {
    SESSION("session-auth"),
    ADMIN("admin-auth"),
    USER_ROUTES("user-routes-auth"),
    SALESMAN_ROUTES("salesman-routes-auth"),
    ORDER_ROUTES("order-routes-auth"),
}
