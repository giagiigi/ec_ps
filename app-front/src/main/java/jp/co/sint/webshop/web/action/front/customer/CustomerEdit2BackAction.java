package jp.co.sint.webshop.web.action.front.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean.CustomerAttributeListAnswerBean;

/**
 * U2030220:お客様情報登録2のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit2BackAction extends CustomerEditBaseAction<CustomerEdit2Bean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (!super.validate()) {
      return false;
    }
    if (!validateConfirm()) {
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

    CustomerConfirmBean confirmBean = (CustomerConfirmBean) getSessionContainer().getTempBean();

    CustomerEdit2Bean nextBean = new CustomerEdit2Bean();
    nextBean.setCustomerCode(confirmBean.getCustomerCode());

    // 顧客属性取得
    setAttributeList(nextBean, "back");

    // 共通Beanにデータが存在する場合、入力内容を取得
    List<CustomerAttributeListAnswerBean> answerList = new ArrayList<CustomerAttributeListAnswerBean>();
    for (int i = 0; i < confirmBean.getAttributeList().size(); i++) {
      nextBean.getAttributeList().get(i).setAttributeAnswer(confirmBean.getAttributeList().get(i).getAttributeAnswer());
      CustomerAttributeListAnswerBean answerEdit = new CustomerAttributeListAnswerBean();
      for (String answer : confirmBean.getAttributeList().get(i).getAttributeAnswerList()) {
        answerEdit.setAttributeAnswerList(answer);
        answerList.add(answerEdit);
      }
      nextBean.getAttributeList().get(i).setAttributeAnswerItem(confirmBean.getAttributeList().get(i).getAttributeAnswerList());
      nextBean.getAttributeList().get(i).setAttributeAnswerList(answerList);
    }

    // 入力内容を保持
    confirmBean.setFailedTransitionFlg(true);
    getSessionContainer().setTempBean(confirmBean);

    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;

  }
}
