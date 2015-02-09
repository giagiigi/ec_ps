package jp.co.sint.webshop.service.tmall;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.TmallBrand;
import jp.co.sint.webshop.data.dto.TmallCategory;

public class TmallBrandAndCategoryInfoList {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 淘宝品牌集合
  private List<TmallBrand> tmallBrand = new ArrayList<TmallBrand>();

  // 淘宝品牌集合
  private List<TmallCategory> tmallCategory = new ArrayList<TmallCategory>();

  public List<TmallBrand> getTmallBrand() {
    return tmallBrand;
  }

  public void setTmallBrand(List<TmallBrand> tmallBrand) {
    this.tmallBrand = tmallBrand;
  }

  public List<TmallCategory> getTmallCategory() {
    return tmallCategory;
  }

  public void setTmallCategory(List<TmallCategory> tmallCategory) {
    this.tmallCategory = tmallCategory;
  }

}
