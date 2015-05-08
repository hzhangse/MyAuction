package com.train.auction.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CheckWinnerJob extends QuartzJobBean {
	private CheckWiner task;
	public CheckWiner getTask() {
		return task;
	}
	public void setTask(CheckWiner task) {
		this.task = task;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		task.run();
	}

}
