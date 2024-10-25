package org.wiki.system.exception

class FieldMessage(
    val constraintType: String,
    val field: String,
    val message: String,
    val invalidValue: Any? = null
)