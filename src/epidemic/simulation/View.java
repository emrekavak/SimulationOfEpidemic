/**
 * Written by Emre KAVAK https://github.com/emrekavak
 */
package epidemic.simulation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;

/**
 * View class in MVC model. This class represent GUI and create frames and panels
 * This class implement Observer interface for get update from model and implement
 * ActionListener interface for button actions
 * Use MyCanvas class for create 1000*600 panel and paint individual on this canvas
 * @author EMRE KAVAK
 */
public class View implements Observer, ActionListener {
    /**
     * For get society information, this class have model object
     */
    private ModelInterface model;
    /**
     * Button events will tell the contoller and it give back action
     */
    private ControllerInterface controller;
    /**
     * for holding canvas panel
     */
    private JInternalFrame innerCanvasFrame;
    /**
     * for create and update canvas
     */
    private MyCanvas canvas;
    /**
     * canvas width size
     */
    private final int width=1000;
    /**
     * canvas height size
     */
    private final int height=600;
    /**
     * all frames add in this frame
     */
    private JFrame mainFrame;

    // adding individual elements
    /**
     * add panel frame
     */
    private JInternalFrame innerAddFrame;
    /**
     * add button use add individuals
     */
    /**
     * start button for start the simulation
     */
    private JButton startBtn;
    /**
     * stop button for stop the simulation
     */
    private JButton addBtn;
    /**
     * + button for increase count
     */
    private JButton indCounterUpBtn;
    /**
     * - button for decrease count
     */
    private JButton indCounterdownBtn;

    //private ButtonGroup btnGroup;

    /**
     * in add panel social field values [1,500]
     */
    private JComboBox howSocialCombo;
    /**
     * in add panel distance field values [0,9]
     */
    private JComboBox distanceCombo;
    /**
     * hold Spearing factor field values [0.5,1.0]
     */
    private JComboBox spearingFactorCombo;
    /**
     * hold Mortality field values [0.5,1.0]
     */
    private JComboBox mortalityCombo;
    /**
     * hold Mask field values in add panel
     */
    private JComboBox maskCombo;
    /**
     * individual count for write the panel
     */
    private JTextField individualCount;
    /**
     * speed count for write the panel
     */
    private JTextField speedCount;
    /**
     * count of indvidivdual on add panel
     */
    private int individualCounter =1;
    /**
     * count of speed on add panel
     */
    private int speedCounter = 1; // [1,500]
    /**
     * up button for increase speed in add panel
     */
    private JButton speedCounterUpBtn;
    /**
     * down button for increase speed in add panel
     */
    private JButton speedCounterDownBtn;
    // spreading factor and mortality rate frame

    /**
     * frame for spearing factor and mortality
     */
    private JInternalFrame innerSpAndMorFrame;
    /**
     * change button for spearing factor and mortality values
     */
    private JButton changeSprMorBtn;
    /**
     * text in add panel
     */
    private JLabel infoSocietyCountAre;
    //start/stop frame

    private JButton stopBtn;
    // info frame
    /**
     * frame for info panel
     */
    private JInternalFrame innerInfoFrame;
    /**
     * mask label in info panel
     */
    private JLabel infoMaskArea1;
    /**
     * mask label in info panel
     */
    private JLabel infoMaskArea2;
    /**
     * info panel element for infected count
     */
    private JLabel infoInfectedCount;
    /**
     * info panel element for healthy count
     */
    private JLabel infoHealthyCount;
    /**
     * info panel element   for hospitalized
     */
    private JLabel infoHospitalizedCount;
    /**
     * info panel element dead count
     */
    private JLabel infoDeadCount;

    // elapsed time fields
    /**
     * panel time element text fied
     */
    private JLabel elapsedTimeText;
    /**
     * time panel
     */
    private JPanel innerElapsideTimePanel;
    /**
     * hold first ticked time the start time
     */
    private long firstTickTime;
    /**
     * keep start button info
     */
    private boolean startedBefore = false;
    /**
     * timer for simulation run time on the screen
     */
    private Timer elapsedTimer;
    /**
     * hold timer miliseconds info
     */
    private long runTime;

    // plots
    /**
     * frame for pie chart of society
     */
    private JInternalFrame societyPlotFrame;
    /**
     * add this values into pie chart
     */
    private DefaultPieDataset datasetSociety;
    /**
     * second info pie chart frame
     */
    private JInternalFrame maskPlotFrame;
    /**
     * mask dataset
     */
    private DefaultPieDataset datasetMask;
    /**
     * bar pilot chart frame
     */
    private JInternalFrame percentagePlotFrame;
    /**
     * bar pilot chart dataset
     */

