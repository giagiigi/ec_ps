package jp.co.sint.webshop.service;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.MenuGroup;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PermissionType;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 権限情報の定義を集約した列挙です。
 * 
 * @author System Integrator Corp.
 */
public enum Permission implements CodeAttribute {

  // 管理者用権限
  /** サイト管理者 */
  SITE_MANAGER("000000", "サイト管理者", null, PermissionType.SITE),
  /** ショップ管理者 */
  SHOP_MANAGER("100000", "ショップ管理者", null, PermissionType.SHOP),

  // オペレータ用権限
  /** サイトオペレータ */
  SITE_OPERATOR("000009", "ショップオペレータ", null, PermissionType.SITE),
  /** ショップオペレータ */
  SHOP_OPERATOR("100009", "サイトオペレータ", null, PermissionType.SHOP),

  // 受注系
  /** 受注入金管理参照 - サイト */
  ORDER_READ_SITE("001001", "受注入金管理参照", MenuGroup.ORDER, PermissionType.SITE),
  /** 受注入金管理登録/更新 - サイト */
  ORDER_UPDATE_SITE("001002", "受注入金管理登録/更新", MenuGroup.ORDER, PermissionType.SITE),
  /** 受注入金管理変更 - サイト */
  ORDER_MODIFY_SITE("001003", "受注入金管理変更", MenuGroup.ORDER, PermissionType.SITE),
  /** 受注入金管理データ入出力 - サイト */
  ORDER_DATA_IO_SITE("001004", "受注入金管理データ入出力", MenuGroup.ORDER, PermissionType.SITE),
  /** 出荷管理参照 - サイト */
  SHIPPING_READ_SITE("001011", "出荷管理参照", MenuGroup.ORDER, PermissionType.SITE),
  /** 出荷管理更新 - サイト */
  SHIPPING_UPDATE_SITE("001012", "出荷管理更新", MenuGroup.ORDER, PermissionType.SITE),
  /** 订单确认对象 */
  SHIPPING_DATA_IO_SITE("001014", "出荷管理データ入出力", MenuGroup.ORDER, PermissionType.SITE),
  /** 订单确认对象 */
  CONFIRM_OBJECT_IO_SITE("001015", "订单确认对象", MenuGroup.ORDER, PermissionType.SITE),
  /** 受注入金管理参照 - ショップ */
  ORDER_READ_SHOP("101001", "受注入金管理参照", MenuGroup.ORDER, PermissionType.SHOP),
  /** 受注入金管理登録/更新 - ショップ */
  ORDER_UPDATE_SHOP("101002", "受注入金管理登録/更新", MenuGroup.ORDER, PermissionType.SHOP),
  /** 受注入金管理変更 - ショップ */
  ORDER_MODIFY_SHOP("101003", "受注入金管理変更", MenuGroup.ORDER, PermissionType.SHOP),
  /** 受注入金管理データ入出力 - ショップ */
  ORDER_DATA_IO_SHOP("101004", "受注入金管理データ入出力", MenuGroup.ORDER, PermissionType.SHOP),
  /** 出荷管理参照 - ショップ */
  SHIPPING_READ_SHOP("101011", "出荷管理参照", MenuGroup.ORDER, PermissionType.SHOP),
  /** 出荷管理更新 - ショップ */
  SHIPPING_UPDATE_SHOP("101012", "出荷管理更新", MenuGroup.ORDER, PermissionType.SHOP),
  /** 出荷管理データ入出力 - ショップ */
  SHIPPING_DATA_IO_SHOP("101014", "出荷管理データ入出力", MenuGroup.ORDER, PermissionType.SHOP),
  /** 订单确认对象 */
  CONFIRM_OBJECT_IO_SHOP("101015", "订单确认对象", MenuGroup.ORDER, PermissionType.SHOP),
  UNTRUE_ORDER_WORD("101016", "虚假订单关键词管理", MenuGroup.ORDER, PermissionType.SHOP),

  // 顧客系
  /** 顧客管理参照 */
  CUSTOMER_READ("002001", "顧客管理参照", MenuGroup.CUSTOMER, PermissionType.SITE),
  /** 顧客管理登録/更新 */
  CUSTOMER_UPDATE("002002", "顧客管理登録/更新", MenuGroup.CUSTOMER, PermissionType.SITE),
  /** 顧客管理削除 */
  CUSTOMER_DELETE("002003", "顧客管理削除", MenuGroup.CUSTOMER, PermissionType.SITE),
  /** 顧客管理データ入出力 */
  CUSTOMER_IO("002004", "顧客管理データ入出力", MenuGroup.CUSTOMER, PermissionType.SITE),
  /** ポイント利用状況参照 */
  CUSTOMER_POINT_READ("002021", "ポイント利用状況参照", MenuGroup.CUSTOMER, PermissionType.SITE), CUSTOMER_COUPON_READ("002025", "优惠券使用状况察看",
      MenuGroup.CUSTOMER, PermissionType.SITE), CUSTOMER_COUPON_INVEST("002026", "优惠券给与", MenuGroup.CUSTOMER, PermissionType.SITE),
  // CUSTOMER_COUPON_DELETE("002027", "优惠券删除", MenuGroup.CUSTOMER,
  // PermissionType.SITE),
  /** ポイント利用状況データ入出力 */
  CUSTOMER_POINT_IO("002022", "ポイント利用状況データ入出力", MenuGroup.CUSTOMER, PermissionType.SITE),
  /** ポイント付与 */
  CUSTOMER_POINT_INVEST("002023", "ポイント付与", MenuGroup.CUSTOMER, PermissionType.SITE),
  /** 無効ポイント削除 */
  CUSTOMER_POINT_DELETE("002024", "無効ポイント削除", MenuGroup.CUSTOMER, PermissionType.SITE),
  /** 顧客管理参照 */
  CUSTOMER_READ_SHOP("102001", "顧客管理参照", MenuGroup.CUSTOMER, PermissionType.SHOP),
  /** クーポン利用状況データ入出力 */
  CUSTOMER_COUPON_IO("002028", "クーポン利用状況データ入出力", MenuGroup.CUSTOMER, PermissionType.SITE),

