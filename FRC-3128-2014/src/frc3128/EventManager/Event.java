package frc3128.EventManager;

import frc3128.Util.DebugLog;

/**
 *
 * @author Noah Sutton-Smolin
 */
final class TimerEvent extends Event {
    private Event linkedEvent;
    private long targetTimeMillis = -1;

    public final void setTargetTime(long millis) {
        DebugLog.log(DebugLog.LVL_STREAM, this, "Event " + linkedEvent.toString() + " set for " + millis + " msec from now.");
        targetTimeMillis = System.currentTimeMillis() + millis;
        EventManager.addContinuousEvent(this);
    }

    private void destroyTimer() {
        targetTimeMillis = -1;
        EventManager.removeEvent(this);
    }

    public final void execute() {
        if (targetTimeMillis == -1) {
            DebugLog.log(DebugLog.LVL_SEVERE, this, "Timer set without valid time!");
            this.destroyTimer();
        }

        if (System.currentTimeMillis() > targetTimeMillis) {
            DebugLog.log(DebugLog.LVL_STREAM, this, "Running timed event " + this.linkedEvent.toString());
            linkedEvent.registerSingleEvent();
            this.destroyTimer();
        }
    }

    public final void linkEvent(Event e) {
        this.linkedEvent = e;
    }
}

final class CancelEvent extends Event {
    private Event linkedEvent;
    
    public CancelEvent(Event l) {this.linkedEvent = l;}
    
    public void execute() {
        linkedEvent.cancelEvent();
        DebugLog.log(DebugLog.LVL_STREAM, this, "Cancelled linked event: " + linkedEvent);
    }
}

public abstract class Event {
    private boolean eventIsCancelled;
    private TimerEvent timerEvent = null;
    
    /**
     * Empty constructor
     */
    public Event() {}

    /**
     * This function is where the event is actually run.
     */
    public abstract void execute();

    /**
     * Cancels the event if it is running. If the event is not running, this
     * does nothing. <p> Note: This does NOT cancel a timed event.
     */
    final public void cancelEvent() {
        eventIsCancelled = true;
        DebugLog.log(DebugLog.LVL_STREAM, this, "Event " + this.toString() + " has been cancelled!");
    }

    /**
     * Cancels the event after the timer has expired
     * 
     * @param msec the time after which the event will cancel
     */
    final public void cancelEventAfter(int msec) {
        DebugLog.log(DebugLog.LVL_STREAM, this, "Canceling self by trigger after " + msec + " msec.");
        new CancelEvent(this).registerTimedEvent(msec);
    }
    
    /**
     * Cancels the event's timer if it is running. If the timer is not running,
     * this does nothing. <p> Note: This does NOT cancel the event's execution.
     */
    final public void cancelTimedEvent() {
        timerEvent.cancelEvent();
    }

    final protected boolean shouldRun() {
        return !eventIsCancelled;
    }

    /**
     * Adds the event to the EventManager's queue as a single-run event. This
     * event will be run once, and then removed from the queue.
     */
    final public void registerSingleEvent() {
        this.eventIsCancelled = false;
        EventManager.addSingleEvent(this);
    }

    /**
     * Adds the event to the EventManager's queue as an iterable event. This
     * event will not be deleted from the queue once it has been run; it will be
     * run every iteration. It must be canceled explicitly.
     */
    final public void registerIterableEvent() {
        this.eventIsCancelled = false;
        EventManager.addContinuousEvent(this);
    }

    /**
     * Starts the event's linked TimerEvent. The TimerEvent is an iterable
     * event. Each iteration the TimerEvent will check to see if the time has
     * expired; if it has, it will run the current event as a SingleEvent. <p>
     * Note: If the timer has not been created, then it will be created when
     * this method is first called. It is recommended that you call
     * Event.prepareTimer() before executing this method.
     *
     * @param msec the time in milliseconds after which the event will execute.
     */
    final public void registerTimedEvent(int msec) {
        //TODO: Allow an event to be timed multiple times; delete the timerEvent instance
        if (timerEvent == null) {
            DebugLog.log(DebugLog.LVL_WARN, this, "Timer event called before instantiation! Timer startup delay possible.");
            prepareTimer();
        }
        timerEvent.setTargetTime(msec);
    }

    /**
     * Creates and links an instance of the TimerEvent. The TimerEvent must be
     * created if Event.registerTimedEvent() is to be called. It is recommended
     * that you call this function before invoking registerTimedEvent(). <p> You
     * may also pass "true" to the constructor during instantiation to create
     * the TimerEvent.
     */
    final public void prepareTimer() {
        if (this.timerEvent != null) return;
        this.timerEvent = new TimerEvent();
        timerEvent.linkEvent(this);
    }
}