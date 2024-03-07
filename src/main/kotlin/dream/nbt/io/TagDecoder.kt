package dream.nbt.io

import dream.nbt.util.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.modules.*
import java.io.*

/**
 * @OptIn annotation indicating the usage of experimental serialization API.
 */
@OptIn(ExperimentalSerializationApi::class)
class TagDecoder(
  val input: DataInput,
  override val serializersModule: SerializersModule,
  var size: Int = 0,
) : AbstractDecoder() {
  
  /**
   * The index of the current element being decoded in a collection.
   */
  private var elementIndex = 0
  
  /**
   * Decodes a boolean value from the input.
   * @return The decoded boolean value.
   */
  override fun decodeBoolean(): Boolean {
    return input.readBoolean()
  }
  
  /**
   * Decodes a byte value from the input.
   * @return The decoded byte value.
   */
  override fun decodeByte(): Byte {
    return input.readByte()
  }
  
  /**
   * Decodes a short value from the input.
   * @return The decoded short value.
   */
  override fun decodeShort(): Short {
    return input.readShort()
  }
  
  /**
   * Decodes an integer value from the input.
   * @return The decoded integer value.
   */
  override fun decodeInt(): Int {
    return input.readInt()
  }
  
  /**
   * Decodes a long value from the input.
   * @return The decoded long value.
   */
  override fun decodeLong(): Long {
    return input.readLong()
  }
  
  /**
   * Decodes a float value from the input.
   * @return The decoded float value.
   */
  override fun decodeFloat(): Float {
    return input.readFloat()
  }
  
  /**
   * Decodes a double value from the input.
   * @return The decoded double value.
   */
  override fun decodeDouble(): Double {
    return input.readDouble()
  }
  
  /**
   * Decodes a char value from the input.
   * @return The decoded char value.
   */
  override fun decodeChar(): Char {
    return input.readChar()
  }
  
  /**
   * Decodes a string value from the input.
   * @return The decoded string value.
   */
  override fun decodeString(): String {
    return input.readUTF()
  }
  
  /**
   * Decodes the index of an enum value from the input.
   * @param enumDescriptor The SerialDescriptor of the enum.
   * @return The decoded index of the enum value.
   */
  override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
    return input.readVarInt()
  }
  
  /**
   * Decodes the size of a collection from the input.
   * @param descriptor The SerialDescriptor of the collection.
   * @return The decoded size of the collection.
   */
  override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
    return input.readVarInt().also { size = it }
  }
  
  /**
   * Decodes the index of the next element in a collection.
   * @param descriptor The SerialDescriptor of the collection.
   * @return The index of the next element or CompositeDecoder.DECODE_DONE if the collection is fully decoded.
   */
  override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
    return if (elementIndex == size) CompositeDecoder.DECODE_DONE else elementIndex++
  }
  
  /**
   * Begins decoding a structure with the specified descriptor.
   * @param descriptor The SerialDescriptor of the structure.
   * @return The TagDecoder to decode the elements of the structure.
   */
  override fun beginStructure(descriptor: SerialDescriptor): TagDecoder {
    return TagDecoder(input, serializersModule, descriptor.elementsCount)
  }
  
  /**
   * Indicates whether decoding is done sequentially.
   * @return Always returns true, indicating sequential decoding.
   */
  override fun decodeSequentially(): Boolean {
    return true
  }
}
