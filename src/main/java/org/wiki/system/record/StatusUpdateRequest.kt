package org.wiki.system.record

import io.smallrye.common.constraint.NotNull
import org.wiki.system.enums.StatusArticle

data class StatusUpdateRequest(
    @field:NotNull
    val newStatus: StatusArticle
)
