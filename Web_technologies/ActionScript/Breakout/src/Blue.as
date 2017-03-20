package
{
	public class Blue extends MovableGameObject
	{
		public var HP:Number;
		public var ball:Ball;
		
		public function Blue()
		{
			super();
			//draw a blue block
			this.graphics.beginFill(0x0000FF);
			this.graphics.drawRect(0,0,60,20);
			this.HP = 2;
		}
	}
}