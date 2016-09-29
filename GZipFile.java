package Implements;

import java.io.FileInputStream;
import Implements.CSVReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import Implements.FileElmts;

public class GZipFile 
{
	private static final String INPUT_GZIP_FILE = "data/1.gz";
    private static final String OUTPUT_FILE = "data/10.csv";
    
    public static void main(String[] args )
    {
    	GZipFile gZip = new GZipFile();
    	gZip.gunzipIt();
    }
    
    public void gunzipIt()
    {
    	byte[] buffer = new byte[1024];

        try
        {
        	GZIPInputStream gzis =
            		new GZIPInputStream(new FileInputStream(INPUT_GZIP_FILE));

            	 FileOutputStream out =
                    new FileOutputStream(OUTPUT_FILE);

                int len = gzis.read(buffer);
                
                System.out.println(buffer);
                
                /*
                while (len > 0)
                {
                	out.write(buffer, 0, len);
                }
                gzis.close();
            	out.close();

            	System.out.println("Done");
            	*/
            	
        }
        catch(IOException ex)
        {
        	ex.printStackTrace();
        }
    }
}
