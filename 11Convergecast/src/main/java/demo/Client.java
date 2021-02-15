package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.time.Duration;


public class Client extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private int i=1;
	private ActorSystem system;
	private ActorRef merger;

	private void scheduleGo(){
		system
				.scheduler()
				.scheduleOnce(
						Duration.ofMillis(1000),
						new Runnable() {
							@Override
							public void run() {
								getSelf().tell(new GoMessage(), ActorRef.noSender());
							}
						},
						system.dispatcher());
	}

	public Client(ActorRef merger, ActorSystem system){
		this.merger = merger;
		this.system = system;
		merger.tell(new JoinMessage(), getSelf());
		scheduleGo();
	}

	// Static function creating actor
	public static Props createActor(ActorRef merger, ActorSystem system) {
		return Props.create(Client.class, merger, system);
	}


	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof GoMessage) {
			GoMessage message = (GoMessage) _message;
			log.info("(" + getSelf().path().name() + ") received a go message from (" + getSender().path().name() + ")");
			Content m = new Content("hi"+String.valueOf(i));
			merger.tell(m, getSelf());
		}
		if(_message instanceof LastMessage) {
			LastMessage message = (LastMessage) _message;
			getSender().tell(new UnJoinMessage(), getSelf());
		}
		if(_message instanceof NotLastMessage) {
			i+=1;
			scheduleGo();
		}
	}
}
