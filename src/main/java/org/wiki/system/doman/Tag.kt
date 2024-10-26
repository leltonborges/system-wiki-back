package org.wiki.system.doman

import io.quarkus.cache.CacheInvalidate
import io.quarkus.cache.CacheResult
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

    @CacheInvalidate(cacheName = "tagsByStatus")
    fun save() {
        super.persist()
    }
    @CacheInvalidate(cacheName = "tagsByStatus")
    fun modify() {
        super.update()
    }

    companion object : PanacheMongoCompanion<Tag> {
        @CacheResult(cacheName = "tagsByStatus")
        fun findByStatus(status: Int): PanacheQuery<Tag> {
            return find("status", status)
        }
    }
}