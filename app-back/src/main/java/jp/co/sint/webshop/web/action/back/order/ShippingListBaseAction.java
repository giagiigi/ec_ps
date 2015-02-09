package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.order.ShippingList;
import jp.co.sint.webshop.service.order.ShippingListSearchCondition;
import jp.co.sint.webshop.service.order.ShippingListSearchQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean.ShippingSearchedListBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListBaseAction extends WebBackAction<ShippingListBean> {

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    ShippingListBean bean = getBean();
    // 出荷ステータス設定
    List<CodeAttribute> statusList = new ArrayList<CodeAttribute>();
    for (ShippingStatus status : ShippingStatus.values()) {
      if (status != ShippingStatus.NOT_READY && status != ShippingStatus.CANCELLED) {
        statusList.add(status);
      }
    }

    bean.setSearchShippingStatusList(statusList);
    setBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    return null;
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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    ShippingListBean reqBean = (ShippingListBean) getRequestBean();

    if (getLoginInfo().isSite()) {
      reqBean.setShopListDisplayFlg(true);
    } else {
      reqBean.setShopListDisplayFlg(false);
    }

    if (Permission.SHIPPING_DATA_IO_SITE.isGranted(getLoginInfo())) {
      reqBean.setShippingImportDisplayFlg(true);
    } else if (Permission.SHIPPING_DATA_IO_SHOP.isGranted(getLoginInfo())) {
      reqBean.setShippingImportDisplayFlg(true);
    // 10.1.3 10078 追加 ここから
    } else {
      reqBean.setShippingImportDisplayFlg(false);
    // 10.1.3 10078 追加 ここまで
    }
    
    if (Permission.SHIPPING_UPDATE_SHOP.isGranted(getLoginInfo())) {
      reqBean.setShippingInstructionsDisplayFlg(true);
    } else if (Permission.SHIPPING_UPDATE_SITE.isGranted(getLoginInfo())) {
      reqBean.setShippingInstructionsDisplayFlg(true);
    } else {
      reqBean.setShippingInstructionsDisplayFlg(false);
    }
  }

  /**
   * 出荷ステータスを設定します。
   */
  public void setShippingStatus() {
    // 出荷ステータス設定
    List<CodeAttribute> statusList = new ArrayList<CodeAttribute>();
    for (ShippingStatus status : ShippingStatus.values()) {
      if (status != ShippingStatus.CANCELLED) {
        statusList.add(status);
      }
    }
    ShippingListBean bean = (ShippingListBean) getRequestBean();
    bean.setSearchShippingStatusList(statusList);
    setBean(bean);
  }

  /**
   * 出荷情報のリストを設定します。
   * 
   * @param nextBean
   * @param shippingList
   * @param list
   */
  public void setShippingList(ShippingListBean nextBean, List<ShippingList> shippingList, List<ShippingSearchedListBean> list) {
    for (ShippingList sl : shippingList) {
      ShippingSearchedListBean detail = new ShippingSearchedListBean();
      detail.setShippingNo(sl.getShippingNo());
      detail.setOrderNo(sl.getOrderNo());
      detail.setOrderDatetime(DateUtil.toDateString(sl.getOrderDatetime()));
      detail.setAddressLastName(sl.getAddressLastName());
      detail.setAddressFirstName(sl.getAddressFirstName());
      detail.setCustomerLastName(sl.getCustomerLastName());
      detail.setCustomerFirstName(sl.getCustomerFirstName());
      detail.setDeliveryTypeName(sl.getDeliveryTypeName());
      detail.setShippingDate(DateUtil.toDateString(sl.getShippingDate()));
      detail.setShippingDirectDate(DateUtil.toDateString(sl.getShippingDirectDate()));
      // 20120116 ysy update start
      detail.setDelivereyAppointedDate(sl.getDeliveryAppointedDate());
      detail.setDeliveryCompanyName(sl.getDeliveryCompanyName());
      detail.setDeliveryCompanyNo(sl.getDeliveryCompanyNo());
      //upd by lc 2012-03-21 start   解决画面检索TMALL运单号问题
      //detail.setDeliverySlipNo1(sl.getDeliverySlipNo1());
      //upd by lc 2012-03-21 start
      detail.setDeliverySlipNo1(sl.getDeliverySlipNo());
      // 20120116 ysy update end      
      detail.setCaution(sl.getCaution());
      detail.setMessage(sl.getMessage());
      detail.setDeliveryRemark(sl.getDeliveryRemark());

      Date orderUpdatedDatetime = null;
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      OrderHeader header = orderService.getOrderHeader(sl.getOrderNo());
      if (header != null) {
        orderUpdatedDatetime = header.getUpdatedDatetime();
      }
      ShippingHeader shippingHeader = orderService.getShippingHeader(sl.getShippingNo());
      if (shippingHeader != null) {
        ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
        DeliveryType deliveryType = shopService.getDeliveryType(shippingHeader.getShopCode(), NumUtil
            .toLong(sl.getDeliveryTypeNo()));
        if (deliveryType != null) {
          detail.setParcelUrl(deliveryType.getParcelUrl());
        }
      }
      detail.setOrderUpdatedDatetime(orderUpdatedDatetime);

      detail.setUpdatedDatetime(sl.getUpdatedDatetime());

      if (sl.getArrivalDate() == null && sl.getArrivalTimeStart() == null && sl.getArrivalTimeEnd() == null) {
        detail.setArrivalDate("");
      } else if (sl.getArrivalDate() != null && sl.getArrivalTimeStart() == null && sl.getArrivalTimeEnd() == null) {
        detail.setArrivalDate(DateUtil.toDateString(sl.getArrivalDate()));
      } else if (sl.getArrivalDate() == null && sl.getArrivalTimeStart() != null && sl.getArrivalTimeEnd() != null) {
        detail.setArrivalDate(MessageFormat.format(Messages.getString("web.action.back.order.ShippingListBaseAction.0"),
            sl.getArrivalTimeStart(), sl.getArrivalTimeEnd()));
      } else if (sl.getArrivalDate() != null && sl.getArrivalTimeStart() != null && sl.getArrivalTimeEnd() != null) {
        detail.setArrivalDate(DateUtil.toDateString(sl.getArrivalDate()) + " "
            + MessageFormat.format(Messages.getString("web.action.back.order.ShippingListBaseAction.0"),
                sl.getArrivalTimeStart(), sl.getArrivalTimeEnd()));
      } else {
        detail.setArrivalDate("");
      }

      for (ShippingStatus ss : ShippingStatus.values()) {
        if (ss.getValue().equals(sl.getShippingStatus())) {
          detail.setShippingStatus(ss.getName());
        }
      }
      list.add(detail);
    }

    nextBean.setList(list);
  }

  /**
   * 出荷管理の検索条件を設定します。
   * 
   * @param reqBean
   * @return 出荷管理の検索条件
   */
  public ShippingListSearchCondition createSearchCondition(ShippingListBean reqBean) {
    ShippingListSearchCondition condition = new ShippingListSearchCondition();

    String searchShopCode = "";
    if (getLoginInfo().isSite()) {
      searchShopCode = reqBean.getSearchShopCode();
    } else {
      searchShopCode = getLoginInfo().getShopCode();
    }

    condition.setSearchShippingNo(reqBean.getSearchShippingNo());
    condition.setSearchOrderNo(reqBean.getSearchOrderNo());

    // 受注日FromTo
    condition.setSearchFromOrderDatetime(reqBean.getSearchFromOrderDate());
    condition.setSearchToOrderDatetime(reqBean.getSearchToOrderDate());
    if (reqBean.getSearchShippingDirectStatus().equals("")) {
      // 全て
      condition.setSearchOnlySetDirectDate(false);
      condition.setSearchOnlyNotSetDirectDate(false);
    } else if (reqBean.getSearchShippingDirectStatus().equals("0")) {
      // 出荷指示日設定済のデータのみ
      condition.setSearchOnlySetDirectDate(true);
      condition.setSearchOnlyNotSetDirectDate(false);
    } else if (reqBean.getSearchShippingDirectStatus().equals("1")) {
      // 出荷指示日未設定のデータのみ
      condition.setSearchOnlySetDirectDate(false);
      condition.setSearchOnlyNotSetDirectDate(true);
    }
    // 20120116 ysy add start
    if (reqBean.getSearchShippingOrderStatus().equals("")) {
        // 全て
        condition.setSearchOnlyEcOrder(false);
        condition.setSearchOnlyTmallOrder(false);
        condition.setSearchOnlyJDOrder(false);
      } else if (reqBean.getSearchShippingOrderStatus().equals("0")) {
        // 出荷指示日設定済のデータのみ
        condition.setSearchOnlyEcOrder(true);
        condition.setSearchOnlyTmallOrder(false);
        condition.setSearchOnlyJDOrder(false);
      } else if (reqBean.getSearchShippingOrderStatus().equals("1")) {
        // 出荷指示日未設定のデータのみ
        condition.setSearchOnlyEcOrder(false);
        condition.setSearchOnlyTmallOrder(true);
        condition.setSearchOnlyJDOrder(false);
      } else if (reqBean.getSearchShippingOrderStatus().equals("2")) {
        condition.setSearchOnlyJDOrder(true);
        condition.setSearchOnlyEcOrder(false);
        condition.setSearchOnlyTmallOrder(false);
      }
    // 20120116 ysy add end    

    condition.setSearchCustomerName(reqBean.getSearchCustomerName());
    condition.setSearchCustomerNameKana(reqBean.getSearchCustomerNameKana());
    condition.setSearchAddressName(reqBean.getSearchAddressName());
    condition.setSearchCustomerPhoneNumber(reqBean.getSearchCustomerPhoneNumber());
    //Add by V10-CH start
    condition.setSearchCustomerMobileNumber(reqBean.getSearchCustomerMobileNumber());
    //Add by V10-CH end
    condition.setSearchShopCode(searchShopCode);
    // 20120118 ysy add start
    condition.setSearchDeliveryCompanyNo(reqBean.getSearchDeliveryCompanyNo());
    // 20120118 ysy add end
    condition.setSearchDeliveryTypeNo(reqBean.getSearchDeliveryTypeNo());
    condition.setSearchDeliverySlipNo1(reqBean.getSearchDeliverySlipNo1());
    condition.setSearchShippingStatus(reqBean.getSearchShippingStatus());
    condition.setSearchFromShippingDatetime(reqBean.getSearchFromShippingDatetime());
    condition.setSearchToShippingDatetime(reqBean.getSearchToShippingDatetime());
    condition.setSearchFromShippingDirectDate(reqBean.getSearchFromShippingDirectDate());
    condition.setSearchToShippingDirectDate(reqBean.getSearchToShippingDirectDate());
    condition.setSearchReturnItemType(reqBean.getSearchReturnItemType());
    condition.setRemoveDataTransportFlg(!reqBean.isSearchDataTransportFlg());
    condition.setRemoveFixedSalesDataFlg(!reqBean.isSearchFixedSalesDataFlg());
    condition.setSearchFromTmallTid(reqBean.getSearchFromTmallTid());
    if (reqBean.isSortAscFlg()) {
      condition.setSortItem(ShippingListSearchQuery.SORT_ASC);
    } else {
      condition.setSortItem(ShippingListSearchQuery.SORT_DESC);
    }
    return condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return false;
  }
}
