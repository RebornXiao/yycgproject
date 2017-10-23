package com.zzvcom.sysmag.persistence.dialect;

//数据库方言接口
public interface Dialect {
    public boolean supportsLimit();
    public String getLimitString(String sql, boolean hasOffset);
    public String getLimitString(String sql, int offset, int limit);
}
