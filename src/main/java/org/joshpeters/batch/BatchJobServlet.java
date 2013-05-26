package org.joshpeters.batch;

import com.google.common.base.Preconditions;

import javax.batch.runtime.JobExecution;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet( urlPatterns = BatchJobServlet.URL_PATTERN, loadOnStartup = 1 )
public class BatchJobServlet
	extends HttpServlet
{
	public static final String URL_PATTERN = "/batch_jobs";
	@EJB
	private BatchExecutionBean batchExecutor;

	private static enum OperatorAction
	{
		START, RESTART, VIEW;
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse res
	)
	throws ServletException, IOException
	{
		long executionId = batchExecutor.submitJob( req.getParameter( "job_name" ) );
		this.write(
			String.format(
				"Batch execution %d is running",
				executionId
			), res
		);
	}

	@Override
	protected void doGet(
		HttpServletRequest req,
		HttpServletResponse res
	)
	throws ServletException, IOException
	{
		final String actionValue = req.getParameter( "action" );
		Preconditions.checkNotNull( actionValue );
		OperatorAction action = OperatorAction.valueOf( actionValue );

		long executionId = -1;

		if ( req.getParameter( "executionId" ) != null ) {
			executionId = Long.valueOf( req.getParameter( "executionId" ) );
		}

		switch ( action ) {
			case RESTART:
				executionId = batchExecutor.restartJob( executionId );
				this.write(
					String.format(
						"Batch execution %d is the result of a restart",
						executionId
					), res
				);
				break;
			case VIEW:
				JobExecution execution =
					batchExecutor.getJobExecutionDetails( executionId );
				this.write( "Execution Id \n " + execution, res );
				break;
		}

		String contextPath = req.getContextPath();

		this.write( "<h2>Options</h2>", res );

		this.writeLink(
			OperatorAction.VIEW,
			"View details for Execution Id "
				+ executionId, executionId, req, res
		);

		this.writeLink(
			OperatorAction.RESTART,
			"Restart Execution Id " + executionId, executionId, req, res
		);

		this.writeLink(
			OperatorAction.START,
			"Start a new job", null, req, res
		);
	}

	private void write(
		String message,
		HttpServletResponse res
	)
	throws IOException
	{

		res.setContentType( "text/html" );
		PrintWriter out = res.getWriter();
		out.println( message );
	}

	private void writeLink(
		OperatorAction action,
		String text,
		Long executionId,
		HttpServletRequest req,
		HttpServletResponse res
	)
	throws IOException
	{

		PrintWriter out = res.getWriter();
		StringBuilder sb = new StringBuilder();

		sb.append( "<a href=\"" )
			.append( req.getContextPath() )
			.append( URL_PATTERN )
			.append( "?action=" )
			.append( action.name() );

		if ( executionId != null ) {
			sb.append( "&executionId=" ).append( executionId );
		}

		sb.append( "\">" ).append( text ).append( "</a>" );

		out.println( sb.toString() );
		out.println( "<hr/>" );
	}
}