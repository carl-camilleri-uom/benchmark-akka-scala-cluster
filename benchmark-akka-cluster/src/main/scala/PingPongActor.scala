import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.scaladsl.AbstractBehavior

trait MySerializable


object PingPongActor {

  final case class Ping(whom: String, replyTo: ActorRef[Pong]) extends MySerializable
  final case class Pong(whom: String, from: ActorRef[Ping]) extends MySerializable

  def apply(entityId:String): Behavior[PingPongActor.Ping] =
    Behaviors.setup(context => new PingPongActor(context,entityId))

//  def apply(): Behavior[PingPongActor.Ping] =
//    Behaviors.setup(context => new PingPongActor(context))

}

class PingPongActor(context:ActorContext[PingPongActor.Ping], entityId:String) extends AbstractBehavior[PingPongActor.Ping](context){
  import PingPongActor._

  import java.net.InetAddress

  private val _ip = InetAddress.getLocalHost
  private val _hostname = _ip.getHostName

  override def onMessage(message: Ping): Behavior[Ping] = {
    //context.log.info("Hello {}!", _hostname)
    message.replyTo ! Pong(s"${message.whom} from ${_hostname}", context.self)
    this
  }

}