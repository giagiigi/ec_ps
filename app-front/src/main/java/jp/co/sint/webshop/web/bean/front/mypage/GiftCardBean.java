package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030110:マイページのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class GiftCardBean extends UIFrontBean {
  
  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  @Required
  @Length(15)
  @Metadata(name = "Card passWord ", order = 1)
  private String passWord;
  
  private boolean errorTimesFlg = false ;

  @Override
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    setPassWord(reqparam.get("passWord"));
    
  }

  @Override
  public String getModuleId() {
    return "";
  }

  @Override
  public String getModuleName() {
    return "礼品卡激活";
  }
  
  /**
   * @return the passWord
   */
  public String getPassWord() {
    return passWord;
  }
  
  /**
   * @param passWord the passWord to set
   */
  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  
  /**
   * @return the errorTimesFlg
   */
  public boolean isErrorTimesFlg() {
    return errorTimesFlg;
  }

  
  /**
   * @param errorTimesFlg the errorTimesFlg to set
   */
  public void setErrorTimesFlg(boolean errorTimesFlg) {
    this.errorTimesFlg = errorTimesFlg;
  }
  
  
  
  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
       topicPath.add(new NameValue(Messages.getString(
              "web.bean.front.mypage.OrderHistoryBean.1"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
      topicPath.add(new NameValue(Messages.getString("web.action.front.order.GiftCardRule.20"), "/app/mypage/gift_card/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }else{
      topicPath.add(new NameValue(Messages.getString("web.action.front.order.GiftCardRule.20"), "/app/mypage/gift_card/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    return topicPath;
  }
  
  

}
