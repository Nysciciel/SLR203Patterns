package demo;

import java.util.ArrayList;
import akka.util.Timeout;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;
import akka.actor.Props;
import scala.concurrent.Await;

public class ActorSearchMain {

	public static void main(String[] args) throws Exception {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef actorCreator = system.actorOf(ActorCreator.createActor(system), "ActorCreator");

		actorCreator.tell(new Create(), ActorRef.noSender());
		actorCreator.tell(new Create(), ActorRef.noSender());
		ActorRef actor1 = Await.result(system.actorSelection("/user/actor1").resolveOne(new Timeout(Duration.create(5, "seconds"))),Duration.create(5, "seconds"));
		ActorRef actor2 = Await.result(system.actorSelection("/user/actor2").resolveOne(new Timeout(Duration.create(5, "seconds"))),Duration.create(5, "seconds"));
		actor1.tell(new Create(), ActorRef.noSender());
		actor2.tell(new Create(), ActorRef.noSender());

		try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}

	public static void sleepFor(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
