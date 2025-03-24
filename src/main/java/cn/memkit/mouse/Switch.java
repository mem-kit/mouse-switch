package cn.memkit.mouse;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

enum HAND {
    LEFT,
    RIGHT
}

public class Switch {

    private static boolean init = false;
    private static final String fileLeft = "aero_arrow_left.cur";
    private static final String fileRight = "aero_arrow_right.cur";
    private static final Path folder = Paths.get(System.getProperty("java.io.tmpdir")).resolve("mouse");

    private static void init() {
        if (init) {
            System.out.println("Already initialized");
            return;
        }
        if (!Files.exists(folder)) {
            try {
                Files.createDirectory(folder);
                System.out.println("Created directory: " + folder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        URL url = null;
        url = Switch.class.getClassLoader().getResource(fileLeft);
        copyFile(url, fileLeft);
        url = Switch.class.getClassLoader().getResource(fileRight);
        copyFile(url, fileRight);
        System.out.println("Initialized");
        init = true;
    }

    private static void copyFile(URL url, String file) {
        Path folder = Paths.get(System.getProperty("java.io.tmpdir")).resolve("mouse");
        url = Switch.class.getClassLoader().getResource(file);
        if (url == null) {
            System.out.println("No cursor loaded");
            return;
        }
        try (InputStream is = url.openStream()) {
            Files.copy(is, folder.resolve(file), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadCurse(HAND hand) {
        boolean result = MKUser32.INSTANCE.SwapMouseButton(hand == HAND.LEFT);
        System.out.println(result);

        final WinNT.HANDLE hImage;
        init();
        String fileName = null;
        if (hand == HAND.LEFT) {
            fileName = folder.resolve(fileLeft).toAbsolutePath().toString();
        } else {
            fileName = folder.resolve(fileRight).toAbsolutePath().toString();
        }

        System.out.println("The cursor file is: " + fileName);
        hImage = User32.INSTANCE.LoadImage(null, fileName, WinUser.IMAGE_CURSOR, 0, 0,
                WinUser.LR_LOADFROMFILE);


        if (hImage != null) {
            System.out.println("LoadImage success");
        } else {
            System.out.println("LoadImage failed");
        }

        MKUser32.INSTANCE.SetSystemCursor(hImage, WinUser.IDC_ARROW);
    }

    public static void main(String[] args) {
        loadCurse(HAND.LEFT);
        // loadCurse(HAND.RIGHT);
    }
}
