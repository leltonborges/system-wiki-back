package org.wiki.system.record

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.wiki.system.doman.Tag

class TagDataNew {
    @NotBlank(message = "Name is not null")
    @Size(min = 2, max = 100, message = "Name size must be between 2 and 100")
    lateinit var name: String;
    @NotBlank(message = "Description is not null")
    @Size(min = 2, max = 100, message = "Description size must be between 2 and 100")

    lateinit var description: String
    fun toTag(): Tag {
        return Tag().apply {
            this.name = this@TagDataNew.name;
            this.description = this@TagDataNew.description
        }
    }
}