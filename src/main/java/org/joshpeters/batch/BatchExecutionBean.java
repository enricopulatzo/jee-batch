package org.joshpeters.batch;

import javax.annotation.Nonnull;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.ejb.Stateless;
import java.util.Properties;

@Stateless
public class BatchExecutionBean
{
	public long submitJob( @Nonnull String jobName )
	{
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobProperties = new Properties();
		return jobOperator.start( jobName, jobProperties );
	}

	public JobExecution getJobExecutionDetails( long executionId )
	{
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		JobExecution jobExecution = jobOperator.getJobExecution( executionId );
		return jobExecution;
	}

	public long restartJob( long executionId )
	{
		Properties jobProperties = new Properties();
		long newExecutionId =
			BatchRuntime.getJobOperator().restart( executionId, jobProperties );
		return newExecutionId;
	}
}