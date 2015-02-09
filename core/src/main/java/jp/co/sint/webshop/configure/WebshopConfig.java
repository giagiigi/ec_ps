package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.sint.webshop.data.domain.ApplicationServerOS;
import jp.co.sint.webshop.data.domain.ApplicationServerType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.RdbmsType;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * SI Web Shopping 10システム全体の設定情報です。
 * 起動時にapplicationContext-configure.xmlの内容を読み込んで初期化します。
 * 
 * @author System Integrator Corp.
 */
public class WebshopConfig implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  /** サイト管理者ショップコード */
  private String siteShopCode = "00000000";

  /** サイト管理者ユーザーコード */
  private String siteUserCode = "0";

  /** 首页显示商品条数 */
  private String indexCommodityLimit = "4";

  private BigDecimal outerCardRate = new BigDecimal("0.967");

  private BigDecimal outerCardTop = new BigDecimal("1000");

  /** 首页显示商品标签 */

  // 2012/11/21 促销对应 ob add start
  /** 套餐明細条数 */
  private String indexCompositionLimit = "15";

  // 2012/11/21 促销对应 ob add end

  /** 首页显示商品标签 */
  private String indexTagCode = "4";

  // 20130321 add by yyq start
  /** 详细页明星商品标签 */
  private String detailStarTagCode = "8";

  /** 详细页明星商品条数 */
  private String detailStarCommodityLimit = "8";

  // 20130321 add by yyq end

  /** 首页分类商品，根据tag查询 */
  private String tagCategoryCommodityLimit = "4";

  /** パスワード再発行メール有効期間 (分指定) */
  private int reminderTimeLimit = 10;

  /** 顧客認証エラー最大回数 */
  private int authorizeCustomerErrorMaxCount = 5;

  /** 管理ユーザ認証エラー最大回数 */
  private int authorizeUserErrorMaxCount = 5;

  /** カテゴリ階層最大値 */
  private int categoryMaxDepth = 5;

  /** カテゴリ属性項目数 */
  private int categoryAttributeMaxCount = 3;

  /** 関連商品表示件数(PC/1行) */
  private int recommendCommodityMaxLineCount = 4;

  /** 関連商品の最大表示件数(PC) */
  private int recommendCommodityMaxCount = 12;

  /** 関連商品の最大表示件数(携帯) */
  private int recommendCommodityMaxCountMobile = 3;

  /** 人気順表示モード(受注件数または購入数) */
  private boolean sortedOrderAmount = true;

  /** 人気順取得件数 */
  private int rankingCommodityMaxCount = 5;

  /** 礼品卡密码输入错误次数 */
  private int errorTimes = 5;

  /** 人気順おすすめ商品取得件数 */
  private int recommendRankingMaxCount = 10;

  /** まとめてカート使用有無 */
  private boolean useBlanketCart = true;

  /** CSV取込時のユーザパスワード暗号化使用有無 */
  private boolean useCustomerPassEncrypt = true;

  private int frontPageWidth = 800;

  private int backPageWidth = 800;

  private int orderHeadOff = 1000;

  private OperatingMode operatingMode = OperatingMode.SHOP;

  private ApplicationServerOS applicationServerOS = ApplicationServerOS.WINDOWS;

  private ApplicationServerType applicationServerType = ApplicationServerType.TOMCAT;

  private RdbmsType rdbmsType = RdbmsType.ORACLE_10G;

  private int dataIOBufferSize;

  /** プレビュー画面URL */
  private String previewUrl;

  /** フロントトップページURL(コンテキストまで) */
  private String topPageUrl;

  /** モバイルトップページURL(コンテキストまで) */
  private String mobileTopPageUrl;

  /** フロントセキュアURL */
  // private String frontSecureUrl;
  /** 携帯セキュアURL */
  // private String mobileSecureUrl;
  private String hostName;

  private int httpPort = 80;

  private int httpsPort = 443;

  /** 管理側ホスト名 */
  private String backHostName;

  /** 管理側コンテキスト名 */
  private String backContext;

  /** 管理側HTTPポート番号 */
  private int backHttpPort;

  /** 管理側HTTPSポート番号 */
  private int backHttpsPort;

  /** フロント側ホスト名 */
  private String frontHostName;

  /** フロント理側コンテキスト名 */
  private String frontContext;

  /** フロント側HTTPポート番号 */
  private int frontHttpPort;

  /** フロント側HTTPSポート番号 */
  private int frontHttpsPort;

  /** 携帯側ホスト名 */
  private String mobileHostName;

  /** 携帯側コンテキスト名 */
  private String mobileContext;

  /** 携帯側HTTPポート番号 */
  private int mobileHttpPort;

  /** 携帯側HTTPSポート番号 */
  private int mobileHttpsPort;

  /** loggerホスト名 */
  private String loggerHostName;

  /** loggerコンテキスト名 */
  private String loggerContext;

  /** loggerHTTPポート番号 */
  private int loggerHttpPort;

  /** loggerHTTPSポート番号 */
  private int loggerHttpsPort;

  /** キャッシュタイムアウト時間(分) */
  private int defaultCacheTimeout = 60;

  private int defaultCookieMaxAge = 60 * 60 * 24 * 10;

  private String csvCharset;

  private String logCharset;

  /** 最短お届け日から、お届け日時に選べる日数 */
  private int deliveryAppointedPeriod = 14;

  private BigDecimal defaultShippingCharge = new BigDecimal("1000");

  private BigDecimal defaultMaxUseOrderAmount = new BigDecimal("99999999");

  // private int defaultLeadTime = 0;
  private int minimalLeadTime = 1;

  /** アップロードファイル拡張子不正エラー */
  private String allowedUploadExtention;

  /** メールマガジン申し込み機能使用可否モード(true:使用する、false:使用しない) */
  private boolean mailMagazineAvailableMode;

  // modify by v10-cn 170 start
  private HelpMapContainer helpMessages = new HelpMapContainer();

  // modify by v10-cn 170 end

  /** おすすめ商品表示件数(PC) */
  private int customerRecommendCommodityMaxLineCount = 10;

  /** おすすめ商品表示件数(携帯) */
  private int customerRecommendCommodityMaxLineCountMobile = 5;

  /** 検索ワード最大文字数 */
  private int searchWordMaxLength = 50;

  // 10.1.1 10006,10016 追加 ここから
  /** アプリケーション内最小年 */
  private int applicationMinYear = 2005;

  /** アプリケーション内最大年 */
  private int applicationMaxYear = 2020;

  /** 购物车免运费阀值 */
  private int aboveCartTip = 200;

  // add by zhanghaibin start 2010-05-18
  /** 积分倍数 */
  private int pointMultiple = 10000;

  /** ポイントフォマート */
  private String pointFormat = "";

  /** ポイントフォマート */
  private BigDecimal rmbPointRate;

  private int mobilePageSize = 5;

  // 2012/03/20 add by os011 start
  /** 淘宝积分转换 */
  private BigDecimal tmallPointConvert;

  /** 产品图片地址 */
  private String productImgUrl;

  private String aboveUrl;

  // 20111215 shen add start
  private int displayDiscountRateLimit = 1;

  // 20111215 shen add end
  // 20111223 os012 ass start
  // 库存在分配临界值
  private Long criticalValue = 0L;

  // 库存在分配比例
  private Long shareRatioRate = 0L;

  // 库存分配剩余后，分配方选择
  private String remainAssignSwitch = "";

  // 20111223 os012 ass end
  // 2014/06/11 库存更新对应 ob_yuan update start
  private String stockRatio = "100:0:0";

  // 2014/06/11 库存更新对应 ob_yuan update end
  // 20120104 os011 add start
  // 共通消費計量単位
  private String ifCmdtyUm = "";

  // 共通Site值
  private String ifSite = "";

  // 共通良品LOCATION
  private String ifLocationForEc = "";

  // 发货指示信息导出T-Mall/EC区分
  private String ifSoChannelEc = "";

  private String ifSoChannelTmall = "";

  // 赠品默认分类
  private String giftDefCategory = "";

  // 信贷条件
  private List<String> creditTerms = new ArrayList<String>();

  // 发货指示信息导出运费订单SKU
  private List<String> ifSkuKbnList = new ArrayList<String>();

  // 淘宝上架延时时间
  private int onTmallMinute = 0;

  // ECゲスト注文の顧客コード
  private String ifCustCdEcGuest = "";

  // TM注文の顧客コード
  private String ifCustCdTmall = "";

  private int customerLvMonth;

  // 20120217 shen add start
  private List<String> invoiceCommodityNameList = new ArrayList<String>();

  // 20120217 shen add end.

  // 20120217 yyq add start desc:淘宝发票商品规格
  private String tmallInvoiceCommodityName = "";

  // 20120217 yyq add end

  private List<String> designHoliDayYesterday = new ArrayList<String>();

  // 2013-03-28 yyq add start desc:配送指定特殊休息日
  private List<String> deliveryDateDesignHoliDay = new ArrayList<String>();

  private List<String> interceptDeliveryCompanyList = new ArrayList<String>();

  private String groupBuyingStartTime = "";

  private String groupBuyingEndTime = "";

  private List<String> groupBuyingSkuList = new ArrayList<String>();

  // 2013-07-30 twh add end

  // 2013-03-28 yyq add start desc:配送指定休息日（星期几）
  private List<Integer> deliveryDateWeekDay = new ArrayList<Integer>();

  // 2013-03-28 yyq add end

  private String commodityMessage;

  private String stockMessage;

  private List<String> customerGroupType;

  // 2013/04/01 优惠券对应 ob add start

  // 手机验证码有效时间
  private int authcodeTimeout;

  // 朋友优惠劵抽出条件
  private String friendCouponCodeDay;

  // 优惠劵有效结束时间
  private int couponUseEndDay;

  // 最大购买金额
  private Long maxUseOrderAmount;

  // 2013/04/01 优惠券对应 ob add end

  private String couponUrl;

  private int freeSearchCategoryListNum;

  private int suggestSearchNum;

  private Long weight;

  private BigDecimal price;

  // 20131015 txw add start
  private String trackUrl;

  // 20131015 txw add end

  // 20131105 txw add start
  // 顾客礼品卡信息
  private String cardId;

  private String cardName;

  private int effectiveYears;

  // 20131105 txw add end

  // 20140227 txw add start
  private String nearMsgCn;

  private String nearMsgEn;

  private String nearMsgJp;

  // 20140227 txw add end

  // 京东订单拦截
  // 20140520 hdh add start
  private BigDecimal jdWeight1;

  private BigDecimal jdWeight2;

  // 其他地区
  private BigDecimal jdWeight3;

  private BigDecimal jdPrice1;

  private BigDecimal jdPrice2;

  // 20140520 hdh add end

  // 20140609 hdh add start
  // 京东对应EC特殊商品编号列表
  private List<String> jdSpeCommodityList = new ArrayList<String>();

  private List<String> jdSpeCommodityListTwo = new ArrayList<String>();

  // 20140609 hdh add end
  // 20140929 yyq add start
  private List<String> shNotCommodityList = new ArrayList<String>();

  // 20140929 yyq add end
  private String csvBackupTemp;

  private String csvBackup;

  // 显示商品关联品牌数量
  private Long showRelatedBrandNum;

  // 显示商品关联类目数量
  private Long showRelatedCategoryNum;

  /**
   * 取得手机验证码有效时间
   * 
   * @return
   */
  public int getAuthcodeTimeout() {
    return authcodeTimeout;
  }

  /**
   * 设定手机验证码有效时间
   * 
   * @param authcodeTimeout
   */
  public void setAuthcodeTimeout(int authcodeTimeout) {
    this.authcodeTimeout = authcodeTimeout;
  }

  // 2013/04/01 优惠券对应 ob add end
  public String getTmallInvoiceCommodityName() {
    return tmallInvoiceCommodityName;
  }

  public void setTmallInvoiceCommodityName(String tmallInvoiceCommodityName) {
    this.tmallInvoiceCommodityName = tmallInvoiceCommodityName;
  }

  // 20120227 yyq add start
  /** 运费模板设定 */
  private List<String> deliveryTemplate;

  // 20120227 yyq add end

  // 20120203 shen add start
  /** 商品出仓日期 */
  private Long commodityLeadTime = 2L;

  // 20120203 shen add end

  private int memoDate = 2;

  /** 商品出仓日期 */
  private Long commodityLeadTimeBefore = 0L;

  /** 商品出仓日期 */
  private Long commodityLeadTimeAfter = 1L;

  /** 商品出仓日期每日判断时间 */
  private String finalSysTime = "14:29:59";

  // 20120219 shen add start
  /** 出仓休息日 */
  private List<Integer> warehouseRestDay = new ArrayList<Integer>();

  /** 配送日指定区间 */
  private int deliveryDayRange = 7;

  // 20120219 shen add end

  public String getIfCustCdTmall() {
    return ifCustCdTmall;
  }

  public void setIfCustCdTmall(String ifCustCdTmall) {
    this.ifCustCdTmall = ifCustCdTmall;
  }

  public Long getShareRatioRate() {
    return shareRatioRate;
  }

  /**
   * @return the deliveryDateDesignHoliDay
   */
  public List<String> getDeliveryDateDesignHoliDay() {
    return deliveryDateDesignHoliDay;
  }

  /**
   * @param deliveryDateDesignHoliDay
   *          the deliveryDateDesignHoliDay to set
   */
  public void setDeliveryDateDesignHoliDay(List<String> deliveryDateDesignHoliDay) {
    this.deliveryDateDesignHoliDay = deliveryDateDesignHoliDay;
  }

  /**
   * @return the deliveryDateWeekDay
   */
  public List<Integer> getDeliveryDateWeekDay() {
    return deliveryDateWeekDay;
  }

  /**
   * @param deliveryDateWeekDay
   *          the deliveryDateWeekDay to set
   */
  public void setDeliveryDateWeekDay(List<Integer> deliveryDateWeekDay) {
    this.deliveryDateWeekDay = deliveryDateWeekDay;
  }

  /**
   * @return the detailStarTagCode
   */
  public String getDetailStarTagCode() {
    return detailStarTagCode;
  }

  /**
   * @param detailStarTagCode
   *          the detailStarTagCode to set
   */
  public void setDetailStarTagCode(String detailStarTagCode) {
    this.detailStarTagCode = detailStarTagCode;
  }

  /**
   * @return the detailStarCommodityLimit
   */
  public String getDetailStarCommodityLimit() {
    return detailStarCommodityLimit;
  }

  /**
   * @param detailStarCommodityLimit
   *          the detailStarCommodityLimit to set
   */
  public void setDetailStarCommodityLimit(String detailStarCommodityLimit) {
    this.detailStarCommodityLimit = detailStarCommodityLimit;
  }

  public void setShareRatioRate(Long shareRatioRate) {
    this.shareRatioRate = shareRatioRate;
  }

  /**
   * @return the commodityMessage
   */
  public String getCommodityMessage() {
    return commodityMessage;
  }

  public int getCustomerLvMonth() {
    return customerLvMonth;
  }

  public void setCustomerLvMonth(int customerLvMonth) {
    this.customerLvMonth = customerLvMonth;
  }

  public String getGiftDefCategory() {
    return giftDefCategory;
  }

  public void setGiftDefCategory(String giftDefCategory) {
    this.giftDefCategory = giftDefCategory;
  }

  /**
   * @param commodityMessage
   *          the commodityMessage to set
   */
  public void setCommodityMessage(String commodityMessage) {
    this.commodityMessage = commodityMessage;
  }

  public List<String> getIfSkuKbnList() {
    return ifSkuKbnList;
  }

  public void setIfSkuKbnList(List<String> ifSkuKbnList) {
    this.ifSkuKbnList = ifSkuKbnList;
  }

  public String getIfSoChannelEc() {
    return ifSoChannelEc;
  }

  public void setIfSoChannelEc(String ifSoChannelEc) {
    this.ifSoChannelEc = ifSoChannelEc;
  }

  public String getIfSoChannelTmall() {
    return ifSoChannelTmall;
  }

  public void setIfSoChannelTmall(String ifSoChannelTmall) {
    this.ifSoChannelTmall = ifSoChannelTmall;
  }

  public String getIfCmdtyUm() {
    return ifCmdtyUm;
  }

  public void setIfCmdtyUm(String ifCmdtyUm) {
    this.ifCmdtyUm = ifCmdtyUm;
  }

  public String getIfSite() {
    return ifSite;
  }

  public String getIfCustCdEcGuest() {
    return ifCustCdEcGuest;
  }

  public void setIfCustCdEcGuest(String ifCustCdEcGuest) {
    this.ifCustCdEcGuest = ifCustCdEcGuest;
  }

  public void setIfSite(String ifSite) {
    this.ifSite = ifSite;
  }

  public String getIfLocationForEc() {
    return ifLocationForEc;
  }

  public void setIfLocationForEc(String ifLocationForEc) {
    this.ifLocationForEc = ifLocationForEc;
  }

  // 20120104 os011 add end
  public String getAboveUrl() {
    return aboveUrl;
  }

  public void setAboveUrl(String aboveUrl) {
    this.aboveUrl = aboveUrl;
  }

  // add by zhanghaibin end 2010-05-18
  /**
   * applicationMaxYearを取得します。
   * 
   * @return applicationMaxYear
   */
  public int getApplicationMaxYear() {
    if (applicationMaxYear >= 2100 || applicationMaxYear < applicationMinYear) {
      applicationMaxYear = 2020;
    }
    return applicationMaxYear;
  }

  /**
   * applicationMaxYearを設定します。
   * 
   * @param applicationMaxYear
   *          applicationMaxYear
   */
  public void setApplicationMaxYear(int applicationMaxYear) {
    this.applicationMaxYear = applicationMaxYear;
  }

  /**
   * applicationMinYearを取得します。
   * 
   * @return applicationMinYear
   */
  public int getApplicationMinYear() {
    if (applicationMinYear <= 1970 || applicationMaxYear < applicationMinYear) {
      applicationMinYear = 2005;
    }
    return applicationMinYear;
  }

  /**
   * applicationMinYearを設定します。
   * 
   * @param applicationMinYear
   *          applicationMinYear
   */
  public void setApplicationMinYear(int applicationMinYear) {
    this.applicationMinYear = applicationMinYear;
  }

  // 10.1.1 10006,10016 追加 ここまで

  /**
   * mailMagazineAvailableModeを取得します。
   * 
   * @return mailMagazineAvailableMode
   */
  public boolean isMailMagazineAvailableMode() {
    return mailMagazineAvailableMode;
  }

  /**
   * mailMagazineAvailableModeを設定します。
   * 
   * @param mailMagazineAvailableMode
   *          設定する mailMagazineAvailableMode
   */
  public void setMailMagazineAvailableMode(boolean mailMagazineAvailableMode) {
    this.mailMagazineAvailableMode = mailMagazineAvailableMode;
  }

  /**
   * allowedUploadExtentionを取得します。
   * 
   * @return allowedUploadExtention
   */
  public String getAllowedUploadExtention() {
    return allowedUploadExtention;
  }

  /**
   * allowedUploadExtentionを設定します。
   * 
   * @param allowedUploadExtention
   *          設定する allowedUploadExtention
   */
  public void setAllowedUploadExtention(String allowedUploadExtention) {
    this.allowedUploadExtention = allowedUploadExtention;
  }

  /**
   * クッキー有効期間のデフォルト値を取得します。
   * 
   * @return the クッキー有効期間のデフォルト値
   */
  public int getDefaultCookieMaxAge() {
    return defaultCookieMaxAge;
  }

  /**
   * クッキー有効期間のデフォルト値を設定します。
   * 
   * @param defaultCookieMaxAge
   *          the defaultCookieMaxAge to set
   */
  public void setDefaultCookieMaxAge(int defaultCookieMaxAge) {
    this.defaultCookieMaxAge = defaultCookieMaxAge;
  }

  public void setOperatingModeString(String modeStr) {
    String md = StringUtil.coalesce(modeStr, "").toUpperCase(Locale.getDefault());
    setOperatingMode(OperatingMode.valueOf(md));
  }

  /**
   * 動作モードが「モール版：ショップ個別決済」かどうかを返します。
   * 
   * @return 動作モードが「ショップ個別決済」のときtrue
   */
  public boolean isShop() {
    return getOperatingMode() == OperatingMode.SHOP;
  }

  /**
   * 動作モードが「モール版：モール一括決済」かどうかを返します。
   * 
   * @return 動作モードが「ショップ個別決済」のときtrue
   */
  public boolean isMall() {
    return getOperatingMode() == OperatingMode.MALL;
  }

  /**
   * 動作モードが「一店舗版」かどうかを返します。
   * 
   * @return 動作モードが「一店舗版」のときtrue
   */
  public boolean isOne() {
    return getOperatingMode() == OperatingMode.ONE;
  }

  /**
   * @return the defaultMaxUseOrderAmount
   */
  public BigDecimal getDefaultMaxUseOrderAmount() {
    return defaultMaxUseOrderAmount;
  }

  /**
   * @param defaultMaxUseOrderAmount
   *          the defaultMaxUseOrderAmount to set
   */
  public void setDefaultMaxUseOrderAmount(BigDecimal defaultMaxUseOrderAmount) {
    this.defaultMaxUseOrderAmount = defaultMaxUseOrderAmount;
  }

  /**
   * reminderTimeLimitを取得します。
   * 
   * @return reminderTimeLimit
   */
  public int getReminderTimeLimit() {
    return reminderTimeLimit;
  }

  /**
   * reminderTimeLimitを設定します。
   * 
   * @param reminderTimeLimit
   *          設定する reminderTimeLimit
   */
  public void setReminderTimeLimit(int reminderTimeLimit) {
    this.reminderTimeLimit = reminderTimeLimit;
  }

  /**
   * authorizeCustomerErrorMaxCountを取得します。
   * 
   * @return authorizeCustomerErrorMaxCount
   */
  public int getAuthorizeCustomerErrorMaxCount() {
    return authorizeCustomerErrorMaxCount;
  }

  /**
   * authorizeCustomerErrorMaxCountを設定します。
   * 
   * @param authorizeCustomerErrorMaxCount
   *          authorizeCustomerErrorMaxCount
   */
  public void setAuthorizeCustomerErrorMaxCount(int authorizeCustomerErrorMaxCount) {
    this.authorizeCustomerErrorMaxCount = authorizeCustomerErrorMaxCount;
  }

  /**
   * authorizeUserErrorMaxCountを取得します。
   * 
   * @return authorizeUserErrorMaxCount
   */
  public int getAuthorizeUserErrorMaxCount() {
    return authorizeUserErrorMaxCount;
  }

  /**
   * authorizeUserErrorMaxCountを設定します。
   * 
   * @param authorizeUserErrorMaxCount
   *          authorizeUserErrorMaxCount
   */
  public void setAuthorizeUserErrorMaxCount(int authorizeUserErrorMaxCount) {
    this.authorizeUserErrorMaxCount = authorizeUserErrorMaxCount;
  }

  /**
   * categoryMaxDepthを取得します。
   * 
   * @return categoryMaxDepth
   */
  public int getCategoryMaxDepth() {
    return categoryMaxDepth;
  }

  /**
   * categoryMaxDepthを設定します。
   * 
   * @param categoryMaxDepth
   *          categoryMaxDepth
   */
  public void setCategoryMaxDepth(int categoryMaxDepth) {
    this.categoryMaxDepth = categoryMaxDepth;
  }

  /**
   * @return the frontPageWidth
   */
  public int getFrontPageWidth() {
    return frontPageWidth;
  }

  /**
   * @param frontPageWidth
   *          the frontPageWidth to set
   */
  public void setFrontPageWidth(int frontPageWidth) {
    this.frontPageWidth = frontPageWidth;
  }

  /**
   * @return the frontPageWidth
   */
  public int getBackPageWidth() {
    return backPageWidth;
  }

  /**
   * @param backPageWidth
   */
  public void setBackPageWidth(int backPageWidth) {
    this.backPageWidth = backPageWidth;
  }

  /**
   * @return the operatingMode
   */
  public OperatingMode getOperatingMode() {
    return operatingMode;
  }

  /**
   * @param operatingMode
   *          the operatingMode to set
   */
  public void setOperatingMode(OperatingMode operatingMode) {
    this.operatingMode = operatingMode;
  }

  /**
   * @return the applicationServerOS
   */
  public ApplicationServerOS getApplicationServerOS() {
    return applicationServerOS;
  }

  /**
   * @param applicationServerOS
   *          the applicationServerOS to set
   */
  public void setApplicationServerOS(ApplicationServerOS applicationServerOS) {
    this.applicationServerOS = applicationServerOS;
  }

  /**
   * @return the applicationServerType
   */
  public ApplicationServerType getApplicationServerType() {
    return applicationServerType;
  }

  /**
   * @param applicationServerType
   *          the applicationServerType to set
   */
  public void setApplicationServerType(ApplicationServerType applicationServerType) {
    this.applicationServerType = applicationServerType;
  }

  /**
   * @return the rdbmsType
   */
  public RdbmsType getRdbmsType() {
    return rdbmsType;
  }

  /**
   * @param rdbmsType
   *          the rdbmsType to set
   */
  public void setRdbmsType(RdbmsType rdbmsType) {
    this.rdbmsType = rdbmsType;
  }

  /**
   * siteShopCodeを取得します。
   * 
   * @return siteShopCode
   */
  public String getSiteShopCode() {
    return siteShopCode;
  }

  /**
   * siteShopCodeを設定します。
   * 
   * @param siteShopCode
   *          設定する siteShopCode
   */
  public void setSiteShopCode(String siteShopCode) {
    this.siteShopCode = siteShopCode;
  }

  /**
   * @return the deliveryAppointedPeriod
   */
  public int getDeliveryAppointedPeriod() {
    return deliveryAppointedPeriod;
  }

  /**
   * @param deliveryAppointedPeriod
   *          the deliveryAppointedPeriod to set
   */
  public void setDeliveryAppointedPeriod(int deliveryAppointedPeriod) {
    this.deliveryAppointedPeriod = deliveryAppointedPeriod;
  }

  /**
   * @return the categoryAttributeMaxCount
   */
  public int getCategoryAttributeMaxCount() {
    return categoryAttributeMaxCount;
  }

  /**
   * @param categoryAttributeMaxCount
   *          the categoryAttributeMaxCount to set
   */
  public void setCategoryAttributeMaxCount(int categoryAttributeMaxCount) {
    this.categoryAttributeMaxCount = categoryAttributeMaxCount;
  }

  /**
   * previewUrlを返します。
   * 
   * @return the previewUrl
   */
  public String getPreviewUrl() {
    return previewUrl;
  }

  /**
   * previewUrlを設定します。
   * 
   * @param previewUrl
   *          設定する previewUrl
   */
  public void setPreviewUrl(String previewUrl) {
    this.previewUrl = previewUrl;
  }

  /**
   * @return the recommendCommodityMaxCount
   */
  public int getRecommendCommodityMaxCount() {
    return recommendCommodityMaxCount;
  }

  /**
   * @param recommendCommodityMaxCount
   *          the recommendCommodityMaxCount to set
   */
  public void setRecommendCommodityMaxCount(int recommendCommodityMaxCount) {
    this.recommendCommodityMaxCount = recommendCommodityMaxCount;
  }

  /**
   * @return the rankingCommodityMaxCount
   */
  public int getRankingCommodityMaxCount() {
    return rankingCommodityMaxCount;
  }

  /**
   * @param rankingCommodityMaxCount
   *          the rankingCommodityMaxCount to set
   */
  public void setRankingCommodityMaxCount(int rankingCommodityMaxCount) {
    this.rankingCommodityMaxCount = rankingCommodityMaxCount;
  }

  /**
   * dataIOBufferSizeを取得します。
   * 
   * @return dataIOBufferSize
   */
  public int getDataIOBufferSize() {
    return dataIOBufferSize;
  }

  /**
   * dataIOBufferSizeを設定します。
   * 
   * @param dataIOBufferSize
   *          設定する dataIOBufferSize
   */
  public void setDataIOBufferSize(int dataIOBufferSize) {
    this.dataIOBufferSize = dataIOBufferSize;
  }

  /**
   * @return the recommendCommodityMaxLineCount
   */
  public int getRecommendCommodityMaxLineCount() {
    return recommendCommodityMaxLineCount;
  }

  /**
   * @param recommendCommodityMaxLineCount
   *          the recommendCommodityMaxLineCount to set
   */
  public void setRecommendCommodityMaxLineCount(int recommendCommodityMaxLineCount) {
    this.recommendCommodityMaxLineCount = recommendCommodityMaxLineCount;
  }

  /**
   * @return the sortedOrderAmount
   */
  public boolean isSortedOrderAmount() {
    return sortedOrderAmount;
  }

  /**
   * @param sortedOrderAmount
   *          the sortedOrderAmount to set
   */
  public void setSortedOrderAmount(boolean sortedOrderAmount) {
    this.sortedOrderAmount = sortedOrderAmount;
  }

  // /**
  // * frontSecureUrlを取得します。
  // *
  // * @return frontSecureUrl
  // */
  // public String getFrontSecureUrl() {
  // return frontSecureUrl;
  // }
  //
  // /**
  // * frontSecureUrlを設定します。
  // *
  // * @param frontSecureUrl
  // * 設定する frontSecureUrl
  // */
  // public void setFrontSecureUrl(String frontSecureUrl) {
  // this.frontSecureUrl = frontSecureUrl;
  // }
  //
  // /**
  // * mobileSecureUrlを取得します。
  // *
  // * @return mobileSecureUrl
  // */
  // public String getMobileSecureUrl() {
  // return mobileSecureUrl;
  // }
  //
  // /**
  // * mobileSecureUrlを設定します。
  // *
  // * @param mobileSecureUrl
  // * 設定する mobileSecureUrl
  // */
  // public void setMobileSecureUrl(String mobileSecureUrl) {
  // this.mobileSecureUrl = mobileSecureUrl;
  // }

  /**
   * @return the siteUserCode
   */
  public String getSiteUserCode() {
    return siteUserCode;
  }

  /**
   * @param siteUserCode
   *          the siteUserCode to set
   */
  public void setSiteUserCode(String siteUserCode) {
    this.siteUserCode = siteUserCode;
  }

  /**
   * hostNameを返します。
   * 
   * @return the hostName
   */
  public String getHostName() {
    return hostName;
  }

  /**
   * hostNameを設定します。
   * 
   * @param hostName
   *          設定する hostName
   */
  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  /**
   * httpPortを返します。
   * 
   * @return the httpPort
   */
  public int getHttpPort() {
    return httpPort;
  }

  /**
   * httpPortを設定します。
   * 
   * @param httpPort
   *          設定する httpPort
   */
  public void setHttpPort(int httpPort) {
    this.httpPort = httpPort;
  }

  /**
   * httpsPortを返します。
   * 
   * @return the httpsPort
   */
  public int getHttpsPort() {
    return httpsPort;
  }

  /**
   * httpsPortを設定します。
   * 
   * @param httpsPort
   *          設定する httpsPort
   */
  public void setHttpsPort(int httpsPort) {
    this.httpsPort = httpsPort;
  }

  /**
   * csvCharsetを取得します。
   * 
   * @return csvCharset
   */
  public String getCsvCharset() {
    return csvCharset;
  }

  /**
   * csvCharsetを設定します。
   * 
   * @param csvCharset
   *          設定する csvCharset
   */
  public void setCsvCharset(String csvCharset) {
    this.csvCharset = csvCharset;
  }

  /**
   * logCharsetを返します。
   * 
   * @return the logCharset
   */
  public String getLogCharset() {
    return logCharset;
  }

  /**
   * logCharsetを設定します。
   * 
   * @param logCharset
   *          設定する logCharset
   */
  public void setLogCharset(String logCharset) {
    this.logCharset = logCharset;
  }

  /**
   * @return the topPageUrl
   */
  public String getTopPageUrl() {
    return topPageUrl;
  }

  /**
   * @param topPageUrl
   *          the topPageUrl to set
   */
  public void setTopPageUrl(String topPageUrl) {
    this.topPageUrl = topPageUrl;
  }

  public String getMobileTopPageUrl() {
    return mobileTopPageUrl;
  }

  public void setMobileTopPageUrl(String mobileTopPageUrl) {
    this.mobileTopPageUrl = mobileTopPageUrl;
  }

  public String getBackHostName() {
    if (backHostName == null) {
      return hostName;
    } else {
      return backHostName;
    }
  }

  public void setBackHostName(String backHostName) {
    this.backHostName = backHostName;
  }

  public String getBackContext() {
    return backContext;
  }

  public void setBackContext(String backContext) {
    this.backContext = backContext;
  }

  public int getBackHttpPort() {
    if (backHttpPort == 0) {
      return httpPort;
    } else {
      return backHttpPort;
    }
  }

  public void setBackHttpPort(int backHttpPort) {
    this.backHttpPort = backHttpPort;
  }

  public int getBackHttpsPort() {
    if (backHttpsPort == 0) {
      return httpsPort;
    } else {
      return backHttpsPort;
    }
  }

  public void setBackHttpsPort(int backHttpsPort) {
    this.backHttpsPort = backHttpsPort;
  }

  public String getFrontHostName() {
    if (frontHostName == null) {
      return hostName;
    } else {
      return frontHostName;
    }
  }

  public void setFrontHostName(String frontHostName) {
    this.frontHostName = frontHostName;
  }

  public String getFrontContext() {
    return frontContext;
  }

  public void setFrontContext(String frontContext) {
    this.frontContext = frontContext;
  }

  public int getFrontHttpPort() {
    if (frontHttpPort == 0) {
      return httpPort;
    } else {
      return frontHttpPort;
    }
  }

  public void setFrontHttpPort(int frontHttpPort) {
    this.frontHttpPort = frontHttpPort;
  }

  public int getFrontHttpsPort() {
    if (frontHttpsPort == 0) {
      return httpsPort;
    } else {
      return frontHttpsPort;
    }
  }

  public void setFrontHttpsPort(int frontHttpsPort) {
    this.frontHttpsPort = frontHttpsPort;
  }

  public String getMobileHostName() {
    if (mobileHostName == null) {
      return hostName;
    } else {
      return mobileHostName;
    }
  }

  public void setMobileHostName(String mobileHostName) {
    this.mobileHostName = mobileHostName;
  }

  public String getMobileContext() {
    return mobileContext;
  }

  public void setMobileContext(String mobileContext) {
    this.mobileContext = mobileContext;
  }

  public int getMobileHttpPort() {
    if (mobileHttpPort == 0) {
      return httpPort;
    } else {
      return mobileHttpPort;
    }
  }

  public void setMobileHttpPort(int mobileHttpPort) {
    this.mobileHttpPort = mobileHttpPort;
  }

  public int getMobileHttpsPort() {
    if (mobileHttpsPort == 0) {
      return httpsPort;
    } else {
      return mobileHttpsPort;
    }
  }

  public void setMobileHttpsPort(int mobileHttpsPort) {
    this.mobileHttpsPort = mobileHttpsPort;
  }

  public boolean isUseBlanketCart() {
    return useBlanketCart;
  }

  public void setUseBlanketCart(boolean useBlanketCart) {
    this.useBlanketCart = useBlanketCart;
  }

  /**
   * loggerHostNameを返します。
   * 
   * @return the loggerHostName
   */
  public String getLoggerHostName() {
    return StringUtil.coalesce(this.loggerHostName, getHostName());
  }

  /**
   * loggerHostNameを設定します。
   * 
   * @param loggerHostName
   *          設定する loggerHostName
   */
  public void setLoggerHostName(String loggerHostName) {
    this.loggerHostName = loggerHostName;
  }

  /**
   * loggerContextを返します。
   * 
   * @return the loggerContext
   */
  public String getLoggerContext() {
    return loggerContext;
  }

  /**
   * loggerContextを設定します。
   * 
   * @param loggerContext
   *          設定する loggerContext
   */
  public void setLoggerContext(String loggerContext) {
    this.loggerContext = loggerContext;
  }

  /**
   * loggerHttpPortを返します。
   * 
   * @return the loggerHttpPort
   */
  public int getLoggerHttpPort() {
    if (loggerHttpPort == 0) {
      return httpPort;
    } else {
      return loggerHttpPort;
    }
  }

  /**
   * loggerHttpPortを設定します。
   * 
   * @param loggerHttpPort
   *          設定する loggerHttpPort
   */
  public void setLoggerHttpPort(int loggerHttpPort) {
    this.loggerHttpPort = loggerHttpPort;
  }

  /**
   * loggerHttpsPortを返します。
   * 
   * @return the loggerHttpsPort
   */
  public int getLoggerHttpsPort() {
    if (loggerHttpsPort == 0) {
      return httpsPort;
    } else {
      return loggerHttpsPort;
    }
  }

  /**
   * loggerHttpsPortを設定します。
   * 
   * @param loggerHttpsPort
   *          設定する loggerHttpsPort
   */
  public void setLoggerHttpsPort(int loggerHttpsPort) {
    this.loggerHttpsPort = loggerHttpsPort;
  }

  /**
   * recommendCommodityMaxCountMobileを取得します。
   * 
   * @return recommendCommodityMaxCountMobile
   */
  public int getRecommendCommodityMaxCountMobile() {
    return recommendCommodityMaxCountMobile;
  }

  /**
   * recommendCommodityMaxCountMobileを設定します。
   * 
   * @param recommendCommodityMaxCountMobile
   *          recommendCommodityMaxCountMobile
   */
  public void setRecommendCommodityMaxCountMobile(int recommendCommodityMaxCountMobile) {
    this.recommendCommodityMaxCountMobile = recommendCommodityMaxCountMobile;
  }

  /**
   * recommendRankingMaxCountを取得します。
   * 
   * @return recommendRankingMaxCount
   */

  public int getRecommendRankingMaxCount() {
    return recommendRankingMaxCount;
  }

  /**
   * recommendRankingMaxCountを設定します。
   * 
   * @param recommendRankingMaxCount
   *          recommendRankingMaxCount
   */
  public void setRecommendRankingMaxCount(int recommendRankingMaxCount) {
    this.recommendRankingMaxCount = recommendRankingMaxCount;
  }

  /**
   * helpMessagesを返します。
   * 
   * @return the helpMessages
   */
  public HelpMapContainer getHelpMessages() {
    HelpMapContainer result = new HelpMapContainer();
    result.setHelpMap(CodeUtil.getMapEntry("helpMessages"));
    if (result.getHelpMap() == null || result.getHelpMap().isEmpty()) {
      return helpMessages;
    }
    return result;
  }

  /**
   * helpMessagesを設定します。
   * 
   * @param helpMessages
   *          設定する helpMessages
   */
  public void setHelpMessages(HelpMapContainer helpMessages) {
    this.helpMessages = helpMessages;
  }

  /**
   * @return the useCustomerPassEncrypt
   */
  public boolean isUseCustomerPassEncrypt() {
    return useCustomerPassEncrypt;
  }

  /**
   * @param useCustomerPassEncrypt
   *          the useCustomerPassEncrypt to set
   */
  public void setUseCustomerPassEncrypt(boolean useCustomerPassEncrypt) {
    this.useCustomerPassEncrypt = useCustomerPassEncrypt;
  }

  /**
   * defaultCacheTimeoutを取得します。
   * 
   * @return defaultCacheTimeout
   */
  public int getDefaultCacheTimeout() {
    return defaultCacheTimeout;
  }

  /**
   * defaultCacheTimeoutを設定します。
   * 
   * @param defaultCacheTimeout
   *          設定する defaultCacheTimeout
   */
  public void setDefaultCacheTimeout(int defaultCacheTimeout) {
    this.defaultCacheTimeout = defaultCacheTimeout;
  }

  /**
   * defaultShippingChargeを返します。
   * 
   * @return the defaultShippingCharge
   */
  public BigDecimal getDefaultShippingCharge() {
    return defaultShippingCharge;
  }

  /**
   * defaultShippingChargeを設定します。
   * 
   * @param defaultShippingCharge
   *          設定する defaultShippingCharge
   */
  public void setDefaultShippingCharge(BigDecimal defaultShippingCharge) {
    this.defaultShippingCharge = defaultShippingCharge;
  }

  /**
   * customerRecommendCommodityMaxLineCountを取得します。
   * 
   * @return customerRecommendCommodityMaxLineCount
   */
  public int getCustomerRecommendCommodityMaxLineCount() {
    return customerRecommendCommodityMaxLineCount;
  }

  /**
   * customerRecommendCommodityMaxLineCountを設定します。
   * 
   * @param customerRecommendCommodityMaxLineCount
   *          customerRecommendCommodityMaxLineCount
   */
  public void setCustomerRecommendCommodityMaxLineCount(int customerRecommendCommodityMaxLineCount) {
    this.customerRecommendCommodityMaxLineCount = customerRecommendCommodityMaxLineCount;
  }

  /**
   * customerRecommendCommodityMaxLineCountMobileを取得します。
   * 
   * @return customerRecommendCommodityMaxLineCountMobile
   */
  public int getCustomerRecommendCommodityMaxLineCountMobile() {
    return customerRecommendCommodityMaxLineCountMobile;
  }

  /**
   * customerRecommendCommodityMaxLineCountMobileを設定します。
   * 
   * @param customerRecommendCommodityMaxLineCountMobile
   *          customerRecommendCommodityMaxLineCountMobile
   */
  public void setCustomerRecommendCommodityMaxLineCountMobile(int customerRecommendCommodityMaxLineCountMobile) {
    this.customerRecommendCommodityMaxLineCountMobile = customerRecommendCommodityMaxLineCountMobile;
  }

  /**
   * searchWordMaxLengthを取得します。
   * 
   * @return searchWordMaxLength
   */
  public int getSearchWordMaxLength() {
    return searchWordMaxLength;
  }

  /**
   * searchWordMaxLengthを設定します。
   * 
   * @param searchWordMaxLength
   *          searchWordMaxLength
   */
  public void setSearchWordMaxLength(int searchWordMaxLength) {
    this.searchWordMaxLength = searchWordMaxLength;
  }

  /**
   * minimalLeadTimeを取得します。
   * 
   * @return minimalLeadTime
   */
  public int getMinimalLeadTime() {
    return minimalLeadTime;
  }

  /**
   * minimalLeadTimeを設定します。
   * 
   * @param minimalLeadTime
   *          minimalLeadTime
   */
  public void setMinimalLeadTime(int minimalLeadTime) {
    this.minimalLeadTime = minimalLeadTime;
  }

  // postgreSQL start
  public boolean isOracle() {
    return getRdbmsType() == RdbmsType.ORACLE_10G;
  }

  public boolean isPostgreSQL() {
    return getRdbmsType() == RdbmsType.POSTGRESQL;
  }

  // postgreSQL end

  public int getPointMultiple() {
    return pointMultiple;
  }

  public void setPointMultiple(int pointMultiple) {
    this.pointMultiple = pointMultiple;
  }

  public String getPointFormat() {
    return pointFormat;
  }

  public void setPointFormat(String pointFormat) {
    this.pointFormat = pointFormat;
  }

  public BigDecimal getRmbPointRate() {
    return rmbPointRate;
  }

  public void setRmbPointRate(BigDecimal rmbPointRate) {
    this.rmbPointRate = rmbPointRate;
  }

  public int getDisplayDiscountRateLimit() {
    return displayDiscountRateLimit;
  }

  public void setDisplayDiscountRateLimit(int displayDiscountRateLimit) {
    this.displayDiscountRateLimit = displayDiscountRateLimit;
  }

  /**
   * @param criticalValue
   *          the criticalValue to set
   */
  public void setCriticalValue(Long criticalValue) {
    this.criticalValue = criticalValue;
  }

  /**
   * @return the criticalValue
   */
  public Long getCriticalValue() {
    return this.criticalValue;
  }

  /**
   * @return the stockMessage
   */
  public String getStockMessage() {
    return stockMessage;
  }

  /**
   * @param stockMessage
   *          the stockMessage to set
   */
  public void setStockMessage(String stockMessage) {
    this.stockMessage = stockMessage;
  }

  /**
   * @return the customerGroupType
   */
  public List<String> getCustomerGroupType() {
    return customerGroupType;
  }

  /**
   * @param customerGroupType
   *          the customerGroupType to set
   */
  public void setCustomerGroupType(List<String> customerGroupType) {
    this.customerGroupType = customerGroupType;
  }

  /**
   * @return the commodityLeadTime
   */
  public Long getCommodityLeadTime() {
    return commodityLeadTime;
  }

  /**
   * @param commodityLeadTime
   *          the commodityLeadTime to set
   */
  public void setCommodityLeadTime(Long commodityLeadTime) {
    this.commodityLeadTime = commodityLeadTime;
  }

  /**
   * @return the invoiceCommodityNameList
   */
  public List<String> getInvoiceCommodityNameList() {
    return invoiceCommodityNameList;
  }

  /**
   * @param invoiceCommodityNameList
   *          the invoiceCommodityNameList to set
   */
  public void setInvoiceCommodityNameList(List<String> invoiceCommodityNameList) {
    this.invoiceCommodityNameList.clear();
    CollectionUtil.copyAll(this.invoiceCommodityNameList, invoiceCommodityNameList);
  }

  /**
   * @return the warehouseRestDay
   */
  public List<Integer> getWarehouseRestDay() {
    return warehouseRestDay;
  }

  /**
   * @param warehouseRestDay
   *          the warehouseRestDay to set
   */
  public void setWarehouseRestDay(List<Integer> warehouseRestDay) {
    this.warehouseRestDay.clear();
    CollectionUtil.copyAll(this.warehouseRestDay, warehouseRestDay);
  }

  /**
   * @return the deliveryDayRange
   */
  public int getDeliveryDayRange() {
    return deliveryDayRange;
  }

  /**
   * @param deliveryDayRange
   *          the deliveryDayRange to set
   */
  public void setDeliveryDayRange(int deliveryDayRange) {
    this.deliveryDayRange = deliveryDayRange;
  }

  public List<String> getDeliveryTemplate() {
    return deliveryTemplate;
  }

  public void setDeliveryTemplate(List<String> deliveryTemplate) {
    this.deliveryTemplate = deliveryTemplate;
  }

  public List<String> getCreditTerms() {
    return creditTerms;
  }

  public void setCreditTerms(List<String> creditTerms) {
    this.creditTerms = creditTerms;
  }

  public BigDecimal getTmallPointConvert() {
    return tmallPointConvert;
  }

  public void setTmallPointConvert(BigDecimal tmallPointConvert) {
    this.tmallPointConvert = tmallPointConvert;
  }

  public String getProductImgUrl() {
    return productImgUrl;
  }

  public void setProductImgUrl(String productImgUrl) {
    this.productImgUrl = productImgUrl;
  }

  public int getOnTmallMinute() {
    return onTmallMinute;
  }

  public void setOnTmallMinute(int onTmallMinute) {
    this.onTmallMinute = onTmallMinute;
  }

  /**
   * @return the aboveCartTip
   */
  public int getAboveCartTip() {
    return aboveCartTip;
  }

  /**
   * @param aboveCartTip
   *          the aboveCartTip to set
   */
  public void setAboveCartTip(int aboveCartTip) {
    this.aboveCartTip = aboveCartTip;
  }

  /**
   * @return the remainAssignSwitch
   */
  public String getRemainAssignSwitch() {
    return remainAssignSwitch;
  }

  /**
   * @param remainAssignSwitch
   *          the remainAssignSwitch to set
   */
  public void setRemainAssignSwitch(String remainAssignSwitch) {
    this.remainAssignSwitch = remainAssignSwitch;
  }

  /**
   * @return the indexCommodityLimit
   */
  public String getIndexCommodityLimit() {
    return indexCommodityLimit;
  }

  /**
   * @param indexCommodityLimit
   *          the indexCommodityLimit to set
   */
  public void setIndexCommodityLimit(String indexCommodityLimit) {
    this.indexCommodityLimit = indexCommodityLimit;
  }

  /**
   * @return the indexCommodityLimit
   */
  public String getIndexCompositionLimit() {
    return indexCompositionLimit;
  }

  /**
   * @param indexCommodityLimit
   *          the indexCommodityLimit to set
   */
  public void setIndexCompositionLimit(String indexCompositionLimit) {
    this.indexCompositionLimit = indexCompositionLimit;
  }

  /**
   * @return the orderHeadOff
   */
  public int getOrderHeadOff() {
    return orderHeadOff;
  }

  /**
   * @param orderHeadOff
   *          the orderHeadOff to set
   */
  public void setOrderHeadOff(int orderHeadOff) {
    this.orderHeadOff = orderHeadOff;
  }

  /**
   * @return the indexTagCode
   */
  public String getIndexTagCode() {
    return indexTagCode;
  }

  /**
   * @param indexTagCode
   *          the indexTagCode to set
   */
  public void setIndexTagCode(String indexTagCode) {
    this.indexTagCode = indexTagCode;
  }

  /**
   * @return the mobilePageSize
   */
  public int getMobilePageSize() {
    return mobilePageSize;
  }

  /**
   * @param mobilePageSize
   *          the mobilePageSize to set
   */
  public void setMobilePageSize(int mobilePageSize) {
    this.mobilePageSize = mobilePageSize;
  }

  /**
   * @return the commodityLeadTimeBefore
   */
  public Long getCommodityLeadTimeBefore() {
    return commodityLeadTimeBefore;
  }

  /**
   * @param commodityLeadTimeBefore
   *          the commodityLeadTimeBefore to set
   */
  public void setCommodityLeadTimeBefore(Long commodityLeadTimeBefore) {
    this.commodityLeadTimeBefore = commodityLeadTimeBefore;
  }

  /**
   * @return the commodityLeadTimeAfter
   */
  public Long getCommodityLeadTimeAfter() {
    return commodityLeadTimeAfter;
  }

  /**
   * @param commodityLeadTimeAfter
   *          the commodityLeadTimeAfter to set
   */
  public void setCommodityLeadTimeAfter(Long commodityLeadTimeAfter) {
    this.commodityLeadTimeAfter = commodityLeadTimeAfter;
  }

  /**
   * @return the finalSysTime
   */
  public String getFinalSysTime() {
    return finalSysTime;
  }

  /**
   * @param finalSysTime
   *          the finalSysTime to set
   */
  public void setFinalSysTime(String finalSysTime) {
    this.finalSysTime = finalSysTime;
  }

  /**
   * @return the tagCategoryCommodityLimit
   */
  public String getTagCategoryCommodityLimit() {
    return tagCategoryCommodityLimit;
  }

  /**
   * @param tagCategoryCommodityLimit
   *          the tagCategoryCommodityLimit to set
   */
  public void setTagCategoryCommodityLimit(String tagCategoryCommodityLimit) {
    this.tagCategoryCommodityLimit = tagCategoryCommodityLimit;
  }

  public String getFriendCouponCodeDay() {
    return friendCouponCodeDay;
  }

  public void setFriendCouponCodeDay(String friendCouponCodeDay) {
    this.friendCouponCodeDay = friendCouponCodeDay;
  }

  public int getCouponUseEndDay() {
    return couponUseEndDay;
  }

  public void setCouponUseEndDay(int couponUseEndDay) {
    this.couponUseEndDay = couponUseEndDay;
  }

  public Long getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  public void setMaxUseOrderAmount(Long maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  /**
   * @return the couponUrl
   */
  public String getCouponUrl() {
    return couponUrl;
  }

  /**
   * @param couponUrl
   *          the couponUrl to set
   */
  public void setCouponUrl(String couponUrl) {
    this.couponUrl = couponUrl;
  }

  /**
   * @return the freeSearchCategoryListNum
   */
  public int getFreeSearchCategoryListNum() {
    return freeSearchCategoryListNum;
  }

  /**
   * @param freeSearchCategoryListNum
   *          the freeSearchCategoryListNum to set
   */
  public void setFreeSearchCategoryListNum(int freeSearchCategoryListNum) {
    this.freeSearchCategoryListNum = freeSearchCategoryListNum;
  }

  /**
   * @return the suggestSearchNum
   */
  public int getSuggestSearchNum() {
    return suggestSearchNum;
  }

  /**
   * @param suggestSearchNum
   *          the suggestSearchNum to set
   */
  public void setSuggestSearchNum(int suggestSearchNum) {
    this.suggestSearchNum = suggestSearchNum;
  }

  /**
   * @return the groupBuyingStartTime
   */
  public String getGroupBuyingStartTime() {
    return groupBuyingStartTime;
  }

  /**
   * @param groupBuyingStartTime
   *          the groupBuyingStartTime to set
   */
  public void setGroupBuyingStartTime(String groupBuyingStartTime) {
    this.groupBuyingStartTime = groupBuyingStartTime;
  }

  /**
   * @return the groupBuyingEndTime
   */
  public String getGroupBuyingEndTime() {
    return groupBuyingEndTime;
  }

  /**
   * @param groupBuyingEndTime
   *          the groupBuyingEndTime to set
   */
  public void setGroupBuyingEndTime(String groupBuyingEndTime) {
    this.groupBuyingEndTime = groupBuyingEndTime;
  }

  /**
   * @return the groupBuyingSkuList
   */
  public List<String> getGroupBuyingSkuList() {
    return groupBuyingSkuList;
  }

  /**
   * @param groupBuyingSkuList
   *          the groupBuyingSkuList to set
   */
  public void setGroupBuyingSkuList(List<String> groupBuyingSkuList) {
    this.groupBuyingSkuList = groupBuyingSkuList;
  }

  /**
   * @return the weight
   */
  public Long getWeight() {
    return weight;
  }

  /**
   * @param weight
   *          the weight to set
   */
  public void setWeight(Long weight) {
    this.weight = weight;
  }

  /**
   * @return the price
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * @param price
   *          the price to set
   */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /**
   * @return the trackUrl
   */
  public String getTrackUrl() {
    return trackUrl;
  }

  /**
   * @param trackUrl
   *          the trackUrl to set
   */
  public void setTrackUrl(String trackUrl) {
    this.trackUrl = trackUrl;
  }

  /**
   * @return the errorTimes
   */
  public int getErrorTimes() {
    return errorTimes;
  }

  /**
   * @param errorTimes
   *          the errorTimes to set
   */
  public void setErrorTimes(int errorTimes) {
    this.errorTimes = errorTimes;
  }

  /**
   * @return the cardId
   */
  public String getCardId() {
    return cardId;
  }

  /**
   * @return the cardName
   */
  public String getCardName() {
    return cardName;
  }

  /**
   * @return the effectiveYears
   */
  public int getEffectiveYears() {
    return effectiveYears;
  }

  /**
   * @param cardId
   *          the cardId to set
   */
  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  /**
   * @param cardName
   *          the cardName to set
   */
  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  /**
   * @param effectiveYears
   *          the effectiveYears to set
   */
  public void setEffectiveYears(int effectiveYears) {
    this.effectiveYears = effectiveYears;
  }

  /**
   * @return the memoDate
   */
  public int getMemoDate() {
    return memoDate;
  }

  /**
   * @param memoDate
   *          the memoDate to set
   */
  public void setMemoDate(int memoDate) {
    this.memoDate = memoDate;
  }

  /**
   * @return the interceptDeliveryCompanyList
   */
  public List<String> getInterceptDeliveryCompanyList() {
    return interceptDeliveryCompanyList;
  }

  /**
   * @param interceptDeliveryCompanyList
   *          the interceptDeliveryCompanyList to set
   */
  public void setInterceptDeliveryCompanyList(List<String> interceptDeliveryCompanyList) {
    this.interceptDeliveryCompanyList = interceptDeliveryCompanyList;
  }

  /**
   * @return the designHoliDayYesterday
   */
  public List<String> getDesignHoliDayYesterday() {
    return designHoliDayYesterday;
  }

  /**
   * @param designHoliDayYesterday
   *          the designHoliDayYesterday to set
   */
  public void setDesignHoliDayYesterday(List<String> designHoliDayYesterday) {
    this.designHoliDayYesterday = designHoliDayYesterday;
  }

  /**
   * @return the nearMsgCn
   */
  public String getNearMsgCn() {
    return nearMsgCn;
  }

  /**
   * @return the nearMsgEn
   */
  public String getNearMsgEn() {
    return nearMsgEn;
  }

  /**
   * @return the nearMsgJp
   */
  public String getNearMsgJp() {
    return nearMsgJp;
  }

  /**
   * @param nearMsgCn
   *          the nearMsgCn to set
   */
  public void setNearMsgCn(String nearMsgCn) {
    this.nearMsgCn = nearMsgCn;
  }

  /**
   * @param nearMsgEn
   *          the nearMsgEn to set
   */
  public void setNearMsgEn(String nearMsgEn) {
    this.nearMsgEn = nearMsgEn;
  }

  /**
   * @param nearMsgJp
   *          the nearMsgJp to set
   */
  public void setNearMsgJp(String nearMsgJp) {
    this.nearMsgJp = nearMsgJp;
  }

  /**
   * @return the outerCardRate
   */
  public BigDecimal getOuterCardRate() {
    return outerCardRate;
  }

  /**
   * @param outerCardRate
   *          the outerCardRate to set
   */
  public void setOuterCardRate(BigDecimal outerCardRate) {
    this.outerCardRate = outerCardRate;
  }

  /**
   * @return the outerCardTop
   */
  public BigDecimal getOuterCardTop() {
    return outerCardTop;
  }

  /**
   * @param outerCardTop
   *          the outerCardTop to set
   */
  public void setOuterCardTop(BigDecimal outerCardTop) {
    this.outerCardTop = outerCardTop;
  }

  /**
   * @return the jdWeight1
   */
  public BigDecimal getJdWeight1() {
    return jdWeight1;
  }

  /**
   * @param jdWeight1
   *          the jdWeight1 to set
   */
  public void setJdWeight1(BigDecimal jdWeight1) {
    this.jdWeight1 = jdWeight1;
  }

  /**
   * @return the jdWeight2
   */
  public BigDecimal getJdWeight2() {
    return jdWeight2;
  }

  /**
   * @param jdWeight2
   *          the jdWeight2 to set
   */
  public void setJdWeight2(BigDecimal jdWeight2) {
    this.jdWeight2 = jdWeight2;
  }

  /**
   * @return the jdPrice1
   */
  public BigDecimal getJdPrice1() {
    return jdPrice1;
  }

  /**
   * @param jdPrice1
   *          the jdPrice1 to set
   */
  public void setJdPrice1(BigDecimal jdPrice1) {
    this.jdPrice1 = jdPrice1;
  }

  /**
   * @return the jdPrice2
   */
  public BigDecimal getJdPrice2() {
    return jdPrice2;
  }

  /**
   * @param jdPrice2
   *          the jdPrice2 to set
   */
  public void setJdPrice2(BigDecimal jdPrice2) {
    this.jdPrice2 = jdPrice2;
  }

  /**
   * @return the jdWeight3
   */
  public BigDecimal getJdWeight3() {
    return jdWeight3;
  }

  /**
   * @param jdWeight3
   *          the jdWeight3 to set
   */
  public void setJdWeight3(BigDecimal jdWeight3) {
    this.jdWeight3 = jdWeight3;
  }

  /**
   * @return the jdSpeCommodityList
   */
  public List<String> getJdSpeCommodityList() {
    return jdSpeCommodityList;
  }

  /**
   * @param jdSpeCommodityList
   *          the jdSpeCommodityList to set
   */
  public void setJdSpeCommodityList(List<String> jdSpeCommodityList) {
    this.jdSpeCommodityList = jdSpeCommodityList;
  }

  /**
   * @return the csvBackupTemp
   */
  public String getCsvBackupTemp() {
    return csvBackupTemp;
  }

  /**
   * @param csvBackupTemp
   *          the csvBackupTemp to set
   */
  public void setCsvBackupTemp(String csvBackupTemp) {
    this.csvBackupTemp = csvBackupTemp;
  }

  /**
   * @return the csvBackup
   */
  public String getCsvBackup() {
    return csvBackup;
  }

  /**
   * @param csvBackup
   *          the csvBackup to set
   */
  public void setCsvBackup(String csvBackup) {
    this.csvBackup = csvBackup;
  }

  /**
   * @return the stockRatio
   */
  public String getStockRatio() {
    return stockRatio;
  }

  /**
   * @param stockRatio
   *          the stockRatio to set
   */
  public void setStockRatio(String stockRatio) {
    this.stockRatio = stockRatio;
  }

  /**
   * @return the showRelatedBrandNum
   */
  public Long getShowRelatedBrandNum() {
    return showRelatedBrandNum;
  }

  /**
   * @param showRelatedBrandNum
   *          the showRelatedBrandNum to set
   */
  public void setShowRelatedBrandNum(Long showRelatedBrandNum) {
    this.showRelatedBrandNum = showRelatedBrandNum;
  }

  /**
   * @return the showRelatedCategoryNum
   */
  public Long getShowRelatedCategoryNum() {
    return showRelatedCategoryNum;
  }

  /**
   * @param showRelatedCategoryNum
   *          the showRelatedCategoryNum to set
   */
  public void setShowRelatedCategoryNum(Long showRelatedCategoryNum) {
    this.showRelatedCategoryNum = showRelatedCategoryNum;
  }

  /**
   * @return the jdSpeCommodityListTwo
   */
  public List<String> getJdSpeCommodityListTwo() {
    return jdSpeCommodityListTwo;
  }

  /**
   * @param jdSpeCommodityListTwo
   *          the jdSpeCommodityListTwo to set
   */
  public void setJdSpeCommodityListTwo(List<String> jdSpeCommodityListTwo) {
    this.jdSpeCommodityListTwo = jdSpeCommodityListTwo;
  }

  /**
   * @return the shNotCommodityList
   */
  public List<String> getShNotCommodityList() {
    return shNotCommodityList;
  }

  /**
   * @param shNotCommodityList
   *          the shNotCommodityList to set
   */
  public void setShNotCommodityList(List<String> shNotCommodityList) {
    this.shNotCommodityList = shNotCommodityList;
  }

}
