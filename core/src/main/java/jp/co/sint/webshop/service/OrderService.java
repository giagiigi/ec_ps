package jp.co.sint.webshop.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.BatchTime;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.JdArea;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.ShippingRealityDetail;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetailComposition;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.data.dto.UntrueOrderWord;
import jp.co.sint.webshop.service.alipay.PaymentAlipayResultBean;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultBean;
import jp.co.sint.webshop.service.customer.CustomerCouponInfo;
import jp.co.sint.webshop.service.event.StockEvent;
import jp.co.sint.webshop.service.event.StockEventType;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;
import jp.co.sint.webshop.service.order.BookSalesContainer;
import jp.co.sint.webshop.service.order.CommodityOfCartInfo;
import jp.co.sint.webshop.service.order.DeliveryInfo;
import jp.co.sint.webshop.service.order.InputShippingReport;
import jp.co.sint.webshop.service.order.MyOrder;
import jp.co.sint.webshop.service.order.MyOrderListSearchCondition;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderCountSearchCondition;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderIdentifier;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.OrderPaymentInfo;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.ReturnInfo;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.order.ShippingList;
import jp.co.sint.webshop.service.order.ShippingListSearchCondition;
import jp.co.sint.webshop.service.order.UntrueOrderWordResult;
import jp.co.sint.webshop.service.order.UntrueOrderWordSearchCondition;
import jp.co.sint.webshop.utility.DateRange;

/**
 * SI Web Shopping 10 受注管理サービス(OrderService)仕様<br>
 * 
 * @see jp.co.sint.webshop.service.result.ServiceErrorContent
 * @author System Integrator Corp.
 */
public interface OrderService {

  /**
   * 受注をキャンセルします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注のキャンセル処理を行います。
   * <ol>
   * <li>引数で受取った受注識別情報の受注番号を用いて、受注ヘッダテーブルから更新対象のデータを取得します。</li>
   * <li>引数で受取った受注識別情報の受注番号を用いて、受注明細テーブルから対象受注明細の一覧を取得します。</li>
   * <li>引数で受取った受注識別情報の受注番号を用いて、該当の受注集計情報を取得します。</li>
   * <li>事前に取得した受注明細一覧情報を元に、入出庫情報のリストを作成します。<br />
   * (入出庫情報には受注明細情報のショップコード・SKUコード・購入商品数が設定されます)</li>
   * <li>TransactionStart
   * <ol>
   * <li>取得した受注情報の受注ステータスを取得します。(キャンセル前受注ステータスとして以後扱います。)</li>
   * <li>取得した受注情報の受注ステータスに、キャンセルを設定します。</li>
   * <li>先ほど値を設定した受注情報を受注ヘッダテーブルへ更新します。</li>
   * <li>ポイント履歴テーブルの、受注番号に紐づくデータを無効に更新します。</li>
   * <li>取得した受注情報の顧客コードを用いて、顧客テーブルより顧客情報を取得します。</li>
   * <li>取得した顧客情報の仮発行ポイントを、該当受注により獲得したポイントの分だけ減らします。</li>
   * <li>取得した顧客情報のポイント残高を、該当受注で使用したポイントの分だけ増やします。</li>
   * <li>ポイントを修正した顧客情報を、顧客テーブルへと更新します。</li>
   * <li>キャンセル前受注ステータスが受注だった場合、StockManagerを用いて在庫引き当て取消処理を行います。</li>
   * <li>キャンセル前受注ステータスが予約だった場合、StockManagerを用いて予約モード在庫引き当て取消処理を行います。</li>
   * <li>キャンセル前受注ステータスがキャンセルだった場合、受注キャンセルエラーを返します。</li>
   * <li>キャンセル前受注ステータスが受注・予約・キャンセル以外の場合、受注キャンセルエラーを返します。</li>
   * <li>
   * StockManagerを用いて引き当て取消を行った際に、StockManagerからfalseが返された場合、受注キャンセルエラーを返します。</li>
   * <li>受注番号に関連付いている出荷ヘッダのリストを取得し、各出荷ヘッダ情報の出荷ステータスにキャンセルを設定します。</li>
   * <li>値を設定した出荷ヘッダ情報を出荷ヘッダテーブルへ更新します。</li>
   * <li>受注番号を元に受注情報を取得し、その受注情報を元に与信キャンセル処理を行います。</li>
   * </ol>
   * TransactionEnd</li>
   * <li>トランザクション成功後、受注キャンセル実行時処理を行います。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取った受注識別情報はnullでないものとします。</dd>
   * <dd>引数で受取った受注識別情報の受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注情報の受注ステータスと、出荷情報の出荷ステータスがキャンセルに変更されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderIdentifier
   *          受注識別情報
   * @return サービス実行結果 更新対象の受注データが存在しない場合、該当データ未存在エラーが返ります。<br />
   *         更新対象の受注データが未入金でない場合、受注キャンセルエラーが返ります。<br />
   *         更新対象の受注データが未出荷でない場合、受注キャンセルエラーが返ります。
   */
  ServiceResult cancel(OrderIdentifier orderIdentifier);
  
  ServiceResult tmallCancel(OrderIdentifier orderIdentifier);
  
  ServiceResult jdCancel(OrderIdentifier orderIdentifier);

  /**
   * 入金日の設定を取り消します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入金日の設定を取り消します。
   * <ol>
   * <li>引数で受取った受注番号を受注ヘッダDTOにセットします。</li>
   * <li>値をセットした受注ヘッダ情報の、受注番号に対してValidationチェックを行います。</li>
   * <li>引数で受取った受注番号を用いて、受注ヘッダテーブルから更新対象の受注ヘッダ情報を取得します。</li>
   * <li>引数で受取った受注番号を用いて、受注集計情報を取得します。</li>
   * <li>TransactionStart
   * <ol>
   * <li>取得した受注ヘッダ情報の入金ステータスに未入金をセットします。</li>
   * <li>取得した受注ヘッダ情報の入金日にnullをセットします。</li>
   * <li>受注ヘッダ情報を、受注ヘッダテーブルへ更新します。</li>
   * <li>取得した受注ヘッダ情報の後先区分が先払いだった場合、引数で受取った受注番号を用いて<br />
   * 出荷ヘッダテーブルから対応する出荷ヘッダのリストを取得します。</li>
   * <li>1取得した出荷ヘッダリスト全件の出荷ステータスを入金待ちに変更し、出荷ヘッダテーブルへ更新します。</li>
   * </ol>
   * TransactionEnd</li>
   * <li>トランザクション成功後、入金日更新実行時処理を行います。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取った受注識別情報はnullでないものとします。</dd>
   * <dd>引数で受取った受注識別情報の受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注ヘッダテーブルの、引数で受取った受注番号のデータが、入金ステータス＝未入金<br />
   * 入金日＝nullに更新されます。</dd>
   * <dd>先払の受注データだった場合、受注番号に関連付いている全出荷データの出荷ステータスが入金待ちに更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderIdentifier
   *          受注識別情報
   * @return サービス実行結果<br />
   *         引数で受取った受注番号の受注データが受注ヘッダテーブルに存在しなかった場合、該当データ未存在エラーが返ります。<br />
   *         更新対象データの入金ステータスが未入金だった場合入金日設定エラーが返ります。<br />
   *         更新対象データの出荷状況が未出荷以外の場合入金日設定エラーが返ります。
   */
  ServiceResult clearPaymentDate(OrderIdentifier orderIdentifier);

  /**
   * 入金日の設定を取り消します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>入金日の設定を取り消します。このメソッドはバッチ処理等からの利用を想定し、同時実行制御を考慮せず常に上書きモードで入金日を取消します。
   * データ更新の同時実行制御が必要な場合はclearPaymentDate(OrderIdentifier orderIdentifier)を
   * 使ってください。
   * <ol>
   * <ol>
   * <li>引数で受取った受注番号を受注ヘッダDTOにセットします。</li>
   * <li>値をセットした受注ヘッダ情報の、受注番号に対してValidationチェックを行います。</li>
   * <li>引数で受取った受注番号を用いて、受注ヘッダテーブルから更新対象の受注ヘッダ情報を取得します。</li>
   * <li>引数で受取った受注番号を用いて、受注集計情報を取得します。</li>
   * <li>TransactionStart
   * <ol>
   * <li>取得した受注ヘッダ情報の入金ステータスに未入金をセットします。</li>
   * <li>取得した受注ヘッダ情報の入金日にnullをセットします。</li>
   * <li>受注ヘッダ情報を、受注ヘッダテーブルへ更新します。</li>
   * <li>取得した受注ヘッダ情報の後先区分が先払いだった場合、引数で受取った受注番号を用いて<br />
   * 出荷ヘッダテーブルから対応する出荷ヘッダのリストを取得します。</li>
   * <li>1取得した出荷ヘッダリスト全件の出荷ステータスを入金待ちに変更し、出荷ヘッダテーブルへ更新します。</li>
   * </ol>
   * TransactionEnd</li>
   * <li>トランザクション成功後、入金日更新実行時処理を行います。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取った受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注ヘッダテーブルの、引数で受取った受注番号のデータが、入金ステータス＝未入金<br />
   * 入金日＝nullに更新されます。</dd>
   * <dd>先払の受注データだった場合、受注番号に関連付いている全出荷データの出荷ステータスが入金待ちに更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @return サービス実行結果<br />
   *         引数で受取った受注番号の受注データが受注ヘッダテーブルに存在しなかった場合、該当データ未存在エラーが返ります。<br />
   *         更新対象データの入金ステータスが未入金だった場合入金日設定エラーが返ります。<br />
   *         更新対象データの出荷状況が未出荷以外の場合入金日設定エラーが返ります。
   * @see OrderService#clearPaymentDate(OrderIdentifier orderIdentifier)
   */
  ServiceResult clearPaymentDate(String orderNo);

