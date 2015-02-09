package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCsvImportBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;


/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCsvImportInitAction extends WebBackAction<CommodityCsvImportBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
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

    CommodityCsvImportBean bean = new CommodityCsvImportBean();
    bean.setSearchImportObject("import-c_commodity_header");
    
    // 20130702 shen add start
    List<CodeAttribute> syncFlagList = new ArrayList<CodeAttribute>();
    syncFlagList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityCsvImportInitAction.2"), "0"));
    syncFlagList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityCsvImportInitAction.3"), "1"));
    bean.setSyncFlagList(syncFlagList);
    bean.setSyncFlagTmall("0");
    // 20130702 shen add end
    
    // 2014/04/28 京东WBS对应 ob_卢 add start
    bean.setSyncFlagJd(SyncFlagJd.SYNCHIDDEN.getValue());
    // 2014/04/28 京东WBS对应 ob_卢 add end

    // 画面表示用Beanを次画面Beanを設定
    setRequestBean(bean);
    
    if (Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo())) {
      bean.setDisplayCsvImportButton(true);
    }else {
      bean.setDisplayCsvImportButton(false);
    }
    
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 1) {
      completeParam = param[0];
    }
    if (completeParam.equals(WebConstantCode.COMPLETE_UPLOAD)) {
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      // 20130703 shen add start
      if (messageBean != null) {
        // 20130703 shen add end
        List<UploadResult> resultList = messageBean.getUploadDetailList();
    
        if (messageBean.getResult().equals(ResultType.SUCCESS)) {
          addInformationMessage(WebMessage.get(CompleteMessage.CSV_IMPORT_COMPLETE));
        } else if (messageBean.getResult().equals(ResultType.FAILED)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_FAILED));
        } else {
          addWarningMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_PARTIAL, ""));
        }
    
        for (UploadResult ur : resultList) {
    
          for (String s : ur.getInformationMessage()) {
            addInformationMessage(s);
          }
          for (String s : ur.getWarningMessage()) {
            addWarningMessage(s);
          }
          for (String s : ur.getErrorMessage()) {
            addErrorMessage(s);
          }
        }
      }
    }
  }
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCsvImportInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3180004001";
  }

}
