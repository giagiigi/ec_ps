package jp.co.sint.webshop.web.menu.back;

import java.util.List;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.web.bean.DownloadResource;
import jp.co.sint.webshop.web.bean.UIBean;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;
import jp.co.sint.webshop.web.webutility.SessionContainer;
import jp.co.sint.webshop.web.webutility.SessionUrl;

public class BackMenuSessionContainer implements SessionContainer {

  private LoginInfo login;

  public BackMenuSessionContainer(LoginInfo login) {
    this.login = login;
  }

  public void clearBackTransitionInfoList() {
  }

  public List<BackTransitionInfo> getBackTransitionInfoList() {
    return null;
  }

  public String getContextPath() {
    return null;
  }

  public LoginInfo getLoginInfo() {
    return login;
  }

  public UIBean getTempBean() {
    return null;
  }

  public void login(LoginInfo loginInfo) {
  }

  public void logout() {
  }

  public void setBackTransitionInfoList(List<BackTransitionInfo> backInfoList) {
  }

  public void setDownloadResource(DownloadResource downloadResource) {
  }

  public void setLoginInfo(LoginInfo userInfo) {
  }

  public void setTempBean(UIBean bean) {
  }

  public String getIpAddress() {
    return null;
  }

  public boolean isValid() {
    return false;
  }

  public BackTransitionInfo getAfterLoginInfo() {
    return null;
  }

  public Cart getCart() {
    return null;
  }
  @Override
  public String getVerifyCode() {
    return null;
  }

  @Override
  public SessionUrl getSessionUrl() {
    return null;
  }

  //2012/11/22 促销对应 ob add start
	@Override
	public String getMedia() {
		return null;
	}
	public void setMedia(String mediaCode) {
  }
  //2012/11/22 促销对应 ob add end

//2013/05/04 优惠券对应 ob add start
  @Override
  public String getMobileAuthCode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setMobileAuthCode(String mediaCode) {
    // TODO Auto-generated method stub
    
  }
//2013/05/04 优惠券对应 ob add end
}
