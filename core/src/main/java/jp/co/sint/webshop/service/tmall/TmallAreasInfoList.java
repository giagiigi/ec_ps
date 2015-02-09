package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.Prefecture;

public class TmallAreasInfoList implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 省份直辖市集合
  private List<Prefecture> prefecture = new ArrayList<Prefecture>();

  // 城市集合
  private List<City> city = new ArrayList<City>();

  // 区县集合
  private List<Area> area = new ArrayList<Area>();

  public List<Prefecture> getPrefecture() {
    return prefecture;
  }

  public void setPrefecture(List<Prefecture> prefecture) {
    this.prefecture = prefecture;
  }

  public List<City> getCity() {
    return city;
  }

  public void setCity(List<City> city) {
    this.city = city;
  }

  public List<Area> getArea() {
    return area;
  }

  public void setArea(List<Area> area) {
    this.area = area;
  }

}
