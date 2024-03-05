package dream.nbt.util

import java.io.*

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