    private DefaultCategoryDataset percentageDataSet;
    /**
     * bar pilot chart labels
     */
    private String infected = "infected";
    private String dead = "Dead";
    private String r = "R";
    private String z = "Z";
    private String maskPercentage = "%Mask";
    private String averageSocialDistance = "%D";

    /**
     * empty
     */
    public View(){

    }

    /**
     * assign frames, controller and model objects and create Mycanvas object
     * @param controller contoller object
     * @param model model object
     */
    public View(ControllerInterface controller, ModelInterface model){
        this.controller = controller;
        this.model = model;
        this.model.registerObserver(this);
        this.mainFrame = new JFrame("Main Frame");
        mainFrame.setLayout(null);
        this.canvas = new MyCanvas();
        canvas.setValues(this.model);
    }

    /**
     * call all create method of frames
     */
    public void createAllFrame(){
        createInfoFrame();
        createSimulation();
        createStartStopFrame();
        createElapsedTimeFrame();
        createAddFrame();
        createSpreAndMortaFrame();
        createPlots();
        showAllFrames();
    }
    public void startElapsedTimer(){
        if(startedBefore==false){
            this.firstTickTime = System.currentTimeMillis();
            startedBefore = true;
        }
        elapsedTimer.start();
    }

    /**
     * create 1000*600 canvas panel
     */
    public void createSimulation(){
        innerCanvasFrame = new JInternalFrame("Simulation Of Epidemic");
        innerCanvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        innerCanvasFrame.getContentPane().add(canvas);
        mainFrame.add(innerCanvasFrame);
    }

    /**
     * create 2 pie chart and 1 barchart pilots
     */
    public void createPlots(){
        datasetSociety =new DefaultPieDataset();
        JFreeChart chart = ChartFactory.createPieChart(
                "",
                datasetSociety,
                true,
                true,
                true);

        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);
        chartpanel.setPreferredSize(new Dimension(240,190));

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.setBounds(0,0,250,250);
        jPanel1.add(chartpanel, BorderLayout.NORTH);

        societyPlotFrame = new JInternalFrame("Society Info");
        societyPlotFrame.setBounds(0,690,250,190);
        societyPlotFrame.add(jPanel1);
        societyPlotFrame.pack();

        datasetMask =new DefaultPieDataset();
        JFreeChart chart2 = ChartFactory.createPieChart(
                "",
                datasetMask,
                true,
                true,
                true);

        ChartPanel chartpanel2 = new ChartPanel(chart2);
        chartpanel2.setDomainZoomable(true);
        chartpanel2.setPreferredSize(new Dimension(240,190));

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        jPanel2.setBounds(0,0,250,250);
        jPanel2.add(chartpanel2, BorderLayout.NORTH);

        maskPlotFrame = new JInternalFrame("Mask Info");
        maskPlotFrame.setBounds(260,690,250,200);
        maskPlotFrame.add(jPanel2);
        maskPlotFrame.pack();

