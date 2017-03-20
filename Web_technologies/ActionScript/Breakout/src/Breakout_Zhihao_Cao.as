package
{
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.ui.Mouse;
	import flash.text.TextField;
	
	[SWF(width="540", height="400",backgroundColor="0xF0F8FF")]
	
	public class Breakout_Zhihao_Cao extends Sprite
	{
		//Decleration
		private var arrR:Array;
		private var arrB:Array;
		private var arrG:Array;
		private var arrDead:Array = new Array();
		private var ball:Ball;
		private var p:Paddle;
		private var sum:int = 6;
		private var myText:TextField;
		
		public function Breakout_Zhihao_Cao()
		{
			super();
			Mouse.hide();
			arrDead = [];
			myText = new TextField()
			//hitTest listener
			addEventListener(Event.ENTER_FRAME, hitTests);
			addEventListener(Event.ENTER_FRAME, checkBlocks);
			//Create Ball
			ball = new Ball(1);
			ball.x = 15;
			ball.y = 200;
			this.addChild(ball);
			
			//Create Paddle
			p = new Paddle();
			p.y = stage.stageHeight - 15;
			this.addChild(p);
			
			//Create blocks
			//Red blocks
			arrR = [];
			for (var i:Number = 0; i <= sum ; i++) {
				var brR:Red = new Red();
				brR.y = 5;
				brR.x = i*(brR.width + 15) + 15;
				this.addChild(brR);
				arrR.push(brR);
			}//end i
			//Blue blocks
			arrB = [];
			for (var j:Number = 0; j <= sum ; j++) {
				var brB:Blue = new Blue();
				brB.y = 15 + brB.height;
				brB.x = j*(brB.width + 15) + 15;
				this.addChild(brB);
				arrB.push(brB);
			}//end j
			//Green blocks
			arrG = [];
			for (var k:Number = 0; k <= sum ; k++) {
				var brG:Green = new Green();
				brG.y = 25 + 2 * (brG.height);
				brG.x = k*(brG.width + 15) + 15;
				this.addChild(brG);
				arrG.push(brG);
			}//end i
		}//End function Breakout
		
		
		//hitTest function
		private function hitTests(event:Event):void
		{
			//Paddle hitTest
			if(ball.hitTestObject(p))
			{
				ball.velocity.y *= -1;
				/*if (ball.x + 10 >= p.leftEdge && ball.y > p.topEdge) {
					ball.velocity.x *= -1;
					var brG2:Green = new Green();
					addChild(brG2);
				}
				if (ball.x == p.rightEdge ) {
					ball.velocity.x *= -1;
					var brB2:Blue = new Blue();
					addChild(brB2);
				}
				if (ball.y + 10 >= p.topEdge && ball.x > p.leftEdge) {
					ball.velocity.y *= -1;
				}*/
				
			}
			
			//Block HitTest
			//Red
			for (var i:int = 0; i < arrR.length; i++)
			{
				var red:Red = arrR[i];
				if(ball.hitTestObject(red))
				{
					ball.velocity.y *= -1;
					red.HP--;
					if (red.HP == 0) {
						removeChild(red);
						arrR.splice(i, 1);
						arrDead.push(1);
					}
				}
			}//End i loop
			
			//Blue
			for (var j:int = 0; j < arrB.length; j++)
			{
				var blue:Blue = arrB[j];
				if(ball.hitTestObject(blue))
				{
					ball.velocity.y *= -1;
					blue.HP--;
					if (blue.HP == 0) {
						removeChild(blue);
						arrB.splice(j, 1);
						arrDead.push(1);
					}
				}
			}//End j loop
			
			//Green
			for (var k:int = 0; k < arrG.length; k++)
			{
				var green:Green = arrG[k];
				if(ball.hitTestObject(green))
				{
					ball.velocity.y *= -1;
					removeChild(green);
					arrG.splice(k, 1);
					arrDead.push(1);
				}
			}//End for loop
		}//End hitTests function
		
		private function checkBlocks (e:Event):void {
			if (arrDead.length == 21) {
				
				myText.y = stage.stageHeight/2;
				myText.x = stage.stageWidth/2 - 20;
				myText.text = "You Win";
				this.addChild(myText);
			}
		}
	}
}