  /**
   * 出荷実績を取り消します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷実績を取り消します。
   * <ol>
   * <li>引数で受取った出荷指示情報の出荷番号を用いて、出荷ヘッダテーブルより出荷ヘッダ情報を取得します。</li>
   * <li>取得した出荷ヘッダ情報の受注番号を用いて、受注ヘッダテーブルから受注ヘッダ情報を取得します。</li>
   * <li>出荷ヘッダ情報の出荷ステータスが出荷済以外だった場合、入力値妥当性エラーを返します。</li>
   * <li>取得した受注ヘッダ情報に引数で受取った出荷指示情報の受注情報更新日時をセットし、受注ヘッダテーブルを更新します。</li>
   * <li>取得した出荷ヘッダ情報の更新日時、更新者に引数で受取った更新日時、更新者をセットします。</li>
   * <li>取得した出荷ヘッダ情報の出荷ステータスに出荷手配中をセットします。</li>
   * <li>取得した出荷ヘッダ情報の宅配伝票番号、出荷日、到着予定日、到着時間開始、到着時間終了にnullをセットします。</li>
   * <li>値をセットした出荷ヘッダ情報に対してValidationチェックを行います。</li>
   * <li>引数で受取った出荷指示情報の出荷番号を用いて、出荷明細情報一覧を取得します。</li>
   * <li>取得した出荷明細情報一覧から、在庫入出力情報のリストを作成します。<br />
   * (在庫入出力情報には、出荷明細情報のショップコード・SKUコード・購入商品数が設定されます。)</li>
   * <li>TransactionStart
   * <ol>
   * <li>StockManagerを用いて、作成した在庫入出力情報のリストから出荷実績取消処理を行います。<br />
   * (在庫数と引き当て数が増やされます。)</li>
   * <li>値をセットした出荷ヘッダ情報を、出荷ヘッダテーブルへと更新します。</li>
   * </ol>
   * TransactionEnd</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shippingNoがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>出荷ヘッダテーブルの、引数で受取った出荷番号のデータが、出荷ステータス＝出荷手配中に、<br />
   * 宅配伝票番号、出荷日、到着予定日、到着時間開始、到着時間終了＝nullに更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param report
   *          出荷指示情報
   * @return サービス実行結果<br />
   *         引数で受取った出荷番号の出荷データが出荷ヘッダテーブルに存在しなかった場合該当データ未存在エラーを返します。<br />
   *         更新対象データがデータ連携済、又は売上確定済みの場合入力値妥当性エラーが返ります。<br />
   *         更新対象データの出荷状況が出荷済以外の場合は入力値妥当性エラーが返ります。
   */
  ServiceResult clearShippingReport(InputShippingReport report);

  /**
   * 出荷指示をクリアします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷指示をクリアします。
   * <ol>
   * <li>引数で受取った出荷指示情報の出荷ヘッダ情報の出荷番号を用いて、出荷ヘッダテーブルより出荷ヘッダ情報を取得します。</li>
   * <li>1で取得した出荷ヘッダ情報に対してValidationチェックを行います。</li>
   * <li>引数で受取った出荷指示情報の出荷ヘッダ情報の受注番号を用いて、受注ヘッダテーブルより受注ヘッダ情報を取得します。</li>
   * <li>5で取得した受注ヘッダ情報に、引数で受取った出荷指示情報の受注情報更新日時をセットし、DAOを用いて受注ヘッダテーブルを更新します。</li>
   * <li>引数で受取った出荷指示情報の出荷ヘッダ情報を、DAOを用いて出荷ヘッダテーブルへ更新します。</li></dd>
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
   * <dd>出荷ヘッダテーブルが引数で受取った出荷ヘッダ情報で更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param report
   *          出荷指示情報
   * @return サービス実行結果<br>
   *         引数で受取った出荷番号の出荷データが出荷ヘッダテーブルに存在しなかった場合該当データ未存在エラーを返します。<br />
   *         更新対象データがデータ連携済、売上確定済み、出荷手配中以外のデータの場合、入力値妥当性エラーが返ります。<br />
   *         更新対象データの出荷ステータスが出荷可能以外の出荷ヘッダ情報で更新を行おうとした場合、入力値妥当性エラーが返します。
   */
  ServiceResult clearShippingDirection(InputShippingReport report);

  /**
   * 出荷指示、出荷指示日をクリアします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷指示、出荷指示日をクリアします。
   * <ol>
   * <li>引数で受取った出荷指示情報の出荷ヘッダ情報の出荷番号を用いて、出荷ヘッダテーブルより出荷ヘッダ情報を取得します。</li>
   * <li>1で取得した出荷ヘッダ情報に対してValidationチェックを行います。</li>
   * <li>引数で受取った出荷指示情報の出荷ヘッダ情報の受注番号を用いて、受注ヘッダテーブルより受注ヘッダ情報を取得します。</li>
   * <li>5で取得した受注ヘッダ情報に、引数で受取った出荷指示情報の受注情報更新日時をセットし、DAOを用いて受注ヘッダテーブルを更新します。</li>
   * <li>引数で受取った出荷指示情報の出荷ヘッダ情報を、DAOを用いて出荷ヘッダテーブルへ更新します。</li></dd>
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
   * <dd>出荷ヘッダテーブルが引数で受取った出荷ヘッダ情報で更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param report
   *          出荷指示情報
   * @return サービス実行結果<br>
   *         引数で受取った出荷番号の出荷データが出荷ヘッダテーブルに存在しなかった場合該当データ未存在エラーを返します。<br />
   *         更新対象データがデータ連携済、売上確定済み以外のデータの場合、入力値妥当性エラーが返ります。<br />
   *         更新対象データの出荷ステータスが出荷可能以外の出荷ヘッダ情報で更新を行おうとした場合、入力値妥当性エラーが返します。
   */
  ServiceResult clearShippingDirectDate(InputShippingReport report);

  /**
   * 新規受注を作成します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>新規受注を作成します。
   * <ol>
   * <li>引数で受取った受注情報の受注ヘッダより、顧客コードを取得します。</li>
   * <li>1で取得した顧客コードがゲスト顧客コードでなかった場合、顧客テーブルから取得した顧客コードを用いて顧客情報を取得します。</li>
   * <li>2で顧客情報を取得した時に顧客情報が存在しなかった場合、ステータスが通常以外だった場合は顧客削除済みエラーを返します。</li>
   * <li>引数で受取った受注情報より、受注ヘッダ情報を取得します。</li>
   * <li>4で取得した受注情報にクライアントグループを設定します。</li>
   * <li>4で取得した受注ヘッダ情報のショップコードを用いて、ショップテーブルよりショップ情報を取得する。</li>
   * <li>6で取得したショップ情報が存在しなかった場合、エラーとします。</li>
   * <li>引数で受取った受注情報の受注明細リストに対して一件ずつ、受注明細情報のショップコード・SKUコードを用いて商品テーブル<br />
   * 商品情報を取得し、存在しなかった場合エラーとします。</li>
   * <li>ゲスト購入の場合はポイント関連は使用不可とし、会員の場合はポイントルールテーブルを参照してポイントが使用できるか取得します。</li>
   * <li>ポイントを利用可能な時、今回使用するポイントが顧客の残りポイントより多い場合はエラーとします。</li>
   * <li>商品詳細情報がキャンペーンコードを持っている場合、ショップコードとキャンペンコードを用いてキャンペーンテーブルから<br />
   * 該当キャンペーン情報を取得し、存在しなかった場合はエラーとします。</li>
   * <li>受注番号シーケンスより新規受注番号を取得し、4で取得した受注情報に設定します。</li>
   * <li>引数で受取った受注情報に、付与ポイントを設定します。(新規受注時の日付で計算)</li>
   * <li>引数で受取った受注情報の出荷情報のリストに対して一件ずつ、受注番号・出荷番号・売上確定ステータスを設定していく。</li>
   * <li>引数で受取った受注情報に対してValidationチェックを行う。</li>
   * <li>ポイントが使用可能な場合、仮発行ポイントと使用ポイントを顧客情報にセットします。</li>
   * </ol>
   * 上記処理にて生成した受注情報・決済情報・顧客情報・ポイント情報を用いて、登録・更新処理を行います。<br />
   * 行われる処理は以下のようになります。
   * <ol>
   * <li>insert: ポイント履歴(使用)</li>
   * <li>update: 在庫を引き当てます。</li>
   * <li>insert: 受注ヘッダ</li>
   * <li>insert: 受注明細</li>
   * <li>insert: 出荷ヘッダ</li>
   * <li>insert: 出荷明細</li>
   * <li>insert: ポイント履歴(獲得)</li>
   * <li>update: 顧客テーブル(ポイント残高更新)</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>cashierPaymentはnullでないものとします。</dd>
   * <dd>order、またorderが保持する受注ヘッダ・受注明細・出荷ヘッダ・出荷明細はnullでないものします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>
   * <ol>
   * <li>ポイントを使用した場合に、ポイント履歴テーブルに使用ポイントが登録されます。</li>
   * <li>在庫テーブルの対象商品が更新されます。</li>
   * <li>受注ヘッダテーブルに受注情報が登録されます。</li>
   * <li>受注明細テーブルに該当受注情報が登録されます。</li>
   * <li>出荷ヘッダテーブルに、受注に関連付いている出荷情報が登録されます。</li>
   * <li>出荷明細テーブルに、受注に関連付いている出荷情報が登録されます。</li>
   * <li>ポイントを使用した場合に、ポイント履歴テーブルに付与されたポイントが登録されます。</li>
   * <li>顧客テーブルのポイント残高が更新されます。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * 
   * @param order
   *          受注情報
   * @param cashierPayment
   *          キャッシャー支払方法情報
   * @return サービス実行結果
   *         <ol>
   *         <li>引数で受取った受注情報の請求先顧客が存在しない場合、もしくは顧客ステータスが通常以外の場合<br>
   *         顧客削除済みエラーを返します。</li>
   *         <li>引数で受取った受注情報の決済ショップが未存在の場合、入力値妥当性エラーを返します。</li>
   *         <li>引数で受取った購入/予約商品が未存在の場合、入力値妥当性エラーを返します。</li>
   *         <li>引数で受取った使用ポイントが請求顧客の残りポイントより多い場合、使用ポイントエラーを返します。</li>
   *         <li>購入/予約商品の在庫引当に失敗した場合、在庫引当エラーを返します。</li>
   *         </ol>
   */
  ServiceResult createNewOrder(OrderContainer order, Cashier cashier, String clientType);

