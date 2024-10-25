package org.wiki.system.resource.response

data class PaginatedResponse<T>(
    val data: List<T>,
    val totalCount: Long,
    val totalPages: Int,
    val currentPage: Int,
                               )
