package cn.memkit.mouse;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

enum HAND{
    LEFT,
    RIGHT
}
public class Switch {


    public static void loadCurse(HAND hand) {
        //
        boolean result = MKUser32.INSTANCE.SwapMouseButton(hand == HAND.LEFT);
        System.out.println(result);
        //
        final WinNT.HANDLE hImage;
        try {
            URL url = null;
            if(hand == HAND.LEFT){
                url = Switch.class.getClassLoader().getResource("aero_arrow_left.cur");
            } else {
                url = Switch.class.getClassLoader().getResource("aero_arrow.cur");
            }
            if(url == null){
                System.out.println("No cursor loaded");
                return;
            }
            URI uri = url.toURI();
            hImage = User32.INSTANCE.LoadImage(null, new File(uri).getAbsolutePath(), WinUser.IMAGE_CURSOR, 0, 0,
                    WinUser.LR_LOADFROMFILE);
        } catch (URISyntaxException | NullPointerException e) {
            throw new RuntimeException("Resource not found or invalid URI", e);
        }
        if (hImage != null) {
            System.out.println("LoadImage success");
        } else {
            System.out.println("LoadImage failed");
        }

        MKUser32.INSTANCE.SetSystemCursor(hImage, WinUser.IDC_ARROW);
    }

    public static void main(String[] args) {

        loadCurse(HAND.LEFT);
//        loadCurse(HAND.RIGHT);

    }
}