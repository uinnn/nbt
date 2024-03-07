# Named Binary Tag (NBT) Implementation in Kotlin

## Introduction

The following presents an implementation of Named Binary Tag (NBT), drawing inspiration from Minecraft and developed using the Kotlin programming language.

## Features

- **Support for Varied Data Types**: Encompassing both primitive and complex data types.
  
- **Custom Type Creation**: Define and integrate custom data types seamlessly.
  
- **Optimized Memory Utilization**: Minimizing memory footprint for efficient resource usage.
  
- **Compact Design**: A highly condensed structure, facilitating streamlined storage.

- **Multiple Compression**: Various compressions formats. GZIP, ZIP, ZLIB.

## Future features
- **Interoperability**: Ability to interoperate with this NBT implementation to Minecraft NBT
- **Kotlin Serialization**: Implementation of Kotlinx Serialization as NBT

## Minecraft NBT Overview

The NBT system within Minecraft's is characterized by its simplicity, albeit lacking in optimization. Minecraft offers various NBT types, each serving distinct purposes:

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

In addition to the robust set provided by Minecraft, this implementation introduces eight supplementary NBT types, enhancing the versatility of the system:

1. **Char**: Introducing a simple primitive char type.
2. **Boolean**: Departing from Minecraft's approach, utilizing 1 byte for 8 boolean values through a bit-based strategy, optimizing storage efficiency.
3. **Set**: Akin to a List but tailored to exclude duplicates.
4. **ShortArray**: A straightforward primitive short array type.
5. **FloatArray**: Embracing a rudimentary primitive float array type.
6. **DoubleArray**: Enabling a basic primitive double array type.
7. **CharArray**: Incorporating a basic primitive char array type.
8. **BooleanArray**: Adhering to a primitive boolean array type, storing boolean values as 1 boolean per byte.
9. **PackedBooleanArray**: Presenting a primitive boolean array type with a storage approach of 8 booleans per byte.
10. **UUID**: Simple UUID type
11. **24 Bits**: An 24 bits (3 bytes) stored integer.
12. **40 Bits**: An 40 bits (5 bytes) stored long.
13. **48 Bits**: An 48 bits (6 bytes) stored long.
14. **56 Bits**: An 56 bits (7 bytes) stored long.
    
## Advantages Over Minecraft NBT Implementation

### Inline Classes

This NBT implementation leverages inline classes, ensuring minimal memory allocation by design, only resorting to allocation when deemed necessary.

### Buffered Implementation

Optimization is achieved through buffering implementations to ByteArray. This not only enhances overall performance but also synergizes with compression algorithms, culminating in a more efficient and compact representation.

### FastUtil
Usage of fastutil collection types for maximum performance

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
