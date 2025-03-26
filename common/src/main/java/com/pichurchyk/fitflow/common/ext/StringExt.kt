package com.pichurchyk.fitflow.common.ext

fun String.removeQuotes(): String {
    return if (this.startsWith("\"") && this.endsWith("\"")) {
        this.substring(1, this.length - 1)
    } else {
        this
    }
}
