package jp.co.sint.webshop.web.action.back.data;

import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.data.DataImportBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080210:データ一括取込のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DataImportInitAction extends WebBackAction<DataImportBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean result = false;
    BackLoginInfo login = getLoginInfo();

    // データ入出力アクセス_サイトを持つユーザは常にアクセス可能
    if (Permission.DATA_IO_ACCESS_SITE.isGranted(login) || Permission.DATA_IO_ACCESS_SHOP.isGranted(login)) {
      result = true;
    }

    // 取り込み可能なものが1つもない場合はfalseにする
    DataImportBean bean = new DataImportBean();
    List<CodeAttribute> importableList = bean.getImportDataTypeList(getLoginInfo(), getConfig());

    result &= importableList != null && importableList.size() > 0;

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    DataImportBean bean = new DataImportBean();

    // 取込データ種別のリスト取得
    bean.setImportDataTypeList(bean.getImportDataTypeList(getLoginInfo(), getConfig()));
    // ヘッダ有無選択肢の作成
    bean.setCsvHeaderList(bean.createHeaderTypeList());

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
    return Messages.getString("web.action.back.data.DataImportInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108021002";
  }

}
