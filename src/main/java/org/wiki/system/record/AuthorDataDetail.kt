package org.wiki.system.record

import org.bson.types.ObjectId

data class AuthorDataDetail(
    val id: ObjectId,
    val name: String,
    val email: String
)
