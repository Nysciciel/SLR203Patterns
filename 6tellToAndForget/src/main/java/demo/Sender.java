package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Sender extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef tr = null;
    private ActorRef b = null;

	public Sender() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof MyMessage){
			MyMessage message = (MyMessage)_message;

			if (message.s != null){
				log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains message:" +message.s);
				MyMessage toSend = new MyMessage("Hello", this.b);
				if (this.tr != null){this.tr.tell(toSend, getSelf());}
				this.tr = null;
				this.b = null;
			}
			else{
				log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains refs to:" + message.ref1.path().name() + " " + message.ref2.path().name());
				this.tr = message.ref1;
				this.b = message.ref2;
			}
		}
	}

}
