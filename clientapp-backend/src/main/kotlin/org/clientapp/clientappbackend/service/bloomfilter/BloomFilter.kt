package org.clientapp.clientappbackend.service.bloomfilter

import java.util.Base64
import java.util.BitSet
import java.util.Random
import org.clientapp.clientappbackend.interfaces.IBloomFilterStrategy

class BloomFilter(
    private var vector: BitSet,
    private val filterLength: Int,
    private val strategy: IBloomFilterStrategy?
) {

  constructor(
      filterLength: Int,
      strategy: IBloomFilterStrategy?
  ) : this(BitSet(filterLength), filterLength, strategy) {
    require(filterLength >= 1) { "filterLength smaller than 1 is not valid" }
  }

  constructor(vector: String, strategy: IBloomFilterStrategy?) : this(vector.length, strategy) {
    this.vector = stringToBitSet(vector)
  }

  fun add(value: String, salt: String = ""): BloomFilter {
    strategy?.let { vector.or(it.getBitVector(value, salt)) }
    return this
  }

  @Suppress("ReturnCount")
  fun isMember(value: String, salt: String = ""): Boolean {
    if (strategy == null) return false

    val searched = strategy.getBitVector(value, salt)
    for (i in 0 until filterLength) {
      if (searched[i] && !vector[i]) return false
    }
    return true
  }

  fun fold(numberOfFolds: Int): BloomFilter {
    val folds = numberOfFolds + 1
    require(filterLength % folds == 0) {
      "length for folding not valid. length divided filter length not without remainder."
    }

    val length = filterLength / folds
    val vectors = Array(folds) { BitSet(length) }

    var i = 0
    for (ind in vectors.indices) {
      for (j in 0 until length) {
        if (vector[i++]) vectors[ind].set(j)
      }
    }

    for (k in 1 until vectors.size) vectors[0].xor(vectors[k])

    return BloomFilter(vectors[0], length, null)
  }

  fun length(): Int = filterLength

  @Suppress("MagicNumber")
  fun getAsEncodedString(): String {
    val byteLength = kotlin.math.ceil(filterLength / 8.0).toInt()
    val vec = vector.toByteArray()
    val completeVec = ByteArray(byteLength) { i -> vec.getOrElse(i) { 0 } }
    return Base64.getEncoder().encodeToString(completeVec)
  }

  override fun equals(other: Any?): Boolean {
    return when {
      this === other -> true
      other !is BloomFilter -> false
      filterLength != other.filterLength -> false
      else -> vector == other.vector
    }
  }

  override fun hashCode(): Int {
    return 31 * (filterLength.hashCode() + (vector.hashCode() * 31) + (strategy?.hashCode() ?: 0))
  }

  override fun toString(): String {
    return (0 until filterLength).joinToString("") { if (vector[it]) "1" else "0" }
  }

  private fun stringToBitSet(str: String): BitSet {
    require(str.all { it == '0' || it == '1' }) { "String contains invalid character." }
    return BitSet(str.length).apply { str.forEachIndexed { i, c -> if (c == '1') set(i) } }
  }

  /**
   * Returns a balanced bloom filter of this bloom filter. Note that the Balanced bloom filter is
   * twice as long as the original bloom filter! See "Randomized Response and Balanced Bloom Filters
   * for Privacy Preserving Record Linkage" (10.1109/ICDMW.2016.0038) of R. Schnell and C. Borgs for
   * details.
   *
   * @param seed Seed for random generator to permute bits
   * @return Balanced bloom filter
   */
  fun getBalancedVector(seed: Long): BitSet {
    val length = filterLength * 2
    val tmpVector = BitSet(length)

    // Copy original vector and its negated version
    for (i in 0 until filterLength) {
      tmpVector[i] = vector[i % filterLength]
      tmpVector[i + filterLength] = !vector[i]
    }

    val rnd = Random(seed)

    // Randomly shuffle bits
    for (i in 0 until length) {
      val idx = rnd.nextInt(length)
      val tmp = tmpVector[idx]
      tmpVector[idx] = tmpVector[i]
      tmpVector[i] = tmp
    }

    return tmpVector
  }
}
