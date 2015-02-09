package jp.co.sint.webshop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.CCommodityCynchro;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CSynchistory;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CandidateWord;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.data.dto.CategorySel;
import jp.co.sint.webshop.data.dto.CommodityDescribe;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.data.dto.CommoditySku;
import jp.co.sint.webshop.data.dto.CustomerCommodity;
import jp.co.sint.webshop.data.dto.CyncroResult;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.data.dto.JdCategory;
import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.RelatedBrand;
import jp.co.sint.webshop.data.dto.RelatedCommodityA;
import jp.co.sint.webshop.data.dto.RelatedSiblingCategory;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.data.dto.SearchKeywordLog;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.data.dto.TmallBrand;
import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.data.dto.TmallProperty;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.data.dto.UntrueOrderWord;
import jp.co.sint.webshop.service.CategoryViewUtil.PropertyKeys;
import jp.co.sint.webshop.service.campain.CampaignMain;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSearchCondition;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSubscritionCount;
import jp.co.sint.webshop.service.catalog.AttributeData;
import jp.co.sint.webshop.service.catalog.BrandData;
import jp.co.sint.webshop.service.catalog.BrandSearchCondition;
import jp.co.sint.webshop.service.catalog.CategoryAttributeHeader;
import jp.co.sint.webshop.service.catalog.CategoryData;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.CommodityHistorySearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityImageSearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityMasterEditInfo;
import jp.co.sint.webshop.service.catalog.CommodityMasterResult;
import jp.co.sint.webshop.service.catalog.CommodityMasterSearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryCondition;
import jp.co.sint.webshop.service.catalog.GiftCount;
import jp.co.sint.webshop.service.catalog.JdStockInfo;
import jp.co.sint.webshop.service.catalog.LeftMenuListBean;
import jp.co.sint.webshop.service.catalog.PlanInfo;
import jp.co.sint.webshop.service.catalog.RankingSearchResult;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedCampaign;
import jp.co.sint.webshop.service.catalog.RelatedCategory;
import jp.co.sint.webshop.service.catalog.RelatedCategorySearchCondition;
import jp.co.sint.webshop.service.catalog.RelatedGift;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseCommodity;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedTag;
import jp.co.sint.webshop.service.catalog.ResultBean;
import jp.co.sint.webshop.service.catalog.ReviewData;
import jp.co.sint.webshop.service.catalog.SalesChartsData;
import jp.co.sint.webshop.service.catalog.SetCommodityInfo;
import jp.co.sint.webshop.service.catalog.StockIOSearchCondition;
import jp.co.sint.webshop.service.catalog.StockListSearchCondition;
import jp.co.sint.webshop.service.catalog.StockListSearchInfo;
import jp.co.sint.webshop.service.catalog.StockStatusCount;
import jp.co.sint.webshop.service.catalog.StockWarnInfo;
import jp.co.sint.webshop.service.catalog.TagCount;
import jp.co.sint.webshop.service.catalog.TagSearchCondition;
import jp.co.sint.webshop.service.catalog.TmallStockInfo;
import jp.co.sint.webshop.service.customer.CustomerGroupCount;
import jp.co.sint.webshop.service.order.UntrueOrderWordResult;
import jp.co.sint.webshop.service.order.UntrueOrderWordSearchCondition;
import jp.co.sint.webshop.utility.Sku;

