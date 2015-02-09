package jp.co.sint.webshop.service.mail;

import java.util.HashSet; // 10.1.1 10015 追加
import java.util.List;
import java.util.Set; // 10.1.1 10015 追加

import jp.co.sint.webshop.data.domain.MailSendStatus; // 10.1.1 10015 追加
import jp.co.sint.webshop.data.dto.BroadcastMailqueueDetail;
import jp.co.sint.webshop.data.dto.BroadcastMailqueueHeader;

public class BroadcastMailqueueSuite {

  private BroadcastMailqueueHeader header;

  private List<BroadcastMailqueueDetail> detailList;

  // 10.1.1 10015 追加 ここから
  private Set<String> failed = new HashSet<String>();

  /**
   * failedを取得します。
   * 
   * @return failed
   */
  public Set<String> getFailed() {
    return failed;
  }

  /**
   * failedを設定します。
   * 
   * @param failed
   *          failed
   */
  public void setFailed(Set<String> failed) {
    this.failed = failed;
  }

  public MailSendStatus getMailSendStatus() {
    if (getFailed().size() == 0) {
      return MailSendStatus.SENT_ALL;
    } else if (getFailed().size() == getDetailList().size()) {
      return MailSendStatus.FAILED_ALL;
    } else {
      return MailSendStatus.PARTIAL_FAILED;
    }
  }
  // 10.1.1 10015 追加 ここまで

  /**
   * detailListを取得します。
   * 
   * @return the detailList
   */
  public List<BroadcastMailqueueDetail> getDetailList() {
    return detailList;
  }

  /**
   * detailListを設定します。
   * 
   * @param detailList
   *          the detailList to set
   */
  public void setDetailList(List<BroadcastMailqueueDetail> detailList) {
    this.detailList = detailList;
  }

  /**
   * headerを取得します。
   * 
   * @return the header
   */
  public BroadcastMailqueueHeader getHeader() {
    return header;
  }

  /**
   * headerを設定します。
   * 
   * @param header
   *          the header to set
   */
  public void setHeader(BroadcastMailqueueHeader header) {
    this.header = header;
  }

}
