public class resistor
{	
	//global vars to be passed to valueToColor
	double resistance;
	String rawInput;
	
	//global vars to be passed to colorToValue
	String[] resistcolors;
	
	//0-9 are normal colors, 10 is Gold (0.1 multiplier) and 11 is Silver (0.01 multiplier)
	String[] digitColors = {"Black","Brown","Red","Orange","Yellow","Green","Blue","Violet","Gray","White","Gold","Silver"};
	double[] digitTolerance = {0,1,2,0,0,0.5,0.25,0.1,0,0,5,10};

	
	public resistor(double resistval, String input)
	{
		//pass arg to global var
		rawInput = input;
		resistance = resistval;
		valueToColor();
		
	}
	
	public resistor(String[] colors)
	{
		colorToValue(colors);
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
				
			}
			else if(args[0].equals("color") || args[0].equals("colors") || args[0].equals("colour") || args[0].equals("colours"))
			{
				//from here we start with arg[1]
				String[] inputcolors = new String[args.length-1];
				
				for(int i = 0; i < inputcolors.length; i++)
				{
					inputcolors[i] = args[i+1].toUpperCase();
				}	
				resistor r = new resistor(inputcolors);
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
	
	public void colorToValue(String[] color)
	{
		if(color.length == 4)
		{
			double[] digits = new double[4];
		
			for(int i = 0; i < color.length; i++)
			{
				for(int c = 0; c < digitColors.length; c++)
				{
					if(digitColors[c].equalsIgnoreCase(color[i]))
					{
						digits[i] = c;
					}
				}
			}
			
			int digit1 = (int) digits[0];
			int digit2 = (int) digits[1];
			
			String firstTwo = Integer.toString(digit1)+Integer.toString(digit2);
			int firstTwoInt = Integer.parseInt(firstTwo);
			double value;
			if(digits[2] <9)
			{
				value = firstTwoInt * Math.pow(10,digits[2]);
				System.out.print((int)value);
			}
			else
			{
				value = firstTwoInt * Math.pow(10,9-digits[2]);
				System.out.print(value);
			}
			double tolerance = digitTolerance[(int)digits[3]];
			if(tolerance != 0)
				System.out.println(" ohms (+/- " +tolerance +"%)");
			else
				System.out.println("Invalid input: No such tolerance color");
		}
		else
		{
			System.out.println("Only 4-color resistors are supported at this time.");
			System.exit(1);
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