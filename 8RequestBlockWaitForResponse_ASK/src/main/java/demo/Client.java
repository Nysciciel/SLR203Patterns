package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;

import java.time.Duration;
import akka.pattern.Patterns;

import akka.dispatch.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.Promise;
import akka.util.Timeout;



public class Client extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef ref;

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
			Request1 req1 = new Request1();
			ref = message.ref;

			Timeout timeout = Timeout.create(Duration.ofSeconds(5));
			Future<Object> future = Patterns.ask(ref,new Request1(), timeout);
			Response1 res1 = (Response1) Await.result(future, timeout.duration());

			log.info("("+getSelf().path().name()+") received a response1 from ("+ getSender().path().name() +")");

			Request2 req2 = new Request2();
			ref.tell(req2, getSelf());
		}
		if(_message instanceof Response2){
			Response2 message = (Response2)_message;
			log.info("("+getSelf().path().name()+") received a response2 from ("+ getSender().path().name() +")");
		}
	}
}
