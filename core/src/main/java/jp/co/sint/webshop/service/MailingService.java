package jp.co.sint.webshop.service;

import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.data.dto.RespectiveMailqueue;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.mail.BroadcastMailqueueSuite;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;

/**
 * SI Web Shopping 10 メール送信サービス(MailingService)仕様
 * 
 * @author System Integrator Corp.
 */
public interface MailingService {

  /**
   * メールをキューに登録せず、即時送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>メールをキューに登録せず、即時送信します。
   * <ol>
   * <li>MailSenderクラスを用いて、引数で受取ったメールを即時送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>フロント側問い合わせ送信などで使うものです メールテンプレートを使用しますメール送信にはこれを使わないでください。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param mail
   *          メール文面
   * @return サービス実行結果
   */
  ServiceResult sendImmediate(MailInfo mail);

  /**
   * 未送信の同報配信メール情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>未送信の同報配信メール情報を取得します。
   * <ol>
   * <li>同報配信メールキューヘッダテーブルから、メール送信ステータスが"全て送信済み"でない一覧を取得します。</li>
   * <li>取得した同報配信メールキューヘッダ情報1件ごとに、以下の処理を実行します。
   * <ol>
   * <li>同報配信メールデータセットを生成し、メールキューヘッダ情報を設定します。</li>
   * <li>同報配信メールキュー明細テーブルから、キューIDがメールキューヘッダ情報のメールキューIDかつ、<br />
   * メール送信ステータスが"全て送信済み"でない一覧を取得します。</li>
   * <li>取得したメールキュー明細一覧を同報配信メールデータセットに設定します。</li>
   * </ol>
   * </li>
   * <li>上記処理にて作成された同報配信メールデータセット一覧を返します。</li>
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
   * @return 未送信の同報配信メール明細
   */
  List<BroadcastMailqueueSuite> getNoSentBroadcastMailqueueDetailList();

  /**
   * 同報配信メールキューを更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>同報配信メールキューを更新します。
   * <ol>
   * <li>引数で受取ったメールキューデータセットの、ヘッダ情報・明細リストに対してValidationチェックを行います。</li>
   * <li>TransactionStart
   * <ol>
   * <li>メールキューヘッダ情報を、同報配信メールキューヘッダテーブルへ更新します。</li>
   * <li>メールキュー明細リストを1件ずつ、同報配信メールキュー明細テーブルへ更新します。</li>
   * </ol>
   * TransactionEnd</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取ったメールキューデータセットはnullでない物とします。</dd>
   * <dd>メールキューデータセットのメールキューヘッダはnullでない物とします。</dd>
   * <dd>メールキューデータセットのメールキュー明細の一覧はnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>同報配信メールキューヘッダテーブル・明細テーブルが、引数で渡された<br />
   * メールキューデータセットの値で更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param suite
   *          同報配信メールキューデータセット
   * @return サービス実行結果<br />
   *         メールキューヘッダ情報が持つメールキューIDのデータが同報配信メールキューヘッダテーブルに<br />
   *         存在しなかった場合、データ非存在エラーが返ります。<br />
   *         メールキュー明細情報が持つメールキューID・顧客コードのデータが同報配信メールキュー明細テーブルに<br />
   *         存在しなかった場合、データ非存在エラーが返ります。<br />
   *         メールキューデータセットのヘッダ情報・明細リストがValidationエラーの場合、Validationエラーが返ります。<br />
   */
  ServiceResult updateBroadcastMailqueue(BroadcastMailqueueSuite suite);

  /**
   * 同報配信メールキューを登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>同報配信メールキューを登録します。
   * <ol>
   * <li>引数で受取った同報配信メールキュー情報の、ヘッダと明細に対してValidationチェックを行います。</li>
   * <li>引数で受取った同報配信メールキュー情報を、同報配信メールキューヘッダ・明細へと登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取ったsuiteはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>同報配信メールキューヘッダ・明細マスタへ、引数で渡した情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param suite
   *          同報配信メールキューデータセット
   * @return サービス処理結果
   */
  ServiceResult insertBroadcastMailqueue(BroadcastMailqueueSuite suite);

