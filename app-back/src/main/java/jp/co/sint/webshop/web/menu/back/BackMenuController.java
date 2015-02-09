package jp.co.sint.webshop.web.menu.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebMainAction;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.SessionContainer;

import org.apache.log4j.Logger;

/**
 * Menu遷移用Controller
 * 
 * @author System Integrator Corp.
 */
public class BackMenuController implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private LoginInfo login;

  private WebshopConfig config;

  private HashMap<BackDetailMenuBase, Boolean> menuPermissionMap = new HashMap<BackDetailMenuBase, Boolean>();

  /**
   * @param login
   *          ログイン情報
   * @param config
   *          システム初期化情報
   */
  public BackMenuController(LoginInfo login, WebshopConfig config) {
    this.login = login;
    this.config = config;
  }

  /**
   * 表示権限があるかどうかを返します。<BR>
   * 
   * @param menu
   *          タブメニュー
   * @return 表示権限有無
   */
  public boolean hasPermission(TabMenu menu) {
    for (BackDetailMenuBase baseMenu : menu.getMenus()) {
      if (hasPermission(baseMenu)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 指定されたMenuに権限が存在するかどうかをチェックする<BR>
   * Menu列挙に使用可能な権限が設定されている場合はそちらを<BR>
   * 設定されていない場合は、指定URLのinitアクションのauthorizeを<BR>
   * 見て権限をチェックする。
   * 
   * @param menu
   *          権限チェックを行うメニュー
   * @return 権限の有無 true-権限有 false-権限無し
   */
  public boolean hasPermission(BackDetailMenuBase menu) {
    Boolean permission = menuPermissionMap.get(menu);
    if (permission == null) {
      Permission[] defaultPermissions = menu.getPermissions();
      boolean result = false;

      if (defaultPermissions.length > 0) {
        // Menuが持ってる権限を優先チェック
        for (Permission p : defaultPermissions) {
          if (p.isGranted(login)) {
            result = true;
          }
        }
      } else {
        WebMainAction action = createActionInstance(menu.getUrl());
        if (action == null) {
          result = false;
        } else {
          SessionContainer container = new BackMenuSessionContainer(login);
          RequestParameter parameter = new BackMenuRequestParameter();
          action.setSessionContainer(container);
          action.setRequestParameter(parameter);
          action.setConfig(config);
          result = action.authorize();
        }
      }
      permission = Boolean.valueOf(result);
      menuPermissionMap.put(menu, permission);
    }
    return permission.booleanValue();
  }

  /**
   * タブメニューの初期表示画面のURLを取得します。
   * 
   * @param menu
   * @return 初画面のURL
   */
  public String getIndexUrl(TabMenu menu) {
    for (BackDetailMenuBase baseMenu : menu.getMenus()) {
      if (hasPermission(baseMenu)) {
        return baseMenu.getUrl();
      }
    }

    return "";
  }

  /**
   * タブメニューの初期表示画面の権限情報を取得します。
   * 
   * @param menu
   * @return 画面の権限情報
   */
  public BackDetailMenuBase[] getPermittedMenus(TabMenu menu) {
    List<BackDetailMenuBase> menuList = new ArrayList<BackDetailMenuBase>();
    try {
      for (BackDetailMenuBase detailMenu : menu.getMenus()) {
        if (hasPermission(detailMenu)) {
          menuList.add(detailMenu);
        }
      }
    } catch (Exception e) {
      Logger.getLogger(this.getClass()).debug("getPermittiedMenu is failed", e);
    }
    return menuList.toArray(new BackDetailMenuBase[menuList.size()]);
  }

  private WebMainAction createActionInstance(String pathInfo) {
    WebMainAction action = null;
    try {
      String jspid = StringUtil.toPascalFormat(getJspId(pathInfo));
      String actid = StringUtil.toPascalFormat(getActionId(pathInfo));
      if (StringUtil.isNullOrEmpty(actid)) {
        actid = "Init";
      }
      String className = jspid + actid + "Action";
      Class<?> c = Class.forName("jp.co.sint.webshop.web.action.back." + getPackageId(pathInfo) + "."
          + className);
      action = (WebMainAction) c.newInstance();
    } catch (Throwable t) {
      Logger.getLogger(this.getClass()).debug("createActionInstance()/ACTION NOT FOUND:" + t.getMessage());
      action = null;
    }
    return action;
  }

  private String getPackageId(String pathInfo) {
    return getId(pathInfo, 2);
  }

  private String getJspId(String pathInfo) {
    return getId(pathInfo, 3);
  }

  private String getActionId(String pathInfo) {
    return getId(pathInfo, 4);
  }

  private String getId(String pathInfo, int no) {
    String id = "";
    if (pathInfo.length() > 0) {
      String[] params = pathInfo.split("/");
      if (params.length > no) {
        id = params[no];
      }
    }
    return id;
  }
}
