package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;


public class LoadBalancer extends UntypedAbstractActor{

	private ActorSystem system;
	private int max=0;
	private ArrayList<Integer> pendingList;
	private ArrayList<ActorRef> workers;
	private int index=0;
	private boolean done=false;
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public LoadBalancer(ActorSystem system, int max) {
		this.system = system;
		this.max = max;
		pendingList = new ArrayList<Integer>(max);
		workers = new ArrayList<ActorRef>(max);
	}

	// Static function creating actor
	public static Props createActor(ActorSystem system, int max) {
		return Props.create(LoadBalancer.class, () -> {
			return new LoadBalancer(system, max);
		});
	}

	@Override
	public void onReceive(Object _message) throws Throwable {
		if(_message instanceof Task){
			if (!done){
				Task message = (Task)_message;
				if (workers.size() < max){
					ActorRef newWorker = system.actorOf(Worker.createActor(), "worker:"+String.valueOf(index + 1));
					workers.add(newWorker);
					pendingList.add(0);
				}
				workers.get(index).tell(new Task(message.s), getSelf());
				pendingList.set(index, pendingList.get(index)+1);
				log.info("("+getSelf().path().name()+") received task:"+message.s);
				index = (index + 1)%max;
			}
		}
		if(_message instanceof Done){
			Done message = (Done)_message;
			done = true;
			for (int i=0;i < max; i++){
				if (pendingList.get(i) == 0){
					system.stop(workers.get(i));
				}
			}
			log.info("("+getSelf().path().name()+") is done receiving tasks");
		}
		if(_message instanceof Finished){
			Finished message = (Finished)_message;
			log.info("("+getSender().path().name()+") finished a task");
			int workerIndex = workers.indexOf(getSender());
			pendingList.set(workerIndex, pendingList.get(workerIndex)-1);
			if (pendingList.get(workerIndex) <= 0){
				system.stop(workers.get(workerIndex));
				log.info("("+getSender().path().name()+") stopped");
			}
		}
	}
}
