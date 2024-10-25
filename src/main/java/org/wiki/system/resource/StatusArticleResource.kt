package org.wiki.system.resource

import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.wiki.system.doman.StatusArticle
import org.wiki.system.validator.ValidId

@Path("/status-article")
class StatusArticleResource {

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun newAuthor(statusArticle: StatusArticle): Response {
        statusArticle.persist()
        return Response.ok(statusArticle).build();
    }

    @GET
    @Path("/{id}")
    fun findById(
        @Valid
        @ValidId(message = "O tamanho esperado é de 24 caracteres", size = 24)
        @PathParam("id") id: String
    ): Response {
        return Response.ok(StatusArticle.findById(ObjectId(id))).build()
    }

    @GET
    @Path("/list")
    fun findAllPage(
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") size: Int = 10
    ): Response {
        return Response.ok(StatusArticle.findAll().page(page, size)).build()
    }
}