  /**
   * 出荷ヘッダから返品データを削除します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受取った出荷番号から、削除対象の出荷データが返品データかどうかチェックを行い<br>
   * 出荷ヘッダから削除処理を行います。
   * <ol>
   * <li>引数で受取った出荷番号より、出荷ヘッダテーブルから出荷ヘッダ情報を取得します。</li>
   * <li>1で取得した出荷ヘッダ情報のreturnItemTypeが返品でなかった場合、サービス処理結果に<br>
   * 出荷情報削除エラーをセットし、値を返します。</li>
   * <li>1で取得した出荷ヘッダ情報が、売上確定かどうかのチェックを行います。</li>
   * <ol>
   * <li>売上確定済みだった場合は、出荷ヘッダテーブルに削除対象のデータのマイナスデータを作成します。</li>
   * <li>データ未確定だった場合は、出荷ヘッダテーブルより該当のレコードを削除します。</li>
   * </ol>
   * </ol></dd>
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
   * <dd>出荷ヘッダテーブルより引数で受取った出荷番号の出荷データが削除されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shippingNo
   *          出荷番号
   * @return サービス処理結果<br />
   *         shippingNoがnullの場合、Validationエラーが返ります。<br />
   *         返品情報でなかった場合、出荷情報削除エラーが返ります。
   */
  ServiceResult deleteReturnItemData(String shippingNo);

  /**
   * 該当受注データを売上確定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>該当受注データを売上確定します。
   * <ol>
   * <li>引数で受取った出荷番号を用いて、出荷ヘッダテーブルから出荷ヘッダ情報を取得します。</li>
   * <li>1で取得した出荷ヘッダ情報の売上確定ステータスに売上確定済みをセットします。</li>
   * <li>ポイント使用可否フラグを作成し、1で取得した出荷データの顧客コードに関連付いている顧客情報が<br />
   * 顧客テーブルに存在した場合のみtrueとし、それ以外はfalseに設定します。</li>
   * <li>3で設定したポイント使用可否フラグがtrueかつ1で取得した出荷ヘッダ情報と同一の受注の出荷情報が全て売上確定済みの場合</li>
   * <ol>
   * <li>引数で受取った受注番号を用いて、ポイント履歴テーブルからポイント履歴のリストを取得します。</li>
   * <li>顧客データの初回購入時フラグをfalseにセットします。</li>
   * <li>今回の受注以外の受注データが存在するかを調べ、存在しなかった場合は初回購入フラグをtrueにセットします。<br />
   * その後、3で取得した顧客情報のポイント残高にポイントルールテーブルの初回購入時付与ポイントを足します。</li>
   * <li>4.1で取得したポイント履歴リスト全件に対して、ポイント発行ステータスを有効にセットし、TransactionManagerを<br />
   * 用いて更新を行い、3で取得した顧客情報のポイント最終獲得日にシステム日付を、ポイント残高には<br />
   * ポイント残高＋発行ポイントを、仮発行ポイントには仮発行ポイント－発行ポイントをセットします。</li>
   * <li>初回購入時フラグがtrueの時、ポイント履歴のDTOを新しく作成しポイント履歴IDにUtilServiceを使って<br />
   * 生成したポイント履歴IDのシーケンスを設定します。</li>
   * <li>4.5で値を設定したポイント履歴情報の顧客コードに1で取得した受注ヘッダ情報の顧客コードを、<br />
   * ポイント発行ステータスに有効を、ポイント発行区分に注文時を、発行ポイントに4.3で設定した初回購入時付与ポイントを設定します。<br />
   * </li>
   * <li>4.6で値を設定したポイント履歴情報のショップコードにサイトのショップコードをセットし、DatabaUtilを用いて<br />
   * ユーザ情報をセットした後にTransactionManagerを用いてポイント履歴テーブルに登録します。</li>
   * </ol>
   * <li>2で値を設定した受注ヘッダ情報をTransactionManagerを用いて受注ヘッダテーブルに更新します。</li> </li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取った受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で受取った受注番号の、受注ヘッダテーブルの受注ヘッダ情報が売上確定ステータス＝確定済に更新されます。</dd>
   * <dd>ポイントが使用されていた場合、ポイントポイント履歴テーブルの該当するポイント履歴情報が<br />
   * ポイント発行ステータス＝有効に更新されます。</dd>
   * <dd>ポイントが使用されていた場合、顧客情報のポイント最終獲得日がシステム日付に、ポイント残高には<br />
   * ポイント残高＋発行ポイントを、仮発行ポイントには仮発行ポイント－発行ポイントが更新されます。</dd>
   * <dd>初回購入時の場合、ポイント履歴テーブルに付与ポイント情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shippingNo
   *          出荷番号
   * @return サービス実行結果<br />
   *         引数で受取った出荷番号の出荷情報が存在しない場合、出荷データ未存在エラーを返します。<br />
   *         更新対象データが出荷済みでない場合、未出荷エラーを返します。<br />
   */
  ServiceResult fixSale(String shippingNo);

  /**
   * 検索条件に該当する受注件数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件に該当する受注件数を取得します。
   * <ol>
   * <li>引数で受取ったsearchConditionを元に検索条件に該当する受注の件数を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionはnullでないものとします。</dd>
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
   *          受注件数検索SearchCondition
   * @return 受注件数
   */
  Long getOrderCount(OrderCountSearchCondition condition);

  /**
   * 受注明細情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注明細情報を取得します。
   * <ol>
   * <li>引数で受取った受注番号、ショップコード、skuコードを用いて、受注明細テーブルより受注明細を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号、ショップコード、skuコードはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          skuコード
   * @return 受注明細
   */
  OrderDetail getOrderDetail(String orderNo, String shopCode, String skuCode);

  /**
   * 受注Noをキーにして、受注明細(ORDER_DETAIL)のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注Noをキーにして、受注明細(ORDER_DETAIL)のリストを返します。
   * <ol>
   * <li>引数で受取った受注番号を用いて、受注明細テーブルから関連付いている受注明細のリストを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @return 受注明細のリスト
   */
  List<OrderDetail> getOrderDetailList(String orderNo);

  /**
   * 受注番号を指定して受注ヘッダを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注番号を指定して受注ヘッダを取得します。
   * <ol>
   * <li>引数で受取った受注番号を用いて、受注ヘッダテーブルから受注ヘッダ情報を取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @return 受注ヘッダ
   */
  OrderHeader getOrderHeader(String orderNo);

  /**
   * 決済サービス取引IDをキーに、受注ヘッダ情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>決済サービス取引IDをキーに、受注ヘッダ情報を取得します。
   * <ol>
   * <li>引数で受取った決済サービス取引IDを用いて、受注ヘッダテーブルから受注ヘッダ情報を取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
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
   *          決済ショップコード
   * @param paymentOrderId
   *          決済サービス取引ID
   * @return 受注ヘッダ
   */
  OrderHeader getOrderHeaderByPaymentOrderId(String shopCode, String paymentOrderId);

  /**
   * 該当のショップコードに関連付いている受注ヘッダリストを取得します。
   * <p>
   * 該当のショップコードに関連付いている受注ヘッダリストを取得します。
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>引数で受取ったショップコードに関連付いている受注ヘッダリストを取得します。</li></dd>
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
   * <dd>該当のショップコードに関連付いている受注ヘッダリストを返します。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @return 受注ヘッダのリスト
   */
  List<OrderHeader> getOrderHeaderList(String shopCode);

  /**
   * 受注番号をキーに、受注ヘッダ、受注明細(全件)、出荷ヘッダ(全件)、出荷明細(全件)を取得する。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注番号をキーに、受注ヘッダ、受注明細(全件)、出荷ヘッダ(全件)、出荷明細(全件)を取得する。
   * <ol>
   * <li>引数で受取った受注番号に関連付いている全受注情報と出荷情報を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @return OrderContainer(受注ヘッダ、受注明細、出荷ヘッダ、出荷明細のセット)
   */
  OrderContainer getOrder(String orderNo);

  /**
   * 受注集計情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注集計情報を取得します。
   * <ol>
   * <li>引数で受取った受注番号を用いて、ORDER_SUMMARY_VIEWから該当するデータを検索し、返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @return 受注集計情報
   */
  OrderSummary getOrderSummary(String orderNo);

  /**
   * 売上確定済みを含むかどうかを指定して、受注集計情報のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>売上確定済みを含むかどうかを指定して、受注集計情報のリストを取得します。
   * <ol>
   * <li>ORDER_SUMMARY_VIEWから全受注集計情報をリストにして返します。</li>
   * <li>引数で受取った確定済受注情報取得フラグがfalseの場合、未確定のデータのみを返します。</li>
   * <li>引数で受取った確定済受注情報取得フラグがtrueの倍、全受注集計情報を返します。</li></dd>
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
   * @param loadFiexdOrder
   *          確定済受注情報取得フラグ
   * @return 受注集計情報のリスト
   */
  List<OrderSummary> getOrderSummaryList(boolean loadFiexdOrder);

  /**
   * 売上確定を行う必要がある出荷ヘッダの一覧を取得します。<br>
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>売上未確定かつ出荷済みの出荷ヘッダを全て取得します。
   * <ol>
   * <li>売上確定フラグが未確定、出荷ステータスが出荷済の出荷データを取得検索して、出荷ヘッダのリストを返します。</li></dd>
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
   * <dd>特にありません</dd>
   * </dl>
   * </p>
   * 
   * @return 出荷情報の一覧
   */
  List<ShippingHeader> getShippingHeaderListForSalsesFix();

