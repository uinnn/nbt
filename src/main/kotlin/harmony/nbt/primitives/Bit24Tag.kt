package harmony.nbt.primitives

import harmony.nbt.*
import harmony.nbt.util.*
import java.io.*

/**
 * A value class representing a Bit24Tag, implementing the NumberTag interface and Comparable<Bit24Tag>.
 * @param value The 24-bit integer value associated with the tag.
 */
@JvmInline
value class Bit24Tag(val value: Int = 0) : NumberTag, Comparable<Bit24Tag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the type of the Bit24Tag.
   * @return The Type object representing Bit24Tag.
   */
  override val type get() = Type
  
  /**
   * Writes the Bit24Tag data to a DataOutput object.
   * @param data The DataOutput object to write to.
   */
  override fun write(data: DataOutput) {
    data.write24Bits(value)
  }
  
  /**
   * Converts the Bit24Tag to a Number.
   * @return The 24-bit integer value as a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the Bit24Tag to a Byte.
   * @return The 24-bit integer value as a Byte.
   */
  override fun toByte(): Byte = value.toByte()
  
  /**
   * Converts the Bit24Tag to a Short.
   * @return The 24-bit integer value as a Short.
   */
  override fun toShort(): Short = value.toShort()
  
  /**
   * Converts the Bit24Tag to an Int.
   * @return The 24-bit integer value as an Int.
   */
  override fun toInt(): Int = value
  
  /**
   * Converts the Bit24Tag to a Long.
   * @return The 24-bit integer value as a Long.
   */
  override fun toLong(): Long = value.toLong()
  
  /**
   * Converts the Bit24Tag to a Float.
   * @return The 24-bit integer value as a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the Bit24Tag to a Double.
   * @return The 24-bit integer value as a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the Bit24Tag.
   * @return The copied Bit24Tag.
   */
  override fun copy() = this
  
  /**
   * Gets the string representation of the Bit24Tag.
   * @return The string representation of the 24-bit integer value.
   */
  override fun toString() = value.toString()
  
  /**
   * Compares this Bit24Tag with another Bit24Tag.
   * @param other The other Bit24Tag to compare.
   * @return An integer representing the comparison result.
   */
  override fun compareTo(other: Bit24Tag) = value.compareTo(other.value)
  
  /**
   * The companion object representing the Type of Bit24Tag.
   */
  object Type : TagType<Bit24Tag>() {
    
    /**
     * Loads a Bit24Tag from the given DataInput object.
     * @param data The DataInput object to read from.
     * @return The loaded Bit24Tag.
     */
    override fun load(data: DataInput): Bit24Tag {
      return Bit24Tag(data.read24Bits())
    }
  }
}

/**
 * Extension function to convert a Number to a Bit24Tag.
 * @return The Bit24Tag with the converted value.
 */
fun Number.to24BitTag() = Bit24Tag(toInt())


