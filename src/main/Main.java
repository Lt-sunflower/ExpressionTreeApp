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
			if (args[0].contains("^"))
			{
				System.out.println("^ is not supported.");
				return;
			}
			input = args[0];
		}
		
//		String input = "((15/(7-(1+1)))*-3)-(2+(1+1))";
		
		// 1.1 Replace int with char(construct HashMap)
		Map<Character,Integer> numberMap = expressionTreeUtil.storeNumbers(input);
		
		for(Character key : numberMap.keySet()){
			String value = numberMap.get(key).toString();
			input = input.replaceAll(value,key.toString());
		}
		
		// 2. infixToPostfix
		String postfix = expressionTreeUtil.infixToPostfix(input);
		if (postfix.equals("Invalid Expression"))
		{
			System.out.println("Invalid Expression");
			return;
		}
//		System.out.println(postfix);
		
		// 3. postfixToExpressionTree
		Node root = expressionTreeUtil.postfixToExpressionTree(postfix);
		if (root == null)
		{
			System.out.println("Invalid Expression");
			return;
		}
		
		// 3.1 print tree
		expressionTreeUtil.printTree(root);
		
		// 4. solveExpressionTree
		double result = expressionTreeUtil.solveExpressionTree(root);
		System.out.println("Answer: " + result);

	}

}