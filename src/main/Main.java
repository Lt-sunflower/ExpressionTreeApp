package main;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

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
		
//		String input = "1-(-1)";
		
		// 1.1 Replace int with char(construct HashMap)
		Map<Character,Integer> numberMap = expressionTreeUtil.storeNumbers(input);
		
		Map<Integer, Character> reverseMap = new HashMap<>();
		
		// O(n), n=sizeof keyset
		for (Character c:numberMap.keySet())
		{
			reverseMap.put(numberMap.get(c), c);
		}
		
		// Encountered problem where when not sorted, numbers get replaced out of sequenced e.g. affected 10 when replacing 1 or affect -1 when replacing 1
		// Fix is currently not the best complexity wise, but will try find a better way to make sequential replace
		TreeSet<Integer> sortedSet = new TreeSet<>();
		sortedSet.addAll(numberMap.values());
		
		TreeSet<Integer> reverseSortedSet = new TreeSet<>(Collections.reverseOrder());
		reverseSortedSet.addAll(numberMap.values());
		
		// Where i<0, smallest -> largest e.g. -10 then -1
		for (Object value : sortedSet)
		{
			int i = (int) value;
			if (i<0)
			{
				// getKeyByValue traverses whole hashMap, returns once found (1-to-1 match), worst case - O(n) for EACH key, whole for loop o(n^2) for worst case
//				Character c = getKeyByValue(numberMap,value);
				
				// Using additional HashMap changes get to constant time O(1)
				Character c = reverseMap.get(i);
				input = input.replaceAll(value.toString(),c.toString());
			} else break;
		}
		
		// Where i>=0, largest -> smallest e.g. 10 then 1
		for (Object value : reverseSortedSet)
		{
			int i = (int) value;
			if (i>=0)
			{
//				Character c = getKeyByValue(numberMap,value);
				Character c = reverseMap.get(i);
				input = input.replaceAll(value.toString(),c.toString());
			} else break;
		}

		
		
		
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

//	private static Character getKeyByValue(Map<Character, Integer> numberMap, Object value) {
//	
//		for (Character c: numberMap.keySet())
//		{
//			if (numberMap.get(c) == value)
//				return c;
//		}
//		return null;
//	}

}