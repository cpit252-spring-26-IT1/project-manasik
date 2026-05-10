package sa.edu.kau.fcit.cpit252.project.strategy;

public class TawafSaiStrategy implements CounterStrategy {

    private final String label;

    public TawafSaiStrategy(String label){
        this.label = label;
    }


    @Override
    public boolean hasCounter() {
        return true;
    }

    @Override
    public int getMaxCount() {
        return 7;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
