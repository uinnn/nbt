package dream.nbt.primitives

import dream.nbt.*
import java.io.*

/**
 * Value class representing a char value as an NBT tag.
 *
 * @param value The char value.
 */
@JvmInline
value class CharTag(val value: Char) : NumberTag, Comparable<CharTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for CharTag.
   */
  override val type get() = Type
  
  /**
   * Writes the char tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeChar(value.code)
  }
  
  /**
   * Converts the char tag to a Number.
   */
  override fun toNumber(): Number = value.code
  
  /**
   * Converts the char tag to a Byte.
   */
  override fun toByte(): Byte = toInt().toByte()
  
  /**
   * Converts the char tag to a Short.
   */
  override fun toShort(): Short = toInt().toShort()
  
  /**
   * Converts the char tag to an Int.
   */
  override fun toInt(): Int = value.code
  
  /**
   * Converts the char tag to a Long.
   */
  override fun toLong(): Long = toInt().toLong()
  
  /**
   * Converts the char tag to a Float.
   */
  override fun toFloat(): Float = toInt().toFloat()
  
  /**
   * Converts the char tag to a Double.
   */
  override fun toDouble(): Double = toInt().toDouble()
  
  /**
   * Creates a copy of the char tag.
   */
  override fun copy() = this
  
  /**
   * Returns a string representation of the char tag.
   */
  override fun toString() = "'$value'"
  
  /**
   * Compares this char tag to another for ordering.
   */
  override fun compareTo(other: CharTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [CharTag].
   */
  object Type : TagType<CharTag>() {
    /**
     * Loads a CharTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded CharTag.
     */
    override fun load(data: DataInput): CharTag {
      return CharTag(data.readChar())
    }
  }
}

fun Char.toTag() = CharTag(this)
