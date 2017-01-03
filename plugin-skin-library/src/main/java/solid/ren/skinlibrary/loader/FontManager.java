package solid.ren.skinlibrary.loader;

import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.listener.IFontLoader;
import solid.ren.skinlibrary.listener.IFontUpdate;

/**
 * Created by Taurus on 2017/1/2.
 */

public class FontManager implements IFontLoader{

    private List<IFontUpdate> mFontObservers;

    private static FontManager instance = new FontManager();

    private FontManager(){
        checkObserverArrays();
    }

    private void checkObserverArrays() {
        if(mFontObservers==null)
            mFontObservers = new ArrayList<>();
    }

    public static FontManager getInstance(){
        return instance;
    }

    @Override
    public void attach(IFontUpdate observer) {
        checkObserverArrays();
        mFontObservers.add(observer);
    }

    @Override
    public void detach(IFontUpdate observer) {
        if(mFontObservers==null)
            return;
        if(mFontObservers.contains(observer)){
            mFontObservers.remove(observer);
        }
    }

    @Override
    public void notifyFontUpdate(Typeface typeface) {
        if(mFontObservers==null)
            return;
        for(IFontUpdate fontUpdate : mFontObservers){
            fontUpdate.onFontUpdate(typeface);
        }
    }
}
