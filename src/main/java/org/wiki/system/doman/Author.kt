package org.wiki.system.doman

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity

@MongoEntity(collection = "doc_author")
class Author : PanacheMongoEntity() {
    lateinit var name: String
    lateinit var email: String

    companion object : PanacheMongoCompanion<Author> {}
}