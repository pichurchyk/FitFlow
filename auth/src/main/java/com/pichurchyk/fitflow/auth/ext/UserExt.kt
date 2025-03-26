package com.pichurchyk.fitflow.auth.ext

import com.pichurchyk.fitflow.auth.model.User
import com.pichurchyk.fitflow.common.ext.removeQuotes
import io.github.jan.supabase.gotrue.user.UserInfo

fun UserInfo.toUser() = User(
    id = this.id,
    name = this.userMetadata?.get("full_name")?.toString()?.removeQuotes(),
    email = this.email,
    avatarUrl = this.userMetadata?.get("avatar_url")?.toString()?.removeQuotes()
)