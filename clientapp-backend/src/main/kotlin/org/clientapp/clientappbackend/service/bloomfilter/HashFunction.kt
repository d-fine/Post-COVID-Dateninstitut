package org.clientapp.clientappbackend.service.bloomfilter

import org.apache.commons.codec.digest.DigestUtils
import org.clientapp.clientappbackend.interfaces.IHashFunction

class HashFunctionMD5 : IHashFunction {
  /**
   * Returns the MD5 hash of the given string.
   *
   * @param value string that is hashed
   * @return The MD5 hash of the given string as a byte array
   */
  override fun hash(value: String): ByteArray {
    return DigestUtils.md5(value.toByteArray())
  }
}

class HashFunctionSHA1 : IHashFunction {
  /**
   * Returns the SHA1 hash of the given string.
   *
   * @param value string that is hashed
   * @return The MD5 hash of the given string as a byte array
   */
  override fun hash(value: String): ByteArray {
    return DigestUtils.sha1(value.toByteArray())
  }
}
