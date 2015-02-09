package jp.co.sint.webshop.service.data;


public interface ImageInfo extends ContentsInfo {
  
  ImageType getImageType();
  
  String getShopCode();
    
  String[] getSuffixies();
  
  String getUploadFileName();
  
}
