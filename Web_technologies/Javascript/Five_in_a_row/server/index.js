var myModule = require('./fiveInRow');
var app = new myModule.fiveInRow();
app.setConfig(8080);
app.serverStart();

