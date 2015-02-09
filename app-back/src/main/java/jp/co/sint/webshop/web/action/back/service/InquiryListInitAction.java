package jp.co.sint.webshop.web.action.back.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.IbObType;
import jp.co.sint.webshop.data.domain.InquiryStatus;
import jp.co.sint.webshop.data.domain.InquiryWay;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryListBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class InquiryListInitAction extends WebBackAction<InquiryListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_COMPLAINT_DATA_READ);
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
    InquiryListBean bean = new InquiryListBean();

    List<CodeAttribute> inquiryStatusList = new ArrayList<CodeAttribute>();
    inquiryStatusList.add(new NameValue(Messages.getString("web.action.back.service.InquiryListInitAction.1"), ""));
    inquiryStatusList.addAll(Arrays.asList(InquiryStatus.values()));
    bean.setInquiryStatusList(inquiryStatusList);
    bean.setSearchInquiryStatus(InquiryStatus.PROCESSING.getValue());

    List<CodeAttribute> largeCategoryList = new ArrayList<CodeAttribute>();
    largeCategoryList.add(new NameValue(Messages.getString("web.action.back.service.InquiryListInitAction.2"), ""));
    for (String largeCategory : DIContainer.getMemberInquiryConfig().getLargeCategory()) {
      largeCategoryList.add(new NameValue(largeCategory, largeCategory));
    }
    bean.setLargeCategoryList(largeCategoryList);

    bean.setCategoryArrayForJs(DIContainer.getMemberInquiryConfig().categoryArray());

    List<CodeAttribute> inquiryWayList = new ArrayList<CodeAttribute>();
    inquiryWayList.add(new NameValue(Messages.getString("web.action.back.service.InquiryListInitAction.1"), ""));
    inquiryWayList.addAll(Arrays.asList(InquiryWay.values()));
    bean.setInquiryWayList(inquiryWayList);
    
    List<CodeAttribute> ibObTypeList = new ArrayList<CodeAttribute>();
    ibObTypeList.add(new NameValue(Messages.getString("web.action.back.service.InquiryListInitAction.1"), ""));
    ibObTypeList.addAll(Arrays.asList(IbObType.values()));
    bean.setIbObTypeList(ibObTypeList);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    InquiryListBean bean = (InquiryListBean) getRequestBean();
    bean.setDisplayMemberButton(Permission.SERVICE_USER_DATA_READ.isGranted(getLoginInfo()));
    bean.setDisplayExportButton(Permission.SERVICE_COMPLAINT_DATA_EXPORT.isGranted(getLoginInfo()));
    bean.setDisplayDeleteButton(Permission.SERVICE_COMPLAINT_DATA_DELETE.isGranted(getLoginInfo()));
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryListInitAction.0");
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
