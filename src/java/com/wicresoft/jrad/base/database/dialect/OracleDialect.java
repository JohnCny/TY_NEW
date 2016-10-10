/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.base.database.dialect;


/**
 * Description of OracleDialet
 * 
 * @author Vincent
 * @created on Apr 21, 2014
 * 
 * @version $Id: OracleDialect.java 1730 2014-11-26 09:50:57Z steven $
 */
public class OracleDialect extends AbstractDialect {

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public String getLimitSql(String sql, int offset, int limit) {

		boolean hasOffset = offset > 0;

		sql = sql.trim();
		String forUpdateClause = null;
		boolean isForUpdate = false;
		final int forUpdateIndex = sql.toLowerCase().lastIndexOf("for update");
		if (forUpdateIndex > -1) {
			// save 'for update ...' and then remove it
			forUpdateClause = sql.substring(forUpdateIndex);
			sql = sql.substring(0, forUpdateIndex - 1);
			isForUpdate = true;
		}

		final StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
		if (hasOffset) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}
		else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect.append(" ) row_ where rownum <= ${limit} + ${offset}) where rownum_ > ${offset}");
		}
		else {
			pagingSelect.append(" ) where rownum <= ${limit}");
		}

		if (isForUpdate) {
			pagingSelect.append(" ");
			pagingSelect.append(forUpdateClause);
		}

		String newSql = pagingSelect.toString();
		newSql = newSql.replaceAll("\\$\\{limit\\}", String.valueOf(limit));
		newSql = newSql.replaceAll("\\$\\{offset\\}", String.valueOf(offset));

		return newSql;
	}

	public static void main(String[] args) {
		OracleDialect dialect = new OracleDialect();
		System.out.println(dialect.getLimitSql("select * from sample where name=?", 5, 10));
		System.out.println(dialect.getLimitSql("select * from sample where id=? for update", 5, 10));
	}
}
