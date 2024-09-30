package dream.nbt.util

import kotlin.experimental.*

/**
 * Converts a [ByteArray] to a [ShortArray].
 *
 * @return The converted [ShortArray].
 */
fun ByteArray.toShortArray(): ShortArray {
  val buffer = ShortArray(size shr 1)
  for ((index, i) in (indices step 2).withIndex()) {
    val v1 = this[i].toInt() and 0xFF
    val v2 = this[i + 1].toInt() and 0xFF
    buffer[index] = (v1 or (v2 shl 8)).toShort()
  }
  return buffer
}

/**
 * Converts a [ByteArray] to an [IntArray].
 *
 * @return The converted [IntArray].
 */
fun ByteArray.toIntArray(): IntArray {
  val buffer = IntArray(size shr 2)
  for ((index, i) in (indices step 4).withIndex()) {
    val v1 = this[i].toInt() and 0xFF
    val v2 = this[i + 1].toInt() and 0xFF
    val v3 = this[i + 2].toInt() and 0xFF
    val v4 = this[i + 3].toInt() and 0xFF
    buffer[index] = (v4 shl 24) or (v3 shl 16) or (v2 shl 8) or v1
  }
  return buffer
}

/**
 * Converts a [ByteArray] to a [LongArray].
 *
 * @return The converted [LongArray].
 */
fun ByteArray.toLongArray(): LongArray {
  val buffer = LongArray(size shr 3)
  for ((index, i) in (indices step 8).withIndex()) {
    val v1 = this[i].toLong() and 0xFF
    val v2 = this[i + 1].toLong() and 0xFF
    val v3 = this[i + 2].toLong() and 0xFF
    val v4 = this[i + 3].toLong() and 0xFF
    val v5 = this[i + 4].toLong() and 0xFF
    val v6 = this[i + 5].toLong() and 0xFF
    val v7 = this[i + 6].toLong() and 0xFF
    val v8 = this[i + 7].toLong() and 0xFF
    buffer[index] = (v8 shl 56) or (v7 shl 48) or (v6 shl 40) or
      (v5 shl 32) or (v4 shl 24) or (v3 shl 16) or
      (v2 shl 8) or v1
  }
  return buffer
}

/**
 * Converts a [ByteArray] to a [FloatArray].
 *
 * @return The converted [FloatArray].
 */
fun ByteArray.toFloatArray(): FloatArray {
  val buffer = FloatArray(size shr 2)
  for ((index, i) in (indices step 4).withIndex()) {
    val bits = (this[i].toInt() and 0xFF) or
      ((this[i + 1].toInt() and 0xFF) shl 8) or
      ((this[i + 2].toInt() and 0xFF) shl 16) or
      ((this[i + 3].toInt() and 0xFF) shl 24)
    buffer[index] = Float.fromBits(bits)
  }
  return buffer
}

/**
 * Converts a [ByteArray] to a [DoubleArray].
 *
 * @return The converted [DoubleArray].
 */
fun ByteArray.toDoubleArray(): DoubleArray {
  val buffer = DoubleArray(size shr 3)
  for ((index, i) in (indices step 8).withIndex()) {
    val bits = (this[i].toLong() and 0xFF) or
      ((this[i + 1].toLong() and 0xFF) shl 8) or
      ((this[i + 2].toLong() and 0xFF) shl 16) or
      ((this[i + 3].toLong() and 0xFF) shl 24) or
      ((this[i + 4].toLong() and 0xFF) shl 32) or
      ((this[i + 5].toLong() and 0xFF) shl 40) or
      ((this[i + 6].toLong() and 0xFF) shl 48) or
      ((this[i + 7].toLong() and 0xFF) shl 56)
    buffer[index] = Double.fromBits(bits)
  }
  return buffer
}

/**
 * Converts a [ByteArray] to a [CharArray].
 *
 * @return The converted [CharArray].
 */
fun ByteArray.toCharArray(): CharArray {
  val buffer = CharArray(size shr 1)
  for ((index, i) in (indices step 2).withIndex()) {
    buffer[index] = (this[i].toInt() and 0xFF or (this[i + 1].toInt() and 0xFF shl 8)).toChar()
  }
  return buffer
}

/**
 * Converts a [ByteArray] to a [BooleanArray].
 *
 * @return The converted [BooleanArray].
 */
fun ByteArray.toBooleanArray(): BooleanArray {
  return BooleanArray(size) { this[it].toInt() and 0xFF == 1 }
}

/**
 * Converts a [ByteArray] to a packed [BooleanArray].
 *
 * @return The converted packed [BooleanArray].
 */
fun ByteArray.toPackedBooleanArray(): BooleanArray {
  val booleanArray = BooleanArray(size shl 3)
  for (i in indices) {
    val byte = this[i]
    val index = i shl 3
    for (j in 0 until 8) {
      booleanArray[index + j] = (byte and ((1 shl j).toByte()) != 0.toByte())
    }
  }
  return booleanArray
}

