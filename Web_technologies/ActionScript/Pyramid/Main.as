package
{
	import flash.display.Sprite;
	
	public class Main extends Sprite
	{
		
		public function Main()
		{
			for (var h:Number = 5; h >= 0 ; h--) {
				var width:Number = 5-h;
				for (var w:Number = 0; w <= 2*h; w++) {
					var br:Brick = new Brick();
					br.x = (w + width) * (br.width + 1);
					br.y = h * (br.height + 1);
					this.addChild(br);
				}//eng w	
			}//end h
		}
	}
}
