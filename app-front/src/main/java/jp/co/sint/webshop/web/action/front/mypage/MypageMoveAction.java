package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewEditBean;
import jp.co.sint.webshop.web.bean.front.mypage.MypageBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

import org.apache.log4j.Logger;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MypageMoveAction extends WebFrontAction<MypageBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length < 1) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
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
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    if (service.isNotFound(customerCode) || service.isInactive(customerCode)) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    String nextParam = "";

    // parameter[0] : 次画面パラメータ
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      nextParam = StringUtil.coalesce(parameter[0], "");
    }

    // 次画面URL設定
    if (nextParam.equals("edit")) {
      setNextUrl("/app/customer/customer_edit1/init");
    } else if (nextParam.equals("point")) {
      setNextUrl("/app/mypage/point_history/init");
    } else if (nextParam.equals("address")) {
    //20111227 os013 delete start
      // アドレス帳一覧存在チェック
//      CustomerSearchCondition condition = new CustomerSearchCondition();
//      condition.setCustomerCode(customerCode);
//      if (service.getCustomerAddressList(condition).getRowCount() < 1) {
//        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
//            Messages.getString("web.action.front.mypage.MypageMoveAction.0")));
//        setRequestBean(getBean());
//
//        return FrontActionResult.RESULT_SUCCESS;
//      }
      //20111227 os013 delete end
      setNextUrl("/app/mypage/address_list/init");
    } else if (nextParam.equals("favorite")) {
      setNextUrl("/app/mypage/favorite_list/init");
    } else if (nextParam.equals("order")) {
      setNextUrl("/app/mypage/order_history/init");
    } else if (nextParam.equals("reserve")) {
      setNextUrl("/app/mypage/order_history/init/reserve");
    } else if (nextParam.equals("cancelable")) {
      setNextUrl("/app/mypage/order_history/init/cancelable");
    } else if (nextParam.equals("recommend")) {
      setNextUrl("/app/mypage/recommend_list/init");
    } else if (nextParam.equals("password")) {
      setNextUrl("/app/mypage/customer_changepassword/init");
    } else if (nextParam.equals("coupon")) {
      setNextUrl("/app/mypage/coupon_list/init");
      // 2013/4/1 优惠券对应 ob add start
    } else if (nextParam.equals("friend")) {
      setNextUrl("/app/mypage/friend_coupon/init");
      // 2013/4/1 优惠券对应 ob add end
      //2013/04/01 优惠券对应 ob add start
    } else if (nextParam.equals("couponHistory")) {
      setNextUrl("/app/mypage/my_coupon_history/init");
      // 2013/04/01 优惠券对应 ob add end
      // 20120111 os013 add start
    } else if (nextParam.equals("privateFriendCoupon")) {
      setNextUrl("/app/mypage/private_coupon_exchange/init");
    } else if (nextParam.equals("giftCard")) {
      setNextUrl("/app/mypage/new_gift_card/init");   
      
    } else if (nextParam.equals("commoditydetail")) {
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
    } else if(nextParam.equals("newCouponHistory")){
      setNextUrl("/app/mypage/couponhistory_list/init");
      //20120111 os013 add end
      // 20111219 os013 add start
    } else if (nextParam.equals("review")) {
      setNextUrl("/app/mypage/review_list/init");
    } else if (nextParam.equals("review_edit")) {
      if (parameter.length < 2 || StringUtil.isNullOrEmpty(parameter[1])) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
      String commodityCode =  parameter[1];
      String orderNo=parameter[2];
      setNextUrl("/app/catalog/review_edit/init/"+commodityCode+"/"+orderNo);
    
      ReviewEditBean bean = new ReviewEditBean();
      bean.setCommodityCode(commodityCode);
      bean.setOrderNo(orderNo);
      setBean(bean);
      // 20111219 os013 add end
    } else if (nextParam.equals("order_detail")) {
      String orderNo = "";
      if (parameter.length < 2 || StringUtil.isNullOrEmpty(parameter[1])) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      } else {
        orderNo = parameter[1];
        getBean().setOrderNo(orderNo);
        ValidationSummary validateCustomer = BeanValidator.partialValidate(getBean(), "orderNo");
        if (validateCustomer.hasError()) {
          Logger logger = Logger.getLogger(this.getClass());
          for (String rs : validateCustomer.getErrorMessages()) {
            logger.debug(rs);
          }
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.MypageMoveAction.1")));
          setRequestBean(getBean());
          setNextUrl(null);
          return FrontActionResult.RESULT_SUCCESS;
        }
      }

      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      OrderHeader orderHeader = orderService.getOrderHeader(orderNo);
      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.mypage.MypageMoveAction.1")));
        setRequestBean(getBean());
        setNextUrl(null);
        return FrontActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/mypage/order_detail/init/" + orderNo);
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/mypage/mypage/init", getSessionContainer());

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
