package org.glowa.danube.components.actor.utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * This class provides the holidays for Bavaria an Schleswig Holstein. 
 * @author Dennis Joswig
 *
 */

/*
 * Bayern:
 * 1 Winterferien: immer Karneval 1Woche
 * 2 Osterferien: Ostern 2 Wochen
 * 3 Pfingsten: 2 Woche (6 Wochen nach Ostern)
 * 4 Sommer: Fix August+2Wochen im September
 * 5 Herbstferien: 1Woche
 * 6 Weihnachtsferien: letzte Woche im Dezember+1. januarwoche
 * 
 * 
 * S-H:
 * 1. Winterferien: keine
 * 2. Osterferien: 3 Wochen
 * 3. Pfingsten: 1 Tag
 * 4. Sommerferien: Variiert   6 Wochen beginn im Juni bis Juli  letzte Juli-Woche minus random 0-5
 * 5. Herbstferien: 2 Wochen Beginn Anfang bis Mitte Oktober
 * 6. Wheinachten: wie Bayern
 * 
 * 
 */

public class Holidays {
	/**
	 * Specifies the holiday scenario.
	 */
	public static int scenario = 0;
	/**
	 * Static variable for Bayern.
	 */
	public static final int BAYERN = 9;
	/**
	 * Static parameter for Schleswig-holstein.
	 */
	public static final int SCHLESWIGHOLSTEIN = 1;
	
	/**
	 * Static parameter to get the Winterferien.
	 */
	public static final int WINTERFERIEN = 1;
	/**
	 * Static parameter to get the Osterferien.
	 */
	public static final int OSTERFERIEN = 2;
	/**
	 * Static parameter to get the Pfingstferien.
	 */
	public static final int PFINGSTEN = 3;
	/**
	 * Static parameter to get the Sommerferien.
	 */
	public static final int SOMMERFERIEN = 4;
	/**
	 * Static parameter to get the Herbstferien.
	 */
	public static final int HERBSTFERIEN = 5;
	/**
	 * Static parameter to get the Weihnachtsferien.
	 */
	public static final int WEIHNACHTSFERIEN = 6;
	/**
	 * Startyear for the first Schleswig-Holstein sommerferien.
	 */
	private int startyear = 2000;
	/**
	 * Sommerferien timeperiod in Schleswig Holstein.
	 */
	private int summerSHDelay = 5;
	
