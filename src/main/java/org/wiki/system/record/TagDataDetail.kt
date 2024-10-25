package org.wiki.system.record

import org.bson.types.ObjectId
import org.wiki.system.doman.Tag

data class TagDataDetail(
    val id: ObjectId,
    val name: String,
    val status: Int,
    val description: String
                        ) {
    constructor(tag: Tag) : this(tag.id!!, tag.name, tag.status, tag.description)
}
