package edu.smith.cs.csc212.fp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the class for Truth Table.
 * 
 * @author zhuyanwan
 *
 */
public class TruthTable {
	
	/**
	 * Lines of values contained in a Truth table
	 */
	private List<Map<String,Boolean>> lines;
	/**
	 * The expression evaluated by the Truth Table
	 */
	private String expression;
	
	/**
	 * Construct a Truth Table with an expression
	 * @param expr
	 */
	public TruthTable(String expr) {
		this.expression = expr;
		lines = new ArrayList<>();
	}
	
	/**
	 * Add a line to the Truth Table
	 * @param line
	 */
	public void addLine(Map<String,Boolean> line) {
		lines.add(line);
	}
	
	/**
	 * Create a Truth Table
	 * @param operands
	 */
	public void create(List<String> operands){
		//First, construct a LogicalExpressionTree
		LogicalExpressionTree tree = new LogicalExpressionTree();
		tree.constructTree(expression);
		//Then, generate possible combinations of truth values
		List<Map<String, Boolean>> listOfValues = generateValues(operands);
		//Feed the generated values into the tree
		for (Map<String, Boolean> v: listOfValues) {
			//Evaluate and add the result back
			v.put("expression", tree.evaluate(v));
			addLine(v);
		}
		//Draw the truth table generated
		draw(operands);
		
	}
	
	/**
	 * Generate all possible combinations of truth values for a set of operands
	 * Reference: https://stackoverflow.com/questions/10923601/java-generator-of-trues-falses-combinations-by-giving-the-number-n
	 * @param operands
	 * @return - A list of maps containing operands with corresponding truth values
	 */
	public List<Map<String,Boolean>> generateValues(List<String> operands ){
		int n = operands.size();
		List<Map<String,Boolean>> listOfValues = new ArrayList<>();
		
		for (int i = 0; i < Math.pow(2, n); i++) {
		    String bin = Integer.toBinaryString(i);
		    while (bin.length() < n)
		        bin = "0" + bin;
		    
		    Map<String,Boolean> values = new HashMap<>();
		    for (int j = 0; j < bin.length(); j++) {
		    	if (Character.toString(bin.charAt(j)).equals("0")) {
		    		values.put(operands.get(j), false);
		    	}else {
		    		values.put(operands.get(j), true);
		    	}
		    }
		    listOfValues.add(values);
		}
		
		return listOfValues;
	}
	
	/**
	 * Print out the Truth Table
	 * @param operands
	 */
	public void draw( List<String> operands ) {
		int n = 2 * operands.size() + 8;
		String repeated = "";
		for (int i = 1; i <= n; i++) {
			repeated += "=";
		}
		System.out.println(repeated);
		
		System.out.print("| ");
		for (String o : operands) {
			System.out.print( o + " ");
		}
		System.out.println("EXPR |");
		
		for (Map<String, Boolean> l: this.lines) {
			System.out.print("| ");
			for ( String o: operands) {
				if (l.get(o)) {
					System.out.print("T ");
				}else {
					System.out.print("F ");
				}
			}
			if (l.get("expression")) {
				System.out.println("  T  |");
			}else {
				System.out.println("  F  |");
			}
		}
		
		System.out.println(repeated);
	}
}
