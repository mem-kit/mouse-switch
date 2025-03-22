package cn.memkit.mouse;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

public interface MKUser32 extends User32 {
    MKUser32 INSTANCE = (MKUser32) Native.load("user32", MKUser32.class);
    boolean SwapMouseButton(boolean fSwap);
    void SetSystemCursor(HANDLE hcur, int id);

}
