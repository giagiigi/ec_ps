package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewEditBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

import org.apache.log4j.Logger;

/**
 * U2030610:ご注文履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryMoveAction extends WebFrontAction<OrderHistoryBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 顧客存在チェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    if (service.isNotFound(getLoginInfo().getCustomerCode()) || service.isInactive(getLoginInfo().getCustomerCode())) {
      setNextUrl("/app/common/index");
      setRequestBean(getBean());

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    String orderNo = "";
    String nextParam = "";
   
    // parameter[0] : 次画面パラメータ , parameter[1] : 受注番号
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      nextParam = StringUtil.coalesce(parameter[0], "");
      if (parameter.length > 1) {
        orderNo = StringUtil.coalesce(parameter[1], "");
      }
    }
    //20111220 os013 add start
    String SelectOrderStatusValue=getBean().getSelectOrderStatusValue();
    //20111220 os013 add end
    // 次画面URL設定
    if (nextParam.equals("order") || nextParam.equals("inquiry")) {
      // ご注文内容存在チェック
      if (StringUtil.isNullOrEmpty(orderNo)) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      } else {
        // 受注番号チェック
        getBean().setOrderNo(orderNo);
        ValidationSummary validateCustomer = BeanValidator.partialValidate(getBean(), "orderNo");
        if (validateCustomer.hasError()) {
          Logger logger = Logger.getLogger(this.getClass());
          for (String rs : validateCustomer.getErrorMessages()) {
            logger.debug(rs);
          }
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.OrderHistoryMoveAction.0")));
          setRequestBean(getBean());
          return FrontActionResult.RESULT_SUCCESS;
        }

        OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
        OrderContainer orderContainer = orderService.getOrder(orderNo);

        if (orderContainer == null || orderContainer.getOrderHeader() == null || orderContainer.getOrderDetails() == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.OrderHistoryMoveAction.0")));
          setRequestBean(getBean());
        } else {
          // 遷移情報
          if (nextParam.equals("order")) {
            setNextUrl("/app/mypage/order_detail/init/" + orderNo);
          } else if (nextParam.equals("inquiry")) {
            OrderService os = ServiceLocator.getOrderService(getLoginInfo());
            String shopCode = os.getOrderHeader(orderNo).getShopCode();
            setNextUrl("/app/customer/inquiry_edit/init/order/" + shopCode + "/" + orderNo);
          }
        }
      }
    //20111220 os013 add start
      //发表评论
    }  else if (nextParam.equals("commoditydetail")) {
      String commodityCode = parameter[1];
      String shopCode = "00000000";
      // 商品存在チェック
      if ( StringUtil.isNullOrEmpty(parameter[1])) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
      CatalogService catalogSv = ServiceLocator.getCatalogService(getLoginInfo());
      CommodityInfo info = catalogSv.getCommodityInfo(shopCode, commodityCode);
      if (info == null || info.getHeader() == null || info.getDetail() == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.mypage.RecommendListMoveAction.1")));
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }
      setNextUrl("/app/catalog/detail/init/" + commodityCode);
      //发表评论
    } else if (nextParam.equals("review_edit")) {
      if (parameter.length < 2 || StringUtil.isNullOrEmpty(parameter[1])) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
      String commodityCode =  parameter[2];
      setNextUrl("/app/catalog/review_edit/init/"+commodityCode+"/"+orderNo);
      
      ReviewEditBean bean = new ReviewEditBean();
      bean.setCommodityCode(commodityCode);
      bean.setOrderNo(orderNo);
      setBean(bean);
    // 20111220 os013 add end
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    //20111220 os013 update start
    // 前画面情報設定
//    DisplayTransition.add(null, "/app/mypage/order_history/init_back/" + getBean().getSelectOrderStatusValue(),
//        getSessionContainer());
    DisplayTransition.add(null, "/app/mypage/order_history/init_back/" + SelectOrderStatusValue,
        getSessionContainer());
    //20111220 os013 update end
    setRequestBean(getBean());
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
