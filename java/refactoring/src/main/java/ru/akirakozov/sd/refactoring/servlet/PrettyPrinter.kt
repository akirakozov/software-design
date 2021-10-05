package ru.akirakozov.sd.refactoring.servlet

import java.io.Closeable
import javax.servlet.http.HttpServletResponse

class PrettyPrinter private constructor(private val response: HttpServletResponse): Closeable {
    companion object {
        fun withPrinter(response: HttpServletResponse, lambda: (PrettyPrinter) -> Unit) {
            PrettyPrinter(response).use { lambda(it) }
        }
    }

    private val stringBuilder = StringBuilder()

    fun printSequence(sequence: Sequence<String>) {
        sequence.forEach { text -> stringBuilder.appendLine("$text</br>") }
    }

    override fun close() {
        response.writer.print(stringBuilder)
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }

    fun header(text: String) {
        stringBuilder.appendLine("<h1>$text</h1>")
    }

    fun printText(text: String) {
        stringBuilder.appendLine(text)
    }
}