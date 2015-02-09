package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.data.dto.RegionBlockLocation;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.RegionBlockBean;
import jp.co.sint.webshop.web.bean.back.shop.RegionBlockBean.PrefectureDetail;
import jp.co.sint.webshop.web.bean.back.shop.RegionBlockBean.RegionBlockDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050810:地域ブロック設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RegionBlockInitAction extends WebBackAction<RegionBlockBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo());
    }
    return authorization;
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

    RegionBlockBean reqBean = new RegionBlockBean();

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    // ===地域情報取得===========================
    List<RegionBlockLocation> regionBlockLocationList = service.getRegionBlockLocationList(getLoginInfo().getShopCode());
    List<PrefectureDetail> prefDetailList = new ArrayList<PrefectureDetail>();
    List<String> usingRegionBlockId = new ArrayList<String>();

    if (regionBlockLocationList.size() == PrefectureCode.values().length) {
      for (int i = 0; i < 31; i++) {
        int count = i;
        RegionBlockLocation region = regionBlockLocationList.get(count);
        PrefectureDetail detail = new PrefectureDetail();
        detail.setPrefectureCode(region.getPrefectureCode());
        detail.setPrefecturName(PrefectureCode.fromValue(region.getPrefectureCode()).getName());
        detail.setRegionBlockId(NumUtil.toString(region.getRegionBlockId()));
        detail.setUpdateDatetime(region.getUpdatedDatetime());

        usingRegionBlockId.add(detail.getRegionBlockId());

        prefDetailList.add(detail);
      }
    } else {
      for (int i = 0; i < 16; i++) {
        for (int j = 0; j < 3; j++) {
          int count = i + j * 16;
          if (count > 31) {
            break;
          }
          PrefectureCode code = PrefectureCode.values()[count];
          PrefectureDetail detail = new PrefectureDetail();
          detail.setPrefectureCode(code.getValue());
          detail.setPrefecturName(code.getName());
          detail.setRegionBlockId("");
          detail.setUpdateDatetime(null);

          prefDetailList.add(detail);
        }
      }
    }

    reqBean.setPrefectureList(prefDetailList);

    // ==地域ブロック情報取得===========================
    Shop shop = new Shop();
    shop.setShopCode(getLoginInfo().getShopCode());
    List<RegionBlock> regionBlockList = service.getRegionBlockList(shop);

    List<RegionBlockDetail> detailList = new ArrayList<RegionBlockDetail>();
    List<CodeAttribute> regionBlockNameList = new ArrayList<CodeAttribute>();
    regionBlockNameList.add(new NameValue(Messages.getString(
      "web.action.back.shop.RegionBlockInitAction.0"), ""));
    for (RegionBlock block : regionBlockList) {
      RegionBlockDetail detail = new RegionBlockDetail();
      detail.setRegionBlockId(NumUtil.toString(block.getRegionBlockId()));
      detail.setRegionBlockName(block.getRegionBlockName());
      detail.setDaletableFlg(!usingRegionBlockId.contains(detail.getRegionBlockId()));
      detail.setUpdateDatetime(block.getUpdatedDatetime());

      detailList.add(detail);

      NameValue regionBlockName = new NameValue(block.getRegionBlockName(), Long.toString(block.getRegionBlockId()));
      regionBlockNameList.add(regionBlockName);
    }
    reqBean.setRegionBlockList(detailList);
    reqBean.setRegionBlockNameList(regionBlockNameList);

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    String[] pathInfoParams = getRequestParameter().getPathArgs();
    String complete = "";
    if (pathInfoParams.length > 0) {
      complete = pathInfoParams[0];
    }

    if (complete.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.back.shop.RegionBlockInitAction.1")));
    } else if (complete.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.shop.RegionBlockInitAction.1")));
    } else if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.shop.RegionBlockInitAction.1")));
    } else if (complete.equals(WebConstantCode.COMPLETE_REGISTER)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.shop.RegionBlockInitAction.2")));
    }

    boolean displayUpdateButtonFlg = false;

    boolean displayDeleteButtonFlg = false;

    RegionBlockBean bean = (RegionBlockBean) getRequestBean();

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      displayUpdateButtonFlg = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
      displayDeleteButtonFlg = Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo());
    } else {
      displayUpdateButtonFlg = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
      displayDeleteButtonFlg = Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo());
    }

    bean.setDisplayUpdateButtonFlg(displayUpdateButtonFlg);

    bean.setDisplayDeleteButtonFlg(displayDeleteButtonFlg);

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.RegionBlockInitAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105081002";
  }

}
