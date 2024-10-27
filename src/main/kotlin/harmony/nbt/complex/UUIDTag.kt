package harmony.nbt.complex

import harmony.nbt.*
import java.io.*
import java.util.*

/**
 * A value class representing a UUIDTag, implementing the Tag interface and Comparable<UUIDTag>.
 * @param value The UUID value associated with the tag.
 */
@JvmInline
value class UUIDTag(val value: UUID) : Tag, Comparable<UUIDTag> {

  override val genericValue get() = value
  
  /**
   * Gets the type of the UUIDTag.
   * @return The Type object representing UUIDTag.
   */
  override val type get() = Type
  
  /**
   * Writes the UUIDTag data to a DataOutput object.
   * @param data The DataOutput object to write to.
   */
  override fun write(data: DataOutput) {
    data.writeLong(value.mostSignificantBits)
    data.writeLong(value.leastSignificantBits)
  }
  
  /**
   * Creates a copy of the UUIDTag.
   * @return The copied UUIDTag.
   */
  override fun copy() = this
  
  /**
   * Gets the string representation of the UUIDTag.
   * @return The string representation of the UUID value.
   */
  override fun toString() = value.toString()
  
  /**
   * Compares this UUIDTag with another UUIDTag.
   * @param other The other UUIDTag to compare.
   * @return An integer representing the comparison result.
   */
  override fun compareTo(other: UUIDTag): Int = value.compareTo(other.value)
  
  /**
   * The companion object representing the Type of UUIDTag.
   */
  object Type : TagType<UUIDTag>() {
    
    /**
     * Loads a UUIDTag from the given DataInput object.
     * @param data The DataInput object to read from.
     * @return The loaded UUIDTag.
     */
    override fun load(data: DataInput): UUIDTag {
      return UUIDTag(UUID(data.readLong(), data.readLong()))
    }
  }
}

fun UUID.toTag() = UUIDTag(this)
