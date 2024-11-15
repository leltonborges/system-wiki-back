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
import org.wiki.system.record.StatusUpdateRequest
import org.wiki.system.resource.params.FilterArticleParams
import org.wiki.system.resource.params.PageParams
import org.wiki.system.resource.response.toPaginatedResponse
import org.wiki.system.util.formatYearMonth
import org.wiki.system.validator.IdValid
import java.time.LocalDate
import java.time.YearMonth

@Path("/article")
class ArticleResource {

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun saveNewPerson(@Valid newArticle: ArticleDataNew): Response {
        val article = newArticle.toArticle();
        article.persist()
        return Response.ok(article.toDetail())
            .build();
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
        val article = Article.findById(ObjectId(id)) ?: return Response.status(Response.Status.NOT_FOUND)
            .build()
        return Response.ok(ArticleDataDetail(article))
            .build()
    }


    @PATCH
    @Path("/{id}/change/status")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun changeStatusArticle(
        @Valid
        @IdValid
        @PathParam("id") id: String,
        @Valid
        request: StatusUpdateRequest
    ): Response {
        val article = Article.findById(ObjectId(id)) ?: return Response.status(Response.Status.NOT_FOUND).build()

        article.status = request.newStatus.code
        article.update()

        return Response.ok(Response.Status.NO_CONTENT).build()
    }


    @PUT
    @Path("/{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateById(
        @Valid
        @IdValid
        @PathParam("id") id: String,
        @Valid newArticle: ArticleDataNew
    ): Response {
        val article = Article.findById(ObjectId(id)) ?: return Response.status(Response.Status.NOT_FOUND)
            .build()
        article.also {
            it.title = newArticle.title
            it.resume = newArticle.resume
            it.content = newArticle.content
            it.linkImg = newArticle.linkImg
            it.idAuthor = ObjectId(newArticle.idAuthor)
            it.idTag = ObjectId(newArticle.idTag)
            it.dtLastUpdate = LocalDate.now()
            it.yearMonth = formatYearMonth(YearMonth.now())
        }
        article.update()
        return Response.ok(ArticleDataDetail(article))
            .build()
    }

    @GET
    @Path("/list/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun listAll(@BeanParam page: PageParams): Response {
        val response = Article.findAll()
            .toPaginatedResponse(page.page, page.pageSize, Article::toDetail)
        return Response.ok(response)
            .build();
    }

    @GET
    @Path("/list/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun listAllByStatus(
        @PathParam("status") status: Int,
        @Valid
        @BeanParam
        filter: FilterArticleParams
    ): Response {
        val response = Article.findByStatusAndFilter(status, filter)
            .toPaginatedResponse(filter.page, filter.pageSize, Article::toDetail)
        return Response.ok(response)
            .build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun search(
        @Valid
        @Size(min = 5, message = "Tamanho mínimo esperado é de 5 caracteres")
        @QueryParam("keyword") keyword: String,
        @BeanParam page: PageParams
    ): Response {
        val articles =
            Article.findBySearch(keyword)
                .toPaginatedResponse(page.page, page.pageSize, Article::toDetail)
        return Response.ok(articles)
            .build()
    }
}