package jp.co.sint.webshop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CartHistory;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerMessage;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.data.dto.MobileAuth;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.data.dto.WebDiagnosisHeader;
import jp.co.sint.webshop.service.customer.CouponStatusAllInfo;
import jp.co.sint.webshop.service.customer.CouponStatusListSearchCondition;
import jp.co.sint.webshop.service.customer.CouponStatusSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerCouponInfo;
import jp.co.sint.webshop.service.customer.CustomerGroupCount;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.customer.CustomerPointInfo;
import jp.co.sint.webshop.service.customer.CustomerRegisterCountSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.service.customer.DeliveryHistoryInfo;
import jp.co.sint.webshop.service.customer.DeliveryHistorySearchCondition;
import jp.co.sint.webshop.service.customer.InquiryInfo;
import jp.co.sint.webshop.service.customer.InquirySearchCondition;
import jp.co.sint.webshop.service.customer.InquirySearchInfo;
import jp.co.sint.webshop.service.customer.MemberCouponHistory;
import jp.co.sint.webshop.service.customer.MemberInquiryHistory;
import jp.co.sint.webshop.service.customer.MemberSearchCondition;
import jp.co.sint.webshop.service.customer.MemberSearchInfo;
import jp.co.sint.webshop.service.customer.MemberShippingHistory;
import jp.co.sint.webshop.service.customer.OwnerCardDetail;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.service.customer.PointStatusShopSearchInfo;
import jp.co.sint.webshop.service.result.ServiceResultImpl;