  /**
   * 検索条件に該当する受注金額を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件に該当する受注金額を取得します。
   * <ol>
   * <li>引数で受取った検索条件を用いて、OrderSumRetailPriceSearchQueryから受注金額の合計値を取得するクエリを生成します。
   * </li>
   * <li>1で生成したクエリを実行し、該当する受注金額の合計値を取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionはnullでないものとします。</dd>
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
   * @return 検索条件に該当する受注金額
   * @see jp.co.sint.webshop.service.order.OrderSumRetailPriceSearchQuery
   */
  Long getSalesAmount(OrderListSearchCondition condition);

  /**
   * 検索条件に該当する出荷件数を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件に該当する出荷件数を取得します。
   * <ol>
   * <li>引数で受取った検索条件を用いて、ShippingCountSearchQueryから出荷件数を取得するクエリを生成します。</li>
   * <li>1で生成したクエリを実行し、該当する出荷件数を取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取った検索条件はnullでないものとします。</dd>
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
   * @return 出荷件数
   * @see jp.co.sint.webshop.service.order.ShippingCountSearchQuery
   */
  Long getShippingCount(ShippingListSearchCondition condition);

  /**
   * 出荷ヘッダ情報と出荷明細情報(全件)を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷ヘッダ情報と出荷明細情報(全件)を取得します。
   * <ol>
   * <li>出荷コンテナ(ShippingContainer)を新しく生成します。</li>
   * <li>引数で受取った出荷番号から、出荷ヘッダ情報を取得し、1で生成した出荷コンテナにセットします。</li>
   * <li>引数で受取った出荷番号から、出荷明細情報のリストを取得し、1で生成した出荷コンテナにセットし返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>出荷番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shippingNo
   *          出荷番号
   * @return 出荷コンテナ(ShippingContainer)
   */
  ShippingContainer getShipping(String shippingNo);

  ShippingContainer getJdShipping(String shippingNo);
  
  /**
   * 出荷ヘッダ情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷ヘッダ情報を取得します。
   * <ol>
   * <li>引数で受取った出荷番号を用いて、出荷ヘッダテーブルから出荷情報を取得し返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>出荷番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shippingNo
   *          出荷番号
   * @return 出荷ヘッダ情報
   */
  ShippingHeader getShippingHeader(String shippingNo);

  /**
   * 出荷コンテナ(ShippingContainer)のリストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷コンテナ(ShippingContainer)のリストを返します。
   * <ol>
   * <li>出荷コンテナのリストを新しく生成します。</li>
   * <li>引数で受取った受注番号を元に、出荷ヘッダ情報のリストを出荷番号の降順で取得します。</li>
   * <li>2で取得した出荷ヘッダ情報ごとに、出荷コンテナを新しく生成します。</li>
   * <li>3で生成した出荷コンテナに、2で取得した出荷ヘッダ情報をセットします。</li>
   * <li>3で生成した出荷コンテナに、2で取得した出荷ヘッダの出荷番号より取得した出荷明細のリストをセットします。</li>
   * <li>5で値を設定した出荷コンテナを、1で生成した出荷コンテナのリストに加えます。</li>
   * <li>2～6の処理を全出荷情報分終了したら、出荷コンテナのリストを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取った受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @return 出荷コンテナのリスト(出荷番号の降順)
   */
  List<ShippingContainer> getShippingList(String orderNo);

  /**
   * 受注番号、出荷番号をキーに、受注ヘッダ、受注明細(全件)、出荷ヘッダ、出荷明細(全件)を取得する。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注番号、出荷番号をキーに、受注ヘッダ、受注明細(全件)、出荷ヘッダ、出荷明細(全件)を取得する。
   * <ol>
   * <li>引数で受取った受注番号、出荷番号に関連付いている全情報を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
   * <dd>出荷番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @param shippingNo
   *          出荷番号
   * @return 受注コンテナ
   */
  OrderContainer getOrder(String orderNo, String shippingNo);

  OrderContainer getJdOrder(String orderNo, String shippingNo);
  
  /**
   * 返品情報を生成します。<BR>
   * 受注情報の更新と、出荷ヘッダ・出荷明細の新規登録を行う。<BR>
   * ただし、出荷明細のSKUコードが設定されていない場合、商品無し返品・金額調整とみなし<BR>
   * 出荷明細情報の登録は行わない<BR>
   * 返品＝「返品」区分の出荷ヘッダ(＋出荷明細)をinsertする。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>返品情報を生成します。
   * <ol>
   * <li>引数で受取った受注ヘッダ情報の受注番号を用いて、受注ヘッダテーブルから更新対象の受注ヘッダ情報を取得します。</li>
   * <li>1で取得した受注ヘッダ情報に、引数で受取った受注ヘッダ情報の受注番号、連絡事項、注意事項（管理側のみ参照）<br />
   * 更新日付をセットします。</li>
   * <li>引数で受取った出荷ヘッダ情報に、DatabaseUtilを用いて取得した出荷番号のシーケンス番号をセットします。</li>
   * <li>3で値をセットした出荷ヘッダ情報の返品日にシステム日付をセットします。</li>
   * <li>4で値をセットした出荷ヘッダ情報に、DatabaseUtilを用いてユーザステータスをセットします。</li>
   * <li>引数で受取った出荷明細情報に、3で取得したシーケンス番号をセットします。</li>
   * <li>6で値をセットした出荷明細情報に、DatabaseUtilを用いてユーザーステータスをセットします。</li>
   * <li>2で値をセットした受注ヘッダ情報、5で値をセットした出荷ヘッダ情報に対してValidationチェックを行います。</li>
   * <li>TransactionManagerを用いて、受注ヘッダ情報を更新します。</li>
   * <li>TransactionManagerを用いて、出荷ヘッダ情報を登録します。</li>
   * <li>出荷明細情報のskuコードが値を持っている場合のみ、出荷明細情報に対してValidationチェックを行い<br />
   * TransactionManagerを用いて、出荷明細情報を登録します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
   * <dd>引数で受取った受注番号の受注データが受注テーブルに存在するものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注ヘッダテーブルの連絡事項、注意事項（管理側のみ参照）が引数で受取った受注ヘッダ情報の物に更新されます。</dd>
   * <dd>出荷ヘッダテーブルに返品情報が登録されます。</dd>
   * <dd>引数で受取った出荷明細情報にskuコードが存在した場合、出荷明細テーブルに出荷明細情報が登録されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderHeader
   *          受注ヘッダ
   * @param shippingHeader
   *          出荷ヘッダ
   * @param shippingDetail
   *          出荷明細
   * @return サービス実行結果
   */
  ServiceResult insertReturnOrder(OrderHeader orderHeader, ShippingHeader shippingHeader, ShippingDetail shippingDetail);

  // soukai add 2011/12/29 ob start
  // TMALL受注情報の更新
  ServiceResult updateTmallOrderWithoutPayment(OrderContainer order);

  ServiceResult updateJdOrderWithoutPayment(OrderContainer order);
  
  TmallOrderHeader getTmallOrderHeader(String orderNo);
  
  JdOrderHeader getJdOrderHeader(String orderNo);

  OrderContainer getTmallOrder(String orderNo);
  
  OrderContainer getJdOrder(String orderNo);

  // ＴＭＡＬＬ出荷ヘッダ情報を取得する
  TmallShippingHeader getTmallShippingHeader(String shippingNo);
  
  JdShippingHeader getJdShippingHeader(String shippingNo);
  
  List<ShippingContainer> getTmallShippingList(String Tid);

  List<ShippingContainer> getJdShippingList(String Tid);
  // soukai add 2011/12/29 ob end

  // soukai add 2012/01/29 yyq start
  OrderContainer getTmallOrder(String orderNo, String shippingNo);

  // soukai add 2012/01/29 yyq end

  /**
   * 出荷実績を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷実績を登録します。
   * <ol>
   * <li>引数で受取った出荷指示情報の出荷番号を用いて、出荷ヘッダテーブルから更新対象の出荷ヘッダ情報を取得します。</li>
   * <li>1で取得した出荷ヘッダ情報がnullだった場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った出荷指示情報の受注番号を用いて、受注ヘッダテーブルから更新対象の受注ヘッダ情報を取得します。</li>
   * <li>3で取得した受注ヘッダ情報がnullだった場合、該当データ未存在エラーを返します。</li>
   * <li>1で取得した出荷ヘッダ情報に、引数で受取った出荷指示情報の到着予定日、到着時間開始、到着時間終了、<br />
   * 宅配便伝票番号、受注番号、出荷日、出荷指示日、出荷番号、更新者、更新日をセットします。</li>
   * <li>5で値をセットした出荷ヘッダ情報の出荷ステータスに出荷済を設定します。</li>
   * <li>6で値をセットした出荷ヘッダ情報の出荷指示日がnullだった場合、出荷日をセットします。</li>
   * <li>7で値をセットした出荷ヘッダ情報の出荷日が受注日より前の場合、出荷日が出荷指示日より前の場合、<br />
   * 3で取得した出荷ヘッダ情報が確定済だった場合は、入力値妥当性エラーを返します。</li>
   * <li>3で取得した受注ヘッダ情報が先払いだった時、7で値をセットした出荷ヘッダ情報の出荷ステータスが<br />
   * 出荷可能、出荷手配中、出荷済のいずれでもない場合は入力値妥当性エラーを返します。</li>
   * <li>7で値をセットした出荷ヘッダ情報に対してValidationチェックを行います。</li>
   * <li>7で値をセットした出荷ヘッダ情報を元に、在庫の出荷処理を行います。</li>
   * <li>7で値をセットした出荷ヘッダ情報をDAOを用いて出荷ヘッダテーブルに更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>inputShippingReportがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>出荷実績が登録されます。<br />
   * 出荷した商品の在庫の出荷処理が実行されます。</dd>
   * </dl>
   * </p>
   * 
   * @param inputShippingReport
   *          出荷指示
   * @return サービス実行結果<br />
   *         更新対象の出荷情報が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象の出荷情報に紐付く受注が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象が予約の場合、出荷データ予約エラーを返します。<br />
   *         更新対象の出荷日が受注日より以前の日付の場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷日が出荷指示日より以前の日付の場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷情報が売上確定済みの場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷情報が先払いかつ出荷ステータスが「出荷可能」「出荷手配中」「出荷済み」以外の場合<br />
   *         入力値妥当性エラーを返します。<br />
   * @see jp.co.sint.webshop.data.StockManager
   */
  ServiceResult registerShippingReport(InputShippingReport inputShippingReport);

