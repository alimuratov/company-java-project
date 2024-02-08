public class Day implements Cloneable, Comparable<Day> {
	
	private int year;
	private int month;
	private int day;

    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	private static final int[] numOfDays = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	// Constructors

	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}

	public Day(String sDay) { set(sDay); } 
	


	// Static methods
	
	// Check if a given year is a leap year
	static public boolean isLeapYear(int y) 
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// Check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}



	// Utility methods

	//Set year,month,day based on a string like 01-Jan-2022
    public void set(String sDay) //Set year,month,day based on a string like 01-Jan-2022
    {		
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
        this.year = Integer.parseInt(sDayParts[2]); 
        this.month = MonthNames.indexOf( sDayParts[1] )/ 3 + 1;
    }

	// check if the Day object represents the last day of its respective month
    public boolean isEndOfAMonth() 
	{
		if (valid(year,month,day+1)) //A smart methd: check whether (year month [day+1]) is still a valid date
			return false;
		else
			return true;
	}

	// check if the Day object represents the first day of its respective month
	public boolean isBeginningOfAMonth() {
        if(valid(year,month,day-1)) return false;
        else return true;
    }



	// Arithmetic day calculation methods

	// When called on a Day object, the function returns a new Day object representing the next day
    public Day next() 
	{
		if (isEndOfAMonth())
			if (month==12)
				return new Day(year+1,1,1); //Since the current day is the end of the year, the next day should be Jan 01
			else
				return new Day(year, month + 1, 1); 
		else
			return new Day(year, month, day + 1);
	}

	// When called on a Day object, the function returns a new Day object representing the previous day
	public Day prev() {
        if (isBeginningOfAMonth()) {
            if(month == 1) {
                return new Day(year - 1, 12, 31);
            } else if (month == 3) {
                if (isLeapYear(year)) {
                    return new Day(year, 2, 29);
                } else {
                    return new Day(year, 2, 28 );
                }
            } else {
                return new Day(year, month - 1, numOfDays[month - 2]);
            }
        } else {
                return new Day(year, month, day - 1);
        }
    }

	// Calculates the total number of days separating two given Day objects
	public static int daysBetween(Day day1, Day day2) {
		if (day1.compareTo(day2) > 0) {
			Day temp = day1;
			day1 = day2;
			day2 = temp;
		}
		int counter = 1;
		while(!day1.equals(day2)) {
			day1 = day1.next();
			counter += 1;
		}
		return counter;
	}

	// Returns a new Day object that represents a date a specified number of days ahead of the date of the original Day object
	public static Day calculateEndDay(Day startDay, int numberOfDays) {
        for (int i = 0 ; i < numberOfDays - 1; i++) {
            startDay = startDay.next();
        }
        return startDay;
    }



	// Overriden methods

	// Comparable interface method
	@Override
	public int compareTo(Day o) {
		int day1 = Integer.parseInt(String.format("%04d%02d%02d", year, month, day));
		int day2 = Integer.parseInt(String.format("%04d%02d%02d", o.year, o.month, o.day));
		if (day1 > day2) {
			return 1;
		} else if (day1 == day2) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}	
		Day day2 = (Day) obj;
		return day == day2.day && month == day2.month && year == day2.year;
	}

	@Override
    public String toString() {		
        return day+"-"+ MonthNames.substring( (month-1) * 3 , (month) * 3 ) + "-"+ year; // (month-1)*3,(month)*3
    }

	// Cloneable interface method
    @Override
    public Day clone() {
        Day copy= null ;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy ;
    }
}