/**
 * SI Web Shopping 10 顧客サービス(CustomerService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface CustomerService {

  /**
   * 全顧客グループのリストおよび顧客グループ別会員数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>全顧客グループのリストおよび顧客グループ別会員数を取得します。
   * <ol>
   * <li>検索条件を指定せず、顧客グループのリストおよび顧客グループ別会員数を全件取得します。</li>
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
   * <dd>検索結果は顧客グループコードの昇順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @return 顧客グループのリスト
   */
  List<CustomerGroupCount> getCustomerGroup();

  /**
   * 指定した顧客グループコードの顧客グループ情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定した顧客グループコードの顧客グループ情報を取得します。
   * <ol>
   * <li>指定した顧客グループコードに該当する顧客グループ情報を取得します。</li>
   * </dd>
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

  /**
   * 顧客グループを新規に登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客グループを新規に登録します。
   * <ol>
   * <li>引数で受取った顧客グループ情報に、DatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>1で値をセットした顧客グループ情報に対してValidationチェックを行います。</li>
   * <li>1で取得した顧客グループ情報の顧客グループコードに一致する顧客グループが存在した場合、該当データ登録済みエラーを返します。</li>
   * <li>1で値をセットした顧客グループ情報をDAOを用いて顧客グループマスタに登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerGroupがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客グループマスタにデータが登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerGroup
   *          顧客グループ
   * @return サービス実行結果
   */
  ServiceResult insertCustomerGroup(CustomerGroup customerGroup);

  /**
   * 顧客グループを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客グループを更新します。
   * <ol>
   * <li>引数で受取った顧客グループ情報の顧客グループコードに一致する顧客グループが存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客グループ情報の顧客グループコードを用いて、顧客グループマスタから更新対象の顧客グループ情報を取得します。</li>
   * <li>2で取得した顧客グループ情報に、引数で受け取った顧客グループ名、顧客グループポイント率、<br />
   * DatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>3で値をセットした顧客グループ情報に対してValidationチェックを行います。</li>
   * <li>3で値をセットした顧客グループ情報をDAOを用いて顧客グループマスタに更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerGroupがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客グループマスタのデータが更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerGroup
   *          顧客グループ
   * @return サービス実行結果
   */
  ServiceResult updateCustomerGroup(CustomerGroup customerGroup);

  /**
   * 顧客グループを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った顧客グループコードに一致する顧客グループマスタから削除処理を行います。<br />
   * ただし、デフォルト登録された顧客グループ(顧客グループコード='0')は削除対象外です。<br>
   * <ol>
   * <li>引数で受取った顧客グループコードが、デフォルト登録の顧客グループコードでないかのチェックを行います。</li>
   * <li>引数で受取った顧客グループ情報の顧客グループコードに一致する顧客グループが存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客グループ情報の顧客グループコードを用いて、顧客グループマスタから削除対象の顧客グループ情報を取得します。</li>
   * <li>3で取得した顧客グループ情報に紐付く顧客が存在する場合、会員登録済み顧客グループ削除エラーを返します。</li>
   * <li>顧客グループマスタより、該当のレコードを削除します。</li>
   * </dd>
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
   * <dd>顧客グループマスタのデータが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerGroupCode
   *          顧客グループコード
   * @return サービス実行結果
   */
  ServiceResult deleteCustomerGroup(String customerGroupCode);

  /**
   * 検索条件に該当する顧客のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件に該当する顧客のリストを返します。</li>
   * </dd>
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
   * <dd>検索結果は顧客コードの昇順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 顧客のリスト
   */
  SearchResult<CustomerSearchInfo> findCustomer(CustomerSearchCondition condition);

  /**
   * 検索条件に該当する全明細のポイント利用状況のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件を元にポイント履歴テーブルからデータを取得します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>検索条件が指定されていない場合は、全件取得する。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>検索結果は登録日時の降順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return ポイント利用状況のリスト
   */
  SearchResult<PointStatusAllSearchInfo> findPointStatusInfo(PointStatusListSearchCondition condition);

  /**
   * 検索条件に該当するショップ別のポイント利用状況のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件を元にショップ別ポイント集計テーブルからデータを取得します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>検索条件が指定されていない場合は、全件取得する。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>検索結果はショップコードの昇順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return ポイント利用状況のリスト
   */
  SearchResult<PointStatusShopSearchInfo> findPointStatusShopInfo(PointStatusListSearchCondition condition);

  /**
   * 顧客コードを指定して顧客を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに該当する顧客情報を取得します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定された顧客コードに該当する顧客情報を取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 顧客
   */
  CustomerInfo getCustomer(String customerCode);

  // 10.1.4 10170 追加 ここから
  /**
   * 顧客コードを指定して顧客を取得します。<br>
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに該当する顧客情報を取得します。<br>
   * 第2引数がtrueの場合は、顧客マスタのポイントをチェックし、<br>
   * ポイントオーバーフローしている可能性がある顧客(顧客マスタのポイント残高が99999999)は、<br>
   * ポイント履歴テーブルの有効ポイントの合計数を算出し、その値を保持ポイントに設定します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。<br>
   * isPointRecalculateがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定された顧客コードに該当する顧客情報を取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param isPointRecalculate
   *          ポイント再計算フラグ
   * @return 顧客
   */
  CustomerInfo getCustomer(String customerCode, boolean isPointRecalculate);

  // 10.1.4 10170 追加 ここまで

  /**
   * ログインIDを指定して顧客を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定されたログインIDに該当する顧客情報を顧客マスタテーブルから取得します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ログインIDがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定されたログインIDに該当する顧客情報を顧客マスタテーブルから取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param loginId
   *          ログインID
   * @return 顧客情報
   */
  Customer getCustomerByLoginId(String loginId);

  /**
   * 顧客コードを指定して顧客のポイント情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに該当する顧客のポイント情報を顧客マスタテーブルとポイント履歴テーブルから取得します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>顧客コードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定された顧客コードに該当する顧客のポイント情報を取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 顧客
   */
  CustomerPointInfo getCustomerPointInfo(String customerCode);

  /**
   * 顧客を新規に登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客を新規に登録します。<br>
   * 登録するデータは、<br>
   * 顧客/アドレス帳/顧客属性(データが存在する場合のみ)/ポイント履歴(顧客登録処理の場合のみ)です。
   * <ol>
   * <li>引数で受取った顧客属性回答情報に、顧客属性選択肢名マスタに一致しない顧客属性選択肢番号が存在した場合、該当データ未存在エラーを返します。</li>
   * <li>メールアドレス重複チェックを行い、一致するデータが存在する場合メールアドレス重複エラーを返します。</li>
   * <li>ログインID重複チェックを行い、一致するデータが存在する場合ログインID重複エラーを返します。</li>
   * <li>引数で受取った顧客情報に、メール区分をセットします。</li>
   * <li>4で値をセットした顧客情報、引数で受取ったアドレス帳、顧客属性回答情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>5で値をセットした顧客情報に、ハッシュ化したパスワードをセットします。</li>
   * <li>6で値をセットした顧客情報に、顧客属性回答が存在する場合のみシステム日付をセットします。</li>
   * <li>7で値をセットした顧客情報に、UtilSerivceを用いて取得した顧客コードのシーケンス番号をセットします。</li>
   * <li>8で値をセットした顧客、アドレス帳、顧客属性回答情報のValidationチェックを行います。</li>
   * <li>ポイント履歴に対して新規顧客登録データを設定します。<br>
   * ポイントシステムを使用しない、または会員登録時ポイントが0ポイント以下の場合は実行しない。</li>
   * <li>10で値をセットしたポイント履歴のValidationチェックを行います。</li>
   * <li>8で値をセットした顧客、アドレス帳、顧客属性回答、10で値をセットしたポイント履歴情報を<br>
   * TransactionManagerを用いてそれぞれ顧客マスタ、アドレス帳マスタ、顧客属性回答マスタ、ポイント履歴マスタに登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerInfoがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客マスタ、アドレス帳マスタ、顧客属性回答マスタ、ポイント履歴マスタテーブルにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerInfo
   *          顧客
   * @return サービス実行結果
   */
  ServiceResult insertCustomer(CustomerInfo customerInfo);
  /*
   * add by os012 20111213 start
   */
  ServiceResult insertCustomers(CustomerInfo customerInfo);
  //add by os012 20111213 end
