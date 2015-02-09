package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class CommodityKey implements Serializable, Comparable<CommodityKey> {

  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  public CommodityKey() {
  }

  public CommodityKey(String shopCode, String commodityCode) {
    setShopCode(shopCode);
    setCommodityCode(commodityCode);
  }

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * commodityCodeを返します。
   * 
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  public String toString() {
    return "{shopCode:" + getShopCode() + ", commodityCode:" + getCommodityCode() + "}";
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public int compareTo(CommodityKey commodityKey) {
    int s1 = this.getShopCode().compareTo(commodityKey.getShopCode());
    int s2 = this.getCommodityCode().compareTo(commodityKey.getCommodityCode());
    if (s1 != 0) {
      return s1;
    } else {
      return s2;
    }
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof CommodityKey)) {
      return false;
    }
    return this.equals((CommodityKey) object);
  }

  public boolean equals(CommodityKey commodityKey) {
    if (!commodityKey.getShopCode().equals(this.getShopCode())) {
      return false;
    }
    if (!commodityKey.getCommodityCode().equals(this.getCommodityCode())) {
      return false;
    }
    return false;
  }

  public int hashCode() {
    return BeanUtil.generateInstantHashCode(this.getShopCode(), this.getCommodityCode());
  }

  public static SqlFragment buildInClause(String shopCodeColName, String commodityCodeColName, List<CommodityKey> keys) {
    SqlFragment fragment = new SqlFragment();
    List<Object> params = new ArrayList<Object>();
    if (keys != null && keys.size() > 0) {
      StringBuilder builder = new StringBuilder();
      builder.append("(");
      builder.append(shopCodeColName);
      builder.append(", ");
      builder.append(commodityCodeColName);
      builder.append(") IN (");
      for (int i = 0; i < keys.size(); i++) {
        if (i > 0) {
          builder.append(", ");
        }
        builder.append("(?, ?)");
        CommodityKey k = keys.get(i);
        params.add(k.getShopCode());
        params.add(k.getCommodityCode());
      }
      builder.append(")");
      fragment.setFragment(builder.toString());
      fragment.setParameters(params.toArray());
    }
    return fragment;

  }

  public static class ContainerComparator implements Serializable, Comparator<CommodityContainer> {

    private static final long serialVersionUID = 1L;

    private HashMap<String, Integer> keyMap;

    public ContainerComparator() {
      this.keyMap = new HashMap<String, Integer>();
    }

    public ContainerComparator(List<CommodityKey> keyList) {
      // リストに並んでいる順番でアクセスキー(ショップコード/商品コード)と順位を生成し、
      // アクセスキー順位の値にランダムアクセスできるようにしておく
      this.keyMap = new HashMap<String, Integer>();
      int i = 0;
      for (CommodityKey k : keyList) {
        keyMap.put(createKey(k.getShopCode(), k.getCommodityCode()), i++);
      }
    }

    public int compare(CommodityContainer o1, CommodityContainer o2) {
      // ショップコード、商品コードからアクセスキーを生成し、順位値で
      // 並べ替える
      String s1 = createKey(o1.getCommodityHeader());
      String s2 = createKey(o2.getCommodityHeader());
      Integer i1 = BeanUtil.coalesce(keyMap.get(s1), -1);
      Integer i2 = BeanUtil.coalesce(keyMap.get(s2), -1);
      return i1.compareTo(i2);
    }

    private static String createKey(String shopCode, String commodityCode) {
      return StringUtil.coalesce(shopCode, "") + "/" + StringUtil.coalesce(commodityCode, "");
    }

    private static String createKey(CommodityHeader ch) {
      return createKey(ch.getShopCode(), ch.getCommodityCode());
    }
  }
}
