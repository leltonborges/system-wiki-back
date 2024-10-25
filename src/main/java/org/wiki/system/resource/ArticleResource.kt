package org.wiki.system.resource


import io.quarkus.panache.common.Page
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.wiki.system.doman.Article
import org.wiki.system.record.ArticleDataNew
import org.wiki.system.validator.ValidId

@Path("/article")
class ArticleResource {

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun saveNewPerson(article: ArticleDataNew): Response {
        val newArticle = article.toArticle();
        Article.persist(newArticle)
        return Response.ok(newArticle.toDetail()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun findById(
        @Valid
        @ValidId(message = "O tamanho esperado é de 24 caracteres", size = 24)
        @PathParam("id") id: String
    ): Response {
        val article = Article.findById(ObjectId(id)) ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(article.toDetail()).build()
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") pageSize: Int = 20
    ): Response {
        val details = Article.findAll().page(page, pageSize).list().map { it.toDetail() }
        return Response.ok(details).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    fun search(
        @QueryParam("keyword") keyword: String,
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") size: Int = 10,
    ): Response {
        val articles = Article.findByTitleOrContent(keyword, Page(page, size)).map { it.toDetail() }
        return Response.ok(articles).build()
    }
}