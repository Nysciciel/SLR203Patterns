package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Client extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef session;
	private ActorRef manager;

	public Client() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Client.class, () -> {
			return new Client();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof m2) {
			m2 message = (m2) _message;
			log.info("(" + getSelf().path().name() + ") received m2 (" + getSender().path().name() + ")");
			session.tell(new m3(), getSelf());
			manager.tell(new EndSession(session), getSelf());
		}
		if(_message instanceof Ref) {
			Ref message = (Ref) _message;
			log.info("(" + getSelf().path().name() + ") received ref (" + getSender().path().name() + ")" + "to" + "("+message.ref.path().name()+")");
			session = message.ref;
			session.tell(new m1(), getSelf());
			manager = getSender();
		}
	}
}
