package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.primitives.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.booleans.*
import java.io.*

/**
 * Value class representing a packed boolean array as an NBT tag.
 *
 * @param value The boolean array value.
 */
@JvmInline
value class PackedBooleanArrayTag(val value: BooleanArray) : IterableTag, BooleanIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for PackedBooleanArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the packed boolean array.
   */
  override val size get() = value.size
  
  /**
   * Writes the packed boolean array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size shr 3)
    data.write(value.toPackedByteArray())
  }
  
  /**
   * Gets the element type of the packed boolean array tag.
   *
   * @return The element type.
   */
  override fun elementType() = BooleanTag.Type
  
  /**
   * Creates a copy of the packed boolean array tag.
   */
  override fun copy() = BooleanArrayTag(value.copyOf())
  
  /**
   * Gets an iterator for the packed boolean array tag.
   *
   * @return The packed boolean array iterator.
   */
  override fun iterator(): BooleanListIterator = BooleanIterators.wrap(value)
  
  /**
   * Returns a string representation of the packed boolean array tag.
   */
  override fun toString() = "[${size} bytes]"
  
  /**
   * Type tag of [PackedBooleanArrayTag].
   */
  object Type : TagType<BooleanArrayTag>() {
    /**
     * Loads a PackedBooleanArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded PackedBooleanArrayTag.
     */
    override fun load(data: DataInput): BooleanArrayTag {
      return BooleanArrayTag(data.readBytes().toPackedBooleanArray())
    }
  }
  
  companion object {
    /**
     * Empty PackedBooleanArrayTag instance.
     */
    val EMPTY = BooleanArrayTag(BooleanArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a boolean array to a PackedBooleanArrayTag.
 */
fun BooleanArray.toPackedTag() = BooleanArrayTag(this)
