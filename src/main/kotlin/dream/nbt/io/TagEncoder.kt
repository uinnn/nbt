@file:Suppress("NOTHING_TO_INLINE")

package dream.nbt.io

import dream.nbt.util.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.modules.*
import java.io.*

/**
 * Encoder class for encoding Tag objects using the NBT (Named Binary Tag) format.
 *
 * @param output The DataOutput to write the encoded data to.
 * @param serializersModule The SerializersModule containing the serializers for custom types.
 * @param size Initial size of the encoded data (default is 0).
 */
@OptIn(ExperimentalSerializationApi::class)
class TagEncoder(
  val output: DataOutput,
  override val serializersModule: SerializersModule,
  var size: Int = 0,
) : AbstractEncoder() {
  
  /**
   * Encodes a Boolean value.
   * @param value The Boolean value to be encoded.
   */
  override fun encodeBoolean(value: Boolean) {
    output.writeBoolean(value)
  }
  
  /**
   * Encodes a Byte value.
   * @param value The Byte value to be encoded.
   */
  override fun encodeByte(value: Byte) {
    output.writeByte(value.toInt())
  }
  
  /**
   * Encodes a Short value.
   * @param value The Short value to be encoded.
   */
  override fun encodeShort(value: Short) {
    output.writeShort(value.toInt())
  }
  
  /**
   * Encodes an Int value.
   * @param value The Int value to be encoded.
   */
  override fun encodeInt(value: Int) {
    output.writeInt(value)
  }
  
  /**
   * Encodes a Long value.
   * @param value The Long value to be encoded.
   */
  override fun encodeLong(value: Long) {
    output.writeLong(value)
  }
  
  /**
   * Encodes a Float value.
   * @param value The Float value to be encoded.
   */
  override fun encodeFloat(value: Float) {
    output.writeFloat(value)
  }
  
  /**
   * Encodes a Double value.
   * @param value The Double value to be encoded.
   */
  override fun encodeDouble(value: Double) {
    output.writeDouble(value)
  }
  
  /**
   * Encodes a Char value.
   * @param value The Char value to be encoded.
   */
  override fun encodeChar(value: Char) {
    output.writeChar(value.code)
  }
  
  /**
   * Encodes a String value.
   * @param value The String value to be encoded.
   */
  override fun encodeString(value: String) {
    output.writeUTF(value)
  }
  
  /**
   * Encodes an Enum value.
   * @param enumDescriptor The SerialDescriptor of the Enum.
   * @param index The index of the Enum value to be encoded.
   */
  override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
    output.writeVarInt(index)
  }
  
  /**
   * Begins encoding a collection, writing the collection size.
   * @param descriptor The SerialDescriptor of the collection.
   * @param collectionSize The size of the collection to be encoded.
   * @return The CompositeEncoder for encoding the elements of the collection.
   */
  override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
    output.writeVarInt(collectionSize)
    return this
  }
}
