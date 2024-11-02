package org.wiki.system.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class OptionalMinSizeValidator: ConstraintValidator<OptionalMinSizeValid, String> {
    private var min: Int = 0

    override fun initialize(optionalMinSizeValid: OptionalMinSizeValid) {
        this.min = optionalMinSizeValid.min
    }
    override fun isValid(content: String?, context: ConstraintValidatorContext): Boolean {
        return content.isNullOrBlank() || content.length >= min
    }
}