package jp.co.sint.webshop.code;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.domain.AccountType;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.AdvanceLaterType;
import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.ClientMailType;
import jp.co.sint.webshop.data.domain.CodType;
import jp.co.sint.webshop.data.domain.CommodityJdUseFlg;
import jp.co.sint.webshop.data.domain.CommodityTmallUseFlg;
import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.data.domain.CustomerCancelableFlg;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.CvsEnableType;
import jp.co.sint.webshop.data.domain.DateDistinguish;
import jp.co.sint.webshop.data.domain.DeliveryDateType;
import jp.co.sint.webshop.data.domain.DigitalCashEnableType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.DisplayFlgNew;
import jp.co.sint.webshop.data.domain.EmbeddedHtmlType;
import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.domain.IbObType;
import jp.co.sint.webshop.data.domain.InformationType;
import jp.co.sint.webshop.data.domain.InquiryStatus;
import jp.co.sint.webshop.data.domain.InquiryWay;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.IssueDateNum;
import jp.co.sint.webshop.data.domain.IssuingMode;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.LanguageType;
import jp.co.sint.webshop.data.domain.Limited;
import jp.co.sint.webshop.data.domain.MailContentType;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.PaymentMethodDisplayType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PeriodClass;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.domain.PrimaryFlg;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.data.domain.SearchOrderDateType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.domain.StatisticsType;
import jp.co.sint.webshop.data.domain.StockIOType;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.domain.UseFlagObject;
import jp.co.sint.webshop.service.analysis.DisplayScale;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;

/**
 * JSPのEL式から列挙型を参照するためのユーティリティです。 JSPからgetterメソッドを
 * ${resource.prefectureCode}のような形で参照して使います。
 * このクラスに追加するgetterメソッドはList<CodeAttribute>またはCodeAttribute[]を返すようにしてください。
 * 
 * @author System Integrator Corp.
 */
public final class ConstantResource implements Serializable {

  private static final long serialVersionUID = 1L;

  public ConstantResource() {
  }

  // 20120510 tuxinwei add start
  private List<CodeAttribute> languageCodeList = null;

  public List<CodeAttribute> getlanguageCodeList() {
    languageCodeList = new ArrayList<CodeAttribute>();
    languageCodeList.add(new NameValue(Messages.getString("code.ConstantResource.0"), ""));
    languageCodeList.addAll(Arrays.asList(LanguageCode.values()));
    return languageCodeList;
  }

  // 20120510 tuxinwei add end
  private List<CodeAttribute> prefList1 = null;

  public List<CodeAttribute> getPrefectureCode() {
    if (prefList1 == null) {
      prefList1 = new ArrayList<CodeAttribute>();
      prefList1.add(new NameValue("--------", ""));
      prefList1.addAll(Arrays.asList(PrefectureCode.values()));
    }
    return prefList1;
  }

  private List<CodeAttribute> paymentTypeList = null;

  public List<CodeAttribute> getPaymentTypeList() {
    if (paymentTypeList == null) {
      paymentTypeList = new ArrayList<CodeAttribute>();
      paymentTypeList.add(new NameValue("-----", ""));
      paymentTypeList.addAll(Arrays.asList(PaymentMethodType.values()));
    }
    return paymentTypeList;
  }

  private List<CodeAttribute> accountTypeList = null;

  public List<CodeAttribute> getAccountTypeList() {
    if (accountTypeList == null) {
      accountTypeList = new ArrayList<CodeAttribute>();
      accountTypeList.add(new NameValue("-----", ""));
      accountTypeList.addAll(Arrays.asList(AccountType.values()));
    }
    return accountTypeList;
  }

  private List<CodeAttribute> paymentCommissionTaxTypeList = null;

  public List<CodeAttribute> getPaymentCommissionTaxTypeList() {
    if (paymentCommissionTaxTypeList == null) {
      paymentCommissionTaxTypeList = new ArrayList<CodeAttribute>();
      paymentCommissionTaxTypeList.add(new NameValue("-----", ""));
      paymentCommissionTaxTypeList.addAll(Arrays.asList(TaxType.values()));
    }
    return paymentCommissionTaxTypeList;
  }

  public CodeAttribute[] getSex() {
    return new CodeAttribute[] {
        Sex.MALE, Sex.FEMALE
    };
  }
  
