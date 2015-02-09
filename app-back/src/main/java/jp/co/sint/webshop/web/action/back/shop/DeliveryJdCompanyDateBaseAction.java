package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.JdDeliveryRelatedInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyDateBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyDateBean.DeliveryRelatedInfoBean;

/**
 * @author System Integrator Corp.
 */
public class DeliveryJdCompanyDateBaseAction extends WebBackAction<DeliveryJdCompanyDateBean> {

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

  
  public void setDeliveryRegionDateJd(List<JdDeliveryRelatedInfo> list, DeliveryJdCompanyDateBean bean) {
    bean.setRelatedInfoList(new ArrayList<DeliveryRelatedInfoBean>());
    for (JdDeliveryRelatedInfo info : list) {
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
  public void setDeliveryRelatedInfo(JdDeliveryRelatedInfo info, DeliveryRelatedInfoBean bean) {
    info.setShopCode(getLoginInfo().getShopCode());
    info.setDeliveryDateType(bean.getDeliveryDateType());
    info.setCodType(bean.getCodType());
  }
}
