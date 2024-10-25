package org.wiki.system.resource

import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.wiki.system.doman.Author
import org.wiki.system.record.AuthorDataNew
import org.wiki.system.resource.response.toPaginatedResponse
import org.wiki.system.validator.ValidId

@Path("/author")
class AuthorResource {

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun newAuthor(authorNew: AuthorDataNew): Response {
        val author = authorNew.toAuthor();
        author.persist()
        return Response.ok(author.toDetail())
                .build();
    }

    @GET
    @Path("/{id}")
    fun findById(@Valid
                 @ValidId(message = "O tamanho esperado é de 24 caracteres", size = 24)
                 @PathParam("id") id: String): Response {
        return Response.ok(Author.findById(ObjectId(id))
                                   ?.toDetail())
                .build()
    }

    @GET
    @Path("/list")
    fun findAllPage(@QueryParam("page") page: Int = 0,
                    @QueryParam("size") size: Int = 10): Response {
        val response = Author.findAll()
                .toPaginatedResponse(page, size, Author::toDetail)
        return Response.ok(response)
                .build()
    }
}