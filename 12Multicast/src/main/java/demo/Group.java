package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class Group{
	private final ActorRef ref1;
	private final ActorRef ref2;

	public Group(ActorRef ref1, ActorRef ref2) {
		this.ref1 = ref1;
		this.ref2 = ref2;
	}



	public void send(Response s, ActorRef sender){
		ref1.tell(s, sender);
		ref2.tell(s, sender);
	}
}
