var fruit = String (prompt("What's your favorite fruit?"));
switch(fruit.toUpperCase()){
	case ("APPLE"):
		alert("I don't like Apple!");
		break;
    case ("BANANA"):
        alert("I love Banana!");
        break;
    case ("ORANGE"):
        alert("Orange is my favorite!");
        break;
    case ("GRAPE"):
        alert("If you really like it, I like it.");
        break;
    default:
        alert("I've never tried this before!")
        break;
}
