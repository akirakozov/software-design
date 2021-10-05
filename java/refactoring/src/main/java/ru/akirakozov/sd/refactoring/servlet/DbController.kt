package ru.akirakozov.sd.refactoring.servlet

import java.sql.DriverManager
import java.sql.Statement
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class DbController(private val dbReference: String) {
    init {
        withStatement { statement ->
            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS PRODUCT" +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)"
            )
        }
    }

    fun evaluateQuery(queryType: DbServletQueryType, request: HttpServletRequest, response: HttpServletResponse) {
        withStatement { statement ->
            PrettyPrinter.withPrinter(response) { printer ->
                queryType.evaluate(statement, request, printer)
            }
        }
    }

    private fun withStatement(block: (Statement) -> Unit) {
        DriverManager.getConnection(dbReference).use { connection ->
            connection.createStatement().use {
                block(it)
            }
        }
    }
}