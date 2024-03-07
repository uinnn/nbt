@file:Suppress("NOTHING_TO_INLINE")

package dream.nbt.io

import dream.nbt.*
import dream.nbt.compression.*
import java.io.*

/**
 * An object providing methods for reading and writing NBT tags.
 */
object TagIO {
  
  /**
   * Reads a tag from the specified [TagDataInputStream].
   *
   * @param input The input stream from which to read the tag.
   * @return The read [Tag] or [EmptyTag] if an error occurs.
   */
  fun read(input: DataInputStream): Tag {
    return try {
      TagTypes[input.readByte()].load(input)
    } catch (ex: Exception) {
      EmptyTag
    } finally {
      input.close()
    }
  }
  
  /**
   * Reads a tag from the specified [InputStream].
   *
   * @param input The input stream from which to read the tag.
   * @return The read [Tag] or [EmptyTag] if an error occurs.
   */
  fun read(input: InputStream, compressor: TagCompressor = GZIPTagCompressor): Tag {
    return read(input.toNBTStream(compressor))
  }
  
  /**
   * Reads a tag from the specified file.
   *
   * @param file The file from which to read the tag.
   * @return The read [Tag] or [EmptyTag] if an error occurs.
   */
  fun read(file: File, compressor: TagCompressor = GZIPTagCompressor): Tag = read(file.inputStream(), compressor)
  
  /**
   * Writes a tag to the specified [TagDataOutputStream].
   *
   * @param output The output stream to which to write the tag.
   * @param value The tag to be written.
   */
  fun write(output: DataOutputStream, value: Tag) {
    try {
      output.writeByte(value.id)
      value.write(output)
    } catch (ex: Exception) {
      println("Error while writing NBT ${value.type} on output.")
      ex.printStackTrace()
    } finally {
      output.close()
    }
  }
  
  /**
   * Writes a tag to the specified [OutputStream].
   *
   * @param output The output stream to which to write the tag.
   * @param value The tag to be written.
   */
  fun write(output: OutputStream, value: Tag, compressor: TagCompressor = GZIPTagCompressor) {
    write(output.toNBTStream(compressor), value)
  }
  
  /**
   * Writes a tag to the specified file.
   *
   * @param file The file to which to write the tag.
   * @param value The tag to be written.
   */
  fun write(file: File, value: Tag, compressor: TagCompressor = GZIPTagCompressor) =
    write(file.outputStream(), value, compressor)
}

/**
 * Converts the [OutputStream] to a [TagDataOutputStream] with the specified size.
 *
 * @return The converted [TagDataOutputStream].
 */
fun OutputStream.toNBTStream(compressor: TagCompressor = GZIPTagCompressor) =
  if (this is TagDataOutputStream) this else TagDataOutputStream(this, compressor)

/**
 * Converts the [InputStream] to a [TagDataInputStream].
 *
 * @return The converted [TagDataInputStream].
 */
fun InputStream.toNBTStream(compressor: TagCompressor = GZIPTagCompressor) =
  if (this is TagDataInputStream) this else TagDataInputStream(this, compressor)

/**
 * Writes a tag to the [OutputStream].
 *
 * @param tag The tag to be written.
 */
fun OutputStream.writeTag(tag: Tag, compressor: TagCompressor = GZIPTagCompressor) = TagIO.write(this, tag, compressor)

/**
 * Writes a tag to the specified file.
 *
 * @param tag The tag to be written.
 */
fun File.writeTag(tag: Tag, compressor: TagCompressor = GZIPTagCompressor) = TagIO.write(this, tag, compressor)

/**
 * Reads a tag from the [InputStream].
 *
 * @return The read [Tag] or [EmptyTag] if an error occurs.
 */
fun InputStream.readTag(compressor: TagCompressor = GZIPTagCompressor) = TagIO.read(this, compressor)

/**
 * Reads a tag from the specified file.
 *
 * @return The read [Tag] or [EmptyTag] if an error occurs.
 */
fun File.readTag(compressor: TagCompressor = GZIPTagCompressor) = TagIO.read(this, compressor)
