package model;

import java.util.ArrayList;
import java.util.HashSet;

public class Transition {

	private String name;
	//private String label;
	//private String[] parameters;
	
	private State source;
	private State target;
	private HashSet<String> Ot;
	
	public Transition() {
		source = new State();
		target = new State();
	}
	
	public Transition(State src, String trans, State dst) {
		name = trans;
		//label = trans.substring(0,trans.indexOf("("));
		//parameters = trans.substring(trans.indexOf("(")+1,trans.lastIndexOf(")")).split(";", 0);		
		source = src;
		target = dst;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newname) {
		name = newname;
	}
	
	public HashSet<String> getOt(){
		return getOt(new ArrayList<Transition>());
	}
	
	public HashSet<String> getOt(ArrayList<Transition> checked) {
		if (Ot != null) {
			return Ot;
		}
		checked.add(this);
		String epsilon = "nope";
		HashSet<String> res = new HashSet<String>();
		if (this.isInput()) {
			for (Transition t: target.getSuccesseurs()) {
				if (checked.contains(t)) {
					Ot = new HashSet<String>();
					Ot.add(epsilon);
					return Ot;
				}
				if (t.isInput()) {
					t.getOt(checked);
				}
				else {
					res.add(t.getName());
				}
			}
		}
		else {	
			for (Transition t: source.getPredecesseurs()) {
				if (checked.contains(t)) {
					Ot = new HashSet<String>();
					Ot.add(epsilon);
					return Ot;
				}
				if (t.isInput()) {
					res.add(t.getName());	
				}
				else {
					res.addAll(t.getOt(checked));
				}
			}		
		}
		Ot = res;
		return Ot;
	}
	
	/*public String getLabel() {
		return label;
	}

	// never used 
	public void setLabel(String newlabel) {
		label = newlabel;		
	}
	
	public String[] getParameters() {
		return parameters;
	}
	
	//never used 
	public void setParameters(String[] newparam) {
		parameters = newparam;
	}
	*/
	
	public State getSource() {
		return source;
	}
	
	public void setSource(State newsource) {
		source = newsource;
	}
	
	public State getTarget() {
		return target;
	}
	
	public void setTarget(State newtarget) {
		target = newtarget;
	}
	
	public boolean equals(String t) {
		return name.equals(t);
	}
	
	public String toString() {
		return name;				
	}
	
	public boolean isInput() {
		if (name.startsWith("?")){
			return true;
		}
		else if (name.startsWith("!")){
			return false;
		}
		else {
			System.err.println("bad fromat, input should starts with ? and output with !, got :" + name);
			return false;//TODO gestion d'erreur
		}
	}
	

	
}
