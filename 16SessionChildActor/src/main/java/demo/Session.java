package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Session extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Session() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Session.class, () -> {
			return new Session();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof m1) {
			m1 message = (m1) _message;
			log.info("(" + getSelf().path().name() + ") received m1 (" + getSender().path().name() + ")");
			getSender().tell(new m2(), getSelf());
		}
		if(_message instanceof m3) {
			m3 message = (m3) _message;
			log.info("(" + getSelf().path().name() + ") received m3 (" + getSender().path().name() + ")");
		}
	}
}
