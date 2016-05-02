/*******************************************************************************
 * Copyright (c) 2016 Chen Chao(cnfree2000@hotmail.com).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Chen Chao  - initial API and implementation
 *******************************************************************************/

package org.sf.feeling.decompiler.jad;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

class StreamRedirectThread extends Thread
{

	private final Reader in;
	private final Writer out;
	private Exception ex;

	private static final int BUFFER_SIZE = 2048;

	StreamRedirectThread( String name, InputStream in, Writer out )
	{
		super( name );
		this.in = new InputStreamReader( in );
		this.out = out;
		setPriority( Thread.MAX_PRIORITY - 1 );
	}

	public Exception getException( )
	{
		return ex;
	}

	/**
	 * Copy.
	 */
	public void run( )
	{
		try
		{
			char[] cbuf = new char[BUFFER_SIZE];
			int count;
			while ( ( count = in.read( cbuf, 0, BUFFER_SIZE ) ) >= 0 )
			{
				out.write( cbuf, 0, count );
				out.flush( );
			}
		}
		catch ( IOException exc )
		{
			// System.err.println("Child I/O Transfer - " + exc);
			ex = exc;
		}
	}
}