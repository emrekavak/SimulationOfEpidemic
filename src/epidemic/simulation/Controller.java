package epidemic.simulation;

/**
 * This class controller class which is control the view and model conversation
 * Model and View class use this class and this class use them also
 */
public class Controller implements ControllerInterface {
    /**
     * MVC view object
     */
    private View view;
    /**
     * model class object
     */
    private ModelInterface model;
    /**
     * Add the created model into model field and create view object.
     * Start the GUI program
     * @param model model object
     */
    public Controller(ModelInterface model){
        this.model=model;
        this.view = new View(this,this.model);
        model.setViewWidth(1000);
        model.setViewHeight(600);
        this.view.createAllFrame();
    }
    /**
     * when start button press, view call this method
     */
    @Override
    public void start() {
        if(this.model.getSocietySize()>0) {
            this.model.start();
        }
        view.setStartBtn(false);
        view.setStopBtn(true);
        view.startElapsedTimer();
    }
    /**
     * when stop button press, view call this method
     */
    @Override
    public void stop() {
        this.model.stop();
        view.setStopBtn(false);
        view.setStartBtn(true);
    }
    /**
     * when user press add button, view call this method
     * @param m mask info
     * @param s speed info
     * @param d social distance
     * @param c how social info
     * @param count individual count
     */
    @Override
    public void addIndividual(double m, int s, int d,int c, int count) {
        this.model.addIndividual(m,s,d,c,count);
        if (!view.isStopBtnEnable())
            view.setStartBtn(true);
    }
    /**
     * When user press change button, view call this method
     * @param r Spreadin factor count
     * @param z mortality count
     */
    @Override
    public void setSpreadingAndMortalitiy(double r, double z) {
        this.model.setRandZ(r,z);
    }
}
