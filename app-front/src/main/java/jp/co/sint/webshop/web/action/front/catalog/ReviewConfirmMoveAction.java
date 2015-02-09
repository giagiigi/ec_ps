package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewConfirmBean;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;

/**
 * U2040620:レビュー確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewConfirmMoveAction extends WebFrontAction<ReviewConfirmBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    // URLパラメータのチェック
    // parameter[0]:遷移パラメータ
    String[] params = getRequestParameter().getPathArgs();
    if (params.length > 0) {
      return true;
    }

    throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // レビュー入力画面へ戻る
    if (getParameter(0).equals("back")) {
      ReviewEditBean nextBean = new ReviewEditBean();

      nextBean.setShopCode(getBean().getShopCode());
      nextBean.setCommodityCode(getBean().getCommodityCode());
      nextBean.setCommodityName(getBean().getCommodityName());
      nextBean.setCustomerCode(getBean().getCustomerCode());
      nextBean.setNickName(getBean().getNickName());
      nextBean.setReviewScoreCondition(getBean().getReviewScore());
      nextBean.setReviewTitle(getBean().getReviewTitle());
      nextBean.setReviewDescription(getBean().getReviewDescription());
      nextBean.setCommodityDescription(getBean().getCommodityDescription());
      nextBean.setCommodityImageUrl(getBean().getCommodityImageUrl());
      nextBean.setSex(getBean().getSex());
      nextBean.setDisplayFlg(true);

      setRequestBean(nextBean);

      setNextUrl("/app/catalog/review_edit/back/" + nextBean.getShopCode() + "/" + nextBean.getCommodityCode());
    } else if (getParameter(0).equals("commodity")) {
      // 商品詳細画面へ遷移
      setNextUrl("/app/catalog/detail/init/" + getBean().getShopCode() + "/" + getBean().getCommodityCode());
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

  private String getParameter(int count) {
    String[] params = getRequestParameter().getPathArgs();
    if (params.length > count) {
      return params[count];
    }
    return "";
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

}
