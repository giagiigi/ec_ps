package jp.co.sint.webshop.ext.veritrans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.ext.ErrorReasonMapper;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.ext.veritrans.VeritransCardParameter.Operation;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.order.PaymentParameter;
import jp.co.sint.webshop.service.order.PaymentProvider;
import jp.co.sint.webshop.service.order.PaymentResult;
import jp.co.sint.webshop.service.order.PaymentResultImpl;
import jp.co.sint.webshop.service.order.PaymentResultType;
import jp.co.sint.webshop.service.result.PaymentErrorContent;
import jp.co.sint.webshop.utility.IOUtil;

import org.apache.log4j.Logger;

import Jp.BuySmart.JPGWLib.Transaction;
import Jp.BuySmart.JPGWLib.TransactionFactory;

/**
 * ベリトランスカード決済フローの各処理に対するインタフェースを提供します。<BR>
 * カード決済で使用する処理は以下の5つです。<BR>
 * <ol>
 * <li>与信</li>
 * <li>売上</li>
 * <li>与信取消</li>
 * <li>売上取消</li>
 * <li>返品</li>
 * </ol>
 * 取消と返品の違いは、以下の通りです。 <blockquote> 一般的に、「取消」は決済処理を行った当日のキャンセル処理であるのに対して、
 * 「返品」は翌日以降の処理となります。<BR>
 * (SBIベリトランスカスタマーサポートページ運用ガイドより抜粋) </blockquote>
 * 
 * @author System Integrator Corp.
 */
public class VeritransCard implements PaymentProvider {

  /**
   * ベリトランスカード決済フロー各処理のトランザクション部分を共通化して行います
   * 
   * @param oper
   *          取引コマンド
   * @param params
   * @return 実行結果
   */
  @SuppressWarnings("unchecked")
  private PaymentResultImpl doTransaction(Operation oper, PaymentParameter params) {
    Logger logger = Logger.getLogger(this.getClass());

    PaymentResultImpl result = new PaymentResultImpl(PaymentResultType.NONE);

    try {
      // パラメータ生成
      VeritransCardParameter vParam = (VeritransCardParameter) params;
      if (!validate(vParam.getArgs(oper))) {
        throw new InvalidParameterException(Messages.getString("ext.veritrans.VeritransCard.0"));
      }
      Hashtable args = new Hashtable(vParam.getArgs(oper));

      // Transaction(通信モジュール)の初期化
      TransactionFactory factory = new TransactionFactory(loadProperties());
      Transaction tx = factory.createInstance(vParam.getMerchantId(), vParam.getSecretKey());
      tx.setPendingDir(getPendingDir(tx.getPendingDir(), params.getShopCode(), tx.getUsePendingLogging()));

      // パラメータ送信
      Hashtable response = tx.sendMServer(oper.getCommand(), args);
      Object status = response.get(Transaction.CC_MSTATUS);

      if (Transaction.CC_MSTATUS_SC.equals(status)) {
        result.setPaymentResultType(PaymentResultType.COMPLETED);
        result.setPaymentOrderId((String) response.get(VeritransCardConstantCode.RES_ORDER_ID));
        logger.info(response.get(Transaction.CC_AUXMSG));
      } else if (Transaction.CC_MSTATUS_FH.equals(status) || VeritransCardConstantCode.CC_MSTATUS_FBM.equals(status)) {
        result.setPaymentResultType(PaymentResultType.FAILED);
        ErrorReasonMapper mapper = VeritransErrorReasonMapper.getInstance(VeritransCard.class);
        result.addPaymentErrorList(mapper.createErrorContent((String) response.get(Transaction.CC_ACTCODE)));
        logger.error(response.get(Transaction.CC_MERRMSG));
      } else if (Transaction.CC_MSTATUS_FQC.equals(status) || Transaction.CC_MSTATUS_FQD.equals(status)) {
        result.setPaymentResultType(PaymentResultType.TIMED_OUT);
        result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
        logger.error(response.get(Transaction.CC_MERRMSG));
      } else if (VeritransCardConstantCode.CC_MSTATUS_FIU.equals(status)) {
        result.setPaymentResultType(PaymentResultType.FAILED);
        result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
        logger.error(response.get(Transaction.CC_MERRMSG));
      }

      return result;

    } catch (Exception e) {
      result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
      result.setPaymentResultType(PaymentResultType.FAILED);
      logger.error(e);
    }
    return result;

  }

  /**
   * 与信処理
   * <p>
   * 与信処理がタイムアウトした場合は、与信取消処理を行います。
   * </p>
   * パラメータには以下の値をセットしてください。
   * <ol>
   * <li>マーチャントID</li>
   * <li>秘密鍵</li>
   * <li>取引ID</li>
   * <li>決済金額</li>
   * <li>カード番号</li>
   * <li>カード有効期限</li>
   * </ol>
   */
  public PaymentResult entry(PaymentParameter parameter) {
    PaymentResultImpl result = doTransaction(Operation.AUTHONLY, parameter);
    if (result.getPaymentResultType() == PaymentResultType.TIMED_OUT) {
      PaymentResultImpl cancelResult = cancelEntry(parameter);
      if (cancelResult.getPaymentResultType() != PaymentResultType.COMPLETED) {
        throw new RuntimeException(Messages.getString("ext.veritrans.VeritransCard.1"));
      }
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(Messages.log("ext.veritrans.VeritransCard.2"));
    }
    return result;
  }

