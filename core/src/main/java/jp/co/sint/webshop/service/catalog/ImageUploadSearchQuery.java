package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class ImageUploadSearchQuery extends AbstractQuery<ImageUploadHistory> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final String LOAD_IMAGE_UPLOAD_HISTORY = "SELECT * FROM IMAGE_UPLOAD_HISTORY";
    

  public ImageUploadSearchQuery() {
    
  }

  public ImageUploadSearchQuery(CommodityImageSearchCondition condition) {
    StringBuilder builder = new StringBuilder(LOAD_IMAGE_UPLOAD_HISTORY);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();
    
    //検索条件:上传时间
    if (StringUtil.hasValueAnyOf(condition.getSearchUploadTime())) {
     String uploadStartTime = condition.getSearchUploadTime() + " 00:00:00";
     String uploadEndTime = condition.getSearchUploadTime() + " 23:59:59";
     
     SqlDialect dialect = SqlDialect.getDefault();
     builder.append(" AND ");
     // 2014/05/06 京东WBS对应 ob_李先超 add start
     builder.append(" ( ");
     SqlFragment jdFragment = dialect.createDateRangeClause("JD_UPLOAD_TIME", DateUtil.fromString(uploadStartTime, true), 
         DateUtil.fromString(uploadEndTime, true));
     // 2014/05/06 京东WBS对应 ob_李先超 add end
     SqlFragment fragment = dialect.createDateRangeClause("UPLOAD_DATETIME", DateUtil.fromString(uploadStartTime, true), 
         DateUtil.fromString(uploadEndTime, true));
     builder.append(fragment.getFragment());
     // 2014/05/06 京东WBS对应 ob_李先超 add start
     builder.append(" OR ");
     builder.append(jdFragment.getFragment());
     builder.append(" ) ");
     params.addAll(Arrays.asList(jdFragment.getParameters()));
     // 2014/05/06 京东WBS对应 ob_李先超 add end
     params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    //検索条件:sku编号list
    String [] skuList = condition.getSkuList();
    if (skuList.length > 0 && !StringUtil.isNullOrEmpty(skuList[0])) {
      builder.append(" AND ");
      for (int i=0; i<skuList.length; i++) {
        builder.append(" SKU_CODE = ? ");
        params.add(skuList[i]);
        
        if (i+1 < skuList.length) {
          builder.append(" OR ");
        }
      }
    }
    
    builder.append(" ORDER BY SKU_CODE");
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  public Class<ImageUploadHistory> getRowType() {
    return ImageUploadHistory.class;
  }

}
