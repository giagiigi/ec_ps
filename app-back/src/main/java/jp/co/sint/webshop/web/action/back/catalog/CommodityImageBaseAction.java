package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityImageBean;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CommodityImageBaseAction extends WebBackAction<CommodityImageBean> {

  private boolean uploadBtnFlg;
  public final String FILE_TYPE = "jpg";  

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean bReturn = false;

    if (Permission.COMMODITY_READ.isGranted(getLoginInfo())) {
      bReturn = true;
    }

    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      setUploadBtnFlg(true);
      bReturn = true;
    }

    return bReturn;
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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityImageBean bean = (CommodityImageBean) getRequestBean();

    // 即有要上传的图片且有上传权限时,才显示上传按钮
    if (bean.isUploadBtnFlg() && isUploadBtnFlg()) {
      bean.setUploadBtnFlg(true);
    } else {
      bean.setUploadBtnFlg(false);
    }
  }

  /**
   * @return the uploadBtnFlg
   */
  public boolean isUploadBtnFlg() {
    return uploadBtnFlg;
  }

  /**
   * @param uploadBtnFlg
   *          the uploadBtnFlg to set
   */
  public void setUploadBtnFlg(boolean uploadBtnFlg) {
    this.uploadBtnFlg = uploadBtnFlg;
  }
}
