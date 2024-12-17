package org.closs.document.data.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.database.helper.DbHelper
import org.closs.core.shared.types.document.CreateDocumentDto
import org.closs.core.shared.types.document.DocumentDto
import org.closs.core.types.document.detailsToDb
import org.closs.core.types.document.toDb
import org.closs.core.types.document.toDto
import org.closs.core.types.document.withLinesToDto

interface DocumentStore {
    suspend fun getDocument(doc: String): DocumentDto?
    suspend fun getDocumentWithLines(doc: String): DocumentDto?
    suspend fun getDocumentsByManager(manager: String): List<DocumentDto>
    suspend fun getDocumentsBySalesman(code: String): List<DocumentDto>
    suspend fun getDocumentsByCustomer(code: String): List<DocumentDto>
    suspend fun createDocument(dto: CreateDocumentDto): DocumentDto?
}

class DefaultDocumentStore(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : DocumentStore {
    override suspend fun getDocument(doc: String): DocumentDto? {
        return dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossDocumentQueries.findDocument(doc)
            )?.toDto()
        }
    }

    override suspend fun getDocumentWithLines(doc: String): DocumentDto? {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossDocumentQueries.findDocumentWithLines(doc)
            ).withLinesToDto()
        }
    }

    override suspend fun getDocumentsByManager(manager: String): List<DocumentDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossDocumentQueries.findDocumentsByManager(manager)
            ).map { document -> document.toDto() }
        }
    }

    override suspend fun getDocumentsBySalesman(code: String): List<DocumentDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossDocumentQueries.findDocumentsBySalesman(code)
            ).map { document -> document.toDto() }
        }
    }

    override suspend fun getDocumentsByCustomer(code: String): List<DocumentDto> {
        return dbHelper.withDatabase { db ->
            executeList(
                query = db.clossDocumentQueries.findDocumentsBySalesman(code)
            ).map { document -> document.toDto() }
        }
    }

    override suspend fun createDocument(dto: CreateDocumentDto): DocumentDto? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val dbDocument = db.clossDocumentQueries.insert(
                        closs_document = dto.toDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    val dbLines = dto.lines.map { line ->
                        db.clossDocumentLinesQueries.insert(
                            closs_document_lines = line.detailsToDb()
                        )
                            .executeAsOneOrNull()
                    }
                    if (dbLines.contains(null)) rollback(null)

                    getDocument(dbDocument.documento)
                }
            }
        }.await()
    }
}
