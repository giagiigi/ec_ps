package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerListBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerListBean.CustomerDetail;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030110:顧客マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CustomerListBaseAction extends WebBackAction<CustomerListBean> {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * 検索条件を設定します。<BR>
   * 
   * @param searchBean
   * @param condition
   */
  public void setSearchCondition(CustomerListBean searchBean, CustomerSearchCondition condition) {
    condition.setSearchCustomerFrom(searchBean.getSearchCustomerFrom());
    condition.setSearchCustomerTo(searchBean.getSearchCustomerTo());
    condition.setSearchCustomerGroupCode(searchBean.getSearchCustomerGroupCode());
    condition.setSearchTel(searchBean.getSearchTel());
    //Add by V10-CH start
    condition.setSearchMobile(searchBean.getSearchMobile());
    //Add by V10-CH end
    condition.setSearchCustomerStatus(searchBean.getSearchCustomerStatus());
    condition.setSearchCustomerName(searchBean.getSearchCustomerName());
    condition.setSearchCustomerNameKana(searchBean.getSearchCustomerNameKana());
    condition.setSearchEmail(searchBean.getSearchEmail());
    condition.setSearchRequestMailType(searchBean.getSearchRequestMailType());
    condition.setSearchClientMailType(searchBean.getSearchClientMailType());
    // 20120329 shen delete start
    // //add by os012 20111230 start
    // //是否是正式会员:TMALL_MEMBER是淘宝会员，MEMBER是正式会员
    // condition.setCustomerKbn(String.valueOf(CustomerKbn.MEMBER.longValue()));
    // //add by os012 20111230 end 
    // 20120329 shen delete end
    
    if (searchBean.isShop()) {
      condition.setShop(searchBean.isShop());
      condition.setShopCode(getLoginInfo().getShopCode());
    }
  }

  /**
   * 顧客一覧の検索結果一覧を作成します。<BR>
   * 
   * @param nextBean
   *          顧客Bean
   * @param customerList
   *          顧客一覧
   */
  public void setCustomerList(CustomerListBean nextBean, List<CustomerSearchInfo> customerList) {
    List<CustomerDetail> list = new ArrayList<CustomerDetail>();

    for (CustomerSearchInfo cc : customerList) {
      CustomerDetail detail = new CustomerDetail();
      detail.setCustomerCode(cc.getCustomerCode());
      detail.setFirstName(cc.getFirstName());
      detail.setLastName(cc.getLastName());
      detail.setEmail(cc.getEmail());
      detail.setTel(cc.getPhoneNumber());
      //Add by V10-CH start
      detail.setMobileNumber(cc.getMobileNumber());
      //Add by V10-CH end
      if (cc.getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.getValue())) {
        detail.setReceivedWithdrawalNotice(true);
      } else {
        detail.setReceivedWithdrawalNotice(false);
      }
      detail.setCaution(cc.getCaution());
      if (StringUtil.hasValue(cc.getCaution())) {
        detail.setCautionFlg(true);
      } else {
        detail.setCautionFlg(false);
      }
      // 一覧データ設定
      setDisplayValue(detail, cc);

      list.add(detail);
    }
    nextBean.setList(list);
  }

  /**
   * 表示用情報メールの設定(0:×, 1:○) : 削除済み顧客チェック
   * 
   * @param detail
   * @param cc
   */
  public void setDisplayValue(CustomerDetail detail, CustomerSearchInfo cc) {
    RequestMailType mailType = RequestMailType.fromValue(cc.getRequestMailType());

    //表示用情報メール設定
    switch (mailType) {
      case WANTED:
        detail.setRequestMailType(Messages.getString("web.action.back.customer.CustomerListBaseAction.0"));
        break;
      case UNWANTED:
        detail.setRequestMailType(Messages.getString("web.action.back.customer.CustomerListBaseAction.1"));
        break;
      default:
        break;
    }

    // 削除済み顧客かどうかをチェック
    if (cc.getCustomerStatus().equals(CustomerStatus.WITHDRAWED.getValue())) {
      detail.setIsWithDrawal(false);
    } else {
      detail.setIsWithDrawal(true);
    }
  }
}
