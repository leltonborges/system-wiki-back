package org.wiki.system.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.bson.types.ObjectId

class ObjectIdValidator : ConstraintValidator<IdValid, String> {
    private var size: Int = 0
    override fun initialize(constraint: IdValid) {
        this.size = constraint.size
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value != null && value.length >= this.size && ObjectId.isValid(value)
    }
}