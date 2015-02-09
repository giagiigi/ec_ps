package jp.co.sint.webshop.web.bean.back.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1010110:ログインのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class AreaSelectBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  private List<AreaBlockCharge> areaBlockChargeList = new ArrayList<AreaBlockCharge>();
  
  private List<String> checkedAreaBlockIdList = new ArrayList<String>();
    
  public static class AreaBlockCharge implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String areaBlockId;

    private String areaBlockName;

    public String getAreaBlockId() {
      return areaBlockId;
    }

    public void setAreaBlockId(String areaBlockId) {
      this.areaBlockId = areaBlockId;
    }

    public String getAreaBlockName() {
      return areaBlockName;
    }

    public void setAreaBlockName(String areaBlockName) {
      this.areaBlockName = areaBlockName;
    }
  }
  
  public List<AreaBlockCharge> getAreaBlockChargeList() {
    return areaBlockChargeList;
  }

  public void setAreaBlockChargeList(List<AreaBlockCharge> areaBlockChargeList) {
    this.areaBlockChargeList = areaBlockChargeList;
  }
  
  public List<String> getCheckedAreaBlockIdList() {
    return checkedAreaBlockIdList;
  }

  public void setCheckedAreaBlockIdList(List<String> checkedAreaBlockIdList) {
    this.checkedAreaBlockIdList = checkedAreaBlockIdList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setCheckedAreaBlockIdList(Arrays.asList(reqparam.getAll("areaId")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1010110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.common.LoginBean.0");
  }

}
