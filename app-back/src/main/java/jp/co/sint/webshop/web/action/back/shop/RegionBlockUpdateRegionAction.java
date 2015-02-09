package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.RegionBlockBean;
import jp.co.sint.webshop.web.bean.back.shop.RegionBlockBean.RegionBlockDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050810:地域ブロック設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RegionBlockUpdateRegionAction extends WebBackAction<RegionBlockBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    boolean authorize;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorize = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login);
    } else {
      authorize = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login);
    }
    return authorize;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    for (RegionBlockDetail detail : getBean().getRegionBlockList()) {
      if (detail.getRegionBlockId().equals(getUpdateRegionBlockId())) {
        return validateBean(detail);
      }
    }
    addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
        Messages.getString("web.action.back.shop.RegionBlockUpdateRegionAction.0"))); //$NON-NLS-1$
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    RegionBlockDetail updateDetail = null;
    for (RegionBlockDetail detail : getBean().getRegionBlockList()) {
      if (detail.getRegionBlockId().equals(getUpdateRegionBlockId())) {
        updateDetail = detail;
      }
    }

    RegionBlock updateBlock = new RegionBlock();

    updateBlock.setShopCode(getLoginInfo().getShopCode());
    updateBlock.setRegionBlockId(NumUtil.toLong(updateDetail.getRegionBlockId()));
    updateBlock.setRegionBlockName(updateDetail.getRegionBlockName());
    updateBlock.setUpdatedDatetime(updateDetail.getUpdateDatetime());

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    ServiceResult result = service.updateRegionBlockList(updateBlock);
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
          //10.1.4 10116 追加 ここから
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
          //10.1.4 10116 追加 ここまで
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/region_block/init/" + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;
  }

  private String getUpdateRegionBlockId() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.RegionBlockUpdateRegionAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105081005";
  }

}
