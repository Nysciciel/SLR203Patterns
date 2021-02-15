package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Client extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Client() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Client.class, () -> {
			return new Client();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Ref){
			Ref message = (Ref)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains ref to:" + message.ref.path().name());
			Request req1 = new Request("req1");
			Request req2 = new Request("req2");
			message.ref.tell(req1, getSelf());
			message.ref.tell(req2, getSelf());
		}
		if(_message instanceof Response){
			Response message = (Response)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains message:" + message.s);
		}
	}
}
