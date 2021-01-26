package epidemic.simulation;

import java.util.ArrayList;

public class Society extends IndividualComponent{
    private ArrayList<IndividualComponent> individuals;

    public Society(){
        this.individuals = new ArrayList<IndividualComponent>();
    }

    @Override
    public IndividualComponent getIndividual(int i){
        if(i<individuals.size() &&  individuals.get(i)!=null)
            return this.individuals.get(i);
        else return null;
    }

    @Override
    public void move() {
        for(int i=0; i<this.individuals.size();i++){
            try{
                if(i<individuals.size() &&  individuals.get(i)!=null)
                    this.individuals.get(i).move();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addIndividual(IndividualComponent individual) {
        this.individuals.add(individual);
    }

    @Override
    public void removeIndividual(IndividualComponent individual) {
        int i=this.individuals.indexOf(individual);
        if(i!=-1) this.individuals.remove(i);
    }

    @Override
    public void callMediator(int i) {
        this.individuals.get(i).callMediator();
    }
    @Override
    public int getSize(){
        return this.individuals.size();
    }
    @Override
    public IndividualComponent getIndividual(IndividualComponent individual){
       int ind = this.individuals.indexOf(individual);
       if(ind != -1)
           return this.individuals.get(ind);
       else return null;
    }
}
