package com.pichurchyk.fitflow.auth.model

data class User(
    val id: String,
    val name: String? = null,
    val avatarUrl: String? = null,
    val email: String? = null
)