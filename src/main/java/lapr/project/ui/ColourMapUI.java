package lapr.project.ui;

import lapr.project.model.MapStore;

public class ColourMapUI implements Runnable {

    @Override
    public void run() {
        MapStore instance = new MapStore();
        instance.reader.PaisReader("src/main/java/lapr/project/data/countries.csv");
        instance.reader.FronteirasReader("src/main/java/lapr/project/data/borders.csv");
        instance.colorirMapa();
    }
}
