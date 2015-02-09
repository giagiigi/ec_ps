package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignListBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author OB.
 */
public class OptionalCampaignListInitAction extends WebBackAction<OptionalCampaignListBean> {

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.RelatedCompleteAction.0");

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {

    BackLoginInfo login = getLoginInfo();
    return Permission.OPTIONAL_CAMPAGIN_READ_SHOP.isGranted(login);
//    return true;
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

    OptionalCampaignListBean bean = createBean();
    setCompleteMessages();
    setNextUrl(null);
    setRequestBean(bean);
    setBean(bean);
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
      if (messageBean != null) {

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
   * 初始化画面Bean
   * 
   * @return 画面Bean
   */
  private OptionalCampaignListBean createBean() {

    OptionalCampaignListBean bean = new OptionalCampaignListBean();
    List<CodeAttribute> couponIssueTypes = new ArrayList<CodeAttribute>();

    // 优惠劵类别下拉框选项设定
    couponIssueTypes.add(new NameValue("请选择", ""));
    for (CampaignType sk : CampaignType.values()) {
      couponIssueTypes.add(sk);
    }
    bean.setCouponIssueTypes(couponIssueTypes);

    // 活动状态
    List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();
    couponActivityStatusList.add(new NameValue("全部", ""));

    for (ActivityStatus sk : ActivityStatus.values()) {
      couponActivityStatusList.add(sk);
    }
    bean.setCouponActivityStatusList(couponActivityStatusList);
    return bean;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    BackLoginInfo login = getLoginInfo();
    OptionalCampaignListBean bean = getBean();

    // 当登录用户有公共优惠券发行规则更新权限时
    if (Permission.PUBLIC_COUPON_UPDATE_SHOP.isGranted(login)) {

      bean.setRegisterNewDisplayFlg(true);
      setRequestBean(bean);
    }

    // 当登录用户有公共优惠券发行规则删除权限时
    if (Permission.PUBLIC_COUPON_DELETE_SHOP.isGranted(login)) {

      bean.setDeleteButtonDisplayFlg(true);
      setRequestBean(bean);
    }

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "公共优惠券发行规则一览画面初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106071001";
  }

}
