package dream.nbt

import java.io.*

/**
 * An interface representing a generic tag.
 */
interface Tag {
  
  /**
   * The value of this tag, as generic.
   */
  val genericValue: Any
  
  /**
   * Gets the type of the tag.
   */
  val type: TagType<out Tag>
  
  /**
   * Writes the tag data to the specified [DataOutput].
   *
   * @param data The [DataOutput] to write the data to.
   */
  fun write(data: DataOutput)
  
  /**
   * Creates a copy of the tag.
   *
   * @return A new instance of the tag with the same type and data.
   */
  fun copy(): Tag
}

/**
 * Checks if the tag is an end tag.
 *
 * @return `true` if the tag is an end tag, `false` otherwise.
 */
inline val Tag.isEnd: Boolean
  get() = type === EmptyType

/**
 * Gets the ID of the tag.
 *
 * @return The integer ID of the tag.
 */
inline val Tag.id: Int
  get() = type.id.toInt()
