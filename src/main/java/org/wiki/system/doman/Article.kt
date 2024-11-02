package org.wiki.system.doman

import com.fasterxml.jackson.annotation.JsonFormat
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheQuery
import org.bson.types.ObjectId
import org.wiki.system.record.ArticleDataDetail
import org.wiki.system.resource.params.FilterArticleParams
import java.time.LocalDate

@MongoEntity(collection = "doc_article")
class Article() : PanacheMongoEntity() {
    lateinit var title: String
    lateinit var resume: String
    lateinit var content: String
    lateinit var linkImg: String
    lateinit var idAuthor: ObjectId
    var status: Int = 0
    lateinit var idTag: ObjectId
    var yearMonth: Int = 0

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    lateinit var dtPublish: LocalDate

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    lateinit var dtCreate: LocalDate

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    lateinit var dtLastUpdate: LocalDate

    fun toDetail(): ArticleDataDetail {
        return ArticleDataDetail(this)
    }

    companion object : PanacheMongoCompanion<Article> {
        fun findByStatusAndFilter(status: Int, filter: FilterArticleParams): PanacheQuery<Article> {
            val queryConditions = mutableListOf<String>()
            val queryParameters = mutableMapOf<String, Any>()

            queryConditions.add("status = :status")
            queryParameters["status"] = status

            val authorIds = filter.authorName?.let { Author.findByName(it).mapNotNull { author -> author.id } }

            filter.title?.let {
                queryConditions.add("title like :title")
                queryParameters["title"] = "%${it}%"
            }

            filter.tagId?.let {
                queryConditions.add("idTag = :tagId")
                queryParameters["tagId"] = it
            }

            filter.endDate.let {
                queryConditions.add("yearMonth <= :endDate")
                queryParameters["endDate"] = it
            }

            filter.startDate?.let {
                queryConditions.add("yearMonth >= :startDate")
                queryParameters["startDate"] = it
            }

            authorIds?.takeIf { it.isNotEmpty() }?.let {
                queryConditions.add("idAuthor in :authorIds")
                queryParameters["authorIds"] = it
            }

            return find(queryConditions.joinToString(" and "), queryParameters)
                .page(filter.page, filter.pageSize)
        }

        fun findBySearch(keyword: String): PanacheQuery<Article> {
            val regex = "(?i).*${keyword}.*"
            return find(
                "{ \$or: [                          " +
                        "   { title: { \$regex: ?1 } },     " +
                        "   { resume: { \$regex: ?1 } },    " +
                        "   { content: { \$regex: ?1 } }    " +
                        "   ] }                             ", regex
            )
        }

        fun findByDateRangeAndStatus(startDate: LocalDate, endDate: LocalDate, status: Int): List<Article> {
            return find("{ dtPublish: { \$gte: ?1, \$lte: ?2 }, status: ?3 }", startDate, endDate, status).list()
        }
    }
}
