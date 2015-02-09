package jp.co.sint.webshop.web.action.front.order;

import java.util.List;

import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.data.ContentsListResult;
import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.CompleteBean;
import jp.co.sint.webshop.web.bean.front.order.EnqueteEditBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020150:注文完了のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class CompleteInitAction extends WebFrontAction<CompleteBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (!validateBean(getBean())) {
      setNextUrl("/app/common/index");
      return false;
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
    CompleteBean bean = getBean();

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Enquete enquete = service.getCurrentEnquete();

    // アンケートが存在し、未回答である

    boolean displayEnquete = enquete != null && !service.isAlreadyAnswerEnquete(bean.getOrderUserCode(), enquete.getEnqueteCode());

    if (displayEnquete) {
      List<EnqueteQuestion> questionList = service.getEnqueteQuestionList(enquete.getEnqueteCode());

      // 設問が存在する

      displayEnquete &= !questionList.isEmpty();

      for (EnqueteQuestion q : questionList) {
        if (q.getEnqueteQuestionType().equals(NumUtil.toLong(EnqueteQuestionType.FREE.getValue()))) {
          continue;
        }
        // 多選式、単一選択式のときは選択肢があることを確認する
        List<EnqueteChoice> choicesList = service.getEnqueteChoiceList(q.getEnqueteCode(), q.getEnqueteQuestionNo());
        displayEnquete &= !choicesList.isEmpty();
      }
    }

    if (displayEnquete) {
      bean.setEnqueteCode(enquete.getEnqueteCode());
      bean.setEnqueteInvestPoint(enquete.getEnqueteInvestPoint());
      // add by V10-CH start
      bean.setEnqueteInvestPointDisplayFlg(enquete.getEnqueteInvestPoint().intValue() == 0 ? false : true);
      // add by V10-CH end
      bean.setDisplayEnquete(true);
    }

    Cart cart = getCart();
    if (getLoginInfo().isGuest()) {
      if (cart.getItemCount() == 0) {
        // カートの中身が空ならログイン情報とゲスト入力情報を消去

        getSessionContainer().getTempBean();
        clearLoginInfo();
      } else {
        // カートの中身が空でなければログイン情報を破棄
        clearLoginInfo();
      }
    }

    // カートの中身が空ならカートをクリアする

    if (cart.getItemCount() == 0) {
      cart.clear();
      bean.setDisplayContinue(false);
    } else {
      bean.setDisplayContinue(true);
    }

    // 広告コンテンツ設定

    DataIOService ioService = ServiceLocator.getDataIOService(getLoginInfo());
    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(ContentsType.CONTENT_SITE_THANKYOU);
    ContentsPath path = DIContainer.get("contentsPath");
    ContentsListResult result = ioService.getRandomContents(condition);
    if (result.getFileName() != null) {
      bean.setAdvertiseImageUrl(path.getThanksPath() + "/" + result.getFileName());
      bean.setAdvertiseLinkUrl(ioService.getContentsData(path.getContentsSharedPath() + path.getThanksPath() + "/"
          + result.getFileName().substring(0, result.getFileName().lastIndexOf(".")) + ".txt"));
    }

    //<img src= ” http://count.chanet.com.cn/ec.cgi?t=THANKSID&i=ECINFO&u=USERID&o=GOODS/PRICE1/COUNT1/NAME1” width=1 length=1> 
    
    // 遷移履歴情報を削除
    getSessionContainer().clearBackTransitionInfoList();
    
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    OrderContainer container = orderService.getOrder(bean.getOrderNo());
    
    bean.setOrderContainer(container);
    
    setRequestBean(bean);
    // 遷移元情報設定

    if (displayEnquete) {
      EnqueteEditBean nextBean = new EnqueteEditBean();
      nextBean.setTransitionFromOrder("fromOrder");
      getSessionContainer().setTempBean(nextBean);
    }


    return FrontActionResult.RESULT_SUCCESS;
  }

  private void clearLoginInfo() {
    Customer customer = new Customer();
    customer.setCustomerCode(getBean().getOrderUserCode());
    customer.setFirstName(Messages.getString("web.action.front.order.CompleteInitAction.0"));
    FrontLoginInfo loginInfo = WebLoginManager.createFrontGuestLoginInfo(customer);
    getSessionContainer().login(loginInfo);

  }
}
