package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class ZsIfCustErpExportCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public ZsIfCustErpExportCsvSchema() {
    getColumns().add(
        new CsvColumnImpl("CUSTOMER_CODE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.0"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("LAST_NAME", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ADDR1", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ADDR2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ADDR3", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CITY", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.5"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_STATE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.6"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ZIP", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.7"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CTRY", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.8"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_COUNTY", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.9"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ATTN", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ATTN2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.11"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PHONE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.12"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_EXT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.13"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PHONE2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.14"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_EXT2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.15"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_FAX", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.16"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_FAX2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.17"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_RESALE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.18"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("EMAIL", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.19"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CUST_STAT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.20"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PLAN_DATE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.21"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SALES_CH", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.22"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ORD_LIMIT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.23"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_DEL_AREA", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.24"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SHIPTO1", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.25"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SHIPTO2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.26"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SHIPTO3", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.27"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_INVOICE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.28"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PAY_MTHD", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.29"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CR_TERMS", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.30"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BILL", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.31"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PST_ID", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.32"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BANK", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.33"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ACCT_TYPE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.34"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_EDI", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.35"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BRANCH", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.36"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BK_ACCT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.37"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BEG_DATE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.38"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_END_DATE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.39"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CAT_FLAG", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.40"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_EMAIL_FLAG", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.41"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_FAX_FLAG", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.42"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_TEL_FLAG", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.43"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_VISIT_FLAG", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.44"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_DM_FLAG", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.45"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_TYPE_IND", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.46"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_COMP_SIZE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.47"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_OFF_SIZE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.48"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CUST_CLASS", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.49"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_TYPE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.50"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_EST_YR", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.51"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_RMKS1", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.52"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_RMKS2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.53"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_RMKS3", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.54"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_TAXABLE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.55"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_ZONE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.56"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CLASS", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.57"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_USAGE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.58"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_TAX_IN", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.59"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_GST_ID", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.60"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_MISC1_ID", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.61"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_MISC2_ID", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.62"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_MISC3_ID", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.63"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_TX_IN_CITY", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.64"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SORT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.65"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SLSPSN1", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.66"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_MULTI", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.67"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SLSPSN2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.68"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SLSPSN3", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.69"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SLSPSN4", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.70"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SHIPVIA", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.71"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PARTIAL", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.72"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_FR_LIST", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.73"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_FR_TERMS", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.74"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_AR_ACCT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.75"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_AR_SUB", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.76"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_AR_CC", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.77"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SITE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.78"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CURR", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.79"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PR_LIST2", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.80"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_FIX_PR", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.81"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PR_LIST", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.82"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_DISC_PCT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.83"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CR_LIMIT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.84"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_FIN", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.85"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_DUN", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.86"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_STMT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.87"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_STMT_CYC", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.88"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CR_REVIEW", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.89"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_CR_UPDATE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.90"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_REGION", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.91"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_HIGH_CR", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.92"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_PAY_DATE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.93"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BTB_TYPE", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.94"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_SHIP_LT", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.95"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BTB_MTHD", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.96"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("XXCM_BTB_CR", Messages.getCsvKey("service.data.csv.ZsIfCustErpExportCsvSchema.97"), CsvDataType.STRING));
  }
  public String getExportConfigureId() {
    return "ZsIfCustErpExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }
}
