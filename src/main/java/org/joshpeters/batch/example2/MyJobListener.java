package org.joshpeters.batch.example2;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import javax.batch.api.listener.JobListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyJobListener
implements JobListener
{
	private Stopwatch sw;
	@Inject
	JobContext jobContext;
	@Override
	public void beforeJob()
	throws Exception
	{
		log.info( "job properties {}", jobContext.getProperties() );
		sw = new Stopwatch().start();
	}

	@Override
	public void afterJob()
	throws Exception
	{
		log.info( "job '{}' took {} ms", jobContext.getJobName(), sw.elapsed( TimeUnit.MILLISECONDS ) );
	}
}
