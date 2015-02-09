package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean.CommodityEditHeaderBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean.CommodityEditSkuBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.file.UploadContents;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditSelectAction extends CommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;

    authorization = Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= getLoginInfo().getShopCode().equals(shopCode);
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    // URLパラメータのチェック
    // 对commodity_edit/select页面商品区分进行状态改变时只有1个参数 所以将3改成1
    // if (getRequestParameter().getPathArgs().length >= 3) {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    // add by tangwh 2012-11-16 start
    // 更新商品套装区分，如当前是对象外，则更新成对象内
    if (getRequestParameter().getPathArgs().length == 1) {
      CommodityEditBean reqBean = getBean();
      List<String> errorList = new ArrayList<String>();
      // TODO 更新套装属性 代码段
      if (reqBean.getHeader().getSetCommodityFlg().equals(SetCommodityFlg.OBJECTOUT.getValue())) {
        ServiceResult sr = catalogService.updateSetCommodityFlg(reqBean.getShopCode(), SetCommodityFlg.OBJECTIN.getValue(), reqBean
            .getCommodityCode());
        if (sr.hasError()) {
          for (ServiceErrorContent error : sr.getServiceErrorList()) {
            if (error == CatalogServiceErrorContent.DELETE_COMMODITY_ERROR) {
              errorList.add(WebMessage.get(CatalogErrorMessage.DELETE_COMMODITY_ERROR, reqBean.getShopCode(),
                  SetCommodityFlg.OBJECTIN.getValue(), reqBean.getCommodityCode()));
            }
          }
        } else {
          reqBean.getHeader().setSetCommodityFlg(SetCommodityFlg.OBJECTIN.getValue());
        }
      } else {
        ServiceResult sr = catalogService.updateSetCommodityFlg(reqBean.getShopCode(), SetCommodityFlg.OBJECTOUT.getValue(),
            reqBean.getCommodityCode());
        if (sr.hasError()) {
          for (ServiceErrorContent error : sr.getServiceErrorList()) {
            if (error == CatalogServiceErrorContent.DELETE_COMMODITY_ERROR) {
              errorList.add(WebMessage.get(CatalogErrorMessage.DELETE_COMMODITY_ERROR, reqBean.getShopCode(),
                  SetCommodityFlg.OBJECTOUT.getValue(), reqBean.getCommodityCode()));
            }
          }
        } else {
          reqBean.getHeader().setSetCommodityFlg(SetCommodityFlg.OBJECTOUT.getValue());
        }
      }
      // 遷移先Beanを設定する
      setRequestBean(reqBean);
      logger.debug("nextUrl: " + getNextUrl());
      setNextUrl("/app/catalog/commodity_edit/select/" + getBean().getShopCode() + "/" + getBean().getCommodityCode() + "/edit/"
          + WebConstantCode.COMPLETE_UPDATE);
      return BackActionResult.RESULT_SUCCESS;
    }
    // add by tangwh 2012-11-16 end
    // nextBeanを生成する
    CommodityEditBean reqBean = new CommodityEditBean();
    CommodityEditHeaderBean headerBean = new CommodityEditHeaderBean();
    CommodityEditSkuBean skuBean = new CommodityEditSkuBean();

    // URLパラメータを取得する
    // パラメータ形式:～/select/【ショップコード】/【商品コード】/【編集モード】[/【完了パラメータ】]
    String[] params = getRequestParameter().getPathArgs();
    String shopCode = params[0];
    String commodityCode = params[1];
    String editMode = params[2];

    CommodityInfo commodityInfo = catalogService.getCommodityInfo(shopCode, commodityCode);

    if (commodityInfo == null || commodityInfo.getHeader() == null || commodityInfo.getDetail() == null) {
      setNextUrl("/app/catalog/commodity_list/init/nodata");
      return BackActionResult.RESULT_SUCCESS;
    }

    CommodityHeader header = commodityInfo.getHeader();
    CommodityDetail detail = commodityInfo.getDetail();
    Stock stock = commodityInfo.getStock();

    headerBean.setSaleFlg(String.valueOf(header.getSaleFlg()));
    headerBean.setImportCommodityType(String.valueOf(header.getImportCommodityType()));
    headerBean.setClearCommodityType(String.valueOf(header.getClearCommodityType()));
    headerBean.setReserveCommodityType1(String.valueOf(header.getReserveCommodityType1()));
    headerBean.setReserveCommodityType2(String.valueOf(header.getReserveCommodityType2()));
    headerBean.setReserveCommodityType3(String.valueOf(header.getReserveCommodityType3()));
    headerBean.setNewReserveCommodityType1(String.valueOf(header.getNewReserveCommodityType1()));
    headerBean.setNewReserveCommodityType2(String.valueOf(header.getNewReserveCommodityType2()));
    headerBean.setNewReserveCommodityType3(String.valueOf(header.getNewReserveCommodityType3()));
    headerBean.setNewReserveCommodityType4(String.valueOf(header.getNewReserveCommodityType4()));
    headerBean.setNewReserveCommodityType5(String.valueOf(header.getNewReserveCommodityType5()));
    headerBean.setCommodityName(header.getCommodityName());
    headerBean.setDisplayClientType(String.valueOf(header.getDisplayClientType()));
    headerBean.setDeliveryTypeNo(NumUtil.toString(header.getDeliveryTypeNo()));
    headerBean.setKeywordCn2(header.getKeywordCn2());
    headerBean.setKeywordEn2(header.getKeywordEn2());
    headerBean.setKeywordJp2(header.getKeywordJp2());
    if (NumUtil.isNull(header.getRecommendCommodityRank()) || header.getRecommendCommodityRank().equals(99999999L)) {
      headerBean.setRecommendCommodityRank(null);
    } else {
      headerBean.setRecommendCommodityRank(NumUtil.toString(header.getRecommendCommodityRank()));
    }
    headerBean.setTaxType(String.valueOf(header.getCommodityTaxType()));
    headerBean.setArrivalGoodsFlg(NumUtil.toString(header.getArrivalGoodsFlg()));
    headerBean.setCommodityPointRate(NumUtil.toString(header.getCommodityPointRate()));
    headerBean.setCommodityPointStartDatetime(DateUtil.toDateTimeString(header.getCommodityPointStartDatetime()));
    headerBean.setCommodityPointEndDatetime(DateUtil.toDateTimeString(header.getCommodityPointEndDatetime()));
    headerBean.setSaleStartDatetime(toDateTimeString(header.getSaleStartDatetime()));
    headerBean.setSaleEndDatetime(toDateTimeString(header.getSaleEndDatetime()));
    headerBean.setDiscountPriceStartDatetime(DateUtil.toDateTimeString(header.getDiscountPriceStartDatetime()));
    headerBean.setDiscountPriceEndDatetime(DateUtil.toDateTimeString(header.getDiscountPriceEndDatetime()));
    // add by tangwh 2012-11-16 start
    headerBean.setCommodityType(NumUtil.toString(header.getCommodityType()));
    if (header.getSetCommodityFlg() != null) {
      headerBean.setSetCommodityFlg(NumUtil.toString(header.getSetCommodityFlg()));
    } else {
      headerBean.setSetCommodityFlg("0");
    }
    // add by tangwh 2012-11-16 end
    if (header.getReservationStartDatetime() != null && header.getReservationStartDatetime().equals(DateUtil.getMin())) {
      headerBean.setReservationStartDatetime(null);
    } else {
      headerBean.setReservationStartDatetime(DateUtil.toDateTimeString(header.getReservationStartDatetime()));
    }

    if (header.getReservationEndDatetime() != null && header.getReservationEndDatetime().equals(DateUtil.getMin())) {
      headerBean.setReservationEndDatetime(null);
    } else {
      headerBean.setReservationEndDatetime(DateUtil.toDateTimeString(header.getReservationEndDatetime()));
    }

    headerBean.setSalePriceChangeDatetime(DateUtil.toDateString(header.getChangeUnitPriceDatetime()));
    headerBean.setStockManagementType(NumUtil.toString(header.getStockManagementType()));
    headerBean.setStockStatusNo(NumUtil.toString(header.getStockStatusNo()));
    headerBean.setCommodityDescription(header.getCommodityDescriptionPc());
    headerBean.setCommodityDescriptionShort(header.getCommodityDescriptionMobile());
    // add by cs_yuli 20120514 start
    headerBean.setCommodityDescriptionEn(header.getCommodityDescriptionPcEn());
    headerBean.setCommodityDescriptionShortEn(header.getCommodityDescriptionMobileEn());
    headerBean.setCommodityDescriptionJp(header.getCommodityDescriptionPcJp());
    headerBean.setCommodityDescriptionShortJp(header.getCommodityDescriptionMobileJp());
    // add by cs_yuli 20120514 end
    headerBean.setCommoditySearchWords(header.getCommoditySearchWords());
    headerBean.setLinkUrl(header.getLinkUrl());
    // 20120109 ysy add start
    headerBean.setCommodityNameEn(header.getCommodityNameEn());
    headerBean.setCommodityNameJp(header.getCommodityNameJp());
    headerBean.setBrandCode(header.getBrandCode());
    // 20120119 ysy add start
    Brand brandName = catalogService.getBrand(shopCode, header.getBrandCode());
    if (brandName != null) {
      headerBean.setBrand(brandName.getBrandName());
    }
    headerBean.setWarningFlag(header.getWarningFlag());
    headerBean.setReturnFlag(NumUtil.toString(header.getReturnFlg()));
    headerBean.setLeadTime(NumUtil.toString(header.getLeadTime()));
    headerBean.setSaleFlag(header.getSaleFlag());
    headerBean.setSpecFlag(header.getSpecFlag());
    // 20120109 ysy add end
    if (header.getHotFlgEn() != null) {
      headerBean.setHotFlgEn(header.getHotFlgEn().toString());
    } else {
      headerBean.setHotFlgEn("");
    }
    if (header.getHotFlgJp() != null) {
      headerBean.setHotFlgJp(header.getHotFlgJp().toString());
    } else {
      headerBean.setHotFlgJp("");
    }
    // 20130703 txw add start
    headerBean.setTitle(header.getTitle());
    headerBean.setTitleEn(header.getTitleEn());
    headerBean.setTitleJp(header.getTitleJp());
    headerBean.setDescription(header.getDescription());
    headerBean.setDescriptionEn(header.getDescriptionEn());
    headerBean.setDescriptionJp(header.getDescriptionJp());
    headerBean.setKeyword(header.getKeyword());
    headerBean.setKeywordEn(header.getKeywordEn());
    headerBean.setKeywordJp(header.getKeywordJp());
    // 20130703 txw add end

    skuBean.setUnitPrice(NumUtil.toString(detail.getUnitPrice()));
    skuBean.setDiscountPrice(NumUtil.toString(detail.getDiscountPrice()));
    skuBean.setReservationPrice(NumUtil.toString(detail.getReservationPrice()));
    skuBean.setChangeUnitPrice(NumUtil.toString(detail.getChangeUnitPrice()));
    skuBean.setJanCode(detail.getJanCode());
    skuBean.setReservationLimit(NumUtil.toString(stock.getReservationLimit()));
    skuBean.setOneshotReservationLimit(NumUtil.toString(stock.getOneshotReservationLimit()));

    if (!NumUtil.isNull(stock.getStockThreshold()) && stock.getStockThreshold() == 0) {
      skuBean.setStockThreshold(null);
    } else {
      skuBean.setStockThreshold(NumUtil.toString(stock.getStockThreshold()));
    }

    boolean faultStandardName1 = false;
    boolean faultStandardName2 = false;
    List<CommodityDetail> skuList = catalogService.getCommoditySku(header.getShopCode(), header.getCommodityCode());
    if (StringUtil.hasValueAllOf(header.getCommodityStandard1Name(), header.getCommodityStandard2Name())) {
      for (CommodityDetail sku : skuList) {
        if (StringUtil.isNullOrEmpty(sku.getStandardDetail1Name())) {
          faultStandardName1 = true;
        }
        if (StringUtil.isNullOrEmpty(sku.getStandardDetail2Name())) {
          faultStandardName2 = true;
        }

      }
    } else if (StringUtil.hasValue(header.getCommodityStandard1Name())) {
      for (CommodityDetail sku : skuList) {
        if (StringUtil.isNullOrEmpty(sku.getStandardDetail1Name())) {
          faultStandardName1 = true;
        }
      }
    }
    if (faultStandardName1) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.FAULT_STANDARD_NAME_SET_ERROR, header.getCommodityStandard1Name()));
    }
    if (faultStandardName2) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.FAULT_STANDARD_NAME_SET_ERROR, header.getCommodityStandard2Name()));
    }

    if (editMode.equals("edit")) {
      reqBean.setCommodityCode(header.getCommodityCode());
      reqBean.setMode(MODE_UPDATE);

      headerBean.setUpdateDatetime(header.getUpdatedDatetime());

      skuBean.setRepresentSkuCode(detail.getSkuCode());
      skuBean.setUpdateDatetime(detail.getUpdatedDatetime());

      ContentsSearchCondition condition = new ContentsSearchCondition();
      condition.setContentsType(ContentsType.IMAGE_DATA_SHOP_COMMODITY);
      condition.setShopCode(getLoginInfo().getShopCode());
      condition.setCommodityCode(reqBean.getCommodityCode());
      condition.setSkuCode(skuBean.getRepresentSkuCode());
    } else {
      reqBean.setMode(MODE_NEW);
    }

    // 共通項目を設定する
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    headerBean.setDeliveyTypeName(utilService.getAvailableDeliveryType(commodityInfo.getHeader().getShopCode()));
    headerBean.setStockStatusName(utilService.getStockStatusNames(commodityInfo.getHeader().getShopCode()));

    reqBean.setShopCode(commodityInfo.getHeader().getShopCode());
    reqBean.setHeader(headerBean);
    reqBean.setSku(skuBean);

    // 商品画像、サムネイル画像、代表SKU画像のコード値を設定する
    List<CodeAttribute> imageList = new ArrayList<CodeAttribute>();
    // 10.1.2 10071 修正 ここから
    // imageList.add(new NameValue("商品画像(PC・携帯共通)", reqBean.getCommodityCode() +
    // "/" + UploadContents.COMMODITY_IMAGE));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.0"), reqBean
        .getCommodityCode()
        + "/" + UploadContents.COMMODITY_IMAGE));
    // 10.1.2 10071 修正 ここまで
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.1"), reqBean
        .getCommodityCode()
        + "/" + UploadContents.COMMODITY_THUMBNAIL_PC));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.2"), reqBean
        .getCommodityCode()
        + "/" + UploadContents.COMMODITY_THUMBNAIL_MOBILE));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.3"), reqBean.getSku()
        .getRepresentSkuCode()
        + "/" + UploadContents.SKU_IMAGE_PC));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.4"), reqBean.getSku()
        .getRepresentSkuCode()
        + "/" + UploadContents.SKU_IMAGE_MOBILE));
    reqBean.setImageList(imageList);

    // 遷移先Beanを設定する
    setRequestBean(reqBean);

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
    // add by twh 2012-12-4 17:16:27 start
    // 套装商品信息查看时页面显示的格式设置
    if (reqBean.getHeader().getSetCommodityFlg().equals("1")) {
      reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_READONLY);
      reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_READONLY);
      reqBean.setDisplayNextButton(true);
      reqBean.setDisplaySuitCommodity(true);
    }
    // add by twh 2012-12-4 17:16:27 end
    setCompleteMessage();

    setRequestBean(reqBean);

  }

  /**
   * 完了メッセージを設定する
   * 
   * @param complete
   * @param moduleName
   */
  private void setCompleteMessage() {

    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length >= 4) {
      complete = params[3];
    }

    if (complete.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.back.catalog.CommodityEditSelectAction.5")));
    } else if (complete.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommodityEditSelectAction.5")));
    } else if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommodityEditSelectAction.6")));
    } else if (complete.equals(WebConstantCode.COMPLETE_UPLOAD)) {

      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      if (messageBean != null) {
        if (messageBean.getResult().equals(ResultType.SUCCESS)) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPLOAD_COMPLETE, Messages
              .getString("web.action.back.catalog.CommodityEditSelectAction.6")));
        } else {
          addErrorMessage(WebMessage.get(ActionErrorMessage.UPLOAD_FAILED, Messages
              .getString("web.action.back.catalog.CommodityEditSelectAction.6")));
        }

        for (UploadResult ur : messageBean.getUploadDetailList()) {
          for (String s : ur.getInformationMessage()) {
            addInformationMessage(s);
          }
          for (String s : ur.getWarningMessage()) {
            addWarningMessage(s);
          }
          for (String s : ur.getErrorMessage()) {
            addErrorMessage(s);
          }
        }
      }

    }
  }

  /**
   * Date型の値をStringに変換する。<BR>
   * システム最大(or最小)日時が設定されている場合は、<BR>
   * 未入力と判断し、""(空文字)を設定する
   * 
   * @param date
   * @return dateString
   */
  private String toDateTimeString(Date date) {
    String dateString = null;

    try {
      if (date.equals(DateUtil.getMin()) || date.equals(DateUtil.getMax())) {
        return "";
      }

      dateString = DateUtil.toDateTimeString(date);
    } catch (NullPointerException e) {
      dateString = "";
    }
    return dateString;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditSelectAction.7");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012008";
  }

}
