package sa.edu.kau.fcit.cpit252.project.model;

import java.util.List;

public class Umrah implements Ritual {

    //return the name of the ritual
    @Override
    public String getName(){
        return "Umrah";
    }

    //return steps for umrah
    @Override
    public List<String> getSteps(){
        return List.of("الإحرام",
                "الميقات",
                "عند دخول مكة وبعد دخول المسجد الحرام",
                "الطواف",
                "الفراغ من الطواف",
                "الصلاة عند مقام إبراهيم عليه السلام",
                "استلام الحجر الأسود",
                "السعي بين الصفا والمروة",
                "الحلق و التقصير");

    }
}
