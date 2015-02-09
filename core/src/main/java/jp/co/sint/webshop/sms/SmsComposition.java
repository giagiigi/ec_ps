package jp.co.sint.webshop.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.domain.SmsType;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * メール構造の初期設定情報を保持するEnum。
 * 
 * @author System Integrator Corp.
 */
public enum SmsComposition {

  // Composition情報は構造の順番どおり記述しなければならない
  /** 共通用ルート構造 */
  COMMON_ROOT_COMPOSITION("", null, "", null),
  
  /** 受注確認（PC）-本文 */
  ORDER_DETAILS_PC_MAIN("本文", SmsType.ORDER_DETAILS_PC, "[#MAIN#]", COMMON_ROOT_COMPOSITION, ShippingTag.values()),  

  /** 受注確認（PC）-受注修正メッセージ */
  ORDER_DETAILS_PC_MODIFY_MESSAGE("受注修正メッセージ", SmsType.ORDER_DETAILS_PC, "[#MAIN1#]",
      COMMON_ROOT_COMPOSITION, ShippingTag.values()),
  /** 受注確認（PC）-配送先情報 */
  ORDER_DETAILS_PC_SHIPPING_HEADER("配送先情報", SmsType.ORDER_DETAILS_PC, "[#SHIPPING_HEADER#]", COMMON_ROOT_COMPOSITION,
      ShippingHeaderTag.values()),
  /** 受注確認（PC）-配送先明細情報 */
  ORDER_DETAILS_PC_SHIPPING_DETAIL("配送先明細情報", SmsType.ORDER_DETAILS_PC, "[#SHIPPING_DETAIL#]", COMMON_ROOT_COMPOSITION,
      ShippingDetailTag.values()),
  /** 受注確認（PC）-支払先情報 */
  ORDER_DETAILS_PC_PAYMENT("支払先情報", SmsType.ORDER_DETAILS_PC, "[#PAYMENT#]", COMMON_ROOT_COMPOSITION, PaymentTag.values()),
  /** 受注確認（PC）-署名 */
  ORDER_DETAILS_PC_SIGN("署名", SmsType.ORDER_DETAILS_PC, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  
  /** 受注確認（携帯）-本文 */
  ORDER_DETAILS_MOBILE_MAIN("本文", SmsType.ORDER_DETAILS_MOBILE, "[#MAIN#]", COMMON_ROOT_COMPOSITION, OrderHeaderTag.values()),

  /** 出荷報告 -本文 */
  SHIPPING_REPORT_MAIN("本文", SmsType.SHIPPING_INFORMATION, "[#MAIN#]", COMMON_ROOT_COMPOSITION, ShippingTag.values()),
  
  /** 出荷報告 -本文 */
  SHIPPING_REPORT_ORDER("本文", SmsType.SHIPPING_INFORMATION, "[#MAIN1#]", COMMON_ROOT_COMPOSITION, ShippingTag.values()),
  /** 出荷報告 -配送先情報 */
  SHIPPING_REPORT_HEADER("配送先情報", SmsType.SHIPPING_INFORMATION, "[#SHIPPING_HEADER#]", COMMON_ROOT_COMPOSITION, ShippingHeaderTag
      .values(), ArrivalTag.values()),
  /** 出荷報告 -配送先明細情報 */
  SHIPPING_REPORT_DETAIL("配送先明細情報", SmsType.SHIPPING_INFORMATION, "[#SHIPPING_DETAIL#]", COMMON_ROOT_COMPOSITION, ShippingDetailTag
      .values()),
  /** 出荷報告 -署名 */
  SHIPPING_REPORT_SIGN("署名", SmsType.SHIPPING_INFORMATION, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  
  // saikou 2011/12/28 ob add start
  COUPON_START_MAIN("本文", SmsType.COUPON_START, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CouponStartTag.values()),
  COUPON_END_MAIN("本文", SmsType.COUPON_END, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CouponEndTag.values());
  // saikou 2011/12/28 ob add end
  
  
  private String name;

  private String substitutionTag;

  private SmsType type;

  private SmsComposition parentCompostion;

  private List<SmsTemplateTag> useableTagList = new ArrayList<SmsTemplateTag>();

  private SmsComposition(String name, SmsType type, String substitutionTag, SmsComposition parentComposition,
      SmsTemplateTag[]... usableTag) {
    this.name = name;
    this.substitutionTag = substitutionTag;
    this.type = type;
    this.parentCompostion = parentComposition;
    for (SmsTemplateTag[] tag : usableTag) {
      this.useableTagList.addAll(Arrays.asList(tag));
    }
  }

  /**
   * nameを取得します。
   * 
   * @return the name
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * compostionTagを取得します。
   * 
   * @return the compostionTag
   */
  public String getSubstitutionTag() {
    return substitutionTag;
  }

  /**
   * typeを取得します。
   * 
   * @return the type
   */
  public SmsType getType() {
    return type;
  }

  /**
   * useableTagListを取得します。
   * 
   * @return the useableTagList
   */
  public List<SmsTemplateTag> getUseableTagList() {
    return useableTagList;
  }

  /**
   * メールタイプから構造情報を取得する。
   * 
   * @param type
   * @return メールタイプから得られる構造情報
   */
  public static List<SmsComposition> fromSmsType(SmsType type) {
    List<SmsComposition> compostionList = new ArrayList<SmsComposition>();
    for (SmsComposition c : SmsComposition.values()) {
      if (c.getType() == type) {
        compostionList.add(c);
      }
    }
    return compostionList;
  }
  
  public static SmsComposition fromSmsTypeAndTag(SmsType type, String substitutionTag) {
    for (SmsComposition c : SmsComposition.values()) {
      if (c.getType() == type && c.getSubstitutionTag().equals(substitutionTag)) {
        return c;
      }
    }
    return null;
  }

  public SmsComposition getParentCompostion() {
    return parentCompostion;
  }
  
  public static List<SmsComposition> getOrderTag() {
    List<SmsComposition> compostionList = new ArrayList<SmsComposition>();
    for (SmsComposition c : SmsComposition.values()) {
      if (c.getType() == SmsType.ORDER_DETAILS_PC) {
        compostionList.add(c);
      }
    }
    return compostionList;
  }

  public static List<SmsComposition> getShippingTag() {
    List<SmsComposition> compostionList = new ArrayList<SmsComposition>();
    for (SmsComposition c : SmsComposition.values()) {
      if (c.getType() == SmsType.SHIPPING_INFORMATION) {
        compostionList.add(c);
      }
    }
    return compostionList;
  }
  
  public static List<SmsComposition> getCouponTag() {
    List<SmsComposition> compostionList = new ArrayList<SmsComposition>();
    for (SmsComposition c : SmsComposition.values()) {
      if (c.getType() == SmsType.COUPON_START) {
        compostionList.add(c);
      }
    }
    return compostionList;
  }
}
