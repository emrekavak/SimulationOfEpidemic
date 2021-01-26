package epidemic.simulation;
/**
 * This class just have update funtion with parameeters.
 * obrservers implement this class
 */
public interface Observer {
    /**
     * this method call from model calls and Update with values to observers
     * @param societyCount int
     * @param wearMaskCount int
     * @param notWearMaskCount int
     * @param infected int
     * @param healty int
     * @param hospitalized int
     * @param dead int
     * @param r double
     * @param z double
     */
    public void update(int societyCount, int wearMaskCount, int notWearMaskCount, int infected, int healty, int hospitalized, int dead, double r, double z, double aveD);
}
