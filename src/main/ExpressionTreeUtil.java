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
	
	public void clearTree() {
		tree = new LinkedList<>();
	}
	
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
    		} else if (c == '-')
    		{
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
			
        	if (value != null) {
        		
        		if (value < 0)
        		{
        			value *= -1;
        			Node numberNode = new Node(value);
        			Node negativeNode = new Node("-",null,numberNode);
        			s.add(negativeNode);
        		} else {
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

		int height = getHeight(root);
		for (int i = 1; i <= height; i++) {
			populateTree(root, i);
		}
		
		return printTree();	
	}
	
	int getHeight(Node root) {
        
		if (root == null)
            return 0;
        else {
            /* compute  height of each subtree */
            int lheight = getHeight(root.left);
            int rheight = getHeight(root.right);
 
            /* use the larger one */
            if (lheight > rheight)
                return (lheight + 1);
            else
                return (rheight + 1);
        }

	}
	
	void populateTree(Node node, int parent) {
		// Add values to linkedlist sequentially using BFS
        if (node == null) {
        	tree.add(" ");
            return;
        }
        if (parent == 1){
        	tree.add(node.value);
        }
        else if (parent > 1) {
        	populateTree(node.left, parent - 1);
        	populateTree(node.right, parent - 1);
        }	
	}
	
    String printTree() {
    	StringBuilder sb = new StringBuilder();
    	Iterator<String> iterator = tree.iterator();
		sb.append("Expression Tree: [");
		while (iterator.hasNext()) {
			sb.append(iterator.next()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		
		System.out.println(sb);
		return sb.toString().substring(17);
	}


	public double solveExpressionTree(Node root) {

        // Base
        if (root == null || root.value.equals("null"))
            return 0;
 
        // If node is a leaf node
        if (root.left == null && root.right == null) {
        	
        	// Retrieve value from HashMap and return (not required as value already converted for Exp Tree)
//        	if (!Character.isDigit(root.value.charAt(0)))
//        		return Double.valueOf(numberMap.get(root.value.charAt(0)));
        	// Return value
//        	else
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
