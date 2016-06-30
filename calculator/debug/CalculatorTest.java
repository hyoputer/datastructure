import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorTest
{
	
	public static final Pattern Num = Pattern.compile("\\d*");
	
	static HashMap<Character, Integer> priority = new HashMap<Character, Integer>(){{
		put('^', 4);
		put('~', 3);
		put('%', 2);
		put('/', 2);
		put('*', 2);
		put('+', 1);
		put('-', 1);
	}};
	
	
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println("ERROR");
			}
		}
	}

	private static void command(String input) throws Exception
	{
		Postfix postfix = infixtopostfix(input);
		
		Long result = calculate(postfix.getQueue());
		
		System.out.println(postfix.getString());
		
		System.out.println(result);
	}
	
	private static Postfix infixtopostfix(String input) throws Exception
	{
		Stack stack = new Stack();
		Queue queue = new Queue();
		String postfix = "";
		int ph = 0;
		char ch;
		boolean opcheck = true; //true 일 때에는 unary'-'나 괄호를 제외한 연산자는 나오면 안되고 false일때는 숫자가 나오면 안된다
		boolean pff = false; // string에 추가될 때 true
		boolean ini = false; // 후위 표현식에서 반복문 돌때 공백처리
		
		input = input.replaceFirst("\\s*", "");
		
		while(true)
		{
			pff = false;
			ch = input.charAt(0);
			
			if('0' <= ch && ch <= '9')
			{
				if(!opcheck)
					throw new Exception();
				
				Matcher matnum = Num.matcher(input);
				while(matnum.find())
				{
					String num = input.substring(matnum.start(), matnum.end());
					postfix += num;
					pff = true;
					queue.enqueue(Long.parseLong(num));
					input = input.substring(matnum.end());
					break;
				}
				opcheck = false;
			}
			else
			{
				if(ch == ')')
				{
					if(opcheck)
					{
						throw new Exception();
					}
					
					ini = false;
					
					while(true)
					{
						char temp = (char) stack.pop();
						
						if(temp == '(')
						{
							break;
						}
						else if(stack.isEmpty())
							throw new Exception();
						
						if(ini == true)
							postfix += " ";
						
						postfix += temp;
						queue.enqueue(temp);
						ini = true;
						pff = true;
					}
					ph--;
					opcheck = false;
				}
			
				else if(ch == '(')
				{
					if(!opcheck)
						throw new Exception();
					ph++;
					opcheck = true;
					stack.push(ch);
				}
				else
				{
					if(ch == '-')
					{
						if(opcheck)
						{
							ch = '~';
						}
					}
					else
					{
						if(opcheck)
						{
							throw new Exception();
						}
					}
					
					ini = false;
				
					while(!stack.isEmpty()) //일반적인 연산자들 일괄처리
					{
						char top = (char) stack.peek();
						
						if(top == '(')
							break;
						else if(priority.get(top) >= priority.get(ch))
						{
							if((ch == '^' || ch == '~') && top == ch)
								break;
							stack.pop();
							if(ini == true)
								postfix += " ";
							postfix += top;
							pff = true;
							ini = true;
							queue.enqueue(top);
						}
						else
							break;
					}
					stack.push(ch);
					opcheck = true;
				}
				input = input.substring(1);
			}
			
			input = input.replaceFirst("\\s*", "");
			
			if(input.equals(""))
			{
				while(!stack.isEmpty())
				{
					char temp = (char) stack.pop();
					
					if(postfix.endsWith(" "))
						postfix += temp;
					else
						postfix += " " + temp;
					pff = false;
					queue.enqueue(temp);
				}
				break;
			}
			
			if(pff == true)
				postfix += " ";
		}
		if(ph != 0)
			throw new Exception();
		
		return new Postfix(postfix, queue);
	}
	
	private static Long calculate(Queue postfix) throws Exception
	{
		Stack stack = new Stack();
		Long result = null;
		
		while(!postfix.isEmpty())
		{
			Object item = postfix.dequeue();
			if(item.getClass().getSimpleName().equals("Long"))
			{
				stack.push(item);
				
				if(postfix.isEmpty())
					result = (Long)item;
			}
			else
			{
				if((char)item == '~')
				{
					Long num = (Long) stack.pop();
					num = -num;
					stack.push(num);
					
					if(postfix.isEmpty())
						result = num;
				}
				
				else
				{
					Long num2 = (Long) stack.pop();
					Long num1 = (Long) stack.pop();
					
					switch((char)item)
					{
					case '+': 
						result = num1 + num2;
						break;
					case '-':
						result = num1 - num2;
						break;
					case '*':
						result = num1 * num2;
						break;
					case '/':
						result = num1 / num2;
						if(num2 == 0)
							throw new Exception();
						break;
					case '%':
						result = num1 % num2;
						if(num2 == 0)
							throw new Exception();
						break;
					case '^':
						result = (long) Math.pow(num1, num2);
						if(num1 == 0 && num2 < 0)
							throw new Exception();
						break;
					}
					
					stack.push(result);
				}
			}
		}
		
		return result;
	}
}

class Postfix
{
	private String string;
	private Queue queue;
	
	Postfix(String newstring, Queue newqueue)
	{
		string = newstring;
		queue = newqueue;
	}
	
	public String getString()
	{
		return string;
	}
	
	public Queue getQueue()
	{
		return queue;
	}
}

class Stack 
{
	private Node top;
	
	Stack()
	{
		top = null;
	}
	
	public boolean isEmpty()
	{
		return top == null;
	}
	
	public void push(Object newItem)
	{
		top = new Node(newItem, top);
	}
	
	public Object pop() throws Exception
	{
		if(!isEmpty())
		{
			Node temp = top;
			top = top.getNext();
			return temp.getItem();
		}
		else
		{
			throw new Exception();
		}
	}
	
	public void popAll()
	{
		top = null;
	}
	
	public Object peek() throws Exception
	{
		if(!isEmpty())
		{
			return top.getItem();
		}
		else
		{
			throw new Exception();
		}
	}
	
}

class Node
{
	private Object item;
	private Node next;
	
	Node(Object newItem)
	{
		item = newItem;
		next = null;
	}
	
	Node(Object newItem, Node node)
	{
		this.item = newItem;
		this.next = node;
	}
	
	public Object getItem()
	{
		return item;
	}
	
	public void setNext(Node node)
	{
		this.next = node; 
	}
	
	public Node getNext()
	{
		return this.next;
	}
}

class Queue
{
	private Node lastNode;
	
	Queue()
	{
		lastNode = null;
	}
	
	public boolean isEmpty()
	{
		return lastNode == null;
	}
	
	public void enqueue(Object newitem)
	{
		Node newNode = new Node(newitem);
		
		if(isEmpty())
		{
			newNode.setNext(newNode);
		}
		else
		{
			newNode.setNext(lastNode.getNext());
			lastNode.setNext(newNode);
		}
		
		lastNode = newNode;
	}
	
	public Object dequeue() throws Exception
	{
		if(!isEmpty())
		{
			Node firstNode = lastNode.getNext();
			
			if(lastNode == firstNode)
			{
				lastNode = null;
			}
			else
			{
				lastNode.setNext(firstNode.getNext());
			}
			
			return firstNode.getItem();
		}
		else
			throw new Exception();
	}
}