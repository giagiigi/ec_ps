package jp.co.sint.webshop.ext.jd.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.api.jd.JdApiConfig;
import jp.co.sint.webshop.configure.JdSendMailConfig;
import jp.co.sint.webshop.ext.jd.LogOutPutRecord;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;

public class JdOrderGetApi {

  int ifId = 2;

  public List<OrderSearchInfo> getJdOrderApiList(String startDate, String endDate, String orderState) {

    Logger logger = Logger.getLogger(this.getClass());
    LogOutPutRecord logtool = null;

    // 定义返回list
    List<OrderSearchInfo> list = new ArrayList<OrderSearchInfo>();
    try {

      JdApiConfig jc = DIContainer.get(JdApiConfig.class.getSimpleName());
      JdClient client = new DefaultJdClient(jc.getReqUrl(), jc.getSessionKey(), jc.getAppKey(), jc.getAppSercet());
      OrderSearchRequest request = new OrderSearchRequest();

      logtool = new LogOutPutRecord(jc.getApiLogpath(), "");

      // 根据最大页数循环所有订单 将数据添加到返回list中
      for (int i = 1; i < 99999; i++) {

        // 实例化对象

        // 设置对象参数
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setOrderState(orderState);
        request.setPage(String.valueOf(i));
        request.setPageSize("100");

        String optionalFields = "order_total_price,order_payment,order_seller_price,freight_price,seller_discount,order_state,"
            + "order_state_remark,invoice_info,order_remark,order_start_time,order_end_time,consignee_info,item_info_list,coupon_detail_list,"
            + "balance_used,modified,payment_confirm_time,logistics_id," + "vat_invoice_info";

        request.setOptionalFields(optionalFields);

        // 调用api获取数据集
        OrderSearchResponse response = client.execute(request);
        if (!response.getCode().equals("0")) {
          logger.error("jd error code:" + response.getCode());
          logger.error("jd enDesc:" + response.getEnDesc());
          logtool.log("获取订单失败", ifId);
          logtool.log(logtool.printHorizontalLine());
          return null;

        }
        // 根据数据条数判断是否需要再次调用api获取数据集
        // 将数据集添加到list
        // 20141211 hdh update start 京东订单下载修正一次最多只能获取100条订单信息
        if (response.getOrderInfoResult().getOrderInfoList() ==null ||response.getOrderInfoResult().getOrderInfoList().size() != 100) {
          if (response.getOrderInfoResult().getOrderInfoList() ==null ||response.getOrderInfoResult().getOrderInfoList().size() != 0) {
            // 20141211 hdh update end
            list.addAll(response.getOrderInfoResult().getOrderInfoList());
          }
          break;
        } else {
          list.addAll(response.getOrderInfoResult().getOrderInfoList());
        }

      }

    } catch (Exception e) {
      logtool.log("出现网络中断", ifId);
      e.printStackTrace();
      return null;
    }

    return list;
  }

  // 发送邮件
  public void sendErrEmail(String startDate, String endDate) {
    MailInfo mailInfo = new MailInfo();
    StringBuffer sb = new StringBuffer();
    sb.append("以下为订单下载失败警告:<BR><BR>");
    sb.append("由于网络通信失败，订单下载API无法调用。<BR><BR>");
    sb.append("下载失败时间段:" + startDate + "~" + endDate);
    mailInfo.setText(sb.toString());
    mailInfo.setSubject("【品店】京东订单下载失败警告");
    mailInfo.setSendDate(DateUtil.getSysdate());

    JdSendMailConfig mailSend = DIContainer.get(JdSendMailConfig.class.getSimpleName());
    mailInfo.setFromInfo(mailSend.getMailFromAddr(), mailSend.getMailFromName());

    mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
    MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    svc.sendImmediate(mailInfo);
  }
}
