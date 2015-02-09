package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewConfirmBean;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewEditBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

// import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
// import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U2040620:レビュー確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewEditConfirmAction extends WebFrontAction<ReviewEditBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean beanFlg = true;
    if (StringUtil.hasValue(getBean().getReviewTitle()) && getBean().getReviewTitle().length() < 5) {
      addErrorMessage(Messages.getString("web.action.front.catalog.ReviewEditConfirmAction.0"));
      beanFlg &= false;
    }
    if (StringUtil.hasValue(getBean().getReviewDescription()) && getBean().getReviewDescription().length() < 5) {
      addErrorMessage(Messages.getString("web.action.front.catalog.ReviewEditConfirmAction.1"));
      beanFlg &= false;
    }
    beanFlg &= validateBean(getBean());
    return beanFlg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ReviewConfirmBean nextBean = new ReviewConfirmBean();
    nextBean.setShopCode(getBean().getShopCode());
    nextBean.setCommodityCode(getBean().getCommodityCode());
    nextBean.setCommodityName(getBean().getCommodityName());
    nextBean.setCommodityDescription(getBean().getCommodityDescription());
    nextBean.setCustomerCode(getBean().getCustomerCode());
    nextBean.setNickName(getBean().getNickName());
    nextBean.setReviewScore(getBean().getReviewScoreCondition());
    nextBean.setReviewTitle(getBean().getReviewTitle());
    nextBean.setReviewDescription(getBean().getReviewDescription());
    nextBean.setOrderNo(getBean().getOrderNo());
    setRequestBean(nextBean);

    setNextUrl("/app/catalog/review_confirm");
    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/catalog/review_edit/init/" + getBean().getCommodityCode(), getSessionContainer());

    return FrontActionResult.RESULT_SUCCESS;
  }

}
