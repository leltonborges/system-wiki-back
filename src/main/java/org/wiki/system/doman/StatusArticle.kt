package org.wiki.system.doman

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity

@MongoEntity(collection = "status_article")
class StatusArticle : PanacheMongoEntity() {
    lateinit var name: String
    lateinit var description: String
}