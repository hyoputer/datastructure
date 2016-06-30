import java.io.BufferedReader; 
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
	boolean sign;
	int [] arr = new int[200];
	int length;
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "ERROR";
 
    public static final Pattern Num = Pattern.compile("(\\d*\\d).[+-]*(\\d*\\d)");
 
    public BigInteger(boolean sign, String s)
    {
    	this.sign = sign;
    	for(int i = 0; i < s.length(); i++) { 
    		this.arr[s.length() - i - 1] = Integer.parseInt(s.substring(i, i + 1));
    	}
    	this.length = s.length();
    }
 
    public BigInteger add(BigInteger big)
    {
    	if(this.sign == big.sign) {
    		int lng = (this.length > big.length) ? this.length : big.length;
    		for(int i = 0; i < lng; i++) {
    			this.arr[i] += big.arr[i];
    			if(this.arr[i] >= 10) {
    				this.arr[i + 1]++;
    				this.arr[i] -= 10;
    			}
    		}
    		this.length = lng;
    		if(this.arr[this.length] != 0) 
    			this.length++;
    		return this;
    	}
    	else
    	{
    		big.sign = !(big.sign);
    		return this.subtract(big);
    	}
    }
 
    public BigInteger subtract(BigInteger big)
    {
    	boolean comp = true; 
    	BigInteger absB, absS;
    	int f = 0;
    	if(this.sign == big.sign)
    	{
    		if(this.length != big.length)
    		{
    			comp = (this.length > big.length) ? true : false;
    		}
    		else 
    		{
    			for(int i = this.length - 1; i >= 0; i--) 
    			{
    				if(this.arr[i] != big.arr[i])
    				{
    					f = 1;
    					comp = (this.arr[i] > big.arr[i]) ? true : false;
    					break;
    				}
    			}
    			if(f == 0) 
    				return new BigInteger(true, "0");
    		}
    		
    		if(comp == true)
    		{
    			absB = this;
    			absS = big;
    		}
    		else
    		{
    			absB = big;
    			absB.sign = !(absB.sign);
    			absS = this;
    		}
    		
    		for(int i = 0; i < absB.length; i++)
    		{
    			absB.arr[i] -= absS.arr[i];
    			if(absB.arr[i] < 0) 
    			{
    				absB.arr[i] += 10;
    				absB.arr[i + 1]--;
    			}
    		}
    		
    		while(absB.arr[absB.length - 1] == 0) 
    			absB.length--;
    		
    		
    		return absB;
    	}
    	else
    	{
    		big.sign = !(big.sign);
    		return this.add(big);
    	}
    }
 
    public BigInteger multiply(BigInteger big)
    {
    	BigInteger result = new BigInteger(true, "0");

    	if(this.sign == big.sign) 
    	{
    		result.sign = true;
    	}
    	else
    		result.sign = false;
    
    	for(int i = 0; i < big.length ; i++) 
    	{
    		for(int j = 0; j < this.length; j++) 
    		{
    			result.arr[i + j] +=(this.arr[j] * big.arr[i]);
    			if((result.arr[i + j] / 10) != 0) 
    			{ 
    				result.arr[i + j + 1] += (result.arr[i + j] / 10);
    				result.arr[i + j] %= 10;
    			}
    		}
    	}
    	
    	result.length = this.length + big.length;
    	while(result.arr[result.length - 1] == 0) 
    	{ 
    		result.length--;
    		if(result.length == 1) 
    			break;
    	}
    	return result;
    }
    
    public void print() 
    {
    	if(this.sign == false)
    		System.out.print("-");
    	for(int i = this.length - 1; i >= 0; i--) 
    	{
    		System.out.print(this.arr[i]);
    	}
    	System.out.println("");
    }
    
    public static int chop (char a) 
    {
    	switch(a) 
    	{
    	case '-':
    		return 0;
    	case '*':
    		return 2;
    	case '+':
    		return 1;
    	default:
    		return -1;
    	}
    }
    
     
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
    	int op, p_op;
    	boolean s1 = true, s2 = true;
    	BigInteger num1, num2;
    	
    	input = input.replace(" ","");
    	Matcher matnum = Num.matcher(input);
    	while(matnum.find())
    	{
    		if(matnum.start(1) != 0 && input.charAt(0) == '-')
				s1 = false;
    		num1 = new BigInteger(s1, input.substring(matnum.start(1), matnum.end(1)));
    		
    		p_op = matnum.end(1);
            
            op = chop(input.charAt(p_op));
          
            if(matnum.start(2) != p_op + 1 && input.charAt(p_op + 1) == '-')
				s2 = false;
            
        	num2 = new BigInteger(s2, input.substring(matnum.start(2), matnum.end(2)));
        	
        	switch(op) 
        	{
        	case 2:
        		return num1.multiply(num2);
        	case 0:
        		return num1.subtract(num2);
        	case 1:
        		return num1.add(num2);
        	}
    	}
    	
    	return new BigInteger(false, "0");
    	
    }
    
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
 
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            result.print(); 
            return false;
        }
    }
 
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
