/*
 * Name: Aliasmk
 * Project: Resistor
 * Description: This program converts between resistor values and color codes, quickly and easily.
 * Usage: java resistor <value | color> < <resistance_value> [tolerance] | <color1> <color2> <color3> <color4> >
 */

public class resistor
{	
	//global vars to be passed to valueToColor
	double resistance;
	String rawInput;
	double tolerance;
	
	//global vars to be passed to colorToValue
	String[] resistcolors;
	
	//0-9 are normal colors, 10 is Gold (0.1 multiplier) and 11 is Silver (0.01 multiplier)
	String[] digitColors = {"Black","Brown","Red","Orange","Yellow","Green","Blue","Violet","Gray","White","Gold","Silver"};
	double[] digitTolerance = {0,1,2,0,0,0.5,0.25,0.1,0,0,5,10};

	//VALUE TO COLOR CONTRUCTOR
	public resistor(double resistval, String input, double tolerancee)
	{
		//pass arg to global var
		rawInput = input;
		resistance = resistval;
		tolerance = tolerancee;
		valueToColor();
		
	}
	
	//COLOR TO VALUE CONSTRUCTOR
	public resistor(String[] colors)
	{
		colorToValue(colors);
	}
	
	//Displays Usage Message
	public static void usage()
	{
		System.out.println("Usage: java resistor [value | color] [resistance_value tolerance | color1 color2 color3 color4]");
		System.exit(0);
	}
	
	//Takes arguments, passes vars and starts methods.
	public static void main(String[] args)
	{
		//Make sure there are arguments, otherwise show usage
		if(args.length > 0)
		{	
			//if the first arg is "value", we want to look for two doubles right after it, one for value and one for tolerance
			if(args[0].equalsIgnoreCase("value"))
			{
				double resistval = 0;
				//Catch if the User Enters something that isnt an Integer
				try
				{
					//get the first double (value)
					resistval = Double.parseDouble(args[1]);		
				}
				catch(Exception e)
				{
					usage();
				}
				
				//Start the program as an instance 
				try
				{
					//if there is a second arg (tolerance) use that in the constructor...
					resistor r = new resistor(resistval, args[1], Double.parseDouble(args[2]));
				}
				catch(Exception e)
				{
					//if there isnt a second arg (tolerance), use the default 5% value. Its really common.
					System.out.println("[warning] No tolerance value specified, assuming 5%");
					resistor r = new resistor(resistval, args[1], 5.0);
					
				}
			}
			//Otherwise if "color" or other spellings is our first arg, we switch to color input mode
			else if(args[0].equalsIgnoreCase("color") || args[0].equalsIgnoreCase("colors") || args[0].equalsIgnoreCase("colour") || args[0].equalsIgnoreCase("colours"))
			{
				//from here we start with arg[1]. For all the colors in the list we put them into an array.
				String[] inputcolors = new String[args.length-1];
				
				for(int i = 0; i < inputcolors.length; i++)
				{
					inputcolors[i] = args[i+1].toUpperCase();
				}	
				//goto contructor.
				resistor r = new resistor(inputcolors);
			}
			else
			{
				//wtf did u put as your 2nd arg lol
				usage();
			}
		}
		else
		{
			//User forgot vars. Show usage.
			usage();
		}
	}
	
	//decodes colors into values
	public void colorToValue(String[] color)
	{
		//difference mothods to calclulate for different numbers of bands.
		if(color.length == 4)
		{
			//basically search the array until you find each color's corresponding index in the array.
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
			
			//cast the first and second digits to an int.
			int digit1 = (int) digits[0];
			int digit2 = (int) digits[1];
			
			//this is ugly but does the job. Converts the casted ints into a string, concats them, then changes them back into one int.
			String firstTwo = Integer.toString(digit1)+Integer.toString(digit2);
			int firstTwoInt = Integer.parseInt(firstTwo);
			double value;
			//calculate the final value. If statement because if the multiplier is >1 we can cast it to an int to look nice.
			if(digits[2] <9)
			{
				//xy * 10^z
				value = firstTwoInt * Math.pow(10,digits[2]);
				System.out.print((int)value);
			}
			else
			{
				value = firstTwoInt * Math.pow(10,9-digits[2]);
				System.out.print(value);
			}
			
			//now deal with figuring out the tolerance
			double tolerance = digitTolerance[(int)digits[3]];
			
			//any colors that dont correspond to a tolerance are marked as zero in the array. if a zero pops up here, something went wrong.
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
	
	//encodes numerical resistor values to color codes
	public void valueToColor()
	{
		//Set Vars
		String inputstring = Double.toString(resistance);
		int length = rawInput.length();		
		boolean decimal = false;
		int zeroafterpoint = 0;
		
		//Tranfer into char array
		char[] input = new char[length];
		
		//does the input contain a decimal?  if so make sure its taken into account below when calculating the multiplier. POTENTIAL ERROR if entry is 390.5
		for(int i = 0; i<length; i++)
		{	
			if(rawInput.charAt(i) == '.')
			{
				decimal = true;
			}
		}
		
		//if its a decimal, find the first two digits after the decimal. POTENTIAL ERROR if entry is 0.3900000
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
				
				//find where the decimal is, and start looking after you found it.
				if(rawInput.charAt(i) == '.')
				{
					record = true;
				}
				
				
			}
		}
		else
		{
			//if there isnt a decimal, this is much easier, just reading everything into an array.
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
		
		//figure out the tolerance
		int tol = 0;
		for(int i = 0; i<digitTolerance.length; i++)
		{
			if(tolerance == digitTolerance[i])
				tol = i;
		}
		
		//OUTPUT
		System.out.println("Resistor Value: " +resistance +" ohms");
		System.out.println("Digit 1: " +digit1);
		System.out.println("Digit 2: " +digit2);
		if(decimal)
			System.out.println("Multiplier: " +Math.pow(10,-(zeroafterpoint)));
		else
			System.out.println("Multiplier: " +Math.pow(10,length-2));
		System.out.println("Colors: " +digitColors[digit1] +" " +digitColors[digit2] +" " +digitColors[multiplier] +" " +digitColors[tol]);
	}
}