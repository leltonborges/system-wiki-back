package org.wiki.system.resource.params

import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter

open class PageParams {
    @QueryParam("page")
    @Parameter(description = "Number page")
    @NotNull(message = "page is not null")
    var page: Int = 0;

    @QueryParam("pageSize")
    @Parameter(description = "Number page size")
    @NotNull(message = "pageSize is not null")
    var pageSize: Int = 10
}