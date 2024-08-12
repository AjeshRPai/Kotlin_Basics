package collectionsExample

interface LRUCache<T> {
  fun get(key: String): T?
  fun set(key: String, value: T)
}

data class CacheLimits(
  /**
   * @property maxItemsCount
   * Maximum count of items (*inclusive*) that this cache is allowed to contain.
 */
  val maxItemsCount: Int
)


fun <T> createLRUCache(options: CacheLimits): LRUCache<T> {
  return MyLRUCache(options.maxItemsCount)
}

class MyLRUCache<T>(val maxSize: Int): LRUCache<T> {
  private val internalCache: MutableMap<String, T> = object : LinkedHashMap<String, T>(0, 0.75f, true) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, T>?): Boolean {
      return size > maxSize
    }
  }

  fun reset() {
    internalCache.clear()
  }

  override fun get(key: String): T? {
    return internalCache[key]
  }

  override fun set(key: String, value: T) {
    internalCache[key] = value
  }


  fun size(): Long {
    return synchronized(this) {
      val snapshot = LinkedHashMap(internalCache)
      snapshot.size.toLong()
    }
  }



  fun dump() {
    println(internalCache)
  }

}