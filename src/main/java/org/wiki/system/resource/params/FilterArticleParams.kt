package org.wiki.system.resource.params

import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.wiki.system.util.formatYearMonth
import org.wiki.system.validator.OptionalMinSizeValid
import java.time.YearMonth

class FilterArticleParams : PageParams() {
    @QueryParam("title")
    @OptionalMinSizeValid(min = 5)
    @Parameter(description = "Title of the article, if provided, the minimum is 5 characters")
    var title: String? = null;

    @QueryParam("authorName")
    @OptionalMinSizeValid(min = 3)
    @Parameter(description = "Name of the author of the article, if provided, the minimum is 3 characters")
    var authorName: String? = null;

    @QueryParam("tagId")
    @Parameter(description = "ID da tag")
    var tagId: String? = null;

    @QueryParam("endDate")
    @Parameter(description = "Year month")
    @NotNull(message = "EndDate is not null")
    var endDate: Int = formatYearMonth(YearMonth.now());

    @QueryParam("startDate")
    @Parameter(description = "Year month")
    var startDate: Int? = null;
}
