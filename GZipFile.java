package Implements;
/*
 * import java.io.FileWriter;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
*/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import Implements.Files;
import java.util.*;

public class GZipFile 
{
	private static final String INPUT_GZIP_FILE = "data/2016-01-01-03-59-59_Birmingham_Germany_TTI_External.csv.gz";
    private static final String OUTPUT_FILE = "data/FirstData.csv";
    
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
            	 
                int len;
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ",";
                String x;
                int y;
                Collection<Files> list = new ArrayList<Files>(); 
                
                while ((len = gzis.read(buffer)) > 0)
                {
                	out.write(buffer, 0, len);
                	
        		}
                try{
        			br = new BufferedReader(new FileReader(OUTPUT_FILE));
        			while ((line = br.readLine()) != null) {
        				String[] content = line.split(cvsSplitBy);
        				String[] intContent = line.split(cvsSplitBy);
        				if (content[0].startsWith("2016-") == true)
        				list.add(new Files(content[0],content[14],content[1],content[5]));
        			}
        		} catch(FileNotFoundException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		} finally {
        			if (br != null) {
        				try {
        					br.close();
        				} catch (IOException e) {
        					e.printStackTrace();
        				}
        			}
        		}
                
                gzis.close();
            	out.close();

            	System.out.println("Done");
            	System.out.println(list.size());
            	
        }
        catch(IOException ex)
        {
        	ex.printStackTrace();
        }
    }
}
