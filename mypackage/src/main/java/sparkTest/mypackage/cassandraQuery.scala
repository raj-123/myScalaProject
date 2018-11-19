package sparkTest.mypackage

object cassandraQuery {
  def main(args: Array[String]) {
  import com.datastax.driver.core.{Session, Cluster}

val cluster = Cluster.builder().addContactPoint("localhost").build()//withPort(8080).build()
val session = cluster.connect("MyKeySpace")
  }
}