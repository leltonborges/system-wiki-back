package org.wiki.system.resource

import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.wiki.system.doman.Tag
import org.wiki.system.record.TagDataNew
import org.wiki.system.resource.response.toPaginatedResponse
import org.wiki.system.validator.IdValid

@Path("/tag")
class TagResource {

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun newAuthor(@Valid tagNew: TagDataNew): Response {
        val tag = tagNew.toTag()
        tag.save()
        return Response.ok(tag.toDetail()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun findById(
        @Valid
        @IdValid
        @PathParam("id") id: String
    ): Response {
        return Response.ok(Tag.findById(ObjectId(id)))
            .build()
    }

    @GET
    @Path("/list/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun findAllPage(
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") size: Int = 10
    ): Response {
        val response = Tag.findAll()
            .toPaginatedResponse(page, size, Tag::toDetail)
        return Response.ok(response)
            .build()
    }

    @GET
    @Path("/list/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun findAllPage(
        @PathParam("status") status: Int,
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") size: Int = 10
    ): Response {
        val response = Tag.findByStatus(status)
            .toPaginatedResponse(page, size, Tag::toDetail)
        return Response.ok(response)
            .build()
    }

    @PATCH
    @Path("/{id}/name")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun editName(
        @Valid
        @IdValid
        @PathParam("id") id: String,
        @QueryParam("name") name: String
    ): Response {
        val tag = Tag.findById(ObjectId(id))
        tag?.name = name
        tag?.modify()
        return Response.ok(tag?.toDetail()).build()
    }
}