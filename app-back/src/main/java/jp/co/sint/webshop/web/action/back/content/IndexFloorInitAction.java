package jp.co.sint.webshop.web.action.back.content;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.content.IndexFloorBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1100210:首页楼层内容初始表示处理
 * 
 * @author KS.
 */
public class IndexFloorInitAction extends IndexFloorBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.INDEX_FLOOR_READ.isGranted(getLoginInfo());
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

    IndexFloorBean bean = new IndexFloorBean();
    String languageCode = bean.getLanguageCode();

    List<CodeAttribute> floorList = new ArrayList<CodeAttribute>();

    if (StringUtil.isNullOrEmpty(languageCode) || languageCode.equals("zh-cn")) {
      for (int i = 1; i < 10; i++) {
        floorList.add(new NameValue(i + "F", NumUtil.toString(i)));
      }
    } else {
      for (int i = 1; i < 9; i++) {
        floorList.add(new NameValue(i + "F", NumUtil.toString(i)));
      }
    }

    bean.setFloorList(floorList);
    bean.setFloorCode(floorList.get(0).getValue());
    bean.setLanguageCode(LanguageCode.Zh_Cn.getValue());

    setDataToBean(bean);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定?初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    IndexFloorBean bean = (IndexFloorBean) getRequestBean();

    // 更新権限チェック
    if (Permission.INDEX_FLOOR_UPDATE.isGranted(login)) {
      bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.content.IndexFloorInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109021001";
  }
}
