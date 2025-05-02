package org.clientapp.clientappbackend.service.bloomfilter

import java.math.BigInteger
import java.util.BitSet
import org.clientapp.clientappbackend.interfaces.IBloomFilterStrategy

class DoubleHashingStrategy(
    override val length: Int,
    private val iterations: Int,
    private val nGramLength: Int
) : IBloomFilterStrategy {
  init {
    require(length >= 1) { "Given length is not valid. Expected length is greater than 0." }
    require(iterations >= 1) {
      "Given number of iterations is not valid. Expected number of iterations is greater than 0."
    }
    require(nGramLength >= 1) {
      "Given nGramLength is not valid. Expected nGramLength is greater than 0."
    }
  }

  /**
   * Returns a BitVector that has the given value and salt encoded by double hashing.
   *
   * @param value Value that is encoded (if null, then empty string is used)
   * @param salt Salt that is added to each n-gram (if null, then no salt is used)
   * @return BitSet with the encoded value
   */
  override fun getBitVector(value: String, salt: String): BitSet {
    val vector = BitSet(length)
    val ngrams = getNGrams(value, nGramLength)
    for (ngram in ngrams) {
      for (i in 0 until iterations) {
        vector.set(getHashedPosition(ngram, salt, i))
      }
    }

    return vector
  }

  /**
   * Calculate the position of a given value in bloom filter.
   *
   * @param value Value that is encoded to a specific position in bloom filter (example: value
   *   contains a n-gram)
   * @param salt Salt that is added to the value before it is encoded
   * @param index Index to generate different positions, even if the given value is equal to a
   *   previous iteration
   * @return Position in bloom filter (position is set to one)
   */
  private fun getHashedPosition(value: String, salt: String, index: Int): Int {
    val hash1 = HashFunctionMD5().hash(salt + value)
    val hash2 = HashFunctionSHA1().hash(salt + value)

    val h1 = BigInteger(1, hash1)
    val h2 = BigInteger(1, hash2)

    val bIndex = BigInteger.valueOf(index.toLong())
    val bLength = BigInteger.valueOf(length.toLong())

    return h1.add(bIndex.multiply(h2)).mod(bLength).toInt()
  }
}
