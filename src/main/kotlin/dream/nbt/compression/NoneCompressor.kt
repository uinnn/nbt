package dream.nbt.compression

import java.io.InputStream
import java.io.OutputStream

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
