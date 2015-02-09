package jp.co.sint.webshop.web.action.front.customer;

import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.MailMagazineBean;
import jp.co.sint.webshop.web.bean.front.customer.MailMagazineBean.MailMagazineListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.text.front.Messages;

import org.apache.log4j.Logger;

/**
 * U2060420:メールマガジン登録のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineInitAction extends MailMagazineBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    WebshopConfig config = getConfig();
    if (!config.isMailMagazineAvailableMode()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.debug(Messages.log("web.action.front.customer.MailMagazineInitAction.0")); //$NON-NLS-1$
      throw new URLNotFoundException();
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

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    List<MailMagazine> mailMagazineList = svc.getMailMagazineList();

    MailMagazineBean bean = new MailMagazineBean();
    for (MailMagazine m : mailMagazineList) {
      MailMagazineListBean listBean = new MailMagazineListBean();
      listBean.setMailMagazineCode(m.getMailMagazineCode());
      listBean.setTitle(m.getMailMagazineTitle());
      listBean.setDescription(m.getMailMagazineDescription());
      bean.getList().add(listBean);
    }

    String email = "";
    if (getLoginInfo().isLogin()) {
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      Customer customer = service.getCustomer(getLoginInfo().getCustomerCode()).getCustomer();
      email = customer.getEmail();
    }
    bean.setEmail(email);

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

}
