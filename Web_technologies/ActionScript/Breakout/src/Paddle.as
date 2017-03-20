package
{
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	
	public class Paddle extends MovableGameObject
	{
		//Decleration
		private var velocityX:Number;
		private var velocityY:Number;
		
		public function Paddle()
		{
			super();
			
			//default paddle speed
			velocityX = 0;
			velocityY = 0;
			
			//draw a paddle
			this.graphics.beginFill(0x000000);
			this.graphics.drawRect(0,0,100,10);
			
			//add listener to the paddle
			this.addEventListener(Event.ADDED_TO_STAGE,init);
			this.addEventListener(Event.ENTER_FRAME,onEnterFrame);
		}
		
		private function onEnterFrame(e:Event): void {
			this.x += velocityX;
			this.y += velocityY;
			//simulate friction
			velocityX *= .8;
			velocityY *= .8;
		}
		
		//add control listener
		private function init(e:Event):void {
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
		}
		
		public function onKeyDown(e:KeyboardEvent): void {
			trace(e.keyCode);
			if(e.keyCode ==37) {
				velocityX = -15;
			}
			if(e.keyCode ==39) {
				velocityX = 15;
			}
			this.x += velocityX;
			this.x += velocityY;
			//37 - Left
			//39 - Right;
		}
	}
}