  public CodeAttribute[] getDisplayFlgNew() {
    return new CodeAttribute[] {
        DisplayFlgNew.ONCOUPON, DisplayFlgNew.ISCOUPON
    };
  }
  public CodeAttribute[] getCommodityJdUseFlg() {
    return new CodeAttribute[] {
        CommodityJdUseFlg.ISJDSEFLG,CommodityJdUseFlg.ONJDUSEFLG
    };
  }

  public CodeAttribute[] getCommodityTmallUseFlg() {
    return new CodeAttribute[] {
        CommodityTmallUseFlg.ISTMUSEFLG,CommodityTmallUseFlg.ONTMUSEFLG
    };
  }


  public CodeAttribute[] getStockManagementType() {
    return StockManagementType.values();
  }

  private List<CodeAttribute> enqueteQuestionType;

  public List<CodeAttribute> getEnqueteQuestionType() {
    if (enqueteQuestionType == null) {
      enqueteQuestionType = new ArrayList<CodeAttribute>();
      enqueteQuestionType.add(new NameValue(Messages.getString("code.ConstantResource.0"), ""));
      enqueteQuestionType.addAll(Arrays.asList(EnqueteQuestionType.values()));
    }
    return enqueteQuestionType;
  }

  public CodeAttribute[] getCustomerAttributeType() {
    return CustomerAttributeType.values();
  }

  public CodeAttribute[] getDisplayScale() {
    return DisplayScale.values();
  }

  public CodeAttribute[] getDisplayFlg() {
    return DisplayFlg.values();
  }

  /**
   * 顧客ステータスコードの配列を返します。
   * 
   * @return 顧客ステータスコード
   */
  public CodeAttribute[] getCustomerStatus() {
    return appendFirst(new NameValue(Messages.getString("code.ConstantResource.1"), ""), CustomerStatus.values());
  }

  /**
   * 希望メールタイプの配列を返します。
   * 
   * @return 希望メールタイプ
   */
  public CodeAttribute[] getRequestMailType() {
    return new CodeAttribute[] {
        new NameValue(Messages.getString("code.ConstantResource.2"), ""),
        new NameValue(RequestMailType.UNWANTED.getName(), RequestMailType.UNWANTED.getValue()),
        new NameValue(Messages.getString("code.ConstantResource.3"), "1")
    };
  }

  /**
   * 希望メール書式の配列を返します。
   * 
   * @return 希望メール書式の配列
   */
  public CodeAttribute[] getRequestMailTypeFormat() {
    return new CodeAttribute[] {
        RequestMailType.WANTED, RequestMailType.UNWANTED
    };
  }

  /**
   * ポイント発行種別の配列を返します。
   * 
   * @return ポイント発行種別の配列
   */
  public CodeAttribute[] getPointIssueType() {
    return appendFirst(new NameValue(Messages.getString("code.ConstantResource.4"), ""), PointIssueType.values());
  }

  /**
   * ポイント区分の配列を返します。
   * 
   * @return ポイント区分の配列
   */
  public CodeAttribute[] getPointIssueStatus() {
    return new CodeAttribute[] {
        new NameValue(Messages.getString("code.ConstantResource.5"), ""),
        new NameValue(Messages.getString("code.ConstantResource.6"), "0"),
        new NameValue(Messages.getString("code.ConstantResource.7"), "1"),
        new NameValue(Messages.getString("code.ConstantResource.8"), "2")
    };
  }

  /**
   * ポイント集計対象の配列を返します。<br>
   * ショップ個別モード
   * 
   * @return ポイント集計対象の配列
   */
  public CodeAttribute[] getPointSummaryConditionShop() {
    return new CodeAttribute[] {
        new NameValue(Messages.getString("code.ConstantResource.9"), "0"),
        new NameValue(Messages.getString("code.ConstantResource.10"), "1")
    };
  }

  /**
   * ポイント集計対象の配列を返します。<br>
   * モール一括、または一店舗モード
   * 
   * @return ポイント集計対象の配列
   */
  public CodeAttribute[] getPointSummaryCondition() {
    return new CodeAttribute[] {
        new NameValue(Messages.getString("code.ConstantResource.11"), "0"),
        new NameValue(Messages.getString("code.ConstantResource.12"), "1")
    };
  }

  /**
   * コンビニ決済利用区分の配列を返します。
   * 
   * @return 受注ステータスの配列
   */
  public CodeAttribute[] getCvsEnableType() {
    return CvsEnableType.values();
  }

