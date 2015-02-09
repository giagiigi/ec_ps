package jp.co.sint.webshop.web.action.back.communication;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.StockHolidayListBean;
import jp.co.sint.webshop.web.text.back.Messages;


public class StockHolidayListMoveAction extends WebBackAction<StockHolidayListBean> {
	
	private final String UPDATE = "edit";
	
	private final String REGISTER = "new";

  @Override
  public boolean authorize() {
    return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo()) && Permission.PRIVATE_COUPON_DELETE_SHOP.isGranted(getLoginInfo());
	//  return true;
  }
  //app/order/shipping/payment_confirm
  //bean不能为空 验证
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    StockHolidayListBean bean = getBean();
    if(urlParam[0].equals("new")){
      if(bean.getStockHoliDay()==null||bean.getStockHoliDay()==""){
        addErrorMessage(MessageFormat.format(Messages
            .getString("web.action.back.communication.StockHolidayListMoveAction.4"),4));
        return false;
      }
    }
    return (urlParam.length ==1 && REGISTER.equals(urlParam[0])) || (urlParam.length == 2 && UPDATE.equals(urlParam[0]));
  }

  @Override
  public WebActionResult callService() {
    String[] urlParam = getRequestParameter().getPathArgs();
    
    if (urlParam[0].equals("new")) {
      boolean shdecide=shdecide();
      if(shdecide){
        newStockHoliday();
        setNextUrl("/app/communication/stock_holiday_list/init/"+"new");
      }else{
        setNextUrl("/app/communication/stock_holiday_list/init/"+"new0");
        
      }
    }else if (urlParam[0].equals("edit")) {   
         editStockHoliday();
          setNextUrl("/app/communication/stock_holiday_list/init/"+"edit"); 
      }
    
    
    return BackActionResult.RESULT_SUCCESS;
    }  

  

  //删除方法
  public boolean editStockHoliday() {
    
    String[] urlParam = getRequestParameter().getPathArgs();
    CommunicationService shService = ServiceLocator.getCommunicationService(getLoginInfo());
    List<StockHoliday> shList =shService.getStockHolidayList();
    for (int i = 0; i < shList.size(); i++) {
      shList.clear();
    }
    
    shList.clear();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String shDate = urlParam[1];
    Date sh = null;
     try {
       sh= df.parse(shDate);
       shService.deleteStockHoliday(sh);  
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return true;
  }
  //增加方法
  public boolean newStockHoliday(){
    StockHolidayListBean bean = getBean();
    CommunicationService shService = ServiceLocator.getCommunicationService(getLoginInfo());
    StockHoliday sh = new StockHoliday();
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    
    
    System.out.println(bean.getStockHoliDay());
    try {
      Date a= df.parse(bean.getStockHoliDay());
      System.out.println(a);
      sh.setHolidayDay(df.parse(bean.getStockHoliDay()));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    shService.insertStockHoliday(sh);
    return true;
  }
  //判断仓库休息日重复添加
  public boolean shdecide (){
    boolean ss=false;
    CommunicationService shService = ServiceLocator.getCommunicationService(getLoginInfo());
    List<StockHoliday> shList =shService.getStockHolidayList();
    StockHolidayListBean bean = getBean();
    if(shList.isEmpty()){
      ss=true;
      return ss;
    }
    for(StockHoliday sh : shList){
      ss=true;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
      String time=sdf.format(sh.getHolidayDay());
      if((bean.getStockHoliDay()).equals(time))
      {
        ss=false;
        break;
      }
  }
    return ss;
  }
  
  
  public String getActionName() {
    return "web.action.back.communication.StockHolidayListMoveAction.0";
  }

  public String getOperationCode() {
    return "5106071002";
  }
  
}
