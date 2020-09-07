package com.habitarium.utility.screen;

import java.io.IOException;

public interface OpenScreens {
    void loadScreen(String screen, String title, Object object) throws IOException;
    void setReload(Reloadable reloadable);
}
