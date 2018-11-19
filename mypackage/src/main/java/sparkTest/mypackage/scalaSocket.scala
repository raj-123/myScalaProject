package sparkTest.mypackage
import java.net._;
import java.io._;
object scalaSocket {
  def main(args : Array[String]) {
   val  hostname = "107.6.151.182";
   val port = 8070;
   
            val socket = new Socket(hostname, port)
            val input = socket.getInputStream();
            val reader = new InputStreamReader(input);
 
            
            val data = new StringBuilder();
 
            
 
            System.out.println(data);
 
 
   
  }
}