package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.CurrencyValidator;
import jp.co.sint.webshop.validation.DigitValidator;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean.BrandDetailBean;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean.CategoryDetailBean;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean.CommoditiyDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public abstract class FriendCouponRuleEditBaseAction extends WebBackAction<FriendCouponRuleEditBean> {

  public boolean authorize() {

    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    return true;
  }

  /**
   * 根据规则编号验证规则是否存在
   * 
   * @param couId
   *          :规则编号
   * @return true:存在; false:不存在;
   */
  public boolean checktoDuplicate(String couponId) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据规则编号得到优惠规则对象
    FriendCouponRule couponRule = communicationService.selectFriendCouponRule(couponId);

    // 如果 对象不存在,提示错误信息
    if (couponRule == null) {
      return false;
    }
    return true;

  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    boolean result = true;
    FriendCouponRuleEditBean bean = getBean();

    // 设置优惠券发行类别为"公共发行"
    bean.setCouponIssueType(CouponType.COMMON_DISTRIBUTION.getValue());

    result = validateBean(bean);
    if (result) {

      // 如果发行类别是比例
      if (bean.getCouponIssueType().equals(CampaignType.PROPORTION.getValue())) {
        if (Long.valueOf(bean.getCouponAmountNumeric()) > 100) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.CAMPAIGN_NUMBER_RANGE_OVER_ERROR));
          return false;
        }

        if (!StringUtil.isNullOrEmpty(bean.getRatio())) {
          if (Double.parseDouble(bean.getRatio()) <= 0) {
            addErrorMessage("优惠比例必须大于零");
            return false;
          }
        }
      }
    }
    return false;
  }

  /**
   * 验证优惠券数值
   */
  public boolean checkCouponAmount(FriendCouponRuleEditBean bean) {

    // 如果优惠劵类别是比例优惠,取得比例优惠的值
    if (CampaignType.PROPORTION.getValue().equals(bean.getCouponIssueType())) {
      // 如果比例优惠的值为空,报错
      if (StringUtil.isNullOrEmpty(bean.getRatio())) {
        addErrorMessage(WebMessage.get(CommunicationErrorMessage.RATIO_NULL_ERROR));
        return false;
      } else {
        DigitValidator dv = new DigitValidator();
        // 比例优惠的值合法的情况下
        if (dv.isValid(bean.getRatio())) {
          setBean(bean);
          setRequestBean(bean);
        } else {
          // 比例输入的数据不正确,报错
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.RATIO_ERROR));
          return false;
        }
      }
    }
    // 如果优惠劵类别是金额优惠,取得金额优惠的值
    if (CampaignType.FIXED.getValue().equals(bean.getCouponIssueType())) {

      // 如果金额优惠的值为空,报错
      if (StringUtil.isNullOrEmpty(bean.getCouponAmountNumeric())) {

        addErrorMessage(WebMessage.get(CommunicationErrorMessage.MONEY_NULL_ERROR));
        return false;
      } else {
        CurrencyValidator cv = new CurrencyValidator();
        // 金额优惠的值合法的情况下
        if (cv.isValid(bean.getCouponAmountNumeric())) {
          setBean(bean);
          setRequestBean(bean);

        } else {
          // 金额输入的数据不正确,报错
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.MONEY_ERROR));
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 根据规则编号得到对象,显示在页面
   * 
   * @param 规则编号
   */
  public void showToBean(String couponId) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据规则编号得到优惠规则对象
    FriendCouponRule rule = communicationService.selectFriendCouponRule(couponId);

    if (rule != null) {
      FriendCouponRuleEditBean bean = new FriendCouponRuleEditBean();

      // 将实体对象赋值给页面bean中
      setBeanfromDTO(rule, bean);

      bean.setCommoditiyDetailBeanList(getAllRelatedCommodity(bean.getFriendCouponRuleNo()));
      bean.setBrandDetailBeanList(getAllRelatedBrand(bean.getFriendCouponRuleNo()));

      // 20130927 txw add start
      bean.setCategoryDetailList(getAllRelatedCategory(bean.getFriendCouponRuleNo()));

      CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
      bean.setCategoryList(service.getAllCategory());
      // 20130927 txw add end

      // 显示更新按钮
      bean.setDisplayRegistButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(true);
      bean.setDisplayDeleteFlg(true);

      setBean(bean);
      setRequestBean(bean);

    } else {
      // 页面初期化设置
      initPageSet();
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
    }
  }

  /**
   * 将实体对象赋值给页面bean中
   * 
   * @param 实体对象
   */
  public void setBeanfromDTO(FriendCouponRule rule, FriendCouponRuleEditBean bean) {
    bean.setFriendCouponRuleNo(rule.getFriendCouponRuleNo());
    bean.setFriendCouponRuleCn(rule.getFriendCouponRuleCn());
    bean.setFriendCouponRuleJp(rule.getFriendCouponRuleJp());
    bean.setFriendCouponRuleEn(rule.getFriendCouponRuleEn());
    bean.setIssueDateType(NumUtil.toString(rule.getIssueDateType()));
    if (bean.getIssueDateType().equals("0")) {
      bean.setIssueDateNum(NumUtil.toString(rule.getIssueDateNum()));
      bean.setIssueStartDate(null);
      bean.setIssueEndDate(null);
    } else {
      Date startDate = DateUtil.getMin();
      Date endDate = DateUtil.getMin();
      if (rule.getIssueStartDate() != null) {
        startDate = rule.getIssueStartDate();
      }
      if (rule.getIssueEndDate() != null) {
        endDate = rule.getIssueEndDate();
      }
      bean.setIssueStartDate(DateUtil.toDateTimeString(startDate, "yyyy/MM/dd HH:mm"));
      bean.setIssueEndDate(DateUtil.toDateTimeString(endDate, "yyyy/MM/dd HH:mm"));
      bean.setIssueDateNum(null);
    }
    bean.setOrderHistory(NumUtil.toString(rule.getOrderHistory()));
 
    bean.setPersonalUseLimit(NumUtil.toString(rule.getPersonalUseLimit()));
    bean.setSiteUseLimit(NumUtil.toString(rule.getSiteUseLimit()));
    bean.setMinUseOrderAmount(NumUtil.toString(rule.getMinUseOrderAmount()));
    bean.setUseValidType(NumUtil.toString(rule.getUseValidType()));
    bean.setUseValidNum(NumUtil.toString(rule.getUseValidNum()));
    bean.setApplicableObjects(NumUtil.toString(rule.getApplicableObjects()));
    bean.setObtainPoint(NumUtil.toString(rule.getObtainPoint()));
    bean.setFixChar(rule.getFixChar());

    // 20140404 hdh add start
    bean.setCouponIssueType(NumUtil.toString(rule.getCouponIssueType()));
    bean.setCouponUseNum(NumUtil.toString(rule.getCouponUseNum()));
    bean.setFormerUsePoint(NumUtil.toString(rule.getFormerUsePoint()));
    bean.setIssueObtainPoint(NumUtil.toString(rule.getIssueObtainPoint()));
    bean.setMaxUseOrderAmount(NumUtil.toString(rule.getMaxUseOrderAmount()));
    if(CampaignType.FIXED.getValue().equals(NumUtil.toString(rule.getCouponIssueType()))){
      bean.setCouponAmountNumeric((rule.getCouponAmount().toString()));
    }else if(CampaignType.PROPORTION.getValue().equals(NumUtil.toString(rule.getCouponIssueType()))){
      
      bean.setRatio(NumUtil.toString(rule.getCouponProportion()));
      
    }
    bean.setUseType(NumUtil.toString(rule.getUseType()));
   
    // 判断月份数值并补0
    if (StringUtil.hasValue(bean.getIssueDateNum())) {
      String month = null;
      if (rule.getIssueDateNum() < 10) {
        month = "0" + rule.getIssueDateNum();
      } else {
        month = NumUtil.toString(rule.getIssueDateNum());
      }
      bean.setIssueDateNum(month);
    }

  }

  /**
   * 显示提示信息
   * 
   * @param 规则编号
   */
  public void setCompleteMessage(String couponId) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据规则编号得到优惠规则对象
    FriendCouponRule rule = communicationService.selectFriendCouponRule(couponId);

    if (rule != null) {

      // 注册时
      String action = getRequestParameter().getPathArgs()[0];
      if (WebConstantCode.COMPLETE_INSERT.equals(action) && !(StringUtil.isNullOrEmpty(getBean().getFriendCouponRuleCn()))) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "朋友优惠券规则"));
      }
      // 更新时
      if (WebConstantCode.COMPLETE_UPDATE.equals(action) && !(StringUtil.isNullOrEmpty(getBean().getFriendCouponRuleCn()))) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "朋友优惠券规则"));
      }
    }
  }

  /**
   * 将页面bean的属性赋值给dto中
   */
  public FriendCouponRule setDTOfromBean(FriendCouponRuleEditBean bean, FriendCouponRule rule) {

    rule.setFriendCouponRuleNo(bean.getFriendCouponRuleNo());
    rule.setFriendCouponRuleCn(bean.getFriendCouponRuleCn());
    rule.setFriendCouponRuleJp(bean.getFriendCouponRuleJp());
    rule.setFriendCouponRuleEn(bean.getFriendCouponRuleEn());
    rule.setIssueDateType(NumUtil.toLong(bean.getIssueDateType()));  //?
    if ("0".equals(bean.getIssueDateType())) {
      rule.setIssueDateNum(NumUtil.toLong(bean.getIssueDateNum()));
      rule.setIssueStartDate(DateUtil.fromString(null, true));
      rule.setIssueEndDate(DateUtil.fromString(null, true));
    } else {
      rule.setIssueDateNum(NumUtil.toLong(null));
      rule.setIssueStartDate(DateUtil.fromString(bean.getIssueStartDate(), true));
      rule.setIssueEndDate(DateUtil.fromString(bean.getIssueEndDate(), true));
    }
    rule.setOrderHistory(NumUtil.toLong(bean.getOrderHistory()));
    rule.setCouponAmount(NumUtil.parse(bean.getCouponAmountNumeric()));
    rule.setPersonalUseLimit(NumUtil.toLong(bean.getPersonalUseLimit()));
    rule.setSiteUseLimit(NumUtil.toLong(bean.getSiteUseLimit()));
    rule.setMinUseOrderAmount(NumUtil.parse(bean.getMinUseOrderAmount()));
    rule.setUseValidType(NumUtil.toLong(bean.getUseValidType()));
    rule.setUseValidNum(NumUtil.toLong(bean.getUseValidNum()));
    rule.setApplicableObjects(NumUtil.toLong(bean.getApplicableObjects()));
    rule.setObtainPoint(NumUtil.toLong(bean.getObtainPoint()));
    rule.setFixChar(bean.getFixChar());
    
    
    // 20140404 hdh add start
    rule.setCouponIssueType(NumUtil.toLong(bean.getCouponIssueType()));
    rule.setIssueObtainPoint(NumUtil.toLong(bean.getIssueObtainPoint()));
    rule.setFormerUsePoint(NumUtil.toLong(bean.getFormerUsePoint()));
    rule.setCouponUseNum(NumUtil.toLong(bean.getCouponUseNum()));
    if(CampaignType.FIXED.getValue().equals(bean.getCouponIssueType())){
      rule.setCouponAmount(NumUtil.parse(bean.getCouponAmountNumeric()));
    }else if(CampaignType.PROPORTION.getValue().equals(bean.getCouponIssueType())){
      rule.setCouponProportion(NumUtil.toLong(bean.getRatio()));
      
    }
    rule.setMaxUseOrderAmount(NumUtil.parse(bean.getMaxUseOrderAmount()));
    
    rule.setUseType(NumUtil.toLong(bean.getUseType()));
    
    return rule;
  }

  // 验证添加的信息是否在商品表中存在
  public boolean isCommodity(FriendCouponRuleEditBean bean, boolean relatedFlg) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = null;

    if (relatedFlg) {
      commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean.getCommoditiyDetailBean()
          .getCommodityCode(), true, false);
    }
    if (commodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 验证添加的信息是否是关联商品
  public boolean isRelated(FriendCouponRuleEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean
        .getCommoditiyDetailBean().getCommodityCode(), true, false);
    if (commodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 判断是否已经存在
  public boolean isExists(FriendCouponRuleEditBean bean, FriendCouponRule rule) {
    boolean flg = false;
    String attributeValue = setRelatedBean(bean, rule);
    if (StringUtil.isNullOrEmpty(attributeValue)) {
      attributeValue = bean.getCommoditiyDetailBean().getCommodityCode();
    } else {
      String[] array = new String[] {};
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(";");
      }
      for (int i = 0; i < array.length; i++) {
        if (bean.getCommoditiyDetailBean().getCommodityCode().equals(array[i])) {
          flg = true;
          break;
        }

      }
    }
    return flg;
  }

  // 拼接
  public String setJoin(FriendCouponRuleEditBean bean, FriendCouponRule rule) {
    String attributeValue = setRelatedBean(bean, rule);
    if (!StringUtil.isNullOrEmpty(attributeValue)) {
      attributeValue += ";" + bean.getCommoditiyDetailBean().getCommodityCode();
    } else {
      attributeValue = bean.getCommoditiyDetailBean().getCommodityCode();
    }
    return attributeValue;
  }

  /**
   * 关联商品登录
   * 
   * @param
   * @param
   * @return
   */
  private String setRelatedBean(FriendCouponRuleEditBean bean, FriendCouponRule newCouponRule) {
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    FriendCouponRule rule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());
    String attributeValue = "";

    if (rule != null) {
      attributeValue = rule.getObjectCommodities();
    }

    return attributeValue;
  }

  // 拼接Brand
  public String setBrandJoin(FriendCouponRuleEditBean bean, FriendCouponRule newCouponRule) {
    String attributeValue = newCouponRule.getObjectBrand();
    if (attributeValue == null) {
      attributeValue = "";
    }
    if (!StringUtil.isNullOrEmpty(attributeValue)) {
      attributeValue += ";" + createBrandListToString(bean, attributeValue);
    } else {
      attributeValue = createBrandListToString(bean, attributeValue);
    }
    return attributeValue;
  }

  public String createBrandListToString(FriendCouponRuleEditBean bean, String attributeValue) {
    String[] couponBrandList = bean.getBrandDetailBean().getBrandCode().split("\r\n");
    String[] list = attributeValue.split(";");
    String str = "";
    if (StringUtil.isNullOrEmpty(bean.getBrandDetailBean().getLimitedNum())) {
      bean.getBrandDetailBean().setLimitedNum("");
    }
    for (int i = 0; i < couponBrandList.length; i++) {
      if (!(attributeValue.contains(";" + couponBrandList[i] + ":") || couponBrandList[i].equals(list[0].split(":")[0]))) {
        if (StringUtil.isNullOrEmpty(str)) {
          str = couponBrandList[i] + ":" + bean.getBrandDetailBean().getLimitedNum();
        } else {
          str += ";" + couponBrandList[i] + ":" + bean.getBrandDetailBean().getLimitedNum();
        }
      }
    }
    return str;
  }

  // 折扣券的关联商品登录时，套餐商品时不可登录
  public boolean isSetCommodity(FriendCouponRuleEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean
        .getCommoditiyDetailBean().getCommodityCode(), true, false);
    if (commodityHeader != null && !SetCommodityFlg.OBJECTIN.longValue().equals(commodityHeader.getSetCommodityFlg())) {
      flag = false;
    } else {
      flag = true;
    }
    return flag;
  }

  // 查询关联商品信息
  private List<CommoditiyDetailBean> getAllRelatedCommodity(String code) {
    List<CommoditiyDetailBean> list = new ArrayList<CommoditiyDetailBean>();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    FriendCouponRule rule = communicationService.selectFriendCouponRule(code);
    if (rule != null) {
      String attributeValue = rule.getObjectCommodities();
      String[] array = new String[] {};
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(";");
      }
      CommodityHeader ommodityHeader = null;
      CommoditiyDetailBean commodityBean = null;
      for (int i = 0; i < array.length; i++) {
        ommodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), array[i], true, false);
        commodityBean = new CommoditiyDetailBean();
        if (ommodityHeader != null) {
          commodityBean.setCommodityCode(ommodityHeader.getCommodityCode());
          commodityBean.setCommodityName(ommodityHeader.getCommodityName());
          list.add(commodityBean);
        }

      }
    }
    return list;
  }

  // 查询关联品牌信息
  private List<BrandDetailBean> getAllRelatedBrand(String code) {
    List<BrandDetailBean> list = new ArrayList<BrandDetailBean>();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    FriendCouponRule rule = communicationService.selectFriendCouponRule(code);
    if (rule != null) {
      String attributeValue = rule.getObjectBrand();
      String[] array = new String[] {};
      if (!StringUtil.isNullOrEmpty(attributeValue)) {
        array = attributeValue.split(";");
      }
      Brand brand = null;
      BrandDetailBean brandBean = null;
      for (int i = 0; i < array.length; i++) {
        String[] brandInfo = array[i].split(":");
        brand = catalogService.getBrand(brandInfo[0]);
        brandBean = new BrandDetailBean();
        if (brand != null) {
          brandBean.setBrandCode(brand.getBrandCode());
          brandBean.setBrandName(brand.getBrandName());
          if (brandInfo.length > 1) {
            brandBean.setLimitedNum(brandInfo[1]);
          }

          list.add(brandBean);
        }

      }
    }
    return list;
  }

  public List<String> getApplicableAreaNames(String applicableArea) {
    List<String> applicableAreaNames = new ArrayList<String>();
    String[] temp = null;
    if (!StringUtil.isNullOrEmpty(applicableArea)) {
      temp = applicableArea.split(";");
    } else {
      return null;
    }
    PrefectureDao prefectureDao = DIContainer.getDao(PrefectureDao.class);
    for (int i = 0; i < temp.length; i++) {
      Prefecture prefecture = prefectureDao.load(temp[i].toString());
      applicableAreaNames.add(prefecture.getPrefectureName());
    }

    return applicableAreaNames;
  }

  /**
   * 验证日期关系是否正确
   * 
   * @param 优惠劵发行开始时间
   * @param 优惠劵发行结束时间
   * @return 关系正确 true; 关系不正确 false
   */
  public boolean checkInputDate(FriendCouponRuleEditBean bean) {
    boolean result = true;
    // 优惠劵利用时间验证
    result = StringUtil.isCorrectRange(bean.getIssueStartDate(), bean.getIssueEndDate());
    if (result == false) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券利用期间"));
    }
    return result;
  }


  /**
   * 页面初始化设置
   */
  public void initPageSet() {
    FriendCouponRuleEditBean bean = new FriendCouponRuleEditBean();

    // 按钮设置
    bean.setDisplayRegistButtonFlg(true);
    bean.setDisplayUpdateButtonFlg(false);
    bean.setDisplayDeleteFlg(false);
    bean.setCouponIssueType(CampaignType.PROPORTION.getValue());
//    bean.setIssuingMode(IssuingMode.NO_LIMIT.getValue());
//    bean.setCouponTypeName(CouponType.COMMON_DISTRIBUTION.getName());
//    bean.setRegionBlockChargeList(getRegionBlockChargeOrgList());
    bean.setDisplayMode("edit");
    setRequestBean(bean);
    setBean(bean);

  }

  // 20130929 txw add start
  // 查询关联分类信息
  private List<CategoryDetailBean> getAllRelatedCategory(String code) {
    List<CategoryDetailBean> list = new ArrayList<CategoryDetailBean>();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    FriendCouponRule rule = communicationService.selectFriendCouponRule(code);
    if (rule != null) {
      String attributeValue = rule.getObjectCategory();
      String[] array = new String[] {};
      if (StringUtil.hasValue(attributeValue)) {
        array = attributeValue.split(";");
      }
      Category category = null;
      CategoryDetailBean categoryBean = null;
      for (int i = 0; i < array.length; i++) {
        String[] categorInfo = array[i].split(":");
        category = catalogService.getCategory(categorInfo[0]);
        categoryBean = new CategoryDetailBean();
        if (category != null) {
          categoryBean.setCategoryCode(category.getCategoryCode());
          categoryBean.setCategoryName(category.getCategoryNamePc());
          if (categorInfo.length > 1) {
            categoryBean.setLimitedNum(categorInfo[1]);
          }
          list.add(categoryBean);
        }

      }
    }
    return list;
  }

  // 拼接Category
  public String setCategoryJoin(FriendCouponRuleEditBean bean, FriendCouponRule rule) {
    String attributeValue = rule.getObjectCategory();
    if (attributeValue == null) {
      attributeValue = "";
    }

    if (StringUtil.isNullOrEmpty(bean.getCategoryDetailBean().getLimitedNum())) {
      bean.getCategoryDetailBean().setLimitedNum("");
    }

    if (StringUtil.hasValue(attributeValue)) {
      attributeValue += ";" + bean.getCategoryDetailBean().getCategoryCode() + ":" + bean.getCategoryDetailBean().getLimitedNum();
    } else {
      attributeValue = bean.getCategoryDetailBean().getCategoryCode() + ":" + bean.getCategoryDetailBean().getLimitedNum();
    }
    return attributeValue;
  }
  // 20130929 txw add end

}
