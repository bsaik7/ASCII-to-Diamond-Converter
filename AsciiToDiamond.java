import java.io.*;
import java.util.Scanner;

public class AsciiToDiamond 
{
	public static void main(String args[]) throws Exception
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Enter the filename containing the list of input fields separated by delimiter:");
		String filename=in.next();
		System.out.println("Enter the field delimiter:");
		String splitBy = in.next();
		//System.out.println(filename); C:\Users\battula.krishna\Desktop\HSS9860_1_20160505135807_0_title.csv
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        String fields[]=null;
        if(line!=null)
        {
             fields = line.split(splitBy);
        }
        br.close();
        
        //Output DIrectory Creation
        System.out.println("Enter the name of a direcctory to create in D:\\ where output Resides");
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
        		System.out.println("DIR created Successfully");  
        	}
        }
        
        

        System.out.println("Enter the Name of the Decoder to generate:");
        String decoder_name=in.next();
        if(!decoder_name.endsWith(".xml"))
        {
        	decoder_name=decoder_name+".xml";
        }
        decoder_name=outputdir+"\\"+decoder_name;
        String current = new java.io.File( "." ).getCanonicalPath();
        File src=new File(current+"\\Sample.xml");
        File dest=new File(decoder_name);
        
        Writer out = null;
        Scanner scanner = null;

        String newLine = System.getProperty("line.separator");

        scanner = new Scanner(new FileInputStream(src));
        out = new OutputStreamWriter(new FileOutputStream(dest));
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            out.write(line);
            out.write(newLine);
            if(line.equals("        <RecordType parseQuotes=\"true\" delimiter=\"comma\" generate=\"record\" allowEOFTerm=\"true\" terminator=\"lf\" root=\"false\" name=\"Record\" type=\"delimitedascii\" parseDoubleQuotes=\"true\">"))
            {
            	for(int i=0;i<fields.length;i++)
                {
            		out.write("            <Field trimQuotes=\"true\" generate=\"field\" name=\""+fields[i]+"\" encoding=\"windows-1252\" trimWhiteSpaceInsideQuotes=\"true\" type=\"string\" trimWhiteSpace=\"true\" id=\"0\"/>");
                    out.write(newLine);
                }
            }
        }

        scanner.close();
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
