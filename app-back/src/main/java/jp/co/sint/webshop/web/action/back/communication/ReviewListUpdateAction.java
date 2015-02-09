package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.domain.ReviewDisplayType;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.ReviewListBean;
import jp.co.sint.webshop.web.bean.back.communication.ReviewListBean.ReviewListBeanDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060210:レビュー管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewListUpdateAction extends ReviewListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.REVIEW_UPDATE_SHOP.isGranted(getLoginInfo()) || Permission.REVIEW_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    ReviewListBean nextBean = getBean();

    // 画面で選択されたレビューIDを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // チェックボックスが一つ以上選択されているか
    //10.1.4 K00168 修正 ここから
    // if (values[0] == "") {
    if (StringUtil.isNullOrEmpty(values[0])) {
    // 10.1.4 K00168 修正 ここまで
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.communication.ReviewListUpdateAction.0")));
      this.setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    List<ReviewPost> reviewList = new ArrayList<ReviewPost>();

    for (String reviewId : values) {
      // 存在チェック
      ReviewPost review = service.getReviewPost(reviewId);
      if (review == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
        return super.callService();
      }

      // UpdateDatetimeの設定
      Date updateDatetime = null;
      for (ReviewListBeanDetail rd : nextBean.getList()) {
        if (rd.getReviewId().equals(reviewId)) {
          updateDatetime = rd.getUpdatedDatetime();
          break;
        }
      }
      review.setUpdatedDatetime(updateDatetime);
      reviewList.add(review); // 更新対象のレビューをリストに加える
    }

    // 更新処理実行
    String[] urlParam = getRequestParameter().getPathArgs();
    long updateType;
    if (urlParam[0] != null && urlParam[0].equals("show")) {
      updateType = ReviewDisplayType.DISPLAY.longValue();
    } else if (urlParam[0] != null && urlParam[0].equals("hide")) {
      updateType = ReviewDisplayType.HIDDEN.longValue();
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return BackActionResult.SERVICE_ERROR;
    }
    ServiceResult updateResult = service.updateReviewPost(reviewList, updateType);

    // エラー処理
    if (updateResult.hasError()) {
      for (ServiceErrorContent error : updateResult.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (error.equals(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR)) {
          Length len = BeanUtil.getAnnotation(Customer.class, "restPoint", Length.class);
          String maximum = StringUtil.times("9", len.value());
          addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_OVERFLOW, maximum));
          return super.callService();
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }
    addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
        Messages.getString("web.action.back.communication.ReviewListUpdateAction.0")));

    this.setRequestBean(nextBean);

    // 更新後の結果を再検索する
    return super.callService();
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
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

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.ReviewListUpdateAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106021006";
  }

}