        percentageDataSet = new DefaultCategoryDataset( );
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "",
                "Count",
                percentageDataSet,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new Dimension( 250 , 180 ) );

        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new BorderLayout());
        jPanel3.setBounds(0,0,250,250);
        jPanel3.add(chartPanel, BorderLayout.NORTH);
        percentagePlotFrame= new JInternalFrame("Percentage Info");
        percentagePlotFrame.add(jPanel3);
        percentagePlotFrame.setBounds(520,690,500,223);

        mainFrame.getLayeredPane().add(societyPlotFrame);
        mainFrame.getLayeredPane().add(maskPlotFrame);
        mainFrame.getLayeredPane().add(percentagePlotFrame);
    }
    /**
     * create elapsed time frame
     */
    public void createElapsedTimeFrame(){
        //innerElapsideTimeFrame = new JInternalFrame("Time");
        innerElapsideTimePanel = new JPanel();
        //innerElapsideTimePanel.setLayout(new FlowLayout());
        innerElapsideTimePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        elapsedTimeText = new JLabel(String.format("%02d:%02d.%03d", 0, 0, 0));
        elapsedTimeText.setBackground(Color.GREEN);
        elapsedTimeText.setFont(new Font("Times New Roman",Font.PLAIN,20));
        //elapsedTimeText.setText("aaaa");
        elapsedTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTime = System.currentTimeMillis() - firstTickTime;
                Duration durationTime = Duration.ofMillis(runTime);
                long hours = durationTime.toHours();
                durationTime = durationTime.minusHours(hours);
                long minutes = durationTime.toMinutes();
                durationTime = durationTime.minusMinutes(minutes);
                long milSec = durationTime.toMillis();
                long seconds = milSec / 1000;
                milSec -= (seconds * 1000);
                elapsedTimeText.setText(String.format("%02d:%02d.%03d", minutes, seconds, milSec));
            }
        });

        innerElapsideTimePanel.add(elapsedTimeText);
        mainFrame.add(innerElapsideTimePanel);
    }

    /**
     * create start, stop buttons
     */
    public void createStartStopFrame(){

        startBtn = new JButton("START");
        startBtn.setEnabled(false);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!startedBefore){
                    firstTickTime = System.currentTimeMillis();
                    startedBefore = true;
                }
                controller.start();
            }
        });
        stopBtn = new JButton("PAUSE");
        stopBtn.setEnabled(false);
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stop();
            }
        });
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(startBtn);
        p.add(stopBtn);
        p.setBounds(425,635,160,30);
        mainFrame.add(p);
    }

    /**
     * create information panel
     */
    public void createInfoFrame(){
        innerInfoFrame = new JInternalFrame("Society Information");

        JLabel infoSocietyCountMsg = new JLabel("Individual Count: ");
        infoSocietyCountAre = new JLabel();
        infoSocietyCountAre.setText("0");

        JLabel infoMask1 = new JLabel("Wearing Mask: ");
        infoMaskArea1 = new JLabel();
        infoMaskArea1.setText("0");

        JLabel infoMask2 = new JLabel("Not Wearing Mask: ");
        infoMaskArea2 = new JLabel();
        infoMaskArea2.setText("0");

        JLabel infoInfected = new JLabel("Infected: ");
        infoInfectedCount = new JLabel();
        infoInfectedCount.setText("0");

        JLabel infoHealthy = new JLabel("Healthy: ");
        infoHealthyCount = new JLabel();
        infoHealthyCount.setText("0");

        JLabel infoHospitalized = new JLabel("Hospitalized: ");
        infoHospitalizedCount = new JLabel();
        infoHospitalizedCount.setText("0");

        JLabel infoDead = new JLabel("Dead: ");
        infoDeadCount = new JLabel();
        infoDeadCount.setText("0");

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(7,7));
        p.setBounds(40,0,250,200);

        p.add(infoSocietyCountMsg);
        p.add(infoSocietyCountAre);

        p.add(infoMask1);
        p.add(infoMaskArea1);

        p.add(infoMask2);
        p.add(infoMaskArea2);

        p.add(infoInfected);
        p.add(infoInfectedCount);

        p.add(infoHealthy);
        p.add(infoHealthyCount);

        p.add(infoHospitalized);
        p.add(infoHospitalizedCount);

        p.add(infoDead);
        p.add(infoDeadCount);

        innerInfoFrame.setLayout(null);
        innerInfoFrame.add(p);
        innerInfoFrame.setResizable(false);

        mainFrame.add(innerInfoFrame);
    }

    /**
     *set start button visible or not
     * @param value true for enable or false for disable
     */
    public void setStartBtn(boolean value){
        this.startBtn.setEnabled(value);
    }

    /**
     * set stop button visible or not
     * @param value true for enable or false for disable
     */
    public void setStopBtn(boolean value){
        this.stopBtn.setEnabled(value);
    }

    /**
     * creates add Frame
     */
    public void createAddFrame(){
        innerAddFrame = new JInternalFrame("Add Individuals");
        JLabel mask = new JLabel("Wear Mask :");

        String maskArr[] = {"YES","NO"};
        maskCombo = new JComboBox(maskArr);

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));


        JLabel speedText = new JLabel("Speed:");
        speedCount = new JTextField(2);
        speedCount.setText(String.valueOf(speedCounter));
        speedCount.setEnabled(false);
        speedCounterUpBtn = new JButton("+");
        //upBtn.setBounds(200,330,110,30);
        speedCounterUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(speedCounter +1 < 500)
                    speedCounter++;
                speedCount.setText(String.valueOf(speedCounter));
            }
        });
        speedCounterDownBtn = new JButton("-");
        speedCounterDownBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(speedCounter -1>0) {
                    speedCounter--;
                    speedCount.setText(String.valueOf(speedCounter));
                }
            }
        });
        p1.add(mask);
        p1.add(maskCombo);
        p1.setBounds(20,10,240,30);
        innerAddFrame.add(p1);
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.add(speedText);
        p2.add(speedCount);
        p2.add(speedCounterUpBtn);
        p2.add(speedCounterDownBtn);
        p2.setBounds(20,45,240,30);
        innerAddFrame.add(p2);

        JLabel distance = new JLabel("Social Distance:");
        Integer distanceArr[] = {0,1,2,3,4,5,6,7,8,9};
        distanceCombo = new JComboBox(distanceArr);
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.add(distance);
        p3.add(distanceCombo);
        p3.setBounds(20,80,240,30);
        innerAddFrame.add(p3);

        JLabel howSocial = new JLabel("How social:");
        Integer howSocialArr[] = {1,2,3,4,5};
        howSocialCombo = new JComboBox(howSocialArr);
        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));
        p4.add(howSocial);
        p4.add(howSocialCombo);
        p4.setBounds(20,115,240,30);
        innerAddFrame.add(p4);

        JLabel count = new JLabel("Individual Count: ");
        individualCount = new JTextField(2);
        individualCount.setText(String.valueOf(this.individualCounter));
        individualCount.setEnabled(false);
        //individualCount.setFont(new Font("Times New Roman",Font.BOLD,11));
        indCounterUpBtn = new JButton("+");
        //upBtn.setBounds(200,330,110,30);
        indCounterUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                individualCounter++;
                individualCount.setText(String.valueOf(individualCounter));
            }
        });
        indCounterdownBtn = new JButton("-");
        //downBtn.setPreferredSize(new Dimension(2,2));
        indCounterdownBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(individualCounter -1>0) {
                    individualCounter--;
                    individualCount.setText(String.valueOf(individualCounter));
                }
            }
        });
        JPanel p7 = new JPanel();
        p7.setLayout(new FlowLayout(FlowLayout.LEFT));
        p7.add(count);
        p7.add(individualCount);
        p7.add(indCounterUpBtn);
        p7.add(indCounterdownBtn);
        p7.setBounds(20,150,350,30);
        innerAddFrame.add(p7);

        addBtn = new JButton("Add");
        addBtn.setBounds(20,185,80,30);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double m = maskCombo.getSelectedItem().toString().equals("YES") ? 0.2 : 1.0;
                int d = Integer.parseInt(distanceCombo.getSelectedItem().toString());
                int c = Integer.parseInt(howSocialCombo.getSelectedItem().toString());
                controller.addIndividual(m,speedCounter,d,c, individualCounter);
            }
        });
        innerAddFrame.add(addBtn);
        innerAddFrame.setLayout(null);
        innerAddFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(innerAddFrame);
    }

    /**
     * Creat Spearing and mortality frame
     */
    public void createSpreAndMortaFrame(){
        this.innerSpAndMorFrame = new JInternalFrame("Spreading And Mortality");
        JLabel spearingFactor = new JLabel("Spearing Factor:");
        Double spearingFactorArr[] = {0.5,0.6,0.7,0.8,0.9,1.0};
        spearingFactorCombo = new JComboBox(spearingFactorArr);
        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout(FlowLayout.LEFT));
        p5.add(spearingFactor);
        p5.add(spearingFactorCombo);
        p5.setBounds(20,0,220,30);
        innerSpAndMorFrame.add(p5);

        JLabel mortality = new JLabel("Mortality Rate:");
        Double mortalityArr[] = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
        mortalityCombo = new JComboBox(mortalityArr);
        JPanel p6 = new JPanel();
        p6.setLayout(new FlowLayout(FlowLayout.LEFT));
        p6.add(mortality);
        p6.add(mortalityCombo);
        p6.setBounds(20,40,220,30);
        innerSpAndMorFrame.add(p6);

        changeSprMorBtn = new JButton("Change");
        changeSprMorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               controller.setSpreadingAndMortalitiy(Double.parseDouble(spearingFactorCombo.getSelectedItem().toString()),
                       Double.parseDouble(mortalityCombo.getSelectedItem().toString()));
            }
        });
        changeSprMorBtn.setBounds(50,80,80,30);
        innerSpAndMorFrame.add(changeSprMorBtn);

        innerSpAndMorFrame.setLayout(null);
        mainFrame.add(innerSpAndMorFrame);
    }

    /**
     * all created frames set visible and set bounds
     */
    public void showAllFrames(){
        innerCanvasFrame.setVisible(true);
        innerCanvasFrame.setBounds(0,0,1010,635);
        innerCanvasFrame.setResizable(false);

        innerElapsideTimePanel.setBounds(600,635,150,100);

        innerInfoFrame.setBounds(1020,0,250,230);
        innerInfoFrame.setResizable(false);
        innerInfoFrame.setVisible(true);

        innerAddFrame.setVisible(true);
        innerAddFrame.setResizable(false);
        innerAddFrame.setBounds(1020,240,250,260);

        innerSpAndMorFrame.setBounds(1020,510,260,150);
        innerSpAndMorFrame.setResizable(false);
        innerSpAndMorFrame.setVisible(true);

        societyPlotFrame.setVisible(true);
        maskPlotFrame.setVisible(true);
        percentagePlotFrame.setVisible(true);

        mainFrame.setSize(1300,980);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    /**
     * returm canvas width
     * @return canvas width
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * returm canvas height
     * @return canvas height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * check stop button
     * @return true if enable, false if not
     */
    public boolean isStopBtnEnable(){
        return this.stopBtn.isEnabled();
    }

    /**
     * Observer method for update canvas. Model call this method when update happen
     * @param societyCount  society count
     * @param wearMaskCount wearing mask individual count
     * @param notWearMaskCount  not wearing mask individual count
     * @param infected  infected count
     * @param healthy   healthy count
     * @param hospitalized sent hospital count
     * @param dead  dead count
     */
    @Override
    public void  update(int societyCount, int wearMaskCount, int notWearMaskCount, int infected, int healthy, int hospitalized, int dead, double r, double z, double aveD) {

        datasetSociety.setValue("Society",societyCount);
        datasetSociety.setValue("Infected ",infected);
        datasetSociety.setValue("Healthy ",healthy);
        datasetSociety.setValue("Dead ",dead);

        datasetMask.setValue("Wear",wearMaskCount);
        datasetMask.setValue("Not Wear",notWearMaskCount);

        percentageDataSet.addValue( infected, this.infected , this.infected  );
        percentageDataSet.addValue( dead , this.dead , this.dead );
        percentageDataSet.addValue( r , this.r , this.r );
        percentageDataSet.addValue( z , this.z , this.z );
        double maskAvg = 0.0;
        if(wearMaskCount!=0) maskAvg =societyCount/wearMaskCount;
        percentageDataSet.addValue( maskAvg , this.maskPercentage , this.maskPercentage  );
        percentageDataSet.addValue( aveD , this.averageSocialDistance , this.averageSocialDistance );

        this.infoSocietyCountAre.setText(String.valueOf(this.model.getSocietySize()));
        this.infoMaskArea1.setText(String.valueOf(wearMaskCount));
        this.infoMaskArea2.setText(String.valueOf(notWearMaskCount));
        this.infoInfectedCount.setText(String.valueOf(infected));
        this.infoHealthyCount.setText(String.valueOf(healthy));
        this.infoHospitalizedCount.setText(String.valueOf(hospitalized));
        this.infoDeadCount.setText(String.valueOf(dead));
        this.canvas.setValues(this.model);
        this.canvas.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.stopBtn){
            this.controller.stop();
        }else if(e.getSource() == this.startBtn){
            this.controller.start();
        }
    }
}

/**
 * this class use for create canvas panel
 */
class MyCanvas extends JPanel{
    private ModelInterface model;

    /**
     * empty
     */
    public MyCanvas(){
    }

    /**
     * set the model because of draw individuals according to x,y coordinate
     * @param model model object
     */
    public void setValues( ModelInterface model){
        this.model=model;
    }

    /**
     * this method draw the canvas
     * @param gc graphic object
     */
    @Override
    protected void paintComponent(Graphics gc) {
        Graphics2D gc2 = (Graphics2D)gc;
        super.paintComponent(gc2);
        for (int i = 0; i < this.model.getSocietySize(); i++) {
            if(this.model.getIndividual(i).isHospital() == false) {
                if (this.model.getIndividual(i).isInfected())
                    gc2.setColor(Color.red);
                else gc2.setColor(Color.GREEN);

                gc2.fillRect(this.model.getIndividual(i).getX(), this.model.getIndividual(i).getY(), 5, 5);
            }
        }
    }
}