package org.joshpeters.batch.example2;

import lombok.extern.slf4j.Slf4j;

import javax.batch.api.chunk.ItemWriter;
import java.io.Serializable;
import java.util.List;

@Slf4j
public class MyWriter
implements ItemWriter
{
	@Override
	public void open( final Serializable checkpoint )
	throws Exception
	{
		log.info( "open( {} )", checkpoint );
	}

	@Override
	public void close()
	throws Exception
	{
		log.info( "close()" );
	}

	@Override
	public void writeItems( final List<Object> items )
	throws Exception
	{
		log.info( "writeItems( {} )", items );
	}

	@Override
	public Serializable checkpointInfo()
	throws Exception
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}