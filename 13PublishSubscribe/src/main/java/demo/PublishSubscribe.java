package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.time.Duration;
import akka.actor.Props;

public class PublishSubscribe {

	public static void main(String[] args) throws InterruptedException {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef topic1 = system.actorOf(Topic.createActor(), "topic1");
		final ActorRef topic2 = system.actorOf(Topic.createActor(), "topic2");
		final ActorRef a = system.actorOf(Subscriber.createActor(), "a");
		final ActorRef b = system.actorOf(Subscriber.createActor(), "b");
		final ActorRef c = system.actorOf(Subscriber.createActor(), "c");
		final ActorRef publisher1 = system.actorOf(Publisher.createActor(), "publisher1");
		final ActorRef publisher2 = system.actorOf(Publisher.createActor(), "publisher2");

		topic1.tell(new Subscribe(), a);
		topic1.tell(new Subscribe(), b);
		Thread.sleep(100);
		topic2.tell(new Subscribe(), b);
		topic2.tell(new Subscribe(), c);
		Thread.sleep(100);
		topic1.tell(new Publication("hello"), publisher1);
		Thread.sleep(100);
		topic2.tell(new Publication("world"), publisher2);
		Thread.sleep(100);
		topic1.tell(new Unsubscribe(), a);
		Thread.sleep(100);
		topic1.tell(new Publication("hello2"), publisher1);

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