  /**
   * 個別配信メールキューを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>個別配信メールキューを削除します。<br>
   * メール送信日時がシステム日付 - month 以前のデータを全て削除します。<br>
   * <ol>
   * <li>引数で受取った月数とシステム日付より、削除日付を取得します。</li>
   * <li>個別配信メールキューテーブルから、送信日付が削除日付以前の物を全て削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数の月数はnullでない、また負の数でない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>個別配信メールキューテーブルより、送信日時が引数で指定した月数以前のデータが全て削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param month
   *          月数
   * @return サービス処理結果
   */
  ServiceResult deleteRespectiveMailqueue(int month);

  /**
   * 同報配信メールキューを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>同報配信メールキューを削除します。<br />
   * メール送信日時がシステム日付 - month 以前のデータを全て削除します。<br>
   * <ol>
   * <li>引数で受取った月数とシステム日付より、削除日付を取得します。</li>
   * <li>同報配信メールキュー明細テーブルから、送信日付が削除日付以前の物を全て削除します。</li>
   * <li>同報配信メールキューヘッダテーブルから、明細テーブルに存在しないキューIDの物を全て削除します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数の月数はnullでない、また負の数でない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>同報配信メールキューヘッダテーブル・明細テーブルより、送信日時が<br />
   * 引数で指定した月数以前のデータが全て削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param month
   *          月数
   * @return サービス処理結果
   */
  ServiceResult deleteBroadcastMailqueue(int month);

  /**
   * 個別配信メールをキューにいれ送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>個別配信メールをキューにいれ送信します。
   * <ol>
   * <li>引数で受取ったメールキューに、メールキューIDシーケンスより生成される新しいIDを設定します。</li>
   * <li>TransactionStart
   * <ol>
   * <li>メール送信用情報を新たに生成し、メールキュー情報から題名・送信元アドレス・送信元名・送信日付を設定します。</li>
   * <li>メールキュー情報のコンテントタイプを、メール送信用情報に設定します。</li>
   * <li>メール送信用情報に、送信先アドレス、BCCアドレス、本文を設定します。</li>
   * <li>メールを送信します。</li>
   * <li>メール送信が成功した時、メールキュー情報の送信ステータスに全て送信済みを設定します。</li>
   * <li>メール送信が成功した時、メールキュー情報の送信日時に現在日時を設定します。</li>
   * <li>個別配信メールキューテーブルに、メールキュー情報を登録します。</li>
   * </ol>
   * TransactionEnd</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取ったメールキュー情報はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>メール送信成功時、個別配信メールキューテーブルに送信済みのメールキュー情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param mailQueue
   *          個別配信メールキュー
   * @return サービス処理結果<br />
   *         メールキュー情報のValidationチェックが失敗した場合、Validationエラーが返されます。
   */
  ServiceResult sendRespectiveMail(RespectiveMailqueue mailQueue);

  /**
   * 受注確認、または受注修正メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った受注情報、支払方法情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>会員の受注情報だった場合、顧客テーブルから最新の送信先アドレスを取得します。</li>
   * <li>メールタイプを受注確認(PC)に設定します。</li>
   * <li>引数で受取ったショップ情報のショップコード・メールタイプからメールテンプレートを取得します。</li>
   * <li>取得したメールテンプレート情報より、メールテンプレート展開クラスを生成します。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ受注ヘッダ情報をセットします。</li>
   * <li>受注修正メッセージをクリアします。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ各出荷ヘッダ情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、展開した各出荷ヘッダ情報が持つ各出荷明細情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、支払情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、ショップ情報をセットします。</li>
   * <li>メールテンプレート情報・値をセットしたメールテンプレート展開クラス・送信先アドレスから、メールキューを生成します。</li>
   * <li>生成したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注情報はnullでない物とします。</dd>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当するショップコードのデータが存在する物とします。</dd>
   * <dd>受注情報、支払方法情報、新規受注フラグは全てnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param order
   *          受注情報
   * @param payment
   *          支払方法情報
   * @param shop
   *          ショップ
   * @return サービス処理結果<br />
   *         メールテンプレートが存在しなかった場合、メールテンプレート非存在エラーが返ります。
   */
  ServiceResult sendNewOrderMailPc(OrderContainer order, CashierPayment payment, Shop shop);

