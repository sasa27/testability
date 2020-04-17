package model;

import java.util.ArrayList;
import java.util.HashSet;

public class Dependencies {

	private ArrayList<HashSet<String>> dep;
	private ArrayList<String> compo;
	
	public Dependencies() {
		dep = new ArrayList<HashSet<String>>();
		compo = new ArrayList<String>();
	}
	
	public ArrayList<HashSet<String>> getDeps() {
		return dep;
	}
	
	public ArrayList<String> getCompo(){
		return compo;
	}
	
	public int nbCompo() {
		return compo.size();
	}
	
	public HashSet<String> getDep(String c){
		if (compo.contains(c)) {
			if (compo.indexOf(c) < dep.size()) {
			return dep.get(compo.indexOf(c));
			}
			else {
				return new HashSet<String>();
			}
		}
		else {
			System.err.println("error, " + c + " is not a component of the system.");
			return null;
		}
	}
		
	public void addDep(String src, String dest) {
		if (src.equals("")) {
			System.err.println("source of dependency not detected (initial state of the DAG must be in the first position in the file)");
		}
		else if (compo.contains(src)) {
			dep.get(compo.indexOf(src)).add(dest);
		}
		else {
			compo.add(src);
			HashSet<String> d = new HashSet<String>();
			d.add(dest);
			dep.add(d);
		}
	}

	public void update() {
		for (HashSet<String> d : dep) {
			for (String c : d){
				if (!compo.contains(c)) {
					compo.add(c);
				}
			}
		}
		
	}
	
}