/**
 * SI Web Shopping 10 カタログサービス(CatalogService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface CatalogService {

  /**
   * 手動リコメンドへ商品の関連付け情報を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った手動リコメンド情報内の商品コードとリンク先商品コードを相互に関連付けます。<BR>
   * <dd>関連付けの登録は順方向(商品コード→リンク先商品コード)と逆方向(リンク先商品コード→商品コード)の両方を行います。
   * <ol>
   * <li>順方向関連付けDTOに対してバリデーションチェックを行います。</li>
   * <li>逆方向関連付けDTOに対してバリデーションチェックを行います。</li>
   * <li>商品コードの存在チェックを行います。</li>
   * <li>トランザクションを開始します。</li>
   * <li>順方向の関連付け情報を登録します。すでに関連付け情報が存在する場合は、正常終了したものとして登録処理をスキップします。</li>
   * <li>逆方向の関連付け情報を登録します。すでに関連付け情報が存在する場合は、正常終了したものとして登録処理をスキップします。</li>
   * <li>トランザクションを終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>手動リコメンド情報がnullでないこと。</dd>
   * <dd>手動リコメンド情報内のショップコードがnullでないこと。</dd>
   * <dd>手動リコメンド情報内の商品コードがnullでないこと。</dd>
   * <dd>手動リコメンド情報内のリンク商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>手動リコメンドへ手動リコメンド情報を登録し、トランザクションをコミットします。</dd>
   * <dd>トランザクション開始以後に例外が発生した場合は、トランザクションをロールバックします。</dd>
   * <dd>手動リコメンドに登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param relatedCommodityA
   *          手動リコメンド情報
   * @return サービス処理結果を返します。
   */
  ServiceResult insertRelatedCommodityA(RelatedCommodityA relatedCommodityA);

  // add by tangweihui 2012-11-16 start
  ServiceResult updateSetCommodityFlg(String shopCode, String setCommodityFlg, String commodityCode);

  // add by tangweihui 2012-11-16 end
  /**
   * 購入されたことのある商品を合計金額の高い順に取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>購入されたことのある商品を合計金額の高い順に取得します。
   * <ol>
   * <li>引数で指定したショップで、注文されたことのある商品を購入金額の高い順に返します。</li>
   * <li>引数で指定したショップコードがサイトのショップコードの場合は、サイト全体で購入金額の高い順に返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 生成した商品のランキング情報を返します。
   */
  List<RankingSearchResult> getRankingSummaryByOrder(String shopCode);

  /**
   * 購入される頻度の高い商品のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>購入される頻度の高い商品のリストを取得します。
   * <ol>
   * <li>引数で指定したショップで、購入される頻度の高い順に商品を返します。</li>
   * <li>引数で指定したショップコードがサイトのショップコードの場合は、サイト全体で購入される頻度の高い商品の順を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 生成した商品のランキング情報を返します。
   */
  List<RankingSearchResult> getRankingSummaryByCount(String shopCode);

  /**
   * 指定した商品の全SKUの中で、在庫が1つでも存在するSKUがあるかどうかを判定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品の全SKUの中で、在庫が1つでも存在するSKUがあるかどうかを判定します。
   * <ol>
   * <li>受け取ったショップコードと商品コードを元に商品の存在チェックを行います。</li>
   * <li>商品が存在しない場合はfalseを返します。</li>
   * <li>商品が予約期間で、商品の在庫管理区分に「2(在庫数表示する)」または「3(在庫状況表示する)」が設定されている商品は、
   * SKUコードごとに「予約上限数 - 予約数」で有効在庫を取得します。
   * 取得した有効在庫のいずれかでも0より大きい場合は、trueを返します。予約上限数がNullの場合はTrueを返します。</li>
   * <li>
   * 商品が予約以外の期間で、商品の在庫管理区分に「2(在庫数表示する)」または「3(在庫状況表示する)」が設定されている商品は、SKUコードごとに「在庫数
   * - 引き手数 - 予約数」で有効在庫を取得します。取得した有効在庫のいずれかでも0より大きい場合は、trueを返します。</li>
   * <li>商品の在庫管理区分に「0(在庫管理しない)」または「1(在庫なし販売する)」が設定されている商品は、常にtrueを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * <dd>受け取った「ショップコード + 商品コード」が存在すること。</dd>
   * <dd>受け取った「ショップコード + 商品コード」の商品の在庫管理区分に「0」「1」「2」「3」のいずれかの値が設定されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return SKUが1つでも在庫が存在する場合はtrueを返します。<BR>
   *         全SKUの在庫が存在しない場合はfalseを返します。<BR>
   *         前提条件が満たされない場合はfalseを返します。
   */
  boolean hasStockCommodity(String shopCode, String commodityCode);

  /**
   * 引数で受け取った検索条件をもとに入出庫の履歴を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った検索条件をもとに検索処理を実行し、入出庫の履歴を取得します。
   * <ol>
   * <li>検索条件に名称が与えられた場合は、部分一致検索をします。</li>
   * <li>名称及び範囲検索以外の検索条件が与えられた場合は、完全一致検索をします。</li>
   * <li>入出庫日の降順・データ行IDの降順にならべた入出庫履歴の一覧を生成します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>condition がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した入出庫履歴の一覧を返します。
   */
  SearchResult<StockIODetail> getStockIOList(StockIOSearchCondition condition);

  /**
   * 販売可能か否かに関わらず、引数で受け取ったショップコードと商品コードをもとに商品情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>販売可能か否かに関わらず、引数で受け取ったショップコードと商品コードをもとに商品情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードを元に、商品ヘッダを取得します。</li>
   * <li>取得した商品ヘッダがnullの場合は、nullを返します。</li>
   * <li>引数で受け取ったショップコードと商品コードを元に、商品詳細を取得します。</li>
   * <li>取得した商品詳細がnullの場合は、nullを返します。</li>
   * <li>取得した商品ヘッダと商品詳細、および空の在庫情報を商品情報にセットします。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成した商品情報を返します。
   */
  CommodityInfo getCommodityInfo(String shopCode, String commodityCode);

  /**
   * 販売可能か否かに関わらず、引数で受け取ったショップコードとSKUコードをもとに商品情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>販売可能か否かに関わらず、引数で受け取ったショップコードとSKUコードをもとに商品情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードを元に、商品ヘッダを取得します。</li>
   * <li>取得した商品ヘッダがnullの場合は、nullを返します。</li>
   * <li>引数で受け取ったショップコードと商品コードを元に、商品詳細を取得します。</li>
   * <li>取得した商品詳細がnullの場合は、nullを返します。</li>
   * <li>取得した商品ヘッダと商品詳細を商品情報にセットします。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return 生成した商品情報を返します。
   */
  CommodityInfo getSkuInfo(String shopCode, String skuCode);

  /**
   * 商品情報を新規登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>新規で商品情報および在庫情報を登録します。
   * <ol>
   * <li>引数で受け取った商品情報の販売期間開始日時がnullの場合は1970/01/01 00:00:00を設定します。</li>
   * <li>引数で受け取った商品情報の販売期間終了日時がnullの場合は2100/12/31 23:59:59を設定します。</li>
   * <li>引数で受け取った商品情報をもとに、在庫情報を登録します。<br />
   * 在庫数・引当数・予約数には0を設定します。<br />
   * 予約上限数・注文毎予約上限数にはnullを設定します。</li>
   * <li>引数で受け取った商品情報に対して、Validationチェックを行います。</li>
   * <li>引数で受け取った商品情報の重複チェックを行い、データの重複がある場合はデータ重複エラーを返します。</li>
   * <li>引数で受け取った商品情報を元に商品ヘッダを登録します。</li>
   * <li>引数で受け取った商品情報を元に商品詳細を登録します。</li>
   * <li>引数で受け取った商品情報を元に在庫を登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>commodityがnullでないこと。</dd>
   * <dd>引数で渡された商品情報が商品ヘッダ・商品詳細・在庫に存在しないこと。</dd>
   * <dd>配送種別が最低1件登録されていること。</dd>
   * <dd>在庫管理区分に在庫管理する(状況表示)を設定する場合は、在庫状況が最低1件登録されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>商品ヘッダ・商品詳細・在庫にレコードが1件ずつ登録されます。</dd>
   * <dd>登録処理が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>商品情報または在庫情報のいずれかの登録に失敗した場合は、トランザクションをロールバックします。</dd>
   * </dl>
   * </p>
   * 
   * @param commodity
   *          商品情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertCommodityInfo(CommodityInfo commodity);

  /**
   * 既存の商品情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存の商品情報および在庫情報を更新します。
   * <ol>
   * <li>引数で受け取った商品情報を元に商品ヘッダを取得します。</li>
   * <li>取得した商品ヘッダがNullの場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った商品情報の販売期間開始日時がnullの場合は1970/01/01 00:00:00を設定します。</li>
   * <li>引数で受け取った商品情報の販売期間終了日時がnullの場合は2100/12/31 23:59:59を設定します。</li>
   * <li>引数で受け取った商品情報を元に商品詳細を取得します。</li>
   * <li>取得した商品詳細がNullの場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った商品情報に紐付く代表SKU以外のSKUの予約価格・特別価格を設定します。<br />
   * 引数で受け取った商品情報に予約価格・特別価格が設定されている場合、かつ登録済みのSKUの予約価格・特別価格がNullの場合は、<br />
   * 登録済みSKUの予約価格・特別価格に引数で受け取った商品情報の価格を設定します。</li>
   * <li>引数で受け取った商品情報を元に在庫を取得します。</li>
   * <li>取得した在庫がNullの場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った商品情報に対して、Validationチェックを行います。</li>
   * <li>引数で受け取った商品情報で商品ヘッダを更新します。</li>
   * <li>引数で受け取った商品情報で代表SKUの商品詳細を更新します。</li>
   * <li>引数で受け取った商品情報で代表SKU以外の商品詳細を更新します。</li>
   * <li>引数で受け取った商品情報で在庫の予約上限数と注文毎予約上限数を更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>commodity がnullでないこと。</dd>
   * <dd>在庫種別が最低1件登録されていること。</dd>
   * <dd>在庫管理区分に在庫管理する(状況表示)を設定する場合は、在庫状況が最低1件登録されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>商品ヘッダの全項目を更新します。</dd>
   * <dd>商品詳細の全項目を更新します。</dd>
   * <dd>在庫の予約上限数と注文毎予約上限数を更新します。</dd>
   * <dd>更新処理が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>商品情報または在庫情報のいずれかの更新に失敗した場合は、トランザクションをロールバックします。</dd>
   * </dl>
   * </p>
   * 
   * @param commodity
   *          商品情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateCommodityInfo(CommodityInfo commodity);

  /**
   * 販売可能か否かに関わらず、引数で受け取ったショップコードと商品コードに関連付いているSKUのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>販売可能か否かに関わらず、引数で受け取ったショップコードと商品コードに関連付いているSKUのリストを取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードをもとに、商品詳細を検索します。</li>
   * <li>検索結果を元に商品情報を生成して、商品詳細情報を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 指定されたショップコード、商品コードを持つ商品SKUのリストを返します。
   */
  List<CommodityDetail> getCommoditySku(String shopCode, String commodityCode);

  /**
   * 商品の規格情報を1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受け取った商品情報のショップコードと商品コードをもとに、商品ヘッダを取得します。</li>
   * <li>取得した商品ヘッダが存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った商品情報・在庫情報に対して、Validationチェックを行います。</li>
   * <li>引数で受け取った商品情報のショップコードSKUコードをもとに、商品詳細を取得します。</li>
   * <li>取得した商品詳細が存在する場合は、データ重複エラーを返します。</li>
   * <li>引数で受け取った在庫情報のショップコードSKUコードをもとに、在庫を取得します。</li>
   * <li>取得した在庫が存在する場合は、データ重複エラーを返します。</li>
   * <li>引数で受け取った商品情報で商品詳細を登録します。</li>
   * <li>引数で受け取った在庫情報で在庫を登録します。</li>
   * <li>引数で受け取った商品情報・在庫をもとに、商品および在庫の情報を一括で登録します。</li>
   * <li>登録対象の商品情報がすでに存在する場合は、エラーとします。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>skuがnullでないこと。</dd>
   * <dd>stockがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>商品詳細に1件登録されます。</dd>
   * <dd>在庫に1件登録されます。</dd>
   * <dd>登録処理に成功した場合は、トランザクションをコミットします。</dd>
   * <dd>商品または在庫情報のいずれかの登録に失敗した場合は、トランザクションをロールバックします。</dd>
   * </dl>
   * </p>
   * 
   * @param sku
   *          SKU情報
   * @param stock
   *          在庫情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertCommoditySku(CommodityDetail sku, Stock stock);

  /**
   * 商品の規格情報を1件更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受け取った商品情報のショップコードと商品コードをもとに、商品ヘッダを取得します。</li>
   * <li>取得した商品ヘッダが存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った商品情報のショップコードとSKUコードをもとに、商品詳細を取得します。</li>
   * <li>取得した商品詳細が存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った在庫情報のショップコードとSKUコードをもとに、在庫を取得します。</li>
   * <li>取得した在庫が存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った商品情報・在庫情報に対して、Validationチェックを行います。</li>
   * <li>引数で受け取った商品情報で、商品詳細を更新します。</li>
   * <li>引数で受け取った在庫情報で、予約上限数と注文毎予約上限数を更新します。</li>
   * <li>引数で受け取った商品情報・在庫情報をもとに、商品および在庫の情報を一括で更新します。</li>
   * <li>登録対象の商品情報が存在しない場合は、エラーとします。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>skuがnullでないこと。</dd>
   * <dd>stockがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>商品詳細を1件更新します。</dd>
   * <dd>在庫の予約上限数と注文毎予約上限数を更新します。</dd>
   * <dd>更新処理に成功した場合は、トランザクションをコミットします。</dd>
   * <dd>商品または在庫情報のいずれかの更新に失敗した場合は、トランザクションをロールバックします。</dd>
   * </dl>
   * </p>
   * 
   * @param sku
   *          SKU情報
   * @param stock
   *          在庫情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateCommoditySku(CommodityDetail sku, Stock stock);

  /**
   * ショップコード,商品コードに関連付いている商品詳細の指定したタイプの価格の値を一括更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップコード,商品コードに関連付いている商品詳細の指定したタイプの価格の値を一括更新します。
   * <ol>
   * <li>引数で受け取った価格に対して、Validationチェックを行います。</li>
   * <li>引数で受け取ったショップコードと商品コードをもとに、商品詳細のリストを取得します。</li>
   * <li>以降の処理を、商品詳細のリストの数だけ繰り返します。</li>
   * <li>商品詳細のリストから1件取得します。</li>
   * <li>引数で受け取ったタイプが商品単価の場合は、引数で受け取った価格を、1件取得した商品詳細の商品単価にセットします。<br />
   * 引数で受け取ったタイプが特別価格の場合は、引数で受け取った価格を、1件取得した商品詳細の特別価格にセットします。<br />
   * 引数で受け取ったタイプが予約価格の場合は、引数で受け取った価格を、1件取得した商品詳細の予約価格にセットします。<br />
   * 引数で受け取ったタイプが改定価格の場合は、引数で受け取った価格を、1件取得した商品詳細の改定価格にセットします。<br />
   * </li>
   * <li>1件取得した商品詳細で、商品詳細を更新します。</li>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと</dd>
   * <dd>commodityCodeがnullでないこと</dd>
   * <dd>typeがnullでないこと</dd>
   * <dd>priceがnullでないこと</dd>
   * <dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で受け取ったタイプに応じて、商品単価・特別価格・予約価格・改定価格を更新します。</dd>
   * <dd>※このメソッドは排他制御を考慮していません。これより前の変更を上書きします。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param type
   *          更新対象となる価格タイプ
   * @param price
   *          価格
   * @return サービスの処理結果を返します。
   */
  ServiceResult updatePriceAll(String shopCode, String commodityCode, CommodityPriceType type, BigDecimal price);

  /**
   * 指定されたショップコード,商品コードリストの商品の販売フラグを一括更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定されたショップコード、商品コードリストの商品の販売フラグを一括更新します。
   * <dd>以下の処理を商品コードリストの件数分繰り返します。
   * <ol>
   * <li>引数で受け取ったショップコード,商品コードをもとに、商品ヘッダを取得します。</li>
   * <li>取得した商品ヘッダが存在しない場合は、以降の処理をスキップし、1に戻ります。</li>
   * <li>取得した商品ヘッダの販売フラグに、引数で受け取った販売フラグの値をセットします。</li>
   * <li>取得した商品ヘッダで、商品ヘッダを更新します。</li>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>saleFlgがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>commodityCodeListで指定した商品の販売フラグを全て更新します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCodeList
   *          商品コードリスト
   * @param saleFlg
   *          販売フラグ
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateCommoditySaleType(String shopCode, List<String> commodityCodeList, SaleFlg saleFlg);

  /**
   * 指定された商品に関連付いている情報を一括で削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された商品に関連付いている情報を一括で削除します。
   * <dd>ただし、次の条件に該当する場合は削除を行いません。
   * <ol>
   * <li>指定した商品が未入金の受注と関連付いていた場合</li>
   * <li>指定した商品が未出荷の受注と関連付いていた場合</li>
   * <li>指定した商品が予約済み商品である場合</li>
   * </ol>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>下記のテーブルより、引数で受け取ったショップコード,商品コードに一致するレコードを削除します。</dd>
   * <ol>
   * <li>商品ヘッダ</li>
   * <li>商品詳細</li>
   * <li>在庫</li>
   * <li>カテゴリ陳列商品</li>
   * <li>カテゴリ属性値</li>
   * <li>タグ対象商品</li>
   * <li>お気に入り商品</li>
   * <li>おすすめ商品</li>
   * <li>キャンペーン対象商品</li>
   * <li>ギフト対象商品</li>
   * <li>手動リコメンド</li>
   * <li>自動リコメンド</li>
   * <li>商品入荷お知らせ</li>
   * <li>レビュー点数集計</li>
   * </ol>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return サービス処理結果
   */
  ServiceResult deleteCommodity(String shopCode, String commodityCode);

  /**
   * 指定された商品SKUに関連付いている情報を一括で削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された商品SKUに関連付いている情報を一括で削除します。
   * <dd>ただし、次の条件に該当する場合は削除を行いません。
   * <ol>
   * <li>指定した商品が未入金の受注と関連付いていた場合</li>
   * <li>指定した商品が未出荷の受注と関連付いていた場合</li>
   * <li>指定した商品が予約済み商品である場合</li>
   * </ol>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>下記のテーブルより、指定した商品SKUに関連付いている情報がされること。</dd>
   * <ol>
   * <li>商品明細</li>
   * <li>在庫</li>
   * <li>カテゴリ陳列商品</li>
   * <li>カテゴリ属性値</li>
   * <li>タグ対象商品</li>
   * <li>お気に入り商品</li>
   * <li>おすすめ商品</li>
   * <li>キャンペーン対象商品</li>
   * <li>ギフト対象商品</li>
   * <li>手動リコメンド</li>
   * <li>自動リコメンド</li>
   * <li>商品入荷お知らせ</li>
   * </ol>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return サービス処理結果
   */
  ServiceResult deleteCommoditySku(String shopCode, String skuCode);

  /**
   * ショップが持つタグの一覧および各タグに関連付いている商品数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップが持つタグの一覧および各タグに関連付いている商品数を取得します。
   * <ol>
   * <li>引数で受け取ったショップコードを元に、ショップが持つすべてのタグを検索します。</li>
   * <li>取得したすべてのタグとそのタグに関連付いている商品の件数をもとに、タグコードの昇順に並べたタグのリストを生成します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 生成したタグのリストを返します。
   */
  List<TagCount> getTagList(String shopCode);

  /**
   * タグを新規登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>タグを新規登録します。
   * <ol>
   * <li>引数で受け取ったタグ情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったタグ情報内のショップコードとタグコードをもとに、タグの重複チェックをします。</li>
   * <li>タグテーブル内に重複チェック対象のタグが存在した場合は、重複エラーを返します。</li>
   * <li>引数で受け取ったタグ情報をタグテーブルに登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>tagがnullでないこと。</dd>
   * <dd>tag内のショップコードとタグコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>タグテーブルに、タグ情報が1件登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param tag
   *          タグコード
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertTag(Tag tag);

  /**
   * 既存のタグ情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存のタグ情報を更新します。
   * <ol>
   * <li>引数で受け取ったタグ情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったタグ情報内のショップコードとタグコードをもとに、タグの存在チェックをします。</li>
   * <li>タグテーブル内に存在チェック対象のタグが存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取ったタグ情報でタグテーブル内の値を更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>tag がnullでないこと。</dd>
   * <dd>tag内のショップコードと商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>タグテーブル内のタグ情報が1件更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param tag
   *          タグ情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateTag(Tag tag);

  /**
   * 既存のタグ情報を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存のタグ情報を削除します。
   * <ol>
   * <li>引数で受け取ったショップコードとタグコードをもとに、タグに関連付いている商品の件数を取得します。</li>
   * <li>関連付いている商品の件数が1件以上ある場合は、入力値妥当性エラーを返します。</li>
   * <li>引数で受け取ったショップコードとタグコードをもとに、タグの存在チェックをします。</li>
   * <li>タグテーブル内に存在チェック対象のタグが存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取ったショップコードとタグコードを持つタグ情報をタグテーブルから削除します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>tagCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>タグテーブルからタグ情報が1件削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param tagCode
   *          タグコード
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteTag(String shopCode, String tagCode);

  /**
   * 指定されたタグ情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定されたタグ情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったshopCodeとtagCodeを元に、タグを検索します。</li>
   * <li>検索結果をもとにタグ情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>tagCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param tagCode
   *          タグコード
   * @return 生成したタグ情報を返します。
   */
  Tag getTag(String shopCode, String tagCode);

  /**
   * 商品とタグの関連付け情報を1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>商品とタグの関連付け情報を1件登録します。
   * <ol>
   * <li>引数で受け取った商品タグ関連情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取った商品タグ関連情報内のショップコードと商品コードをもとに、商品ヘッダに対して存在チェックをします。</li>
   * <li>商品ヘッダ内に商品が存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取った商品タグ関連情報内のショップコード・タグコード・商品コードをもとに、タグ商品テーブルの重複チェックをします。</li>
   * <li>タグ商品テーブル内に重複チェック対象の関連付け情報が存在する場合は、重複エラーを返します。</li>
   * <li>引数で受け取った商品とタグの関連付け情報を1件登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>tagCommodityがnullでないこと。</dd>
   * <dd>tagCommodity内のショップコード・タグコード・商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param tagCommodity
   *          タグ陳列商品情報
   * @return サービス処理結果を返します。
   */
  ServiceResult insertRelatedTag(TagCommodity tagCommodity);

  /**
   * 商品とギフトの関連付け情報を1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>商品とギフトの関連付け情報を1件登録します。
   * <ol>
   * <li>引数で受け取った商品ギフト関連情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取った商品ギフト関連情報内のショップコードと商品コードをもとに、商品ヘッダに対して存在チェックをします。</li>
   * <li>商品ヘッダ内に商品が存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取った商品ギフト関連情報内のショップコード・ギフトコード・商品コードをもとに、ギフト商品テーブルの重複チェックをします。</li>
   * <li>ギフト商品テーブル内に重複チェック対象の関連付け情報が存在する場合は、重複エラーを返します。</li>
   * <li>引数で受け取った商品とギフトの関連付け情報を1件登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>giftCommodityがnullでないこと。</dd>
   * <dd>giftCommodity内のショップコード・ギフトコード・商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param giftCommodity
   *          ギフト対象商品情報
   * @return サービス処理結果を返します。
   */
  ServiceResult insertRelatedGift(GiftCommodity giftCommodity);

  /**
   * 商品とタグの関連付け情報を1件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>商品とタグの関連付け情報を1件削除します。
   * <ol>
   * <li>引数で受け取ったショップコード・タグコード・商品コードをもとに、商品とタグの関連付け情報を1件削除します。</li>
   * <li>削除対象の関連付け情報が存在しない場合も、処理が正常終了したものとします。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>tagCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定された関連付け情報を削除します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param tagCode
   *          タグコード
   * @param commodityCode
   *          商品コード
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteRelatedTag(String shopCode, String tagCode, String commodityCode);

  /**
   * 商品とギフトの関連付け情報を1件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>商品とギフトの関連付け情報を1件削除します。
   * <ol>
   * <li>引数で受け取ったショップコード・ギフトコード・商品コードをもとに、商品とギフトの関連付け情報を1件削除します。</li>
   * <li>削除対象の関連付け情報が存在しない場合も、処理が正常終了したものとします。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>giftCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定された関連付け情報を削除します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param giftCode
   *          ギフトコード
   * @param commodityCode
   *          商品コード
   * @return サービス処理結果を返します。
   */
  ServiceResult deleteRelatedGift(String shopCode, String giftCode, String commodityCode);

  /**
   * ショップが持つギフトの一覧および各ギフトに関連付いている商品数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップが持つギフトの一覧および各ギフトに関連付いている商品数を取得します。
   * <ol>
   * <li>引数で受け取ったショップコードを元に、ショップが持つすべてのギフトを検索します。</li>
   * <li>取得したすべてのギフトとそのギフトに関連付いている商品の件数をもとに、ギフトコードの昇順に並べたギフトのリストを生成します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 生成したギフトのリストを返します。
   */
  List<GiftCount> getGiftList(String shopCode);

  /**
   * 商品に関連付いている、販売可能なギフトの一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>商品に関連付いている、販売可能なギフトの一覧を取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードをもとに、その商品に関連付いていて、かつ表示区分が表示に設定してあるギフトを検索します。</li>
   * <li>検索結果をもとに、ギフト情報の一覧を作成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成したギフト情報の一覧を返します。
   */
  List<Gift> getAvailableGiftList(String shopCode, String commodityCode);

  /**
   * ギフトを1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ギフトを1件登録します。
   * <ol>
   * <li>引数で受け取ったギフト情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったギフト情報内のショップコードとギフトコードをもとに、ギフトの重複チェックをします。</li>
   * <li>ギフトテーブル内に重複チェック対象のギフトが存在した場合は、重複エラーを返します。</li>
   * <li>引数で受け取ったギフト情報をギフトテーブルに登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>giftがnullでないこと。</dd>
   * <dd>gift内のショップコードとギフトコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ギフトテーブルに、ギフト情報が1件登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param gift
   *          ギフト情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertGift(Gift gift);

  /**
   * 既存のギフト情報を1件更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存のギフト情報を1件更新します。
   * <ol>
   * <li>引数で受け取ったギフト情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったギフト情報内のショップコードとギフトコードをもとに、ギフトの存在チェックをします。</li>
   * <li>ギフトテーブル内に存在チェック対象のギフトが存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取ったギフト情報でギフトテーブル内の値を更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>giftがnullでないこと。</dd>
   * <dd>gift内のショップコードと商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ギフトテーブル内のギフト情報が1件更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param gift
   *          ギフト情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateGift(Gift gift);

  /**
   * 既存のギフト情報を1件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存のギフト情報を1件削除します。
   * <ol>
   * <li>引数で受け取ったショップコードとギフトコードをもとに、ギフトに関連付いている商品の件数を取得します。</li>
   * <li>関連付いている商品の件数が1件以上ある場合は、入力値妥当性エラーを返します。</li>
   * <li>引数で受け取ったショップコードとギフトコードをもとに、ギフトの存在チェックをします。</li>
   * <li>ギフトテーブル内に存在チェック対象のギフトが存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取ったショップコードとギフトコードを持つギフト情報をギフトテーブルから削除します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>giftCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ギフトテーブルからギフト情報が1件削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param giftCode
   *          ギフトコード
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteGift(String shopCode, String giftCode);

  // 10.1.4 10036 追加 ここから
  /**
   * 在庫状況を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったショップコードと在庫状況番号を元に在庫状況を取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと在庫状況番号が値を持っているかどうかをチェックし、どちらか一方でも値を持たない場合はnullを返します。</li>
   * <li>Daoを使用して在庫状況を1件取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode, stockStatusNoがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param stockStatusNo
   *          在庫状況番号
   * @return 在庫状況を返します。
   */
  StockStatus getStockStatus(String shopCode, Long stockStatusNo);

  // 10.1.4 10036 追加 ここまで

  /**
   * ショップが持つ在庫状況設定の一覧および各在庫状況設定に関連付いている商品数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップが持つ在庫状況設定の一覧および各在庫状況設定に関連付いている商品数を取得します。
   * <ol>
   * <li>引数で受け取ったショップコードを元に、ショップが持つすべての在庫状況設定を検索します。</li>
   * <li>
   * 取得したすべての在庫状況設定とその在庫状況設定に関連付いている商品の件数をもとに、在庫状況設定コードの昇順に並べた在庫状況設定のリストを生成します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 生成した在庫状況のリストを返します。
   */
  List<StockStatusCount> getStockStatusList(String shopCode);

  /**
   * 在庫状況設定を1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>在庫状況設定を1件登録します。
   * <ol>
   * <li>引数で受け取った在庫状況設定情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取った在庫状況設定情報内のショップコードと在庫状況番号をもとに、在庫状況設定の重複チェックをします。</li>
   * <li>在庫状況設定テーブル内に重複チェック対象の在庫状況設定が存在した場合は、重複エラーを返します。</li>
   * <li>引数で受け取った在庫状況設定情報を在庫状況設定テーブルに登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>stockStatusがnullでないこと。</dd>
   * <dd>stockStatus内のショップコードと在庫状況番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>在庫状況設定テーブルに、在庫状況設定情報が1件登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param stockStatus
   *          在庫状況設定情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertStockStatus(StockStatus stockStatus);

  /**
   * 既存の在庫状況設定情報を1件更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存の在庫状況設定情報を1件更新します。
   * <ol>
   * <li>引数で受け取った在庫状況設定情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取った在庫状況設定情報内のショップコードと在庫状況番号をもとに、在庫状況設定の存在チェックをします。</li>
   * <li>在庫状況設定テーブル内に存在チェック対象の在庫状況設定が存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取った在庫状況設定情報で在庫状況設定テーブル内の値を更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>stockStatusがnullでないこと。</dd>
   * <dd>stockStatus内のショップコードと商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>在庫状況設定テーブル内の在庫状況設定情報が1件更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param stockStatus
   *          在庫状況設定情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateStockStatus(StockStatus stockStatus);

  /**
   * 既存の在庫状況設定情報を1件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存の在庫状況設定情報を1件削除します。
   * <ol>
   * <li>引数で受け取ったショップコードと在庫状況番号をもとに、在庫状況設定に関連付いている商品の件数を取得します。</li>
   * <li>関連付いている商品の件数が1件以上ある場合は、入力値妥当性エラーを返します。</li>
   * <li>引数で受け取ったショップコードと在庫状況番号をもとに、在庫状況設定の存在チェックをします。</li>
   * <li>在庫状況設定テーブル内に存在チェック対象の在庫状況設定が存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取ったショップコードと在庫状況番号を持つ在庫状況設定情報を在庫状況設定テーブルから削除します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>stockStatusCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>在庫状況設定テーブルから在庫状況設定情報が1件削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param stockStatusNo
   *          在庫コード
   * @return サービス実行結果
   */
  ServiceResult deleteStockStatus(String shopCode, Long stockStatusNo);

  /**
   * ルートカテゴリのカテゴリ情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ルートカテゴリのカテゴリ情報を1件取得します。
   * <ol>
   * <li>階層が0のカテゴリ情報を検索します。</li>
   * <li>検索結果を元にカテゴリ情報生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 生成したカテゴリ情報を返します。
   */
  Category getRootCategory();

  /**
   * 指定したカテゴリコードのカテゴリ情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリコードのカテゴリ情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったカテゴリコードをもとに、カテゴリテーブル内を検索します。</li>
   * <li>検索結果をもとにカテゴリ情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          カテゴリコード
   * @return 生成したカテゴリ情報を返します。
   */
  Category getCategory(String categoryCode);

  /**
   * カテゴリ情報とカテゴリ属性情報を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カテゴリ情報とカテゴリ属性情報を登録します。
   * <ol>
   * <li>引数で受け取ったカテゴリ情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったカテゴリ情報からカテゴリDTOに値をコピーします。</li>
   * <li>引数で受け取ったカテゴリ情報内のカテゴリ属性の数だけ、カテゴリ属性DTOにコピーします。<br />
   * コピーしたカテゴリ属性DTOに対して、Validationチェックをします。</li>
   * <li>カテゴリDTO内のカテゴリコードをもとに、カテゴリの重複チェックをします。</li>
   * <li>カテゴリテーブルにカテゴリ情報が存在した場合は、重複エラーを返します。</li>
   * <li>トランザクション処理を開始します。</li>
   * <li>カテゴリDTOで、カテゴリテーブルにカテゴリ情報を登録します。</li>
   * <li>カテゴリ属性DTOのリストの数だけ、以降の処理を繰り返します。</li>
   * <li>カテゴリ属性DTOのカテゴリコードとカテゴリ属性番号をもとに、カテゴリ属性テーブル内の存在チェックをします。</li>
   * <li>カテゴリ属性テーブル内にカテゴリ属性情報が存在しない場合は、カテゴリ属性DTOでカテゴリ属性テーブルを登録します。<br />
   * カテゴリ属性テーブル内にカテゴリ属性情報が存在する場合は、カテゴリ属性DTOでカテゴリ属性テーブルの値を更新します。</li>
   * <li>トランザクション処理を終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryInfoがnullでないこと。</dd>
   * <dd>categoryInfoのカテゴリコードとカテゴリ属性番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての登録/更新処理が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>1件でも登録/更新処理が失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>カテゴリテーブルに、カテゴリ情報が1件登録されます。</dd>
   * <dd>カテゴリ属性テーブルに、カテゴリ属性情報が引数で受け取ったカテゴリ属性情報の数だけ登録または更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryInfo
   *          カテゴリ情報
   * @return サービス処理結果を返します。
   */
  ServiceResult insertCategory(CategoryInfo categoryInfo);

  /**
   * 既存のカテゴリ情報とカテゴリ属性情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存のカテゴリ情報とカテゴリ属性情報を更新します。
   * <ol>
   * <li>引数で受け取ったカテゴリ情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったカテゴリ情報をカテゴリDTOにコピーします。</li>
   * <li>引数で受け取ったカテゴリ情報内のカテゴリ属性の数だけ、カテゴリ属性DTOにコピーします。<br />
   * コピーしたカテゴリ属性DTOに対して、Validationチェックをします。</li>
   * <li>カテゴリDTO内のカテゴリコードをもとに、カテゴリテーブルに対して存在チェックをします。</li>
   * <li>カテゴリテーブル内にカテゴリが存在しない場合は、存在エラーを返します。</li>
   * <li>トランザクション処理を開始します。</li>
   * <li>カテゴリ属性DTOのリストの数だけ、以降の処理を繰り返します。</li>
   * <li>カテゴリ属性DTOをもとに、カテゴリ属性テーブルに対して存在チェックをします。</li>
   * <li>カテゴリ属性テーブル内にカテゴリ属性情報が存在しない場合は、カテゴリ属性DTOでカテゴリ属性テーブルを登録します。<br />
   * カテゴリ属性テーブル内にカテゴリ属性情報が存在する場合は、カテゴリ属性DTOでカテゴリ属性テーブルの値を更新します。</li>
   * <li>トランザクション処理を終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryInfoがnullでないこと。</dd>
   * <dd>categoryInfoのカテゴリコードとカテゴリ属性番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての登録/更新処理が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>1件でも登録/更新処理が失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>カテゴリテーブル内のカテゴリ情報が1件更新されます。</dd>
   * <dd>カテゴリ属性テーブルに、カテゴリ属性情報が引数で受け取ったカテゴリ属性情報の数だけ登録または更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryInfo
   *          カテゴリ情報
   * @return サービス実行結果を返します。
   */
  ServiceResult updateCategory(CategoryInfo categoryInfo);

  /**
   * 引数で受け取ったカテゴリコードを持つカテゴリに関連する情報を一括で削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったカテゴリコードを持つカテゴリに関連する情報を一括で削除します。
   * <ol>
   * <li>トランザクションを開始します。</li>
   * <li>引数で受け取ったカテゴリコードのリストの数だけ以降の処理を繰り返します。</li>
   * <li>引数で受け取ったカテゴリコードのリストから1件カテゴリコードを取得します。</li>
   * <li>カテゴリテーブル・カテゴリ属性テーブル・カテゴリ属性値テーブル・カテゴリ陳列商品テーブルから<br />
   * 取得したカテゴリコードを持つレコードを削除します。</li>
   * <li>トランザクションを終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCodeListがnullでないこと。</dd>
   * <dd>categoryCodeList内のカテゴリコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての削除処理が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>1件でもの削除処理が失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>カテゴリテーブル内からカテゴリ情報が削除されます。</dd>
   * <dd>カテゴリ属性テーブル内からカテゴリ属性情報が削除されます。</dd>
   * <dd>カテゴリ属性値テーブル内からカテゴリ属性値情報が削除されます。</dd>
   * <dd>カテゴリ陳列商品テーブル内からカテゴリと商品の関連付け情報が削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCodeList
   *          カテゴリコードリスト
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteCategory(List<String> categoryCodeList);

  /**
   * 登録されているカテゴリを全件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>カテゴリテーブル内に存在する、階層のリストを取得します。</li>
   * <li>取得した階層のリストごとに、以降の処理をテンポラリのカテゴリ情報リストの生成が終わるまで繰り返します。</li>
   * <li>階層のリストから1件取得します。</li>
   * <li>取得した階層に登録されているすべてのカテゴリの情報を、カテゴリテーブルとカテゴリ属性テーブルから<br />
   * パス・表示順・カテゴリコード・カテゴリ属性番号の昇順に取得します。</li>
   * <li>カテゴリテーブル・カテゴリ属性テーブルから取得した情報をもとに、カテゴリ詳細情報のリストを生成します。</li>
   * <li>生成したカテゴリ詳細のリスト内にあるカテゴリ情報とカテゴリ属性情報を、テンポラリのカテゴリ情報に生成します。</li>
   * <li>生成したテンポラリのカテゴリ情報を、テンポラリのカテゴリ情報リストに設定します。</li>
   * <li>テンポラリのカテゴリ情報リストへの設定が終わったら、階層のリスト取得処理に戻ります。</li>
   * <li>戻り値用のカテゴリ情報を生成し、以降の処理を繰り返します。</li>
   * <li>テンポラリのカテゴリ情報リストから1件取得します。</li>
   * <li>リストの1件目と2件目の場合は、戻り値用カテゴリ情報にカテゴリ情報とカテゴリ属性情報を生成して、戻り値用カテゴリ情報リストに設定します。<br />
   * リストの3件目以降の場合は、テンポラリのリストから取得したカテゴリ情報の親カテゴリコードと<br />
   * 戻り値用カテゴリ情報のカテゴリコードが一致したときに、戻り値用カテゴリ情報リストにテンポラリのリストから取得したカテゴリ情報をせっていします。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>階層0のルートカテゴリが登録されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return カテゴリ情報のリストを返します。
   */
  List<CategoryInfo> getAllCategory();

  /**
   * 引数で渡されたショップコード、SKUコード、メールアドレスに関連付いている入荷お知らせ情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で渡されたショップコード、SKUコード、メールアドレスに関連付いている入荷お知らせ情報を1件取得します。
   * <ol>
   * <li>引数で渡されたショップコード、SKUコード、メールアドレスをもとに入荷お知らせテーブルを検索します。</li>
   * <li>検索結果をもとに入荷お知らせ情報を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * <dd>emailがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param email
   *          メールアドレス
   * @return 生成した入荷お知らせ情報を返します。
   */
  ArrivalGoods getArrivalGoods(String shopCode, String skuCode, String email);

  /**
   * 指定された検索条件に該当するSKU情報と、その商品の入荷お知らせ申し込み数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>conditionで指定された検索条件をもとに、入荷お知らせテーブルから該当する商品の入荷お知らせ件数を取得します。
   * <dd>検索条件の各パラメータについては、以下のように検索を行います。</dd>
   * <ol>
   * <li>searchShopCode: ショップコードの完全一致</li>
   * <li>searchSkuCode: SKUコードの完全一致</li>
   * <li>searchCommodityName: 商品名の前方一致</li> </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>condtion がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定された条件に該当するSKU情報と入荷お知らせ申し込み数のリストを返します。</dd>
   * <dd>入荷お知らせ申し込み数が0件のSKUは検索されません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 指定された条件に該当するSKU情報と入荷お知らせ申し込み数のリストを返します。
   */
  SearchResult<ArrivalGoodsSubscritionCount> getArrivalGoodsSubcriptionCountList(ArrivalGoodsSearchCondition condition);

  /**
   * 入荷お知らせ情報を1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入荷お知らせ情報を1件登録します。
   * <ol>
   * <li>引数で受け取った入荷お知らせ情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取った入荷お知らせ情報内のショップコード・SKUコード・メールアドレスをもとに、入荷お知らせテーブルを重複チェックします。</li>
   * <li>入荷お知らせテーブル内に引数で受け取った入荷お知らせ情報が存在する場合は、存在エラーを返します。</li>
   * <li>引数で受け取った入荷お知らせ情報で、入荷お知らせテーブルを更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>arrivalGoodsがnullでないこと。</dd>
   * <dd>arrivalGoods内のショップコードがnullでないこと。</dd>
   * <dd>arrivalGoods内のSKUコードがnullでないこと。</dd>
   * <dd>arrivalGoods内のメールアドレスがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>入荷お知らせ情報が1件登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param arrivalGoods
   *          入荷お知らせ情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertArrivalGoods(ArrivalGoods arrivalGoods);

  /**
   * 既存の入荷お知らせ情報を1件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>既存の入荷お知らせ情報を1件削除します。
   * <ol>
   * <li>引数で受け取ったショップコード・SKUコード・メールアドレスをもとに、入荷お知らせ情報を1件削除します。</li>
   * <li>入荷お知らせテーブル内に引数で受け取ったショップコード・SKUコード・メールアドレスを持つレコードが存在しなくても、
   * 正常に削除が完了したものとして処理します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * <dd>emailがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>入荷お知らせテーブルから1件削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param email
   *          メールアドレス
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteArrivalGoods(String shopCode, String skuCode, String email);

  /**
   * ショップコード・SKUコードに関連付いている入荷お知らせ情報を全件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップコード・SKUコードに関連付いている入荷お知らせ情報を全件削除します。
   * <ol>
   * <li>引数で受け取ったショップコード・SKUコードを持つレコードを入荷お知らせテーブルから削除します。</li>
   * <li>入荷お知らせテーブル内に引数で受け取ったショップコード・SKUコードを持つレコードが存在しなくても、
   * 正常に削除が完了したものとして処理します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>入荷お知らせテーブルから引数で受け取ったショップコードとSKUコードを持つレコードが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteCommodityArrivalGoods(String shopCode, String skuCode);

  /**
   * 販売可能か否かに関わらず、指定したギフトのギフト情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したギフトのギフト情報を、販売可能か否かに関わらず取得します。
   * <ol>
   * <li>引数で受け取ったショップコードとギフトコードをもとに、ギフトテーブルを検索します。</li>
   * <li>検索結果をもとにギフト情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>giftCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param giftCode
   *          ギフトコード
   * @return 生成したギフト情報を返します。
   */
  Gift getGift(String shopCode, String giftCode);

  /**
   * 販売可能か否かに関わらず、指定した商品の商品ヘッダ情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>販売可能か否かに関わらず、指定した商品の商品ヘッダ情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードをもとに、商品ヘッダを検索します。</li>
   * <li>検索結果をもとに商品ヘッダ情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>取得結果をもとに商品ヘッダ情報を生成します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成した商品ヘッダ情報を返します。
   */
  CommodityHeader getCommodityHeader(String shopCode, String commodityCode);
  
  Long getAllDescribeImgHistory(String commodityCode,String type);

  /**
   * 指定したキャンペーンに関連付いている、指定した商品の関連付け情報を1件削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したキャンペーンに関連付いている、指定した商品の関連付け情報を1件削除します。
   * <ol>
   * <li>引数で受け取ったショップコード・キャンペーンコード・商品コードを元に、キャンペーンと商品の関連付け情報を削除します。</li>
   * <li>キャンペーン対象商品テーブル内に引数で受け取ったショップコード・キャンペーンコード・商品コードを持つレコードが存在しなくても、
   * 正常に削除が完了したものとして処理します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>campaignCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>キャンペーン対象商品テーブルから、キャンペーンと商品の関連付け情報が1件削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param campaignCode
   *          キャンペーンコード
   * @param commodityCode
   *          商品コード
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteRelatedCampaign(String shopCode, String campaignCode, String commodityCode);

  /**
   * 引数で受け取った値をもとに、商品の相互の関連付けまたは一方の関連付け情報を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った値をもとに、商品の相互の関連付けまたは一方の関連付け情報を削除します。
   * <ol>
   * <li>引数で受け取ったフラグがtrueの場合は、ショップコード・商品コード・リンク商品コードのレコードが<br />
   * 手動関連付けテーブルに存在するかをチェックして、存在する場合は削除します。</li>
   * <li>ショップコード・リンクショップコード・商品コードのレコードが手動関連付けテーブルに存在するかをチェックして、<br />
   * 存在する場合はレコードを削除します。</li>
   * <li>それぞれの削除処理は、削除対象のレコードが存在しない場合も正常に削除されたものとして処理します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * <dd>linkCommodityCodeがnullでないこと。</dd>
   * <dd>interactiveDeleteFlgがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd></dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param linkCommodityCode
   *          商品関連付けコード
   * @param interactiveDeleteFlg
   *          双方向削除フラグ
   * @return サービス実行結果
   */
  ServiceResult deleteRelatedCommodityA(String shopCode, String commodityCode, String linkCommodityCode,
      boolean interactiveDeleteFlg);

  /**
   * タグ対象商品から、タグと商品の関連付け情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>タグ対象商品から、タグと商品の関連付け情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・タグコード・商品コードをもとにタグ対象商品を検索します。</li>
   * <li>検索結果をもとに関連付け情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>tagCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param tagCode
   *          タグコード
   * @param commodityCode
   *          商品コード
   * @return 生成した関連付け情報を返します。
   */
  TagCommodity getTagCommodity(String shopCode, String tagCode, String commodityCode);

  /**
   * キャンペーン対象商品から、キャンペーンと商品の関連付け情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>キャンペーン対象商品から、キャンペーンと商品の関連付け情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・キャンペーンコード・商品コードをもとにキャンペーン対象商品を検索します。</li>
   * <li>検索結果をもとに関連付け情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>campaignCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param campaignCode
   *          キャンペーンコード
   * @param commodityCode
   *          商品コード
   * @return 生成した関連付け情報を返します。
   */
  CampaignCommodity getCampaignCommodity(String shopCode, String campaignCode, String commodityCode);

  /**
   * ギフト対象商品から、ギフトと商品の関連付け情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ギフト対象商品から、ギフトと商品の関連付け情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・ギフトコード・商品コードをもとにギフト対象商品を検索します。</li>
   * <li>検索結果をもとに関連付け情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>giftCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param giftCode
   *          ギフトコード
   * @param commodityCode
   *          商品コード
   * @return 生成した関連付け情報を返します。
   */
  GiftCommodity getGiftCommodity(String shopCode, String giftCode, String commodityCode);

  /**
   * 手動リコメンドから、商品と商品の関連付け情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>手動リコメンドから、商品と商品の関連付け情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・商品コード・リンク商品コードをもとに手動リコメンドを検索します。</li>
   * <li>検索結果をもとに関連付け情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * <dd>linkCommodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param linkCommodityCode
   *          リンク商品コード
   * @return 生成した関連付け情報を返します。
   */
  RelatedCommodityA getRelatedCommodityA(String shopCode, String commodityCode, String linkCommodityCode);

  /**
   * キャンペーン対象商品に、指定した関連付け情報を1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>キャンペーン対象商品に、指定した関連付け情報を1件登録します。
   * <ol>
   * <li>引数で受け取ったキャンペーン対象商品情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったキャンペーン対象商品情報内のショップコードと商品コードをもとに、商品ヘッダの存在チェックをします。</li>
   * <li>商品ヘッダに商品が存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取ったキャンペーン対象商品情報内のショップコードとキャンペーンコードをもとに、キャンペーンの存在チェックをします。</li>
   * <li>キャンペーンが存在しない場合は、存在エラーを返します。</li>
   * <li>引数で受け取ったキャンペーン対象商品情報内のショップコード・キャンペーンコード・商品コードをもとに、<br />
   * キャンペーン対象商品テーブル内の重複チェックをします。</li>
   * <li>キャンペーン対象商品テーブル内に存在する場合は、重複エラーを返します。</li>
   * <li>引数で受け取ったキャンペーン対象商品情報で、キャンペーン対象商品テーブルに登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>campaignCommodityがnullでないこと。</dd>
   * <dd>campaignCommodity内のショップコードがnullでないこと。</dd>
   * <dd>campaignCommodity内のキャンペーンコードがnullでないこと。</dd>
   * <dd>campaignCommodity内の商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>キャンペーン対象商品に関連付け情報が1件登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param campaignCommodity
   *          キャンペーン対象商品情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertRelatedCampaign(CampaignCommodity campaignCommodity);

  /**
   * ギフト対象商品から、検索条件に合致する関連付け情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ギフト対象商品から、検索条件に合致する関連付け情報を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・ギフトコード・ギフト名をもとに、ギフトテーブルとギフト対象商品テーブルを検索します。</li>
   * <li>検索結果をもとに関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の適用コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した検索結果の一覧を返します。
   */
  SearchResult<RelatedBaseEvent> getRelatedGiftCommoditySearchBaseEvent(RelatedSearchConditionBaseEvent condition);

  /**
   * キャンペーン対象商品から、検索条件に合致する関連付け情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>キャンペーン対象商品から、検索条件に合致する関連付け情報を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・キャンペーンコード・キャンペーン名をもとに、キャンペーンテーブルとキャンペーン対象商品テーブルを検索します。</li>
   * <li>検索結果をもとに関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の適用コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した検索結果の一覧を返します。
   */
  SearchResult<RelatedBaseEvent> getRelatedCampaignCommoditySearchBaseEvent(RelatedSearchConditionBaseEvent condition);

  /**
   * 手動リコメンドから、検索条件に合致する関連付け情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>手動リコメンドから、検索条件に合致する関連付け情報を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・商品コード・商品名をもとに、商品ヘッダと手動リコメンドテーブルを検索します。</li>
   * <li>検索結果をもとに関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の適用コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した検索結果の一覧を返します。
   */
  SearchResult<RelatedBaseEvent> getRelatedCommodityASearchBaseEvent(RelatedSearchConditionBaseEvent condition);

  /**
   * 自動リコメンドから、検索条件に合致する関連付け情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>自動リコメンドから、検索条件に合致する関連付け情報を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・商品コード・商品名をもとに、商品ヘッダと自動リコメンドテーブルを検索します。</li>
   * <li>検索結果をもとに関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の適用コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した検索結果の一覧を返します。
   */
  SearchResult<RelatedBaseEvent> getRelatedCommodityBSearchBaseEvent(RelatedSearchConditionBaseEvent condition);

  /**
   * タグ対象商品から、検索条件に合致する関連付け情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>タグ対象商品から、検索条件に合致する関連付け情報を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・タグコード・タグ名をもとに、タグテーブルとタグ対象商品テーブルを検索します。</li>
   * <li>検索結果をもとに関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の適用コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した検索結果の一覧を返します。
   */
  SearchResult<RelatedBaseEvent> getRelatedTagCommoditySearchBaseEvent(RelatedSearchConditionBaseEvent condition);

  /**
   * キャンペーン対象商品から、指定した商品コードに関連付いているキャンペーンの一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>キャンペーン対象商品から、指定した商品コードに関連付いているキャンペーンの一覧を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・キャンペーンコード・キャンペーン名をもとに、キャンペーンテーブルとキャンペーン対象商品テーブルを検索します。</li>
   * <li>検索結果をもとにキャンペーンの関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成したキャンペーンの関連付け情報の一覧を返します。
   */
  SearchResult<RelatedCampaign> getRelatedCampaignCommoditySearch(RelatedSearchConditionBaseCommodity condition);

  /**
   * ギフト対象商品から、指定した商品コードに関連付いているギフトの一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ギフト対象商品から、指定した商品コードに関連付いているギフトの一覧を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・ギフトコード・ギフト名をもとに、ギフトテーブルとギフト対象商品テーブルを検索します。</li>
   * <li>検索結果をもとにギフトの関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成したギフトの関連付け情報の一覧を返します。
   */
  SearchResult<RelatedGift> getRelatedGiftCommoditySearch(RelatedSearchConditionBaseCommodity condition);

  /**
   * タグ対象商品から、指定した商品コードに関連付いているタグの一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>タグ対象商品から、指定した商品コードに関連付いているタグの一覧を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・タグコード・タグ名をもとに、タグテーブルとタグ対象商品テーブルを検索します。</li>
   * <li>検索結果をもとにタグの関連付け情報の一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内の商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成したタグの関連付け情報の一覧を返します。
   */
  SearchResult<RelatedTag> getRelatedTagCommoditySearch(RelatedSearchConditionBaseCommodity condition);

  /**
   * 指定した商品に対して、複数のキャンペーンの関連付け情報を一括で登録・削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に対して、複数のキャンペーンの関連付け情報を一括で登録・削除します。キャンペーン対象商品内のデータの有無と、
   * 画面で入力されたチェックボックスとの関係により登録・削除を制御します。<BR>
   * 【テーブル内に関連付けデータが登録されている場合】<BR>
   * 1．チェックされている時：すでにデータが登録されているため登録処理をスキップします<BR>
   * 2．チェックされていない時：関連付けテーブルにデータを登録します<BR>
   * 【テーブル内に関連付けデータが登録されていない場合】<BR>
   * 1．チェックされている時：該当レコードを削除します<BR>
   * 2．チェックされていない時：テーブル内にデータが存在しないため削除処理をスキップします<BR>
   * <ol></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>relatedCampaign がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての関連付け情報の登録が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>関連付け情報の登録が1件でも失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>商品とキャンペーンの関連付け情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param relatedCampaign
   *          キャンペーン関連付け情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult registerRelatedCampaign(List<RelatedCampaign> relatedCampaign);

  /**
   * 指定した商品に対して、複数のギフトの関連付け情報を一括で登録・削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に対して、複数のギフトの関連付け情報を一括で登録・削除します。ギフト対象商品内のデータの有無と、
   * 画面で入力されたチェックボックスとの関係により登録・削除を制御します。<BR>
   * 【テーブル内に関連付けデータが登録されている場合】<BR>
   * 1．チェックされている時：すでにデータが登録されているため登録処理をスキップします<BR>
   * 2．チェックされていない時：関連付けテーブルにデータを登録します<BR>
   * 【テーブル内に関連付けデータが登録されていない場合】<BR>
   * 1．チェックされている時：該当レコードを削除します<BR>
   * 2．チェックされていない時：テーブル内にデータが存在しないため削除処理をスキップします<BR>
   * <ol></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>relatedGift がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての関連付け情報の登録が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>関連付け情報の登録が1件でも失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>商品とギフトの関連付け情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param relatedGift
   *          ギフト関連付け情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult registerRelatedGift(List<RelatedGift> relatedGift);

  /**
   * 指定した商品に対して、複数のタグの関連付け情報を一括で登録・削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に対して、複数のタグの関連付け情報を一括で登録・削除します。タグ対象商品内のデータの有無と、
   * 画面で入力されたチェックボックスとの関係により登録・削除を制御します。<BR>
   * 【テーブル内に関連付けデータが登録されている場合】<BR>
   * 1．チェックされている時：すでにデータが登録されているため登録処理をスキップします<BR>
   * 2．チェックされていない時：関連付けテーブルにデータを登録します<BR>
   * 【テーブル内に関連付けデータが登録されていない場合】<BR>
   * 1．チェックされている時：該当レコードを削除します<BR>
   * 2．チェックされていない時：テーブル内にデータが存在しないため削除処理をスキップします<BR>
   * <ol></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>relatedGift がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての関連付け情報の登録が成功した場合は、トランザクションをコミットします。</dd>
   * <dd>関連付け情報の登録が1件でも失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>商品とタグの関連付け情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param relatedTag
   *          タグ関連付け情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult registerRelatedTag(List<RelatedTag> relatedTag);

  /**
   * 手動リコメンドの関連付け情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った関連付け情報を元に、指定した手動リコメンドの情報を1件更新します。
   * <ol>
   * <li>引数で受け取った手動リコメンド情報内のショップコード・商品コード・リンク商品コードをもとに、手動リコメンドテーブルの存在チェックをします。</li>
   * <li>手動リコメンドに存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った手動リコメンド情報で、手動リコメンドテーブルを更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>relatedCommodityA がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>更新処理に成功した場合は、サービスの処理結果に何も設定しません。</dd>
   * <dd>更新処理に失敗した場合は、サービスの処理結果に失敗した理由を設定します。</dd>
   * <dd>手動リコメンドテーブルが更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param relatedA
   *          手動リコメンド情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateRelatedCommodityA(RelatedCommodityA relatedA);

  /**
   * 開催しているか否かに関わらず、キャンペーン情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>開催しているか否かに関わらず、キャンペーン情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコードとキャンペーンコードををもとに、キャンペーンテーブルを検索します。</li>
   * <li>検索結果をもとにキャンペーン情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>campaignCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param campaignCode
   *          キャンペーンコード
   * @return 生成したキャンペーン情報を返します。
   */
  Campaign getCampaign(String shopCode, String campaignCode);

  /**
   * 指定した商品に関連付いているキャンペーンのうち、現在適用されているキャンペーンを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に関連付いているキャンペーンのうち、現在適用されているキャンペーンを取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードをもとに、現在適用されているキャンペーンを検索します。</li>
   * <li>検索結果をもとに、キャンペーン情報を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成したキャンペーン情報を返します。
   */
  Campaign getAppliedCampaignInfo(String shopCode, String commodityCode);

  /**
   * 指定した商品に関連付いているキャンペーンのうち、現在適用されているキャンペーンを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に関連付いているキャンペーンのうち、現在適用されているキャンペーンを取得します。
   * <ol>
   * <li>引数で受け取ったショップコードとSKUコードをもとに、現在適用されているキャンペーンを検索します。</li>
   * <li>検索結果をもとに、キャンペーン情報を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return 生成したキャンペーン情報を返します。
   */
  Campaign getAppliedCampaignBySku(String shopCode, String skuCode);

  /**
   * 引数で指定した商品が、指定した日に適用されているキャンペーンを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で指定した商品が、指定した日に適用されているキャンペーンを取得します。
   * <ol>
   * <li>引数で指定した商品と日付を元に、その商品に適用されているキャンペーンを検索します。</li>
   * <li>取得したキャンペーン情報を1件返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * <dd>appliedDateがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param appliedDate
   * @return 生成したキャンペーン情報を返します。
   */
  Campaign getAppliedCampaignInfo(String shopCode, String commodityCode, String appliedDate);

  /**
   * 指定された検索条件にしたがって、タグ情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された検索条件にしたがって、タグ情報を取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・タグコード・タグ名称をもとに、タグテーブルを検索します。</li>
   * <li>検索条件に合致するタグごとに、関連付いている商品の数を取得します。</li>
   * <li>取得したタグと商品件数のリストをタグ情報リストとして返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のショップコードがnullでないこと。</dd>
   * <dd>condition内のタグコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>取得した検索結果を元に、タグとそれに関連付いている商品件数の一覧を生成します。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成したタグ情報リストを返します。
   */
  SearchResult<TagCount> getTagSearch(TagSearchCondition condition);

  /**
   * 引数で受け取った商品情報で、該当商品の商品ヘッダを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った商品情報で、該当商品の商品ヘッダを更新します。
   * <ol>
   * <li>引数で受け取った商品情報にあるショップコードと商品コードを元に、商品ヘッダの存在チェックをします。</li>
   * <li>商品情報が存在しなければ、データ未存在エラーを返します。</li>
   * <li>引数で受け取った商品情報にある代表SKUコードを元に、商品明細の存在チェックをします。</li>
   * <li>代表SKU情報が存在しなければ、データ未存在エラーを返します。</li>
   * <li>商品情報および代表SKU情報が存在する場合は、商品情報を更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>headerがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>商品ヘッダの更新に成功した場合は、サービス処理結果に何も設定しません。</dd>
   * <dd>商品ヘッダの更新に失敗した場合は、トランザクションをロールバックし、サービス処理結果にエラーの理由を設定します。</dd>
   * <dd>商品ヘッダが更新されます。。</dd>
   * </dl>
   * </p>
   * 
   * @param header
   *          商品ヘッダ情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateCommodityHeader(CommodityHeader header);

  /**
   * 指定したカテゴリ属性番号に合致するカテゴリ属性を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <ol>
   * <li>トランザクションを開始します。</li>
   * <li>カテゴリ属性テーブルから、引数で受け取ったカテゴリコードとカテゴリ属性番号を持つデータをすべて削除します。</li>
   * <li>指定したカテゴリ属性番号に合致するカテゴリ属性値を削除します。</li>
   * <li>カテゴリ属性値テーブルから、引数で受け取ったカテゴリコードとカテゴリ属性番号を持つデータをすべて削除します。</li>
   * <li>トランザクションを終了します。</li> </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCodeがnullでないこと。</dd>
   * <dd>categoryAttributeNoがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>カテゴリ属性の更新処理及びカテゴリ属性値の削除処理に成功した場合は、サービス処理結果に何も設定しません。</dd>
   * <dd>カテゴリ属性の更新処理及びカテゴリ属性値の削除処理のいずれかでも失敗した場合は、トランザクションをロールバックし、<br />
   * サービス処理結果にエラーの理由を設定します。</dd>
   * <dd>カテゴリ属性テーブルからデータが削除されます。</dd>
   * <dd>カテゴリ属性値テーブルからデータが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          カテゴリコード
   * @param categoryAttributeNo
   *          カテゴリ属性番号
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteCategoryAttributeValue(String categoryCode, Long categoryAttributeNo);

  /**
   * カテゴリ属性を1件登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カテゴリ属性を1件登録します。
   * <ol>
   * <li>引数で受け取ったカテゴリ属性情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったカテゴリ属性情報内のカテゴリコードをもとに、カテゴリの存在チェックをします。</li>
   * <li>カテゴリが存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取ったカテゴリ属性情報内のカテゴリコードとカテゴリ属性番号をもとに、カテゴリ属性テーブルを重複チェックをします。</li>
   * <li>カテゴリ属性テーブルに存在する場合は、重複エラーを返します。</li>
   * <li>カテゴリ属性情報を登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryAttributeValueがnullでないこと。</dd>
   * <dd>categoryAttributeValueのカテゴリコードがnullでないこと。</dd>
   * <dd>categoryAttributeValueのカテゴリ属性番号がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>カテゴリ属性情報に1件登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryAttributeValue
   *          カテゴリ属性値情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertCategoryAttributeValue(CategoryAttributeValue categoryAttributeValue);

  /**
   * 既存のカテゴリ属性値を1件更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受け取ったカテゴリ属性値に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったカテゴリ属性値内のカテゴリコードをもとに、カテゴリの存在チェックをします。</li>
   * <li>カテゴリが存在しない場合は、データ未存在エラーを返します。</li>
   * <li>
   * 引数で受け取ったカテゴリ属性値内のカテゴリコード・カテゴリ属性番号・ショップコード・商品コードをもとに、カテゴリ属性値テーブルの存在チェックをします。
   * </li>
   * <li>カテゴリ属性値テーブルに存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で取得したカテゴリ属性値情報で、既存のカテゴリ属性値テーブルを1件更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryAttributeValueがnullでないこと。</dd>
   * <dd>categoryAttributeValue内のカテゴリコードがnullでないこと。</dd>
   * <dd>categoryAttributeValue内のカテゴリ属性値番号がnullでないこと。</dd>
   * <dd>categoryAttributeValue内のショップコードがnullでないこと。</dd>
   * <dd>categoryAttributeValue内の商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>カテゴリ属性値テーブルが1件更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryAttributeValue
   *          カテゴリ属性値情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateCategoryAttributeValue(CategoryAttributeValue categoryAttributeValue);

  /**
   * カテゴリ陳列商品およびカテゴリ属性値を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カテゴリ陳列商品およびカテゴリ属性値を登録します。
   * <ol>
   * <li>引数で受け取ったカテゴリ陳列商品情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったカテゴリ属性値情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったカテゴリ陳列商品情報内のショップコードと商品コードをもとに、商品ヘッダの存在チェックをします。</li>
   * <li>商品ヘッダに商品が存在しない場合は、データ未存在エラーを返します。</li>
   * <li>トランザクション処理を開始します。</li>
   * <li>引数で受け取ったカテゴリ陳列商品情報内のカテゴリコード・ショップコード・商品コードをもとに、カテゴリ陳列商品内を検索します。</li>
   * <li>カテゴリ陳列商品内に存在しない場合は、引数で受け取ったカテゴリ陳列商品情報を登録します。</li>
   * <li>
   * 引数で受け取ったカテゴリ属性値情報内のカテゴリコード・カテゴリ属性番号・ショップコード・商品コードをもとに、カテゴリ属性値テーブル内を検索します。</li>
   * <li>カテゴリ属性値テーブル内に存在しない場合は、引数で受け取ったカテゴリ属性値情報を登録します。</li>
   * <li>トランザクション処理を終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>
   * <ol>
   * <li>categoryCommodityがnullでないこと</li>
   * <li>categoryCommodity内のカテゴリコードがnullでないこと</li>
   * <li>categoryCommodity内のショップコードがnullでないこと</li>
   * <li>categoryCommodity内の商品コードがnullでないこと</li>
   * <li>categoryAttributeValueがnullでないこと</li>
   * <li>categoryAttributeValue内のカテゴリコードがnullでないこと</li>
   * <li>categoryAttributeValue内のカテゴリ属性番号がnullでないこと</li>
   * <li>categoryAttributeValue内のショップコードがnullでないこと</li>
   * <li>categoryAttributeValue内の商品コードがnullでないこと</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>カテゴリ情報の登録、およびカテゴリ属性情報の登録に成功した場合は、サービスの処理結果に何も設定しません。</dd>
   * <dd>カテゴリ情報の登録、およびカテゴリ属性情報の登録のいずれかでも失敗した場合は、サービスの処理結果にエラーの理由を設定します。</dd>
   * <dd>カテゴリ陳列商品情報が登録されます。</dd>
   * <dd>カテゴリ属性値情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCommodity
   *          カテゴリ陳列商品情報
   * @param categoryAttributeValue
   *          カテゴリ属性値情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult registerCategoryCommodity(CategoryCommodity categoryCommodity, List<CategoryAttributeValue> categoryAttributeValue);

  /**
   * 指定されたカテゴリ属性値を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定されたカテゴリ属性値を更新します。
   * <ol>
   * <li>引数で受け取ったカテゴリ属性値情報に対して、Validationチェックをします。</li>
   * <li>引数で受け取ったカテゴリ属性値情報内のカテゴリコードをもとに、カテゴリの存在チェックをします。</li>
   * <li>カテゴリが存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取ったカテゴリ属性値情報内のショップコードと商品コードをもとに、商品ヘッダに商品の存在チェックをします。</li>
   * <li>商品ヘッダに商品が存在しない場合は、データ未存在エラーを返します。</li>
   * <li>トランザクション処理を開始します。</li>
   * <li>カテゴリ属性値情報内のカテゴリコード・カテゴリ属性値番号・ショップコード・商品コードをもとにカテゴリ属性値テーブルを検索します。</li>
   * <li>カテゴリ属性値テーブルに存在しない場合は、引数で受け取ったカテゴリ属性値情報を登録します。<br />
   * カテゴリ属性値テーブルに存在する場合は、引数で受け取ったカテゴリ属性値情報を更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryAttributeValueがnullでないこと。</dd>
   * <dd>categoryAttributeValue内のカテゴリコードがnullでないこと。</dd>
   * <dd>categoryAttributeValue内のカテゴリ属性値番号がnullでないこと。</dd>
   * <dd>categoryAttributeValue内のショップコードがnullでないこと。</dd>
   * <dd>categoryAttributeValue内の商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>カテゴリ属性の登録・更新処理のいずれでも失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>カテゴリ属性値テーブルに、カテゴリ属性値情報が登録または更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryAttributeValue
   *          カテゴリ属性値情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateCategoryAttributeValue(List<CategoryAttributeValue> categoryAttributeValue);

  /**
   * 指定したカテゴリに関連付いている商品とカテゴリ属性値のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリに関連付いている商品とカテゴリ属性値のリストを取得します。
   * <ol>
   * <li>引数で受け取ったショップコード・カテゴリコード・商品コードをもとに、カテゴリ属性値テーブルと商品ヘッダを検索します。</li>
   * <li>検索結果をもとに商品とカテゴリ属性値のリストを生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>categoryCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param categoryCode
   *          カテゴリコード
   * @param commodityCode
   *          商品コード
   * @return 生成した商品とカテゴリ属性値のリストを返します。
   */
  List<RelatedCategory> getCategoryAttributeValueList(String shopCode, String categoryCode, String commodityCode);

  /**
   * 指定したカテゴリコードに関連付いている、カテゴリ属性の一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリコードに関連付いている、カテゴリ属性の一覧を取得します。
   * <ol>
   * <li>引数で受け取ったカテゴリコードをもとに、カテゴリ属性値テーブルと商品ヘッダを検索します。</li>
   * <li>検索結果をもとに商品とカテゴリ属性のリストを生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCode がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          カテゴリコード
   * @return 生成したカテゴリ属性のリストを返します。
   */
  List<CategoryAttribute> getCategoryAttributeList(String categoryCode);

  /**
   * 指定したカテゴリ属性値を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリ属性値を1件取得します。
   * <ol>
   * <li>引数で指定されたカテゴリコード・カテゴリ属性番号・ショップコード・商品コードをもとに、カテゴリ属性値テーブルと商品ヘッダを検索します。</li>
   * <li>検索結果をもとに商品とカテゴリ属性のリストを生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCodeがnullでないこと。</dd>
   * <dd>categoryAttributeNoがnullでないこと。</dd>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          カテゴリコード
   * @param categoryAttributeNo
   *          カテゴリ属性番号
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成したカテゴリ属性値を返します。
   */
  CategoryAttributeValue getCategoryAttributeValue(String categoryCode, Long categoryAttributeNo, String shopCode,
      String commodityCode);

  /**
   * 指定したカテゴリに関連付いている商品のカテゴリ属性およびカテゴリ属性値を一括で削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリに関連付いている商品のカテゴリ属性およびカテゴリ属性値を一括で削除します。
   * <ol>
   * <li>引数で受け取ったカテゴリコード・ショップコード・商品コードをもとに、カテゴリ陳列商品の存在チェックをします。</li>
   * <li>カテゴリ陳列商品に存在しなければ、データ未存在エラーを返します。</li>
   * <li>トランザクション処理を開始します。</li>
   * <li>カテゴリ属性値テーブルから、引数で受け取ったカテゴリコード・ショップコード・商品コードを持つデータを削除します。</li>
   * <li>カテゴリ陳列商品から、引数で受け取ったカテゴリコード・ショップコード・商品コードを持つデータを削除します。</li>
   * <li>トランザクション処理を終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCodeがnullでないこと。</dd>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての削除処理に成功した場合は、トランザクションをコミットします。</dd>
   * <dd>いずれかの削除処理に失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>カテゴリ陳列商品から、指定したカテゴリ陳列商品情報が1件削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          カテゴリコード
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteCategoryAttributeValue(String categoryCode, String shopCode, String commodityCode);

  /**
   * 受け取ったカテゴリ陳列商品で、登録済みのカテゴリ陳列商品を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受け取ったカテゴリ陳列商品で、登録済みのカテゴリ陳列商品を更新します。
   * <ol>
   * <li>引数で受け取ったカテゴリ陳列商品のリストに対して、Validationチェックをします。</li>
   * <li>トランザクション処理を開始します。</li>
   * <li>カテゴリ陳列商品内から、引数で受け取ったカテゴリ陳列商品情報内のショップコードと商品コードを持つデータを削除します。</li>
   * <li>引数で受け取ったカテゴリ陳列商品リスト内のすべてのカテゴリ陳列商品情報を、カテゴリ陳列商品テーブルに登録します。</li>
   * <li>トランザクション処理を終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCommodityがnullでないこと。</dd>
   * <dd>categoryCommodity内のカテゴリコードがnullでないこと。</dd>
   * <dd>categoryCommodity内のショップコードがnullでないこと。</dd>
   * <dd>categoryCommodity内の商品コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての削除および登録に成功した場合は、トランザクションをコミットします。</dd>
   * <dd>削除および登録処理のいずれかでも失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>カテゴリ陳列商品に、カテゴリ陳列商品情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCommodity
   *          カテゴリ陳列商品情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult registerCategoryCommodity(List<CategoryCommodity> categoryCommodity);

  /**
   * 指定した商品に関連付いているカテゴリ陳列商品情報を全て削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に関連付いているカテゴリ陳列商品情報を全て削除します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードをもとにカテゴリ陳列商品を検索します。</li>
   * <li>検索結果をもとにカテゴリ陳列商品DTOのリストを生成します。</li>
   * <li>トランザクションを開始します。</li>
   * <li>カテゴリ陳列商品DTOのリストの数だけ、以降の処理を繰り返します。</li>
   * <li>カテゴリ陳列商品DTOのリストから1件取得します。</li>
   * <li>カテゴリ陳列商品テーブルから、取得したカテゴリ陳列商品DTO内のショップコードと商品コードを持つカテゴリ陳列商品情報を削除します。</li>
   * <li>トランザクションを終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての削除に成功した場合は、トランザクションをコミットします。</dd>
   * <dd>いずれかでも削除に失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>カテゴリ陳列商品から、カテゴリ陳列商品情報が削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return サービスの処理結果を返します。
   */
  ServiceResult deleteCategoryCommodity(String shopCode, String commodityCode);

  /**
   * 指定した商品に関連付いているカテゴリ陳列商品の一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に関連付いているカテゴリ陳列商品の一覧を取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードをもとに、カテゴリ陳列商品を検索します。</li>
   * <li>検索結果をもとにカテゴリ陳列商品のリスト生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成した一覧を返します。
   */
  List<CategoryCommodity> getCategoryCommodityList(String shopCode, String commodityCode);

  /**
   * 指定したカテゴリに関連付いている指定したショップの商品の一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリに関連付いている指定したショップの商品の一覧を取得します。
   * <ol>
   * <li>引数で指定した検索条件をもとに、カテゴリ陳列商品テーブル・カテゴリ属性テーブル・商品ヘッダを検索します。</li>
   * <li>検索条件に指定した値を元に、指定したカテゴリに関連付いている商品の一覧を取得します。</li>
   * <li>検索結果をもとに商品の一覧情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のカテゴリコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成したカテゴリに関連付いている商品の一覧を返します。
   */
  SearchResult<CategoryAttributeHeader> getCategoryAttributeValueHeader(RelatedCategorySearchCondition condition);

  /**
   * 指定したカテゴリコードに関連付いているカテゴリ情報とその配下のカテゴリのカテゴリ情報のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリコードに関連付いているカテゴリ情報とその配下のカテゴリのカテゴリ情報のリストを取得します。
   * <ol>
   * <li>引数で受け取ったカテゴリコードを持つカテゴリを検索します。</li>
   * <li>引数で受け取ったパスを持つカテゴリを検索します。</li>
   * <li>カテゴリコードとパスの検索結果をもとに、カテゴリ情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCodeがnullでないこと。</dd>
   * <dd>categoryPathがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          カテゴリコード
   * @param categoryPath
   *          カテゴリパス
   * @return 生成したカテゴリ情報を返します。
   */
  List<Category> getCategoryListFromPath(String categoryCode, String categoryPath);

  /**
   * 入出庫情報をもとに入出庫情報の登録および在庫数の更新をします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入出庫情報をもとに入出庫情報の登録および在庫数の更新をします。
   * <ol>
   * <li>引数で受け取った入出庫明細情報にあるショップコードとSKUコードをもとに、在庫テーブルの存在チェックをします。</li>
   * <li>在庫が存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った入出庫明細情報にあるショップコードとSKUコードをもとに、商品明細テーブルの存在チェックをします。</li>
   * <li>商品明細が存在しない場合は、データ未存在エラーを返します。</li>
   * <li>商品明細情報内のショップコードと商品コードをもとに、商品ヘッダの存在チェックをします。</li>
   * <li>商品ヘッダが存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った入出庫明細情報を在庫更新用DTOにコピーします。</li>
   * <li>トランザクションを開始します。</li>
   * <li>引数で受け取った入出庫明細情報内の入庫区分に応じて、入庫と出庫の処理を振り分けます。<br />
   * 入出庫区分の値が0の場合：入庫登録します。<br />
   * 入出庫区分の値が1の場合：出庫登録します。<br />
   * 入出庫区分の値が0でも1でもない場合：入力値妥当性エラーを返します。</li>
   * <li>トランザクションを終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>detailがnullでないこと。</dd>
   * <dd>detail内のショップコードがnullでないこと。</dd>
   * <dd>detail内のSKUコードがnullでないこと。</dd>
   * <dd>detail内の入出庫区分がnullでないこと。</dd>
   * <dd>detail内の入出庫数量がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>入出庫情報の登録および在庫情報の更新に成功した場合は、トランザクションをコミットします。</dd>
   * <dd>入出庫情報の登録または在庫情報の更新のいずれかに失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>入出庫履歴が登録されます。</dd>
   * <dd>在庫が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param detail
   *          入出庫明細情報
   * @return サービスの処理結果を返します。
   */
  ServiceResult insertStockIO(StockIODetail detail);

  /**
   * 指定したショップコードに関連付いている商品レイアウト情報の一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップコードに関連付いている商品レイアウト情報の一覧を取得します。
   * <ol>
   * <li>引数で受け取ったショップコードをもとに商品レイアウトテーブルを検索し、<br />
   * 該当のショップコードを持つ商品レイアウトの一覧を取得します。</li>
   * <li>取得結果をもとに、商品レイアウトの一覧を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 商品レイアウトの一覧を返します。
   */
  List<CommodityLayout> getCommodityLayoutList(String shopCode);

  /**
   * 指定したパーツコードに関連付いている商品レイアウト情報を1件取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップのパーツコードに該当する商品レイアウト情報を1件取得します。
   * <ol>
   * <li>引数で受け取ったショップコードとパーツコードをもとに商品レイアウトテーブルを検索します。</li>
   * <li>検索結果をもとに、商品レイアウト情報を生成して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>partsCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param partsCode
   *          パーツコード
   * @return 生成した商品レイアウト情報を返します。
   */
  CommodityLayout getCommodityLayout(String shopCode, String partsCode);

  /**
   * 引数で受け取った商品レイアウト情報で、商品レイアウト情報テーブルを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った商品レイアウト情報で、商品レイアウト情報テーブルを更新します。
   * <ol>
   * <li>引数で受け取った商品レイアウトのリストに対して、Validationチェックをします。</li>
   * <li>トランザクションを開始します。</li>
   * <li>引数で受け取った商品レイアウトのリストを1件ずつ取得して更新します。</li>
   * <li>トランザクションを終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>layoutListがnullでないこと。</dd>
   * <dd>layoutList内のショップコードがnullでないこと。</dd>
   * <dd>layoutList内のパーツコードがnullでないこと。</dd>
   * <dd>layoutList内の表示順がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>すべての更新処理が成功し場合は、トランザクションをコミットします。</dd>
   * <dd>更新処理が1件でも失敗し場合は、トランザクションをロールバックします。</dd>
   * <dd>商品レイアウト情報が更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param layoutList
   *          商品レイアウト情報リスト
   * @return サービス実行結果を返します。
   */
  ServiceResult registerCommodityLayout(List<CommodityLayout> layoutList);

  /**
   * 商品レイアウト情報を初期化します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>商品レイアウト情報を初期化します。
   * <ol>
   * <li>初期化用商品レイアウト情報を生成します。</li>
   * <li>トランザクションを開始します。</li>
   * <li>引数で受け取ったショップコードが持つ商品レイアウト情報を全て削除します。</li>
   * <li>初期化用に作成したしょうひインレイアウト情報を登録します。</li>
   * <li>トランザクションを終了します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>削除および登録処理が全て成功した場合は、トランザクションをコミットします。</dd>
   * <dd>削除および登録処理のいずれかでも失敗した場合は、トランザクションをロールバックします。</dd>
   * <dd>商品レイアウト情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return サービスの処理結果を返します。
   */
  ServiceResult resetCommodityLayout(String shopCode);

  /**
   * 指定した検索条件に該当し、かつ販売可能な条件を満たしたSKUの一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品コードに関連付き、かつ販売可能な条件を満たしたSKUの一覧を取得します。<br />
   * 販売可能な条件を満たした商品は以下の条件を満たすものとします。
   * <ol>
   * <li>システム日付が販売期間または予約期間内であること。</li>
   * <li>販売フラグが"1"(販売中)であること。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>取得結果を元に商品情報の一覧を生成します。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した商品情報の一覧を返します。
   */
  SearchResult<CommodityContainer> getCommoditySearch(CommodityListSearchCondition condition);

  /**
   * 指定したカテゴリの直下に関連付いているカテゴリ情報の一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリの直下に関連付いているカテゴリ情報の一覧を取得します。
   * <ol>
   * <li>引数で受け取ったカテゴリコードをもとにカテゴリテーブルを検索して、<br />
   * 受け取ったカテゴリコードのカテゴリ情報とそのカテゴリの配下に登録されているカテゴリ情報を検索します。</li>
   * <li>検索結果をもとにカテゴリ情報リストを生成して返します。</li>
   * <li>取得結果は、階層・表示順の昇順に並べ替えます。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>categoryCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          カテゴリコード
   * @return 生成した一覧を返します。
   */
  List<Category> getSubCategoryTree(String categoryCode);

  /**
   * 指定した商品に関連付いている手動リコメンドの商品情報の一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に関連付いている手動リコメンドの商品情報の一覧を取得します。
   * <ol>
   * <li>システム日付が、販売期間または予約期間内であり、かつ販売フラグが販売中である商品の一覧を取得します。</li>
   * <li>表示クライアントタイプを指定し、PCまたはMOBILEのいずれかを取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>検索条件に表示クライアントタイプが指定されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した一覧を返します。
   */
  SearchResult<CommodityContainer> getRecommendAList(CommodityContainerCondition condition);

  /**
   * 指定した商品に関連付いている自動リコメンドの商品情報の一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品に関連付いている自動リコメンドの商品情報の一覧を取得します。
   * <ol>
   * <li>システム日付が、販売期間または予約期間内であり、かつ販売フラグが販売中である商品の一覧を取得します。</li>
   * <li>表示クライアントタイプを指定し、PCまたはmobileのいずれかを取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>検索条件に表示クライアントタイプが指定されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した一覧を返します。
   */
  SearchResult<CommodityContainer> getRecommendBList(CommodityContainerCondition condition);

  /**
   * 指定した商品コードに関連付いているSKUの一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品コードに関連付いているSKUの一覧を取得します。
   * <dd>isForSale がtrueの場合、
   * {@link CatalogService#getCommoditySkuList(String, String, boolean, DisplayClientType)}
   * の返す結果と等価となります。
   * <dd>isForSale がfalseの場合、SKUが販売可能かどうかの判断を行わず、該当するSKUすべてを返します。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成した商品情報の一覧を返します。
   */
  List<CommodityContainer> getCommoditySkuList(String shopCode, String commodityCode, boolean isForSale,
      DisplayClientType displayClientType);

  /**
   * 指定した集計期間内の受注データをもとに、商品のランキングを集計します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>注文された商品数および注文金額の、両方のランキングを集計します。
   * <ol></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>monthが0以下でないこと。</dd>
   * <dd>指定した集計期間内に、注文が行われていること。</dd>
   * <dd>updatedUserがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>集計結果を登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param month
   *          集計対象期間(月)
   * @return サービスの処理結果を返します。
   */
  ServiceResult generateRankingSummary(int month);

  /**
   * 指定したショップの商品に関連付いているタグの一覧を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップの商品に関連付いているタグの一覧を取得します。
   * <ol></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>commodityCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>取得結果を元にタグコードとタグ名称の一覧を生成します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   * @param commodityCode
   * @return 生成した一覧を返します。
   */
  List<CodeAttribute> getTagCommodityList(String shopCode, String commodityCode);

  /**
   * 受注データを集計し、自動リコメンドデータを生成します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>システム日付から、引数に指定された月数を引いた期間内の受注データを集計対象とします。</li>
   * <li>顧客別に、該当期間内で購入した商品の一覧を取得します。</li>
   * <li>2で取得した商品を、それぞれ関連商品とし、自動リコメンドテーブルに登録します。</li>
   * <li>3で、既に関連商品データが登録されていた場合は、<BR>
   * 該当レコードのランキング点数を1加算します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>該当期間内に受注データが存在すること。</dd>
   * <dd>month がnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>自動リコメンドテーブルに関連商品データが生成されます。</dd>
   * </dl>
   * </p>
   * 
   * @param month
   *          月数
   * @return サービスの処理結果を返します。
   */
  ServiceResult generateAutoRecommendSummary(String month);

  /**
   * カテゴリに関連付いている商品数を集計します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>あるカテゴリ、およびその子(・孫)カテゴリに関連付いている商品数を集計します。</li>
   * <li>下記の条件を満たす商品を集計対象とします。
   * <ul>
   * <li>販売フラグが販売中</li>
   * <li>販売期間中または予約期間中</li>
   * <li>関連づいている配送種別が表示可能</li>
   * </ul>
   * </li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>集計結果を登録します。</dd>
   * </dl>
   * </p>
   * 
   * @return サービスの処理結果を返します。
   */
  ServiceResult generateCategorySummary();

  /**
   * 指定した価格改定日が設定された商品の商品単価を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した価格改定日が設定された商品の商品単価を更新します。
   * <dd>価格改定日が、指定した価格改定日と一致する商品の商品単価を、改定価格に更新します。</dd>
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>revisionDateがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>商品単価が改定価格で更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param revisionDate
   *          改定日
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateRevisionPricing(String revisionDate);

  /**
   * 入荷お知らせメールの送信が可能な入荷お知らせのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入荷お知らせメールの送信が可能な入荷お知らせのリストを取得します。
   * <ol>
   * <li>「入荷お知らせメールを送信する」「在庫管理区分が在庫管理する」「販売状態が販売中」「有効在庫が1以上」を満たす商品のリストを検索します。</li>
   * <li>検索条件に合致する商品のリストを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 生成した入荷お知らせの一覧を返します。
   */
  List<ArrivalGoods> getIntendedArrivalGoods();

  /**
   * 指定した商品明細の在庫情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った指定したショップコード、SKUコードにをもとに在庫情報を取得します。
   * <dd>該当する在庫情報が存在しない場合は空の在庫情報を返します。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>取得結果を元に、在庫情報を生成します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return 生成した在庫情報を返します。
   */
  Stock getStock(String shopCode, String skuCode);

  /**
   * 販売期間内かつ有効在庫のある商品明細のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>販売期間内かつ有効在庫のある商品のリストを取得します。
   * <ol>
   * <li>予約終了日が現在日付よりも前、かつ有効在庫のある商品明細のリストを返します。</li>
   * <li>商品明細リストは、順不同で返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 生成した商品情報の一覧を返します。
   */
  List<CommodityDetail> getChangeableReservationToOrderCommodity();

  /**
   * 販売期間内かつ有効在庫のある商品のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>販売期間内かつ有効在庫のある商品のリストを取得します。
   * <ol>
   * <li>予約終了日が引数で指定された日付よりも前、かつ有効在庫のある商品のリストを返します。</li>
   * <li>商品明細リストは、順不同返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>availableDateがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 生成した商品情報の一覧を返します。
   */
  List<CommodityDetail> getChangeableReservationToOrderCommodity(String availableDate);

  /**
   * 指定した商品明細に登録されている入荷お知らせのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した商品明細に登録されている入荷お知らせのリストを取得します。
   * <ol>
   * <li>引数で指定したショップコードとSKUコードを元に、登録されている入荷お知らせのリストを取得します。</li>
   * <li>検索した入荷お知らせのリストは、ショップコードの昇順、SKUコードの昇順に返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return 生成した入荷お知らせ情報の一覧を返します。
   */
  List<ArrivalGoods> getArrivalGoodsList(String shopCode, String skuCode);

  /**
   * 指定した検索条件を満たした商品のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した検索条件を満たした商品のリストを取得します。
   * <ol>
   * <li>指定した検索条件を元に商品情報を検索します。</li>
   * <li>検索ワードが入力された場合は、商品名、商品コード、検索キーワードに対して部分一致検索を行います。</li>
   * <li>商品名が入力された場合は、商品名の部分一致検索を行います。</li>
   * <li>ショップコード、商品コード、キャンペーンコード、タグコードが入力された場合は、完全一致検索を行います。</li>
   * <li>SKUコードが入力された場合は、前方一致検索を行います。</li>
   * <li>販売価格開始、販売価格終了が入力された場合は、販売価格の範囲検索を行います。</li>
   * <li>カテゴリコードが入力された場合は、指定されたカテゴリとその配下のカテゴリのカテゴリコードの完全一致検索を行います。</li>
   * <li>取得した商品のリストは、検索条件で指定した並び順で返します。</li>
   * <li>検索条件で並び順を指定しなかった場合は、人気順に並べ替えて返します。</li>
   * <li>指定した並び順が同値の場合は、商品コードの昇順に並べ替えて返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した商品情報の一覧を返します。
   */
  SearchResult<CommodityContainer> getCommodityContainer(CommodityContainerCondition condition);

  /**
   * 検索条件を指定して商品を検索します。SKU単位の指定はできませんが、getCommodityContainerよりパフォーマンス面で有利です。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した検索条件を満たした商品のリストを取得します。
   * <ol>
   * <li>指定した検索条件を元に商品情報を検索します。</li>
   * <li>検索ワードが入力された場合は、商品名、商品コード、検索キーワードに対して部分一致検索を行います。</li>
   * <li>商品名が入力された場合は、商品名の部分一致検索を行います。</li>
   * <li>ショップコード、商品コード、キャンペーンコード、タグコードが入力された場合は、完全一致検索を行います。</li>
   * <li>SKUコードが入力された場合は、前方一致検索を行います。</li>
   * <li>販売価格開始、販売価格終了が入力された場合は、販売価格の範囲検索を行います。</li>
   * <li>カテゴリコードが入力された場合は、指定されたカテゴリとその配下のカテゴリのカテゴリコードの完全一致検索を行います。</li>
   * <li>取得した商品のリストは、検索条件で指定した並び順で返します。</li>
   * <li>検索条件で並び順を指定しなかった場合は、人気順に並べ替えて返します。</li>
   * <li>指定した並び順が同値の場合は、商品コードの昇順に並べ替えて返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した商品情報の一覧を返します。
   */
  SearchResult<CommodityContainer> fastFindCommodityContainer(CommodityContainerCondition condition, boolean planDetailFlag);

  /**
   * 指定した検索条件を満たした商品のうち、代表SKUに指定されている商品のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した検索条件を満たした商品のうち、代表SKUに指定されている商品のリストを取得します。
   * <ol>
   * <li>指定した検索条件を元に商品情報を検索します。</li>
   * <li>検索ワードが入力された場合は、商品名、商品コード、検索キーワードに対して部分一致検索を行います。</li>
   * <li>商品名が入力された場合は、商品名の部分一致検索を行います。</li>
   * <li>ショップコード、商品コード、キャンペーンコード、タグコードが入力された場合は、完全一致検索を行います。</li>
   * <li>SKUコードが入力された場合は、前方一致検索を行います。</li>
   * <li>販売価格開始、販売価格終了が入力された場合は、販売価格の範囲検索を行います。</li>
   * <li>カテゴリコードが入力された場合は、指定されたカテゴリとその配下のカテゴリのカテゴリコードの完全一致検索を行います。</li>
   * <li>取得した商品のリストは、検索条件で指定した並び順で返します。</li>
   * <li>検索条件で並び順を指定しなかった場合は、人気順に並べ替えて返します。</li>
   * <li>指定した並び順が同値の場合は、商品コードの昇順に並べ替えて返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した商品情報の一覧を返します。
   */
  SearchResult<CommodityContainer> getCommodityContainerBySku(CommodityContainerCondition condition);

  /**
   * 指定顧客のおすすめ商品リストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定顧客のおすすめ商品リストを取得します。
   * <ol>
   * <li>引数の検索条件で指定され顧客コードを元に、おすすめ商品のリストを検索します。</li>
   * <li>取得したおすすめ商品のリストは、「登録日時の降順」「商品コードの昇順」で返します。</li>
   * <li>おすすめ商品のリストが存在しない場合は、「人気順の降順」「商品コードの降順」で並べ替えた商品の一覧を取得して返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のsearchCustomerCodeがnullでないこと。</dd>
   * <dd>condition内のdisplayClientTypeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した商品情報の一覧を返します。
   */
  SearchResult<CommodityContainer> getRecommendedCommodities(CommodityContainerCondition condition);

  /**
   * 指定顧客のお気に入り商品リストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定顧客のお気に入り商品リストを取得します。
   * <ol>
   * <li>引数の検索条件で指定され顧客コードを元に、お気に入り商品のリストを検索します。</li>
   * <li>取得したお気に入り商品のリストは、「登録日時の降順」「商品コードの昇順」で返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。</dd>
   * <dd>condition内のsearchCustomerCodeがnullでないこと。</dd>
   * <dd>condition内のdisplayClientTypeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 生成した商品情報の一覧を返します。
   */
  SearchResult<CommodityContainer> getFavoriteCommodities(CommodityContainerCondition condition);

  /**
   * 指定されたキャンペーンの対象商品の中で、そのキャンペーン以外が優先して適用されている商品のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受け取ったキャンペーン情報から、ショップコード、キャンペーンコード、キャンペーン値引率、キャンペーン開始日を取得します。</li>
   * <li>1で取得した値を、指定キャンペーンの対象商品の中から他キャンペーンが適用されている商品を取得するクエリに渡し、実行します。</li>
   * <li>クエリの実行結果であるキャンペーン商品リストを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>campaignがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>該当のキャンペーン商品リストが存在しない場合は、nullを返します。</dd>
   * </dl>
   * </p>
   * 
   * @param campaign
   *          キャンペーン情報
   * @return 取得したキャンペーン商品リストを返します。
   */
  List<CampaignCommodity> getExceptCampaignCommodityList(Campaign campaign);

  /**
   * 指定の商品が販売期間内かどうかをチェックします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で指定された商品が販売期間内かどうかをチェックし、販売期間内の場合はtrue、それ以外の場合はfalseを返します。
   * <ol>
   * <li>引数で受け取ったショップコード、skuコードを元に商品情報を取得します。</li>
   * <li>1で取得した商品情報がnullの場合、falseを返します。</li>
   * <li>引数で指定された商品が予約期間内かどうかをチェックし、予約期間内である場合はfalseを返します。</li>
   * <li>1で取得した商品情報の商品ヘッダより、販売期間開始日時と販売期間終了日時を取得します。</li>
   * <li>4で取得した販売期間開始日時と販売期間終了日時の期間が現在日時を含む期間である場合はtrue、<BR>
   * 現在日時を含まない期間である場合はfalseを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードがnullでないこと。</dd>
   * <dd>skuコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          skuコード
   * @return 販売期間内だった場合true、それ以外の場合はfalseを返します。<br />
   *         該当商品が存在しない場合、または該当商品が予約期間内である場合もfalseを返します。
   */
  boolean isSale(String shopCode, String skuCode);

  /**
   * 指定の商品が予約期間内かどうかをチェックします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で指定された商品が予約期間内かどうかをチェックし、予約期間内の場合はtrue、それ以外の場合はfalseを返します。
   * <ol>
   * <li>引数で受け取ったショップコード、skuコードを元に商品情報を取得します。</li>
   * <li>1で取得した商品情報がnullの場合、falseを返します。</li>
   * <li>1で取得した商品情報の商品ヘッダより、予約期間開始日時と予約期間終了日時を取得します。</li>
   * <li>3で取得した予約期間開始日時と予約期間終了日時の両方がnullの場合、falseを返します。</li>
   * <li>3で取得した予約期間開始日時と予約期間終了日時の期間が現在日時を含む期間である場合はtrue、<BR>
   * 現在日時を含まない期間である場合はfalseを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードがnullでないこと。</dd>
   * <dd>skuコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          skuコード
   * @return 予約期間内だった場合はtrue、それ以外の場合はfalseを返します。<br />
   *         該当商品が存在しない場合、または予約期間が設定されていない商品の場合もfalseが返します。
   */
  boolean isReserve(String shopCode, String skuCode);

  /**
   * キャンペーンの対象商品数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で指定されたキャンペーンが適用されている対象商品の数を取得し、取得結果を返します。
   * <ol>
   * <li>引数で受け取ったショップコード、キャンペーンコードを元にキャンペーンに関連付けられた商品数を取得します。<BR>
   * ただし、別のキャンペーンの対象商品であり、かつそのキャンペーンが適用されている商品についてはカウントしません。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>campaignCodeがnullでないこと。</dd>
   * <dd>displayClientTypeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param campaignCode
   *          キャンペーンコード
   * @param displayClientType
   *          表示対象のクライアント
   * @return 該当のキャンペーンに関連付けられた商品数を返します。
   */
  Long getCampaignCommodityCount(String shopCode, String campaignCode, String displayClientType);

  /**
   * 指定したショップコード、商品コード、およびカテゴリコードから展開されるカテゴリツリーを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップコード、商品コード、およびカテゴリコードから展開されるカテゴリツリーを取得します。</dd>
   * <dd>カテゴリツリーの生成パターンは、以下の場合によって分かれます。</dd>
   * <dd>
   * <ol>
   * <li>shopCode、commodityCode、categoryCodeの全てが与えられたとき</li>
   * <dl>
   * <dd>指定したカテゴリの子孫カテゴリの中で、ショップコードと商品コードに、一番深い階層で関連付いているカテゴリを取得</dd>
   * <dd>取得したカテゴリを現在位置とみなしたカテゴリツリーを展開</dd>
   * </dl>
   * <li>shopCode、commodityCodeが与えられたとき</li>
   * <dl>
   * <dd>ショップコードと商品コードに、一番深い階層で関連付いているカテゴリを取得</dd>
   * <dd>取得したカテゴリを現在位置とみなしたカテゴリツリーを展開</dd>
   * </dl>
   * <li>categoryCodeが与えられたとき</li>
   * <dl>
   * <dd>指定したカテゴリを現在位置とみなしたカテゴリツリーを展開</dd>
   * </dl>
   * <li>何も与えられなかったとき</li>
   * <dl>
   * <dd>2階層まで（ルートカテゴリとその子カテゴリ）のカテゴリツリーを展開</dd>
   * </dl>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>カテゴリツリーを取得します。</dd>
   * <dd>取得結果は、階層・表示順の昇順にツリー構造が形成されるように並べ替えます。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param categoryCode
   *          カテゴリコード
   * @return 生成したカテゴリツリーを返します。
   */
  List<CategoryData> getCategoryTree(String shopCode, String commodityCode, String categoryCode);

  /**
   * 指定したショップコード、商品コード、およびカテゴリコードから展開されるパンくずリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップコード、商品コード、およびカテゴリコードから展開されるパンくずリストを取得します。</dd>
   * <dd>パンくずリストの生成パターンは、以下の場合によって分かれます。</dd>
   * <dd>
   * <ol>
   * <li>shopCode、commodityCode、categoryCodeの全てが与えられたとき</li>
   * <dl>
   * <dd>指定したカテゴリの子孫カテゴリの中で、ショップコードと商品コードに、一番深い階層で関連付いているカテゴリを取得</dd>
   * <dd>取得したカテゴリを現在位置とみなしたパンくずリストを展開</dd>
   * </dl>
   * <li>shopCode、commodityCodeが与えられたとき</li>
   * <dl>
   * <dd>ショップコードと商品コードに、一番深い階層で関連付いているカテゴリを取得</dd>
   * <dd>取得したカテゴリを現在位置とみなしたパンくずリストを展開</dd>
   * </dl>
   * <li>categoryCodeが与えられたとき</li>
   * <dl>
   * <dd>指定したカテゴリを現在位置とみなしたパンくずリストを展開</dd>
   * </dl>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>パンくずリストを取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param categoryCode
   *          カテゴリコード
   * @return 生成したパンくずリストを返します。
   */
  List<CodeAttribute> getTopicPath(String shopCode, String commodityCode, String categoryCode);

  /**
   * ショップコード、SKUコード、数量を指定して、その商品が購入可能(予約可能)かどうかを返します。
   * <p>
   * 購入可能とは「販売中、あるいは予約期間中であること」「在庫が確保できること(引き当て可能)」の2つの条件を満たすことをいいます。
   * </p>
   * <p>
   * </p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <ol>
   * <li>ショップコード、SKUコード、数量の妥当性をチェックします。</li>
   * <li>ショップコード、SKUコードから商品を特定し、販売期間中(予約期間中)かどうかを調べます。</li>
   * <li>商品が販売期間中(予約期間中)である場合、指定された数量が引き当て可能かどうかを調べます。</li>
   * <li>引数isReserveがtrueの場合は予約商品として、falseの場合は通常商品として調べます。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードが有効であること</dd>
   * <dd>SKUコードが有効であること</dd>
   * <dd>数量が1以上であること</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。このメソッドを実行しても、在庫の引き当ては行われません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param quantity
   *          引き当て数量
   * @param isReserve
   *          予約期間フラグ
   * @return 商品が販売期間中(予約期間中)かつ引き当て可能である場合、
   *         {@link CommodityAvailability#AVAILABLE}を返します。
   */
  CommodityAvailability isAvailable(String shopCode, String skuCode, int quantity, boolean isReserve);

  // 2012/11/24 促销对应 ob add start
  /**
   * ショップコード、SKUコード、数量を指定して、その商品が購入可能(予約可能)かどうかを返します。
   * <p>
   * 購入可能とは「販売中、あるいは予約期間中であること」「在庫が確保できること(引き当て可能)」の2つの条件を満たすことをいいます。
   * </p>
   * <p>
   * </p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <ol>
   * <li>ショップコード、SKUコード、数量の妥当性をチェックします。</li>
   * <li>ショップコード、SKUコードから商品を特定し、販売期間中(予約期間中)かどうかを調べます。</li>
   * <li>商品が販売期間中(予約期間中)である場合、指定された数量が引き当て可能かどうかを調べます。</li>
   * <li>引数isReserveがtrueの場合は予約商品として、falseの場合は通常商品として調べます。</li>
   * <li>引数isSetがtrueの場合はセット商品として、falseの場合は通常商品として調べます。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードが有効であること</dd>
   * <dd>SKUコードが有効であること</dd>
   * <dd>数量が1以上であること</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。このメソッドを実行しても、在庫の引き当ては行われません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param quantity
   *          引き当て数量
   * @param isReserve
   *          予約期間フラグ
   * @param isSet
   *          セット品フラグ
   * @return 商品が販売期間中(予約期間中)かつ引き当て可能である場合、
   *         {@link CommodityAvailability#AVAILABLE}を返します。
   */
  CommodityAvailability isAvailable(String shopCode, String skuCode, int quantity, boolean isReserve, boolean isSet);

  // 2012/11/24 促销对应 ob add end

  // 2012/11/23 促销对应 ob add start
  /**
   * ショップコード、SKUコード、数量を指定して、その商品が購入可能かどうかを返します。
   * <p>
   * 購入可能とは「在庫が確保できること(引き当て可能)」の条件を満たすことをいいます。
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードが有効であること</dd>
   * <dd>SKUコードが有効であること</dd>
   * <dd>数量が1以上であること</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。このメソッドを実行しても、在庫の引き当ては行われません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param quantity
   *          引き当て数量
   * @return 商品が販売期間中(予約期間中)かつ引き当て可能である場合、
   *         {@link CommodityAvailability#AVAILABLE}を返します。
   */
  CommodityAvailability isAvailableGift(String shopCode, String skuCode, int quantity);

  CommodityAvailability isAvailableGift(String shopCode, String codeValue, int quantity, boolean isCommodityCode);

  // 2012/11/23 促销对应 ob add end

  // 10.1.4 K00175 追加 ここから
  /**
   * ショップコードと商品コード、数量を指定して、その商品に含まれる各SKUが購入可能(予約可能)かどうかを一括で返します。
   * <p>
   * 購入可能とは「販売中、あるいは予約期間中であること」「在庫が確保できること(引き当て可能)」の2つの条件を満たすことをいいます。
   * </p>
   * <p>
   * </p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <ol>
   * <li>ショップコード、商品コード、数量の妥当性をチェックします。</li>
   * <li>ショップコード、商品コードから商品を特定し、販売期間中(予約期間中)かどうかを調べます。</li>
   * <li>商品が販売期間中(予約期間中)である場合、指定された数量が引き当て可能かどうかを調べます。</li>
   * <li>引数isReserveがtrueの場合は予約商品として、falseの場合は通常商品として調べます。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードが有効であること</dd>
   * <dd>商品コードが有効であること</dd>
   * <dd>数量が1以上であること</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。このメソッドを実行しても、在庫の引き当ては行われません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param quantity
   *          引き当て数量
   * @param isReserve
   *          予約期間フラグ
   * @return Skuの購入可能状態(CommodityAvailability)ペアをjava.util.Map形式で返します。
   */
  Map<Sku, CommodityAvailability> getAvailablilityMap(String shopCode, String commodityCode, int quantity, boolean isReserve);

  // 10.1.4 K00175 追加 ここまで

  /**
   * ショップコード、商品コード指定して、その商品が公開されているかどうかを返します。
   * <p>
   * 「公開されている」とは「ショップが存在し、開店していること」「販売中、あるいは予約期間中であること」「販売停止でないこと」
   * 「1つ以上のカテゴリに関連付けられていること」「配送種別が公開設定であること」4条件を満たすものをいいます。
   * </p>
   * <p>
   * </p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <ol>
   * <li>ショップコード、商品コード、数量の妥当性をチェックします。</li>
   * <li>ショップコード、商品コードから商品を特定し、条件を満たすかどうかを調べます。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードが有効であること</dd>
   * <dd>商品コードが有効であること</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 商品が販売期間中(予約期間中)かつ引き当て可能である場合、trueを返します。
   */
  boolean isListed(String shopCode, String commodityCode);

  /**
   * 引数で受け取ったSKUコードの有効在庫数を返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったSKUコードの有効在庫数を返します。
   * <ol>
   * <li>引数で受け取ったショップコードとSKUコードを元に商品の存在チェックを行います。</li>
   * <li>商品が存在しない場合は0を返します。</li>
   * <li>商品が予約期間で、在庫管理区分に「2(在庫数表示する)」または「3(在庫状況表示する)」が設定されている商品は、「予約上限数 -
   * 予約数」の値を返します。予約上限数がNullの場合は-1を返します。</li>
   * <li>商品が予約以外の期間で、商品の在庫管理区分に「2(在庫数表示する)」または「3(在庫状況表示する)」が設定されている商品は、「在庫数 -
   * 引当数 - 予約数」の値を返します。</li>
   * <li>商品の在庫管理区分に「0(在庫管理しない)」または「1(在庫なし販売する)」が設定されている商品は、-1を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * <dd>受け取った「ショップコード + SKUコード」の商品が存在すること。</dd>
   * <dd>受け取った「ショップコード + SKUコード」の商品の在庫管理区分に「0」「1」「2」「3」のいずれかの値が設定されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return 指定した商品の現在引当可能な在庫数を返します。<BR>
   *         前提条件が満たされない場合は0を返します。
   */
  Long getAvailableStock(String shopCode, String skuCode);
  
  Long getUseSuitStock(String commodityCode);
  
  Long getUseSuitStockButThis(String commodityCode,String pareCommodityCode);

  Long getAvailableStockByCommodityCode(String shopCode, String commodityCode);

  // 2012/11/23 促销对应 ob add start
  /**
   * 引数で受け取ったSKUコードの有効在庫数を返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったSKUコードの有効在庫数を返します。
   * <ol>
   * <li>引数で受け取ったショップコードとSKUコードを元に商品の存在チェックを行います。</li>
   * <li>商品が存在しない場合は0を返します。</li>
   * <li>商品が予約期間で、在庫管理区分に「2(在庫数表示する)」または「3(在庫状況表示する)」が設定されている商品は、「予約上限数 -
   * 予約数」の値を返します。予約上限数がNullの場合は-1を返します。</li>
   * <li>商品が予約以外の期間で、商品の在庫管理区分に「2(在庫数表示する)」または「3(在庫状況表示する)」が設定されている商品は、「在庫数 -
   * 引当数 - 予約数」の値を返します。</li>
   * <li>商品の在庫管理区分に「0(在庫管理しない)」または「1(在庫なし販売する)」が設定されている商品は、-1を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param isSet
   *          true:套餐商品 false：通常商品
   * @param compositionSkuCodeList
   *          套餐明细商品的sku编号
   * @param giftSkuCodeList
   *          赠品的sku编号
   * @return 指定した商品の現在引当可能な在庫数を返します。<BR>
   *         前提条件が満たされない場合は0を返します。
   */
  Long getAvailableStock(String shopCode, String skuCode, boolean isSet, List<String> compositionSkuCodeList,
      List<String> giftSkuCodeList);

  // 2012/11/23 促销对应 ob add end

  /**
   * 引数で受け取った予約商品のSKUコードの有効在庫数を返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取った予約商品のSKUコードの有効在庫数を返します。
   * <ol>
   * <li>引数で受け取ったショップコードとSKUコードを元に商品の存在チェックを行います。</li>
   * <li>商品が存在しない場合は0を返します。</li>
   * <li>商品が予約期間で、在庫管理区分に「2(在庫数表示する)」または「3(在庫状況表示する)」が設定されている商品は、「予約上限数 -
   * 予約数」の値を返します。予約上限数がNullの場合は-1を返します。</li>
   * <li>商品が予約以外の期間の場合は0を返します。</li>
   * <li>商品の在庫管理区分に「0(在庫管理しない)」または「1(在庫なし販売する)」が設定されている商品は、-1を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * <dd>受け取った「ショップコード + SKUコード」の商品が存在すること。</dd>
   * <dd>受け取った「ショップコード + SKUコード」の商品の在庫管理区分に「0」「1」「2」「3」のいずれかの値が設定されていること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return 指定した商品の現在引当可能な在庫数を返します。<BR>
   *         前提条件が満たされない場合は0を返します。
   */
  Long getReservationAvailableStock(String shopCode, String skuCode);

  /**
   * 規格名称を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>規格名称を更新します。
   * <ol>
   * <li>引数で受け取った商品ヘッダを元に商品の存在チェックを行います。</li>
   * <li>商品が存在しない場合は商品未存在エラーを返します。</li>
   * <li>更新前の商品情報が「規格名称1と規格名称2が設定されている」、更新後の商品情報が「規格名称1のみ設定されている」場合は、<br>
   * 商品詳細の規格名称2を削除して、商品ヘッダの規格名称1を更新します。</li>
   * <li>規格名称2のみが設定されている場合は、規格名称2のみは更新できない旨のエラーメッセージを返します。</li>
   * <li>規格名称1と規格名称2が設定されている場合は、両方更新処理を行い、規格名称1のみ設定されている場合は、規格名称1のみを更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>headerがnullでないこと。</dd>
   * <dd>受け取った商品が存在すること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param header
   *          商品ヘッダ
   * @return サービス処理結果を返します。
   */
  ServiceResult updateCommodityStandardName(CommodityHeader header);

  // 10.1.7 10327 追加 ここから
  /**
   * 商品の規格数を変更できるかどうかを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>規格数変更前に、売上未確定の出荷データがないかどうか確認します。
   * <ol>
   * <li>引数で受け取ったショップコード、商品コードに該当する商品が存在するかどうかを調べます。</li>
   * <li>商品が存在しない場合はfalseを返します。</li>
   * <li>商品が存在する場合は出荷ヘッダ・出荷明細を参照し、該当商品を含む売上未確定の出荷データがあるかどうかを調べます。<br>
   * 売上未確定の出荷データとは「出荷ヘッダ.売上確定フラグ=0(未確定)」かつ「出荷ヘッダ.出荷ステータス!=4(キャンセル)」のデータを指します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコード、商品コードが存在すること</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 規格数を変更可能であればtrueを返します。
   */
  boolean canModifyStandardCount(String shopCode, String commodityCode);

  // 10.1.7 10327 追加 ここまで

  // 10.1.4 K00171 追加 ここから
  /**
   * 指定した商品が、個別の返品特約をもつかどうかを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>規格名称を更新します。
   * <ol>
   * <li>引数で受け取った商品ヘッダを元に商品の存在チェックを行います。</li>
   * <li>商品が存在しない場合は商品未存在エラーを返します。</li>
   * <li>更新前の商品情報が「規格名称1と規格名称2が設定されている」、更新後の商品情報が「規格名称1のみ設定されている」場合は、<br>
   * 商品詳細の規格名称2を削除して、商品ヘッダの規格名称1を更新します。</li>
   * <li>規格名称2のみが設定されている場合は、規格名称2のみは更新できない旨のエラーメッセージを返します。</li>
   * <li>規格名称1と規格名称2が設定されている場合は、両方更新処理を行い、規格名称1のみ設定されている場合は、規格名称1のみを更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコード、SKUコードによって特定される商品が存在すること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param skuSet
   *          SKU(ショップコードとSKUコード)の組。
   * @param isMobile
   *          携帯の場合はtrue
   * @return SKUと真偽値のセットをMap形式で返します。
   */
  Map<Sku, Boolean> getCommodityReturnPolicies(Set<Sku> skuSet, boolean isMobile);

  // 10.1.4 K00171 追加 ここまで
  /**
   * add by os012 20111219 start 用于品牌管理查询
   */
  SearchResult<Brand> getBrandSearch(BrandSearchCondition condition);

  // add by os012 20111219 end
  /**
   * add by os012 20111219 start 用于品牌管理登录
   */
  ServiceResult insertBrand(Brand brand);

  /**
   * add by os012 20111219 start 用于品牌管理更新查询
   */
  Brand getBrand(String shopCode, String brandCode);

  /**
   * add by os012 20111219 start 用于品牌管理更新
   */
  ServiceResult updateBrand(Brand brand);

  /**
   * * add by os012 20111219 start 用于品牌管理删除
   */
  ServiceResult deleteBrand(String shopCode, String brandCode);

  /**
   * add by os012 20111219 start 用于分类管理登录
   */
  ServiceResult insertCategoryinfo(CategoryInfo categoryInfo);

  // 20111214 ob add start
  /**
   * 获得所有品牌
   * 
   * @return 所有品牌
   */
  List<Brand> getAllBrand();

  // 20111214 ob add end

  // 20111214 lirong add start
  /**
   * 指定した顧客グループコードの顧客グループ情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した顧客グループコードの顧客グループ情報を取得します。
   * <ol>
   * <li>指定した顧客グループコードに該当する顧客グループ情報を取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerGroupCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>取得した顧客グループに関連付いている会員数を集計します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerGroupCode
   *          顧客グループコード
   * @return 顧客グループ
   */
  CustomerGroupCount getCustomerGroup(String customerGroupCode);

  // 20111214 lirong add end
  // add by os014 2011 12 26
  /**
   * 获取同期时间根据传入类型（0为EC最后一次同期时间 ，1 为Tmall最后一次同期时间）
   */
  public String getCychroTimeByType(String type);

  /**
   * 库存再分配批处理。add by os012 20111222 start
   * <p>
   * <dl>
   * <dt><b>处理概要: </b></dt>
   * <dd>根据shopcode、skucode、blCalculate执行库存再分配批处理</dd>
   * <ol>
   * <li></li>
   * <li></li>
   * <li>返回：0为分配成功，1为未分配，-1为error</li>
   * </ol>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>提供txMgr,stock,blCalculate</dd>
   * </dl>
   ** <dt><b>前提条件: </b></dt>
   * <dd>blCalculate</dd> </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return
   * @return 服务结果
   */
  int calculateStockByBatch(TransactionManager txMgr, Stock stock, boolean blCalculate);

  ServiceResult updateCategoryInfo(CategoryInfo categoryInfo);

  /**
   * 获取EC系统的同期化信息
   * 
   * @return
   */
  List<CCommodityHeader> getCynchEcInfo();

  /**
   * 获取TMALL系统的同期化信息
   * 
   * @return
   */
  List<CCommodityHeader> getCynchTmallInfo();
  
  // 2014/05/02 京东WBS对应 ob_姚 add start
  /**
   * 获取京东系统的同期化信息
   * 
   * @return
   */
  List<CCommodityHeader> getCynchJdInfo();
  // 2014/05/02 京东WBS对应 ob_姚 add end

  List<CCommodityCynchro> getCynchTInfo();

  /**
   * 获取同期化同期化履历信息
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public List getCynchHistoryInfo();

  /**
   * 执行EC系统同期化
   * 
   * @return 同期总条数，，成功条数 失败条数 Map
   */
  public CyncroResult executeCynchEc();

  public CyncroResult executeCynchEcByCheckBox(String[] commodityCodes);

  /**
   * 执行EC系统同期化
   * 
   * @return
   */
  public CyncroResult executeCynchTmallByCheckBox(String[] commodityCodes);

  /**
   * 同时执行EC和TMALL系统同期化
   * 
   * @return
   */
  public ServiceResult executeCynchAll();

  SearchResult<CommodityContainer> getCCommoditySearch(CommodityListSearchCondition condition);

  /***
   * add by os014 20111230 start. 分页查询同期化履历表 <dt><b>处理概要: </b></dt> <dd></dd>
   * <ol>
   * <li></li>
   * <li></li>
   * <li></li>
   * </ol>
   * </dl> </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>分页对象</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd></dd>
   * </dl>
   * </p>
   * add by os014 20111230 end
   * 
   * @return SearchResult
   */
  SearchResult<CSynchistory> getCynchroHiSearchResult(CommodityHistorySearchCondition contition);

  /***
   * add by os014 20111230 start. 分页查询同期化履历表 <dt><b>处理概要: </b></dt> <dd></dd>
   * <ol>
   * <li></li>
   * <li></li>
   * <li></li>
   * </ol>
   * </dl> </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: sync_flag_ec = 1 </b></dt>
   * <dd>分页对象</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd></dd>
   * </dl>
   * </p>
   * add by os014 20111230 end
   * 
   * @return SearchResult
   */
  SearchResult<CCommodityHeader> getCynchEcInfo(CommodityHistorySearchCondition contition);

  SearchResult<CCommodityCynchro> getCynchEInfo(CommodityHistorySearchCondition contition);

  /***
   * add by os014 20111230 start. 分页查询同期化Tmall表（c_commodity_header） <dt><b>处理概要:
   * </b></dt> <dd></dd>
   * <ol>
   * <li></li>
   * <li></li>
   * <li></li>
   * </ol>
   * </dl> </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: sync_flag_tmall = 1 </b></dt>
   * <dd>分页对象</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd></dd>
   * </dl>
   * </p>
   * add by os014 20111230 end
   * 
   * @return SearchResult
   */
  SearchResult<CCommodityHeader> getCynchTmallInfo(CommodityHistorySearchCondition contition);

  SearchResult<CCommodityCynchro> getCynchTInfo(CommodityHistorySearchCondition contition);
  
  // 2014/05/02 京东WBS对应 ob_姚 add start
  SearchResult<CCommodityCynchro> getCynchJdInfo(CommodityHistorySearchCondition contition);
  CyncroResult executeCynchJdByCheckBox(String[] commodityCodes);
  // 2014/05/02 京东WBS对应 ob_姚 add end

  // 20111228 os013 add start
  // StockList查询
  List<StockListSearchInfo> getStockListInfo(StockListSearchCondition condition);

  // 20111228 os013 add end
  // 20111230 os013 add start
  // StockList STOCK_THRESHOLD更新
  ServiceResult updateStockInfo(String shopCode, List<String> checkedCode);

  // 20111230 os013 add end
  
  // 2014/06/10 库存更新对应 ob_卢 add start
  ServiceResult updateStock(String shopCode, List<String> checkedCode, List<String> tmallApiFailCodeList,
      List<String> jdApiFailCodeList, List<String> stockFailCodeList, List<String> ecCynchroCodeList);
  // 2014/06/10 库存更新对应 ob_卢 add end
  /**
   * 应用于Tmall商品管理查询 add by os012 20111228 start
   * 
   * @param shopCode
   * @param commodityCode
   * @return
   */
  CommodityInfo getCCommodityInfo(String shopCode, String commodityCode);

  /**
   * * 应用于Tmall商品管理修改 add by os012 20111228 start
   * 
   * @param commodityInfo
   * @return
   */
  ServiceResult updateCCommodityInfo(CommodityInfo commodityInfo);

  /***
   * * 应用于Tmall商品管理商品详细修改 add by os012 20111228 start
   * 
   * @param sku
   * @param stock
   * @return
   */
  ServiceResult updateCCommoditySku(CCommodityDetail sku, Stock stock);

  /***
   * * 应用于Tmall商品管理商品详细获取 add by os012 20111228 start
   * 
   * @param shopCode
   * @param commodityCode
   * @return
   */
  List<CCommodityDetail> getCCommoditySku(String shopCode, String commodityCode);

  /***
   * * 应用于Tmall商品管理商品删除 add by os012 20111228 start
   * 
   * @param shopCode
   * @param commodityCode
   * @return
   */
  ServiceResult deleteCCommodity(String shopCode, String commodityCode);

  /**
   * 应用于Tmall商品管理商品关联SKU信息 add by os012 20111228 start
   * 
   * @param shopCode
   * @param skuCode
   * @return CommodityInfo
   */
  CommodityInfo getAboutSkuInfo(String shopCode, String skuCode);

  // add by wjw 20120103 start
  /**
   * 指定したショップコード、商品コードから展開されるパンくずリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップコード、商品コードから展開されるパンくずリストを取得します。</dd>
   * <dd>パンくずリストの生成パターンは、以下の場合によって分かれます。</dd>
   * <dd>
   * <ol>
   * <li>shopCode、commodityCodeの全てが与えられたとき</li>
   * <dl>
   * <dd>指定ショップコードと商品コードに、一番深い階層で関連付いているカテゴリを取得</dd>
   * <dd>取得したカテゴリを現在位置とみなしたパンくずリストを展開</dd>
   * </dl>
   * <li>shopCode、commodityCodeが与えられたとき</li>
   * <dl>
   * <dd>ショップコードと商品コードに、一番深い階層で関連付いているカテゴリを取得</dd>
   * <dd>取得したカテゴリを現在位置とみなしたパンくずリストを展開</dd>
   * </dl>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>パンくずリストを取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @return 生成したパンくずリストを返します。
   */
  List<CodeAttribute> getTopicPathByDetail(String shopCode, String commodityCode);

  SearchResult<CommodityContainer> getRecommendCList(CommodityContainerCondition condition);

  SearchResult<CCommodityHeadline> getCommodityExportSearch(String code, String searchExportObject);

  List<BrandData> getBrandList(CommodityContainerCondition condition);

  List<ReviewData> getReviewSummary(CommodityContainerCondition condition);

  Object getPriceCount(CommodityContainerCondition condition);

  List<AttributeData> getAttributeList(CommodityContainerCondition condition);

  Object getCategoryCount(String categoryPath, String brandCode);

  List<SalesChartsData> getSalesChartsList();

  List<SalesChartsData> getSalesChartsList(String categoryCode, String limit);

  List<SalesChartsData> getSalesChartsList(String categoryCode, String tag_no, String limit);

  SearchResult<CommodityContainer> getSalesChartsList(CommodityContainerCondition condition);

  List<PlanInfo> getPlanCommodityInfoList();

  
  

  /**
   * 查询商品sku最小价格 返回 min(purchase_price), min(unit_price), min(tmall_unit_price),
   * min(discount_price), min(tmall_discount_price) add by os014 2012-01-06
   */
  Map<String, String> findTmallDetailMinPrice(String commodityCode, String shopCode);

  /**
   * 查询类别属性 返回 add by os014 2012-01-06
   */
  List<TmallProperty> loadTmallPropertiesByCategoryId(String CategoryId);

  // add by os012 20110207 start
  List<TmallCategory> loadAllCategory();

  List<TmallCategory> loadAllCategory(String CategoryId);

  List<TmallCategory> loadAllChild(String CategoryId);

  List<TmallCategory> loadAllFather(String CategoryId);

  List<TmallCategory> loadAllFather();

  // add by os012 20110207 end
  /**
   * 查询子类目 add by os014 2012-01-06
   */
  List<TmallCategory> loadAllChildCategory();
  
  //2014/4/28 京东WBS对应 ob_李 add start
  /**
   * 查询子类目 add by os014 2012-01-06
   */
  List<JdCategory> loadAllChildJdCategory();
  //2014/4/28 京东WBS对应 ob_李 add end

  /**
   * 查询属性下的属性值 add by os014 2012-01-06
   */
  List<TmallPropertyValue> loadTmallPropertyValuesByPropertyId(String propertyId, String categoryId);

  /**
   * 查询商品的属性和属性值 add by os014 2012-01-07
   */
  List<TmallCommodityProperty> loadPropertyByCommodityCode(String commodityCode);

  /**
   * add by os012 20120106 start 淘宝已下订单 库存再分配
   * 
   * @param orderHds
   * @return
   */
  ServiceResult TmallStockAllocate();

  // add by os011 20120108 start

  /**
   * 淘宝已下订单 库存再分配
   * 
   * @param orderHds
   * @return
   */
  int TmallSku_Code_UP(String sku_code, Stock stock, Long onStockFlg, String modFlg);

  // add by os011 20120108 end
  List<CCommodityDetail> getSkuListByCommodityCode(String commodityCode, String shopCode);

  /**
   * add by os014 2012-01-09 更新c_commodity_header表
   * 
   * @param CcommodityHeader对象
   * @return 操作结果
   */
  ServiceResult updateCcheader(CCommodityHeader header);

  /**
   * add by os014 2012-01-09 添加c_commodity_header表
   * 
   * @param CcommodityHeader对象
   * @return 操作结果
   */
  ServiceResult addCcheader(CCommodityHeader header);

  // add by os011 20120110 start
  // 库存导入再计算
  int recalcStock(String fromStr);

  // 删除库存临时表信息
  void deleteStockTemp(String shop_Code, String sku_Code);

  // add by os011 20120110 end
  // 根据类目ID查询sku属性
  List<TmallProperty> loadSkuPropertyByCategoryId(String categoryId);

  /**
   * add by os014 2012-01-16 生成查询属性值的query对象
   * 
   * @param cid
   *          : 类目ID
   * @param pid
   *          : 属性ID
   * @param vids
   *          :属性值集合
   * @return query
   */
  void findValueName(String categoryId, PropertyKeys keys);

  /**
   * add by os014 2012-01-17 更新sku信息
   */
  void updateSku(CCommodityDetail detail) throws Exception;

  /**
   * add by os014 2012-01-17 查询cheader信息
   * 
   * @param shopCode
   *          : 店铺ID
   * @param commodityCode
   *          : 商品ID
   */
  CCommodityHeader getCCommodityheader(String shopCode, String commodityCode);

  /**
   * @param planType
   * @param planDetailType
   * @return
   */
  Plan getPlan(String planType, String planDetailType);

  /**
   * @param planDetailType
   *          企划明细区分（时间限定、期间限定）
   * @param planType
   *          企划类型（销售企划、特集企划）
   * @return
   */
  List<PlanInfo> getPlanCommodityInfoList(String planCode);

  /**
   * @param planDetailType
   *          企划明细区分（时间限定、期间限定）
   * @param planType
   *          企划类型（销售企划、特集企划）
   * @return
   */
  List<PlanInfo> getFeaturedCommodityInfoList(String planCode);

  /**
   * add by os014 2012-01-18 更新商品属性和属性值数据，对应表为：tmall_commodity_property
   * 
   * @param commodityId
   *          商品ID
   * @param shopCode
   *          店铺ID
   * @param keys
   *          属性与属性值集体对象
   * @return 更新结果
   */
  ServiceResult updateCommodityPropertys(String categoryId, String commodityId, String shopCode, PropertyKeys keys);

  /**
   * ショップコード,商品コードに関連付いている商品詳細の指定したタイプの価格の値を一括更新します。
   * <p>
   * <dl>
   * add by os014 2012-01-19
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップコード,商品コードに関連付いている商品詳細の指定したタイプの価格の値を一括更新します。
   * <ol>
   * <li>引数で受け取った価格に対して、Validationチェックを行います。</li>
   * <li>引数で受け取ったショップコードと商品コードをもとに、商品詳細のリストを取得します。</li>
   * <li>以降の処理を、商品詳細のリストの数だけ繰り返します。</li>
   * <li>商品詳細のリストから1件取得します。</li>
   * <li>引数で受け取ったタイプが商品単価の場合は、引数で受け取った価格を、1件取得した商品詳細の商品単価にセットします。<br />
   * 引数で受け取ったタイプが特別価格の場合は、引数で受け取った価格を、1件取得した商品詳細の特別価格にセットします。<br />
   * 引数で受け取ったタイプが予約価格の場合は、引数で受け取った価格を、1件取得した商品詳細の予約価格にセットします。<br />
   * 引数で受け取ったタイプが改定価格の場合は、引数で受け取った価格を、1件取得した商品詳細の改定価格にセットします。<br />
   * </li>
   * <li>1件取得した商品詳細で、商品詳細を更新します。</li>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと</dd>
   * <dd>commodityCodeがnullでないこと</dd>
   * <dd>typeがnullでないこと</dd>
   * <dd>priceがnullでないこと</dd>
   * <dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で受け取ったタイプに応じて、商品単価・特別価格・予約価格・改定価格を更新します。</dd>
   * <dd>※このメソッドは排他制御を考慮していません。これより前の変更を上書きします。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param type
   *          更新対象となる価格タイプ
   * @param price
   *          価格
   * @return サービスの処理結果を返します。
   */
  ServiceResult updateCcommodityPriceAll(String shopCode, String commodityCode, CommodityPriceType type, BigDecimal price);

  /**
   * @param categoryCode
   * @param brandCode
   * @param reviewScore
   * @param priceType
   * @param categoryAttribute1
   * @param searchWord
   * @return
   */
  List<LeftMenuListBean> getLeftMenuInfo(String selected,String categoryCode, String brandCode, String reviewScore, String priceType,
      String priceStart, String priceEnd, String categoryAttribute1, String categoryAttribute2, String categoryAttribute3,
      String searchWord);

  List<CodeAttribute> getBrandPath(String categoryCode, String brandCode);

  List<CodeAttribute> getParamPath(String categoryCode, String brandCode, String reviewScore, String priceType, String priceStart,
      String priceEnd, String categoryAttribute1, String categoryAttribute2, String categoryAttribute3, String searchWord);

  /**
   * add by os014 2012-01-31 更新c_commodity_header 作用于修改产品属性
   * 
   * @param header
   * @return
   */
  ServiceResult updateCheaderStandardDetailInfo(CCommodityHeader header);

  TmallBrand getTmallBrand(String tmallBrandCode);

  /**
   * 指定された商品SKUに関連付いている情報を一括で削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された商品SKUに関連付いている情報を一括で削除します。
   * <dd>ただし、次の条件に該当する場合は削除を行いません。
   * <ol>
   * <li>指定した商品が未入金の受注と関連付いていた場合</li>
   * <li>指定した商品が未出荷の受注と関連付いていた場合</li>
   * <li>指定した商品が予約済み商品である場合</li>
   * </ol>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと。</dd>
   * <dd>skuCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>下記のテーブルより、指定した商品SKUに関連付いている情報がされること。</dd>
   * <ol>
   * <li>商品明細</li>
   * <li>在庫</li>
   * <li>カテゴリ陳列商品</li>
   * <li>カテゴリ属性値</li>
   * <li>タグ対象商品</li>
   * <li>お気に入り商品</li>
   * <li>おすすめ商品</li>
   * <li>キャンペーン対象商品</li>
   * <li>ギフト対象商品</li>
   * <li>手動リコメンド</li>
   * <li>自動リコメンド</li>
   * <li>商品入荷お知らせ</li>
   * </ol>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return サービス処理結果
   */
  ServiceResult deleteCCommoditySku(String shopCode, String skuCode);

  // add by lc 2012-02-01 start
  /**
   * 根据条件查询顾客的收藏夹
   * 
   * @param customerCode
   *          顧客コード
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return FavoriteCommodity
   */
  FavoriteCommodity getFavoriteCommodity(String customerCode, String shopCode, String skuCode);

  // add by lc 2012-02-01 end

  // add by os 20120206 start
  /*
   * 商品图片上传batch处理
   * @param shopCode
   * @param errMsgList 错误信息列表
   */
  ServiceResult uploadImgBatch(String shopCode, List<String> errMsgList);

  ServiceResult uploadImgBatchForCampaign(String shopCode, List<String> errMsgList);

  /*
   * 从数据库查询所有生成的商品图片列表
   */
  List<ImageUploadHistory> getCommodityImgInfoList(String shopCode);

  /**
   * 根据条件查询商品图片上传履历
   * 
   * @param condition
   * @return
   */
  SearchResult<ImageUploadHistory> getCommodityImgUploadInfoList(CommodityImageSearchCondition condition);

  // add by os 20120206 end

  ReviewSummary getCommodityReviewSummary(String shopCode, String commodityCode);

  /**
   * add by os014 2012-02-14 更新c_commodity_header表 特价设置
   * 
   * @param CcommodityHeader对象
   * @return 操作结果
   */
  ServiceResult updateCcheader(CCommodityHeader header, String discountPrice, String tmallDiscountPrice,
      List<TmallCommodityProperty> propertys, String oldCommodityCode);
  
  //2014/4/28 京东WBS对应 ob_李 add start
  ServiceResult updateJdCommodityProperty(CCommodityHeader header, List<JdCommodityProperty> propertys);
  //2014/4/28 京东WBS对应 ob_李 add end

  /**
   *查询commodityCode在商品拡張情報中是否存在
   */
  boolean hasCommodityCode(String shopCode, String commodityCode);

  /**
   * add by os014 2012-02-20 插入商品属性到tmall_commodity_property表
   */
  ServiceResult updateTmallCommodityProperty(List<TmallCommodityProperty> propertys);

  public String getCommissionByCommodityCode(String commodityCodes);

  // 2012/11/16 促销对应 ob add start
  /**
   * 商品是否促销，促销时赠品库存验证
   */
  Long campaignAvailability(String commodityCode, String shopCode, List<GiftItem> giftItems);

  /**
   * 根据套餐构成子商品取得套餐构成
   */
  List<String> getSetCommodityByClildCommodity(String commodityCode, String shopCode);

  /**
   * 根据套餐构成商品取得套餐构成
   */
  List<SetCommodityComposition> getSetCommodityCompositipon(String commodityCode, String shopCode);

  /**
   * 套餐商品子商品信息取得
   */
  List<CommodityContainer> isSetCanBuy(String shopCode, List<SetCommodityComposition> setCompositions,
      SetCommodityInfo setCommodityInfo);

  /**
   * 套餐商品商品信息取得
   */
  SetCommodityInfo getSetComposition(String commodityCode, String shopCode);
  
  SetCommodityComposition getSuitSalePrice(String commodityCode);

  /**
   * 套餐构成商品信息取得
   */
  List<CommodityCompositionContainer> getCommodityCompositionContainerList(String shopCode, String commodityCode);

  /**
   * 取得套餐商品明细信息
   * 
   * @param shopCode
   *          店铺编号
   * @param commodityCode
   *          商品编号
   * @return
   */
  List<SetCommodityComposition> getSetCommodityInfo(String shopCode, String commodityCode);

  /**
   * 删除套餐商品明细信息
   * 
   * @param shopCode
   *          店铺编号
   * @param commodityCode
   *          商品编号
   * @param childCommodityCode
   *          套餐明细商品编号
   * @return
   */
  ServiceResult deleteSetCommodityComposition(List<SetCommodityComposition> dto, CommodityHeader header, CommodityDetail detail);

  /**
   * 登录套餐商品明细信息
   * 
   * @param dto
   *          套餐明细DTO
   * @return
   */
  ServiceResult insertSetCommodityCompositionInfo(SetCommodityComposition dto, CommodityHeader header, CommodityDetail detail);
  
  ServiceResult updateSetCommodityCompositionInfo(SetCommodityComposition dto, CommodityHeader header, CommodityDetail detail);
  
  ServiceResult insertTmallSuitCommodity(TmallSuitCommodity suitCommodity);

  /**
   * 商品信息取得
   */
  CommodityInfo getCommodityInfoBySkuCode(String shopCode, String commodityCode, String skuCode);

  // 2012/11/19 促销对应 ob add end

  // 2012.11.22 add by yyq start desc: 获取满足通知库存预警的商品集合
  public List<StockWarnInfo> getStockWarnCommdities();

  CommodityHeader loadBycommodityCode(String shopCode, String commodityCode, boolean flag, boolean resultFlg);

  boolean isExistsSetCommodityComposition(String shopCode, String commodityCode);

  String getCompositionDetailSkuOfMaxAvailableStock(String shopCode, String commodityCode);

  public List<CampaignMain> getCampaignMainByType(Date createdDatetime);

  public CampaignCondition getCampaignConditionByType(String campaignCode);

  public CampaignDoings getCampaignDoingsList(String campaignCode);

  List<SalesChartsData> getSalesStarList(String tag_no, String limit);

  SearchResult<CommodityContainer> fastFindDetailRecommendBContainer(CommodityContainerCondition condition);

  List<OptionalCampaign> getOptionalCampaignList();
  
  List<String> getSuggestionList(String name,String languageCode); 
  
  List<Category> getSearchPathList(String name,String categoryCode,int num) ;
  
  Category getCategoryListForPath(String code) ;
  
  List<Brand> getBrandList(String name,int num) ;

  Category getCategoryByParenent(String code); 
  
  List<Category> getCategorySelf();
  
  ServiceResult updateCategory(String code, String jp, String cn, String en);

  List<Brand> getBrand();
  
  ServiceResult updateBrand(String code, String jp, String cn, String en);
  
  List<CCommodityHeader> getCCommodityHeader();
  
  //2014/4/28 京东WBS对应 ob_李 add start
  CCommodityHeader getCCommodityHeader(String shopCode, String commodityCode);
  //2014/4/28 京东WBS对应 ob_李 add end
  
  List<CommodityHeader> getCommodityHeader();
  
  Brand getBrand(String code);
  
  List<CategorySel> getCategorys(String code);
  
  ServiceResult updateCCommodityHeader(String code, String jp, String cn, String en);
  
  ServiceResult updateCommodityKeyword(String code, String jp, String cn, String en, String jp2, String cn2, String en2);
  
  SearchKeywordLog getSearchCount(String searchWord);
  
  ResultBean getCommodityHeaderCount(String keyword,String languageCode);
  
  CandidateWord getCandidateWord(String keyword,String language);
  
  ServiceResult addCandidateWord(CandidateWord candidateword);

  ServiceResult updateCandidateWord(CandidateWord candidateword);
  // 20130603 add by zhangzhengtao end 
  //2013/6/7 组合商品登录 zhangzhengtao add strat
  List<CCommodityHeader> getCCommodityHeaderByOriginalCommodityCode(String originalCommodityCode);
  //2014/4/28 京东WBS对应 ob_李 add start
  List<CCommodityDetail> getCCommodityDetailByCommodityCode(String commodityCode);
  
  List<String> getJdCommodiytyPropertyIdList(String commodityCode);
  
  List<JdCommodityProperty> getJdCommodiytyPropertyValueList(String commodityCode, String propertyId);
  //2014/4/28 京东WBS对应 ob_李 add end
  CCommodityDetail getCCommodityDetail(String shopCode, String commodityCode);
  
  ServiceResult deleteCCommodityDetailByCommodityCode(String commodityCode);
  
  List<CCommodityHeader> getCode(String code);
  
  List<CCommodityHeader> getOriginalCode(String code);
  
  ServiceResult addTmallStockAllocation(TmallStockAllocation tamll);
  
  boolean isTmallStockAllocation(String code);
  
  boolean isCCommodityHeader(String code);
  
  ServiceResult addCdetail(CCommodityDetail detail);
  
  ServiceResult addStock(Stock stock);
  
  ServiceResult addCategoryCommodity(CategoryCommodity comm);
  
  DiscountCommodity getDiscountCommodityByCommodityCode(String commodityCode);
  
  List<CommodityHeadline> getDiscountPlan(String languageCode);
  
  DiscountHeader getDiscountHeaderLeast();
  
  List<jp.co.sint.webshop.data.dto.CampaignMain> getCampaignMainList();
  
  List<CommodityHeadline> getNewCommodityPlan(Long amount);
  
  List<CommodityHeadline> getIndexBatchCommodity(Long type);
  
  List<CommodityHeadline> getHotSaleCommodity(String languageCode);
  
  ServiceResult deleteCommodity();
  
  List<CommodityHeadline> getHotCommodityPlanByCategoryPath( String categoryPath1,String categoryPath2,String categoryPath3,String categoryPath4,String categoryPath5,String categoryPath6,String categoryPath7,String categoryPath8,Long amount);
  
  List<CommodityHeader> getHotCommodityPlanByBrand( String brandCode);
  
  List<CommodityHeadline> getHotCommodityPlan(Long amount);
  
  List<CommodityHeadline> getCommodityPlanFirstTime( String categoryCode);
  
  List<CommodityHeadline> getCommodityPlanSomeCategoty( String[] strList);
  
  List<CommodityHeadline> getCommodityByBrandEach() ;
  
  // 20140325 txw add start
  List<CommodityHeadline> getCommodityByFreePostage();
  // 20140325 txw add end
  
  List<CommodityHeadline> getAvaCommodityPlan( String categoryPath,int num);
  
  Long getDiscountTypeByOrderDetail(String commodityCode,String OrderNo,Long discountType,BigDecimal price);
  
  DiscountHeader getDiscountHeaderByCommodityCode(String commodityCode);
  
  Long getHistoryBuyAmount(String commodityCode,String customerCode);
  
  Long getHistoryBuyAmountTotal(String commodityCode);
  
  //2013/6/7 组合商品登录 zhangzhengtao add end

  List<CustomerCommodity> getCusCommodityList(); 
  
  // 2014/06/05 库存更新对应 ob_卢 add start
  JdStockInfo getOrgJdStockInfo(String commodityCode);
  
  List<JdStockInfo> getJdStockInfo(String commodityCode);
  
  Long getUseSuitStockJd(String commodityCode);
  
  ServiceResult updateTmallStockAll(String commodityCode, List<TmallStockAllocation> stockAlloctionList, List<String> tmallApiFailCodeList,
      List<String> jdApiFailCodeList, List<String> stockFailCodeList);
  
  ServiceResult updateJdStockAll(String commodityCode, List<JdStockAllocation> stockAlloctionList, List<String> tmallApiFailCodeList,
      List<String> jdApiFailCodeList, List<String> stockFailCodeList);
  
  // 2014/06/05 库存更新对应 ob_卢 add end
  // 20130613 txw add start
  TmallStockInfo getOrgTmallStockInfo(String commodityCode);
  
  List<TmallStockInfo> getTmallStockInfo(String commodityCode);
  
  ServiceResult updateTmallStockAllocation(TmallStockAllocation tmallStockAllocation);
  /**
   * TMAL库存修改时 淘宝上传
   * 
   * @param stock
   *        库存DTO
   * 
   * @return
   */
  boolean tmallSkuCodeUp(Stock stock);
  // 20130613 txw add end
  //20130701 zzt add start
  List<CommodityHeadline> getCommodityHeaderBySaleFlg();
  //20130701 zzt add end

  // 2014/05/02 京东WBS对应 ob_姚 add start
  /**
   * 取得类目的属性输入类型input_type=3的属性List
   * @param 店铺编号，商品编号
   * @return 类目的属性输入类型input_type=3的属性List
   */
  List<JdCommodityProperty> getJdCommodityPropertyInput(String shopCode, String commodityCode);
  
  /**
   * 取得类目的属性输入类型input_type=1或2的属性List
   * @param 店铺编号，商品编号
   * @return 类目的属性输入类型input_type=1或2的属性List
   */
  List<JdCommodityProperty> getJdCommodityPropertyNotInput(String shopCode, String commodityCode);

  ServiceResult updateCommodityDescribe(CommodityDescribe describe, boolean jdUpdateFlg);
  
  ServiceResult insertCommodityDescribe(CommodityDescribe describe, boolean jdUpdateFlg);
  // 2014/05/02 京东WBS对应 ob_姚 add end

  ServiceResult updateCommodityDescribe(CommodityDescribe describe);
  
  Long getCommodityExtOnStockFlg(String Commodity); 
  
  // 2014/06/05 库存更新对应 ob_卢 update start
  //ServiceResult addNewCommodityObjects(CCommodityHeader cheader,CCommodityDetail detail,Stock stock,CCommodityExt cceBean,CategoryCommodity commodity,CategoryAttributeValue buteValue,TmallStockAllocation tamllStockAllocation);
  ServiceResult addNewCommodityObjects(CCommodityHeader cheader, CCommodityDetail detail, Stock stock,CCommodityExt cceBean,
      CategoryCommodity commodity, CategoryAttributeValue buteValue, TmallStockAllocation tamllStockAllocation
      , JdStockAllocation jdStockAllocation);

  // 2014/06/05 库存更新对应 ob_卢 update end
  
  String getLoginDiscountCommodityName(Date startDate, Date endDate, String commodityCode);
  
  String getLoginFreePostageName(Date startDate, Date endDate, String issueCode);
  
  // 20140227 txw add start desc:临近效期提醒
  String getNearCommodityMsg(String shopCode, String commodityCode);
  // 20140227 txw add end desc:临近效期提醒
  
  //品店精选列表
  List<CommodityHeadline> getChosenSortCommodity(Long type);
  
  
  List<CommodityHeadline> getIndexBatchCommodityNoCache(Long type);
  
  List<CommodityHeadline> getHotSaleCommodityNoCache(String languageCode);
  
  //2014/06/17 库存更新对应 ob_李先超 add start
  Long getTmallStock(String commodityCode);
  
  Long getJdStock(String commodityCode);
  // 2014/06/17 库存更新对应 ob_李先超 add end
  
  SearchResult<CommodityPriceChangeHistory> searchCommodityPriceChangeHistory(CommodityPriceChangeHistoryCondition commodityPriceChangeHistoryCondition);

  SearchResult<CCommodityDetail> searchCCommodityDetail(String commodityCode);
  
  SearchResult<CCommodityHeader> searchCCommodityHeader(String commodityCode);
    
  ServiceResult insertCommodityPriceChangeHistory(CommodityPriceChangeHistory commodityPriceChangeHistory);
  
  ServiceResult updateCommodityPriceChangeHistoryReviewFlg(String commodityCode, String updatedUser);
  
  
  
  /**** 获取特定日期，特定类型的活动列表*****/
  List<CampaignMain> getCampaignMainByDateAndType(Date startedDatetime,Date endDatetime,Long Type);
  
  
  /**** 获取商品编号关联的品牌列表*****/
  List<Brand> getBrandListByRelatedCommodityCode(String commodityCode);
  
  RelatedBrand loadRelatedBrand(String shopCode,String commodityCode,String brandCode);
  
  void insertRelatedBrand(RelatedBrand obj);
  
  List<CCommodityHeader> getAllCCommodityHeader();
  
  /**** 获取商品编号关联的类目列表*****/
  List<Category> getCategoryListByRelatedCommodityCode(String commodityCode);
  
  RelatedSiblingCategory loadRelatedSiblingCategory(String shopCode,String commodityCode,String categoryCode);
  
  void insertRelatedSiblingCategory(RelatedSiblingCategory obj);
  
  List<RelatedSiblingCategory> getRelatedSiblingCategoryByCommodityCode(String commodityCode,Long limitNum);
  
  List<RelatedBrand> getRelatedBrandByCommodityCode(String commodityCode,Long limitNum);

  /****  品店热销排行榜*****/
  SearchResult<CommodityHeadline> fastFindCommodityHeaderLine(CommodityContainerCondition condition, boolean planDetailFlag);
 
  CommodityMaster getCommodityMasterByTmallCode(String commodityCode,String tmallCommdityCode);
  
  CommoditySku getCommoditySkuByTmallCode(String commodityCode,String skuCode);
  
  CommodityMaster getCommodityMasterByJdCode(String jdCommodityId);
  
  CommoditySku getCommoditySkuByJdCode(String commodityCode,String skuCode);
  
  //zzy 2015/1/14 add start
  /**tm/jd多商品关联检索**/
  SearchResult<CommodityMasterResult> getCommodityMasterResult(CommodityMasterSearchCondition condition);
  ServiceResult deleteCommodityMaster(String ccode);  
  public Long getCommoditySku(String ccode);
  //查询主商品编号是否存在
  public Long getCommodityCode1(String ccode);
  public void registerCommodityMaster(CommodityMaster cm);
  public CommodityMaster getCommodityMasters(String ccode);
  public ServiceResult updateCommodityMaster(CommodityMaster cm);
  //主商品下的子商品
  public CommodityMasterEditInfo getCommodityMasterEditInfo(String ccode);
  List<CommoditySku> getCommoditySkuList(String ccode);
  //删除单一子商品
  void deleteCommodityEdit(String skucode);
  List<CommodityHeader> getCommodityHeaderName(String ccode);
  
  public Long getCommoditySkuCode(String ccode,String skucode); 
  public void registerCommoditySku(CommoditySku cs);  
  //zzy 2015/1/14 add end
//判断commodity_sku 是否存在形同商品 skucode
  public List<String> getCommoditySkuCodes(String skucode);
  
}
