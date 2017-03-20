window.onload = function() {
	var theCanvas = document.getElementById('Canvas1');
	if (theCanvas && theCanvas.getContext) {
		var ctx = theCanvas.getContext("2d");
		if (ctx) {
			var theString = "2012, Zhihao's Get the Draw assignment";				
			ctx.font = "12pt Georgia"
			ctx.fillText(theString, 240,20);				
		}
	}
	
	///////////////////////////////////////////////////////////			
		var b=document.getElementById("banner");
		var ctx1=b.getContext("2d");
		var img0=new Image();
		img0.onload = function(){
		ctx1.drawImage(img0,0,0);
		};
		img0.src="banner.png";
	//////////////////////////////////////////////////////////
		var c=document.getElementById("myCanvas");
		var ctx2=c.getContext("2d");
		var img=new Image();
		img.onload = function(){
		ctx2.drawImage(img,0,0);
		};
		img.src="STAR_WAR.png";

}