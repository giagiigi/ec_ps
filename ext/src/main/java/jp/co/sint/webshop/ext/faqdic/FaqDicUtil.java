package jp.co.sint.webshop.ext.faqdic;

import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.utility.PostalAddress;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.zipdic.ZipDic;

public final class FaqDicUtil {

  private FaqDicUtil() {
  }

  public static PostalAddress getZipDic(String zipDicDir, String postalCode) {
    ZipDic dic = new ZipDic(zipDicDir);
    FaqDicAddress address = new FaqDicAddress();
    if (dic != null) {
      String pref = dic.getAddress(postalCode, ZipDic.MODE_PREFECTURE);
      if (StringUtil.hasValue(pref)) {
        address.setPostalCode(postalCode);
        address.setPrefecture(pref);
        address.setCity(dic.getAddress(postalCode, ZipDic.MODE_CITY));
        address.setAddress(dic.getAddress(postalCode, ZipDic.MODE_TOWN));
        String prefectureCode = PrefectureCode.fromName(address.getPrefecture()).getValue();
        address.setPrefectureCode(prefectureCode);
      }
    }
    return address;

  }
}
