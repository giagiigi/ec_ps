package jp.co.sint.webshop.service;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.GoogleAnalysis;
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.data.dto.OnlineService;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.data.dto.UserAccessLog;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.data.dto.UserPermission;
import jp.co.sint.webshop.service.shop.InformationCountSearchCondition;

/**
 * SI Web Shopping 10 サイト管理サービス(SiteManagementService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface SiteManagementService {

  /**
   * サイト情報（ショップ区分が"0"サイトのショップ）を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップマスタより、サイト情報を取得します。
   * <ol>
   * <li>ショップマスタより、ショップ区分が"0"のショップ情報一覧を取得します。</li>
   * <li>1で取得したショップ情報一覧の1件目をサイト情報として返します。<br>
   * またサイト情報が存在しなかった場合、このメソッドはnullを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップマスタにショップ区分が"0"(サイト情報)は1件しか存在しないものとします。 </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return サイト情報
   */
  Shop getSite();

  /**
   * サイト情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったサイト情報より、ショップマスタを更新します。
   * <ol>
   * <li>getSiteメソッドを使い、ショップマスタからサイト情報を取得します。</li>
   * <li>1で取得したサイト情報がnull、又は取得したサイト情報の持つショップコードがnullの時は<br>
   * 戻り値のサービス処理結果にサイト未登録エラーをセットし、値を返します。</li>
   * <li>引数で受取ったサイト情報に、1で取得したショップマスタのサイト情報から<br>
   * 行ID、作成者、作成日をセットします。</li>
   * <li>3で値をセットした更新用サイト情報に対してValidationチェックを行い、エラーが存在した<br>
   * 場合はエラーの件数分サービス処理結果にValidationエラーをセットし、値を返します。</li>
   * <li>ショップマスタに対して、DAOを用いて更新処理を行います。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>更新するサイト情報はnullでないものとします。</dd>
   * <dd>更新するサイト情報のショップ名はnullでないものとします。</dd>
   * <dd>更新対象のサイト情報が存在しない場合、サイト未登録エラーが返されます。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ショップマスタに登録されているサイト情報(ショップ区分が0の物)が、引数で<br>
   * 渡したサイト情報に更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param site
   *          更新するサイト情報
   * @return サービス処理結果
   */
  ServiceResult updateSite(Shop site);

  /**
   * ユーザー権限リストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったユーザーコードより、管理ユーザ権限マスタから該当するユーザーの持つ<br>
   * 権限をリストで返します。
   * <ol>
   * <li>1. 引数で受取ったユーザーコードより、管理ユーザ権限マスタからDAOを用いて該当する<br>
   * ユーザーの持つ権限をリストで返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ユーザーコードはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param userCode
   *          ユーザーコード
   * @return ユーザー権限のリスト
   */
  List<UserPermission> getUserPermissionList(Long userCode);

  /**
   * ユーザアクセスログを登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったユーザアクセスログを、管理側アクセスログマスタへ登録します。
   * <ol>
   * <li>引数で受取ったユーザアクセスログに、UtilServiceを用いてユーザアクセスログIDの<br>
   * シーケンスNoをセットします。</li>
   * <li>1で値をセットしたユーザアクセスログに、DatabaseUtilを用いてユーザーステータスを<br>
   * セットします。</li>
   * <li>2で値をセットしたユーザアクセスログに対してValidationチェックを行い、エラーが存在した<br>
   * 場合はエラーの件数分サービス処理結果にValidationエラーをセットし、値を返します。</li>
   * <li>管理側アクセスログマスタへDAOを用いて登録を行います。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ユーザアクセスログはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>管理側アクセスログマスタに引数で渡したユーザアクセスログが登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param accessLog
   *          ユーザアクセスログ
   * @return サービス処理結果
   */
  ServiceResult insertUserAccessLog(UserAccessLog accessLog);

  /**
   * ユーザアクセスログを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったSearchConditionより、管理側アクセスログマスタからユーザーアクセスログの<br>
   * 検索結果情報を取得します。
   * <ol>
   * <li>引数で受取ったconditionがUserAccessLogSearchConditionのインスタンスで無い場合<br>
   * 空のユーザアクセスログの検索結果情報を返します。</li>
   * <li>引数で受取ったconditionがUserAccessLogSearchConditionのインスタンスであった場合、<br>
   * 管理側アクセスログマスタからconditionの検索条件に該当するデータのリストを取得し<br>
   * 検索結果情報に値をセットして返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>検索条件のconditionはnullでないとします。</dd>
   * <dd>検索条件のconditionがUserAccessLogSearchConditionのインスタンスで無い場合<br>
   * 空のユーザアクセスログの検索結果情報が返ります。</dd>
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
   * @return ユーザアクセスログの検索結果情報
   */
  SearchResult<UserAccessLog> getUserAccessLog(SearchCondition condition);

  /**
   * ユーザーアカウントのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったSearchConditionより、管理ユーザマスタからユーザーアカウントの<br>
   * 検索結果情報を取得します。
   * <ol>
   * <li>引数で受取ったconditionがUserAccessSearchConditionのインスタンスで無い場合<br>
   * 空のユーザアカウントの検索結果情報を返します。</li>
   * <li>引数で受取ったconditionがUserAccountSearchConditionのインスタンスであった場合、<br>
   * 管理ユーザマスタからconditionの検索条件に該当するデータのリストを取得し<br>
   * 検索結果情報に値をセットして返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>検索条件のconditionはnullでないとします。</dd>
   * <dd>conditionがUserAccountSearchConditionのインスタンスで無い場合<br>
   * 空のユーザアカウントの検索結果情報が返ります。</dd>
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
   * @return 管理ユーザの検索結果情報
   */
  SearchResult<UserAccount> getUserAccountList(SearchCondition condition);

  /**
   * ユーザー情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったユーザーコードより、管理ユーザマスタから該当するユーザーアカウントを取得します。
   * <ol>
   * <li>引数で受取ったユーザーコードより、管理ユーザーマスタからDAOを用いてユーザーアカウントを取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ユーザーコードはnullでないとします。</dd>
   * <dd>該当するデータが存在しなかった場合、nullが返されます。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param userCode
   *          ユーザーコード
   * @return ユーザーアカウント
   */
  UserAccount getUserAccount(Long userCode);

  /**
   * ショップコードとログインIDよりユーザアカウントを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったショップコードとログインIDより、管理ユーザマスタからユーザーアカウントを取得します。
   * <ol>
   * <li>引数で受取ったショップコードとログインIDより、管理ユーザマスタから該当のユーザーアカウント<br>
   * を取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ショップコード、ログインIDはnullでないものとします。</dd>
   * <dd>該当するデータが存在しなかった場合、nullが返されます。</dd>
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
   * @param loginId
   *          ログインID
   * @return ユーザーアカウント
   */
  UserAccount getUserAccountByLoginId(String shopCode, String loginId);

  /**
   * ユーザ情報を登録し、同時にユーザ権限情報を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったユーザアカウント、権限リストを管理ユーザマスタ、管理ユーザ権限マスタへ<br>
   * 登録します。
   * <ol>
   * <li>引数で受取ったユーザアカウントのショップコード・ログインIDより、getUserAccountByLoginIdを<br>
   * 用いて管理ユーザマスタからユーザアカウントを取得します。</li>
   * <li>1で取得したユーザアカウントがnullでなかった場合、重複エラーをサービス処理結果に<br>
   * セットし値を返します。</li>
   * <li>ユーザコードのシーケンスを使い、登録用のユーザコードを取得します。</li>
   * <li>引数で受取ったユーザアカウントに3で取得したユーザコードをセットします。</li>
   * <li>4で値をセットしたユーザアカウントのパスワードを暗号化してセットしなおします。</li>
   * <li>5で値をセットしたユーザアカウントに、DatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>6で値をセットしたユーザアカウントにValidationチェックを行い、エラーが存在した<br>
   * 場合はエラーの件数分サービス処理結果にValidationエラーをセットします。</li>
   * <li>引数で受取ったユーザ権限一覧の各ユーザ権限に対して、3で取得したコードをセットします。</li>
   * <li>8で値を設定したユーザ権限に対して、DatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>9で値をセットしたユーザアカウントにValidationチェックを行い、エラーが存在した<br>
   * 場合はエラーの件数分サービス処理結果にValidationエラーをセットします。</li>
   * <li>サービス処理結果にエラーが存在した場合、サービス処理結果を返します。</li>
   * <li>11でエラーが存在しなかった場合、ユーザアカウントを管理ユーザマスタへ登録します。</li>
   * <li>11でエラーが存在しなかった場合、各ユーザ権限を管理ユーザ権限マスタへ登録します。</li>
   * <li>12、13の処理で例外が発生した場合、サービス処理結果にDBオブジェクト実行エラーをセットし<br>
   * 値を返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で渡されるユーザアカウントはnullでないものとします。</dd>
   * <dd>引数で渡されるユーザアカウントのショップコード、ログインIDはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で渡したユーザアカウントを管理ユーザマスタへ登録します。</dd>
   * <dd>引数で渡したユーザ権限一覧を管理ユーザ権限マスタへ登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param account
   *          アカウント情報
   * @param permissionList
   *          ユーザ権限一覧
   * @return サービス処理結果
   */
  ServiceResult insertUserAccount(UserAccount account, List<UserPermission> permissionList);

  /**
   * 引数で受取ったユーザアカウントより、管理ユーザマスタを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったユーザアカウントより、管理ユーザマスタを更新します。
   * <ol>
   * <li>引数で受取ったユーザアカウントのユーザコードより、管理ユーザマスタから<br>
   * 更新対象のユーザアカウントを取得します。</li>
   * <li>1で取得したユーザアカウントがnullだった場合、データ未存在エラーをサービス処理結果に<br>
   * セットし、値を返します。</li>
   * <li>引数で受取ったユーザアカウントに、1で取得したユーザアカウントの<br>
   * 作成日、作成者をセットします。</li>
   * <li>3で値をセットしたユーザアカウントにDatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>4で値をセットしたユーザアカウントに対してValidationチェックを行い、エラーが存在した<br>
   * 場合はエラーの件数分サービス処理結果にValidationエラーをセットします。</li>
   * <li>4で値をセットしたユーザアカウントを使い、管理ユーザマスタを更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で渡されるユーザアカウントはnullでない物とします。</dd>
   * <dd>引数で渡されるユーザアカウントのユーザコードはnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>管理ユーザマスタの更新対象のユーザアカウントが、引数で渡したユーザアカウントに<br>
   * 更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param account
   *          アカウント情報
   * @return サービス処理結果
   */
  ServiceResult updateUserAccount(UserAccount account);

  /**
   * 引数で受取ったユーザアカウント・ユーザ権限一覧から、管理ユーザマスタ・管理ユーザ権限マスタを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったユーザアカウント・ユーザ権限一覧から、管理ユーザマスタ・管理ユーザ権限マスタを更新します。
   * <ol>
   * <li>引数で受取ったユーザアカウントのユーザコードより、管理ユーザマスタから<br>
   * 更新対象のユーザアカウントを取得します。</li>
   * <li>1で取得したユーザアカウントがnullだった場合、データ未存在エラーをサービス処理結果に<br>
   * セットし、値を返します。</li>
   * <li>引数で受取ったユーザアカウントのパスワードを暗号化します。</li>
   * <li>ユーザアカウントに対してValidationチェックを行い、エラーでなかった場合ユーザアカウントマスタに対して更新を行います。</li>
   * <li>管理ユーザ権限マスタから、引数で受取ったユーザアカウントのユーザコードに関連付いている物を全て削除します。</li>
   * <li>引数で受取ったユーザ権限一覧に対してユーザアカウントのユーザコードを設定し、管理ユーザ権限マスタへ一件ずつ登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で渡されるユーザアカウントはnullでないものとします。</dd>
   * <dd>引数で渡されるユーザ権限一覧はnullでないものとします。</dd>
   * <dd>引数で渡されるユーザアカウントのユーザコード、パスワードはnullでないものとします。</dd>
   * <dd>管理ユーザ権限マスタはDelete-Insertにより更新します。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>
   * <ol>
   * <li>管理ユーザマスタの更新対象のユーザアカウントが、引数で渡したユーザアカウントの情報に更新されます。</li>
   * <li>管理ユーザ権限マスタの、更新対象のユーザコードに紐づくデータが全て削除されます。</li>
   * <li>管理ユーザ権限マスタに、引数で渡したユーザ権限一覧が全て登録されます。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * 
   * @param account
   *          アカウント情報
   * @param permissionList
   *          ユーザ権限一覧
   * @return サービス処理結果
   */
  ServiceResult updateUserAccount(UserAccount account, List<UserPermission> permissionList);

  /**
   * ユーザアカウント情報を削除する<BR>
   * 関連付いている権限情報も削除する<BR>
   * 
   * @param account
   *          削除するユーザアカウント
   * @return サービス処理結果
   */
  ServiceResult deleteUserAccount(UserAccount account);

  /**
   * 最新の消費税を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>消費税リストを取得し、当日日付以前に適用されている物のうち最新の物を取得します。
   * <ol>
   * <li>1. 消費税リストを取得します。</li>
   * <li>2. システム日付を取得します。</li>
   * <li>3. 取得した消費税リストを1件づつループし、2で取得した日付以前から適用されている最新の消費税を返します。</li>
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
   * <dd>消費税マスタにデータが未登録、該当するデータが存在しない場合nullが返ります。</dd>
   * </dl>
   * </p>
   * 
   * @return 最新の消費税
   */
  Tax getCurrentTax();

  /**
   * 消費税情報のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>消費税マスタに登録されている消費税のリストを取得します。
   * <ol>
   * <li>1. 消費税マスタより、消費税リストを取得します。</li>
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
   * <dd>取得される消費税リストは、適用開始日の降順で取得されます。</dd>
   * </dl>
   * </p>
   * 
   * @return 消費税情報のリスト
   */
  List<Tax> getTaxList();

  /**
   * 消費税情報を追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>消費税情報を消費税マスタに追加します。
   * <ol>
   * <li>1. UtilServiceを使って、消費税番号のSequenceを取得します。</li>
   * <li>2. 1で取得した消費税番号を登録用のDTOにセットします。</li>
   * <li>3. DatabaseUtilを用いて、ユーザステータスをセットします。</li>
   * <li>4. Validationチェックを行います。</li>
   * <li>5. DAOを用いて、INSERTを実行します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>Tax ≠null。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param tax
   *          登録する消費税情報がセットされたTaxDTO
   * @return ServiceResult サービス実行結果
   */
  ServiceResult insertTax(Tax tax);

  /**
   * 消費税情報を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った消費税番号の消費税情報を削除します。
   * <ol>
   * <li>1. 引数で受取った消費税番号を用いて、DAOを使い消費税情報を削除します<br>
   * (削除時の削除対象データ存在チェックは行いません)。</li>
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
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param taxNo
   *          削除される消費税番号。Long型。
   * @return サービス実行結果
   */
  ServiceResult deleteTax(Long taxNo);

  /**
   * ポイントシステム使用ルールを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ポイントルールマスタよりポイントルールを取得します。
   * <ol>
   * <li>1. ポイントルールマスタより、ポイントルールを全件取得します。</li>
   * <li>2. ポイントルールはが存在する場合、ポイントルールは1件のみのはずなので<br>
   * 1で取得したポイントルールリストの1件目を返します。</li>
   * <li>3. ポイントルールが存在しない場合、新たにnewしたポイントルールを返します。</li>
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
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return ポイントシステム使用ルール
   */
  PointRule getPointRule();

  /**
   * ポイントシステム使用ルールを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受取ったポイントルールのDTOを元に、ポイントルールマスタを更新します。
   * <ol>
   * <li>1. ポイントルールをポイントルールマスタから全件取得します。</li>
   * <li>2. 1で取得したポイントルールが1件も存在しなかった場合エラーを返します。</li>
   * <li>3. 1で取得したポイントルールリストの1件目を取得します。<br>
   * (ポイントルールは1件しか存在しないはずの為)</li>
   * <li>4. 引数として受取ったポイントルール更新用のDTOに、3で取得したポイントルールの行ID、作成者、作成日、更新者をセットします。</li>
   * <li>5. 4で設定したポイントルール更新用のDTOに対してValidationチェックを行う。エラーが存在した場合、エラーを返します。</li>
   * <li>6. 4で設定したポイントルール更新用のDTOを用いて、DAOを使用しポイントルールを更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>rule≠null。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param rule
   *          ポイントシステム使用ルール
   * @return サービス実行結果
   */
  ServiceResult updatePointRule(PointRule rule);

  /**
   * お知らせ情報マスタに登録されている全お知らせ情報のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>お知らせ情報マスタに登録されている全お知らせ情報のリストを返します。
   * <ol>
   * <li>1. DAOを使ってお知らせ情報マスタより全お知らせ情報のリストを、表示開始日時の降順で取得し返します。</li>
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
   * <dd>返されるお知らせ情報のリストは、表示開始日時の降順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @return お知らせ情報一覧
   */
  List<Information> getInformationList();

  /**
   * お知らせ情報を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>お知らせ情報をお知らせ情報マスタに追加します。
   * <ol>
   * <li>1. UtilServiceを使って、お知らせ情報番号のSequenceを取得します。</li>
   * <li>2. 1で取得したお知らせ情報番号を登録用のDTOにセットします。</li>
   * <li>3. DatabaseUtilを用いて、ユーザステータスをセットします。</li>
   * <li>4. Validationチェックを行います。</li>
   * <li>5. DAOを用いて、INSERTを実行します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>Tax ≠null。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param information
   *          登録するお知らせ情報がセットされたInformationDTO
   * @return ServiceResult サービス実行結果
   */
  ServiceResult insertInformation(Information information);

  /**
   * お知らせ情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受取ったお知らせ情報のDTOを元に、お知らせ情報マスタを更新します。
   * <ol>
   * <li>1. 引数で受取ったDTOのお知らせ情報番号を用いて、お知らせ情報マスタよりお知らせ情報を取得します。</li>
   * <li>2. 1でマスタより取得したお知らせ情報が存在しなかった場合、未存在エラーを返します。</li>
   * <li>3. 引数で受取ったDTOに、1でマスタより取得したお知らせ情報の行ID、作成者、作成日、更新者をセットします。</li>
   * <li>4. 3で設定したお知らせ情報更新用のDTOに対してValidationチェックを行い、エラーが存在した場合エラーを返します。</li>
   * <li>5. 3で設定したお知らせ情報更新用のDTOを用いて、DAOを使いお知らせ情報を更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>information≠null</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param information
   *          お知らせ情報
   * @return サービス処理結果
   */
  ServiceResult updateInformation(Information information);

  /**
   * お知らせ情報を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取ったお知らせ情報番号のお知らせ情報を削除します。
   * <ol>
   * <li>1. 引数で受取ったお知らせ情報番号を用いて、DAOを使いお知らせ情報を削除します。<br>
   * (削除時の削除対象データ存在チェックは行いません)。</li>
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
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param informationNo
   * @return サービス実行結果
   */
  ServiceResult deleteInformation(Long informationNo);

  /**
   * 検索条件に該当するお知らせ件数を取得します。
   * 
   * @param condition
   *          検索条件
   * @return お知らせ件数
   */
  String getInformationCount(InformationCountSearchCondition condition);

  OnlineService getOnlineService(String shopCode);

  GoogleAnalysis getGoogleAnalysis();
  
  //Add by V10-CH start
  ServiceResult insertOnlineService(OnlineService onlineService);
  
  Boolean isHaveOnline(String shopCode); 
  //Add by V10-CH end
  
  ServiceResult updateOnlineService(OnlineService onlineService);

  ServiceResult updateGoogleAnalysis(GoogleAnalysis googleAnalysis);

  ServiceResult insertGoogleAnalysis(GoogleAnalysis googleAnalysis);

  /**
   * ポイントシステム使用ルールを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ポイントルールマスタよりポイントルールを取得します。
   * <ol>
   *</li>
   * <li>2. ポイントルールはが存在する場合、ポイントルールは1件のみのはずなので<br>
   * 1で取得したポイントルールリストの1件目を返します。</li>
   * <li>3. ポイントルールが存在しない場合、新たにnewしたポイントルールを返します。</li>
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
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return ポイントシステム使用ルール
   */
  CouponRule getCouponRule();
  
  ServiceResult updateCouponRule(CouponRule couponRule);
  
  Advert getAdvert(Long AdvertNo);
  
  ServiceResult updateAdvert(Advert advert);
  
  List<Advert> getEnabledAdvert(String advertType);
  
  List<Advert> getAdvertByType(String advertType);
}
