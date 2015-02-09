package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set; // 10.1.7 10313 追加

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.DeliveryHistoryInfo;
import jp.co.sint.webshop.service.customer.DeliveryHistorySearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil; // 10.1.3 10126 追加
import jp.co.sint.webshop.utility.Sku; // 10.1.7 10313 追加
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.DeliveryHistoryBean;
import jp.co.sint.webshop.web.bean.front.mypage.DeliveryHistoryBean.DeliveryHistoryCommodityDetail;
import jp.co.sint.webshop.web.bean.front.mypage.DeliveryHistoryBean.DeliveryHistoryDetail;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030630:配送履
歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryHistoryInitAction extends WebFrontAction<DeliveryHistoryBean> {

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
    DeliveryHistoryBean bean = new DeliveryHistoryBean();

    // URLから取得したアドレス帳番号をセット
    String[] parameter = getRequestParameter().getPathArgs();
    bean.setAddressNo(parameter[0]);

    // アドレス帳番号チェック
    bean.setAddressNo(bean.getAddressNo());
    ValidationSummary validateCustomer = BeanValidator.partialValidate(bean, "addressNo");
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.DeliveryHistoryInitAction.0")));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // セッションから顧客コードを取得
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

    bean.setCustomerCode(customerCode);

    // 顧客情報を取得
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    CustomerAddress address = customerService.getCustomerAddress(customerCode, Long.parseLong(bean.getAddressNo()));

    // アドレス帳が存在しない
    if (address == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.DeliveryHistoryInitAction.0")));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    bean.setAddressFirstName(address.getAddressFirstName());
    bean.setAddressLastName(address.getAddressLastName());
    bean.setPostalCode(address.getPostalCode());
    bean.setAddress1(address.getAddress1());
    bean.setAddress2(address.getAddress2());
    bean.setAddress3(address.getAddress3());
    bean.setAddress4(address.getAddress4());

    // 配送履歴を取得
    DeliveryHistorySearchCondition condition = new DeliveryHistorySearchCondition();
    condition.setSearchCustomerCode(customerCode);
    condition.setSearchAddressNo(bean.getAddressNo());

    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索実行
    SearchResult<DeliveryHistoryInfo> result = customerService.findDeliveryHistoryInfo(condition);

    DeliveryHistoryBean nextBean = bean;
    nextBean.getList().clear();

    // リスト生成
    nextBean.setPagerValue(PagerUtil.createValue(result));
    List<DeliveryHistoryInfo> deliveryList = result.getRows();
    List<DeliveryHistoryDetail> detailList = nextBean.getList();

    for (DeliveryHistoryInfo b : deliveryList) {
      if (b.getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
        // 返品の場合は表示しない
        continue;
      }
      DeliveryHistoryDetail row = new DeliveryHistoryDetail();
      row.setShippingDate(DateUtil.toDateString(DateUtil.fromString(b.getShippingDate())));
      row.setShippingNo(b.getShippingNo());

      List<OrderDetail> orderDetailList = orderService.getOrderDetailList(b.getOrderNo());
      List<DeliveryHistoryCommodityDetail> commodityList = new ArrayList<DeliveryHistoryCommodityDetail>();
      Set<Sku> skuSet = orderService.getShipping(b.getShippingNo()).getSkuSet(); // 10.1.7 10313 追加
      
      for (OrderDetail dl : orderDetailList) {
        // 10.1.7 10313 追加 ここから
        if (!skuSet.contains(new Sku(dl.getShopCode(), dl.getSkuCode()))) {
          continue;
        }
        // 10.1.7 10313 追加 ここまで
        DeliveryHistoryCommodityDetail commodity = new DeliveryHistoryCommodityDetail();

        commodity.setCommodityName(dl.getCommodityName());
        commodity.setStandardDetail1Name(dl.getStandardDetail1Name());
        commodity.setStandardDetail2Name(dl.getStandardDetail2Name());

        commodityList.add(commodity);
      }
      row.setCommodityList(commodityList);

      // 10.1.3 10126 修正 ここから
      // row.setTotalPrice(b.getTotalPrice());
      // 10.1.7 10313 修正 ここから
      // 配送先毎の合計金額は sum((商品単価＋ギフト単価)×数量)＋送料。
      // 支払手数料は注文単位の要素なので除外する。
      // row.setTotalPrice(NumUtil.toString(NumUtil.toLong(b.getTotalPrice()) + b.getPaymentCommission()));
      row.setTotalPrice(NumUtil.toString(NumUtil.toLong(b.getTotalPrice())));
      // 10.1.7 10313 修正 ここまで
      // 10.1.3 10126 修正 ここまで
      row.setOrderDatetime(b.getOrderDatetime());

      detailList.add(row);
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

}
