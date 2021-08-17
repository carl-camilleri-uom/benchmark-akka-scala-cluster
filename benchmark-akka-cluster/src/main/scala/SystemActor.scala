import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object SystemActor {

  def apply(): Behavior[PingPongActor.Ping] =
    Behaviors.setup(context => new SystemActor(context))


}

class SystemActor(context:ActorContext[PingPongActor.Ping]) extends AbstractBehavior[PingPongActor.Ping](context){
  import java.net.InetAddress

  import PingPongActor._

  override def onMessage(message: Ping): Behavior[Ping] = {

    message.replyTo ! Pong(message.whom, context.self)
    this
  }

}