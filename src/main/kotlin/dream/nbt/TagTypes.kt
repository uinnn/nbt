package dream.nbt

import dream.nbt.arrays.*
import dream.nbt.collections.*
import dream.nbt.primitives.*
import it.unimi.dsi.fastutil.bytes.*

typealias EmptyType = EmptyTag.Type
typealias ByteType = ByteTag.Type
typealias ShortType = ShortTag.Type
typealias IntType = IntTag.Type
typealias LongType = LongTag.Type
typealias FloatType = FloatTag.Type
typealias DoubleType = DoubleTag.Type
typealias ByteArrayType = ByteArrayTag.Type
typealias StringType = StringTag.Type
typealias ListType = ListTag.Type
typealias CompoundType = CompoundTag.Type
typealias IntArrayType = IntArrayTag.Type
typealias LongArrayType = LongArrayTag.Type
typealias CharType = CharTag.Type
typealias BooleanType = BooleanTag.Type
typealias SetType = SetTag.Type
typealias ShortArrayType = ShortArrayTag.Type
typealias FloatArrayType = FloatArrayTag.Type
typealias DoubleArrayType = DoubleArrayTag.Type
typealias CharArrayType = CharArrayTag.Type
typealias BooleanArrayType = BooleanArrayTag.Type
typealias PackedBooleanArrayType = PackedBooleanArrayTag.Type

/**
 * The `TagTypes` object represents a registry for various tag types in Kotlin. It provides methods for
 * accessing, registering, and checking the registration status of different tag types.
 */
object TagTypes {
  
  // The registry to store tag types with their corresponding IDs
  val registry = Byte2ObjectArrayMap<TagType<out Tag>>(22)
  
  // Initialize the registry by registering vanilla tag types
  init {
    registerVanilla()
  }
  
  /**
   * Retrieves the tag type associated with the specified ID.
   *
   * @param id The ID of the tag type.
   * @return The tag type corresponding to the given ID, or [EmptyType] if not found.
   */
  operator fun get(id: Byte): TagType<out Tag> = registry[id] ?: EmptyType
  
  /**
   * Generates the next available ID for a new tag type and increments the registry size.
   *
   * @return The next available ID for a new tag type.
   */
  fun nextId(): Byte = registry.size.toByte()
  
  /**
   * Registers a new tag type in the registry with the next available ID.
   *
   * @param type The tag type to be registered.
   */
  fun registerType(type: TagType<out Tag>) {
    val id = nextId()
    type.id = id
    registry.putIfAbsent(id, type)
  }
  
  /**
   * Checks if a specific tag type is already registered.
   *
   * @param type The tag type to check for registration.
   * @return `true` if the tag type is registered, otherwise `false`.
   */
  fun isRegistered(type: TagType<out Tag>): Boolean {
    return type.id in registry
  }
  
  /**
   * Checks if a tag type with the specified ID is registered.
   *
   * @param id The ID of the tag type to check.
   * @return `true` if a tag type with the given ID is registered, otherwise `false`.
   */
  fun isRegistered(id: Byte): Boolean {
    return id in registry
  }
  
  // Registers vanilla tag types, including pre-1.13 and post-1.13 types, as well as custom types
  private fun registerVanilla() {
    // Order is important for proper initialization
    
    // Pre-1.13
    registerType(EmptyType)
    registerType(ByteType)
    registerType(ShortType)
    registerType(IntType)
    registerType(LongType)
    registerType(FloatType)
    registerType(DoubleType)
    registerType(ByteArrayType)
    registerType(StringType)
    registerType(ListType)
    registerType(CompoundType)
    registerType(IntArrayType)
    
    // Post-1.13
    registerType(LongArrayType)
    
    // Custom types
    registerType(CharType)
    registerType(BooleanType)
    registerType(SetType)
    registerType(ShortArrayType)
    registerType(FloatArrayType)
    registerType(DoubleArrayType)
    registerType(CharArrayType)
    registerType(BooleanArrayType)
    registerType(PackedBooleanArrayType)
  }
}
