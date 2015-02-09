package jp.co.sint.webshop.sms.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import jp.co.sint.webshop.sms.SmsSender;
import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

import cn.emay.sdk.client.api.Client;


public class SmsSenderImpl implements SmsSender {

  private Logger logger = Logger.getLogger(this.getClass());
  
  private static Client client ;

  public boolean sendSms(String[] mobile, String msg) throws UnsupportedEncodingException, MessagingException {

    /**
     * 发送短信、可以发送定时和即时短信 1、mobile 手机数组长度不能超过1000 2、msg 最多500个汉字或1000个纯英文
     * 请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
     * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
     * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号) 5、其它短信发送请参考使用手册自己尝试使用
     */
    boolean flag = false;
    Client client = null;
    try {
//      client = new Client(DIContainer.getSmsValue().getAccountId(), DIContainer.getSmsValue().getKey());
      client = getClient();
      int i = client.sendSMS(mobile, msg, 5);// 带扩展码
      logger.info("返回编号:"+i);
      //System.out.println("返回编号:"+i+"-------------------------------------------------");
      if (i == 0) {
        flag = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
//      if(client != null){
//        closeClient(client);
//      }

    }
    return flag;
  }

  public synchronized static  Client getClient() {
    if (client == null) {
      try {
        client = new Client(DIContainer.getSmsValue().getAccountId(), DIContainer.getSmsValue().getKey());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return client;
  }
  
  public void closeClient(Client client){
    try {
      client.closeSocketConnect();
      client.closeRemoteSocket();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
