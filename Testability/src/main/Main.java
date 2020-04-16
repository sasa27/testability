package main;

import java.io.File;
import java.util.HashSet;

import model.LTS;
import model.Transition;


public class Main {

	static String dot;
	
	public static void main(String[] args) {
		try {
			ProgOption.setOptions(args);
		} catch (Exception e) {
			System.err.println("pb option");
			System.exit(3);
		}
		LTS model = mapper.LTSmapper.mapping(new File(dot));
		makeOt(model);
		double obs = observability(model);
		double ct = controlability(model);
		System.out.println("Observability :" + obs);
		System.out.println("Controlability :" + ct);
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
			//System.out.println("output: " + out);
			HashSet<String> union = new HashSet<String>();
			HashSet<String> exclu = new HashSet<String>();
			for (Transition t: model.getTransitions()) {
				if(!t.isInput() && t.getName().contains(out)) {
					union.addAll(t.getOt());
				}
				else if(!t.isInput()) {
					exclu.addAll(t.getOt());
				}
			}
			HashSet<String> set = new HashSet<String>();
			set.addAll(union);
			//System.out.println("union: " + union);
			set.removeAll(exclu);
			//System.out.println("exclu:"  + exclu);
			//System.out.println("set: " + set + "\n");
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
