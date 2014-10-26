package com.explore.android.mobile.model;

public class ExMessage {

	private int msgId;
	private int infoId;
	private String type;
	private String title;
	private String content;
	private int createBy;
	private String createByTime;
	private String createByName;
	private String sts;
	private String sts2;
	private String sendByTime;
	private String confirmByTime;

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public String getCreateByTime() {
		return createByTime;
	}

	public void setCreateByTime(String createByTime) {
		this.createByTime = createByTime;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getSts2() {
		return sts2;
	}

	public void setSts2(String sts2) {
		this.sts2 = sts2;
	}

	public String getSendByTime() {
		return sendByTime;
	}

	public void setSendByTime(String sendByTime) {
		this.sendByTime = sendByTime;
	}

	public String getConfirmByTime() {
		return confirmByTime;
	}

	public void setConfirmByTime(String confirmByTime) {
		this.confirmByTime = confirmByTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
