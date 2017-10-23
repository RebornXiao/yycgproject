package com.zzvcom.sysmag.persistence.dialect;

public class OracleDialect implements Dialect {
    protected static final String SQL_END_DELIMITER = ";";

    public String getLimitString(String sql, boolean hasOffset) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getLimitString(String sql, int offset, int limit) {
        //System.out.println("sql in:"+sql);
        sql = this.trim(sql);
        StringBuilder sb = new StringBuilder();
        int start_c = offset + 1;
        int end_c = start_c + limit;
        sb.append(" SELECT * FROM ( SELECT A.*,rownum numrow FROM ( ").append(
                sql).append(") A WHERE rownum < ").append(end_c).append(
                " ) B WHERE numrow >= ").append(start_c).append(" ");
        //System.out.println("sql out:"+sb.toString());
        return sb.toString();
    }

    public boolean supportsLimit() {
        // TODO Auto-generated method stub
        return true;
    }
    
    private String trim(String sql) {
        sql = sql.trim();
        if (sql.endsWith(SQL_END_DELIMITER)) {
            sql = sql.substring(0, sql.length() - 1
                    - SQL_END_DELIMITER.length());
        }
        return sql;
    }

}
