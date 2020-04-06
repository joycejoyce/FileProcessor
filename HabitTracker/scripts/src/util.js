function Util() {}

Util.getDayByDate = function(year, month, day) {
    const date = new Date(year, month-1, day);
    return date.getDay();
};

export {
    Util
};