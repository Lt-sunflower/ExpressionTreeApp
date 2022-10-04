package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import main.ExpressionTreeUtil;
import main.Node;

class ExpressionTreeUtilTest {

	ExpressionTreeUtil expressionTreeUtil = new ExpressionTreeUtil();

	// Data
	
	@Test
	void testStoreNumberMapLength() {
		
		Map<Character, Integer> map = expressionTreeUtil.storeNumbers("1+2+3+4");
		assertEquals(4, map.size());
		
	}
	
	@Test
	void testStoreNumberMapLength2() {
		
		Map<Character, Integer> map = expressionTreeUtil.storeNumbers("2+2+2+2");
		assertEquals(1, map.size());
		
	}
	
	@Test
	void testStoreNumberMapContent() {
		
		Map<Character, Integer> map = expressionTreeUtil.storeNumbers("1+2+3+4");
		Boolean check = map.containsValue(1) && map.containsValue(2) && map.containsValue(3) && map.containsValue(4);
		
		assertTrue(check);
		
	}
	
	@Test
	void testStoreNumberMapTwoDigitsOrLarger() {
		
		Map<Character, Integer> map = expressionTreeUtil.storeNumbers("10+200");
		Boolean check = map.containsValue(10) && map.containsValue(200);
		
		assertTrue(check);
		
	}
	
	@Test
	void testStoreNumberMapNegativeAndZero() {
		
		Map<Character, Integer> map = expressionTreeUtil.storeNumbers("-5*0");
		Boolean check = map.containsValue(-5) && map.containsValue(0);
		
		assertTrue(check);
		
	}
	
	@Test
	void testInfixToPostfixInt() {
		
		String s = expressionTreeUtil.infixToPostfix("1+2+3+4");
				
		assertEquals("12+3+4+", s);
		
	}
	
	@Test
	void testInfixToPostfixInt2() {
		
		// https://www.free-online-calculator-use.com/infix-to-postfix-converter.html
		String s = expressionTreeUtil.infixToPostfix("8/2*(2+2)");
				
		assertEquals("82/22+*", s);
		
	}
	
	@Test
	void testInfixToPostfixInt3() {
		
		// https://www.free-online-calculator-use.com/infix-to-postfix-converter.html
		String s = expressionTreeUtil.infixToPostfix("((15/(7-(1+1)))*-3)-(2+(1+1))");
				
		assertEquals("15711+-/*3-211++-", s);
		
	}
	
	@Test
	void testInfixToPostfixChar() {
		
		String s = expressionTreeUtil.infixToPostfix("A+B+C+D");
				
		assertEquals("AB+C+D+", s);
		
	}
	
	@Test
	void testInfixToPostfixInvalid() {
		
		String s = expressionTreeUtil.infixToPostfix("(1+2)(");
				
		assertEquals("Invalid Expression", s);
		
	}
	
	@Test
	void testInfixToPostfixInvalid2() {
		
		String s = expressionTreeUtil.infixToPostfix("(1+2))");
				
		assertEquals("Invalid Expression", s);
		
	}
	
	@Test
	void testBuildPrintExpressionTree() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("12+3+4+");
		
		String s = expressionTreeUtil.printTree(root);
		
		assertEquals("[+,+,4,+,3, , ,1,2, , , , , , ]", s);
		
	}
	
	@Test
	void testBuildPrintExpressionTree2() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("82/22+*");
		
		String s = expressionTreeUtil.printTree(root);
		
		assertEquals("[*,/,+,8,2,2,2]", s);
		
	}
	
	@Test
	void testBuildPrintExpressionTree3() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("15711+-/*3-211++-");
		
		String s = expressionTreeUtil.printTree(root);
		
		// Limitation of not able to handle >1digit integers
		assertNotEquals("[-,*,+,/,-,2,+,15,-, ,3, , ,1,1, , ,7,+, , , , , , , , , , , , , , , , , , ,1,1, , , , , , , , , , , , , , , , , , , , , , , , ]", s);
		
	}
	
	@Test
	void testBuildPrintExpressionTreeInvalidString() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("hello");
				
		assertEquals(null, root);
		
	}
	
	@Test
	void testBuildPrintExpressionTreeInvalidString2() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("5++9");
				
		assertEquals(null, root);
		
	}
	
	@Test
	void testBuildPrintExpressionTreeNull() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree(null);
				
		assertEquals(null, root);
		
	}
	
	@Test
	void testBuildPrintExpressionTree3_2() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		String input = "((15/(7-(1+1)))*-3)-(2+(1+1))";
		
		Map<Character,Integer> numberMap = expressionTreeUtil.storeNumbers(input);
		
		for(Character key : numberMap.keySet()){
			String value = numberMap.get(key).toString();
			input = input.replaceAll(value,key.toString());
		}
		
		String postfix = expressionTreeUtil.infixToPostfix(input);
		
		Node root = expressionTreeUtil.postfixToExpressionTree(postfix);
		
		String s = expressionTreeUtil.printTree(root);
		
		// Handles >2digits by using value from HashMap
		assertEquals("[-,*,+,/,-,2,+,15,-, ,3, , ,1,1, , ,7,+, , , , , , , , , , , , , , , , , , ,1,1, , , , , , , , , , , , , , , , , , , , , , , , ]", s);
	}
	
	@Test
	void testSolveExpressionTree() {
		
		// PrintTree function to test
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("12+3+4+");
		
		Double d = expressionTreeUtil.solveExpressionTree(root);
		
		assertEquals(Double.valueOf(10.0), d);
		
	}
	
	@Test
	void testSolveExpressionTree2() {
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("82/22+*");
		
		Double d = expressionTreeUtil.solveExpressionTree(root);
		
		assertEquals(Double.valueOf(16.0), d);
		
	}
	
	@Test
	void testSolveExpressionTree3() {
		
		expressionTreeUtil.clearTree();
		
		Node root = expressionTreeUtil.postfixToExpressionTree("15711+-/*3-211++-");
		
		Double d = expressionTreeUtil.solveExpressionTree(root);
		
		// Limitation of not able to handle >1digit integers
		assertNotEquals(Double.valueOf(-13.0), d);		
	}
	
	@Test
	void testSolveExpressionTree3_2() {
		
		expressionTreeUtil.clearTree();
		
		String input = "((15/(7-(1+1)))*-3)-(2+(1+1))";
		
		Map<Character,Integer> numberMap = expressionTreeUtil.storeNumbers(input);
		
		for(Character key : numberMap.keySet()){
			String value = numberMap.get(key).toString();
			input = input.replaceAll(value,key.toString());
		}
		
		String postfix = expressionTreeUtil.infixToPostfix(input);
		
		Node root = expressionTreeUtil.postfixToExpressionTree(postfix);
		
		Double d = expressionTreeUtil.solveExpressionTree(root);
		
		// Handles >2digits by using value from HashMap
		assertEquals(Double.valueOf(-13.0), d);		
	}
	
	@Test
	void testCalculateInvalidOperator() {
		Double d = expressionTreeUtil.calculate("#",5,9);
		
		assertEquals(Double.valueOf(0), d);
	}

}
