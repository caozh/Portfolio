package
{
	
	import flash.display.Sprite;
	import flash.events.Event;
	
	public class Ball extends Sprite
	{
		
		protected var velocity:Number = 5;
		private var hp:Number = 2;
		
		public function Ball()
		{
			this.graphics.beginFill(0x000000);
			this.graphics.drawCircle(0,0,40);
			
			this.addEventListener(Event.ENTER_FRAME, onE);
		}//end Ball
		
		protected function onE(e:Event):void {
			this.x += velocity;
			this.y += velocity;
			
			if (this.y > stage.stageHeight) {
				this.y = stage.stageHeight;
				velocity *= -1;
				this.hp --;
			}
			if (this.y < 0) {
				this.y = 0;
				velocity *= -1;
				this.hp --;
			}
			if (this.x < 0) {
				this.x = 0;
				velocity *= -1;
				this.hp --;
			}
			if (this.x > stage.stageWidth) {
				this.x = stage.stageWidth;
				velocity *= -1;
				this.hp --;
			}
			
			if (hp <= 0) {
				this.parent.removeChild(this);
				this.removeEventListener(Event.ENTER_FRAME, onE);
			}
		}//end onE
	}//End class Ball
}