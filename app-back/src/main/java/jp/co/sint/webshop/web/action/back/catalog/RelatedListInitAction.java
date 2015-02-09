package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseCommodity;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

import org.apache.log4j.Logger;

/**
 * U1040170:一括関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedListInitAction extends WebBackAction<RelatedListBean> {

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
    RelatedListBean reqBean = getBean();

    boolean isValid = true;

    isValid &= validateBean(reqBean);

    if (StringUtil.hasValueAllOf(reqBean.getSearchEffectualCodeStart(), reqBean.getSearchEffectualCodeEnd())) {
      if (!ValidatorUtil.isCorrectOrder(reqBean.getSearchEffectualCodeStart(), reqBean.getSearchEffectualCodeEnd())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.back.catalog.RelatedListInitAction.0")));
        isValid = false;
      }
    }

    return isValid;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());

    RelatedListBean relatedList = getBean();

    if (getLoginInfo().isShop()) {
      relatedList.setSearchShopCode(getLoginInfo().getShopCode());
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = service.getCommodityHeader(relatedList.getSearchShopCode(), relatedList.getCommodityCode());

    // 商品情報を取得
    if (commodityHeader == null) {
      addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR));
      logger.debug(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    } else {
      // 商品が存在する場合は商品名を取得する
      relatedList.setCommodityName(commodityHeader.getCommodityName());
    }

    // 検索条件を設定する
    RelatedSearchConditionBaseCommodity condition = new RelatedSearchConditionBaseCommodity();
    condition.setShopCode(relatedList.getSearchShopCode());
    condition.setCommodityCode(relatedList.getCommodityCode());
    condition.setSearchEffectualCodeStart(relatedList.getSearchEffectualCodeStart());
    condition.setSearchEffectualCodeEnd(relatedList.getSearchEffectualCodeEnd());
    condition.setSearchStartDateFrom(relatedList.getSearchStartDateTime());
    condition.setSearchStartDateTo(relatedList.getSearchStartDateTime());
    condition.setSearchEndDateFrom(relatedList.getSearchEndDateTime());
    condition.setSearchEndDateTo(relatedList.getSearchEndDateTime());
    condition.setSearchEffectualName(relatedList.getSearchEffectualName());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // pictureModeの種類に応じて、インスタンス化するクラスを変更する
    // タグ:tag_list ギフト:gift_list キャンペーン:campaign_list
    RelatedListBase relatedListBase = RelatedListBase.createNewInstance(relatedList.getPictureMode(), getLoginInfo());

    // 一覧を取得する
    relatedList = relatedListBase.search(relatedList, condition);

    relatedList.setShopName(getShopName());

    setNextUrl(null);
    setRequestBean(relatedList);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * @return shopName
   */
  public String getShopName() {
    String shopName = "";
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    List<CodeAttribute> shopList = utilService.getShopNames(false);

    if (getLoginInfo().isShop()) {
      shopName = getLoginInfo().getShopName();

    } else {
      for (CodeAttribute cb : shopList) {
        if (cb.getValue().equals(getBean().getSearchShopCode())) {
          shopName = cb.getName();
          break;
        }
      }
    }
    return shopName;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    RelatedListBean relatedBean = (RelatedListBean) getRequestBean();

    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      relatedBean.setRegisterTableDisplayFlg(true);
    } else {
      relatedBean.setRegisterTableDisplayFlg(false);
    }

    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length > 0) {
      completeParam = param[0];
    }

    if (completeParam.equals("register")) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
          Messages.getString("web.action.back.catalog.RelatedListInitAction.1")));

    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedListInitAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104017001";
  }

}
