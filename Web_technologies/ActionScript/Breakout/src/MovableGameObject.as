package
{
	import flash.display.Sprite;
	
	public class MovableGameObject extends Sprite
	{
		
		// ========== GETTERS ==========
		
		public function get leftEdge():Number
		{
			return this.x;
		}
		
		public function get rightEdge():Number
		{
			return this.x + this.width;
		}
		
		public function get topEdge():Number
		{
			return this.y;
		}
		
		public function get bottomEdge():Number
		{
			return this.y + this.height;
		}
		
		
		// ========== SETTERS ==========
		
		public function set leftEdge(pos:Number):void
		{
			this.x = pos;
		}
		
		public function set rightEdge(pos:Number):void
		{
			this.x = pos - this.width;
		}
		
		public function set topEdge(pos:Number):void
		{
			this.y = pos;
		}
		
		public function set bottomEdge(pos:Number):void
		{
			this.y = pos - this.height;
		}		
		
		
		
		
		

		
		public function MovableGameObject()
		{
			
		}
		
	}
}