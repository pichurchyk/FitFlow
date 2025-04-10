package com.pichurchyk.fitflow.common.ext

fun Double.toPrettyString() = if (this % 1.0 == 0.0) {
    this.toInt().toString()
} else {
    this.toString()
}