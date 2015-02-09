package jp.co.sint.webshop.service.tmall;

import java.util.List;


public interface TmallSearchOrderDownloadNumService {
  
  List<String> searchOrderDownLoadNums(String startTime, String endTime);
  
}
