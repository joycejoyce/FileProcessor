import {Util} from "../scripts/src/util.js";

const expect = require("chai").expect;

describe(`getDayByDate()`, function() {
    it(`get day by year and month`, function() {
        const day = Util.getDayByDate(2020, 4, 6);
        expect(day).to.eql(1);
    })
})