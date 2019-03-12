public class Flag{


    private HandlingMethod method;
    private double value;

    public enum HandlingMethod{
        DESTROY{
            @Override
            public String getMethod(){
                return "DESTROY";
            }
        },
        ADD_SPEED{
            @Override
            public String getMethod(){
                return "ADD_SPEED";
            }
        },
        SET_SPEED{
            @Override
            public String getMethod(){
                return "SET_SPEED";
            }
        },
        ADD_DIRECTION{
            @Override
            public String getMethod(){
                return "ADD_DIRECTION";
            }
        },
        SET_DIRECTION{
            @Override
            public String getMethod(){
                return "SET_DIRECTION";
            }
        };


        public abstract String getMethod();
    }

    public Flag(double value, HandlingMethod method){
        this(method);
        this.value = value;


    }

    public Flag(HandlingMethod method){
        setHandlingMethod(method);
    }

    public double getValue(){
        return value;
    }

    public String getHandlingMethod(){
        return method.getMethod();
    }

    public void setHandlingMethod(HandlingMethod method){
        this.method = method;
    }

}
