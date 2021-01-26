package epidemic.simulation;

/**
 * interface for contoller class
 */
public interface ControllerInterface {
    /**
     * when start button press, view call this method
     */
    public void start();
    /**
     * when stop button press, view call this method
     */
    public void stop();
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
     * When user press change button, view call this method
     * @param r Spreadin factor count
     * @param z mortality count
     */
    public void setSpreadingAndMortalitiy(double r, double z);
}
