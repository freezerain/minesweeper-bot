public class Timer {
    long start;
    String name;
    public Timer(String name) {
        this.start = System.currentTimeMillis();
        this.name = name;
    }
    public void printTime(){
        System.out.println(name + " time: " + (System.currentTimeMillis()-start) + "ms.");
    }
}
