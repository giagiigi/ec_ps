package jp.co.sint.webshop.ext.tmall.getapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TimeGetRequest;
import com.taobao.api.response.TimeGetResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallGetSysDateTimeService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallGetSysDateTime implements TmallGetSysDateTimeService {

  /**
   * 查询淘宝当前系统时间 api:taobao.time.get
   */
  int ifId = 6;

  @Override
  public String getSysDateTime() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    TimeGetRequest req = new TimeGetRequest();
    try {
      TimeGetResponse response = client.execute(req);
      if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
        String subMsg = "";
        String msg = "";
        if (!StringUtil.isNullOrEmpty(response.getSubMsg())) {
          subMsg = response.getSubMsg();
        } else {
          subMsg = "";
        }
        if (!StringUtil.isNullOrEmpty(response.getMsg())) {
          msg = response.getMsg();
        } else {
          msg = "";
        }
        logger.error(response.getErrorCode() + "_" + subMsg + "_" + msg);
        logtool.log("taobao.time.get（获取淘宝系统时间失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        Date date = response.getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logtool.log("taobao.time.get（获取淘宝系统时间成功：" + date + "）", ifId);
        logtool.log(logtool.printHorizontalLine());
        return format.format(date);
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return null;
  }

}