  /**
   * 受注確認、または受注修正メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った受注情報、支払方法情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>会員の受注情報だった場合、顧客テーブルから最新の送信先アドレスを取得します。</li>
   * <li>メールタイプを受注確認(携帯)に設定します。</li>
   * <li>引数で受取ったショップ情報のショップコード・メールタイプからメールテンプレートを取得します。</li>
   * <li>取得したメールテンプレート情報より、メールテンプレート展開クラスを生成します。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ受注ヘッダ情報をセットします。</li>
   * <li>受注修正メッセージをクリアします。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ各出荷ヘッダ情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、展開した各出荷ヘッダ情報が持つ各出荷明細情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、支払情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、ショップ情報をセットします。</li>
   * <li>メールテンプレート情報・値をセットしたメールテンプレート展開クラス・送信先アドレスから、メールキューを生成します。</li>
   * <li>生成したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注情報はnullでない物とします。</dd>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当するショップコードのデータが存在する物とします。</dd>
   * <dd>受注情報、支払方法情報、新規受注フラグは全てnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param order
   *          受注情報
   * @param payment
   *          支払方法情報
   * @param shop
   *          ショップ
   * @return サービス処理結果<br />
   *         メールテンプレートが存在しなかった場合、メールテンプレート非存在エラーが返ります。
   */
  ServiceResult sendNewOrderMailMobile(OrderContainer order, CashierPayment payment, Shop shop);

  /**
   * 受注確認、または受注修正メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った受注情報、支払方法情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>会員の受注情報だった場合、顧客テーブルから最新の送信先アドレスを取得します。</li>
   * <li>メールタイプを受注確認(PC)に設定します。</li>
   * <li>引数で受取ったショップ情報のショップコード・メールタイプからメールテンプレートを取得します。</li>
   * <li>取得したメールテンプレート情報より、メールテンプレート展開クラスを生成します。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ受注ヘッダ情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ各出荷ヘッダ情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、展開した各出荷ヘッダ情報が持つ各出荷明細情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、支払情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、ショップ情報をセットします。</li>
   * <li>メールテンプレート情報・値をセットしたメールテンプレート展開クラス・送信先アドレスから、メールキューを生成します。</li>
   * <li>生成したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注情報はnullでない物とします。</dd>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当するショップコードのデータが存在する物とします。</dd>
   * <dd>受注情報、支払方法情報、新規受注フラグは全てnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param order
   *          受注情報
   * @param payment
   *          支払方法情報
   * @param shop
   *          ショップ
   * @return サービス処理結果<br />
   *         メールテンプレートが存在しなかった場合、メールテンプレート非存在エラーが返ります。
   */
  ServiceResult sendUpdateOrderMail(OrderContainer order, CashierPayment payment, Shop shop);

  /**
   * 入金確認メールを送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入金確認メールを送信します。
   * <ol>
   * <li>ゲストの受注だった場合は引数で受取った受注情報のメールアドレスを、会員の受注だった場合は<br />
   * 受注情報の顧客コードを用いて顧客マスタより取得した顧客情報のメールアドレスを顧客メールアドレスとします。</li>
   * <li>1で取得した顧客メールアドレスを、引数で受取った受注情報の受注ヘッダへセットします。</li>
   * <li>引数で受取ったショップ情報のショップコードから、出荷連絡のメールテンプレート情報を取得します。<br />
   * メールテンプレートが存在しなかった場合、メールテンプレート非存在エラーを返します。</li>
   * <li>3で生成したメールテンプレート情報から、メールテンプレート展開クラスを生成します。</li>
   * <li>4で生成したメールテンプレート展開クラスに、必要な情報をセットしていきます。</li>
   * <li>3で生成したメールテンプレート情報、1で取得した顧客メールアドレス、5で値をセットしたメールテンプレート展開クラスからメールキューを生成します。</li>
   * <li>6で生成したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>
   * <ol>
   * <li>orderContainerはnullでない物とします。</li>
   * <li>orderSummaryはnullでない物とします。</li>
   * <li>shopはnullでない物とします。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>送信したメール情報が個別配信メールキューマスタへ登録されます</dd>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   * @param orderSummary
   * @param shop
   * @return サービス処理結果
   */
  ServiceResult sendPaymentReceivedMail(OrderContainer orderContainer, OrderSummary orderSummary, Shop shop);

