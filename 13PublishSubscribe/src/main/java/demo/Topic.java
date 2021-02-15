package demo;

import java.util.ArrayList;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Topic extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> subscribers = new ArrayList<ActorRef>();

	public Topic() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Topic.class, () -> {
			return new Topic();
		});
	}

	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Subscribe){
			Subscribe message = (Subscribe)_message;
			log.info("("+getSelf().path().name()+") received a subscribe message from ("+ getSender().path().name() +")");
			subscribers.add(getSender());
		}
		if(_message instanceof Unsubscribe){
			Unsubscribe message = (Unsubscribe)_message;
			log.info("("+getSelf().path().name()+") received a unsubscribe message from ("+ getSender().path().name() +")");
			subscribers.remove(getSender());
		}
		if(_message instanceof Publication){
			Publication message = (Publication)_message;
			log.info("("+getSelf().path().name()+") received a publication from ("+ getSender().path().name() +")" + "contains:" + message.s);

			for (ActorRef i: subscribers){
				i.tell( new Content(message.s), getSelf());
			}
		}
	}

}
