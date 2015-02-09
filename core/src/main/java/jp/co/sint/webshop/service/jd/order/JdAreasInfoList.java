package jp.co.sint.webshop.service.jd.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.JdArea;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.JdPrefecture;

public class JdAreasInfoList implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 省份直辖市集合
  private List<JdPrefecture> prefecture = new ArrayList<JdPrefecture>();

  // 城市集合
  private List<JdCity> city = new ArrayList<JdCity>();

  // 区县集合
  private List<JdArea> area = new ArrayList<JdArea>();

  public List<JdPrefecture> getPrefecture() {
    return prefecture;
  }

  public void setPrefecture(List<JdPrefecture> prefecture) {
    this.prefecture = prefecture;
  }

  public List<JdCity> getCity() {
    return city;
  }

  public void setCity(List<JdCity> city) {
    this.city = city;
  }

  public List<JdArea> getArea() {
    return area;
  }

  public void setArea(List<JdArea> area) {
    this.area = area;
  }

}
