package dream.nbt.compression

import com.github.luben.zstd.ZstdInputStream
import com.github.luben.zstd.ZstdOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * Object representing a TagCompressor using ZSTD compression.
 */
object ZstdTagCompressor : TagCompressor {
  override fun input(relative: InputStream): InputStream {
    return ZstdInputStream(relative)
  }
  
  override fun output(relative: OutputStream): OutputStream {
    return ZstdOutputStream(relative)
  }
}