/**
 * Converts a [ShortArray] to a [ByteArray].
 *
 * @return The converted [ByteArray].
 */
fun ShortArray.toByteArray(): ByteArray {
  val buffer = ByteArray(size shl 1)
  for (i in indices) {
    val value = this[i]
    val index = i shl 1
    buffer[index] = (value.toInt() and 0xFF).toByte()
    buffer[index + 1] = ((value.toInt() shr 8) and 0xFF).toByte()
  }
  return buffer
}

/**
 * Converts an [IntArray] to a [ByteArray].
 *
 * @return The converted [ByteArray].
 */
fun IntArray.toByteArray(): ByteArray {
  val buffer = ByteArray(size shl 2)
  for (i in indices) {
    val value = this[i]
    val index = i shl 2
    buffer[index] = (value and 0xFF).toByte()
    buffer[index + 1] = ((value shr 8) and 0xFF).toByte()
    buffer[index + 2] = ((value shr 16) and 0xFF).toByte()
    buffer[index + 3] = ((value shr 24) and 0xFF).toByte()
  }
  return buffer
}

/**
 * Converts a [LongArray] to a [ByteArray].
 *
 * @return The converted [ByteArray].
 */
fun LongArray.toByteArray(): ByteArray {
  val buffer = ByteArray(size shl 3)
  for (i in indices) {
    val value = this[i]
    val index = i shl 3
    buffer[index] = (value and 0xFF).toByte()
    buffer[index + 1] = ((value shr 8) and 0xFF).toByte()
    buffer[index + 2] = ((value shr 16) and 0xFF).toByte()
    buffer[index + 3] = ((value shr 24) and 0xFF).toByte()
    buffer[index + 4] = ((value shr 32) and 0xFF).toByte()
    buffer[index + 5] = ((value shr 40) and 0xFF).toByte()
    buffer[index + 6] = ((value shr 48) and 0xFF).toByte()
    buffer[index + 7] = ((value shr 56) and 0xFF).toByte()
  }
  return buffer
}

/**
 * Converts a [FloatArray] to a [ByteArray].
 *
 * @return The converted [ByteArray].
 */
fun FloatArray.toByteArray(): ByteArray {
  val buffer = ByteArray(size shl 2)
  for (i in indices) {
    val bits = this[i].toBits()
    val index = i shl 2
    buffer[index] = (bits and 0xFF).toByte()
    buffer[index + 1] = ((bits shr 8) and 0xFF).toByte()
    buffer[index + 2] = ((bits shr 16) and 0xFF).toByte()
    buffer[index + 3] = ((bits shr 24) and 0xFF).toByte()
  }
  return buffer
}

/**
 * Converts a [DoubleArray] to a [ByteArray].
 *
 * @return The converted [ByteArray].
 */
fun DoubleArray.toByteArray(): ByteArray {
  val buffer = ByteArray(size shl 3)
  for (i in indices) {
    val bits = this[i].toBits()
    val index = i shl 3
    buffer[index] = (bits and 0xFF).toByte()
    buffer[index + 1] = ((bits shr 8) and 0xFF).toByte()
    buffer[index + 2] = ((bits shr 16) and 0xFF).toByte()
    buffer[index + 3] = ((bits shr 24) and 0xFF).toByte()
    buffer[index + 4] = ((bits shr 32) and 0xFF).toByte()
    buffer[index + 5] = ((bits shr 40) and 0xFF).toByte()
    buffer[index + 6] = ((bits shr 48) and 0xFF).toByte()
    buffer[index + 7] = ((bits shr 56) and 0xFF).toByte()
  }
  return buffer
}

/**
 * Converts a [CharArray] to a [ByteArray].
 *
 * @return The converted [ByteArray].
 */
fun CharArray.toByteArray(): ByteArray {
  val buffer = ByteArray(size shl 1)
  for (i in indices) {
    val code = this[i].code
    val index = i shl 1
    buffer[index] = (code and 0xFF).toByte()
    buffer[index + 1] = ((code shr 8) and 0xFF).toByte()
  }
  return buffer
}

/**
 * Converts a [BooleanArray] to a [ByteArray].
 *
 * @return The converted [ByteArray].
 */
fun BooleanArray.toByteArray(): ByteArray {
  return ByteArray(size) { if (this[it]) 1 else 0 }
}

/**
 * Converts a [BooleanArray] to a packed [ByteArray].
 *
 * @return The converted packed [ByteArray].
 */
fun BooleanArray.toPackedByteArray(): ByteArray {
  val buffer = ByteArray(size shr 3 + if (size % 8 != 0) 1 else 0)
  var value = 0
  var bits = 0
  for (i in indices) {
    if (this[i]) value = value or (1 shl bits)
    bits++
    if (bits == 8 || i == this.size - 1) {
      buffer[i shr 3] = value.toByte()
      value = 0
      bits = 0
    }
  }
  return buffer
}
