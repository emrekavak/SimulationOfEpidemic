package epidemic.simulation;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * MVC model class hold data and manage data logic.
 * Implement ModelInterface which is refer to Subject for observer pattern
 * View register this class for get updates
 * Hold individuals via society object
 */
public class Model implements ModelInterface {
    /**
     * observer list
     */
    private ArrayList<Observer> observers;  // View will add this array
    /**
     * hold individuals
     */
    private IndividualComponent society;
    /**
     * for producer consumer semaphore keep fill count for consumer
     */
    private static Semaphore fillCount = new Semaphore(0);    //
    /**
     * for producer consumer semaphore keep fill count for producer
     */
    private static Semaphore emptyCount;                              //
    /**
     * keep ventilator count according to P/10
     */
    private volatile int ventilatorCount = 0;
    /**
     * count of how many individual at hospital
     */
    private volatile int hospitalizedCount = 0 ;
    /**
     * for register individuals into mediator object and set up R value and call checkme function
     */
    private MediatorInterface mediator;
    /**
     * for use start stop
     */
    private boolean move=false;  // use for pause, continue situations
    /**
     * for checking individual dont pass canvas view width
     */
    private int viewWidth;
    /**
     * for checking individual dont pass canvas view height
     */
    private int viewHeight;
    /**
     * keep how many times start button pass
     */
    private int startCount = 0;
    /**
     * for assign random coordinates
     */
    private int[] directionArr={1,-1,2,-2,3,-3,4,-4};

    // Society informations
    /**
     * society wear msak count
     */
    private int wearMaskCount=0;
    /**
     * society not wear msak count
     */
    private int notWearMaskCount=0;
    /**
     * society infected count
     */
    private int infectedCount = 0;
    /**
     * society healthy count
     */
    private int healthyCount = 0;
    /**
     * society dead count
     */
    private int deadCount = 0;
    /**
     * mortality rate
     */
    private double Z = 0.1;
    private double R = 0.5;

    /**
     * create society, obsever ad mediator objects
     */
    public Model() {
        this.society = new Society();
        this.observers = new ArrayList<Observer>();
        this.mediator = new Mediator();
    }

