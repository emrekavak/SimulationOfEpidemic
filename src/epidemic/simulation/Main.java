package epidemic.simulation;
/**
 * Written by Emre KAVAK https://github.com/emrekavak
 */
/**
 * Main Class Testing The simulation program just create Model and controller objects
 * @author EMRE KAVAK
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        ControllerInterface controller = new Controller(model);
    }
}