  /**
   * 出荷指示日を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷状況を出荷手配中にします。
   * <ol>
   * <li>引数で受取った出荷指示情報の出荷番号を用いて、出荷ヘッダテーブルから更新対象の出荷ヘッダ情報を取得します。</li>
   * <li>1で取得した出荷指示情報がnullだった場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った出荷指示情報の受注番号を用いて、受注ヘッダテーブルから更新対象の受注ヘッダ情報を取得します。</li>
   * <li>3で取得した受注ヘッダ情報がnullだった場合、該当データ未存在エラーを返します。</li>
   * <li>1で取得した出荷ヘッダ情報に、引数で受取った出荷指示情報、出荷番号、更新者、更新日をセットします。</li>
   * <li>5で値をセットした出荷ヘッダ情報の出荷ステータスに出荷手配中を設定します。</li>
   * <li>6で値をセットした出荷ヘッダ情報の出荷指示日が空の場合、<br />
   * 3で取得した出荷ヘッダ情報が確定済だった場合は、入力値妥当性エラーを返します。</li>
   * <li>6で値をセットした出荷ヘッダ情報の出荷ステータスが出荷可能でない場合は入力値妥当性エラーを返します。</li>
   * <li>6で値をセットした出荷ヘッダ情報に対してValidationチェックを行います。</li>
   * <li>6で値をセットした出荷ヘッダ情報をDAOを用いて出荷ヘッダテーブルに更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>inputShippingReportがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>出荷実績が登録されます。<br />
   * 出荷した商品の在庫の出荷処理が実行されます。</dd>
   * </dl>
   * </p>
   * 
   * @param inputShippingReport
   *          出荷指示情報
   * @return サービス実行結果<br />
   *         更新対象の出荷情報が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象の出荷情報に紐付く受注が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象がデータ連携済みの場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷指示日が受注日より以前の日付の場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷情報が売上確定済みの場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷情報の出荷ステータスが「出荷可能」以外の場合、入力値妥当性エラーを返します。<br />
   */
  ServiceResult registerShippingDirection(InputShippingReport inputShippingReport);

  /**
   * 出荷指示日を登録します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>出荷指示日を登録します。
   * <ol>
   * <li>引数で受取った出荷指示情報の出荷番号を用いて、出荷ヘッダテーブルから更新対象の出荷ヘッダ情報を取得します。</li>
   * <li>1で取得した出荷ヘッダ情報がnullだった場合、該当データ未存在エラーを返します。</li>
   * <li>引数で受取った出荷指示情報の受注番号を用いて、受注ヘッダテーブルから更新対象の受注ヘッダ情報を取得します。</li>
   * <li>3で取得した受注ヘッダ情報がnullだった場合、該当データ未存在エラーを返します。</li>
   * <li>1で取得した出荷ヘッダ情報に、引数で受取った出荷指示情報、出荷指示日、出荷番号、更新者、更新日をセットします。</li>
   * <li>出荷指示有無が出荷指示ありの場合、5で値をセットした出荷ヘッダ情報の出荷ステータスに出荷手配中を設定します。</li>
   * <li>6で値をセットした出荷ヘッダ情報の出荷指示日が受注日より前の場合、<br />
   * 3で取得した出荷ヘッダ情報が確定済だった場合は、入力値妥当性エラーを返します。</li>
   * <li>6で値をセットした出荷ヘッダ情報の出荷ステータスが出荷可能でない場合は入力値妥当性エラーを返します。</li>
   * <li>6で値をセットした出荷ヘッダ情報に対してValidationチェックを行います。</li>
   * <li>6で値をセットした出荷ヘッダ情報をDAOを用いて出荷ヘッダテーブルに更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>inputShippingReportがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>出荷実績が登録されます。<br />
   * 出荷した商品の在庫の出荷処理が実行されます。</dd>
   * </dl>
   * </p>
   * 
   * @param inputShippingReport
   *          出荷指示日情報
   * @param updateStatus
   *          出荷指示有無
   * @return サービス実行結果<br />
   *         更新対象の出荷情報が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象の出荷情報に紐付く受注が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象がデータ連携済みの場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷指示日が受注日より以前の日付の場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷情報が売上確定済みの場合、入力値妥当性エラーを返します。<br />
   *         更新対象の出荷情報の出荷ステータスが「出荷可能」以外の場合、入力値妥当性エラーを返します。<br />
   */
  ServiceResult registerShippingDirectionDate(InputShippingReport inputShippingReport, boolean updateStatus);

  /**
   * 検索条件に該当する受注ヘッドラインのリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件に該当する受注ヘッドラインのリストを取得します。
   * <ol>
   * <li>引数で受取った検索条件を用いて、OrderListSearchQueryから受注ヘッドラインを取得するクエリを生成します。</li>
   * <li>1で生成したクエリを実行し、該当する受注ヘッドラインを取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>引数で受取った検索条件はnullでないものとします。</dd>
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
   * @return 受注ヘッドラインのリスト
   * @see jp.co.sint.webshop.service.order.OrderListSearchQuery
   */
  SearchResult<OrderHeadline> searchOrderList(OrderListSearchCondition condition);

  /**
   * ShippingContainer(出荷ヘッダのデータセット)のリストに対して、 獲得ポイントを設定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <ol>
   * <li>モールのポイントルールを取得します。</li>
   * <li>モールのポイントルールの設定が、ポイントシステム使用しないの場合、獲得ポイントを設定しません。</li>
   * <li>支払金額が、ポイント付与最低金額を下回る場合、獲得ポイントを設定しません。</li>
   * <li>ポイントルールの付与率を取得します。ボーナス期間の場合は、ボーナス付与率を取得します。</li>
   * <li>使用ポイント比率を算出します。(注文の合計金額-使用ポイント)/(注文の合計金額-使用ポイント)が比率となります。</li>
   * <li>商品別にポイントルールの付与ポイントを算出します。商品別ポイント付与率が設定されている商品は、商品別付与率を優先し、設定されていない場合は、
   * ポイントルールの付与率を適用させます。</li>
   * <li>商品別に会員別ポイントの付与ポイントを算出します。</li>
   * <li>商品別の付与ポイント(ポイントルール分＋顧客グループ分)に、使用ポイント比率を掛けます。</li>
   * <li>明細ごと(商品別×数量)に算出した付与ポイントを出荷単位に集計します。</li>
   * <li>集計したポイントをOrderContainerに設定します。</li> </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>OrderContainer のgetOrderDetails()とgetShippingDetails()に受注情報が設定されていること。</dd>
   * <dd>ポイントルールのデータが1件存在すること</dd>
   * <dd>受注日が空の場合、当日日付を受注日として作成</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>OrderContainer内の獲得ポイントが設定されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   *          受注情報
   * @throws RuntimeException
   *           - ポイントルールが取得できなかった場合
   */
  void setAcquiredPoint(OrderContainer orderContainer);

  /**
   * 宅配便伝票番号を設定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で指定された出荷データに宅配便伝票番号を設定します。
   * <ol>
   * <li>引数で受け取った出荷番号と宅配便伝票番号を出荷ヘッダDTOにセットし、DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>1で値をセットした出荷ヘッダ情報の出荷番号と宅配便伝票番号に関してValidationチェックをします。</li>
   * <li>引数で指定した出荷番号を持つ出荷ヘッダの宅配便伝票番号項目を、引数で受け取った宅配便伝票番号で更新します。</li>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shippingNoは、nullでないものとします。</dd>
   * <dd>deliverySlipNoは、nullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>出荷ヘッダの宅配便伝票番号が更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param shippingNo
   * @param deliverySlipNo
   * @return サービス実行結果
   */
  ServiceResult setDeliverySlipNo(String shippingNo, String deliverySlipNo);

  /**
   * 入金日を設定します。<BR>
   * 日付のため、時間はカットされます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数に指定した受注情報に対して、引数で指定した入金日を設定します。
   * <ol>
   * <li>引数で受け取った受注情報の受注番号と入金日を受注ヘッダDTOにセットし、DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>1で値をセットした受注情報の受注番号と入金日に関してValidationチェックをします。</li>
   * <li>1で値をセットした受注情報の受注番号をキーとして、最新の受注情報を取得して受注ヘッダDTOにセットします。</li>
   * <li>3で取得した最新の受注情報が存在しない場合は、データ存在エラーを返します。</li>
   * <li>3で取得した最新の受注情報の受注番号をキーとして、該当受注が持つ出荷情報の一覧を取得します。</li>
   * <li>3で取得した最新の受注情報の受注ステータスが【キャンセル】の場合は、キャンセル済みエラーを返します。</li>
   * <li>3で取得した最新の受注情報の入金ステータスに【入金済み】を設定します。</li>
   * <li>3で取得した最新の受注情報の入金日に、引数で受け取った入金日を設定します。</li>
   * <li>3で取得した最新の受注情報の更新日時に、引数で受け取った受注情報の更新日時を設定します。</li>
   * <li>3で取得した最新の受注情報に、DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>3で取得した受注ヘッダDTOで、受注情報を更新します。</li>
   * <li>3で取得した最新の受注情報の先後払いフラグが【先払い】、かつ5で取得した出荷情報の出荷ステータスが【入金待ち】の出荷に対して、<BR>
   * 5で取得した出荷情報の出荷ステータスを【出荷可能】で更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注識別情報はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で受け取った受注情報の入金日が更新されます。</dd>
   * <dd>入金日が既に設定されている場合、入金ステータスは【入金済み】のまま入金日が上書きされます。</dd>
   * <dd>引数で受け取った受注情報が持つ出荷情報の出荷ステータスが更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderIdentifier
   *          受注識別情報
   * @param paymentDate
   *          入金日
   * @return サービス実行結果<br />
   *         更新対象の受注情報が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象の受注情報がキャンセル済みの場合、受注キャンセルエラーを返します。<br />
   */
  ServiceResult setPaymentDate(OrderIdentifier orderIdentifier, Date paymentDate);

