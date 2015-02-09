package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class InquiryListDeleteAction extends WebBackAction<InquiryListBean> {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_COMPLAINT_DATA_DELETE);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // チェックボックスが選択されているか
    if (!StringUtil.hasValueAllOf(ArrayUtil.toArray(getBean().getCheckedInquiryNoList(), String.class))) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, Messages
          .getString("web.action.back.service.InquiryListDeleteAction.1")));
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

    InquiryListBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // データベース更新処理
    ServiceResult customerResult = service.deleteInquiry(bean.getCheckedInquiryNoList());

    // 情報更新時エラー
    if (customerResult.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/service/inquiry_list/search/" + WebConstantCode.COMPLETE_DELETE);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryListDeleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109021003";
  }
}
