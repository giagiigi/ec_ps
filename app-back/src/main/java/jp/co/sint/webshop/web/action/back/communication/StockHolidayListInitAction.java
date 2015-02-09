package jp.co.sint.webshop.web.action.back.communication;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.StockHolidayListBean;
import jp.co.sint.webshop.web.bean.back.communication.StockHolidayListBean.StockHolidayDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;


/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author OB.
 */
public class StockHolidayListInitAction extends WebBackAction<StockHolidayListBean> {

  public boolean authorize() {

    BackLoginInfo login = getLoginInfo();
    return Permission.OPTIONAL_CAMPAGIN_READ_SHOP.isGranted(login);
  }


  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public WebActionResult callService() {
    
    CommunicationService shService = ServiceLocator.getCommunicationService(getLoginInfo());
    List<StockHoliday> shList =shService.getStockHolidayList();
    
    
    List<StockHolidayDetail> shDetailList = new ArrayList<StockHolidayDetail>();
    for(StockHoliday sh : shList){
      StockHolidayDetail detail =new StockHolidayDetail();
      detail.setOrmRowid(sh.getOrmRowid());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(sh.getHolidayDay()); 
        detail.setStockHoliDay(dateString);
        shDetailList.add(detail);
    }
    
    StockHolidayListBean shbean = new StockHolidayListBean();
    shbean.setStockHolidayList(shDetailList);
    setRequestBean(shbean);
    
    //StockHolidayListBean jspBean = getBean();
    String parameter = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }
    if (parameter.equals("new")) {
      addInformationMessage(MessageFormat.format(Messages
          .getString("web.action.back.communication.StockHolidayListMoveAction.2"),1));
    }
    if (parameter.equals("edit")) {
      addInformationMessage(MessageFormat.format(Messages
          .getString("web.action.back.communication.StockHolidayListMoveAction.1"),0));
    }
    if (parameter.equals("new0")){
      addErrorMessage(MessageFormat.format(Messages
          .getString("web.action.back.communication.StockHolidayListMoveAction.3"),2));
    /*  addInformationMessage(MessageFormat.format(Messages
          .getString("web.action.back.communication.StockHolidayListMoveAction.2"),2));*/
    }
    return BackActionResult.RESULT_SUCCESS;
  }
    /**
   * Action名の取得
   * https://192.168.0.220:8443/back/app/catalog/commodity_image
   * 
   * @return Action名
   */
  public String getActionName() {
    return "web.action.back.communication.StockHolidayListInitAction.0";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106071001";
  }

}