  /**
   * 入金督促メールを送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入金督促メールを送信します。
   * <ol>
   * <li>ゲストの受注だった場合は引数で受取った受注情報のメールアドレスを、会員の受注だった場合は<br />
   * 受注情報の顧客コードを用いて顧客マスタより取得した顧客情報のメールアドレスを顧客メールアドレスとします。</li>
   * <li>1で取得した顧客メールアドレスを、引数で受取った受注情報の受注ヘッダへセットします。</li>
   * <li>引数で受取ったショップ情報のショップコードから、出荷連絡のメールテンプレート情報を取得します。</li>
   * <li>3で生成したメールテンプレート情報から、メールテンプレート展開クラスを生成します。</li>
   * <li>4で生成したメールテンプレート展開クラスに、必要な情報をセットしていきます。</li>
   * <li>3で生成したメールテンプレート情報、1で取得した顧客メールアドレス、5で値をセットしたメールテンプレート展開クラスからメールキューを生成します。</li>
   * <li>6で生成したメールキューを用いて、メールを送信します。</li>
   * <li></li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>
   * <ol>
   * <li>orderContainerはnullでない物とします。</li>
   * <li>orderSummaryはnullでない物とします。</li>
   * <li>shopはnullでない物とします。</li>
   * <li>cashierPaymentはnullでない物とします。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>送信したメール情報が個別配信メールキューマスタへ登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   * @param orderSummary
   * @param shop
   * @param cashierPayment
   * @return サービス処理結果
   */
  ServiceResult sendPaymentReminderMail(OrderContainer orderContainer, OrderSummary orderSummary, Shop shop,
      CashierPayment cashierPayment);

  /**
   * 出荷連絡メールを送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷連絡メールを送信します。
   * <ol>
   * <li>ゲストの受注だった場合は引数で受取った受注情報のメールアドレスを、会員の受注だった場合は<br />
   * 受注情報の顧客コードを用いて顧客マスタより取得した顧客情報のメールアドレスを顧客メールアドレスとします。</li>
   * <li>1で取得した顧客メールアドレスを、引数で受取った受注情報の受注ヘッダへセットします。</li>
   * <li>引数で受取ったショップ情報のショップコードから、出荷連絡のメールテンプレート情報を取得します。<br />
   * メールテンプレートが存在しなかった場合、メールテンプレート非存在エラーを返します。</li>
   * <li>3で生成したメールテンプレート情報から、メールテンプレート展開クラスを生成します。</li>
   * <li>4で生成したメールテンプレート展開クラスに、必要な情報をセットしていきます。</li>
   * <li>3で生成したメールテンプレート情報、1で取得した顧客メールアドレス、5で値をセットしたメールテンプレート展開クラスからメールキューを生成します。</li>
   * <li>6で生成したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>
   * <ol>
   * <li>orderContainerはnullでない物とします。</li>
   * <li>allContainerはnullでない物とします。</li>
   * <li>shopはnullでない物とします。</li>
   * <li>paymentはnullでない物とします。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>送信したメール情報が個別配信メールキューマスタへ登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   * @param allContainer
   * @param shop
   * @param payment
   * @param shippingNo
   * @return サービス処理結果
   */
  ServiceResult sendShippingReceivedMail(OrderContainer orderContainer, OrderContainer allContainer, Shop shop,
      CashierPayment payment, String shippingNo);
  
  // 2011-02-06 yyq add start desc:tmall 发送邮件
  ServiceResult sendTmallShippingReceivedMail(OrderContainer orderContainer, OrderContainer allContainer, Shop shop,
      CashierPayment payment, String shippingNo);
  // 2011-02-06 yyq add end desc:tmall 发送邮件

