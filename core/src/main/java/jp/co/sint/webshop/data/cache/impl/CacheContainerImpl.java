// 10.4.0 X40001 �V�K�쐬
package jp.co.sint.webshop.data.cache.impl;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.cache.CacheContainer;
import jp.co.sint.webshop.data.cache.CacheKey;
import jp.co.sint.webshop.data.cache.CacheRetriever;

/**
 * CacheContainer �C���^�t�F�[�X�̃f�t�H���g�����N���X�ł��B
 * 
 * @author System Integrator Corp.
 *
 */
public class CacheContainerImpl implements CacheContainer {

  private static final int DEFAULT_CAPACITY = 1000;

  private static final double DEFAULT_SAFE_LEVEL = 0.8;

  private static final long DEFAULT_TTL = 300L;

  private final Logger logger = Logger.getLogger(this.getClass());

  /** �L���b�V���G���g���̋��e�l */
  // 10.4.1 10644 �C�� ��������
  // private final int capacity;
  private int capacity;
  // 10.4.1 10644 �C�� �����܂�

  // 10.4.1 10644 �C�� ��������
  // private final int watermark;
  private int watermark;
  // 10.4.1 10644 �C�� �����܂�

  // 10.4.1 10644 �C�� ��������
  // private final double safeLevel;
  private double safeLevel;
  // 10.4.1 10644 �C�� �����܂�

  /** �L���b�V���̐�������(�b) */
  // 10.4.1 10644 �C�� ��������
  // private final long ttl;
  private long ttl;
  // 10.4.1 10644 �C�� �����܂�

  /** �L���b�V���@�\�̖�����: ���g�p���Ȃ��ꍇ��true */
  // 10.4.1 10644 �C�� ��������
  // private final boolean disabled;
  private boolean disabled;
  // 10.4.1 10644 �C�� �����܂�

  /** �L���b�V���̃q�b�g�J�E���^ */
  private final AtomicLong hitCount = new AtomicLong(0L);

  /** �L���b�V���̃G���[�J�E���^ */
  private final AtomicLong errCount = new AtomicLong(0L);

  /** �V�������N�C�x���g�̃J�E���g */
  private final AtomicLong shrinkCount = new AtomicLong(0L);

  /** �L�[�ƃf�[�^�I�u�W�F�N�g�̃}�b�v */
  private final Map<String, Object> dataObjectMap;

  /** null �Ƃ��ĕێ����Ă���L�[�̃Z�b�g */
  private final Set<String> nullKeySet = new CopyOnWriteArraySet<String>();

  /** �L�[�Ɛ������Ԃ̃}�b�v */
  private final SortedMap<String, Long> dataTTLMap = new ConcurrentSkipListMap<String, Long>();

  /** �������ԏ��ɕ��ׂ��L�[�̃Z�b�g */
  private final SortedSet<TTLKey> dataTTLSet = new ConcurrentSkipListSet<CacheContainerImpl.TTLKey>();

  public CacheContainerImpl() {
    this(DEFAULT_CAPACITY, DEFAULT_SAFE_LEVEL, DEFAULT_TTL);
  }

  public CacheContainerImpl(final int capacity, final double safeLevel, final long ttl) {
    this.disabled = capacity <= 0;
    this.capacity = capacity;
    this.safeLevel = safeLevel;
    this.ttl = ttl;
    this.watermark = Double.valueOf(this.capacity * this.safeLevel).intValue();
    this.dataObjectMap = new ConcurrentHashMap<String, Object>(this.capacity);
  }

  // 10.4.1 10644 �ǉ� ��������
  /**
   * �ŐV�p�����[�^�����ƂɊ֌W�ݒ���s���܂��B
   */
  public void initContainer() {
    this.disabled = capacity <= 0;
    this.watermark = Double.valueOf(this.capacity * this.safeLevel).intValue();
  }

  /**
   * �L���b�V���f�[�^�}�b�v�����擾���܂��B
   * 
   * @return �L���b�V���f�[�^�}�b�v���
   */
  public Map<String, Object> getDataObjectMap() {
    return this.dataObjectMap;
  }

