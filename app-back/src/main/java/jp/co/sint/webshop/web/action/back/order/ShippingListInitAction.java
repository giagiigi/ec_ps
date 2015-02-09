package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListInitAction extends ShippingListBaseAction {

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    ShippingListBean bean = new ShippingListBean();
    //上传提示信息处理
    bean.addSubBean(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN, getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN));
    this.setBean(bean);
    super.init();
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    BackLoginInfo login = getLoginInfo();

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      if (Permission.SHIPPING_READ_SITE.isGranted(login)) {
        auth = true;
      }
    } else {
      if (Permission.SHIPPING_READ_SHOP.isGranted(login) || Permission.SHIPPING_READ_SITE.isGranted(login)) {
        auth = true;
      }
    }
    return auth;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 共通項目設定する
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());

    ShippingListBean reqBean = getBean();
    reqBean.setSearchShopList(utilService.getShopNamesDefaultAllShop(false, false));

    if (getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
      reqBean.setSearchDeliveryType(utilService.getDeliveryTypes(getLoginInfo().getShopCode(), true));
      reqBean.setDeliveryTypeDisplayFlg(true);
      // 20120116 ysy add start
      reqBean.setSearchDeliveryCompany(utilService.getDeliveryCompany(getLoginInfo().getShopCode(), true));
      reqBean.setDeliveryCompanyDisplayFlg(true);
      // 20120116 ysy add end
    } else {
      if (StringUtil.hasValue(reqBean.getSearchShopCode())) {
        reqBean.setSearchDeliveryType(utilService.getDeliveryTypes(reqBean.getSearchShopCode(), true));
        reqBean.setDeliveryTypeDisplayFlg(true);
        // 20120116 ysy add start
        reqBean.setSearchDeliveryCompany(utilService.getDeliveryCompany(getLoginInfo().getShopCode(), true));
        reqBean.setDeliveryCompanyDisplayFlg(true);
        // 20120116 ysy add end
      } else {
        reqBean.setDeliveryTypeDisplayFlg(false);
        // 20120116 ysy add start
        reqBean.setDeliveryCompanyDisplayFlg(false);
        // 20120116 ysy add end
      }

    }
    reqBean.getEdit().setUpdateWithShipping(true);

    // 出荷ステータスの「出荷可能」にチェックを入れる
    List<String> checkShippingStatus = new ArrayList<String>();
    checkShippingStatus.add(ShippingStatus.READY.getValue());
    reqBean.setSearchShippingStatus(checkShippingStatus);

    // 初期表示時は返品区分のチェックを外す
    reqBean.setSearchReturnItemType(ReturnItemType.ORDERED.getValue());

    String completeParam = "";

    if (getRequestParameter().getPathArgs().length > 0) {
      completeParam = getRequestParameter().getPathArgs()[0];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_UPLOAD)) {
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
    // add by lc 2012-03-15 start
    reqBean.setSearchFixedSalesDataFlg(true);
    // add by lc 2012-03-15 end
    setRequestBean(reqBean);

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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    ShippingListBean reqBean = (ShippingListBean) getRequestBean();
    reqBean.setAuthorityIO(false);
    BackLoginInfo login = getLoginInfo();
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      if (Permission.SHIPPING_DATA_IO_SITE.isGranted(login)) {
        reqBean.setAuthorityIO(true);
      // 10.1.3 10078 追加 ここから
        reqBean.setShippingImportDisplayFlg(true);
      } else {
        reqBean.setAuthorityIO(false);
        reqBean.setShippingImportDisplayFlg(false);
      // 10.1.3 10078 追加 ここまで
      }
    } else {
      if (Permission.SHIPPING_DATA_IO_SHOP.isGranted(login) || Permission.SHIPPING_DATA_IO_SITE.isGranted(login)) {
        reqBean.setAuthorityIO(true);
      // 10.1.3 10078 追加 ここから
        reqBean.setShippingImportDisplayFlg(true);
      } else {
        reqBean.setAuthorityIO(false);
        reqBean.setShippingImportDisplayFlg(false);
      // 10.1.3 10078 追加 ここまで
      }
    }
    if (login.isSite()) {
      reqBean.setShopListDisplayFlg(true);
    } else {
      reqBean.setShopListDisplayFlg(false);
    }
    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041005";
  }

}
