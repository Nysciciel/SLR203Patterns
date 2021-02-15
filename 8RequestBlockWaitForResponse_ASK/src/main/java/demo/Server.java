package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Server extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Server() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Server.class, () -> {
			return new Server();
		});
	}

	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Request1){
			Request1 message = (Request1)_message;
			log.info("("+getSelf().path().name()+") received a request1 from ("+ getSender().path().name() +")");
			getSender().tell(new Response1(), getSelf());
		}
		if(_message instanceof Request2){
			Request2 message = (Request2)_message;
			log.info("("+getSelf().path().name()+") received a request2 from ("+ getSender().path().name() +")");
			getSender().tell(new Response2(), getSelf());
		}
	}

}
