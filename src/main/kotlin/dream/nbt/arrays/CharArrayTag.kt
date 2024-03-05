package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.chars.*
import java.io.*

/**
 * Value class representing a char array as an NBT tag.
 *
 * @param value The char array value.
 */
@JvmInline
value class CharArrayTag(val value: CharArray) : ArrayTag, CharIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for CharArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the char array.
   */
  override val size get() = value.size
  
  /**
   * Writes the char array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size * 2)
    data.write(value.toByteArray())
  }
  
  /**
   * Gets the element type of the char array tag.
   *
   * @return The element type.
   */
  override fun elementType() = ByteType
  
  /**
   * Creates a copy of the char array tag.
   */
  override fun copy() = CharArrayTag(value.clone())
  
  /**
   * Gets an iterator for the char array tag.
   *
   * @return The char array iterator.
   */
  override fun iterator(): CharListIterator = CharIterators.wrap(value)
  
  /**
   * Returns a string representation of the char array tag.
   */
  override fun toString() = value.toString()
  
  /**
   * Type tag of [CharArrayTag].
   */
  object Type : TagType<CharArrayTag>() {
    /**
     * Loads a CharArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded CharArrayTag.
     */
    override fun load(data: DataInput): CharArrayTag {
      return CharArrayTag(data.readBytes().toCharArray())
    }
  }
  
  companion object {
    /**
     * Empty CharArrayTag instance.
     */
    val EMPTY = CharArrayTag(CharArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a char array to a CharArrayTag.
 */
fun CharArray.toTag() = CharArrayTag(this)
