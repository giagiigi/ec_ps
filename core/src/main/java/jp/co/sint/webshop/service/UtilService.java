package jp.co.sint.webshop.service;

import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.utility.InquiryConfig;
import jp.co.sint.webshop.utility.MailMagazineConfig;

public interface UtilService {

  /**
   * 指定されたショップのショップ名称を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で与えられたshopCodeを持つショップのショップコードを取得し返します。
   * <ol>
   * <li>引数で与えられたshopCodeを持つショップを取得します。</li>
   * <li>取得したショップのショップ名を返します。ショップが存在しない場合は空文字を返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
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
   *          取得対象のショップのショップコード
   * @return 引数で与えられたshopCodeを持つショップのショップ名称
   */
  String getShopName(String shopCode);

  /**
   * サイトを除く全てのショップのショップ名とショップコードのセットを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>全てのショップのショップ名とショップコードのセットを取得します。
   * 未入力時データ(「選択してください」)及びサイトのショップ情報は含まれません。
   * <ol>
   * <li>ショップテーブルからサイト以外の全てのショップのショップコード及びショップ名を取得します。</li>
   * <li>取得したデータからショップ名とショップコードのセットを作成しリストにします。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @return サイトを除く全てのショップのショップ名とショップコードのセットのリスト
   */
  List<CodeAttribute> getShopNames();

  /**
   * サイトを除く全てのショップのショップ名とショップコードのセットを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd> 全てのショップのショップ名とショップコードのセットを取得します。
   * blankがtrueの場合は未入力時データ(「選択してください」)を最初に挿入します。
   * <ol>
   * <li>ショップテーブルからサイト以外の各ショップのショップコード及びショップ名を取得します。</li>
   * <li>取得したデータからショップ名とショップコードのセットを作成しリストにします。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param blank
   *          true:未入力時データ(「選択してください」)を含める false:未入力時データ(「選択してください」)を含めない
   * @return サイトを除く全てのショップのショップ名とショップコードのセットのリスト
   */
  List<CodeAttribute> getShopNames(boolean blank);

  /**
   * 全てのショップのショップ名とショップコードのセットを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd> 全てのショップのショップ名とショップコードのセットを取得します。
   * blankがtrueの場合は未入力時データ(「選択してください」)を最初に挿入します。また、siteがtrueの場合はサイト情報を含みます。
   * <ol>
   * <li>ショップテーブルから各ショップのショップコード及びショップ名を取得します。</li>
   * <li>取得したデータからショップ名とショップコードのセットを作成しリストにします。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param blank
   *          true:未入力時データ(「選択してください」)を含める false:未入力時データ(「選択してください」)を含めない
   * @param site
   *          true:サイト情報を含める false:サイト情報を含めない
   * @return 全てのショップのショップ名とショップコードのセットのリスト
   */
  List<CodeAttribute> getShopNames(boolean blank, boolean site);

  /**
   * 全てのショップのショップ名とショップコードのセットを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd> 全てのショップのショップ名とショップコードのセットを取得します。
   * blankがtrueの場合は未入力時データ(「選択してください」)を最初に挿入します。また、siteがtrueの場合はサイト情報を含みます。
   * また、justOpenがtrueの場合は現在開店中のショップのみを取得します。
   * <ol>
   * <li>ショップテーブルから各ショップのショップコード及びショップ名を取得します。</li>
   * <li>取得したデータからショップ名とショップコードのセットを作成しリストにします。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param blank
   *          true:未入力時データ(「選択してください」)を含める false:未入力時データ(「選択してください」)を含めない
   * @param site
   *          true:サイト情報を含める false:サイト情報を含めない
   * @param justOpen
   *          true:開店中のショップのみ false:全てのショップ
   * @return 全てのショップのショップ名とショップコードのセットのリスト
   */
  List<CodeAttribute> getShopNames(boolean blank, boolean site, boolean justOpen);

