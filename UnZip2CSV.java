package Implements;
import Implements.Files;
import Implements.AggFiles;
import Implements.Roads;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Iterator;

public class UnZip2CSV 
{	
	//Path to csv-File
	private static final String INPUT_GZIP_FILE = "data/2016-01-01-03-59-59_Birmingham_Germany_TTI_External.csv.gz";
    private static final String OUTPUT_FILE = "data/testData.csv";
    private static final String JAM_FILE = "data/Jams_agg.csv";
    //Delimiter for new csv File
    private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	//Header for csv File
	private static final String FILE_HEADER = "Date/Time, RoadNumber, Delay[s], Length[m]";
    public static void main(String[] args )
    {
    	UnZip2CSV gZip = new UnZip2CSV();
    	gZip.gunzipIt();
    	
    }
    ArrayList<Files> list = new ArrayList<Files>();
    ArrayList<AggFiles> aggList = new ArrayList<AggFiles>();
    HashSet<String> roadSet = new HashSet<String>();
    LinkedList<String> tsLList = new LinkedList<String>();
    
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
                int tsCounter = 0;
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ",";
                FileWriter fileWriter = null;
                //Counter
                	// for listed timestamps
                int countTS = 0;
                int intTS = 0;
                	// for datasets without Road Number
                int ctrNoRoadNr = 0;
                double dblDelay, dblLength;
                double dblDelayA5 = 0.0;
                
                while ((len = gzis.read(buffer)) > 0)
                {
                	out.write(buffer, 0, len);
        		}
                try{
        			br = new BufferedReader(new FileReader(OUTPUT_FILE));
        			while ((line = br.readLine()) != null) {
        				String[] content = line.split(cvsSplitBy);
        				// Counting empty roadarrays (ctr = counter)
        				if (content[14].equals("") == true)
        					ctrNoRoadNr = ctrNoRoadNr + 1;
        				//Creating a set of all mentioned roads
        				roadSet.add(content[14]);
        				//Creating a list with specific files(timestamp, roadNumber, delay, length)
        				list.add(new Files(content[0],content[14],content[1],content[5]));
        			}
        			for (int i=0,j=0;i<list.size() || j<list.size();i++,j++)
        			{
        				String a = list.get(i).getTime();
        				String b = list.get(i).getRoadNumber();
        				String c = list.get(i).getDelay();
        				String d = list.get(i).getLength();
        				double dblDelayAggSum = 0.0;
        				double dblDelayAgg = list.get(i).delayToInt();
        				String strDelayAgg = String.valueOf(dblDelayAgg);
        				double dblLengthAggSum = 0.0;
        				double dblLengthAgg = list.get(i).lengthToInt();
        				String strLengthAgg = String.valueOf(dblLengthAgg);
        				if (a.length()>0)
        					tsLList.add(list.get(j).getTime());
        				aggList.add(new AggFiles(a, b, strDelayAgg, strLengthAgg));
        				/*
        				for (j=0;j<aggList.size();j++)
        					if (aggList.get(i).getTimeAgg().equals(tsLList.get(j)) == true)
        						System.out.println("jawoll");
        				
        				if (aggList.get(i).getTimeAgg().equals(tsLList.get(i)) == true)
        				{
        					String tsRepeat = aggList.get(0).getTimeAgg();
        					//aggList.add(new AggFiles(tsRepeat, b, strDelayAgg, strLengthAgg));
        					if (aggList.get(i).equals(roadSet.contains(b)) == true)
        					{
        						dblDelayAggSum = dblDelayAggSum + dblDelayAgg;
        						dblLengthAggSum = dblLengthAggSum + dblLengthAgg;
        						aggList.add(new AggFiles(tsRepeat, b, String.valueOf(dblDelayAggSum), String.valueOf(dblLengthAggSum)));
        					}	
        					System.out.println("das war nichts.");
        				}	
        				else	
        					aggList.add(new AggFiles(a, b, c, d));
        				*/	
        			}
        			//for (int j=0;j<59;j++)
        				//System.out.println(aggList.get(j));
        			
        		    //Iterator<Files> itr = list.iterator();
        		    //int lengthOfString = 0;
        		    //while(itr.hasNext())
        		    //while (itr.next().timeToString().length() > 20)
        		    	//System.out.println();
        		    //while(itr.hasNext())
        		    	
        		    //while (itr.next().timeToString().length() > 0)
        		    	//System.out.println(itr.next().getTime());
        		    //for (int i = lengthOfString; i > 0; i++){
        		    	//lengthOfString = itr.next().timeToString().length();
        		    	//System.out.println(i);
        		    //}
        		    //for (lengthOfString = itr.next().timeToString().length();lengthOfString > 0;lengthOfString++){
        		    	//System.out.println("Faxen");
        		    //}
        			//while(itr.hasNext())
        			//{
        				//System.out.println(itr.next().getTime());
        			//}
    				//convert Strings to Double-Values
    					//dblDelay = Double.parseDouble(intcontent[1]);
    					//dblLength = Double.parseDouble(intcontent[5]);
    				//sum up delays and length of the roads
    				//convert Double-Values to Strings
    				//Creating List with aggregated Files
    					//if (list.contains(content[0]) == true)
    						//aggList.add(new AggFiles(content[0],content[14],"",""));
        			//System.out.println("Zeitstempel enthalten? " + list.contains("2016-01-01T03:00:00Z,A5/E35,83,2630"));
        			System.out.println("Total Datasets: " + list.size());
        			System.out.println("Total Datasets (aggregated): " + aggList.size());
    				System.out.println("Total Roads: " + roadSet.size());
    				//System.out.println("Names of the Roads: " + "\n" + roadSet);
    				System.out.println("Data without a Road Number: " + ctrNoRoadNr);
    				System.out.println("Total Delay of the Road A5: " + dblDelayA5);
        			fileWriter = new FileWriter(JAM_FILE);
        			//Header
        			fileWriter.append(FILE_HEADER.toString());
        			//New line separator after Header
        			fileWriter.append(NEW_LINE_SEPARATOR);
        			//write (specific) objects to csv
	        		for (Files file : list)
	        			{
	    					fileWriter.append(file.getTime());
	    		    		fileWriter.append(COMMA_DELIMITER);
	    		    		fileWriter.append(file.getRoadNumber());  		    		
	    		    		fileWriter.append(COMMA_DELIMITER);
	    		    		fileWriter.append(file.getDelay());
	    		    		fileWriter.append(COMMA_DELIMITER);
	    		    		fileWriter.append(file.getLength());
	    		    		fileWriter.append(NEW_LINE_SEPARATOR);
	        			}
    				fileWriter.flush();
        			fileWriter.close();
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
        }
        catch(IOException ex)
        {
        	ex.printStackTrace();
        }
    }

}
