package org.wiki.system.resource.response

import io.quarkus.mongodb.panache.kotlin.PanacheQuery

fun <T : Any, R : Any> PanacheQuery<T>.toPaginatedResponse(
    page: Int,
    pageSize: Int,
    map: (T) -> R
): PaginatedResponse<R> {
    val items = this.page(page, pageSize)
        .list()
        .map(map)
    val totalCount = this.count()
    val totalPages = (totalCount / pageSize) + 1
    return PaginatedResponse(items, totalCount, totalPages.toInt(), page)
}