  /**
   * 全てのショップのショップ名とショップコードのセットを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd> 全てのショップのショップ名とショップコードのセットを取得します。 blankがtrueの場合は未入力時データを最初に挿入します。<br>
   * その場合、defaultAllShopがtrueの場合は未入力時データに「すべてのショップ」と設定し、<br>
   * falseの場合は「選択してください」と設定します。また、siteがtrueの場合はサイト情報を含みます。
   * また、justOpenがtrueの場合は現在開店中のショップのみを取得します。
   * <ol>
   * <li>ショップテーブルから各ショップのショップコード及びショップ名を取得します。</li>
   * <li>取得したデータからショップ名とショップコードのセットを作成しリストにします。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param blank
   *          true:未入力時データを含める false:未入力時データを含めない
   * @param site
   *          true:サイト情報を含める false:サイト情報を含めない
   * @param justOpen
   *          true:開店中のショップのみ false:全てのショップ
   * @param defaultAllShop
   *          true:未入力時データ(「すべてのショップ」)を含める false:未入力時データ(「選択してください」)を含めない<br>
   *          blankがtrueの場合に有効
   * @return 全てのショップのショップ名とショップコードのセットのリスト
   */
  List<CodeAttribute> getShopNames(boolean blank, boolean site, boolean justOpen, boolean defaultAllShop);

  /**
   * 全てのショップのショップ名とショップコードのセットを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd> 全てのショップのショップ名とショップコードのセットを取得します。<br>
   * 未入力時データを最初に挿入することを前提とし、その場合、未入力時データに「すべてのショップ」と設定します。<br>
   * また、siteがtrueの場合はサイト情報を含みます。
   * <ol>
   * <li>ショップテーブルから各ショップのショップコード及びショップ名を取得します。</li>
   * <li>取得したデータからショップ名とショップコードのセットを作成しリストにします。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param site
   *          true:サイト情報を含める false:サイト情報を含めない
   * @param justOpen
   *          true:開店中のショップのみ false:全てのショップ
   * @return 全てのショップのショップ名とショップコードのセットのリスト
   */
  List<CodeAttribute> getShopNamesDefaultAllShop(boolean site, boolean justOpen);

  /**
   * 該当のショップに関連付けられた配送種別のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で与えられたshopCodeに関連付けられた配送種別のリストを取得します。
   * blankがtrueの場合は未入力時データ(「選択してください」)を最初に挿入します。
   * <ol>
   * <li>配送種別テーブルから引数で与えられたshopCodeを持つ配送種の配送種別、配送種別コードを取得します </li>
   * <li>取得したデータから配送種別名と配送種別コードのセットを作成します。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
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
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          検索対象のショップのショップコード
   * @param blank
   *          true:未入力時データ(「選択してください」)を含む false：未入力時データ(「選択してください」)を含まない
   * @return 対象のショップに関連付けられた配送種別の配送種別名と配送種別コードのリスト
   */
  List<CodeAttribute> getDeliveryTypes(String shopCode, boolean blank);
  
  // 20120116 ysy add start
  List<CodeAttribute> getDeliveryCompany(String shopCode, boolean blank);
  // 20120116 ysy add end

  /**
   * 指定された配送種別で選択可能なお届け日を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された配送種別とお届け先都道府県から選択可能なお届け日のリストを取得します。
   * 返されるリストには未入力時データ(「-----」)が含まれます。
   * <ol>
   * <li>指定された配送種別と都道府県から、配送リードタイムを取得します。</li>
   * <li>配送種別に含まれるショップコードから休日マスタを参照し、システム日付と配送リードタイムから最短お届け可能日を取得します。</li>
   * <li>最短お届け可能日からWebshopConfigにて設定された日数分日付を取得します。</li>
   * <li>5で取得したリストの最初に未入力時データを挿入し、返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>deliveryType、prefectureCodeがいずれもnullでないこと。</dd>
   * <dd>prefectureCodeがPrefectureCodeの定義域内にあること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param deliveryType
   *          お届け可能日時を取得する対象の配送種別
   * @param prefectureCode
   *          お届け先の都道府県
   * @return 選択可能なお届け日のリスト
   */
  List<CodeAttribute> getDeliveryAppointedDays(DeliveryType deliveryType, String prefectureCode);

  /**
   * 指定された配送種別で選択可能なお届け時間を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された配送種別とお届け先都道府県からお届け可能な日付のリストを取得します。 *
   * 返されるリストには未入力時データ(「-----」)が含まれます。
   * <ol>
   * <li>配送指定時間テーブルから指定された配送種別のお届け可能時間を取得します。</li>
   * <li>取得したお届け可能時間のリストの最初に未入力時データを挿入し返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>deliveryDate、prefectureCodeがいずれもnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param deliveryType
   *          配送種別
   */
  List<CodeAttribute> getDeliveryAppointedTimes(DeliveryType deliveryType);

