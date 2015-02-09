// 10.4.0 X40001 �V�K�쐬
package jp.co.sint.webshop.data.cache;

import java.io.Serializable;
import java.util.Arrays;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchQuery;
import jp.co.sint.webshop.utility.PasswordUtil;

/**
 * �L���b�V���G���g���̃L�[��\���N���X�ł��B
 * 
 * @author System Integrator Corp.
 */
public final class CacheKey implements Comparable<CacheKey>, Serializable {

  private static final long serialVersionUID = 1L;

  private final String id;

  private CacheKey(String id) {
    this.id = PasswordUtil.getDigest(id);
  }

  public static <T>CacheKey create(SearchQuery<T> searchQuery) {
    // SearchQuery����: SQL(SQL������+����) + �y�[�W���O�w��
    return new CacheKey(String.format(
        "sql://%s(%s)[%s]",
        searchQuery.getSqlString(),
        Arrays.toString(searchQuery.getParameters()),
        Arrays.asList(searchQuery.getMaxFetchSize(),
            searchQuery.getPageNumber(),
            searchQuery.getPageSize(),
            searchQuery.getRowType())));
  }
  public static CacheKey create(String identifier) {
    return new CacheKey(identifier);
  }

  public static CacheKey create(Query query) {
    // Query����: SQL(SQL������+����)
    return new CacheKey(String.format("sql://%s(%s)",
        query.getSqlString(), Arrays.toString(query.getParameters())));
  }

  public static CacheKey createWithRowId(Class<? extends GenericDao<?, ?>> daoClass, Long id) {
    // DAO����: DAO�N���X + loadByRowId��z��
    return new CacheKey(String.format("dao://loadByRowId(%d)", id));
  }
  
  public static CacheKey create(Class<? extends GenericDao<?, ?>> daoClass, Object... args) {
    // DAO����: DAO�N���X + ���� (load, loadAll)��z��
    return new CacheKey(String.format("dao://%s(%s)",
        daoClass.getName(), Arrays.toString(args)));
  }

  public static CacheKey create(Class<? extends GenericDao<?, ?>> daoClass, Query query) {
    // DAO����: DAO�N���X + SQL(SQL������+����)
    return new CacheKey(String.format("dao://%s-%s(%s)",
        daoClass.getName(), query.getSqlString(),
        Arrays.toString(query.getParameters())));
  }

  public String getId() {
    return this.id;
  }

  @Override
  public int compareTo(CacheKey that) {
    return this.id.compareTo(that.id);
  }

  @Override
  public String toString() {
    return getId();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    int hash = 0;
    if (id != null) {
      hash = id.hashCode();
    }
    result = prime * result + hash;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CacheKey other = (CacheKey) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

}