	/**
	 * THis Method returns an array with the holidayweeks of the holidaytype and state according to the given year.
	 * @param type holidaytype, use the static integers to chose type.
	 * @param state the state, use the static integer to chose the state.
	 * @param year Year of the holidays.
	 * @return an array with the holidayweeks of the holidaytype and state according to the given year.
	 */
	public int[] getHolidays(int type, int state, int year){
		if(type == WINTERFERIEN){
			return getWinterferien(state, year);
		}
		if(type == OSTERFERIEN){
			return getOsterferien(state, year);
		}
		if(type == PFINGSTEN){
			return getPfingstferien(state, year);
		}
		if(type == SOMMERFERIEN){
			return getSommerferien(state, year);
		}
		if(type == HERBSTFERIEN){
			return getHerbstferien(state, year);
		}
		if(type == WEIHNACHTSFERIEN){
			return getWeihnachtsferien(state, year);
		}
		return null;
	}
	
	
	/**
	 * This method provides the weeks for the winterferien of an state and year.
	 * @param state state of provided holidays
	 * @param year year of holidays
	 * @return an array with the holidayweeks of the state according to the given year.
	 */
	private int[] getWinterferien(int state, int year){
		if(state == BAYERN){
			int[] wf = new int[1];
			GregorianCalendar karneval = getKarneval(year);
			wf[0] = karneval.get(GregorianCalendar.WEEK_OF_YEAR);
			return wf;
		}
		if(state == SCHLESWIGHOLSTEIN){
			return null;
		}
		return null;
	}
	/**
	 * This method provides the weeks for the osterferien of an state and year.
	 * @param state state of provided holidays
	 * @param year year of holidays
	 * @return an array with the holidayweeks of the state according to the given year.
	 */
	private int[] getOsterferien(int state, int year){
		if(state == BAYERN){
			int[] of = new int[2];
			GregorianCalendar easter = findEaster(year);
			easter.setMinimalDaysInFirstWeek(4);
			easter.setFirstDayOfWeek(1);
			of[1] = easter.get(GregorianCalendar.WEEK_OF_YEAR);
			of[0] = of[1]-1;
			return of;
		}
		if(state == SCHLESWIGHOLSTEIN){
			int[] of = new int[3];
			GregorianCalendar easter = findEaster(year);
			easter.setMinimalDaysInFirstWeek(4);
			easter.setFirstDayOfWeek(1);
			of[1] = easter.get(GregorianCalendar.WEEK_OF_YEAR);
			of[0] = of[1]-1;
			of[2] = of[1]-1;
			return of;
		}
		return null;
	}
	/**
	 * This method provides the weeks for the pfingstferien of an state and year.
	 * @param state state of provided holidays
	 * @param year year of holidays
	 * @return an array with the holidayweeks of the state according to the given year.
	 */
	private int[] getPfingstferien(int state, int year){
		if(state == BAYERN){
			int[] pf = new int[2];
			GregorianCalendar pfingsten = getPfingsten(year);
			pf[0] = pfingsten.get(GregorianCalendar.WEEK_OF_YEAR);
			pf[1] = pf[0]+1;
			return pf;
		}
		if(state == SCHLESWIGHOLSTEIN){
			return null;
		}
		return null;
	}
	/**
	 * This method provides the weeks for the sommerferien of an state and year.
	 * @param state state of provided holidays
	 * @param year year of holidays
	 * @return an array with the holidayweeks of the state according to the given year.
	 */
	private int[] getSommerferien(int state, int year){
		if((state == BAYERN  && scenario == 0 || scenario == 1) || (state == SCHLESWIGHOLSTEIN && scenario == 1)){
			int[] sf = new int[6];
			GregorianCalendar augustend = new GregorianCalendar(year, 7, 2);
			augustend.setMinimalDaysInFirstWeek(4);
			augustend.setFirstDayOfWeek(1);
			sf[0] = augustend.get(GregorianCalendar.WEEK_OF_YEAR);
			for(int i = 1; i < sf.length;i++){
				sf[i] = sf[0]+i;
			}
			return sf;
		}
		
		if(state == BAYERN && scenario == 2){
			int[] sf = new int[6];
			GregorianCalendar augustend = new GregorianCalendar(year, 6, 2);
			augustend.setMinimalDaysInFirstWeek(4);
			augustend.setFirstDayOfWeek(1);
			sf[0] = augustend.get(GregorianCalendar.WEEK_OF_YEAR);
			for(int i = 1; i < sf.length;i++){
				sf[i] = sf[0]+i;
			}
			return sf;
		}
		
		if(state == SCHLESWIGHOLSTEIN && scenario == 0|| scenario == 2){
			int[] sf = new int[6];
			GregorianCalendar augustend = new GregorianCalendar(year, 7, 4);
			augustend.setMinimalDaysInFirstWeek(4);
			augustend.setFirstDayOfWeek(1);
			sf[0] = augustend.get(GregorianCalendar.WEEK_OF_YEAR)-(summerSHDelay+1)+((year%startyear)%summerSHDelay);
			for(int i = 1; i < sf.length;i++){
				sf[i] = sf[0]+i;
			}
			return sf;
		}
		return null;
	}
	
	
	/**
	 * This method provides the weeks for the herbstferien of an state and year.
	 * @param state state of provided holidays
	 * @param year year of holidays
	 * @return an array with the holidayweeks of the state according to the given year.
	 */
	private int[] getHerbstferien(int state, int year){
		if(state == BAYERN){
			int[] hf = new int[1];
			GregorianCalendar november = new GregorianCalendar(year, 10, 1);
			november.setMinimalDaysInFirstWeek(4);
			november.setFirstDayOfWeek(1);
			hf[0] = november.get(GregorianCalendar.WEEK_OF_YEAR);
			return hf;
		}
		if(state == SCHLESWIGHOLSTEIN){
			int[] hf = new int[1];
			GregorianCalendar october = new GregorianCalendar(year, 9, 15);
			october.setMinimalDaysInFirstWeek(4);
			october.setFirstDayOfWeek(1);
			hf[0] = october.get(GregorianCalendar.WEEK_OF_YEAR);
			return hf;
		}
		return null;
	}
	/**
	 * This method provides the weeks for the weihnachtsferien of an state and year.
	 * @param state state of provided holidays
	 * @param year year of holidays
	 * @return an array with the holidayweeks of the state according to the given year.
	 */
	private int[] getWeihnachtsferien(int state, int year){
		if(state == BAYERN || state == SCHLESWIGHOLSTEIN){
			int[] wf = new int[2];
			GregorianCalendar christmas = new GregorianCalendar(year, 11, 27);
			christmas.setMinimalDaysInFirstWeek(4);
			christmas.setFirstDayOfWeek(1);
			wf[0] = christmas.get(GregorianCalendar.WEEK_OF_YEAR);
			GregorianCalendar newyear = new GregorianCalendar(year+1, 0, 3);
			newyear.setMinimalDaysInFirstWeek(4);
			newyear.setFirstDayOfWeek(1);
			wf[1] = newyear.get(GregorianCalendar.WEEK_OF_YEAR);
			return wf;
		}
		return null;
	}
	