  /**
   * 配送指定日から出荷指示日を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>配送指定日を元に出荷指示日を取得します。
   * <ol>
   * <li>配送種別と都道府県コードを元に最短お届け可能日を取得します。</li>
   * <li>配送指定日と最短お届け可能日の間の日数を計算します。</li>
   * <li>当日日付の翌日計算で営業日を飛ばして、2で取得した日数を加算します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>全ての引数が空でないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param deliveryAppointedDate
   *          配送指定日
   * @param deliveryType
   *          配送種別
   * @param prefectureCode
   *          都道府県コード
   * @return 配送指定日
   */
  Date createShippingDirectDate(Date deliveryAppointedDate, DeliveryType deliveryType, String prefectureCode);

  /**
   * 指定したショップに関連付けられている配送種別のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で指定されたショップに関連付けられている配送種別の配送種別名と配送種別コードのセットのリストを返します。
   * 返されるリストに未入力時データ(「選択してください」)は含まれません。
   * <ol>
   * <li>配送種別テーブルから指定のショップコードを持つ配送種別を取得します。</li>
   * <li>取得した配送種別から配送種別名と配送種別コードのセットを作成しリストにします。</li>
   * <li>作成されたリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコードがnullでないこと。</dd>
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
   *          ショップコード
   * @return 指定したショップに関連付けられている配送種別のリスト
   */
  List<CodeAttribute> getAvailableDeliveryType(String shopCode);

  // フロント、管理など条件つきの場合
  // List<CodeAttribute> getAvailableDeliveryType(String shopCode, int
  // displayType);

  /**
   * カテゴリ名とカテゴリコードのセットのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カテゴリ名とカテゴリコードのセットのリストを取得します。返されるリストには未選択時データ(「選択してください」)は含まれません。
   * <ol>
   * <li>カテゴリテーブルからカテゴリ名とカテゴリコードを取得します。</li>
   * <li>取得したカテゴリ名とカテゴリコードのセットをリストにします。</li>
   * <li>生成したリストを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 全てのカテゴリのカテゴリ名とカテゴリコードのセットのリスト
   */
  List<CodeAttribute> getCategoryNames();

  /**
   * 指定したカテゴリの子カテゴリのカテゴリ名とカテゴリコードのセットのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したカテゴリの全ての子カテゴリのカテゴリ名とカテゴリコードのセットのリストを取得します。
   * <ol>
   * <li>カテゴリテーブルから指定したカテゴリを親に持つカテゴリを全て取得します。</li>
   * <li>取得したカテゴリのカテゴリ名とカテゴリコードのセットのリストを作成します。</li>
   * <li>作成したリストを返します。</li>
   * </ol>
   * </dd>
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
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param categoryCode
   *          親カテゴリのカテゴリコード
   * @return 引数で与えられたカテゴリを親に持つカテゴリのカテゴリ名とカテゴリコードのセットのリスト
   */
  List<CodeAttribute> getSubCategoryNames(String categoryCode);

  // List<CodeAttribute> getRegionBlocks(String shopCode);

  /**
   * 全ての顧客グループの顧客グループ名と顧客グループコードのセットのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>全ての顧客グループの顧客グループ名と顧客グループコードのセットのリストを取得します。
   * 返されるリストには未入力時データ(「選択してください」)が最初に挿入されます。
   * <ol>
   * <li>顧客グループテーブルから顧客グループ名と顧客グループコードを取得します。</li>
   * <li>取得した顧客グループ名と顧客グループコードのセットをリストにします。</li>
   * <li>作成したリストの最初に未入力時データを挿入し、返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 全ての顧客グループの顧客グループ名と顧客グループコードのセットのリスト
   */
  List<CodeAttribute> getCustomerGroupNames();

  /**
   * 指定したショップの在庫状況分類名と在庫状況分類番号のセットのリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップの在庫状況分類名と在庫状況分類番号のセットのリストを返します。
   * 返されるリストには未入力時データ(「選択してください」)が最初に挿入されます。
   * <ol>
   * <li>在庫状況テーブルから引数のshopCodeを持つレコードの在庫状況分類名と在庫状況分類番号を取得します。</li>
   * <li>取得したデータから在庫状況分類名と在庫状況分類番号のセットのリストを作成します。</li>
   * <li>作成したリストの最初に未入力時データを挿入し返します。</li>
   * </ol>
   * </dd>
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
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          在庫状況を取得する対象のショップのショップコード
   * @return 指定されたショップの在庫状況の在庫状況分類名と在庫状況分類番号のセットのリスト
   */
  List<CodeAttribute> getStockStatusNames(String shopCode);

