package harmony.nbt.compression

import java.io.*

/**
 * Object representing a TagCompressor using no compression.
 */
object NoneCompressor : TagCompressor {
  override fun input(relative: InputStream): InputStream {
    return relative
  }
  
  override fun output(relative: OutputStream): OutputStream {
    return relative
  }
}
