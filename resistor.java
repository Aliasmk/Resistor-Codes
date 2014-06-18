public class resistor
{
	
	int resistance;
	//0-9 are normal colors, 10 is Silver (0.01 multiplier) and 11 is Gold (0.1 multiplier)
	String[] digitColors = {"Black","Brown","Red","Orange","Yellow","Green","Blue","Violet","Gray","White","Silver","Gold"};
	
	public resistor(int resistval)
	{
		resistance = resistval;
		start();
	}
	
	public static void main(String[] args)
	{
		int resistval = 0;
		if(args.length > 0)
		{	
			try
			{
				
				resistval = Integer.parseInt(args[0]);
			}
			catch(Exception e)
			{
				System.out.println("Invalid Input. Please enter an integer. (Decimals are not supported yet.)");
				System.exit(1);
			}
			
			resistor r = new resistor(resistval);
		}
		else
		{
			System.out.println("Usage: java resistor resistance_value");
			System.exit(0);
		}
	}
	
	public void start()
	{
		
		String inputstring = Integer.toString(resistance);
		int length = inputstring.length();
		
		boolean decimal = false;
		int zeroafterpoint = 0;
		
		//print value
		System.out.println("Resistor Value: " +resistance +" ohms");
		//tranfer into char array
		
		char[] input = new char[length];
		for(int i = 0; i<length; i++)
		{
			if(decimal)
				zeroafterpoint++;
			input[i] = inputstring.charAt(i);
			if(input[i] == '.' && input[0] == 0)
				decimal = true;
		}
		
		int digit1 = Character.getNumericValue(input[0]);
		int digit2 = Character.getNumericValue(input[1]);
		int multiplier = 0;
		
		if(decimal)
			multiplier = 9+zeroafterpoint;
		else
			multiplier = length-2;
		
		System.out.println("Digit 1: " +digit1);
		System.out.println("Digit 2: " +digit2);
		
		if(decimal)
			System.out.println("Multiplier: " +Math.pow(10,-(zeroafterpoint-2)));
		else
			System.out.println("Multiplier: " +Math.pow(10,length-2));
		
		
		System.out.println("Colors: " +digitColors[digit1] +" " +digitColors[digit2] +" " +digitColors[multiplier]);
		
		
	}
}