  /**
   * 指定したアンケートの設問のうち、自由回答形式でないもののアンケート設問内容とアンケート設問番号のセットのリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したアンケートの設問のうち、自由回答形式でないもののアンケート設問内容とアンケート設問番号のセットのリストを返します。
   * 返されるリストには未入力時データ(「選択してください」)が最初に挿入されます。
   * <ol>
   * <li>アンケート設問テーブルから指定のアンケートコードを持ち、自由回答形式でない設問のアンケート設問内容とアンケート設問番号を取得します。</li>
   * <li>取得したデータからアンケート設問内容とアンケート設問番号のセットのリストを作成します。</li>
   * <li>作成したリストの最初に未入力時データを挿入し返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>enqueteCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param enqueteCode
   *          取得対象のアンケートのアンケートコード
   * @return 指定したアンケートのアンケート設問内容とアンケート設問番号のセットのリスト
   */
  List<CodeAttribute> getEnqueteQuestions(String enqueteCode);

  /**
   * 現在適用されている消費税率を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>現在適用されている消費税率を取得します。
   * <ol>
   * <li>消費税テーブルから現在適用中の消費税率を取得し、返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 現在適用中の消費税率
   */
  Long getAppliedTaxRateNow();

  /**
   * 指定した日時に適用中の消費税率を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した日時に適用中の消費税率を取得します。
   * <ol>
   * <li>消費税テーブルから指定した日時に適用されている消費税率を取得し、返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>whenがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param when
   *          消費税率を取得する日時
   * @return 指定日時に適用されている消費税率
   */
  Long getAppliedTaxRateWhen(Date when);

  /**
   * 設定ファイルから顧客問合せ時に使用する設定情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>設定ファイルから顧客問合せ時に使用する設定情報を取得します。
   * <ol>
   * <li>設定ファイルから顧客問合せ時に使用する設定情報を取得し、返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @return 顧客問合せ設定情報
   */
  InquiryConfig getInquiryConfig();

  /**
   * 指定したショップコード、商品コードを持つ商品に関連付けられたギフトのギフト名とギフトコードのセットのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したショップコード、商品コードを持つ商品に関連付けられたギフトのギフト名とギフトコードのセットのリストを取得します。
   * 返されるリストには未選択時データ(「-----」)が最初に挿入されます。
   * <ol>
   * <li>ギフトテーブルから指定したショップコード、商品コードを持つレコードのギフト名、ギフトコードを取得します。</li>
   * <li>取得したデータからギフト名、ギフトコードのセットのリストを作成します。</li>
   * <li>作成したリストの最初に未選択時データを挿入し返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode、commodityCodeのいずれもnullでないこと。</dd>
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
   *          対象商品のショップコード
   * @param commodityCode
   *          対象商品の商品コード
   * @return 指定したショップコード、商品コードの商品に関連付けられたギフトのギフト名とギフトコードのセットのリスト
   */
  List<CodeAttribute> getGiftList(String shopCode, String commodityCode);

  /**
   * メールマガジンを配信登録、配信停止、配信確認する際の自動配信メールテンプレートを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>設定ファイルからメールマガジン配信登録、配信停止、配信確認する際の自動配信メールテンプレートを取得します。
   * <ol>
   * <li>設定ファイルからメールマガジン配信登録、配信停止、配信確認する際の自動配信メールテンプレートを取得し、返します。
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @return メールマガジンを配信登録、配信停止、配信確認する際の自動配信メールテンプレート
   */
  MailMagazineConfig getMailMagazineConfig();

  /**
   * 主要カテゴリのコードと名称のペアを返します。主要カテゴリには、ルートカテゴリとその子要素(階層レベル1)が含まれます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カテゴリマスタを検索し、主要カテゴリのコードと名称のペアを返します。 </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>なし</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし</dd>
   * </dl>
   * </p>
   * 
   * @return 主要カテゴリのコードと名称のペアを表すCodeAttributeのリスト
   */
  List<CodeAttribute> getMajorCategories();
  
  //add by V10-CH 170 start
  
  public List<CodeAttribute> getCityNames(String regionCode);
  
  public List<CodeAttribute> getJdCityNames(String regionCode);
  
  public String getCityName(String regionCode, String cityCode);
  //add by V10-CH 170 end
  
