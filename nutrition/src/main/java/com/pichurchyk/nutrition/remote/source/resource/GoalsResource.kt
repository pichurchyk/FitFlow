package com.pichurchyk.nutrition.remote.source.resource

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/rest/v1/Goals")
class GoalsResource