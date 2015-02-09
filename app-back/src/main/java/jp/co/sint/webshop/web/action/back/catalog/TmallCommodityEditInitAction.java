package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.CodeUtil;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditHeaderBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditSkuBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditInitAction extends TmallCommodityEditBaseAction {

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
    TmallCommodityEditBean nextBean = new TmallCommodityEditBean();
    TmallCommodityEditHeaderBean headerBean = new TmallCommodityEditHeaderBean();
    TmallCommodityEditSkuBean skuBean = new TmallCommodityEditSkuBean();
    // 得到参数
    String[] path = getRequestParameter().getPathArgs();
    String code = null;
    if (StringUtil.hasValueAllOf(path)) {
      code = path[0];
      nextBean.setPagePd(false);
    }
    // 获取原商品信息
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = catalogService.getCCommodityInfo("00000000", code);
    if (commodityInfo == null) {
      // 系统错误
      return BackActionResult.RESULT_SUCCESS;
    }

    // 各項目に初期値を設定する
    headerBean.setSaleFlgEc(SaleFlg.DISCONTINUED.getValue());
    // headerBean.setArrivalGoodsFlg(CodeUtil.getDefaultValue(ArrivalGoodsFlg.class).getValue());
    // headerBean.setTaxType(CodeUtil.getDefaultValue(TaxType.class).getValue());
    headerBean.setStockManagementType(CodeUtil.getDefaultValue(StockManagementType.class).getValue());
    headerBean.setStockStatusNo(StringUtil.EMPTY);

    nextBean.setMode(MODE_NEW);

    // 得到值
    if (commodityInfo != null) {
      CCommodityHeader header = commodityInfo.getCheader();
      headerBean = buildBeanByCcheader(header);
      headerBean.setOriginalCommodityCode(header.getCommodityCode());
      skuBean = buildSkuBean(commodityInfo.getCdetail());
      nextBean.setOldCommodityCode(header.getCommodityCode());
    }

    /**
     * 查询类别
     */
    List<TmallCategory> listCategorys = catalogService.loadAllChildCategory();
    nextBean.setListCategorys(listCategorys);

    List<CodeAttribute> warningFlags = new ArrayList<CodeAttribute>();
    warningFlags.add(new NameValue("无", "0"));
    warningFlags.add(new NameValue("A", "1"));
    warningFlags.add(new NameValue("B", "2"));
    warningFlags.add(new NameValue("C", "3"));
    warningFlags.add(new NameValue("D", "4"));
    warningFlags.add(new NameValue("E", "5"));
    nextBean.setWarningFlags(warningFlags);

    // 共通項目を設定する
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // headerBean.setDeliveyTypeName(utilService.getAvailableDeliveryType(login.getShopCode()));
    headerBean.setStockStatusName(utilService.getStockStatusNames(login.getShopCode()));

    // if (headerBean.getDeliveyTypeName().isEmpty()) {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.NO_AVAILABLE_DELIVERY_TYPE_ERROR));
    // }

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
    TmallCommodityEditBean reqBean = (TmallCommodityEditBean) getRequestBean();
    if (!reqBean.isPagePd()) {
      reqBean.setDisplayNextButton(false);
      reqBean.setDisplayRegisterButton(false);
      reqBean.setDisplayUpdateButton(true);
      reqBean.setDisplayCancelButton(false);
    }
    setDisplayControl(reqBean);
    TmallCommodityEditHeaderBean header = reqBean.getHeader();
    header.setSaleFlgEc("0");
    reqBean.setHeader(header);
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
    return "3104022004";
  }

}