    /**
     * when individual infected, index pass this method and after 100*(1-z) time
     * if individual still isfected, remove from society perminantyle
     * @param i index of individual
     */
    public void deadRoom(int i){
        double deadTime = 100.0*(1.0-Z);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                    if(society.getIndividual(i) != null){
                        if(society.getIndividual(i).isInfected()){
                            society.removeIndividual(society.getIndividual(i));
                            deadCount++;
                            infectedCount--;
                        }
                    }
                }
        };
        timer.schedule(task,1000*(int)deadTime); // run after 25 sc
    }

    /**
     * start the individual moving operation and update each 20 ms
     * Control all logic operations
     */
    @Override
    public void start() {
        startCount++;
        if(startCount==1){  // 1 individual assign infected randomly
            Random random = new Random();
            int index = random.ints(0,this.society.getSize()).findFirst().getAsInt();
            this.society.getIndividual(index).setInfected(true);
            this.society.getIndividual(index).setMoveTempList(true);
        }
        if(move == false) {
            move = true;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(move) {
                        for (int i = 0; i < society.getSize(); i++) {
                            if(society.getIndividual(i).isInfected() && !society.getIndividual(i).isHospital() && !society.getIndividual(i).isIn25Sec()){
                                infectedCount++;
                                healthyCount--;
                                deadRoom(i);
                                society.getIndividual(i).setMoveTempList(false);
                                society.getIndividual(i).setIn25Sec(true);
                                timer25Sec(i);
                            }
                            int direction = society.getIndividual(i).getDirection();
                            int x = society.getIndividual(i).getX();
                            int y = society.getIndividual(i).getY();
                            int s = society.getIndividual(i).getS();
                            if (direction == -1) {
                                if (x - s <= 0) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            } else if (direction == 1) {
                                if (x + s + 5 >= viewWidth) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            } else if (direction == -2) {
                                if (y + s + 5 >= viewHeight) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            } else if (direction == 2) {
                                if (y - s <= 0 || x - s <=0) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            } else if (direction == 3) {
                                if (y - s <= 0 || x + s + 5 >= viewWidth) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            } else if (direction == -3) {
                                if (x - s <= 0 || y + s + 5 >= viewHeight) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            } else if (direction == 4) {
                                if (x - s <= 0 || y - s <= 0) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            } else if (direction == -4) {
                                if (x + s + 5 >= viewWidth || y + s + 5 > viewHeight) {
                                    society.getIndividual(i).assignRandomPosition();
                                }
                            }
                        }
                        society.move();
                        for (int i = 0; i < society.getSize(); i++) {
                            society.callMediator(i);
                        }
                        notifyObservers();
                    }
                }
            };
            timer.schedule(task, 0, 20);
        }
    }

    /**
     * this method get individual index, creates a 25 ms timer
     * after 25 ms, using producer model, enter critical section
     * and set individual at hospital true and call timer10Sec function
     * @param i index of individuals
     */
    @Override
    public void timer25Sec(int i) {
        Timer timer25sc = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if(hospitalizedCount < ventilatorCount) {
                        if (hospitalizedCount < ventilatorCount) {
                                emptyCount.acquire();
                                synchronized (Model.class) {     // like mutex, critical section
                                    if(society.getIndividual(i)!=null){
                                        society.getIndividual(i).setHospital(true);
                                        hospitalizedCount++;
                                        timer10Sec(i);
                                        fillCount.release();      // say consumer 1 patient added
                                    }else emptyCount.release();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer25sc.schedule(task,25*1000); // run after 25 sc
    }

    /**
     * this method call from timer25ms method
     * Creates 10 sec timer and use consumer pattern, set the individual hospital false
     * @param i index individual
     */
    @Override
    public void timer10Sec(int i) {
        Timer timer10sc = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if(hospitalizedCount>0) {
                        if (hospitalizedCount > 0) {// check patient is there or not
                            fillCount.acquire();
                            synchronized (Model.class) { // after patient added, enter critical section
                                if(society.getIndividual(i) != null) {
                                    if (!society.getIndividual(i).isDead()) {
                                        society.getIndividual(i).setHospital(false);
                                        society.getIndividual(i).setInfected(false);
                                        society.getIndividual(i).setIn25Sec(false);
                                        healthyCount++;
                                        if(infectedCount>0) infectedCount--;
                                        hospitalizedCount--;
                                        emptyCount.release();                 // say to producer 1 item deleted
                                    }
                                }else fillCount.release();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer10sc.schedule(task,10000); // run after 10 sc
    }

    /**
     * set move false
     */
    @Override
    public void stop() {
        move=false;
    }

    /**
     * regester observer
     * @param observer observer object
     */
    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * remove observer
     * @param observer observer object
     */
    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * this method call from start method every 20 ms
     * call update method to notify observer update happend
     */
    @Override
    public void notifyObservers() {
        double averageSocialDistance = 0;
        for(int i=0; i< this.society.getSize();i++){
            averageSocialDistance += this.society.getIndividual(i).getD();
        }
        if(averageSocialDistance!=0)
            averageSocialDistance =  averageSocialDistance / (double)this.society.getSize();
        for(int i=0;i<this.observers.size();i++)
            //int societyCount, int wearMaskCount, int notWearMaskCount, int infected, int healty, int hospitalized, int dead
            this.observers.get(i).update(society.getSize(),this.wearMaskCount,this.notWearMaskCount,this.infectedCount,
                    this.healthyCount,this.hospitalizedCount,this.deadCount, this.R, this.Z,averageSocialDistance);
    }

    /**
     * set view width
     * @param width view widt info
     */
    @Override
    public void setViewWidth(int width) {
        this.viewWidth = width;
    }

    /**
     * set view height
     * @param height view height info
     */
    @Override
    public void setViewHeight(int height) {
        this.viewHeight = height;
    }

    /**
     * set R ve Z values. call from controller
     * @param r Sperading factor value
     * @param z mortality value
     */
    @Override
    public void setRandZ(double r, double z) {
        this.mediator.setSpreading(r);
        this.Z = z;
        this.R = r;
    }

    /**
     * add individual into society. view call controller and contorler call this method
     * @param m mask info
     * @param s speed ingo
     * @param d social distance info
     * @param c how social info
     * @param count added individual cont
     */
    @Override
    public void addIndividual(double m, int s, int d,int c, int count) {
        int x,y;
        int index;
        int direction;
        for(int i=0; i<count;i++) {
            if(m == 0.2) this.wearMaskCount++;  // random position assign
            else if(m == 1.0) this.notWearMaskCount++;
            Random random = new Random();
            x = random.ints(0,1000).findFirst().getAsInt();
            y = random.ints(0,600).findFirst().getAsInt();
            index = random.ints(0,4).findFirst().getAsInt();
            direction=directionArr[index];
            this.society.addIndividual(new Individual(x, y, m, s, d, c, direction, false, this.mediator));
        }
        healthyCount = this.society.getSize()-infectedCount;
        this.emptyCount = new Semaphore((this.society.getSize()/10) -this.hospitalizedCount);    // first access unlock producer
        this.ventilatorCount = this.society.getSize()/10;
        notifyObservers();
    }

    /**
     * return individual from society object. view use these function
     * @param i index
     * @return individual object
     */
    @Override
    public IndividualComponent getIndividual(int i){
        return this.society.getIndividual(i);
    }

    /**
     * return society size
     * @return society size
     */
    @Override
    public int getSocietySize(){
        return  this.society.getSize();
    }
}
