# SimulationOfEpidemic
Simulation of Epidemic Desktop Program written using JAVA language. JAVA version = 11

<h2> Used libraries </h2>
<li> Swing
<li> JFreeChart

<h2> Used Object Oriented Design Patterns </h2>
<li> Model View Controller (MVC): Observer, Strategy, Composite
<li> Mediator: Used for Individual interactions
<li> Composite: Used for Create individuals

<h2> Rules </h2>
<li> Each individual represent by 5x5 square in 1000x600 canvas
<li> Randomly 1 individual assigned infected at the beginning
<li> Let two individuals I_1 and I_2 collide, with mask statuses M_1 and M_2, and social
distances D_1 and D_2 respectively. Let I_1 be infected and I_2 be healthy. They stay
together for a duration C=max{C_1,C_2} before parting, and the social distance between
them is D=min{D_1,D_2}.
<li> According to p=min(R * (1+C/10) * M_1 * M_2 * (1-D/10),1) formula, if p>0.5, this individual will assume infected.
<li> After 25 seconds, individual hospitilazed and deleted from canvas temporarily
<li> After 10 seconds, hospitilazed individual back to canvas as healty
<li> Hospital has individual count / 10 ventilator. If hospital full, individual back to canvas and when 1 ventilator be empty go to hospital
<li> An infected individual will die after 100 * (1-Z) seconds if he/she still infected
<br>

<h2>GUI Example</h2>
<img src = "https://github.com/emrekavak/SimulationOfEpidemic/blob/main/simulationOfEpidemic.png" >

<li> After you add individuals from add frame, you should click the START button and simulation will start.
<li> If you click PAUSE button, simulation will pause but the data will continue process.
<li> The frames did not fixed positions, so you can change the design of GUI.
<li> The charts show the statistical informations
<br>
<b> This program implemented in Object Oriented Design Pattern Final project.</b>
