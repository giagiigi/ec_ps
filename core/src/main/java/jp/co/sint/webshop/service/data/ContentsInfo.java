package jp.co.sint.webshop.service.data;

import java.io.File;


public interface ContentsInfo {

  ContentsType getContentsType();
  
  File getContentsFile();

}
