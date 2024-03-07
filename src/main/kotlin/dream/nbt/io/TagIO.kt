@file:Suppress("NOTHING_TO_INLINE")

package dream.nbt.io

import dream.nbt.*
import dream.nbt.compression.*
import it.unimi.dsi.fastutil.io.*
import kotlinx.serialization.*
import kotlinx.serialization.modules.*
import java.io.*

/**
 * An object providing methods for reading and writing NBT tags.
 */
open class TagIO(val serializersModule: SerializersModule) {
  companion object : TagIO(EmptySerializersModule())
  
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
      ex.printStackTrace()
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
  fun read(file: File, compressor: TagCompressor = GZIPTagCompressor): Tag {
    return read(file.inputStream(), compressor)
  }
  
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
  fun write(file: File, value: Tag, compressor: TagCompressor = GZIPTagCompressor) {
    write(file.outputStream(), value, compressor)
  }
  
  /**
   * Reads a Tag from a ByteArray using the specified TagCompressor, defaulting to GZIPTagCompressor.
   * @param bytes The ByteArray containing the encoded Tag data.
   * @param compressor The TagCompressor to use for decompression (default is GZIPTagCompressor).
   * @return The decoded Tag.
   */
  fun read(bytes: ByteArray, compressor: TagCompressor = GZIPTagCompressor): Tag {
    return read(FastByteArrayInputStream(bytes), compressor)
  }
  
  /**
   * Encodes a Tag to an OutputStream using the specified serializer, value, and TagCompressor.
   * @param stream The OutputStream to write the encoded Tag to.
   * @param serializer The SerializationStrategy for the Tag type.
   * @param value The Tag to be encoded.
   * @param compressor The TagCompressor to use for compression (default is GZIPTagCompressor).
   */
  fun <T> encodeToStream(
    stream: OutputStream,
    serializer: SerializationStrategy<T>,
    value: T,
    compressor: TagCompressor = GZIPTagCompressor,
  ) = stream.toNBTStream(compressor).use {
    val encoder = TagEncoder(it, serializersModule)
    encoder.encodeSerializableValue(serializer, value)
  }
  
  /**
   * Decodes a Tag from an InputStream using the specified deserializer and TagCompressor.
   * @param stream The InputStream containing the encoded Tag data.
   * @param serializer The DeserializationStrategy for the Tag type.
   * @param compressor The TagCompressor to use for decompression (default is GZIPTagCompressor).
   * @return The decoded Tag.
   */
  fun <T> decodeFromStream(
    stream: InputStream,
    serializer: DeserializationStrategy<T>,
    compressor: TagCompressor = GZIPTagCompressor,
  ) = stream.toNBTStream(compressor).use {
    val decoder = TagDecoder(it, serializersModule)
    decoder.decodeSerializableValue(serializer)
  }
  
  /**
   * Encodes a Tag to a File using the specified serializer, value, and TagCompressor.
   * @param file The File to write the encoded Tag to.
   * @param serializer The SerializationStrategy for the Tag type.
   * @param value The Tag to be encoded.
   * @param compressor The TagCompressor to use for compression (default is GZIPTagCompressor).
   */
  fun <T> encodeToFile(
    file: File,
    serializer: SerializationStrategy<T>,
    value: T,
    compressor: TagCompressor = GZIPTagCompressor,
  ) = encodeToStream(file.outputStream(), serializer, value, compressor)
  
  /**
   * Decodes a Tag from a File using the specified deserializer and TagCompressor.
   * @param file The File containing the encoded Tag data.
   * @param serializer The DeserializationStrategy for the Tag type.
   * @param compressor The TagCompressor to use for decompression (default is GZIPTagCompressor).
   * @return The decoded Tag.
   */
  fun <T> decodeFromFile(
    file: File,
    serializer: DeserializationStrategy<T>,
    compressor: TagCompressor = GZIPTagCompressor,
  ) = decodeFromStream(file.inputStream(), serializer, compressor)
  
