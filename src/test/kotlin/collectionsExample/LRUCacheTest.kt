package collectionsExample

import org.junit.jupiter.api.Test

class LRUCacheTest{
    @Test
    fun `get should return value for existing key`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 10))
        lruCache.set("foo", "bar")
        kotlin.test.assertEquals("bar", lruCache.get("foo"))
    }

    @Test
    fun `get should return null for non-existent key`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 10))
        lruCache.set("foo", "bar")
        kotlin.test.assertNull(lruCache.get("bar"))
        kotlin.test.assertNull(lruCache.get(""))
    }

    @Test
    fun `get should return value for many existing keys`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 10))
        lruCache.set("foo", "foo")
        lruCache.set("baz", "baz")
        kotlin.test.assertEquals("foo", lruCache.get("foo"))
        kotlin.test.assertEquals("baz", lruCache.get("baz"))
    }

    @Test
    fun `get should return null for key not fitting maxItemsCount`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 1))
        lruCache.set("foo", "bar")
        lruCache.set("baz", "bar")
        kotlin.test.assertNull(lruCache.get("foo"))
        kotlin.test.assertEquals("bar", lruCache.get("baz"))
    }

    @Test
    fun `get should return value for recreated key after it was previously removed`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 1))
        lruCache.set("foo", "bar")
        lruCache.set("baz", "bar")
        lruCache.set("foo", "bar")
        kotlin.test.assertEquals("bar", lruCache.get("foo"))
        kotlin.test.assertNull(lruCache.get("baz"))
    }

    @Test
    fun `set replaces existing value`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 10))
        lruCache.set("key", "firstValue")
        lruCache.set("key", "secondValue")
        kotlin.test.assertEquals(
            expected = "secondValue",
            actual = lruCache.get("key"),
        )
    }

    @Test
    fun `set should remove oldest key on reaching maxItemsCount if no get or has been used`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 1))
        lruCache.set("foo", "bar")
        lruCache.set("baz", "bar")
        kotlin.test.assertEquals(null, lruCache.get("foo"))
        kotlin.test.assertEquals("bar", lruCache.get("baz"))
    }

    @Test
    fun `set should remove least recently used key on reaching maxItemsCount`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 2))
        lruCache.set("foo", "bar")
        lruCache.set("bar", "bar")
        lruCache.get("foo")
        lruCache.set("baz", "bar")

        kotlin.test.assertEquals("bar", lruCache.get("foo"))
        kotlin.test.assertEquals("bar", lruCache.get("foo"))

        kotlin.test.assertNull(lruCache.get("bar"))

        kotlin.test.assertEquals("bar", lruCache.get("baz"))
    }

    @Test
    fun `Item is considered accessed when 'get' is called`() {
        val lruCache = createLRUCache<String>(CacheLimits(maxItemsCount = 2))
        lruCache.set("1key", "1value")
        lruCache.set("2key", "2value")

        lruCache.get("1key")
        lruCache.set("3key", "3value")

        kotlin.test.assertEquals("1value", lruCache.get("1key"))
    }
}