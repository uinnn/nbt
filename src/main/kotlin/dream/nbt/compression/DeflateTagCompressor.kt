package dream.nbt.compression

import java.io.*
import java.util.zip.*

/**
 * Object representing a TagCompressor using Deflate compression.
 */
object DeflateTagCompressor : TagCompressor {
  override fun input(relative: InputStream): InputStream {
    return InflaterInputStream(relative)
  }
  
  override fun output(relative: OutputStream): OutputStream {
    return DeflaterOutputStream(relative)
  }
}
