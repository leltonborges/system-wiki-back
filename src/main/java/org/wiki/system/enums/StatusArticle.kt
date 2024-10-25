package org.wiki.system.enums

enum class StatusArticle(val code: Int,
                         val description: String) {
    ACTIVE(1, "Active"),
    INACTIVE(0, "Inactive"),
    CANCEL(2, "Cancel");

    companion object {
        fun findByCode(code: Int): StatusArticle? {
            return entries.find { it.code == code }
        }
    }
}