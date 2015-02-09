package jp.co.sint.webshop.web.action.front.cart;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.bean.front.cart.BlanketCartBean;
import jp.co.sint.webshop.web.bean.front.cart.BlanketCartBean.BlanketCartDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

import org.apache.log4j.Logger;

/**
 * U2020310:まとめてカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class BlanketCartBaseAction extends WebFrontAction<BlanketCartBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // まとめてカートを使用しない場合は404画面へ
    if (!getConfig().isUseBlanketCart()) {
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
  public abstract WebActionResult callService();

  // 10.1.4 10177 追加 ここから
  public boolean validateBlanketCartDetailBean(BlanketCartDetailBean detail) {
    return validateBlanketCartDetailBean(detail, false);
  }
  // 10.1.4 10177 追加 ここまで

  // 10.1.4 10177 修正 ここから
  // public boolean validateBlanketCartDetailBean(BlanketCartDetailBean detail) {
  public boolean validateBlanketCartDetailBean(BlanketCartDetailBean detail, boolean amountCheck) {
  // 10.1.4 10177 修正 ここまで
    boolean valid = true;
    Logger logger = Logger.getLogger(this.getClass());
    List<ValidationResult> resultList = BeanValidator.validate(detail).getErrors();
    if (resultList.size() > 0) {
      for (ValidationResult result : resultList) {
        addErrorMessage(MessageFormat.format(
            Messages.getString("web.action.front.cart.BlanketCartBaseAction.0"),
            detail.getNo(), result.getFormedMessage()));
        logger.debug(result.getFormedMessage());
      }
      valid = false;
    }
    // 10.1.4 10177 追加 ここから
    if (amountCheck && StringUtil.isNullOrEmpty(detail.getAmount())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.AMOUNT_REQUIRED_ERROR, detail.getNo()));
      valid = false;
    }
    // 10.1.4 10177 追加 ここまで
    return valid;
  }

  @Override
  public void addErrorMessage(String message) {
    super.addErrorMessage(WebUtil.escapeXml(message));
  }
  
  // 10.1.6 10268 追加 ここから
  @Override
  public void prerender() {
    UIMainBean bean = getRequestBean();
    
    if (bean instanceof BlanketCartBean) {
      BlanketCartBean reqBean = (BlanketCartBean) bean;
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      reqBean.setShopList(utilService.getShopNames(true, false, true));
    }
  }
  // 10.1.6 10268 追加 ここまで

}
