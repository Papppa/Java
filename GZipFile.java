package Implements;
/*
 * 
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
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.util.Collection;

public class GZipFile 
{	
	//Path to csv-File
	private static final String INPUT_GZIP_FILE = "data/2016-01-01-03-59-59_Birmingham_Germany_TTI_External.csv.gz";
    private static final String OUTPUT_FILE = "data/FirstData.csv";
    private static final String NEW_FILE = "data/Jams.csv";
    //Delimiter for new csv File
    private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
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
                Collection<Files> list = new ArrayList<Files>();
                FileWriter fileWriter = null;
                while ((len = gzis.read(buffer)) > 0)
                {
                	out.write(buffer, 0, len);
        		}
                try{
        			br = new BufferedReader(new FileReader(OUTPUT_FILE));
        			fileWriter = new FileWriter(NEW_FILE);
        			while ((line = br.readLine()) != null) {
        				String[] content = line.split(cvsSplitBy);
        				//String[] intContent = line.split(cvsSplitBy);
        				//if (content[0] != null)
        				list.add(new Files(content[0],content[14],content[1],content[5]));
        			}
        			for (Files file : list){
    					fileWriter.append(file.getTime());
    		    		fileWriter.append(COMMA_DELIMITER);
    		    		fileWriter.append(file.getRoadNumber());  		    		
    		    		fileWriter.append(COMMA_DELIMITER);
    		    		fileWriter.append(file.getDelay());
    		    		fileWriter.append(COMMA_DELIMITER);
    		    		fileWriter.append(file.getLength());
    		    		fileWriter.append(NEW_LINE_SEPARATOR);
    				}
        		} catch(FileNotFoundException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		} finally {
        			if (br != null) {
        				try {
        					br.close();
        					fileWriter.flush();
            				fileWriter.close();
        				} catch (IOException e) {
        					e.printStackTrace();
        				}
        			}
        		}
                
                gzis.close();
            	out.close();

            	System.out.println("Done");
            	System.out.println("Größe der File-Collection: " + list.size());       	
        }
        catch(IOException ex)
        {
        	ex.printStackTrace();
        }
    }
}
