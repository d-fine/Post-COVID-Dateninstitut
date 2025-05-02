package org.clientapp.clientappbackend.interfaces

import java.util.BitSet

interface IBloomFilterStrategy {
  /**
   * Returns a BitSet for the given value.
   *
   * @param value The value for which the BitSet is created.
   * @return The BitSet generated for the value.
   */
  fun getBitVector(value: String): BitSet = getBitVector(value, "")

  /**
   * Returns a BitSet for the given value and salt.
   *
   * @param value The value for which the BitSet is created.
   * @param salt An optional salt to customize the BitSet.
   * @return The BitSet generated for the value and salt.
   */
  fun getBitVector(value: String, salt: String): BitSet

  /** The length of the bloom filter */
  val length: Int
}
