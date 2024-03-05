package dream.nbt.primitives

import dream.nbt.*
import java.io.*

/**
 * Value class representing a long value as an NBT tag.
 *
 * @param value The long value.
 */
@JvmInline
value class LongTag(val value: Long = 0) : NumberTag, Comparable<LongTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for LongTag.
   */
  override val type get() = Type
  
  /**
   * Writes the long tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeLong(value)
  }
  
  /**
   * Converts the long tag to a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the long tag to a Byte.
   */
  override fun toByte(): Byte = value.toByte()
  
  /**
   * Converts the long tag to a Short.
   */
  override fun toShort(): Short = value.toShort()
  
  /**
   * Converts the long tag to an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the long tag to a Long.
   */
  override fun toLong(): Long = value
  
  /**
   * Converts the long tag to a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the long tag to a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the long tag.
   */
  override fun copy() = LongTag(value)
  
  /**
   * Returns a string representation of the long tag.
   */
  override fun toString() = "${value}L"
  
  /**
   * Compares this long tag to another for ordering.
   */
  override fun compareTo(other: LongTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [LongTag].
   */
  object Type : TagType<LongTag>() {
    /**
     * Loads a LongTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded LongTag.
     */
    override fun load(data: DataInput): LongTag {
      return LongTag(data.readLong())
    }
  }
}
