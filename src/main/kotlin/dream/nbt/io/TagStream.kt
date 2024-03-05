package dream.nbt.io

import java.io.*
import java.util.zip.*

/**
 * Custom DataOutputStream for writing NBT tags.
 *
 * @param stream The underlying output stream.
 * @param size The size of the buffer.
 */
class TagDataOutputStream(
  stream: OutputStream,
  size: Int = 1024,
) : DataOutputStream(BufferedOutputStream(GZIPOutputStream(stream, size)))

/**
 * Custom DataInputStream for reading NBT tags.
 *
 * @param stream The underlying input stream.
 */
class TagDataInputStream(
  stream: InputStream,
) : DataInputStream(BufferedInputStream(GZIPInputStream(stream)))
