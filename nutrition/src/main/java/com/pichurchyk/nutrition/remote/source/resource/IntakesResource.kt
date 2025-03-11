package com.pichurchyk.nutrition.remote.source.resource

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/functions/v1/save-intake")
class IntakesResource