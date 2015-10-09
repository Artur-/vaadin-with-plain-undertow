import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

import javax.servlet.ServletException;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 *
 * @author Matti Tahvonen
 */
public class MainClazz {

    public static void main(String... args) throws ServletException {

        ServletInfo servletInfo = new ServletInfo("vaadin", MyServlet.class)
                .addMapping("/*")
                .addInitParam("Resources", "http://virit.in/dawn/11")
                .setAsyncSupported(true);

        DeploymentInfo deployment = Servlets
                .deployment()
                .setClassLoader(Thread.currentThread().getContextClassLoader())
                .setContextPath("/")
                .setDeploymentName("vaadin")
                .addServlets(servletInfo)
                .addListeners(new ListenerInfo(in.virit.WidgetSet.class))
                .addServletContextAttribute(
                        WebSocketDeploymentInfo.ATTRIBUTE_NAME,
                        new WebSocketDeploymentInfo());

        DeploymentManager dm = Servlets.defaultContainer().addDeployment(
                deployment);
        dm.deploy();

        Undertow.builder().addHttpListener(8080, "localhost")
                .setHandler(dm.start()).build().start();

    }

    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyServlet extends VaadinServlet {

    }

    @Theme("valo")
    @Push
    public static class MyUI extends UI {

        @Override
        protected void init(VaadinRequest request) {
            setContent(new Label("HelloWorld!"));
        }

    }

}
