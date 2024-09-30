package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.bytes.*
import java.io.*

/**
 * Value class representing a byte array as an NBT tag.
 *
 * @param value The byte array value.
 */
@JvmInline
value class ByteArrayTag(val value: ByteArray) : IterableTag, ByteIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for ByteArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the byte array.
   */
  override val size get() = value.size
  
  /**
   * Writes the byte array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size)
    data.write(value)
  }
  
  /**
   * Gets the element type of the byte array tag.
   *
   * @return The element type.
   */
  override fun elementType() = ByteType
  
  /**
   * Creates a copy of the byte array tag.
   */
  override fun copy() = ByteArrayTag(value.copyOf())
  
  /**
   * Gets an iterator for the byte array tag.
   *
   * @return The byte array iterator.
   */
  override fun iterator(): ByteListIterator = ByteIterators.wrap(value)
  
  /**
   * Returns a string representation of the byte array tag.
   */
  override fun toString() = "[${size} bytes]"
  
  /**
   * Type tag of [ByteArrayTag].
   */
  object Type : TagType<ByteArrayTag>() {
    /**
     * Loads a ByteArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded ByteArrayTag.
     */
    override fun load(data: DataInput): ByteArrayTag {
      return ByteArrayTag(data.readBytes())
    }
  }
  
  companion object {
    /**
     * Empty ByteArrayTag instance.
     */
    val EMPTY = ByteArrayTag(ByteArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a byte array to a ByteArrayTag.
 */
fun ByteArray.toTag() = ByteArrayTag(this)
