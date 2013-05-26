package org.joshpeters.batch.example2;

import lombok.extern.slf4j.Slf4j;

import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import java.io.Serializable;

@Slf4j
public class MyReader
	implements ItemReader
{
	@Inject
	StepContext stepContext;

	private int maxCount = 25;
	private int current = 0;
	private String itemTemplate;

	@Override
	public void open( final Serializable checkpoint )
	throws Exception
	{
		log.info( "open( {} )", checkpoint );
		log.info( "step context properties {}", stepContext.getProperties() );
		itemTemplate = stepContext.getProperties().getProperty( "itemTemplate" );
	}

	@Override
	public void close()
	throws Exception
	{
		log.info( "close()" );
	}

	@Override
	public Object readItem()
	throws Exception
	{
		log.info( "readItem()" );
		if ( current >= maxCount ) {
			return null;
		}
		current++;
		return String.format( "%1$s %2$d", itemTemplate, current );
	}

	@Override
	public Serializable checkpointInfo()
	throws Exception
	{
		log.info( "checkpointInfo()" );
		return "some checkpoint info";
	}
}
