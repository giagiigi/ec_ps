package jp.co.sint.webshop.web.action.front.catalog;

import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.service.CommunicationService; 
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo; 
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.CampaignInfoBean;
import jp.co.sint.webshop.web.bean.front.catalog.CampaignInfoBean.CampaignInfoDetailBean; 
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * キャンペーン情報のサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignInfoSubAction extends WebSubAction {
  
  private static final int MAX_DISPLAY_SIZE = 100;

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前CampaignInfoSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前CampaignInfoSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    CampaignInfoBean reqBean = (CampaignInfoBean) getBean();
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    List<CampaignInfoDetailBean> list = reqBean.getList();
    List<CodeAttribute> codeList = reqBean.getCodeList();
    list.clear();
    codeList.clear();
    codeList.add(new NameValue(Messages.getString(
    "web.action.front.catalog.CampaignInfoSubAction.0"), ""));
    
    CampaignListSearchCondition condition = new CampaignListSearchCondition();
    condition.setCampaignStartDateTo(DateUtil.getSysdateString());
    condition.setCampaignEndDateFrom(DateUtil.getSysdateString());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    condition.setPageSize(MAX_DISPLAY_SIZE);

    CommunicationService communicationService = ServiceLocator.getCommunicationService(ServiceLoginInfo.getInstance());
    SearchResult<CampaignHeadLine> result = communicationService.getCampaignList(condition);

    for (CampaignHeadLine campaign : result.getRows()) {
      CampaignInfoDetailBean bean = new CampaignInfoDetailBean();
      bean.setCampaignCode(campaign.getCampaignCode());
      if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
    	  bean.setCampaignName(campaign.getCampaignName());
        } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
        	bean.setCampaignName(campaign.getCampaignNameJp());
        } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
        	bean.setCampaignName(campaign.getCampaignNameEn());
        } 
      bean.setShopName(campaign.getShopName());
      bean.setShopCode(campaign.getShopCode());
      bean.setCampaignStartDate(campaign.getCampaignStartDate());
      bean.setCampaignEndDate(campaign.getCampaignEndDate());
      bean.setCampaignDiscountRate(campaign.getCampaignDiscountRate());
      
      bean.setCampaignExists(campaign.isCampaignExists());

      list.add(bean);
      
      codeList.add(new NameValue(StringUtil.getHeadline(bean.getCampaignName(), 10), campaign.getCampaignCode()));
    }
    setBean(reqBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前CampaignInfoSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前CampaignInfoSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
  } 
}
