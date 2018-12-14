package edu.smith.cs.csc212.fp;

import java.util.Map;
import java.util.Stack;

/**
 * This is the class for Logical Expression Tree.
 * 
 * @author zhuyanwan
 *
 */
public class LogicalExpressionTree {

	TreeNode root;

	/**
	 * A tree starts with an empty root.
	 */
	public LogicalExpressionTree() {
		this.root = null;
	}

	/**
	 * Construct the logical expression tree from a postorder expression
	 * @param expression - the logical expression that needs to be "translated" into a tree
	 */
	public void constructTree(String expression) {
		//Assuming using postfix(reverse Polish notation), all operators and operands separated with blank space
		String[] expr = expression.trim().split("\\s+");
		//Create a stack to store the TreeNodes I create during the process
		Stack<TreeNode> treeStack = new Stack<>();
		for (String s: expr) {
			//String s = expr[i];
			if (isOperator(s)) {
				//If is operator, 
				//pop out two nodes in the stack and 
				//make them the children of the operator node
				if (s.equals("!")) {
					TreeNode temp = new TreeNode(s);
					temp.right = treeStack.pop();
					treeStack.push(temp);
				}else {
					TreeNode temp = new TreeNode(s);
					temp.left = treeStack.pop();
					temp.right = treeStack.pop();
					treeStack.push(temp);
				}

			}else {
				//If is a variable,
				//Push the variable node into the stack for later use
				TreeNode temp = new TreeNode(s);
				treeStack.push(temp);
			}
		}

		//By the end, we should have only one TreeNode in the stack, which is the root of our desire tree
		this.root = treeStack.pop();
	}

	/**
	 * Print the tree using inorder traversal
	 * @param t
	 */
	public void inorder(TreeNode t) { 
		if (t != null) { 
			System.out.print("( ");
			inorder(t.left); 
			System.out.print(t.data ); 
			inorder(t.right); 
			System.out.print(" )");
		} 
	} 

	/**
	 * Print the tree using preorder traversal
	 * @param t
	 */
	public void inPolish(TreeNode t) { 
		if (t != null) { 
			System.out.print(t.data + " "); 
			inPolish(t.left); 
			inPolish(t.right); 
		} 
	}

	/**
	 * Evaluate the truth value of the tree
	 * @return
	 */
	public boolean evaluate(Map<String, Boolean> values) {
		if (root == null) {
			throw new RuntimeException("Empty tree");
		}
		return root.eval(values);
	}	

	/**
	 * Check if a string is a logical operator
	 * @param s
	 * @return
	 */
	public boolean isOperator(String s) {
		if (s.equals("&&") || s.equals("||") || s.equals("!")) {
			return true;
		}
		return false;
	}

	/**
	 * This is the class for TreeNode in a tree.
	 * @author zhuyanwan
	 *
	 */
	public class TreeNode{

		/**
		 * The value stored in the TreeNode
		 */
		String data;
		/**
		 * The left children
		 */
		TreeNode left;
		/**
		 * The right children
		 */
		TreeNode right;
		
		/**
		 * Check if the TreeNode contains a variable (must be a leaf node)
		 * @return
		 */
		boolean isVariable() {
			return this.left == null && this.right == null;
		}

		/**
		 * A TreeNode starts with no children
		 * @param value
		 */
		public TreeNode(String value) {
			this.data = value;
			this.left = null;
			this.right = null;
		}

		/**
		 * Evaluate the value of a TreeNode recursively
		 * @return
		 */
		public boolean eval(Map<String, Boolean> values) {
			if (this.isVariable()) {
				return values.get(this.data);
			}

			if (data.equals("&&")) {
				return left.eval(values) && right.eval(values);
			}else if (data.equals("||")) {
				return left.eval(values) || right.eval(values);
			}else if (data.equals("!")) {
				return !(right.eval(values));
			}
			
			throw new RuntimeException("Invalid string");
		}
	}
}
