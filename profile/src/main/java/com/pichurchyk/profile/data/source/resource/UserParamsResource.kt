package com.pichurchyk.profile.data.source.resource

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/rest/v1/UserParams")
class UserParamsResource()