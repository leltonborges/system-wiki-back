package org.wiki.system.doman

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.wiki.system.record.AuthorDataDetail

@MongoEntity(collection = "doc_author")
class Author : PanacheMongoEntity() {
    lateinit var name: String
    lateinit var email: String

    fun toDetail(): AuthorDataDetail {
        return AuthorDataDetail(
            id = this.id!!,
            name,
            email
        )
    }

    companion object : PanacheMongoCompanion<Author> {
        fun findByName(name: String): List<Author> {
            val regex = "(?i).*${name}.*"
            return find("{'name': {\$regex: ?1}}", regex)
                .list()
                .distinct()
        }
    }
}