package org.wiki.system.record


import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId
import org.wiki.system.doman.Article
import org.wiki.system.doman.Author
import org.wiki.system.doman.Tag
import org.wiki.system.util.formatYearMonth
import java.time.LocalDate
import java.time.YearMonth

data class ArticleDataNew @JsonCreator constructor(
    @JsonProperty("title") val title: String,
    @JsonProperty("resume") val resume: String? = null,
    @JsonProperty("content") val content: String,
    @JsonProperty("linkImg") val linkImg: String,
    @JsonProperty("idAuthor") val idAuthor: String,
    @JsonProperty("status") val status: Int,
    @JsonProperty("idTag") val idTag: String,
    @JsonProperty("dtPublish") val dtPublish: LocalDate,
    @JsonProperty("dtCreate") val dtCreate: LocalDate,
    @JsonProperty("dtLastUpdate") val dtLastUpdate: LocalDate
) {
    fun toArticle(): Article {
        val tagId = ObjectId(this@ArticleDataNew.idTag)
        val authorId = ObjectId(this@ArticleDataNew.idAuthor)

        Author.findById(authorId) ?: throw IllegalArgumentException("Author invalid")
        Tag.findById(tagId) ?: throw IllegalArgumentException("Tag invalid")

        return Article().apply {
            this.title = this@ArticleDataNew.title
            this.resume = this@ArticleDataNew.resume ?: ""
            this.content = this@ArticleDataNew.content
            this.linkImg = this@ArticleDataNew.linkImg
            this.status = this@ArticleDataNew.status
            this.idAuthor = authorId
            this.idTag = tagId
            this.dtPublish = this@ArticleDataNew.dtPublish
            this.dtCreate = this@ArticleDataNew.dtCreate
            this.dtLastUpdate = this@ArticleDataNew.dtLastUpdate
            this.yearMonth = formatYearMonth(YearMonth.now())
        }
    }
}
