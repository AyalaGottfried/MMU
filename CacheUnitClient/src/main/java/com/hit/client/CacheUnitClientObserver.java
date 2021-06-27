package main.java.com.hit.client;

import main.java.com.hit.view.CacheUnitView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class CacheUnitClientObserver implements PropertyChangeListener {
    private final CacheUnitClient client;

    public CacheUnitClientObserver() throws IOException {
        client = new CacheUnitClient();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CacheUnitView view = (CacheUnitView) evt.getSource();
        String res = client.send(evt.getNewValue().toString());
        view.updateUIData(res);
    }
}
