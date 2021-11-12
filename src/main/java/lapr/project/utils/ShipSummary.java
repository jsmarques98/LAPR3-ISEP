package lapr.project.utils;

import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShipSummary {
    public static ArrayList create(Ship ship, String code) {
        ArrayList<Object> sumary = new ArrayList<>();
        LocalDateTime inicialTime = null, finalTime = null;
        int nMoves = 0;
        double maxSog = 0.0, meanSog = 0.0, maxCog = 0.0, meanCog = 0.0;
        double departLat = 0.0, departLong = 0.0, arrLat = 0.0, arrLong = 0.0;

        for (DynamicShip sd : ship.getDynamicShip()) {
            if (nMoves == 0) {
                inicialTime = sd.getBaseDateTime();
                departLat = sd.getLat();
                departLong = sd.getLon();
            } else if (nMoves + 1 == ship.getDynamicShip().size()) {
                finalTime = sd.getBaseDateTime();
                arrLat = sd.getLat();
                arrLong = sd.getLon();
            }
            if (maxSog < sd.getSog()) {
                maxSog = sd.getSog();
            }
            if (maxCog < sd.getCog()) {
                maxCog = sd.getCog();
            }
            meanSog += sd.getSog();
            meanCog += sd.getCog();
            nMoves++;
        }


        String finalMoveTime = getTime(inicialTime,finalTime);

        meanSog = meanSog / nMoves;
        meanCog = meanCog / nMoves;

        Double travelDistance = totalDistance(ship.getDynamicShip());
        Double deltaDistance = distanciaDelta(arrLat,departLat,arrLong,departLong);


        // Code
        if (code.equalsIgnoreCase("MMSI")) {
            sumary.add(ship.getMmsi());
        } else if (code.equalsIgnoreCase("IMO")) {
            sumary.add(ship.getImo());
        } else if (code.equalsIgnoreCase("CallSign")) {
            sumary.add(ship.getCallSign());
        }
        sumary.add(ship.getVesselName()); // Name
        sumary.add(ship.getVesselType()); // VasselType
        sumary.add(inicialTime); // BDT Inicial
        sumary.add(finalTime); // BDT Final
        sumary.add(finalMoveTime); // Tempo total dos movimentos
        sumary.add(nMoves); // Numero total de movimentos
        sumary.add(maxSog); // MaxSog
        sumary.add(meanSog); // MeanSog
        sumary.add(maxCog); // MaxCog
        sumary.add(meanCog); // MeanCog
        sumary.add(departLat); // DepartureLatitude
        sumary.add(departLong); // DepartureLongitude
        sumary.add(arrLat); // ArrivalLatitude
        sumary.add(arrLong); // ArrivalLongitude
        sumary.add(travelDistance); // TraveledDistance
        sumary.add(deltaDistance); // DeltaDistance

        return sumary;
    }

    public static double distanciaDelta(double lat1,double lat2, double log1, double log2){
        //1 grau = 111.11km
        double convert = 111.11;
        lat1 = lat1*convert;
        lat2 = lat2*convert;
        log1 = log1*convert;
        log2 = log2*convert;
        double lat = (lat2-lat1)*(lat2-lat1);
        double log = (log2-log1)*(log2-log1);
        return Math.sqrt(lat+log);
    }

    public static double totalDistance(ArrayList<DynamicShip> ds){
        double totalDistance = -1;
        double lat1 = 91;
        double lat2 = 91;
        double lon1 = 181;
        double lon2 = 181;
        DynamicShip pos1 = null;
        DynamicShip pos2 = null;

        for(int i = 0; i < ds.size() - 1; i++) {
            pos1= ds.get(i);
            pos2= ds.get(i+1);
            lat1= pos1.getLat();
            lat2= pos2.getLat();
            lon1= pos1.getLon();
            lon2= pos2.getLon();

            totalDistance+= distanciaDelta(lat1, lon1, lat2, lon2);
        }
        return totalDistance;
    }


    public static String getTime(LocalDateTime dob, LocalDateTime now) {
        final int MINUTES_PER_HOUR = 60;
        final int SECONDS_PER_MINUTE = 60;
        final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);

        return hours+"H"+minutes+"M";
    }

}
