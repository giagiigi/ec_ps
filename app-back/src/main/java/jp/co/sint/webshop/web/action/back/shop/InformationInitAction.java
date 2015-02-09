package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.InformationType;
import jp.co.sint.webshop.data.domain.PrimaryFlg;
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.InformationBean;
import jp.co.sint.webshop.web.bean.back.shop.InformationBean.InformationDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051210:お知らせ編集のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationInitAction extends WebBackAction<InformationBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
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
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    List<Information> informationList = service.getInformationList();
    List<InformationDetail> informationDetailList = new ArrayList<InformationDetail>();

    for (Information information : informationList) {
      InformationDetail informationDetail = new InformationDetail();
      informationDetail.setInformationNo(information.getInformationNo().toString());
      Date startDate = DateUtil.getMin();
      Date endDate = DateUtil.getMin();
      if (information.getInformationStartDatetime() != null) {
        startDate = information.getInformationStartDatetime();
      }
      if (information.getInformationEndDatetime() != null) {
        endDate = information.getInformationEndDatetime();
      }
      informationDetail.setStartDatetime(DateUtil.toDateTimeString(startDate, "yyyy/MM/dd HH"));
      informationDetail.setEndDatetime(DateUtil.toDateTimeString(endDate, "yyyy/MM/dd HH"));
      informationDetail.setDisplayClientType(information.getDisplayClientType().toString());
      informationDetail.setType(information.getInformationType().toString());
      //soukai add 2011/12/19 ob start
      informationDetail.setPrimaryFlg(information.getPrimaryFlg());
      informationDetail.setInformationUrl(information.getInformationUrl());
      informationDetail.setInformationUrlEn(information.getInformationUrlEn());
      informationDetail.setInformationUrlJp(information.getInformationUrlJp());
      //soukai add 2011/12/19 ob end
      informationDetail.setContent(information.getInformationContent());
      //20120514 tuxinwei add start
      informationDetail.setContentEn(information.getInformationContentEn());
      informationDetail.setContentJp(information.getInformationContentJp());
      //20120514 tuxinwei add end
      informationDetail.setUpdateDatetime(information.getUpdatedDatetime());
      informationDetailList.add(informationDetail);
    }

    InformationBean bean = new InformationBean();
    bean.setListInformation(informationDetailList);
    InformationDetail detailBean = new InformationDetail();
    detailBean.setDisplayClientType(DisplayClientType.ALL.getValue());
    detailBean.setType(InformationType.SYSTEM.getValue());
    //soukai add 2011/12/19 ob start
    detailBean.setPrimaryFlg(PrimaryFlg.ORDINARY.longValue());
    //soukai add 2011/12/19 ob end
    bean.setRegisterInformation(detailBean);
    setRequestBean(bean);

    String parameter = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }
    if (parameter.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.InformationInitAction.0")));
    }
    if (parameter.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
          Messages.getString("web.action.back.shop.InformationInitAction.0")));
    }
    if (parameter.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.shop.InformationInitAction.0")));
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    InformationBean requestBean = (InformationBean) getRequestBean();
    requestBean.setDeleteButtonFlg(false);
    requestBean.setButtonFlg(false);
    requestBean.setRegisterPartDisplayFlg(false);
    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo())) {
      requestBean.setDeleteButtonFlg(true);
    }
    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      requestBean.setButtonFlg(true);
      requestBean.setRegisterPartDisplayFlg(true);
    }

    setRequestBean(requestBean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.InformationInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105121002";
  }

}
