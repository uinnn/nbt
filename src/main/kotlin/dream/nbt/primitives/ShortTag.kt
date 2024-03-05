package dream.nbt.primitives

import dream.nbt.*
import java.io.*

/**
 * Value class representing a short value as an NBT tag.
 *
 * @param value The short value.
 */
@JvmInline
value class ShortTag(val value: Short = 0) : NumberTag, Comparable<ShortTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for ShortTag.
   */
  override val type get() = Type
  
  /**
   * Writes the short tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeShort(toInt())
  }
  
  /**
   * Converts the short tag to a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the short tag to a Byte.
   */
  override fun toByte(): Byte = value.toByte()
  
  /**
   * Converts the short tag to a Short.
   */
  override fun toShort(): Short = value
  
  /**
   * Converts the short tag to an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the short tag to a Long.
   */
  override fun toLong(): Long = value.toLong()
  
  /**
   * Converts the short tag to a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the short tag to a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the short tag.
   */
  override fun copy() = ShortTag(value)
  
  /**
   * Returns a string representation of the short tag.
   */
  override fun toString() = "${value}s"
  
  /**
   * Compares this short tag to another for ordering.
   */
  override fun compareTo(other: ShortTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [ShortTag].
   */
  object Type : TagType<ShortTag>() {
    /**
     * Loads a ShortTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded ShortTag.
     */
    override fun load(data: DataInput): ShortTag {
      return ShortTag(data.readShort())
    }
  }
}
