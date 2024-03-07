package dream.nbt

import dream.nbt.compression.*
import dream.nbt.io.*
import java.io.*

/**
 * An abstract class representing the type of a tag.
 *
 * @param T The type of the tag associated with this tag type.
 */
abstract class TagType<T : Tag> {
  
  /**
   * The ID of the tag type.
   */
  var id: Byte = -1
    internal set
  
  /**
   * Loads tag data from the specified [DataInput] and creates an instance of the associated tag.
   *
   * @param data The [DataInput] containing the tag data.
   * @return An instance of the associated tag loaded with data from the [DataInput].
   */
  abstract fun load(data: DataInput): T
  
  /**
   * Type-safe decoding a tag from a [ByteArray] into an instance of the associated tag.
   */
  fun decode(bytes: ByteArray, compressor: TagCompressor = GZIPTagCompressor): T {
    return TagIO.read(bytes, compressor) as T
  }
  
  /**
   * Type-safe decoding a tag from a [ByteArray] into an instance of the associated tag or null.
   */
  fun decodeOrNull(bytes: ByteArray, compressor: TagCompressor = GZIPTagCompressor): T? {
    return try {
      decode(bytes, compressor)
    } catch (e: Throwable) {
      null
    }
  }
}
