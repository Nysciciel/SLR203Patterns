package demo;

import java.util.HashMap;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Merger extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private final ActorRef receiver;
	private HashMap<ActorRef, Boolean > map = new HashMap<ActorRef, Boolean >();

	public Merger(ActorRef receiver) {this.receiver = receiver;}

	// Static function creating actor
	public static Props createActor(ActorRef receiver) {
		return Props.create(Merger.class, receiver);
	}

	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof JoinMessage){
			JoinMessage message = (JoinMessage)_message;
			log.info("("+getSelf().path().name()+") received a join message from ("+ getSender().path().name() +")");
			map.put(getSender(), false);
		}
		if(_message instanceof UnJoinMessage){
			UnJoinMessage message = (UnJoinMessage)_message;
			log.info("("+getSelf().path().name()+") received a unjoin message from ("+ getSender().path().name() +")");
			map.remove(getSender());
		}
		if(_message instanceof Content){
			Content message = (Content)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")" + "contains:" + message.s);
			map.replace(getSender(), true);

			boolean done = true;
			for (boolean i:map.values()){
				if (!i){done = false;}
			}
			if (done){
				for (ActorRef i:map.keySet()){
					map.replace(i, false);
				}
				receiver.tell(new Content(message.s), getSelf());
				getSender().tell(new LastMessage(), getSelf());
			}
			else{
				getSender().tell(new NotLastMessage(), getSelf());
			}
		}
	}

}
