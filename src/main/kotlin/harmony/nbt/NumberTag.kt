package harmony.nbt

/**
 * An interface representing a tag that holds numeric values.
 * Extends the [Tag] interface.
 */
interface NumberTag : Tag {
  
  override val genericValue: Number
  
  /**
   * Converts the tag value to a generic [Number].
   *
   * @return The numeric value of the tag.
   */
  fun toNumber(): Number
  
  /**
   * Converts the tag value to a [Byte].
   *
   * @return The [Byte] representation of the tag value.
   */
  fun toByte(): Byte
  
  /**
   * Converts the tag value to a [Short].
   *
   * @return The [Short] representation of the tag value.
   */
  fun toShort(): Short
  
  /**
   * Converts the tag value to an [Int].
   *
   * @return The [Int] representation of the tag value.
   */
  fun toInt(): Int
  
  /**
   * Converts the tag value to a [Long].
   *
   * @return The [Long] representation of the tag value.
   */
  fun toLong(): Long
  
  /**
   * Converts the tag value to a [Float].
   *
   * @return The [Float] representation of the tag value.
   */
  fun toFloat(): Float
  
  /**
   * Converts the tag value to a [Double].
   *
   * @return The [Double] representation of the tag value.
   */
  fun toDouble(): Double
  
  /**
   * Compares the tag value to the specified [Byte] value.
   *
   * @param value The [Byte] value to compare to.
   * @return A value less than 0 if the tag value is less than the specified [Byte],
   *         0 if they are equal, and a value greater than 0 if the tag value is greater.
   */
  operator fun compareTo(value: Byte) = toByte().compareTo(value)
  
  /**
   * Compares the tag value to the specified [Short] value.
   *
   * @param value The [Short] value to compare to.
   * @return A value less than 0 if the tag value is less than the specified [Short],
   *         0 if they are equal, and a value greater than 0 if the tag value is greater.
   */
  operator fun compareTo(value: Short) = toShort().compareTo(value)
  
  /**
   * Compares the tag value to the specified [Int] value.
   *
   * @param value The [Int] value to compare to.
   * @return A value less than 0 if the tag value is less than the specified [Int],
   *         0 if they are equal, and a value greater than 0 if the tag value is greater.
   */
  operator fun compareTo(value: Int) = toInt().compareTo(value)
  
  /**
   * Compares the tag value to the specified [Long] value.
   *
   * @param value The [Long] value to compare to.
   * @return A value less than 0 if the tag value is less than the specified [Long],
   *         0 if they are equal, and a value greater than 0 if the tag value is greater.
   */
  operator fun compareTo(value: Long) = toLong().compareTo(value)
  
  /**
   * Compares the tag value to the specified [Float] value.
   *
   * @param value The [Float] value to compare to.
   * @return A value less than 0 if the tag value is less than the specified [Float],
   *         0 if they are equal, and a value greater than 0 if the tag value is greater.
   */
  operator fun compareTo(value: Float) = toFloat().compareTo(value)
  
  /**
   * Compares the tag value to the specified [Double] value.
   *
   * @param value The [Double] value to compare to.
   * @return A value less than 0 if the tag value is less than the specified [Double],
   *         0 if they are equal, and a value greater than 0 if the tag value is greater.
   */
  operator fun compareTo(value: Double) = toDouble().compareTo(value)
}