  /**
   * 電子マネー決済利用区分の配列を返します。
   * 
   * @return 受注ステータスの配列
   */
  public CodeAttribute[] getDigitalCashEnableType() {
    return DigitalCashEnableType.values();
  }

  /**
   * 受注ステータスの配列を返します。
   * 
   * @return 受注ステータスの配列
   */
  public CodeAttribute[] getOrderStatus() {
    // soukai update 2012/01/29 ob start
    // return OrderStatus.values();
    return new CodeAttribute[] {
        OrderStatus.ORDERED, OrderStatus.CANCELLED
    };
    // soukai update 2012/01/29 ob end
  }

  /**
   * 受注用出荷ステータスの配列を返します。
   * 
   * @return 出荷ステータスの配列
   */
  public List<CodeAttribute> getShippingStatusSummaryWithoutCancell() {
    List<CodeAttribute> list = new ArrayList<CodeAttribute>();

    for (ShippingStatusSummary shipping : ShippingStatusSummary.values()) {
      if (shipping.equals(ShippingStatusSummary.CANCELLED)) {
        continue;
      }
      list.add(shipping);
    }

    return list;
  }

  /**
   * 受注用返品ステータスの配列を返します。
   * 
   * @return 返品ステータスの配列
   */
  public CodeAttribute[] getReturnStatusSummary() {
    return ReturnStatusSummary.values();
  }

  private List<CodeAttribute> shippingStatusList = null;

  /**
   * 出荷用出荷ステータスの配列を返します。
   * 
   * @return 出荷ステータスの配列
   */
  public List<CodeAttribute> getShippingStatus() {
    if (shippingStatusList == null) {
      shippingStatusList = new ArrayList<CodeAttribute>();
      shippingStatusList.addAll(Arrays.asList(ShippingStatus.values()));
    }
    return shippingStatusList;
  }

  private static CodeAttribute[] appendFirst(CodeAttribute element, CodeAttribute[] src) {
    CodeAttribute[] dest = new CodeAttribute[src.length + 1];
    dest[0] = element;
    System.arraycopy(src, 0, dest, 1, src.length);
    return dest;
  }

  /**
   * 入出庫区分の配列を返します。
   * 
   * @return 入出庫区分の配列
   */
  public CodeAttribute[] getStockIOType() {
    return StockIOType.values();
  }

  /**
   * レビュースコアの検索条件配列を返します。
   */
  private List<CodeAttribute> reviewScoreList = null;

  public List<CodeAttribute> getReviewScore() {
    if (reviewScoreList == null) {
      reviewScoreList = new ArrayList<CodeAttribute>();
      reviewScoreList.add(new NameValue(Messages.getString("code.ConstantResource.13"), ""));
      reviewScoreList.addAll(Arrays.asList(ReviewScore.values()));
    }
    return reviewScoreList;
  }

