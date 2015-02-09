package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.domain.CodType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.JdDeliveryRegion;
import jp.co.sint.webshop.data.dto.JdDeliveryRelatedInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyDateBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyDateBean.DeliveryRelatedInfoBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;

import org.apache.log4j.Logger;

/**
 * 配送希望日时登录
 * 
 * @author
 */
public class DeliveryJdCompanyDateAddAction extends DeliveryJdCompanyDateBaseAction {

  /**
   * @return
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
    }
    return authorization;
  }

  /**
   * @return
   */
  @Override
  public boolean validate() {
    boolean result = validateBean(getBean().getRelatedInfoBean());
    if (getBean().getRelatedInfoBean().getMinWeight() != null && getBean().getRelatedInfoBean().getMaxWeight() != null) {
      if (NumUtil.toLong(getBean().getRelatedInfoBean().getMinWeight()) > NumUtil.toLong(getBean().getRelatedInfoBean()
          .getMaxWeight())) {
        addErrorMessage(WebMessage.get(ShopErrorMessage.WEIGHT_START_END_ERROR));
        return false;
      }
    }
    return result;
  }

  /**
   * 查询所以配送时间间隔和配送希望日
   * 
   * @return WebActionResult
   */
  public WebActionResult callService() {
    DeliveryJdCompanyDateBean bean = (DeliveryJdCompanyDateBean) getBean();
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    JdDeliveryRegion deliveryRegion = service.getDeliveryRegionJd(getLoginInfo().getShopCode(), bean.getDeliveryCompanyNo(), bean
        .getPrefectureCode());
    // 配送关联地域不存在时URL错误
    if (deliveryRegion == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司和配送地域的关联信息"));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }
    
    List<JdDeliveryRelatedInfo> results = service.getDeliveryRelatedInfoListJd(getLoginInfo().getShopCode(), bean
        .getDeliveryCompanyNo(), bean.getPrefectureCode());
    // COD区分 ALL 和（COD或者COD以外）不能并存
    if (results != null && results.size() > 0) {
      for (JdDeliveryRelatedInfo info : results) {
        if (CodType.ALL.longValue().equals(bean.getRelatedInfoBean().getCodType())
            && !CodType.ALL.longValue().equals(info.getCodType())) {
          addErrorMessage(WebMessage.get(ShopErrorMessage.COD_TYPE_ALL_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
        if (!CodType.ALL.longValue().equals(bean.getRelatedInfoBean().getCodType())
            && CodType.ALL.longValue().equals(info.getCodType())) {
          addErrorMessage(WebMessage.get(ShopErrorMessage.COD_TYPE_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
    }
    JdDeliveryRelatedInfo info = new JdDeliveryRelatedInfo();
    info.setShopCode(getLoginInfo().getShopCode());
    info.setDeliveryCompanyNo(bean.getDeliveryCompanyNo());
    info.setPrefectureCode(bean.getPrefectureCode());
    info.setDeliveryDateType(0L);
    info.setCodType(bean.getRelatedInfoBean().getCodType());
    info.setDeliveryAppointedTimeType(0L);
    if(!StringUtil.isNullOrEmpty(bean.getRelatedInfoBean().getMinWeight())){
      info.setMinWeight(NumUtil.toLong(bean.getRelatedInfoBean().getMinWeight()));
    }
    if(!StringUtil.isNullOrEmpty(bean.getRelatedInfoBean().getMaxWeight())){
      info.setMaxWeight(NumUtil.toLong(bean.getRelatedInfoBean().getMaxWeight()));
    }
    ServiceResult result = service.insertDeliveryRelatedInfoJd(info);
    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    }
    addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "配送希望日时的关联信息"));
    results = service.getDeliveryRelatedInfoListJd(getLoginInfo().getShopCode(), bean.getDeliveryCompanyNo(), bean
        .getPrefectureCode());
    // 设定配送希望日时一览画面
    setDeliveryRegionDateJd(results, bean);
    bean.setRelatedInfoBean(new DeliveryRelatedInfoBean());
      
    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "配送希望日时关联情报追加处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105161002";
  }

}
