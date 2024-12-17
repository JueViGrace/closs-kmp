package org.closs.document.data.handler

import kotlinx.coroutines.withContext
import org.closs.core.shared.types.document.CreateDocumentDto
import org.closs.core.shared.types.document.DocumentDto
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse
import org.closs.document.data.store.DocumentStore
import kotlin.coroutines.CoroutineContext

interface DocumentHandler {
    suspend fun getDocument(doc: String): APIResponse<DocumentDto?>
    suspend fun getDocumentWithLines(doc: String): APIResponse<DocumentDto?>
    suspend fun getDocumentsByManager(manager: String): APIResponse<List<DocumentDto>>
    suspend fun getDocumentsBySalesman(code: String): APIResponse<List<DocumentDto>>
    suspend fun getDocumentsByCustomer(code: String): APIResponse<List<DocumentDto>>
    suspend fun createDocument(dto: CreateDocumentDto): APIResponse<DocumentDto?>
}

class DefaultDocumentHandler(
    private val storage: DocumentStore,
    private val coroutineContext: CoroutineContext
) : DocumentHandler {
    override suspend fun getDocument(doc: String): APIResponse<DocumentDto?> {
        return withContext(coroutineContext) {
            val result = storage.getDocument(doc)
                ?: return@withContext ServerResponse.notFound(
                    message = "Document with code $doc was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getDocumentWithLines(doc: String): APIResponse<DocumentDto?> {
        return withContext(coroutineContext) {
            val result = storage.getDocumentWithLines(doc)
                ?: return@withContext ServerResponse.notFound(
                    message = "Document with code $doc was not found"
                )

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getDocumentsByManager(manager: String): APIResponse<List<DocumentDto>> {
        return withContext(coroutineContext) {
            val result = storage.getDocumentsByManager(manager)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Documents of manager $manager were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getDocumentsBySalesman(code: String): APIResponse<List<DocumentDto>> {
        return withContext(coroutineContext) {
            val result = storage.getDocumentsBySalesman(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Documents of salesman $code were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun getDocumentsByCustomer(code: String): APIResponse<List<DocumentDto>> {
        return withContext(coroutineContext) {
            val result = storage.getDocumentsByCustomer(code)

            if (result.isEmpty()) {
                return@withContext ServerResponse.notFound(
                    message = "Documents of customer $code were not found"
                )
            }

            ServerResponse.ok(
                data = result,
                message = "Processed successfully"
            )
        }
    }

    override suspend fun createDocument(dto: CreateDocumentDto): APIResponse<DocumentDto?> {
        return withContext(coroutineContext) {
            val result = storage.createDocument(dto)
                ?: return@withContext ServerResponse.notFound(
                    message = "Unable to create document, try again"
                )

            ServerResponse.created(
                data = result,
                message = "Processed successfully"
            )
        }
    }
}