  /**
   * 入金日を設定します。<BR>
   * 日付のため、時間はカットされます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数に指定した受注情報に対して、引数で指定した入金日を設定します。
   * <ol>
   * <li>引数で受け取った受注情報の受注番号と入金日を受注ヘッダDTOにセットし、DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>1で値をセットした受注情報の受注番号と入金日に関してValidationチェックをします。</li>
   * <li>1で値をセットした受注情報の受注番号をキーとして、最新の受注情報を取得して受注ヘッダDTOにセットします。</li>
   * <li>3で取得した最新の受注情報が存在しない場合は、データ存在エラーを返します。</li>
   * <li>3で取得した最新の受注情報の受注番号をキーとして、該当受注が持つ出荷情報の一覧を取得します。</li>
   * <li>3で取得した最新の受注情報の受注ステータスが【キャンセル】の場合は、キャンセル済みエラーを返します。</li>
   * <li>3で取得した最新の受注情報の入金ステータスに【入金済み】を設定します。</li>
   * <li>3で取得した最新の受注情報の入金日に、引数で受け取った入金日を設定します。</li>
   * <li>3で取得した最新の受注情報に、DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>3で取得した受注ヘッダDTOで、受注情報を更新します。</li>
   * <li>3で取得した最新の受注情報の先後払いフラグが【先払い】、かつ5で取得した出荷情報の出荷ステータスが【入金待ち】の出荷に対して、<BR>
   * 5で取得した出荷情報の出荷ステータスを【出荷可能】で更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注識別情報はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で受け取った受注情報の入金日が更新されます。</dd>
   * <dd>入金日が既に設定されている場合、入金ステータスは【入金済み】のまま入金日が上書きされます。</dd>
   * <dd>引数で受け取った受注情報が持つ出荷情報の出荷ステータスが更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @param paymentDate
   *          入金日
   * @return サービス実行結果<br />
   *         更新対象の受注情報が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象の受注情報がキャンセル済みの場合、受注キャンセルエラーを返します。<br />
   * @see #setPaymentDate(OrderIdentifier orderIdentifier, Date paymentDate)
   */
  ServiceResult setPaymentDate(String orderNo, Date paymentDate);

  /**
   * 入金日を設定します。<BR>
   * 日付のため、時間はカットされます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数に指定した受注情報に対して、引数で指定した入金日を設定します。
   * <ol>
   * <li>引数で受け取った受注情報の受注番号と入金日を受注ヘッダDTOにセットし、DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>1で値をセットした受注情報の受注番号と入金日に関してValidationチェックをします。</li>
   * <li>1で値をセットした受注情報の受注番号をキーとして、最新の受注情報を取得して受注ヘッダDTOにセットします。</li>
   * <li>3で取得した最新の受注情報が存在しない場合は、データ存在エラーを返します。</li>
   * <li>3で取得した最新の受注情報の受注番号をキーとして、該当受注が持つ出荷情報の一覧を取得します。</li>
   * <li>3で取得した最新の受注情報の受注ステータスが【キャンセル】の場合は、キャンセル済みエラーを返します。</li>
   * <li>3で取得した最新の受注情報の入金ステータスに【入金済み】を設定します。</li>
   * <li>3で取得した最新の受注情報の入金日に、引数で受け取った入金日を設定します。</li>
   * <li>3で取得した最新の受注情報に、DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>3で取得した受注ヘッダDTOで、受注情報を更新します。</li>
   * <li>3で取得した最新の受注情報の先後払いフラグが【先払い】、かつ5で取得した出荷情報の出荷ステータスが【入金待ち】の出荷に対して、<BR>
   * 5で取得した出荷情報の出荷ステータスを【出荷可能】で更新します。</li>
   * <li>引数で受け取った売上処理情報をもとに、決済プロバイダ売上送信処理を行ないます。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注識別情報はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で受け取った受注情報の入金日が更新されます。</dd>
   * <dd>入金日が既に設定されている場合、入金ステータスは【入金済み】のまま入金日が上書きされます。</dd>
   * <dd>引数で受け取った受注情報が持つ出荷情報の出荷ステータスが更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @param paymentDate
   *          入金日
   * @return サービス実行結果<br />
   *         更新対象の受注情報が存在しない場合、該当データ未存在エラーを返します。<br />
   *         更新対象の受注情報がキャンセル済みの場合、受注キャンセルエラーを返します。<br />
   *         売上送信処理に失敗した場合、決済失敗エラーを返します。<br />
   * @see #setPaymentDate(OrderIdentifier orderIdentifier, Date paymentDate)
   */
  ServiceResult setPaymentDate(String orderNo, Date paymentDate, BookSalesContainer bookSalesContainer);

  /**
   * 検索条件に該当する出荷リストを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>検索条件に該当する出荷リストを返します。
   * <ol>
   * <li>引数で受取ったconditionを元に検索条件に該当する出荷情報のリストを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>conditionはnullでないものとします。</dd>
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
   * @return searchResult
   */
  SearchResult<ShippingList> searchShippingList(ShippingListSearchCondition condition);

  /**
   * 決済プロバイダへ売上の送信を行います。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>売上送信対象データを取得します。</li>
   * <li>取得した送信用データを決済プロバイダへ1件ずつ送信します。</li>
   * <li>エラーがなければ受注情報を入金済みに更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>periodはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注ヘッダテーブルの入金日と入金ステータスを更新します。</dd>
   * </dl>
   * </p>
   * 
   * @param period
   *          対象期間を表すDateRangeオブジェクト
   * @return サービス実行結果<br />
   *         決済プロバイダへ売上の送信に失敗した場合、決済失敗エラーを返します。
   * @see #setPaymentDate(OrderIdentifier, Date)
   */
  ServiceResult transportPayment(DateRange period);

  /**
   * 受注情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注情報を更新します。
   * <ol>
   * <li>受注ヘッダ取得＋必要情報コピー</li>
   * <li>付与ポイントを計算し生成します。その際、新規受注時の受注日を元にポイント計算が行われます。</li>
   * <li>Validationチェック<BR>
   * Validationエラーが発生した場合、ServiceResultにCommonServiceErrorContent.
   * VALIDATION_ERRORを設定して、以下処理を中断します。</li>
   * <li>データ整合性チェック <BR>
   * <ol>
   * <li>存在しないショップのショップコードが受注及び出荷情報に設定された場合<BR>
   * CommonServiceErrorContent.INPUT_ADEQUATE_ERRORをServiceResultに設定して以下処理を中断します
   * 。<BR>
   * </li>
   * <li>使用ポイントの合計値から修正前の使用ポイント合計を引いたものが、顧客の使用可能ポイントより多い場合<BR>
   * OrderServiceErrorContent.USEABLE_POINT_ERRORをServiceResultに設定して以下処理を中断します。</li>
   * </ol>
   * </li> <BR>
   * begin transaction-- トランザクションここから
   * <li>update: ポイント履歴(修正前使用ポイントを無効化)</li>
   * <li>insert: ポイント履歴(修正後使用ポイント)</li>
   * <li>update: 在庫引当＋可非チェック<BR>
   * 在庫引当エラー時はOrderServiceErrorContent.
   * STOCK_ALLOCATE_ERRORをServiceResultに設定してTransactionをRollbackします。</li>
   * <li>与信処理＋与信関連情報設定</li>
   * <li>Validationチェック(与信関連情報追加用)</li>
   * <li>insert: 受注ヘッダ</li>
   * <li>delete: 受注明細(ソートはショップコード、SKUコードの順に昇順)</li>
   * <li>delete: 出荷ヘッダ(ソートは出荷番号の昇順)</li>
   * <li>delete: 出荷明細(ソートは出荷番号、SKUコードの順に昇順)</li>
   * <li>insert: 受注明細</li>
   * <li>insert: 出荷ヘッダ</li>
   * <li>insert: 出荷明細</li>
   * <li>update: ポイント履歴</li>
   * <li>insert: ポイント履歴</li>
   * <li>update: 顧客テーブル(ポイント残高更新)</li> <BR>
   * commit transaction -- トランザクションここまで
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>OrderContainer及びOrderContainerの保持する情報がnullもしくはサイズが0でないこと。<BR>
   * 但し、下記情報は本メソッド内にて設定される為、nullもしくは空も可能とします。<BR>
   * <ol>
   * <li>与信情報:決済サービス取引ID・認証番号・払込URLは与信時に取得する情報の為与信情報として、与信終了後に設定ます。</li>
   * <li>顧客コード/グループコード:顧客が変更となる受注変更は認めない為、修正前情報を取得して設定します。</li>
   * <li>付与ポイント:購入金額を元に内部で計算し設定されます。</li>
   * <li>受注日:初回受注情報を保持する為、修正前受注情報を元に内部で設定します。</li>
   * <li>受注ステータス：初回受注情報を保持する為、修正前受注情報を元に内部で設定します。</li> <BR>
   * 上記条件が満たされない場合、RuntimeExeption及びそのサブExeptionが発生する可能性があります。<BR>
   * <BR>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>修正受注情報で指定された受注情報が更新されます。<BR>
   * 1.OrderHeader-受注番号・顧客コード・顧客グループコード・受注日以外の情報が修正受注情報で指定された情報に設定され登録されます。<BR>
   * 2.OrderDetail-旧受注に関する情報が全て削除され、新たに修正受注情報で指定された受注明細情報が登録されます。<BR>
   * 3.ShippingHeader-旧受注に関する情報が全て削除され、新たに修正受注情報で指定された出荷ヘッダ情報が登録されます。<BR>
   * 4.ShippingDetail-旧受注に関する情報が全て削除され、新たに修正受注情報で指定された出荷明細情報が登録されます。<BR>
   * 5.Stock-該当商品に関連付いた在庫テーブルの情報が、修正受注情報で指定された数量分修正され更新されます。<br />
   * 6.Point-ポイント履歴テーブルの、該当受注に関連付く情報が全て削除され、修正受注情報で指定された使用ポイント情報が登録されます。<br />
   * また、受注修正後の獲得ポイントがポイント履歴テーブルに登録され、顧客テーブルのポイント残高が更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param order
   *          修正受注情報
   * @return サービス実行結果
   */
  ServiceResult updateOrder(OrderContainer order, CashierPayment cashierPayment);

