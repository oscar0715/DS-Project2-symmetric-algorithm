import java.io.FileWriter;

/**
 * Created by Jiaming on 10/7/16.
 */
public class KMLWriter {
    String header;
    String footer;
    String pointFooter;

    String seanbHeader;
    String seanbLocation;

    String jamesbHeader;
    String jamesbLocation;

    String joemHeader;
    String joemLocation;

    String mikemHeader;
    String mikemLocation;

    public void writeToFile() {

        StringBuilder builder = new StringBuilder();
        builder.append(header);

        builder.append(seanbHeader);
        builder.append(seanbLocation);
        builder.append(pointFooter);

        builder.append(jamesbHeader);
        builder.append(jamesbLocation);
        builder.append(pointFooter);

        builder.append(joemHeader);
        builder.append(joemLocation);
        builder.append(pointFooter);

        builder.append(mikemHeader);
        builder.append(mikemLocation);
        builder.append(pointFooter);

        builder.append(footer);

        try {
            FileWriter fw = new FileWriter("./src/SecretAgents.kml");
            fw.write(builder.toString());
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void change(String name, String location) {
        if (name.equals("seanb")) {
            seanbLocation = location;
        }
        if (name.equals("jamesb")) {
            jamesbLocation = location;
        }
        if (name.equals("joem")) {
            joemLocation = location;
        }
        if (name.equals("mikem")) {
            mikemLocation = location;
        }
    }

    public KMLWriter() {
        header =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><kml xmlns=\"http://earth.google.com/kml/2.2\">\n"
                        + "\t<Document>\n" + "\t\t<Style id=\"style1\">\n"
                        + "\t\t\t<IconStyle>\n" + "\t\t\t\t<Icon>\n"
                        + "\t\t\t\t\t<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue- dot.png</href>\n"
                        + "\t\t\t\t</Icon> \n" + "\t\t\t</IconStyle> \n"
                        + "\t\t</Style> \n" + "\t\t ";

        // pointer footer
        pointFooter = "</coordinates>\n\t\t\t</Point>\n" + "\t\t</Placemark>\n";

        // seanb
        seanbHeader = "\t\t<Placemark>\n" + "\t\t\t<name>seanb</name>\n"
                              + "\t\t\t<description>Spy Commander</description>\n"
                              + "\t\t\t<styleUrl>#style1</styleUrl>\n"
                              + "\t\t\t<Point>\n" + "\t\t\t\t<coordinates>";
        seanbLocation = "-79.945289,40.44431,0.00000";

        // james
        jamesbHeader = "\t\t<Placemark>\n" + "\t\t\t<name>jamesb</name>\n"
                               + "\t\t\t<description>Spy</description>\n"
                               + "\t\t\t<styleUrl>#style1</styleUrl>\n"
                               + "\t\t\t<Point>\n" + "\t\t\t\t<coordinates>";
        jamesbLocation = "-79.945289,40.44431,0.00000";

        // joem
        joemHeader = "\t\t<Placemark>\n" + "\t\t\t<name>joem</name>\n"
                             + "\t\t\t<description>Spy</description>\n"
                             + "\t\t\t<styleUrl>#style1</styleUrl>\n"
                             + "\t\t\t<Point>\n" + "\t\t\t\t<coordinates>";
        joemLocation = "-79.945289,40.44431,0.00000";

        // mikem
        mikemHeader = "\t\t<Placemark>\n" + "\t\t\t<name>mikem</name>\n"
                              + "\t\t\t<description>Spy</description>\n"
                              + "\t\t\t<styleUrl>#style1</styleUrl>\n"
                              + "\t\t\t<Point>\n" + "\t\t\t\t<coordinates>";
        mikemLocation = "-79.945289,40.44431,0.00000";

        footer = "\t</Document>\n" + "</kml>";
    }

    public static void main(String[] args){
        KMLWriter k = new KMLWriter();
        k.writeToFile();
    }
}
