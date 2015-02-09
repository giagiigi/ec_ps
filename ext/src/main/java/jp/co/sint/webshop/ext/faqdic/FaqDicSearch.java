package jp.co.sint.webshop.ext.faqdic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.utility.PostalAddress;
import jp.co.sint.webshop.utility.PostalSearch;

/**
 * 「FAQ里恵の郵便番号辞書」による郵便番号検索実装
 * 
 * @author System Integrator Corp.
 */
public class FaqDicSearch implements PostalSearch {

  private String zipDicDir;

  private static final String DIC_FILE_1 = "satoe1.dic";

  private static final String DIC_FILE_2 = "satoe2.dic";

  private static final String ZIPDIC_CLASSNAME = "jp.co.sint.zipdic.ZipDic";

  public FaqDicSearch(String zipDicDir) {
    setZipDicDir(zipDicDir);
  }

  public PostalAddress get(String postalCode) {
    if (isEnabled()) {
      return FaqDicUtil.getZipDic(getZipDicDir(), postalCode);
    } else {
      return new FaqDicAddress();
    }
  }

  public List<PostalAddress> getAll(String postalCode) {
    List<PostalAddress> list = new ArrayList<PostalAddress>();
    PostalAddress pa = this.get(postalCode);
    if (pa.getPostalCode() != null) {
      list.add(pa);
    }
    return list;
  }

  /**
   * zipDicDirを返します。
   * 
   * @return the zipDicDir
   */
  public String getZipDicDir() {
    return zipDicDir;
  }

  /**
   * zipDicDirを設定します。
   * 
   * @param zipDicDir
   *          設定する zipDicDir
   */
  public void setZipDicDir(String zipDicDir) {
    this.zipDicDir = zipDicDir;
  }

  /**
   * enabledを返します。
   * 
   * @return the enabled
   */
  public boolean isEnabled() {
    Logger logger = Logger.getLogger(this.getClass());
    boolean result = false;
    try {
      File zipDir = new File(getZipDicDir());
      if (zipDir.exists() && zipDir.isDirectory()) {
        File dicFile1 = new File(zipDir, DIC_FILE_1);
        File dicFile2 = new File(zipDir, DIC_FILE_2);

        result = true;
        //辞書ファイル(satoe1.dic/satoe2.dic)が存在するかどうか
        result &= dicFile1.exists() && dicFile2.exists();

        //郵便番号辞書のjarファイルがクラスパスにあるか調べる
        result &= foundZipDic();
      }
    } catch (RuntimeException e) {
      logger.warn(e);
      result = false;
    } catch (Exception e) {
      logger.warn(e);
      result = false;
    }
    return result;
  }

  private boolean foundZipDic() {
    boolean result = false;
    try {
      Class<?> z = Class.forName(ZIPDIC_CLASSNAME);
      result = z != null;
    } catch (ClassNotFoundException cnfEx) {
      result = false;
    }
    return result;
  }
}
