package jp.co.sint.webshop.service.data;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {

  public boolean accept(File dir, String name) {
    int x = name.lastIndexOf('.');
    String extent = name.substring(x + 1);
    if (extent.equals("jpg")) {
      return true;
    }
    return false;
  }

}
