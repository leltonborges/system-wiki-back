package org.wiki.system.record


import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.bson.types.ObjectId
import org.hibernate.validator.constraints.URL
import org.wiki.system.doman.Article
import org.wiki.system.doman.Author
import org.wiki.system.doman.Tag
import org.wiki.system.util.formatYearMonth
import org.wiki.system.validator.IdValid
import org.wiki.system.validator.OptionalMinSizeValid
import java.time.LocalDate
import java.time.YearMonth

class ArticleDataNew {
    @Size(min = 5, max = 100, message = "Title size must be between 5 and 100")
    @JsonProperty("title")
    lateinit var title: String

    @OptionalMinSizeValid(min = 10, message = "Resume size must be greater than 10")
    @JsonProperty("resume")
    lateinit var resume: String

    @Size(min = 20, message = "Content size must be greater than 20")
    @JsonProperty("content")
    lateinit var content: String

    @JsonProperty("linkImg")
    @NotBlank(message = "LinkImg is not null")
    @URL(message = "LinkImg must be valid url")
    lateinit var linkImg: String

    @IdValid
    @JsonProperty("idAuthor")
    lateinit var idAuthor: String;

    @IdValid
    @JsonProperty("idTag")
    lateinit var idTag: String

    fun toArticle(): Article {
        val tagId = ObjectId(this@ArticleDataNew.idTag)
        val authorId = ObjectId(this@ArticleDataNew.idAuthor)

        Author.findById(authorId) ?: throw IllegalArgumentException("Author invalid")
        Tag.findById(tagId) ?: throw IllegalArgumentException("Tag invalid")

        return Article().apply {
            this.title = this@ArticleDataNew.title
            this.resume = this@ArticleDataNew.resume
            this.content = this@ArticleDataNew.content
            this.linkImg = this@ArticleDataNew.linkImg
            this.idAuthor = authorId
            this.idTag = tagId
            this.status = 1
            this.dtPublish = LocalDate.now()
            this.dtCreate = LocalDate.now()
            this.dtLastUpdate = LocalDate.now()
            this.yearMonth = formatYearMonth(YearMonth.now())
        }
    }
}
