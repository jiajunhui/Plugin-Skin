package solid.ren.skinlibrary.listener;

/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:20:47
 * <p></p>
 *
 * Used to add, remove the need to update the skin interface and
 * notify the user interface to update the skin
 *
 */
public interface ISkinLoader {
    void attach(ISkinUpdate observer);

    void detach(ISkinUpdate observer);

    void notifySkinUpdate();
}
