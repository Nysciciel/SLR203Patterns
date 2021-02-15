package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.time.Duration;
import akka.actor.Props;

public class BroadcastRoundRobin {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef a = system.actorOf(Sender.createActor(), "a");
		final ActorRef b = system.actorOf(Receiver.createActor(), "b");
		final ActorRef c = system.actorOf(Receiver.createActor(), "c");
		final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");


		system
			.scheduler()
			.scheduleOnce(
			Duration.ofMillis(1000),
				new Runnable() {
					@Override
					public void run() {
						a.tell(new GoMessage(broadcaster), ActorRef.noSender());
					}
				},
				system.dispatcher());


		JoinMessage join = new JoinMessage();
		broadcaster.tell(join, b);
		broadcaster.tell(join, c);


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