  // 商品系
  /** 商品管理参照 */
  CATALOG_READ("003001", "商品管理参照", MenuGroup.CATALOG, PermissionType.SITE),
  /** カテゴリ管理参照 */
  CATEGORY_READ("003011", "カテゴリ管理参照", MenuGroup.CATALOG, PermissionType.SITE),
  /** カテゴリ管理登録/更新 */
  CATEGORY_UPDATE("003012", "カテゴリ管理登録/更新", MenuGroup.CATALOG, PermissionType.SITE),
  /** カテゴリ管理削除 */
  CATEGORY_DELETE("003013", "カテゴリ管理削除", MenuGroup.CATALOG, PermissionType.SITE),
  /** カテゴリ管理データ入出力 */
  CATEGORY_DATA_IO("003014", "カテゴリ管理データ入出力", MenuGroup.CATALOG, PermissionType.SITE),
  /** 商品管理参照 */
  COMMODITY_READ("103001", "商品管理参照", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 商品管理登録/更新 */
  COMMODITY_UPDATE("103002", "商品管理登録/更新", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 商品管理削除 */
  COMMODITY_DELETE("103003", "商品管理削除", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 商品管理データ入出力 */
  COMMODITY_DATA_IO("103004", "商品管理データ入出力", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 在庫管理参照 */
  STOCK_MANAGEMENT_READ("103011", "在庫管理参照", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 在庫管理登録/更新 */
  STOCK_MANAGEMENT_UPDATE("103012", "在庫管理登録/更新", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 在庫管理データ取込 */
  STOCK_MANAGEMENT_DATA_IO("103014", "在庫管理データ取込", MenuGroup.CATALOG, PermissionType.SHOP),
  // 20130614 txw add start
  /** 组合商品登录参照 */
  COMMODITY_CONSTITUTE_READ("103021", "组合商品登录参照", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 组合商品登录登録/更新 */
  COMMODITY_CONSTITUTE_UPDATE("103022", "组合商品登录登録/更新", MenuGroup.CATALOG, PermissionType.SHOP),
  /** 组合商品登录削除 */
  COMMODITY_CONSTITUTE_DELETE("103023", "组合商品登录削除", MenuGroup.CATALOG, PermissionType.SHOP),
  // 20130614 txw add end
  COMMODITY_PRICE_CHANGE("103024", "商品改价审核", MenuGroup.CATALOG, PermissionType.SHOP),
  // zzy 2015-1-14 add start
  COMMODITY_MASTER_LIST("103025", "TM/JD多SKU商品关联管理", MenuGroup.CATALOG, PermissionType.SHOP),
  // zzy 2015 add end
  
  // ショップ系
  /** ショップ管理参照 - サイト */
  SHOP_MANAGEMENT_READ_SITE("004001", "ショップ管理参照", MenuGroup.SHOP, PermissionType.SITE),
  /** ショップ管理登録/更新 - サイト */
  SHOP_MANAGEMENT_UPDATE_SITE("004002", "ショップ管理登録/更新", MenuGroup.SHOP, PermissionType.SITE),
  /** ショップ管理削除 - サイト */
  SHOP_MANAGEMENT_DELETE_SITE("004003", "ショップ管理削除", MenuGroup.SHOP, PermissionType.SITE),
  /** ショップ管理データ入出力 - サイト */
  SHOP_MANAGEMENT_IO_SITE("004004", "ショップ管理データ入出力", MenuGroup.SHOP, PermissionType.SITE),
  /** ショップ管理参照 - ショップ */
  SHOP_MANAGEMENT_READ_SHOP("104001", "ショップ管理参照", MenuGroup.SHOP, PermissionType.SHOP),
  /** ショップ管理登録/更新 - ショップ */
  SHOP_MANAGEMENT_UPDATE_SHOP("104002", "ショップ管理登録/更新", MenuGroup.SHOP, PermissionType.SHOP),
  /** ショップ管理削除 - ショップ */
  SHOP_MANAGEMENT_DELETE_SHOP("104003", "ショップ管理削除", MenuGroup.SHOP, PermissionType.SHOP),
  /** ショップ管理データ入出力 - ショップ */
  SHOP_MANAGEMENT_IO_SHOP("104004", "ショップ管理データ入出力", MenuGroup.SHOP, PermissionType.SHOP),
  // 20111208 lirong add start
  // /** 参照退换货管理 */
  // RETURN_DATA_MANAGEMENT("001031", "参照退换货管理", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 登录/更新受理信息 */
  // RETURN_DATA_READ("001032", "登录/更新受理信息", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 登录/更新退货确认信息 */
  // RETURN_GOODS_DATA_CONFIRM("001033", "登录/更新退货确认信息", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 登录/更新退款确认信息 */
  // RETURN_REFUND_DATA_CONFIRM("001034", "登录/更新退款确认信息", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 登录/更新退款完成信息 */
  // RETURN_COMPLETE_DATA("001035", "登录/更新退款完成信息", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 登录/更新退款联络信息 */
  // RETURN_CONTACT_DATA("001036", "登录/更新退款联络信息", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 取消退换货信息 */
  // RETURN_DATA_CANCEL("001039", "取消退换货信息", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 删除退换货信息 */
  // RETURN_DATA_DELETE("001037", "删除退换货信息", MenuGroup.ORDER,
  // PermissionType.SITE),
  // /** 导出退换货数据 */
  // RETURN_DATA_EXPORT("001038", "导出退换货数据", MenuGroup.ORDER,
  // PermissionType.SITE),
  // 20111208 lirong add end

  // //add by shikui start 2010/04/28
  // /** 在线客服参照 */
  // ONlINE_READ_SITE("004005", " 在线客服参照", MenuGroup.SHOP, PermissionType.SITE),
  // /** 在线客服登録/更新 */
  // ONlINE_UPDATE_SITE("004006", "在线客服登録/更新 ", MenuGroup.SHOP,
  // PermissionType.SITE),
  // //add by shikui end 2010/04/28
  //  
  // //add by ytw start 2010/06/10
  // /** Google Analysis 参照*/
  // GOOGLE_ANALYSIS_SITE("009005", " Google Analysis参照", MenuGroup.SHOP,
  // PermissionType.SITE),
  // /** Google Analysis 登録/更新*/
  // GOOGLE_ANALYSIS_UPDATE_SITE("009006", "Google Analysis登録/更新 ",
  // MenuGroup.SHOP, PermissionType.SITE),
  // //add by ytw end 2010/06/10

  // コミュニケーション系
  /** キャンペーン参照 - サイト */
  CAMPAIGN_READ_SITE("005011", "キャンペーン参照", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** キャンペーン登録/更新 - サイト */
  CAMPAIGN_UPDATE_SITE("005012", "キャンペーン登録/更新", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** キャンペーン削除 - サイト */
  CAMPAIGN_DELETE_SITE("005013", "キャンペーン削除", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** キャンペーンデータ入出力 - サイト */
  CAMPAIGN_DATA_IO_SITE("005014", "キャンペーンデータ入出力", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** アンケート参照 - サイト */
  ENQUETE_READ_SITE("005021", "アンケート参照", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** アンケート登録/更新 - サイト */
  ENQUETE_UPDATE_SITE("005022", "アンケート登録/更新", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** アンケート削除 - サイト */
  ENQUETE_DELETE_SITE("005023", "アンケート削除", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** アンケートデータ入出力 - サイト */
  ENQUETE_DATA_IO_SITE("005024", "アンケートデータ入出力", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** レビュー参照 - サイト */
  REVIEW_READ_SITE("005031", "レビュー参照", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** レビュー登録/更新 - サイト */
  REVIEW_UPDATE_SITE("005032", "レビュー登録/更新", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** レビュー削除 - サイト */
  REVIEW_DELETE_SITE("005033", "レビュー削除", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** メールマガジン参照 - サイト */
  MAIL_MAGAZINE_READ_SITE("005041", "メールマガジン参照", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** メールマガジン登録/更新 - サイト */
  MAIL_MAGAZINE_UPDATE_SITE("005042", "メールマガジン登録/更新", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** メールマガジン削除 - サイト */
  MAIL_MAGAZINE_DELETE_SITE("005043", "メールマガジン削除", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** メールマガジンデータ入出力 - サイト */
  MAIL_MAGAZINE_IO_SITE("005044", "メールマガジンデータ入出力", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** キャンペーン参照 - ショップ */
  CAMPAIGN_READ_SHOP("105011", "キャンペーン参照", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** キャンペーン登録/更新 - ショップ */
  CAMPAIGN_UPDATE_SHOP("105012", "キャンペーン登録/更新", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** キャンペーン削除 - ショップ */
  CAMPAIGN_DELETE_SHOP("105013", "キャンペーン削除", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** キャンペーンデータ入出力 - ショップ */
  CAMPAIGN_DATA_IO_SHOP("105014", "キャンペーンデータ入出力", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** レビュー参照 - ショップ */
  REVIEW_READ_SHOP("105031", "レビュー参照", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** レビュー登録/更新 - ショップ */
  REVIEW_UPDATE_SHOP("105032", "レビュー登録/更新", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** レビュー削除 - ショップ */
  REVIEW_DELETE_SHOP("105033", "レビュー削除", MenuGroup.COMMUNICATION, PermissionType.SHOP),

  // soukai add ob 2011/12/14 shb start */
  /** 多店铺 */
  CUSTOMER_GROUP_CAMPAIGN_READ_SITE("005051", "会员折扣规则查看", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 会员折扣规则新建更新权限 */
  CUSTOMER_GROUP_CAMPAIGN_UPDATE_SITE("005052", "会员折扣规则新建更新", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 会员折扣规则删除权限 */
  CUSTOMER_GROUP_CAMPAIGN_DELETE_SITE("005053", "会员折扣规则删除", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 会员优惠券发行规则查看权限 */
  PRIVATE_COUPON_READ_SITE("005061", "别优惠券发行规则查看", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 会员优惠券发行规则更新权限 */
  PRIVATE_COUPON_UPDATE_SITE("005062", "会员优惠券发行规则更新", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 会员优惠券发行规则删除权限 */
  PRIVATE_COUPON_DELETE_SITE("005063", "会员优惠券发行规则删除", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 媒体优惠代码发行规则查看权限 */
  PUBLIC_COUPON_READ_SITE("005071", "媒体优惠代码发行规则查看", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 媒体优惠代码发行规则更新权限 */
  PUBLIC_COUPON_UPDATE_SITE("005072", "媒体优惠代码发行规则更新权限", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 媒体优惠代码发行规则删除权限 */
  PUBLIC_COUPON_DELETE_SITE("005073", "媒体优惠代码发行规则删除权限", MenuGroup.COMMUNICATION, PermissionType.SITE),
  /** 一店铺 */
  /** 会员折扣规则查看权限 */
  CUSTOMER_GROUP_CAMPAIGN_READ_SHOP("105051", "会员折扣规则查看", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 会员折扣规则新建更新权限 */
  CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP("105052", "会员折扣规则新建更新", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 会员折扣规则删除权限 */
  CUSTOMER_GROUP_CAMPAIGN_DELETE_SHOP("105053", "会员折扣规则删除", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 会员优惠券发行规则查看权限 */
  PRIVATE_COUPON_READ_SHOP("105061", "会员优惠券发行规则查看", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 会员优惠券发行规则更新权限 */
  PRIVATE_COUPON_UPDATE_SHOP("105062", "会员优惠券发行规则更新", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 会员优惠券发行规则删除权限 */
  PRIVATE_COUPON_DELETE_SHOP("105063", "会员优惠券发行规则删除", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 媒体优惠代码发行规则查看权限 */
  PUBLIC_COUPON_READ_SHOP("105071", "媒体优惠代码发行规则查看", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 媒体优惠代码发行规则更新权限 */
  PUBLIC_COUPON_UPDATE_SHOP("105072", "媒体优惠代码发行规则更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 媒体优惠代码发行规则删除权限 */
  PUBLIC_COUPON_DELETE_SHOP("105073", "媒体优惠代码发行规则删除权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 优惠券履历导入权限 */
  PUBLIC_COUPON_DATA_IO("105074", "优惠券履历导入", MenuGroup.COMMUNICATION, PermissionType.SHOP),

  /** 企划查看权限 */
  PLAN_READ_SHOP("105075", "企划查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 企划新建更新权限 */
  PLAN_UPDATE_SHOP("105076", "企划新建更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 企划删除权限 */
  PLAN_DELETE_SHOP("105077", "企划删除权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  // soukai add ob 2011/12/20 end

  // zzt and 2013.5.16 start
  /** 朋友推荐优惠券查看权限 */
  FriendCouponRule_READ_SHOP("105081", "朋友推荐优惠券查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 朋友推荐优惠券更新权限 */
  FriendCouponRule_UPDATE_SHOP("105082", "朋友推荐优惠券新建更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 企划删除权限 */
  FriendCouponRule_DELETE_SHOP("105083", "朋友推荐优惠券删除权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  // zzt and 2013.5.16 end
  
  // 20130729 txw and start
  /** 限时限量折扣管理查看权限 */
  DISCOUNT_READ_SHOP("105091", "限时限量折扣管理查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 限时限量折扣管理新建更新权限 */
  DISCOUNT_UPDATE_SHOP("105092", "限时限量折扣管理新建更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 限时限量折扣管理删除权限 */
  DISCOUNT_DELETE_SHOP("105093", "限时限量折扣管理删除权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  // 20130729 txw and end
  
  // zhangfeng 2014/4/11 add begin
  /** 顾客留言管理查看权限 */
  MESSAGE_READ_SHOP("105094", "顾客留言管理查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 顾客留言管理删除权限 */
  MESSAGE_DELETE_SHOP("105095", "顾客留言管理删除权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  // zhangfeng 2014/4/11 add end
  
  
  // 20131011 txw and start
  /** 免邮促销查看权限 */
  FREE_POSTAGE_READ_SHOP("105111", "免邮促销查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 免邮促销新建更新权限 */
  FREE_POSTAGE_UPDATE_SHOP("105112", "免邮促销新建更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 免邮促销删除权限 */
  FREE_POSTAGE_DELETE_SHOP("105113", "免邮促销删除权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  // 20131011 txw and end
  
  GIFT_CARD_RULE_IO("105114", "礼品卡IO权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  
  GIFT_CARD_RULE_READ_SHOP("105115", "礼品卡发行规则查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  
  GIFT_CARD_RULE_UPDATE_SHOP("105116", "礼品卡发行规则更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  
  GIFT_CARD_LOG_IO("105117","礼品卡发行统计导出权限",MenuGroup.COMMUNICATION, PermissionType.SHOP),
  
  GIFT_CARD_USE_LOG_IO("105118","礼品卡使用明细导出权限",MenuGroup.COMMUNICATION, PermissionType.SHOP),
  
  // 20140303 txw and start
  /** 宣传品活动查看权限 */
  PROPAGANDA_ACTIVITY_RULE_READ_SHOP("105131", "宣传品活动查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 宣传品活动新建更新权限 */
  PROPAGANDA_ACTIVITY_RULE_UPDATE_SHOP("105132", "宣传品活动新建更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  /** 宣传品活动删除权限 */
  PROPAGANDA_ACTIVITY_RULE_DELETE_SHOP("105133", "宣传品活动删除权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  // 20140303 txw and end
  
  OPTIONAL_CAMPAGIN_READ_SHOP("105134", "任意活动查看权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  OPTIONAL_CAMPAGIN_REGISTER_SHOP("105135", "任意活动新规权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),
  OPTIONAL_CAMPAGIN_UPDATE_SHOP("105136", "任意活动更新权限", MenuGroup.COMMUNICATION, PermissionType.SHOP),

  
  
  // 分析系
  /** 分析データ参照 - サイト */
  ANALYSIS_READ_SITE("006001", "分析データ参照", MenuGroup.ANALYSIS, PermissionType.SITE),
  /** 分析データ出力 - サイト */
  ANALYSIS_DATA_SITE("006004", "分析データ出力", MenuGroup.ANALYSIS, PermissionType.SITE),
  /** 分析データ参照 - ショップ */
  ANALYSIS_READ_SHOP("106001", "分析データ参照", MenuGroup.ANALYSIS, PermissionType.SHOP),
  /** 分析データ出力 - ショップ */
  ANALYSIS_DATA_SHOP("106004", "分析データ出力", MenuGroup.ANALYSIS, PermissionType.SHOP),
  // データIO系
  /** データ入出力アクセス - サイト */
  DATA_IO_ACCESS_SITE("007001", "データ入出力アクセス", MenuGroup.DATA_IO, PermissionType.SITE),
  /** データ入出力アクセス - ショップ */
  DATA_IO_ACCESS_SHOP("107001", "データ入出力アクセス", MenuGroup.DATA_IO, PermissionType.SHOP),
  
  // 20131023 txw add start
  /** 首页TOP内容管理参照权限 */
  INDEX_TOP_IMAGE_READ("010011", "首页TOP内容管理参照权限", MenuGroup.CONTENT, PermissionType.SITE),
  /** 首页TOP内容管理登録/更新权限 */
  INDEX_TOP_IMAGE_UPDATE("010012", "首页TOP内容管理登録/更新权限", MenuGroup.CONTENT, PermissionType.SITE),

  /** 首页楼层内容管理参照权限 */
  INDEX_FLOOR_READ("011011", "首页楼层内容管理参照权限", MenuGroup.CONTENT, PermissionType.SITE),
  /** 首页楼层内容管理登録/更新权限 */
  INDEX_FLOOR_UPDATE("011012", "首页楼层内容管理登録/更新权限", MenuGroup.CONTENT, PermissionType.SITE),
  // 20131023 txw add end

  // 20111208 yl add start
  // 客服会员咨询待机管理
  /** 参照电话中心待机管理 */
  SERVICE_USER_DATA_READ("008001", "参照客服中心管理", MenuGroup.SERVICE, PermissionType.SITE),
  /** 礼品卡退款权限 */
  SERVICE_GIFT_CARD_CONFIRM("008007", "礼品卡退款权限", MenuGroup.SERVICE, PermissionType.SITE),
  /** 参照咨询投诉管理 */
  SERVICE_COMPLAINT_DATA_READ("008002", "参照咨询投诉管理", MenuGroup.SERVICE, PermissionType.SITE),
  /** 登录/更新咨询投诉管理 */
  SERVICE_COMPLAINT_DATA_UPDATE("008003", "登录/更新咨询投诉管理", MenuGroup.SERVICE, PermissionType.SITE),
  /** 删除咨询投诉 */
  SERVICE_COMPLAINT_DATA_DELETE("008004", "删除咨询投诉", MenuGroup.SERVICE, PermissionType.SITE),
  /** 导入导出咨询投诉管理数据 */

  SERVICE_COMPLAINT_DATA_EXPORT("008005", "导入导出咨询投诉管理数据", MenuGroup.SERVICE, PermissionType.SITE),

  // 20131106 txw add start
  /** 客服中心退款权限 */
  MEMBER_INFO_REFUND("008006", "客服中心退款权限", MenuGroup.SERVICE, PermissionType.SITE);
  // 20131106 txw add end
  // 20111208 yl add end
  
  //2015-01-04 zzy add start
  /**虚假订单关键词管理权限*/
  //2015-01-04 zzy add end

  
  private String code;

  private String name;

  private PermissionType permissionType;

  private MenuGroup menuGroup;

  private Permission() {
  }

  private Permission(String code, String name, MenuGroup group, PermissionType permissionType) {
    this.code = code;
    this.name = name;
    this.menuGroup = group;
    this.permissionType = permissionType;
  }

  /**
   * 権限コードを返します。
   * 
   * @return 権限コード
   */
  public String getCode() {
    return StringUtil.coalesce(CodeUtil.getCode(this), this.code);
  }

  /**
   * メニューグループを返します。
   * 
   * @return メニューグループ
   */
  public MenuGroup getMenuGroup() {
    return menuGroup;
  }

  /**
   * 権限名を返します。
   * 
   * @return 権限名
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * 権限区分を返します。
   * 
   * @return 権限区分
   */
  public PermissionType getPermissionType() {
    return permissionType;
  }

  /**
   * コード値(権限コード)を返します。
   * 
   * @return 権限コード
   */
  public String getValue() {
    return getCode();
  }

  /**
   * この権限がサイト管理権限かどうかを返します。
   * 
   * @return サイト管理権限ならtrue
   */
  public boolean isSite() {
    return getPermissionType().equals(PermissionType.SITE);
  }

  /**
   * この権限がショップ管理権限かどうかを返します。
   * 
   * @return ショップ管理権限ならtrue
   */
  public boolean isShop() {
    return getPermissionType().equals(PermissionType.SHOP);
  }

  /**
   * 管理ユーザーにこの権限があるかどうかを返します。
   * 
   * @param login
   *          管理ユーザログイン
   * @return 権限があればtrue
   */
  public boolean isGranted(LoginInfo login) {
    if (login == null) {
      return false;
    }
    return login.hasPermission(this);
  }

  /**
   * 管理ユーザーがこの権限を利用不能どうかを返します。
   * 
   * @param login
   *          管理ユーザログイン
   * @return 権限がなければtrue
   */
  public boolean isDenied(LoginInfo login) {
    return !isGranted(login); // login.hasPermission(this);
  }

  public static Permission[] getPermissionSet(OperatingMode mode) {
    switch (mode) {
      case MALL:
        return ArrayUtil.immutableCopy(MODE_MALL_PERMISSIONS);
      case SHOP:
        return ArrayUtil.immutableCopy(MODE_SHOP_PERMISSIONS);
      case ONE:
        return ArrayUtil.immutableCopy(MODE_ONE_PERMISSIONS);
      default:
        return new Permission[0];
    }
  }

  private static final Permission[] MODE_MALL_PERMISSIONS = new Permission[] {
      /** サイト管理者 */
      SITE_MANAGER,
      /** ショップ管理者 */
      SHOP_MANAGER,
      /** 受注入金管理参照 */
      ORDER_READ_SITE,
      /** 受注入金管理登録/更新 */
      ORDER_UPDATE_SITE,
      /** 受注入金管理変更 */
      ORDER_MODIFY_SITE,
      /** 受注入金管理データ入出力 */
      ORDER_DATA_IO_SITE,
      /** 出荷管理参照 */
      SHIPPING_READ_SITE,
      /** 出荷管理更新 */
      SHIPPING_UPDATE_SITE,
      /** 出荷管理データ入出力 */
      SHIPPING_DATA_IO_SITE,
      CONFIRM_OBJECT_IO_SITE,
      /** 出荷管理参照 */
      SHIPPING_READ_SHOP,
      /** 出荷管理更新 */
      SHIPPING_UPDATE_SHOP,
      /** 出荷管理データ入出力 */
      SHIPPING_DATA_IO_SHOP,
      CONFIRM_OBJECT_IO_SHOP,
      UNTRUE_ORDER_WORD,
      
      /** 顧客管理参照 */
      CUSTOMER_READ,
      /** 顧客管理登録/更新 */
      CUSTOMER_UPDATE,
      /** 顧客管理削除 */
      CUSTOMER_DELETE,
      /** 顧客管理データ入出力 */
      CUSTOMER_IO,
      /** ポイント利用状況参照 */
      CUSTOMER_POINT_READ,
      /** ポイント利用状況データ入出力 */
      CUSTOMER_POINT_IO,
      /** ポイント付与 */
      CUSTOMER_POINT_INVEST, CUSTOMER_COUPON_INVEST,
      /** 优惠券利用状況参照 */
      CUSTOMER_COUPON_READ, CUSTOMER_COUPON_IO,
      /** 無効ポイント削除 */
      CUSTOMER_POINT_DELETE,
      /** 商品管理参照 */
      CATALOG_READ,
      /** カテゴリ管理参照 */
      CATEGORY_READ,
      /** カテゴリ管理登録/更新 */
      CATEGORY_UPDATE,
      /** カテゴリ管理削除 */
      CATEGORY_DELETE,
      /** カテゴリ管理データ入出力 */
      CATEGORY_DATA_IO,
      /** 商品管理参照 */
      COMMODITY_READ,
      /** 商品管理登録/更新 */
      COMMODITY_UPDATE,
      /** 商品管理削除 */
      COMMODITY_DELETE,
      /** 商品改价审核 */
      COMMODITY_PRICE_CHANGE,
      COMMODITY_MASTER_LIST,
      /** 商品管理データ入出力 */
      COMMODITY_DATA_IO,
      /** 在庫管理参照 */
      STOCK_MANAGEMENT_READ,
      /** 在庫管理登録/更新 */
      STOCK_MANAGEMENT_UPDATE,
      /** 在庫管理データ取込 */
      STOCK_MANAGEMENT_DATA_IO,
      /** ショップ管理参照 */
      SHOP_MANAGEMENT_READ_SITE,
      /** ショップ管理登録/更新 */
      SHOP_MANAGEMENT_UPDATE_SITE,
      /** ショップ管理削除 */
      SHOP_MANAGEMENT_DELETE_SITE,
      /** ショップ管理データ入出力 */
      SHOP_MANAGEMENT_IO_SITE,
      /** ショップ管理参照 */
      SHOP_MANAGEMENT_READ_SHOP,
      /** ショップ管理登録/更新 */
      SHOP_MANAGEMENT_UPDATE_SHOP,
      /** ショップ管理削除 */
      SHOP_MANAGEMENT_DELETE_SHOP,
      /** ショップ管理データ入出力 */
      SHOP_MANAGEMENT_IO_SHOP,
      // add by shikui start 2010/04/28
      // /** 在线客服参照 */
      // ONlINE_READ_SITE,
      // /** 在线客服登録/更新 */
      // ONlINE_UPDATE_SITE,
      // GOOGLE_ANALYSIS_SITE,
      // /** Google Analysis 登録/更新*/
      // GOOGLE_ANALYSIS_UPDATE_SITE,
      // // add by shikui end 2010/04/28
      /** キャンペーン参照 */
      CAMPAIGN_READ_SITE,
      /** キャンペーンデータ入出力 */
      CAMPAIGN_DATA_IO_SITE,
      /** アンケート参照 */
      ENQUETE_READ_SITE,
      /** アンケート登録/更新 */
      ENQUETE_UPDATE_SITE,
      /** アンケート削除 */
      ENQUETE_DELETE_SITE,
      /** アンケートデータ入出力 */
      ENQUETE_DATA_IO_SITE,
      /** レビュー参照 */
      REVIEW_READ_SITE,
      /** レビュー登録/更新 */
      REVIEW_UPDATE_SITE,
      /** レビュー削除 */
      REVIEW_DELETE_SITE,
      /** メールマガジン参照 */
      MAIL_MAGAZINE_READ_SITE,
      /** メールマガジン登録/更新 */
      MAIL_MAGAZINE_UPDATE_SITE,
      /** メールマガジン削除 */
      MAIL_MAGAZINE_DELETE_SITE,
      /** メールマガジンデータ入出力 */
      MAIL_MAGAZINE_IO_SITE,
      /** キャンペーン参照 */
      CAMPAIGN_READ_SHOP,
      /** キャンペーン登録/更新 */
      CAMPAIGN_UPDATE_SHOP,
      /** キャンペーン削除 */
      CAMPAIGN_DELETE_SHOP,
      /** キャンペーンデータ入出力 */
      CAMPAIGN_DATA_IO_SHOP,
      /** 顾客组别优惠规则查看权限 */
      CUSTOMER_GROUP_CAMPAIGN_READ_SHOP,
      /** 顾客组别优惠规则新建更新权限 */
      CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP,
      /** 顾客组别优惠规则删除权限 */
      CUSTOMER_GROUP_CAMPAIGN_DELETE_SHOP,
      /** 顾客别优惠券发行规则查看权限 */
      PRIVATE_COUPON_READ_SHOP,
      /** 顾客别优惠券发行规则更新权限 */
      PRIVATE_COUPON_UPDATE_SHOP,
      /** 顾客别优惠券发行规则删除权限 */
      PRIVATE_COUPON_DELETE_SHOP,
      /** 媒体优惠代码发行规则查看权限 */
      PUBLIC_COUPON_READ_SHOP,
      /** 媒体优惠代码发行规则更新权限 */
      PUBLIC_COUPON_UPDATE_SHOP,
      /** 媒体优惠代码发行规则删除权限 */
      PUBLIC_COUPON_DELETE_SHOP,
      /** レビュー参照 */
      REVIEW_READ_SHOP,
      /** レビュー登録/更新 */
      REVIEW_UPDATE_SHOP,
      /** レビュー削除 */
      REVIEW_DELETE_SHOP,
      /** 分析データ参照 */
      ANALYSIS_READ_SITE,
      /** 分析データ出力 */
      ANALYSIS_DATA_SITE,
      /** 分析データ参照 */
      ANALYSIS_READ_SHOP,
      /** 分析データ出力 */
      ANALYSIS_DATA_SHOP,
      /** データ入出力アクセス */
      DATA_IO_ACCESS_SITE,
      /** データ入出力アクセス */
      DATA_IO_ACCESS_SHOP
      // 20131023 txw add start
      /** 首页TOP内容管理参照权限 */
      ,INDEX_TOP_IMAGE_READ,
      /** 首页TOP内容管理登録/更新权限 */
      INDEX_TOP_IMAGE_UPDATE
      /** 首页楼层内容管理参照权限 */
      ,INDEX_FLOOR_READ,
      /** 首页楼层内容管理登録/更新权限 */
      INDEX_FLOOR_UPDATE,
      // 20131023 txw add end
      
      // 20141023 hdh add start
      SHIPPING_DATA_IO_SHOP,
      // 20141023 hdh add end
      UNTRUE_ORDER_WORD
  };

  private static final Permission[] MODE_SHOP_PERMISSIONS = new Permission[] {
      /** サイト管理者 */
      SITE_MANAGER,
      /** ショップ管理者 */
      SHOP_MANAGER,
      /** 受注入金管理参照 */
      ORDER_READ_SITE,
      /** 受注入金管理登録/更新 */
      ORDER_UPDATE_SITE,
      /** 受注入金管理変更 */
      ORDER_MODIFY_SITE,
      /** 受注入金管理データ入出力 */
      ORDER_DATA_IO_SITE,
      /** 出荷管理参照 */
      SHIPPING_READ_SITE,
      /** 出荷管理更新 */
      SHIPPING_UPDATE_SITE,
      /** 出荷管理データ入出力 */
      SHIPPING_DATA_IO_SITE,
      CONFIRM_OBJECT_IO_SITE,
      /** 受注入金管理参照 */
      ORDER_READ_SHOP,
      /** 受注入金管理登録/更新 */
      ORDER_UPDATE_SHOP,
      /** 受注入金管理変更 */
      ORDER_MODIFY_SHOP,
      /** 受注入金管理データ入出力 */
      ORDER_DATA_IO_SHOP,
      /** 出荷管理参照 */
      SHIPPING_READ_SHOP,
      /** 出荷管理更新 */
      SHIPPING_UPDATE_SHOP,
      /** 出荷管理データ入出力 */
      SHIPPING_DATA_IO_SHOP,
      CONFIRM_OBJECT_IO_SHOP,
      UNTRUE_ORDER_WORD,
      /** 顧客管理参照 */
      CUSTOMER_READ,
      /** 顧客管理登録/更新 */
      CUSTOMER_UPDATE,
      /** 顧客管理削除 */
      CUSTOMER_DELETE,
      /** 顧客管理データ入出力 */
      CUSTOMER_IO,
      /** ポイント利用状況参照 */
      CUSTOMER_POINT_READ,
      /** ポイント利用状況データ入出力 */
      CUSTOMER_POINT_IO,
      /** ポイント付与 */
      /** 优惠券利用状況参照 */
      CUSTOMER_COUPON_READ, CUSTOMER_POINT_INVEST, CUSTOMER_COUPON_INVEST, CUSTOMER_COUPON_READ, CUSTOMER_COUPON_IO,
      // CUSTOMER_COUPON_DELETE,
      /** 無効ポイント削除 */
      CUSTOMER_POINT_DELETE,
      /** 顧客管理参照 */
      CUSTOMER_READ_SHOP,
      /** 商品管理参照 */
      CATALOG_READ,
      /** カテゴリ管理参照 */
      CATEGORY_READ,
      /** カテゴリ管理登録/更新 */
      CATEGORY_UPDATE,
      /** カテゴリ管理削除 */
      CATEGORY_DELETE,
      /** カテゴリ管理データ入出力 */
      CATEGORY_DATA_IO,
      /** 商品管理参照 */
      COMMODITY_READ,
      /** 商品管理登録/更新 */
      COMMODITY_UPDATE,
      /** 商品管理削除 */
      COMMODITY_DELETE,
      /** 商品管理データ入出力 */
      COMMODITY_DATA_IO,
      /** 商品改价审核 */
      COMMODITY_PRICE_CHANGE,
      COMMODITY_MASTER_LIST,
      /** 在庫管理参照 */
      STOCK_MANAGEMENT_READ,
      /** 在庫管理登録/更新 */
      STOCK_MANAGEMENT_UPDATE,
      /** 在庫管理データ取込 */
      STOCK_MANAGEMENT_DATA_IO,
      /** ショップ管理参照 */
      SHOP_MANAGEMENT_READ_SITE,
      /** ショップ管理登録/更新 */
      SHOP_MANAGEMENT_UPDATE_SITE,
      /** ショップ管理削除 */
      SHOP_MANAGEMENT_DELETE_SITE,
      /** ショップ管理データ入出力 */
      SHOP_MANAGEMENT_IO_SITE,
      /** ショップ管理参照 */
      SHOP_MANAGEMENT_READ_SHOP,
      /** ショップ管理登録/更新 */
      SHOP_MANAGEMENT_UPDATE_SHOP,
      /** ショップ管理削除 */
      SHOP_MANAGEMENT_DELETE_SHOP,
      /** ショップ管理データ入出力 */
      SHOP_MANAGEMENT_IO_SHOP,
      // add by shikui start 2010/04/28
      // /** 在线客服参照 */
      // ONlINE_READ_SITE,
      // /** 在线客服登録/更新 */
      // ONlINE_UPDATE_SITE,
      // GOOGLE_ANALYSIS_SITE,
      // /** Google Analysis 登録/更新*/
      // GOOGLE_ANALYSIS_UPDATE_SITE,
      //      
      // add by shikui end 2010/04/28
      /** キャンペーン参照 */
      CAMPAIGN_READ_SITE,
      /** キャンペーンデータ入出力 */
      CAMPAIGN_DATA_IO_SITE,
      /** アンケート参照 */
      ENQUETE_READ_SITE,
      /** アンケート登録/更新 */
      ENQUETE_UPDATE_SITE,
      /** アンケート削除 */
      ENQUETE_DELETE_SITE,
      /** アンケートデータ入出力 */
      ENQUETE_DATA_IO_SITE,
      /** レビュー参照 */
      REVIEW_READ_SITE,
      /** レビュー登録/更新 */
      REVIEW_UPDATE_SITE,
      /** レビュー削除 */
      REVIEW_DELETE_SITE,
      /** メールマガジン参照 */
      MAIL_MAGAZINE_READ_SITE,
      /** メールマガジン登録/更新 */
      MAIL_MAGAZINE_UPDATE_SITE,
      /** メールマガジン削除 */
      MAIL_MAGAZINE_DELETE_SITE,
      /** メールマガジンデータ入出力 */
      MAIL_MAGAZINE_IO_SITE,
      /** キャンペーン参照 */
      CAMPAIGN_READ_SHOP,
      /** キャンペーン登録/更新 */
      CAMPAIGN_UPDATE_SHOP,
      /** キャンペーン削除 */
      CAMPAIGN_DELETE_SHOP,
      /** キャンペーンデータ入出力 */
      CAMPAIGN_DATA_IO_SHOP,
      /** 顾客组别优惠规则查看权限 */
      CUSTOMER_GROUP_CAMPAIGN_READ_SHOP,
      /** 顾客组别优惠规则新建更新权限 */
      CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP,
      /** 顾客组别优惠规则删除权限 */
      CUSTOMER_GROUP_CAMPAIGN_DELETE_SHOP,
      /** 顾客别优惠券发行规则查看权限 */
      PRIVATE_COUPON_READ_SHOP,
      /** 顾客别优惠券发行规则更新权限 */
      PRIVATE_COUPON_UPDATE_SHOP,
      /** 顾客别优惠券发行规则删除权限 */
      PRIVATE_COUPON_DELETE_SHOP,
      /** 媒体优惠代码发行规则查看权限 */
      PUBLIC_COUPON_READ_SHOP,
      /** 媒体优惠代码发行规则更新权限 */
      PUBLIC_COUPON_UPDATE_SHOP,
      /** 媒体优惠代码发行规则删除权限 */
      PUBLIC_COUPON_DELETE_SHOP,
      /** レビュー参照 */
      REVIEW_READ_SHOP,
      /** レビュー登録/更新 */
      REVIEW_UPDATE_SHOP,
      /** レビュー削除 */
      REVIEW_DELETE_SHOP,
      /** 分析データ参照 */
      ANALYSIS_READ_SITE,
      /** 分析データ出力 */
      ANALYSIS_DATA_SITE,
      /** 分析データ参照 */
      ANALYSIS_READ_SHOP,
      /** 分析データ出力 */
      ANALYSIS_DATA_SHOP,
      /** データ入出力アクセス */
      DATA_IO_ACCESS_SITE,
      /** データ入出力アクセス */
      DATA_IO_ACCESS_SHOP
      // 20131023 txw add start
      /** 首页TOP内容管理参照权限 */
      ,INDEX_TOP_IMAGE_READ,
      /** 首页TOP内容管理登録/更新权限 */
      INDEX_TOP_IMAGE_UPDATE
      /** 首页楼层内容管理参照权限 */
      ,INDEX_FLOOR_READ,
      /** 首页楼层内容管理登録/更新权限 */
      INDEX_FLOOR_UPDATE,
      // 20131023 txw add end
      
      // 20141023 hdh add start
      SHIPPING_DATA_IO_SHOP,
      // 20141023 hdh add end
      UNTRUE_ORDER_WORD,
  };

  private static final Permission[] MODE_ONE_PERMISSIONS = new Permission[] {
      /** 管理者 */
      SITE_MANAGER,
      /** 受注系 */
      ORDER_READ_SITE,
      ORDER_UPDATE_SITE,
      ORDER_MODIFY_SITE,
      ORDER_DATA_IO_SITE,
      SHIPPING_READ_SITE,
      SHIPPING_UPDATE_SITE,
      SHIPPING_DATA_IO_SITE,
      CONFIRM_OBJECT_IO_SITE,
      /** 参照退换货管理 **/
      // RETURN_DATA_MANAGEMENT,
      // RETURN_DATA_READ,
      // RETURN_GOODS_DATA_CONFIRM,
      // RETURN_REFUND_DATA_CONFIRM,
      // RETURN_COMPLETE_DATA,
      // RETURN_CONTACT_DATA,
      // RETURN_DATA_CANCEL ,
      // RETURN_DATA_DELETE,
      // RETURN_DATA_EXPORT,
      /** 顧客系 */
      CUSTOMER_READ, CUSTOMER_UPDATE, CUSTOMER_DELETE, CUSTOMER_IO, CUSTOMER_POINT_READ, CUSTOMER_POINT_IO,
      CUSTOMER_POINT_INVEST,
      CUSTOMER_POINT_DELETE,
      CUSTOMER_COUPON_READ,
      CUSTOMER_COUPON_IO,
      CUSTOMER_COUPON_INVEST,// CUSTOMER_COUPON_DELETE,
      /** 商品系 */
      CATEGORY_READ, CATEGORY_UPDATE, CATEGORY_DELETE, CATEGORY_DATA_IO, CATALOG_READ, COMMODITY_UPDATE, COMMODITY_DELETE,
      COMMODITY_DATA_IO, STOCK_MANAGEMENT_READ, STOCK_MANAGEMENT_UPDATE, STOCK_MANAGEMENT_DATA_IO, COMMODITY_PRICE_CHANGE,
      COMMODITY_MASTER_LIST,
      /** ショップ管理系 */
      // 20130614 txw add start
      /** 组合商品登录 */
      COMMODITY_CONSTITUTE_READ,COMMODITY_CONSTITUTE_UPDATE,COMMODITY_CONSTITUTE_DELETE,
      // 20130614 txw add end
      SHOP_MANAGEMENT_READ_SITE, SHOP_MANAGEMENT_UPDATE_SITE,
      SHOP_MANAGEMENT_DELETE_SITE,
      SHOP_MANAGEMENT_IO_SITE,
      // 20111208 lirong add start
      /** 客服会员咨询待机管理 */
      SERVICE_USER_DATA_READ,
      SERVICE_GIFT_CARD_CONFIRM,
      SERVICE_COMPLAINT_DATA_READ,
      SERVICE_COMPLAINT_DATA_UPDATE,
      SERVICE_COMPLAINT_DATA_DELETE,
      SERVICE_COMPLAINT_DATA_EXPORT,
      MEMBER_INFO_REFUND,
      // 20111208 lirong add start
      // soukai add ob 2011/12/14 shb start */
      CUSTOMER_GROUP_CAMPAIGN_READ_SHOP,
      /** 顾客组别优惠规则新建更新权限 */
      CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP,
      /** 顾客组别优惠规则删除权限 */
      CUSTOMER_GROUP_CAMPAIGN_DELETE_SHOP,
      /** 顾客别优惠券发行规则查看权限 */
      PRIVATE_COUPON_READ_SHOP,
      /** 顾客别优惠券发行规则更新权限 */
      PRIVATE_COUPON_UPDATE_SHOP,
      /** 顾客别优惠券发行规则删除权限 */
      PRIVATE_COUPON_DELETE_SHOP,
      /** 媒体优惠代码发行规则查看权限 */
      PUBLIC_COUPON_READ_SHOP,
      /** 媒体优惠代码发行规则更新权限 */
      PUBLIC_COUPON_UPDATE_SHOP,
      /** 媒体优惠代码发行规则删除权限 */
      PUBLIC_COUPON_DELETE_SHOP,
      // soukai add ob 2011/12/20 start
      /** 优惠券履历导入权限 */
      PUBLIC_COUPON_DATA_IO,
      // soukai add ob 2011/12/20 end
      /** 企划查看权限 */
      PLAN_READ_SHOP,
      /** 企划新建更新权限 */
      PLAN_UPDATE_SHOP,
      /** 企划删除权限 */
      PLAN_DELETE_SHOP,
      /** 好友推荐优惠券查看权限 */
      FriendCouponRule_READ_SHOP,
      /** 好友推荐优惠券更新权限 */
      FriendCouponRule_UPDATE_SHOP,
      /** 好友推荐优惠券删除权限 */
      FriendCouponRule_DELETE_SHOP,
      // soukai add ob 2011/12/14 shb end */
      /** コミュニケーション系 */
      CAMPAIGN_READ_SITE, CAMPAIGN_UPDATE_SITE, CAMPAIGN_DELETE_SITE, CAMPAIGN_DATA_IO_SITE, ENQUETE_READ_SITE,
      ENQUETE_UPDATE_SITE, ENQUETE_DELETE_SITE, ENQUETE_DATA_IO_SITE, REVIEW_READ_SITE, REVIEW_UPDATE_SITE, REVIEW_DELETE_SITE,
      MAIL_MAGAZINE_READ_SITE, MAIL_MAGAZINE_UPDATE_SITE, MAIL_MAGAZINE_DELETE_SITE, MAIL_MAGAZINE_IO_SITE,
      /** 分析系 */
      ANALYSIS_READ_SITE, ANALYSIS_DATA_SITE
  /** データ入出力系 */
  // DATA_IO_ACCESS_SITE
      /** 20130729 txw add start */
      ,DISCOUNT_READ_SHOP,
      DISCOUNT_UPDATE_SHOP,
      DISCOUNT_DELETE_SHOP
      /** 20130729 txw add end */
      // zhangfeng 2014/4/11 add begin
      ,MESSAGE_READ_SHOP,
      MESSAGE_DELETE_SHOP
      // zhangfeng 2014/4/11 add end
      /** 20131011 txw add start */
      ,FREE_POSTAGE_READ_SHOP,
      FREE_POSTAGE_UPDATE_SHOP,
      FREE_POSTAGE_DELETE_SHOP,
      /** 20131011 txw add end */
      GIFT_CARD_RULE_IO,
      // 20131023 wz add start
      
      GIFT_CARD_RULE_READ_SHOP,
      GIFT_CARD_RULE_UPDATE_SHOP,
      GIFT_CARD_LOG_IO,
      GIFT_CARD_USE_LOG_IO,
      PROPAGANDA_ACTIVITY_RULE_READ_SHOP,
      PROPAGANDA_ACTIVITY_RULE_UPDATE_SHOP,
      PROPAGANDA_ACTIVITY_RULE_DELETE_SHOP,
      // 20131023 txw add end
      // 20131023 txw add start
      // 20140804 hdh add start
      OPTIONAL_CAMPAGIN_READ_SHOP,
      OPTIONAL_CAMPAGIN_REGISTER_SHOP,
      OPTIONAL_CAMPAGIN_UPDATE_SHOP,
      // 20140804 hdh add end
      /** 首页TOP内容管理参照权限 */
      INDEX_TOP_IMAGE_READ,
      /** 首页TOP内容管理登録/更新权限 */
      INDEX_TOP_IMAGE_UPDATE,
      /** 首页楼层内容管理参照权限 */
      INDEX_FLOOR_READ,
      /** 首页楼层内容管理登録/更新权限 */
      INDEX_FLOOR_UPDATE,
      // 20131023 txw add end
      
      // 20141023 hdh add start
      SHIPPING_DATA_IO_SHOP,
      // 20141023 hdh add end
      //20150104  zzy add start
      UNTRUE_ORDER_WORD
      //20150104  zzy add end
  };

}
