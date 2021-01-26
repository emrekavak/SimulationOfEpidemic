package epidemic.simulation;

public abstract class IndividualComponent {

    public int getX() {
        throw new UnsupportedOperationException();
    }
    public int getY() {
        throw new UnsupportedOperationException();
    }
    public int getS() {
        throw new UnsupportedOperationException();
    }
    public int getC() {
        throw new UnsupportedOperationException();
    }
    public double getM() {
        throw new UnsupportedOperationException();
    }
    public int getD() {
        throw new UnsupportedOperationException();
    }
    public IndividualComponent getIndividual(int i){
        throw new UnsupportedOperationException();
    }
    public IndividualComponent getIndividual(IndividualComponent individual){
        throw new UnsupportedOperationException();
    }
    public void setHospital(boolean value){
        throw new UnsupportedOperationException();
    }
    public boolean isHospital(){
        throw new UnsupportedOperationException();
    }
    public void assignRandomPosition(){
        throw new UnsupportedOperationException();
    }
    public boolean isInfected() {
        throw new UnsupportedOperationException();
    }
    public void setInfected(boolean value) {
        throw new UnsupportedOperationException();
    }
    public void move(){
        // direction yönü ne ise o yönde hızına göre ++ yapıcaz
    }
    public void addIndividual(IndividualComponent individual){
        // component kullanıcak bu classı
    }
    public void removeIndividual(IndividualComponent individual){
        // component kullanıcak bu classı
    }
    public void callMediator(){
        throw new UnsupportedOperationException();
    }
    public void callMediator(int i){
        throw new UnsupportedOperationException();
    }
    public void setMove(boolean move){
        throw new UnsupportedOperationException();
    }
    public void staySocialDistance(int d){
        throw new UnsupportedOperationException();
    }
    public int getSize(){
        throw new UnsupportedOperationException();
    }
    public void setInfected(int i,boolean value) {
        throw new UnsupportedOperationException();
    }
    public boolean isInfected(int i) {
        throw new UnsupportedOperationException();
    }
    public void setPosition(int i){
        throw new UnsupportedOperationException();
    }
    public boolean canMove(){
        throw new UnsupportedOperationException();
    }
    public int getDirection(){
        throw new UnsupportedOperationException();
    }
    public boolean isMoveTempList() {
        throw new UnsupportedOperationException();
    }
    public void setMoveTempList(boolean moveTempList) {
        throw new UnsupportedOperationException();
    }
    public boolean isIn25Sec() {
        throw new UnsupportedOperationException();
    }
    public void setIn25Sec(boolean in25Sec) {
        throw new UnsupportedOperationException();
    }
    public void setDead(boolean value) {
        throw new UnsupportedOperationException();
    }
    public boolean isDead() {
        throw new UnsupportedOperationException();
    }
}
