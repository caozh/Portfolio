//project2Lib.js
//Objects for shoooting game


function Background() {
	//Create Background
	tBackground = new Sprite(game, "Scene2.jpg", 999, 600);
	tBackground.setSpeed(0,0);
    tBackground.setPosition(350, 300);
	return tBackground;
}

function Character() {
	//Create Character
	tCharacter = new Sprite(game, "cs.png", 50, 23.5);
	tCharacter.setPosition(350, 300);
    tCharacter.setSpeed(0);
	return tCharacter
}

function GunSound() {
	tGunSound = new Sound("Sound/m4.wav");
	tGunSound.stop = function() 
	{ 
		this.snd.pause(); 
		this.snd.currentTime = 0.0; 
	} 	
}