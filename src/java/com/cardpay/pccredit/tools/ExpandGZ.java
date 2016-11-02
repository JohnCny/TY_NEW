package com.cardpay.pccredit.tools;

//Expand a .gz file into uncompressed form 
//Peter van der Linden, August 2001 
import java.io.*;
import java.util.zip.*;

public class ExpandGZ {

	public static boolean doExpand(String file){
		try {
			GZIPInputStream gzi = new GZIPInputStream(new FileInputStream(file));
			int to = file.lastIndexOf('.');
			if (to == -1) {
				System.out.println("usage:  java expandgz  filename.gz");
				//System.exit(0);
				return false;
			}
			String fout = file.substring(0, to);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(fout));

			System.out.println("writing " + fout);

			int b;
			do {
				b = gzi.read();
				if (b == -1)
					break;
				bos.write(b);
			} while (true);
			gzi.close();
			bos.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
