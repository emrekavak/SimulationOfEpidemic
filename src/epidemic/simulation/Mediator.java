package epidemic.simulation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ths class apply mediator design pattern on individual interaction
 * calculate infected probabiltiy, if prob 0*5 set infected the individuals
 * Each individual register this class object
 */
public class Mediator implements MediatorInterface{
    /**
     * individual list
     */
    private ArrayList<IndividualComponent> individuals;
    /**
     * Spreading factor value
     */
    private double R = 0.5;     // 0.5 , 1.0
    /**
     * timer for stay collide position
     */
    private Timer timer = new Timer();
    /**
     * creates arraylist of individuals
     */
    public Mediator(){
        this.individuals = new ArrayList<IndividualComponent>();
    }
    /**
     * each ind. call this method when update happen
     * this function check the collide situation, calculate infected probability and assign ind infected or at
     * and have timer for stay collide position with max C sc
     * @param individual object
     */
    @Override
    public void checkMe(Individual individual) {
        for(int i=0; i< this.individuals.size();i++){   // just one collide capture
            if(!individual.isDead() && !this.individuals.get(i).isDead() ) {
                if (this.individuals.get(i).canMove() && individual.canMove() && !individual.isHospital() && !this.individuals.get(i).isHospital()) {
                    if (!(this.individuals.get(i).equals(individual))) {
                        int ax1 = individual.getX();
                        int ay1 = individual.getY();
                        int ax2 = ax1 + 5;
                        int ay2 = ay1 + 5;

                        int bx1 = this.individuals.get(i).getX();
                        int by1 = this.individuals.get(i).getY();
                        int bx2 = bx1 + 5;
                        int by2 = by1 + 5;

                        if (ax1 < bx2 && ax2 > bx1 && ay1 < by2 && ay2 > by1) { // collide check
                            this.individuals.get(i).setMove(false);
                            individual.setMove(false);
                            int maxC = Math.max(this.individuals.get(i).getC(), individual.getC());
                            int maxD = Math.min(this.individuals.get(i).getD(), individual.getD());
                            individual.setMove(true);
                            individual.staySocialDistance(maxD);     // stay ocial distance
                            individual.setMove(false);

                            int finalI = i;
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    individual.assignRandomPosition();
                                    individuals.get(finalI).assignRandomPosition();
                                    individual.setMove(true);
                                    individuals.get(finalI).setMove(true);
                                    if (individual.isInfected() || individuals.get(finalI).isInfected()) {
                                        double probability = Math.min(R * (1 + ((double) maxC) / 10) * individuals.get(finalI).getM() * individual.getM() * ((double) 1 - ((double) maxD) / (double) 10), 1);
                                        if (probability > 0.5 && !individual.isHospital() && !individual.isDead() &&
                                                !individuals.get(finalI).isHospital() && !individual.isDead()) {
                                            if (!individual.isInfected()) {
                                                individual.setInfected(true);
                                            } else {
                                                individuals.get(finalI).setInfected(true);
                                            }
                                        }
                                    }
                                }
                            };
                            timer.schedule(task, maxC * 1000); // mediator says "stay with together maxC seconds"
                            break;
                        }
                    }
                }
            }else {
                if(this.individuals.get(i).isDead())
                    this.individuals.remove(this.individuals.get(i));
                else this.individuals.remove(individual);
                break;
            }
        }

    }
    /**
     * add ind. into list
     * @param individual object
     */
    @Override
    public void addIndividual(IndividualComponent individual) {
        this.individuals.add(individual);
    }
    /**
     * remove ind. from list
     * @param individual object
     */
    @Override
    public void removeIndividual(IndividualComponent individual) {
        individuals.remove(individual);
    }
    /**
     * set spreadin factor value
     * @param r double
     */
    @Override
    public void setSpreading(double r) {
        this.R = r;
    }
}
