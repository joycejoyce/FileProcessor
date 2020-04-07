import {MonthlyCalendar} from "./monthly-calendar.js";
function Calendar() {
    this.getCalendar = function(dateObj, type) {
        switch(type) {
            case CALENDAR_TYPE.monthly:
                return new MonthlyCalendar(dateObj);
                break;
            default:
                throw `Invalid calendar type: [${type}]`;
                break;
        }
    };
}

const CALENDAR_TYPE = {
    monthly: "monthly",
    yearly: "yearly"
};

export {
    Calendar,
    CALENDAR_TYPE
};