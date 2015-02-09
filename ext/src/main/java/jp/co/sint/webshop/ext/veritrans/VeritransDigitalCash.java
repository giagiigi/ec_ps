package jp.co.sint.webshop.ext.veritrans;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.DigitalCashEnableType;
import jp.co.sint.webshop.ext.ErrorReasonMapper;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.order.PaymentParameter;
import jp.co.sint.webshop.service.order.PaymentProvider;
import jp.co.sint.webshop.service.order.PaymentResult;
import jp.co.sint.webshop.service.order.PaymentResultImpl;
import jp.co.sint.webshop.service.order.PaymentResultType;
import jp.co.sint.webshop.service.result.PaymentErrorContent;
import jp.co.sint.webshop.utility.IOUtil;
import jp.co.veritrans.em.gwlib.Request;
import jp.co.veritrans.em.gwlib.Response;
import jp.co.veritrans.em.gwlib.Transaction;
import jp.co.veritrans.em.gwlib.TransactionFactory;

import org.apache.log4j.Logger;

/**
 * <p>
 * ベリトランス電子マネー決済フローの各処理に対するインタフェースを提供します。
 * </p>
 * <p>
 * 電子マネー決済で使用する処理は以下の2つです。
 * </p>
 * <ol>
 * <li>申込み</li>
 * <li>返金</li>
 * </ol>
 * 
 * @author System Integrator Corp.
 */
public class VeritransDigitalCash implements PaymentProvider {

  /** ダミー請求タイプ(未入金 + ベリトランスからの入金通知送信なし) */
  private static final String DUMMY_TYPE_NO_PAYMENT = "1";

