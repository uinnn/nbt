package dream.nbt

import dream.nbt.io.*
import it.unimi.dsi.fastutil.io.*
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

/**
 * Extension function for converting a Tag to a ByteArray.
 * @return The ByteArray representation of the Tag.
 */
fun Tag.toByteArray(): ByteArray {
  val stream = FastByteArrayOutputStream()
  TagIO.write(stream, this)
  return stream.array
}

/**
 * Extension function for decoding a Tag from a ByteArray.
 * @return The decoded Tag from the ByteArray.
 */
fun ByteArray.decodeTag(): Tag {
  return TagIO.read(FastByteArrayInputStream(this))
}