  /**
   * デフォルト"--"と00時～23時までのCodeAttributeを返します。
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getTimes() {
    List<CodeAttribute> times = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(":--/0:00/1:01/2:02/3:03/4:04/5:05/6:06/7:07/8:08/9:09/10:10/11:11/12:12/"
        + "13:13/14:14/15:15/16:16/17:17/18:18/19:19/20:20/21:21/22:22/23:23/24:24");
    for (NameValue value : values) {
      times.add(value);
    }
    return times;
  }

  /**
   * デフォルト"--"と1日～31日までのCodeAttributeを返します。
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getDays() {
    List<CodeAttribute> days = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(":--/1:01/2:02/3:03/4:04/5:05/6:06/7:07/8:08/9:09/10:10/"
        + "11:11/12:12/13:13/14:14/15:15/16:16/17:17/18:18/19:19/20:20/"
        + "21:21/22:22/23:23/24:24/25:25/26:26/27:27/28:28/29:29/30:30/31:31");
    for (NameValue value : values) {
      days.add(value);
    }
    return days;
  }

  /**
   * 閉店、開店、両方のCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getShopStatus() {
    List<CodeAttribute> shopStatusList = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(Messages.getString("code.ConstantResource.14"));
    for (NameValue value : values) {
      shopStatusList.add(value);
    }
    return shopStatusList;
  }

  /**
   * ショップステータスラベル(○、×)のCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getShopStatusLabel() {
    List<CodeAttribute> shopStatusLabelList = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(Messages.getString("code.ConstantResource.15"));
    for (NameValue value : values) {
      shopStatusLabelList.add(value);
    }
    return shopStatusLabelList;
  }

  /**
   * 使用有無のCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getEnableFlg() {
    List<CodeAttribute> enableFlg = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(Messages.getString("code.ConstantResource.16"));
    for (NameValue value : values) {
      enableFlg.add(value);
    }
    return enableFlg;
  }

  /**
   * 顧客キャンセルフラグのCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getCustomerCancelableFlg() {
    List<CodeAttribute> customerCancelableFlg = new ArrayList<CodeAttribute>();
    customerCancelableFlg.addAll(Arrays.asList(CustomerCancelableFlg.values()));
    return customerCancelableFlg;
  }

  /**
   * 先払い、後払いのCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getAdvanceLaterFlg() {
    List<CodeAttribute> advanceLaterFlg = new ArrayList<CodeAttribute>();
    advanceLaterFlg.addAll(Arrays.asList(AdvanceLaterType.values()));
    return advanceLaterFlg;
  }

  /**
   * 支払方法表示タイプのCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getPaymentMethodDisplayType() {
    List<CodeAttribute> paymentMthodDisplayType = new ArrayList<CodeAttribute>();
    paymentMthodDisplayType.addAll(Arrays.asList(PaymentMethodDisplayType.values()));
    return paymentMthodDisplayType;
  }

  /**
   * 支払方法のCodeAttributeを返します getPaymentTypeとの違いは、支払不要と全額ポイント払いが無い事です。
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getPaymentMethodType() {
    List<CodeAttribute> paymentMethodType = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(Messages.getString("code.ConstantResource.17"));
    for (NameValue value : values) {
      paymentMethodType.add(value);
    }
    return paymentMethodType;
  }

  /**
   * お知らせ区分のCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getRegisterInformationType() {
    List<CodeAttribute> registerInformationType = new ArrayList<CodeAttribute>();
    for (InformationType type : InformationType.values()) {
      registerInformationType.add(type);
    }
    return registerInformationType;
  }

  /**
   * 曜日名とコード値のリストを返します。
   * 
   * @return 曜日名とコード値のリスト
   */
  public List<CodeAttribute> getDayOfWeekList() {
    List<CodeAttribute> dayOfWeekList = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(Messages.getString("code.ConstantResource.18"));
    for (NameValue value : values) {
      dayOfWeekList.add(value);
    }
    return dayOfWeekList;
  }

  public List<CodeAttribute> getArrivalGoodsType() {
    List<CodeAttribute> arrivalGoodsType = new ArrayList<CodeAttribute>();
    arrivalGoodsType.addAll(Arrays.asList(ArrivalGoodsFlg.values()));
    arrivalGoodsType.add(new NameValue(Messages.getString("code.ConstantResource.19"), "2"));
    return arrivalGoodsType;
  }

  /**
   * 表示端末タイプのCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getDisplayClientType() {
    List<CodeAttribute> displayClientType = new ArrayList<CodeAttribute>();
    displayClientType.addAll(Arrays.asList(DisplayClientType.values()));

    return displayClientType;
  }

  /**
   * メール区分の配列を返します。
   * 
   * @return メール区分
   */
  public List<CodeAttribute> getClientMailType() {
    List<CodeAttribute> clientMailType = new ArrayList<CodeAttribute>();
    clientMailType.add(new NameValue(Messages.getString("code.ConstantResource.20"), ""));
    clientMailType.addAll(Arrays.asList(ClientMailType.values()));
    return clientMailType;
  }

  /**
   * メールコンテントタイプのCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getMailContentType() {
    List<CodeAttribute> mailContentType = new ArrayList<CodeAttribute>();
    mailContentType.addAll(Arrays.asList(MailContentType.values()));
    return mailContentType;
  }

  // add by V10-CH 170 start
  /**
   * 商品一覧表示タイプのCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getModeList() {
    List<CodeAttribute> modeList = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(Messages.getString("code.ConstantResource.21")); //$NON-NLS-1$
    for (NameValue value : values) {
      modeList.add(value);
    }
    return modeList;
  }

  /**
   * 商品一覧キーワード検索方式のCodeAttributeを返します
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getSearchMethodList() {
    List<CodeAttribute> searchMethodList = new ArrayList<CodeAttribute>();
    List<NameValue> values = NameValue.asList(Messages.getString("code.ConstantResource.22")); //$NON-NLS-1$
    for (NameValue value : values) {
      searchMethodList.add(value);
    }
    return searchMethodList;
  }

  /**
   * 咨询状态的CodeAttribute返回
   * 
   * @return CodeAttribute结果
   */
  public List<CodeAttribute> getInquiryStatus() {
    List<CodeAttribute> inquiryStatusList = new ArrayList<CodeAttribute>();
    inquiryStatusList.addAll(Arrays.asList(InquiryStatus.values()));
    return inquiryStatusList;
  }

