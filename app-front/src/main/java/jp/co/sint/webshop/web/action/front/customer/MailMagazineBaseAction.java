package jp.co.sint.webshop.web.action.front.customer;

import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.MailMagazineBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

import org.apache.log4j.Logger;

public abstract class MailMagazineBaseAction extends WebFrontAction<MailMagazineBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    WebshopConfig config = getConfig();
    if (!config.isMailMagazineAvailableMode()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.debug(Messages.log("web.action.front.customer.MailMagazineBaseAction.0"));
      throw new URLNotFoundException();
    }

    if (!MassiveSendChecker.check(getBean().getEmail())) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.MASSIVE_SEND_ERROR));
      return false;
    }

    return true;
  }

  @Override
  public abstract WebActionResult callService();

  /**
   * 大量送信チェッククラス
   */
  private static final class MassiveSendChecker {

    // 連続送信を許可する回数
    private static final int COUNT_MAX = 5;

    // 連続送信回数カウンタの保持期間
    private static final long TIME_OUT = 300000; // 5 minutes

    private static Logger log = Logger.getLogger(MassiveSendChecker.class);

    private static Map<String, History> map = new HashMap<String, History>();

    /**
     * コンストラクタ(インスタンス生成不可)
     */
    private MassiveSendChecker() {
    }

    /**
     * 指定されたメールアドレスへの連続投稿をチェックします。
     * 
     * @param email
     *          チェック対象メールアドレス
     * @return 送信が許可される場合はtrue
     */
    public static synchronized boolean check(String email) {
      boolean result = true;
      if (StringUtil.hasValue(email)) {

        // 以前の履歴がある場合
        if (map.containsKey(email)) {
          History history = (History) map.get(email);
          long now = System.currentTimeMillis();

          // タイムアウトしたらカウンタをリセット
          if (now - history.getTimestamp() > TIME_OUT) {
            history.setCount(1);
            history.setTimestamp(now);
            // ログへ出力
            log.warn("Count Reset:{email=" + email + ",count=" + history.getCount() + "}");
            return true;
          }

          // タイムスタンプ更新とカウントアップ
          history.setTimestamp(now);
          history.setCount(history.getCount() + 1);

          // 連続投稿が検知されたら警告ログを出力
          result = history.getCount() <= COUNT_MAX;
          if (!result) {
            log.warn("Detected Continuous Sending{email=" + email + ", count=" + history.getCount() + "}");
          }

        } else {
          // 初回は履歴を新規作成
          map.put(email, new History(1, System.currentTimeMillis()));
          result = true;
        }
      }
      return result;
    }

    /**
     * 内部用履歴管理クラス：(回数とタイムスタンプを管理)
     */
    private static class History {

      /** タイムスタンプ */
      private long timestamp;

      /** 投稿回数 */
      private int count;

      /** 新しい */
      public History(int lCount, long lTimestamp) {
        this.setCount(lCount);
        this.setTimestamp(lTimestamp);
      }

      public void setTimestamp(long lTimestamp) {
        this.timestamp = lTimestamp;
      }

      public long getTimestamp() {
        return timestamp;
      }

      public void setCount(int lCount) {
        this.count = lCount;
      }

      public int getCount() {
        return count;
      }
    }
  }

}
