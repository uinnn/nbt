package dream.nbt.arrays

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.doubles.*
import java.io.*

/**
 * Value class representing a double array as an NBT tag.
 *
 * @param value The double array value.
 */
@JvmInline
value class DoubleArrayTag(val value: DoubleArray) : ArrayTag, DoubleIterable {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for DoubleArrayTag.
   */
  override val type get() = Type
  
  /**
   * Gets the size of the double array.
   */
  override val size get() = value.size
  
  /**
   * Writes the double array tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeVarInt(size * 8)
    data.write(value.toByteArray())
  }
  
  /**
   * Gets the element type of the double array tag.
   *
   * @return The element type.
   */
  override fun elementType() = DoubleType
  
  /**
   * Creates a copy of the double array tag.
   */
  override fun copy() = DoubleArrayTag(value.copyOf())
  
  /**
   * Gets an iterator for the double array tag.
   *
   * @return The double array iterator.
   */
  override fun iterator(): DoubleListIterator = DoubleIterators.wrap(value)
  
  /**
   * Returns a string representation of the double array tag.
   */
  override fun toString(): String = buildString {
    append('[')
    for (v in value) append("$v, ")
    append(']')
  }
  
  /**
   * Type tag of [DoubleArrayTag].
   */
  object Type : TagType<DoubleArrayTag>() {
    /**
     * Loads a DoubleArrayTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded DoubleArrayTag.
     */
    override fun load(data: DataInput): DoubleArrayTag {
      return DoubleArrayTag(data.readBytes().toDoubleArray())
    }
  }
  
  companion object {
    /**
     * Empty DoubleArrayTag instance.
     */
    val EMPTY = DoubleArrayTag(DoubleArrays.EMPTY_ARRAY)
  }
}

/**
 * Extension function to convert a double array to a DoubleArrayTag.
 */
fun DoubleArray.toTag() = DoubleArrayTag(this)
