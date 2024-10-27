package harmony.nbt.primitives

import harmony.nbt.*
import harmony.nbt.util.*
import java.io.*

/**
 * A value class representing a Bit56Tag, implementing the NumberTag interface and Comparable<Bit56Tag>.
 * @param value The 56-bit long value associated with the tag.
 */
@JvmInline
value class Bit56Tag(val value: Long = 0) : NumberTag, Comparable<Bit56Tag> {
  
  /**
   * Gets the generic value of the Bit56Tag.
   * @return The 56-bit long value.
   */
  override val genericValue get() = value
  
  /**
   * Gets the type of the Bit56Tag.
   * @return The Type object representing Bit56Tag.
   */
  override val type get() = Type
  
  /**
   * Writes the Bit56Tag data to a DataOutput object.
   * @param data The DataOutput object to write to.
   */
  override fun write(data: DataOutput) {
    data.write56Bits(value)
  }
  
  /**
   * Converts the Bit56Tag to a Number.
   * @return The 56-bit long value as a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the Bit56Tag to a Byte.
   * @return The 56-bit long value as a Byte.
   */
  override fun toByte(): Byte = value.toByte()
  
  /**
   * Converts the Bit56Tag to a Short.
   * @return The 56-bit long value as a Short.
   */
  override fun toShort(): Short = value.toShort()
  
  /**
   * Converts the Bit56Tag to an Int.
   * @return The 56-bit long value as an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the Bit56Tag to a Long.
   * @return The 56-bit long value.
   */
  override fun toLong(): Long = value
  
  /**
   * Converts the Bit56Tag to a Float.
   * @return The 56-bit long value as a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the Bit56Tag to a Double.
   * @return The 56-bit long value as a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the Bit56Tag.
   * @return The copied Bit56Tag.
   */
  override fun copy() = this
  
  /**
   * Gets the string representation of the Bit56Tag.
   * @return The string representation of the 56-bit long value.
   */
  override fun toString() = "${value}L"
  
  /**
   * Compares this Bit56Tag with another Bit56Tag.
   * @param other The other Bit56Tag to compare.
   * @return An integer representing the comparison result.
   */
  override fun compareTo(other: Bit56Tag) = value.compareTo(other.value)
  
  /**
   * The companion object representing the Type of Bit56Tag.
   */
  object Type : TagType<Bit56Tag>() {
    
    /**
     * Loads a Bit56Tag from the given DataInput object.
     * @param data The DataInput object to read from.
     * @return The loaded Bit56Tag.
     */
    override fun load(data: DataInput): Bit56Tag {
      return Bit56Tag(data.read56Bits())
    }
  }
}

/**
 * Extension function to convert a Number to a Bit56Tag.
 * @return The Bit56Tag with the converted value.
 */
fun Number.to56BitTag() = Bit56Tag(toLong())


