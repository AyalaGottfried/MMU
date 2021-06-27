package main.java.com.hit.driver;

import main.java.com.hit.client.CacheUnitClientObserver;
import main.java.com.hit.view.CacheUnitView;

import java.io.IOException;

public class CacheUnitClientDriver {
    public CacheUnitClientDriver() {
    }

    public static void main(String[] args) throws IOException {
        CacheUnitClientObserver cacheUnitClientObserver = new CacheUnitClientObserver();
        CacheUnitView view = new CacheUnitView();
        view.addPropertyChangeListener(cacheUnitClientObserver);
        view.start();
    }
}
