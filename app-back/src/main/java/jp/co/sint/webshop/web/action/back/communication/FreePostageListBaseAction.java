package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageListBean;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageListBean.FreePostageListBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * 免邮促销共通处理
 * 
 * @author Kousen.
 */
public abstract class FreePostageListBaseAction extends WebBackAction<FreePostageListBean> {

  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.FREE_POSTAGE_READ_SHOP.isGranted(login);
  }
  
  /**
   * レビューの検索結果一覧を作成します。<BR>
   * 
   * @param reviewList
   * @param nextBean
   */
  public List<FreePostageListBeanDetail> createList(List<FreePostageRule> resultList) {
    List<FreePostageListBeanDetail> list = new ArrayList<FreePostageListBeanDetail>();
    for (FreePostageRule freePostageRule : resultList) {
      FreePostageListBeanDetail detail = new FreePostageListBeanDetail();

      detail.setFreePostageCode(freePostageRule.getFreePostageCode());
      detail.setFreePostageName(freePostageRule.getFreePostageName());
      detail.setFreeStartDate(DateUtil.toDateTimeString(freePostageRule.getFreeStartDate()));
      detail.setFreeEndDate(DateUtil.toDateTimeString(freePostageRule.getFreeEndDate()));

      list.add(detail);
    }

    return list;
  }

}
