package harmony.nbt.util

import java.io.*

/**
 * Writes the specified 24-bit value to the specified [DataOutput].
 *
 * @param value The 24-bit value to write.
 */
fun DataOutput.write24Bits(value: Int) {
  writeByte((value shr 16) and 0xFF)
  writeByte((value shr 8) and 0xFF)
  writeByte(value and 0xFF)
}

/**
 * Reads a 24-bit value from the specified [DataInput].
 *
 * @return The 24-bit value read.
 */
fun DataInput.read24Bits(): Int {
  return (readByte().toInt() shl 16) or (readByte().toInt() shl 8) or readByte().toInt()
}

/**
 * Writes the specified 40-bit value to the specified [DataOutput].
 *
 * @param value The 40-bit value to write.
 */
fun DataOutput.write40Bits(value: Long) {
  writeByte(((value shr 32) and 0xFF).toInt())
  writeByte(((value shr 24) and 0xFF).toInt())
  writeByte(((value shr 16) and 0xFF).toInt())
  writeByte(((value shr 8) and 0xFF).toInt())
  writeByte((value and 0xFF).toInt())
}

/**
 * Reads a 40-bit value from the specified [DataInput].
 *
 * @return The 40-bit value read.
 */
fun DataInput.read40Bits(): Long {
  return (readByte().toLong() shl 32) or
    (readByte().toLong() shl 24) or
    (readByte().toLong() shl 16) or
    (readByte().toLong() shl 8) or
    readByte().toLong()
}

/**
 * Writes the specified 48-bit value to the specified [DataOutput].
 *
 * @param value The 48-bit value to write.
 */
fun DataOutput.write48Bits(value: Long) {
  writeByte(((value shr 40) and 0xFF).toInt())
  writeByte(((value shr 32) and 0xFF).toInt())
  writeByte(((value shr 24) and 0xFF).toInt())
  writeByte(((value shr 16) and 0xFF).toInt())
  writeByte(((value shr 8) and 0xFF).toInt())
  writeByte((value and 0xFF).toInt())
}

/**
 * Reads a 48-bit value from the specified [DataInput].
 *
 * @return The 48-bit value read.
 */
fun DataInput.read48Bits(): Long {
  return (readByte().toLong() shl 40) or
    (readByte().toLong() shl 32) or
    (readByte().toLong() shl 24) or
    (readByte().toLong() shl 16) or
    (readByte().toLong() shl 8) or
    readByte().toLong()
}

/**
 * Writes the specified 56-bit value to the specified [DataOutput].
 *
 * @param value The 56-bit value to write.
 */
fun DataOutput.write56Bits(value: Long) {
  writeByte(((value shr 48) and 0xFF).toInt())
  writeByte(((value shr 40) and 0xFF).toInt())
  writeByte(((value shr 32) and 0xFF).toInt())
  writeByte(((value shr 24) and 0xFF).toInt())
  writeByte(((value shr 16) and 0xFF).toInt())
  writeByte(((value shr 8) and 0xFF).toInt())
  writeByte((value and 0xFF).toInt())
}

/**
 * Reads a 56-bit value from the specified [DataInput].
 *
 * @return The 56-bit value read.
 */
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
 * This is a fast check version for the most commons sizes of a VarInt (1-2 bytes),
 * if [value] needs more bytes than 2, write using [writeVarIntFull].
 *
 * @param value The integer value to write.
 */
fun DataOutput.writeVarInt(value: Int) {
  val mask = 0xFFFFFFFF.toInt()
  if (value and (mask shl 7) == 0) {
    writeByte(value)
  } else if (value and (mask shl 14) == 0) {
    writeShort((value and 0x7F or 0x80) shl 8 or (value ushr 7))
  } else {
    writeVarIntFull(value)
  }
}

/**
 * Writes a fully variable-length integer to the specified [DataOutput].
 *
 * This is the full version for writing a VarInt.
 *
 * @param value The integer value to write.
 */
fun DataOutput.writeVarIntFull(value: Int) {
  val mask = 0xFFFFFFFF.toInt()
  if (value and (mask shl 7) == 0) {
    writeByte(value)
  } else if (value and (mask shl 14) == 0) {
    writeShort((value and 0x7F or 0x80) shl 8 or (value ushr 7))
  } else if (value and (mask shl 21) == 0) {
    write24Bits((value and 0x7F or 0x80) shl 16 or ((value ushr 7) and 0x7F or 0x80) shl 8 or (value ushr 14))
  } else if (value and (mask shl 28) == 0) {
    writeInt((value and 0x7F or 0x80) shl 24 or (((value ushr 7) and 0x7F or 0x80) shl 16) or ((value ushr 14) and 0x7F or 0x80) shl 8 or (value ushr 21))
  } else {
    writeInt((value and 0x7F or 0x80) shl 24 or ((value ushr 7 and 0x7F or 0x80) shl 16) or ((value ushr 14 and 0x7F or 0x80) shl 8) or ((value ushr 21 and 0x7F or 0x80)))
    writeByte(value ushr 28)
  }
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
    val k = readByte().toInt()
    value = value or ((k and 0x7F) shl shift)
    if ((k and 0x80) == 0) break
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

