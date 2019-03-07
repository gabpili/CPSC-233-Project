public class Checkpoint extends Wall{
	  private int number;

		public Checkpoint (double x, double y, String name, double x2, double y2, int number){
				super(x, y, name, x2, y2);
				setNumber(number);
		}

		public Checkpoint(Checkpoint copy){
			  super(copy);
		}

		/* setters */
		public void setNumber(int number){
			this.number = number;
		}

		/*getters */
		 public int getNumber(){
				 return number;
		 }


@Override
		public void resolveCollision(DynamicObject dObj){
		}
}
