/**
 * Written by Emre KAVAK https://github.com/emrekavak
 */
package epidemic.simulation;

/**
 * this class mediator interface for mediator class implementation
 */
public interface MediatorInterface {
    /**
     * each ind. call this method when update happen
     * this function check the collide situation, calculate infected probability and assign ind infected or at
     * and have timer for stay collide position with max C sc
     * @param individual object
     */
    public void checkMe(Individual individual);
    /**
     * add ind. into list
     * @param individual object
     */
    public void addIndividual(IndividualComponent individual);
    /**
     * remove ind. from list
     * @param individual object
     */
    public void removeIndividual(IndividualComponent individual);
    /**
     * set spreadin factor value
     * @param r double
     */
    public void setSpreading(double r);
}
