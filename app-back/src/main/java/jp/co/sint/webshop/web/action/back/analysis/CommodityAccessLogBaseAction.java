package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.CommodityAccessLogBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070210:商品別アクセスログ集計の基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class CommodityAccessLogBaseAction extends WebBackAction<CommodityAccessLogBean> {

  /**
   * データ入出力権限を持つかどうか判定します
   * 
   * @return データ入出力権限があればtrue
   */
  public boolean hasExportAuthority() {
    BackLoginInfo login = getLoginInfo();
    boolean auth = false;

    if (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && !getConfig().isOne()) {
      // 分析データ_ショップの権限を持つユーザは一店舗モードでないときのみ権限を持つ
      auth = true;
    } else if (Permission.ANALYSIS_DATA_SITE.isGranted(login)) {
      // 分析データ_サイトの権限を持つユーザは常に権限あり
      auth = true;
    }

    return auth;
  }

  /**
   * ショップ一覧を作成します。
   * 
   * @return ショップのリスト
   */
  public List<CodeAttribute> createShopList() {
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());

    return service.getShopNamesDefaultAllShop(false, false);
  }

  /**
   * クライアントグループ一覧を作成します。
   * 
   * @return クライアントグループのリスト
   */
  public List<CodeAttribute> createClientGroupList() {
    UserAgentManager manager = DIContainer.getUserAgentManager();

    List<CodeAttribute> clientList = new ArrayList<CodeAttribute>();
    clientList.add(new NameValue(Messages.getString(
        "web.action.back.analysis.CommodityAccessLogBaseAction.0"), ""));
    for (UserAgent ua : manager.getUserAgentList()) {
      clientList.add(new NameValue(ua.getAgentName(), ua.getClientGroup()));
    }

    return clientList;
  }
}
