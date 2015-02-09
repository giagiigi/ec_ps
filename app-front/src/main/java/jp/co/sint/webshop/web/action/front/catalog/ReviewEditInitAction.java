package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.CommodityPriceType; 
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewEditBean;
import jp.co.sint.webshop.web.exception.NoSuchCommodityException;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.catalog.CatalogErrorMessage;

/**
 * U2040620:レビュー確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewEditInitAction extends WebFrontAction<ReviewEditBean> {

  private String commodityDescriptionPc="";
  private String commodityName="";
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    // URLパラメータのチェック
    // parameter[0]:ショップコード, parameter[1]:商品コード
    String[] params = getRequestParameter().getPathArgs();
    if (params.length < 1) {
      throw new URLNotFoundException();
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

    // URLパラメータよりショップコード、商品コードを取得
    // parameter[0]:ショップコード, parameter[1]:商品コード
    String[] params = getRequestParameter().getPathArgs();
 
    String shopCode = "00000000";
    String commodityCode = params[0];
    //20111220 os013 add start
    //orderNo等于0表示没有订单号
    String orderNo="0";
    if(params.length>1){
       orderNo = params[1];
    }
    //20111220 os013 add end
    // 商品情報の取得
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    // CommodityHeader commodity = catalogService.getCommodityHeader(shopCode, commodityCode);
    CommodityInfo commodityInfo = catalogService.getCommodityInfo(shopCode, commodityCode);
    
    if (commodityInfo == null || commodityInfo.getHeader() == null) {
      throw new NoSuchCommodityException(shopCode, commodityCode);
    }

    if (getLoginInfo().isNotLogin()) {
      setNextUrl("/app/common/login/init");
      return FrontActionResult.RESULT_SUCCESS;
    }

    // ログイン情報の取得
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo customer = customerService.getCustomer(getLoginInfo().getCustomerCode());

    // nextBeanの生成
    ReviewEditBean nextBean = getBean();
    if(nextBean == null){
      nextBean = new ReviewEditBean();
    }else{
//      if(!commodityCode.equals(nextBean.getCommodityCode()) || !orderNo.equals(nextBean.getOrderNo())){
//        nextBean = new ReviewEditBean();
//      }
 
      if(!orderNo.equals("0001")){
        nextBean = new ReviewEditBean();
      }
    }
    nextBean.setShopCode(commodityInfo.getHeader().getShopCode());
    nextBean.setCommodityCode(commodityInfo.getHeader().getCommodityCode());
    
    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());
	commodityName = utilService.getNameByLanguage(commodityInfo.getHeader().getCommodityName(),commodityInfo.getHeader().getCommodityNameEn(),commodityInfo.getHeader().getCommodityNameJp());
	commodityDescriptionPc=utilService.getNameByLanguage(commodityInfo.getHeader().getCommodityDescriptionMobile(),commodityInfo.getHeader().getCommodityDescriptionMobileEn(),commodityInfo.getHeader().getCommodityDescriptionMobileJp());
    nextBean.setCommodityName(commodityName);
    nextBean.setCommodityDescription(commodityDescriptionPc); 
    nextBean.setSex(String.valueOf(customer.getCustomer().getSex()));
    nextBean.setOrderNo(orderNo);
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    //20111220 os013 update start
    //orderNo有值时表示有订单
    // 顧客的同一商品有没订单的投稿回数
    //if (service.isAlreadyPostReview(commodity.getShopCode(), commodity.getCommodityCode(), getLoginInfo().getCustomerCode())) {
    if (service.isAlreadyPostReview(commodityInfo.getHeader().getShopCode(), commodityInfo.getHeader().getCommodityCode(), getLoginInfo().getCustomerCode(),orderNo)) {
    //20111220 os013 update end
      addErrorMessage(WebMessage.get(CatalogErrorMessage.DUPLICATED_REVIEW_ERROR));

      setRequestBean(nextBean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    ReviewSummary reviewSummary = catalogService.getCommodityReviewSummary(commodityInfo.getHeader().getShopCode(), commodityInfo.getHeader().getCommodityCode());
    if (reviewSummary != null) {
      nextBean.setReviewScore(Long.toString(reviewSummary.getReviewScore()));
    }
    int displayDiscountRateLimit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    Price price = new Price(commodityInfo.getHeader(), commodityInfo.getDetail(), null, taxRate);
    nextBean.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
    if (price.getDiscountRate().intValue() >= displayDiscountRateLimit) {
      nextBean.setDisplayDiscountRate(true);
    } else {
      nextBean.setDisplayDiscountRate(false);
    }
    if(StringUtil.hasValue(commodityInfo.getHeader().getOriginalCommodityCode())){
      nextBean.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(new BigDecimal(commodityInfo.getHeader().getCombinationAmount()))));
      nextBean.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(new BigDecimal(commodityInfo.getHeader().getCombinationAmount()))));
      nextBean.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs().multiply(new BigDecimal(commodityInfo.getHeader().getCombinationAmount()))));
    } else {
      nextBean.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
      nextBean.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
      nextBean.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
    }

    nextBean.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
    nextBean.setCommodityTaxType(NumUtil.toString(commodityInfo.getHeader().getCommodityTaxType()));

    if (price.isSale()) {
      // 通常の販売期間の場合
      nextBean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    } else if (price.isDiscount()) {
      // 特別価格期間の場合
      nextBean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
    } else if (price.isReserved()) {
      // 予約期間の場合
      nextBean.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
    } else {
      nextBean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    }
    
    // 评星判断
    String rs = nextBean.getReviewScoreCondition();
    if(StringUtil.isNullOrEmpty(rs)){
      nextBean.setReviewScoreCondition(ReviewScore.FIVE_STARS.getValue());
    }else{
      if(rs.equals(ReviewScore.FIVE_STARS.getValue())){
        nextBean.setReviewScoreCondition(ReviewScore.FIVE_STARS.getValue());
      }
      if(rs.equals(ReviewScore.FOUR_STARS.getValue())){
        nextBean.setReviewScoreCondition(ReviewScore.FOUR_STARS.getValue());
      }
      if(rs.equals(ReviewScore.THREE_STARS.getValue())){
        nextBean.setReviewScoreCondition(ReviewScore.THREE_STARS.getValue());
      }
      if(rs.equals(ReviewScore.TWO_STARS.getValue())){
        nextBean.setReviewScoreCondition(ReviewScore.TWO_STARS.getValue());
      }
      if(rs.equals(ReviewScore.ONE_STAR.getValue())){
        nextBean.setReviewScoreCondition(ReviewScore.ONE_STAR.getValue());
      }
    }

    nextBean.setDisplayFlg(true);
    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return true;
  }

}