  /**
   * 入荷お知らせメールを送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入荷お知らせメールを送信します。
   * <ol>
   * <li>引数で受取ったショップ情報のショップコードから、入荷お知らせのメールテンプレート情報を取得します。<br />
   * メールテンプレートが存在しなかった場合、メールテンプレート非存在エラーを返します。</li>
   * <li>1で生成したメールテンプレート情報から、メールテンプレート展開クラスを生成します。</li>
   * <li>2で生成したメールテンプレート展開クラスに、必要な情報をセットしていきます。</li>
   * <li>1で生成したメールテンプレート情報、引数で受取った顧客リスト、3で値をセットしたメールテンプレート展開クラスからメールキューを生成します。</li>
   * <li>4で生成したメールキューをBroadcastMailqueueに登録します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>
   * <ol>
   * <li>commodityHeaderはnullでないものとします。</li>
   * <li>commodityDetailはnullでないものとします。</li>
   * <li>shopはnullでないものとします。</li>
   * <li>customerListはnullでないものとします。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>同報配信メールキューヘッダ、同報配信メールキュー明細に送信予定のメールキューが登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param commodityHeader
   * @param commodityDetail
   * @param shop
   * @param customerList
   * @return サービス処理結果
   */
  ServiceResult sendArrivalInformationMail(CommodityHeader commodityHeader, CommodityDetail commodityDetail, Shop shop,
      List<Customer> customerList);

  /**
   * 顧客登録、または顧客更新メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った顧客情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>サイト管理者情報が持つショップコードから、メールテンプレート情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、顧客情報が持つ顧客情報をセットします。</li>
   * <li>サイト管理者ショップ情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、3で取得したショップ情報をセットします。</li>
   * <li>1～4で値をセットしたメールテンプレート展開クラスから、メール本文を取得します。</li>
   * <li>5で取得したメール本文、顧客情報、ショップ情報からメールキューを生成します。</li>
   * <li>6で取得したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当しますサイト管理者ショップコードのデータが存在する物とします。</dd>
   * <dd>顧客情報はnullでない物とします。</dd>
   * <dd>ショップ情報はnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param customer
   *          顧客情報
   * @param shop
   *          ショップ情報
   * @return サービス処理結果
   */
  ServiceResult sendCustomerRegisterdMail(Customer customer, Shop shop);

  /**
   * パスワード変更メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った顧客情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>サイト管理者情報が持つショップコードから、メールテンプレート情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、顧客情報が持つ顧客情報をセットします。</li>
   * <li>サイト管理者ショップ情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、3で取得したショップ情報をセットします。</li>
   * <li>1～4で値をセットしたメールテンプレート展開クラスから、メール本文を取得します。</li>
   * <li>5で取得したメール本文、顧客情報、ショップ情報からメールキューを生成します。</li>
   * <li>6で取得したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当しますサイト管理者ショップコードのデータが存在する物とします。</dd>
   * <dd>顧客情報はnullでない物とします。</dd>
   * <dd>ショップ情報はnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param customer
   *          顧客情報
   * @param shop
   *          ショップ情報
   * @return サービス処理結果
   */
  ServiceResult sendPasswordChangeMail(Customer customer, Shop shop);

  /**
   * パスワード再登録URL送信メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った顧客情報とリマインダー情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>サイト管理者情報が持つショップコードから、メールテンプレート情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、顧客情報が持つ顧客情報とリマインダー情報をセットします。</li>
   * <li>サイト管理者ショップ情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、3で取得したショップ情報をセットします。</li>
   * <li>1～4で値をセットしたメールテンプレート展開クラスから、メール本文を取得します。</li>
   * <li>5で取得したメール本文、顧客情報、リマインダー情報、ショップ情報からメールキューを生成します。</li>
   * <li>6で取得したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当しますサイト管理者ショップコードのデータが存在する物とします。</dd>
   * <dd>顧客情報はnullでない物とします。</dd>
   * <dd>ショップ情報はnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param customer
   *          顧客情報
   * @param reminder
   *          リマインダー情報
   * @param shop
   *          ショップ情報
   * @param contextPath
   *          コンテキストパス
   * @return サービス処理結果
   */
  ServiceResult sendPasswordSendMail(Customer customer, Reminder reminder, Shop shop, String contextPath, String langStr);
  
