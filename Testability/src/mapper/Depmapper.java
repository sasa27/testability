package mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.Dependencies;

public class Depmapper {

	public static Dependencies mapping(File dir) {
		Dependencies dep = new Dependencies();
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (!file.getName().endsWith(".dot")) {
					continue;
				}
				//System.out.println(file);
				String c = "";
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line = br.readLine();
					while (!line.equals("}")){
						if (line.contains("[") && line.contains(",fillcolor=grey,style=filled")) {
							c = line.substring(line.indexOf("\"") + 1);
							c = c.substring(0, c.indexOf("\""));
						}
						else if (line.contains("[") && !line.contains(",fillcolor=grey,style=filled")) {
							String d = line.substring(line.indexOf("\"") + 1);
							d = d.substring(0, d.indexOf("\""));
							dep.addDep(c, d);
						}						
						line = br.readLine();
					}
					br.close();
				} catch (FileNotFoundException e) {
					System.out.println("file " + file + " not found: " + e);
				} catch (IOException e){
					System.out.println("error " + e);
				}
			}
		}
		else {
			System.err.println("error : " + dir + " has to be a directory containing the DAGs");
			System.exit(3);
		}
		dep.update();
		return dep;
	}


}
