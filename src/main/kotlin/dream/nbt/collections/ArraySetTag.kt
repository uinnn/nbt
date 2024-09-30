package dream.nbt.collections

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.objects.*
import java.io.*
import kotlin.contracts.*

/**
 * Value class representing a set of NBT tags.
 *
 * @param T The type of tags in the set.
 * @property value The underlying set of tags.
 */
@JvmInline
value class ArraySetTag<T : Tag>(val value: ObjectArraySet<T>) : MutableSet<T> by value, IterableTag {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for SetTag.
   */
  override val type get() = Type
  
  /**
   * Constructs an empty SetTag.
   */
  constructor() : this(ObjectArraySet(4))
  
  /**
   * Writes the set tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    data.writeByte(elementType().id.toInt())
    data.writeVarInt(size)
    for (element in value) {
      element.write(data)
    }
  }
  
  /**
   * Gets the element type of the set tag.
   *
   * @return The element type.
   */
  override fun elementType(): TagType<out Tag> {
    return if (isEmpty()) EmptyType else first().type
  }
  
  /**
   * Creates a copy of the set tag.
   */
  override fun copy(): ArraySetTag<T> = ArraySetTag(value.clone())
  
  /**
   * Returns a string representation of the set tag.
   */
  override fun toString(): String = buildString {
    append('[')
    value.forEachIndexed { index, tag ->
      if (index != 0) append(',')
      append(index).append(':').append(tag)
    }
    append(']')
  }
  
  /**
   * Type tag of [ArraySetTag].
   */
  object Type : TagType<ArraySetTag<out Tag>>() {
    /**
     * Loads a SetTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded SetTag.
     */
    override fun load(data: DataInput): ArraySetTag<out Tag> {
      val type = TagTypes[data.readByte()]
      val set = ArraySetTag<Tag>()
      repeat(data.readVarInt()) { set.add(type.load(data)) }
      return set
    }
  }
}

/**
 * Creates an empty SetTag.
 *
 * @param T The type of tags in the set.
 * @return The empty SetTag.
 */
fun <T : Tag> tagArraySetOf() = ArraySetTag<T>()

/**
 * Creates a SetTag with the specified values.
 *
 * @param values The initial values.
 * @return The SetTag containing the specified values.
 */
fun tagArraySetOf(vararg values: Tag) = ArraySetTag(ObjectArraySet(values))

/**
 * Inline function to create a SetTag using a builder-style DSL.
 *
 * @param T The type of tags in the set.
 * @param builder The DSL builder for the SetTag.
 * @return The constructed SetTag.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T : Tag> tagArraySet(builder: ArraySetTag<T>.() -> Unit): ArraySetTag<T> {
  contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }
  return ArraySetTag<T>().apply(builder)
}
