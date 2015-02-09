package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean.RelatedDetailBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040160:お気に入り商品画像のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCompleteAction extends RelatedBaseAction {

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.RelatedCompleteAction.0");

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    RelatedBean reqBean = (RelatedBean) getBean();

    // 検索条件をセット
    RelatedSearchConditionBaseEvent condition = setSearchCondition(reqBean);

    // URLパラメータで取得した関連付けの種類に応じて、インスタンス化するクラスを変更
    // タグ:tag ギフト:gift キャンペーン:campaign
    RelatedBase related = RelatedBase.createNewInstance(reqBean.getPictureMode(), getLoginInfo());
    related.setEffectualCode(reqBean.getEffectualCode());

    // URLパラメータで取得したコードに関連付いている商品の一覧を取得
    SearchResult<RelatedBaseEvent> searchResult = related.search(reqBean, condition);
    List<RelatedBaseEvent> relatedList = searchResult.getRows();

    if (relatedList != null) {
      reqBean.setPagerValue(PagerUtil.createValue(searchResult));
      reqBean.getList().clear();

      // nextBeanを作成
      setNextBean(reqBean, relatedList);
    }

    RelatedDetailBean edit = new RelatedDetailBean();
    edit.setShopCode(reqBean.getSearchShopCode());
    edit.setCommodityCode("");
    edit.setCommodityName("");
    edit.setAppliedCampaignName("");
    edit.setDisplayOrder(null);

    reqBean.setEdit(edit);
    setCompleteMessages();
    setNextUrl(null);

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 
   */
  public void setCompleteMessages() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length > 0) {
      completeParam = param[0];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, PICTURE_NAME));

    } else if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, PICTURE_NAME));

    } else if (completeParam.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, PICTURE_NAME));

    } else if (completeParam.equals(WebConstantCode.COMPLETE_UPLOAD)) {
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
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

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // 10.1.4 10035 修正 ここから
//    return Permission.COMMODITY_READ.isGranted(login) || Permission.CAMPAIGN_READ_SITE.isGranted(login)
//        || Permission.CAMPAIGN_READ_SHOP.isGranted(login);
    return Permission.CATALOG_READ.isGranted(login) || Permission.COMMODITY_READ.isGranted(login)
        || Permission.CAMPAIGN_READ_SITE.isGranted(login) || Permission.CAMPAIGN_READ_SHOP.isGranted(login);
    // 10.1.4 10035 修正 ここまで
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    RelatedBean reqBean = (RelatedBean) getBean();
    return validateBean(reqBean);
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    RelatedBean relatedBean = (RelatedBean) getRequestBean();
    setDisplayControl(relatedBean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedCompleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104016001";
  }

}
