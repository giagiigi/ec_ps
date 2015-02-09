package jp.co.sint.webshop.web.action.back.catalog;


import java.text.MessageFormat;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterListBean;
import jp.co.sint.webshop.web.text.back.Messages;
/**
 * tm/jd多商品关联
 */
public class CommodityMasterListInitAction extends WebBackAction<CommodityMasterListBean> {
  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    CommodityMasterListBean bean = new CommodityMasterListBean();
    setBean(bean);
  }

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  
  @Override
  public boolean validate() {
    return true;
  }
  
  @Override
  public WebActionResult callService() {

    CommodityMasterListBean bean = getBean();
    setRequestBean(bean);
    
    String parameter = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }
    if (parameter.equals("deletemaster")) {
      addInformationMessage(MessageFormat.format(Messages
          .getString("web.action.back.catalog.CommodityMasterListDeleteAction.2"),true));
    }
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityMasterListBean bean = (CommodityMasterListBean) getRequestBean();
    setRequestBean(bean);
  }
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.UntrueOrderWordInitAction.0");
  }
  
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102081008";
  }


}
