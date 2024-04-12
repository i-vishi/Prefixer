package dev.vishalgaur.prefixer.base

internal sealed class PrefValueType(open val value: Any?) {
    data class StringType(override val value: String?) : PrefValueType(value)
    data class LongType(override val value: Long) : PrefValueType(value)
    data class BooleanType(override val value: Boolean) : PrefValueType(value)
}
