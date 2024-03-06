package dream.nbt.primitives

import dream.nbt.*
import java.io.*

/**
 * Value class representing a string value as an NBT tag.
 *
 * @param value The string value.
 */
@JvmInline
value class StringTag(val value: String = "") : Tag, CharSequence, Comparable<StringTag> {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for StringTag.
   */
  override val type get() = Type
  
  /**
   * Gets the length of the string tag.
   */
  override val length: Int get() = value.length
  
  /**
   * Writes the string tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeUTF(value)
  }
  
  /**
   * Gets the character at the specified index.
   *
   * @param index The index of the character.
   * @return The character at the specified index.
   */
  override fun get(index: Int): Char = value[index]
  
  /**
   * Gets a subsequence of the string tag.
   *
   * @param startIndex The starting index of the subsequence.
   * @param endIndex The ending index of the subsequence.
   * @return The subsequence.
   */
  override fun subSequence(startIndex: Int, endIndex: Int) = value.subSequence(startIndex, endIndex)
  
  /**
   * Creates a copy of the string tag.
   */
  override fun copy() = StringTag(value)
  
  /**
   * Returns a string representation of the string tag.
   */
  override fun toString() = "\"" + value.replace("\"", "\\\"") + "\""
  
  /**
   * Compares this string tag to another for ordering.
   */
  override fun compareTo(other: StringTag) = value.compareTo(other.value)
  
  /**
   * Type tag of [StringTag].
   */
  object Type : TagType<StringTag>() {
    /**
     * Loads a StringTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded StringTag.
     */
    override fun load(data: DataInput): StringTag {
      return StringTag(data.readUTF())
    }
  }
}

fun String.toTag() = StringTag(this)
