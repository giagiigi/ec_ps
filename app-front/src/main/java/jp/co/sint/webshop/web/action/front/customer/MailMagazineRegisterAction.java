package jp.co.sint.webshop.web.action.front.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.data.dto.MailMagazineSubscriber;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.MailMagazineBean;
import jp.co.sint.webshop.web.bean.front.customer.MailMagazineBean.MailMagazineListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

import org.apache.log4j.Logger;

/**
 * U2060420:メールマガジン登録のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineRegisterAction extends MailMagazineBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = super.validate();
    if (getRequestParameter().getPathArgs().length <= 0) {
      result &= false;
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else {
      result &= true;
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

    MailMagazineBean bean = null;
    String encrypt = WebUtil.urlEncode(getPathInfo(0)); // 暗号取得
    String dencrypt = PasswordUtil.decrypt(encrypt); // 復号化

    // 複合された値を切り分ける「メールマガジンコード;メールアドレス;登録依頼受付日時」
    String[] element = dencrypt.split(";");

    if (element.length == 3) {
      String[] mailMagazineCodeList = element[0].split(",");
      String email = element[1];
      Date requestedDatetime = DateUtil.fromString(element[2], true);

      // 有効期限チェック(現在日時が受付時間と締切時間に含まれるか)
      Date closedDatetime = DateUtil.addHour(requestedDatetime, 2);
      Date sysdate = DateUtil.getSysdate();
      if (!DateUtil.isPeriodDate(requestedDatetime, closedDatetime, sysdate)) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.EXPIRED_URL_ERROR, Messages
            .getString("web.action.front.customer.MailMagazineRegisterAction.0")));
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }

      // 登録処理
      CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
      ServiceResult result = null;
      Logger logger = Logger.getLogger(this.getClass());

      // 登録に成功したメルマガのリスト
      List<String> registeredMailMagazineTitleList = new ArrayList<String>();
      // 既に登録されていたメルマガのリスト
      List<String> existMailMagazineTitleList = new ArrayList<String>();
      // 既に消されていたメルマガのリスト
      List<String> deletedMailMagazineTitleList = new ArrayList<String>();

      for (int i = 0; i < mailMagazineCodeList.length; i++) {

        // メールマガジンの存在チェック
        if (svc.getMailMagazine(mailMagazineCodeList[i]) == null) {
          for (MailMagazineListBean m : getBean().getList()) {
            if (m.getMailMagazineCode().equals(mailMagazineCodeList[i])) {
              deletedMailMagazineTitleList.add(m.getTitle());
            }
          }
          continue;
        }

        MailMagazineSubscriber subscriber = new MailMagazineSubscriber();
        subscriber.setMailMagazineCode(mailMagazineCodeList[i]);
        subscriber.setEmail(email);
        result = svc.insertMailMagazineSubscriber(subscriber);

        if (result.hasError()) {
          for (ServiceErrorContent error : result.getServiceErrorList()) {
            if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
              return FrontActionResult.SERVICE_VALIDATION_ERROR;
            } else if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
              logger.error("already registered. mailMagazineCode : " + mailMagazineCodeList[i]);
              existMailMagazineTitleList.add(svc.getMailMagazine(mailMagazineCodeList[i]).getMailMagazineTitle());
            }
          }
        } else {
          registeredMailMagazineTitleList.add(svc.getMailMagazine(mailMagazineCodeList[i]).getMailMagazineTitle());
        }

      }

      // メッセージ生成
      if (existMailMagazineTitleList.size() > 0) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.MAILMAGAZINE_DUPLICATED_ERROR));
        for (String title : existMailMagazineTitleList) {
          addErrorMessage("・" + title);
        }
      }
      if (deletedMailMagazineTitleList.size() > 0) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.front.customer.MailMagazineRegisterAction.2")));
        for (String s : deletedMailMagazineTitleList) {
          addErrorMessage("・" + s);
        }
      }
      if (registeredMailMagazineTitleList.size() > 0) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,  Messages
            .getString("web.action.front.customer.MailMagazineRegisterAction.3")));
        for (String s : registeredMailMagazineTitleList) {
          addInformationMessage("・" + s);
        }
      }

      // メルマガリストの取得
      List<MailMagazine> mailMagazineList = svc.getMailMagazineList();
      bean = new MailMagazineBean();
      for (MailMagazine m : mailMagazineList) {
        MailMagazineListBean listBean = new MailMagazineListBean();
        listBean.setMailMagazineCode(m.getMailMagazineCode());
        listBean.setTitle(m.getMailMagazineTitle());
        listBean.setDescription(m.getMailMagazineDescription());
        bean.getList().add(listBean);
      }
      bean.setEmail(email);

    }

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;

  }

  private String getPathInfo(int index) {
    String[] pathInfo = getRequestParameter().getPathArgs();
    if (pathInfo != null && pathInfo.length > index) {
      return pathInfo[index];
    }
    return "";
  }

}
