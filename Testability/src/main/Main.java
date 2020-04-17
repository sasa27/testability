package main;

import java.io.File;
import java.util.HashSet;

import model.Dependencies;
import model.LTS;
import model.Transition;


public class Main {

	static String dot;
	static String dep;
	static String component;
	
	public static void main(String[] args) {
		try {
			ProgOption.setOptions(args);
		} catch (Exception e) {
			System.err.println("pb option");
			System.exit(3);
		}
		LTS model = mapper.LTSmapper.mapping(new File(dot));
		component = model.getCompo();
		Dependencies dag = mapper.Depmapper.mapping(new File(dep));
		makeOt(model);
		//double dependa = 
		double OutDep = OutDependability(dag, component);
		double InDep = InDependability(dag, component);
		double observa = observability(model);
		double controla = controlability(model);
		
		System.out.println("Observability :" + observa);
		System.out.println("Controlability :" + controla);
		System.out.println("InDep :" + InDep);
		System.out.println("OutDep :" + OutDep);
	}
	
	private static double OutDependability(Dependencies dag, String component) {
		//System.out.println("compo:" + component);
		//System.out.println("getcompo:" + dag.getCompo());
		if (dag.getCompo().contains(component)) {
			return (double) dag.getDep(component).size() / (double) dag.nbCompo();
		}
		System.err.println(component + " not found as a comonent of the system");
		return -1.0;
	}
	
	private static double InDependability(Dependencies dag, String component) {
		//System.out.println("compo:" + component);
		//System.out.println("getcompo:" + dag.getCompo());
		Double ct = 0.0;
		int index = -1;
		if (dag.getCompo().contains(component)) {
			index = dag.getCompo().indexOf(component);
		}
		else {
			System.err.println(component + " not found as a comonent of the system");
			return -1.0;
		}
		
		for (int i = 0; i< dag.getDeps().size(); ++i) {
			if (i != index) {
				if (dag.getDeps().get(i).contains(component)) {
					ct ++;
				}
			}
		}
		return ct / (double) dag.nbCompo();
	}
	

	private static void makeOt(LTS model) {
		for (Transition t: model.getTransitions()) {
			t.getOt();
		}
	}
	
	private static double observability(LTS model) {
		int obs = 0;
		for (String in: model.getInputs()) {
			HashSet<String> union = new HashSet<String>();
			HashSet<String> exclu = new HashSet<String>();
			for (Transition t: model.getTransitions()) {
				if(t.isInput() && t.getName().contains(in)) {
					union.addAll(t.getOt());
				}
				else if (t.isInput()){
					exclu.addAll(t.getOt());
				}
			}
			HashSet<String> set = new HashSet<String>();
			set.addAll(union);
			//System.out.println("union: " + union + "\n");
			set.removeAll(exclu);
			//System.out.println("exclu:"  + exclu + "\n");
			//System.out.println("set: " + set + "\n");
			if ((!set.isEmpty()) && set.size() == union.size() && (!union.contains("nope"))) {
				obs++;
			}
			/*else {
				System.out.println("issue on input: " + in + "\n"
						+ "all card equals 1:" + card1 + "\n"
						+ "set = " + set + "\n"
						+ "union = " + union + "\n"
						+ "excluded = " + exclu + "\n");
			}*/
		}
		return obs / (double) model.getInputs().size();
	}
	

	private static double controlability(LTS model) {
		int ct = 0;
		for (String out: model.getOutputs()) {
			System.out.println("output: " + out);
			HashSet<String> union = new HashSet<String>();
			HashSet<String> exclu = new HashSet<String>();
			for (Transition t: model.getTransitions()) {
				if(t.isOutput() && t.getName().contains(out)) {
					union.addAll(t.getOt());
				}
				else if(t.isOutput()) {
					exclu.addAll(t.getOt());
				}
			}
			HashSet<String> set = new HashSet<String>();
			set.addAll(union);
			System.out.println("union: " + union);
			set.removeAll(exclu);
			System.out.println("exclu:"  + exclu);
			System.out.println("set: " + set + "\n");
			if ((!set.isEmpty()) && set.size() == union.size() && (!union.contains("nope"))) {
				ct++;
			}
			/*else {
				System.out.println("issue on output: " + out + "\n"
						+ "all card equals 1:" + card1 + "\n"
						+ "set = " + set + "\n"
						+ "union = " + union + "\n"
						+ "excluded = " + exclu + "\n");
				
			}*/
		}
		return ct / (double) model.getOutputs().size();
	}
}
