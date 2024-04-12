package dev.vishalgaur.prefixer.base

import androidx.core.text.isDigitsOnly
import java.math.MathContext

internal object PrefixerUtils {

    internal fun validateStringInput(input: String, prefValue: PrefValueType): Boolean {
        return when (prefValue) {
            is PrefValueType.LongType, is PrefValueType.IntType -> input.isDigitsOnly()
            is PrefValueType.FloatType -> input.matches(Regex("^\\d*\\.?\\d*\$"))
            else -> true
        }
    }

    @Throws(NumberFormatException::class)
    internal fun parsePrefValue(
        prefValue: PrefValueType,
        booleanValue: Boolean,
        stringValue: String,
    ): PrefValueType {
        return when (prefValue) {
            is PrefValueType.BooleanType -> PrefValueType.BooleanType(booleanValue)
            is PrefValueType.LongType -> PrefValueType.LongType(stringValue.toLong())
            is PrefValueType.IntType -> PrefValueType.IntType(stringValue.toInt())
            is PrefValueType.FloatType -> PrefValueType.FloatType(stringValue.toBigDecimal(mathContext = MathContext.DECIMAL128).toFloat())
            is PrefValueType.StringType -> PrefValueType.StringType(stringValue)
        }
    }
}
