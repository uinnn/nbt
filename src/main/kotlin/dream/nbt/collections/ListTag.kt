package dream.nbt.collections

import dream.nbt.*
import dream.nbt.util.*
import it.unimi.dsi.fastutil.objects.*
import java.io.*
import kotlin.contracts.*

/**
 * Value class representing a list of NBT tags.
 *
 * @param T The type of tags in the list.
 * @property value The underlying list of tags.
 */
@JvmInline
value class ListTag<T : Tag>(val value: ObjectArrayList<T>) : MutableList<T> by value, ArrayTag {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for ListTag.
   */
  override val type get() = Type
  
  /**
   * Constructs an empty ListTag.
   */
  constructor() : this(ObjectArrayList(4))
  
  /**
   * Writes the list tag data to the specified [DataOutput].
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
   * Gets the element type of the list tag.
   *
   * @return The element type.
   */
  override fun elementType(): TagType<out Tag> {
    return if (isEmpty()) EmptyType else get(0).type
  }
  
  /**
   * Creates a copy of the list tag.
   */
  override fun copy(): ListTag<T> = ListTag(ObjectArrayList(value))
  
  /**
   * Returns a string representation of the list tag.
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
   * Type tag of [ListTag].
   */
  object Type : TagType<ListTag<out Tag>>() {
    /**
     * Loads a ListTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded ListTag.
     */
    override fun load(data: DataInput): ListTag<out Tag> {
      val type = TagTypes[data.readByte()]
      val list = ListTag<Tag>()
      repeat(data.readVarInt()) { list.add(type.load(data)) }
      return list
    }
  }
}

/**
 * Creates an empty ListTag.
 *
 * @param T The type of tags in the list.
 * @return The empty ListTag.
 */
fun <T : Tag> tagListOf() = ListTag<T>()

/**
 * Creates a ListTag with the specified values.
 *
 * @param values The initial values.
 * @return The ListTag containing the specified values.
 */
fun tagListOf(vararg values: Tag) = ListTag(ObjectArrayList(values))

/**
 * Inline function to create a ListTag using a builder-style DSL.
 *
 * @param T The type of tags in the list.
 * @param builder The DSL builder for the ListTag.
 * @return The constructed ListTag.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T : Tag> tagList(builder: ListTag<T>.() -> Unit): ListTag<T> {
  contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }
  return ListTag<T>().apply(builder)
}
