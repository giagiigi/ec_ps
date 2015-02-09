package jp.co.sint.webshop.ext.veritrans;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.CvsEnableType;
import jp.co.sint.webshop.ext.ErrorReasonMapper;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.ext.veritrans.VeritransCvsParameter.Operation;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.order.PaymentParameter;
import jp.co.sint.webshop.service.order.PaymentProvider;
import jp.co.sint.webshop.service.order.PaymentResult;
import jp.co.sint.webshop.service.order.PaymentResultImpl;
import jp.co.sint.webshop.service.order.PaymentResultType;
import jp.co.sint.webshop.service.result.PaymentErrorContent;
import jp.co.sint.webshop.utility.IOUtil;

import org.apache.log4j.Logger;

import Jp.BuySmart.CVS.GWLib.Transaction;
import Jp.BuySmart.CVS.GWLib.TransactionFactory;

/**
 * <p>
 * ベリトランスコンビニ決済フローの各処理に対するインタフェースを提供します。
 * </p>
 * <p>
 * コンビニ決済で使用する処理は以下の2つです。
 * </p>
 * <ol>
 * <li>申込み</li>
 * <li>問合せ</li>
 * </ol>
 * 
 * @author System Integrator Corp.
 */
public class VeritransCvs implements PaymentProvider {

  /**
   * ベリトランスコンビニ決済フロー各処理のトランザクション部分を共通化して行います
   * 
   * @param oper
   *          取引コマンド
   * @param params
   * @return 実行結果
   */
  @SuppressWarnings("unchecked")
  private PaymentResultImpl doTransaction(Operation oper, PaymentParameter parameter) {
    Logger logger = Logger.getLogger(this.getClass());

    PaymentResultImpl result = new PaymentResultImpl(PaymentResultType.NONE);

    try {

      if (!IOUtil.existsResource("cvsgwlib")) {
        logger.warn(Messages.log("ext.veritrans.VeritransCvs.0"));
      }

      VeritransCvsParameter vParam = (VeritransCvsParameter) parameter;

      // requestを"cvsgwlib.properties"で初期化(デフォルトのSECRET,KEY_HA1はWSでは使用しないため削除)
      Map<String, String> request = (Map<String, String>) TransactionFactory.createRequest();
      request.remove(Transaction.REQ_SECRET);
      request.remove(Transaction.REQ_KEY_HA1);
      Map<String, String> args = vParam.getArgs(oper);
      if (!validate(request)) {
        throw new InvalidParameterException(Messages.getString("ext.veritrans.VeritransCvs.1"));
      }
      request.putAll(args);

      // Transaction(通信モジュール)の初期化
      Transaction tx = TransactionFactory.createInstance(vParam.getMerchantId(), vParam.getSecretKey());
      tx.setPendingDir(getPendingDir(tx.getPendingDir(), vParam.getShopCode()));

      // パラメータ送信
      Map<String, String> response = (Map<String, String>) tx.doTransaction(request);
      String status = response.get(Transaction.RES_MSTATUS);

      if (Transaction.RES_MSTATUS_SC.equals(status)) {
        result.setPaymentResultType(PaymentResultType.COMPLETED);
        result.setPaymentOrderId(response.get(Transaction.RES_ORDER_ID));
        result.setPaymentReceiptNo(response.get(Transaction.RES_RECEIPT_NO));
        result.setPaymentReceiptUrl(response.get(Transaction.RES_HARAIKOMI_URL));
        result.setPaymentLimitDate(vParam.getPaymentLimitDate());
        result.setCvsCode(vParam.getCvsCode().getValue());
        logger.info(response.get(Transaction.RES_AUX_MSG));
      } else if (Transaction.RES_MSTATUS_FC.equals(status) || Transaction.RES_MSTATUS_FD.equals(status)) {
        result.setPaymentResultType(PaymentResultType.TIMED_OUT);
        result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
        logger.error(response.get(Transaction.RES_MERRMSG));
      } else {
        result.setPaymentResultType(PaymentResultType.FAILED);
        ErrorReasonMapper mapper = VeritransErrorReasonMapper.getInstance(VeritransCvs.class);
        result.addPaymentErrorList(mapper.createErrorContent(response.get(Transaction.RES_ERR_CODE)));
        logger.error(response.get(Transaction.RES_MERRMSG));
      }
    } catch (RuntimeException e) {
      result.setPaymentResultType(PaymentResultType.FAILED);
      result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
      logger.error(e);
    } catch (IOException e) {
      result.setPaymentResultType(PaymentResultType.FAILED);
      result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
      logger.error(e);
    } catch (Exception e) {
      result.setPaymentResultType(PaymentResultType.FAILED);
      result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
      logger.error(e);
    }
    return result;
  }

