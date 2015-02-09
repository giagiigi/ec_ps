package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class Price implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private Campaign campaignInfo;

  private Long taxRate;

  private CommodityHeader header;

  private CommodityDetail detail;

  public Price() {

  }

  public Price(CommodityHeader header, CommodityDetail detail, Campaign campaign, Long taxRate) {
    this.header = header;
    this.detail = detail;
    setTaxRate(taxRate);
    setCampaignInfo(campaign);
  }

  /**
   * @deprecated Priceクラス内でのCatalogService使用箇所はdeprecatedにします。
   */
  public Price(CommodityHeader header, CommodityDetail detail) {
    this.header = header;
    this.detail = detail;

    // 消費税取得
    TaxUtil u = DIContainer.get("TaxUtil");
    this.taxRate = u.getTaxRate();

    // キャンペーンの取得
    CatalogService catalogSvc = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    this.campaignInfo = catalogSvc.getAppliedCampaignBySku(header.getShopCode(), detail.getSkuCode());
  }

  /**
   * @return the shopCode
   */
  private String getShopCode() {
    return header.getShopCode();
  }

  /**
   * @return the campaignInfo
   */
  public Campaign getCampaignInfo() {
    return campaignInfo;
  }

  /**
   * @param campaignInfo
   *          the campaignInfo to set
   */
  public void setCampaignInfo(Campaign campaignInfo) {
    this.campaignInfo = campaignInfo;
  }

  /**
   * @return the taxRate
   */
  public Long getTaxRate() {
    return taxRate;
  }

  /**
   * @param taxRate
   *          the taxRate to set
   */
  public void setTaxRate(Long taxRate) {
    this.taxRate = taxRate;
  }

  /**
   * 特別価格適用期間中であればtrue;
   */
  public boolean isDiscount() {
    if (header.getDiscountPriceStartDatetime() == null && header.getDiscountPriceEndDatetime() == null) {
      return false;
    } else {
      return DateUtil.isPeriodDate(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime());
    }
  }

  /**
   * 予約価格適用期間中であればtrue;
   */
  public boolean isReserved() {
    if (header.getReservationStartDatetime() == null && header.getReservationEndDatetime() == null) {
      return false;
    } else {
      return DateUtil.isPeriodDate(header.getReservationStartDatetime(), header.getReservationEndDatetime());
    }
  }

  /**
   * 販売価格適用期間中であればtrue;
   */
  public boolean isSale() {
    if (this.isDiscount() || this.isReserved()) {
      return false;
    } else {
      return DateUtil.isPeriodDate(header.getSaleStartDatetime(), header.getSaleEndDatetime());
    }
  }

  /**
   * キャンペーン適用期間中であればtrue;
   */
  public boolean isCampaign() {
    return (this.getCampaignInfo() != null);
  }

  /**
   * 商品別ポイント付与期間であればtrue;
   */
  public boolean isPoint() {
    if (header.getCommodityPointStartDatetime() == null && header.getCommodityPointEndDatetime() == null) {
      return false;
    } else {
      return DateUtil.isPeriodDate(header.getCommodityPointStartDatetime(), header.getCommodityPointEndDatetime());
    }

  }

  /**
   * キャンペーンを設定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>キャンペーンの有効期限に関係なく、販売価格計算用のキャンペーン情報を設定します。
   * <ol>
   * <li>CatalogServiceのキャンペーン情報取得処理より、キャンペーン情報を取得します。</li>
   * <li>キャンペーン情報が取得できた場合、キャンペーン情報を設定します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>campaignCodeがnullで無いこと</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特になし</dd>
   * </dl>
   * </p>
   * 
   * @deprecated Priceクラス内でのCatalogService使用箇所はdeprecatedにします。
   */
  public void setCampaign(String campaignCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());

    Campaign campaign = catalogService.getCampaign(this.getShopCode(), campaignCode);
    if (campaign != null) {
      this.campaignInfo = campaign;
    }
  }

  /**
   * 適用した消費税率
   */
  public long retailTaxRate() {
    return this.getTaxRate();
  }

  /**
   * 商品の値引きが反映された販売金額を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>特別価格・予約・セール・キャンペーンに応じた販売金額を取得します。<BR>
   * 以下１～４はそれぞれ排他で、１＞２＞３＞４の順に優先計算されます。
   * <ol>
   * <li>特別価格商品の場合<BR>
   * 商品の特別価格に税計算を行ったものを返します。</li>
   * <li>予約商品の場合<BR>
   * 予約価格に税計算を行ったものを返します。</li>
   * <li>キャンペーン商品の場合<BR>
   * キャンペーンの値引率により金額計算したものを返します。<BR>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>getAllXXXメソッドにより金額情報が設定されていること</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特になし</dd>
   * </dl>
   * </p>
   * 
   * @return 販売価格
   */
  public BigDecimal getRetailPrice() {
    if (this.isDiscount()) {
      return Price.getPriceIncludingTax(detail.getDiscountPrice(), this.getTaxRate(), NumUtil
          .toString(header.getCommodityTaxType()));
    } else if (this.isReserved()) {
      return Price.getPriceIncludingTax(detail.getReservationPrice(), this.getTaxRate(), NumUtil.toString(header
          .getCommodityTaxType()));
    } else if (this.isSale() && this.isCampaign()) {
      BigDecimal campainPrice = Price.getPriceIncludingTax(detail.getUnitPrice(), taxRate, String.valueOf(header.getCommodityTaxType()));
      return Price.getPriceIncludingDiscountRate(campainPrice, this.getCampaignInfo().getCampaignDiscountRate());
    } else {
      return getUnitPrice();
    }
  }

  /**
   * 適用した消費税額
   */
  public BigDecimal retailTaxCharge() {
    if (this.isDiscount()) {
      return Price.getPriceTaxCharge(detail.getDiscountPrice(), this.getTaxRate(), NumUtil.toString(header.getCommodityTaxType()));
    } else if (this.isReserved()) {
      return Price.getPriceTaxCharge(detail.getReservationPrice(), this.getTaxRate(), NumUtil
          .toString(header.getCommodityTaxType()));
    } else if (this.isSale() && this.isCampaign()) {
      BigDecimal campainPrice = Price.getPriceIncludingDiscountRate(detail.getUnitPrice(), this.getCampaignInfo()
          .getCampaignDiscountRate());
      return Price.getPriceTaxCharge(campainPrice, taxRate, String.valueOf(header.getCommodityTaxType()));
    } else {
      return Price.getPriceTaxCharge(detail.getUnitPrice(), this.getTaxRate(), String.valueOf(header.getCommodityTaxType()));
    }
  }

  /**
   * 商品単価の消費税額
   */
  public BigDecimal getUnitTaxCharge() {
    return Price.getPriceTaxCharge(detail.getUnitPrice(), taxRate, NumUtil.toString(header.getCommodityTaxType()));
//    return Price.getPriceTaxCharge(detail.getUnitPrice(), taxRate, NumUtil.toString(header.getCommodityTaxType()));
  }

  /**
   * Price - retailPrice 通常価格と表示金額の差額
   */
  public BigDecimal discountDifference() {
    return this.getUnitPrice().subtract(this.getRetailPrice());
  }

  private boolean hasError() {
    boolean result = false;
    // 金額算出に必要なパラメータ(String)が設定されていなければエラー
    if (StringUtil.isNullOrEmpty(this.getShopCode())) {
      result = true;
    }

    if (StringUtil.isNullOrEmpty(detail.getSkuCode())) {
      result = true;
    }

    if (detail.getUnitPrice() == null) {
      result = true;
    }

    if (header.getCommodityTaxType() == null) {
      result = true;
    }

    if (this.getTaxRate() == null) {
      result = true;
    }

    // 販売期間でも予約期間でもない場合はエラー
    if (!this.isSale() && !this.isReserved() && !this.isDiscount()) {
      result = true;
    }

    // ディスカウント中なのにディスカウント価格がない
    if (this.isDiscount() && detail.getDiscountPrice() == null) {
      result = true;
    }

    // 予約期間中なのに、予約価格がない
    if (this.isReserved() && detail.getReservationPrice() == null) {
      result = true;
    }

    return result;
  }

  /**
   * エラーがなければtrue
   */
  public boolean isCorrect() {
    return !hasError();
  }

  /**
   * 税計算を行った商品単価を返します。
   */
  public BigDecimal getUnitPrice() {
    if (NumUtil.isNull(detail.getUnitPrice())) {
      throw new RuntimeException();
    }

    return Price.getPriceIncludingTax(detail.getUnitPrice(), getTaxRate().longValue(), NumUtil.toString(header
        .getCommodityTaxType()));
  }

  /**
   * 税計算を行った特別価格を返します。<br>
   * 特別価格期間ではない場合はLong.MAXVALUEを返します。
   */
  public BigDecimal getDiscountPrice() {
    if (isDiscount() && NumUtil.isNull(detail.getDiscountPrice())) {
      throw new RuntimeException();
    } else if (!isDiscount() && NumUtil.isNull(detail.getDiscountPrice())) {
      return new BigDecimal(Long.MAX_VALUE);
    } else {
      return Price.getPriceIncludingTax(detail.getDiscountPrice(), getTaxRate().longValue(), NumUtil.toString(header
          .getCommodityTaxType()));
    }
  }

  /**
   * 税計算を行った予約価格を返します。<br>
   * 予約価格期間ではない場合はLong.MAXVALUEを返します。
   */
  public BigDecimal getReservationPrice() {
    if (isReserved() && NumUtil.isNull(detail.getReservationPrice())) {
      throw new RuntimeException();

    } else if (!isReserved() && NumUtil.isNull(detail.getReservationPrice())) {
      return new BigDecimal(Long.MAX_VALUE);

    } else {
      return Price.getPriceIncludingTax(detail.getReservationPrice(), getTaxRate().longValue(), NumUtil.toString(header
          .getCommodityTaxType()));
    }
  }

  /**
   * price、taxRate、taxTypeを元に税込価格を返します。<BR>
   * 非課税：taxRateの値に関わらず受け取ったpriceを返します。<BR>
   * 内税(税込)：taxRateの値に関わらず受け取ったpriceを返します。<BR>
   * 外税(税抜)：(price * (100 + taxRate)) / 100 の小数点以下を切捨てた値を返します。<BR>
   * <br>
   * ex)<br>
   * unitPrice=500、taxRate=5、taxType=1(外税)の場合525が返ります。<BR>
   * unitPrice=210、taxRate=5、taxType=2(内税)の場合210が返ります。<BR>
   * 
   * @param price
   *          商品価格
   * @param taxRate
   *          税率
   * @param taxType
   *          消費税の扱い
   * @return 税込価格
   */
  public static BigDecimal getPriceIncludingTax(BigDecimal price, long taxRate, String taxType) {
    BigDecimal result;

    if (TaxType.EXCLUDED.getValue().equals(taxType)) {
      //BigDecimal bdPrice = new BigDecimal(price);
      BigDecimal bdRate = new BigDecimal(100 + taxRate);
      result = price.multiply(bdRate).divide(new BigDecimal(100), RoundingMode.FLOOR);
      //result = bdPrice.multiply(bdRate).divide(new BigDecimal(100), RoundingMode.FLOOR).longValue();
    } else {
      result = price;
    }
    
    return result;
  }

  /**
   * unitPrice、taxRate、taxTypeを元に税額を返します。<BR>
   * 非課税：taxRateの値に関わらず0を返します。<BR>
   * 内税(税込)：price * taxRate / (100 + taxRate) の小数点以下を切り捨てた値を返します。<BR>
   * 外税(税抜)：price * taxRate / (100)の小数点以下を切り捨てた値を返します。<BR>
   * <br>
   * ex)<br>
   * unitPrice=500、taxRate=5、taxType=1(外税)の場合25が返ります。<BR>
   * unitPrice=210、taxRate=5、taxType=2(内税)の場合10が返ります。<BR>
   * 
   * @param unitPrice
   *          商品価格
   * @param taxRate
   *          税率
   * @param taxType
   *          消費税の扱い
   * @return 税額
   */
  public static BigDecimal getPriceTaxCharge(BigDecimal bdPrice, long taxRate, String taxType) {
    BigDecimal result;

    if (TaxType.NO_TAX.getValue().equals(taxType)) {
      result = BigDecimal.ZERO;
    } else if (TaxType.INCLUDED.getValue().equals(taxType)) {
      //BigDecimal bdPrice = new BigDecimal(unitPrice);
      BigDecimal bdRate = new BigDecimal(taxRate);
      //result = bdPrice.multiply(bdRate).divide(new BigDecimal(taxRate).add(new BigDecimal(100)), RoundingMode.FLOOR).longValue();
      result = bdPrice.multiply(bdRate).divide(new BigDecimal(taxRate).add(new BigDecimal(100)), RoundingMode.FLOOR);
    } else {
      //BigDecimal bdPrice = new BigDecimal(unitPrice);
      BigDecimal bdRate = new BigDecimal(taxRate);
      //result = bdPrice.multiply(bdRate).divide(new BigDecimal(100), RoundingMode.FLOOR).longValue();
      result = bdPrice.multiply(bdRate).divide(new BigDecimal(100), RoundingMode.FLOOR);
    }

    return result;
  }

  /**
   * 税込金額を受け取って、税額を返します。
   * 
   * @return 税額
   */
  public static BigDecimal getPriceTaxChargeInclueding(BigDecimal unitPrice, long taxRate, String taxType) {
    BigDecimal result;

    if (TaxType.NO_TAX.getValue().equals(taxType)) {
      result = BigDecimal.ZERO;
    } else {
      BigDecimal bdRate = new BigDecimal(taxRate);
      result = unitPrice.multiply(bdRate).divide(new BigDecimal(taxRate).add(new BigDecimal(100)), RoundingMode.FLOOR);
    }

    return result;
  }

  /**
   * price, rateを元に、値引価格を返します。<BR>
   * priceは税込価格が渡されることを前提とします。<BR>
   * <BR>
   * また、価格が0円未満、または値引率が0%以上100%以下の範囲を超える場合は、<BR>
   * ArithmeticExceptionをスローします。
   * 
   * @param price
   *          税込み価格
   * @param discountRate
   *          値引率
   * @return 値引価格
   */
  public static BigDecimal getPriceIncludingDiscountRate(BigDecimal price, long discountRate) {
    // BigDecimal bdPrice = new BigDecimal(price);
    // BigDecimal bdRate = new BigDecimal(100 - discountRate);
    // return bdPrice.multiply(bdRate).divide(new BigDecimal(100),
    // RoundingMode.FLOOR).longValue();
    return calcDiscount(price, discountRate);
  }

  /**
   * price, rateを元に、値引価格を返します。<BR>
   * priceは税込価格が渡されることを前提とします。<BR>
   * <BR>
   * また、価格が0円未満、または値引率が0%以上100%以下の範囲を超える場合は、<BR>
   * ArithmeticExceptionをスローします。
   * 
   * @param price
   *          税込み価格
   * @param rate
   *          値引率
   * @return 値引価格
   */
  public static BigDecimal calcDiscount(BigDecimal dbPrice, long rate) {
    Logger logger = Logger.getLogger(Price.class);
    // 入力値チェック：入力値が異常な場合はArighmeticExceptionをスローする
    String errorMessage = null;

    if (dbPrice.compareTo(BigDecimal.ZERO) == -1) {
      //errorMessage = "価格は0円以上である必要があります: price = " + Long.toString(price);
      errorMessage = MessageFormat.format(Messages.getString("service.Price.0"), dbPrice);
    } else if (rate < 0 || 100 < rate) {
      //errorMessage = "値引率は0%以上100%以下である必要があります: rate = " + Long.toString(rate);
      errorMessage = MessageFormat.format(Messages.getString("service.Price.1"), rate);
    }

    if (errorMessage != null) {
      logger.warn(errorMessage);
      throw new ArithmeticException(errorMessage);
    }

    //BigDecimal dbPrice = new BigDecimal(price);
    BigDecimal dbRate = new BigDecimal(rate);

    // 値引率から値引価格を求める
    BigDecimal discPrice = dbPrice.multiply(dbRate).divide(BD_HUNDRED, RoundingMode.FLOOR);

    // 元の価格から値引価格を引いて、結果を返す
    return dbPrice.subtract(discPrice);
  }

  private static final BigDecimal BD_HUNDRED = BigDecimal.valueOf(100L);

  public static String getFormatPrice(BigDecimal price) {
    if (NumUtil.isNull(price)) {
      return "";
    } else {
      return NumUtil.formatCurrency(price);
    }
  }

  public BigDecimal getDiscountRate() {
    if (isDiscount() && NumUtil.isNull(detail.getDiscountPrice())) {
      throw new RuntimeException();
    } else {
      if (getUnitPrice().longValue() != 0L) {
        return BigDecimalUtil.multiply(100, BigDecimalUtil.subtract(BigDecimal.ONE, BigDecimalUtil.divide(getRetailPrice(), getUnitPrice(), 2, RoundingMode.UP)));
      } else {
        return new BigDecimal(0);
      }
    }
  }

  public BigDecimal getDiscountPrices() {
    if (isDiscount() && NumUtil.isNull(detail.getDiscountPrice())) {
      throw new RuntimeException();
    } else {
      return BigDecimalUtil.subtract(getRetailPrice(), getUnitPrice());
    }
  }
}
