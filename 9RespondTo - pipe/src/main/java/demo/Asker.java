package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Asker extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Asker() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Asker.class, () -> {
			return new Asker();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Ref){
			Ref message = (Ref)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains:" + message.ref1+" "+message.ref2);
			Request req = new Request("req1", message.ref2);
			message.ref1.tell(req, getSelf());
		}
		if(_message instanceof Response){
			Response message = (Response)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains:" + message.s);
		}
	}
}
