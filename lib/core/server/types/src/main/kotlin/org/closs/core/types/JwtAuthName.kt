package org.closs.core.types

enum class JwtAuthName(val value: String) {
    SESSION("session-auth"),
    MANAGER("manager-auth"),
    ADMIN("admin-auth"),
    USER_ROUTES("user-routes-auth"),
    SALESMAN_ROUTES("salesman-routes-auth"),
    ORDER_ROUTES("order-routes-auth"),
    DOCUMENT_ROUTES("document-routes-auth"),
}