  /**
   * 咨询途径的CodeAttribute返回
   * 
   * @return CodeAttribute结果
   */
  public List<CodeAttribute> getInquiryWay() {
    List<CodeAttribute> inquiryWayList = new ArrayList<CodeAttribute>();
    inquiryWayList.add(new NameValue(Messages.getString("code.ConstantResource.0"), ""));
    inquiryWayList.addAll(Arrays.asList(InquiryWay.values()));
    return inquiryWayList;
  }

  /**
   * IB/OB区分的CodeAttribute返回
   * 
   * @return CodeAttribute结果
   */
  public List<CodeAttribute> getIbObType() {
    List<CodeAttribute> ibObTypeList = new ArrayList<CodeAttribute>();
    ibObTypeList.addAll(Arrays.asList(IbObType.values()));
    return ibObTypeList;
  }

  /**
   * 商品券状态
   * 
   * @return 商品券状态列表
   */
  public CodeAttribute[] getCouponStatus() {
    return appendFirst(new NameValue(Messages.getString("code.ConstantResource.5"), ""), CouponUsedFlg.values());
  }

  // soukai add ob 2011/12/14 start
  /**
   * 获得所有优惠种别
   * 
   * @return 优惠种别列表
   */
  public List<CodeAttribute> getCampaignType() {
    List<CodeAttribute> campaignTypeList = new ArrayList<CodeAttribute>();
    campaignTypeList.addAll(Arrays.asList(CampaignType.values()));
    return campaignTypeList;
  }

  public List<CodeAttribute> getIssuingMode() {
    List<CodeAttribute> issuingModeList = new ArrayList<CodeAttribute>();
    issuingModeList.addAll(Arrays.asList(IssuingMode.values()));
    return issuingModeList;
  }
  
  public List<CodeAttribute> getUseFlagMode() {
    List<CodeAttribute> UseFlagModeList = new ArrayList<CodeAttribute>();
    UseFlagModeList.addAll(Arrays.asList(UseFlagObject.values()));
    return UseFlagModeList;
  }

  /**
   * 获得所有优惠种别
   * 
   * @return 优惠种别列表
   */
  public List<CodeAttribute> getAllCampaignType() {
    List<CodeAttribute> campaignTypeList = new ArrayList<CodeAttribute>();
    campaignTypeList.add(new NameValue(Messages.getString("code.ConstantResource.5"), ""));
    campaignTypeList.addAll(Arrays.asList(CampaignType.values()));
    return campaignTypeList;
  }

  /**
   * 获得所有重要度区分
   * 
   * @return 重要度区分列表
   */
  public List<CodeAttribute> getPrimaryFlg() {
    List<CodeAttribute> primaryFlgList = new ArrayList<CodeAttribute>();
    primaryFlgList.addAll(Arrays.asList(PrimaryFlg.values()));
    return primaryFlgList;
  }

  public List<CodeAttribute> getCamapaignStatus() {
    List<CodeAttribute> camapaignStatusList = new ArrayList<CodeAttribute>();
    camapaignStatusList.add(new NameValue(Messages.getString("code.ConstantResource.5"), ""));
    camapaignStatusList.addAll(Arrays.asList(ActivityStatus.values()));
    return camapaignStatusList;
  }

  /**
   * COD区分
   * 
   * @return List<CodeAttribute>
   */
  public List<CodeAttribute> getCodTypeList() {
    List<CodeAttribute> codTypeList = new ArrayList<CodeAttribute>();
    codTypeList.add(new NameValue(Messages.getString("code.ConstantResource.0"), ""));
    codTypeList.addAll(Arrays.asList(CodType.values()));
    return codTypeList;
  }

