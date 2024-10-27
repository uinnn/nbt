package harmony.nbt.primitives

import harmony.nbt.*
import java.io.*
import kotlin.experimental.*

/**
 * Value class representing a boolean value as an NBT tag.
 *
 * @param raw The raw byte value representing the boolean.
 */
@JvmInline
value class BooleanTag(val raw: Byte) : NumberTag, Comparable<BooleanTag> {
  
  override val genericValue get() = raw
  
  /**
   * Gets the NBT tag type for BooleanTag.
   */
  override val type get() = Type
  
  /**
   * Gets the boolean value represented by this tag.
   */
  val value get() = raw != 0.toByte()
  
  /**
   * Constructor to create a BooleanTag from a boolean value.
   *
   * @param value The boolean value.
   */
  constructor(value: Boolean) : this(if (value) 1 else 0)
  
  /**
   * Constructor to create a BooleanTag from multiple boolean values.
   */
  constructor(value1: Boolean, value2: Boolean) : this(0.toByte().toggleBit(1, value1).toggleBit(2, value2))
  
  /**
   * Constructor to create a BooleanTag from multiple boolean values.
   */
  constructor(value1: Boolean, value2: Boolean, value3: Boolean) :
    this(0.toByte().toggleBit(1, value1).toggleBit(2, value2).toggleBit(3, value3))
  
  /**
   * Constructor to create a BooleanTag from multiple boolean values.
   */
  constructor(vararg values: Boolean) : this(values.asByteCompacted())
  
  /**
   * Writes the boolean tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeByte(toInt())
  }
  
  /**
   * Gets the value of the specified bit in the boolean tag.
   *
   * @param bit The bit index.
   * @return True if the bit is set, false otherwise.
   */
  operator fun get(bit: Int) = (raw and mask(bit)) != 0.toByte()
  
  /**
   * Sets the value of the specified bit in the boolean tag.
   *
   * @param bit The bit index.
   * @param value The boolean value to set.
   * @return A new BooleanTag with the updated value.
   */
  fun set(bit: Int, value: Boolean) = BooleanTag(raw.toggleBit(bit, value))
  
  /**
   * Converts the boolean tag to a Number.
   */
  override fun toNumber(): Number = raw
  
  /**
   * Converts the boolean tag to a Byte.
   */
  override fun toByte(): Byte = raw
  
  /**
   * Converts the boolean tag to a Short.
   */
  override fun toShort(): Short = raw.toShort()
  
  /**
   * Converts the boolean tag to an Int.
   */
  override fun toInt(): Int = raw.toInt()
  
  /**
   * Converts the boolean tag to a Long.
   */
  override fun toLong(): Long = raw.toLong()
  
  /**
   * Converts the boolean tag to a Float.
   */
  override fun toFloat(): Float = raw.toFloat()
  
  /**
   * Converts the boolean tag to a Double.
   */
  override fun toDouble(): Double = raw.toDouble()
  
  /**
   * Creates a copy of the boolean tag.
   */
  override fun copy() = this
  
  /**
   * Returns a string representation of the boolean tag.
   */
  override fun toString() = "${raw}b"
  
  /**
   * Compares this boolean tag to another for ordering.
   */
  override fun compareTo(other: BooleanTag) = raw.compareTo(other.raw)
  
  /**
   * TagType for BooleanTag.
   */
  object Type : TagType<BooleanTag>() {
    /**
     * Loads a BooleanTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded BooleanTag.
     */
    override fun load(data: DataInput): BooleanTag {
      return BooleanTag(data.readByte())
    }
  }
  
  /**
   * Static companion object providing predefined BooleanTag instances for true and false values.
   */
  companion object {
    val TRUE = BooleanTag(true)
    val FALSE = BooleanTag(false)
  }
}


fun Boolean.toTag() = BooleanTag(this)
fun Byte.toBooleanTag() = BooleanTag(this)

/**
 * Toggle the specified bit in the byte to the specified value.
 *
 * @param bit The bit index.
 * @param value The boolean value to set.
 * @return The updated byte value.
 */
fun Byte.toggleBit(bit: Int, value: Boolean): Byte {
  return if (value) this or mask(bit) else this and mask(bit).inv()
}

/**
 * Sets the specified bit in the byte to true (1).
 *
 * @param bit The bit index.
 * @return The updated byte value.
 */
fun Byte.setBit(bit: Int): Byte {
  return this or mask(bit)
}

/**
 * Sets the specified bit in the byte to false (0).
 *
 * @param bit The bit index.
 * @return The updated byte value.
 */
fun Byte.unsetBit(bit: Int): Byte {
  return this and mask(bit).inv()
}

/**
 * Compacts the boolean array into a single byte.
 *
 * @return The compacted byte.
 */
private fun BooleanArray.asByteCompacted(): Byte {
  var b: Byte = 0
  for (i in 0 until size.coerceAtMost(8)) {
    if (this[i]) b = b or mask(i)
  }
  return b
}

/**
 * Generates a bitmask for the specified bit.
 *
 * @param v The bit index.
 * @return The bitmask for the specified bit.
 */
private fun mask(v: Int): Byte = (1 shl v).toByte()
