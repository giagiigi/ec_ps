package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean.BrandDetailBean;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean.CategoryDetailBean;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean.CommodityDetailBean;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean.IssueDetailBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge;

public abstract class FreePostageEditBaseAction extends WebBackAction<FreePostageEditBean> {

  public String REGISTER = "register";

  public String UPDATE = "update";

  public String REGISTER_COMMODITY = "registerCommodity";

  public String REGISTER_BRAND = "registerBrand";

  public String REGISTER_CATEGORY = "registerCategory";

  public String REGISTER_ISSUE = "registerIssue";

  public String DELETE_COMMODITY = "deleteCommodity";

  public String DELETE_BRAND = "deleteBrand";

  public String DELETE_CATEGORY = "deleteCategory";

  public String DELETE_ISSUE = "deleteIssue";

  public abstract WebActionResult callService();

  public boolean validate() {
    return true;
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
   * 根据免邮促销编号验证免邮促销是否存在
   * 
   * @param freePostageCode
   *          :免邮促销编号
   * @return true:存在; false:不存在;
   */
  public boolean existFreePostage(String freePostageCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    FreePostageRule freePostageRule = communicationService.getFreePostageRule(freePostageCode);

    if (freePostageRule == null) {
      return false;
    }
    return true;

  }

  /**
   * 设置bean内容到DTO
   * 
   * @param bean
   * @param dto
   */
  public void setBeanToDto(FreePostageEditBean bean, FreePostageRule dto) {
    dto.setFreePostageCode(bean.getFreePostageCode());
    dto.setFreePostageName(bean.getFreePostageName());
    dto.setFreeStartDate(DateUtil.fromString(bean.getFreeStartDate(), true));
    dto.setFreeEndDate(DateUtil.fromString(bean.getFreeEndDate(), true));
    dto.setFreeEndDate(DateUtil.setSecond(dto.getFreeEndDate(), 59));
    if (StringUtil.hasValue(bean.getUseType())) {
      dto.setUseType(Long.parseLong(bean.getUseType()));
    } else {
      dto.setUseType(0L);
    }
    dto.setApplicableObject(Long.valueOf(bean.getApplicableObject()));
    if (StringUtil.hasValue(bean.getWeightLimit())) {
      dto.setWeightLimit(BigDecimal.valueOf(Double.valueOf(bean.getWeightLimit())));
    } else {
      dto.setWeightLimit(null);
    }
    if (StringUtil.hasValue(bean.getMinUseOrderAmount())) {
      dto.setMinUseOrderAmount(BigDecimal.valueOf(Double.valueOf(bean.getMinUseOrderAmount())));
    } else {
      dto.setMinUseOrderAmount(null);
    }
    dto.setApplicableArea(getApplicableArea(bean));
  }

  /**
   * 设置DTO内容到bean
   * 
   * @param dto
   * @param bean
   */
  public void setDtoToBean(FreePostageRule dto, FreePostageEditBean bean) {
    bean.setFreePostageCode(dto.getFreePostageCode());
    bean.setFreePostageName(dto.getFreePostageName());
    bean.setFreeStartDate(DateUtil.toDateTimeString(dto.getFreeStartDate()));
    bean.setFreeEndDate(DateUtil.toDateTimeString(dto.getFreeEndDate()));
    if (dto.getUseType() != null) {
      bean.setUseType(NumUtil.toString(dto.getUseType()));
    }
    bean.setApplicableObject(NumUtil.toString(dto.getApplicableObject()));
    if (dto.getWeightLimit() != null) {
      bean.setWeightLimit(NumUtil.toString(dto.getWeightLimit()));
    }
    if (dto.getMinUseOrderAmount() != null) {
      bean.setMinUseOrderAmount(NumUtil.toString(dto.getMinUseOrderAmount()));
    }
    bean.setRegionBlockChargeList(getRegionBlockChargeOrgList());

    if (StringUtil.hasValue(dto.getApplicableArea())) {
      bean.setCheckedAreaDispalyNames(getApplicableAreaNames(dto.getApplicableArea()));

      // 将之前选中的地域选项打上勾
      List<RegionBlockCharge> rbcList = new ArrayList<RegionBlockCharge>();
      for (RegionBlockCharge regionBlock : bean.getRegionBlockChargeList()) {
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
    // 商品列表
    bean.setCommodityDetailBeanList(getAllRelatedCommodity(dto));

    // 品牌列表
    bean.setBrandDetailBeanList(getAllRelatedBrand(dto));

    // 分类列表
    bean.setCategoryDetailBeanList(getAllRelatedCategory(dto));

    // 发行代码列表
    bean.setIssueDetailBeanList(getAllRelatedIssue(dto));
  }

  // 查询关联商品信息
  private List<CommodityDetailBean> getAllRelatedCommodity(FreePostageRule dto) {
    List<CommodityDetailBean> commodityDetailBeanList = new ArrayList<CommodityDetailBean>();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    if (StringUtil.hasValue(dto.getObjectCommodity())) {
      String[] objects = dto.getObjectCommodity().split(";");

      for (int i = 0; i < objects.length; i++) {
        CommodityHeader ommodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), objects[i], true, false);
        CommodityDetailBean commodityBean = new CommodityDetailBean();
        if (ommodityHeader != null) {
          commodityBean.setCommodityCode(ommodityHeader.getCommodityCode());
          commodityBean.setCommodityName(ommodityHeader.getCommodityName());
          commodityDetailBeanList.add(commodityBean);
        }
      }
    }
    return commodityDetailBeanList;
  }

  // 查询关联品牌信息
  private List<BrandDetailBean> getAllRelatedBrand(FreePostageRule dto) {
    List<BrandDetailBean> brandDetailBeanList = new ArrayList<BrandDetailBean>();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    if (StringUtil.hasValue(dto.getObjectBrand())) {
      String[] objects = dto.getObjectBrand().split(";");

      for (int i = 0; i < objects.length; i++) {
        Brand brand = catalogService.getBrand(objects[i]);
        BrandDetailBean brandBean = new BrandDetailBean();
        if (brand != null) {
          brandBean.setBrandCode(brand.getBrandCode());
          brandBean.setBrandName(brand.getBrandName());
          brandDetailBeanList.add(brandBean);
        }
      }
    }
    return brandDetailBeanList;
  }

  // 查询关联分类信息
  private List<CategoryDetailBean> getAllRelatedCategory(FreePostageRule dto) {
    List<CategoryDetailBean> categoryDetailBeanList = new ArrayList<CategoryDetailBean>();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    if (StringUtil.hasValue(dto.getObjectCategory())) {
      String[] objects = dto.getObjectCategory().split(";");

      for (int i = 0; i < objects.length; i++) {
        Category category = catalogService.getCategory(objects[i]);
        CategoryDetailBean categoryBean = new CategoryDetailBean();
        if (category != null) {
          categoryBean.setCategoryCode(category.getCategoryCode());
          categoryBean.setCategoryName(category.getCategoryNamePc());
          categoryDetailBeanList.add(categoryBean);
        }
      }
    }
    return categoryDetailBeanList;
  }

  // 查询关联发布代码信息
  private List<IssueDetailBean> getAllRelatedIssue(FreePostageRule dto) {
    List<IssueDetailBean> issueDetailBeanList = new ArrayList<IssueDetailBean>();

    if (StringUtil.hasValue(dto.getObjectIssueCode())) {
      String[] objects = dto.getObjectIssueCode().split(";");
      String[] urls = dto.getTargetUrl().split(";");

      for (int i = 0; i < objects.length; i++) {
        IssueDetailBean categoryBean = new IssueDetailBean();
        categoryBean.setIssueCode(objects[i]);
        String trackUrl = getConfig().getTrackUrl();
        trackUrl += "?cid=" + objects[i];
        if (urls[i].length() > 1) {
          trackUrl += "&url=" + urls[i].substring(1);
        }
        categoryBean.setTrackUrl(trackUrl);
        issueDetailBeanList.add(categoryBean);
      }
    }
    return issueDetailBeanList;
  }

  public String getApplicableArea(FreePostageEditBean bean) {
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

}
