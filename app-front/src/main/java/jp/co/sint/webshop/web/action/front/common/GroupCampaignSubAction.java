package jp.co.sint.webshop.web.action.front.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
 
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignHeadLine; 
import jp.co.sint.webshop.utility.StringUtil; 
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.common.GroupCampaignBean;
import jp.co.sint.webshop.web.bean.front.common.GroupCampaignBean.CampaignDetailBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * インフォメーションのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GroupCampaignSubAction extends WebSubAction {
  private String campaignName="";
  
  //20120522 tuxinwei add start 
  String customerGroupName = "";
  //20120522 tuxinwei add end
  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前GroupCampaignSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前GroupCampaignSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    GroupCampaignBean bean = (GroupCampaignBean) getBean();
    List<CampaignDetailBean> campaignList = new ArrayList<CampaignDetailBean>();  
    CommunicationService siteService = ServiceLocator.getCommunicationService(getLoginInfo());
    List<CustomerGroupCampaignHeadLine> customerGroupCampaignList = siteService.getCustomerGroupCampaignList();
    for (CustomerGroupCampaignHeadLine headLine : customerGroupCampaignList) {
      CampaignDetailBean detailBean = new CampaignDetailBean();
      //add by cs_yuli 20120514 start 
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());
	   campaignName = utilService.getNameByLanguage(headLine.getCampaignName(),headLine.getCampaignNameEn(),headLine.getCampaignNameJp()); 
      //add by cs_yuli 20120514 end
      //20120522 tuxinwei add start  
       customerGroupName = utilService.getNameByLanguage(headLine.getCustomerGroupName(),headLine.getCustomerGroupNameEn(),headLine.getCustomerGroupNameJp()); 
      //20120522 tuxinwei add end
      if (StringUtil.hasValue(headLine.getCustomerGroupCode())) { 
        detailBean.setCampaignName(campaignName); 
        detailBean.setCustomerGroupName(customerGroupName);
        detailBean.setCampaignStartDatetime(headLine.getCampaignStartDatetime());
        detailBean.setCampaignEndDatetime(headLine.getCampaignEndDatetime());
        detailBean.setCampaignType(headLine.getCampaignType());
        detailBean.setCampaignProportion(headLine.getCampaignProportion());
        detailBean.setCampaignAmount(headLine.getCampaignAmount());
        detailBean.setMinOrderAmount(headLine.getMinOrderAmount());
        campaignList.add(detailBean);
      } else { 
        bean.setCampaignName(campaignName); 
        bean.setCustomerGroupName(customerGroupName);
        bean.setCampaignStartDatetime(headLine.getCampaignStartDatetime());
        bean.setCampaignEndDatetime(headLine.getCampaignEndDatetime());
        bean.setCampaignType(headLine.getCampaignType());
        bean.setCampaignProportion(headLine.getCampaignProportion());
        bean.setCampaignAmount(headLine.getCampaignAmount());
        bean.setMinOrderAmount(headLine.getMinOrderAmount());
      }
    }
    bean.setCampaignList(campaignList);
    setBean(bean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前GroupCampaignSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前GroupCampaignSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
  }

  /**
   * ログイン情報を取得します
   * 
   * @return frontLoginInfo
   */
  public FrontLoginInfo getLoginInfo() {
    LoginInfo loginInfo = getSessionContainer().getLoginInfo();
    FrontLoginInfo frontLoginInfo = null;

    if (loginInfo == null) {
      frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
    } else {
      if (loginInfo instanceof FrontLoginInfo) {
        frontLoginInfo = (FrontLoginInfo) loginInfo;
      } else {
        frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
      }
    }
    return frontLoginInfo;
  }

}
