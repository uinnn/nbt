package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.shorts.*
import java.io.*

/**
 * Value class representing a short array as an NBT tag.
 *
 * @param value The short array value.
 */
@JvmInline
value class ShortArrayTag(val value: ShortArray) : ArrayTag, ShortIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for ShortArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the short array.
   */
  override val size get() = value.size
  
  /**
   * Writes the short array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size * 2)
    data.write(value.toByteArray())
  }
  
  /**
   * Gets the element type of the short array tag.
   *
   * @return The element type.
   */
  override fun elementType() = ShortType
  
  /**
   * Creates a copy of the short array tag.
   */
  override fun copy() = ShortArrayTag(value.clone())
  
  /**
   * Gets an iterator for the short array tag.
   *
   * @return The short array iterator.
   */
  override fun iterator(): ShortListIterator = ShortIterators.wrap(value)
  
  /**
   * Returns a string representation of the short array tag.
   */
  override fun toString(): String = buildString {
    append('[')
    for (v in value) append("$v, ")
    append(']')
  }
  
  /**
   * Type tag of [ShortArrayTag].
   */
  object Type : TagType<ShortArrayTag>() {
    /**
     * Loads a ShortArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded ShortArrayTag.
     */
    override fun load(data: DataInput): ShortArrayTag {
      return ShortArrayTag(data.readBytes().toShortArray())
    }
  }
  
  companion object {
    /**
     * Empty ShortArrayTag instance.
     */
    val EMPTY = ShortArrayTag(ShortArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a short array to a ShortArrayTag.
 */
fun ShortArray.toTag() = ShortArrayTag(this)
