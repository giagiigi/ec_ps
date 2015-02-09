package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.data.domain.CodType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.DeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.TmallDeliveryRegion;
import jp.co.sint.webshop.data.dto.TmallDeliveryRelatedInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyDateBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyDateBean.DeliveryRelatedInfoBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;

/**
 * 配送希望日时登录
 * 
 * @author
 */
public class DeliveryCompanyDateAddAction extends DeliveryCompanyDateBaseAction {

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
    String[] params = getRequestParameter().getPathArgs();
    if (!(params.length > 0 && params[params.length-1].equals("tmall"))){
      if (AppointedTimeType.DO.longValue().equals(getBean().getRelatedInfoBean().getDeliveryAppointedTimeType())) {
        // 未指定错误
        if (getBean().getRelatedInfoBean().getDeliveryAppointedStartTime() == null
            && getBean().getRelatedInfoBean().getDeliveryAppointedEndTime() == null) {
          addErrorMessage(WebMessage.get(ShopErrorMessage.APPOINTED_TIME_ERROR));
          return false;
          // 大小关系错误
        } else if (getBean().getRelatedInfoBean().getDeliveryAppointedStartTime() != null
            && getBean().getRelatedInfoBean().getDeliveryAppointedEndTime() != null) {
          if (getBean().getRelatedInfoBean().getDeliveryAppointedStartTime() > getBean().getRelatedInfoBean()
              .getDeliveryAppointedEndTime()) {
            addErrorMessage(WebMessage.get(ShopErrorMessage.APPOINTED_TIME_START_END_ERROR));
            return false;
          }
        }
      }
    }
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
    String[] params = getRequestParameter().getPathArgs();
    DeliveryCompanyDateBean bean = (DeliveryCompanyDateBean) getBean();
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    if (params.length > 0 && params[params.length-1].equals("tmall")){
      TmallDeliveryRegion deliveryRegion = service.getDeliveryRegionTmall(getLoginInfo().getShopCode(), bean.getDeliveryCompanyNo(), bean
          .getPrefectureCode());
      // 配送关联地域不存在时URL错误
      if (deliveryRegion == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司和配送地域的关联信息"));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
      
      List<TmallDeliveryRelatedInfo> results = service.getDeliveryRelatedInfoListTmall(getLoginInfo().getShopCode(), bean
          .getDeliveryCompanyNo(), bean.getPrefectureCode());
      // COD区分 ALL 和（COD或者COD以外）不能并存
      if (results != null && results.size() > 0) {
        for (TmallDeliveryRelatedInfo info : results) {
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
      TmallDeliveryRelatedInfo info = new TmallDeliveryRelatedInfo();
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
      ServiceResult result = service.insertDeliveryRelatedInfoTmall(info);
      if (result.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "配送希望日时的关联信息"));
      results = service.getDeliveryRelatedInfoListTmall(getLoginInfo().getShopCode(), bean.getDeliveryCompanyNo(), bean
          .getPrefectureCode());
      // 设定配送希望日时一览画面
      setDeliveryRegionDateTmall(results, bean);
      bean.setRelatedInfoBean(new DeliveryRelatedInfoBean());
      
    } else {
      DeliveryRegion deliveryRegion = service.getDeliveryRegion(getLoginInfo().getShopCode(), bean.getDeliveryCompanyNo(), bean
          .getPrefectureCode());
      // 配送关联地域不存在时URL错误
      if (deliveryRegion == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司和配送地域的关联信息"));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
      
      List<DeliveryRelatedInfo> results = service.getDeliveryRelatedInfoList(getLoginInfo().getShopCode(), bean
          .getDeliveryCompanyNo(), bean.getPrefectureCode());
      // COD区分 ALL 和（COD或者COD以外）不能并存
      if (results != null && results.size() > 0) {
        for (DeliveryRelatedInfo info : results) {
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
      DeliveryRelatedInfo info = new DeliveryRelatedInfo();
      info.setShopCode(getLoginInfo().getShopCode());
      info.setDeliveryCompanyNo(bean.getDeliveryCompanyNo());
      info.setPrefectureCode(bean.getPrefectureCode());
      info.setDeliveryDateType(bean.getRelatedInfoBean().getDeliveryDateType());
      info.setCodType(bean.getRelatedInfoBean().getCodType());
      info.setDeliveryAppointedTimeType(bean.getRelatedInfoBean().getDeliveryAppointedTimeType());
      if(!StringUtil.isNullOrEmpty(bean.getRelatedInfoBean().getMinWeight())){
        info.setMinWeight(NumUtil.toLong(bean.getRelatedInfoBean().getMinWeight()));
      }
      if(!StringUtil.isNullOrEmpty(bean.getRelatedInfoBean().getMaxWeight())){
        info.setMaxWeight(NumUtil.toLong(bean.getRelatedInfoBean().getMaxWeight()));
      }
   
      if (AppointedTimeType.DO.longValue().equals(bean.getRelatedInfoBean().getDeliveryAppointedTimeType())) {
        if (bean.getRelatedInfoBean().getDeliveryAppointedStartTime() != null) {
          info.setDeliveryAppointedStartTime(bean.getRelatedInfoBean().getDeliveryAppointedStartTime());
        }
        if (bean.getRelatedInfoBean().getDeliveryAppointedEndTime() != null) {
          info.setDeliveryAppointedEndTime(bean.getRelatedInfoBean().getDeliveryAppointedEndTime());
        }
      }
      ServiceResult result = service.insertDeliveryRelatedInfo(info);
      if (result.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "配送希望日时的关联信息"));
      results = service.getDeliveryRelatedInfoList(getLoginInfo().getShopCode(), bean.getDeliveryCompanyNo(), bean
          .getPrefectureCode());
      // 设定配送希望日时一览画面
      setDeliveryRegionDate(results, bean);
      bean.setRelatedInfoBean(new DeliveryRelatedInfoBean());
      
    }


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