  /**
   * 申込み処理 パラメータには以下の値をセットしてください。
   * </p>
   * <ol>
   * <li>マーチャントID</li>
   * <li>秘密鍵</li>
   * <li>取引ID</li>
   * <li>決済金額</li>
   * <li>コンビニコード</li>
   * <li>姓</li>
   * <li>名</li>
   * <li>姓(カナ)</li>
   * <li>名(カナ)</li>
   * <li>支払期限</li>
   * <li>電話番号</li>
   * </ol>
   */
  public PaymentResult entry(PaymentParameter parameter) {
    return doTransaction(Operation.ENTRY, parameter);

  }

  /**
   * 売上げ処理<BR>
   * ベリトランスコンビニ決済では使用しません<BR>
   */
  public PaymentResult invoice(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  /**
   * キャンセル処理<BR>
   * ベリトランスコンビニ決済では使用しません<BR>
   */
  public PaymentResult cancel(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  /**
   * 問合せ処理
   */
  public PaymentResult query(PaymentParameter parameter) {
    return doTransaction(Operation.QUERY, parameter);
  }

  public PaymentParameter createParameterInstance() {
    return new VeritransCvsParameter();
  }

  public CodeAttribute[] getCodeList(CashierPaymentTypeBase cashier) {
    if (cashier.getPaymentMethod() != null && cashier.getPaymentMethod().getCvsEnableType() != null) {
      Long cvsEnableType = cashier.getPaymentMethod().getCvsEnableType();

      if (cvsEnableType.equals(CvsEnableType.ALL.longValue())) {
        return VeritransCvsCode.values();
      } else if (cvsEnableType.equals(CvsEnableType.CVS.longValue())) {
        List<CodeAttribute> codeList = new ArrayList<CodeAttribute>();
        for (VeritransCvsCode code : VeritransCvsCode.values()) {
          if (code != VeritransCvsCode.PAY_EASY) {
            codeList.add(code);
          }
        }
        return codeList.toArray(new CodeAttribute[codeList.size()]);
      } else if (cvsEnableType.equals(CvsEnableType.PAY_EASY.longValue())) {
        List<CodeAttribute> codeList = new ArrayList<CodeAttribute>();
        for (VeritransCvsCode code : VeritransCvsCode.values()) {
          if (code == VeritransCvsCode.PAY_EASY) {
            codeList.add(code);
          }
        }
        return codeList.toArray(new CodeAttribute[codeList.size()]);
      }
    }
    return VeritransCvsCode.values();
  }

  private boolean validate(Map<String, String> args) {
    for (String value : args.values()) {
      if (value == null) {
        return false;
      }
    }
    return true;
  }

  /**
   * <p>
   * cvsgwlib.propertiesのPENDING_DIRフィールドとshopCodeより、<BR>
   * ペンディングディレクトリのパスを取得します。<BR>
   * <BR>
   * ペンディングディレクトリが存在せず、かつディレクトリの作成に失敗した場合は、<BR>
   * IOExceptionをスローします。 <p/>
   * 
   * @param path
   *          ペンディングディレクトリのパス
   * @param shopCode
   *          ショップコード
   * @return ペンディングディレクトリのpath
   * @throws IOException
   */
  private String getPendingDir(String path, String shopCode) throws IOException {
    String pendingDir = null;

    if (shopCode == null) {
      throw new InvalidParameterException(Messages.getString("ext.veritrans.VeritransCvs.2"));
    }

    File file = new File(path + File.separator + shopCode);
    if (file.exists()) {
      pendingDir = file.getAbsolutePath();
    } else {
      if (file.mkdir()) {
        pendingDir = file.getAbsolutePath();
      } else {
        throw new IOException(Messages.getString("ext.veritrans.VeritransCvs.3"));
      }
    }

    return pendingDir;
  }

}
