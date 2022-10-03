package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class ExpressionTreeUtil {

	private static Map<Character,Integer> numberMap = new HashMap<>();
	private static LinkedList<String> tree = new LinkedList<>();
	
	public Map<Character,Integer> storeNumbers(String input) {
		
    	Map<Character,Integer> result = new HashMap<>();
    	String s = "";
    	int index = 0;
    	
    	for(int i=0; i<input.length(); i++)
    	{
    		char c = input.charAt(i);
    		if (Character.isDigit(c))
    		{
    			s += c;
    			i+=1;
    			while(i<input.length())
    			{
    				
    				if (Character.isDigit(input.charAt(i)))
    				{
    					s += input.charAt(i);
    					i+=1;
    				} else break;
    				
    			}
    			int temp = Integer.parseInt(s);
    			Character temp2 = (char) ('A'+index);
    			if (!result.containsValue(temp))
    				result.put(temp2, temp);
    			index++; s="";
    		} 
    	}
    	
    	numberMap = result;
    	return result;
	}
	
	public String infixToPostfix(String input) {

		String result = "";
		Stack<Character> stack = new Stack<>();
		
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
 
            // Add operands directly into result
            if (Character.isLetterOrDigit(c))
                result += c;
 
            // Add open bracket '(' to stack
            else if (c == '(')
                stack.push(c);
 
            // If ')' pop until '(' is found
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') 
                {
                    result += stack.pop();
                }
 
                stack.pop();
            }
            else // Operator
            {
            	// Newly added operator cannot be of lower/equal priority than top of stack
                while (!stack.isEmpty() && opPriority(c) <= opPriority(stack.peek())) 
                {
                	result += stack.pop();
                }
                stack.push(c);
            }
        }
 
        // Pop remaining operators from stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "Invalid Expression";
            result += stack.pop();
        }
		
		return result;

	}
	
	int opPriority(char ch)
    {
        switch (ch) {
        case '+':
        case '-':
            return 1;
 
        case '*':
        case '/':
            return 2;
 
//        case '^':
//            return 3;
        }
        return -1;
    }

	public Node postfixToExpressionTree(String postfix) {

    	// Base
        if (postfix == null || postfix.length() == 0)
            return null;

        Stack<Node> s = new Stack<>();
 
        for (char c: postfix.toCharArray())
        {
        	// Add operands as leaf nodes, and add them into stack
        	if (Character.isLetterOrDigit(c)) {
        		s.add(new Node(c));
        	}
        	else { // Operator
                
            	// Pop 2 items from stack
                Node right = s.pop();
                Node left = s.pop();
 
                // Assign nodes as child of operator
                Node node = new Node(String.valueOf(c), left, right);
 
                // Add node to stack
                s.add(node);

        	}
        }
 
        // Return root node
        return s.peek();
	
	}
	
	public void printTree(Node root) {
		// TODO Auto-generated method stub
		
	}

	public double solveExpressionTree(Node root) {

        // Base
        if (root == null || root.value.equals("null"))
            return 0;
 
        // If node is a leaf node
        if (root.left == null && root.right == null) {
        	
        	// Retrieve value from HashMap and return
        	if (!Character.isDigit(root.value.charAt(0)))
        		return Double.valueOf(numberMap.get(root.value.charAt(0)));
        	// Return value
        	else
        		return Double.valueOf(root.value);
        }
 
        // Recursive, depth-first (post-traversal: l-r-R)
        double x = solveExpressionTree(root.left);
        double y = solveExpressionTree(root.right);
 
        // Calculate
        return calculate(root.value, x, y);
    
	}
	
	double calculate(String op, double x, double y)
    {
        if (op.equals("+")) { return x + y; }
        if (op.equals("-")) { return x - y; }
        if (op.equals("*")) { return x * y; }
        if (op.equals("/")) { return x / y; }
 
        return 0;
    }

}
