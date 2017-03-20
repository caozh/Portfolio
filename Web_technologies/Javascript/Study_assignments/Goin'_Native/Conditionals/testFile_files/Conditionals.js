var name = new String("");
name = prompt("What is your name?");
var result = new String();
result = prompt("Hello, " + name + ".  Did you make it to class on time today?");

if (result.toUpperCase() == "YES") {
	alert(name + ", you got extra credit today!");
} else {
	alert(name + ", I recommend you to drop the class, or you will fail.");
}