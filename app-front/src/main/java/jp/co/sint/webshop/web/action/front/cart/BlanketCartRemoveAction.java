package jp.co.sint.webshop.web.action.front.cart;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.BlanketCartBean.BlanketCartDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;

/**
 * U2020310:まとめてカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BlanketCartRemoveAction extends BlanketCartBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // まとめてカートを使用しない場合は404画面へ
    super.validate();

    if (StringUtil.isNullOrEmpty(getPathInfo(0))) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.WRONG_URL_PARAMETER));
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
    String no = getPathInfo(0);
    BlanketCartDetailBean removeBean = null;

    for (BlanketCartDetailBean detail : getBean().getDetailList()) {
      if (detail == null) {
        continue;
      }
      if (detail.getNo().equals(no)) {
        removeBean = detail;
      }
    }

    if (removeBean == null) {
      getBean().setFirstDisplay(true);
      addErrorMessage(WebMessage.get(OrderErrorMessage.WRONG_URL_PARAMETER));
    } else {
      getBean().getDetailList().remove(removeBean);
      List<BlanketCartDetailBean> removedDetailList = new ArrayList<BlanketCartDetailBean>();
      for (int i = 0; i < 10; i++) {
        boolean isEmpty = true;
        for (BlanketCartDetailBean detail : getBean().getDetailList()) {
          if (detail == null) {
            continue;
          }
          if (i + 1 == Integer.parseInt(detail.getNo())) {
            removedDetailList.add(i, detail);
            isEmpty = false;

            break;
          }
        }
        if (isEmpty) {
          removedDetailList.add(null);
        }
      }
      getBean().setDetailList(removedDetailList);
    }

    getBean().setFirstDisplay(false);
    setRequestBean(getBean());
    return FrontActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

}
