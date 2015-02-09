package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListMoveAction extends ShippingListBaseAction {

  //ECのShipping情報の場合
  public static final String EMALL_SHIP_TYPE = "T";
  
  private static final String JD_SHIP_TYPE = "D";
  
  private String nextParam = "";

  private String noParam = "";

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = false;

    String[] params = getRequestParameter().getPathArgs();

    if (params.length > 1) {
      nextParam = params[0];
      noParam = params[1];
      ShippingListBean bean = new ShippingListBean();
      bean.setSearchShippingNo(noParam);
      result = validateItems(bean, "searchShippingNo");
    } else {
      result = false;
    }

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    // 受注タイプを取得する
    String shippingType = noParam.substring(0,1);
    
    // ECの場合
    if (!(EMALL_SHIP_TYPE.equals(shippingType) || JD_SHIP_TYPE.equals(shippingType))) {
      
      if (nextParam.equals("shipping")) {
        // 出荷明細へ遷移
        ShippingHeader shippingHeader = service.getShippingHeader(noParam);
        if (shippingHeader == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.order.ShippingListMoveAction.0")));
          setRequestBean(getBean());
          setNextUrl(null);
          return BackActionResult.RESULT_SUCCESS;
        }

        setNextUrl("/app/order/shipping_detail/init/" + noParam);
      } else if (nextParam.equals("order")) {
        // 受注明細へ遷移
        OrderHeader orderHeader = service.getOrderHeader(noParam);
        if (orderHeader == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.order.ShippingListMoveAction.1")));
          setRequestBean(getBean());
          setNextUrl(null);
          return BackActionResult.RESULT_SUCCESS;
        }

        setNextUrl("/app/order/order_detail/init/" + noParam);
      }
    //TMALLの場合
    }else if(EMALL_SHIP_TYPE.equals(shippingType)) 
    {
      if (nextParam.equals("shipping")) {
        // 出荷明細へ遷移
        //TmallShippingHeader shippingHeader = service.getTmallShippingHeader(noParam);
        ShippingContainer orderContainer = service.getShipping(noParam);
        TmallShippingHeader tmallShippingHeader = orderContainer.getTmallShippingHeader();
        if (tmallShippingHeader == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.order.ShippingListMoveAction.0")));
          setRequestBean(getBean());
          setNextUrl(null);
          return BackActionResult.RESULT_SUCCESS;
        }

        setNextUrl("/app/order/shipping_detail/init/" + noParam);
      } else if (nextParam.equals("order")) {
        // 受注明細へ遷移
        //TmallOrderHeader orderHeader = service.getTmallOrderHeader(noParam);
        OrderContainer orderContainer = service.getTmallOrder(noParam);
        TmallOrderHeader tmallOrderHeader = orderContainer.getTmallOrderHeader();
        if (tmallOrderHeader == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.order.ShippingListMoveAction.1")));
          setRequestBean(getBean());
          setNextUrl(null);
          return BackActionResult.RESULT_SUCCESS;
        }

        setNextUrl("/app/order/order_detail/init/" + noParam);
      }
    // JDの場合
    } else {
      // 出荷明細へ遷移
      if(nextParam.equals("shipping")) {
        ShippingContainer orderContainer = service.getJdShipping(noParam);
        JdShippingHeader JdShippingHeader = orderContainer.getJdShippingHeader();
        if(JdShippingHeader == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,Messages.getString("web.action.back.order.ShippingListMoveAction.0")));
          setRequestBean(getBean());
          setNextUrl(null);
          return BackActionResult.RESULT_SUCCESS;
        }
        setNextUrl("/app/order/shipping_detail/init/" + noParam);
      // 受注明細へ遷移
      } else if(nextParam.equals("order")) {
        OrderContainer orderContainer = service.getJdOrder(noParam);
        JdOrderHeader JdOrderHeader = orderContainer.getJdOrderHeader();
        if(JdOrderHeader == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.back.order.ShippingListMoveAction.1")));
          setRequestBean(getBean());
          setNextUrl(null);
          return BackActionResult.RESULT_SUCCESS;
        }
        setNextUrl("/app/order/order_detail/init/" + noParam);
      }
    }
    
    

    DisplayTransition.add(getBean(), "/app/order/shipping_list/search_back", getSessionContainer());

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041006";
  }

}
