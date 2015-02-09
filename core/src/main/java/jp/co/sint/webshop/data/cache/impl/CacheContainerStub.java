// 10.4.0 X40001 V‹Kì¬
package jp.co.sint.webshop.data.cache.impl;

import jp.co.sint.webshop.data.cache.CacheContainer;
import jp.co.sint.webshop.data.cache.CacheKey;
import jp.co.sint.webshop.data.cache.CacheRetriever;

public class CacheContainerStub implements CacheContainer {

  @Override
  public <V>V get(CacheKey key) {
    return null;
  }

  @Override
  public <V>V get(CacheKey key, CacheRetriever<V> callback) {
    return callback.retrieve();
  }

  @Override
  public <V>V put(CacheKey key, V value) {
    return null;
  }

}
