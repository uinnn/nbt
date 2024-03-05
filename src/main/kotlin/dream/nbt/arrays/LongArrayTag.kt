package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.longs.*
import java.io.*

/**
 * Value class representing a long array as an NBT tag.
 *
 * @param value The long array value.
 */
@JvmInline
value class LongArrayTag(val value: LongArray) : ArrayTag, LongIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for LongArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the long array.
   */
  override val size get() = value.size
  
  /**
   * Writes the long array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size * 8)
    data.write(value.toByteArray())
  }
  
  /**
   * Gets the element type of the long array tag.
   *
   * @return The element type.
   */
  override fun elementType() = LongType
  
  /**
   * Creates a copy of the long array tag.
   */
  override fun copy() = LongArrayTag(value.clone())
  
  /**
   * Gets an iterator for the long array tag.
   *
   * @return The long array iterator.
   */
  override fun iterator(): LongListIterator = LongIterators.wrap(value)
  
  /**
   * Returns a string representation of the long array tag.
   */
  override fun toString(): String = buildString {
    append('[')
    for (v in value) append("$v, ")
    append(']')
  }
  
  /**
   * Type tag of [LongArrayTag].
   */
  object Type : TagType<LongArrayTag>() {
    /**
     * Loads a LongArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded LongArrayTag.
     */
    override fun load(data: DataInput): LongArrayTag {
      return LongArrayTag(data.readBytes().toLongArray())
    }
  }
  
  companion object {
    /**
     * Empty LongArrayTag instance.
     */
    val EMPTY = LongArrayTag(LongArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a long array to a LongArrayTag.
 */
fun LongArray.toTag() = LongArrayTag(this)

