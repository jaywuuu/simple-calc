package wu.jay.simplecalc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* TODO: be more rigorous with checking input statements.
 * Statements such as this: (1+)3 need to be handled.
 */

public class Calculator {
	public static enum Operator {
		PLUS('+', 1),
		MINUS('-', 1),
		MULTIPLY('*', 2),
		DIVIDE('/', 2),
		BRACKETL('(', 0),
		BRACKETR(')', 0),
		NOTFOUND('?', 0);
		
		private char opChar;
		private int priority;
		
		private Operator(char c, int p) {
			opChar = c;
			priority = p;
		}
		
		public char getChar() { return opChar; }
		public int getPriority() { return priority; }
		public static Operator getOp(char c) {
			for (Operator op : Operator.values()) {
				if (c == op.getChar()) return op;
			}
			return NOTFOUND;
		}
	}
	
	public static enum Key {
		CLEAR,
		EQUALS;
	}
	
	private static final int PRECISION = 100000000;
	private Stack<Double> numberStack;
	private double total = 0;
	private String inputExpression;
	private String output;
	private boolean failState = false;
	
	public Calculator() {
		numberStack = new Stack<Double>();
		total = 0;
		inputExpression = "";
		output = "";
	}
	
	public void setInput(String in) { inputExpression = in; }
	
	public double getTotal() { return total; }
	public String getOutput() { return output; }
	public boolean getFailState() { return failState; }
	
	public void handleInput(double num) {
		numberStack.push(num);
	}

	public void handleKeyPress(Key k) {
		 if (k == Key.CLEAR) {
			 total = 0;
			 inputExpression = "";
			 output = "";
			 failState = false;
			 numberStack.removeAllElements();
		 }
		 else if (k == Key.EQUALS) {
			 List<String> parsedExpr = parseInputExpr(inputExpression);
			 if (!failState) {
				total = evalExpr(parsedExpr);
			 	output = Double.toString((double)Math.round(total*PRECISION)/PRECISION);
			 }
		 }
	}
	
	public double popAndCalc(Operator op) {
		double b = numberStack.pop();
		double a = numberStack.pop();
		return calc(a, b, op);
	}
	
	private List<String> parseInputExpr(String input) {
		List<String> expr = new ArrayList<String>();
		
		if (!isValidInput(input)) {
			output = "Invalid input expression.";
			failState = true;
			return expr;
		}
		
		Stack<Operator> opStack = new Stack<Operator>();
		StringBuffer num = new StringBuffer();
		
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if ( c == ' ') continue;
			
			Operator op = Operator.getOp(c);
			
			if (op != Operator.NOTFOUND) {
				if (num.length() > 0) {
					expr.add(num.toString());
					num.delete(0, num.length());
				}
				if (op == Operator.BRACKETR) {
					while (!opStack.isEmpty()) {
						op = opStack.pop();
						if (op == Operator.BRACKETL) break;
						else expr.add(String.valueOf(op.getChar()));
					}
				}
				else if (opStack.isEmpty() ||
						op.getPriority() > opStack.peek().getPriority() ||
						op == Operator.BRACKETL) {
					opStack.push(op);
				}
				else if (op.getPriority() <= opStack.peek().getPriority()) {
					expr.add(String.valueOf(opStack.pop().getChar()));
					opStack.push(op);
				}
			}
			else {
				num.append(c);
			}
		}
		if (num.length() > 0) {
			expr.add(num.toString());
			num.delete(0, num.length());
		}
		
		while (!opStack.isEmpty()) {
			Operator op = opStack.pop();
			expr.add(String.valueOf(op.getChar()));
		}
		
		return expr;
	}
	
	private double evalExpr(List<String> expr) {
		for (String token : expr) {
			Operator op = Operator.getOp(token.charAt(0));
			if (op == Operator.NOTFOUND) {
				numberStack.push(Double.valueOf(token));
			}
			else {
				double temp = popAndCalc(op);
				numberStack.push(temp);
			}
		}
		
		if (!numberStack.isEmpty()) {
			return numberStack.peek();
		}	
		return 0;
	}
	
	/* Really just makes sure that brackets are balanced.
	 * No ) without a ( and vice versa.
	 * Should probably use a RegExp to check for valid statemnt.
	 * [\\(]*[0-9]+\\.*[0-9]+[+/\\-*]{1}[\\)]*
	 */
	private boolean isValidInput(String input) {
		Stack<Character> bra = new Stack<Character>();
		for (char c : input.toCharArray()) {
			if (c < '(' || c >'9') return false;
			if ('(' == c) bra.push(c); 
			else if (')' == c) {
				if (bra.isEmpty()) return false;
				bra.pop();
			}
		}
		
		if (bra.isEmpty()) return true;
		else return false;
	}
	
	private double calc(double a, double b, Operator op) {
		switch (op) {
		case PLUS:
			return add(a, b);
		case MINUS:
			return subtract(a, b);
		case MULTIPLY:
			return multiply(a, b);
		case DIVIDE:
			return divide(a, b);
		default:
			return 0;
		}
	}
	
	private double add(double a, double b) {
		return a+b;
	}
	
	private double subtract(double a, double b) {
		return a-b;
	}
	
	private double multiply(double a, double b) {
		return a*b;
	}
	
	private double divide(double a, double b) {
		return a/b;
	}
}
