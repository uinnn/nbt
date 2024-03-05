package dream.nbt.primitives

import dream.nbt.*
import java.io.*

/**
 * Value class representing a double value as an NBT tag.
 *
 * @param value The double value.
 */
@JvmInline
value class DoubleTag(val value: Double = 0.0) : NumberTag, Comparable<DoubleTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for DoubleTag.
   */
  override val type get() = Type
  
  /**
   * Writes the double tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeDouble(value)
  }
  
  /**
   * Converts the double tag to a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the double tag to a Byte.
   */
  override fun toByte(): Byte = toInt().toByte()
  
  /**
   * Converts the double tag to a Short.
   */
  override fun toShort(): Short = toInt().toShort()
  
  /**
   * Converts the double tag to an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the double tag to a Long.
   */
  override fun toLong(): Long = value.toLong()
  
  /**
   * Converts the double tag to a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the double tag to a Double.
   */
  override fun toDouble(): Double = value
  
  /**
   * Creates a copy of the double tag.
   */
  override fun copy() = DoubleTag(value)
  
  /**
   * Returns a string representation of the double tag.
   */
  override fun toString() = "${value}D"
  
  /**
   * Compares this double tag to another for ordering.
   */
  override fun compareTo(other: DoubleTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [DoubleTag].
   */
  object Type : TagType<DoubleTag>() {
    /**
     * Loads a DoubleTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded DoubleTag.
     */
    override fun load(data: DataInput): DoubleTag {
      return DoubleTag(data.readDouble())
    }
  }
}
