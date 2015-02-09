package jp.co.sint.webshop.web.bean.front;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;

public class OnlineServiceBean {

  @Length(2000)
  @Metadata(name = "script内容1")
  private String scriptText1;

  @Length(2000)
  @Metadata(name = "script内容2")
  private String scriptText2;
  
  //Add by V10-CH start
  @Length(2000)
  @Metadata(name = "script内容3")
  private String scriptText3;
  
  public String getScriptText3() {
    return scriptText3;
  }

  public void setScriptText3(String scriptText3) {
    this.scriptText3 = scriptText3;
  }
  //Add by V10-CH end

  public String getScriptText1() {
    return scriptText1;
  }

  
  public void setScriptText1(String scriptText1) {
    this.scriptText1 = scriptText1;
  }
  
  public String getScriptText2() {
    return scriptText2;
  }

  
  public void setScriptText2(String scriptText2) {
    this.scriptText2 = scriptText2;
  }
  
  
}
