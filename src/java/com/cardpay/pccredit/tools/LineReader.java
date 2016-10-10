package com.cardpay.pccredit.tools;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*LineReader lr = null;
try {
	lr = new LineReader(new InputStreamReader(new FileInputStream(
			fileName), yourFileEncode));
	//指定换行符
	lr.setLineSeparator("\n");
	String line;
	while ((line = lr.readLine()) != null) {
		System.out.println("'" + line + "'");
	}
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} finally {
	if (lr != null) {
		try {
			lr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}			
}*/

public class LineReader {

	private static final int BUFFER_SIZE = 4096;
	private Reader in;
	private int bufferSize;
	private List<String> bufferedLines;
	private String lineSeparator;
	private String preString;

	public LineReader(Reader in) {
		if (in == null) {
			throw new IllegalArgumentException("in is null");
		}
		this.in = in;
		bufferedLines = new ArrayList<String>();
		lineSeparator = System.getProperty("line.separator");
		bufferSize = BUFFER_SIZE;
	}

	public void close() throws IOException {
		in.close();
	}

	public void setBufferSize(int bufferSize) {
		if (bufferSize <= 0) {
			throw new IllegalArgumentException("bufferSize <= 0");
		}
		this.bufferSize = bufferSize;
	}

	public void setLineSeparator(String lineSeparator) {
		if (lineSeparator == null) {
			throw new IllegalArgumentException("lineSeparator is null");
		}
		this.lineSeparator = lineSeparator;
	}

	public String readLine() throws IOException {
		if (bufferedLines.size() > 1) {
			return bufferedLines.remove(0);
		} else if (bufferedLines.size() == 1) {
			preString = bufferedLines.remove(0);
			return readLine();
		} else {
			char[] src = new char[bufferSize];
			int len;
			if ((len = in.read(src)) != -1) {
				String bufferString = new String(src, 0, len);
				if (preString != null) {
					bufferString = preString + bufferString;
					preString = null;
				}
				boolean returnDelims = false;
				StringTokenizer token = new StringTokenizer(bufferString,
						lineSeparator, returnDelims);
				while (token.hasMoreTokens()) {
					bufferedLines.add(token.nextToken());
				}
				if (bufferString.endsWith(lineSeparator)) {
					bufferedLines.add("");
				}
				return readLine();
			} else {
				if (preString != null) {
					String ret = preString;
					preString = null;
					return ret;
				} else {
					return null;	
				}				
			}
		}
	}
}
