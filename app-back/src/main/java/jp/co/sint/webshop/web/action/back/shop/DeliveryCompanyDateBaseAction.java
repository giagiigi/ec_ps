package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.data.dto.DeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.TmallDeliveryRelatedInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyDateBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyDateBean.DeliveryRelatedInfoBean;

/**
 * @author System Integrator Corp.
 */
public class DeliveryCompanyDateBaseAction extends WebBackAction<DeliveryCompanyDateBean> {

  /**
   * @return
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().isAdmin();
  }

  /**
   * @return
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * @return
   */
  public WebActionResult callService() {
    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 设定配送希望日时一览画面
   * 
   * @param list
   *          List<DeliveryRegionDateInfo>
   * @param bean
   *          DeliveryCompanyDateBean
   */
  public void setDeliveryRegionDate(List<DeliveryRelatedInfo> list, DeliveryCompanyDateBean bean) {
    bean.setRelatedInfoList(new ArrayList<DeliveryRelatedInfoBean>());
    for (DeliveryRelatedInfo info : list) {
      DeliveryRelatedInfoBean infoBean = new DeliveryRelatedInfoBean();
      infoBean.setDeliveryRelatedInfoNo(info.getDeliveryRelatedInfoNo());
      infoBean.setCodType(info.getCodType());
      infoBean.setDeliveryAppointedEndTime(info.getDeliveryAppointedEndTime());
      infoBean.setDeliveryAppointedStartTime(info.getDeliveryAppointedStartTime());
      infoBean.setDeliveryAppointedTimeType(info.getDeliveryAppointedTimeType());
      infoBean.setDeliveryDateType(info.getDeliveryDateType());
      if (info.getMinWeight() != null) {
        infoBean.setMinWeight(info.getMinWeight().toString());
      }
      if (info.getMaxWeight() != null) {
        infoBean.setMaxWeight(info.getMaxWeight().toString());
      }

      if (AppointedTimeType.NOT.longValue().equals(info.getDeliveryAppointedTimeType())) {
        infoBean.setDeliveryAppointedTimeName("不可指定时间");
      } else {
        if (info.getDeliveryAppointedStartTime() != null) {
          if (info.getDeliveryAppointedEndTime() != null) {
            infoBean.setDeliveryAppointedTimeName(info.getDeliveryAppointedStartTime().toString() + "点-"
                + info.getDeliveryAppointedEndTime().toString() + "点");
          } else {
            infoBean.setDeliveryAppointedTimeName(info.getDeliveryAppointedStartTime().toString() + "点以后");
          }
        } else {
          infoBean.setDeliveryAppointedTimeName(info.getDeliveryAppointedEndTime().toString() + "点以前");
        }
      }
      bean.getRelatedInfoList().add(infoBean);
    }

  }
  
  
  
  public void setDeliveryRegionDateTmall(List<TmallDeliveryRelatedInfo> list, DeliveryCompanyDateBean bean) {
    bean.setRelatedInfoList(new ArrayList<DeliveryRelatedInfoBean>());
    for (TmallDeliveryRelatedInfo info : list) {
      DeliveryRelatedInfoBean infoBean = new DeliveryRelatedInfoBean();
      infoBean.setDeliveryRelatedInfoNo(info.getDeliveryRelatedInfoNo());
      infoBean.setCodType(info.getCodType());
      if (info.getMinWeight() != null) {
        infoBean.setMinWeight(info.getMinWeight().toString());
      }
      if (info.getMaxWeight() != null) {
        infoBean.setMaxWeight(info.getMaxWeight().toString());
      }
      bean.getRelatedInfoList().add(infoBean);
    }

  }

  /**
   * 填充到DeliveryRelatedInfo DTO中
   * 
   * @param info
   *          DeliveryRelatedInfo
   * @param bean
   *          DeliveryRelatedInfoBean
   */
  public void setDeliveryRelatedInfo(DeliveryRelatedInfo info, DeliveryRelatedInfoBean bean) {
    info.setShopCode(getLoginInfo().getShopCode());
    info.setDeliveryDateType(bean.getDeliveryDateType());
    info.setCodType(bean.getCodType());
  }
}
