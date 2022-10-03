package main;

import java.util.Map;

public class Main {

	public static void main(String[] args) {		
		
		ExpressionTreeUtil expressionTreeUtil = new ExpressionTreeUtil();
		
		// 1. Retrieve user input (from args)
		String input = "";
		if (args.length != 1 && !args[0].equals(null))
		{
			System.out.println("Invalid Input.");
			return;
		}
		else
		{
			input = args[0];
		}
		
//		String input = "1+2+3+4";
		
		// 1.1 Replace int with char(construct HashMap)
		Map<Character,Integer> numberMap = expressionTreeUtil.storeNumbers(input);
		
		for(Character key : numberMap.keySet()){
			String value = numberMap.get(key).toString();
			input = input.replaceAll(value,key.toString());
		}
		
		// 2. infixToPostfix
		String postfix = expressionTreeUtil.infixToPostfix(input);
//		System.out.println(postfix);
		
		// 3. postfixToExpressionTree
		Node root = expressionTreeUtil.postfixToExpressionTree(postfix);
		
		// 3.1 print tree
		expressionTreeUtil.printTree(root);
		
		// 4. solveExpressionTree
		double result = expressionTreeUtil.solveExpressionTree(root);
		System.out.println("Answer: " + result);

	}

}