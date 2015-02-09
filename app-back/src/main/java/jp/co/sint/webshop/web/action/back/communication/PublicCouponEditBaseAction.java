package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.IssuingMode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.NewCouponRule;
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
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponEditBean;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponEditBean.BrandDetailBean;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponEditBean.CategoryDetailBean;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponEditBean.CommoditiyDetailBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge;
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
public abstract class PublicCouponEditBaseAction extends WebBackAction<PublicCouponEditBean> {

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
  public boolean checktoDuplicate(String couId) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据规则编号得到优惠规则对象
    NewCouponRule couponRule = communicationService.getPrivateCoupon(couId);

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
    PublicCouponEditBean bean = getBean();

    // 设置优惠券发行类别为"公共发行"
    bean.setCouponType(CouponType.COMMON_DISTRIBUTION.getValue());

    result = validateBean(bean);
    if (result) {

      // 如果发行类别是比例
      if (bean.getCouponIssueType().equals(CampaignType.PROPORTION.getValue())) {
        if (Long.valueOf(bean.getCouponAmount()) > 100) {
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
      // 验证日期关系

      // 优惠劵利用时间验证
      result = StringUtil.isCorrectRange(bean.getMinUseStartDatetime(), bean.getMinUseEndDatetime());
      if (result == false) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR, "利用期间"));
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * 验证优惠券数值
   */
  public boolean checkCouponAmount(PublicCouponEditBean bean) {

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
          bean.setCouponAmount(bean.getRatio());
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
      if (StringUtil.isNullOrEmpty(bean.getMoney())) {

        addErrorMessage(WebMessage.get(CommunicationErrorMessage.MONEY_NULL_ERROR));
        return false;
      } else {

        CurrencyValidator cv = new CurrencyValidator();
        // 金额优惠的值合法的情况下
        if (cv.isValid(bean.getMoney())) {
          bean.setCouponAmount(bean.getMoney());
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
    NewCouponRule newCouponRule = communicationService.getPrivateCoupon(couponId);

    if (newCouponRule != null) {
      PublicCouponEditBean bean = new PublicCouponEditBean();

      // 将实体对象赋值给页面bean中
      setBeanfromDTO(newCouponRule, bean);

      bean.setCommoditiyDetailBeanList(getAllRelatedCommodity(bean.getCouponId()));
      bean.setBrandDetailBeanList(getAllRelatedBrand(bean.getCouponId()));

      if (bean.getCheckedAreaDispalyNames() == null) {
        bean.setRegionBlockChargeList(getRegionBlockChargeOrgList());
      } else {

        // 将之前选中的地域选项打上勾
        List<RegionBlockCharge> rbcList = new ArrayList<RegionBlockCharge>();
        for (RegionBlockCharge regionBlock : getRegionBlockChargeOrgList()) {
          for (String selectedRegion : bean.getCheckedAreaDispalyNames()) {
            if (regionBlock.getRegionBlockName().equals(selectedRegion)) {
              regionBlock.setSelected(true);
            }
          }
          rbcList.add(regionBlock);
        }

        // 把打上勾之后的整个地域选项显示在bean上
        bean.setRegionBlockChargeList(rbcList);
      }

      // 20130927 txw add start
      bean.setCategoryDetailList(getAllRelatedCategory(bean.getCouponId()));

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
  public void setBeanfromDTO(NewCouponRule newCouponRule, PublicCouponEditBean bean) {

    bean.setCouponId(newCouponRule.getCouponCode());

    // 固定金额的情况下
    if (CampaignType.FIXED.getValue().equals(String.valueOf(newCouponRule.getCouponIssueType()))) {

      bean.setMoney(String.valueOf(newCouponRule.getCouponAmount()));
    } else {
      if (newCouponRule.getCouponProportion() != null) {
        bean.setRatio(newCouponRule.getCouponProportion().toString());
      }
    }

    bean.setCouponIssueType(String.valueOf(newCouponRule.getCouponIssueType()));
    bean.setCouponName(newCouponRule.getCouponName());
    bean.setIssuingMode(String.valueOf(newCouponRule.getApplicableObjects()));
    bean.setCheckedAreaDispalyNames(getApplicableAreaNames(newCouponRule.getApplicableArea()));
    if (newCouponRule.getUseType() != null) {
      bean.setUseType(newCouponRule.getUseType().toString());
    } else {
      bean.setUseType("0");
    }
    bean.setCouponType(String.valueOf(newCouponRule.getCouponType()));
    bean.setMinIssueOrderAmount(String.valueOf(newCouponRule.getMinIssueOrderAmount()));
    bean.setMinUseEndDatetime(DateUtil.toDateTimeString(newCouponRule.getMinUseEndDatetime()));
    bean.setMinUseOrderAmount(String.valueOf(newCouponRule.getMinUseOrderAmount()));
    bean.setMaxUseOrderAmount(String.valueOf(newCouponRule.getMaxUseOrderAmount()));
    bean.setMinUseStartDatetime(DateUtil.toDateTimeString(newCouponRule.getMinUseStartDatetime()));
    bean.setPersonalUseLimit(String.valueOf(newCouponRule.getPersonalUseLimit()));
    bean.setSiteUseLimit(String.valueOf(newCouponRule.getSiteUseLimit()));
    bean.setObjectCommodities(newCouponRule.getObjectCommodities());
    bean.setObjectBrand(newCouponRule.getObjectBrand());
    bean.setMemo(newCouponRule.getMemo());
    // 更新时间
    bean.setUpdateDatetime(newCouponRule.getUpdatedDatetime());

  }

  /**
   * 显示提示信息
   * 
   * @param 规则编号
   */
  public void setCompleteMessage(String couponId) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据规则编号得到优惠规则对象
    NewCouponRule newCouponRule = communicationService.getPrivateCoupon(couponId);

    if (newCouponRule != null) {

      // 注册时
      String action = getRequestParameter().getPathArgs()[0];
      if (WebConstantCode.COMPLETE_INSERT.equals(action) && !(StringUtil.isNullOrEmpty(getBean().getCouponName()))) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "公共优惠券规则"));
      }
      // 更新时
      if (WebConstantCode.COMPLETE_UPDATE.equals(action) && !(StringUtil.isNullOrEmpty(getBean().getCouponName()))) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "公共优惠券规则"));
      }
    }
  }

  /**
   * 将页面bean的属性赋值给dto中
   */
  public NewCouponRule setDTOfromBean(PublicCouponEditBean bean, NewCouponRule newCouponRule) {

    if (CouponIssueType.FIXED.getValue().equals(bean.getCouponIssueType())) {
      newCouponRule.setCouponAmount(NumUtil.parse(bean.getCouponAmount()));
      newCouponRule.setCouponProportion(null);
    } else {
      newCouponRule.setCouponProportion(NumUtil.toLong(bean.getCouponAmount()));
      newCouponRule.setCouponAmount(null);
    }

    if (StringUtil.hasValue(bean.getUseType())) {
      newCouponRule.setUseType(Long.parseLong(bean.getUseType()));
    } else {
      newCouponRule.setUseType(0L);
    }

    newCouponRule.setCouponIssueType(Long.valueOf(bean.getCouponIssueType()));

    newCouponRule.setCouponName(bean.getCouponName());

    newCouponRule.setApplicableObjects(Long.valueOf(bean.getIssuingMode()));

    newCouponRule.setApplicableArea(getApplicableArea(bean));

    newCouponRule.setCouponType(Long.valueOf(bean.getCouponType()));

    // if(!(StringUtil.isNullOrEmpty(bean.getMinIssueEndDatetime()))){
    // newCouponRule.setMinIssueEndDatetime(DateUtil.fromString(bean
    // .getMinIssueEndDatetime(), true));
    // }

    newCouponRule.setMinUseEndDatetime(DateUtil.fromString(bean.getMinUseEndDatetime(), true));

    newCouponRule.setMinUseOrderAmount(BigDecimal.valueOf(Double.valueOf(bean.getMinUseOrderAmount())));

    if (!StringUtil.isNullOrEmpty(bean.getMaxUseOrderAmount())) {
      newCouponRule.setMaxUseOrderAmount(BigDecimal.valueOf(Double.valueOf(bean.getMaxUseOrderAmount())));
    } else {
      newCouponRule.setMaxUseOrderAmount(null);
    }

    newCouponRule.setMinUseStartDatetime(DateUtil.fromString(bean.getMinUseStartDatetime(), true));

    newCouponRule.setPersonalUseLimit(BigDecimal.valueOf(Double.valueOf(bean.getPersonalUseLimit())));

    newCouponRule.setSiteUseLimit(BigDecimal.valueOf(Double.valueOf(bean.getSiteUseLimit())));

    newCouponRule.setMemo(bean.getMemo());

    newCouponRule.setUpdatedDatetime(bean.getUpdateDatetime());

    return newCouponRule;
  }

  // 验证添加的信息是否在商品表中存在
  public boolean isCommodity(PublicCouponEditBean bean, boolean relatedFlg) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = null;

    if (relatedFlg) {
      commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean.getCommoditiyDetailBean()
          .getCommodityCode(), true, false);
    }
    // if (giftFlg) {
    // commodityHeader =
    // catalogService.loadBycommodityCode(getLoginInfo().getShopCode(),
    // bean.getGiftCommodityBean().getGiftComdtyCode(), false, true);
    // }

    if (commodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 验证添加的信息是否是关联商品
  public boolean isRelated(PublicCouponEditBean bean) {
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
  public boolean isExists(PublicCouponEditBean bean, NewCouponRule newCouponRule) {
    boolean flg = false;
    String attributeValue = setRelatedBean(bean, newCouponRule);
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
  public String setJoin(PublicCouponEditBean bean, NewCouponRule NewCouponRule) {
    String attributeValue = setRelatedBean(bean, NewCouponRule);
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
  private String setRelatedBean(PublicCouponEditBean bean, NewCouponRule newCouponRule) {
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    NewCouponRule rule = communicationService.getPrivateCoupon(bean.getCouponId());
    String attributeValue = "";

    if (rule != null) {
      attributeValue = rule.getObjectCommodities();
    }

    return attributeValue;
  }

  // 拼接Brand
  public String setBrandJoin(PublicCouponEditBean bean, NewCouponRule newCouponRule) {
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

  public String createBrandListToString(PublicCouponEditBean bean, String attributeValue) {
    String[] couponBrandList = bean.getBrandDetailBean().getBrandCode().split("\r\n");
    String[] list = attributeValue.split(";");
    String str = "";
    if(StringUtil.isNullOrEmpty(bean.getBrandDetailBean().getLimitedNum())){
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
  public boolean isSetCommodity(PublicCouponEditBean bean) {
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
    NewCouponRule newCouponRule = communicationService.getPrivateCoupon(code);
    if (newCouponRule != null) {
      String attributeValue = newCouponRule.getObjectCommodities();
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
    NewCouponRule newCouponRule = communicationService.getPrivateCoupon(code);
    if (newCouponRule != null) {
      String attributeValue = newCouponRule.getObjectBrand();
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

  public String getApplicableArea(PublicCouponEditBean bean) {
    String areaStr = "";
    List<String> checkedAreaBlockIdList = bean.getCheckedAreaBlockIdList();
    if (checkedAreaBlockIdList != null) {
      for (int i = 0; i < checkedAreaBlockIdList.size(); i++) {
        if (checkedAreaBlockIdList.size() - 1 > i) {
          areaStr = areaStr + checkedAreaBlockIdList.get(i) + ";";
        } else {
          areaStr = areaStr + checkedAreaBlockIdList.get(i);
        }
      }
    }
    return areaStr;
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
   * @param 优惠劵利用开始时间
   * @param 优惠劵利用结束时间
   * @return 关系正确 true; 关系不正确 false
   */
  public boolean checkInputDate(PublicCouponEditBean bean) {
    boolean result = true;
    // 优惠劵利用时间验证
    result = StringUtil.isCorrectRange(bean.getMinUseStartDatetime(), bean.getMinUseEndDatetime());
    if (result == false) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券利用期间"));
    }
    return result;
  }

  /**
   * 配送地域一览
   * 
   * @return regionBlockList
   */
  public List<RegionBlockCharge> getRegionBlockChargeOrgList() {
    PrefectureDao prefectureDao = DIContainer.getDao(PrefectureDao.class);
    List<Prefecture> prefectureList = prefectureDao.loadAll();

    List<RegionBlockCharge> regionBlockList = new ArrayList<RegionBlockCharge>();
    for (Prefecture block : prefectureList) {
      RegionBlockCharge charge = new RegionBlockCharge();
      charge.setRegionBlockName(block.getPrefectureName());
      charge.setPrefectureCode(block.getPrefectureCode());
      charge.setSelected(false);

      regionBlockList.add(charge);
    }
    return regionBlockList;
  }

  /**
   * 页面初始化设置
   */
  public void initPageSet() {
    PublicCouponEditBean bean = new PublicCouponEditBean();

    // 按钮设置
    bean.setDisplayRegistButtonFlg(true);
    bean.setDisplayUpdateButtonFlg(false);
    bean.setDisplayDeleteFlg(false);
    bean.setCouponIssueType(CampaignType.PROPORTION.getValue());
    bean.setIssuingMode(IssuingMode.NO_LIMIT.getValue());
    bean.setCouponTypeName(CouponType.COMMON_DISTRIBUTION.getName());
    bean.setRegionBlockChargeList(getRegionBlockChargeOrgList());
    setRequestBean(bean);
    setBean(bean);

  }

  // 20130929 txw add start
  // 查询关联分类信息
  private List<CategoryDetailBean> getAllRelatedCategory(String code) {
    List<CategoryDetailBean> list = new ArrayList<CategoryDetailBean>();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    NewCouponRule newCouponRule = communicationService.getPrivateCoupon(code);
    if (newCouponRule != null) {
      String attributeValue = newCouponRule.getObjectCategory();
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
  public String setCategoryJoin(PublicCouponEditBean bean, NewCouponRule newCouponRule) {
    String attributeValue = newCouponRule.getObjectCategory();
    if (attributeValue == null) {
      attributeValue = "";
    }
    
    if(StringUtil.isNullOrEmpty(bean.getCategoryDetailBean().getLimitedNum())){
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