  ServiceResult sendPaymentPasswordSendMail(Customer customer, Reminder reminder, Shop shop, String contextPath, String langStr);

  /**
   * 顧客削除依頼メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った顧客情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>サイト管理者情報が持つショップコードから、メールテンプレート情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、顧客情報が持つ顧客情報をセットします。</li>
   * <li>サイト管理者ショップ情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、3で取得したショップ情報をセットします。</li>
   * <li>1～4で値をセットしたメールテンプレート展開クラスから、メール本文を取得します。</li>
   * <li>5で取得したメール本文、顧客情報、ショップ情報からメールキューを生成します。</li>
   * <li>6で取得したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当しますサイト管理者ショップコードのデータが存在する物とします。</dd>
   * <dd>顧客情報はnullでない物とします。</dd>
   * <dd>ショップ情報はnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param customer
   *          顧客情報
   * @param shop
   *          ショップ情報
   * @return サービス処理結果
   */
  ServiceResult sendWithdrawalRequestMail(Customer customer, Shop shop);

  /**
   * 顧客削除メールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った顧客情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>サイト管理者情報が持つショップコードから、メールテンプレート情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、顧客情報が持つ顧客情報をセットします。</li>
   * <li>サイト管理者ショップ情報を取得します。</li>
   * <li>メールテンプレート展開クラスに、3で取得したショップ情報をセットします。</li>
   * <li>1～4で値をセットしたメールテンプレート展開クラスから、メール本文を取得します。</li>
   * <li>5で取得したメール本文、顧客情報、ショップ情報からメールキューを生成します。</li>
   * <li>6で取得したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当しますサイト管理者ショップコードのデータが存在する物とします。</dd>
   * <dd>顧客情報はnullでない物とします。</dd>
   * <dd>ショップ情報はnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param customer
   *          顧客情報
   * @param shop
   *          ショップ情報
   * @return サービス処理結果
   */
  ServiceResult sendWithdrawalMail(Customer customer, Shop shop);

  /**
   * 誕生日は今月以内の顧客に対し誕生日メールを送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>メール送信必要の顧客と贈りのクーポンがいるかどうか検索し、いた場合誕生日メールを送信します。
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特になし
   * </dl>
   * <dl>
   * </dl>
   * </p>
   * @param customerList
   *       　　　　　　メール送信必要の顧客リスト
   * @param newCouponRule
   *       　　　　　　贈りのクーポン情報     
   * @param shop
   *          ショップ情報
   * @return サービス実行結果です。メール送信に失敗した顧客がいた場合、エラーメッセージが格納されます。
   */
  /** 2013/05/13 优惠券对应 ob update start */
  //ServiceResult sendBirthdayMail(List<Customer> customerList,NewCouponRule newCouponRule,Shop shop);
  ServiceResult sendBirthdayMail(List<Customer> customerList,List<NewCouponHistory> historyList,Shop shop);
  /** 2013/05/13 优惠券对应 ob update end */
  

  /**
   * ポイント失効直前の顧客に対しポイント失効メールを送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>ポイント失効直前の顧客がいるかどうか検索し、いた場合ポイント失効メールを送信します。
   * <ol>
   * <li>引数で指定された日数が1以上であるかどうかチェックします。1未満であった場合はサービス実行結果にVALIDATION_ERRORを設定します。</li>
   * <li>顧客テーブルからポイント失効日ががシステム日付ののbeforeDays日後である顧客のリストを取得します。</li>
   * <li>メールテンプレート(ポイント失効メール)を取得します。</li>
   * <li>2．で取得した顧客に対しポイント失効メールを送信します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>beforeDaysが1以上であること。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param beforeDays
   *          ポイント失効日の何日前にポイント失効メールを送信するかを定める値です。例えば3月3日にbeforeDays=3でこのメソッドを実行すると、ポイント失効日がが3月6日である顧客に対しポイント失効メールが送信されます。
   * @return サービス実行結果です。メール送信に失敗した顧客がいた場合、エラーメッセージが格納されます。
   */
  ServiceResult sendPointExpiredMail(int beforeDays);

