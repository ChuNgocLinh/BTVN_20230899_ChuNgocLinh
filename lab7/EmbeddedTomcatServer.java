package vn.edu.eaut.lab7;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.jasper.servlet.JasperInitializer;

public class EmbeddedTomcatServer {
    private static final int PORT = 8080;
    private static final String CONTEXT_PATH = "/lab07-hibernate-mvc";

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.setBaseDir(new File("target/tomcat").getAbsolutePath());
        tomcat.getHost().setAppBase(new File("target/tomcat/webapps").getAbsolutePath());
        tomcat.getConnector();

        File webappDir = new File("target/lab07-hibernate-mvc");
        if (!webappDir.exists()) {
            throw new IllegalStateException("Hay chay 'mvn clean package' truoc. Khong tim thay: "
                    + webappDir.getAbsolutePath());
        }

        removeEmbeddedServerLibraries(webappDir.toPath().resolve("WEB-INF/lib"));
        deleteIfExists(new File("target/tomcat/webapps/lab07-hibernate-mvc").toPath());

        Context context = tomcat.addWebapp(CONTEXT_PATH, webappDir.getAbsolutePath());
        context.setParentClassLoader(EmbeddedTomcatServer.class.getClassLoader());
        context.addServletContainerInitializer(new JasperInitializer(), null);

        tomcat.start();
        System.out.println("Lab 7 dang chay tai: http://localhost:" + PORT + CONTEXT_PATH + "/");
        tomcat.getServer().await();
    }

    private static void deleteIfExists(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }
        try (var paths = Files.walk(path)) {
            paths.sorted(Comparator.reverseOrder())
                    .forEach(item -> {
                        try {
                            Files.deleteIfExists(item);
                        } catch (IOException ex) {
                            throw new IllegalStateException("Khong xoa duoc file tam: " + item, ex);
                        }
                    });
        }
    }

    private static void removeEmbeddedServerLibraries(Path libDir) throws IOException {
        if (!Files.isDirectory(libDir)) {
            return;
        }
        try (var paths = Files.list(libDir)) {
            paths.filter(path -> {
                        String name = path.getFileName().toString();
                        return name.startsWith("tomcat-") || name.startsWith("ecj-");
                    })
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ex) {
                            throw new IllegalStateException("Khong xoa duoc server jar trong webapp: " + path, ex);
                        }
                    });
        }
    }
}
