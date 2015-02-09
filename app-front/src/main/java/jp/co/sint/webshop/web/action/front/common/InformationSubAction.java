package jp.co.sint.webshop.web.action.front.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.InformationType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.common.InformationBean;
import jp.co.sint.webshop.web.bean.front.common.InformationBean.InformationDetailBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * インフォメーションのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前InformationSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前InformationSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    
    InformationBean bean = (InformationBean) getBean();
    List<InformationDetailBean> informationList = new ArrayList<InformationDetailBean>();
    //20120111 os013 add start
    //重要通知
    List<InformationDetailBean> importantInformationList = new ArrayList<InformationDetailBean>();
    //20120111 os013 add end
    List<InformationDetailBean> systemInformation = new ArrayList<InformationDetailBean>();
    List<InformationDetailBean> businessInformation = new ArrayList<InformationDetailBean>();

    SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());

    //20120516 tuxinwei add start
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    //20120516 tuxinwei add end
    for (Information info : siteService.getInformationList()) {
      Date startDatetime = info.getInformationStartDatetime();
      Date endDatetime = info.getInformationEndDatetime();
      DisplayClientType displayClientType = DisplayClientType.fromValue(info.getDisplayClientType());
      if (DateUtil.isPeriodDate(startDatetime, endDatetime)
          && (DisplayClientType.ALL == displayClientType || DisplayClientType.PC == displayClientType)) {

        InformationDetailBean item = new InformationDetailBean();
        item.setInformationNo(String.valueOf(info.getInformationNo()));
        item.setInformationStartDatetime(info.getInformationStartDatetime());
        //20120516 tuxinwei update start
        if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
          item.setInformationContent(info.getInformationContent());
          item.setInformationUrl(info.getInformationUrl());
        } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
          item.setInformationContent(info.getInformationContentJp());
          item.setInformationUrl(info.getInformationUrlJp());
        } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
          item.setInformationContent(info.getInformationContentEn());
          item.setInformationUrl(info.getInformationUrlEn());
        }
        //20120516 tuxinwei update end
        
        if (info.getInformationType().equals(InformationType.SYSTEM.longValue())) {
          item.setInformationType("system");
          systemInformation.add(item);
        } else {
          item.setInformationType("business");
          businessInformation.add(item);
        }
        //20120111 os013 add start
        //判断是否为重要通知
        if(info.getPrimaryFlg()==1L){
          importantInformationList.add(item);
        }else{
        //20120111 os013 add end  
          informationList.add(item);
        }
        
        
      }
    }

    bean.setInformationList(informationList);
    //20120111 os013 add start
    bean.setImportantInformationList(importantInformationList);
    //20120111 os013 add end
    bean.setSystemInformation(systemInformation);
    bean.setBusinessInformation(businessInformation);

    setBean(bean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前InformationSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前InformationSubAction:session缺失，结束记录--------------------------------------------------------------------");
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
