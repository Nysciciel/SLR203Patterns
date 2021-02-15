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
		if(_message instanceof Request){
			Request message = (Request)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains message:" + message.s );
			Thread.sleep(100);
			getSender().tell(new Response(message.s + " validated"), getSelf());
		}
	}

}
