package com.explore.android.core.base;

public interface ExHttpRequest {
	
	/**
	 * 同步数据请求，用于从服务器端获取数据，需要时间比较长
	 * @param request 向服务器发送的数据，加密的JSON数据
	 * @param url 访问的对应操作的URL
	 */
	public void asynDataRequest(String url, String request, int what);
	
	/**
	 * 提交数据请求，用于向服务器端发送操作请求，时间比较短
	 * @param request 向服务器发送的数据，加密的JSON数据
	 * @param url 访问的对应操作的URL
	 */
	public void submitRequest(String url, String request, int what);
	
	/**
	 * 处理服务器返回的数据
	 * @param response 服务器端返回的数据
	 * @param what 请求的是什么数据（Int常量表示）
	 */
	public void handlerResponse(String response, final int what);
	
	/**
	 * 显示加载进度旋转
	 */
	public void showLoading();
	
	/**
	 * 隐藏加载进度旋转
	 */
	public void dismissLoading();
}
