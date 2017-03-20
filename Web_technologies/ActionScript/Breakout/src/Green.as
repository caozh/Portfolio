package
{
	public class Green extends MovableGameObject
	{
		
		public var ball:Ball;
		
		public function Green()
		{
			super();
			//draw a green block
			this.graphics.beginFill(0x008000);
			this.graphics.drawRect(0,0,60,20);
		}
	}
}