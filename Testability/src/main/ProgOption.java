package main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


public class ProgOption {
	
	public static void setOptions(String[] args) throws Exception {
		final Options options = configParameters();
	    final CommandLineParser parser = new DefaultParser();
	    try {
		    final CommandLine line = parser.parse(options, args);

		    Main.dot = line.getOptionValue("dot");
		    Main.dep = line.getOptionValue("dep");
		    
	    }catch(Exception e) {
	    	System.out.println("Usage : Main -i <dot file> -d <folder containing the DAGs>"
	    			);  
	    	System.exit(1);}
	}
	
	private static Options configParameters() {
	
		final Option dotFileOption = Option.builder("i")
				.longOpt("dot")
				.desc("model")
				.hasArg(true)
				.argName("dot")
				.required(true)
				.build();
		
		final Option depFileOption = Option.builder("d")
				.longOpt("dep")
				.desc("dag")
				.hasArg(true)
				.argName("dep")
				.required(true)
				.build();
			
	    final Options options = new Options();
	
	    options.addOption(dotFileOption);
	    options.addOption(depFileOption);
	    
	    return options;
	}
}
