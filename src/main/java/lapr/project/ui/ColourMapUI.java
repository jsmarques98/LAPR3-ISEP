package lapr.project.ui;

import lapr.project.data.MapStore;

public class ColourMapUI implements Runnable {

    @Override
    public void run() {
        MapStore instance = new MapStore();
        instance.reader.PaisReader("src/main/java/lapr/project/data/countries.csv");
        instance.reader.FronteirasReader("src/main/java/lapr/project/data/borders.csv");
        instance.colorirMapa();
    }
}