  // /**
  // * 配送希望日指定区分
  // *
  // * @return List<CodeAttribute>
  // */
  // public List<CodeAttribute> getDeliveryDateTypeList() {
  // List<CodeAttribute> deliveryDateTypeList = new ArrayList<CodeAttribute>();
  // deliveryDateTypeList.add(new
  // NameValue(Messages.getString("code.ConstantResource.0"), ""));
  // deliveryDateTypeList.addAll(Arrays.asList(DeliveryDateType.values()));
  // return deliveryDateTypeList;
  // }

  /**
   * 企划分类区分
   * 
   * @return List<NameValue>
   */
  public List<NameValue> getPlanTypeList1() {
    return DIContainer.getPlanDetailTypeValue().getSalePlanDetailTypes();
  }

  /**
   * 企划分类区分
   * 
   * @return List<NameValue>
   */
  public List<NameValue> getPlanTypeList2() {
    return DIContainer.getPlanDetailTypeValue().getFeaturedPlanDetailTypes();
  }

  /**
   * 订单类别区分
   * 
   * @return List<CodeAttribute>
   */
  public List<CodeAttribute> getOrderTypeList() {
    List<CodeAttribute> orderTypeList = new ArrayList<CodeAttribute>();
    orderTypeList.addAll(Arrays.asList(OrderType.values()));
    return orderTypeList;
  }

  /**
   * 移动设备与PC的区分
   * 
   * @return List<CodeAttribute>
   */
  public List<CodeAttribute> getMobileComputerTypeList() {
    List<CodeAttribute> mobileComputerTypeList = new ArrayList<CodeAttribute>();
    mobileComputerTypeList.addAll(Arrays.asList(MobileComputerType.values()));
    return mobileComputerTypeList;
  }

  /**
   * 统计类别区分
   * 
   * @return List<CodeAttribute>
   */
  public List<CodeAttribute> getStatisticsTypeList() {
    List<CodeAttribute> statisticsTypeList = new ArrayList<CodeAttribute>();
    statisticsTypeList.addAll(Arrays.asList(StatisticsType.values()));
    return statisticsTypeList;
  }

  /**
   * 时间段指定区分
   * 
   * @return List<CodeAttribute>
   */
  public List<CodeAttribute> getAppointedTimeTypeList() {
    List<CodeAttribute> appointedTimeTypeList = new ArrayList<CodeAttribute>();
    List<CodeAttribute> tempAppointedTimeTypeList = new ArrayList<CodeAttribute>();
    tempAppointedTimeTypeList.addAll(Arrays.asList(AppointedTimeType.values()));
    for (CodeAttribute appointedTimeType : tempAppointedTimeTypeList) {
      appointedTimeTypeList.add(new NameValue(appointedTimeType.getName(), appointedTimeType.getValue()));
    }
    // appointedTimeTypeList.addAll(Arrays.asList(AppointedTimeType.values()));
    return appointedTimeTypeList;
  }

  // soukai add ob 2011/12/19 end

  // add by V10-CH 170 end

  // 2010/05/18 zhanghaibin Add Start.
  // /**
  // * 积分扩大倍数 。
  // *
  // * @return CodeAttributeされた結果
  // */
  // public List<CodeAttribute> getAmplificationRate() {
  // List<CodeAttribute> amplificationRate = new ArrayList<CodeAttribute>();
  //    List<NameValue> values = NameValue.asList("1:整数/10:小数点后1/100:小数点后2/1000:小数点后3"); //$NON-NLS-1$
  // for (NameValue value : values) {
  // amplificationRate.add(value);
  // }
  // return amplificationRate;
  // }
  // 2010/05/18 zhanghaibin Add End.

  // 20111221 shen add start
  /**
   * 发票类型
   * 
   * @return List<CodeAttribute>
   */
  public List<CodeAttribute> getInvoiceTypeList() {
    List<CodeAttribute> invoiceTypeList = new ArrayList<CodeAttribute>();
    // modify by cs_yuli 20120524 start
    List<CodeAttribute> tempInvoiceTypeList = new ArrayList<CodeAttribute>();
    tempInvoiceTypeList.addAll(Arrays.asList(InvoiceType.values()));
    for (CodeAttribute invoiceType : tempInvoiceTypeList) {
      invoiceTypeList.add(new NameValue(invoiceType.getName(), invoiceType.getValue()));
    }
    // invoiceTypeList.addAll(Arrays.asList(InvoiceType.values()));
    // modify by cs_yuli 20120524 end
    return invoiceTypeList;
  }

