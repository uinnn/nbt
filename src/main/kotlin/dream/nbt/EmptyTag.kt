package dream.nbt

import java.io.*

/**
 * An object representing an empty tag.
 * Implements the [Tag] interface.
 */
object EmptyTag : Tag {
  
  /**
   * Gets the type of the empty tag.
   */
  override val type: TagType<EmptyTag> get() = Type
  
  /**
   * Writes empty tag data to the specified [DataOutput].
   *
   * @param data The [DataOutput] to write the data to (no data is written for an empty tag).
   */
  override fun write(data: DataOutput) = Unit
  
  /**
   * Creates a copy of the empty tag.
   *
   * @return The [EmptyTag] instance (since it is already empty).
   */
  override fun copy() = EmptyTag
  
  /**
   * Converts the empty tag to its string representation.
   *
   * @return The string representation of the empty tag.
   */
  override fun toString() = "EmptyTag()"
  
  /**
   * The type of the empty tag.
   */
  object Type : TagType<EmptyTag>() {
    
    /**
     * Loads empty tag data from the specified [DataInput].
     *
     * @param data The [DataInput] containing the empty tag data (no data is loaded for an empty tag).
     * @return The [EmptyTag] instance.
     */
    override fun load(data: DataInput): EmptyTag {
      return EmptyTag
    }
  }
}
