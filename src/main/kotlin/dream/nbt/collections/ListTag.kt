package dream.nbt.collections

import dream.nbt.*
import dream.nbt.primitives.*
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
  override fun copy(): ListTag<T> = ListTag(value.clone())
  
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
fun tagListOf(vararg values: Byte) = values.wrapTag()
fun tagListOf(vararg values: Short) = values.wrapTag()
fun tagListOf(vararg values: Boolean) = values.wrapTag()
fun tagListOf(vararg values: Char) = values.wrapTag()
fun tagListOf(vararg values: Double) = values.wrapTag()
fun tagListOf(vararg values: Float) = values.wrapTag()
fun tagListOf(vararg values: Int) = values.wrapTag()
fun tagListOf(vararg values: Long) = values.wrapTag()
fun tagListOf(vararg values: String) = values.wrapTag()

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

fun ListTag<BooleanTag>.value(index: Int) = get(index).value
fun ListTag<ByteTag>.value(index: Int) = get(index).value
fun ListTag<ShortTag>.value(index: Int) = get(index).value
fun ListTag<CharTag>.value(index: Int) = get(index).value
fun ListTag<DoubleTag>.value(index: Int) = get(index).value
fun ListTag<FloatTag>.value(index: Int) = get(index).value
fun ListTag<IntTag>.value(index: Int) = get(index).value
fun ListTag<LongTag>.value(index: Int) = get(index).value
fun ListTag<StringTag>.value(index: Int) = get(index).value

fun ListTag<BooleanTag>.add(value: Boolean) = add(value.toTag())
fun ListTag<ByteTag>.add(value: Byte) = add(value.toTag())
fun ListTag<ShortTag>.add(value: Short) = add(value.toTag())
fun ListTag<CharTag>.add(value: Char) = add(value.toTag())
fun ListTag<DoubleTag>.add(value: Double) = add(value.toTag())
fun ListTag<FloatTag>.add(value: Float) = add(value.toTag())
fun ListTag<IntTag>.add(value: Int) = add(value.toTag())
fun ListTag<LongTag>.add(value: Long) = add(value.toTag())
fun ListTag<StringTag>.add(value: String) = add(value.toTag())

fun Iterable<Tag>.wrapTag() = toCollection(ListTag())
@JvmName("wrapBool") fun Iterable<Boolean>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapByte") fun Iterable<Byte>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapShort") fun Iterable<Short>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapChar") fun Iterable<Char>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapDouble") fun Iterable<Double>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapFloat") fun Iterable<Float>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapInt") fun Iterable<Int>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapLong") fun Iterable<Long>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapString") fun Iterable<String>.wrapTag() = mapTo(ListTag()) { it.toTag() }

fun Array<Tag>.wrapTag() = toCollection(ListTag())
fun BooleanArray.wrapTag() = mapTo(ListTag()) { it.toTag() }
fun ByteArray.wrapTag() = mapTo(ListTag()) { it.toTag() }
fun ShortArray.wrapTag() = mapTo(ListTag()) { it.toTag() }
fun CharArray.wrapTag() = mapTo(ListTag()) { it.toTag() }
fun DoubleArray.wrapTag() = mapTo(ListTag()) { it.toTag() }
fun FloatArray.wrapTag() = mapTo(ListTag()) { it.toTag() }
fun IntArray.wrapTag() = mapTo(ListTag()) { it.toTag() }
fun LongArray.wrapTag() = mapTo(ListTag()) { it.toTag() }

@JvmName("wrapBool") fun Array<Boolean>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapByte") fun Array<Byte>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapShort") fun Array<Short>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapChar") fun Array<Char>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapDouble") fun Array<Double>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapFloat") fun Array<Float>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapInt") fun Array<Int>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapLong") fun Array<Long>.wrapTag() = mapTo(ListTag()) { it.toTag() }
@JvmName("wrapString") fun Array<out String>.wrapTag() = mapTo(ListTag()) { it.toTag() }