  /**
   * �L���b�V���֘A�f�[�^�����N���A���܂��B
   */
  public void clearCache() {
    if (this.dataObjectMap != null) {
      this.dataObjectMap.clear();
    }
    this.nullKeySet.clear();
    this.dataTTLMap.clear();
    this.dataTTLSet.clear();
  }

  /**
   * �L���b�V���G���g���̋��e�l���擾���܂��B
   * 
   * @return �L���b�V���G���g���̋��e�l
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * �L���b�V���G���g���̋��e�l��ݒ肵�܂��B
   * 
   * @param capacity
   *          �L���b�V���G���g���̋��e�l
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  /**
   * �L���b�V���N���A���̎c�f�[�^�ێ������擾���܂��B
   * 
   * @return �L���b�V���N���A���̎c�f�[�^�ێ���
   */
  public double getSafeLevel() {
    return safeLevel;
  }

  /**
   * �L���b�V���N���A���̎c�f�[�^�ێ�����ݒ肵�܂��B
   * 
   * @param safeLevel
   *          �L���b�V���N���A���̎c�f�[�^�ێ���
   */
  public void setSafeLevel(double safeLevel) {
    this.safeLevel = safeLevel;
  }

  /**
   * �L���b�V���̐�������(�b)���擾���܂��B
   * 
   * @return �L���b�V���̐�������(�b)
   */
  public long getTtl() {
    return ttl;
  }

  /**
   * �L���b�V���̐�������(�b)��ݒ肵�܂��B
   * 
   * @param ttl
   *          �L���b�V���̐�������(�b)
   */
  public void setTtl(long ttl) {
    this.ttl = ttl;
  }
  // 10.4.1 10644 �ǉ� �����܂�

  @Override
  @SuppressWarnings("unchecked")
  public <V>V get(final CacheKey cacheKey) {
    if (disabled) {
      return null;
    }
    String key = cacheKey.getId();
    V data = (V) dataObjectMap.get(key);
    if (data == null) {
      logger.trace("$$ CACHE NOT FOUND $$");
      errCount.incrementAndGet();
      dataTTLMap.remove(key);
    } else {
      logger.trace("$$ CACHE HIT!! $$");
      if (isExpired(cacheKey)) {
        logger.trace("----> but EXPIRED");
        errCount.incrementAndGet();
        dataObjectMap.remove(key);
        dataTTLMap.remove(key);
        logger.trace("----> Expired cache was removed from dataObjectMap.");
        data = null;
      } else {
        hitCount.incrementAndGet();
        logger.trace("----> and STILL ALIVE");
      }
    }
    return data;
  }

  private boolean isExpired(final CacheKey cacheKey) {
    long now = System.currentTimeMillis();
    Long cacheTtl = dataTTLMap.get(cacheKey.getId());
    if (cacheTtl != null) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
      logger.trace("----> NOW=" + df.format(new Date(now)));
      logger.trace("----> TTL=" + df.format(new Date(cacheTtl)));
    }
    return (cacheTtl == null || cacheTtl < now);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <V>V get(final CacheKey cacheKey, final CacheRetriever<V> callback) {
    if (disabled) {
      return callback.retrieve();
    }
    V data = (V) get(cacheKey);
    if (data == null) {
      // 10.4.1 10633 �C�� ��������
      // if (nullKeySet.contains(cacheKey.getId())) {
      if (nullKeySet.contains(cacheKey.getId()) && isExpired(cacheKey) == false) {
      // 10.4.1 10633 �C�� �����܂�
        logger.trace("----> CACHED AS NULL");
      } else {
        logger.trace("----> RETRIEVE NEWER DATA FROM CALLBACK");
        data = put(cacheKey, callback.retrieve());
        logger.trace("----> DONE");
      }
    }
    showStatistics();
    return data;
  }

