package com.zzvcom.sysmag.persistence;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.util.ReflectUtil;

public abstract class BaseDaoiBatis extends SqlMapClientDaoSupport {
    private SqlExecutor sqlExecutor;

    public SqlExecutor getSqlExecutor() {
        return sqlExecutor;
    }

    public void setSqlExecutor(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public void setEnableLimit(boolean enableLimit) {
        if (sqlExecutor instanceof LimitSqlExecutor) {
            ((LimitSqlExecutor) sqlExecutor).setEnableLimit(enableLimit);
        }
    }

    @SuppressWarnings("deprecation")
    public void initialize() throws Exception {
        if (sqlExecutor != null) {
            SqlMapClient sqlMapClient = getSqlMapClientTemplate()
                    .getSqlMapClient();
            if (sqlMapClient instanceof ExtendedSqlMapClient) {
                ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient)
                        .getDelegate(), "sqlExecutor", SqlExecutor.class,
                        sqlExecutor);
            }
        }
    }
    
    /**
     * 得到分页结果集信息（包括单页结果集列表和记录总数）
     * @param sqlMapId ibatis的xml配置sqlMapId
     * @param start 开始位置
     * @param pageSize 每页条数限制
     * @return
     */
    public PagingResultDTO getPagingResultMap(String statementName, Object parameterObject, int start, int limit ) {
        String sqlMapIdForResultTotalCount = statementName+"_count";
        List resultList = getSqlMapClientTemplate().queryForList(statementName, parameterObject, start, limit);
        Integer totalCount = (Integer)getSqlMapClientTemplate().queryForObject(sqlMapIdForResultTotalCount, parameterObject);
        PagingResultDTO pagingResult = new PagingResultDTO();
        pagingResult.setResultList(resultList);
        pagingResult.setTotalCount(totalCount.intValue());
        return pagingResult;
    }
    
}
