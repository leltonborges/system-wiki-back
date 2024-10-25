package org.wiki.system.record

import org.wiki.system.doman.Tag

data class TagDataNew(
    val name: String,
    val description: String
) {
    fun toTag(): Tag {
        return Tag().apply {
            this.name = this@TagDataNew.name;
            this.description = this@TagDataNew.description
        }
    }
}