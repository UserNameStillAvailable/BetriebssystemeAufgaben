public class Ball
{
    public static final int GRAVITATION = 10;
    private float y = 0.0f;
    private float v = 0.0f;

    public  Ball(float y, float v0){
        this.y = y;
        this.v = v0;
    }

    public void update (float deltaTime){
        this.y += deltaTime * v;
        this.v -= deltaTime * GRAVITATION;
    }

    public String toString(){
        return "Y = " + String.format(".%2f", y) + " v = " + String.format(".%2f", v);
    }

    public float gety(){
        return y;
    }


}
