package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.domain.LanguageType;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.PropagandaActivityCommodityInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean.CommodityDetailBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge;

public abstract class PropagandaActivityRuleEditBaseAction extends WebBackAction<PropagandaActivityRuleEditBean> {

  public String REGISTER = "register";

  public String UPDATE = "update";

  public String REGISTER_COMMODITY = "registerCommodity";

  public String DELETE_COMMODITY = "deleteCommodity";

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
   * 根据活动编号验证活动是否存在
   * 
   * @param activityCode
   *          :活动编号
   * @return true:存在; false:不存在;
   */
  public boolean existActivity(String activityCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    PropagandaActivityRule propagandaActivityRule = communicationService.getPropagandaActivityRule(activityCode);

    if (propagandaActivityRule == null) {
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
  public void setBeanToDto(PropagandaActivityRuleEditBean bean, PropagandaActivityRule dto) {
    dto.setActivityCode(bean.getActivityCode());
    dto.setActivityName(bean.getActivityName());
    dto.setOrderType(NumUtil.toLong(bean.getActivityType()));
    if(bean.getActivityType().equals(OrderType.EC.getValue())) {
      dto.setLanguageCode(NumUtil.toLong(bean.getLanguageType()));
    } else {
      dto.setLanguageCode(LanguageType.Zh_Cn.longValue());
    }
    dto.setActivityStartDatetime(DateUtil.fromString(bean.getActivityStartDate(), true));
    dto.setActivityEndDatetime(DateUtil.fromString(bean.getActivityEndDate(), true));
    dto.setActivityEndDatetime(DateUtil.setSecond(dto.getActivityEndDatetime(), 59));
    dto.setDeliveryArea(getApplicableArea(bean));
  }

  /**
   * 设置DTO内容到bean
   * 
   * @param dto
   * @param bean
   */
  public void setDtoToBean(PropagandaActivityRule dto, PropagandaActivityRuleEditBean bean) {
    bean.setActivityCode(dto.getActivityCode());
    bean.setActivityName(dto.getActivityName());
    bean.setActivityType(NumUtil.toString(dto.getOrderType()));
    bean.setLanguageType(NumUtil.toString(dto.getLanguageCode()));
    bean.setActivityStartDate(DateUtil.toDateTimeString(dto.getActivityStartDatetime()));
    bean.setActivityEndDate(DateUtil.toDateTimeString(dto.getActivityEndDatetime()));
    bean.setRegionBlockChargeList(getRegionBlockChargeOrgList());

    if (StringUtil.hasValue(dto.getDeliveryArea())) {
      bean.setCheckedAreaDispalyNames(getApplicableAreaNames(dto.getDeliveryArea()));

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
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    List<PropagandaActivityCommodityInfo> commodityList = communicationService.getPropagandaActivityCommodityList(dto.getActivityCode());
    List<CommodityDetailBean> commodityDetailBeanList = new ArrayList<CommodityDetailBean>();
    for(PropagandaActivityCommodityInfo paci: commodityList) {
      CommodityDetailBean detail = new CommodityDetailBean();
      detail.setCommodityCode(paci.getCommodityCode());
      detail.setCommodityName(paci.getCommodityName());
      detail.setCommodityAmount(NumUtil.toString(paci.getPurchasingAmount()));
      commodityDetailBeanList.add(detail);
    }
    bean.setCommodityDetailBeanList(commodityDetailBeanList);
  }

  public String getApplicableArea(PropagandaActivityRuleEditBean bean) {
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
   * 同类型同语言同期间只能登录一个活动
   */
  public boolean existCommon(PropagandaActivityRuleEditBean bean) {
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    Date startTime = DateUtil.fromString(bean.getActivityStartDate(), true);
    Date endTime = DateUtil.fromString(bean.getActivityEndDate(), true);
    endTime = DateUtil.setSecond(endTime, 59);
    if(bean.getActivityType().equals(OrderType.EC.getValue())) {
      return communicationService.existCommonActivityDate(OrderType.EC.longValue(), NumUtil.toLong(bean.getLanguageType()), startTime, endTime, bean.getActivityCode());
    } else {
      return communicationService.existCommonActivityDate(OrderType.TMALL.longValue(), LanguageType.Zh_Cn.longValue(), startTime, endTime, bean.getActivityCode());
    }
  }

}
