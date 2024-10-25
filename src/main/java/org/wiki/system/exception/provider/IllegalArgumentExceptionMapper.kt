package org.wiki.system.exception.provider

import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.wiki.system.exception.Standard

@Provider
class IllegalArgumentExceptionMapper(@Context val uriInfo: UriInfo) : ExceptionMapper<IllegalArgumentException> {
    override fun toResponse(exception: IllegalArgumentException): Response {
        val standardResponse = Standard(
            message = exception.message ?: "IllegalArgumentException",
            statusCode = Response.Status.BAD_REQUEST.statusCode,
            path = this.uriInfo.requestUri.path,
        )
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(standardResponse).build()
    }
}