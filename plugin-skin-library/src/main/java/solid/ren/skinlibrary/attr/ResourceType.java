package solid.ren.skinlibrary.attr;

/**
 * Created by Taurus on 16/12/7.
 */

public enum ResourceType {

    Color("color"),Drawable("drawable"),Bool("bool")
    ;

    String str;

    ResourceType(String str){
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