  // 20111221 shen add end
  // add by cs_yuli 20120604 start
  /**
   * 配送希望日指定区分CodeAttribute。
   * 
   * @return CodeAttributeされた結果
   */
  public List<CodeAttribute> getDeliveryDateTypeList() {
    List<CodeAttribute> deliveryDateType = new ArrayList<CodeAttribute>();
    List<CodeAttribute> tempDeliveryDateType = new ArrayList<CodeAttribute>();
    tempDeliveryDateType.addAll(Arrays.asList(DeliveryDateType.values()));
    for (CodeAttribute deliverydatetype : tempDeliveryDateType) {
      deliveryDateType.add(new NameValue(deliverydatetype.getName(), deliverydatetype.getValue()));
    }
    return deliveryDateType;
  }

  // add by cs_yuli 20120604 end
  // soukai add 2012/03/27 ob start
  public List<CodeAttribute> getOrderFlgList() {
    List<CodeAttribute> orderFlgList = new ArrayList<CodeAttribute>();
    orderFlgList.add(OrderFlg.NOT_CHECKED);
    orderFlgList.add(OrderFlg.CHECKED);
    orderFlgList.add(OrderFlg.GROUPCHECKED);
    return orderFlgList;
  }

  // soukai add 2012/03/27 ob end
  // 2013.4.22 add zhangzhengtao
  public List<CodeAttribute> getDateDistinguish() {
    List<CodeAttribute> dateDistinguish = new ArrayList<CodeAttribute>();
    for (DateDistinguish type : DateDistinguish.values()) {
      dateDistinguish.add(type);
    }
    return dateDistinguish;
  }

  public List<CodeAttribute> getPeriodClass() {
    List<CodeAttribute> periodClass = new ArrayList<CodeAttribute>();
    for (PeriodClass type : PeriodClass.values()) {
      periodClass.add(type);
    }
    return periodClass;
  }

  public List<CodeAttribute> getLimited() {
    List<CodeAttribute> limited = new ArrayList<CodeAttribute>();
    for (Limited type : Limited.values()) {
      limited.add(type);
    }
    return limited;
  }

  public List<CodeAttribute> getEmbeddedHtmlTypeList() {
    List<CodeAttribute> list = new ArrayList<CodeAttribute>();
    list.addAll(Arrays.asList(EmbeddedHtmlType.values()));
    return list;
  }
  
  private List<CodeAttribute> IssueDateNum1 = null;

  public List<CodeAttribute> getIssueDateNum() {
    if (IssueDateNum1 == null) {
      IssueDateNum1 = new ArrayList<CodeAttribute>();
      IssueDateNum1.add(new NameValue("--------", ""));
      IssueDateNum1.addAll(Arrays.asList(IssueDateNum.values()));
    }
    return prefList1;
  }
  // 2013.4.22 end zhangzhengtao
  
  // 20130729 txw add start
  public List<CodeAttribute> getDiscountStatus() {
    List<CodeAttribute> discountStatusList = new ArrayList<CodeAttribute>();
    discountStatusList.add(new NameValue(Messages.getString("code.ConstantResource.5"), ""));
    discountStatusList.addAll(Arrays.asList(ActivityStatus.values()));
    return discountStatusList;
  }
  // 20130729 txw add end
  // 20140304 txw add start
  public List<CodeAttribute> getOrderTypes() {
    List<CodeAttribute> orderTypeList = new ArrayList<CodeAttribute>();
    orderTypeList.add(new NameValue(OrderType.EC.getName(), OrderType.EC.getValue()));
    orderTypeList.add(new NameValue(OrderType.TMALL.getName(), OrderType.TMALL.getValue()));
    return orderTypeList;
  }
  
  public List<CodeAttribute> getLanguageTypes() {
    List<CodeAttribute> languageTypeList = new ArrayList<CodeAttribute>();
    languageTypeList.addAll(Arrays.asList(LanguageType.values()));
    return languageTypeList;
  }
  // 20140304 txw add end
  
  // 20141009 hdh add start
  public List<CodeAttribute> getSearchOrderDateType() {
    List<CodeAttribute> dateTypeList = new ArrayList<CodeAttribute>();
    dateTypeList.addAll(Arrays.asList(SearchOrderDateType.values()));
    return dateTypeList;
  }
  // 20141009 hdh add end
  
  
  
}
