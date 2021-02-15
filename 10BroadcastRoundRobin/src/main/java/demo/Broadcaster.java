package demo;

import java.util.ArrayList;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Broadcaster extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Broadcaster() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Broadcaster.class, () -> {
			return new Broadcaster();
		});
	}


	ArrayList<ActorRef> actors = new ArrayList<ActorRef>();

	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof JoinMessage){
			JoinMessage message = (JoinMessage)_message;
			log.info("("+getSelf().path().name()+") received a join message from ("+ getSender().path().name() +")");
			actors.add(getSender());
		}
		if(_message instanceof Content){
			Content message = (Content)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")" + "contains:" + message.s);
			Content m = new Content(message.s);
			for (ActorRef actor : actors){
				actor.tell(m, getSelf());
			}
		}
	}

}
