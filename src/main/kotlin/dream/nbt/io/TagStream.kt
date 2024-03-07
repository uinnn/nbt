package dream.nbt.io

import dream.nbt.compression.*
import java.io.*

/**
 * Custom DataOutputStream for writing NBT tags.
 *
 * @param stream The underlying output stream.
 */
class TagDataOutputStream(
  stream: OutputStream,
  compressor: TagCompressor = GZIPTagCompressor,
) : DataOutputStream(BufferedOutputStream(compressor.output(stream)))

/**
 * Custom DataInputStream for reading NBT tags.
 *
 * @param stream The underlying input stream.
 */
class TagDataInputStream(
  stream: InputStream,
  compressor: TagCompressor = GZIPTagCompressor
) : DataInputStream(BufferedInputStream(compressor.input(stream)))
