package com.explore.android.core.util;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class QuerySql {

	private static final String SELECT_HEADER = "SELECT * FROM ";

	private String tableName;
	private List<String> conditions = new ArrayList<String>();
	private String orderBy = "";
	private String groupBy = "";
	private int numPage;
	private int pageNo;

	public QuerySql(String tname) {
		this.tableName = tname;
	}

	public String getQuerySql() {

		StringBuffer sql = new StringBuffer(SELECT_HEADER);

		sql.append(tableName);

		if (conditions != null && conditions.size() > 0) {

			sql.append(" WHERE ");

			for (int i = 0; i < conditions.size(); i++) {

				sql.append(" " + conditions.get(i));

			}
		}

		if (groupBy != null && !("").equals(groupBy)) {
			sql.append(" GROUP BY " + groupBy);
		}

		if (orderBy != null && !("").equals(orderBy)) {
			sql.append(" ORDER BY " + orderBy);
		}

		if (numPage > 0 && pageNo > 0) {
			sql.append(" LIMIT " + numPage + " OFFSET " + numPage * pageNo);
		}

		Log.i("Database QuerySql:", sql.toString());

		return sql.toString();
	}

	public void addCondition(String con) {
		this.conditions.add(con);
	}

	public void addConditionAnd(String con) {
		this.conditions.add("AND " + con);
	}

	public void addConditionOr(String con) {
		this.conditions.add("OR " + con);
	}

	public void addConditionBetween(String con, String con1, String con2) {
		if (this.conditions.size() == 0) {
			this.conditions.add(con + " BETWEEN " + con1 + " AND " + con2);
		} else {
			this.conditions.add("AND " + con + " BETWEEN " + con1 + " AND "
					+ con2);
		}
	}

	public void setOrderByDESC(String col) {
		this.orderBy = col + " DESC";
	}

	public void setOrderByASC(String col) {
		this.orderBy = col + " ASC";
	}

	public void setGroupBy(String str) {
		this.groupBy = str;
	}

	public void setDataPage(int num, int no) {
		this.numPage = num;
		this.pageNo = no;
	}
}
