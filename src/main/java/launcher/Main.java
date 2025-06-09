package launcher;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.awt.Desktop;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws Exception {
        // Apuntar a la carpeta "webapp" directamente (debe estar junto al .exe)
        String webappDirLocation = "webapp";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector(); // Fuerza creación del conector

        // Cargar contexto con la ruta absoluta al directorio webapp
        var ctx = tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        // Establecer recursos del contexto (necesario para JSP, JSTL, etc.)
        WebResourceRoot resources = new StandardRoot(ctx);
        ctx.setResources(resources);

        System.out.println("Servidor iniciado en http://localhost:8080/");
        tomcat.start();

        // Abrir navegador automáticamente si es posible
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://localhost:8080/"));
        }

        tomcat.getServer().await();
    }
}
