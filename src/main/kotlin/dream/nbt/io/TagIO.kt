@file:Suppress("NOTHING_TO_INLINE")

package dream.nbt.io

import dream.nbt.*
import java.io.*

/**
 * An object providing methods for reading and writing NBT tags.
 */
object TagIO {
  
  /**
   * Reads a tag from the specified [TagDataInputStream].
   *
   * @param input The input stream from which to read the tag.
   * @param close Flag indicating whether to close the input stream after reading.
   * @return The read [Tag] or [EmptyTag] if an error occurs.
   */
  fun read(input: TagDataInputStream, close: Boolean = true): Tag {
    return try {
      TagTypes[input.readByte()].load(input)
    } catch (ex: Exception) {
      EmptyTag
    } finally {
      if (close) input.close()
    }
  }
  
  /**
   * Reads a tag from the specified [InputStream].
   *
   * @param input The input stream from which to read the tag.
   * @param close Flag indicating whether to close the input stream after reading.
   * @return The read [Tag] or [EmptyTag] if an error occurs.
   */
  fun read(input: InputStream, close: Boolean = true): Tag {
    return read(input.toNBTStream(), close)
  }
  
  /**
   * Reads a tag from the specified file.
   *
   * @param file The file from which to read the tag.
   * @param close Flag indicating whether to close the input stream after reading.
   * @return The read [Tag] or [EmptyTag] if an error occurs.
   */
  fun read(file: File, close: Boolean = true): Tag = read(file.inputStream(), close)
  
  /**
   * Writes a tag to the specified [TagDataOutputStream].
   *
   * @param output The output stream to which to write the tag.
   * @param value The tag to be written.
   * @param close Flag indicating whether to close the output stream after writing.
   */
  fun write(output: TagDataOutputStream, value: Tag, close: Boolean = true) {
    try {
      output.writeByte(value.id)
      value.write(output)
    } catch (ex: Exception) {
      println("Error while writing ${value.type} on TagDataOutputStream $output")
      ex.printStackTrace()
    } finally {
      if (close) output.close()
    }
  }
  
  /**
   * Writes a tag to the specified [OutputStream].
   *
   * @param output The output stream to which to write the tag.
   * @param value The tag to be written.
   * @param close Flag indicating whether to close the output stream after writing.
   */
  fun write(output: OutputStream, value: Tag, close: Boolean = true) {
    write(output.toNBTStream(), value, close)
  }
  
  /**
   * Writes a tag to the specified file.
   *
   * @param file The file to which to write the tag.
   * @param value The tag to be written.
   * @param close Flag indicating whether to close the output stream after writing.
   */
  fun write(file: File, value: Tag, close: Boolean = true) = write(file.outputStream(), value, close)
}

/**
 * Converts the [OutputStream] to a [TagDataOutputStream] with the specified size.
 *
 * @param size The size of the buffer.
 * @return The converted [TagDataOutputStream].
 */
fun OutputStream.toNBTStream(size: Int = 1024) =
  if (this is TagDataOutputStream) this else TagDataOutputStream(this, size)

/**
 * Converts the [InputStream] to a [TagDataInputStream].
 *
 * @return The converted [TagDataInputStream].
 */
fun InputStream.toNBTStream() = if (this is TagDataInputStream) this else TagDataInputStream(this)

/**
 * Writes a tag to the [OutputStream].
 *
 * @param tag The tag to be written.
 * @param close Flag indicating whether to close the output stream after writing.
 */
fun OutputStream.writeTag(tag: Tag, close: Boolean = true) = TagIO.write(this, tag, close)

/**
 * Writes a tag to the specified file.
 *
 * @param tag The tag to be written.
 * @param close Flag indicating whether to close the output stream after writing.
 */
fun File.writeTag(tag: Tag, close: Boolean = true) = TagIO.write(this, tag, close)

/**
 * Reads a tag from the [InputStream].
 *
 * @param close Flag indicating whether to close the input stream after reading.
 * @return The read [Tag] or [EmptyTag] if an error occurs.
 */
fun InputStream.readTag(close: Boolean = true) = TagIO.read(this, close)

/**
 * Reads a tag from the specified file.
 *
 * @param close Flag indicating whether to close the input stream after reading.
 * @return The read [Tag] or [EmptyTag] if an error occurs.
 */
fun File.readTag(close: Boolean = true) = TagIO.read(this, close)
