package solid.ren.skinlibrary.attr.base;

import java.util.HashMap;
import solid.ren.skinlibrary.attr.BackgroundAttr;
import solid.ren.skinlibrary.attr.ProgressBarIndeterminateDrawable;
import solid.ren.skinlibrary.attr.SrcAttr;
import solid.ren.skinlibrary.attr.TextColorAttr;
import solid.ren.skinlibrary.utils.SkinL;

/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:9:47
 */
public class AttrFactory {

    private static String TAG = "AttrFactory";

    public static HashMap<String, SkinAttr> mSupportAttr = new HashMap<>();

    static {
        mSupportAttr.put("background", new BackgroundAttr());
        mSupportAttr.put("textColor", new TextColorAttr());
        mSupportAttr.put("src", new SrcAttr());
        mSupportAttr.put("indeterminateDrawable", new ProgressBarIndeterminateDrawable());
    }


    public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {
        SkinL.i(TAG, "attrName:" + attrName);
        SkinAttr mSkinAttr = mSupportAttr.get(attrName).clone();
        if (mSkinAttr == null) return null;
        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = attrValueRefId;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }

    /**
     *
     * Whether the test attribute is supported
     *
     * @param attrName
     * @return true : supported <br>
     * false: not supported
     */
    public static boolean isSupportedAttr(String attrName) {
        return mSupportAttr.containsKey(attrName);
    }

    /**
     * Increase the skin properties of support
     *
     * @param attrName
     * @param skinAttr custom attr.
     */
    public static void addSupportAttr(String attrName, SkinAttr skinAttr) {
        mSupportAttr.put(attrName, skinAttr);
    }
}
