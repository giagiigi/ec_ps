package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.PlanRelatedHeadLine;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanRelatedBean;
import jp.co.sint.webshop.web.bean.back.communication.PlanRelatedBean.RelatedCommodityBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class PlanRelatedInitAction extends WebBackAction<PlanRelatedBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    String[] args = getRequestParameter().getPathArgs();
    if (args.length == 2 && Permission.PLAN_UPDATE_SHOP.isDenied(login)) {
      return false;
    }
    return Permission.PLAN_READ_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length == 0) {
      throw new URLNotFoundException();
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] params = getRequestParameter().getPathArgs();
    PlanRelatedBean lastBean = getBean();
    PlanRelatedBean bean = new PlanRelatedBean();
    bean.setSuccessList(lastBean.getSuccessList());
    bean.setFailureList(lastBean.getFailureList());

    for (String s : bean.getFailureList()) {
      addErrorMessage(s);
    }
    for (String s : bean.getSuccessList()) {
      addInformationMessage(s);
    }
    bean.getSuccessList().clear();
    bean.getFailureList().clear();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    bean.setPlanTypeMode(communicationService.getPlan(params[0]).getPlanType().toString());
    // 処理完了メッセージ存在時
    if (params.length == 4) {
      if (params[3].equals("register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "关联商品"));
      }
      if (params[3].equals("delete")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "关联商品"));
      }
    }
    // 20130812 txw update start
    String sortNumMode = WebConstantCode.DISPLAY_READONLY;
    // 20130812 txw update end
    // 新建
    if (params.length == 2) {
      bean.setDetailType(params[1]);
      bean.setPlanCode(params[0]);
      bean.setIsUpdateFlg(false);
      bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
      if (PlanDetailType.CATEGORY.getValue().equals(params[1])) {
        setCategoryList(bean);
      } else if (PlanDetailType.BRAND.getValue().equals(params[1])) {
        setBrandList(bean);
      }
      // 更新关联商品
    } else {
      String planCode = params[0];
      String detailType = params[1];
      String detailCode = params[2];
      PlanDetail planDetail = communicationService.getPlanDetail(planCode, detailType, detailCode);
      if (planDetail == null) {
        throw new URLNotFoundException();
      }
      bean.setPlanCode(planCode);
      bean.setDetailType(NumUtil.toString(planDetail.getDetailType()));
      bean.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      bean.setIsUpdateFlg(true);
      bean.setUpdateDatetime(planDetail.getUpdatedDatetime());

      CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
      // 20130828 txw update start
      Plan plan = communicationService.getPlan(planCode);
      // 20130828 txw update end
      // 品牌
      if (PlanDetailType.BRAND.longValue().equals(planDetail.getDetailType())) {
        bean.setBrandCode(planDetail.getDetailCode());
        Brand brand = cs.getBrand(getLoginInfo().getShopCode(), planDetail.getDetailCode());
        bean.setBrandName(brand.getBrandName());
        setBrandList(bean);
        // 20130828 txw update start
        if (PlanType.FEATURE.longValue().equals(plan.getPlanType())) {
          sortNumMode = WebConstantCode.DISPLAY_EDIT;
        }
        // 20130828 txw update end
        // 商品分类
      } else if (PlanDetailType.CATEGORY.longValue().equals(planDetail.getDetailType())) {
        bean.setCategoryCode(planDetail.getDetailCode());
        bean.setCategoryName(cs.getCategory(planDetail.getDetailCode()).getCategoryNamePc());
        bean.setCategoryPath(cs.getCategory(planDetail.getDetailCode()).getPath());
        setCategoryList(bean);
        // 20130812 txw update start
        if (PlanType.FEATURE.longValue().equals(plan.getPlanType())) {
          sortNumMode = WebConstantCode.DISPLAY_EDIT;
        }
        // 20130812 txw update end
        // 自由组合
      } else if (PlanDetailType.FREE.longValue().equals(planDetail.getDetailType())) {
        bean.setCode(planDetail.getDetailCode());
        bean.setName(planDetail.getDetailName());
        bean.setNameEn(planDetail.getDetailNameEn());
        bean.setNameJp(planDetail.getDetailNameJp());
        bean.setUrl(planDetail.getDetailUrl());
        bean.setUrlEn(planDetail.getDetailUrlEn());
        bean.setUrlJp(planDetail.getDetailUrlJp());
        // 20130828 txw update start
        if (PlanType.FEATURE.longValue().equals(plan.getPlanType())) {
          sortNumMode = WebConstantCode.DISPLAY_EDIT;
        }
        // 20130828 txw update end
      }
      bean.setShowCount(NumUtil.toString(planDetail.getShowCommodityCount()));
      bean.setDisplayOrder(NumUtil.toString(planDetail.getDisplayOrder()));
      List<PlanRelatedHeadLine> list = communicationService.getRelatedHeadLine(planCode, detailType, detailCode);
      List<RelatedCommodityBean> commodityBeanList = new ArrayList<RelatedCommodityBean>();
      for (int i = 0; i < list.size(); i++) {
        RelatedCommodityBean commodityBean = new RelatedCommodityBean();
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
      bean.setList(commodityBeanList);
    }

    if (Permission.PLAN_UPDATE_SHOP.isDenied(getLoginInfo())) {
      bean.setMode(WebConstantCode.DISPLAY_READONLY);
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
      bean.setUpdateAuthorizeFlg(false);
      // 20130812 txw update start
      sortNumMode = WebConstantCode.DISPLAY_READONLY;
      // 20130812 txw update end
    } else {
      bean.setMode(WebConstantCode.DISPLAY_EDIT);
      bean.setUpdateAuthorizeFlg(true);
    }

    // 20130812 txw update start
    bean.setSortNumMode(sortNumMode);
    // 20130812 txw update end

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private List<Brand> getBrandList() {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    return service.getAllBrand();
  }

  private void setCategoryList(PlanRelatedBean bean) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    bean.setCategoryList(service.getAllCategory());
  }

  private void setBrandList(PlanRelatedBean bean) {
    List<CodeAttribute> brandList = new ArrayList<CodeAttribute>();
    List<Brand> brandListAll = getBrandList();
    for (int i = 0; i < brandListAll.size(); i++) {
      Brand brand = brandListAll.get(i);
      brandList.add(new NameValue(brand.getBrandName(), brand.getBrandCode()));
    }
    bean.setBrandList(brandList);
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
      return "促销企划明细关联初期表示处理";
    } else if (PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划明细关联初期表示处理";
    }
    return "";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106082001";
    } else if (PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106092001";
    }
    return "";
  }
}
