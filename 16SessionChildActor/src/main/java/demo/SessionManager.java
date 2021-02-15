package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class SessionManager extends UntypedAbstractActor{

	private ActorSystem system;
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public SessionManager(ActorSystem system) {this.system = system;}

	// Static function creating actor
	public static Props createActor(ActorSystem system) {
		return Props.create(SessionManager.class, () -> {
			return new SessionManager(system);
		});
	}

	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof CreateSession){
			CreateSession message = (CreateSession)_message;
			ActorRef session = system.actorOf(Session.createActor(), "Session:"+getSender().path().name());
			log.info("("+getSelf().path().name()+") received a CreateSession message from ("+ getSender().path().name() +")");
			getSender().tell(new Ref(session), getSelf());
		}
		if(_message instanceof EndSession){
			EndSession message = (EndSession)_message;
			ActorRef session = message.ref;
			system.stop(session);
			log.info("("+getSelf().path().name()+") received a end Session message from ("+ getSender().path().name() +")");
		}
	}
}