  /**
   * ベリトランスコンビニ決済フロー各処理のトランザクション部分を共通化して行います
   * 
   * @param oper
   *          取引コマンド
   * @param params
   * @return 実行結果
   */
  @SuppressWarnings("unchecked")
  private PaymentResultImpl doTransaction(int oper, PaymentParameter parameter) {
    Logger logger = Logger.getLogger(this.getClass());

    PaymentResultImpl result = new PaymentResultImpl(PaymentResultType.NONE);

    try {
      if (!IOUtil.existsResource("emgwlib")) {
        logger.warn(Messages.log("ext.veritrans.VeritransDigitalCash.0"));
      }

      // パラメータ生成
      VeritransDigitalCashParameter vParam = (VeritransDigitalCashParameter) parameter;
      Request request = TransactionFactory.createRequest(oper);
      Map<String, String> args = vParam.getArgs(oper);
      if (!validate(args)) {
        throw new InvalidParameterException(Messages.getString("ext.veritrans.VeritransDigitalCash.1"));
      }
      request.putAll(vParam.getArgs(oper));

      // Transaction(通信モジュール)の初期化
      Transaction tx = TransactionFactory.createTransaction();
      tx.setSecret(vParam.getMerchantId());
      tx.setKey(vParam.getSecretKey());
      tx.setPendingDir(getPendingDir(tx.getPendingDir(), vParam.getShopCode()));
      // 取引種別が返金で、かつダミー取引の場合は、ダミータイプを1にする
      if (oper == Transaction.CMD_RETURN && tx.getDummyType() != null) {
        tx.setDummyType(DUMMY_TYPE_NO_PAYMENT);
      }

      // パラメータ送信
      Response response = tx.doTransaction(request);
      String status = (String) response.get(Transaction.RES_MSTATUS);

      if (Transaction.RES_MSTATUS_SC.equals(status)) {
        result.setPaymentResultType(PaymentResultType.COMPLETED);
        result.setPaymentOrderId((String) response.get(Transaction.RES_ORDER_ID));
        result.setPaymentReceiptNo((String) response.get(Transaction.RES_RECEIPT_NO));
        result.setDigitalCashType(vParam.getDigitalCashType().getValue());
        result.setPaymentLimitDate(vParam.getPaymentLimitDate());
        result.setPaymentReceiptUrl((String) response.get(Transaction.RES_APP_URL));
        logger.info((String) response.get(Transaction.RES_AUX_MSG));
      } else if (Transaction.RES_MSTATUS_FH.equals(status)) {
        ErrorReasonMapper mapper = VeritransErrorReasonMapper.getInstance(VeritransDigitalCash.class);
        result.setPaymentResultType(PaymentResultType.FAILED);
        result.addPaymentErrorList(mapper.createErrorContent((String) response.get(Transaction.RES_ERR_CODE)));
        logger.error((String) response.get(Transaction.RES_MERRMSG));
      } else if (Transaction.RES_MSTATUS_FC.equals(status) || Transaction.RES_MSTATUS_FD.equals(status)) {
        result.setPaymentResultType(PaymentResultType.TIMED_OUT);
        result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
        logger.error((String) response.get(Transaction.RES_MERRMSG));
      }

      return result;

    } catch (InvalidParameterException e) {
      result.addPaymentErrorList(PaymentErrorContent.INVALID_OTHER_PARAMETER_ERROR);
      result.setPaymentResultType(PaymentResultType.FAILED);
      result.setMessage(e.toString());
      logger.error(e);
    } catch (IOException e) {
      result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
      result.setPaymentResultType(PaymentResultType.FAILED);
      logger.error(e);
    } catch (RuntimeException e) {
      result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
      result.setPaymentResultType(PaymentResultType.FAILED);
      logger.error(e);
    } catch (Exception e) {
      result.addPaymentErrorList(PaymentErrorContent.OTHER_PAYMENT_ERROR);
      result.setPaymentResultType(PaymentResultType.FAILED);
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
   * <li>電子マネー決済方法</li>
   * <li>支払期限※Cyber Edy以外</li>
   * <li>メールアドレス※Mobile Edy, モバイルSuica(メール決済)のみ</li>
   * <li>画面タイトル※モバイルSuica(メール決済/アプリ決済)のみ</li>
   * <li>支払完了通知URL※Cyber Edyのみ</>
   * </ol>
   */
  public PaymentResult entry(PaymentParameter parameter) {
    return doTransaction(Transaction.CMD_ENTRY, parameter);
  }

  /**
   * キャンセル処理<BR>
   * ベリトランス電子マネー決済では使用しません <BR>
   */
  public PaymentResult cancel(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  /**
   * <p>
   * 返金処理 パラメータには以下の値をセットしてください。
   * <ol>
   * <li>マーチャントID</li>
   * <li>秘密鍵</li>
   * <li>取引ID</li>
   * <li>元取引ID</li>
   * <li>決済金額</li>
   * <li>電子マネー決済方法</li>
   * <li>支払期限※モバイルSuica(メール決済/アプリ決済)のみ</li>
   * <li>画面タイトル※モバイルSuica(メール決済/アプリ決済)のみ</li>
   * </ol>
   * </p>
   * 取引IDは新規に採番し、元取引IDに返金対象受注の取引番号を指定してください。 また返金金額は申込み金額と同額を指定してください。
   * </p>
   */
  public PaymentResult returnEntry(PaymentParameter parameter) {
    return doTransaction(Transaction.CMD_RETURN, parameter);
  }

  /**
   * 請求処理<BR>
   * ベリトランス電子マネー決済では使用しません <BR>
   */
  public PaymentResult invoice(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  /**
   * 問合せ処理<BR>
   * ベリトランス電子マネー決済では使用しません <BR>
   */
  public PaymentResult query(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  public PaymentParameter createParameterInstance() {
    return new VeritransDigitalCashParameter();
  }

  public CodeAttribute[] getCodeList(CashierPaymentTypeBase cashier) {
    if (cashier.getPaymentMethod() != null && cashier.getPaymentMethod().getDigitalCashEnableType() != null) {
      Long digitalCashEnableType = cashier.getPaymentMethod().getDigitalCashEnableType();

      if (digitalCashEnableType.equals(DigitalCashEnableType.ALL.longValue())) {
        return VeritransDigitalCashType.values();
      } else if (digitalCashEnableType.equals(DigitalCashEnableType.EDY.longValue())) {
        List<CodeAttribute> codeList = new ArrayList<CodeAttribute>();
        for (VeritransDigitalCashType code : VeritransDigitalCashType.values()) {
          if (code == VeritransDigitalCashType.MOBILE_EDY) {
            codeList.add(code);
          }
        }
        return codeList.toArray(new CodeAttribute[codeList.size()]);
      } else if (digitalCashEnableType.equals(DigitalCashEnableType.SUICA.longValue())) {
        List<CodeAttribute> codeList = new ArrayList<CodeAttribute>();
        for (VeritransDigitalCashType code : VeritransDigitalCashType.values()) {
          if (code == VeritransDigitalCashType.MAIL_SUICA) {
            codeList.add(code);
          }
        }
        return codeList.toArray(new CodeAttribute[codeList.size()]);
      }
    }
    return VeritransDigitalCashType.values();
  }

  private boolean validate(Map<String, String> map) {
    for (String arg : map.values()) {
      if (arg == null) {
        return false;
      }
    }
    return true;
  }

  /**
   * <p>
   * emgwlib.propertiesのPENDING_DIRフィールドとshopCodeより、<BR>
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
      throw new InvalidParameterException(Messages.getString("ext.veritrans.VeritransDigitalCash.2"));
    }

    File file = new File(path + File.separator + shopCode);
    if (file.exists()) {
      pendingDir = file.getAbsolutePath();
    } else {
      if (file.mkdir()) {
        pendingDir = file.getAbsolutePath();
      } else {
        throw new IOException(Messages.getString("ext.veritrans.VeritransDigitalCash.3"));
      }
    }

    return pendingDir;
  }

}
