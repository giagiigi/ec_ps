package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;


/**
 * U1020220:受注管理明細のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class OrderDetailChangeDeliveryDateAction extends OrderDetailBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
      authorization = Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      authorization = Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo()) || Permission.ORDER_UPDATE_SHOP.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
    }

    return authorization;
  }

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
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
 
    OrderDetailBean bean = getBean();
    

    	 OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
       OrderContainer orderContainer = orderService.getTmallOrder(bean.getOrderNo());
       TmallOrderHeader tmallOrderHeader = orderContainer.getTmallOrderHeader();
       // 存在チェック
       if (tmallOrderHeader == null) {
         setNextUrl("/app/order/order_list");
         setRequestBean(bean);
         return BackActionResult.RESULT_SUCCESS;
       }
       // ショップ個別決済時、他ショップ情報の場合はログイン画面へ
       if (getConfig().getOperatingMode() == OperatingMode.SHOP && getLoginInfo().isShop()) {
         if (!getLoginInfo().getShopCode().equals(tmallOrderHeader.getShopCode())) {
           setNextUrl("/app/common/login");
           setRequestBean(bean);
           return BackActionResult.RESULT_SUCCESS;
         }
       }
   
       UtilService s = ServiceLocator.getUtilService(getLoginInfo());
      
       //配送希望日时指定
       ShippingContainer shippingContainer = orderContainer.getShippings().get(0);
       boolean codFlg = false;
       if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(tmallOrderHeader.getPaymentMethodType())) {
         codFlg = true;
       }
       
       List<CartCommodityInfo> commodityList = new ArrayList<CartCommodityInfo>();
       for (TmallOrderDetail orderDetail : orderContainer.getTmallIOrderDetails()) {
         CartCommodityInfo cartCommodityInfo = new CartCommodityInfo();
         CommodityInfo commodityInfo = catalogService.getSkuInfo(orderDetail.getShopCode(), orderDetail.getSkuCode());
         if (commodityInfo!=null && commodityInfo.getHeader()!=null) {
           cartCommodityInfo.setStockManagementType(commodityInfo.getHeader().getStockManagementType().toString());
           commodityList.add(cartCommodityInfo);
         }
       }
       
       List<CodeAttribute> deliveryAppointedDateList = s.getTmallDeliveryDateList(shippingContainer.getTmallShippingHeader().getShopCode(),
           shippingContainer.getTmallShippingHeader().getPrefectureCode(), codFlg, commodityList);
       bean.setDeliveryAppointedDateList(deliveryAppointedDateList);
       
       String deliveryDate = bean.getShippingList().get(0).getDeliveryAppointedDate();
       boolean exitsFlg = false;
       if (StringUtil.hasValue(deliveryDate)) {
         for(CodeAttribute info : deliveryAppointedDateList) {
           if (info.getValue().equals(deliveryDate)) {
             exitsFlg = true;
           }
         }
       }
       if (!exitsFlg) {
         deliveryDate = null;
       }
       if (StringUtil.isNullOrEmpty(deliveryDate) && deliveryAppointedDateList.size()>0) {
         deliveryDate = deliveryAppointedDateList.get(0).getValue();
       }
       
       List<CodeAttribute> deliveryAppointedTimeList = s.getTmallDeliveryTimeList(shippingContainer.getTmallShippingHeader().getShopCode(),
           shippingContainer.getTmallShippingHeader().getPrefectureCode(), codFlg, deliveryDate , shippingContainer.getTmallShippingHeader().getAreaCode());
       bean.setDeliveryAppointedTimeList(deliveryAppointedTimeList);


       bean.setAddressScript(s.createAddressScript());
       bean.setAddressPrefectureList(s.createPrefectureList());
       bean.setAddressCityList(s.createCityList(bean.getOrderHeaderEdit().getPrefectureCode()));
       bean.setAddressAreaList(s.createAreaList(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit().getCityCode()));

       setRequestBean(bean);


    return BackActionResult.RESULT_SUCCESS;
  }


 

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderDetailInitAction.6");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102022008";
  }

}
