import static org.junit.Assert.*;

import org.junit.Test;


public class CallPrice {
	CallPriceDriver driver = new CallPriceDriver();
	@Test
	public void test001_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime00000DayOfTheWeekThursdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test002_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime00001DayOfTheWeekFridayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test003_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime55959DayOfTheWeekMondayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test004_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime60000DayOfTheWeekTuesdayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test005_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime60001DayOfTheWeekWednesdayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test006_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime195959DayOfTheWeekThursdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test007_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime200000DayOfTheWeekFridayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test008_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime200001DayOfTheWeekMondayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test009_isCallValidDestinationNationalFocCountryMotherlandTariffStandardFocCallBeginTime235959DayOfTheWeekTuesdayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test010_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime00000FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test011_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime00001FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test012_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime55959FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test013_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime60000FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test014_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime60001FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test015_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime195959FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test016_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime200000FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test017_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime200001FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test018_isCallValidDestinationNationalFocCountryMotherlandTariffCheapCallFocCallBeginTime235959FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test019_isCallValidDestinationNationalFocCountryMotherlandTariffAtNightFocCallBeginTime200000FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test020_isCallValidDestinationNationalFocCountryMotherlandTariffAtNightFocCallBeginTime200001FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test021_isCallValidDestinationNationalFocCountryMotherlandTariffAtNightFocCallBeginTime235959FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test022_isCallValidDestinationNationalFocCountryMotherlandTariffAtNightFocCallBeginTime000000FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "00:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test023_isCallValidDestinationNationalFocCountryMotherlandTariffAtNightFocCallBeginTime055959FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "05:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test024_isCallValidDestinationNationalFocCountryMotherlandTariffOnTheWeekendFocCallBeginTime00000FocDayOfWeekSundayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test025_isCallValidDestinationNationalFocCountryMotherlandTariffOnTheWeekendFocCallBeginTime00001FocDayOfWeekSaturdayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test026_isCallValidDestinationNationalFocCountryMotherlandTariffOnTheWeekendFocCallBeginTime195959FocDayOfWeekSundayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Motherland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test027_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime00000DayOfTheWeekWednesdayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test028_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime00001DayOfTheWeekThursdayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test029_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime55959DayOfTheWeekFridayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test030_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime60000DayOfTheWeekMondayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test031_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime60001DayOfTheWeekTuesdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test032_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime195959DayOfTheWeekWednesdayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test033_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime200000DayOfTheWeekThursdayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test034_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime200001DayOfTheWeekFridayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test035_isCallValidDestinationNationalFocCountryMissingTariffStandardFocCallBeginTime235959DayOfTheWeekMondayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test036_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime00000FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test037_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime00001FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test038_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime55959FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test039_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime60000FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test040_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime60001FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test041_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime195959FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test042_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime200000FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test043_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime200001FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test044_isCallValidDestinationNationalFocCountryMissingTariffCheapCallFocCallBeginTime235959FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test045_isCallValidDestinationNationalFocCountryMissingTariffAtNightFocCallBeginTime200000FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test046_isCallValidDestinationNationalFocCountryMissingTariffAtNightFocCallBeginTime200001FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test047_isCallValidDestinationNationalFocCountryMissingTariffAtNightFocCallBeginTime235959FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test048_isCallValidDestinationNationalFocCountryMissingTariffAtNightFocCallBeginTime000000FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "00:00:00", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test049_isCallValidDestinationNationalFocCountryMissingTariffAtNightFocCallBeginTime055959FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "05:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test050_isCallValidDestinationNationalFocCountryMissingTariffOnTheWeekendFocCallBeginTime00000FocDayOfWeekSaturdayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test051_isCallValidDestinationNationalFocCountryMissingTariffOnTheWeekendFocCallBeginTime00001FocDayOfWeekSundayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test052_isCallValidDestinationNationalFocCountryMissingTariffOnTheWeekendFocCallBeginTime195959FocDayOfWeekSaturdayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test053_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime00000DayOfTheWeekTuesdayFocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test054_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime00001DayOfTheWeekWednesdayFocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test055_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime55959DayOfTheWeekThursdayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test056_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime60000DayOfTheWeekFridayFocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test057_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime60001DayOfTheWeekMondayFocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test058_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime195959DayOfTheWeekTuesdayFocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test059_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime200000DayOfTheWeekWednesdayFocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test060_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime200001DayOfTheWeekThursdayFocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test061_isCallValidDestinationInternational1FocCountryGreenlandTariffStandardFocCallBeginTime235959DayOfTheWeekFridayFocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test062_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime00000FocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test063_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime00001FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test064_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime55959FocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test065_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime60000FocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test066_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime60001FocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test067_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime195959FocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test068_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime200000FocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test069_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime200001FocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test070_isCallValidDestinationInternational1FocCountryGreenlandTariffCheapCallFocCallBeginTime235959FocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test071_isCallValidDestinationInternational1FocCountryGreenlandTariffAtNightFocCallBeginTime200000FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test072_isCallValidDestinationInternational1FocCountryGreenlandTariffAtNightFocCallBeginTime200001FocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test073_isCallValidDestinationInternational1FocCountryGreenlandTariffAtNightFocCallBeginTime235959FocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test074_isCallValidDestinationInternational1FocCountryGreenlandTariffAtNightFocCallBeginTime000000FocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "00:00:00", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test075_isCallValidDestinationInternational1FocCountryGreenlandTariffAtNightFocCallBeginTime055959FocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "05:59:59", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test076_isCallValidDestinationInternational1FocCountryGreenlandTariffOnTheWeekendFocCallBeginTime00000FocDayOfWeekSundayFocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test077_isCallValidDestinationInternational1FocCountryGreenlandTariffOnTheWeekendFocCallBeginTime00001FocDayOfWeekSaturdayFocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test078_isCallValidDestinationInternational1FocCountryGreenlandTariffOnTheWeekendFocCallBeginTime195959FocDayOfWeekSundayFocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test079_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime00000DayOfTheWeekMondayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test080_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime00001DayOfTheWeekTuesdayFocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test081_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime55959DayOfTheWeekWednesdayFocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test082_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime60000DayOfTheWeekThursdayFocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test083_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime60001DayOfTheWeekFridayFocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test084_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime195959DayOfTheWeekMondayFocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test085_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime200000DayOfTheWeekTuesdayFocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test086_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime200001DayOfTheWeekWednesdayFocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test087_isCallValidDestinationInternational1FocCountryBluelandTariffStandardFocCallBeginTime235959DayOfTheWeekThursdayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test088_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime00000FocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test089_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime00001FocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test090_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime55959FocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test091_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime60000FocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test092_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime60001FocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test093_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime195959FocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test094_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime200000FocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test095_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime200001FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test096_isCallValidDestinationInternational1FocCountryBluelandTariffCheapCallFocCallBeginTime235959FocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test097_isCallValidDestinationInternational1FocCountryBluelandTariffAtNightFocCallBeginTime200000FocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test098_isCallValidDestinationInternational1FocCountryBluelandTariffAtNightFocCallBeginTime200001FocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test099_isCallValidDestinationInternational1FocCountryBluelandTariffAtNightFocCallBeginTime235959FocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test100_isCallValidDestinationInternational1FocCountryBluelandTariffAtNightFocCallBeginTime000000FocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "00:00:00", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test101_isCallValidDestinationInternational1FocCountryBluelandTariffAtNightFocCallBeginTime055959FocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "05:59:59", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test102_isCallValidDestinationInternational1FocCountryBluelandTariffOnTheWeekendFocCallBeginTime00000FocDayOfWeekSaturdayFocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test103_isCallValidDestinationInternational1FocCountryBluelandTariffOnTheWeekendFocCallBeginTime00001FocDayOfWeekSundayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test104_isCallValidDestinationInternational1FocCountryBluelandTariffOnTheWeekendFocCallBeginTime195959FocDayOfWeekSaturdayFocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Blueland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test105_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime00000DayOfTheWeekFridayFocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test106_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime00001DayOfTheWeekMondayFocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test107_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime55959DayOfTheWeekTuesdayFocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test108_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime60000DayOfTheWeekWednesdayFocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test109_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime60001DayOfTheWeekThursdayFocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test110_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime195959DayOfTheWeekFridayFocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test111_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime200000DayOfTheWeekMondayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test112_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime200001DayOfTheWeekTuesdayFocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test113_isCallValidDestinationInternational1FocCountryNeverlandTariffStandardFocCallBeginTime235959DayOfTheWeekWednesdayFocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test114_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime00000FocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test115_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime00001FocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test116_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime55959FocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test117_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime60000FocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test118_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime60001FocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test119_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime195959FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test120_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime200000FocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test121_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime200001FocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test122_isCallValidDestinationInternational1FocCountryNeverlandTariffCheapCallFocCallBeginTime235959FocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test123_isCallValidDestinationInternational1FocCountryNeverlandTariffAtNightFocCallBeginTime200000FocCallDuration00029() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:29", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test124_isCallValidDestinationInternational1FocCountryNeverlandTariffAtNightFocCallBeginTime200001FocCallDuration00140() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:40", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test125_isCallValidDestinationInternational1FocCountryNeverlandTariffAtNightFocCallBeginTime235959FocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test126_isCallValidDestinationInternational1FocCountryNeverlandTariffAtNightFocCallBeginTime000000FocCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "00:00:00", 
			/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test127_isCallValidDestinationInternational1FocCountryNeverlandTariffAtNightFocCallBeginTime055959FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "05:59:59", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test128_isCallValidDestinationInternational1FocCountryNeverlandTariffOnTheWeekendFocCallBeginTime00000FocDayOfWeekSundayFocCallDuration00019() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:19", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test129_isCallValidDestinationInternational1FocCountryNeverlandTariffOnTheWeekendFocCallBeginTime00001FocDayOfWeekSaturdayFocCallDuration00020() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:20", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test130_isCallValidDestinationInternational1FocCountryNeverlandTariffOnTheWeekendFocCallBeginTime195959FocDayOfWeekSundayFocCallDuration00021() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:00:21", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Neverland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test131_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime00000DayOfTheWeekThursdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test132_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime00001DayOfTheWeekFridayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test133_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime55959DayOfTheWeekMondayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test134_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime60000DayOfTheWeekTuesdayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test135_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime60001DayOfTheWeekWednesdayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test136_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime195959DayOfTheWeekThursdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test137_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime200000DayOfTheWeekFridayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test138_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime200001DayOfTheWeekMondayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test139_isCallValidDestinationInternational2FocCountryYellowlandTariffStandardFocCallBeginTime235959DayOfTheWeekTuesdayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test140_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime00000FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test141_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime00001FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test142_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime55959FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test143_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime60000FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test144_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime60001FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test145_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime195959FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test146_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime200000FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test147_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime200001FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test148_isCallValidDestinationInternational2FocCountryYellowlandTariffCheapCallFocCallBeginTime235959FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test149_isCallValidDestinationInternational2FocCountryYellowlandTariffAtNightFocCallBeginTime200000FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test150_isCallValidDestinationInternational2FocCountryYellowlandTariffAtNightFocCallBeginTime200001FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test151_isCallValidDestinationInternational2FocCountryYellowlandTariffAtNightFocCallBeginTime235959FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test152_isCallValidDestinationInternational2FocCountryYellowlandTariffAtNightFocCallBeginTime000000FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "00:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test153_isCallValidDestinationInternational2FocCountryYellowlandTariffAtNightFocCallBeginTime055959FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "05:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test154_isCallValidDestinationInternational2FocCountryYellowlandTariffOnTheWeekendFocCallBeginTime00000FocDayOfWeekSaturdayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test155_isCallValidDestinationInternational2FocCountryYellowlandTariffOnTheWeekendFocCallBeginTime00001FocDayOfWeekSundayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test156_isCallValidDestinationInternational2FocCountryYellowlandTariffOnTheWeekendFocCallBeginTime195959FocDayOfWeekSaturdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Yellowland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test157_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime00000DayOfTheWeekWednesdayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test158_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime00001DayOfTheWeekThursdayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test159_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime55959DayOfTheWeekFridayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test160_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime60000DayOfTheWeekMondayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test161_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime60001DayOfTheWeekTuesdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test162_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime195959DayOfTheWeekWednesdayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test163_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime200000DayOfTheWeekThursdayFocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test164_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime200001DayOfTheWeekFridayFocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test165_isCallValidDestinationInternational2FocCountryRedlandTariffStandardFocCallBeginTime235959DayOfTheWeekMondayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test166_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime00000FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test167_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime00001FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test168_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime55959FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "5:59:59", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test169_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime60000FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:00", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test170_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime60001FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "6:00:01", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test171_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime195959FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test172_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime200000FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test173_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime200001FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test174_isCallValidDestinationInternational2FocCountryRedlandTariffCheapCallFocCallBeginTime235959FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ true, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test175_isCallValidDestinationInternational2FocCountryRedlandTariffAtNightFocCallBeginTime200000FocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test176_isCallValidDestinationInternational2FocCountryRedlandTariffAtNightFocCallBeginTime200001FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test177_isCallValidDestinationInternational2FocCountryRedlandTariffAtNightFocCallBeginTime235959FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "23:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test178_isCallValidDestinationInternational2FocCountryRedlandTariffAtNightFocCallBeginTime000000FocCallDuration240000() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "00:00:00", 
			/* callDuration*/ "24:00:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test179_isCallValidDestinationInternational2FocCountryRedlandTariffAtNightFocCallBeginTime055959FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "05:59:59", 
			/* callDuration*/ ""0:00:01"", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test180_isCallValidDestinationInternational2FocCountryRedlandTariffOnTheWeekendFocCallBeginTime00000FocDayOfWeekSundayFocCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test181_isCallValidDestinationInternational2FocCountryRedlandTariffOnTheWeekendFocCallBeginTime00001FocDayOfWeekSaturdayFocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:01", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test182_isCallValidDestinationInternational2FocCountryRedlandTariffOnTheWeekendFocCallBeginTime195959FocDayOfWeekSundayFocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "19:59:59", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test183_failureReasonWrongFocCountryCodeFocCallBeginTime200001FocCallDuration00001() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:00:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "invalid", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test184_failureReasonWrongPhoneNumberFocCallBeginTimeUNDEFINED_focCallDuration00059() {
		
