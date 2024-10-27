package harmony.nbt

/**
 * An interface representing a tag that holds an iterable of elements.
 * Extends the [Tag] interface.
 */
interface IterableTag : Tag {
  
  /**
   * Gets the size of the array.
   */
  val size: Int
  
  /**
   * Gets the element type of the array.
   *
   * @return The [TagType] representing the type of elements in the array.
   */
  fun elementType(): TagType<out Tag>
}
