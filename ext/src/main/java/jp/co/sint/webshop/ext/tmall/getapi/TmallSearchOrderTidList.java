package jp.co.sint.webshop.ext.tmall.getapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.configure.TmallSendMailConfig;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.TradesSoldIncrementGetResponse;

public class TmallSearchOrderTidList {

  /**
   * 交易信息唯一标示码批量获取 api:taobao.trades.sold.increment.get
   */

  @SuppressWarnings("deprecation")
  public List<String> searchOrderTidList(String startTime, String endTime) {

    boolean mailFlg = false;
    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    List<String> list = new ArrayList<String>();
    String[] statusStr = {
        "WAIT_BUYER_PAY", "WAIT_SELLER_SEND_GOODS", "TRADE_FINISHED", "TRADE_CLOSED", "TRADE_CLOSED_BY_TAOBAO","TRADE_NO_CREATE_PAY"
    };

    for (int st = 0; st < statusStr.length; st++) {
      
      for (int i = 1; i < 99999; i++) {
        TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
        TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
 
        try {
          // 组装应用参数
          req.setFields("tid");
          req.setPageNo((long) i);
          req.setPageSize(100L);
          // 日期格式 2013/07/26 20:57:46
          Date dateTimeStart = new Date(startTime);
          req.setStartModified(dateTimeStart);
          Date dateTimeEnd = new Date(endTime);
          req.setEndModified(dateTimeEnd);
          req.setUseHasNext(true);
          req.setStatus(statusStr[st]);
          TradesSoldIncrementGetResponse response = client.execute(req, tc.getSessionKey());
          List<Trade> tradeList = response.getTrades();
          boolean flag = false;
          if (tradeList != null && tradeList.size() > 0) {
            Trade trade = null;
            for (int c = 0; c < tradeList.size(); c++) {
              trade = (Trade) tradeList.get(c);
              if (!trade.getTid().toString().equals("926517309502062")){
                list.add(trade.getTid() + "");
              }
            }
          } else {
            boolean has_next = response.getHasNext();
            if (!has_next) {
              flag = true;
            }
          }
          if (flag) {
            break;
          }
        } catch (Exception e1) {
          mailFlg = true;
          break;
        }
      }
    }

    if (mailFlg) {
      // TODO sand emall
      MailInfo mailInfo = new MailInfo();
      StringBuffer sb = new StringBuffer();
      sb.append("以下为订单下载失败警告:<BR><BR>");
      sb.append("由于网络通信失败，订单下载API无法调用。<BR><BR>");
      sb.append("下载失败时间段:" + startTime + "~" + endTime);
      mailInfo.setText(sb.toString());
      mailInfo.setSubject("【品店】淘宝订单下载失败警告");
      mailInfo.setSendDate(DateUtil.getSysdate());

      TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());
      mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
      String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
      String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
      for (int i = 0; i < mailToAddrArray.length; i++) {
        if (i >= mailToNameArray.length) {
          mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
        } else {
          mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
        }
      }
      mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
      MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
      svc.sendImmediate(mailInfo);
      list.clear();
      list.add("NETERROR");
    }
    return list;
  }
}
