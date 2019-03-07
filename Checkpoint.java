public class Checkpoint extends Wall{
	  private int number;

		public Checkpoint(int number, double x2, double y2, String name, double halfW, double halfH){
				super(x2, y2, name, halfW, halfH);
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
		public void resolveCollision(DynamicObject){
		}
}
