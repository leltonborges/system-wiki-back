package org.wiki.system.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.quarkus.jackson.ObjectMapperCustomizer
import jakarta.inject.Singleton

@Singleton
class JacksonObjectMapper : ObjectMapperCustomizer {
    override fun customize(objectMapper: ObjectMapper?) {
        objectMapper?.registerModule(JavaTimeModule())
        objectMapper?.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }
}