//20111213 os013 add start
  ServiceResult insertCustomer1(Customer customer,String companyCode);
  //20111213 os013 add end
  /**
   * 顧客を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客を更新します。<br>
   * 更新するデータは、<br>
   * 顧客/アドレス帳/顧客属性(削除/登録)です。
   * <ol>
   * <li>メールアドレス重複チェックを行い、一致するデータが存在する場合メールアドレス重複エラーを返します。</li>
   * <li>ログインID重複チェックを行い、一致するデータが存在する場合ログインID重複エラーを返します。</li>
   * <li>引数で受取った顧客情報の顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客情報の顧客コードに一致するアドレス帳情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客属性回答情報に、顧客属性選択肢名マスタに一致しない顧客属性選択肢番号が存在した場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客情報に、メール区分をセットします。</li>
   * <li>6で値をセットした顧客情報に、顧客属性回答が存在する場合のみシステム日付をセットします。</li>
   * <li>7で値をセットした顧客、引数で受取ったアドレス帳情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>8で値をセットした顧客、引数で受取ったアドレス帳のValidationチェックを行います。</li>
   * <li>引数で受取った顧客属性回答情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>10で値をセットした顧客属性回答情報のValidationチェックを行います。</li>
   * <li>8で値をセットした顧客、アドレス帳情報をTransactionManagerを用いてそれぞれ顧客マスタ、アドレス帳マスタに登録します。</li>
   * <li>更新対象顧客コードに一致する顧客属性回答情報を、顧客属性回答マスタから削除します。</li>
   * <li>10で値をセットした顧客属性回答情報をTransactionManagerを用いて顧客属性回答マスタに登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerInfoがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客マスタ・アドレス帳マスタ・顧客属性マスタテーブルのデータを更新します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerInfo
   *          顧客
   * @return サービス実行結果
   */
  ServiceResult updateCustomer(CustomerInfo customerInfo);
 //add by os012 20111213 start
  ServiceResult updateCustomerOnly(CustomerInfo customerInfo);
 //add by os012 20111213 end
  //20111213 os013 add start
  ServiceResult updateCustomer1(Customer customer);
  //20111213 os013 add end
  /**
   * 顧客のログイン時パスワードを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客のパスワードを更新します。
   * <ol>
   * <li>引数で受取った顧客情報の顧客コードに一致する顧客情報が存在しない、<br>
   * または既に退会済み、または退会依頼中の場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客情報のValidationチェックを行います。</li>
   * <li>パスワードのパスワードポリシーチェックを行います。</li>
   * <li>引数で受取った顧客情報に、ハッシュ化したパスワードをセットします。</li>
   * <li>4で値をセットした顧客情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>5で値をセットした顧客情報をDAOを用いて顧客グループマスタに更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客マスタテーブルのパスワードを、ハッシュ化されたデータで更新します。</dd>
   * </dl>
   * </p>
   * 
   * @param customer
   *          顧客
   * @return サービス実行結果
   */
  ServiceResult updatePassword(Customer customer);

  /**
   * 顧客の退会依頼を行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客の退会依頼を行います。<br>
   * 指定された顧客コードに該当する顧客マスタの"退会依頼日" "顧客ステータス(退会希望='8')"を更新します。
   * <ol>
   * <li>引数で受取った顧客情報の顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客情報の顧客コードに一致する顧客情報が既に退会依頼済みの場合、顧客退会依頼済みエラーを返します。</li>
   * <li>引数で受取った顧客情報の顧客コードに一致する顧客情報が既に退会済みの場合、顧客削除済みエラーを返します。</li>
   * <li>引数で受取った顧客情報に退会依頼日、顧客ステータス、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>4で値をセットした顧客情報をTransactionManagerを用いて顧客マスタを更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客マスタの顧客ステータスを退会希望='8'で更新します。</dd>
   * <dd>退会依頼後、顧客はログイン不可となります。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param updatedDatetime
   *          更新日時
   * @return サービス実行結果
   */
  ServiceResult withdrawalRequest(String customerCode, Date updatedDatetime);

  /**
   * 顧客の退会処理を行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客の退会処理を行います。<br>
   * 顧客/アドレス帳/受注ヘッダ/出荷ヘッダテーブルの個人情報をマスクし、<br>
   * 顧客マスタの"退会日" "顧客ステータス(退会済み='9')"を更新します。
   * <ol>
   * <li>引数で受取った顧客情報に対して、有効受注が存在する場合、有効受注存在エラーを返します。</li>
   * <li>引数で受取った顧客情報の顧客コードに一致する顧客情報が存在しないまたは既に退会済みの場合、顧客削除済みエラーを返します。</li>
   * <li>引数で受取った顧客情報に対して、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>3で値をセットした顧客情報に顧客退会データをセットします。</li>
   * <li>4で値をセットした顧客情報をTransactionManagerを用いて顧客マスタを更新します。</li>
   * <li>退会対象顧客に紐付くアドレス帳情報、受注ヘッダ情報、出荷ヘッダ情報を更新します。</li>
   * <li>退会対象顧客に紐付くお気に入り情報、おすすめ商品情報、リマインダ情報、商品入荷お知らせ情報を削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客マスタの顧客ステータスを退会済み='9'で更新します。</dd>
   * <dd>個人情報に関する情報が「*」でマスクされます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param updatedDatetime
   *          更新日時
   * @return サービス実行結果
   */
  ServiceResult withdrawalFromMembership(String customerCode, Date updatedDatetime);

  /**
   * 顧客アドレスのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定された顧客コードに該当する顧客アドレス帳リスト情報を取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>検索結果はアドレス帳番号の昇順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 顧客アドレスのリスト
   */
  List<CustomerAddress> getCustomerAddressList(String customerCode);

  /**
   * 顧客アドレスのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定された顧客コードに該当する顧客アドレス帳リスト情報を取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerSearchがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>検索結果はアドレス帳番号の昇順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerSearch
   *          検索条件
   * @return 顧客アドレスのリスト
   */
  SearchResult<CustomerAddress> getCustomerAddressList(CustomerSearchCondition customerSearch);

  /**
   * 顧客アドレスを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>顧客アドレスを取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。<br>
   * addressNoがnullでないこと。 </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>指定された顧客コード、アドレス帳番号に該当する顧客アドレス帳リスト情報を取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param addressNo
   *          アドレス帳番号
   * @return 顧客アドレス
   */
  CustomerAddress getCustomerAddress(String customerCode, Long addressNo);

  /**
   * 顧客アドレスを新規追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受取ったアドレス帳情報の顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取ったアドレス帳情報に、UtilSerivceを用いて取得したアドレス帳番号のシーケンス番号、<br>
   * DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットしたアドレス帳情報のValidationチェックを行います。</li>
   * <li>2で値をセットしたアドレス帳情報をDAOを用いて顧客グループマスタに登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>addressがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客アドレス帳マスタにデータを登録します。</dd>
   * </dl>
   * </p>
   * 
   * @param address
   *          顧客アドレス
   * @return サービス実行結果
   */
  ServiceResult insertCustomerAddress(CustomerAddress address);

  /**
   * 顧客アドレスを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客アドレスを更新します。<br>
   * ただし、本人アドレス帳(アドレス帳番号 = '0')は更新できません。
   * <ol>
   * <li>引数で受取ったアドレス帳情報の顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取ったアドレス帳情報のアドレス帳番号が本人アドレス帳番号である場合、本人アドレス帳更新エラーを返します。</li>
   * <li>引数で受取ったアドレス帳情報のアドレス帳番号に一致するアドレス帳情報が存在しない場合、アドレス帳未登録エラーを返します。</li>
   * <li>引数で受取ったアドレス帳情報対して、DatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>4で値をセットしたアドレス帳情報のValidationチェックを行います。</li>
   * <li>4で値をセットしたアドレス帳情報をDAOを用いて顧客グループマスタに更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>addressがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客アドレス帳マスタのデータを更新します。</dd>
   * </dl>
   * </p>
   * 
   * @param address
   *          顧客アドレス
   * @return サービス実行結果
   */
  ServiceResult updateCustomerAddress(CustomerAddress address);

  /**
   * 顧客アドレスを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受取ったアドレス帳情報の顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取ったアドレス帳情報のアドレス帳番号に一致するアドレス帳情報が存在しない場合、アドレス帳未登録エラーを返します。</li>
   * <li>アドレス帳マスタより、該当のレコードを削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。<br>
   * addressNoがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客アドレス帳マスタのデータを削除します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客番号
   * @param addressNo
   *          アドレス帳番号
   * @return サービス実行結果
   */
  ServiceResult deleteCustomerAddress(String customerCode, Long addressNo);

  /**
   * ポイントを追加します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ポイント履歴テーブルにデータを登録します。
   * <ol>
   * <li>ポイントシステムを使用しない場合、ポイントシステム停止中エラーを返します。</li>
   * <li>引数で受取ったポイント履歴情報に、UtilSerivceを用いて取得したポイント履歴IDのシーケンス番号をセットします。</li>
   * <li>2で値をセットしたポイント履歴情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>3で値をセットしたポイント履歴情報のValidationチェックを行います。</li>
   * <li>保有ポイントがマイナスになる場合、保有ポイントマイナスエラーを返します。</li>
   * <li>引数で受取ったポイント履歴情報の顧客コードを用いて、顧客マスタから更新対象の顧客情報を取得します。</li>
   * <li>6で取得した顧客情報に、引数で受け取った発行ポイント(ポイント残高へ)、システム日付(ポイント最終獲得日へ)、<br />
   * DatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>7で値をセットした顧客、2で値をセットしたポイント履歴情報を<br>
   * TransactionManagerを用いてそれぞれ顧客マスタに更新、ポイント履歴マスタに登録します。</li>
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
   * <dd>
   * <ol>
   * <li>ポイント履歴テーブルにデータを登録します。</li>
   * <li>顧客マスタの残ポイントと、ポイント最終獲得日を更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * 
   * @param history
   *          ポイント履歴
   * @param updatedDatetime
   *          更新日時
   * @return サービス実行結果
   */
  ServiceResult insertPointHistory(PointHistory history, Date updatedDatetime);

  /**
   * 該当顧客の無効ポイントを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに該当する顧客の無効ポイントを削除します。<br>
   * <ol>
   * <li>引数で受取った顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>ポイントシステムを使用しない場合、ポイントシステム停止中エラーを返します。</li>
   * <li>引数で受取った顧客コードかつポイント発行ステータスが無効のポイント履歴情報を、ポイント履歴マスタから削除します。</li>
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
   * <dd>ポイント履歴テーブルのデータを削除します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return サービス実行結果
   */
  ServiceResult deleteIneffectivePointHistory(String customerCode);

  /**
   * 顧客が登録したお気に入り商品の情報を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>お気に入り商品テーブルの情報を削除します。 </dd>
   * <ol>
   * <li>引数で受取った顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客コード、ショップコード、SKUコードに一致するお気に入り情報を、お気に入り商品マスタから削除します。</li>
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
   * <dd>お気に入り商品テーブルのデータが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return サービス実行結果
   */
  ServiceResult deleteFavoriteCommodity(String customerCode, String shopCode, String skuCode);

  /**
   * 顧客属性番号を指定して顧客属性を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客属性番号に該当する顧客属性情報を取得します。
   * <ol>
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
   * <dd>指定された顧客属性番号に該当する顧客属性情報を取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param attributeNo
   *          顧客属性番号
   * @return 顧客属性
   */
  CustomerAttribute getAttribute(Long attributeNo);

  /**
   * 顧客属性選択肢情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客属性番号と顧客属性選択肢Noの組合せに該当する顧客属性選択肢情報を取得します。
   * <ol>
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
   * @param attributeCoice
   *          顧客属性
   * @return 顧客属性選択肢
   */
  CustomerAttributeChoice getAttributeChoice(CustomerAttributeChoice attributeCoice);

  /**
   * 全顧客属性のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件を指定せず、顧客属性のリストを全件取得します。</li>
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
   * <dd>検索結果は表示順の昇順、顧客属性番号の昇順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @return 顧客属性のリスト
   */
  List<CustomerAttribute> getAttributeList();

  /**
   * 顧客属性と顧客属性選択肢を一括で新規登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受取った顧客属性情報に、UtilSerivceを用いて取得した顧客属性番号のシーケンス番号をセットします。</li>
   * <li>1で値をセットした顧客属性情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットした顧客属性情報のValidationチェックを行います。</li>
   * <li>3で値をセットした顧客属性情報をTransactionManagerを用いてそれぞれ顧客属性マスタに登録します。</li>
   * <li>引数で受取った顧客属性選択肢名情報に、1で取得した顧客属性番号のシーケンス番号をセットします。</li>
   * <li>5で値をセットした顧客属性選択肢名情報に、UtilSerivceを用いて取得した顧客属性選択肢番号のシーケンス番号をセットします。</li>
   * <li>6で値をセットした顧客属性選択肢名情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>7で値をセットした顧客属性選択肢名情報のValidationチェックを行います。</li>
   * <li>8で値をセットした顧客属性選択肢名情報をTransactionManagerを用いてそれぞれ顧客属性選択肢名マスタに登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>顧客属性と顧客属性選択肢名を一括で登録します。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客属性マスタと顧客属性選択肢名マスタにデータが登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param attribute
   *          顧客属性
   * @param attributeChoice
   *          顧客属性選択肢
   * @return サービス実行結果
   */
  ServiceResult insertCustomerAttribute(CustomerAttribute attribute, List<CustomerAttributeChoice> attributeChoice);

  /**
   * 顧客属性を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客属性を更新します。
   * <ol>
   * <li>引数で受取った顧客属性情報の顧客属性番号に一致する顧客属性情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客属性情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットした顧客属性情報のValidationチェックを行います。</li>
   * <li>2で値をセットした顧客属性情報をDAOを用いて顧客属性マスタに更新します。</li>
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
   * <dd>
   * <ol>
   * <li>顧客属性テーブルのデータを更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * 
   * @param attribute
   *          顧客属性
   * @return サービス実行結果
   */
  ServiceResult updateCustomerAttribute(CustomerAttribute attribute);

  /**
   * 顧客属性選択肢を新規登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受取った顧客属性選択肢名情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>1で値をセットした顧客属性選択肢名情報のValidationチェックを行います。</li>
   * <li>1で値をセットした顧客属性選択肢名情報をTransactionManagerを用いてそれぞれ顧客属性選択肢名マスタに登録します。</li>
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
   * <dd>顧客属性選択肢マスタにデータが登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param attributeChoice
   *          顧客属性選択肢名
   * @return サービス実行結果
   */
  ServiceResult insertCustomerAttributeChoice(CustomerAttributeChoice attributeChoice);

  /**
   * 顧客属性選択肢を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客属性選択肢を更新します。
   * <ol>
   * <li>引数で受取った顧客属性選択肢名情報に一致する顧客属性選択肢名情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客属性選択肢名情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットした顧客属性選択肢名情報のValidationチェックを行います。</li>
   * <li>2で値をセットした顧客属性選択肢名情報をDAOを用いて顧客属性選択肢名マスタに登録します。</li>
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
   * <dd>
   * <ol>
   * <li>顧客属性選択肢テーブルのデータを更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * 
   * @param attributeChoice
   *          顧客属性選択肢名
   * @return サービス実行結果
   */
  ServiceResult updateCustomerAttributeChoice(CustomerAttributeChoice attributeChoice);

  /**
   * 顧客属性選択肢を削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受取った顧客属性番号、顧客属性選択肢番号に一致する顧客属性選択肢名情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>顧客属性選択肢名マスタより、該当のレコードを削除します。</li>
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
   * <dd>顧客属性選択肢名テーブルのデータを削除します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerAttributeNo
   *          顧客属性番号
   * @param customerAttributeChoiceNo
   *          顧客属性選択肢番号
   * @return サービス実行結果
   */
  ServiceResult deleteCustomerAttributeChoice(Long customerAttributeNo, Long customerAttributeChoiceNo);

  /**
   * 顧客属性と顧客属性選択肢と顧客属性回答を一括で削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客属性とそれに関連付いている顧客属性選択肢と顧客属性回答を一括で削除します。
   * <ol>
   * <li>引数で受取った顧客属性番号に一致する顧客属性のデータを削除します。</li>
   * <li>引数で受取った顧客属性番号に一致する顧客属性選択肢名のデータを削除します。</li>
   * <li>引数で受取った顧客属性番号に一致する顧客属性回答のデータを削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>attributeNoが存在する。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客グループマスタのデータが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param attributeNo
   *          顧客属性番号
   * @return サービス実行結果
   */
  ServiceResult deleteCustomerAttributeAndChoices(Long attributeNo);

  /**
   * 指定された顧客属性番号の顧客属性選択肢リストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>指定された顧客属性番号に関連付いている顧客属性選択肢のリストを取得します。</li>
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
   * <dd>検索結果は表示順の昇順、顧客属性選択肢Noの昇順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param attributeNo
   *          顧客属性番号
   * @return 顧客属性選択肢のリスト
   */
  List<CustomerAttributeChoice> getAttributeChoiceList(String attributeNo);

  /**
   * 顧客属性回答のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>顧客属性回答のリストを取得します。</li>
   * </dd>
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
   * <dd>条件に一致する顧客属性情報の一覧が返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件:顧客属性番号<br>
   *          顧客コード
   * @return 顧客属性回答のリスト
   */
  List<CustomerAttributeAnswer> getCustomerAttributeAnswer(CustomerSearchCondition condition);

  /**
   * 検索条件に該当する個人別のポイント利用状況のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件に該当するポイント利用状況のリストを返します。</li>
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
   * <dd>検索結果はポイント履歴テーブルの登録日の降順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return ポイント利用状況のリスト
   */
  SearchResult<PointStatusAllSearchInfo> findPointStatusCustomerInfo(PointStatusListSearchCondition condition);

  /**
   * 検索条件に該当する配送履歴リストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件に該当する配送履歴のリストを返します。</li>
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
   * <dd>検索結果は受注日の降順で返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return ポイント利用状況のリスト
   */
  SearchResult<DeliveryHistoryInfo> findDeliveryHistoryInfo(DeliveryHistorySearchCondition condition);

  /**
   * パスワード再発行メールのURLトークンからリマインダ情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>パスワード再発行メールのURLトークンからリマインダ情報を取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>tokenがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>取得した条件に関連付いているリマインダ情報を取得します。</dd>
   * </dl>
   * </p>
   * 
   * @param token
   *          URLトークン
   * @return リマインダ情報
   */
  Reminder getReminderInfo(String token);

  /**
   * リマインダーを登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったリマインダーデータをリマインダーマスタに新規登録します。
   * <ol>
   * <li>引数で受取ったリマインダ情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>1で値をセットしたリマインダ情報のValidationチェックを行います。</li>
   * <li>リマインダ情報の再発行キーに一致するリマインダが存在する場合、データ重複エラーを返します。</li>
   * <li>1で値をセットしたリマインダ情報をDAOを用いてリマインダマスタに更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>reminderがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param reminder
   *          リマインダ
   * @return サービス実行結果
   */
  ServiceResult insertReminder(Reminder reminder);

  /**
   * メールアドレスの重複チェックを行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>現在処理対象である顧客コードを除いた同一のメールアドレスの存在チェックをします。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。<br>
   * checkEmailがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客マスタに同一のメールアドレスが存在するかどうかをboolean値で返します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param checkEmail
   *          検索対象メールアドレス
   * @return メールアドレスが存在する場合はfalseを返します。<br>
   *         メールアドレスが存在しない場合はtrueを返します。<br>
   *         前提条件が満たされない場合はtrueを返します。
   */
  boolean isAvailableEmailUpdate(String customerCode, String checkEmail);

  /**
   * メールアドレスの重複チェックを行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>DB上に完全一致するメールアドレスの存在チェックをします。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>checkEmailがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客マスタに同一のメールアドレスが存在するかどうかをboolean値で返します。</dd>
   * </dl>
   * </p>
   * 
   * @param checkEmail
   *          検索対象メールアドレス
   * @return メールアドレスが存在する場合はfalseを返します。<br>
   *         メールアドレスが存在しない場合はtrueを返します。<br>
   *         前提条件が満たされない場合はtrueを返します。
   */
  boolean isAvailableEmailInsert(String checkEmail);

  /**
   * お気に入り商品を新規登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受取った顧客コードに一致する顧客情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>新たに生成したお気に入り商品情報に、引数で受け取った顧客コード、ショップコード、SKUコード、<br>
   * システム日付(お気に入り登録日へ)、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットしたお気に入り商品情報のValidationチェックを行います。</li>
   * <li>引数で受取ったショップコード、SKUコードに一致する商品情報が存在しない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客コード、ショップコード、SKUコードに一致するお気に入り商品情報が存在する場合、データ重複エラーを返します。</li>
   * <li>2で値をセットしたお気に入り商品情報をDAOを用いて顧客属性回答マスタに登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。<br>
   * shopCodeがnullでないこと。<br>
   * skuCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>お気に入り商品テーブルにデータが登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return サービス実行結果
   */
  ServiceResult insertFavoriteCommodity(String customerCode, String shopCode, String skuCode);

  /**
   * 集計対象を指定しておすすめ商品リストを作成します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>ストアドを呼び出し、集計対象を指定しておすすめ商品リストを作成します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * monthがnullでないこと。<br>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param month
   *          集計期間月
   * @return サービス実行結果
   */
  ServiceResult customerRecommendSummary(String month);

  /**
   * 検索条件に該当する顧客の件数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>検索条件に該当する顧客の件数を取得します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionがnullでないこと。
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>検索条件に該当する顧客の件数が返されます。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @return 登録済みの顧客件数
   */
  Long getCustomerRegisterCount(CustomerRegisterCountSearchCondition condition);

  /**
   * ポイント使用期限が過ぎた顧客のポイントを無効にします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>ストアドを呼び出し、ポイント使用期限が過ぎた顧客のポイントを無効にします。</li>
   * <li>結果が'1'(0:成功/1:失敗)の場合はエラーとし、更新を行いません。<br>
   * 成功の場合は、ポイント履歴マスタにポイントマイナス明細を登録・ポイントステータスを'無効'とし、<br>
   * 顧客マスタのポイント残高を'0'に更新します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>updatedUserがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @return サービス実行結果
   */
  ServiceResult deleteExpiredPoint();

  /**
   * 顧客コード・ショップコードを指定して顧客を検索し、<br>
   * 指定したショップで受注処理を行っている場合trueを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客が指定したショップで受注処理を行っているかチェックします。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。<br>
   * shopCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注処理を行っている:true<br>
   * 受注処理を行っていない:false</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @param shopCode
   *          ショップコード
   * @return 指定された顧客が指定したショップで受注処理を行っている場合はtrueを返します。
   *         指定された顧客が指定したショップで受注処理を行っていない場合はfalseを返します。
   *         前提条件が満たされない場合はfalseを返します。
   */
  boolean isShopCustomer(String customerCode, String shopCode);

  /**
   * 指定したメールアドレスに完全一致する顧客コードを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したメールアドレスに完全一致する顧客が存在するかチェックし、<br>
   * 一致すれば顧客コードを返します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>emailがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>顧客が存在する:顧客コード 顧客が存在しない：空白</dd>
   * </dl>
   * </p>
   * 
   * @param checkEmail
   *          メールアドレス
   * @return 指定したメールアドレスに完全一致するメールアドレスが存在する場合、<br>
   *         顧客コードを返します。<br>
   *         指定したメールアドレスに完全一致するメールアドレスが存在しない場合、<br>
   *         空白を返します。<br>
   *         前提条件が満たされない場合は空白を返します。
   */
  String getCustomerCodeToEmail(String checkEmail);

  /**
   * パスワード再登録を実行します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定したパスワードを顧客情報に更新します。
   * <ol>
   * <li>引数で受取った顧客情報の顧客コードに一致する顧客情報が存在しない、<br>
   * または退会依頼中、または退会済みの場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取ったTOKENが有効かチェックし、有効でない場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った顧客情報のパスワードのValidationチェックを行います。</li>
   * <li>パスワードのパスワードポリシーチェックを行います。</li>
   * <li>引数で受取った顧客情報に、ハッシュ化したパスワードをセットします。</li>
   * <li>引数で受取った顧客情報のパスワードパスワードポリシーチェックを行います。</li>
   * <li>6で値をセットした顧客情報に対してDatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>7で値をセットした顧客情報をDAOを用いて顧客マスタを更新します。</li>
   * <li>引数で受け取ったTOKENに一致するリマインダ情報を削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>パスワードを更新します。</dd>
   * </dl>
   * </p>
   * 
   * @param customer
   *          顧客情報
   * @param token
   *          URLパラメータtoken
   * @return サービス実行結果
   */
  ServiceResult initPassword(Customer customer, String token);
  
  ServiceResult initPaymentPassword(Customer customer, String token);

  /**
   * 指定された顧客コードに関連付いている顧客が存在しない場合、trueを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに関連付いている顧客を検索します。<br>
   * データが存在しない場合、trueを返します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>booleanを返します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 指定した顧客が存在する場合falseを返します。<br>
   *         指定した顧客が存在しない場合trueを返します。<br>
   *         前提条件が満たされない場合はtrueを返します。
   */
  boolean isNotFound(String customerCode);

  /**
   * 指定された顧客コードに関連付いている顧客が存在し、<br>
   * かつ退会済み/退会依頼中である場合trueを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに関連付いている顧客を検索します。<br>
   * ・データが存在する。<br>
   * ・退会済/退会依頼中である。<br>
   * 上記の条件が満たされる場合、trueを返します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>booleanを返します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 指定した顧客が存在するかつ、顧客ステータスが退会依頼中、または退会済みの場合trueを返します。<br>
   *         上記の条件が満たされない場合はfalseを返します。<br>
   *         前提条件が満たされない場合はfalseを返します。
   */
  boolean isInactive(String customerCode);

  /**
   * 指定された顧客コードに関連付いている顧客が存在し、<br>
   * かつ退会済みである場合trueを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに関連付いている顧客を検索します。<br>
   * ・データが存在する。<br>
   * ・退会済である。<br>
   * 上記の条件が満たされる場合、trueを返します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>booleanを返します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 指定した顧客が存在しない、または顧客ステータスが退会済みの場合trueを返します。<br>
   *         上記の条件が満たされない場合はfalseを返します。 <br>
   *         前提条件が満たされない場合はfalseを返します。
   */
  boolean isWithdrawed(String customerCode);

  /**
   * 指定された顧客コードに関連付いている受注が存在し、<br>
   * かつ、有効である場合trueを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに関連付いている受注を検索します。<br>
   * ・データが存在する。<br>
   * ・受注ステータス！＝キャンセル || 入金ステータス＝未入金 || 売上確定＝未確定<br>
   * 上記の条件が満たされる場合、trueを返します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>booleanを返します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 指定した顧客が有効受注を保有している場合trueを返します。<br>
   *         指定した顧客が有効受注を保有していない場合falseを返します。<br>
   *         前提条件が満たされない場合はfalseを返します。
   */
  boolean hasActiveOrder(String customerCode);

  /**
   * 指定された顧客コードに関連付いている受注が存在し、<br>
   * かつ、未入金の受注がある場合trueを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>指定された顧客コードに関連付いている受注を検索します。<br>
   * ・データが存在する。<br>
   * ・受注ステータス！＝キャンセル<br>
   * ・入金ステータス＝未入金<br>
   * 上記の条件が満たされる場合、trueを返します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>customerCodeがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>booleanを返します。</dd>
   * </dl>
   * </p>
   * 
   * @param customerCode
   *          顧客コード
   * @return 指定した顧客が未入金の受注を保有している場合trueを返します。<br>
   *         指定した顧客が未入金の受注を保有していない場合falseを返します。<br>
   *         前提条件が満たされない場合はfalseを返します。
   */
  boolean hasNotPaymentOrder(String customerCode);
  
  List<CustomerMessage> getCustomerMessageList(Date dateStart,Date dateEnd,String customerCode);

  //add by V10-CH start
  List<CustomerCoupon> getCustomerCouponList(String customerCode);

  SearchResult<CouponStatusAllInfo> findCouponStatusDetailInfo(CouponStatusSearchCondition condition);  
  
  CustomerCoupon getCustomerCoupon(String customerCouponId);
  
  GiftCardIssueDetail getGiftCardIssueDetailByPassWord(String password);
  
  CustomerCardInfo getCustomerCardInfoByCustomerCode(String customerCode);
  
  CustomerCardInfo getCustomerCardInfoByCustomerCodeUnable(String customerCode);
  
  GiftCardReturnConfirm getGiftCardReturnConfirm(String customerCode);
  
  List<OwnerCardDetail> getCustomerCardInfo(String customerCode);
  
  CustomerCardUseInfo getCustomerCardUseInfoBycustomerCode(String customerCode);
  
  List<CustomerCardInfo> getCustomerCardInfoList(String customerCode);
  
  ServiceResult giftCardActivateProcess(Customer customer, GiftCardIssueDetail cardDetail , GiftCardRule rule);
  
  List<CustomerCouponInfo> getCustomerCouponInfoBack(String customerCode);
  
  SearchResult<CustomerCoupon> findCouponStatusCustomerInfo(CouponStatusListSearchCondition condition);

  ServiceResult overdueCoupon();
  
  ServiceResult insertCustomerCoupon(CustomerCoupon cc);
  //add by V10-CH end
  
  //add by yl 20121209 start
  SearchResult<InquiryDetail> getInquiryDetailList(InquirySearchCondition condition);
  
  InquirySearchInfo getInquiryInfo(String inquiryHeaderNo);
  
  InquiryHeader getInquiryHeader(String inquiryHeaderNo);
  
  ServiceResult insertInquiryDetail(InquiryDetail inquiryDetail);
  
  ServiceResult deleteInquiryDetail(String inquiryHeaderNo, String inquiryDetailNo);
  
  SearchResult<InquirySearchInfo> getInquiryCountList(InquirySearchCondition condition);
    
  ServiceResult insertInquiry(InquiryInfo inquiryInfo); 
  
  SearchResult<InquirySearchInfo> getInquiryList(InquirySearchCondition condition);
  
  ServiceResult deleteInquiry(List<String> inquiryHeaderNoList);
