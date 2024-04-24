# Named Binary Tag (NBT) Implementation in Kotlin

## Introduction

Enhanced Named Binary Tag implementation writen in Kotlin.

## Features

- **Support for Varied Data Types**: Primitive and complex types.
  
- **Custom Type Creation**: Easily create new types as you need.
  
- **Optimized Memory Utilization**: Uses specialized types to decrease memory usage.
  
- **Compact Design**: This is designed to be simple, so, is simple.

- **Multiple Compression**: Various compressions formats. GZIP, ZIP, ZLIB.

   **Kotlin Serialization**: Implementation of Kotlinx Serialization as NBT

## Future features
- **Interoperability**: Ability to interoperate with this NBT implementation to Minecraft NBT

## Minecraft NBT Overview

The Minecraft NBT is very nice for their use, perhaps some bad things, it fit in their purpose, but we can always made things better!
Minecraft NBT overview:

1. **Empty**
2. **Byte**
3. **Short**
4. **Int**
5. **Long**
6. **Float**
7. **Double**
8. **ByteArray**
9. **String**
10. **List**
11. **Compound**
12. **IntArray**
13. **LongArray** - Introduced in version 1.13+

## Extended NBT Types

In addition to these previous NBT provided by Minecraft, this implementation introduces 14 new types:

1. **Char**: Simple Char.
2. **Boolean**: Minecraft uses a Byte Tag to store boolean, in this imlpementation, we use a Byte too, but, with support to get the 8 bits of the byte, making possible to store 8 boolean in one Boolean Tag. Optimizing this way 8x more memory
3. **Set**: Equals to List, but without duplicates, of course.
4. **ShortArray**: Simple ShortArray.
5. **FloatArray**: Simple FloatArray.
6. **DoubleArray**: Simple DoubleArray.
7. **CharArray**: Simple CharArray.
8. **BooleanArray**: Default BooleanArray Type. Stores 1 boolean per byte
9. **PackedBooleanArray**: Packed BooleanArray Type. Stores up to 8 boolean per byte, similar to BitSet
10. **UUID**: Simple UUID.
11. **24 Bits**: An 24 bits (3 bytes) stored integer.
12. **40 Bits**: An 40 bits (5 bytes) stored long.
13. **48 Bits**: An 48 bits (6 bytes) stored long.
14. **56 Bits**: An 56 bits (7 bytes) stored long.
    
## Advantages Over Minecraft NBT Implementation

### Inline Classes

We use Kotlin inline classes, so no more extra memory allocation.

### Buffered Implementation

Arrays Type (ShortArray, IntArray, LongArray, etc...) is firstly buffered to ByteArray and them serialized, this enhances overall performance and makes the compression algorithms better.

### FastUtil

We use FastUtil collection types, enhancing more the performance.

---
## Usage
Simple overall usage
```kt
  val compound = compound {
    set("Name", "Test")
    set("Version", "1.12")
    set("Patch", 7)
    // multiple booleans
    set("Available", true, false, false)
    set("Authors", tagListOf("uin", "other"))
  }
  
  // write
  TagIO.write(stream, compound)
  TagIO.write(file, compound)
  
  // read
  val loaded = TagIO.read(stream)
  val loaded = TagIO.read(file)
  
  val name = compound.string("Name")
  val version = compound.string("Version", default = "1.0")
  val authors = compound.stringList("Authors")

  // getting multiple booleans
  val available = compound.booleanTag("Available")
  val availableForWindows = available[0]
  val availableForLinux = available[1]
  val availableForMac = available[2]

  // usage with Kotlinx Serialization
  @Serializable
  data class A(val value: Int, val value2: String)
  
  @Serializable
  data class B(val a: A, val b: A)
  
  val a = A(1, "a")
  val b = A(2, "b")
  
  val encoded = TagIO.encodeToBytes(B(a, b))
  val decoded = TagIO.decodeFromBytes<B>(encoded)
  
  println(decoded) // B(a=A(value=1, value2="a"), b=A(value=2, value2="b"))
  
  // other encode functions
  TagIO.encodeToStream(stream, a)
  TagIO.encodeToFile(file, a)
```

## Creating a custom type
In this example we gonna create a UUID NBT type!
```kt
// don't forget to set as inline class!
@JvmInline
value class UUIDTag(val value: UUID) : Tag {
  
  // this allows we get any value in the tag if we don't know the type
  override val genericValue get() = value
  
  // the type of this tag
  override val type get() = Type
  
  // the write function is used to save tag value to data output
  override fun write(data: DataOutput) {
    data.writeLong(value.mostSignificantBits)
    data.writeLong(value.leastSignificantBits)
  }
  
  // creates a copy of the tag, in some cases this can be useful
  // but not for this example
  override fun copy(): UUIDTag = UUIDTag(value)
  
  override fun toString() = value.toString()
  
  // we also need to create a type to loading them back.
  object Type : TagType<UUIDTag>() {
    override fun load(data: DataInput): UUIDTag {
      return UUIDTag(UUID(data.readLong(), data.readLong()))
    }
  }
}

// registering the type
TagTypes.registerType(UUIDTag.Type)
```
