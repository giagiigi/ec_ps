package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.code.CodeUtil;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean.CommodityEditHeaderBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean.CommodityEditSkuBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;

import org.apache.log4j.Logger;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditInitAction extends CommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());

    // ログイン情報の取得
    BackLoginInfo login = getLoginInfo();

    // nextBeanを生成する
    CommodityEditBean nextBean = new CommodityEditBean();
    CommodityEditHeaderBean headerBean = new CommodityEditHeaderBean();
    CommodityEditSkuBean skuBean = new CommodityEditSkuBean();

    // 各項目に初期値を設定する
    headerBean.setSaleFlg(SaleFlg.DISCONTINUED.getValue());
    headerBean.setDisplayClientType(CodeUtil.getDefaultValue(DisplayClientType.class).getValue());
    headerBean.setArrivalGoodsFlg(CodeUtil.getDefaultValue(ArrivalGoodsFlg.class).getValue());
    headerBean.setTaxType(CodeUtil.getDefaultValue(TaxType.class).getValue());
    headerBean.setStockManagementType(CodeUtil.getDefaultValue(StockManagementType.class).getValue());
    headerBean.setStockStatusNo(StringUtil.EMPTY);

    nextBean.setMode(MODE_NEW);

    // 共通項目を設定する
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    headerBean.setDeliveyTypeName(utilService.getAvailableDeliveryType(login.getShopCode()));
    headerBean.setStockStatusName(utilService.getStockStatusNames(login.getShopCode()));
    
    if (headerBean.getDeliveyTypeName().isEmpty()) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.NO_AVAILABLE_DELIVERY_TYPE_ERROR));
    }

    nextBean.setHeader(headerBean);
    nextBean.setSku(skuBean);
    nextBean.setShopCode(login.getShopCode());
    
    setRequestBean(nextBean);

    logger.debug("nextUrl: " + getNextUrl());
    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityEditBean reqBean = (CommodityEditBean) getRequestBean();

    setDisplayControl(reqBean);

    //add by twh 2012-12-4 17:16:27 start
    //套装商品登录时页面显示的格式设置
    reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
    reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
    reqBean.setDisplayNextButton(true);
    reqBean.setDisplaySuitCommodity(false);
    //add by twh 2012-12-4 17:16:27 end
    setRequestBean(reqBean);

  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012004";
  }

}
