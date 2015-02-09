package jp.co.sint.webshop.service.catalog;
  
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;

public class CategoryQuery extends AbstractQuery<CategoryInfo> {

  /**
   */
  private static final long serialVersionUID = 1L;
  
  
  public static final String GET_CATEGORY_LIST_QUERY = ""
    + "SELECT A.CATEGORY_CODE, "
    + "       A.CATEGORY_NAME_PC, "
    //20120514 tuxinwei add start
    + "       A.CATEGORY_NAME_PC_EN, "
    + "       A.CATEGORY_NAME_PC_JP, "
    //20120514 tuxinwei add end
    + "       A.CATEGORY_NAME_MOBILE, "
    + "       A.PARENT_CATEGORY_CODE, "
    + "       A.COMMODITY_COUNT_PC, "
    //add by os012 20111221 start 
    //淘宝分类编号
    + "       A.CATEGORY_ID_TMALL, "
    //add by os012 20111221 end
    //2014/4/28 京东WBS对应 ob_李 add start
    + "       A.CATEGORY_ID_JD, "
    //2014/4/28 京东WBS对应 ob_李 add end
    + "       A.META_KEYWORD, "
    + "       A.META_DESCRIPTION, "
    + "       A.COMMODITY_COUNT_MOBILE, "
    + "       A.DISPLAY_ORDER, "
    + "       A.PATH, "
    + "       A.DEPTH, "
    + "       A.UPDATED_DATETIME, "
    + "       A.KEYWORD_JP1, "
    + "       A.KEYWORD_JP2, "
    + "       A.KEYWORD_Cn1, "
    + "       A.KEYWORD_Cn2, "
    + "       A.KEYWORD_En1, "
    + "       A.KEYWORD_En2, "
    // 20130808 txw add start
    + "       A.TITLE, "
    + "       A.TITLE_EN, "
    + "       A.TITLE_JP, "
    + "       A.DESCRIPTION, "
    + "       A.DESCRIPTION_EN, "
    + "       A.DESCRIPTION_JP, "
    + "       A.KEYWORD, "
    + "       A.KEYWORD_EN, "
    + "       A.KEYWORD_JP, "
    // 20130808 txw add end
    + "       COALESCE(B.CATEGORY_ATTRIBUTE_NO, 0) AS CATEGORY_ATTRIBUTE_NO, "
    + "       B.CATEGORY_ATTRIBUTE_NAME, "
    //add by cs_yuli 20120607 start
    + "       B.CATEGORY_ATTRIBUTE_NAME_EN, "
    + "       B.CATEGORY_ATTRIBUTE_NAME_JP, "
    //add by cs_yuli 20120607 end
    + "       B.ORM_ROWID AS ATTRIBUTE_ORM_ROWID, "
    + "       B.UPDATED_DATETIME AS ATTRIBUTE_UPDATED_DATETIME "
    + "  FROM CATEGORY A LEFT JOIN CATEGORY_ATTRIBUTE B "
    + "    ON A.CATEGORY_CODE = B.CATEGORY_CODE "
    + " WHERE DEPTH = ? "
    + " ORDER BY A.PATH, "
    + "          A.DISPLAY_ORDER, "
    + "          A.CATEGORY_CODE, "
    + "          B.CATEGORY_ATTRIBUTE_NO ";
  
  /**
   */

  //add by os012 20111221 start
  public static final String GET_CATEGORY_LIST_TMALL_QUERY = ""
    + "SELECT A.CATEGORY_CODE, "
    + "       A.CATEGORY_NAME_PC, "
    + "       A.CATEGORY_NAME_MOBILE, "
    + "       A.PARENT_CATEGORY_CODE, "
    + "       A.COMMODITY_COUNT_PC, "  
    + "       A.CATEGORY_ID_TMALL, "
    + "       A.COMMODITY_COUNT_MOBILE, "
    + "       A.DISPLAY_ORDER, "
    + "       A.PATH, "
    + "       A.DEPTH, "
    + "       A.UPDATED_DATETIME, "
    + "       COALESCE(B.CATEGORY_ATTRIBUTE_NO, 0) AS CATEGORY_ATTRIBUTE_NO, "   
    + "  FROM CATEGORY A LEFT JOIN TMALL_CATEGORY  B "
    + "    ON A.CATEGORY_ID_TMALL = B.CATEGORY_CODE "
    + " WHERE CATEGORY_ID_TMALL = ? "
    + " ORDER BY A.PATH, "
    + "          A.DISPLAY_ORDER, "
    + "          A.CATEGORY_CODE, " ;

  //add by os012 20111221 end
  /**
   */
  public CategoryQuery createCategoryListQuery(String depth) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT CATEGORY_CODE,CATEGORY_NAME_PC,CATEGORY_NAME_MOBILE,"
        + "                 PARENT_CATEGORY_CODE, COMMODITY_COUNT_PC, " 
        + "                 COMMODITY_COUNT_MOBILE,DISPLAY_ORDER, "
        + "                 PATH,DEPTH,UPDATED_DATETIME " 
        + "          FROM   CATEGORY "
        + "          WHERE  DEPTH = ? ORDER BY PATH, DISPLAY_ORDER, CATEGORY_CODE ");
    params.add(depth);
    setPageSize(getMaxFetchSize());
    setSqlString(builder.toString());
    setParameters(params.toArray());

    return this;
  }

  public Class<CategoryInfo> getRowType() {
    return CategoryInfo.class;
  }
}