  /**
   * 売上処理
   * <p>
   * パラメータには以下の値をセットしてください。
   * </p>
   * <ol>
   * <li>マーチャントID</li>
   * <li>秘密鍵</li>
   * <li>取引ID</li>
   * <li>決済金額</li>
   * </ol>
   */
  public PaymentResultImpl invoice(PaymentParameter parameter) {
    PaymentResultImpl result = doTransaction(Operation.POSTAUTH, parameter);
    return result;
  }

  /**
   * 取消処理 推奨されていません
   * <p>
   * 
   * @see jp.co.sint.webshop.veritrans.VeritransCard#canselEntry
   * @see jp.co.sint.webshop.veritrans.VeritransCard#canselInvice
   * @see jp.co.sint.webshop.veritrans.VeritransCard#returnInvoice
   *      </p>
   *      を使用してください。
   */
  public PaymentResultImpl cancel(PaymentParameter parameter) {
    return cancelEntry(parameter);
  }

  /**
   * 与信取消処理
   * <p>
   * 売上処理前の取引をキャンセルします
   * </p>
   * <p>
   * パラメータには以下の値をセットしてください。
   * </p>
   * <ol>
   * <li>マーチャントID</li>
   * <li>秘密鍵</li>
   * <li>取引ID</li>
   * </ol>
   */
  public PaymentResultImpl cancelEntry(PaymentParameter parameter) {
    return doTransaction(Operation.CANCEL_AUTHONLY, parameter);
  }

  /**
   * 売上取消処理
   * <p>
   * 売上処理後の取引をキャンセルします
   * </p>
   * <ol>
   * <li>マーチャントID</li>
   * <li>秘密鍵</li>
   * <li>取引ID</li>
   * </ol>
   */
  public PaymentResultImpl cancelInvoice(PaymentParameter parameter) {
    return doTransaction(Operation.CANCEL_POSTAUTH, parameter);
  }

  /**
   * 返品処理
   * <p>
   * 決済金額が売上処理時の金額より小さい場合は、部分返品になります。
   * </p>
   * <ol>
   * <li>マーチャントID</li>
   * <li>秘密鍵</li>
   * <li>取引ID</li>
   * <li>決済金額</li>
   * </ol>
   */
  public PaymentResultImpl returnInvoice(PaymentParameter parameter) {
    return doTransaction(Operation.RETURN, parameter);
  }

  /**
   * 問合せ処理
   * <p>
   * ベリトランスカード決済では使用しません。
   * </p>
   * 
   * @exception UnsupportedOperationException
   */
  public PaymentResultImpl query(PaymentParameter parameter) {
    throw new UnsupportedOperationException();
  }

  public PaymentParameter createParameterInstance() {
    return new VeritransCardParameter();
  }

  public CodeAttribute[] getCodeList(CashierPaymentTypeBase cashier) {
    // ベリトランスカード決済はコード値なし
    return new CodeAttribute[0];
  }

  /**
   * クラスパスからプロパティファイル"jpgwlib.properties"をロードします <BR>
   * 
   * @return
   * @throws IOException
   */
  private Properties loadProperties() throws IOException {
    Properties props = new Properties();
    // 10.1.4 K00168 修正 ここから
    // InputStream in = VeritransCard.class.getResourceAsStream("/jpgwlib.properties");
    // props.load(in);
    InputStream in = null;
    try {
      in = VeritransCard.class.getResourceAsStream("/jpgwlib.properties");
      props.load(in);
    } catch (IOException e) {
      throw e;
    } finally {
      IOUtil.close(in);
    }
    // 10.1.4 K00168 修正 ここまで
    
    return props;
  }

  private boolean validate(Map<String, String> args) {
    for (String arg : args.values()) {
      if (arg == null) {
        return false;
      }
    }
    return true;

  }

  /**
   * <p>
   * jpgwlib.propertiesのPENDING_DIRフィールドとshopCodeより、<BR>
   * ペンディングディレクトリのパスを取得します。<BR>
   * jpgwlib.propertiesのUSE_PENDING_LOGGINGフィールドがfalseの場合は、<BR>
   * jpgwlib.propertiesのPENDING_DIRフィールドの値をそのまま返します。<BR>
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
  private String getPendingDir(String path, String shopCode, boolean usePendingLogging) throws IOException {
    String pendingDir = null;

    if (shopCode == null) {
      throw new InvalidParameterException(Messages.getString("ext.veritrans.VeritransCard.3"));
    }

    if (!usePendingLogging) {
      return path;
    }

    File file = new File(path + File.separator + shopCode);
    if (file.exists()) {
      pendingDir = file.getAbsolutePath();
    } else {
      if (file.mkdir()) {
        pendingDir = file.getAbsolutePath();
      } else {
        throw new IOException(Messages.getString("ext.veritrans.VeritransCard.4"));
      }
    }

    return pendingDir;
  }
}
