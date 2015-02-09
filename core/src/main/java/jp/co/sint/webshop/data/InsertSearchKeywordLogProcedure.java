package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class InsertSearchKeywordLogProcedure implements ProcedureDelegate {

  private String searchType;

  private String searchWord;

  private String updatedUser;

  private int result;

  public InsertSearchKeywordLogProcedure() {
  }

  public InsertSearchKeywordLogProcedure(String searchType, String searchWord, String updatedUser) {
    setSearchType(searchType);
    setSearchWord(searchWord);
    setUpdatedUser(updatedUser);
  }

  public void execute(CallableStatement statement) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    int index = 0;
    statement.setString(++index, getSearchType());
    statement.setString(++index, getSearchWord());
    statement.setString(++index, getUpdatedUser());
    //postgres start
    setResult(DatabaseUtil.getResultSet(statement, ++index));
    //postgres end
    if (getResult() != 0) {
      throw new SQLException("INSERT_SEARCH_KEYWORD_LOG Failed");
    } else {
      logger.info("executed successfully.");
    }
  }

  public String getStatement() {
    return "{CALL INSERT_SEARCH_KEYWORD_LOG(?, ?, ?, ?)}";
  }

  /**
   * searchTypeを返します。
   * 
   * @return the searchType
   */
  public String getSearchType() {
    return searchType;
  }

  /**
   * searchTypeを設定します。
   * 
   * @param searchType
   *          設定する searchType
   */
  public void setSearchType(String searchType) {
    this.searchType = searchType;
  }

  /**
   * searchWordを返します。
   * 
   * @return the searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  /**
   * searchWordを設定します。
   * 
   * @param searchWord
   *          設定する searchWord
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

  /**
   * updatedUserを返します。
   * 
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * updatedUserを設定します。
   * 
   * @param updatedUser
   *          設定する updatedUser
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * resultを返します。
   * 
   * @return the result
   */
  public int getResult() {
    return result;
  }

  /**
   * resultを設定します。
   * 
   * @param result
   *          設定する result
   */
  public void setResult(int result) {
    this.result = result;
  }

}
