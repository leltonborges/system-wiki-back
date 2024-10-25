package org.wiki.system.record

import org.bson.types.ObjectId
import org.wiki.system.doman.Article
import org.wiki.system.doman.Author
import org.wiki.system.doman.Tag
import java.time.LocalDate

data class ArticleDataDetail(
    val id: ObjectId,
    val title: String,
    val resume: String,
    val content: String,
    val linkImg: String,
    val authorName: String,
    val status: Int,
    val tagName: String,
    val dtPublish: LocalDate,
    val dtCreate: LocalDate,
    val dtLastUpdate: LocalDate,
    val yearMonth: Int
                            ) {
    constructor(article: Article) : this(
        article.id!!,
        article.title,
        article.resume,
        article.content,
        article.linkImg,
        authorName = Author.findById(article.idAuthor)?.name ?: "DESCONHECIDO",
        article.status,
        tagName = Tag.findById(article.idTag)?.name ?: "DESCONHECIDO",
        article.dtPublish,
        article.dtCreate,
        article.dtLastUpdate,
        article.yearMonth
                                        );
}
