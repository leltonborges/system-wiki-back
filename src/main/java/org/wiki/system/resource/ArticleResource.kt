package org.wiki.system.resource


import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.wiki.system.doman.Article
import org.wiki.system.record.ArticleDataDetail
import org.wiki.system.record.ArticleDataNew
import org.wiki.system.resource.response.toPaginatedResponse
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
        return Response.ok(newArticle.toDetail())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun findById(@Valid
                 @ValidId(message = "O tamanho esperado é de 24 caracteres", size = 24)
                 @PathParam("id") id: String): Response {
        val article = Article.findById(ObjectId(id)) ?: return Response.status(Response.Status.NOT_FOUND)
                .build()
        return Response.ok(ArticleDataDetail(article))
                .build()
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun listAll(@QueryParam("page") page: Int = 0,
                @QueryParam("size") pageSize: Int = 20): Response {
        val response = Article.findAll()
                .toPaginatedResponse(page, pageSize, Article::toDetail)
        return Response.ok(response)
                .build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    fun search(@Valid
               @Size(min = 5, message = "Tamanho mínimo esperado é de 5 caracteres")
               @QueryParam("keyword") keyword: String,
               @QueryParam("page") page: Int = 0,
               @QueryParam("size") size: Int = 10): Response {
        val articles =
            Article.findBySearch(keyword)
                    .toPaginatedResponse(page, size, Article::toDetail)
        return Response.ok(articles)
                .build()
    }
}