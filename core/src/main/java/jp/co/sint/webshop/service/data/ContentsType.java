package jp.co.sint.webshop.service.data;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.PermissionType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * コンテンツタイプのコード定義を表す列挙クラスです。<br>
 * 
 * @author System Integrator Corp.
 */
public enum ContentsType implements CodeAttribute {

  /** 「トップページ(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SITE_TOPPAGE("010101", "トップページ　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),

  /** 「ヘルプ(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SITE_GUIDE("010102", "ヘルプ　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),

  /** 「利用規約(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SITE_RULE("010103", "利用規約　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),

  /** 「カテゴリトップ(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SITE_CATEGORY("010104", "カテゴリトップ　【コンテンツ】", ContentsDivision.CONTENTS, Permission.CATEGORY_UPDATE, PermissionType.SITE),

  /** 「ログイン広告(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SITE_LOGIN("010105", "ログイン広告　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),

  /** 「注文完了広告(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SITE_THANKYOU("010106", "注文完了広告　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),

  /** 「アンケート完了広告(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SITE_ENQUETE("010107", "アンケート広告　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),

//  /** 「特定商取引法に基づく表記(コンテンツ:サイト管理)」を表す値です。 */
//  CONTENT_SITE_COMPLIANCE("010108", "特定商取引法に基づく表記　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
//      PermissionType.SITE),
//    add by shikui start 2010/04/23
//  /** 「個人情報保護方(コンテンツ:サイト管理)」を表す値です。 */
//  CONTENT_SITE_PRIVACY("010109", "個人情報保護方針　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
//      PermissionType.SITE),
//    add by shikui end 2010/04/23
  /** 「ショップ情報(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SHOP_SHOPINFO_SITE("010110", "サイト情報　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),

  /** 「キャンペーン(コンテンツ:サイト管理)」を表す値です。 */
  CONTENT_SHOP_CAMPAIGN_SITE("010111", "キャンペーン　【コンテンツ】", ContentsDivision.CONTENTS, Permission.CAMPAIGN_UPDATE_SITE,
      PermissionType.SITE),

  /** 「キャンペーン(コンテンツ:ショップ管理)」を表す値です。 */
  CONTENT_SHOP_CAMPAIGN_SHOP("010201", "キャンペーン　【コンテンツ】", ContentsDivision.CONTENTS, Permission.CAMPAIGN_UPDATE_SHOP,
      PermissionType.SHOP),

  /** 「ショップ情報(コンテンツ:ショップ管理)」を表す値です。 */
  CONTENT_SHOP_SHOPINFO_SHOP("010202", "ショップ情報　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SHOP,
      PermissionType.SHOP),

  /** 「特定商取引法に基づく表記(コンテンツ:ショップ管理)」を表す値です。 */
  CONTENT_SHOP_COMPLIANCE("010203", "特定商取引法に基づく表記　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SHOP,
      PermissionType.SHOP),

  /** 「商品画像(画像:ショップ管理)」を表す値です。 */
  IMAGE_DATA_SHOP_COMMODITY("020201", "商品画像　【画像】", ContentsDivision.IMAGE_DATA, Permission.COMMODITY_UPDATE, PermissionType.SHOP),

  /** 「ギフト画像(画像:ショップ管理)」を表す値です。 */
  IMAGE_DATA_SHOP_GIFT("020206", "ギフト画像　【画像】", ContentsDivision.IMAGE_DATA, Permission.COMMODITY_UPDATE, PermissionType.SHOP),

  /** 「携帯用コンテンツ(サイト管理)」をあらわす値です。 */
  CONTENT_SITE_MOBILE("030101", "携帯用コンテンツ(ヘルプ/特定商取引法に基づく表記/個人情報保護方針/利用規約)　【テキスト】", ContentsDivision.TEXT_DATA,
      Permission.SHOP_MANAGEMENT_UPDATE_SITE, PermissionType.SITE),

  /** 「携帯用コンテンツ(ショップ管理)」をあらわす値です。 */
  CONTENT_SHOP_MOBILE("030201", "携帯用コンテンツ(特定商取引法に基づく表記)　【テキスト】", ContentsDivision.TEXT_DATA,
      Permission.SHOP_MANAGEMENT_UPDATE_SHOP, PermissionType.SHOP),
  
  CONTENT_SITE_ABOUTUS("030202", "ヘルプ　【コンテンツ】", ContentsDivision.CONTENTS, Permission.SHOP_MANAGEMENT_UPDATE_SITE,
      PermissionType.SITE),
  // 20131024 txw add start
  /** 「首页TOP画像(画像:ショップ管理)」を表す値です。 */
  INDEX_TOP_IMAGE("040101", "首页TOP画像　【画像】", ContentsDivision.IMAGE_DATA, Permission.INDEX_TOP_IMAGE_UPDATE, PermissionType.SHOP),
  /** 「首页楼层画像(画像:ショップ管理)」を表す値です。 */
  INDEX_FLOOR_IMAGE("040102", "首页楼层画像　【画像】", ContentsDivision.IMAGE_DATA, Permission.INDEX_FLOOR_UPDATE, PermissionType.SHOP);
  // 20131024 txw add end

  private String code;

  private String name;

  private ContentsDivision contentsDivision;

  private Permission permission;

  private PermissionType permissionType;

  private ContentsType(String code, String name, ContentsDivision contentsDivision, Permission permission,
      PermissionType permissionType) {
    this.code = code;
    this.name = name;
    this.contentsDivision = contentsDivision;
    this.permission = permission;
    this.permissionType = permissionType;
  }

  /**
   * コード名称を返します。
   * 
   * @return コード名称
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * コード値を返します。
   * 
   * @return コード値
   */
  public String getValue() {
    return getCode();
  }

  /**
   * 指定されたコード名を持つファイル種別を返します。
   * 
   * @param name
   *          コード名
   * @return ファイル種別
   */
  public static ContentsType fromName(String name) {
    for (ContentsType p : ContentsType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つファイル種別を返します。
   * 
   * @param code
   *          コード値
   * @return ファイル種別
   */
  public static ContentsType fromValue(String code) {
    for (ContentsType p : ContentsType.values()) {
      if (p.getValue().equals(code)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param code
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String code) {
    if (StringUtil.hasValue(code)) {
      for (ContentsType p : ContentsType.values()) {
        if (p.getValue().equals(code)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * codeを取得します。
   * 
   * @return code
   */
  public String getCode() {
    return StringUtil.coalesce(CodeUtil.getCode(this), this.code);
  }

  /**
   * contentsDivisionを取得します。
   * 
   * @return contentsDivision
   */
  public ContentsDivision getContentsDivision() {
    return contentsDivision;
  }

  /**
   * permissionTypeを取得します。
   * 
   * @return permissionType
   */
  public PermissionType getPermissionType() {
    return permissionType;
  }

  /**
   * permissionを取得します。
   * 
   * @return permission
   */
  public Permission getPermission() {
    return permission;
  }

}