	// Focus 1
		driver.calculate(/* callDuration*/ "0:00:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Redland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Wednesday", 
			/* phoneNumber*/ "invalid");
	}
		
	@Test
	public void test185_failureReasonWrongFocCountryCodeFocCallBeginTime00000FocCallDuration00100() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:00", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "invalid", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Thursday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test186_failureReasonWrongPhoneNumberFocCallBeginTime200001FocCallDuration00101() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:01", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "National", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Friday", 
			/* phoneNumber*/ "invalid");
	}
		
	@Test
	public void test187_failureReasonWrongFocCountryCodeFocCallBeginTimeUNDEFINED_focCallDuration00121() {
		
	// Focus 1
		driver.calculate(/* callDuration*/ "0:01:21", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "invalid", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Saturday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test188_failureReasonWrongPhoneNumberFocCallBeginTime00000FocCallDuration00131() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "0:00:00", 
			/* callDuration*/ "0:01:31", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "missing", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Sunday", 
			/* phoneNumber*/ "invalid");
	}
		
	@Test
	public void test189_failureReasonWrongFocCountryCodeFocCallBeginTime200001FocCallDuration00141() {
		
	// Focus 1
		driver.calculate(/* callBeginTime*/ "20:00:01", 
			/* callDuration*/ "0:01:41", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "invalid", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Monday", 
			/* phoneNumber*/ "valid");
	}
		
	@Test
	public void test190_failureReasonWrongPhoneNumberFocCallBeginTimeUNDEFINED_focCallDuration235959() {
		
	// Focus 1
		driver.calculate(/* callDuration*/ "23:59:59", 
			/* cheapCallActiveForCall*/ false, 
			/* country*/ "Greenland", 
			/* customerID*/ 12345, 
			/* dayOfWeek*/ "Tuesday", 
			/* phoneNumber*/ "invalid");
	}
		
}