  @Override
  public synchronized <V>V put(final CacheKey cacheKey, final V data) {
    if (disabled) {
      return null;
    }
    String key = cacheKey.getId();
    // 10.4.1 10633 �ǉ� ��������
    long expiredAt = System.currentTimeMillis() + (ttl * 1000);
    dataTTLMap.put(key, expiredAt);
    dataTTLSet.add(new TTLKey(key, expiredAt));
    // 10.4.1 10633 �ǉ� �����܂�
    if (data != null) {
      dataObjectMap.put(key, data);
      // 10.4.1 10633 �C�� ��������
      // long expiredAt = System.currentTimeMillis() + (ttl * 1000);
      // dataTTLMap.put(key, expiredAt);
      // dataTTLSet.add(new TTLKey(key, expiredAt));
      nullKeySet.remove(key);
      // 10.4.1 10633 �C�� �����܂�
      shrink();
    } else {
      logger.trace("** DATA NOT FOUND ** ");
      nullKeySet.add(key);
      dataObjectMap.remove(key); // 10.4.1 10633 �ǉ�
    }
    return (V) data;
  }

  private void showStatistics() {
    logger.trace(getStatistics());
  }

  private String printStats(boolean multiLine) {
    StringWriter sw = new StringWriter();
    PrintWriter p = new PrintWriter(sw);
    long hit = hitCount.get();
    long err = errCount.get();
    long shrink = shrinkCount.get();
    int size = dataObjectMap.size();
    double total = Double.valueOf(hit + err);
    double hitRatio = ((double) hit) / total;
    if (multiLine) {
      p.printf("-------------------------------%n");
      p.printf("Cache Container%n");
      p.printf("-------------------------------%n");
      p.printf("Entries  : %20d%n", size);
      p.printf("Hit Count: %20d%n", hit);
      p.printf("Err Count: %20d%n", err);
      p.printf("Hit Ratio: %20f%n", hitRatio);
      p.printf("Shrinks  : %20d%n", shrink);
      p.printf("-------------------------------%n");
      for (TTLKey t : dataTTLSet) {
        p.printf("ttl=%s%n", getTimestamp(t.ttl));
      }
    } else {
      p.printf("----> CACHE stats: entries=%d, hits=%d, errs=%d, hit_ratio=%04f, shrink=%d",
          size, hit, err, hitRatio, shrink);
    }
    return sw.toString();
  }

  public String getInfo() {
    return printStats(true);
  }

  private String getStatistics() {
    return printStats(false);
  }

  private void shrink() {
    int sizeNow = dataObjectMap.size();
    if (sizeNow > this.capacity) {
      long now = System.currentTimeMillis();
      TTLKey first = TTLKey.DEFAULT;
      while (sizeNow > this.watermark && first.ttl < now) {
        first = dataTTLSet.first();
        dataTTLSet.remove(first);
        dataTTLMap.remove(first.key);
        dataObjectMap.remove(first.key);
        nullKeySet.remove(first.key);
        logger.trace("CACHE/removed: id=" + first.key + ", ttl=" + getTimestamp(first.ttl));
        sizeNow--;
      }
      shrinkCount.incrementAndGet();
      showStatistics();
    }

  }

  private String getTimestamp(final long time) {
    return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")).format(new Date(time));
  }

  @Override
  public String toString() {
    return getInfo();
  }

  private static class TTLKey implements Comparable<TTLKey>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final TTLKey DEFAULT = new TTLKey();

    private final String key;

    private final long ttl;

    public TTLKey() {
      this.key = "";
      this.ttl = 0L;
    }

    public TTLKey(final String key, final long ttl) {
      this.key = key;
      this.ttl = ttl;
    }

    @Override
    public int compareTo(final TTLKey that) {
      if (this.ttl == that.ttl) {
        return this.key.compareTo(that.key);
      } else {
        return Long.valueOf(this.ttl).compareTo(that.ttl);
      }
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((key == null) ? 0 : key.hashCode());
      result = prime * result + (int) (ttl ^ (ttl >>> 32));
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      TTLKey other = (TTLKey) obj;
      if (key == null) {
        if (other.key != null) {
          return false;
        }
      } else if (!key.equals(other.key)) {
        return false;
      }
      if (ttl != other.ttl) {
        return false;
      }
      return true;
    }

  }
}
