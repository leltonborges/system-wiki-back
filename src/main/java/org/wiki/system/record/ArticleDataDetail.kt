package org.wiki.system.record

import org.bson.types.ObjectId
import java.time.LocalDate

data class ArticleDataDetail(
    val id: ObjectId,
    val title: String,
    val resume: String,
    val content: String,
    val linkImg: String,
    val authorName: String,
    val status: String,
    val tagName: String,
    val dtPublish: LocalDate,
    val dtCreate: LocalDate,
    val dtLastUpdate: LocalDate,
    val yearMonth: Int
)
