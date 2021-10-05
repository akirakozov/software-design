package ru.akirakozov.sd.refactoring.servlet

import java.sql.ResultSet
import java.sql.Statement
import javax.servlet.http.HttpServletRequest

enum class DbServletQueryType {
    ADD {
        override fun queryString(request: HttpServletRequest): String {
            val name = request.getParameter("name")
            val price = request.getParameter("price").toLong()
            return "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")"
        }

        override fun evaluate(statement: Statement, request: HttpServletRequest, printer: PrettyPrinter) =
            update(statement, request) {
                printer.printText("ok")
            }
    },
    MIN {
        override fun queryString(request: HttpServletRequest) = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1"
        override fun evaluate(statement: Statement, request: HttpServletRequest, printer: PrettyPrinter) =
            execute(statement, request) {
                printSequence(it, printer, header = "Product with min price: ")
            }
    },
    MAX {
        override fun queryString(request: HttpServletRequest) = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1"
        override fun evaluate(statement: Statement, request: HttpServletRequest, printer: PrettyPrinter) =
            execute(statement, request) {
                printSequence(it, printer, header = "Product with max price: ")
            }
    },
    SUM {
        override fun queryString(request: HttpServletRequest) = "SELECT SUM(price) FROM PRODUCT"
        override fun evaluate(statement: Statement, request: HttpServletRequest, printer: PrettyPrinter) =
            execute(statement, request) {
                printInt(it, printer, text = "Summary price: %d")
            }
    },
    COUNT {
        override fun queryString(request: HttpServletRequest) = "SELECT COUNT(*) FROM PRODUCT"
        override fun evaluate(statement: Statement, request: HttpServletRequest, printer: PrettyPrinter) =
            execute(statement, request) {
                printInt(it, printer, text = "Number of products: %d")
            }
    },
    GET {
        override fun queryString(request: HttpServletRequest) = "SELECT * FROM PRODUCT"
        override fun evaluate(statement: Statement, request: HttpServletRequest, printer: PrettyPrinter) =
            execute(statement, request) {
                printSequence(it, printer)
            }
    };

    abstract fun evaluate(statement: Statement, request: HttpServletRequest, printer: PrettyPrinter)
    abstract fun queryString(request: HttpServletRequest): String

    protected fun productSequence(res: ResultSet): Sequence<String> = sequence {
        while (res.next()) {
            val name = res.getString("name")
            val price = res.getInt("price")

            yield(name + "\t" + price)
        }
    }

    protected fun execute(statement: Statement, request: HttpServletRequest, toExecute: (ResultSet) -> Unit) =
        queryString(request).run(statement::executeQuery).use(toExecute)

    protected fun update(statement: Statement, request: HttpServletRequest, post: (Int) -> Unit) =
        queryString(request).run(statement::executeUpdate).run(post)

    protected fun printSequence(res: ResultSet, printer: PrettyPrinter, header: String? = null) {
        header?.let { printer.header(header) }
        printer.printSequence(productSequence(res))
    }

    protected fun printInt(res: ResultSet, printer: PrettyPrinter, text: String) {
        if (res.next()) {
            val result = res.getInt(1)
            val formattedText = text.format(result)

            printer.printText(formattedText)
        }
    }
}