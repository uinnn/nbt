@file:Suppress("NOTHING_TO_INLINE")

package dream.nbt.collections

import dream.nbt.*
import dream.nbt.arrays.*
import dream.nbt.complex.*
import dream.nbt.primitives.*
import it.unimi.dsi.fastutil.booleans.*
import it.unimi.dsi.fastutil.bytes.*
import it.unimi.dsi.fastutil.chars.*
import it.unimi.dsi.fastutil.doubles.*
import it.unimi.dsi.fastutil.floats.*
import it.unimi.dsi.fastutil.ints.*
import it.unimi.dsi.fastutil.longs.*
import it.unimi.dsi.fastutil.objects.*
import it.unimi.dsi.fastutil.shorts.*
import java.io.*
import java.util.*
import kotlin.contracts.*

/**
 * Value class representing a compound NBT tag.
 *
 * @property value The underlying map of tags.
 */
@JvmInline
value class CompoundTag(val value: Object2ObjectOpenHashMap<String, Tag>) : MutableMap<String, Tag> by value, Tag {
  
  override val genericValue get() = value
  
  /**
   * Gets the NBT tag type for CompoundTag.
   */
  override val type get() = Type
  
  /**
   * Constructs a CompoundTag with the specified initial capacity.
   *
   * @param size The initial capacity.
   */
  constructor(size: Int = 5) : this(Object2ObjectOpenHashMap(size, 0.8f))
  
  /**
   * Constructs a CompoundTag with the specified key-value pairs.
   *
   * @param entries The initial key-value pairs.
   */
  constructor(vararg entries: Pair<String, Tag>) : this(entries.size.coerceAtLeast(5)) {
    value.putAll(entries)
  }
  
  /**
   * Writes the compound tag data to the specified [DataOutput].
   *
   * @param data The data output stream.
   */
  override fun write(data: DataOutput) {
    for (entry in value) {
      writeEntry(entry.key, entry.value, data)
    }
    data.writeByte(0) // end
  }
  
  /**
   * Writes a key-value entry to the specified [DataOutput].
   *
   * @param key The key.
   * @param value The value tag.
   * @param data The data output stream.
   */
  private fun writeEntry(key: String, value: Tag, data: DataOutput) {
    data.writeByte(value.id)
    if (!value.isEnd) {
      data.writeUTF(key)
      value.write(data)
    }
  }
  
  /**
   * Reads a key-value entry from the specified [DataInput].
   *
   * @param type The expected type of the value tag.
   * @param key The key.
   * @param data The data input stream.
   * @return The loaded value tag.
   */
  private fun readEntry(type: TagType<out Tag>, key: String, data: DataInput): Tag {
    val value = type.load(data)
    put(key, value)
    return value
  }
  
  /**
   * Creates a copy of the compound tag.
   */
  override fun copy() = CompoundTag(value.clone())
  
  /**
   * Returns a string representation of the compound tag.
   */
  override fun toString(): String = buildString {
    append('{')
    for (entry in value) {
      if (length != 1) append(", ")
      append(entry.key).append(':').append(entry.value)
    }
    append('}')
  }
  
  /**
   * Internal function to handle the case when a tag with the specified key is not found.
   *
   * @param key The key.
   */
  @PublishedApi
  internal fun tagNotFound(key: String) {
    error("No NBT element in CompoundTag found with key $key")
  }
  
  /**
   * Gets a tag of type [T] with the specified key.
   *
   * @param key The key.
   * @return The tag of type [T].
   */
  private inline fun <reified T : Tag> getTag(key: String): T {
    val tag = get(key) ?: tagNotFound(key)
    return tag as T
  }
  
  /**
   * Gets a tag of type [T] or returns null if the tag with the specified key is not found.
   *
   * @param key The key.
   * @return The tag of type [T] or null.
   */
  private inline fun <reified T : Tag> getTagOrNull(key: String): T? {
    return get(key) as? T
  }
  
  /**
   * Gets a tag of type [T] with the specified key, or adds a new tag if not found.
   *
   * @param key The key.
   * @param value A function to create the new tag if not found.
   * @return The tag of type [T].
   */
  private inline fun <reified T : Tag> getOrAdd(key: String, value: (CompoundTag) -> T): T {
    return if (key in this) {
      get(key) as T
    } else {
      val tag = value(this)
      put(key, tag)
      tag
    }
  }
  
  /**
   * Checks if contains the specified key.
   */
  fun has(key: String): Boolean = containsKey(key)
  
  /**
   * Checks if contains the specified key with their specific id.
   */
  fun has(key: String, id: Int): Boolean {
    return (get(key)?.type ?: return false).id == id.toByte()
  }
  
  /**
   * Checks if contains the specified key with their specific type.
   */
  fun has(key: String, type: TagType<out Tag>): Boolean {
    return (get(key)?.type ?: return false) === type
  }
  
  /**
   * Gets the tag type present of the given key.
   */
  fun typeOf(key: String): TagType<out Tag> = get(key)?.type ?: EmptyType
  
  /**
   * Gets the tag type id present of the given key.
   */
  fun typeIdOf(key: String): Int = typeOf(key).id.toInt()
  
  /**
   * Checks if the tag type present of the given key is [EmptyType].
   */
  fun isEmptyType(key: String): Boolean = typeOf(key) === EmptyType
  
  /**
   * Checks if the tag type present of the given key is a [NumberTag].
   */
  fun isNumber(key: String): Boolean = get(key) is NumberTag
  
  /**
   * Checks if the tag type present of the given key is an [ArrayTag].
   */
  fun isArray(key: String): Boolean = get(key) is ArrayTag
  
  /**
   * Gets a number from the compound tag.
   */
  fun number(key: String, default: Number = 0): Number {
    val tag = get(key) ?: return default
    return if (tag is NumberTag) tag.toNumber() else default
  }
  
  /**
   * Gets a number from the compound tag or returns null if not found.
   */
  fun numberOrNull(key: String): Number? {
    val tag = get(key) ?: return null
    return if (tag is NumberTag) tag.toNumber() else null
  }
  
  /**
   * Gets a byte from the compound tag.
   */
  fun byte(key: String, default: Byte = 0): Byte {
    return (getTagOrNull<ByteTag>(key) ?: return default).value
  }
  
  /**
   * Gets a byte from the compound tag or returns null if not found.
   */
  fun byteOrNull(key: String): Byte? {
    return (getTagOrNull<ByteTag>(key) ?: return null).value
  }
  
  /**
   * Gets a raw boolean from the compound tag.
   * Raw boolean is presented by [ByteTag].
   */
  fun rawBoolean(key: String, default: Boolean = false): Boolean {
    val value = byteOrNull(key) ?: return default
    return value == 1.toByte()
  }
  
  /**
   * Gets a raw boolean from the compound tag or returns null if not found.
   * Raw boolean is presented by [ByteTag].
   */
  fun rawBooleanOrNull(key: String): Boolean? {
    val value = byteOrNull(key) ?: return null
    return value == 1.toByte()
  }
  
  /**
   * Gets a boolean tag from the compound tag.
   */
  fun booleanTag(key: String, default: BooleanTag = BooleanTag.FALSE): BooleanTag {
    return getTagOrNull<BooleanTag>(key) ?: return default
  }
  
  /**
   * Gets a boolean tag from the compound tag or returns null if not found.
   */
  fun booleanTagOrNull(key: String): BooleanTag? {
    return getTagOrNull<BooleanTag>(key)
  }
  
  /**
   * Gets a boolean value from the compound tag.
   */
  fun boolean(key: String, bit: Int = 1, default: Boolean = false): Boolean {
    val value = getTagOrNull<BooleanTag>(key) ?: return default
    return value[bit]
  }
  
  /**
   * Gets a boolean value from the compound tag or returns null if not found.
   */
  fun booleanOrNull(key: String, bit: Int = 1): Boolean? {
    val value = getTagOrNull<BooleanTag>(key) ?: return null
    return value[bit]
  }
  
  /**
   * Gets a char from the compound tag.
   */
  fun char(key: String, default: Char = ' '): Char {
    return (getTagOrNull<CharTag>(key) ?: return default).value
  }
  
  /**
   * Gets a char from the compound tag or returns null if not found.
   */
  fun charOrNull(key: String): Char? {
    return (getTagOrNull<CharTag>(key) ?: return null).value
  }
  
  /**
   * Gets a short from the compound tag.
   */
  fun short(key: String, default: Short = 0): Short {
    return (getTagOrNull<ShortTag>(key) ?: return default).value
  }
  
  /**
   * Gets a short from the compound tag or returns null if not found.
   */
  fun shortOrNull(key: String): Short? {
    return (getTagOrNull<ShortTag>(key) ?: return null).value
  }
  
  /**
   * Gets an int from the compound tag.
   */
  fun int(key: String, default: Int = 0): Int {
    return (getTagOrNull<IntTag>(key) ?: return default).value
  }
  
  /**
   * Gets an int from the compound tag or returns null if not found.
   */
  fun intOrNull(key: String): Int? {
    return (getTagOrNull<IntTag>(key) ?: return null).value
  }
  
  /**
   * Gets a long from the compound tag.
   */
  fun long(key: String, default: Long = 0): Long {
    return (getTagOrNull<LongTag>(key) ?: return default).value
  }
  
  /**
   * Gets a long from the compound tag or returns null if not found.
   */
  fun longOrNull(key: String): Long? {
    return (getTagOrNull<LongTag>(key) ?: return null).value
  }
  
  /**
   * Gets a float from the compound tag.
   */
  fun float(key: String, default: Float = 0f): Float {
    return (getTagOrNull<FloatTag>(key) ?: return default).value
  }
  
  /**
   * Gets a float from the compound tag or returns null if not found.
   */
  fun floatOrNull(key: String): Float? {
    return (getTagOrNull<FloatTag>(key) ?: return null).value
  }
  
  /**
   * Gets a double from the compound tag.
   */
  fun double(key: String, default: Double = 0.0): Double {
    return (getTagOrNull<DoubleTag>(key) ?: return default).value
  }
  
  /**
   * Gets a double from the compound tag or returns null if not found.
   */
  fun doubleOrNull(key: String): Double? {
    return (getTagOrNull<DoubleTag>(key) ?: return null).value
  }
  
  /**
   * Gets a string from the compound tag.
   */
  fun string(key: String, default: String = ""): String {
    return (getTagOrNull<StringTag>(key) ?: return default).value
  }
  
  /**
   * Gets a string from the compound tag or returns null if not found.
   */
  fun stringOrNull(key: String): String? {
    return (getTagOrNull<StringTag>(key) ?: return null).value
  }
  
  /**
   * Gets a list from the compound tag.
   */
  fun <T : Tag> list(key: String, default: ListTag<T>? = null): ListTag<T> {
    return getTagOrNull(key) ?: return default ?: ListTag()
  }
  
  /**
   * Gets a list from the compound tag or returns null if not found.
   */
  fun <T : Tag> listOrNull(key: String): ListTag<T>? {
    return getTagOrNull(key)
  }
  
  /**
   * Gets a list of strings from the compound tag.
   */
  fun stringList(key: String, default: ListTag<StringTag>? = null): ListTag<StringTag> {
    return getTagOrNull(key) ?: return default ?: ListTag()
  }
  
  /**
   * Gets a list of strings from the compound tag or returns null if not found.
   */
  fun stringListOrNull(key: String): ListTag<StringTag>? {
    return getTagOrNull(key)
  }
  
  /**
   * Gets a list of compounds from the compound tag.
   */
  fun compoundList(key: String, default: ListTag<CompoundTag>? = null): ListTag<CompoundTag> {
    return getTagOrNull(key) ?: return default ?: ListTag()
  }
  
  /**
   * Gets a list of compounds from the compound tag or returns null if not found.
   */
  fun compoundListOrNull(key: String): ListTag<CompoundTag>? {
    return getTagOrNull(key)
  }
  
  /**
   * Gets a set from the compound tag.
   */
  fun <T : Tag> getSet(key: String, default: SetTag<T>? = null): SetTag<T> {
    return getTagOrNull(key) ?: return default ?: SetTag()
  }
  
  /**
   * Gets a set from the compound tag or returns null if not found.
   */
  fun <T : Tag> getSetOrNull(key: String): SetTag<T>? {
    return getTagOrNull(key)
  }
  
  /**
   * Gets a compound from the compound tag.
   */
  fun compound(key: String, default: CompoundTag? = null): CompoundTag {
    return getTagOrNull(key) ?: return default ?: CompoundTag()
  }
  
  /**
   * Gets a compound from the compound tag or returns null if not found.
   */
  fun compoundOrNull(key: String): CompoundTag? {
    return getTagOrNull(key)
  }
  
  /**
   * Gets a byte array from the compound tag.
   */
  fun byteArray(key: String, default: ByteArray? = null): ByteArray {
    return (getTagOrNull<ByteArrayTag>(key) ?: return default ?: ByteArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a byte array from the compound tag or returns null if not found.
   */
  fun byteArrayOrNull(key: String): ByteArray? {
    return (getTagOrNull<ByteArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a boolean array from the compound tag.
   */
  fun booleanArray(key: String, default: BooleanArray? = null): BooleanArray {
    return (getTagOrNull<BooleanArrayTag>(key) ?: return default ?: BooleanArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a boolean array from the compound tag or returns null if not found.
   */
  fun booleanArrayOrNull(key: String): BooleanArray? {
    return (getTagOrNull<BooleanArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a packed boolean array from the compound tag.
   */
  fun packedBooleanArray(key: String, default: BooleanArray? = null): BooleanArray {
    return (getTagOrNull<PackedBooleanArrayTag>(key) ?: return default ?: BooleanArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a packed boolean array from the compound tag or returns null if not found.
   */
  fun packedBooleanArrayOrNull(key: String): BooleanArray? {
    return (getTagOrNull<PackedBooleanArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a char array from the compound tag.
   */
  fun charArray(key: String, default: CharArray? = null): CharArray {
    return (getTagOrNull<CharArrayTag>(key) ?: return default ?: CharArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a char array from the compound tag or returns null if not found.
   */
  fun charArrayOrNull(key: String): CharArray? {
    return (getTagOrNull<CharArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a short array from the compound tag.
   */
  fun shortArray(key: String, default: ShortArray? = null): ShortArray {
    return (getTagOrNull<ShortArrayTag>(key) ?: return default ?: ShortArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a short array from the compound tag or returns null if not found.
   */
  fun shortArrayOrNull(key: String): ShortArray? {
    return (getTagOrNull<ShortArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets an int array from the compound tag.
   */
  fun intArray(key: String, default: IntArray? = null): IntArray {
    return (getTagOrNull<IntArrayTag>(key) ?: return default ?: IntArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets an int array from the compound tag or returns null if not found.
   */
  fun intArrayOrNull(key: String): IntArray? {
    return (getTagOrNull<IntArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a long array from the compound tag.
   */
  fun longArray(key: String, default: LongArray? = null): LongArray {
    return (getTagOrNull<LongArrayTag>(key) ?: return default ?: LongArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a long array from the compound tag or returns null if not found.
   */
  fun longArrayOrNull(key: String): LongArray? {
    return (getTagOrNull<LongArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a float array from the compound tag.
   */
  fun floatArray(key: String, default: FloatArray? = null): FloatArray {
    return (getTagOrNull<FloatArrayTag>(key) ?: return default ?: FloatArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a float array from the compound tag or returns null if not found.
   */
  fun floatArrayOrNull(key: String): FloatArray? {
    return (getTagOrNull<FloatArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a double array from the compound tag.
   */
  fun doubleArray(key: String, default: DoubleArray? = null): DoubleArray {
    return (getTagOrNull<DoubleArrayTag>(key) ?: return default ?: DoubleArrays.EMPTY_ARRAY).value
  }
  
  /**
   * Gets a double array from the compound tag or returns null if not found.
   */
  fun doubleArrayOrNull(key: String): DoubleArray? {
    return (getTagOrNull<DoubleArrayTag>(key) ?: return null).value
  }
  
  /**
   * Gets a UUID from the compound tag.
   */
  fun uuid(key: String, default: UUID? = null): UUID {
    return (getTagOrNull<UUIDTag>(key) ?: return default ?: UUID(0, 0)).value
  }
  
  /**
   * Gets a UUID from the compound tag or returns null if not found.
   */
  fun uuidOrNull(key: String): UUID? {
    return (getTagOrNull<UUIDTag>(key) ?: return null).value
  }
  
  /**
   * Merges the values from another compound tag into this compound tag.
   *
   * @param tag The compound tag to merge.
   * @return The merged compound tag.
   */
  fun merge(tag: CompoundTag): CompoundTag {
    for ((key, value) in tag) {
      if (value is CompoundTag) {
        if (key in this) {
          compound(key).merge(value)
        } else {
          put(key, value.copy())
        }
      } else {
        put(key, value.copy())
      }
    }
    return this
  }
  
  /**
   * Sets a boolean value at the specified key using a BooleanTag.
   */
  operator fun set(key: String, value: Boolean) {
    put(key, BooleanTag(value))
  }
  
  /**
   * Sets a pair of boolean values at the specified key using a BooleanTag.
   */
  operator fun set(key: String, value1: Boolean, value2: Boolean) {
    put(key, BooleanTag(value1, value2))
  }
  
  /**
   * Sets a triple of boolean values at the specified key using a BooleanTag.
   */
  operator fun set(key: String, value1: Boolean, value2: Boolean, value3: Boolean) {
    put(key, BooleanTag(value1, value2, value3))
  }
  
  /**
   * Sets multiple boolean values at the specified key using a BooleanTag.
   */
  fun set(key: String, vararg values: Boolean) {
    put(key, BooleanTag(*values))
  }
  
  /**
   * Sets a byte value at the specified key using a ByteTag.
   */
  operator fun set(key: String, value: Byte) {
    put(key, ByteTag(value))
  }
  
  /**
   * Sets a char value at the specified key using a CharTag.
   */
  operator fun set(key: String, value: Char) {
    put(key, CharTag(value))
  }
  
  /**
   * Sets a short value at the specified key using a ShortTag.
   */
  operator fun set(key: String, value: Short) {
    put(key, ShortTag(value))
  }
  
  /**
   * Sets an int value at the specified key using an IntTag.
   */
  operator fun set(key: String, value: Int) {
    put(key, IntTag(value))
  }
  
  /**
   * Sets a long value at the specified key using a LongTag.
   */
  operator fun set(key: String, value: Long) {
    put(key, LongTag(value))
  }
  
  /**
   * Sets a float value at the specified key using a FloatTag.
   */
  operator fun set(key: String, value: Float) {
    put(key, FloatTag(value))
  }
  
  /**
   * Sets a double value at the specified key using a DoubleTag.
   */
  operator fun set(key: String, value: Double) {
    put(key, DoubleTag(value))
  }
  
  /**
   * Sets a string value at the specified key using a StringTag.
   */
  operator fun set(key: String, value: String) {
    put(key, StringTag(value))
  }
  
  /**
   * Sets a boolean array value at the specified key using a PackedBooleanArrayTag.
   */
  operator fun set(key: String, value: BooleanArray) {
    put(key, PackedBooleanArrayTag(value))
  }
  
  /**
   * Sets a byte array value at the specified key using a ByteArrayTag.
   */
  operator fun set(key: String, value: ByteArray) {
    put(key, ByteArrayTag(value))
  }
  
  /**
   * Sets a char array value at the specified key using a CharArrayTag.
   */
  operator fun set(key: String, value: CharArray) {
    put(key, CharArrayTag(value))
  }
  
  /**
   * Sets a short array value at the specified key using a ShortArrayTag.
   */
  operator fun set(key: String, value: ShortArray) {
    put(key, ShortArrayTag(value))
  }
  
  /**
   * Sets an int array value at the specified key using an IntArrayTag.
   */
  operator fun set(key: String, value: IntArray) {
    put(key, IntArrayTag(value))
  }
  
  /**
   * Sets a long array value at the specified key using a LongArrayTag.
   */
  operator fun set(key: String, value: LongArray) {
    put(key, LongArrayTag(value))
  }
  
  /**
   * Sets a float array value at the specified key using a FloatArrayTag.
   */
  operator fun set(key: String, value: FloatArray) {
    put(key, FloatArrayTag(value))
  }
  
  /**
   * Sets a double array value at the specified key using a DoubleArrayTag.
   */
  operator fun set(key: String, value: DoubleArray) {
    put(key, DoubleArrayTag(value))
  }
  
  /**
   * Sets a UUID value at the specified key using a UUIDTag.
   */
  operator fun set(key: String, value: UUID) {
    put(key, UUIDTag(value))
  }
  
  /**
   * Tag type for CompoundTag.
   */
  object Type : TagType<CompoundTag>() {
    /**
     * Loads a CompoundTag from the specified [DataInput].
     *
     * @param data The data input stream.
     * @return The loaded CompoundTag.
     */
    override fun load(data: DataInput): CompoundTag {
      val tag = CompoundTag()
      while (true) {
        val id = data.readByte()
        if (id == 0.toByte()) break
        tag.readEntry(TagTypes[id], data.readUTF(), data)
      }
      return tag
    }
  }
}

/**
 * Creates an empty CompoundTag.
 *
 * @return The empty CompoundTag.
 */
fun compoundOf() = CompoundTag()


/**
 * Creates a CompoundTag with the specified entries.
 */
fun compoundOf(vararg entries: Pair<String, Tag>) = CompoundTag(*entries)


/**
 * Creates a CompoundTag building all data with the specified [builder].
 */
@OptIn(ExperimentalContracts::class)
inline fun compound(builder: CompoundTag.() -> Unit): CompoundTag {
  contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }
  return CompoundTag().apply(builder)
}
