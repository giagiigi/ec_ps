package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.TaxBean;
import jp.co.sint.webshop.web.bean.back.shop.TaxBean.TaxDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050410:消費税マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TaxInitAction extends WebBackAction<TaxBean> {

  private boolean displayRegisterPartFlg = false;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    // 権限のチェック
    BackLoginInfo loginInfo = getLoginInfo();
    return Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(loginInfo);
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    BackLoginInfo login = getLoginInfo();

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    List<Tax> taxList = service.getTaxList();
    TaxBean taxBean = new TaxBean();

    // 取得したデータを受け取る変数を作成
    List<TaxDetail> taxDetailList = new ArrayList<TaxDetail>();

    // 取得した消費税マスタのデータ数分、値を設定
    for (Tax tax : taxList) {
      TaxDetail detail = new TaxDetail();
      detail.setAppliedStartDate(DateUtil.toDateString(tax.getAppliedStartDate()));
      detail.setTaxNo(Long.toString(tax.getTaxNo()));
      detail.setTaxRate(Long.toString(tax.getTaxRate()));
      detail.setDisplayDeleteButtonFlg(true);
      if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isDenied(login) || tax.getTaxNo().equals(0L)) {
        detail.setDisplayDeleteButtonFlg(false);
      }
      taxDetailList.add(detail);
    }
    taxBean.setTaxList(taxDetailList);
    taxBean.setDisplayRegisterPartFlg(displayRegisterPartFlg);

    // 登録部分の初期値を設定
    TaxDetail taxDetail = new TaxDetail();
    taxDetail.setAppliedStartDate(DateUtil.getSysdateString());
    taxDetail.setTaxRate("");
    taxBean.setTaxRegister(taxDetail);

    setRequestBean(taxBean);
    setCompleteMessage();

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    BackLoginInfo loginInfo = getLoginInfo();
    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(loginInfo)) {
      displayRegisterPartFlg = true;
    } else if (Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(loginInfo)) {
      displayRegisterPartFlg = false;
    }

    TaxBean nextBean = (TaxBean) getRequestBean();
    nextBean.setDisplayRegisterPartFlg(displayRegisterPartFlg);
    setRequestBean(nextBean);
  }

  /**
   * パラメータに処理完了情報が存在した場合、処理完了メッセージをセット
   */
  private void setCompleteMessage() {
    String completeMessage = "";

    if (getRequestParameter().getPathArgs().length > 0) {
      // 配列の[1]には"insert"又は"delete"が入る
      completeMessage = getRequestParameter().getPathArgs()[0];
    }

    if (completeMessage.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.TaxInitAction.0")));
    } else if (completeMessage.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.shop.TaxInitAction.0")));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.TaxInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105041002";
  }

}
