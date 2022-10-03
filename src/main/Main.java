package main;

import java.util.Map;

public class Main {

	public static void main(String[] args) {		
		
		ExpressionTreeUtil expressionTreeUtil = new ExpressionTreeUtil();
		
		// 1. Retrieve user input (from args)
		String input = "";
		if (args.length != 1)
		{
			System.out.println("Invalid Input.");
			return;
		}
		else
		{
			input = args[0];
		}
		
		// 2. infixToPostfix
		String postfix = expressionTreeUtil.infixToPostfix(input);
		System.out.println(postfix);
		
		// 3. postfixToExpressionTree
		Node root = expressionTreeUtil.postfixToExpressionTree(postfix);
		
		// 4. solveExpressionTree
		double result = expressionTreeUtil.solveExpressionTree(root);
		System.out.println("Answer: " + result);

	}

}