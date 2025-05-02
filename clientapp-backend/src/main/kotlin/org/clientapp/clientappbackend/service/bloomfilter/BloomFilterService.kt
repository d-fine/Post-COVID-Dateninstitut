package org.clientapp.clientappbackend.service.bloomfilter

import org.clientapp.clientappbackend.interfaces.IBloomFilterStrategy
import org.springframework.stereotype.Service

@Service
class BloomFilterService {

  fun transformDataMap(
      data: Map<String, Map<String, String>>,
      strategy: IBloomFilterStrategy,
      filterLength: Int,
      salt: String
  ): Map<String, Map<String, String>> {
    return data.mapValues { rowMap ->
      rowMap.value.mapValues { columnEntry ->
        BloomFilter(filterLength, strategy).add(columnEntry.value, salt).toString()
      }
    }
  }
}