//add by yl 20121209 end
//20111209 lirong add start
  
  /**
   * 根据肌肤诊断编号获得肌肤诊断header<br>
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * </dl>
   * </p>
   * @param webDiagnosisHeaderNo 肌肤诊断编号
   * @return WebDiagnosisHeader
   *         
   *        
   */
  WebDiagnosisHeader getWebDiagnosisHeader(String webDiagnosisHeaderNo);
//20111209 lirong add  end
  
  // 20111214 shen add start
  ServiceResult insertCartHistory(CartHistory cartHistory);

  ServiceResult deleteCartHistory(String customerCode);
  
  ServiceResult deleteCartHistory(String customerCode, String shopCode, String skuCode);
  
  List<CartHistory> getCartHistoryList(String customerCode);
  // 20111214 shen add end

  // Add by V10-CH start
  ServiceResult changeCustomerGroup(Customer customer);
  // Add by V10-CH end
  
  // 20111223 shen add start
  CustomerGroupCampaign getCustomerGroupCampaign(String shopCode, String customerGroupCode);
  
  List<NewCouponHistory> getUnusedPersonalCouponList(String customerCode);
  
  NewCouponHistory getUnusedPersonalCoupon(String customerCode, String couponIssueNo);
  
  NewCouponRule getPublicCoupon(String couponCode);
  
  NewCouponRule getPersonalBirthdayCoupon(String couponIssueCode);
  
  NewCouponRule getSpecialMemberDistribution(String couponIssueCode);
  // 20111223 shen add end
  //20111225 os013 add start
  //根据支付宝用户编号查出顧客コード
  Customer getCustomerCode(String tmallUserId,String customerStatus);
  //20111225 os013 add end
  SearchResult<MemberSearchInfo> getMemberList (MemberSearchCondition condition);
  
  //20111225 ob add start
  List<NewCouponHistory> getMailNoticeForStartCoupon(String useStartDatetime);
  
  List<NewCouponHistory> getMailNoticeForEndCoupon(String useEndDatetime);
  //20111225 ob add end
  
  // 20120104 shen add start
  ServiceResultImpl insertCustomerSelfAddress(CustomerAddress address);
  // 20120104 shen add end
  //20120110 os013 add start
  //支付宝快捷登录返回处理
  ServiceResult settleFastpay(String tmallUserId,String realName,String email);
  //20120110 os013 add end
  //20120111 os013 add start
  //我的所有优惠券查询
  List<NewCouponHistory> getCouponHistoryList(String customerCode);
  //20120111 os913 add end
  
  SearchResult<MemberShippingHistory> getMemberShippingHistoryList(MemberSearchCondition condition);
  
  SearchResult<MemberInquiryHistory> getMemberInquiryHistoryList(MemberSearchCondition condition);
  
  SearchResult<MemberCouponHistory> getMemberCouponHistoryList(MemberSearchCondition condition);
  
  ServiceResult updateCouponUserStatus(NewCouponHistory history);
  
  List<NewCouponHistory> getCouponHistoryValidList(String customerCode);
  
  Long getCount(String number, String veri);
  
  String getMobileNumber(String code);
  // 2013/04/01 优惠券对应 ob add start
  /**
   * 携帯電話検証コードを取得する
   * @return MobileAuthを返します。
   */
  MobileAuth getAuthInfo(String authCode, String mobileNumber);
  // 2013/04/01 优惠券对应 ob add end
  
  NewCouponHistory getNewCouponHistory(String customerCode);
  
  BigDecimal getCouponType(String couponCode);
  
  String getCouponCodeByCouponIssueNo(String couponIssueCode);
  
  List<NewCouponRuleUseInfo> getNewCouponRuleUseInfo(String couponCode);
  
  //获取可用礼品卡数量
  Long getAvaliableGiftCardCount(String customerCode);
    
}
