package lapr.project.utils;

import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

        String path = "testReader";

        private final Ship s1;
        ArrayList<Ship> shipArray = new ArrayList<>();
        ArrayList<DynamicShip> shipDataArray = new ArrayList<>();
        LocalDateTime t1 = LocalDateTime.of(2020, 12, 31, 17, 03, 00);
        LocalDateTime t2 = LocalDateTime.of(2020, 12, 31, 17, 19, 00);
        DynamicShip ds1 = new DynamicShip(t1, 42.92236, -66.97243, 12.5, 2.4, 358, 'B');
        DynamicShip ds2 = new DynamicShip(t2, 42.97875, -66.97001, 12.9, 13.1, 355, 'B');

        public CsvReaderTest() {
            s1 = new Ship(210950000, shipDataArray, "VARAMO", 9395044, "C4SQ2", 70, 166, 25, 9.5, 0) {
            };
            shipDataArray.add(ds2);
            shipDataArray.add(ds1);
        }
        
    @Test
        void readCSV() throws Exception {
            shipArray = CsvReader.readCSV(path);
            ArrayList<Ship> expectRes = shipArray;
            assertEquals(expectRes, shipArray);
        }


        @Test
        void sortByDate() throws Exception {
            shipArray = CsvReader.readCSV(path);
            ArrayList<Ship> expectRes = shipArray;
            assertEquals(expectRes, shipArray);
        }
    }