	/**
	 * This methods returns the date of easter of the year.
	 * @param year year of request.
	 * @return an object with the easter date.
	 */
	private GregorianCalendar findEaster(int year) {
	    if (year <= 1582) {
	      throw new IllegalArgumentException(
	          "Algorithm invalid before April 1583");
	    }
	    int golden, century, x, z, d, epact, n;

	    golden = (year % 19) + 1; /* E1: metonic cycle */
	    century = (year / 100) + 1; /* E2: e.g. 1984 was in 20th C */
	    x = (3 * century / 4) - 12; /* E3: leap year correction */
	    z = ((8 * century + 5) / 25) - 5; /* E3: sync with moon's orbit */
	    d = (5 * year / 4) - x - 10;
	    epact = (11 * golden + 20 + z - x) % 30; /* E5: epact */
	    if ((epact == 25 && golden > 11) || epact == 24)
	      epact++;
	    n = 44 - epact;
	    n += 30 * (n < 21 ? 1 : 0); /* E6: */
	    n += 7 - ((d + n) % 7);
	    if (n > 31) /* E7: */
	      return new GregorianCalendar(year, 4 - 1, n - 31); /* April */
	    else
	      return new GregorianCalendar(year, 3 - 1, n); /* March */
	}
	/**
	 * This methods returns the date of karneval of the year.
	 * @param year year of request.
	 * @return an object with the karneval date.
	 */
	private GregorianCalendar getKarneval(int year){
		GregorianCalendar c = findEaster(year);
		int n = c.get(Calendar.DAY_OF_YEAR);
		c.setMinimalDaysInFirstWeek(4);
		c.setFirstDayOfWeek(1);
		c.set(Calendar.DAY_OF_YEAR, n - 48);
		return c;
	}
	/**
	 * This methods returns the date of pfingsten of the year.
	 * @param year year of request.
	 * @return an object with the pfingst date.
	 */
	private GregorianCalendar getPfingsten(int year){
		GregorianCalendar c = findEaster(year);
		int n = c.get(Calendar.DAY_OF_YEAR);
		c.setMinimalDaysInFirstWeek(4);
		c.setFirstDayOfWeek(1);
		c.set(Calendar.DAY_OF_YEAR, n + 50);
		return c;
	}
}
