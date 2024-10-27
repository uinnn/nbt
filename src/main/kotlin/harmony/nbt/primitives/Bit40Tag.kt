package harmony.nbt.primitives

import harmony.nbt.*
import harmony.nbt.util.*
import java.io.*

/**
 * A value class representing a Bit40Tag, implementing the NumberTag interface and Comparable<Bit40Tag>.
 * @param value The 40-bit long value associated with the tag.
 */
@JvmInline
value class Bit40Tag(val value: Long = 0) : NumberTag, Comparable<Bit40Tag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the type of the Bit40Tag.
   * @return The Type object representing Bit40Tag.
   */
  override val type get() = Type
  
  /**
   * Writes the Bit40Tag data to a DataOutput object.
   * @param data The DataOutput object to write to.
   */
  override fun write(data: DataOutput) {
    data.write40Bits(value)
  }
  
  /**
   * Converts the Bit40Tag to a Number.
   * @return The 40-bit long value as a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the Bit40Tag to a Byte.
   * @return The 40-bit long value as a Byte.
   */
  override fun toByte(): Byte = value.toByte()
  
  /**
   * Converts the Bit40Tag to a Short.
   * @return The 40-bit long value as a Short.
   */
  override fun toShort(): Short = value.toShort()
  
  /**
   * Converts the Bit40Tag to an Int.
   * @return The 40-bit long value as an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the Bit40Tag to a Long.
   * @return The 40-bit long value.
   */
  override fun toLong(): Long = value
  
  /**
   * Converts the Bit40Tag to a Float.
   * @return The 40-bit long value as a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the Bit40Tag to a Double.
   * @return The 40-bit long value as a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the Bit40Tag.
   * @return The copied Bit40Tag.
   */
  override fun copy() = this
  
  /**
   * Gets the string representation of the Bit40Tag.
   * @return The string representation of the 40-bit long value suffixed with "L".
   */
  override fun toString() = "${value}L"
  
  /**
   * Compares this Bit40Tag with another Bit40Tag.
   * @param other The other Bit40Tag to compare.
   * @return An integer representing the comparison result.
   */
  override fun compareTo(other: Bit40Tag) = value.compareTo(other.value)
  
  /**
   * The companion object representing the Type of Bit40Tag.
   */
  object Type : TagType<Bit40Tag>() {
    
    /**
     * Loads a Bit40Tag from the given DataInput object.
     * @param data The DataInput object to read from.
     * @return The loaded Bit40Tag.
     */
    override fun load(data: DataInput): Bit40Tag {
      return Bit40Tag(data.readLong())
    }
  }
}

/**
 * Extension function to convert a Number to a Bit40Tag.
 * @return The Bit40Tag with the converted value.
 */
fun Number.to40BitTag() = Bit40Tag(toLong())


