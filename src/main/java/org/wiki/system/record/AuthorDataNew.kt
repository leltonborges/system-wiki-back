package org.wiki.system.record

import org.wiki.system.doman.Author

data class AuthorDataNew(
    val name: String,
    val email: String,
                        ) {
    fun toAuthor(): Author {
        return Author().apply {
            name = this@AuthorDataNew.name;
            email = this@AuthorDataNew.email
        }
    }
}
