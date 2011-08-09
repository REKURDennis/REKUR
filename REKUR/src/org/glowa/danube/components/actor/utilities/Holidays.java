package org.glowa.danube.components.actor.utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
	public static final int BAYERN = 9;
	public static final int SCHLESWIGHOLSTEIN = 1;
	
	public static final int WINTERFERIEN = 1;
	public static final int OSTERFERIEN = 2;
	public static final int PFINGSTEN = 3;
	public static final int SOMMERFERIEN = 4;
	public static final int HERBSTFERIEN = 5;
	public static final int WEIHNACHTSFERIEN = 6;
	private int startyear = 2011;
	private int summerSHDelay = 5;
	
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
	private int[] getSommerferien(int state, int year){
		if(state == BAYERN){
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
		if(state == SCHLESWIGHOLSTEIN){
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
	private GregorianCalendar getKarneval(int year){
		GregorianCalendar c = findEaster(year);
		int n = c.get(Calendar.DAY_OF_YEAR);
		c.setMinimalDaysInFirstWeek(4);
		c.setFirstDayOfWeek(1);
		c.set(Calendar.DAY_OF_YEAR, n - 48);
		return c;
	}
	private GregorianCalendar getPfingsten(int year){
		GregorianCalendar c = findEaster(year);
		int n = c.get(Calendar.DAY_OF_YEAR);
		c.setMinimalDaysInFirstWeek(4);
		c.setFirstDayOfWeek(1);
		c.set(Calendar.DAY_OF_YEAR, n + 50);
		return c;
	}
}
