package expr;

import java.util.Stack;
import java.util.StringTokenizer;

public class EspressioneAritmetica {
	private String expr;
	private Stack<String> operatori;
	private Stack<Integer> operandi;

	public EspressioneAritmetica(String expr){
		String regex = "([\\^*/%\\+-]|\\d|[\\()])+";
		expr = expr.trim();
		expr = expr.replaceAll(" ", "");
		if(!expr.matches(regex)) throw new RuntimeException("Stringa non valida");
		int pAperte = expr.length() - expr.replace("(", "").length();
		int pChiuse = expr.length() - expr.replace(")", "").length();
		if(pAperte != pChiuse) throw new RuntimeException("Stringa non valida! Controlla le parentesi!");

		this.expr = expr;
		
		operatori = new Stack<>();
		operandi  = new Stack<>();
	}//EspressioneAritmetica
	
	public int valutaEspressione(){
		String delims = "+-*/%^()";
		StringTokenizer st = new StringTokenizer(expr, delims, true);
		return valutaEspressione(st);
	}//valutaEspressione (no param.)
	
	
	private int valutaEspressione(StringTokenizer st){   
		String regex = "[\\^*/%\\+-]";
		Stack<String> operatori = new Stack<>(); 
		Stack<Integer> operandi = new Stack<>();
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			if(token.matches(regex)) {
				if(piuPrioritario(token, operatori)){
					operatori.push(token);
				}//if interno
				else{
					while(!piuPrioritario(token, operatori)){
						eseguiCalcolo(operatori, operandi);
					}//while
					operatori.push(token);
				}//else
			}//if esterno
			else if(token.equals("(")){
				operandi.push(valutaEspressione(st));
			}//else if (esterno)
			else if(token.equals(")")){
				break;
			}//else if (esterno)
			else {
				operandi.push(Integer.parseInt(token));
			}//else (esterno)
		}
		while(!operatori.empty()){
			eseguiCalcolo(operatori, operandi);
		}//while
		if(operandi.size() != 1)	throw new RuntimeException("Espressione non valida!");
		return operandi.pop(); //final op.
	}//method
	
	private boolean piuPrioritario(String token, Stack<String> operatori) {
		if(operatori.empty())
			return true;
		String op = operatori.peek();
		if(token.equals("^"))
			return true;
		if(op.equals("^"))
			return false;
		if((token.equals("/") || token.equals("*") || token.equals("%")) && (op.equals("/") || op.equals("*") || op.equals("%")) )
			return false;
		if( token.equals("/") || token.equals("*") || token.equals("%") )
			return true;
		return false;
	}//method
	
	private void eseguiCalcolo(Stack<String> operatori, Stack<Integer> operandi){
		Integer oper2 = operandi.pop(); //operatore 2
		Integer oper1 = operandi.pop(); //operatore 1
		String operatore = operatori.pop();
		switch(operatore){
		case "^": 
				Integer pow = (int) Math.pow(oper1,oper2);
				operandi.push(pow);
				break;
		case "+":
				Integer sum = oper1 + oper2;
				operandi.push(sum);
				break;
		case "-":
			Integer sub = oper1 - oper2;
			operandi.push(sub);
			break;
		case "*":
			Integer mul = oper1 * oper2;
			operandi.push(mul);
			break;
		case "/":
			Integer div = oper1 / oper2;
			operandi.push(div);
			break;
		case "%":
			Integer rem = oper1 % oper2;
			operandi.push(rem);
			break;		
		}
	}

	public String toString(){
		return this.expr;
	}//toString

	
	public static void main(String[] args) {
		//semplice main applicativo
		EspressioneAritmetica espr = new EspressioneAritmetica(" 3 * ( 7 + 2 - 1) + ( (8+4*3) ) ");
		System.out.println(espr);
		System.out.println(espr.valutaEspressione());
	}//main

}//end
