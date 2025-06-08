package launcher;

import org.apache.catalina.startup.Tomcat;
import java.io.File;
import java.awt.Desktop;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws Exception {
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);
        tomcat.getConnector(); // Crea el conector

        tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        System.out.println("Iniciando servidor en http://localhost:8080/");
        tomcat.start();

        // Abrir navegador automáticamente
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://localhost:8080/"));
        }

        tomcat.getServer().await();
    }
}
