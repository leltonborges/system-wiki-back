package org.wiki.system.doman

import com.fasterxml.jackson.annotation.JsonFormat
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Sort
import io.quarkus.panache.common.Sort.Direction
import org.bson.types.ObjectId
import org.wiki.system.record.ArticleDataDetail
import org.wiki.system.resource.params.FilterArticleParams
import java.time.LocalDate
import java.util.*

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
            val query = StringJoiner(" and ");
            val parameters = mutableMapOf<String, Any>()

            query.add("status = :status")
            parameters["status"] = status

            filter.title?.let {
                query.add("title like :title")
                parameters["title"] = "%${it}%"
            }

            filter.tagId?.let {
                query.add("idTag = :tagId")
                parameters["tagId"] = ObjectId(it)
            }

            filter.endDate.let {
                query.add("yearMonth <= :endDate")
                parameters["endDate"] = it
            }

            filter.startDate?.let {
                query.add("yearMonth >= :startDate")
                parameters["startDate"] = it
            }

            filter.authorName?.let { name ->
                val authorIds = Author.findByName(name).mapNotNull { it.id }
                if (authorIds.isNotEmpty()) {
                    query.add("idAuthor in :authorIds")
                    parameters["authorIds"] = authorIds
                }
            }

            val sort = Sort.by("yearMonth", Direction.Descending)
                .and("dtLastUpdate", Direction.Descending)
            return find(query.toString(), sort, parameters)
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
