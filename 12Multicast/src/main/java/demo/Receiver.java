package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Receiver extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Receiver() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Receiver.class, () -> {
			return new Receiver();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Response) {
			Response message = (Response) _message;
			log.info("(" + getSelf().path().name() + ") received a message from (" + getSender().path().name() + ")" + "contains:" + message.s);
		}
	}
}
