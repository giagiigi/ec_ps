package jp.co.sint.webshop.service.result;

public enum DataIOServiceErrorContent implements ServiceErrorContent {
  /** コンテンツアップロードエラー */
  CONTENTS_UPLOAD_ERROR,
  /** コンテンツアップロード拡張子エラー */
  CONTENTS_EXTENSION_ERROR,
  /** ZIP解凍エラー */
  ZIPMELT_ERROR,
  /** コンテンツファイル名不正(日本語混在) */
  FILENAME_INCLUDE_JAPANESE_ERROR,
  /** コンテンツ未存在エラー */
  CONTENTS_NOEXSITS,
  /** コンテンツ削除エラー */
  CONTENTS_DELETE_ERROR,
  /** ファイル名命名規約エラー */
  CONTENTS_FILE_NAME_ERROR,
  /** ディレクトリ作成失敗エラー */
  DIRECTORY_CREATION_ERROR,
  /**ファイル移動エラー*/
  FILE_MOVE_ERROR,
  /** 対象ファイル削除失敗エラー */
  FILE_DELETE_ERROR,
  /** 対象ファイル上書き失敗エラー */
  FILE_OVERWRITE_FAILURE_ERROR;

}
