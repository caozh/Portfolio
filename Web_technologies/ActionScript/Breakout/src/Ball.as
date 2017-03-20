package
{
	import flash.events.Event;
	
	public class Ball extends MovableGameObject
	{
		//Decleration
		public var paddle:Paddle;
		public var bounciness:Number;
		public var velocity:Object;
		
		public function Ball(targBounciness:Number)
		{
			super();
			//draw a ball
			this.graphics.beginFill(0x6F4E37);
			this.graphics.drawCircle(0,0,10);
			
			this.addEventListener(Event.ENTER_FRAME, onE);
			
			//set our bounciness
			bounciness = targBounciness;
			
			//setup velocity
			velocity = {};
			velocity.x = 6;
			velocity.y = 6;
		}
		
		//move the ball & bounce when hitting border
		private function onE (e:Event):void {
			
			this.x += this.velocity.x;
			this.y += this.velocity.y;
			
			//bouncer
			if(this.y < 0) {
				this.y = 0;
				this.velocity.y *= -1;
			}
			
			if(this.x < 10) {
				this.x = 10;
				this.velocity.x *= -1;
			}
			
			if(this.x > stage.stageWidth-10) {
				this.x = stage.stageWidth-10;
				this.velocity.x *= -1;
			}
			
			if(this.y > stage.stageHeight) {
				this.y = 100;
				this.x = 15;
			}
		}
	}
}