/*const chai = require("chai");
const expect = chai.expect;
const assert = chai.assert;*/
const expect = require("chai").expect;

function checkDOMProperties(dom, propertiesToCheck) {
    let keys = Object.keys(propertiesToCheck);
    let values = Object.values(propertiesToCheck);
    keys.forEach((item, index) => {
        expect(dom[item]).to.eql(values[index]);
    });
}

function loadHTML() {
    const { JSDOM } = require("jsdom");
    const jsdom = new JSDOM("<!doctype html><html><body></body></html>");
    const { window } = jsdom;
    global.document = window.document;
    global.$ = require('jquery')(window);
};

module.exports = {
    checkDOMProperties,
    loadHTML
};