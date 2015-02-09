package jp.co.sint.webshop.web.action.back.communication;

import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.communication.EnqueteList;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteListBean;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteListBean.EnqueteListBeanDetail;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class EnqueteListBaseAction extends WebBackAction<EnqueteListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.ENQUETE_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アンケートの検索結果一覧を作成します。<BR>
   * 
   * @param enqueteList
   * @param nextBean
   */
  public void addResult(List<EnqueteList> enqueteList, EnqueteListBean nextBean) {
    for (EnqueteList enqueteData : enqueteList) {
      EnqueteListBeanDetail detail = new EnqueteListBeanDetail();
      detail.setEnqueteCode(enqueteData.getEnqueteCode());
      detail.setEnqueteName(enqueteData.getEnqueteName());
      detail.setEnqueteNameEn(enqueteData.getEnqueteNameEn());
      detail.setEnqueteNameJp(enqueteData.getEnqueteNameJp());
      detail.setEnqueteStartDate(DateUtil.toDateString(enqueteData.getEnqueteStartDate()));
      detail.setEnqueteEndDate(DateUtil.toDateString(enqueteData.getEnqueteEndDate()));
      detail.setEnqueteAnswerCount(enqueteData.getEnqueteAnswerCount());
      detail.setEnqueteStatus(getStatus(enqueteData.getEnqueteStartDate(), enqueteData.getEnqueteEndDate()));
      nextBean.getList().add(detail);
    }
  }

  /**
   * アンケートの実施状況を取得します。<BR>
   * ：実施前、実施中、実施終了
   * 
   * @param startDate
   * @param endDate
   * @return アンケートの実施状況
   */
  public String getStatus(Date startDate, Date endDate) {
    String result = null;
    Date today = DateUtil.truncateDate(DateUtil.getSysdate());

    if (today.before(startDate)) { // システム日付 < 開始日の場合
      result = Messages.getString("web.action.back.communication.EnqueteListBaseAction.0");
    } else if (today.after(endDate)) { // システム日付 > 終了日の場合
      result = Messages.getString("web.action.back.communication.EnqueteListBaseAction.1");
    } else { // 開始日 <= システム日付 <= 終了日の場合
      result = Messages.getString("web.action.back.communication.EnqueteListBaseAction.2");
    }
    return result;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // ボタン表示制御
    EnqueteListBean bean = (EnqueteListBean) getRequestBean();
    bean.setDeleteButtonDisplayFlg(Permission.ENQUETE_DELETE_SITE.isGranted(getLoginInfo()));
    bean.setEditButtonDisplayFlg(Permission.ENQUETE_UPDATE_SITE.isGranted(getLoginInfo()));
    setRequestBean(bean);
  }

}
