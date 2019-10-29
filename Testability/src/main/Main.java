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
			boolean card1 = true;
			HashSet<String> union = new HashSet<String>();
			HashSet<String> exclu = new HashSet<String>();
			for (Transition t: model.getTransitions()) {
				if(t.getName().contains(in)) {
					if (t.getOt().size() != 1) {
						card1 = false;
					}
					union.addAll(t.getOt());
				}
				else {
					exclu.addAll(t.getOt());
				}
			}
			union.removeAll(exclu);
			if (card1 && (!union.isEmpty()) && (!union.contains("nope"))) {
				obs++;
			}
			else {
				System.out.println("issue on input: " + in + "\n"
						+ "all card equals 1:" + card1 + "\n"
						+ "set = " + union + "\n");
			}
		}
		return obs / (double) model.getInputs().size();
	}
	

	private static double controlability(LTS model) {
		int ct = 0;
		for (String out: model.getOutputs()) {
			boolean card1 = true;
			HashSet<String> union = new HashSet<String>();
			HashSet<String> exclu = new HashSet<String>();
			for (Transition t: model.getTransitions()) {
				if(t.getName().contains(out)) {
					if (t.getOt().size() != 1) {
						card1 = false;
					}
					union.addAll(t.getOt());
				}
				else {
					exclu.addAll(t.getOt());
				}
			}
			union.removeAll(exclu);
			if (card1 && (!union.isEmpty()) && (!union.contains("nope"))) {
				ct++;
			}
			else {
				System.out.println("issue on output: " + out + "\n"
						+ "all card equals 1:" + card1 + "\n"
						+ "set = " + union + "\n");
			}
		}
		return ct / (double) model.getOutputs().size();
	}
}
