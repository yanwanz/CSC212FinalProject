package edu.smith.cs.csc212.fp;

import java.util.List;

public class ProjectMain {

	public static void main(String[] args) {
		
		// Read in input from user
		System.out.println("Please enter an inorder expression with appropriate parentheses.");
		System.out.println("Only support conjunction,disjunction, and negation statements.");
		System.out.println("Please add a blankspace after any parenthesis, operand, or operator. For example:");
		System.out.println(" ( a and b ) or ( not c ) ");
		Input input = new Input();
		String expr = input.getUserWords();
		String original = expr;
		
		// Translate the input into postfix
		List<String> operands = input.getOperands(expr);
		expr = input.translate(expr);
		System.out.println("Operands: "+operands);
		System.out.println("The postfix version of the expression: " + expr);
		
		//Create the Truth Table
		TruthTable table = new TruthTable(expr);
		System.out.println("Truth Table for ( " + original + " ):");
		table.create(operands);
		
	}
}
