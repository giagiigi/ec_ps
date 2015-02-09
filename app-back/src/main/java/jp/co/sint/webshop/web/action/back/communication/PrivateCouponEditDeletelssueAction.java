package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060620:PRIVATEクーポンマスタのデータモデルです。
 * 
 * @author OB.
 */
public class PrivateCouponEditDeletelssueAction extends PrivateCouponEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo())
        && Permission.PRIVATE_COUPON_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PrivateCouponEditBean bean = getBean();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length > 0 && urlParam[0].equals("commodity")) {
      // 選択されたレビューIDを取得する
      String[] values = getRequestParameter().getAll("checkBox");
      // チェックボックスが選択されているか
      if (StringUtil.isNullOrEmpty(values[0])) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "发行关联商品"));
        setNextUrl(null);
        this.setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 削除処理実行
      for (String commodityCode : values) {
        ServiceResult serviceResult = service.deletePrivateCouponLssue(bean.getCouponCode(), commodityCode,
            WebConstantCode.COMPLETE_DELETE);
        if (serviceResult.hasError()) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR, "优惠券发行关联商品"));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      setNextUrl("/app/communication/private_coupon_edit/init/edit/" + bean.getCouponCode() + "/" + WebConstantCode.COMPLETE_DELETE);
    } else if (urlParam.length > 0 && urlParam[0].equals("brand")) {
      // 選択されたレビューIDを取得する
      String[] values = getRequestParameter().getAll("checkBox4");
      // チェックボックスが選択されているか
      if (StringUtil.isNullOrEmpty(values[0])) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "发行关联品牌"));
        setNextUrl(null);
        this.setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 削除処理実行
      for (String brandCode : values) {
        ServiceResult serviceResult = service.deletePrivateCouponLssue(bean.getCouponCode(), brandCode,
            WebConstantCode.LSSUE_BRAND_DELETE);
        if (serviceResult.hasError()) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR, "优惠券发行关联品牌"));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      setNextUrl("/app/communication/private_coupon_edit/init/edit/" + bean.getCouponCode() + "/"
          + WebConstantCode.LSSUE_BRAND_DELETE);
    } else if (urlParam.length > 0 && urlParam[0].equals("category")) {
      // 選択されたレビューIDを取得する
      String[] values = getRequestParameter().getAll("checkBox5");
      // チェックボックスが選択されているか
      if (StringUtil.isNullOrEmpty(values[0])) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, "发行关联分类"));
        setNextUrl(null);
        this.setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 削除処理実行
      for (String categoryCode : values) {
        ServiceResult serviceResult = service.deletePrivateCouponLssue(bean.getCouponCode(), categoryCode,
            WebConstantCode.LSSUE_CATEGORY_DELETE);
        if (serviceResult.hasError()) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR, "优惠券发行关联分类"));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      setNextUrl("/app/communication/private_coupon_edit/init/edit/" + bean.getCouponCode() + "/"
          + WebConstantCode.LSSUE_CATEGORY_DELETE);
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "优惠券规则_发行关联信息新建登录";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106062003";
  }

}
