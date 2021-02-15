package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class ActorCreator extends UntypedAbstractActor{

	private ActorSystem system;
	private int index=0;
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public ActorCreator(ActorSystem system) {
		this.system = system;
	}

	// Static function creating actor
	public static Props createActor(ActorSystem system) {
		return Props.create(ActorCreator.class, () -> {
			return new ActorCreator(system);
		});
	}

	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Create){
			Create message = (Create)_message;
			ActorRef actor = system.actorOf(Actor.createActor(), "actor"+String.valueOf(index + 1));
			log.info("("+getSelf().path().name()+") created actor:"+String.valueOf(index+1));
			index = index + 1;
		}
	}
}
