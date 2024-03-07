package dream.nbt.primitives

import dream.nbt.*
import java.io.*

/**
 * Value class representing a float value as an NBT tag.
 *
 * @param value The float value.
 */
@JvmInline
value class FloatTag(val value: Float = 0f) : NumberTag, Comparable<FloatTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for FloatTag.
   */
  override val type get() = Type
  
  /**
   * Writes the float tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeFloat(value)
  }
  
  /**
   * Converts the float tag to a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the float tag to a Byte.
   */
  override fun toByte(): Byte = toInt().toByte()
  
  /**
   * Converts the float tag to a Short.
   */
  override fun toShort(): Short = toInt().toShort()
  
  /**
   * Converts the float tag to an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the float tag to a Long.
   */
  override fun toLong(): Long = value.toLong()
  
  /**
   * Converts the float tag to a Float.
   */
  override fun toFloat(): Float = value
  
  /**
   * Converts the float tag to a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the float tag.
   */
  override fun copy() = this
  
  /**
   * Returns a string representation of the float tag.
   */
  override fun toString() = "${value}f"
  
  /**
   * Compares this float tag to another for ordering.
   */
  override fun compareTo(other: FloatTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [FloatTag].
   */
  object Type : TagType<FloatTag>() {
    /**
     * Loads a FloatTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded FloatTag.
     */
    override fun load(data: DataInput): FloatTag {
      return FloatTag(data.readFloat())
    }
  }
}

fun Float.toTag() = FloatTag(this)
