package dream.nbt.primitives

import dream.nbt.*
import java.io.*

/**
 * Value class representing an integer value as an NBT tag.
 *
 * @param value The integer value.
 */
@JvmInline
value class IntTag(val value: Int = 0) : NumberTag, Comparable<IntTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for IntTag.
   */
  override val type get() = Type
  
  /**
   * Writes the integer tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeInt(value)
  }
  
  /**
   * Converts the integer tag to a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the integer tag to a Byte.
   */
  override fun toByte(): Byte = value.toByte()
  
  /**
   * Converts the integer tag to a Short.
   */
  override fun toShort(): Short = value.toShort()
  
  /**
   * Converts the integer tag to an Int.
   */
  override fun toInt(): Int = value
  
  /**
   * Converts the integer tag to a Long.
   */
  override fun toLong(): Long = value.toLong()
  
  /**
   * Converts the integer tag to a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the integer tag to a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the integer tag.
   */
  override fun copy() = this
  
  /**
   * Returns a string representation of the integer tag.
   */
  override fun toString() = value.toString()
  
  /**
   * Compares this integer tag to another for ordering.
   */
  override fun compareTo(other: IntTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [IntTag].
   */
  object Type : TagType<IntTag>() {
    /**
     * Loads an IntTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded IntTag.
     */
    override fun load(data: DataInput): IntTag {
      return IntTag(data.readInt())
    }
  }
}

fun Int.toTag() = IntTag(this)
