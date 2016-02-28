import com.example.app.{SoSanhGiaAPIServlet, SoSanhGiaServlet}
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

/**
 * Created by vinhbachsy on 28/2/16.
 */
object JettyLauncher {
  def main(args: Array[String]) {
    val port = if (System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080

    val server = new Server(port)
    val context = new WebAppContext()
    context.setContextPath("/")
    context.setResourceBase("src/main/webapp")

    context.setEventListeners(Array(new ScalatraListener))

    context.addServlet(classOf[SoSanhGiaServlet], "/*")
    context.addServlet(classOf[SoSanhGiaAPIServlet], "/api/*")

    server.setHandler(context)

    server.start
    server.join
  }
}
