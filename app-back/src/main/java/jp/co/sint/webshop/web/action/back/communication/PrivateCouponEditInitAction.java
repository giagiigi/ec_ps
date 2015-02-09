package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.ApplicableObject;
import jp.co.sint.webshop.data.domain.BeforeAfterDiscountType;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.NewImportCommodityType;
import jp.co.sint.webshop.data.domain.RelatedCommodityFlg;
import jp.co.sint.webshop.data.domain.UseFlagObject;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.NewCouponRuleLssueInfo;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.service.communication.PlanRelatedHeadLine;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean.BrandBean;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean.CategoryBean;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean.IssueCategoryBean;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean.LssueBrandBean;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean.LssueCommodityBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060620:PRIVATEクーポンマスタのアクションクラスです
 * 
 * @author OB.
 */
public class PrivateCouponEditInitAction extends PrivateCouponEditBaseAction {

  private final String UPDATE = "edit";

  private final String REGISTER = "new";

  private final String PURCHASE = "purchase";

  private final String SPECIAL = "special";
  
  private final String BIRTHDAY = "birthday";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam != null && urlParam.length > 0 && urlParam.length <= 3) {
      return true;
    }
    addErrorMessage(Messages.getString("validation.UrlValidator.0"));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] urlParam = getRequestParameter().getPathArgs();
    PrivateCouponEditBean nextBean = initBean();

    // 设置用户是否有权限导入优惠券规则履历
    if (Permission.PUBLIC_COUPON_DATA_IO.isGranted(getLoginInfo())) {
      nextBean.setCsvImportFlag(true);
    }

    // 20130929 txw add start
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    nextBean.setSelectCategoryList(service.getAllCategory());
    // 20130929 txw add end

    // 更新
    if (UPDATE.equals(urlParam[0])) {
      // 登录成功后跳转到本页面提示登陆成功信息
      if (urlParam.length == 3 && WebConstantCode.COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则"));
      }
      // 2013/04/01 优惠券对应 ob add start
      else if (urlParam.length == 3 && WebConstantCode.LSSUE_COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则发行关联商品"));
      } else if (urlParam.length == 3 && WebConstantCode.USE_COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则使用关联商品"));
      } else if (urlParam.length == 3 && WebConstantCode.BRAND_COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则使用关联品牌"));
      } else if (urlParam.length == 3 && WebConstantCode.USE_DELETE.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "顾客别优惠券发行规则使用关联商品"));
      } else if (urlParam.length == 3 && WebConstantCode.BRAND_DELETE.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "顾客别优惠券发行规则使用关联品牌"));
      } else if (urlParam.length == 3 && WebConstantCode.COMPLETE_DELETE.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "顾客别优惠券发行规则发行关联商品"));
      } else if (urlParam.length == 3 && WebConstantCode.IMPORT_COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则 使用关联商品区分"));
        // 20130929 txw add start
      } else if (urlParam.length == 3 && WebConstantCode.CATEGORY_COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则使用关联分类"));
      } else if (urlParam.length == 3 && WebConstantCode.CATEGORY_DELETE.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "顾客别优惠券发行规则使用关联分类"));
      } else if (urlParam.length == 3 && WebConstantCode.LSSUE_BRAND_DELETE.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "顾客别优惠券发行规则发行关联品牌"));
      } else if (urlParam.length == 3 && WebConstantCode.LSSUE_BRAND_COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则发行关联品牌"));
      } else if (urlParam.length == 3 && WebConstantCode.LSSUE_CATEGORY_DELETE.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "顾客别优惠券发行规则发行关联分类"));
      } else if (urlParam.length == 3 && WebConstantCode.LSSUE_CATEGORY_COMPLETE_REGISTER.equals(urlParam[2])) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客别优惠券发行规则发行关联分类"));
      }
      // 20130929 txw add end
      // 2013/04/01 优惠券对应 ob add end
      nextBean.setCouponCode(urlParam[1]);

      PrivateCouponEditBean bean = createBean(nextBean);

      if (StringUtil.isNullOrEmpty(bean.getCouponCode())) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format("顾客别优惠券发行规则(优惠券规则编号:{0})", nextBean
            .getCouponCode())));
      }
      setBrandListAll(nextBean);
      // 20130929 txw add start
      setCategoryListAll(nextBean);
      // 20130929 txw add end
      setImportCommodityTypeList(urlParam[1], nextBean);
      // 查询使用关联商品明细
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      List<PlanRelatedHeadLine> list = communicationService.getRelatedHeadLineList(nextBean.getCouponCode());
      List<LssueCommodityBean> commodityBeanList = new ArrayList<LssueCommodityBean>();
      for (int i = 0; i < list.size(); i++) {
        LssueCommodityBean commodityBean = new LssueCommodityBean();
        commodityBean.setCommodityCode(list.get(i).getCommodityCode());
        commodityBean.setCommodityName(list.get(i).getCommodityName());
        commodityBean.setDisplayOrder(list.get(i).getDisplayOrder());
        if (list.get(i).getDiscountPrice() != null) {
          commodityBean.setDiscountPrice(list.get(i).getDiscountPrice());
        }
        if (list.get(i).getDiscountPriceStartDatetime() != null) {
          commodityBean.setDiscountPriceStartDatetime(list.get(i).getDiscountPriceStartDatetime());
        }
        if (list.get(i).getDiscountPriceEndDatetime() != null) {
          commodityBean.setDiscountPriceEndDatetime(list.get(i).getDiscountPriceEndDatetime());
        }
        if (list.get(i).getDiscountPrice() != null) {
          if (StringUtil.hasValueAllOf(list.get(i).getDiscountPriceStartDatetime(), list.get(i).getDiscountPriceEndDatetime())) {
            Date startDate = DateUtil.fromString(list.get(i).getDiscountPriceStartDatetime(), true);
            Date endDate = DateUtil.fromString(list.get(i).getDiscountPriceEndDatetime(), true);
            if (DateUtil.isPeriodDate(startDate, endDate)) {
              commodityBean.setDateOut(false);
            }
          }
        }
        commodityBeanList.add(commodityBean);
      }
      bean.setLssueList(commodityBeanList);
      // 20131009 txw add start
      // 查询发行关联品牌明细
      List<LssueBrandBean> lssueBrandList = new ArrayList<LssueBrandBean>();
      List<NewCouponRuleLssueInfo> lssueBrand = communicationService.getPrivateCouponLssue(nextBean.getCouponCode(), true);
      if (lssueBrand != null && lssueBrand.size() > 0) {
        for (NewCouponRuleLssueInfo brand : lssueBrand) {
          for (int i = 0; i < nextBean.getBrandListAll().size(); i++) {
            LssueBrandBean lssueBrandBean = new LssueBrandBean();
            if (nextBean.getBrandListAll().get(i).getValue().equals(brand.getBrandCode())) {
              lssueBrandBean.setBrandCode(nextBean.getBrandListAll().get(i).getValue());
              lssueBrandBean.setBrandName(nextBean.getBrandListAll().get(i).getName());
              lssueBrandList.add(lssueBrandBean);
            }
          }
        }
        nextBean.setLssueBrandList(lssueBrandList);
      }

      // 查询发行关联分类明细
      List<IssueCategoryBean> lssueCategoryList = new ArrayList<IssueCategoryBean>();
      List<NewCouponRuleLssueInfo> lssueCategory = communicationService.getPrivateCouponLssue(nextBean.getCouponCode(), false);
      if (lssueCategory != null && lssueCategory.size() > 0) {
        for (NewCouponRuleLssueInfo category : lssueCategory) {
          for (int i = 0; i < nextBean.getCategoryListAll().size(); i++) {
            IssueCategoryBean categoryBean = new IssueCategoryBean();
            if (nextBean.getCategoryListAll().get(i).getValue().equals(category.getCategoryCode())) {
              categoryBean.setCategoryCode(nextBean.getCategoryListAll().get(i).getValue());
              categoryBean.setCategoryName(nextBean.getCategoryListAll().get(i).getName());
              lssueCategoryList.add(categoryBean);
            }
          }
        }
        nextBean.setLssueCategoryList(lssueCategoryList);
      }
      // 20131009 txw add end
      // 查询使用关联品牌明细
      List<BrandBean> brandList = new ArrayList<BrandBean>();
      CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
      List<NewCouponRuleUseInfo> rule = svc.getPrivateCouponUse(nextBean.getCouponCode(), true);
      if (rule != null && rule.size() > 0) {
        for (NewCouponRuleUseInfo brand : rule) {
          for (int i = 0; i < nextBean.getBrandListAll().size(); i++) {
            BrandBean brandBean = new BrandBean();
            if (nextBean.getBrandListAll().get(i).getValue().equals(brand.getBrandCode())) {
              brandBean.setBrandCode(nextBean.getBrandListAll().get(i).getValue());
              brandBean.setBrandName(nextBean.getBrandListAll().get(i).getName());
              brandList.add(brandBean);

            }
          }
        }
        nextBean.setBrandList(brandList);
      }

      // 20130929 txw add start
      // 查询使用关联分类明细
      List<CategoryBean> categoryList = new ArrayList<CategoryBean>();
      List<NewCouponRuleUseInfo> categoryList1 = svc.getPrivateCouponUse(nextBean.getCouponCode(), "",
          WebConstantCode.CATEGORY_LOAD);
      if (categoryList1 != null && categoryList1.size() > 0) {
        for (NewCouponRuleUseInfo category : categoryList1) {
          for (int i = 0; i < nextBean.getCategoryListAll().size(); i++) {
            CategoryBean categoryBean = new CategoryBean();
            if (nextBean.getCategoryListAll().get(i).getValue().equals(category.getCategoryCode())) {
              categoryBean.setCategoryCode(nextBean.getCategoryListAll().get(i).getValue());
              categoryBean.setCategoryName(nextBean.getCategoryListAll().get(i).getName());
              categoryList.add(categoryBean);

            }
          }
        }
        nextBean.setCategoryList(categoryList);
      }
      // 20130929 txw add end

      // 查询使用关联商品明细
      List<PlanRelatedHeadLine> useList = communicationService.getRelatedHeadLineListUse(nextBean.getCouponCode());
      List<LssueCommodityBean> useCommodityBeanList = new ArrayList<LssueCommodityBean>();
      for (int i = 0; i < useList.size(); i++) {
        LssueCommodityBean commodityBean = new LssueCommodityBean();
        commodityBean.setCommodityCode(useList.get(i).getCommodityCode());
        commodityBean.setCommodityName(useList.get(i).getCommodityName());
        commodityBean.setDisplayOrder(useList.get(i).getDisplayOrder());
        if (useList.get(i).getDiscountPrice() != null) {
          commodityBean.setDiscountPrice(useList.get(i).getDiscountPrice());
        }
        if (useList.get(i).getDiscountPriceStartDatetime() != null) {
          commodityBean.setDiscountPriceStartDatetime(useList.get(i).getDiscountPriceStartDatetime());
        }
        if (useList.get(i).getDiscountPriceEndDatetime() != null) {
          commodityBean.setDiscountPriceEndDatetime(useList.get(i).getDiscountPriceEndDatetime());
        }
        if (useList.get(i).getDiscountPrice() != null) {
          if (StringUtil
              .hasValueAllOf(useList.get(i).getDiscountPriceStartDatetime(), useList.get(i).getDiscountPriceEndDatetime())) {
            Date startDate = DateUtil.fromString(useList.get(i).getDiscountPriceStartDatetime(), true);
            Date endDate = DateUtil.fromString(useList.get(i).getDiscountPriceEndDatetime(), true);
            if (DateUtil.isPeriodDate(startDate, endDate)) {
              commodityBean.setDateOut(false);
            }
          }
        }
        useCommodityBeanList.add(commodityBean);
      }
      bean.setUseList(useCommodityBeanList);

      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;

      // 新建
    } else if (REGISTER.equals(urlParam[0])) {
      // 画面登录按钮显示
      if (Permission.PRIVATE_COUPON_UPDATE_SHOP.isGranted(getLoginInfo())) {
        nextBean.setRegisterNewDisplayFlag(true);
      } else {
        nextBean.setCouponDisplayMode(WebConstantCode.DISPLAY_READONLY);
      }

      // 设置优惠券类别
      nextBean.setCampaignType(CampaignType.PROPORTION.getValue());
      // 2013/04/01 优惠券对应 ob add start
      // 设置有无指定商品
      nextBean.setRelatedCommodityFlg(RelatedCommodityFlg.HAVE.getValue());
      // 设置适用对象
      nextBean.setApplicableObject(ApplicableObject.LIMIT.getValue());
      // 設置优惠券发行金额类别
      nextBean.setBeforeAfterDiscountType(BeforeAfterDiscountType.BEFOREDISCOUNT.getValue());
      // //設置优惠券 使用关联商品区分
      // nextBean.setImportCommodityType(NewImportCommodityType.IMPORT.getValue());
      setBrandListAll(nextBean);

      // 2013/04/01 优惠券对应 ob add end

      // 判断优惠券发行方式
      if (PURCHASE.equals(urlParam[1])) {
        nextBean.setCouponType(CouponType.PURCHASE_DISTRIBUTION.getName());
        nextBean.setCouponTypeFlag(0);
      } else if (SPECIAL.equals(urlParam[1])) {
        nextBean.setCouponType(CouponType.SPECIAL_MEMBER_DISTRIBUTION.getName());
        nextBean.setCouponTypeFlag(1);
      } else if (BIRTHDAY.equals(urlParam[1])) {
        nextBean.setCouponType(CouponType.BIRTHDAY_DISTRIBUTION.getName());
        nextBean.setCouponTypeFlag(3);
      } else {
        addErrorMessage(Messages.getString("validation.UrlValidator.0"));
        this.setRequestBean(nextBean);
        return BackActionResult.RESULT_SUCCESS;
      }
      this.setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;

    } else {
      addErrorMessage(Messages.getString("validation.UrlValidator.0"));
      this.setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }

  }

  // 2013/04/03 优惠券对应 ob add start
  private List<Brand> getBrandList() {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.getAllBrand();
  }

  private void setBrandListAll(PrivateCouponEditBean nextBean) {
    List<CodeAttribute> brandList = new ArrayList<CodeAttribute>();
    List<Brand> brandListAll = getBrandList();
    for (Brand brandAll : brandListAll) {
      brandList.add(new NameValue(brandAll.getBrandName(), brandAll.getBrandCode()));
    }
    nextBean.setBrandListAll(brandList);
  }

  // 20130929 txw add start
  private void setCategoryListAll(PrivateCouponEditBean nextBean) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CodeAttribute> categoryList = new ArrayList<CodeAttribute>();
    List<CategoryInfo> categoryListAll = service.getAllCategory();
    for (CategoryInfo category : categoryListAll) {
      if (!category.getCategoryCode().equals("0")) {
        categoryList.add(new NameValue(category.getCategoryNamePc(), category.getCategoryCode()));
      }
    }
    nextBean.setCategoryListAll(categoryList);
  }

  // 20130929 txw add end

  // 添加使用关联商品区分
  private void setImportCommodityTypeList(String couponCode, PrivateCouponEditBean nextBean) {
    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    List<NewCouponRuleUseInfo> rule = svc.getPrivateCouponUse(couponCode, false);
    if (rule != null && rule.size() > 0) {
      for (NewCouponRuleUseInfo importCommodityType : rule) {
        nextBean.getImportCommodityType().add(importCommodityType.getImportCommodityType() + "");
      }
    }
  }

  // 2013/04/03 优惠券对应 ob add end
  /**
   * 初始化画面Bean
   * 
   * @return 画面Bean
   */
  public PrivateCouponEditBean initBean() {
    List<CodeAttribute> campaignTypes = new ArrayList<CodeAttribute>();
    List<CodeAttribute> relatedCommodityFlgs = new ArrayList<CodeAttribute>();
    List<CodeAttribute> applicableObjects = new ArrayList<CodeAttribute>();
    List<CodeAttribute> useFlagObjects = new ArrayList<CodeAttribute>();
    List<CodeAttribute> beforeAfterDiscountTypes = new ArrayList<CodeAttribute>();
    List<CodeAttribute> importCommodityType = new ArrayList<CodeAttribute>();

    PrivateCouponEditBean bean = new PrivateCouponEditBean();

    // 优惠劵类别下拉框选项设定
    for (CampaignType sk : CampaignType.values()) {
      campaignTypes.add(sk);
    }
    bean.setCampaignTypes(campaignTypes);
    // 2013/04/01 优惠券对应 ob add start
    // 有無指定商品
    for (RelatedCommodityFlg sk : RelatedCommodityFlg.values()) {
      relatedCommodityFlgs.add(sk);
    }
    bean.setRelatedCommodityFlgs(relatedCommodityFlgs);
    // 適用對象
    for (ApplicableObject sk : ApplicableObject.values()) {
      applicableObjects.add(sk);
    }
    bean.setApplicableObjects(applicableObjects);

    for (UseFlagObject ufo : UseFlagObject.values()) {
      useFlagObjects.add(ufo);
    }
    bean.setUseFlagObjects(useFlagObjects);

    // 优惠券发行金额类别
    for (BeforeAfterDiscountType sk : BeforeAfterDiscountType.values()) {
      beforeAfterDiscountTypes.add(sk);
    }
    bean.setBeforeAfterDiscountTypes(beforeAfterDiscountTypes);
    // 使用关联商品区分
    for (NewImportCommodityType sk : NewImportCommodityType.values()) {
      importCommodityType.add(sk);
    }
    bean.setImportCommodityTypeList(importCommodityType);
    // 2013/04/01 优惠券对应 ob add end
    return bean;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 3) {
      completeParam = param[2];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_UPLOAD)) {
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      List<UploadResult> resultList = messageBean.getUploadDetailList();

      if (messageBean.getResult().equals(ResultType.SUCCESS)) {
        addInformationMessage(WebMessage.get(CompleteMessage.CSV_IMPORT_COMPLETE));
      } else if (messageBean.getResult().equals(ResultType.FAILED)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_FAILED));
      } else {
        addWarningMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_PARTIAL, ""));
      }

      for (UploadResult ur : resultList) {

        for (String s : ur.getInformationMessage()) {
          addInformationMessage(s);
        }
        for (String s : ur.getWarningMessage()) {
          addWarningMessage(s);
        }
        for (String s : ur.getErrorMessage()) {
          addErrorMessage(s);
        }
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客组别优惠券发行规则详细信息一览处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106062001";
  }

}
