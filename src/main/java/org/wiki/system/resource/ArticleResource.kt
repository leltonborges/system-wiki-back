package org.wiki.system.resource


import io.quarkus.panache.common.Page
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.wiki.system.doman.Article
import org.wiki.system.record.ArticleDataNew

@Path("/article")
class ArticleResource {

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun saveNewPerson(@Valid article: ArticleDataNew): Response {
        val newArticle = article.toArticle();
        Article.persist(newArticle)
        return Response.ok(newArticle).build();
    }

//    @POST
//    @Path("/save2")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    fun saveNewPerson2(@Valid article: Article): Response {
//        Article.persist(article);
//        return Response.ok(article).build();
//    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun findById(
        @PathParam("id") id: String
    ): Article? {
        return Article.findById(ObjectId(id))
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") pageSize: Int = 20
    ): List<Article> {
        return Article.findAll().page(page, pageSize).list();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    fun search(
        @QueryParam("keyword") keyword: String,
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") size: Int = 10,
    ): List<Article> {
        return Article.findByTitleOrContent(keyword, Page(page, size))
    }
}