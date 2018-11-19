package sparkTest.mypackage
import com.redis._

object redisPubSub {
  def main(args: Array[String]) {
    
    val r = new RedisClient(args(0), args(1).toInt)
    r.auth("abhit")
    
    while(true){
    
    r.subscribe(args(2).toString()){ m => println(m) }
    println("done")
    }
    
    
    
  }
  
}