  /**
   * 支払に関する情報以外の受注情報を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>支払に関する情報以外の受注情報を更新します。
   * <ol>
   * <li>引数で受け取った受注情報の受注番号から、最新の受注情報を取得して受注ヘッダDTOにセットします。</li>
   * <li>最新の受注情報が存在しない場合は、データ未存在エラーを返します。</li>
   * <li>引数で受け取った受注情報を受注ヘッダDTOにセットします。</li>
   * <li>1で作成した最新の受注ヘッダDTOに対して、3で作成した受注ヘッダDTOの中から支払金額に関わらない情報を設定します。</li>
   * <li>3で作成した受注ヘッダDTOに対して、Validationチェックを行います。</li>
   * <li>引数で受け取った受注情報の中にある出荷ヘッダ情報を取得して7から11の処理を繰り返します。</li>
   * <li>出荷ヘッダ情報を1件取得して、出荷ヘッダDTOにセットします。</li>
   * <li>7で作成した出荷ヘッダの出荷番号をキーとして出荷ヘッダを検索し、存在すれば最新の出荷ヘッダDTOを作成し、<BR>
   * 存在しなければデータ未存在エラーを返します。</li>
   * <li>8で作成した最新の出荷ヘッダDTOの売上確定ステータスが【確定済み】の場合は、売上確定エラーを返します。</li>
   * <li>8で作成した最新の出荷ヘッダDTOに対して、7で作成した出荷ヘッダDTOの中から金額に関わらない情報を設定します。</li>
   * <li>8で作成した最新の出荷ヘッダDTOに対して、Validationチェックを行います。</li>
   * <li>引数で受け取った受注情報の値をセットした最新の受注ヘッダDTOで、受注情報を更新します。</li>
   * <li>引数で受け取った受注情報の値をセットした最新の出荷ヘッダDTOで、出荷情報を更新します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>orderが、nullがないこととします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注ヘッダのうち、支払金額に関わらない項目が更新されます。</dd>
   * <dd>出荷ヘッダのうち、支払金額に関わらない項目が更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param order
   *          変更受注情報
   * @return サービス実行結果
   */
  ServiceResult updateOrderWithoutPayment(OrderContainer order);

  /**
   * 受注データの連絡事項、注意事項（管理側のみ参照）を更新します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注データの連絡事項、注意事項（管理側のみ参照）を更新します。
   * <ol>
   * <li>引数で受け取った受注番号をキーとして受注ヘッダを検索して、受注ヘッダDTOにセットします。</li>
   * <li>1で作成した受注ヘッダDTOがnullの場合は、データ未存在エラーを返します。</li>
   * <li>1で作成した受注ヘッダDTOに、引数で受け取った連絡事項と注意事項（管理側のみ参照）をセットし、
   * DatabaseUtilを用いてユーザ情報をセットします。</li>
   * <li>1で作成した受注ヘッダDTOのデータ連携ステータスが【連携済み】の場合は、データ連携済みエラーを返します。</li>
   * <li>1で作成した受注ヘッダDTOに対して、Validationチェックを行います。</li>
   * <li>1で作成した受注ヘッダDTOで、受注データを更新します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>orderNoがnullでないこととします。</dd>
   * <dd>updateDatetimeがnullでないこととします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>受注ヘッダの連絡事項が更新されます。</dd>
   * <dd>受注ヘッダの注意事項（管理側のみ参照）が更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号
   * @param message
   *          更新する連絡事項の内容
   * @param caution
   *          更新する注意事項（管理側のみ参照）の内容
   * @param updateDatetime
   *          更新日付
   * @return サービス実行結果
   */
  ServiceResult updateOrderRemark(String orderNo, String message, String caution, Date updateDatetime);

  // TMALL受注情報の更新
  ServiceResult updateTmallOrderRemark(String orderNo, String message, String caution, Date updateDatetime);

  // JD受注情報の更新
  ServiceResult updateJdOrderRemark(String orderNo, String message, String caution, Date updateDatetime);

  /**
   * 予約情報のステータスを更新して、受注情報に変換します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>予約情報のステータスを更新して、受注情報に変換します。
   * <ol>
   * <li>引数で受け取った受注番号をキーとして、最新の受注情報を生成します。</li>
   * <li>1で生成した受注情報の受注明細情報が空の場合は、データ未存在エラーを返します。</li>
   * <li>1で生成した受注情報の受注明細情報を取得し、受注明細DTOにセットします。</li>
   * <li>3で生成した受注明細DTOに存在する商品情報を元に、予約数の減算と引当数の加算を行います。</li>
   * <li>3で生成した受注明細DTOの受注番号を持つ受注ヘッダの受注ステータスを【受注】に変更します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>orderNoはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>在庫の予約数と引当数が更新されます。</dd>
   * <dd>受注ヘッダの受注ステータスが更新されます。</dd>
   * <dd>予約されていた商品の在庫情報が更新されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderNo
   *          受注番号(予約番号)
   * @return サービス処理結果
   */
  ServiceResult changeReservationToOrder(String orderNo);

  /**
   * SKUを指定して、予約として登録されている受注明細のリストを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>SKUを指定して、予約として登録されている受注明細のリストを取得します。
   * <ol>
   * <li>引数で受け取ったショップコードと商品コードを持ち、受注ステータスが【予約】の受注データを取得します。</li>
   * <li>取得した受注で明細の一覧は、受注番号の昇順に返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeはnullでないものとします。</dd>
   * <dd>skuCodeはnullでないものとします。</dd>
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
   *          予約商品のショップコード
   * @param skuCode
   *          予約商品のSKUコード
   * @return 受注明細情報の一覧を返します。
   */
  List<OrderDetail> getResearvationOrderDetail(String shopCode, String skuCode);

  /**
   * 受注番号のリストから、支払情報のサマリを返します。支払情報には入金確認メール、入金督促メールの送信状態が含まれます。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>受注番号のリストから、支払情報のサマリを返します。
   * <ol>
   * <li>支払情報には入金確認メールの送信状態が含まれます。</li>
   * <li>支払情報には入金督促メールの送信状態が含まれます。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>orderNoListが長さ1以上のListであること。orderNoListがnull, 空の場合は空のListが返されます。<br>
   * また、存在しない受注番号に対応するOrderPaymentInfoは返されません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>なし</dd>
   * </dl>
   * </p>
   * 
   * @param orderNoList
   *          受注番号のリスト
   * @return 支払情報のMap
   */
  Map<String, OrderPaymentInfo> getOrderPaymentInfo(List<String> orderNoList);

  /**
   * 送信済の出荷報告メールの件数を返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>送信済の出荷報告メールの件数を返します。
   * <ol>
   * <li>出荷番号を元に、受注メール送信履歴を検索し、件数を返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>出荷番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shippingNo
   * @return 送信された出荷報告メールの件数
   */
  Long getShippingMailCount(String shippingNo);

  // v10-ch-pg add start
  ServiceResult settlePhantomOrder(OrderHeader header);

  ServiceResult deletePhantomOrders(Long expireHours);

  List<CustomerCouponInfo> getPaymentCouponList(String customerCode);

  List<CustomerCoupon> getOrderUsedCoupon(String orderNo);

  List<CustomerCoupon> getOrderModifyEnableCoupon(String customerCode, String orderNo);

  // v10-ch-pg add end

  //
  /**
   * 获取银联支付接口参数列表
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>获取银联支付接口参数列表
   * <ol></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>订单信息存在</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   * @return 银联支付接口参数列表
   */
  PaymentChinapayResultBean getChinapayBean(OrderContainer orderContainer);

  //
  /**
   * 获取银联支付接口参数列表
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>获取银联支付接口参数列表
   * <ol></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>订单信息存在</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   * @return 银联支付接口参数列表
   */
  PaymentChinapayResultBean getWapChinapayBean(OrderContainer orderContainer);
  
  PaymentAlipayResultBean getAlipayBean(OrderContainer orderContainer);

  // 20111209 lirong add start
  /**
   * 根据退换货编号得到退换货信息
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <li>首先获得ReturnHeader</li>
   * <dd>
   * <ol>
   * <li>根据退换货编号得到退换货信息</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd></dd>
   * </dl>
   * </p>
   * 
   * @param returnNo
   *          退换货编号
   * @return 退换货信息取得
   */
  ReturnInfo getReturnInfo(String returnNo);

  // 20111209 lirong add end

  // Add by V10-CH start
  OrderSummary getPayedOrderSummary(String customerCode, String dateFrom, String dateTo);

  // Add by V10-CH end

  // 20111220 ysy add start
  List<OrderSummary> getNeedRemindSummary(String dateFrom, String dateTo);

  // 20111220 ysy add end

