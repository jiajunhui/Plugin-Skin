package solid.ren.skinlibrary.loader;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.attr.base.AttrFactory;
import solid.ren.skinlibrary.attr.base.DynamicAttr;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.config.SkinConfig;
import solid.ren.skinlibrary.entity.SkinItem;
import solid.ren.skinlibrary.utils.SkinL;
import solid.ren.skinlibrary.utils.SkinListUtils;

/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:21:19
 * <p></p>
 * Custom InflaterFactory，Used to replace the default LayoutInflaterFactory.
 * Reference link：http://willowtreeapps.com/blog/app-development-how-to-get-the-right-layoutinflater/
 */
public class SkinInflaterFactory implements LayoutInflaterFactory {

    private static String TAG = "SkinInflaterFactory";
    /**
     * Storage of View and its attrs, which have skin changes
     */
    private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();

    private AppCompatActivity mAppCompatActivity;

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        AppCompatDelegate delegate = mAppCompatActivity.getDelegate();
        View view = delegate.createView(parent, name, context, attrs);

        if (view instanceof TextView) {
            TextViewRepository.add((TextView) view);
        }

        if (isSkinEnable) {
            if (view == null) {
                // view = createView(context, name, attrs);
                view = ViewProducer.createViewFromTag(context, name, attrs);
            }
            if (view == null) {
                return null;
            }
            parseSkinAttr(context, attrs, view);
        }
        return view;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    /**
     * To collect the properties for the replacement of the skin
     *
     * @param context
     * @param attrs
     * @param view
     */
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        //Storage View can be replaced by the collection of skin attributes
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //attr name
            String attrName = attrs.getAttributeName(i);
            //attr value
            String attrValue = attrs.getAttributeValue(i);

            SkinL.i("Aattrname", "Aattrname:" + attrName);
            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue;
            }
            //only check reference type attr , such as @color/xxx
            if (attrValue.startsWith("@")) {
                try {
                    //res id
                    int id = Integer.parseInt(attrValue.substring(1));
                    //You define the name of the attribute . such as item_text_color
                    String entryName = context.getResources().getResourceEntryName(id);
                    //attr type name . such as color,background.
                    String typeName = context.getResources().getResourceTypeName(id);
                    //Check whether the attr can be skin
                    SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                    SkinL.i("parseSkinAttr",
                            "view:" + view.getClass().getSimpleName() + "\n" +
                                    "id:" + id + "\n" +
                                    "attrName:" + attrName + " | attrValue:" + attrValue + "\n" +
                                    "entryName:" + entryName + "\n" +
                                    "typeName:" + typeName
                    );
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!SkinListUtils.isEmpty(viewAttrs)) {
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;
            mSkinItems.add(skinItem);
            //To determine whether the current skin from outside
            if (SkinManager.getInstance().isExternalSkin()) {
                skinItem.apply();
            }
        }
    }

    /**
     * apply the skin
     */
    public void applySkin() {

        if (SkinListUtils.isEmpty(mSkinItems)) {
            return;
        }

        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue;
            }
            si.apply();
        }
    }

    /**
     * clear the skins attrs list.
     */
    public void clean() {
        if (SkinListUtils.isEmpty(mSkinItems)) {
            return;
        }
        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue;
            }
            si.clean();
        }
    }

    public void addSkinView(SkinItem item) {
        mSkinItems.add(item);
    }

    /**
     * Dynamically add View, which has skin changes, and its attrs.
     *
     * @param context
     * @param view
     * @param attrName
     * @param attrValueResId
     */
    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId) {
        int id = attrValueResId;
        String entryName = context.getResources().getResourceEntryName(id);
        String typeName = context.getResources().getResourceTypeName(id);
        SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        List<SkinAttr> viewAttrs = new ArrayList<>();
        viewAttrs.add(mSkinAttr);
        skinItem.attrs = viewAttrs;
        skinItem.apply();
        addSkinView(skinItem);
    }

    /**
     * Dynamically add View, which has skin changes, and its attrs.
     *
     * @param context
     * @param view
     * @param pDAttrs
     */
    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs) {
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;

        for (DynamicAttr dAttr : pDAttrs) {
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        }

        skinItem.attrs = viewAttrs;
        skinItem.apply();
        addSkinView(skinItem);
    }


    public void dynamicAddFontEnableView(TextView textView) {
        TextViewRepository.add(textView);
    }

}
