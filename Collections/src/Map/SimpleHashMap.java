package Map;

public class SimpleHashMap<K, V> {

  private int DEFAULT_BUCKET_COUNT = 128;
  private int actSize = 0; 

  private AnEntry<K, V>[] buckets;

  public SimpleHashMap() {
    buckets = new AnEntry[DEFAULT_BUCKET_COUNT];
  }

  public V get(K key) {
    throwIfKeyNull(key);

    AnEntry<K, V> entry = buckets[bucketIndexForKey(key)];
    while (entry != null && !key.equals(entry.getKey()))
      entry = entry.getNext();
    return entry != null ? entry.getValue() : null;
  }
  
  public void put(K key, V value) {
    throwIfKeyNull(key);

    int bucketIndex = bucketIndexForKey(key);
    AnEntry<K, V> entry = buckets[bucketIndex];

    if (null != entry) {
      boolean done = false;
      while (!done) {
        if (key.equals(entry.getKey())) {
          entry.setValue(value);
          done = true;
          actSize++;
        } else if (entry.getNext() == null) {
          entry.setNext(new AnEntry<K, V>(key, value));
          done = true;
          actSize++;
        }
        entry = entry.getNext();
      }
    } else {
      buckets[bucketIndex] = new AnEntry<K, V>(key, value);
      actSize++;
    }
  }

  public int size() {
    return actSize;
  }

  private int bucketIndexForKey(K key) {
    int bucketIndex = key.hashCode() % buckets.length;
    return bucketIndex;
  }

  private void throwIfKeyNull(K key) {
    if (key == null) {
      throw new IllegalArgumentException("key may not be null");
    }
  }
}