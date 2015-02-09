package jp.co.sint.webshop.web.action.back.data;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.PermissionType;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.service.communication.CampaignListSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.data.FileUploadBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1080310:ファイルアップロードのアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class FileUploadBaseAction extends WebBackAction<FileUploadBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    return null;
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
   * コンテンツタイプのリストを取得します。
   * 
   * @param login
   * @return コンテンツタイプのリスト
   */
  public List<CodeAttribute> getContentsTypeList(BackLoginInfo login) {

    List<NameValue> list = new ArrayList<NameValue>();
    list.add(new NameValue(Messages.getString(
      "web.action.back.data.FileUploadBaseAction.0"), "000000"));

    for (ContentsType contentType : ContentsType.values()) {
      // ショップ管理更新権限を持つ場合
      if (PermissionType.SITE.equals(contentType.getPermissionType())
          && Permission.SHOP_MANAGEMENT_UPDATE_SITE.equals(contentType.getPermission())
          && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login)) {
        list.add(new NameValue(contentType.getName(), contentType.getValue()));
      }

      // カテゴリ管理更新権限を持つ場合
      if (PermissionType.SITE.equals(contentType.getPermissionType())
          && Permission.CATEGORY_UPDATE.equals(contentType.getPermission()) && Permission.CATEGORY_UPDATE.isGranted(login)) {
        list.add(new NameValue(contentType.getName(), contentType.getValue()));
      }

      // 商品管理更新権限を持つ場合
      if (PermissionType.SHOP.equals(contentType.getPermissionType())
          && Permission.COMMODITY_UPDATE.equals(contentType.getPermission()) && Permission.COMMODITY_UPDATE.isGranted(login)) {
        list.add(new NameValue(contentType.getName(), contentType.getValue()));
      }

      // キャンペーン管理更新権限を持つ場合
      if (PermissionType.SHOP.equals(contentType.getPermissionType())
          && Permission.CAMPAIGN_UPDATE_SHOP.equals(contentType.getPermission())
          && Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)) {
        list.add(new NameValue(contentType.getName(), contentType.getValue()));
      }
      if (PermissionType.SITE.equals(contentType.getPermissionType())
          && Permission.CAMPAIGN_UPDATE_SITE.equals(contentType.getPermission())
          && Permission.CAMPAIGN_UPDATE_SITE.isGranted(login)) {
        list.add(new NameValue(contentType.getName(), contentType.getValue()));
      }

      // ショップ管理更新権限を持つ場合
      if (PermissionType.SHOP.equals(contentType.getPermissionType())
          && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.equals(contentType.getPermission())
          && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login)) {
        list.add(new NameValue(contentType.getName(), contentType.getValue()));
      }
    }

    return new ArrayList<CodeAttribute>(list);

  }

  /**
   * カテゴリツリーのリストを取得します。
   * 
   * @return カテゴリツリーのリスト
   */
  public List<CodeAttribute> getCategoryTopList() {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CategoryInfo> category = service.getAllCategory();

    List<CodeAttribute> list = new ArrayList<CodeAttribute>();
    for (CategoryInfo c : category) {
      list.add(new NameValue(c.getCategoryNamePc(), c.getCategoryCode()));
    }

    return new ArrayList<CodeAttribute>(list);
  }

  /**
   * キャンペーンのリストを取得します。
   * 
   * @param shopCode
   * @return キャンペーンのリスト
   */
  public List<CodeAttribute> getCampaignList(String shopCode) {
    CampaignListSearchCondition condition = new CampaignListSearchCondition();
    condition.setShopCode(shopCode);

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<CampaignHeadLine> campaign = service.getCampaignList(condition);

    List<CodeAttribute> list = new ArrayList<CodeAttribute>();
    for (CampaignHeadLine c : campaign.getRows()) {
      list.add(new NameValue(c.getCampaignName(), c.getCampaignCode()));
    }

    return list;
  }

}
