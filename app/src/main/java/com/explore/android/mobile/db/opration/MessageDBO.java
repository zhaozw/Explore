package com.explore.android.mobile.db.opration;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.explore.android.core.base.BaseDBO;
import com.explore.android.core.util.QuerySql;
import com.explore.android.mobile.db.DB;
import com.explore.android.mobile.db.DBManager;
import com.explore.android.mobile.model.ExMessage;

public class MessageDBO extends BaseDBO{

	private SQLiteDatabase db;
	private DBManager dbManager;
	
	public MessageDBO(Context context){
		dbManager = new DBManager(context);
	}
	
	public long save(ExMessage message){
		ContentValues contentValues = createMsgValues(message);
		return db.insert(DB.TABLE_NAME_MESSAGE, null, contentValues);
	}
	
	public int addMessages(List<ExMessage> msgs){
		db.beginTransaction();
		try {
			for (int i = 0; i < msgs.size(); i++) {
				save(msgs.get(i));
			}
			db.setTransactionSuccessful();
			return SUCCESS;

		} catch (Exception e) {

		} finally {
			db.endTransaction();
		}
		return FAILED;
	}
	
	public ExMessage loadById(long id) {
		QuerySql query = new QuerySql(DB.TABLE_NAME_MESSAGE);
		query.addCondition(DB.MESSAGE_ID + "=" + id);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if (cursor.getCount() == 0 || cursor.getCount() > 1) {
			cursor.close();
		} else {
			ExMessage msg = new ExMessage();
			msg = setMessageValues(cursor);
			cursor.close();
			return msg;
		}
		return null;
	}
	
	public ExMessage loadByInfoId(int infoId){
		QuerySql query = new QuerySql(DB.TABLE_NAME_MESSAGE);
		query.addCondition(DB.MESSAGE_INFOID + "=" + infoId);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if (cursor.getCount() == 0 || cursor.getCount() > 1) {
			cursor.close();
		} else {
			cursor.moveToFirst();
			ExMessage msg = new ExMessage();
			msg = setMessageValues(cursor);
			cursor.close();
			return msg;
		}
		return null;
	}
	
	public List<ExMessage> getMessages(){
		List<ExMessage> list = new ArrayList<ExMessage>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_MESSAGE);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while(cursor.moveToNext()){
			ExMessage msg = setMessageValues(cursor);
			list.add(msg);
		}
		cursor.close();
		return list;
	}
	
	public int getMsgCount(){
		QuerySql query = new QuerySql(DB.TABLE_NAME_MESSAGE);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		return cursor.getCount();
	}
	
	private ContentValues createMsgValues(ExMessage msg) {
		ContentValues values = new ContentValues();
		values.put(DB.MESSAGE_INFOID, msg.getInfoId());
		values.put(DB.MESSAGE_TYPE, msg.getType());
		values.put(DB.MESSAGE_TITLE, msg.getTitle());
		values.put(DB.MESSAGE_CONTENT, msg.getContent());
		values.put(DB.MESSAGE_CREATEBY, msg.getCreateBy());
		values.put(DB.MESSAGE_CREATEBYNAME, msg.getCreateByName());
		values.put(DB.MESSAGE_CREATEBYTIME, msg.getCreateByTime());
		values.put(DB.MESSAGE_STS, msg.getSts());
		values.put(DB.MESSAGE_STS2, msg.getSts2());
		values.put(DB.MESSAGE_SENDBYTIME, msg.getSendByTime());
		values.put(DB.MESSAGE_CONFIRMBYTIME, msg.getConfirmByTime());
		return values;
	}
	
	private ExMessage setMessageValues(Cursor cursor){
		ExMessage msg = new ExMessage();
		msg.setMsgId(cursor.getInt(cursor.getColumnIndex(DB.MESSAGE_ID)));
		msg.setInfoId(cursor.getInt(cursor.getColumnIndex(DB.MESSAGE_INFOID)));
		msg.setType(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_TYPE)));
		msg.setTitle(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_TITLE)));
		msg.setContent(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_CONTENT)));
		msg.setCreateBy(cursor.getInt(cursor.getColumnIndex(DB.MESSAGE_CREATEBY)));
		msg.setCreateByTime(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_CREATEBYTIME)));
		msg.setCreateByName(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_CREATEBYNAME)));
		msg.setSts(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_STS)));
		msg.setSts2(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_STS2)));
		msg.setSendByTime(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_SENDBYTIME)));
		msg.setConfirmByTime(cursor.getString(cursor.getColumnIndex(DB.MESSAGE_CONFIRMBYTIME)));
		return msg;
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
	
	public void clearMessage(){
		dbManager.cleanMessage(db);
	}

}
