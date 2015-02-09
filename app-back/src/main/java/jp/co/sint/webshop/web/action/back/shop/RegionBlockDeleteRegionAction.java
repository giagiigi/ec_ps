package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.DigitValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.RegionBlockBean;
import jp.co.sint.webshop.web.bean.back.shop.RegionBlockBean.RegionBlockDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050810:地域ブロック設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RegionBlockDeleteRegionAction extends WebBackAction<RegionBlockBean> {

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
      authorize = Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(login);
    } else {
      authorize = Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(login);
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
    String regionBlockId = getDeleteRegionBlockId();
    DigitValidator v = new DigitValidator();
    if (StringUtil.hasValue(regionBlockId) && v.isValid(regionBlockId)) {
      for (RegionBlockDetail detail : getBean().getRegionBlockList()) {
        if (detail.getRegionBlockId().equals(getDeleteRegionBlockId())) {
          return true;
        }
      }
    }

    addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
        Messages.getString("web.action.back.shop.RegionBlockDeleteRegionAction.0")));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    ServiceResult result = service.deleteRegionBlockList(getLoginInfo().getShopCode(), NumUtil.toLong(getDeleteRegionBlockId()));
    if (result.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/region_block/init/" + WebConstantCode.COMPLETE_DELETE);

    return BackActionResult.RESULT_SUCCESS;
  }

  private String getDeleteRegionBlockId() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    }
    return "";
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.RegionBlockDeleteRegionAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105081001";
  }

}
