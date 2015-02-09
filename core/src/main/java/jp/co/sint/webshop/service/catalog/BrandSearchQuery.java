package jp.co.sint.webshop.service.catalog;
  
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.utility.StringUtil;

public class BrandSearchQuery extends AbstractQuery<Brand> {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   *
   */
  public BrandSearchQuery createBrandSearchQuery(BrandSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT A.SHOP_CODE,"
        + "        A.BRAND_CODE,"
        + "        A.BRAND_NAME,"
        + "        A.BRAND_ENGLISH_NAME,"
        //20120515 tuxinwei add start
        + "        A.BRAND_JAPANESE_NAME,"
        //20120515 tuxinwei add end
        + "        A.TMALL_BRAND_CODE,"
        + "        A.TMALL_BRAND_NAME,"
        + "        A.BRAND_NAME_ABBR,"
        + "        A.BRAND_TYPE,"
        + "        A.ORM_ROWID,"
        + "        A.CREATED_USER,"
        + "        A.CREATED_DATETIME,"
        + "        A.UPDATED_USER,"
        + "        A.UPDATED_DATETIME,"
        + "        A.BRAND_DESCRIPTION,"
        + "        A.KEYWORD_JP2,"
        + "        A.KEYWORD_EN2,"
        + "        A.KEYWORD_CN2,"
        //20120524 tuxinwei add start
        + "        A.BRAND_DESCRIPTION_EN,"
        + "        A.BRAND_DESCRIPTION_JP,"
        //20120524 tuxinwei add end
        // 20130808 txw add start
        + "        A.TITLE,"
        + "        A.TITLE_EN,"
        + "        A.TITLE_JP,"
        + "        A.DESCRIPTION,"
        + "        A.DESCRIPTION_EN,"
        + "        A.DESCRIPTION_JP,"
        + "        A.KEYWORD,"
        + "        A.KEYWORD_EN,"
        + "        A.KEYWORD_JP"
        // 20130808 txw add end
        + " FROM   BRAND A    "
        + " WHERE A.SHOP_CODE = ? ");
    params.add(condition.getShopCode());  
    if (StringUtil.hasValue(condition.getSearchBrandCode())) {
      builder.append(" AND A.BRAND_CODE LIKE ? "); 
      params.add("%"+condition.getSearchBrandCode()+"%");  
    }  
    if (StringUtil.hasValue(condition.getSearchBrandName())) {
      builder.append(" AND A.BRAND_NAME LIKE ? ");  
      params.add("%"+condition.getSearchBrandName()+"%");
    }
    if (StringUtil.hasValue(condition.getSearchBrandEnglishName())) {
      builder.append(" AND A.BRAND_ENGLISH_NAME LIKE ? ");  
      params.add("%"+condition.getSearchBrandEnglishName()+"%");
    }
    //20120515 tuxinwei add start
    if (StringUtil.hasValue(condition.getSearchBrandJapaneseName())) {
      builder.append(" AND A.BRAND_JAPANESE_NAME LIKE ? ");  
      params.add("%"+condition.getSearchBrandJapaneseName()+"%");
    }
    //20120515 tuxinwei add end
    builder.append(" ORDER BY A.BRAND_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  public Class<Brand> getRowType() {
    return Brand.class;
  }
}
