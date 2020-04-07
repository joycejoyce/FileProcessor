import {Calendar, CALENDAR_TYPE} from "../scripts/src/calendars/calendar.js";
import {MonthlyCalendar} from "../scripts/src/calendars/monthly-calendar.js";

const expect = require("chai").expect;

describe(`new Calendar()`, function() {
    it(`return a Calendar object`, function() {
        const calendar = new Calendar();
        expect(calendar instanceof Calendar).to.be.true;
    })
})

describe(`getCalendar({year, month}, CALENDAR_TYPE.monthly)`, function() {
    it(`return a MonthlyCalendar object`, function() {
        const dateObj = {
            year: 2020,
            month: 4,
            firstOf7Days: 0
        };
        const calendar = new Calendar().getCalendar(dateObj, CALENDAR_TYPE.monthly);
        expect(calendar instanceof MonthlyCalendar).to.be.true;
    })
})

describe(`(MonthlyCalendar)getDom()`, function() {
    it(`get the dom of the monthly calendar`, function() {
        const dateObj = {
            year: 2020,
            month: 4,
            firstOf7Days: 0
        };
        const calendar = new Calendar().getCalendar(dateObj, CALENDAR_TYPE.monthly);
        const dom = calendar.getDom();
    })
})