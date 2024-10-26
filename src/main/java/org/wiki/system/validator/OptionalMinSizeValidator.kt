package org.wiki.system.validator

import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

@ApplicationScoped
class OptionalMinSizeValidator: ConstraintValidator<OptionalMinSizeValid, String> {
    var min: Int = 0

    override fun initialize(optionalMinSizeValid: OptionalMinSizeValid) {
        this.min = optionalMinSizeValid.min
    }
    override fun isValid(content: String?, context: ConstraintValidatorContext): Boolean {
        return content.isNullOrBlank() || content.length >= min
    }
}