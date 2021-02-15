package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Sender extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Sender() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof GoMessage) {
			GoMessage message = (GoMessage) _message;
			log.info("(" + getSelf().path().name() + ") received a go message from (" + getSender().path().name() + ")");
			Content m = new Content("m");
			message.broad.tell(m, getSelf());
		}
	}
}
