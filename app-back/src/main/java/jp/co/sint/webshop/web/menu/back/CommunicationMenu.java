package jp.co.sint.webshop.web.menu.back;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * メニュー制御のenumです。
 * 
 * @author System Integrator Corp.
 */
public enum CommunicationMenu implements BackDetailMenuBase {
  /** キャンペーン管理 */
  // CAMPAIGN_LIST("U1060310", "キャンペーン管理", "/menu/communication/campaign_list"),
  /** アンケート管理 */
  ENQUETE_LIST("U1060110", "アンケート管理", "/menu/communication/enquete_list"),
  /** レビュー管理 */
  REVIEW_LIST("U1060210", "レビュー管理", "/menu/communication/review_list"),
  /** メールマガジンマスタ */
  MAIL_MAGAZINE("U1060410", "メールマガジンマスタ", "/menu/communication/mail_magazine"),
  /** soukai add/update/delete ob 2011/12/14 shb start */
  /** 会员折扣活动设定 */
  CUSTOMER_GROUP_CAMPAIGN_LIST("U1060510", "会员折扣活动设定", "/menu/communication/customer_group_campaign_list"),
  /** 顾客别优惠券发行规则一览 */
  PRIVATE_COUPON_LIST("U1060610", "会员优惠券管理", "/menu/communication/private_coupon_list"),
  /** 公共优惠券发行规则一览 */
  PUBLIC_COUPON_LIST("U1060710", "媒体优惠代码管理", "/menu/communication/public_coupon_list"),
  /** 促销企划管理 */
  PLAN_LIST_0("U1060810", "商品折扣活动管理", "/app/communication/plan_list/init/0"),
  /** 特集企划管理 */
  PLAN_LIST_1("U1060910", "特集活动管理", "/app/communication/plan_list/init/1"),
  /** # soukai add/update/delete ob 2011/12/14 shb start */
  // 2012/11/21 促销对应 ob add start
  /** 促销活动管理 */
  NEW_CAMPAIGN_LIST("U1061010", "促销活动管理", "/menu/communication/new_campaign_list"),
  // 2012/11/21 促销对应 ob add end

  /** 朋友推荐优惠券发行规则一览 */
  FRIEND_COUPON_RULE("U1061110", "朋友推荐优惠券管理", "/menu/communication/friend_coupon_rule_list"),
  
  // 20130729 txw add start
  /** 限时限量折扣管理 */
  DISCOUNT_LIST("U1061210", "限时限量折扣管理", "/menu/communication/discount_list"),
  // 20130729 txw add end
  // zhangfeng 2014/4/8 add start
  CUSTOMER_MESSAGE("U1061220", "顾客需求外国商品", "/menu/communication/customer_message"),
  // zhangfeng 2014/4/8 add end
  // 20131011 txw add start
  /** 免邮促销管理 */
  FREE_POSTAGE_LIST("U1061310", "免邮促销管理", "/menu/communication/free_postage_list"),
  // 20131011 txw add end
  //20131104 wz add end
  /** 礼品卡管理 */
  GIFT_CARD_RULE_LIST("U1061410","礼品卡管理","/menu/communication/gift_card_rule_list"),
  //20131104 wz add end
  // 20140303 txw add start
  /** 宣传品管理 */
  PROPAGANDA_ACTIVITY_RULE_LIST("U1061510","宣传品管理","/menu/communication/propaganda_activity_rule_list"),
 
  // 20140723 hdh add end
  /** 任意活动 */
  OPTIONAL_CAMPAIGN("U1061610","购买多件优惠活动","/menu/communication/optional_campaign_list"),
  /**仓库休息日管理*/
  STOCK_HOLIDAY("U1061611","仓库休息日管理","/menu/communication/stock_holiday_list");
  // 20140723 hdh add end
  
  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private CommunicationMenu(String moduleId, String label, String url, Permission... permissions) {
    this.moduleId = moduleId;
    this.label = label;
    this.url = url;
    this.permissions = permissions;
  }

  /**
   * 画面のURLを取得します。
   * 
   * @return 各画面のURL
   */
  public String getUrl() {
    return url;
  }

  /**
   * 画面の名称を取得します。
   * 
   * @return 各画面の名称
   */
  public String getLabel() {
    return StringUtil.coalesce(CodeUtil.getLabel(this), this.label);
  }

  /**
   * 画面のモジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return this.moduleId;
  }

  /**
   * 画面の権限情報を取得します。
   * 
   * @return 各画面の権限情報
   */
  public Permission[] getPermissions() {
    return ArrayUtil.immutableCopy(permissions);
  }
}
