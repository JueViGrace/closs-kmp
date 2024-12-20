package org.closs.core.types

enum class JwtAuthName(val value: String) {
    SESSION("session-auth"),
    MANAGER("manager-auth"),
    USER_ID("user-id-auth"),
    MANAGER_CODE("manager-code-auth"),
    SALESMAN_CODE("salesman-code-auth"),
    CUSTOMER_CODE("customer-code-auth"),
    ORDER_CODE("order-code-auth"),
    DOCUMENT_CODE("document-code-auth"),
    ADMIN("admin-auth"),
}
