/**
 * Written by Emre KAVAK https://github.com/emrekavak
 */
package epidemic.simulation;
import java.util.Random;

/**
 * This class refer to individual. Each individual creat via this class and add the society
 * This also also a leaf for Society class
 */
public class Individual extends IndividualComponent {
    /**
     * individual x position coordinate
     */
    private int X;
    /**
     * individual y position coordinate
     */
    private int Y;
    /**
     * individual speed info
     */
    private int S;
    /**
     * individual how to social info
     */
    private int C;
    /**
     * individual social distance info
     */
    private int D;
    /**
     * mask info
     */
    private double M;
    /**
     * in canvas, direction info 1 = x, -1 = -x, 2 = y, -2 = -y, 3 = x+y, -3 = -x-y, 4 = -x+y, -4 = x-y
     */
    private int direction;
    /**
     * keep directions
     */
    private int[] directionArr={1,-1,2,-2,3,-3,4,-4}; // 1 = x, -1 = -x, 2 = y, -2 = -y, 3 = x+y, -3 = -x-y, 4 = -x+y, -4 = x-y

    /**
     * infected info
     */
    private boolean infected;
    /**
     * mediator class object for register and call checkMe function
     */
    private MediatorInterface mediator;
    /**
     * if collide, move will false
     */
    private boolean move=true;
    /**
     * if collide, after 25 sec, it will be set true
     */
    private boolean moveTempList = false;
    /**
     * keep in 25 sec timer or not
     */
    private boolean in25Sec = false;
    /**
     * hold info abouth ind. at hospital or not
     */
    private boolean atHospital=false;
    /**
     * dead info
     */
    private boolean dead = false;

    /**
     * if ind. in 25 sec timer
     * @return if ind. in 25 sec timer true, other wise false
     */

    /***
     * take info about ind
     * @param x coordinate
     * @param y coordinate
     * @param m mask info
     * @param s speed info
     * @param d distance info
     * @param c how to social info
     * @param direction direction info
     * @param infected infection info
     * @param mediator mediator object
     */
    public Individual(int x, int y, double m, int s, int d,int c, int direction, boolean infected, MediatorInterface mediator) {
        X = x;
        Y = y;
        S = s;
        C = c;
        M = m;
        D=d;
        this.mediator = mediator;
        this.mediator.addIndividual(this);
        this.direction = direction;
        this.infected = infected;
    }
    /**
     * x coordinate in canvas
     * @return x coordinate
     */
    @Override
    public int getX() {
        return this.X;
    }
    /**
     * y coordinate in canvas
     * @return y coordinate
     */
    @Override
    public int getY() {
        return this.Y;
    }
    /**
     * speed info
     * @return speed
     */
    @Override
    public int getS() {
        return this.S;
    }
    /**
     * how to social info
     * @return
     */
    @Override
    public int getC() {
        return this.C;
    }
    /**
     * msak info
     * @return mask value
     */
    @Override
    public double getM() {
        return this.M;
    }
    /**
     * social distance
     * @return social distance info
     */
    @Override
    public int getD() {
        return this.D;
    }
    /**
     *  in 25sectimer info
     * @return true if in 25 sec timer
     */
    public boolean isIn25Sec() {
        return in25Sec;
    }
    /**
     * hospitallized info
     * @return true if ind at hospital
     */
    @Override
    public boolean isHospital(){
        return this.atHospital;
    }
    /**
     * set ind. in timer
     * @param in25Sec true if in 25 sec timer
     */
    public void setIn25Sec(boolean in25Sec) {
        this.in25Sec = in25Sec;
    }
    /**
     * ind. at movelist info
     * @return true if in list
     */
    @Override
    public boolean isMoveTempList() {
        return moveTempList;
    }
    /**
     * set list info
     * @param moveTempList true is in list
     */
    @Override
    public void setMoveTempList(boolean moveTempList) {
        this.moveTempList = moveTempList;
    }
    public void assignRandomPosition() {
        Random random = new Random();
        int index = random.ints(0,this.directionArr.length).findFirst().getAsInt();;
        while(this.direction == directionArr[index]){
            index = random.ints(0,directionArr.length).findFirst().getAsInt();;
        }
        this.direction = directionArr[index];
    }
    /**
     * infected info
     * @return if infected true
     */
    @Override
    public boolean isInfected() {
        return this.infected;
    }
    /**
     * set infected info
     * @param value true or false
     */
    @Override
    public void setInfected(boolean value) {
        this.infected=value;
    }
    /**
     * move according to position
     */
    @Override
    public void move() { // 1 = x, -1 = -x, 2 = y, -2 = -y, 3 = x+y, -3 = -x-y, 4 = -x+y, -4 = x-y
        if(move) {
            switch (this.direction){
                case 1:  X += S;
                    break;
                case -1: X -= S;
                    break;
                case 2:  Y -= S;
                    break;
                case -2: Y += S;
                    break;
                case 3:  X += S;
                         Y -= S;
                    break;
                case -3: X -= S;
                         Y += S;
                    break;
                case 4:  X -= S;
                         Y -= S;
                    break;
                case -4: X += S;
                         Y += S;
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * call mediator checkMe function
     */
    @Override
    public void callMediator() {
        this.mediator.checkMe(this);
    }
    /**
     * set move or not
     * @param move true or false
     */
    @Override
    public void setMove(boolean move){
        this.move = move;
    }
    /**
     * return move info
     * @return true or false
     */
    @Override
    public boolean canMove(){
        return this.move = move;
    }
    /**
     * set the position of opposite direction
     * @param d social distance
     */
    @Override
    public void staySocialDistance(int d){
        switch (this.direction){
            case 1:
                X -= d;
                break;
            case -1:
                X += d;
                break;
            case 2:
                Y += d;
                break;
            case -2:
                Y -= d;
                break;
            case 3:
                X -= d;
                Y += d;
                break;
            case -3:
                X += d;
                Y -= d;
                break;
            case 4:
                X += d;
                Y += d;
                break;
            case -4:
                X -= d;
                Y -= d;
                break;
            default:
                break;
        }
    }
    /**
     * direction info
     * @return direction
     */
    @Override
    public int getDirection(){
        return this.direction;
    }
    /**
     * if ind. dead, set true
     * @param value true or false
     */
    @Override
    public void setDead(boolean value) {
       this.dead = value;
    }
    /**
     * dead info
     * @return true or false
     */
    @Override
    public boolean isDead() {
        return this.dead;
    }
    /**
     * set hospital
     * @param value true or false
     */
    @Override
    public void setHospital(boolean value){
        this.atHospital = value;
    }
}
