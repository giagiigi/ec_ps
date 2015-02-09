package jp.co.sint.webshop.web.action.front.mypage;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SearchOrderDateType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.OrderReview;
import jp.co.sint.webshop.service.order.MyOrder;
import jp.co.sint.webshop.service.order.MyOrderListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MyReviewBean;
import jp.co.sint.webshop.web.bean.front.mypage.MyReviewBean.MyOrderBean;
import jp.co.sint.webshop.web.bean.front.mypage.MyReviewBean.MyOrderBean.MyOrderDetailBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MyReviewInitAction extends WebFrontAction<MyReviewBean> {

  private MyOrderListSearchCondition condition;

  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public void init() {
    condition = new MyOrderListSearchCondition();
  }

  protected MyOrderListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected MyOrderListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // 検索条件作成
    // セッションから顧客コードを取得
    MyReviewBean bean = new MyReviewBean();

    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    // 顧客存在チェック
    CustomerService customerSv = ServiceLocator.getCustomerService(getLoginInfo());
    if (customerSv.isNotFound(customerCode) || customerSv.isInactive(customerCode)) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    condition.setCustomerCode(customerCode);
    
    //默认近期订单(6个月内)
    String searchDateType = getRequestParameter().get("searchOrderDateType");
    if(StringUtil.isNullOrEmpty(searchDateType) || SearchOrderDateType.RECENT.getValue().equals(searchDateType)){
      condition.setOrderDateType(SearchOrderDateType.RECENT.getValue());
      bean.setSearchOrderDateType(SearchOrderDateType.RECENT.getValue());
    }else{
      bean.setSearchOrderDateType(SearchOrderDateType.ALL.getValue());
      condition.setOrderDateType(SearchOrderDateType.ALL.getValue());
    }
   
    condition.setShippingStatus(new String[] {
      ShippingStatus.SHIPPED.getValue()
    });

    condition = getCondition();

    OrderService orderSv = ServiceLocator.getOrderService(getLoginInfo());
    CommunicationService commSV = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<MyOrder> result = orderSv.searchMyOrderList(condition);

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    //
    // bean.getList().clear();

    // リスト生成
    bean.setPagerValue(PagerUtil.createValue(result));
    List<MyOrder> orderList = result.getRows();
    if (orderList != null && orderList.size() > 0) {
      for (MyOrder order : orderList) {

        MyOrderBean orderBean = new MyOrderBean();
        orderBean.setOrderNo(order.getOrderNo());
        orderBean.setOrderDate(DateUtil.toDateTimeString(order.getOrderDate(), DateUtil.ALIPAY_DATE_FORMAT));
        List<OrderReview> orderDetailList = commSV.getOrderReviewListByCustomerCodeAndOrderNo(customerCode, orderBean.getOrderNo());
        if (orderDetailList != null && orderDetailList.size() > 0) {
          for (OrderReview review : orderDetailList) {
            if (CommodityType.GIFT.getValue().equals(review.getCommodityType())) {
              // detailBean.setIsGift("true");
              continue;
            }
            MyOrderDetailBean detailBean = new MyOrderDetailBean();
            detailBean.setShopCode(review.getShopCode());
            detailBean.setCommodityCode(review.getCommodityCode());
            detailBean.setCommodityName(utilService.getNameByLanguage(review.getCommodityName(), review.getCommodityNameEn(),
                review.getCommodityNameJp()));
            detailBean.setPrice(review.getRetailPrice());
            if (StringUtil.hasValue(review.getRewardId())) {
              detailBean.setReviewFlag("true");
            }

            orderBean.getList().add(detailBean);

          }
        }
        bean.getList().add(orderBean);
      }
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * @param condition
   *          the condition to set
   */
  public void setCondition(MyOrderListSearchCondition condition) {
    this.condition = condition;
  }

  
  @Override
  public void prerender() {
    
    String[] url = getRequestParameter().getPathArgs();
    if(url.length>0){
      if("insert".equals(url[0])){
        addInformationMessage(Messages.getString("web.bean.front.mypage.MyRevieInitAction.0"));
      }
    }
    
  }

  
}
