package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class SetCommodityCompositionRegisterAction extends SetCommodityCompositionInitAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    SetCommodityCompositionBean bean = getBean();
    if (!validateBean(bean)) {
      return false;
    }
    if (StringUtil.isNullOrEmpty(bean.getInputRetailPrice())) {
      addErrorMessage("套餐明细价格不能为空");
      return false;
    }
    if (StringUtil.isNullOrEmpty(bean.getInputTmallRetailPrice())) {
      addErrorMessage("tmall套餐明细价格不能为空");
      return false;
    }
    if (StringUtil.isNullOrEmpty(bean.getSearchCommodityCode())) {
      addErrorMessage("商品编号不能为空");
      return false;
    }
    // 明细商品不能超过15件
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader header = service.getCommodityHeader("00000000", bean.getSearchCommodityCode());
    if (header !=null && StringUtil.hasValue(header.getOriginalCommodityCode())) {
      addErrorMessage("组合品不能设定为套装明细商品");
      setRequestBean(bean);
      return false;
    }
    List<SetCommodityComposition> values = service.getSetCommodityInfo(bean.getShopCode(), bean.getCommodityCode());
    if (values != null && values.size() >= Integer.valueOf(DIContainer.getWebshopConfig().getIndexCompositionLimit())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.NUMBER_OUT_ERROR));
      setRequestBean(bean);
      return false;
    }
    return true;
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

    // 添加商品存在
    CommodityInfo info = catalogService.getCommodityInfo(getLoginInfo().getShopCode(), reqBean.getSearchCommodityCode());
    if (info == null || info.getHeader() == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品:" + reqBean.getSearchCommodityCode()));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 商品是普通商品
    if (!CommodityType.GENERALGOODS.longValue().equals(info.getHeader().getCommodityType())
        || SetCommodityFlg.OBJECTIN.longValue().equals(info.getHeader().getSetCommodityFlg())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.COMMODITY_TYPE_ERROR));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 明细的合计金额不能超过最大值
    BigDecimal bd = new BigDecimal(reqBean.getInputRetailPrice());
    BigDecimal bdTmall = new BigDecimal(reqBean.getInputTmallRetailPrice());
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
    dto.setChildCommodityCode(reqBean.getSearchCommodityCode());
    dto.setRetailPrice(bd);
    dto.setTmallRetailPrice(bdTmall);
//    BigDecimal priceSum = BigDecimalUtil.add(bd, price);
    // 商品header表和明细表更新用DTO做成
//    infoHeader.getHeader().setRepresentSkuUnitPrice(priceSum);
//    infoHeader.getDetail().setUnitPrice(priceSum);

    // 登録処理
    ServiceResult result = catalogService.insertSetCommodityCompositionInfo(dto, infoHeader.getHeader(), infoHeader.getDetail());
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          this.addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_DEFAULT_ERROR, "套餐明细商品:" + reqBean.getSearchCommodityCode()));
          setRequestBean(reqBean);
          return BackActionResult.RESULT_SUCCESS;
        } else {
          this.addErrorMessage(WebMessage.get(ActionErrorMessage.REGISTER_FAILED_ERROR, "套餐明细商品:" + reqBean.getSearchCommodityCode()));
          setRequestBean(reqBean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
    }
    addInformationMessage(WebMessage.get(ActionErrorMessage.REGISTER_SECCESS, "套餐明细商品:" + reqBean.getSearchCommodityCode()));
    setRequestBean(reqBean);
    super.callService();
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("套餐明细登录处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019002";
  }

}
