package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.ArrayList;


public class Actor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> known;

	public Actor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Actor.class, () -> {
			return new Actor();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof KnowActors) {
			KnowActors message = (KnowActors) _message;
			String s = "";
			for (ActorRef i:message.s){
				s += i.path().name()+" ";
			}
			log.info("(" + getSelf().path().name() + ") knows:"+s);
			known = message.s;
		}
		if(_message instanceof Message) {
			Message message = (Message) _message;
			log.info("(" + getSelf().path().name() + ") received a message from ("+getSender().path().name()+")");
			ArrayList<ActorRef> visited = new ArrayList<>(message.s);
			if (visited.contains(getSelf())){
				log.info("(" + getSelf().path().name() + ") Infinite loop detected");
			}
			else{
				visited.add(getSelf());
				for (ActorRef i:known){
					i.tell(new Message(visited), getSelf());
				}
			}
		}
	}
}
