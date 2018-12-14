package edu.smith.cs.csc212.fp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * This is the class for parsing user input
 * 
 * @author zhuyanwan
 *
 */
public class Input {
	
	/**
	 * The source of data for text input
	 */
	private Scanner input;
	
	/**
	 * Read in input
	 */
	public Input(){
		this.input = new Scanner(System.in);
	}
	
	/**
	 * Get the expression from user input
	 * @return - a string
	 */
	public String getUserWords() {
		System.out.print("Please enter the expression you want to evaluate:  ");
		this.input = new Scanner(System.in);
		String expression = input.nextLine();
        return expression;
	}
	
	/**
	 * Get a list of operands contained in the input
	 * @param input
	 * @return - a list of strings
	 */
	public List<String> getOperands(String input) {
		String[] tokens = input.split("\\s+");
		HashSet<String> operands = new HashSet<>();
		for (String str : tokens) {
			if (isOperand(str)) {
				operands.add(str);
			}
		}
		
		List<String> out = new ArrayList<>();
		for (String s : operands) {
			out.add(s);
		}
		return out;
	}

	/**
	 * Translate the inorder input into postfix, 
	 * so that it can be used to construct a LogicalExpressionTree
	 * @param input
	 * @return - a string of postfix expression
	 */
	public String translate(String input) {
		String[] tokens = input.split("\\s+");
		Stack<String> strStack = new Stack<>();
		List<String> output = new ArrayList<>();
		
		for (String str : tokens) {
			//If the token is an operand, add to the output
			if (isOperand(str)) {
				output.add(str);
			}else if (isOperator(str)) {
				if (isNot(str)) {
					strStack.push("!");
				}else {
					//If the token is an operator, pop the operators from stack and add to the output
					while (!strStack.empty() && isOperator(strStack.peek())) {
						output.add(strStack.pop());
					}
					// Then, push the token to the stack
					if (isAnd(str)) {
						strStack.push("&&");
					}else if (isOr(str)) {
						strStack.push("||");
					}
				}
			}else if (str.equals("(")) {
				strStack.push(str);
			}else if (str.equals(")")) {
				//If the token is a right parenthesis, pop all the operators from stack,
				//until there is a left parenthesis
				while (!strStack.peek().equals("(")){
					output.add(strStack.pop());
				}
				//Then pop the left parenthesis out
				strStack.pop();
			}else {
				throw new RuntimeException("Unsupported expression");
			}
		}
		
		while (!strStack.empty()) {
			if (strStack.peek().equals("(") || strStack.peek().equals(")")) {
				throw new RuntimeException("Mismatched parentheses");
			}else {
				output.add(strStack.pop());
			}
		}
		
		String postfix = "";
		for (String s : output) {
			postfix = postfix + " " +s;
		}
		return postfix;
	}
	
	/**
	 * Check if a string is a logical operator
	 * @param s
	 * @return - a boolean
	 */
	public boolean isOperator(String s) {
		if (isAnd(s) || isOr(s) || isNot(s)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if a string is an operand
	 * @param s
	 * @return - a boolean
	 */
	public boolean isOperand(String s) {
		
		String operand = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		if (operand.contains(s)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if a string is a conjunction connective
	 * @param s
	 * @return - a boolean
	 */
	public boolean isAnd(String s) {
		if (s.equals("and") || s.equals("&&") || s.equals("^")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if a string is a disjunction connective
	 * @param s
	 * @return - a boolean
	 */
	public boolean isOr(String s) {
		if (s.equals("or") || s.equals("||") || s.equals("v")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if a string is a negation connective
	 * @param s
	 * @return - a boolean
	 */
	public boolean isNot(String s) {
		if (s.equals("not") || s.equals("!") || s.equals("~")) {
			return true;
		}
		return false;
	}

}
