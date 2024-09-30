package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.primitives.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.booleans.*
import java.io.*

/**
 * Value class representing a boolean array as an NBT tag.
 *
 * @param value The boolean array value.
 */
@JvmInline
value class BooleanArrayTag(val value: BooleanArray) : IterableTag, BooleanIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for BooleanArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the boolean array.
   */
  override val size get() = value.size
  
  /**
   * Writes the boolean array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size)
    data.write(value.toByteArray())
  }
  
  /**
   * Gets the element type of the boolean array tag.
   *
   * @return The element type.
   */
  override fun elementType() = BooleanTag.Type
  
  /**
   * Creates a copy of the boolean array tag.
   */
  override fun copy() = BooleanArrayTag(value.copyOf())
  
  /**
   * Gets an iterator for the boolean array tag.
   *
   * @return The boolean array iterator.
   */
  override fun iterator(): BooleanListIterator = BooleanIterators.wrap(value)
  
  /**
   * Returns a string representation of the boolean array tag.
   */
  override fun toString() = "[${size} bytes]"
  
  /**
   * Type tag of [BooleanArrayTag].
   */
  object Type : TagType<BooleanArrayTag>() {
    /**
     * Loads a BooleanArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded BooleanArrayTag.
     */
    override fun load(data: DataInput): BooleanArrayTag {
      return BooleanArrayTag(data.readBytes().toBooleanArray())
    }
  }
  
  companion object {
    /**
     * Empty BooleanArrayTag instance.
     */
    val EMPTY = BooleanArrayTag(BooleanArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a boolean array to a BooleanArrayTag.
 */
fun BooleanArray.toTag() = BooleanArrayTag(this)

