package
{
	public class Red extends MovableGameObject
	{
		public var HP:Number;
		public var ball:Ball;
		
		public function Red()
		{
			super();
			//draw a red block
			this.graphics.beginFill(0xFF0000);
			this.graphics.drawRect(0,0,60,20);
			this.HP = 3;
		}
	}
}