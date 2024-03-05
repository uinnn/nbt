package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.floats.*
import java.io.*

/**
 * Value class representing a float array as an NBT tag.
 *
 * @param value The float array value.
 */
@JvmInline
value class FloatArrayTag(val value: FloatArray) : ArrayTag, FloatIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for FloatArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the float array.
   */
  override val size get() = value.size
  
  /**
   * Writes the float array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size * 4)
    data.write(value.toByteArray())
  }
  
  /**
   * Gets the element type of the float array tag.
   *
   * @return The element type.
   */
  override fun elementType() = FloatType
  
  /**
   * Creates a copy of the float array tag.
   */
  override fun copy() = FloatArrayTag(value.clone())
  
  /**
   * Gets an iterator for the float array tag.
   *
   * @return The float array iterator.
   */
  override fun iterator(): FloatListIterator = FloatIterators.wrap(value)
  
  /**
   * Returns a string representation of the float array tag.
   */
  override fun toString(): String = buildString {
    append('[')
    for (v in value) append("$v, ")
    append(']')
  }
  
  /**
   * Type tag of [FloatArrayTag].
   */
  object Type : TagType<FloatArrayTag>() {
    /**
     * Loads a FloatArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded FloatArrayTag.
     */
    override fun load(data: DataInput): FloatArrayTag {
      return FloatArrayTag(data.readBytes().toFloatArray())
    }
  }
  
  companion object {
    /**
     * Empty FloatArrayTag instance.
     */
    val EMPTY = FloatArrayTag(FloatArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a float array to a FloatArrayTag.
 */
fun FloatArray.toTag() = FloatArrayTag(this)
