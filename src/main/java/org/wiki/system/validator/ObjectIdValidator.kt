package org.wiki.system.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.bson.types.ObjectId

class ObjectIdValidator : ConstraintValidator<IdValid, String> {
    private var size: Int = 0
    private var optional: Boolean = false
    override fun initialize(constraint: IdValid) {
        this.size = constraint.size
        this.optional = constraint.optional
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (optional && value.isNullOrBlank()) return true
        return !value.isNullOrBlank() && value.length >= size && ObjectId.isValid(value)
    }
}