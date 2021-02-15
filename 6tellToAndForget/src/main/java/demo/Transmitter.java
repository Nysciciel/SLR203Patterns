package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Transmitter extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Transmitter() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Transmitter.class, () -> {
			return new Transmitter();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof MyMessage){
			MyMessage message = (MyMessage)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains message:" + message.s + " to send to" + message.ref1.path().name());
			MyMessage toSend = new MyMessage(message.s);
			message.ref1.tell(toSend, getSender());
			
		}
	}

}
