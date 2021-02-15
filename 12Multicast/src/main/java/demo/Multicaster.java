package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.HashMap;


public class Multicaster extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private HashMap<String, Group> groups = new HashMap<String, Group>();
	public Multicaster() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Multicaster.class, () -> {
			return new Multicaster();
		});
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof GroupCreate){
			GroupCreate message = (GroupCreate)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains:" + message.grp+" "+message.ref1.path().name()+" "+message.ref2.path().name());
			Group grp = new Group(message.ref1, message.ref2);
			groups.put(message.grp, grp);
		}
		if(_message instanceof GroupMessage){
			GroupMessage message = (GroupMessage)_message;
			log.info("("+getSelf().path().name()+") received a message from ("+ getSender().path().name() +")"+"contains:" + message.grp+" "+ message.s);
			Response res = new Response(message.s);
			groups.get(message.grp).send(res, getSelf());
		}
	}
}
