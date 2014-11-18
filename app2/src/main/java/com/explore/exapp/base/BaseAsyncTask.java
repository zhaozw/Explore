package com.explore.exapp.base;

import android.os.AsyncTask;

public abstract class BaseAsyncTask<Params, Progress, Result> extends
		AsyncTask<Params, Progress, Result> {

	private TaskOnCancelledListener taskOnCancelledListener;

	private TaskOnPostExecuteListener<? super Result> taskOnPostExecuteListener;

	private TaskOnProgressUpdateListener<Progress> taskOnProgressUpdateListener;

	private TaskOnPreExecuteListener taskOnPreExecuteListener;
	
	/**
	 * 取消加载时处理的时间
	 * @param taskOnCancelledListener
	 */
	public void setTaskOnCancelledListener(
			TaskOnCancelledListener taskOnCancelledListener) {
		this.taskOnCancelledListener = taskOnCancelledListener;
	}

	/**
	 * 操作完成之后进行的操作
	 * @param taskOnPostExecuteListener
	 */
	public void setTaskOnPostExecuteListener(
			TaskOnPostExecuteListener<? super Result> taskOnPostExecuteListener) {
		this.taskOnPostExecuteListener = taskOnPostExecuteListener;
	}

	/**
	 * 加载数据或进行处理时的动作
	 * @param taskOnProgressUpdateListener
	 */
	public void setTaskOnProgressUpdateListener(
			TaskOnProgressUpdateListener<Progress> taskOnProgressUpdateListener) {
		this.taskOnProgressUpdateListener = taskOnProgressUpdateListener;
	}

	/**
	 * 执行动作预处理
	 * @param taskOnPreExecuteListener
	 */
	public void setTaskOnPreExecuteListener(
			TaskOnPreExecuteListener taskOnPreExecuteListener) {
		this.taskOnPreExecuteListener = taskOnPreExecuteListener;
	}

	@Override
	public void onCancelled() {
		if (taskOnCancelledListener != null) {
			taskOnCancelledListener.onCancelled();
		}
	}

	@Override
	public void onPostExecute(Result result) {
		if (taskOnPostExecuteListener != null) {
			taskOnPostExecuteListener.onPostExecute(new TaskResult<Result>(this, result, STATUS_SUCCESS));
		}
	}

	@Override
	public void onPreExecute() {
		if(taskOnPreExecuteListener!= null){
			taskOnPreExecuteListener.onPreExecute();
		}
	}

	@Override
	public void onProgressUpdate(Progress... values) {
		if(taskOnProgressUpdateListener != null){
			taskOnProgressUpdateListener.onProgressUpdate(values);
		}
	}

	public static interface TaskOnPreExecuteListener {
		public void onPreExecute();
	}

	public static interface TaskOnProgressUpdateListener<Progress> {
		public void onProgressUpdate(Progress... values);
	}

	public static interface TaskOnCancelledListener {
		public void onCancelled();
	}

	public static interface TaskOnPostExecuteListener<T> {
		public void onPostExecute(final TaskResult<? extends T> result);
	}

	public static final int STATUS_SUCCESS = 0;

	public static final int STATUS_CANCEL = -1;

	public static final int STATUS_FAILE = 2;

	public static class TaskResult<Result> {

		public final Result Value;

		public final BaseAsyncTask<?, ?, Result> Task;

		public final int Status;

		public TaskResult(BaseAsyncTask<?, ?, Result> task, Result value,
				int status) {
			this.Value = value;
			this.Task = task;
			this.Status = status;
		}
	}

}
