package jp.co.sint.webshop.web.menu.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.web.webutility.RequestParameter;

public class BackMenuRequestParameter implements RequestParameter {

  public void copy(Object obj) {
  }

  public String get(String argName) {
    return "";
  }

  public String[] getAll(String argName) {
    return new String[0];
  }

  public String getDateString(String name) {
    return "";
  }

  public String getDateTimeString(String argName) {
    return "";
  }

  public List<Map<String, String>> getListData(String... nameList) {
    return new ArrayList<Map<String, String>>();
  }

  public Map<String, String> getListData(int index, String... nameList) {
    return new HashMap<String, String>();
  }

  public Map<String, String> getListDataWithKey(String key, String... nameList) {
    return new HashMap<String, String>();
  }

  public String[] getPathArgs() {
    return new String[0];
  }

  public String[] getPostalCode(String name) {
    return new String[0];
  }

  public String[] getPostalCode(String name, int index) {
    return new String[0];
  }

  public boolean isEmpty() {
    return false;
  }

  public int size() {
    return 0;
  }

  public String getContextPath() {
    return "";
  }

  @Override
  public Map<String, String[]> getRequestMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isGetFlg() {
    // TODO Auto-generated method stub
    return false;
  }
}
