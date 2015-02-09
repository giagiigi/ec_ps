package jp.co.sint.webshop.service;

import java.util.List;

import jp.co.sint.webshop.data.domain.PermissionType;
import jp.co.sint.webshop.data.dto.UserPermission;

/**
 * SI Web Shopping 10 認証サービス(AuthorizationService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface AuthorizationService {

  /**
   * 管理ユーザのログイン認証を行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>管理ユーザのログイン認証を行います。
   * <ol>
   * <li>ショップコード、ログインIDで管理ユーザを取得します。<br>
   * ・管理ユーザが存在しなければ、認証エラーを返します。<br>
   * ・ログインロック中であれば、認証エラーを返します。</li>
   * <li>管理ユーザの権限チェックを行います。<br>
   * ・権限情報が存在しないか不正の場合、認証エラーを返します。</li>
   * <li>暗号化されたパスワードと認証を行います。</li>
   * <li>認証成功の場合<br>
   * ・ログイン失敗回数をクリアします。<br>
   * ・ログイン日時をシステム日付で更新します。</li>
   * <li>認証失敗の場合<br>
   * ・ログイン失敗回数を1加算します。<br>
   * ・ログインエラー最大回数を超過した場合、管理ユーザをロック状態にします。<br>
   * ・認証エラーを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCode、loginId、passwordは必須であること。</dd>
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
   * @param loginId
   * @param password
   * @return ログイン認証結果
   */
  ServiceResult authorizeUser(String shopCode, String loginId, String password);

  /**
   * ユーザ権限リストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ユーザ権限リストを取得します。
   * <ol>
   * <li>パラメータのチェックを行い、前提条件を満たさなければ空のリストを返します。</li>
   * <li>ユーザコードを使用して管理ユーザ権限のリストを取得します。</li>
   * <li>レコードが存在しない場合は、空のリストを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>userCode≠null</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param userCode
   * @return 管理ユーザ権限のリスト
   */
  List<UserPermission> getUserPermissionList(Long userCode);

  /**
   * 管理ユーザの権限区分を返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>管理ユーザの権限区分を返します。
   * <ol>
   * <li>パラメータのチェックを行い、前提条件を満たさなければnullを返します。</li>
   * <li>ユーザコードを使用して管理ユーザ権限のリストを取得します。</li>
   * <li>レコードが存在しない場合はnullを返します。</li>
   * <li>取得した権限リストとPermissionのenumを比較します。</li>
   * <li>サイト権限を保持していれば、PermissionType.SITEを返します。</li>
   * <li>ショップ権限を保持していれば、PermissionType.SHOPを返します。</li>
   * <li>権限なしか、両方の権限を保持する場合は、改竄とみなしてnullを返します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>userCode≠null</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param userCode
   * @return 管理ユーザの権限区分
   */
  PermissionType getPermissionType(Long userCode);

  /**
   * 顧客のログイン認証を行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客のログイン認証を行います。
   * <ol>
   * <li>ログインIDで顧客情報を取得します。<br>
   * ・顧客が存在しなければ、認証エラーを返します。<br>
   * ・ログインロック中であれば、認証エラーを返します。</li>
   * <li>暗号化されたパスワードと認証を行います。</li>
   * <li>認証成功の場合<br>
   * ・ログイン失敗回数をクリアします。<br>
   * ・ログイン日時をシステム日付で更新します。</li>
   * <li>認証失敗の場合<br>
   * ・ログイン失敗回数を1加算します。<br>
   * ・ログインエラー最大回数を超過した場合、顧客をロック状態にします。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>loginId、passwordは必須であること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし。</dd>
   * </dl>
   * </p>
   * 
   * @param loginId
   * @param password
   * @return ログイン認証結果
   */
  ServiceResult authorizeCustomer(String loginId, String password);

}
