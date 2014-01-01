#include <stdarg.h>
#include <stddef.h>
#include <setjmp.h>
#include <cmocka.h>

#include "testdriver/call_price_driver.h"

static void global_precondition() {

}

static void global_postprocessing(){

}

/*!
->script: CallPrice
->isCallValid: true
isCallValid->destination: "National"
destination->foc.country: "Motherland"
foc.country->tariff: "Standard"
tariff->foc.callBeginTime: "0:00:00"
tariff->dayOfTheWeek: "Monday"
destination->foc.callDuration: "0:00:01"
<-testcase: isCallValid destination="National" foc.country="Motherland" tariff="Standard" foc.callBeginTime="0:00:00" dayOfTheWeek="Monday" foc.callDuration="0:00:01"

\par First Hit Coverage:
			goal = requirements, item=req123, coverage="National", bla
*/
static void test001_is_call_valid_destination_national_foc_country_motherland_tariff_standard_foc_call_begin_time00000_day_of_the_week_monday_foc_call_duration00001() {
	// Focus 1
		call_price_driver_calculate(/* call_begin_time*/ "0:00:00", 
			/* call_duration*/ "0:00:01", 
			/* cheap_call_active_for_call*/ FALSE, 
			/* country*/ "Motherland", 
			/* customer_id*/ 12345, 
			/* day_of_week*/ "Monday", 
			/* phone_number*/ "valid");
}
/*!
->script: CallPrice
->isCallValid: true
isCallValid->destination: "National"
destination->foc.country: "Motherland"
foc.country->tariff: "Standard"
tariff->foc.callBeginTime: "0:00:01"
tariff->dayOfTheWeek: "Tuesday"
destination->foc.callDuration: "0:00:59"
<-testcase: isCallValid destination="National" foc.country="Motherland" tariff="Standard" foc.callBeginTime="0:00:01" dayOfTheWeek="Tuesday" foc.callDuration="0:00:59"

\par Next Hit Coverage:
			goal = requirements, item=req123, coverage="National", bla
*/
static void test002_is_call_valid_destination_national_foc_country_motherland_tariff_standard_foc_call_begin_time00001_day_of_the_week_tuesday_foc_call_duration00059() {
	// Focus 1
		call_price_driver_calculate(/* call_begin_time*/ "0:00:01", 
			/* call_duration*/ "0:00:59", 
			/* cheap_call_active_for_call*/ FALSE, 
			/* country*/ "Motherland", 
			/* customer_id*/ 12345, 
			/* day_of_week*/ "Tuesday", 
			/* phone_number*/ "valid");
}
/*!
description

\par First Hit Coverage:
			goal = requirements2, item=req123, coverage="National"

\par Next Hit Coverage:
			goal = requirements1, item=req123, coverage="National", bla
			goal = requirements2, item=req123, coverage=bla
*/
static void test003_multiple_goals() {
	// Focus 1
		call_price_driver_focus();
}


int main(void) {
    int rc;
    const UnitTest tests[] = {
		unit_test(test001_is_call_valid_destination_national_foc_country_motherland_tariff_standard_foc_call_begin_time00000_day_of_the_week_monday_foc_call_duration00001),
		unit_test(test002_is_call_valid_destination_national_foc_country_motherland_tariff_standard_foc_call_begin_time00001_day_of_the_week_tuesday_foc_call_duration00059),
		unit_test(test003_multiple_goals),
	};
	global_precondition();
    rc = run_tests(tests);
    global_postprocessing();
    return rc;
}
