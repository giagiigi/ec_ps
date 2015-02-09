package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean.CompositionDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;


public class SetCommodityCompositionUpdateAction extends SetCommodityCompositionInitAction {

  /**
   * 权限确认。
   * 
   * @return 有删除权限时返回true
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
  }
  
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] param = getRequestParameter().getPathArgs();
    SetCommodityCompositionBean reqBean = getBean();
    CompositionDetail bean = reqBean.getEdit();
    bean.setRetailPrice(getRequestParameter().get("retailPrice" + param[1]));
    bean.setTmallRetailPrice(getRequestParameter().get("tmallRetailPrice" + param[1]));
    bean.setCommodityCode(param[1]);
    boolean flg = validateItems(bean, "retailPrice", "tmallRetailPrice");
    return flg;
  }

  
  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    SetCommodityCompositionBean reqBean = getBean();
    
    CompositionDetail bean = reqBean.getEdit();

    // 添加商品不存在
    CommodityInfo info = catalogService.getCommodityInfo(getLoginInfo().getShopCode(), bean.getCommodityCode());

    // 商品是普通商品
    if (!CommodityType.GENERALGOODS.longValue().equals(info.getHeader().getCommodityType())
        || SetCommodityFlg.OBJECTIN.longValue().equals(info.getHeader().getSetCommodityFlg())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.COMMODITY_TYPE_ERROR));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 明细的合计金额不能超过最大值
    BigDecimal bd = new BigDecimal(bean.getRetailPrice());
    BigDecimal bdTmall = new BigDecimal(bean.getTmallRetailPrice());
    BigDecimal bigBd = BigDecimal.ZERO;
    if (BigDecimalUtil.isAbove(bd, bdTmall)) {
      bigBd = bd;
    } else {
      bigBd = bdTmall;
    }
    CommodityInfo infoHeader = catalogService.getCommodityInfo(getLoginInfo().getShopCode(), reqBean.getCommodityCode());
    BigDecimal price = new BigDecimal(infoHeader.getDetail().getUnitPrice().toString());
    BigDecimal max = new BigDecimal("99999999.99");
    
    if (BigDecimalUtil.add(bigBd, price).compareTo(max) == 1) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.PRICE_NUMBER_OUT_ERROR));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // DB登録用DTOを生成する
    SetCommodityComposition dto = new SetCommodityComposition();
    dto.setShopCode(getLoginInfo().getShopCode());
    dto.setCommodityCode(reqBean.getCommodityCode());
    dto.setChildCommodityCode(bean.getCommodityCode());
    dto.setRetailPrice(bd);
    dto.setTmallRetailPrice(bdTmall);

    // 登録処理
    ServiceResult result = catalogService.updateSetCommodityCompositionInfo(dto, infoHeader.getHeader(), infoHeader.getDetail());
    if (result.hasError()) {
      this.addErrorMessage(WebMessage.get(ActionErrorMessage.REGISTER_FAILED_ERROR, "套餐明细商品:" + reqBean.getSearchCommodityCode()));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }
    reqBean.setUpdatePriceFlg(true);
    super.callService();
    setRequestBean(reqBean);
    //setNextUrl("/app/catalog/set_commodity_composition/init/" + reqBean.getShopCode() + "/" + reqBean.getCommodityCode());
    this.addInformationMessage(WebMessage.get(ActionErrorMessage.REGISTER_SECCESS, "套餐明细商品:" + reqBean.getSearchCommodityCode()));

    return BackActionResult.RESULT_SUCCESS;
  }

  public void prerender() {
    super.prerender();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("套餐价格更新处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019003";
  }

}
