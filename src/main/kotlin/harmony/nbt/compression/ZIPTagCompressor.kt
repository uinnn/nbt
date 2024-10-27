package harmony.nbt.compression

import java.io.*
import java.util.zip.*

/**
 * Object representing a TagCompressor using ZIP compression.
 */
object ZIPTagCompressor : TagCompressor {
  override fun input(relative: InputStream): InputStream {
    return ZipInputStream(relative)
  }
  
  override fun output(relative: OutputStream): OutputStream {
    return ZipOutputStream(relative)
  }
}
