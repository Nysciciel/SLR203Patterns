package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Publisher extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Publisher() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Publisher.class, () -> {
			return new Publisher();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
	}
}
