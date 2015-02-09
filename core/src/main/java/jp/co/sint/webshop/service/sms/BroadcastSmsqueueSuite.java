package jp.co.sint.webshop.service.sms;

import java.util.HashSet; // 10.1.1 10015 追加
import java.util.List;
import java.util.Set; // 10.1.1 10015 追加

import jp.co.sint.webshop.data.domain.SmsSendStatus; // 10.1.1 10015 追加
import jp.co.sint.webshop.data.dto.BroadcastSmsqueueDetail;

public class BroadcastSmsqueueSuite {


  private List<BroadcastSmsqueueDetail> detailList;

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

  public SmsSendStatus getSmsSendStatus() {
    if (getFailed().size() == 0) {
      return SmsSendStatus.SENT_ALL;
    } else if (getFailed().size() == getDetailList().size()) {
      return SmsSendStatus.FAILED_ALL;
    } else {
      return SmsSendStatus.PARTIAL_FAILED;
    }
  }
  // 10.1.1 10015 追加 ここまで

  /**
   * detailListを取得します。
   * 
   * @return the detailList
   */
  public List<BroadcastSmsqueueDetail> getDetailList() {
    return detailList;
  }

  /**
   * detailListを設定します。
   * 
   * @param detailList
   *          the detailList to set
   */
  public void setDetailList(List<BroadcastSmsqueueDetail> detailList) {
    this.detailList = detailList;
  }

}
