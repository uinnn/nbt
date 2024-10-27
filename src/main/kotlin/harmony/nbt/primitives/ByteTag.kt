package harmony.nbt.primitives

import harmony.nbt.*
import java.io.*

/**
 * Value class representing a byte value as an NBT tag.
 *
 * @param value The byte value.
 */
@JvmInline
value class ByteTag(val value: Byte = 0) : NumberTag, Comparable<ByteTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for ByteTag.
   */
  override val type get() = Type
  
  /**
   * Constructor to create a ByteTag from a boolean value.
   *
   * @param value The boolean value.
   */
  constructor(value: Boolean) : this(if (value) 1 else 0)
  
  /**
   * Writes the byte tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeByte(toInt())
  }
  
  /**
   * Converts the byte tag to a Number.
   */
  override fun toNumber(): Number = value
  
  /**
   * Converts the byte tag to a Byte.
   */
  override fun toByte(): Byte = value
  
  /**
   * Converts the byte tag to a Short.
   */
  override fun toShort(): Short = value.toShort()
  
  /**
   * Converts the byte tag to an Int.
   */
  override fun toInt(): Int = value.toInt()
  
  /**
   * Converts the byte tag to a Long.
   */
  override fun toLong(): Long = value.toLong()
  
  /**
   * Converts the byte tag to a Float.
   */
  override fun toFloat(): Float = value.toFloat()
  
  /**
   * Converts the byte tag to a Double.
   */
  override fun toDouble(): Double = value.toDouble()
  
  /**
   * Creates a copy of the byte tag.
   */
  override fun copy() = this
  
  /**
   * Returns a string representation of the byte tag.
   */
  override fun toString() = "${value}b"
  
  /**
   * Compares this byte tag to another for ordering.
   */
  override fun compareTo(other: ByteTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [ByteTag].
   */
  object Type : TagType<ByteTag>() {
    /**
     * Loads a ByteTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded ByteTag.
     */
    override fun load(data: DataInput): ByteTag {
      return ByteTag(data.readByte())
    }
  }
}

fun Byte.toTag() = ByteTag(this)
