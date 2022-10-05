package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class ExpressionTreeUtil {

	private static Map<Character,Integer> numberMap = new HashMap<>();
	private static LinkedList<String> tree = new LinkedList<>();
	private String[] expTree;
	
	public void clearTree() {
		tree = new LinkedList<>();
		expTree = null;
	}
	
	public Map<Character,Integer> storeNumbers(String input) {
		
    	Map<Character,Integer> result = new HashMap<>();
    	String s = "";
    	int index = 0;
    	
    	// Traverse through string
    	for(int i=0; i<input.length(); i++)
    	{
    		char c = input.charAt(i);
    		// If digit, get the whole integer
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
    		} else if (c == '-')
    		{
    			// Handling for negative numbers, not taking into consideration "--a"
    			ArrayList<Character> negOps = new ArrayList<>();
    			negOps.add('+');
    			negOps.add('*');
    			negOps.add('/');
    			negOps.add('(');
    			
    			// Negative sign if 1. at start of expression 2. after operator 3. after open parenthesis
    			if (i==0 || negOps.contains(input.charAt(i-1)))
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
            	if (!stack.contains('('))
            	{
            		return "Invalid Expression";
            	}
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
 
        // Remove handling for ^, since problem does not require
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
        	Integer value = null;
        	if (Character.isLetter(c)) {
				value = numberMap.get(c);
        	} else if (Character.isDigit(c))
			{
				value = Character.getNumericValue(c);
			} else { // Operator
                
				Node left = null;
				Node right = null;
            	// Pop 2 items from stack
				if (s.size()>=2) {
	                right = s.pop();
	                left = s.pop();
				} else
					return null;
 
                // Assign nodes as child of operator
                Node node = new Node(String.valueOf(c), left, right);
 
                // Add node to stack
                s.add(node);

        	}
			
        	// Create node and add to stack
        	if (value != null) {
        		
        		// Negative value handling
        		if (value < 0)
        		{
        			value *= -1;
        			Node numberNode = new Node(value);
        			Node negativeNode = new Node("-",null,numberNode);
        			s.add(negativeNode);
        		} else {
        			// Positive
            		s.add(new Node(value));
        		}

        	}
        	
        }
 
        // Return root node
        if (!s.isEmpty())
        	return s.peek();
        else 
        	return null;
	
	}
	
	public String printTree(Node root) {

		// Create array of size 2^height-1
		int height = getHeight(root);
		
		int size = (int) Math.pow(2,height);
		expTree = new String[size-1];
		// Place items in array
		populateTree(root);
		
		// Print array contents
		String result = new ArrayList<String>(Arrays.asList(expTree)).toString();
		System.out.println(result);
		return result;	
	}

	public void populateTree(Node root) {

		root.position=0;
		Queue<Node> fifo = new LinkedList<>();
		fifo.add(root);
		
		// Pop 1, push left&right, left-index=2n+1, right-index=2n+2
		while (!fifo.isEmpty())
		{
			Node node = fifo.remove();
			if (node.left != null)
			{
				Node left = node.left;
				left.position = 2*node.position+1;
				fifo.add(left);
			}
			if (node.right!= null)
			{
				Node right = node.right;
				right.position = 2*node.position+2;
				fifo.add(right);
			}
			
			expTree[node.position] = node.value;
		}
		
	}

	int getHeight(Node root) {
        
		if (root == null)
            return 0;
        else {
            // Recursion
            int lheight = getHeight(root.left);
            int rheight = getHeight(root.right);
 
            // Return longer "branch"
            if (lheight > rheight)
                return (lheight + 1);
            else
                return (rheight + 1);
        }

	}

	public double solveExpressionTree(Node root) {

        // Base
        if (root == null || root.value.equals("null"))
            return 0;
 
        // If node is a leaf node
        if (root.left == null && root.right == null) {
        	
        	return Double.valueOf(root.value);
        }
 
        // Recursive, depth-first (post-traversal: l-r-R)
        double x = solveExpressionTree(root.left);
        double y = solveExpressionTree(root.right);
 
        // Calculate
        return calculate(root.value, x, y);
    
	}
	
	public double calculate(String op, double x, double y)
    {
        if (op.equals("+")) { return x + y; }
        if (op.equals("-")) { return x - y; }
        if (op.equals("*")) { return x * y; }
        if (op.equals("/")) { return x / y; }
 
        return 0;
    }

}
