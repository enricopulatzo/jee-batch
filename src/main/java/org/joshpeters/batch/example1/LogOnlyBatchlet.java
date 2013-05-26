package org.joshpeters.batch.example1;

import lombok.extern.slf4j.Slf4j;

import javax.batch.api.Batchlet;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

@Slf4j
public class LogOnlyBatchlet
	implements Batchlet
{
	@Inject
	JobContext jobContext;

	@Inject
	StepContext stepContext;

	@Override
	public String process()
	throws Exception
	{
		log.info( "jobContext: {}", jobContext );
		log.info( "stepContext: {}", stepContext );
		return "SUCCESS";
	}

	@Override
	public void stop()
	throws Exception
	{
		log.info( "stop?!? why should we stop?!?" );
	}
}
