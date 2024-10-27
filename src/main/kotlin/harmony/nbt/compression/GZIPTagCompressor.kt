package harmony.nbt.compression

import java.io.*
import java.util.zip.*

/**
 * Object representing a TagCompressor using GZIP compression.
 */
object GZIPTagCompressor : TagCompressor {
  override fun input(relative: InputStream): InputStream {
    return GZIPInputStream(relative)
  }
  
  override fun output(relative: OutputStream): OutputStream {
    return GZIPOutputStream(relative)
  }
}