  List<OrderSummary> getNeedRemindSummary(String custumerCode, String dateFrom, String dateTo);

  // 20111223 shen add start
  CustomerVatInvoice getCustomerVatInvoice(String customerCode);

  // 20111223 shen add end
  // soukai add 2011/12/28 ob start
  /**
   * 获得订单发票情报
   * 
   * @param 订单code
   */
  OrderInvoice getOrderInvoice(String orderCode);

  /**
   * 获得订单发票的商品规格（订单详细的商品情报）
   * 
   * @param 订单code
   * @param 店铺code
   */
  List<OrderDetail> getOrderDetailCommodityList(String orderCode, String shopCode);

  /**
   * 获得订单发票的商品规格（订单详细的商品情报）
   * 
   * @param 订单code
   * @param 店铺code
   */
  List<TmallOrderDetail> getTmallOrderDetailCommodityList(String orderCode, String shopCode);

  OrderSummary getOrderSummary(String orderNo, String orderType);

  OrderSummary getOrderSummary2(String orderNo, String orderType);
  // soukai add 2011/12/28 ob end
  /***
   * add by os012 20111226 start.T-Mall订单下载批处理 <dt><b>处理概要: </b></dt> <dd>
   * 根据OrderHeader、List<OrderDetail> 、blExecute执行T-Mall订单下载批处理</dd>
   * <ol>
   * <li></li>
   * <li></li>
   * <li></li>
   * </ol>
   * </dl> </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>提供OrderHeader和OrderDetail</dd>
   * </dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>blExecute</dd> </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>batch调用。</dd>
   * </dl>
   * </p>
   * 
   * @return
   */
  ServiceResult TmallOrdersHandling(TmallOrderHeader orderHeader, List<TmallOrderDetail> orderDetails, TransactionManager txMgr,
      boolean blExecute);

  // 20111227 shen add start
  Long countUsedCouponOrder(String couponCode, String customerCode);

  Long countCustomerGroupCampaignOrder(String campaignCode, String customerCode);
  // 20111227 shen add end
  
  // 20121102 yyq add start
  Long countUsedCouponFirstOrder(String customerCode);
  // 20121102 yyq add end
  
  /**
   * add by os012 20120106 start 城市、省份情报查询
   */
  City getCity(String city, String district);
  
  JdCity getJdCity(String city, String district);
  
  JdCity getSpecialJdCity(String city);

  /**
   * add by os012 20120106 start 支付信息获取
   */
  PaymentMethod GetAlipayInfo(String method, Long flg);

  /**
   * add by os012 20120106 start 支付运送公司
   */
  DeliveryInfo GetDeliveryCopNo(Long cod, String RegionCode, String cityCode, String areaCode, String weight);
  
  DeliveryInfo GetJdDeliveryCopNo(Long cod, String RegionCode, String cityCode, String areaCode, String weight);

  DeliveryCompany GetDeliveryCompany();

  /**
   * add by os012 20120106 start 获取企划信息
   */
  Plan GetEcMaster(String commodityCode, Date time);

  /**
   * add by os012 20120106 start 淘宝订单下载后批处理
   * 
   * @param orderHds
   * @return
   */
  ServiceResult TmallOrdersDownLoad(List<TmallOrderHeader> orderHds);

  /**
   * add by os012 20120109 start 获取区县信息
   * 
   * @param prefectureCode
   * @param cityCode
   * @return
   */
  Area getArea(String prefectureCode, String cityCode, String district);
  
  JdArea getJdArea(String prefectureCode, String cityCode, String district);

  /**
   * add by os012 20120201 start 获取品牌信息
   * 
   * @param commodityCode
   * @return
   */
  Brand getBrand(String commodityCode);

  /**
   * * add by os012 20120109 start 获取batch时间信息
   * 
   * @return
   */
  BatchTime getBatchTime();

  /**
   * add by os012 20120109 start 操作batch时间信息
   * 
   * @param date
   * @return
   */
  ServiceResult OperateBatchTime(Date date, Long status);

  /**
   * 订单下载操作 add by os012 20120109 start
   * 
   * @param startTime
   * @param endTime
   * @param 是否执行再分配
   *          true:执行 false:不执行
   * @return ServiceResult
   */
  int OrderDownLoadCommon(String startTime, String endTime, boolean stockAllocate);
  
  
  /**
   * 订单下载订单数量验证 add by yyq 20121024 start
   * 
   * @return 1为成功 0为失败
   */
  int TmallOrderDownLoadNums(String startTime, String endTime);

  /***
   * 20111226 os013 add start. ERP发货实际批处理 <dt><b>处理概要: </b></dt> <dd></dd>
   * <ol>
   * <li></li>
   * </ol>
   * </dl> </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd></dd>
   * </dl>
   * </p>
   * <p>
   * </p>
   *20111226 os013 add end
   * 
   * @return
   */
  ServiceResult erpActualDelivery(ShippingHeader shippingHearBean, ShippingRealityDetail shippingDetailListBean);

  // 20120110 wjw add start
  ServiceResult setOrderFlg(String orderNo);

  // 20120110 wjw add end
  // soukai add 2012/01/10 ob start
  /**
   * 验证优惠信息
   * 
   * @param customerCode
   * @param discountType
   * @param discountCode
   * @param detailCode
   * @param commodityPrice
   * @param useFlg
   * @return
   */
  // 2013/04/18 优惠券对应 ob update start
//  ServiceResult checkDiscountInfo(String customerCode, String discountType, String discountCode, String detailCode,
//      BigDecimal commodityPrice, boolean useFlg);
  
  ServiceResult checkDiscountInfo(String customerCode, String discountType, String discountCode, String detailCode,
      BigDecimal commodityPrice, List<CommodityOfCartInfo> commodityListOfCart);
  // 2013/04/18 优惠券对应 ob update end
  

  BigDecimal getTotalOrderPrice(OrderListSearchCondition condition);

  // soukai add 2012/01/10 ob end

  // 20120130 ysy add start
  List<CodeAttribute> getDeliveryShipNoSum(String shippingNo);

  // 20120130 ysy add end
  // 20120202 ysy add start

  List<CodeAttribute> getTmallDeliveryShipNoSum(String shippingNo);

  List<CodeAttribute> getJdDeliveryShipNoSum(String shippingNo);
  
  String getDeliveryShipNoArray(String shippingNo);

  OrderDetail getReturnOrderDetail(String originalShippingNo, String shopCode, String skuCode);

  // 20120202 ysy add end
  // soukai add 2012/02/04 ob start
  /**
   * 支払う状態をキーに、受注ヘッダ情報を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>支払う状態をキーに、受注ヘッダ情報を取得します。
   * <ol>
   * <li>引数で受取った支払う状態を用いて、受注ヘッダテーブルから受注ヘッダ情報を取得します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>受注番号はnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param paymentStatus
   *          支払う状態
   * @return 受注ヘッダ
   */
  List<OrderHeader> getOrderHeaderByPaymentStatus();

  ServiceResult trialsetCancel(OrderIdentifier orderIdentifier);

  // soukai add 2021/02/04 ob end

  // 20120224 yyq add start 淘宝api批量发货集合
  List<TmallShippingHeader> getTmallShippingHeaderList();

  // 20120224 yyq add end 淘宝api批量发货集合
  // 20120225 OS011 add start 支付宝支付信息取得
  PaymentMethod getAlipayPayMethodInfo();

  // 20120225 OS011 add end 支付宝支付信息取得

  // 20120628 shen add start
  List<OrderHeader> getCustomerUsePaymentMethodList(String customerCode);

  // 20120628 shen add end

  // 20120827 yyq add start
  List<NewCouponRule> getCouponLimitOrderCheck(String couponCode, String mobileNumber, String addresslastName, String address);

  // 20120827 yyq add end

  // 20120831 yyq add start
  List<NewCouponRule> getCouponLimitNewOrderCheck(String couponCode, String mobileNumber, String phoneNumber, String nameAndAddress);
  // 20120831 yyq add end
  // 2012/11/28 促销活动 ob add start
  Long getCampaignDiscountUsedCount(String campaignCode,String orderNo);
  // 2012/11/28 促销活动 ob add end
  
  TmallOrderHeader getTmallOrderHeaderByTid(String tid);
  
  TmallOrderDetail getTmallOrderDetailByType(String orderNo,String skuCode);
  
  List<OrderDetail> getOrderDetailCommodityListWeight(String orderCode, String shopCode);

  CommodityDetail getCommodityDetail(String commodityCode, String shopCode);
  
  ShippingHeader getShippingStatusByOrderNo(String orderNo);
  
  ShippingDetailComposition getShippingDetailCompositionBean(String shippingNo, String skuCode);
  
  TmallShippingDetailComposition getTmallShippingDetailCompositionBean(String shippingNo, String skuCode);
  
  JdShippingDetailComposition getJdShippingDetailCompositionBean(String shippingNo, String skuCode);
  
  // 20130801 txw add start
  List<ShippingDetail> getShippingDetailList(String shippingNo);
  // 20130801 txw add end
  
  // 20140305 hdh add start
  public int compareTmallOrderStatus(String startTime, String endTime);
  // 20140305 hdh add end
//2014/06/13 库存更新对应 ob_李 add start
  public void performStockEvent(StockEvent event, StockEventType type);
//2014/06/13 库存更新对应 ob_李 add end
  
  SearchResult<MyOrder> searchMyOrderList(MyOrderListSearchCondition condition);
  
  public Long getCustomerCode(String chlidorderno);
  //虚假订单关键词get
  SearchResult<UntrueOrderWordResult> getUntrueOrderWordList(UntrueOrderWordSearchCondition condition);
  ServiceResult insertUntrueOrderWord(UntrueOrderWord uow);
  ServiceResult deleteUntrueOrderWord(String uowcode);
  public Long getCountUntrueOrderWord(String words);
  //关键词列表
  public List<String> getUntrueOrderWordList();
}
