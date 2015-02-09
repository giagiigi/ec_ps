package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class NewCampaignEditBean extends UIBackBean implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  // 促销编号
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "活动编号")
  private String campaignCode;

  // 促销名称(说明)
  @Required
  @Length(50)
  @Metadata(name = "活动名称")
  private String campaignName;

  // 促销英文名
  @Required
  @Length(100)
  @Metadata(name = "活动名称(英文)")
  private String campaignNameEn;

  // 促销日文名
  @Required
  @Length(100)
  @Metadata(name = "活动名称(日文)")
  private String campaignNameJp;

  // 开始日期（年月日 时）
  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "活动期间(From)")
  private String campaignStartDate;

  // 结束日期（年月日 时）
  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "活动期间(To)")
  private String campaignEndDate;

  // 活动类型（1：免邮费 2：折扣 3：礼品）
  @Required
  @Length(1)
  @Metadata(name = "活动类型")
  private String campaignType;

  private List<CodeAttribute> campaignTypeList = new ArrayList<CodeAttribute>();

  // 条件类型（1：订单对象商品 2：订单地址区域 3：会员等级 4：订单金额 5：商品合计重量）
  @Length(1)
  @Metadata(name = "对象类型")
  private String campaignConditionType;

  private List<CodeAttribute> campaignConditionTypeList = new ArrayList<CodeAttribute>();
  

  private List<String> checkList = new ArrayList<String>();

  // 订购次数限制
  @Length(8)
  @Digit
  @Range(min = 1, max = 99999999)
  @Metadata(name = "订购次数限制")
  private String orderLimit;

  // 对象商品（包含/仅有）
  @Length(1)
  @Metadata(name = "对象商品区分")
  private String campaignConditionFlg;

  private List<CodeAttribute> campaignConditionFlgList = new ArrayList<CodeAttribute>();

  // 对象商品限定件数
  @Length(8)
  @Digit
  @Range(min = 1, max = 99999999)
  @Metadata(name = "对象商品限定件数")
  private String maxCommodityNum;

  // 备注
  @Length(256)
  @Metadata(name = "备注")
  private String remarks;

  // 折扣区分
  @Length(1)
  @Metadata(name = "折扣类型")
  private String discountType;

  private List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();

  // 折扣比例
  @Length(3)
  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "折扣比例")
  private String discoutRate;

  // 折扣金额
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "折扣金额")
  private String discountMoney;

  // 注文金额
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "订单金额")
  private String orderAmount;

  // 广告媒体编号
  @Length(16)
  @AlphaNum2
  @Metadata(name = "广告媒体编号")
  private String advertCode;

  // 关联商品
  @Length(500)
  @Metadata(name = "关联商品")
  private String relatedCommodity;

  // 配送地址
  @Length(500)
  @Metadata(name = "配送地址")
  private String deliveryArea;

  // 赠品
  @Length(500)
  @Metadata(name = "赠品")
  private String giftCommodity;

  // 关联商品部分显示与否
  private boolean disRelatedButtonFlg;

  // 赠品部分显示与否
  private boolean disAreaButtonFlg;

  // 区分更新或增加
  private String displayMode;

  private List<RelatedCommodityBean> relatedList = new ArrayList<RelatedCommodityBean>();

  private List<GiftCommodityBean> giftList = new ArrayList<GiftCommodityBean>();

  // 用于遍历值
  private List<PrefectureBean> prefectureBeanList = new ArrayList<PrefectureBean>();

  private List<String> prefectureCode = new ArrayList<String>();

  private RelatedCommodityBean relatedCommodityBean = new RelatedCommodityBean();

  private GiftCommodityBean giftCommodityBean = new GiftCommodityBean();

  // 登录按钮
  private boolean displayLoginButtonFlg;

  // 更新按钮
  private boolean displayUpdateButtonFlg;

  // 删除按钮
  private boolean displayDeleteFlg;

  // 只读权限控制
  private boolean displayReadFlg;

  // 赠品数量
  @Length(8)
  @Digit
  @Range(min = 1, max = 99999999)
  @Metadata(name = "赠品促销活动的赠品数量")
  private String giftAmount;
  
  private boolean disGiftButtonFlg;

  private List<String> checkedCode = new ArrayList<String>();

  private List<String> checkedGiftCode = new ArrayList<String>();

  private String relatedNum;

  private String giftNum;

  // 使用限制次数
  @Length(8)
  @Digit
  @Range(min = 1, max = 99999999)
  @Metadata(name = "使用限制次数")
  private String useLimit;

  private Date updateDatetime;
  
  
  //计算商品数量类型
  private List<CodeAttribute> commodityNumTypeList = new ArrayList<CodeAttribute>();
  
  
  @Length(8)
  @Digit
  @Range(min = 0, max = 99999999)
  @Metadata(name = "最小购买数")
  private String minCommodityNum;
  
  
  /**
   * 
   * @return 使用限制次数
   */
  public String getUseLimit() {
    return useLimit;
  }

  
  /**
   * updateDatetimeを取得します。
   * 
   * @return updateDatetime
   */
  public Date getUpdateDatetime() {
    return DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * updateDatetimeを設定します。
   * 
   * @param updateDatetime
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
  }
  
  
  /**
   * 
   * @param useLimit
   *          使用限制次数
   */
  public void setUseLimit(String useLimit) {
    this.useLimit = useLimit;
  }

  public String getRelatedNum() {
    return relatedNum;
  }

  public void setRelatedNum(String relatedNum) {
    this.relatedNum = relatedNum;
  }

  public String getGiftNum() {
    return giftNum;
  }

  public void setGiftNum(String giftNum) {
    this.giftNum = giftNum;
  }

  public List<String> getCheckedCode() {
    return checkedCode;
  }

  public void setCheckedCode(List<String> checkedCode) {
    this.checkedCode = checkedCode;
  }

  public List<String> getCheckedGiftCode() {
    return checkedGiftCode;
  }

  public void setCheckedGiftCode(List<String> checkedGiftCode) {
    this.checkedGiftCode = checkedGiftCode;
  }

  public List<String> getPrefectureCode() {
    return prefectureCode;
  }

  public void setPrefectureCode(List<String> prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  public static class RelatedCommodityBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // 关联商品编号
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "关联商品编号")
    private String relatedComdtyCode;

    // 关联商品名称
    private String relatedComdtyName;

    /**
     * 
     * @return
     */
    public String getRelatedComdtyCode() {
      return relatedComdtyCode;
    }

    /**
     * 
     * @param relatedComdtyCode
     */
    public void setRelatedComdtyCode(String relatedComdtyCode) {
      this.relatedComdtyCode = relatedComdtyCode;
    }

    /**
     * 
     * @return
     */
    public String getRelatedComdtyName() {
      return relatedComdtyName;
    }

    /**
     * 
     * @param relatedComdtyName
     */
    public void setRelatedComdtyName(String relatedComdtyName) {
      this.relatedComdtyName = relatedComdtyName;
    }

  }

  public static class GiftCommodityBean implements Serializable {

    /**
		 * 
		 */
    private static final long serialVersionUID = 1L;

    // 赠送商品编号
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "赠送商品编号")
    private String giftComdtyCode;

    // 赠送商品名称
    private String giftComdtyName;

    /**
     * 
     * @return 赠送商品编号
     */
    public String getGiftComdtyCode() {
      return giftComdtyCode;
    }

    /**
     * 
     * @param relatedComdtyCode
     *          赠送商品编号
     */
    public void setGiftComdtyCode(String giftComdtyCode) {
      this.giftComdtyCode = giftComdtyCode;
    }

    /**
     * 
     * @return 赠送商品名称
     */
    public String getGiftComdtyName() {
      return giftComdtyName;
    }

    /**
     * 
     * @param relatedComdtyName
     *          赠送商品名称
     */
    public void setGiftComdtyName(String giftComdtyName) {
      this.giftComdtyName = giftComdtyName;
    }

  }

  public static class PrefectureBean implements Serializable {
    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 都道府県コード */
    private String prefectureCode;

    /** 配送地域名称 */
    private String regionBlockName;

    /** 标识地域是否被选中 */
    private boolean selected;

    /**
     * regionBlockNameを取得します。
     * 
     * @return regionBlockName
     */
    public String getRegionBlockName() {
      return regionBlockName;
    }

    /**
     * regionBlockNameを設定します。
     * 
     * @param regionBlockName
     *          regionBlockName
     */
    public void setRegionBlockName(String regionBlockName) {
      this.regionBlockName = regionBlockName;
    }

    public boolean isSelected() {
      return selected;
    }

    public void setSelected(boolean selected) {
      this.selected = selected;
    }

    public String getPrefectureCode() {
      return prefectureCode;
    }

    public void setPrefectureCode(String prefectureCode) {
      this.prefectureCode = prefectureCode;
    }

  }

  /**
   * 
   * @return 促销编号
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * 
   * @param campaignCode促销编号
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * 
   * @return 促销名称(说明)
   */
  public String getCampaignName() {
    return campaignName;
  }

  /**
   * 
   * @param campaignName
   *          促销名称(说明)
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * 
   * @return 促销英文名
   */
  public String getCampaignNameEn() {
    return campaignNameEn;
  }

  /**
   * 
   * @param campaignNameEn
   *          促销英文名
   */
  public void setCampaignNameEn(String campaignNameEn) {
    this.campaignNameEn = campaignNameEn;
  }

  /**
   * 
   * @return 促销日文名
   */
  public String getCampaignNameJp() {
    return campaignNameJp;
  }

  /**
   * 
   * @param campaignNameJp
   *          促销日文名
   */
  public void setCampaignNameJp(String campaignNameJp) {
    this.campaignNameJp = campaignNameJp;
  }

  /**
   * 
   * @return 开始日期（年月日 时）
   */
  public String getCampaignStartDate() {
    return campaignStartDate;
  }

  /**
   * 
   * @param campaignStartDate
   *          开始日期（年月日 时）
   */
  public void setCampaignStartDate(String campaignStartDate) {
    this.campaignStartDate = campaignStartDate;
  }

  /**
   * 
   * @return 结束日期（年月日 时）
   */
  public String getCampaignEndDate() {
    return campaignEndDate;
  }

  /**
   * 
   * @param campaignEndDate
   *          结束日期（年月日 时）
   */
  public void setCampaignEndDate(String campaignEndDate) {
    this.campaignEndDate = campaignEndDate;
  }

  /**
   * 
   * @return 活动类型（1：免邮费 2：折扣 3：礼品）
   */
  public List<CodeAttribute> getCampaignTypeList() {
    return campaignTypeList;
  }

  /**
   * 
   * @param campaignTypeList
   *          活动类型（1：免邮费 2：折扣 3：礼品）
   */
  public void setCampaignTypeList(List<CodeAttribute> campaignTypeList) {
    this.campaignTypeList = campaignTypeList;
  }

  /**
   * 
   * @return 条件类型（1：订单对象商品 2：订单地址区域 3：会员等级 4：订单金额 5：商品合计重量）
   */
  public List<CodeAttribute> getCampaignConditionTypeList() {
    return campaignConditionTypeList;
  }

  /**
   * 
   * @param campaignConditionTypeList
   *          条件类型（1：订单对象商品 2：订单地址区域 3：会员等级 4：订单金额 5：商品合计重量）
   */
  public void setCampaignConditionTypeList(List<CodeAttribute> campaignConditionTypeList) {
    this.campaignConditionTypeList = campaignConditionTypeList;
  }

  /**
   * 
   * @return 订购次数限制
   */
  public String getOrderLimit() {
    return orderLimit;
  }

  /**
   * 
   * @param orderLimit
   *          订购次数限制
   */
  public void setOrderLimit(String orderLimit) {
    this.orderLimit = orderLimit;
  }

  /**
   * 
   * @return 对象商品（包含/仅有）
   */
  public List<CodeAttribute> getCampaignConditionFlgList() {
    return campaignConditionFlgList;
  }

  /**
   * 
   * @param campaignConditionFlgList
   *          对象商品（包含/仅有）
   */
  public void setCampaignConditionFlgList(List<CodeAttribute> campaignConditionFlgList) {
    this.campaignConditionFlgList = campaignConditionFlgList;
  }

  /**
   * 
   * @return 对象商品限定件数
   */
  public String getMaxCommodityNum() {
    return maxCommodityNum;
  }

  /**
   * 
   * @param maxCommodityNum
   *          对象商品限定件数
   */
  public void setMaxCommodityNum(String maxCommodityNum) {
    this.maxCommodityNum = maxCommodityNum;
  }

  /**
   * 
   * @return 备注
   */
  public String getRemarks() {
    return remarks;
  }

  /**
   * 
   * @param remarks
   *          备注
   */
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  /**
   * 
   * @return 折扣区分
   */
  public List<CodeAttribute> getDiscountTypeList() {
    return discountTypeList;
  }

  /**
   * 
   * @param discountType
   *          折扣区分
   */
  public void setDiscountTypeList(List<CodeAttribute> discountTypeList) {
    this.discountTypeList = discountTypeList;
  }

  /**
   * 
   * @return 折扣比例
   */
  public String getDiscoutRate() {
    return discoutRate;
  }

  /**
   * 
   * @param discoutRate
   *          折扣比例
   */
  public void setDiscoutRate(String discoutRate) {
    this.discoutRate = discoutRate;
  }

  /**
   * 
   * @return 折扣金额
   */
  public String getDiscountMoney() {
    return discountMoney;
  }

  /**
   * 
   * @param discountMoney
   *          折扣金额
   */
  public void setDiscountMoney(String discountMoney) {
    this.discountMoney = discountMoney;
  }

  /**
   * 
   * @return 注文金额
   */
  public String getOrderAmount() {
    return orderAmount;
  }

  /**
   * 
   * @param orderAmount
   *          注文金额
   */
  public void setOrderAmount(String orderAmount) {
    this.orderAmount = orderAmount;
  }

  /**
   * 
   * @return 广告媒体编号
   */
  public String getAdvertCode() {
    return advertCode;
  }

  /**
   * 
   * @param advertCode
   *          广告媒体编号
   */
  public void setAdvertCode(String advertCode) {
    this.advertCode = advertCode;
  }

  /**
   * 
   * @return 关联商品
   */
  public String getRelatedCommodity() {
    return relatedCommodity;
  }

  /**
   * 
   * @param relatedCommodity
   *          关联商品
   */
  public void setRelatedCommodity(String relatedCommodity) {
    this.relatedCommodity = relatedCommodity;
  }

  /**
   * 
   * @return 配送地址
   */
  public String getDeliveryArea() {
    return deliveryArea;
  }

  /**
   * 
   * @param deliveryArea
   *          配送地址
   */
  public void setDeliveryArea(String deliveryArea) {
    this.deliveryArea = deliveryArea;
  }

  /**
   * 
   * @return 赠品
   */
  public String getGiftCommodity() {
    return giftCommodity;
  }

  /**
   * 
   * @param giftCommodity
   *          赠品
   */
  public void setGiftCommodity(String giftCommodity) {
    this.giftCommodity = giftCommodity;
  }

  /**
   * 
   * @return 关联商品部分显示与否
   */
  public boolean getDisRelatedButtonFlg() {
    return disRelatedButtonFlg;
  }

  /**
   * 
   * @param disRelatedButtonFlg
   *          关联商品部分显示与否
   */
  public void setDisRelatedButtonFlg(boolean disRelatedButtonFlg) {
    this.disRelatedButtonFlg = disRelatedButtonFlg;
  }

  /**
   * 
   * @return disGiftButtonFlg
   */
  public boolean getDisAreaButtonFlg() {
    return disAreaButtonFlg;
  }

  /**
   * 
   * @param disGiftButtonFlg
   *          disGiftButtonFlg
   */
  public void setDisAreaButtonFlg(boolean disAreaButtonFlg) {
    this.disAreaButtonFlg = disAreaButtonFlg;
  }

  /**
   * 
   * @return 不同的标志
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * 
   * @param displayMode
   *          不同的标志
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * 
   * @return 活动类型
   */
  public String getCampaignType() {
    return campaignType;
  }

  /**
   * 
   * @param campaignType
   *          活动类型
   */
  public void setCampaignType(String campaignType) {
    this.campaignType = campaignType;
  }

  /**
   * 
   * @return 对象类型
   */
  public String getCampaignConditionType() {
    return campaignConditionType;
  }

  /**
   * 
   * @param campaignConditionType
   *          对象类型
   */
  public void setCampaignConditionType(String campaignConditionType) {
    this.campaignConditionType = campaignConditionType;
  }

  /**
   * 
   * @return 对象商品区分
   */
  public String getCampaignConditionFlg() {
    return campaignConditionFlg;
  }

  /**
   * 
   * @param campaignConditionFlg
   *          对象商品区分
   */
  public void setCampaignConditionFlg(String campaignConditionFlg) {
    this.campaignConditionFlg = campaignConditionFlg;
  }

  /**
   * 
   * @return 折扣类型
   */
  public String getDiscountType() {
    return discountType;
  }

  /**
   * 
   * @param discountType
   *          折扣类型
   */
  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

  /**
   * 
   * @return 关联商品
   */
  public List<RelatedCommodityBean> getRelatedList() {
    return relatedList;
  }

  /**
   * 
   * @param relatedList关联商品
   */
  public void setRelatedList(List<RelatedCommodityBean> relatedList) {
    this.relatedList = relatedList;
  }

  /**
   * 
   * @return 赠送商品
   */
  public List<GiftCommodityBean> getGiftList() {
    return giftList;
  }

  /***
   * 
   * @param giftList
   *          赠送商品
   */
  public void setGiftList(List<GiftCommodityBean> giftList) {
    this.giftList = giftList;
  }

  public RelatedCommodityBean getRelatedCommodityBean() {
    return relatedCommodityBean;
  }

  public void setRelatedCommodityBean(RelatedCommodityBean relatedCommodityBean) {
    this.relatedCommodityBean = relatedCommodityBean;
  }

  public GiftCommodityBean getGiftCommodityBean() {
    return giftCommodityBean;
  }

  public void setGiftCommodityBean(GiftCommodityBean giftCommodityBean) {
    this.giftCommodityBean = giftCommodityBean;
  }

  public List<PrefectureBean> getPrefectureBeanList() {
    return prefectureBeanList;
  }

  public void setPrefectureBeanList(List<PrefectureBean> prefectureBeanList) {
    this.prefectureBeanList = prefectureBeanList;
  }

  public boolean getDisplayLoginButtonFlg() {
    return displayLoginButtonFlg;
  }

  public void setDisplayLoginButtonFlg(boolean displayLoginButtonFlg) {
    this.displayLoginButtonFlg = displayLoginButtonFlg;
  }

  public boolean getDisplayUpdateButtonFlg() {
    return displayUpdateButtonFlg;
  }

  public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
    this.displayUpdateButtonFlg = displayUpdateButtonFlg;
  }

  public boolean getDisplayDeleteFlg() {
    return displayDeleteFlg;
  }

  public void setDisplayDeleteFlg(boolean displayDeleteFlg) {
    this.displayDeleteFlg = displayDeleteFlg;
  }

  public boolean getDisplayReadFlg() {
    return displayReadFlg;
  }

  public void setDisplayReadFlg(boolean displayReadFlg) {
    this.displayReadFlg = displayReadFlg;
  }

  
  /**
   * @return the giftAmount
   */
  public String getGiftAmount() {
    return giftAmount;
  }


  
  /**
   * @param giftAmount the giftAmount to set
   */
  public void setGiftAmount(String giftAmount) {
    this.giftAmount = giftAmount;
  }


  public List<String> getCheckList() {
    return checkList;
  }

  public void setCheckList(List<String> checkList) {
    this.checkList = checkList;
  }

  public boolean getDisGiftButtonFlg() {
    return disGiftButtonFlg;
  }

  public void setDisGiftButtonFlg(boolean disGiftButtonFlg) {
    this.disGiftButtonFlg = disGiftButtonFlg;
  }

  @Override
  public void setSubJspId() {
  }

  /**
   *取得页面参数
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    this.getCheckList().clear();

    String[] array = reqparam.getAll("campaignConditionType");
    if (array != null && array.length > 0) {
      for (int i = 0; i < array.length; i++) {
        if (!StringUtil.isNullOrEmpty(array[i])) {
          this.getCheckList().add(array[i]);
        }
      }
    }

    this.setPrefectureCode(Arrays.asList(reqparam.getAll("areaId")));
    this.setCheckedCode(Arrays.asList(reqparam.getAll("checkBox")));
    this.setCheckedGiftCode(Arrays.asList(reqparam.getAll("checkBoxGift")));
    this.getRelatedCommodityBean().setRelatedComdtyCode(reqparam.get("relatedComdtyCode"));
    this.getGiftCommodityBean().setGiftComdtyCode(reqparam.get("giftComdtyCode"));
    this.setCampaignStartDate(reqparam.getDateTimeString("campaignStartDate"));
    this.setCampaignEndDate(reqparam.getDateTimeString("campaignEndDate"));
    this.setGiftAmount(reqparam.get("giftAmount"));
    
  }

  public String getModuleId() {
    return "U1061020";
  }

  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditBean.001");
  }


  
  /**
   * @return the commodityNumTypeList
   */
  public List<CodeAttribute> getCommodityNumTypeList() {
    return commodityNumTypeList;
  }


  
  /**
   * @param commodityNumTypeList the commodityNumTypeList to set
   */
  public void setCommodityNumTypeList(List<CodeAttribute> commodityNumTypeList) {
    this.commodityNumTypeList = commodityNumTypeList;
  }


  
  /**
   * @return the minCommodityNum
   */
  public String getMinCommodityNum() {
    return minCommodityNum;
  }


  
  /**
   * @param minCommodityNum the minCommodityNum to set
   */
  public void setMinCommodityNum(String minCommodityNum) {
    this.minCommodityNum = minCommodityNum;
  }



}
