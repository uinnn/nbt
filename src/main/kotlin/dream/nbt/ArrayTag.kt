package dream.nbt

/**
 * An interface representing a tag that holds an array of elements.
 * Extends the [Tag] interface.
 */
interface ArrayTag : Tag {
  
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
