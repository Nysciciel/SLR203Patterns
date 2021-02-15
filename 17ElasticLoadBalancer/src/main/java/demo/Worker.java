package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Worker extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Worker() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Worker.class, () -> {
			return new Worker();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Task) {
			Task message = (Task) _message;
			log.info("(" + getSelf().path().name() + ") received task:" + message.s);
			getSender().tell(new Finished(), getSelf());
		}
	}
}
