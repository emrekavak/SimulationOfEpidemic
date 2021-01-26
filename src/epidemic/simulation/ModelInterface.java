package epidemic.simulation;

public interface ModelInterface {
    /**
     * start the individual moving operation and update each 20 ms
     * Control all logic operations
     */
    public void start();
    /**
     * when stop button press, view call this method
     */
    public void stop();
    /**
     * return individual from society object. view use these function
     * @param i index
     * @return individual object
     */
    public IndividualComponent getIndividual(int i);
    /**
     * return individual size
     * @return int
     */
    public int getSocietySize();
    /**
     * register object
     * @param observer object
     */
    public void registerObserver(Observer observer);
    /**
     * remove registered observer
     * @param observer object
     */
    public void removeObserver(Observer observer);
    /**
     * notify observers
     */
    public void notifyObservers();
    /**
     * set view width
     * @param width int
     */
    public void setViewWidth(int width);
    /**
     * set view height
     * @param height
     */
    public void setViewHeight(int height);
    /**
     * set R ve Z values
     * @param r int
     * @param z int
     */
    public void setRandZ(double r, double z);
    /**
     * when user press add button, view call this method
     * @param m mask info
     * @param s speed info
     * @param d social distance
     * @param c how social info
     * @param count individual count
     */
    public void addIndividual(double m, int s, int d,int c, int count);
    /**
     * this method get individual index, creates a 25 ms timer
     * after 25 ms, using producer model, enter critical section
     * and set individual at hospital true and call timer10Sec function
     * @param i index of individuals
     */
    abstract void timer25Sec(int i);
    /**
     * this method call from timer25ms method
     * Creates 10 sec timer and use consumer pattern, set the individual hospital false
     * @param i index individual
     */
    public void timer10Sec(int i);
}
