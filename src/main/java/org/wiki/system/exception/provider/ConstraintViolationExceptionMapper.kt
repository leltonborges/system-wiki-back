package org.wiki.system.exception.provider

import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.wiki.system.exception.FieldMessage
import org.wiki.system.exception.StandardFiled

@Provider
class ConstraintViolationExceptionMapper(@Context val uriInfo: UriInfo) :
    ExceptionMapper<ConstraintViolationException> {
    override fun toResponse(exception: ConstraintViolationException): Response {
        val fields = exception.constraintViolations.map { field ->
            FieldMessage(
                field.constraintDescriptor.annotation.annotationClass.simpleName ?: "Unknown",
                field.propertyPath.toString(),
                field.message,
                field.invalidValue
            )
        }.toList()

        val status = Response.Status.BAD_REQUEST
        val errorResponse = StandardFiled(
            message = "Invalid fields",
            statusCode = status.statusCode,
            path = uriInfo.requestUri.path,
            fields
        )
        return Response.status(status).entity(errorResponse).build()
    }
}