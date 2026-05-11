package sa.edu.kau.fcit.cpit252.project.strategy;

public class CounterStrategyResolver {

    private static final CounterStrategy NO_COUNTER = new NoCounterStrategy();
    private static final CounterStrategy TAWAF = new TawafSaiStrategy("أشواط الطواف");
    private static final CounterStrategy SAI = new TawafSaiStrategy("أشواط السعي");


    public static CounterStrategy resolve(String stepName) {
        if (stepName == null) return NO_COUNTER;
        String trimmed = stepName.trim();
        String lower = stepName.toLowerCase();

        // Arabic
        if (trimmed.equals("الطواف")) return TAWAF;
        if (trimmed.startsWith("طواف")) return TAWAF;
        if (trimmed.startsWith("السعي")) return SAI;

        // English
        if (lower.startsWith("tawaf")) return TAWAF;
        if (lower.startsWith("sa'i") || lower.startsWith("sai")) return SAI;

        return NO_COUNTER;
    }

}
