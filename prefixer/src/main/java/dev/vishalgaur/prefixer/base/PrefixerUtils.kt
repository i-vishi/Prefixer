package dev.vishalgaur.prefixer.base

import androidx.core.text.isDigitsOnly

internal object PrefixerUtils {

    internal fun validateStringInput(input: String, prefValue: PrefValueType): Boolean {
        return when (prefValue) {
            is PrefValueType.LongType, is PrefValueType.IntType -> input.isDigitsOnly()
            is PrefValueType.FloatType -> input.matches(Regex("^\\d*\\.?\\d*\$"))
            else -> true
        }
    }

    internal fun parsePrefValue(prefValue: PrefValueType, booleanValue: Boolean, stringValue: String): PrefValueType {
        return when (prefValue) {
            is PrefValueType.BooleanType -> PrefValueType.BooleanType(booleanValue)
            is PrefValueType.LongType -> PrefValueType.LongType(stringValue.toLong())
            is PrefValueType.IntType -> PrefValueType.IntType(stringValue.toInt())
            is PrefValueType.FloatType -> PrefValueType.FloatType(stringValue.toFloat())
            is PrefValueType.StringType -> PrefValueType.StringType(stringValue)
        }
    }
}
