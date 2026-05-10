package sa.edu.kau.fcit.cpit252.project.strategy;

public class NoCounterStrategy implements CounterStrategy{

    @Override
    public boolean hasCounter() {
        return false;
    }

    @Override
    public int getMaxCount() {
        return 0;
    }

    @Override
    public String getLabel() {
        return "";
    }
}
