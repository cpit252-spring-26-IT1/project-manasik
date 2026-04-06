package sa.edu.kau.fcit.cpit252.project.factory;

import sa.edu.kau.fcit.cpit252.project.model.Hajj;
import sa.edu.kau.fcit.cpit252.project.model.Ritual;
import sa.edu.kau.fcit.cpit252.project.model.Umrah;

public class RitualFactory {
    // static to get object based on type
    public static Ritual getRitual(String type){
        //if null or empty return null
        if(type==null || type.isEmpty()) {
            return null;
            // create and return umrah if type matches
        } else if (type.equalsIgnoreCase("Umrah")){
            return new Umrah();
            // create and return hajj if type matches
        }else if (type.equalsIgnoreCase("Hajj")){
            return new Hajj();
        }
        //return null if type unknown
        return null;
    }
}
