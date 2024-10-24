package org.wiki.system.doman

import com.fasterxml.jackson.annotation.JsonFormat
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import io.quarkus.panache.common.Page
import org.wiki.system.record.ArticleDataNew
import org.wiki.system.util.formatYearMonth
import java.time.LocalDate
import java.time.YearMonth

@MongoEntity(collection = "article")
class Article() : PanacheMongoEntity() {
    lateinit var title: String
    lateinit var resume: String
    lateinit var content: String
    lateinit var linkImg: String
    var status: Int = 0
    var idAuthor: Long = 0L
    var idTag: Long = 0L
    var yearMonth: Int = 0

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    lateinit var dtPublish: LocalDate

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    lateinit var dtCreate: LocalDate

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    lateinit var dtLastUpdate: LocalDate

    constructor(data: ArticleDataNew) : this() {
        this.title = data.title
        this.resume = data.resume ?: ""
        this.content = data.content
        this.linkImg = data.linkImg
        this.status = data.status
        this.idAuthor = data.idAuthor
        this.idTag = data.idTag
        this.dtPublish = data.dtPublish
        this.dtCreate = data.dtCreate
        this.dtLastUpdate = data.dtLastUpdate
        this.yearMonth = formatYearMonth(YearMonth.now())
    }

    companion object : PanacheMongoCompanion<Article> {
        fun findByTitleOrContent(keyword: String, page: Page): List<Article> {
            val regex = "(?i).*${keyword}.*"
            return find("{ \$or: [ { title: { \$regex: ?1 } }, { content: { \$regex: ?1 } } ] }", regex)
                .page(page)
                .list()
        }

        fun findByDateRangeAndStatus(startDate: LocalDate, endDate: LocalDate, status: Int): List<Article> {
            return find("{ dtPublish: { \$gte: ?1, \$lte: ?2 }, status: ?3 }", startDate, endDate, status).list()
        }
    }
}
