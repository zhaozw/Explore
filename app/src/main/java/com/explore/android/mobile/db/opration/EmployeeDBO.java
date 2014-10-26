package com.explore.android.mobile.db.opration;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.explore.android.core.base.BaseDBO;
import com.explore.android.core.util.QuerySql;
import com.explore.android.mobile.db.DB;
import com.explore.android.mobile.db.DBManager;
import com.explore.android.mobile.model.Employee;

public class EmployeeDBO extends BaseDBO {

	private SQLiteDatabase db;
	private DBManager dbManager;

	public EmployeeDBO(Context context) {
		dbManager = new DBManager(context);
	}

	public long save(Employee emp) {
		ContentValues contentValues = createEmpValues(emp);
		return db.insert(DB.TABLE_NAME_EMPLOYEE, null, contentValues);
	}

	public int importEmps(List<Employee> emps) {

		db.beginTransaction();

		try {
			dbManager.cleanEmployee(db);

			for (int i = 0; i < emps.size(); i++) {
				save(emps.get(i));
			}

			db.setTransactionSuccessful();
			return SUCCESS;

		} catch (Exception e) {

		} finally {
			db.endTransaction();
		}

		return FAILED;
	}

	public int importEmps(JSONObject json, String dataName) {
		
		db.beginTransaction();
		
		try {
			
			dbManager.cleanEmployee(db);
			
			JSONArray jsonArray = json.getJSONArray(dataName);
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				Employee employee = new Employee();
				employee.setUid(jsonObject.getString("uid"));
				employee.setEid(jsonObject.getString("eid"));
				employee.setName(jsonObject.getString(DB.EMPLOYEE_NAME));
				employee.setGender(jsonObject.getString(DB.EMPLOYEE_GENDER));
				employee.setMobile(jsonObject.getString(DB.EMPLOYEE_PHONE));
				employee.setEmail(jsonObject.getString(DB.EMPLOYEE_EMAIL));
				employee.setDepartment(jsonObject.getString(DB.EMPLOYEE_DEPARTMENT));
				employee.setPosition(jsonObject.getString(DB.EMPLOYEE_POSITION));
				employee.setPositionLevel(jsonObject.getString("positionLevel"));
				save(employee);
			}
			db.setTransactionSuccessful();
			return SUCCESS;
		} catch (JSONException e) {
			e.printStackTrace();
		} finally{
			db.endTransaction();
		}
		
		return FAILED;
	}

	public Employee loadById(long id) {
		QuerySql query = new QuerySql(DB.TABLE_NAME_EMPLOYEE);
		query.addCondition(DB.EMPLOYEE_ID + "=" + id);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if (cursor.getCount() == 0 || cursor.getCount() > 1) {
			cursor.close();
		} else {
			Employee emp = new Employee();
			emp = setEmployeeValues(cursor);
			cursor.close();
			return emp;
		}
		return null;
	}
	
	public List<Employee> getEmployees(){
		List<Employee> list = new ArrayList<Employee>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_EMPLOYEE);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while(cursor.moveToNext()){
			Employee emp = setEmployeeValues(cursor);
			list.add(emp);
		}
		cursor.close();
		return list;
	}
	
	public int getEmpCount(){
		QuerySql query = new QuerySql(DB.TABLE_NAME_EMPLOYEE);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		return cursor.getCount();
	}
	
	private Employee setEmployeeValues(Cursor cursor){
		Employee emp = new Employee();
		emp.setEmployeeId(cursor.getInt(cursor.getColumnIndex(DB.EMPLOYEE_ID)));
		emp.setEid(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_EID)));
		emp.setUid(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_UID)));
		emp.setName(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_NAME)));
		emp.setGender(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_GENDER)));
		emp.setMobile(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_PHONE)));
		emp.setEmail(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_EMAIL)));
		emp.setDepartment(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_DEPARTMENT)));
		emp.setPosition(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_POSITION)));
		emp.setPositionLevel(cursor.getString(cursor.getColumnIndex(DB.EMPLOYEE_POSITIONLEVEL)));
		return emp;
	}

	private ContentValues createEmpValues(Employee emp) {
		ContentValues values = new ContentValues();
		values.put(DB.EMPLOYEE_UID, emp.getUid());
		values.put(DB.EMPLOYEE_EID, emp.getEid());
		values.put(DB.EMPLOYEE_NAME, emp.getName());
		values.put(DB.EMPLOYEE_GENDER, emp.getGender());
		values.put(DB.EMPLOYEE_PHONE, emp.getMobile());
		values.put(DB.EMPLOYEE_EMAIL, emp.getEmail());
		values.put(DB.EMPLOYEE_DEPARTMENT, emp.getDepartment());
		values.put(DB.EMPLOYEE_POSITION, emp.getPosition());
		values.put(DB.EMPLOYEE_POSITIONLEVEL, emp.getPositionLevel());
		return values;
	}

	@Override
	public BaseDBO open() {
		db = dbManager.getWritableDatabase();
		return this;
	}

	@Override
	public void close() {
		dbManager.close();
	}

}
