package dream.nbt.util

import java.io.*

fun DataOutput.write24Bits(value: Int) {
  writeByte((value shr 16) and 0xFF)
  writeByte((value shr 8) and 0xFF)
  writeByte(value and 0xFF)
}

fun DataInput.read24Bits(): Int {
  return (readByte().toInt() shl 16) or (readByte().toInt() shl 8) or readByte().toInt()
}

fun DataOutput.write40Bits(value: Long) {
  writeByte(((value shr 32) and 0xFF).toInt())
  writeByte(((value shr 24) and 0xFF).toInt())
  writeByte(((value shr 16) and 0xFF).toInt())
  writeByte(((value shr 8) and 0xFF).toInt())
  writeByte((value and 0xFF).toInt())
}

fun DataInput.read40Bits(): Long {
  return (readByte().toLong() shl 32) or
    (readByte().toLong() shl 24) or
    (readByte().toLong() shl 16) or
    (readByte().toLong() shl 8) or
    readByte().toLong()
}

fun DataOutput.write48Bits(value: Long) {
  writeByte(((value shr 40) and 0xFF).toInt())
  writeByte(((value shr 32) and 0xFF).toInt())
  writeByte(((value shr 24) and 0xFF).toInt())
  writeByte(((value shr 16) and 0xFF).toInt())
  writeByte(((value shr 8) and 0xFF).toInt())
  writeByte((value and 0xFF).toInt())
}

fun DataInput.read48Bits(): Long {
  return (readByte().toLong() shl 40) or
    (readByte().toLong() shl 32) or
    (readByte().toLong() shl 24) or
    (readByte().toLong() shl 16) or
    (readByte().toLong() shl 8) or
    readByte().toLong()
}

fun DataOutput.write56Bits(value: Long) {
  writeByte(((value shr 48) and 0xFF).toInt())
  writeByte(((value shr 40) and 0xFF).toInt())
  writeByte(((value shr 32) and 0xFF).toInt())
  writeByte(((value shr 24) and 0xFF).toInt())
  writeByte(((value shr 16) and 0xFF).toInt())
  writeByte(((value shr 8) and 0xFF).toInt())
  writeByte((value and 0xFF).toInt())
}

fun DataInput.read56Bits(): Long {
  return (readByte().toLong() shl 48) or
    (readByte().toLong() shl 40) or
    (readByte().toLong() shl 32) or
    (readByte().toLong() shl 24) or
    (readByte().toLong() shl 16) or
    (readByte().toLong() shl 8) or
    readByte().toLong()
}

/**
 * Writes a variable-length integer to the specified [DataOutput].
 *
 * @param value The integer value to write.
 */
fun DataOutput.writeVarInt(value: Int) {
  var result = value
  while ((result and 0xFFFFFF80.toInt()) != 0) {
    writeByte((result and 0x7F) or 0x80)
    result = result ushr 7
  }
  writeByte(result and 0x7F)
}

/**
 * Reads a variable-length integer from the specified [DataInput].
 *
 * @return The integer value read.
 */
fun DataInput.readVarInt(): Int {
  var value = 0
  var shift = 0
  while (true) {
    val b = readByte().toInt()
    value = value or ((b and 0x7F) shl shift)
    if ((b and 0x80) == 0) break
    shift += 7
  }
  return value
}

/**
 * Reads a variable-length byte array from the specified [DataInput].
 *
 * @param size The size of the byte array to read (default is read from the variable-length integer).
 * @return The byte array read.
 */
fun DataInput.readBytes(size: Int = readVarInt()): ByteArray {
  val buffer = ByteArray(size)
  readFully(buffer)
  return buffer
}
