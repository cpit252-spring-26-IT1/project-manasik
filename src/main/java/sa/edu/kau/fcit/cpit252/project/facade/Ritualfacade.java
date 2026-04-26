package sa.edu.kau.fcit.cpit252.project.facade;

import sa.edu.kau.fcit.cpit252.project.factory.RitualFactory;
import sa.edu.kau.fcit.cpit252.project.model.Ritual;

import java.util.List;

public class Ritualfacade {

    private Ritual ritual;

    // When the user picks Hajj or Umrah
    public boolean startRitual(String type){
        ritual = RitualFactory.getRitual(type);
        return ritual != null;
    }

    // Return all steps for the RoadMap
    public List<String> getAllSteps(){
        if (ritual == null) return List.of();
        return ritual.getSteps();
    }

    // Return the name of the step
    public String getStep(int index) {
        if (ritual == null) return "";
        return ritual.getSteps().get(index);
    }

    // Return the description of the step
    public String getStepDetails(int index) {
        if (ritual == null) return "";
        List<String> details = ritual.getDetails();
        if (index < 0 || index >= details.size()) return "";
        return details.get(index);
    }

    // Return ritual name (Hajj or Umrah)
    public String getRitualName(){
        if (ritual == null) return"";
        return ritual.getName();
    }


}
