package sa.edu.kau.fcit.cpit252.project.strategy;

public class CounterStrategyResolver {

    private static final CounterStrategy NO_COUNTER = new NoCounterStrategy();
    private static final CounterStrategy TAWAF = new TawafSaiStrategy("أشواط الطواف");
    private static final CounterStrategy SAI = new TawafSaiStrategy("أشواط السعي");


    public static CounterStrategy resolve(String stepName) {
        if (stepName == null) return NO_COUNTER;
        String lower = stepName.toLowerCase();

        // Arabic
        if (stepName.contains("طواف") || stepName.contains("الطواف")) return TAWAF;
        if (stepName.contains("سعي") || stepName.contains("السعي")) return SAI;

        // English
        if (lower.contains("tawaf")) return TAWAF;
        if(lower.contains("sa'i") || lower.contains("sai")) return SAI;

        return NO_COUNTER;
    }

}
