package org.wiki.system.doman

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheQuery
import org.wiki.system.record.TagDataDetail

@MongoEntity(collection = "doc_tag")
class Tag() : PanacheMongoEntity() {
    lateinit var name: String
    lateinit var description: String
    var status: Int = 0

    fun toDetail(): TagDataDetail {
        return TagDataDetail(this);
    }

    companion object : PanacheMongoCompanion<Tag> {
        fun findByStatus(status: Int): PanacheQuery<Tag> {
            return find("status", status)
        }
    }
}