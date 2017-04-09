import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AsciiToDiamond_Mapping 
{
	public static void main(String args[]) throws Exception
	{
		Scanner in=new Scanner(System.in);
		int id=0;
		//INPUT Parse Fields reading
		System.out.println("Enter the filename containing the list of parse input fields:");
		String filename=in.next();
		System.out.println("Enter the field delimiter for above file (new line delimiter = \\n):");
		String splitBy = in.next();
		//System.out.println(filename); C:\Users\battula.krishna\Desktop\HSS9860_1_20160505135807_0_title.csv
		System.out.println("Please enter Which type of delimited ASCII(comma,space,tab,|, etc.,):");
		String delimiter = in.next();        
        //Output DIrectory Creation
        System.out.println("Enter the name of a direcctory to create in D:\\ where output decoder Resides : ");
        String outputdir=in.next();
        outputdir="D:\\"+outputdir;
        File theDir = new File(outputdir);

        // if the directory does not exist, create it
        if (!theDir.exists()) 
        {
        	System.out.println("creating directory: " + outputdir);
        	boolean result = false;
        	try
        	{
        		theDir.mkdir();
        		result = true;
        	} 
        	catch(SecurityException se)
        	{
             //handle it
        	}        
        	if(result) 
        	{    
        		System.out.println("DIR created Successfully in D:\\");  
        	}
        }
        
        
        //Decoder Creation
        System.out.println("Enter the Name of the Decoder to generate:");
        String decoder_name=in.next();
        if(!decoder_name.endsWith(".xml"))
        {
        	decoder_name=decoder_name+".xml";
        }
        decoder_name=outputdir+"\\"+decoder_name;
        String current = new java.io.File( "." ).getCanonicalPath();
        File dest=new File(decoder_name);
        
        Writer out = null;
        
        String newLine = System.getProperty("line.separator");

        out = new OutputStreamWriter(new FileOutputStream(dest));
        int c;
        //Writing Fileds to a Decoder
        {
            out.write("<?xml version=\"1.0\"?>");
            out.write(newLine);
            out.write(newLine);
            out.write("<Diamond application=\"spark\" version=\"1\" softwareVersion=\"1.0.14872.0\">");
            out.write(newLine);
            out.write("    <Decoder name=\"SAI_ASCII\" type=\"ascii\">");
            out.write(newLine);
            out.write("        <RecordType root=\"true\" name=\"Root\" type=\"none\">");
            out.write(newLine);
            out.write("            <Logic type=\"sequenceOf\">");
            out.write(newLine);
            out.write("                <Logic recordType=\"Record\" type=\"recordReference\"/>");
            out.write(newLine);
            out.write("            </Logic>");
            out.write(newLine);
            out.write("        </RecordType>");
    		out.write(newLine);
            out.write("        <RecordType parseQuotes=\"true\" delimiter=\""+delimiter+"\" generate=\"record\" allowEOFTerm=\"true\" terminator=\"lf\" root=\"false\" name=\"Record\" type=\"delimitedascii\" parseDoubleQuotes=\"true\">");
            out.write(newLine);        		
            
            BufferedReader br = new BufferedReader(new FileReader(filename));
            int c1=0;
            if(splitBy.equalsIgnoreCase("\\n"))
    		{
    			String line2;
    			String[] parse_field=new String[1000];
    			while((line2 = br.readLine())!=null)
    			{
    				parse_field[c1]=line2;
    				c1++;
    			}
    			for(int i=0;i<c1;i++)
                 {
    				out.write("            <Field trimQuotes=\"true\" generate=\"field\" name=\""+parse_field[i]+"\" encoding=\"windows-1252\" trimWhiteSpaceInsideQuotes=\"true\" type=\"string\" trimWhiteSpace=\"true\" id=\""+id+"\"/>");
                    out.write(newLine);
                    id++;
                 }
    			
    		}
    		else
    		{
    		//System.out.println(filename); C:\Users\battula.krishna\Desktop\HSS9860_1_20160505135807_0_title.csv
    			//C:\Users\battula.krishna\Desktop\mapping.txt
    			
    			String line = br.readLine();
    	        String fields[]=null;
    	        if(line!=null)
    	        {
    	        	 if(splitBy.equals("|"))
    	        		 fields = line.split(Pattern.quote(splitBy));
    	        	 else
    	        		 fields = line.split(splitBy);	
    	        }
    	        
    			
    	        for(int i=0;i<fields.length;i++)
                {
            		out.write("            <Field trimQuotes=\"true\" generate=\"field\" name=\""+fields[i]+"\" encoding=\"windows-1252\" trimWhiteSpaceInsideQuotes=\"true\" type=\"string\" trimWhiteSpace=\"true\" id=\""+id+"\"/>");
                    out.write(newLine);
                    id++;
                }
            
    		}
    		br.close();
    		
            out.write("		</RecordType>");
            out.write(newLine);		
            out.write("    </Decoder>");		
            out.write(newLine);		
            out.write("    <Mappings>");
            out.write(newLine);		
            out.write("        <Nodes/>");
            out.write(newLine);		
            out.write("        <DynamicFunctions/>");
            out.write(newLine);
            out.write("        <RecordTypes>");
            out.write(newLine);
            
            //Mapping FingerPrint Code Goes Here
            System.out.println("Do you want Mapping Fingerprint to be Added to decoder(Y/N) : ");
            String map=in.next();
            if(map.equalsIgnoreCase("Y"))
            {
            	System.out.println("Enter the filename containing the list of Mapping Fingerprint fields:");
        		String map_file=in.next();
        		System.out.println("Enter the field delimiter for above file (new line delimiter = \\n): ");
        		String map_delimiter = in.next();
        		
        		
        		out.write("            <RecordType name=\"FingerPrint\">");
            	out.write(newLine);
            	
            	BufferedReader br1 = new BufferedReader(new FileReader(map_file));
        		if(map_delimiter.equalsIgnoreCase("\\n"))
        		{
        			String line1;
        			c=0;
        			String[] map_field=new String[1000];
					while((line1 = br1.readLine())!=null)
        			{
						map_field[c]=line1;
						c++;
        			}
					for(int i=0;i<c;i++)
	                 {
	             		out.write("                <Field name=\""+map_field[i]+"\" nullable=\"true\" type=\"string\" id=\""+id+"\"/>");
	                     out.write(newLine);
	                     id++;
	                 }
					
        		}
        		else
        		{
        		//System.out.println(filename); C:\Users\battula.krishna\Desktop\HSS9860_1_20160505135807_0_title.csv
        			//C:\Users\battula.krishna\Desktop\mapping.txt
        			String line1 = br1.readLine();
        			String map_field1[]=null;
        			if(line1!=null)
        			{
        				if(map_delimiter.equals("|"))
        					map_field1 = line1.split(Pattern.quote(map_delimiter));
       	        	 	else
       	        	 		map_field1 = line1.split(map_delimiter);
        				
        			}
        			
        			for(int i=0;i<map_field1.length;i++)
                    {
                		out.write("                <Field name=\""+map_field1[i]+"\" nullable=\"true\" type=\"string\" id=\""+id+"\"/>");
                        out.write(newLine);
                        id++;
                    }
                
        		}
        		br1.close();
            	
            	
       
            	out.write("            </RecordType>");
            	out.write(newLine);
            
            }
            
            //
            out.write("        </RecordTypes>");
            out.write(newLine);
            out.write("        <Globals/>");
            out.write(newLine);		
            out.write("    </Mappings>");
            out.write(newLine);		
            out.write("</Diamond>");
         
        }

        out.close();
        
        System.out.println("Processing Done and Decoder Generated at "+decoder_name);
        
        //Decoder Copying
        /*
        System.out.println("Enter the Name of the Output Decoder:");
        String decoder_name=in.next();
        decoder_name=outputdir+"\\"+decoder_name;
        File src=new File("C:\\Users\\battula.krishna\\Desktop\\Sample.xml");
        File dest=new File(decoder_name);
        //copyFileUsingStream(src, dest);
        for(int i=0;i<fields.length;i++)
        {
        	System.out.println(fields[i]);
        }
        */

	}
	/*private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try 
	    {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) 
	        {
	            os.write(buffer, 0, length);
	        }
	    } 
	    finally 
	    {
	        is.close();
	        os.close();
	    }
	}*/
}