  /**
   * 顧客検索条件に一致する顧客に対し情報メールを送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>顧客検索条件に一致する顧客がいるかどうか検索し、いた場合情報メールを送信します。
   * <ol>
   * <li>顧客テーブルから引数の条件に一致する顧客のリストを取得します。</li>
   * <li>1.で取得した顧客に対し引数で取得したメールテンプレート(情報メール)を送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>検索条件情報はnullでない物とします。</dd>
   * <dd>メールテンプレートはnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param condition
   *          検索条件
   * @param mailTemplateSuite
   *          メールテンプレート
   * @return サービス実行結果です。メール送信に失敗した顧客がいた場合、エラーメッセージが格納されます。
   */
  ServiceResult sendInformationMail(CustomerSearchCondition condition, MailTemplateSuite mailTemplateSuite);

  /**
   * 受注キャンセルメールをメールテンプレート画面で作成した<br>
   * テンプレートに従って作成し、送信します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った受注情報、支払方法情報をメールテンプレート画面で作成した<br>
   * テンプレートにそって展開し、生成されたメールを送信します。
   * <ol>
   * <li>会員の受注情報だった場合、顧客テーブルから最新の送信先アドレスを取得します。</li>
   * <li>メールタイプを受注キャンセルに設定します。</li>
   * <li>引数で受取ったショップ情報のショップコード・メールタイプからメールテンプレートを取得します。</li>
   * <li>取得したメールテンプレート情報より、メールテンプレート展開クラスを生成します。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ受注ヘッダ情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、受注情報が持つ各出荷ヘッダ情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、展開した各出荷ヘッダ情報が持つ各出荷明細情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、支払情報をセットします。</li>
   * <li>メールテンプレート展開クラスに、ショップ情報をセットします。</li>
   * <li>メールテンプレート情報・値をセットしたメールテンプレート展開クラス・送信先アドレスから、メールキューを生成します。</li>
   * <li>生成したメールキューを用いて、メールを送信します。</li>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注情報はnullでない物とします。</dd>
   * <dd>メールテンプレートヘッダマスタ、メールテンプレート明細マスタに<br>
   * 該当するショップコードのデータが存在する物とします。</dd>
   * <dd>受注情報、支払方法情報は全てnullでない物とします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   *          受注情報
   * @param payment
   *          支払方法情報
   * @param shop
   *          ショップ
   * @return サービス処理結果<br />
   *         メールテンプレートが存在しなかった場合、メールテンプレート非存在エラーが返ります。
   */
  ServiceResult sendCancelOrderMail(OrderContainer orderContainer, CashierPayment payment, Shop shop);

  // Add by V10-CH start
  ServiceResult sendCustomerChangeGroupMail(Customer customer, Shop shop, String customerGroupName, String groupChangeType, String month);
  ServiceResult sendCustomerChangeGroupMailToLower(Customer customer, Shop shop, String customerGroupName, String groupChangeType, String month,String groupRange);
  // Add by V10-CH end
  ServiceResult sendCustomerUpgradesRemindMail(Customer customer, Shop shop, String customerGroupName, String groupRange, String groupChangeType, String month);
  
  // 20111230 ob add start
  ServiceResult sendCouponStartBeforeMail(NewCouponHistory newCouponHistory, Shop shop, Customer customer);
  
  ServiceResult sendCouponEndBeforeMail(NewCouponHistory newCouponHistory, Shop shop, Customer customer);
  // 20111230 ob add end
  
  //成绩介绍发送邮件
  ServiceResult sendFriendCouponMail(String customerCode);
  
  /**
   * バッチでクーポンステータス更新します<br>
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * </dd>出荷日時と受注ステータスチェックに基づいてクーポンステータス更新する
   * </dl>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * </dl>特にありません。
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * @return サービス処理結果<br />
   */
  ServiceResult updatePointStatus();
}
