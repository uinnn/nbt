package dream.nbt.compression

import java.io.*

/**
 * Interface for a TagCompressor, which defines methods for compressing and decompressing input and output streams.
 */
interface TagCompressor {
  
  /**
   * Constructs the input stream used to compress [relative].
   */
  fun input(relative: InputStream): InputStream
  
  /**
   * Constructs the output stream used to decompress [relative].
   */
  fun output(relative: OutputStream): OutputStream
}
