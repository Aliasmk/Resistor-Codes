public class resistor
{
	//global var to be passed to all methods
	double resistance;
	String rawInput;
	//0-9 are normal colors, 10 is Gold (0.1 multiplier) and 11 is Silver (0.01 multiplier)
	String[] digitColors = {"Black","Brown","Red","Orange","Yellow","Green","Blue","Violet","Gray","White","Gold","Silver"};
	
	public resistor(double resistval, String input)
	{
		//pass arg to global var
		rawInput = input;
		resistance = resistval;
		
	}
	
	public static void usage()
	{
		System.out.println("Usage: java resistor [value | color] [resistance_value | color1 color2 color3 color4]");
		System.exit(0);
	}
	
	public static void main(String[] args)
	{
		
		if(args.length > 0)
		{	
			if(args[0].equals("value"))
			{
				double resistval = 0;
				//Catch if the User Enters something that isnt an Integer
				try
				{
					resistval = Double.parseDouble(args[1]);		
				}
				catch(Exception e)
				{
					usage();
				}
				//Start the program as an instance
				resistor r = new resistor(resistval, args[1]);
				r.valueToColor();
			}
			else if(args[0].equals("value"))
			{
				//nothing yet
			}
			else
			{
				usage();
			}
		}
		else
		{
			//User forgot vars. Show usage.
			usage();
		}
	}
	
	public void valueToColor()
	{
		//Set Vars
		String inputstring = Double.toString(resistance);
		int length = rawInput.length();		
		boolean decimal = false;
		int zeroafterpoint = 0;
		
		//Tranfer into char array
		char[] input = new char[length];
		
		
	
		
		for(int i = 0; i<length; i++)
		{	
			if(rawInput.charAt(i) == '.')
			{
				decimal = true;
			}
		}
		
		if(decimal)
		{
			boolean record = false;
			int shiftedIndex = 0;
			for(int i = 0; i<length; i++)
			{
				if(record)
				{
					input[shiftedIndex] = inputstring.charAt(i);
					zeroafterpoint++;
					shiftedIndex++;
				}
				
				if(rawInput.charAt(i) == '.')
				{
					record = true;
				}
				
				
			}
		}
		else
		{
			for(int i = 0; i<length; i++)
			{
				input[i] = inputstring.charAt(i);
			}
		}
		
		
		
		//Set Each Value to Correspond to Color Array at the Top
		int digit1 = Character.getNumericValue(input[0]);
		int digit2 = Character.getNumericValue(input[1]);
		int multiplier;
		if(decimal)
			multiplier = 9+zeroafterpoint;
		else
			multiplier = length-2;
		
		//OUTPUT
		System.out.println("Resistor Value: " +resistance +" ohms");
		System.out.println("Digit 1: " +digit1);
		System.out.println("Digit 2: " +digit2);
		if(decimal)
			System.out.println("Multiplier: " +Math.pow(10,-(zeroafterpoint)));
		else
			System.out.println("Multiplier: " +Math.pow(10,length-2));
		System.out.println("Colors: " +digitColors[digit1] +" " +digitColors[digit2] +" " +digitColors[multiplier]);
	}
}