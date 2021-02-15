package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Target extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Target() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Target.class, () -> {
			return new Target();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof MyMessage){
			MyMessage message = (MyMessage)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains message:"+ message.s);
			MyMessage toSend = new MyMessage("hi");
			getSender().tell(toSend, getSelf());
		}
	}

}
