import App.system
import akka.actor.typed.{ActorRef, ActorSystem, Scheduler}
import akka.cluster.sharding.typed.ShardingEnvelope
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, Entity, EntityRef, EntityTypeKey}
import akka.serialization.Serialization
import akka.util.Timeout
import io.jooby.ExecutionMode.EVENT_LOOP
import io.jooby.{Context, Jooby}

import scala.concurrent.duration._
import scala.jdk.javaapi.FutureConverters


object App {
  implicit val timeout: Timeout = 3.seconds
  val system: ActorSystem[PingPongActor.Ping] = ActorSystem(SystemActor(), "ping-pong-cluster-system")
  implicit val scheduler: Scheduler = system.scheduler

  val sharding = ClusterSharding(system)


  val TypeKey = EntityTypeKey[PingPongActor.Ping]("Ping")

  val shardRegion: ActorRef[ShardingEnvelope[PingPongActor.Ping]] =
  sharding.init(Entity(TypeKey)(createBehavior = entityContext => PingPongActor(entityContext.entityId)))

  def main(args: Array[String]): Unit = {
    Jooby.runApp(args,EVENT_LOOP,classOf[App])
  }

}

class App extends Jooby {
  implicit val scheduler: Scheduler = system.scheduler
  private implicit val askTimeout: Timeout = Timeout(5.seconds)


  try get("/{id}", (ctx: Context) => {

    val id = ctx.path("id").value


    val entityRef: EntityRef[PingPongActor.Ping] = App.sharding.entityRefFor(App.TypeKey,id)

    val res = entityRef.ask((replyTo: ActorRef[PingPongActor.Pong])=>  PingPongActor.Ping("hello", replyTo))

    FutureConverters.asJava(res).toCompletableFuture


  })


}