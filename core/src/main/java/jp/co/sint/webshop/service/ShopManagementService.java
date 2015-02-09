package jp.co.sint.webshop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryLocation;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.data.dto.DeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.data.dto.JdDeliveryLocation;
import jp.co.sint.webshop.data.dto.JdDeliveryRegion;
import jp.co.sint.webshop.data.dto.JdDeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.JdPrefecture;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.PostPayment;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.data.dto.RegionBlockLocation;
import jp.co.sint.webshop.data.dto.ShippingCharge;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.TmallDeliveryLocation;
import jp.co.sint.webshop.data.dto.TmallDeliveryRegion;
import jp.co.sint.webshop.data.dto.TmallDeliveryRelatedInfo;
import jp.co.sint.webshop.service.shop.CouponResearch;
import jp.co.sint.webshop.service.shop.CouponResearchInfo;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeCondition;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeInfo;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.service.shop.ShippingChargeSuite;
import jp.co.sint.webshop.service.shop.ShopListSearchCondition;
import jp.co.sint.webshop.service.shop.SmsTemplateSuite;

/**
 * SI Web Shopping 10 ショップ管理サービス(ShopManagementService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface ShopManagementService {

	/**
	 * ショップを全件取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>ショップマスタよりサイト情報以外のショップ情報を全件リストとして取得します。
	 * 
	 * 
	 * <ol>
	 * <li>ショップマスタより、サイト情報以外のショップ情報をショップコードの昇順で取得します。</li></dd>
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
	 * @return ショップのリスト<br />
	 *         サイト情報は含まれません。<br />
	 *         検索結果はショップコードの昇順で返されます。
	 */
	List<Shop> getShopAll();

	/**
	 * 検索条件にもとづきショップのリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったsearchConditionを元にショップマスタから該当ショップのリストを返します。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取ったsearchConditionを、ShopSearchQuery(ショップマスタ検索クエリ生成クラス)<br>
	 * にそのまま引数として渡し、ショップマスタ検索用のクエリを取得します。</li>
	 * <li>DatabaUtilのexecuteSearchメソッドに1で取得したクエリを引数として渡し、検索結果を返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>condition≠null</dd>
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
	 * @return ショップのSearchResult<br />
	 *         サイト情報は含まれません。<br />
	 *         検索結果はショップコードの昇順で返されます。
	 */
	SearchResult<Shop> getShopList(ShopListSearchCondition condition);

	/**
	 * ショップコードを指定してショップを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードを元に、ショップを返します。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取ったショップコードを元にショップを返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @return ショップ
	 */
	Shop getShop(String shopCode);

	/**
	 * 新しいショップを追加します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>新規にショップ情報をショップマスタに登録し、最低限必要なデフォルトデータを各テーブルへ登録します。<br />
	 * 登録されるテーブル・データに関しては事後条件を参照して下さい。<br>
	 * <ol>
	 * <li>引数で受取ったショップ情報に、DatabaseUtilを使いユーザーステータスをセットします。</li>
	 * <li>値をセットしたショップ情報に対してValidationチェックを行います。</li>
	 * <li>ショップマスタの重複チェックを行います。</li>
	 * <li>TransactionStart
	 * <ol>
	 * <li>ショップマスタにショップ情報を登録します。</li>
	 * <li>ショップ情報を元にメールテンプレート情報のリストを取得し、Validationチェックを行います。</li>
	 * <li></li>
	 * </ol>
	 * TransactionEnd</li>
	 * <li>ショップマスタにショップ情報を登録します。</li>
	 * <li>メールテンプレートヘッダーと明細のマスタにデフォルトデータを登録します。</li>
	 * <li>支払方法マスタに、登録するショップの全額ポイント払いと支払不要の支払方法を登録します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>引数のショップはnullでないとします。</dd>
	 * <dd>MailCompositionには、MailTypeに登録されているメールに<br />
	 * 関する情報が全て登録されているものとします。</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>ショップマスタに引数で与えられたショップ情報が登録されます。</dd>
	 * <dd>以下のデフォルトデータが登録されます。
	 * 
	 * 
	 * <ol>
	 * <li>メールテンプレート</li>
	 * <li>支払方法</li>
	 * <li>地域ブロック</li>
	 * <li>地域ブロック配置</li>
	 * <li>配送種別</li>
	 * </ol>
	 * </dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shop
	 *            ショップ
	 * @return サービス実行結果
	 */
	ServiceResult insertShop(Shop shop);

	/**
	 * ショップを更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップ情報を元に、ショップマスタを更新します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップ情報のショップコードから、マスタの該当ショップ情報を取得する。</li>
	 * <li>2. 1で取得したショップ情報が存在しなかった場合、エラーを返します。</li>
	 * <li>3. 引数で受取ったショップ情報に、1で取得したショップ情報の行ID、作成者、作成日、更新者をセットします。</li>
	 * <li>4. Validationチェックを行います。</li>
	 * <li>5. DAOを用いてショップ情報の更新を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shop≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shop
	 *            ショップ
	 * @return サービス実行結果
	 */
	ServiceResult updateShop(Shop shop);

	/**
	 * ショップ情報とそこに関連付いている全ての情報を削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードをキーに、ショップマスタの該当ショップ情報と<br>
	 * それに関連付いている全ての情報を削除します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードをキーに、削除対象の各マスタに対してDELETE文を発行します。</li>
	 * <li>2. 1の最中にエラーが発生した場合、ロールバックを行います。</li>
	 * <li>3. 正常に処理が行われた場合、コミットを行いserviceResultを返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>ショップマスタ以外に、下記マスタの情報が削除されます。<br>
	 * 商品入荷お知らせ<br>
	 * 人気ランキング詳細<br>
	 * レビュー投稿<br>
	 * レビュー点数集計<br>
	 * 在庫<br>
	 * 入出庫ヘッダ<br>
	 * 入出庫明細<br>
	 * キャンペーン<br>
	 * キャンペーン対象商品<br>
	 * カテゴリ属性値<br>
	 * 商品ヘッダ<br>
	 * 商品詳細<br>
	 * ギフト<br>
	 * ギフト対象商品<br>
	 * 関連商品A<br>
	 * 関連商品B<br>
	 * 在庫状況<br>
	 * タグ<br>
	 * タグ商品<br>
	 * 受注明細<br>
	 * 受注ヘッダ<br>
	 * 出荷明細<br>
	 * 支払方法<br>
	 * 金融機関<br>
	 * 支払手数料<br>
	 * 配送時間指定<br>
	 * 配送種別<br>
	 * 休日<br>
	 * メールテンプレートヘッダー<br>
	 * メールテンプレート明細<br>
	 * 都道府県<br>
	 * 地域ブロック<br>
	 * 送料<br>
	 * 管理ユーザ<br>
	 * 管理側アクセスログ<br>
	 * お気に入り商品<br>
	 * おすすめ商品<br>
	 * 商品別アクセスログ</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shopCode
	 * @return サービス処理結果
	 */
	ServiceResult deleteShop(String shopCode);

	/**
	 * 引数で受取ったショップコードに関連付いている、削除不可である受注の件数を取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードを元に、受注ヘッダマスタから削除不可の受注リストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取ったショップコードを元に、受注ヘッダマスタからショップコードが等しく、<br>
	 * 入金ステータスが1以外、又は出荷状況区分が3以外のレコードのリストを取得します。</li>
	 * <li>1で取得した受注ヘッダリストの件数を返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>引数で渡されるショップコードはnullでないとします。</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @return 削除不可な受注の件数
	 */
	int countUndeletableOrders(String shopCode);

	/**
	 * 開店中のショップ数を取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>開店中のショップ数を取得するクエリを生成・実行しショップ数を取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 現在日付がマスタデータの開店日付と閉店日付の間に収まっているショップをカウントするクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリをDatabaseUtilを用いて実行し、ショップ数を取得します。</li></dd>
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
	 * @return 開店中のショップ数
	 */
	Long getOpenShopCount();

	/**
	 * 地域ブロックのリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップ情報のショップコードより、地域ブロックのリストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップ情報のショップコードより、対応するショップの<br>
	 * 地域ブロックリストを、地域ブロックマスタから取得するクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリ文をDatabaseUtilを用いて実行し、地域ブロックのリストを取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shop≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shop
	 *            ショップ
	 * @return 地域ブロックのリスト
	 */
	List<RegionBlock> getRegionBlockList(Shop shop);

	/**
	 * 新しい地域ブロックを追加します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った地域ブロックを元に、地域ブロックマスタに新規地域ブロックを追加します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った地域ブロック情報に、UtilServiceを用いて取得した地域ブロックコードのシーケンスをセットします。</li>
	 * <li>2. 1で値をセットした地域ブロック情報に、DatabaseUtilを使いユーザーステータスをセットします。</li>
	 * <li>3. 3で値をセットした地域ブロック情報にValidationチェックを行います。</li>
	 * <li>4. 地域ブロックマスタに新規地域ブロック情報を追加します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>regionBlock≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param regionBlock
	 *            地域ブロック
	 * @return サービス実行結果
	 */
	ServiceResult insertRegionBlockList(RegionBlock regionBlock);

	/**
	 * 地域ブロックを更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った地域ブロック情報を元に、地域ブロックマスタを更新します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った地域ブロック情報のショップコード、地域ブロックコードで<br>
	 * 地域ブロック情報マスタより更新対象の地域ブロック情報を取得します。</li>
	 * <li>2. 1で取得した更新対象の地域ブロック情報が存在しなかった場合、エラーを返します。</li>
	 * <li>3. 引数で受取った地域ブロック情報に、1で取得した更新対象データの行ID、作成者、作成日<br>
	 * をセットします。</li>
	 * <li>4. 3で値をセットした地域ブロック情報に、DatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>5. 4で値をセットした地域ブロック情報にValidationチェックを行います。</li>
	 * <li>6. 地域ブロックマスタを更新します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>regionBlock≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param regionBlock
	 *            地域ブロック
	 * @return サービス実行結果
	 */
	ServiceResult updateRegionBlockList(RegionBlock regionBlock);

	/**
	 * 地域ブロックを削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、地域ブロックコードより地域ブロックマスタの対象データを削除します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、地域ブロックコードより地域ブロックマスタの対象データを削除します<br>
	 * (削除対象データの存在チェックは行いません)。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、regionBlockId≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param regionBlockId
	 *            地域ブロックコード
	 * 
	 * 
	 * @return サービス実行結果
	 */
	ServiceResult deleteRegionBlockList(String shopCode, Long regionBlockId);

	/**
	 * 地域ブロック配置情報のリストを返します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>地域ブロック配置マスタから、ショップコードに関連付いている地域ブロック配置情報のリストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードから、ショップコードに関連付いている地域ブロック配置情報のリストを<br>
	 * 地域ブロック配置マスタから取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>地域ブロック配置情報のリストは都道府県コードの昇順で返されます。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shopCode
	 *            ショップコード
	 * 
	 * 
	 * @return 地域ブロック配置情報一覧
	 */
	List<RegionBlockLocation> getRegionBlockLocationList(String shopCode);

	/**
	 * 地域ブロック配置情報のリストを登録します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>各地域ブロック配置情報が存在する場合は更新を、存在しない場合は新規登録を行います。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取ったショップコードより、地域ブロック配置マスタから<br>
	 * 地域ブロック配置情報のリストを取得します。</li>
	 * <li>
	 * <ol>
	 * <li>引数で受取った地域ブロック配置情報のリストと、1で取得した地域ブロック配置情報の<br>
	 * リストのサイズが等しくない場合、地域ブロック配置マスタからショップコードに<br>
	 * 関連付いている地域ブロック配置情報を全て削除します。</li>
	 * <li>引数で受取った各地域ブロック配置情報にDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>2.1.2で値をセットした地域ブロック配置情報にValidationチェックを行います。</li>
	 * <li>2.1.3で値をセットした地域ブロック配置情報を地域ブロック配置マスタに登録します。</li>
	 * </ol>
	 * </li>
	 * <li>
	 * <ol>
	 * <li>引数で受取った地域ブロック配置情報のリストと、1で取得した地域ブロック配置情報のリストの<br>
	 * サイズが等しかった場合、引数で受取った地域ブロック配置情報の各都道府県コードと<br>
	 * 地域ブロック配置情報のマップを生成します。</li>
	 * <li>1で取得した地域ブロック配置情報のリストの、各都道府県コードを用いて、2.3.1で<br>
	 * 生成したマップから更新用の都道府県情報を取得します。</li>
	 * <li>2.3.2で取得した更新用地域ブロック配置情報が存在しなかった場合、サービス実行結果<br>
	 * にDBオブジェクト実行エラーをセットしサービス実行結果を返します。</li>
	 * <li>2.3.2で取得した更新用地域ブロック配置情報に、マスタの地域ブロック配置情報の<br>
	 * 行ID、作成者、作成日をセットします。</li>
	 * <li>2.3.4で値をセットした更新用地域ブロック配置情報に、DatabaseUtilを用いて<br>
	 * ユーザーステータスをセットします。</li>
	 * <li>2.3.5で値をセットした更新用地域ブロック配置情報にValidationチェックを行います。</li>
	 * <li>更新処理を行います。</li>
	 * </ol>
	 * </li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>prefectureList≠null、shopCode≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param regionBlockLocationList
	 *            地域ブロック配置情報のリスト
	 * @param shopCode
	 *            ショップコード
	 * 
	 * 
	 * @return サービス実行結果
	 */
	ServiceResult registerRegionBlockLocationList(
			List<RegionBlockLocation> regionBlockLocationList, String shopCode);

	/**
	 * 配送種別のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードに関連付いている配送種別のリストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードを元に、配送種別マスタからショップコードに<br>
	 * 関連付いている配送種別のリストを取得するクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリ文を、DatabaseUtilを用いて実行し配送種別情報のリストを取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null</dd>
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
	 *            ショップ
	 * @return 配送種別のリスト
	 */
	List<DeliveryType> getDeliveryTypeList(String shopCode);

	/**
	 * 配送種別を取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードと配送種別コードから、配送種別情報を取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードと配送種別コードより、配送種別マスタからDAOを用いて配送種別情報を取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、deliveryCode≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @return 配送種別情報
	 */
	DeliveryType getDeliveryType(String shopCode, Long deliveryTypeNo);

	/**
	 * 新しい配送種別と送料ルールを追加します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った配送種別と手数料リストを各マスタへと新規に登録します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った配送種別のショップコードと配送種別コードより、マスタから配送種別を取得します。</li>
	 * <li>2. 1で配送種別情報が取得できた場合、エラーを返します。</li>
	 * <li>3. 引数で受取った配送種別情報にDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>4. 3で値をセットした配送種別情報にValidationチェックを行います。</li>
	 * <li>5. 配送種別マスタに登録を行います。</li>
	 * <li>6. 引数で受取った手数料リストの各手数料情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>7. 6で値をセットした手数料情報にValidationチェックを行います。</li>
	 * <li>8. 手数料マスタに登録を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>deliveryType≠null、chargeList≠null(0件のデータはあっても構いません)</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param deliveryType
	 *            配送種別
	 * 
	 * 
	 * @param chargeList
	 *            手数料リスト
	 * @return サービス実行結果
	 */
	ServiceResult insertDeliveryType(DeliveryType deliveryType,
			List<ShippingCharge> chargeList);

	/**
	 * 配送種別と送料ルールを更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った配送種別と手数料リストを用いて、各マスタを更新します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った配送種別のショップコード、配送種別コードより配送種別マスタから配送種別情報を取得します。</li>
	 * <li>2. 1で取得した配送種別が存在しなかった場合、エラーを返します。</li>
	 * <li>3. 引数で受取った配送種別に1でマスタから取得した配送種別の行ID、作成者、作成日をセットします。</li>
	 * <li>4. 3で値をセットした配送種別に、DatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>5. 4で値をセットした配送種別にValidationチェックを行います。</li>
	 * <li>6. 配送種別マスタに更新処理を行います。</li>
	 * <li>7. 引数で受取った配送種別のショップコード、配送種別コードより手数料マスタから<br>
	 * 関連付いている手数料データを全件削除するクエリ文を生成します。</li>
	 * <li>8. 引数で受取った手数料リストの各手数料情報に対して、DatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>9. 8で値をセットした手数料情報に対してValidationチェックを行います。</li>
	 * <li>10. 手数料マスタに登録を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>deliveryType≠null、chargeList≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param deliveryType
	 *            配送種別
	 * 
	 * 
	 * @param chargeList
	 *            手数料リスト
	 * @return サービス実行結果
	 */
	ServiceResult updateDeliveryType(DeliveryType deliveryType,
			List<ShippingCharge> chargeList);

	/**
	 * 配送種別を削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、配送種別コードより配送種別マスタと手数料マスタから該当データを削除します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、配送種別コードより配送種別マスタからデータを取得します。</li>
	 * <li>2. 1で取得した配送種別情報が存在しなかった場合、処理を終了します。</li>
	 * <li>3. 1で取得した配送種別情報を元に、配送種別マスタへ削除処理を行います。</li>
	 * <li>4. 引数で受取ったショップコード、配送種別コードを元に手数料マスタから関連付いているデータを削除するクエリ文を生成します。</li>
	 * <li>5. 4で生成したクエリ文を実行し、手数料マスタへ削除処理を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、deliveryTypeNo≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @return サービス実行結果
	 */
	ServiceResult deleteDeliveryType(String shopCode, Long deliveryTypeNo);

	/**
	 * 配送指定時間帯のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、配送種別コードより配送指定時間帯のリストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、配送種別コードより配送指定時間マスタから<br>
	 * 関連付いているデータのリストを返すクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリ文をDatabaseUtilを用いて実行し、配送指定時間のリストを取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、deliveryTypeNo≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @return 配送指定時間帯のリスト
	 */
	List<DeliveryAppointedTime> getDeliveryAppointedTimeList(String shopCode,
			Long deliveryTypeNo);

	/**
	 * 配送時間帯のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、配送種別コード、配送開始時間、配送終了時間より配送指定時間マスタから<br>
	 * 配送時間帯のリストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、配送種別コード、配送開始時間、配送終了時間より<br>
	 * 配送指定時間マスタから配送時間帯のリストを取得するクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリ文をDatabaseUtilを用いて実行し、配送指定時間のリストを取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、deliveryTypeNo≠null、</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @param deliveryAppointedStartTime
	 *            配送開始時間
	 * 
	 * 
	 * @param deliveryAppointedEndTime
	 *            配送終了時間
	 * 
	 * 
	 * @return 配送指定時間帯のリスト
	 */
	List<DeliveryAppointedTime> getDeliveryAppointedTimeList(String shopCode,
			Long deliveryTypeNo, Long deliveryAppointedStartTime,
			Long deliveryAppointedEndTime);

	/**
	 * 新しい配送指定時間帯を追加します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った配送指定時間帯より、配送指定時間マスタを更新します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った配送指定時間帯情報のショップコード、配送種別コード、配送指定時間コードより<br>
	 * 配送指定時間マスタからデータを取得するクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリ文をDatabaseUtilを用いて実行し、配送指定時間情報のリストを取得します。</li>
	 * <li>3. 2で取得したリストが1件以上存在した場合、エラーを返します。</li>
	 * <li>4. 引数で受取った配送指定時間帯情報にDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>5. 4で値を設定した配送指定時間帯情報にValidationチェックを行います。</li>
	 * <li>6. 配送指定時間マスタへDAOを用いて登録を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>deliveryTimeSlot≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param deliveryTimeSlot
	 *            配送指定時間帯
	 * @return サービス実行結果
	 */
	ServiceResult insertDeliveryAppointedTime(
			DeliveryAppointedTime deliveryTimeSlot);

	/**
	 * 配送指定時間帯を更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った配送指定時間帯から、配送指定時間マスタを更新します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った配送指定時間帯のショップコード、配送種別コード、配送指定時間コードより、<br>
	 * </li>
	 * 配送指定時間マスタから配送指定時間情報を取得します。
	 * 
	 * 
	 * <li>2. 1で取得した配送指定時間情報が存在しなかった場合、エラーを返します。</li>
	 * <li>3. 引数で受取った配送指定時間帯情報に、1で取得した配送指定時間情報の行ID、作成者、作成日、更新者をセットします。</li>
	 * <li>4. 3で値をセットした配送指定時間帯情報にDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>5. 4で値をセットした配送指定時間帯情報にValidationチェックを行います。</li>
	 * <li>6. 配送指定時間マスタへ更新処理を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>deliveryTimeSlot≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param deliveryTimeSlot
	 *            配送指定時間帯
	 * @return サービス実行結果
	 */
	ServiceResult updateDeliveryAppointedTime(
			DeliveryAppointedTime deliveryTimeSlot);

	/**
	 * 配送指定時間帯を削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、配送種別コード、配送指定時間コードを元に配送指定時間マスタから削除を行います。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、配送種別コード、配送指定時間コードより、配送指定時間マスタから削除データを取得します。</li>
	 * <li>2. 1で取得した削除データが存在しなかった場合、エラーを返します。</li>
	 * <li>3. 配送指定時間マスタから削除を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、deliveryTypeNo≠null、deliveryAppointedTimeCode≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @param deliveryAppointedTimeCode
	 *            配送指定時間コード
	 * @return サービス実行結果
	 */
	ServiceResult deleteDeliveryAppointedTime(String shopCode,
			Long deliveryTypeNo, String deliveryAppointedTimeCode);

	/**
	 * 送料のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードと配送種別コードより、送料マスタから送料のリストを取得します。<br>
	 * また、対応する地域ブロック名を取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードと配送種別コードより、送料マスタから送料のリストを取得するクエリ文を生成します。</li>
	 * <li>2. 引数で受取ったショップコードより地域ブロックリストを取得し、地域ブロックコードと地域ブロック名のマップを生成します。</li>
	 * <li>3. 1で生成したクエリ文をDatabaseUtilを用いて実行し、送料情報のリストを取得します。</li>
	 * <li>4. 3で取得した各送料情報の地域ブロックコードより、2で生成した地域ブロックのマップ情報から地域ブロック名を取得します。</li>
	 * <li>5. 4で取得した地域ブロック名と送料情報をセットにした値を返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、deliveryTypeNo≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @return 送料のリスト
	 */
	List<ShippingChargeSuite> getShippingChargeList(String shopCode,
			Long deliveryTypeNo);

	/**
	 * 配送種別と都道府県から送料を1件取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、配送種別コード、都道府県コードから送料データを1件取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードより、都道府県マスタから都道府県のリストを取得します。</li>
	 * <li>2. 1で取得した都道府県のリストの中で、都道府県コードが引数で受取った都道府県コードと<br>
	 * 一致するデータの地域ブロックコードを取得します。</li>
	 * <li>3. 引数で受取ったショップコード、配送種別コード、2で取得した地域ブロックコードより<br>
	 * 送料マスタから送料を取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、deliveryTypeNo≠null、prefectureCode≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @param prefectureCode
	 *            都道府県コード
	 * 
	 * 
	 * @return 送料
	 */
	ShippingCharge getShippingCharge(String shopCode, Long deliveryTypeNo,
			String prefectureCode);

	/**
	 * 支払方法のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードを元に、支払方法のリストを返します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードを元に、定義されたクエリ文を使って支払方法のリストを返します。</li></dd>
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
	 * @param shopCode
	 * @return 支払方法のリスト
	 */
	List<PaymentMethod> getPaymentMethodList(String shopCode);

	/**
	 * 支払方法のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードを元に、支払方法のリストを返します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードを元に、定義されたクエリ文を使って支払方法のリストを返します。<br />
	 * また、削除フラグの値はチェックしない為、削除された支払方法も検索結果に含まれます。</li></dd>
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
	 * @param shopCode
	 * @return 支払方法のリスト
	 */
	List<PaymentMethod> getAllPaymentMethodList(String shopCode);

	/**
	 * 手数料を1件取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、支払方法コード、購入金額から手数料を1件取得します。。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード・支払方法コード・購入金額から、<br>
	 * ショップコードと支払方法コードが一致し、購入金額が購入金額(To)以下の手数料を取得します。。<br>
	 * 取得した手数料データの中で、購入金額(To)が最低値の手数料データを返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、 paymentMethodNo≠null、 price≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>ショップコードと支払方法コードが一致する手数料データの中で、引数で受取った購入金額が<br>
	 * 購入金額(To)以下のデータのうち、購入金額(To)が最低の手数料データを取得します。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shopCode
	 * @param paymentMethodNo
	 * @param price
	 * @return 手数料
	 */
	Commission getCommission(String shopCode, Long paymentMethodNo,
			BigDecimal price);

	/**
	 * 支払方法を1件と、それに関連付いている手数料のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードと支払方法コードより、支払方法とそれに関連付いている手数料のリストを返します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードと支払方法コードより、対応する手数料一覧を購入金額(To)の昇順で取得するクエリ文を取得します。</li>
	 * <li>2. 1で取得したクエリ文を使い、手数料マスタのDAOより手数料のリストを取得します。</li>
	 * <li>3. 引数で受取ったショップコードと支払方法コードより、支払方法マスタより支払方法を取得します。</li>
	 * <li>4. 2、3で取得した支払方法と手数料のリストをPaymentMethodSuiteに詰め込み、値を返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、paymentMethodNo≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>手数料の一覧は購入金額(TO)の昇順で返されます。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shopCode
	 * @param paymentMethodNo
	 * @return PaymentMethodSuite
	 */
	PaymentMethodSuite getPaymentMethod(String shopCode, Long paymentMethodNo);

	/**
	 * 削除可能な支払方法かどうかを返します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>削除可能な支払方法かどうかを返します。
	 * 
	 * 
	 * <ol>
	 * <li>受注ヘッダテーブルより、引数で受取ったショップコード・支払方法Noの受注、かつ、未入金未出荷の受注情報の数を取得します。</li>
	 * <li>取得した受注情報の数が0より大きい場合はfalse、そうでない場合はtrueを返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>deleteShopCode≠null、deletePaymentMethodNo≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param deleteShopCode
	 *            ショップコード
	 * 
	 * 
	 * @param deletePaymentMethodNo
	 *            支払方法No
	 * @return boolean(削除可能な場合はtrue、そうでない場合はfalse)
	 */
	boolean isDeletablePayment(String deleteShopCode, Long deletePaymentMethodNo);

	/**
	 * 削除可能な配送種別情報かどうかを返します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>削除可能な配送先情報かどうかを返します。
	 * 
	 * 
	 * <ol>
	 * <li>商品マスタより、引数で受取ったショップコードと配送種別番号の配送情報を使用している<br>
	 * 商品数を取得し、0より大きい場合はfalseを返します。</li>
	 * <li>出荷ヘッダより、引数で受取ったショップコードと配送種別番号の配送情報を使用している<br>
	 * 出荷情報数を取得し、0より大きい場合はfalseを返します。</li>
	 * <li>上記処理二つの処理でともに配送情報が使用されていなかった場合は、trueを返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>ショップコードはnullでない物とします。</dd>
	 * <dd>配送種別番号はnullでない物とします。</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param deleteShopCode
	 *            ショップコード
	 * 
	 * 
	 * @param deleteDeliveryTypeNo
	 *            配送種別番号
	 * 
	 * 
	 * @return boolean(削除可能な場合はtrue、そうでない場合はfalse)
	 */
	boolean isDeletableDelivery(String deleteShopCode, Long deleteDeliveryTypeNo);

	/**
	 * 支払方法、手数料、金融機関を新規に登録します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>支払方法、手数料、金融機関情報(nullでない場合)を各マスタへ登録します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったpaymentMethodSuiteから、支払方法情報を取得します。</li>
	 * <li>2. 引数で受取ったpaymentMethodSuiteから、手数料の一覧を取得しその1件めを登録用手数料として取得します。<br>
	 * (支払方法登録時には手数料は1つしか登録できない為)</li>
	 * <li>3. 1.取得した支払方法に、UtilServiceを用いて取得した支払方法コードのシーケンスを設定します。</li>
	 * <li>4. 3で支払方法コードを設定した支払方法に、DatabaseUtilを用いてユーザーステータスを設定します。</li>
	 * <li>5. 4でユーザーステータスを設定した支払方法にValidationチェックを行います。</li>
	 * <li>6. 引数で受取った金融機関がnullで無い場合、金融機関に3で設定した物と同じ支払方法コードを設定します。</li>
	 * <li>6.1. 6で支払方法コードを設定した金融機関に、DatabaseUtilを用いてユーザーステータスを設定します。</li>
	 * <li>6.2. 6.1でユーザーステータスを設定した金融機関にValidationチェックを行います。</li>
	 * <li>7. 2で取得した手数料に3で設定した物と同じ支払方法コードを設定します。</li>
	 * <li>8. 7で支払方法コードを設定した手数料に、DatabaUtilを用いてユーザーステータスを設定します。</li>
	 * <li>9. 8でユーザーステータスを設定した手数料にValidationチェックを行います。</li>
	 * <li>10. 上記処理でエラーが存在しなかった場合、TransactionManagerを用いて各マスタへ新規登録を行います。<br>
	 * ただし、金融機関がnullの場合は金融機関マスタへの登録は行いません。</li>
	 * <li>11. サービス実行結果を返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>paymentMethod≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param paymentMethod
	 * @param bank
	 * @return サービス実行結果
	 */
	// v10-ch-pg modify start
	// ServiceResult insertPaymentMethod(PaymentMethodSuite paymentMethod, Bank
	// bank);
	ServiceResult insertPaymentMethod(PaymentMethodSuite paymentMethod,
			Bank bank, PostPayment postPayment);

	// v10-ch-pg modify end

	/**
	 * 支払方法を更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数に受取ったpaymentMethodSuiteを元に、支払方法マスタを更新します。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取ったpaymentMethodSuiteから支払方法を取得します。</li>
	 * <li>引数で受取ったpaymentMethodSuiteから手数料リストの1件目を取得します。</li>
	 * <li>1で取得した支払方法を元に、支払方法マスタから更新対象の支払方法を取得します。</li>
	 * <li>2で取得した手数料を元に、手数料マスタから更新対象の支払方法を取得します。</li>
	 * <li>3で取得した支払方法がnullだった場合、該当データ未存在エラーを返します。</li>
	 * <li>1で取得した支払方法に、3でマスタから取得した支払方法の行ID、作成日、作成者、更新者をセットします。</li>
	 * <li>6で値を設定した支払方法にValidationチェックを行います。</li>
	 * <li>支払方法タイプが代引以外かつ、4で取得した手数料がnullだった場合、該当データ未存在エラーを返します。</li>
	 * <li>2で取得した手数料に、4でマスタから取得した手数料の行ID、作成日、作成者、更新者、更新日をセットします。</li>
	 * <li>9で値を設定した手数料にValidationチェックを行います。</li>
	 * <li>TransactionManagerを用いて、支払方法マスタに対して更新処理を行います。</li>
	 * <li>支払方法タイプが代引以外の時、TransactionManagerを用いて、支払方法マスタに対して更新処理を行います。</li>
	 * <li>コミットを行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>paymentMethodSuiteはnullでない物とします。</dd>
	 * <dd>paymentMethodSuiteが持つpaymentMethodの、ショップコード・支払方法Noの<br />
	 * データが支払方法マスタに存在する物とします。</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>引数で受取ったpaymentMethodSuiteが持つpaymentMethodの値で<br />
	 * 支払方法マスタを更新します。<br />
	 * また、支払方法が代引以外の場合はpaymentMethodSuiteが持つcommissionListの<br />
	 * 1件目の値で手数料マスタを更新します。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param paymentMethodSuite
	 *            支払方法情報
	 * @return サービス実行結果
	 */
	ServiceResult updatePaymentMethod(PaymentMethodSuite paymentMethodSuite);

	/**
	 * 支払方法を削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、支払方法コードをキーに支払方法と支払方法に関連づいている金融機関・手数料をすべて削除します。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取ったショップコードをもとに、ショップの支払方法を取得します。</li>
	 * <li>「支払不要」「全額ポイント払い」を除いた支払方法が1件以下の場合、支払方法を削除することができないため支払方法削除不可エラーを返します。</li>
	 * <li>「支払不要」「全額ポイント払い」を除いた支払方法が2件以上の場合、トランザクション処理を開始します。</li>
	 * <li>引数で受取ったショップコードと支払方法コードをもとに、支払方法DTOを取得します。</li>
	 * <li>取得した支払方法DTOがnullでなければ、支払方法を削除します。</li>
	 * <li>引数で受取ったショップコードと支払方法コードをもとに、支払方法に関連づく金融機関DTOのリストを取得します。</li>
	 * <li>取得した金融機関DTOを全件削除します。</li>
	 * <li>引数で受取ったショップコードと支払方法コードをもとに、支払方法に関連づく手数料DTOのリストを取得します。</li>
	 * <li>取得した手数料DTOを全件削除します。</li>
	 * <li>トランザクション処理を終了します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、paymentMethodNo≠null</dd>
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
	 * @param paymentMethodNo
	 * @return サービス実行結果
	 */
	ServiceResult deletePaymentMethod(String shopCode, Long paymentMethodNo);

	/**
	 * 手数料のリストを取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、支払方法コードを元に、手数料のリストを返します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、支払方法コードを元に、定義されたクエリ文を使って手数料のリストを返します。</li></dd>
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
	 * @param shopCode
	 * @param paymentMethodNo
	 * @return 手数料のリスト
	 */
	List<Commission> getCommissionList(String shopCode, Long paymentMethodNo);

	/**
	 * 手数料を新規に登録します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った手数料を手数料マスタに登録します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った手数料を元に、手数料マスタから手数料を取得します。</li>
	 * <li>2. 1で手数料マスタに手数料が存在した場合、重複エラーを返します。</li>
	 * <li>3. 2でエラーにならなかった場合、引数で受取った手数料にDatabaUtilを用いてユーザステータスを設定します。</li>
	 * <li>4. 3で値を設定した手数料にValidationチェックを行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>commission≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param commission
	 * @return サービス実行結果
	 */
	ServiceResult insertCommission(Commission commission);

	/**
	 * 手数料を更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取って手数料を元に手数料マスタを更新します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った手数料を元に、手数料マスタから更新対象の手数料を取得します。</li>
	 * <li>2. 1で取得した手数料が存在しなかった場合、エラーを返します。</li>
	 * <li>3. 引数で受取った手数料に、1で手数料マスタから取得した手数料の行ID、作成者、作成日、更新者をセットします。</li>
	 * <li>4. 4で値をセットした手数料にValidationチェックを行います。</li>
	 * <li>5. DAOを用いて手数料マスタを更新します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>commission≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param commission
	 * @return サービス実行結果
	 */
	ServiceResult updateCommission(Commission commission);

	/**
	 * 手数料を削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、支払方法コード、支払金額閾値を元に手数料マスタから削除を行います。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、支払方法コード、支払金額閾値を元にDAOを用いて手数料マスタから削除を行います。<br>
	 * (削除対象データの存在チェックは行いません)</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、paymentMethodNo≠null、paymentPriceThreshold≠null</dd>
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
	 * @param paymentMethodNo
	 * @param paymentPriceThreshold
	 * @return サービス実行結果
	 */
	ServiceResult deleteCommission(String shopCode, Long paymentMethodNo,
			BigDecimal paymentPriceThreshold);

	/**
	 * ショップごとの金融機関情報一覧を取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコードを元に金融機関情報のリストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコードを元に、金融機関を取得するクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリ文を使い金融機関マスタからDAOを用いて金融機関のリストを取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param paymentMethodNo
	 *            支払方法コード
	 * 
	 * 
	 * @return 金融機関のリスト(ショップ、支払方法ごと)
	 */
	List<Bank> getBankList(String shopCode, Long paymentMethodNo);

	// 05-20 Add start
	List<PostPayment> getPostList(String shopCode, Long paymentMethodNo);

	// 05-20 Add end
	/**
	 * 金融機関情報を登録します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った金融機関情報を、金融機関マスタに登録します。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取った金融機関情報のショップコード、支払方法コード、銀行コード、支店コード、口座番号<br>
	 * より、金融機関情報マスタより金融機関情報を取得します。</li>
	 * <li>1で金融機関情報が取得できた場合、データ重複エラーを返します。</li>
	 * <li>2でエラーが存在しなかった場合、引数で受取った金融機関情報にDatabaseUtilを用いて<br>
	 * ユーザーステータスをセットします。</li>
	 * <li>3で値をセットした金融機関情報にValidationチェックを行います。</li>
	 * <li>金融機関情報マスタに登録を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>引数で受取る金融機関情報はnullでない物とします。</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>金融機関情報マスタに、引数で与えた情報が登録されます。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param bank
	 *            金融機関情報
	 * @return サービス実行結果
	 */
	ServiceResult insertBank(Bank bank);

	// Add by VC10-CH start
	ServiceResult insertPost(PostPayment post);

	// Add by VC10-CH end
	/**
	 * 金融機関情報を更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取った金融機関情報を、金融機関マスタに更新します。
	 * 
	 * 
	 * <ol>
	 * <li>引数で受取った金融機関情報のショップコード、支払方法コード、銀行コード、支店コード、口座番号<br>
	 * より、金融機関情報マスタより金融機関情報を取得します。</li>
	 * <li>1で取得した金融機関情報が存在しなかった場合、該当データ未存在エラーを返します。</li>
	 * <li>引数で受取った金融機関情報に取得した金融機関情報の行ID、作成者、作成日、更新者をセットします。</li>
	 * <li>3で値をセットした金融機関情報にValidationチェックを行います。</li>
	 * <li>金融機関情報マスタに更新を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>引数で受取る金融機関情報はnullでない物とします。</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>金融機関情報マスタの対象のレコードが、引数で与えた情報で更新されます。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param bank
	 *            金融機関情報
	 * @return サービス実行結果
	 */
	ServiceResult updateBank(Bank bank);

	// 05-20 Add start
	ServiceResult updatePost(PostPayment post);

	// 05-20 Add end

	/**
	 * 金融機関情報を削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、支払方法コード、金融機関コード、金融機関支店コード、口座番号より、<br>
	 * 金融機関マスタから金融機関情報を削除します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、支払方法コード、金融機関コード、金融機関支店コード、口座番号を使い、<br>
	 * DAOを用いて金融機関マスタより対象レコードを削除します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、paymentMethodNo≠null、bankCode≠null、bankBranchCode≠null、
	 * accountNo≠null</dd>
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
	 * @param paymentMethodNo
	 * @param bankCode
	 * @param bankBranchCode
	 * @param accountNo
	 * @return サービス実行結果
	 */
	ServiceResult deleteBank(String shopCode, Long paymentMethodNo,
			String bankCode, String bankBranchCode, String accountNo);

	// 05-20 Add start
	ServiceResult deletePost(String deletePost, Long paymentMethodNo,
			String postAccountNo);

	// 05-20 Add end
	/**
	 * 指定された月の休日情報を取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>dateで指定された年月の1日から末日の間に存在する 休日を取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った年月から、その月の月初日と月末日を生成します。</li>
	 * <li>2. 引数で受取ったショップコードと1で生成した月末日、月初日より、休日マスタから<br>
	 * 対応する休日情報を取得するクエリ文を生成します。</li>
	 * <li>3. 2で生成したクエリ文を、DatabaseUtilを用いて実行し休日情報のリストを取得します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、data≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param date
	 *            年月
	 * @return 休日情報のリスト
	 */
	List<Holiday> getHoliday(String shopCode, Date date);

	/**
	 * 休日情報を月単位で更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>登録年月の休日情報を全て削除した後、登録年月日の休日情報を登録します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取った年月から、その月の月初日と月末日を生成します。</li>
	 * <li>2. 引数で受取ったショップコードと1で生成した月初日、月末日より、休日マスタから対象の年月で<br>
	 * ショップコードに関連付いているデータを削除します。</li>
	 * <li>3. 引数で受取った日付リストのデータごとに、新たに休日データを作成し、引数で受取ったショップコードをセットします。</li>
	 * <li>4. 3で生成した休日データに、引数で受取った年、月と日付データを"/"で結合した日付をセットします。</li>
	 * <li>5. 4で値をセットした休日データにDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>6. 5で値をセットした休日データにValidationチェックを行います。</li>
	 * <li>7. 休日マスタに更新を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、year≠null、month≠null、date≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param year
	 *            登録年
	 * 
	 * 
	 * @param month
	 *            登録月
	 * 
	 * 
	 * @param date
	 *            登録日のリスト
	 * 
	 * 
	 * @return サービス処理結果
	 */
	ServiceResult updateHoliday(String shopCode, String year, String month,
			List<String> date);

	/**
	 * メールテンプレートの設定情報を取得する。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>引数で受取ったショップコード、メールタイプ、テンプレートNoより、メールテンプレートヘッダマスタ、<br>
	 * メールテンプレート明細マスタからメールテンプレート設定情報を取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったショップコード、メールタイプ、テンプレートNoより、メールテンプレートヘッダマスタから<br>
	 * メールテンプレートヘッダを取得します。</li>
	 * <li>2. 引数で受取ったショップコード、メールタイプ、テンプレートNoより、メールテンプレート明細マスタから<br>
	 * メールテンプレート明細のリストを取得するクエリ文を生成します。</li>
	 * <li>3. 2で生成したクエリ文をDatabaseUtilを用いて実行し、メールテンプレート明細のリストを取得します。</li>
	 * <li>4. 1と3で取得したメールテンプレートヘッダと明細のリストを合わせてメールテンプレート設定情報として返します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>shopCode≠null、mailType≠null、templateNo≠null</dd>
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
	 *            ショップコード
	 * 
	 * 
	 * @param mailType
	 *            メールタイプ
	 * @param templateNo
	 *            メールテンプレート番号
	 * 
	 * 
	 * @return メールテンプレート設定情報
	 */
	MailTemplateSuite getMailTemplateConfig(String shopCode, String mailType,
			Long templateNo);

	// Add by V10-CH start
	SmsTemplateSuite getSmsTemplateConfig(String smsType);

	// Add by V10-CH end
	/**
	 * サイトに登録されている情報メールの一覧をCodeAttributeとして取得します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>メールテンプレート明細マスタから、情報メールのリストを取得します。
	 * 
	 * 
	 * <ol>
	 * <li>1. メールテンプレート明細マスタから、メールタイプが"00"のデータをメールテンプレートNoの<br>
	 * 降順で取得するクエリ文を生成します。</li>
	 * <li>2. 1で生成したクエリ文をDatabaseUtilを用いて実行し、メールテンプレート明細のリストを取得します。</li>
	 * <li>3. 2で取得したメールテンプレート明細ごとに、メールテンプレートNoとメール構造名のリストを返します。</li></dd>
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
	 * <dd>CodeAttributeのValueにはMAIL_TEMPLATE_NOが、
	 * NameにはMAIL_COMPOSITION_NAMEが設定されます。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @return 情報メールの一覧(CodeAttributeのリスト)
	 */
	List<CodeAttribute> getInformationMailTypeList();

	// Add by V10-CH start
	List<CodeAttribute> getInformationSmsTypeList();

	// Add by V10-CH end

	/**
	 * メールテンプレート情報を更新します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>メールテンプレート情報を同一トランザクションで更新します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったメールテンプレート情報の、ヘッダ情報の持つショップコード、メールタイプ<br>
	 * メールテンプレートNoより、メールテンプレートヘッダマスタからメールテンプレートヘッダ情報を取得します。</li>
	 * <li>2. 1で取得したメールテンプレートヘッダが存在しなかった場合、エラーを返します。</li>
	 * <li>3. 引数で受取ったメールテンプレート情報のヘッダ情報に、1で取得したマスタのヘッダ情報の行ID、作成者、作成日をセットします。</li>
	 * <li>4. 3で値をセットしたメールテンプレート情報のヘッダ情報に、DatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>5. 4で値をセットしたヘッダ情報にValidationチェックを行います。</li>
	 * <li>6. 引数で受取ったメールテンプレート情報の、明細情報ごとに、明細情報の持つショップコード、メールタイプ<br>
	 * メールテンプレートNo、メールテンプレート枝番より、メールテンプレート明細マスタからメールテンプレート明細情報を取得します。</li>
	 * <li>7. 6で取得したメールテンプレート明細が存在しなかった場合、エラーを返します。</li>
	 * <li>8. 引数で受取ったメールテンプレート情報の明細情報に、6で取得したマスタのヘッダ情報の行ID、作成者、作成日をセットします。</li>
	 * <li>9. 8で値をセットしたメールテンプレート情報の明細情報に、DatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>10. 9で値をセットした明細情報にValidationチェックを行います。</li>
	 * <li>11. 上記処理10までの処理で値をセットしたヘッダ情報と明細情報のリストを用いて、メールテンプレートヘッダマスタ、<br>
	 * メールテンプレート明細マスタにたいして更新処理を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>template≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param template
	 *            テンプレート情報のセット
	 * @return サービス処理結果
	 */
	ServiceResult updateMailTemplateDetail(MailTemplateSuite template);

	// Add by V10-CH start
	ServiceResult updateSmsTemplateDetail(SmsTemplateSuite template);

	// Add by V10-CH end

	/**
	 * 情報メールを新規登録します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>情報メール名を指定し、空の情報メールを作成します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 情報メールの一覧を取得し、テンプレートNoの最大値を取得します。</li>
	 * <li>2. webショップの設定情報を取得します。</li>
	 * <li>3. メールテンプレートヘッダ情報を新規に生成し、前提条件に記述した内容に沿って値をセットしていきます。</li>
	 * <li>4. 3で値をセットしたメールテンプレートヘッダ情報にDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>5. 4で値をセットしたメールテンプレートヘッダ情報にValidationチェックを行います。</li>
	 * <li>6. メールテンプレートヘッダマスタに登録処理を行います。</li>
	 * <li>7. メールテンプレート明細情報を新規に生成し、前提条件に記述した内容に沿って値をセットしていきます。</li>
	 * <li>8. 7で値をセットしたメールテンプレート明細情報にDatabaseUtilを用いてユーザーステータスをセットします。</li>
	 * <li>9. 8で値をセットしたメールテンプレート明細情報にValidationチェックを行います。</li>
	 * <li>10. メールテンプレート明細マスタに登録処理を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>生成情報はメールテンプレートヘッダ１件とメールテンプレート明細１件ずつとし、<br>
	 * メールテンプレートヘッダは下記情報で固定登録とします。</dd>
	 * <dd>
	 * <ol>
	 * <li>ショップコード サイトショップコード</li>
	 * <li>テンプレート番号 連番 メールタイプ 情報メール固定</li>
	 * <li>件名 空</li>
	 * <li>構造 [#MAIN#]</li>
	 * <li>表示順 0</li></dd>
	 * <dd>メールテンプレート明細は下記情報で登録します。</dd>
	 * <dd>
	 * <ol>
	 * <li>ショップコード サイトショップコード</li>
	 * <li>テンプレート番号 連番 メールタイプ 情報メール固定</li>
	 * <li>テンプレート枝番 1</li>
	 * <li>親枝番 0</li>
	 * <li>階層 1</li>
	 * <li>本文 空</li>
	 * <li>構造名 引数の情報メール名</li>
	 * <li>置換タグ [#MAIN#]</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param informationName
	 *            情報メール名
	 * @return サービス処理結果
	 */
	ServiceResult insertInformationMail(String informationName);

	// Add bny V10-CH start
	ServiceResult insertInformationSms(String informationName);

	// Add bY V10-CH end

	/**
	 * メールテンプレート情報を削除します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>MailTemplateSuiteに設定されている全ヘッダ・明細情報を同一トランザクションで削除します。
	 * 
	 * 
	 * <ol>
	 * <li>1. 引数で受取ったMailTemplateSuiteのヘッダの持つショップコード、メールタイプ、メールテンプレートNoより<br>
	 * メールテンプレートヘッダマスタから削除対象のデータを取得します。</li>
	 * <li>2. 引数で受取ったMailTemplateSuiteの明細リストごとの、ショップコード、メールタイプ、メールテンプレートNo、<br>
	 * メールテンプレート枝番よりメールテンプレート明細マスタから削除対象データのリストを取得します。</li>
	 * <li>3. 1で取得したヘッダ情報が存在する場合、メールテンプレートヘッダマスタへ削除処理を行います。</li>
	 * <li>4. 2で取得した明細情報のリストごとに、メールテンプレート明細マスタへ削除処理を行います。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>template≠null</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>特にありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param template
	 *            メールテンプレート
	 * 
	 * 
	 * @return サービス処理結果
	 */
	ServiceResult deleteMailTemplate(MailTemplateSuite template);

	/**
	 * 各ショップの送料ルール、配送先の都道府県、合計金額から送料を計算します。
	 * 
	 * 
	 * <p>
	 * <dl>
	 * <dt><b>処理概要: </b></dt>
	 * <dd>
	 * <ol>
	 * <li>ショップコード、配送種別コードから配送種別(適用送料ルール)を特定します。</li>
	 * <li>ショップコード、都道府県コードから配送地域ブロックを特定します。</li>
	 * <li>配送種別、配送地域ブロックから送料を算出します。</li></dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>前提条件: </b></dt>
	 * <dd>
	 * <ol>
	 * <li>ショップコード、配送種別コードは事前に登録されているものとします。</li>
	 * <li>都道府県コードはnullでないものとします。</li>
	 * <li>注文金額合計は0以上とします。</li></dd>
	 * </dd>
	 * </dl>
	 * </p>
	 * <p>
	 * <dl>
	 * <dt><b>事後条件: </b></dt>
	 * <dd>ありません。</dd>
	 * </dl>
	 * </p>
	 * 
	 * @param shopCode
	 *            ショップコード
	 * 
	 * 
	 * @param deliveryTypeNo
	 *            配送種別コード
	 * @param prefectureCode
	 *            都道府県コード(JIS X0401/ISO 3166-2:JP)
	 * @param itemCount
	 *            商品点数
	 * @param subTotal
	 *            配送の注文金額合計
	 * @return 計算結果の送料を返します。
	 */
	BigDecimal calculateShippingCharge(String shopCode, Long deliveryTypeNo,
			String prefectureCode, int itemCount, BigDecimal subTotal);

	List<CouponIssue> getCouponIssueList(String shopCode);

	CouponIssue getCouponIssue(String shopCode, Long couponIssueNo);

	ServiceResult insertCouponIssue(CouponIssue couponIssue);

	ServiceResult updateCouponIssue(CouponIssue couponIssue);

	ServiceResult deleteCouponIssue(String shopCode, Long couponIssueNo);

	List<CouponResearch> getCouponResearch(String shopCode, String couponIssueNo);

	CouponResearchInfo getCouponResearchInfo(String shopCode, String couponCode);

	/**
	 * 取得配送公司全部信息
	 * 
	 * @param shopCode
	 * @return 配送公司信息集合
	 */
	List<DeliveryCompany> getDeliveryCompanyList(String shopCode);

	// soukai add 2011/12/19 ob start
	/**
	 * 配送公司编号不能重复
	 * 
	 * @param deliveryCompanyNo
	 * @return 配送公司信息
	 */		
	DeliveryCompany getDeliveryCompanyByNo(String deliveryCompanyNo);	

	/**
	 * 根据(店铺编号)查询配送会社
	 * 
	 * @param shopCode
	 * @param deliveryCompanyNo
	 * @return 指定的配送会社信息
	 */
	// soukai add 2011/12/19 ob end
	DeliveryCompany getDeliveryCompany(String shopCode, String deliveryCompanyNo);

	/**
	 * 根据(店铺编号)删除配送会社
	 * 
	 * @param shopCode
	 *            店铺编号
	 * @return 指定的配送会社信息
	 */		
	ServiceResult deleteDeliveryCompany(String shopCode, String deliveryCompanyNo);

	/**
	 * 取得地域区分全部信息
	 * 
	 * @param shopCode
	 * @param deliveryCompanyNo
	 * @return 地域区分信息集合
	 */	
	List<ShippingChargeSuite> getShippingChargeList(String shopCode, String deliveryCompanyNo);

	/**
	 * 追加配送公司信息
	 * 
	 * @param company
	 * @return 配送公司信息
	 */	
	ServiceResult insertDeliveryCompany(DeliveryCompany company);


	/**
	 * 更新配送公司信息
	 * 
	 * @param company
	 * @return 配送公司信息
	 */		
	ServiceResult updateDeliveryCompany(DeliveryCompany company);

	// 更新运费信息
	ServiceResult updateDeliveryRegionCharge(
			DeliveryRegionCharge deliveryRegionCharge);

	// 查询运费信息
	SearchResult<DeliveryRegionChargeInfo> getDeliveryRegionCharge(DeliveryRegionChargeCondition condition);

	// soukai add 2011/12/19 ob start

	/**
	 * 跟据配送公司编号、店铺号查询运费功能
	 * @param shopCode 店铺号
	 * @param deliveryCompanyNo 配送公司编号
	 * @param regionBlockId  配送地域编号
	 * @return DeliveryRegionCharge
	 */
	DeliveryRegionCharge getDeliveryRegionCharge(String shopCode,String deliveryCompanyNo,Long regionBlockId);
	
	/**
	 * 跟据配送公司编号、店铺号查询运费集合功能
	 * @param shopCode 店铺号
	 * @param deliveryCompanyNo 配送公司编号
	 * @param regionBlockId  配送地域编号
	 * @return List<DeliveryRegionCharge>
	 */
	List<DeliveryRegionCharge> getDeliveryRegionChargeList(String shopCode,String deliveryCompanyNo);
	
	/**
	 * 根据店铺号、地域名、货到付款区分、配送希望日指定区分、公司Id和配送时间ID查询所有的配送信息的信息
	 * @param shopCode shopCode
	 * @param deliveryComputerNo 公司编号
	 * @param prefectureCode 地域Id
	 * @param timeCode 配送时间ID
	 * @return DeliveryRegionAppointedTime
	 */
	DeliveryRelatedInfo getDeliveryCompanyAppointedTimeListByTimeCode(String shopCode,String deliveryCompanyNo,String prefectureCode,Long codType,Long deliveryDateType,String timeCode);
	
	/**
	 * 取得配送公司关联情报集合
	 * @param shopCode shopCode
	 * @param deliveryCompanyNo 公司编号
	 * @param prefectureCode 地域号
	 * @return List<DeliveryRelatedInfo>
	 */
	List<DeliveryRelatedInfo> getDeliveryRelatedInfoList(String shopCode,String deliveryCompanyNo,String prefectureCode);
	
	List<JdDeliveryRelatedInfo> getDeliveryRelatedInfoListJd(String shopCode,String deliveryCompanyNo,String prefectureCode);
	
	List<TmallDeliveryRelatedInfo> getDeliveryRelatedInfoListTmall(String shopCode,String deliveryCompanyNo,String prefectureCode);
	
	/**
	 * 执行取得配送公司关联情报更新功能
	 * @return ServiceResult
	 */
	ServiceResult updateDeliveryRelatedInfo(DeliveryRelatedInfo deliveryRelatedInfo);
	
	/**
	 * 执行取得配送公司关联情报插入功能
	 * @param DeliveryRelatedInfo 配送公司关联情报Bean
	 * @return  ServiceResult
	 */
	ServiceResult insertDeliveryRelatedInfo(DeliveryRelatedInfo deliveryRelatedInfo);
	
	ServiceResult insertDeliveryRelatedInfoJd(JdDeliveryRelatedInfo deliveryRelatedInfo);
	
	ServiceResult insertDeliveryRelatedInfoTmall(TmallDeliveryRelatedInfo deliveryRelatedInfo);
	
	/**
	 * 执行取得配送公司关联情报删除功能
	 * @return ServiceResult
	 */
	ServiceResult deleteDeliveryRelatedInfo(DeliveryRelatedInfo deliveryRelatedInfo) ;
	
	ServiceResult deleteDeliveryRelatedInfoJd(JdDeliveryRelatedInfo deliveryRelatedInfo) ;
	
  ServiceResult deleteDeliveryRelatedInfoTmall(TmallDeliveryRelatedInfo deliveryRelatedInfo) ;
	
	/**
	 * 根据根据店铺号、地域名、公司Id查询配送信息的组别信息
	 * @param shopCode shopCode
	 * @param deliveryComputerNo 公司编号
	 * @param prefectureCode 地域Id
	 * @return List<DeliveryRelatedInfo>
	 */
	List<DeliveryRelatedInfo> getDeliveryRelatedInfo(String shopCode,String deliveryCompanyNo,String prefectureCode);

	/**
	 * 设置默认配送公司
	 * @param String deliveryCompanyNo
	 * @return ServiceResult
	 */
	ServiceResult setDefaultDeliveryCompany(String deliveryCompanyNo);
	
	/**
	 * 根据根据配送信息关联编号查询配送关联信息
	 * @param deliveryRelatedInfoNo
	 * @return DeliveryRelatedInfo
	 */
	DeliveryRelatedInfo getDeliveryRelatedInfo(String deliveryRelatedInfoNo);
	
  JdDeliveryRelatedInfo getDeliveryRelatedInfoJd(String deliveryRelatedInfoNo);
	
  TmallDeliveryRelatedInfo getDeliveryRelatedInfoTmall(String deliveryRelatedInfoNo);
	
	/**
   * 根据根据店铺号、地域名、公司Id查询配送公司的地域手续费信息
   * @param shopCode shopCode
   * @param deliveryComputerNo 公司编号
   * @return List<DeliveryRegion>
   */
	List<DeliveryRegion> getDeliveryRegionListByDeliveryCompanyNo(String shopCode, String deliveryCompanyNo);
	
  List<TmallDeliveryRegion> getDeliveryRegionListByDeliveryCompanyNoTmall(String shopCode, String deliveryCompanyNo);
  
  List<JdDeliveryRegion> getDeliveryRegionListByDeliveryCompanyNoJd(String shopCode, String deliveryCompanyNo);
	
	/**
	 * 获取指定的  DeliveryRegion
	 * @param shopCode           
	 * @param deliveryCompanyNo  公司编号
	 * @param prefectureCode     地域Id
	 * @return
	 */
	DeliveryRegion getDeliveryRegion(String shopCode, String deliveryCompanyNo, String prefectureCode);
	
  JdDeliveryRegion getDeliveryRegionJd(String shopCode, String deliveryCompanyNo, String prefectureCode);
	
	TmallDeliveryRegion getDeliveryRegionTmall(String shopCode, String deliveryCompanyNo, String prefectureCode);
	
	/**
	 * 插入DeliveryRegion信息列表
	 * @param deliveryRegionList
	 * @return 插入结果
	 */
	ServiceResult insertDeliveryRegionList(List<DeliveryRegion> deliveryRegionList);
	
  ServiceResult insertDeliveryRegionListJd(List<JdDeliveryRegion> deliveryRegionList);
	
	ServiceResult insertDeliveryRegionListTmall(List<TmallDeliveryRegion> deliveryRegionListTmall);
	
	 /**
   * 插入DeliveryLocation信息列表
   * @param deliveryLocationList
   * @return 插入结果
   */
  ServiceResult insertDeliveryLocationList(List<DeliveryLocation> deliveryLocationList);
  
  ServiceResult insertDeliveryLocationListJd(List<JdDeliveryLocation> deliveryLocationList);
  
  ServiceResult insertDeliveryLocationListTmall(List<TmallDeliveryLocation> deliveryLocationListTmall);
	
	/**
	 * 插入DeliveryRegion信息
	 * @param deliveryRegion
	 * @return 插入结果
	 */
	ServiceResult insertDeliveryRegion(DeliveryRegion deliveryRegion);
	
	/**
	 * 更新DeliveryRegion信息
	 * @param deliveryRegion
	 * @return 更新结果
	 */
	ServiceResult updateDeliveryRegion(DeliveryRegion deliveryRegion);
	
	/**
	 * 删除指定的DeliveryRegion信息
	 * @param deliveryRegion
	 * @return 删除结果
	 */
	ServiceResult deleteDeliveryRegion(DeliveryRegion deliveryRegion);
	
	ServiceResult deleteDeliveryRegionJd(JdDeliveryRegion deliveryRegion);
	
	ServiceResult deleteDeliveryRegionTmall(TmallDeliveryRegion deliveryRegion);
	
	/**
	 * 获得省/直辖市/自治区信息
	 * @param prefectureCode
	 * @return 省/直辖市/自治区信息
	*/
    Prefecture getPrefectureInfo(String prefectureCode);
    
    JdPrefecture getPrefectureInfoJd(String prefectureCode);
    
    /**
	 * 获得运费
	 * @param prefectureCode 
	 * @param totalOrderPrice 
	 * @param totalCommodityWeight 
	 * @return 省/直辖市/自治区信息
	*/
    BigDecimal getShippingCharge(String prefectureCode,BigDecimal totalOrderPrice,BigDecimal totalCommodityWeight, String deliveryCompanyNo);
  //soukai add 2011/12/20 ob shb end
    
    // 20120113 shen add start
    DeliveryRegionCharge getDeliveryRegionCharge(String shopCode, String prefectureCode, String deliveryCompanyNo);
    
    Area getArea(String areaCode);
    // 20120113 shen add end
}