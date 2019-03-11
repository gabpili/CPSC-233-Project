public class Flag{


    private HandlingMethod method;
    private double value;

    public enum HandlingMethod{
        DESTROY, ADD_SPEED, SET_SPEED, ADD_DIRECTION, SET_DIRECTION;
    }

    public Flag(double value){
        this.value = value;

    }

    public Flag(){

    }

    public double getValue(){
        return value;
    }

    public HandlingMethod getHandlingMethod(){
        return method;
    }

}