  /**
   * Encodes a Tag to a ByteArray using the specified serializer, value, and TagCompressor.
   * @param serializer The SerializationStrategy for the Tag type.
   * @param value The Tag to be encoded.
   * @param compressor The TagCompressor to use for compression (default is GZIPTagCompressor).
   * @return The encoded Tag as a ByteArray.
   */
  fun <T> encodeToBytes(
    serializer: SerializationStrategy<T>,
    value: T,
    compressor: TagCompressor = GZIPTagCompressor,
  ): ByteArray {
    val output = FastByteArrayOutputStream()
    encodeToStream(output, serializer, value, compressor)
    return output.array
  }
  
  /**
   * Decodes a Tag from a ByteArray using the specified deserializer and TagCompressor.
   * @param serializer The DeserializationStrategy for the Tag type.
   * @param bytes The ByteArray containing the encoded Tag data.
   * @param compressor The TagCompressor to use for decompression (default is GZIPTagCompressor).
   * @return The decoded Tag.
   */
  fun <T> decodeFromBytes(
    serializer: DeserializationStrategy<T>,
    bytes: ByteArray,
    compressor: TagCompressor = GZIPTagCompressor,
  ) = decodeFromStream(FastByteArrayInputStream(bytes), serializer, compressor)
  
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

/**
 * Inline function to encode a Tag of type [T] to an OutputStream using the specified serializer, value, and TagCompressor.
 * @param stream The OutputStream to write the encoded Tag to.
 * @param value The Tag to be encoded.
 * @param compressor The TagCompressor to use for compression (default is GZIPTagCompressor).
 */
inline fun <reified T> TagIO.encodeToStream(
  stream: OutputStream,
  value: T,
  compressor: TagCompressor = GZIPTagCompressor,
) = encodeToStream(stream, serializer(), value, compressor)

/**
 * Inline function to decode a Tag of type [T] from an InputStream using the specified deserializer and TagCompressor.
 * @param stream The InputStream containing the encoded Tag data.
 * @param compressor The TagCompressor to use for decompression (default is GZIPTagCompressor).
 * @return The decoded Tag.
 */
inline fun <reified T> TagIO.decodeFromStream(
  stream: InputStream,
  compressor: TagCompressor = GZIPTagCompressor,
) = decodeFromStream(stream, serializer<T>(), compressor)

/**
 * Inline function to encode a Tag of type [T] to a File using the specified serializer, value, and TagCompressor.
 * @param file The File to write the encoded Tag to.
 * @param value The Tag to be encoded.
 * @param compressor The TagCompressor to use for compression (default is GZIPTagCompressor).
 */
inline fun <reified T> TagIO.encodeToFile(
  file: File,
  value: T,
  compressor: TagCompressor = GZIPTagCompressor,
) = encodeToStream(file.outputStream(), serializer(), value, compressor)

/**
 * Inline function to decode a Tag of type [T] from a File using the specified deserializer and TagCompressor.
 * @param file The File containing the encoded Tag data.
 * @param compressor The TagCompressor to use for decompression (default is GZIPTagCompressor).
 * @return The decoded Tag.
 */
inline fun <reified T> TagIO.decodeFromFile(
  file: File,
  compressor: TagCompressor = GZIPTagCompressor,
) = decodeFromStream(file.inputStream(), serializer<T>(), compressor)

/**
 * Inline function to encode a Tag of type [T] to a ByteArray using the specified serializer, value, and TagCompressor.
 * @param value The Tag to be encoded.
 * @param compressor The TagCompressor to use for compression (default is GZIPTagCompressor).
 * @return The encoded Tag as a ByteArray.
 */
inline fun <reified T> TagIO.encodeToBytes(
  value: T,
  compressor: TagCompressor = GZIPTagCompressor,
): ByteArray {
  val output = FastByteArrayOutputStream()
  encodeToStream(output, serializer(), value, compressor)
  return output.array
}

/**
 * Inline function to decode a Tag of type [T] from a ByteArray using the specified deserializer and TagCompressor.
 * @param bytes The ByteArray containing the encoded Tag data.
 * @param compressor The TagCompressor to use for decompression (default is GZIPTagCompressor).
 * @return The decoded Tag.
 */
inline fun <reified T> TagIO.decodeFromBytes(
  bytes: ByteArray,
  compressor: TagCompressor = GZIPTagCompressor,
) = decodeFromStream(FastByteArrayInputStream(bytes), serializer<T>(), compressor)

