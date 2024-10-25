package org.wiki.system.doman

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity

@MongoEntity(collection = "doc_tag")
class Tag() : PanacheMongoEntity() {
    lateinit var name: String
    lateinit var description: String

    companion object : PanacheMongoCompanion<Tag> {}
}