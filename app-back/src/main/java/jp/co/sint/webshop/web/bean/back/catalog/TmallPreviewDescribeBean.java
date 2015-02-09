package jp.co.sint.webshop.web.bean.back.catalog;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040230:カテゴリ－関連付けのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class TmallPreviewDescribeBean extends UIBackBean {

  private static final long serialVersionUID = 1L;

  @Override
  public void setSubJspId() {
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
  }

  @Override
  public String getModuleId() {
    return "U1090009";
  }

  @Override
  public String getModuleName() {
    return "商品描述预览";
  }

}
