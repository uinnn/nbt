package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.ints.*
import java.io.*

/**
 * Value class representing an int array as an NBT tag.
 *
 * @param value The int array value.
 */
@JvmInline
value class IntArrayTag(val value: IntArray) : ArrayTag, IntIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for IntArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the int array.
   */
  override val size get() = value.size
  
  /**
   * Writes the int array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size * 4)
    data.write(value.toByteArray())
  }
  
  /**
   * Gets the element type of the int array tag.
   *
   * @return The element type.
   */
  override fun elementType() = IntType
  
  /**
   * Creates a copy of the int array tag.
   */
  override fun copy() = IntArrayTag(value.clone())
  
  /**
   * Gets an iterator for the int array tag.
   *
   * @return The int array iterator.
   */
  override fun iterator(): IntListIterator = IntIterators.wrap(value)
  
  /**
   * Returns a string representation of the int array tag.
   */
  override fun toString(): String = buildString {
    append('[')
    for (v in value) append("$v, ")
    append(']')
  }
  
  /**
   * Type tag of [IntArrayTag].
   */
  object Type : TagType<IntArrayTag>() {
    /**
     * Loads an IntArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded IntArrayTag.
     */
    override fun load(data: DataInput): IntArrayTag {
      return IntArrayTag(data.readBytes().toIntArray())
    }
  }
  
  companion object {
    /**
     * Empty IntArrayTag instance.
     */
    val EMPTY = IntArrayTag(IntArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert an int array to an IntArrayTag.
 */
fun IntArray.toTag() = IntArrayTag(this)