  // 20111227 shen add start
  List<CodeAttribute> getDeliveryDateList(String shopCode, String prefectureCode, boolean codFlg, List<CartCommodityInfo> commodityList, String weight, String deliveryCompanyNo);
  
  List<CodeAttribute> getDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDateType, String weight, String deliveryCompanyNo);
  
  List<CodeAttribute> getDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate, String areaCode, String weight, String deliveryCompanyNo);
  
  DeliveryCompany getDeliveryCompany(String shopCode, String prefectureCode, boolean codFlg, String weight);
  // 20111227 shen add end
  // add by lc 2012-08-23 start
  // 2013/04/21 优惠券对应 ob update start
  List<DeliveryCompany> getDeliveryCompanys(String shopCode, String prefectureCode, String cityCode, String areaCode, boolean codFlg, String weight);
  // 2013/04/21 优惠券对应 ob update end
  // add by lc 2012-08-23 end
  //soukai add 2012/03/27 ob start
  List<CodeAttribute> getTmallDeliveryDateList(String shopCode, String prefectureCode, boolean codFlg, List<CartCommodityInfo> commodityList);

  List<CodeAttribute> getJdDeliveryDateList(String shopCode, String prefectureCode, boolean codFlg, List<CartCommodityInfo> commodityList);
  
  List<CodeAttribute> getTmallDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDateType);
  
  List<CodeAttribute> getTmallDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate, String areaCode);
  
  List<CodeAttribute> getJdDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate, String areaCode);
  
  DeliveryCompany getTmallDeliveryCompany(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate,
      String deliveryTimeStart, String deliveryTimeEnd);
  //soukai add 2012/03/27 ob end
  
  // 20120105 shen add start
  /**
   * 省份列表生成
	 *
   * @return 省份列表
   */
  List<CodeAttribute> createPrefectureList();
  
  List<CodeAttribute> createJdPrefectureList();
  
  List<CodeAttribute> createPrefectureList(boolean hasTitle);
  
  /**
   * 城市列表生成
	 *
   * @param prefectureCode
   * @return 城市列表
   */
  List<CodeAttribute> createCityList(String prefectureCode);
  
  /**
   * Jd城市列表生成
   *
   * @param prefectureCode
   * @return Jd城市列表
   */
  List<CodeAttribute> createJdCityList(String prefectureCode);
  
  
  /**
   * 区县列表生成
	 *
   * @param prefectureCode
   * @param cityCode
   * @return 区县列表
   */
  List<CodeAttribute> createAreaList(String prefectureCode, String cityCode);
  
  /**
   * Jd区县列表生成
   *
   * @param prefectureCode
   * @param cityCode
   * @return Jd区县列表
   */
  List<CodeAttribute> createJdAreaList(String prefectureCode, String cityCode);
  
  
  /**
   * 省份、城市、区县切换Script生成
	 *
   * @return Script
   */
  String createAddressScript();
  
  /**
   * JD省份、城市、区县切换Script生成
   *
   * @return Script
   */
  String createJdAddressScript();
  
  /**
   * 省份名取得
	 *
   * @param prefectureCode
   * @return 省份名
   */
  String getPrefectureName(String prefectureCode);
  
  /**
   * 区县名取得
	 *
   * @param areaCode
   * @return 区县名
   */
  String getAreaName(String areaCode);
  // 20120105 shen add end
  
  // 20120112 shen add start
  String getPrefectureCode(String prefectureName);
  // 20120112 shen add end
  
  // soukai add 20120107 ob start
  /**
   * 获得默认配送公司
	 *
   * @return 配送公司
   */
  DeliveryCompany getDefaultDeliveryCompany();
  // soukai add 20120107 ob end
  
  // 20120219 shen add start
  boolean isBusinessDay(String shopCode, Date day);
  // 20120219 shen add end
  
  // 20120515 shen add start
  String getNameByLanguage(String nameCn, String nameEn, String nameJp);
  
  String getNameByLanguage(String nameCn, String nameEn, String nameJp, String languageCode);
  // 20120515 shen add end

  List<CodeAttribute> getOriginalPlace();
  
  OriginalPlace getOriginal(String originalplace);
  
  OriginalPlace getOriginal(String originalplace,String originalplaceEn,String originalplaceJp);
  
  // 根据产地code查询产地信息 20130416 add by yyq start
  OriginalPlace getOriginalPlaceByCode(String originalCode);
  // 根据产地code查询产地信息 20130416 add by yyq end

}
