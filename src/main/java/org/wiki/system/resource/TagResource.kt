package org.wiki.system.resource

import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.wiki.system.doman.Tag
import org.wiki.system.resource.response.toPaginatedResponse
import org.wiki.system.validator.ValidId

@Path("/tag")
class TagResource {

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun newAuthor(tag: Tag): Response {
        tag.persist()
        return Response.ok(tag)
                .build();
    }

    @GET
    @Path("/{id}")
    fun findById(@Valid
                 @ValidId(message = "O tamanho esperado é de 24 caracteres", size = 24)
                 @PathParam("id") id: String): Response {
        return Response.ok(Tag.findById(ObjectId(id)))
                .build()
    }

    @GET
    @Path("/list/all")
    fun findAllPage(@QueryParam("page") page: Int = 0,
                    @QueryParam("size") size: Int = 10): Response {
        val response = Tag.findAll()
                .toPaginatedResponse(page, size, Tag::toDetail)
        return Response.ok(response)
                .build()
    }

    @GET
    @Path("/list/status/{status}")
    fun findAllPage(@PathParam("status") status: Int,
                    @QueryParam("page") page: Int = 0,
                    @QueryParam("size") size: Int = 10): Response {
        val response = Tag.findByStatus(status)
                .toPaginatedResponse(page, size, Tag::toDetail)
        return Response.ok(response)
                .build()
    }
}