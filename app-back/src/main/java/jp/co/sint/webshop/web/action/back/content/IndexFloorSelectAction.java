package jp.co.sint.webshop.web.action.back.content;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.content.IndexFloorBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1100210:首页楼层内容选择表示处理
 * 
 * @author KS.
 */
public class IndexFloorSelectAction extends IndexFloorBaseAction {

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
    String[] param = getRequestParameter().getPathArgs();
    if (param.length < 1) {
      throw new URLNotFoundException();
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
    String[] param = getRequestParameter().getPathArgs();

    IndexFloorBean bean = new IndexFloorBean();
    if (param.length == 1) {
      bean = getBean();
      bean.setLanguageCode(param[0]);
      if (bean.getLanguageCode().equals("ja-jp") || bean.getLanguageCode().equals("en-us")) {
        if (bean.getFloorCode().equals("9")) {
          bean.setFloorCode("8");
        }
      }
      setDataToBean(bean);
    } else if (param.length == 2) {
      bean = getBean();
      if ("update".equals(param[1])) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "图片信息"));
      } else if ("updateTitle".equals(param[1])) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "楼层TITLE"));
      }
      setDataToBean(bean);
    } else if (param.length == 3) {
      List<CodeAttribute> floorList = new ArrayList<CodeAttribute>();
      for (int i = 1; i < 8; i++) {
        floorList.add(new NameValue(i + "F", NumUtil.toString(i)));
      }
      bean.setFloorList(floorList);
      bean.setLanguageCode(param[0]);
      bean.setFloorCode(param[1]);
      if (param[2].equals("upload")) {
        addInformationMessage("图片已上传。");
      } else {
        throw new URLNotFoundException();
      }

      setDataToBean(bean);
    } else {
      throw new URLNotFoundException();
    }

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
    return Messages.getString("web.bean.back.content.IndexFloorSelectAction.0");
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
