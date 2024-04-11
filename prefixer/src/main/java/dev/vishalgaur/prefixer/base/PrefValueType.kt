package dev.vishalgaur.prefixer.base

sealed class PrefValueType(open val value: Any?) {
    data class StringType(override val value: String?) : PrefValueType(value)
    data class IntType(override val value: Int) : PrefValueType(value)
    data class BooleanType(override val value: Boolean) : PrefValueType(value)
}
