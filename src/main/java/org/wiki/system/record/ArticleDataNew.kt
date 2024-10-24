package org.wiki.system.record


import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.wiki.system.doman.Article
import java.time.LocalDate

data class ArticleDataNew @JsonCreator constructor(
    @JsonProperty("title") val title: String,
    @JsonProperty("resume") val resume: String? = null,
    @JsonProperty("content") val content: String,
    @JsonProperty("linkImg") val linkImg: String,
    @JsonProperty("idAuthor") val idAuthor: Long,
    @JsonProperty("status") val status: Int,
    @JsonProperty("idTag") val idTag: Long,
    @JsonProperty("dtPublish") val dtPublish: LocalDate,
    @JsonProperty("dtCreate") val dtCreate: LocalDate,
    @JsonProperty("dtLastUpdate") val dtLastUpdate: LocalDate
) {
    fun toArticle() = Article(this)
}
