package harmony.nbt.primitives

import harmony.nbt.*
import harmony.nbt.util.*
import java.io.*

/**
 * A value class representing a Bit48Tag, implementing the NumberTag interface and Comparable<Bit48Tag>.
 * @param value The 48-bit long value associated with the tag.
 */
@JvmInline
value class Bit48Tag(val value: Long = 0) : NumberTag, Comparable<Bit48Tag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the type of the Bit48Tag.
   * @return The Type object representing Bit48Tag.
   */
  override val type get() = Type
  
  /**
   * Writes the Bit48Tag data to a DataOutput object.
   * @param data The DataOutput object to write to.
   */
  override fun write(data: DataOutput) {
    data.write48Bits(value)
  }
  
  /**
   * Converts the Bit48Tag to a Number.
   * @return The 48-bit long value as a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the Bit48Tag to a Byte.
   * @return The 48-bit long value as a Byte.
   */
  override fun toByte(): Byte = value.toByte()
  
  /**
   * Converts the Bit48Tag to a Short.
   * @return The 48-bit long value as a Short.
   */
  override fun toShort(): Short = value.toShort()
  
  /**
   * Converts the Bit48Tag to an Int.
   * @return The 48-bit long value as an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the Bit48Tag to a Long.
   * @return The 48-bit long value.
   */
  override fun toLong(): Long = value
  
  /**
   * Converts the Bit48Tag to a Float.
   * @return The 48-bit long value as a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the Bit48Tag to a Double.
   * @return The 48-bit long value as a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the Bit48Tag.
   * @return The copied Bit48Tag.
   */
  override fun copy() = this
  
  /**
   * Gets the string representation of the Bit48Tag.
   * @return The string representation of the 48-bit long value.
   */
  override fun toString() = "${value}L"
  
  /**
   * Compares this Bit48Tag with another Bit48Tag.
   * @param other The other Bit48Tag to compare.
   * @return An integer representing the comparison result.
   */
  override fun compareTo(other: Bit48Tag) = value.compareTo(other.value)
  
  /**
   * The companion object representing the Type of Bit48Tag.
   */
  object Type : TagType<Bit48Tag>() {
    
    /**
     * Loads a Bit48Tag from the given DataInput object.
     * @param data The DataInput object to read from.
     * @return The loaded Bit48Tag.
     */
    override fun load(data: DataInput): Bit48Tag {
      return Bit48Tag(data.read48Bits())
    }
  }
}

/**
 * Extension function to convert a Number to a Bit48Tag.
 * @return The Bit48Tag with the converted value.
 */
fun Number.to48BitTag() = Bit48Tag(toLong())

